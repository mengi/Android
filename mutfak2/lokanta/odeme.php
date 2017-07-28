<?
	session_start();
	include 'index.php';
	include 'class/baglanti.php';
	$sid = $_SESSION[sid];

	//print_r($_SESSION[uyeler]);

	if (mysql_escape_string($_GET[MasaID])) {

		$MasaID = mysql_escape_string($_GET[MasaID]);
		list($masad) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));

		list($siparisid, $aktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
	}

?>

<body  style="background-color: #ffffff;">

    <script type="text/javascript" language="JavaScript">
        function UrunOdes (StokID, MasaID, Miktar, SiparisID) {
        	//alert(StokID + ' - ' + MasaID +' - '+ Miktar+' - '+ SiparisID);
            $(".siparisliste").load('odeme_liste.php?StokID='+StokID+'&MasaID='+MasaID+'&Miktar='+Miktar+'&SiparisID='+SiparisID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function UrunOdess (MasaID, SiparisID, Durum) {
        	//alert(MasaID + ' - ' + SiparisID +' - '+ Durum);
            $(".siparisliste").load('odeme_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&Durum='+Durum);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function UrunOdeHepsi (MasaID, SiparisID) {
        	//alert(MasaID + ' - '+SiparisID);
            $(".odemeliste").load('tumu_odeme_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function IkramEdilenAdet (MasaID, SiparisID, Adet, SiparisHaID) {
        	//alert(SiparisID + ' - '+MasaID+ ' - '+Adet+ ' - '+SiparisHaID);
            $(".odemeliste").load('ikram_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&Adet='+Adet+'&SiparisHaID='+SiparisHaID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function IkramEdilenAdetGeriAl (MasaID, SiparisID, SiparisHaID) {
        	//alert(SiparisID + ' - '+MasaID+ ' - '+Adet+ ' - '+SiparisHaID);
            $(".odemeliste").load('ikram_geri_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&SiparisHaID='+SiparisHaID);
        };
    </script>

   	<script type="text/javascript" language="JavaScript">
        function UrunIkramEt (SiparisID, MasaID) {
        	//alert(SiparisID + ' - '+MasaID);
            $(".odemeliste").load('ikram_liste.php?SiparisID='+SiparisID+'&MasaID='+MasaID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function UrunIkramGeriAl (MasaID, SiparisID) {
        	//alert(SiparisID + ' - '+MasaID);
            $(".odemeliste").load('ikram_geri_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID);
        };
    </script>

   	<script type="text/javascript" language="JavaScript">
        function ParcaliVeresiyeGor (MasaID, SiparisID) {
        	//alert(SiparisID + ' - '+MasaID);
            $(".siparisliste").load('parcaliveresiye.php?MasaID='+MasaID+'&SiparisID='+SiparisID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function ParcaliVeresiyeOde (MasaID, SiparisID, Durum, AdSoyad, Indirim, IkramTutar, ToplamTutar, VeresiyeID) {

        	if (!Indirim) {Indirim=0;};
        	if (!IkramTutar) {IkramTutar=0;};

        	//alert(MasaID+' - '+SiparisID+' - '+Durum+' - '+Indirim+' - '+IkramTutar+' - '+AdSoyad+' - '+ToplamTutar);

            $(".siparisliste").load('parcalisanalode.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&ToplamTutar='+ToplamTutar+
            	'&IkramTutar='+IkramTutar+'&Indirim='+Indirim+'&Durum='+Durum+'&AdSoyad='+AdSoyad+'&VeresiyeID='+VeresiyeID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function NKOdemeTumu (MasaID, SiparisID, Durum) {
        	var Indirim = document.getElementById('indirim').value;
        	var ToplamTutar = document.getElementById('toplamtutar').value;
			var IkramTutar = document.getElementById('ikramtutar').value;
        	var GercekTutar = ToplamTutar - Indirim;

        	if (!Indirim) {Indirim=0;};
        	if (!IkramTutar) {IkramTutar=0;};
        	//alert(MasaID + '-' + SiparisID+'-'+Durum+'-'+Indirim+' - '+IkramTutar);
            $(".odemeliste").load('tumu_odeme_liste.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&Durum='+Durum+'&Indirim='+Indirim+'&GercekTutar='+GercekTutar+'&IkramTutar='+IkramTutar);
        
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function PNKOdemeTumu (MasaID, SiparisID, Durum) {
        	var Indirim = document.getElementById('indirim').value;
        	var ToplamTutar = document.getElementById('toplamtutar').value;
			var IkramTutar = document.getElementById('ikramtutar').value;

        	if (!Indirim) {Indirim=0;};
        	if (!IkramTutar) {IkramTutar=0;};
        	//alert(MasaID + ' - ' + SiparisID+' - '+Durum+' - '+Indirim+' - '+IkramTutar+' - '+ToplamTutar);
            $(".siparisliste").load('parcalisanalode.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&ToplamTutar='+ToplamTutar+'&IkramTutar='+IkramTutar+'&Indirim='+Indirim+'&Durum='+Durum);
        };
    </script>	


    <script type="text/javascript" language="JavaScript">
        function ParcaliSanalSil (Index, Miktar) {
        	//alert(Index + ' - '+ Miktar);
        	if (Index == 0) {Index=-2};
            $(".siparisliste").load('sanalliste.php?SonIndex='+Index+'&Miktar='+Miktar);
        };
    </script>				

    <? include 'menu.php'; ?>
                   	<div class="clearfix"></div>
                <? 
                	if ($aktifmi == "1") {
                		$SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='$MasaID' AND siparisid='$siparisid' 
                			AND siparisdurum='2' AND durum='0'");
                
                ?>

	                    <div class="row">
	                    	<div class="siparisliste">
		                        <div class="col-md-6 col-xs-12">
		                            <div class="x_panel">
		                                <div class="x_title">
		                                    <h2> <?=$masad; ?> Ödeme</h2>
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

		                                    	
		                                        <div class="col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
		                                         	<div class="input-group">
		                                                <span class="input-group-btn">
		                                                    <button type="button" onclick="UrunOdess('<?=$MasaID?>', '<?=$siparisid?>', '3')" class="btn btn-primary">Sepet Gör !</button> 
		                                                </span>
		                                         		<input id="girilen_miktar" type="text" class="form-control"  name="girilen_miktar" placeholder="Adet Giriniz">
		                                                <span class="input-group-btn">
		                                                    <button type="button" onclick="UrunOdeHepsi('<?=$MasaID?>', '<?=$siparisid?>')" class="btn btn-primary">Tümünü Öde !</button> 
		                                                </span>
		                                            </div>
			                                        
		                                        </div>

		                                        
		                                    <div class="row top_tiles">
                                            <?  
                                                $KalanMiktar = 0;
                                                while ($SiparisUrun = mysql_fetch_array($SqlMasaSiparisSorgu)) { 
                                                    $KalanMiktar = ($SiparisUrun[adet] - ($SiparisUrun[sanaladet] + $SiparisUrun[ikramadet]));

                                                    if ($KalanMiktar > 0) { ?>
                                                    
		                                                <a  onclick="UrunOdes('<?=$SiparisUrun[id]?>', '<?=$MasaID?>', document.getElementById('girilen_miktar').value, '<?=$siparisid?>')">                 
		                                                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
		                                                    <div class="tile-stats">
		                                                        <div class="icon" style="color:#1ABB9C;">

		                                                        </div>
		                                                        <div class="count"> </div>
		                                                        <br>
		                                                        <h3><?=$SiparisUrun[urunadi]; ?></h3>
		                                                        <p> Ürün Adeti : <?=$KalanMiktar; ?></p>
		                                                    </div>
		                                                </div>
		                                                </a>
                                            <?		}
                                            	}
                                            ?>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>

		                        <div class="col-md-6 col-xs-12">
		                            <div class="x_panel">
		                                <div class="x_title">
		                                    <h2><?=$masad; ?> Hesap </h2>
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
		                                    <div class="odemeliste"></div>

		                                    </div>
		                                </div>
		                            </div>
		                        </div>
	                    	</div>
	               		</div>
                
				<? } else { ?>
						<div class="row">
	                    	<div class="siparisliste">
		                        <div class="col-md-6 col-xs-12">
		                            <div class="x_panel">
		                                <div class="x_title">
		                                    <h2> <?=$masad; ?> Ödeme</h2>
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
			                           		<div class="" role="tabpanel" data-example-id="togglable-tabs">
		                                        <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
		                                            <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">Paket Satış Öde</a>
		                                            </li>
		                                            <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab"  aria-expanded="false">Sıcak Satış Öde</a>
		                                            </li>
		                                        </ul>
		                                        <div id="myTabContent" class="tab-content">
		                                            <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab" >
		                                            	<?
		                                            		list($siparisid, $aktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='-1' AND aktif='1'"));
		                                            		$SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='-1' 
		                                            			AND siparisid='$siparisid' AND siparisdurum='2' AND durum='0'");

		                                            		if ($aktifmi == "1") { ?>
				                                                <div class="x_content">
								                                    <a  onclick="UrunOdeHepsi('-1', '<?=$siparisid?>')" class="col-md-12 btn btn-primary">Tümünü Öde !</a> 
								                                    <div class="row top_tiles">
						                                            <?  
						                                                $KalanMiktar = 0;
						                                                while ($SiparisUrun = mysql_fetch_array($SqlMasaSiparisSorgu)) { 
						                                                    $KalanMiktar = ($SiparisUrun[adet] - ($SiparisUrun[sanaladet] + $SiparisUrun[ikramadet]));

						                                                    if ($KalanMiktar > 0) { ?>
								                                                <a  onclick="UrunOdes('<?=$SiparisUrun[id]?>', '<?=$MasaID?>', document.getElementById('girilen_miktar').value, '<?=$siparisid?>')">                 
								                                                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
								                                                    <div class="tile-stats">
								                                                        <div class="icon" style="color:#1ABB9C;">

								                                                        </div>
								                                                        <div class="count"> </div>
								                                                        <br>
								                                                        <h3><?=$SiparisUrun[urunadi]; ?></h3>
								                                                        <p> Ürün Adeti : <?=$KalanMiktar; ?></p>
								                                                    </div>
								                                                </div>
								                                                </a>
						                                            <?		}
						                                            	}
						                                            ?>

				                                                </div>
			                                            <?  } ?>
		                                            </div>
		                                            <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
		                                            	<?
		                                            		list($siparisid, $aktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='-2' AND aktif='1'"));
		                                            		$SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='-2' 
		                                            			AND siparisid='$siparisid' AND siparisdurum='2' AND durum='0'");
		                                            		if ($aktifmi == "1") { ?>
				                                                <div class="x_content">
								                                    <a type="button" onclick="UrunOdeHepsi('-2', '<?=$siparisid?>')" class="col-md-12 btn btn-primary">Tümünü Öde !</a> 
								                                   	<div class="row top_tiles">
						                                            <?  
						                                                $KalanMiktar = 0;
						                                                while ($SiparisUrun = mysql_fetch_array($SqlMasaSiparisSorgu)) { 
						                                                    $KalanMiktar = ($SiparisUrun[adet] - ($SiparisUrun[sanaladet] + $SiparisUrun[ikramadet]));

						                                                    if ($KalanMiktar > 0) { ?>

								                                                <a  onclick="UrunOdes('<?=$SiparisUrun[id]?>', '<?=$MasaID?>', document.getElementById('girilen_miktar').value, '<?=$siparisid?>')">                 
								                                                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
								                                                    <div class="tile-stats">
								                                                        <div class="icon" style="color:#1ABB9C;">

								                                                        </div>
								                                                        <div class="count"> </div>
								                                                        <br>
								                                                        <h3><?=$SiparisUrun[urunadi]; ?></h3>
								                                                        <p> Ürün Adeti : <?=$KalanMiktar; ?></p>
								                                                    </div>
								                                                </div>
								                                                </a>
						                                            <?		}
						                                            	}
						                                            ?>
								                                   	</div>
								                                   	
				                                                </div>
				                                         <? } ?>
		                                            </div>
		                                        </div>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>

		                        <div class="col-md-6 col-xs-12">
		                            <div class="x_panel">
		                                <div class="x_title">
		                                    <h2><?=$masad; ?> Hesap </h2>
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
		                                    <div class="odemeliste"></div>

		                                    </div>
		                                </div>
		                            </div>
		                        </div>
	                    	</div>
	               		</div>
				<? } ?>


    <div id="custom_notifications" class="custom-notifications dsp_none">
        <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
        </ul>
        <div class="clearfix"></div>
        <div id="notif-group" class="tabbed_notifications"></div>
    </div>
        
        <script src="js/bootstrap.min.js"></script>
        <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="js/select/select2.full.js"></script>
        <script src="js/input_mask/jquery.inputmask.js"></script>
        <script src="js/custom.js"></script>

            <script src="js/icheck/icheck.min.js"></script>


        <!-- Datatables -->
        <script src="js/datatables/js/jquery.dataTables.js"></script>
        <script src="js/datatables/tools/js/dataTables.tableTools.js"></script>

        <!-- PNotify -->
    <script type="text/javascript" src="js/notify/pnotify.core.js"></script>
    <script type="text/javascript" src="js/notify/pnotify.buttons.js"></script>
    <script type="text/javascript" src="js/notify/pnotify.nonblock.js"></script>
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
        $(document).ready(function () {
            $(":input").inputmask();
        });
    </script>

    <!-- /footer content -->
</body>

</html>