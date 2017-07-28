<?
	include 'class/baglanti.php';

	$SonTarih = mysql_escape_string($_GET[SonTarih]);
	$IlkTarih = mysql_escape_string($_GET[IlkTarih]);
    $OdemeTip = mysql_escape_string($_GET[OdemeTip]);

	$SqlSorgu = "";
	$hata = 0;

	function MasaAdi($MasaID) {
		$MasaAd = "";
		try {
			list($MasaAds) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
			$MasaAd = $MasaAds;
		} catch (Exception $e) {
			
		}

		return $MasaAd;
	}

    if ($OdemeTip == "-1") {

    	if (($SonTarih == -2) && ($IlkTarih != -1)) {
    		$IlkTime = strtotime($IlkTarih);
    		(string) $IlkTR= date('Y-m-d', $IlkTime);

            $CevirTarih = new DateTime($IlkTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

    		$SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam FROM siparis WHERE 
    			aktif='0' AND bittrh BETWEEN '$IlkTR' AND '$STarih'");

    	} else if (($SonTarih != "-2") && ($IlkTarih == "-1")) {
    		$SonTime = strtotime($SonTarih);
    		(string) $SonTR= date('Y-m-d', $SonTime);

    		$CevirTarih = new DateTime($SonTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

    		$SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam  FROM siparis WHERE 
    			aktif='0' AND bittrh BETWEEN '$SonTR' AND '$STarih'");

    	} else if (($SonTarih != -2) && ($IlkTarih != -1)) {

    		$SonTime = strtotime($SonTarih);
    		$IlkTime = strtotime($IlkTarih);

    		(string) $SonTR= date('Y-m-d', $SonTime);
    		(string) $IlkTR= date('Y-m-d', $IlkTime);

    		$SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam FROM siparis WHERE 
    			aktif='0' AND bittrh BETWEEN '$IlkTR' AND '$SonTR'");
    	} else {
    		$hata = -1;
    	}
    } else {

        if (($SonTarih == -2) && ($IlkTarih != -1)) {
            $IlkTime = strtotime($IlkTarih);
            (string) $IlkTR= date('Y-m-d', $IlkTime);

            $CevirTarih = new DateTime($IlkTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

            $SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam FROM siparis WHERE odemetur='$OdemeTip' AND 
                aktif='0' AND bittrh BETWEEN '$IlkTR' AND '$STarih'");

        } else if (($SonTarih != "-2") && ($IlkTarih == "-1")) {
            $SonTime = strtotime($SonTarih);
            (string) $SonTR= date('Y-m-d', $SonTime);

            $CevirTarih = new DateTime($SonTR);
            $CevirTarih->modify('+1 day');
            (string) $STarih = $CevirTarih->format('Y-m-d');

            $SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam  FROM siparis WHERE odemetur='$OdemeTip' AND 
                aktif='0' AND bittrh BETWEEN '$SonTR' AND '$STarih' ");

        } else if (($SonTarih != -2) && ($IlkTarih != -1)) {

            $SonTime = strtotime($SonTarih);
            $IlkTime = strtotime($IlkTarih);

            (string) $SonTR= date('Y-m-d', $SonTime);
            (string) $IlkTR= date('Y-m-d', $IlkTime);

            $SqlSorgu = mysql_query("SELECT masa, odemetur, indirim, ikramtutar, toplam FROM siparis WHERE odemetur='$OdemeTip' AND 
                aktif='0' AND bittrh BETWEEN '$IlkTR' AND '$SonTR'");
        } else {
           $hata = -1; 
        }
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
                                                            <th>Masa Adı</th>
                                                            <th>Ödeme Tipi</th>
                                                            <th style="width: 18%">indirim Tutar</th>
                                                            <th style="width: 19%">ikram Tutar</th>
                                                            <th>Toplam Tutar</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?
                                                    	$indirimTutar = 0;
                                                    	$ikramTutar = 0;
                                                    	$ToplamTutar = 0;
                                                    	while($Sql = mysql_fetch_array($SqlSorgu)) {

                                                    		$indirimTutar += $Sql[indirim];
                                                    		$ikramTutar += $Sql[ikramtutar];
                                                    		$ToplamTutar += $Sql[toplam];
                                                    ?>
                                                        <tr>
                                                            <td><?=MasaAdi($Sql[masa]); ?></td>
                                                            <td><?=$Sql[odemetur]; ?></td>
                                                            <td><?=number_format($Sql[indirim], 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                            <td><?=number_format($Sql[ikramtutar], 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                            <td><?=number_format($Sql[toplam], 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                        </tr>
                                                    <?  } ?>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <!-- /.col -->
                                        </div>
                                        <!-- /.row -->

                                        <div class="row">
                                            <!-- accepted payments column -->
                                            <div class="col-xs-6">
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-xs-6">
                                                <div class="table-responsive">
                                                    <table class="table">
                                                        <tbody>
                                                            <tr>
                                                                <th>İndirim T. : </th>
                                                                <td><?=number_format($indirimTutar, 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                            </tr>
                                                            <tr>
                                                                <th>İkram T. : </th>
                                                                <td><?=number_format($ikramTutar, 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                            </tr>
                                                            <tr>
                                                                <th>Toplam T. : </th>
                                                                <td><?=number_format($ToplamTutar, 2, '.', ','); ?> <i class="fa fa-try"></i></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <!-- /.col -->
                                        </div>
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

