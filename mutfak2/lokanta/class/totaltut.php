<?
	
	function BolumSayisi () {
		$Sayac = 0;
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT COUNT(*) AS Sayac FROM kat"));
			$Sayac = $SqlSorgu;
		} catch (Exception $e) {
			$Sayac = -1;
		}

		return $Sayac;
	}

	function MasaSayisi () {
		$Sayac = 0;
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT COUNT(*) AS Sayac FROM masa"));
			$Sayac = $SqlSorgu;
		} catch (Exception $e) {
			$Sayac = -1;
		}

		return $Sayac;
	}

	function GarsonSayisi() {
				$Sayac = 0;
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT COUNT(*) AS Sayac FROM garson"));
			$Sayac = $SqlSorgu;
		} catch (Exception $e) {
			$Sayac = -1;
		}

		return $Sayac;
	}

	function VMuseteriSayisi () {
		$Sayac = 0;
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT COUNT(*) AS Sayac FROM veresiye"));
			$Sayac = $SqlSorgu;
		} catch (Exception $e) {
			$Sayac = -1;
		}

		return $Sayac;
	}

	function GunclukKazanc () {
		$GunlukToplam = 0;
		(string) $BTarih = date ('Y-m-d');
		$CevirTarih = new DateTime($BTarih);
		$CevirTarih->modify('+1 day');
		(string) $STarih = $CevirTarih->format('Y-m-d');
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT SUM(toplam) AS GunlukToplam FROM siparis WHERE bittrh BETWEEN '$BTarih' AND '$STarih'"));
			$GunlukToplam = $SqlSorgu;
		} catch (Exception $e) {
			$GunlukToplam = 0;
		}

		return $GunlukToplam;
	}

	function ToplamKazanc () {
		$ToplamTutar = 0;
		try {
			list($SqlSorgu) = mysql_fetch_array(mysql_query("SELECT SUM(tutar) AS Toplam FROM siparishareket_yedek"));
			$ToplamTutar = $SqlSorgu;
		} catch (Exception $e) {
			$ToplamTutar = 0;
		}

		return $ToplamTutar;
	}
?>