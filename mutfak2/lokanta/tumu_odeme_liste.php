<?	
	session_start();
	include 'class/baglanti.php';

	$ToplamTutar = 0;
	$Indirim = 0;
	$IkramTutar = 0;
	$hata = 0;

	$Durum = mysql_escape_string($_GET[Durum]);
	if (isset($Durum)) {
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
		$GercekTutar = mysql_escape_string($_GET[GercekTutar]);
		$Indirim = mysql_escape_string($_GET[Indirim]);
		$IkramTutar = mysql_escape_string($_GET[IkramTutar]);

		$bttrh = date('Y-m-d H:i:s');


		list($aktifmi) = mysql_fetch_array(mysql_query("SELECT aktif FROM siparis WHERE masa='$MasaID' AND id='$SiparisID' AND aktif='1'"));

		if ($aktifmi == "1") {
			list($AcanKim) = mysql_fetch_array(mysql_query("SELECT acankim FROM masa WHERE id='$MasaID'"));

			if ($Durum == "0") {
				
				$Sonuc = mysql_query("UPDATE siparishareket SET durum='1', odemetur='NAKIT' WHERE masaid='$MasaID' 
					AND siparisid='$SiparisID'");

				$Sonuc1 = mysql_query("UPDATE siparis SET aktif='0', bittrh='$bttrh', odemetur='NAKIT', toplam='$GercekTutar', indirim='$Indirim', 
					sacankim='$AcanKim', oacankim='$_SESSION[kullanici]', ikramtutar='$IkramTutar' WHERE id='$SiparisID' AND masa='$MasaID' AND aktif='1'");
				

				$SqlSiparisHar = mysql_query("SELECT stokid, urunadi, adet, sanaladet, ikramadet FROM siparishareket WHERE siparisid='$SiparisID'");
				while ($SiparisHar = mysql_fetch_array($SqlSiparisHar)) {
					list($SDurumY) = mysql_fetch_array(mysql_query("SELECT durumd FROM stok WHERE stokID='$SiparisHar[stokid]'"));

					if ($SDurumY == 1) {

						$YeniAdet = 0;
						list($SqlStokBirTMiktar) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE acik='$SiparisHar[urunadi]'"));
						$YeniAdet = $SqlStokBirTMiktar - ($SiparisHar[adet] - ($SiparisHar[sanaladet] + $SiparisHar[ikramadet]));
						
						/*
						echo "Stok Adet = ".$SqlStokBirTMiktar."\n";
						echo "Siparis Adet = ".$SiparisHar[adet]."\n";
						echo "Kalan Adet = ".$YeniAdet."\n";
						*/
						
						$Sonuc4 = mysql_query("UPDATE stokbir SET tmiktar='$YeniAdet' WHERE acik='$SiparisHar[urunadi]'"); 
					}
				}

				
				$Sonuc2 = mysql_query("UPDATE masa SET aktif='off' WHERE id='$MasaID'");

				if ($Sonuc1) {
					if ($Sonuc) {
						$hata = -1;
					} else {
						$hata = -2;
					}
				}
				
				
			} else if ($Durum == "1") {
				
				$Sonuc = mysql_query("UPDATE siparishareket SET durum='1', odemetur='KREDI' WHERE masaid='$MasaID' 
					AND siparisid='$SiparisID'");

				$Sonuc1 = mysql_query("UPDATE siparis SET aktif='0', bittrh='$bttrh', odemetur='KREDI', toplam='$GercekTutar' , indirim='$Indirim', 
					sacankim='$AcanKim', oacankim='$_SESSION[kullanici]', ikramtutar='$IkramTutar' WHERE id='$SiparisID' AND masa='$MasaID' AND aktif='1'");

				$SqlSiparisHar = mysql_query("SELECT stokid, urunadi, adet, sanaladet, ikramadet FROM siparishareket WHERE siparisid='$SiparisID'");
				while ($SiparisHar = mysql_fetch_array($SqlSiparisHar)) {
					list($SDurumY) = mysql_fetch_array(mysql_query("SELECT durumd FROM stok WHERE stokID='$SiparisHar[stokid]'"));

					if ($SDurumY == 1) {

						$YeniAdet = 0;
						list($SqlStokBirTMiktar) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE acik='$SiparisHar[urunadi]'"));
						$YeniAdet = $SqlStokBirTMiktar - ($SiparisHar[adet] - ($SiparisHar[sanaladet] + $SiparisHar[ikramadet]));
						
						/*
						echo "Stok Adet = ".$SqlStokBirTMiktar."\n";
						echo "Siparis Adet = ".$SiparisHar[adet]."\n";
						echo "Kalan Adet = ".$YeniAdet."\n";
						*/
						
						$Sonuc4 = mysql_query("UPDATE stokbir SET tmiktar='$YeniAdet' WHERE acik='$SiparisHar[urunadi]'"); 
					}
				}

				$Sonuc2 = mysql_query("UPDATE masa SET aktif='off' WHERE id='$MasaID'");

				if ($Sonuc1) {
					if ($Sonuc) {
						$hata = -3;
					} else {
						$hata = -4;
					}
				}
			}
		}
	}

	if (mysql_escape_string($_GET[MasaID])) {
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);

		list($siparisid, $aktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));

		if ($aktifmi == "1") {
			$SqlSiparisListeSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='$MasaID' 
                AND siparisid='$siparisid' AND siparisdurum='2' AND durum='0'");

			$SSqlSiparisListe = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='$MasaID' 
                AND siparisid='$siparisid' AND siparisdurum='2' AND durum='0'");

			while ($SiparisTutar = mysql_fetch_array($SSqlSiparisListe)) {
				$ToplamTutar += (($SiparisTutar[adet] - $SiparisTutar[ikramadet] - $SiparisTutar[sanaladet]) * $SiparisTutar[tutar]);
				$IkramTutar += ($SiparisTutar[ikramadet] * $SiparisTutar[tutar]); 
			}
?>

			<div class="col-md-12 col-sm-12 col-xs-12">
	            <div class="x_content">
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
				   			$KalanAdet = 0;
				   			while ($SiparisListe = mysql_fetch_array($SqlSiparisListeSorgu)) {
				   				$KalanAdet = $SiparisListe[adet] - ($SiparisListe[sanaladet] + $SiparisListe[ikramadet]);

				   				if ($KalanAdet > 0) { ?>
				   					<tr class="even pointer">
						              	<td class="a-center"><?=$SiparisListe[urunadi]; ?></td>
						              	<td class=" "><?=$KalanAdet; ?></td>
						              	<td class=" "><?=$SiparisListe[tutar]; ?> <i class="fa fa-try"></i></td>
						              	<td class=" last"><a onclick="" class="btn btn-default btn-xs" >Ikram</a></td>
						          	</tr>

				   			<?	}
				   			} ?>
		              	</tbody>
	            	</table>

	         	</div>

	           	<div class="x_content">
	         		<div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
	                    <input type="text" class="form-control has-feedback-right" id="ikramtutar" placeholder="Tutar Giriniz" value="<?=number_format($IkramTutar, 2, '.', ','); ?>">
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
	         		<div class = "col-md-2 col-sm-2 col-xs-12" ></div>
	         		<div class="col-md-8 col-sm-8 col-xs-12 form-group has-feedback">
		                <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '0')" class="btn btn-primary">Nakit Ödeme</a>
		                <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '1')" class="btn btn-success">Kredi Ödeme</a>
		                <a href="veresiye.php?MasaID=<?=$MasaID;?>&SiparisID=<?=$SiparisID;?>&Durum=4" class="btn btn-warning">Cari Ödeme</a>
		                <a onclick="UrunIkramEt('<?=$SiparisID; ?>', '<?=$MasaID; ?>')" class="btn btn-danger">İkram Oluştur</a>
	                </div>
	                <div class = "col-md-2 col-sm-2 col-xs-12" ></div>
	            </div>
	        </div>
    <? } else { ?>
	    	<div class="col-md-12 col-sm-12 col-xs-12">
				<? if ($hata == -1) { ?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-success alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > Nakit Ödeme İşlemi Başarılı !!!</strong>
			                </div>
			            </div>
				<? } else if($hata == -2) {?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-danger alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > Nakit Ödeme İşlemi Başarısız !!!</strong>
			                </div>
			            </div>
				<? } else if($hata == -3) {?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-success alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > Kredi Ödeme İşlemi Başarılı  !!!</strong>
			                </div>
			            </div>
				<? } else if($hata == -4) {?>
						<div class="col-md-12 col-sm-12 col-xs-1">
			                <div class="alert alert-danger alert-dismissible fade in" role="alert">
			                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
			                    <strong > Nakit Ödeme İşlemi Başarısız !!!</strong>
			                </div>
			            </div>
				<? } ?>

	        </div>

    <? } ?>
<? } ?>