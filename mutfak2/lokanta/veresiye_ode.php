<?
	include 'class/baglanti.php';
	include 'class/mysqlclass.php';
	$hata = 0;

	if (mysql_escape_string($_GET[Durum])) {
		$Durum = mysql_escape_string($_GET[Durum]);
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
		$AdSoyad = mysql_escape_string($_GET[AdSoyad]);
		$Indirim = mysql_escape_string($_GET[Indirim]);
		$Ikram = mysql_escape_string($_GET[Ikram]);
		$Veresiye = mysql_escape_string($_GET[Veresiye]);
		$GercekTutar = mysql_escape_string($_GET[GercekTutar]);

		$bttrh = date('Y-m-d H:i:s');


		list($aktifmi) = mysql_fetch_array(mysql_query("SELECT aktif FROM siparis WHERE masa='$MasaID' AND id='$SiparisID' AND aktif='1'"));
		if ($aktifmi == "1") {
			if ($Durum == "4") {
				$Sonuc = mysql_query("UPDATE siparishareket SET durum='1', odemetur='VERESIYE', veresiyeid='$Veresiye' WHERE masaid='$MasaID' 
					AND siparisid='$SiparisID'");

				$Sonuc1 = mysql_query("UPDATE siparis SET aktif='0', bittrh='$bttrh', odemetur='VERESIYE', toplam='$GercekTutar', 
					indirim='$Indirim', ikramtutar='$Ikram' WHERE id='$SiparisID' AND masa='$MasaID' AND aktif='1'");

				$Sonuc2 = mysql_query("UPDATE masa SET aktif='off', acankim='' WHERE id='$MasaID'");

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


				if ($Sonuc1) {
					if ($Sonuc) {
						$_POST['veresiyeid'] = $Veresiye;
				        $_POST['siparisid'] = $SiparisID;
				        $_POST['adsoyad'] = $AdSoyad;
				        $_POST['telefon'] = "1";
				        
				        $Sonuc3 = $db->sql_insert("veresiyesiparis", $_POST) or die($hata=-2);

						$hata = -1;
					} else {
						$hata = -2;
					}
				}
			}
		}
	}

?>

	<? if ($hata == -1) { ?>
			<div class="col-md-12 col-sm-12 col-xs-1">
				<div class="alert alert-success alert-dismissible fade in" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
					<strong > Cari Ödeme İşlemi Başarılı !!!</strong>
				</div>
			</div>
	<? } else if($hata == -2) {?>
			<div class="col-md-12 col-sm-12 col-xs-1">
				<div class="alert alert-danger alert-dismissible fade in" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
					<strong > Cari Ödeme İşlemi Başarısız !!!</strong>
				</div>
			</div>
	<? } ?>