<?
	include 'index.php';

	$ToplamTutar = 0;
	$Indirim = 0;
	$hata=0;

	if (mysql_escape_string($_GET[MasaID])) {

		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
		$Durum = mysql_escape_string($_GET[Durum]);

		list($MasaAd) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));

		$SqlVeresiyeSorgu = mysql_query("SELECT id, adsoyad FROM veresiye");
		$SqlSiparisListeSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='$MasaID' 
                AND siparisid='$SiparisID' AND siparisdurum='2' AND durum='0'");

	}



?>

<body  style="background-color: #ffffff;">
    <script type="text/javascript" language="JavaScript">
        function VeresiyeOde (MasaID, SiparisID, Durum) {
			var Indirim = document.getElementById('indirim').value;
        	var ToplamTutar = document.getElementById('toplamtutar').value;
			var AdSoyad = document.getElementById('adsoyad').value;
			var Veresiye = document.getElementById('veresiye').value;
			var Ikram = document.getElementById('ikram').value;
        	
        	if (!Indirim) {Indirim=0;};
        	var GercekTutar = ToplamTutar - Indirim;
        	//alert(Indirim + '-'+ToplamTutar+ '-'+AdSoyad+'-'+Veresiye+ '-'+GercekTutar+' - '+MasaID+' - '+SiparisID+' - '+Durum);

            $(".veresiyeliste").load('veresiye_ode.php?MasaID='+MasaID+'&SiparisID='+SiparisID+'&Durum='+Durum+'&AdSoyad='
            	+AdSoyad+'&Veresiye='+Veresiye+'&GercekTutar='+GercekTutar+'&Indirim='+Indirim+'&Ikram='+Ikram);
        };
    </script>

	<? include 'menu.php'; ?>
        <div class="clearfix"></div>
        	<div class="row">
		        <div class="col-md-6 col-xs-12">
		            <div class="x_panel">
		                <div class="x_title">
		                    <h2> <?=$MasaAd; ?> Ödeme</h2>
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
		                	<form data-parsley-validate class="form-horizontal form-label-left">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">Ad Soyad <span class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                         <input type="text" id="adsoyad" required="required" class="form-control col-md-7 col-xs-12" name="adsoyad">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Telefon Numarası <span class="required">*</span></label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="text" id="telefon" class="form-control" data-inputmask="'mask' : '(999) 999-9999'" name="telefon">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Kat Seçiniz</label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <select id="veresiye" class="select2_single form-control" tabindex="-1" name="veresiye">
                                        <? while(list($id, $adsoyad) = mysql_fetch_array($SqlVeresiyeSorgu)) {?>
                                            <option value="<?=$id;?>"><?=$adsoyad;?></option>
                                        <? } ?>
                                        </select>
                                    </div>
                                </div>
                            </form>
		                </div>
		            </div>
		        </div>

		        <div class="col-md-6 col-xs-12">
		            <div class="x_panel">
		                <div class="x_title">
		                    <h2><?=$MasaAd; ?> Hesap </h2>
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
			            	<div class="col-md-12 col-sm-12 col-xs-12">
			            		<div class="veresiyeliste">
					               	<table class="table table-striped responsive-utilities jambo_table bulk_action">
					                  	<thead>
					                    	<tr class="headings">
					                        	<th class="column-title">Ürün Adı </th>
					                        	<th class="column-title">Adet </th>
					                        	<th class="column-title">Tutar </th>
					                        	<th class="column-title">İslemler </th>
					              			</tr>
								      	</thead>
								   		<tbody>
								   		<? 	
								   			$IkramTutar = 0;
								   			$KalanMiktar = 0;
								   			while ($SiparisListe = mysql_fetch_array($SqlSiparisListeSorgu)) {
								   				$KalanMiktar = $SiparisListe[adet] - ($SiparisListe[sanaladet] + $SiparisListe[ikramadet]);

								   				if ($KalanMiktar > 0) { 
								   					$ToplamTutar += ($KalanMiktar * $SiparisListe[tutar]);
								   					$IkramTutar += ($SiparisListe[ikramadet] *  $SiparisListe[tutar]); 
								   				?>

										          	<tr class="even pointer">
										              	<td class="a-center"><?=$SiparisListe[urunadi]; ?></td>
										              	<td class=" "><?=$KalanMiktar; ?></td>
										              	<td class=" "><?=$SiparisListe[tutar]; ?> <i class="fa fa-try"></i></td>
										              	<td class=" last"><a onclick="" class="btn btn-default btn-xs" >Ikram</a></td>
										          	</tr>
								        	<?  } 
								        	}?>
						              	</tbody>
					            	</table>
				            	</div>
			         		</div>

			         	<div class="x_content">
			         		<div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
			                    <input type="text" class="form-control has-feedback-right" id="ikram" value="<?=number_format($IkramTutar, 2, '.', ','); ?>" placeholder="İkram Tutarı Giriniz">
			                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
			              	</div>
			            </div>

			         	<div class="x_content">
			         		<div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
			                    <input type="text" class="form-control has-feedback-right" id="indirim" placeholder="İndirim Tutarı Giriniz">
			                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
			              	</div>

			                <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
			                    <input type="text" class="form-control" id="toplamtutar" placeholder="Tutar Giriniz" value="<?=number_format($ToplamTutar, 2, '.', ','); ?>">
			                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
			               	</div>
			            </div>

			         	<div class="x_content">
				            <a onclick="VeresiyeOde('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '4')" class="col-md-12 btn btn-warning">Cari Ödeme</a>
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
        <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="js/select/select2.full.js"></script>
        <script src="js/input_mask/jquery.inputmask.js"></script>
        <script src="js/custom.js"></script>
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
</body>
</html>