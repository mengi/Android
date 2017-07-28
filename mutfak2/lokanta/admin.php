<? 
    
    include 'index.php';
    $SqlSorguBolumIstatislik = mysql_query("SELECT id ,acik FROM kat");


    function MasaToplamTutar ($MasaID) {
        $MasaTutar = 0;

        (string) $BTarih = date ('Y-m-d');
        $CevirTarih = new DateTime($BTarih);
        $CevirTarih->modify('+1 day');
        (string) $STarih = $CevirTarih->format('Y-m-d');

        try {
            $SorguMasaTutar = mysql_query("SELECT toplam FROM siparis WHERE masa='$MasaID' AND bittrh BETWEEN '$BTarih' AND '$STarih'");
            while ($Masa = mysql_fetch_array($SorguMasaTutar)) {
                $MasaTutar += $Masa[toplam];
            }
        } catch (Exception $e) {
            $MasaTutar = 0;
        }

        return $MasaTutar;
    }

    function GunclukKazancs () {
        $GunlukToplam = 0;
        (string) $BTarih = date ('Y-m-d');
        $CevirTarih = new DateTime($BTarih);
        $CevirTarih->modify('+1 day');
        (string) $STarih = $CevirTarih->format('Y-m-d');
        try {
            list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT SUM(toplam) AS GunlukToplam FROM siparis WHERE bittrh BETWEEN '$BTarih' AND '$STarih'"));
            $GunlukToplam = $SqlSorgu;
        } catch (Exception $e) {
            $GunlukToplam = 0;
        }

        return $GunlukToplam;
    }


    function BolumToplamTutar($BolumID) {
        $BolumTutar = 0;
        try {
            $SqlSorguBolum = mysql_query("SELECT id FROM masa WHERE kat='$BolumID'");
            while ($Bolum = mysql_fetch_array($SqlSorguBolum)) {
                $BolumTutar += MasaToplamTutar($Bolum[id]);
            }
        } catch (Exception $e) {
            $BolumTutar = 0;
        }

        return $BolumTutar;
    }

    function OdemeTipTutar() {

        (string) $BTarih = date ('Y-m-d');
        $CevirTarih = new DateTime($BTarih);
        $CevirTarih->modify('+1 day');
        (string) $STarih = $CevirTarih->format('Y-m-d');

        $dizi = array('Nakit' => 0, 'Kredi' => 0, 'Veresiye' => 0);

        $KrediTutar = 0;
        $NakitTutar = 0;
        $VeresiyeTutar = 0;

        try {
            $SqlSiparisSorgu = mysql_query("SELECT id, toplam, odemetur FROM siparis WHERE bittrh BETWEEN '$BTarih' AND '$STarih'");

            while ($Siparis = mysql_fetch_array($SqlSiparisSorgu)) {
                if ($Siparis[odemetur] == "NAKIT") {
                    $NakitTutar += $Siparis[toplam];
                } else if ($Siparis[odemetur] == "KREDI"){
                    $KrediTutar += $Siparis[toplam];
                } else if ($Siparis[odemetur] == "PARCALI") {
                    list($ParcaliNakit) = mysql_fetch_array(mysql_query("SELECT SUM(tutar) AS Nakit FROM parcaliode WHERE siparisid='$Siparis[id]' AND odemetur='NAKIT'"));
                    list($ParcaliVeresiye) = mysql_fetch_array(mysql_query("SELECT SUM(tutar) AS Veresiye FROM parcaliode WHERE siparisid='$Siparis[id]' AND odemetur='VERESIYE'"));
                    list($ParcaliKredi) = mysql_fetch_array(mysql_query("SELECT SUM(tutar) AS Kredi FROM parcaliode WHERE siparisid='$Siparis[id]' AND odemetur='KREDI'"));
                    
                    $KrediTutar += $ParcaliKredi;
                    $NakitTutar += $ParcaliNakit;
                    $VeresiyeTutar += $ParcaliVeresiye;
                
                } else if ($Siparis[odemetur] == "VERESIYE") {
                    $VeresiyeTutar += $Siparis[toplam];
                }
            }        
                
        } catch (Exception $e) {
            $KrediTutar = 0;
            $NakitTutar = 0;
            $VeresiyeTutar = 0;
        }

        $dizi['Nakit'] = $NakitTutar;
        $dizi['Kredi'] = $KrediTutar;
        $dizi['Veresiye'] = $VeresiyeTutar;

        return $dizi;
    }

    $SqlSorguStok = mysql_query("SELECT stokID, acik FROM stok");

    list($PaketSatisToplamTutar) = mysql_fetch_array(mysql_query("SELECT SUM(toplam) AS PSToplamTutar FROM siparis WHERE masa='-1'"));
    list($SicakSatisToplamTutar) = mysql_fetch_array(mysql_query("SELECT SUM(toplam) AS SSToplamTutar FROM siparis WHERE masa='-2'"));

    $SqlStokDurum = mysql_query("SELECT acik, tmiktar FROM stokbir");

?>

<body class="nav-md">
        <script type="text/javascript" language="JavaScript">
            function Aramayap() {
                var IlkTarih = document.getElementById('ilk_tarih').value;
                var SonTarih = document.getElementById('son_tarih').value;
                var Stok = document.getElementById('stok').value;

                if (!IlkTarih) {IlkTarih=-1;};
                if (!SonTarih) {SonTarih=-2;};
                //alert(BAraKelime);
                $(".liste").load('rapor_liste.php?IlkTarih='+IlkTarih+'&SonTarih='+SonTarih+'&Stok='+Stok);
            };
        </script>

    <?  
        $Toplamsss = GunclukKazancs();
        if ($Toplamsss > 0) {
            
            $TutarDizi = OdemeTipTutar();
        
            $NakitYuzdelikDilim = 0;
            $KrediYuzdelikDilim = 0;
            $VeresiyeYuzdelikDilim = 0;

            $TumToplam = ($TutarDizi['Nakit'] + $TutarDizi['Kredi']+ $TutarDizi['Veresiye']);
            $YuzdelikDilim = 360.0 / ($TumToplam);

            $NakitYuzdelikDilim = $YuzdelikDilim * $TutarDizi['Nakit'];
            $KrediYuzdelikDilim = $YuzdelikDilim * $TutarDizi['Kredi'];
            $VeresiyeYuzdelikDilim = $YuzdelikDilim * $TutarDizi['Veresiye'];
        }



    ?>
    <div class="container body">


        <div class="main_container">
        <? include 'menu.php' ; ?>

        	<div class="right_col" role="main">

                <? include 'alt_menu.php'; ?>
                <br />

                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel fixed_height_320">
                                <div class="x_title">
                                    <h2>Bölüm Durum Tablosu</small></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <h4>Bölüm Adı</h4>
                                    <?
                                        while ($BolumIstatislik = mysql_fetch_array($SqlSorguBolumIstatislik)) {
                                    ?>
                                        <div class="widget_summary">
                                            <div class="w_left w_25">
                                                <span><?=$BolumIstatislik[acik]; ?></span>
                                            </div>
                                            <?$Sonuc = (BolumToplamTutar($BolumIstatislik[id]) / 1000.0) * 100;?>
                                            <div class="w_center w_55">
                                                <div class="progress">
                                                    <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <?=$Sonuc;?>%;">
                                                        <span class="sr-only"><?=$Sonuc;?>% Complete</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="w_right w_20">
                                                <span><?=BolumToplamTutar($BolumIstatislik[id]); ?> <i class="fa fa-try"></i></span>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    <? } ?>
                                </div>
                                <div class="x_title">
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                        <div class="widget_summary">
                                            <div class="w_left w_25">
                                                <span>Sıcak Satış</span>
                                            </div>
                                            <?$SSonuc = ($SicakSatisToplamTutar / 1000.0) * 100;?>
                                            <div class="w_center w_55">
                                                <div class="progress">
                                                    <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <?=$SSonuc;?>%;">
                                                        <span class="sr-only"><?=$SSonuc;?>% Complete</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="w_right w_20">
                                                <span><?=$SicakSatisToplamTutar; ?> <i class="fa fa-try"></i></span>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="widget_summary">
                                            <div class="w_left w_25">
                                                <span>Paket Satış</span>
                                            </div>
                                            <?$PSonuc = ($PaketSatisToplamTutar / 1000.0) * 100;?>
                                            <div class="w_center w_55">
                                                <div class="progress">
                                                    <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <?=$PSonuc;?>%;">
                                                        <span class="sr-only"><?=$PSonuc;?>% Complete</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="w_right w_20">
                                                <span><?=$PaketSatisToplamTutar; ?> <i class="fa fa-try"></i></span>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel fixed_height_320">
                                <div class="x_title">
                                    <h2>Günlük Kazanç </h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">

                                    <table class="tile" style="width:100%">
                                        <tr>
                                            <th style="width:37%;">
                                                <span> Gösterim</span>
                                            </th>
                                            <th>
                                                <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                                                    <span class="hidden-small">Ad</span>
                                                </div>
                                                <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                                                    <span class="hidden-small">Kazanç</span>
                                                </div>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td>
                                                <canvas id="canvas1" height="140" width="140" style="margin: 15px 10px 10px 0"></canvas>
                                            </td>
                                            <td>
                                                <table class="tile_info">
                                                    <tr>
                                                        <td>
                                                            <p><i class="fa fa-square blue"></i>Nakit <i class="fa fa-try"></i></p>
                                                        </td>
                                                        <td><?=number_format($TutarDizi['Nakit'], 2, '.', ','); ?> </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <p><i class="fa fa-square green"></i>Kredi <i class="fa fa-try"></i></p>
                                                        </td>
                                                        <td><?=number_format($TutarDizi['Kredi'], 2, '.', ','); ?> </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <p><i class="fa fa-square purple"></i>Veresi.<i class="fa fa-try"></i></p>
                                                        </td>
                                                        <td><?=number_format($TutarDizi['Veresiye'], 2, '.', ','); ?> </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>


                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="x_panel fixed_height_320">
                                <div class="x_title">
                                    <h2>Günlük Kasa Durum </small></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <div class="dashboard-widget-content">
                                        <ul class="quick-list">
                                            <li><i class="fa fa-calendar-o"></i><a href="#">Tüm Kazanç</a>
                                            </li>
                                        </ul>

                                        <div class="sidebar-widget">
                                            <h4>Hedef Çizelgesi</h4>
                                            <canvas width="150" height="80" id="foo" class="" style="width: 160px; height: 100px;"></canvas>
                                            <div class="goal-wrapper">
                                                <span class="gauge-value pull-left"><i class="fa fa-try"></i></span>
                                                <span id="gauge-text" class="gauge-value pull-left"><?=number_format(GunclukKazancs(), 2, '.', ','); ?></span>
                                                <span id="goal-text" class="goal-value pull-right"><i class="fa fa-try"></i>4,000 </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="x_panel">

                                <div class="x_content">

                                    <div class="row">
                                        <div class="clearfix"></div>

                                        <div class="x_content">

                                            <section class="content invoice">
                                                <div class="row">
                                                    <div class="col-xs-12 table">
                                                        <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>ürün Adı</th>
                                                                    <th style="width: 18%"> Kalan Adet</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            <?

                                                                $SqlSorguStokBir = mysql_query("SELECT id, acik, tmiktar FROM stokbir WHERE tmiktar <= 10");
                                                                $Toplam = 0;
                                                                while($Sql = mysql_fetch_array($SqlSorguStokBir)) {
                                                            ?>  
                                                                <tr>
                                                                    <td><?=$Sql[id]; ?></td>
                                                                    <td><?=$Sql[acik]; ?></td>
                                                                    <td><?=number_format($Sql[tmiktar], 1, '.', ','); ?></td>
                                                                </tr>
                                                            <?  } ?>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <!-- /.col -->
                                                </div>
                                            </section>
                                        </div>
                                    
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="x_panel">

                                <div class="x_content">

                                    <div class="row">
                                        <div class="clearfix"></div>

                                        <div class="x_content">
                                            <form class="form-horizontal form-label-left" action="" method="POST">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">İlk Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <input type="text" class="form-control has-feedback-left" id="ilk_tarih" aria-describedby="inputSuccess2Status2">
                                                                    <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                                    <span id="inputSuccess2Status2" class="sr-only">(success)</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Son Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <input type="text" class="form-control has-feedback-left" id="son_tarih"  aria-describedby="inputSuccess2Status2">
                                                                    <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                                    <span id="inputSuccess2Status2" class="sr-only">(success)</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Son Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <select id="stok" class="select2_single form-control" tabindex="-1" name="stok">
                                                                        <? while($Stok = mysql_fetch_array($SqlSorguStok)) {?>
                                                                            <option value="<?=$Stok[stokID]; ?>"> <?=$Stok[acik]; ?></option>
                                                                        <? } ?>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="divider-dashed"></div>
                                                <div class="form-group">
                                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        
                                                        <a onclick="Aramayap()" class="btn btn-success"> Arama Yap!</a>
                                                    </div>
                                                </div>
                                                        
                                            </form>

                                            <div class="liste">

                                            </div>
                                        </div>
                                    
                                </div>
                            </div>
                        </div>

                    </div>
                    </div>

                    </div>
            </div>
   		</div>
    </div>

    <div id="custom_notifications" class="custom-notifications dsp_none">
        <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
        </ul>
        <div class="clearfix"></div>
        <div id="notif-group" class="tabbed_notifications"></div>
    </div>

    <script src="js/bootstrap.min.js"></script>

    <!-- chart js -->
    <script src="js/chartjs/chart.min.js"></script>
    <!-- bootstrap progress js -->
    <script src="js/progressbar/bootstrap-progressbar.min.js"></script>
    <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
    <!-- icheck -->
    <script src="js/icheck/icheck.min.js"></script>
    <!-- gauge js -->
    <script type="text/javascript" src="js/gauge/gauge.min.js"></script>
    <!-- daterangepicker -->
    <script type="text/javascript" src="js/moment.min2.js"></script>
    <script type="text/javascript" src="js/datepicker/daterangepicker.js"></script>
    <!-- sparkline -->
    <script src="js/sparkline/jquery.sparkline.min.js"></script>

    <script src="js/custom.js"></script>
    <!-- skycons -->
    <script src="js/skycons/skycons.js"></script>

    <!-- flot js -->
    <!--[if lte IE 8]><script type="text/javascript" src="js/excanvas.min.js"></script><![endif]-->
    <script type="text/javascript" src="js/flot/jquery.flot.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.pie.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.orderBars.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.time.min.js"></script>
    <script type="text/javascript" src="js/flot/date.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.spline.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.stack.js"></script>
    <script type="text/javascript" src="js/flot/curvedLines.js"></script>
    <script type="text/javascript" src="js/flot/jquery.flot.resize.js"></script>

    <!-- color picker -->
    <script src="js/colorpicker/bootstrap-colorpicker.js"></script>
    <script src="js/colorpicker/docs.js"></script>
    <!-- select2 -->
    <script src="js/select/select2.full.js"></script>


    <!-- /datepicker -->
    <script type="text/javascript">
        $(document).ready(function () {

            $('#ilk_tarih').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_2",
                dateFormat: 'dd-mm-yyyy'
            });            

            $('#son_tarih').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_2"
            });

        });
    </script>
    <script type=”text/javascript”>
        $(document).ready(function() {;
        $("#ilk_tarih").datepicker({ dateFormat: 'd-mm-yyyy' });
        });
    </script>

    <script>
            $(document).ready(function () {
                $(".select2_single").select2({
                    placeholder: "Select a state",
                    allowClear: true
                });
                $(".select2_group").select2({});
                $(".select2_multiple").select2({
                    maximumSelectionLength: 4,
                    placeholder: "With Max Selection limit 4",
                    allowClear: true
                });
            });
        </script>

    <script>
        $('document').ready(function () {
            $(".sparkline_one").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6], {
                type: 'bar',
                height: '40',
                barWidth: 9,
                colorMap: {
                    '7': '#a1a1a1'
                },
                barSpacing: 2,
                barColor: '#26B99A'
            });

            $(".sparkline_two").sparkline([2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 5, 6, 7, 5, 4, 3, 5, 6], {
                type: 'line',
                width: '200',
                height: '40',
                lineColor: '#26B99A',
                fillColor: 'rgba(223, 223, 223, 0.57)',
                lineWidth: 2,
                spotColor: '#26B99A',
                minSpotColor: '#26B99A'
            });

            var doughnutData = [
                {
                    value: <?=number_format($VeresiyeYuzdelikDilim, 2, '.', ','); ?>,
                    color: "#9B59B6"
                },
                {
                    value: <?=number_format($KrediYuzdelikDilim, 2, '.', ','); ?>,
                    color: "#26B99A"
                },
                {
                    value: <?=number_format($NakitYuzdelikDilim, 2, '.', ','); ?>,
                    color: "#3498DB"
                }
            ];
            var myDoughnut = new Chart(document.getElementById("canvas1").getContext("2d")).Doughnut(doughnutData);


        })
    </script>
    <script>
        var opts = {
        lines: 12, // The number of lines to draw
        angle: 0, // The length of each line
        lineWidth: 0.4, // The line thickness
        pointer: {
            length: 0.75, // The radius of the inner circle
            strokeWidth: 0.042, // The rotation offset
            color: '#1D212A' // Fill color
        },
        limitMax: 'false', // If true, the pointer will not go past the end of the gauge
        colorStart: '#1ABC9C', // Colors
        colorStop: '#1ABC9C', // just experiment with them
        strokeColor: '#F0F3F3', // to see which ones work best for you
        generateGradient: true
    };
    var target = document.getElementById('foo'); // your canvas element
    var gauge = new Gauge(target).setOptions(opts); // create sexy gauge!
    gauge.maxValue = 2000; // set max gauge value
    gauge.animationSpeed = 30; // set animation speed (32 is default value)
    gauge.set(<?=number_format(GunclukKazancs(), 2, '.', ','); ?>); // set actual value
    gauge.setTextField(document.getElementById("gauge-text"));
 </script>

    <!-- /footer content -->
</body>

</html>
