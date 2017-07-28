<?	
	include 'index.php';
	$anasayfa = "Ana Sayfa";
    $urun = "Garson";
    $urunekle = "Garson Bölüm";
    $urunliste = "Garson Parola";
    $durum = "Düzenleme";

    $hata = -1;

    $SqlSorguKat = mysql_query("SELECT id, acik FROM kat");

	if (mysql_escape_string($_GET[PersonelID])) {
        $SqlSorgu = mysql_query("SELECT ID, BULUNDUGUYER FROM garson WHERE ID='$_GET[PersonelID]'");
        list($ID, $BULUNDUGUYER) = mysql_fetch_array($SqlSorgu);
        list($katid, $katacik) = mysql_fetch_array(mysql_query("SELECT id, acik FROM kat WHERE id='$BULUNDUGUYER'"));
	}

    if (mysql_escape_string($_POST[bolum])) {
        $Sonuc = mysql_query("UPDATE garson SET BULUNDUGUYER='$_POST[bolum]' WHERE ID='$_GET[PersonelID]'");

        if ($Sonuc) {
            $hata = 1;
        } else {
            $hata = 0;
        }
    }

    if (mysql_escape_string($_POST[yeni_parola])) {
        
        $Sonuc = mysql_query("UPDATE garson SET PAROLA='$_POST[yeni_parola]' WHERE ID='$ID'");
        if ($Sonuc) {
            $hata = 4;
        } else {
            $hata = 0;
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
                                                            <strong > Garson Güncelleme İşlemi Başarılı !!!</strong>
                                                        </div>
                                                    </div>
                                                <? } else if ($hata == 0) { ?>
                                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                            <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                                                <strong > Garson Alanları Doldurunuz !!!</strong>
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
                                                                <strong > Parolalar BirBiriyle Uyuşmuyor !!!</strong>
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
                                                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Bölüm Seçiniz</label>
                                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                                <select id="bolum" class="select2_single form-control" tabindex="-1" name="bolum">
                                                                    <option value="<?=$katid; ?>"><?=$katacik; ?></option>
                                                                <? while (list($id, $acik) = mysql_fetch_array($SqlSorguKat)) {
                                                                    if ($id != $katid) { ?>
                                                                       <option value="<?=$id; ?>"><?=$acik; ?></option>
                                                                    <? }
                                                                ?>
                                                                    
                                                                <?}?>
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