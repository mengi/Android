<? 
    session_start();

    include 'index.php';
    include 'class/mysqlclass.php';
    
    $anasayfa = "Ana Sayfa";
    $urun = "Stok";
    $urunekle = "Stok Ekle";
    $urunliste = "Stok İstatislik";
    $durum = "İşlemler";

    $hata = -1;

    if (mysql_escape_string($_POST[stokadi]) AND (mysql_escape_string($_POST[stok_adeti]))) {
        $_POST['stokid'] = $_POST[stokadi];
        $_POST['miktar'] = (int) $_POST[stok_adeti];
        $_POST['tarih'] =  date('Y-m-d H:i:s');
        $_POST['durum'] = (int) $_POST[stok_durum];
        $_POST['stoktip'] = (int) $_POST[stok_tipi];
        
        $Sonuc = $db->sql_insert("stokbirhar", $_POST) or die($hata=0);

        if ($Sonuc) {
            $hata = 1;

            list($GAdet) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE id='$_POST[stokadi]'"));
            $YAdet = 0;
            if ($_POST[durum] == 0) {
                $YAdet = $GAdet + $_POST[stok_adeti];
            } else if($_POST[durum] == 1) {
                $YAdet = $GAdet - $_POST[stok_adeti];
            }

            if ($YAdet > 0) {
                $SqlSorgu = mysql_query("UPDATE stokbir SET tmiktar='$YAdet' WHERE id='$_POST[stokadi]'");
            } else {
                $hata = 4;
            }

            
        } else {
            $hata = 0;
        }
    }

    $SqlSorguUrunGrup = mysql_query("SELECT id, acik, fiyat, tmiktar FROM stokbir");

?>

<body class="nav-md">

    <div class="container body">
            <script type="text/javascript" language="JavaScript">
            function Aramayap() {
                var IlkTarih = document.getElementById('ilk_tarih').value;
                var SonTarih = document.getElementById('son_tarih').value;

                if (!IlkTarih) {IlkTarih=-1;};
                if (!SonTarih) {SonTarih=-2;};
                alert(IlkTarih);
                $(".liste").load('stok_giris_liste.php?IlkTarih='+IlkTarih+'&SonTarih='+SonTarih);
            };
        </script>

        <div class="main_container">
        <? include 'menu.php' ; ?>

            <div class="right_col" role="main">

                <? include 'alt_menu.php'; ?>
                <br />
                <div class="row">
                <div class="">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><i class="fa fa-bars"></i> <?=$urun; ?> <small><?=$durum; ?></small></h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                            <!--
                                            <ul class="dropdown-menu" role="menu">
                                                <li><a href="#">Settings 1</a>
                                                </li>
                                                <li><a href="#">Settings 2</a>
                                                </li>
                                            </ul>
                                            -->
                                        </li>
                                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">


                                    <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                        <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                                            <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true"><?=$urunekle; ?></a>
                                            </li>
                                            <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab"  aria-expanded="false"><?=$urunliste; ?></a>
                                            </li>
                                        </ul>
                                        <div id="myTabContent" class="tab-content">
                                                <?  if ($hata == 1) { ?>
                                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        <div class="alert alert-success alert-dismissible fade in" role="alert">
                                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                            <strong > Stok Giriş İşlemi Başarılı !!!</strong>
                                                        </div>
                                                    </div>
                                                <? } else if ($hata == 0) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Stok Giriş İşlemi Başarısız !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 2) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-warning alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Gerekli Alanı Doldurunuz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 4) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-warning alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Stok Miktarı Yeterli Değil !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } ?>
                                            <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                                                <div class="x_content">
                                                    <br />
                                                    <form id="demo-form2" data-parsley-validate class="form-horizontal form-label-left" action="" method="POST">

                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"> Stok Seçiniz <span class="required">*</span></label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="stokadi" class="select2_single form-control" tabindex="-1" name="stokadi">
                                                                
                                                                <? while (list($urunid, $urunacik) = mysql_fetch_array($SqlSorguUrunGrup)) {?>
                                                                    <option value="<?=$urunid; ?>"><?=$urunacik; ?></option>
                                                                <?}?>
                                                                
                                                                </select>
                                                            </div>
                                                        </div>


                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"> Stok Durum <span class="required">*</span></label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="stok_durum" class="select2_single form-control" tabindex="-1" name="stok_durum">
                                                               
                                                                    <option value="0">Giriş</option>
                                                                    <option value="1">Çıkış</option>
                                                                
                                                                </select>
                                                            </div>
                                                        </div>


                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"> Stok Tipi <span class="required">*</span></label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="stok_tipi" class="select2_single form-control" tabindex="-1" name="stok_tipi">
                                                               
                                                                    <option value="Kg"> Kg</option>
                                                                    <option value="Koli"> Koli</option>
                                                                    <option value="Adet"> Adet</option>
                                                                
                                                                </select>
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name"> Stok Adeti <span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <input type="text" id="stok_adeti" required="required" class="form-control col-md-7 col-xs-12" name="stok_adeti">
                                                            </div>
                                                        </div>

                                                        <div class="ln_solid"></div>
                                                        <div class="form-group">
                                                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        
                                                                <button type="submit" class="btn btn-success">Kaydet</button>
                                                            </div>
                                                        </div>

                                                    </form>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
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
        </div>

    </div>

    <script src="js/bootstrap.min.js"></script>

    <!-- chart js -->
    <script src="js/chartjs/chart.min.js"></script>
    <!-- bootstrap progress js -->
    <script src="js/progressbar/bootstrap-progressbar.min.js"></script>
    <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
    <!-- icheck -->
    <script src="js/icheck/icheck.min.js"></script>
    <script src="js/custom.js"></script>

    <!-- daterangepicker -->
    <script type="text/javascript" src="js/moment.min2.js"></script>
    <script type="text/javascript" src="js/datepicker/daterangepicker.js"></script>

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
</body>

</html>