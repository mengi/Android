<?
	include 'class/baglanti.php';

	$SonTarih = mysql_escape_string($_GET[SonTarih]);
	$IlkTarih = mysql_escape_string($_GET[IlkTarih]);
    $StokID = mysql_escape_string($_GET[Stok]);

	$SqlSorgu = "";
	$hata = 0;
    $MiktarDurum = 0;

    if (($SonTarih == -2) && ($IlkTarih != -1)) {
    	$IlkTime = strtotime($IlkTarih);
    	(string) $IlkTR= date('Y-m-d', $IlkTime);

        (string) $STarih = date('Y-m-d');

    	$SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet, eklemetrh FROM siparishareket WHERE 
    		stokid='$Stok' AND eklemetrh BETWEEN '$IlkTR' AND '$STarih'");

    } else if (($SonTarih != "-2") && ($IlkTarih == "-1")) {
    	$SonTime = strtotime($SonTarih);
    	(string) $SonTR= date('Y-m-d', $SonTime);

        (string) $STarih = date('Y-m-d');

    	$SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet, eklemetrh FROM siparishareket WHERE 
    			stokid='$Stok' AND eklemetrh BETWEEN '$SonTR' AND '$STarih'");

    } else if (($SonTarih != -2) && ($IlkTarih != -1)) {

    	$SonTime = strtotime($SonTarih);
    	$IlkTime = strtotime($IlkTarih);

    	(string) $SonTR= date('Y-m-d', $SonTime);
    	(string) $IlkTR= date('Y-m-d', $IlkTime);

    	$SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet,eklemetrh FROM siparishareket WHERE 
    			stokid='$Stok' AND eklemetrh BETWEEN '$IlkTR' AND '$SonTR'");
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
                                                            <th>#</th>
                                                            <th>ürün Adı</th>
                                                            <th style="width: 18%">Adet</th>
                                                            <th style="width: 19%">Tarih</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?
                                                       $Toplam = 0;
                                                    	while($Sql = mysql_fetch_array($SqlSorgu)) {
                                                            $Toplam += $Sql[adet]; 
                                                    ?>  
                                                        <tr>
                                                            <td><?=$Sql[id]; ?></td>
                                                            <td><?=$Sql[urunadi]; ?></td>
                                                            <td><?=number_format($Sql[adet], 1, '.', ','); ?></td>
                                                            <td><?=$Sql[eklemetrh]; ?></td>
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
                                                                <th>Toplam M. : </th>
                                                                <td><?=number_format($Toplam, 2, '.', ','); ?></td>
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

