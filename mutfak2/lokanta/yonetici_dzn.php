<?	
	include 'index.php';
	$anasayfa = "Ana Sayfa";
    $urun = "Yönetici";
    $urunekle = "Yönetici Bölüm";
    $urunliste = "Yönetici Parola";
    $durum = "Düzenleme";

    $hata = -1;

	if (mysql_escape_string($_GET[YoneticiID])) {
		$SqlSorgu = mysql_query("SELECT id, kullanici, statu FROM yonetici WHERE id='$_GET[YoneticiID]'");
		list($id, $kullaniciadis, $durums) = mysql_fetch_array($SqlSorgu);
	}

    if (mysql_escape_string($_POST[kullaniciadi])) {
        $Sonuc = mysql_query("UPDATE yonetici SET kullanici='$_POST[kullaniciadi]', statu='$_POST[durum]' WHERE id='$id'");
        if ($Sonuc) {
            $hata = 1;
        } else {
            $hata = 0;
        }
    }

    if (mysql_escape_string($_POST[yeni_parola]) and mysql_escape_string($_POST[eski_parola])) {


        list($Durum) = mysql_fetch_array(mysql_query("SELECT id FROM yonetici WHERE id='$id' AND sifre=PASSWORD('$_POST[eski_parola]')"));
        
        if ($Durum) {
            $Sonuc = mysql_query("UPDATE yonetici SET sifre=PASSWORD('$_POST[yeni_parola]') WHERE id='$id'");
            if ($Sonuc) {
                $hata = 4;
            } else {
                $hata = 0;
            }    
        } else {
            $hata = 3;
        }
    }
    

?>

<body class="nav-md">

    <div class="container body">

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
                                        <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                                            <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true"><?=$urunekle; ?></a>
                                            </li>
                                            <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab"  aria-expanded="false"><?=$urunliste; ?></a>
                                            </li>
                                        </ul>

                                    <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                        <div id="myTabContent" class="tab-content">
                                                <?  if ($hata == 1) { ?>
                                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        <div class="alert alert-success alert-dismissible fade in" role="alert">
                                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                            <strong > Yönetici Güncelleme İşlemi Başarılı !!!</strong>
                                                        </div>
                                                    </div>
                                                <? } else if ($hata == 0) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Yönetici Alanları Doldurunuz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 2) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-warning alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Gerekli Alanı Doldurunuz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 3) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Lütfen Eski Parolayı Doğru Giriniz !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } else if ($hata == 4) {?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-success alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Parola Güncelleme İşlemi Başarılı !!!</strong>
                                                            </div>
                                                        </div>
                                                <? } ?>
                                            <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                                                <div class="x_content">
                                                    <br />
                                                    <form id="demo-form2" data-parsley-validate class="form-horizontal form-label-left" action="" method="POST">

                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name"> Kullanıcı Adı <span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <input type="text" id="kullaniciadi" required="required" class="form-control col-md-7 col-xs-12" name="kullaniciadi" value="<?=$kullaniciadis; ?>">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Durum Seçiniz</label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="durum" class="select2_single form-control" tabindex="-1" name="durum">
                                                                    <?if ($durums == "0") { ?>
                                                                        <option value="0">Admin</option>
                                                                        <option value="1">Kasa</option>
                                                                        <option value="2">Mutfak</option>
                                                                   <? } else if ($durums == "1") { ?>
                                                                        
                                                                        <option value="2">Mutfak</option>
                                                                        <option value="0">Admin</option>
                                                                        <option value="1">Kasa</option>
                                                                        
                                                                   <? } else if ($durums == "2") { ?>
                                                                        
                                                                        <option value="1">Kasa</option>
                                                                        <option value="0">Admin</option>
                                                                        <option value="2">Mutfak</option>
                                                                        
                                                                   <? } ?>

                                                                </select>
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
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Eski Parola</label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <input id="eski_parola" type="password" class="form-control" name="eski_parola">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Yeni Parola</label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <input id="yeni_parola" type="password" class="form-control" name="yeni_parola">
                                                            </div>
                                                        </div>
                                                        <div class="divider-dashed"></div>
                                                        <div class="form-group">
                                                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        
                                                                <button type="submit" class="btn btn-success">Kaydet</button>
                                                            </div>
                                                        </div>
                                                        
                                                    </form>
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