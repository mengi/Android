<?
	include 'class/baglanti.php';

	$StokSayac = 0;
	$hata = 0;

	if (mysql_escape_string($_GET[MasaID])) {
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
		$Adet = mysql_escape_string($_GET[Adet]);
		$SiparisHaID = mysql_escape_string($_GET[SiparisHaID]);



		list($siparisid, $aktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));

		if ($aktifmi == "1") {
			if ($siparisid == $SiparisID) {
				$SorguSiparisIkramListe = mysql_query("SELECT id, stokid, urunadi, siparisid, masaid, sanaladet, ikramadet, adet FROM siparishareket WHERE masaid='$MasaID' 
				AND siparisid='$SiparisID' AND siparisdurum='2' AND durum='0'");
				
				if (!empty($Adet) && (!empty($SiparisHaID))) {

					list($GercekAdet, $GercekIkramAdet) = mysql_fetch_array(mysql_query("SELECT adet, ikramadet FROM siparishareket WHERE id='$SiparisHaID' AND siparisid='$SiparisID' AND masaid='$MasaID' AND siparisdurum='2' AND durum='0' "));

					if ($GercekAdet >= $Adet) {
						$Sonuc = mysql_query("UPDATE siparishareket SET ikramadet='$Adet' WHERE id='$SiparisHaID' AND siparisid='$SiparisID' AND masaid='$MasaID' AND siparisdurum='2' AND durum='0' ");
						if ($Sonuc) {

							$SqlSiparisHar = mysql_query("SELECT stokid, urunadi, adet, sanaladet, ikramadet FROM siparishareket WHERE siparisid='$SiparisID'");
							while ($SiparisHar = mysql_fetch_array($SqlSiparisHar)) {
								list($SDurumY) = mysql_fetch_array(mysql_query("SELECT durumd FROM stok WHERE stokID='$SiparisHar[stokid]'"));

								if ($SDurumY == 1) {

									$YeniAdet = 0;
									list($SqlStokBirTMiktar) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE acik='$SiparisHar[urunadi]'"));
									$YeniAdet = ($SqlStokBirTMiktar + $GercekIkramAdet) - $SiparisHar[ikramadet];
									
									/*
									echo "Stok Adet = ".$SqlStokBirTMiktar."\n";
									echo "ikramadet Adet = ".$SiparisHar[adet]."\n";
									echo "Kalan Adet = ".$YeniAdet."\n";
									*/
									
									$Sonuc4 = mysql_query("UPDATE stokbir SET tmiktar='$YeniAdet' WHERE acik='$SiparisHar[urunadi]'"); 
								}
							}
							
							$SorguSiparisIkramListe = mysql_query("SELECT id, stokid, urunadi, siparisid, masaid, sanaladet, ikramadet, adet FROM siparishareket WHERE masaid='$MasaID' 
								AND siparisid='$SiparisID' AND siparisdurum='2' AND durum='0'");
							$hata = -1;
						} else {
							$hata = -2;
						}
					} else {
						$hata = -3;
					}

				}
			}	
			
		}

	}

?>

	    	<div class="col-md-12 col-sm-12 col-xs-12">
				<? if ($hata == -1) { ?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-success alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > İkram Kayıt İşlemi Başarılı !!!</strong>
			                </div>
			            </div>
				<? } else if($hata == -2) {?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-danger alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > İkram Kayıt İşlemi Başarısız !!!</strong>
			                </div>
			            </div>
			    <?  } else if($hata == -3) {?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-danger alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > Ikram Adet Sayısı <?=$GercekAdet;?> Geçemez !!!</strong>
			                </div>
			            </div>
			    <?  } ?>

	        </div>

			<div class="col-md-12 col-sm-12 col-xs-12">
	            <div class="x_content">
	               	<table class="table table-striped responsive-utilities jambo_table bulk_action">
	                  	<thead>
	                    	<tr class="headings">
	                        	<th class="column-title">Ürün Adı </th>
	                        	<th class="column-title">İkram Adet </th>
	                        	<th class="column-title">İkram Edilebilir Adet </th>
	                        	<th class="column-title">İslemler </th>
	              			</tr>
				      	</thead>
				   		<tbody>
				   		<? 
				   			$i=0;
				   			while ($SiparisIkramListe = mysql_fetch_array($SorguSiparisIkramListe)) {
				   				$i++;
				   				$StokSayac = ($SiparisIkramListe[sanaladet] + $SiparisIkramListe[ikramadet]);
				   				$IkramEdilebilirAdet = $SiparisIkramListe[adet] - $StokSayac;

				   				if ($SiparisIkramListe[adet] > $StokSayac) {
							   		?>
							          	<tr class="even pointer">
							              	<td class="a-center"><?=$SiparisIkramListe[urunadi]; ?></td>
							              	<td class=" ">                                                    
			                                    <div class="col-md-4">
			                                        <input type="text" value="<?=$SiparisIkramListe[ikramadet];?>" class="form-control" id="ikram_adet<?=$i;?>" >
			                                    </div>
			                                </td>
							              	<td class=" "><?=$IkramEdilebilirAdet; ?></td>
							              	<td class=" last">
							              		<a onclick="IkramEdilenAdet('<?=$MasaID; ?>', '<?=$SiparisID; ?>', document.getElementById('ikram_adet<?=$i;?>').value, '<?=$SiparisIkramListe[id];?>')" 
							              			class="btn btn-default btn-xs" >Ikram
							              		</a>
							              	</td>
							          	</tr>
							        <? }

				        	} ?>
		              	</tbody>
	            	</table>

	         	</div>
	         	<!--
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
	            -->

	         	<div class="x_content">
	         		<div class = "col-md-4 col-sm-4 col-xs-12" ></div>
	         		<div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
	         		<!--
		                <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '0')" class="btn btn-primary">Nakit Ödeme</a>
		                <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '1')" class="btn btn-success">Kredi Ödeme</a>
		                <a href="veresiye.php?MasaID=<?=$MasaID;?>&SiparisID=<?=$SiparisID;?>&Durum=4" class="btn btn-warning">Veresiye Ödeme</a>
		            -->
		                <a onclick="UrunOdeHepsi('<?=$MasaID; ?>', '<?=$SiparisID; ?>')" class="btn btn-danger">İkram Bitir</a>
		                <a onclick="UrunIkramGeriAl('<?=$MasaID; ?>', '<?=$SiparisID; ?>')" class="btn btn-success">İkram Geri Al</a>
	                </div>
	                <div class = "col-md-4 col-sm-4 col-xs-12" ></div>
	            </div>
	        </div>