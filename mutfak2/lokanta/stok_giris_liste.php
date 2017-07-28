<?
	include 'class/baglanti.php';

	$SonTarih = mysql_escape_string($_GET[SonTarih]);
	$IlkTarih = mysql_escape_string($_GET[IlkTarih]);

	$SqlSorgu = "";
	$hata = 0;

	function StokAdi($StokID) {
		$MasaAd = "";
		try {
			list($MasaAds) = mysql_fetch_array(mysql_query("SELECT acik FROM stokbir WHERE id='$StokID'"));
			$MasaAd = $MasaAds;
		} catch (Exception $e) {
			
		}

		return $MasaAd;
	}


	function GirilenStok($StokID) {
		$MasaAd = "";
		try {
			list($MasaAds) = mysql_fetch_array(mysql_query("SELECT SUM(miktar) AS miktars FROM stokbir WHERE id='$StokID' AND durum='0'"));
			$MasaAd = $MasaAds;
		} catch (Exception $e) {
			
		}

		return $MasaAd;
	}

	function CikisStok($StokID) {
		$MasaAd = "";
		try {
			list($MasaAds) = mysql_fetch_array(mysql_query("SELECT SUM(miktar) AS miktars FROM stokbir WHERE id='$StokID'"));
			$MasaAd = $MasaAds;
		} catch (Exception $e) {
			
		}

		return $MasaAd;
	}



    	if (($SonTarih == -2) && ($IlkTarih != -1)) {
    		$IlkTime = strtotime($IlkTarih);
    		(string) $IlkTR= date('Y-m-d', $IlkTime);

            $CevirTarih = new DateTime($IlkTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

    		$SqlSorgu = mysql_query("SELECT stokid, tarih, miktar, durum, stoktip FROM stokbirhar WHERE tarih BETWEEN '$IlkTR' AND '$STarih'");

    	} else if (($SonTarih != "-2") && ($IlkTarih == "-1")) {
    		$SonTime = strtotime($SonTarih);
    		(string) $SonTR= date('Y-m-d', $SonTime);

    		$CevirTarih = new DateTime($SonTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

    		$SqlSorgu = mysql_query("SELECT stokid, tarih, miktar, durum, stoktip FROM stokbirhar WHERE tarih BETWEEN '$SonTR' AND '$STarih'");

    	} else if (($SonTarih != -2) && ($IlkTarih != -1)) {

    		$SonTime = strtotime($SonTarih);
    		$IlkTime = strtotime($IlkTarih);

    		(string) $SonTR= date('Y-m-d', $SonTime);
    		(string) $IlkTR= date('Y-m-d', $IlkTime);

    		$SqlSorgu = mysql_query("SELECT stokid, tarih, miktar, durum, stoktip FROM stokbirhar WHERE tarih BETWEEN '$IlkTR' AND '$SonTR'");
    	} else {
    		$hata = -1;
    	}
?>


		<? if ($hata == 0) { ?>
		
				
                                <div class="x_content">

                                    <section class="content invoice">
                                        <div class="row">
                                            <div class="col-xs-12 table">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Stok Adı</th>
                                                            <th>Tarih</th>
                                                            <th>Stok Durum</th>
                                                            <th style="width: 19%">Miktar</th>
                                                            <th style="width: 18%"> Tip</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?
                                                    	while($Sql = mysql_fetch_array($SqlSorgu)) {
                                                    ?>
                                                        <tr>
                                                            <td><?=StokAdi($Sql[stokid]); ?></td>
                                                            <td><?=$Sql[tarih]; ?></td>
                                                            <td>

	                                                            <?  if ($Sql[durum] == 0) {?>
	                                                            	Giriş
	                                                            <? } else if($Sql[durum] == 1){ ?>
	                                                            	Çıkış
	                                                            <?}?>

                                                            </td>
                                                            <td><?=$Sql[miktar]; ?></td>
                                                            <td><?=$Sql[stoktip]; ?></td>
                                                        </tr>
                                                    <?  } ?>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- /.col -->
                                        </div>
                                        <!-- /.row -->
                                    </section>
                                </div>
                                <?} else if ($hata == -1) {?>
                                     	<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                         	<div class="alert alert-danger alert-dismissible fade in" role="alert">
                                             	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                             	<strong > Lütfen Tarih Seçiniz !!!</strong>
                                         	</div>
                                     	</div>
                                <?  } ?>

