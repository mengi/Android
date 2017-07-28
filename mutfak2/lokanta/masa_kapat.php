<?
	include 'class/baglanti.php';

	if (mysql_escape_string($_GET[ID])) {
		$MasaID = $_GET[ID];
		$Sonuc = mysql_query("UPDATE masa SET aktif='off', acankim='' WHERE id='$MasaID'");
        list($siparisid) = mysql_fetch_array(mysql_query("SELECT id FROM siparis WHERE masa='$MasaID' AND aktif='1'"));

        if (isset($siparisid)) {
            $Sonuc1 = mysql_query("DELETE FROM siparishareket WHERE masaid = '$MasaID' AND siparisid = '$siparisid' AND siparisdurum != '2'");
            $Sonuc2 = mysql_query("DELETE FROM siparis WHERE masa='$MasaID' AND aktif='1'");
        }
	}

	$BolumID = mysql_escape_string($_GET[BolumID]);

    list($BolumAd) = mysql_fetch_array(mysql_query("SELECT acik FROM kat WHERE id='$BolumID'"));

    $SorguMasa = "";
    if ($BolumID) {
        $SorguMasa = mysql_query("SELECT id, acik, aktif FROM masa WHERE kat='$BolumID'");
?>

        <div class="container" style="margin-right:0px;">
            <? 
            while ($Masa = mysql_fetch_array($SorguMasa)) { ?>
                <a href="masa_urun_ekle.php?MasaID=<?=$Masa[id];?>">
                    <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <? if ($Masa[aktif] == "on") { ?>
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-check"></i></div>
                            <? } else if ($Masa[aktif] == "off") {?>
                                <div class="icon" style="color:#ff4136;"><i class="fa fa-times"></i></div>
                                        <? }?>
                                            
                            <div class="count"><?=$Masa[acik];?></div>
                            <h3><?=$BolumAd; ?></h3>
                            <p> 
                                <a onclick="masa_urun_ekle.php?MasaID=<?=$Masa[id];?>" class="btn btn-success btn-xs"> <i class="fa fa-circle">
                                </i> Masa Aç </a>
                                <a onclick="MasaKapat('<?=$Masa[id];?>', <?=$BolumID?>)" class="btn btn-danger btn-xs"> <i class="fa fa-circle">
                                </i> Masa Kapat </a>
                            </p>
                                            
                        </div>
                    </div>
                </a>
            <?}?>
        </div>
     <?}?>