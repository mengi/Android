<?	
	include 'index.php';
    include 'class/mysqlclass.php';

    $MasaID = -2;
    $SorguUrunGrup = "";

    try {
    	list($MasaAd, $MasaAktifMi) = mysql_fetch_array(mysql_query("SELECT acik, aktif FROM masa WHERE id='$MasaID'"));

        if ($MasaAktifMi == "off") {

            if ($MasaAktifMi == "off") {
                $_POST['masa'] = $MasaID;
                $_POST['aktif'] = 1;
                $_POST['bastrh'] = date('Y-m-d H:i:s');
                $_POST['siparistip'] = "2";

                $Sonuc = $db->sql_insert("siparis", $_POST) or die();
                $Sonuc1 = mysql_query("UPDATE masa SET aktif='on', acankim='kasa' WHERE id='$MasaID'");
                
            }
        }
        
        $SorguUrunGrup = mysql_query("SELECT id, acik FROM urungrup");
    } catch (Exception $e) {

    }
?>

<body  style="background-color: #ffffff;">
	
        <script type="text/javascript" language="JavaScript">
            function UrunGoster (UrunGrupID, MasaID) {

                $(".liste").load('masa_urun_liste.php?UrunGrupID='+UrunGrupID+'&MasaID='+MasaID);
            };
        </script>
       
        <script type="text/javascript" language="JavaScript">
            function SiparisGoster (MasaID) {
                $(".siparisliste").load('masa_siparis_liste.php?MasaID='+MasaID);
            };
        </script>
        <script type="text/javascript" language="JavaScript">
            function SiparisListeYenile () {
                location.reload();
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function SiparisUrunSil (SiparisID, MasaID) {
                //alert("SiparisID = "+SiparisID);
                $(".siparisliste").load('masa_siparis_sil.php?SiparisID='+SiparisID+'&MasaID='+MasaID); 
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function MutfagaGonder (SiparisID, MasaID) {
                //alert("SiparisID = "+SiparisID + "  MasaID="+MasaID);
                $(".siparisliste").load('mutfaga_gonder.php?SiparisID='+SiparisID+'&MasaID='+MasaID); 
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function SiparisDuzenle (SiparisID, MasaID, Miktar) {
                //alert("SiparisID = "+SiparisID + "  MasaID="+MasaID + "   Miktar="+Miktar);
                if (Miktar > 0) {
                    $(".siparisliste").load('masa_siparis_duzenle.php?SiparisID='+SiparisID+'&MasaID='+MasaID+'&Miktar='+Miktar); 
                };
            };
        </script>

        <script type="text/javascript" language="JavaScript">
            function KaydetUrun(UrunID, Miktar, MasaID) {

                //alert("kayıt");
                if (Miktar > 0) {
                    $(".liste").load('masa_siparis_kayit.php?UrunID='+UrunID+'&Miktar='+Miktar+'&MasaID='+MasaID);
                      //$('#myModal').modal('hide');
                };
                
                
            };
        </script>
       

	   <? include 'menu.php'; ?>

                
                    <div class="clearfix"></div>


                    <div class="row">
                        <div class="col-md-4 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Ürün Kategori <small><?=$MasaAd; ?></small></h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                            <ul class="dropdown-menu" role="menu">
                                                <li><a href="#">Settings 1</a>
                                                </li>
                                                <li><a href="#">Settings 2</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <br />
                                    <div class="row top_tiles">
                                        <? while ($UrunGrup = mysql_fetch_array($SorguUrunGrup)) { ?>
                                            <a  onclick="UrunGoster('<?=$UrunGrup[id]?>', '<?=$MasaID?>')">                 
                                            <div class="animated flipInY col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                <div class="tile-stats">
                                                    <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cubes"></i>
                                                    </div>
                                                    <div class="count"> </div>
                                                    <br>
                                                    <h3><?=$UrunGrup[acik]; ?></h3>
                                                    <p> 
                                                        
                                                                
                                                    </p>
                                                </div>
                                            </div>
                                            </a>
                                        <?}?>

                                    </div>
                                </div>
                            </div>
                        </div>
<!--
                        <div class="col-md-8 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Ürünler <small><?=$MasaAd; ?></small></h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                            <ul class="dropdown-menu" role="menu">
                                                <li><a href="#">Settings 1</a>
                                                </li>
                                                <li><a href="#">Settings 2</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <br />
                                    <div class="liste">
                                        
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
-->

                        <div class="col-md-8 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Ürünler <small><?=$MasaAd; ?></small></h2>
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
                                            <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true"> Ürün Liste</a>
                                            </li>
                                            <li role="presentation" class=""><a href="#tab_content2" onclick="SiparisGoster('<?=$MasaID?>')" role="tab" id="profile-tab" data-toggle="tab"  aria-expanded="false"> Sipariş Liste</a>
                                            </li>
                                            <li role="presentation" class=""><a href="#tab_content3" onclick="SiparisListeYenile()" role="tab" id="profile-tab" data-toggle="tab"  aria-expanded="false"> Sipariş Yenile</a>
                                            </li>
                                        </ul>
                                        <div id="myTabContent" class="tab-content">
                                            <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                                                <div class="x_content">
                                                    <br />
                                                    <div class="liste">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
                                                <div class="x_content">
                                                    <br />
                                                    <div class="siparisliste">
                                        
                                                    </div>
                                                    <div class="siparislistes">
                                        
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


    <script src="js/custom.js"></script>

        <!-- PNotify -->
    <script type="text/javascript" src="js/notify/pnotify.core.js"></script>
    <script type="text/javascript" src="js/notify/pnotify.buttons.js"></script>
    <script type="text/javascript" src="js/notify/pnotify.nonblock.js"></script>

    <!-- /footer content -->
</body>

</html>