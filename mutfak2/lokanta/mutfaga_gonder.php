<?
	include 'class/baglanti.php';

	if (mysql_escape_string($_GET[SiparisID])) {
		$SiparisID = $_GET[SiparisID];
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SqlSorgu = "";
		try {
			list($siparisid, $siparisaktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND id='$SiparisID' AND aktif='1'"));
			if ($siparisaktifmi == "1") {
				try {
					$Sonuc = mysql_query("UPDATE siparishareket SET siparisdurum='1' WHERE masaid='$MasaID' AND siparisid='$SiparisID' AND siparisdurum='0'");
				} catch (Exception $e) {
					
				}

				$SqlSorgu = mysql_query("SELECT id, urunadi, adet, tutar, siparisdurum FROM siparishareket WHERE siparisid='$siparisid' AND masaid='$MasaID'");
			}
		} catch (Exception $e) {
			
		}
	}
?>

    <div class="x_content">
       <table class="table table-striped projects">
            <thead>
                <tr>
                    <th style="width: 20%">Ürün Adı</th>
                    <th>Miktar</th>
                    <th>Sipariş Durum</th>
                    <th>Fiyat</th>
                    <th style="width: 22%">İslem</th>
                </tr>
            </thead>
            <tbody>
            <? 
            	$ToplamTutar = 0;
            	while ($Siparis = mysql_fetch_array($SqlSorgu)) { 
            		$ToplamTutar += ($Siparis[adet] * $Siparis[tutar]);

            ?>
                <tr>
                    <td><a><?=$Siparis[urunadi]; ?></a></td>
                    <td><?=$Siparis[adet]; ?> Adet</td>
                    <td>
                    <?
                    	if ($Siparis[siparisdurum] == "0") {?>
                    		<a class="btn btn-danger btn-xs">Gönderilmedi</a>
                    <?  } else if ($Siparis[siparisdurum] == "1") { ?>
                    		<a class="btn btn-warning btn-xs"> Bekleniyor</a>
                    <?	} else if ($Siparis[siparisdurum] == "2") { ?>
							<a class="btn btn-success btn-xs">Gönderildi</a>
                    <?	} ?>
                    </td>
                    <td><?=$Siparis[tutar]; ?> <i class="fa fa-try"></i></td>
                    <td> 
                        <a class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Duzenle </a>
                        <a onclick="SiparisUrunSil('<?=$Siparis[id]?>', '<?=$MasaID?>')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Sil </a>
                    </td>
                </tr>
         <? } ?>
        </table>
    </div>
    <div class="siparisurunsil"></div>

    <div class="row">
     <div class="col-xs-6"></div>
        <div class="col-xs-6">
          <div class="table-responsive">
               	<table class="table">
               	    <tbody>
                         <tr>
              	            <th> <h3> Toplam Tutar : </h3></th>
               	            <th><h3><?=number_format($ToplamTutar, 2, '.', ','); ?> <i class="fa fa-try"></i></h3> </th>
                        </tr> 
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="row no-print">
        <div class="col-xs-12 col-md-6">
            <a class="btn btn-danger pull-right"><i class="fa fa-cutlery"></i> Mutfağa Gönder</a>
        </div>
    </div>