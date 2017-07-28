<? 
    session_start();

    include 'index.php';
    include 'class/mysqlclass.php';
    
    $anasayfa = "Ana Sayfa";
    $urun = "Masa";
    $urunekle = "Masa Ekle";
    $urunliste = "Masa Liste";
    $durum = "İşlemler";

    $MasaSiraSayisi = 0;
    $hata = -1;

    $SqlSorguKat = mysql_query("SELECT id, acik FROM kat ORDER BY acik");
    $SqlSorguMasa = mysql_query("SELECT MAX(id) AS Sayi FROM masa");

    while ($MasaSay = mysql_fetch_array($SqlSorguMasa)) {
         $MasaSiraSayisi = $MasaSay[Sayi];
     }

    $MasaSiraSayisi += 1; 

    if (mysql_escape_string($_POST[masa_adi]) and mysql_escape_string($_POST[kat_adi])) {
        $_POST['acik'] = $_POST[masa_adi];
        $_POST['kat'] = $_POST[kat_adi];
        $_POST['aktif'] = "off";
        $_POST['sira'] = $MasaSiraSayisi;
        $_POST['siparis'] = 0;
        
        $Sonuc = $db->sql_insert("masa", $_POST) or die($hata=0);

        if ($Sonuc) {
            $hata = 1;
        } else {
            $hata = 0;
        }
    }

    function MasaSayisis ($BolumId) {
        list($sayac) = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM masa WHERE kat='$BolumId'"));
        return $sayac;
    }

    function GarsonSayisis ($BolumId) {
        list($sayac) = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM garson WHERE BULUNDUGUYER='$BolumId'"));
        return $sayac;
    }


    $SorguBolum = mysql_query("SELECT id, acik FROM kat");


?>

<body class="nav-md">

    <div class="container body">
        <script type="text/javascript" language="JavaScript">
            function Aramayap() {
                var AraKelime = document.getElementById('aranacak_kelime').value
                var BAraKelime = AraKelime.replace(" ", "~");
                //alert(BAraKelime);
                $(".liste").load('masa_liste.php?AraKelime='+BAraKelime);
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function MasaGoster (Id) {
                //alert(Id + "   " + Durum);
                $(".liste").load('masa_liste.php?BolumID='+Id);
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function Sil(Id) {
                var AraKelime = document.getElementById('aranacak_kelime').value
                $(".liste").load('masa_liste.php?AraKelime='+AraKelime+'&ID='+Id);
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
                                                            <strong > Masa Ekleme İşlemi Başarılı !!!</strong>
                                                        </div>
                                                    </div>
                                                <? } else if ($hata == 0) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Masa Alanları Doldurunuz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 2) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-warning alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Gerekli Alanı Doldurunuz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } ?>
                                            <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab" >
                                                <div class="x_content">
                                                    <br />
                                                    <form id="demo-form2" data-parsley-validate class="form-horizontal form-label-left" action="" method="POST">

                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"  for="first-name" > Masa Adı <span class="required">*</span>
                                                            </label>

                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                            <div class="input-group">
                                                                    <span class="input-group-btn">
                                                                        <button  class="btn btn-primary">Kod</button> 
                                                                    </span>
                                                                <input type="text" id="masa_adi" required="required" name="masa_adi" value="<?=$MasaSiraSayisi; ?>" class="form-control col-md-7 col-xs-12">
                                                            </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Kat Seçiniz</label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="kat_adi" class="select2_single form-control" tabindex="-1" name="kat_adi">
                                                                <? while(list($id, $acik) = mysql_fetch_array($SqlSorguKat)) {?>
                                                                    <option value="<?=$id;?>"><?=$acik;?></option>
                                                                <? } ?>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="ln_solid"></div>
                                                        <div class="form-group">
                                                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        
                                                                <button type="submit" class="btn btn-success"> Kaydet</button>
                                                            </div>
                                                        </div>

                                                    </form>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
                                                <div class="x_content">
                                                    <form class="form-horizontal form-label-left" method="POST">

                                                        <div class="form-group">
                                                            <label class="col-sm-3 control-label"></label>

                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <div class="input-group">
                                                                    <span class="input-group-btn">
                                                                        <button  href="masa_ekle.php" class="btn btn-primary"> Geri !</button> 
                                                                    </span>

                                                                    <input id="aranacak_kelime" type="text" class="form-control"  name="aranacak_kelime" placeholder="Masa Ara">
                                                                    <span class="input-group-btn">
                                                                        <button type="button" onclick="Aramayap()" class="btn btn-primary">Arama Yap !</button> 
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="divider-dashed"></div>

                                                        
                                                    </form>
                                                    <div class="liste">

                                                        
                                                        <div class="row top_tiles">
                                                            <? while ($Bolum = mysql_fetch_array($SorguBolum)) { ?>
                                                                
                                                                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                                                    <div class="tile-stats">
                                                                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-circle-o"></i>
                                                                        </div>
                                                                        <div class="count"></div>

                                                                        <h3><?=$Bolum[acik]; ?></h3>
                                                                        <p>Garson Sayısı : <?=GarsonSayisis($Bolum[id]); ?></p>
                                                                        <p>Masa Sayısı : <?=MasaSayisis($Bolum[id]); ?></p>
                                                                        <p> 
                                                                            <a onclick="MasaGoster('<?=$Bolum[id]?>')" class="btn btn-primary btn-xs"> <i class="fa fa-eye">
                                                                                        </i> Masaları Göster </a>
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                                
                                                            <?}?>

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

    </div>

    <div id="custom_notifications" class="custom-notifications dsp_none">
        <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
        </ul>
        <div class="clearfix"></div>
        <div id="notif-group" class="tabbed_notifications"></div>
    </div>

        <script src="js/bootstrap.min.js"></script>

        <!-- bootstrap progress js -->
        <script src="js/nicescroll/jquery.nicescroll.min.js"></script>


        <!-- select2 -->
        <script src="js/select/select2.full.js"></script>

        <script src="js/custom.js"></script>


        <!-- select2 -->
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
        <!-- /select2 -->
      
    <!-- /footer content -->
</body>

</html>