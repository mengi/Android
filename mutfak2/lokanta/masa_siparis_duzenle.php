<?	
	include 'class/baglanti.php';

	if (mysql_escape_string($_GET[SiparisID])) {
		$SiparisID = mysql_escape_string($_GET[SiparisID]);
		$MasaID = mysql_escape_string($_GET[MasaID]);
		$Miktar = mysql_escape_string($_GET[Miktar]);
		$SqlSorgu = "";
		try {
			if ($Miktar > 0) {
				try {
					try {
						$Sonuc = mysql_query("UPDATE siparishareket SET adet='$Miktar' WHERE masaid='$MasaID' AND id='$SiparisID'");
					} catch (Exception $e) {
						
					}
					
					list($siparisid, $siparisaktifmi) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
					if ($siparisaktifmi == "1") {
						$SqlSorgu = mysql_query("SELECT id, urunadi, adet, tutar, siparisdurum FROM siparishareket WHERE siparisid='$siparisid' AND masaid='$MasaID'");
					}
				} catch (Exception $e) {
					
				}
			}
		} catch (Exception $e) {
					
		}
	}
?>

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
                    $i = 0;
                    while ($Siparis = mysql_fetch_array($SqlSorgu)) {
                        $i++;
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
                        <?  } else if ($Siparis[siparisdurum] == "2") { ?>
                                <a class="btn btn-success btn-xs">Gönderildi</a>
                        <?  } ?>
                        </td>
                        <td><?=$Siparis[tutar]; ?> <i class="fa fa-try"></i></td>
                        <td>
                            <a data-toggle="modal" data-target=".bs-example-modal-sm<?=$Siparis[id]; ?>" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Duzenle </a>
                            <a onclick="SiparisUrunSil('<?=$Siparis[id]?>', '<?=$MasaID?>')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Sil </a>
                        </td>
                    </tr>

                    <div class="modal fade bs-example-modal-sm<?=$Siparis[id]; ?>" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
                                    </button>
                                    <h4 class="modal-title center" id="myModalLabel2">Ürünler Detay</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal form-label-left" method="POST">
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name"> Miktar <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-8 col-xs-12">
                                                <input type="text" id="miktars<?=$i?>" required="required" class="form-control col-md-7 col-xs-12" name="miktars<?=$i?>">
                                            </div>
                                        </div>
                                         <div class="divider-dashed"></div>
                                     </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" onclick="SiparisDuzenle('<?=$Siparis[id]?>', '<?=$MasaID?>', document.getElementById('miktars<?=$i?>').value)"  class="btn btn-primary"> Kaydet</button>
                                </div>
                            </div>
                        </div>
                    </div>
             <? } ?>
            </table>


    
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
    

    <div class="row no-print">
        <div class="col-xs-12 col-md-6">
            <a onclick="MutfagaGonder('<?=$siparisid?>', '<?=$MasaID?>')" class="btn btn-danger pull-right"><i class="fa fa-cutlery"></i> Mutfağa Gönder</a>
        </div>
    </div>
