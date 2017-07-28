<?	
	
	include 'class/baglanti.php';

	function MasaAdi($MasaID) {
        list($masaadi) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
        return $masaadi;
    }

    function SureHesapla ($EklemeTarihi) {
    	$BaslangicTarihi= strtotime($EklemeTarihi);
		$BitisTarihi = strtotime(date("Y-m-d H:i:s"));
		$Fark = abs($BitisTarihi - $BaslangicTarihi);
		$GecenSure = number_format($Fark / 60, 1, '.', '');
    	
    	return $GecenSure;
    }

	if (mysql_escape_string($_GET[SiparisID])) {
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
        $MasaID = mysql_escape_string($_GET[MasaID]);
        $StokID = mysql_escape_string($_GET[StokID]);
		$SqlSorgu = "";
        
        try {

            list($siparisid, $aktifmi, $sacankim) = mysql_fetch_array(mysql_query("SELECT id, aktif, sacankim FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
            list($acik) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));

            $registatoin_ids = array();

            $sql = mysql_query("SELECT RegistrationID FROM wp_gcm_kullanicilar WHERE GarsonKod='$sacankim'");
            while($row = mysql_fetch_array($sql)){
                array_push($registatoin_ids, $row['RegistrationID']);
            }

            //print_r($registatoin_ids);

            list($MesajAdet, $MesajUrunAdi) = mysql_fetch_array(mysql_query("SELECT adet, urunadi FROM siparishareket WHERE id='$SiparisID'"));
            (string) $GonderilecekMesaj = "Masa ".$acik." ".$MesajAdet." Adet ".$MesajUrunAdi." Hazır !!!";

            $url = 'https://android.googleapis.com/gcm/send';
            $mesaj = array("notification_message" => $GonderilecekMesaj); 
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $mesaj,
            );

            $headers = array(
                'Authorization: key=AIzaSyAqYkKNSVqFSOaaeqv-1yfgg3gXDv2sFN8', 
                'Content-Type: application/json'
            );
                        
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

            $result = curl_exec($ch);

            if ($result === FALSE) {
                die('Curl failed: ' . curl_error($ch));
            }

            curl_close($ch);

            if ($aktifmi == "1") {
                $HazilanmayanSiparisAdet = 0;
                list($HazirlananSiparisAdet) = mysql_fetch_array(mysql_query("SELECT adet FROM siparishareket WHERE masaid='$MasaID' AND stokid='$StokID' AND siparisid='$siparisid' AND siparisdurum='2'"));
                
                if ($HazirlananSiparisAdet > 0) {

                    $SqlSorguSipris = mysql_query("SELECT id, adet FROM siparishareket WHERE masaid='$MasaID' AND stokid='$StokID' AND id='$SiparisID' AND siparisdurum='1'");
                    while ($HazirlanmayanSiparis = mysql_fetch_array($SqlSorguSipris)) {
                       $HazilanmayanSiparisAdet += $HazirlanmayanSiparis[adet];
                    }

                    $YeniHazirlananSiparisAdet = $HazirlananSiparisAdet + $HazilanmayanSiparisAdet;

                    $Sonuc1 = mysql_query("UPDATE siparishareket SET adet='$YeniHazirlananSiparisAdet', kesinadet='$YeniHazirlananSiparisAdet' WHERE masaid='$MasaID' AND stokid='$StokID' AND siparisid='$siparisid' AND siparisdurum='2'");

                    $Sonuc3 = mysql_query("DELETE FROM siparishareket WHERE id='$SiparisID'");
                    $SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet, masaid, eklemetrh, siparisdurum, ses FROM siparishareket WHERE siparisdurum='1' ORDER BY id ASC");
                } else {
                    $Sonuc4 = mysql_query("UPDATE siparishareket SET siparisdurum='2' WHERE id='$SiparisID'");
                }

                $SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet, masaid, eklemetrh, siparisdurum, ses FROM siparishareket WHERE siparisdurum='1' ORDER BY id ASC");
            }
                
        } catch (Exception $e) {
            
        }

	}
?>

    <table class="table table-striped responsive-utilities jambo_table bulk_action">
        <thead>
            <tr class="headings">
                <th class="column-title"><h2> Masa Adı </h2></th>
                <th class="column-title"><h2> Ürün Adı </h2></th>
                <th class="column-title"><h2> Adet </h2></th>
                <th class="column-title"><h2> Süre </h2></th>
                <th style="width: 1%" class="column-title"><h2> İşlem </h2></th>
            </tr>
        </thead>

        <tbody>
        <? 
            while ($SiparisUrun = mysql_fetch_array($SqlSorgu)) { ?>
                <tr class="even pointer">
                    <td ><?=MasaAdi($SiparisUrun[masaid])?></td>
                    <td ><?=$SiparisUrun[urunadi]; ?> </td>
                    <td ><?=$SiparisUrun[adet]; ?> </td>
                    <td ><?=SureHesapla($SiparisUrun[eklemetrh]); ?> Sn. <i class="success fa fa-clock-o"></i></td>
                    <td >
                        <a onclick="SiparisListeGoster('<?=$SiparisUrun[id]?>', '<?=$SiparisUrun[masaid]?>', '<?=$SiparisUrun[stokid]?>')" class="btn btn-danger pull-right"><i class="fa fa-bell-o"></i> Bekliyor</a>
                    </td>
                </tr>
        <?      
                if($SiparisUrun[ses]=='OKUNMADI')
                {?>
                    <object height="0px" width="0px" data="images/sgeldi.mp3">
                        <param name="autoplay" value="true">
                        <param name="loop" value="0">
                    </object>
                <?} 

            mysql_query("UPDATE siparishareket SET ses='OKUNDU' WHERE id='$SiparisUrun[id]'");
         } ?>
        </tbody>
    </table>