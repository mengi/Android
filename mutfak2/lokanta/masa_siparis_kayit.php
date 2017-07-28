<?	
	include 'class/baglanti.php';
	include 'class/mysqlclass.php';

	if (mysql_escape_string($_GET[UrunID])) {
		$UrunID = mysql_escape_string($_GET[UrunID]);
		$Miktar = mysql_escape_string($_GET[Miktar]);
		$MasaID = mysql_escape_string($_GET[MasaID]);

		list($siparisid, $aktif) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
		if ($aktif == "1") {
			list($stokid, $stokadi, $fiyat, $urungrupid) = mysql_fetch_array(mysql_query("SELECT stokID, acik, fiyat, urungrupid FROM stok WHERE stokID='$UrunID'"));
			$SqlSorgu = mysql_query("SELECT stokID, acik, fiyat, aciklama, urungrupid FROM stok WHERE urungrupid='$urungrupid'");

			$_POST['stokid'] = $stokid;
            $_POST['urunadi'] = $stokadi;
            $_POST['adet'] = $Miktar;
            $_POST['siparisid'] = $siparisid;
            $_POST['masaid'] = $MasaID;
            $_POST['eklemetrh'] = date('Y-m-d H:i:s');
            $_POST['tutar']= $fiyat;
            $_POST['durum']= "0";
            $_POST['siparisdurum']= "0";
            $_POST['siparissekli']= "NORMAL";
            $_POST['sanaladet']= "0";
            $_POST['kesinadet']= $Miktar;
            $_POST['ses']= "OKUNMADI";

            $Sonuc = $db->sql_insert("siparishareket", $_POST) or die(); 
		}
	}
?>


        <div class="row top_tiles" >
            <? 
            $i = 0;
            while ($Urun = mysql_fetch_array($SqlSorgu)) { 

            	$i++;
           	?>
                <a  data-toggle="modal" data-target=".bs-example-modal-sm<?=$Urun[stokID]; ?>">
                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tile-stats">
                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cube"></i></div>
                        <div class="count"></div>
                        <h3></h3>
                        <p id="na"><?=$Urun[acik]; ?></p>
                        <p> Fiyat : <?=$Urun[fiyat]; ?> <i class="fa fa-try"></i></p>
                    </div>

                </div>
                </a>	
                <div id="myModal" class="modal fade bs-example-modal-sm<?=$Urun[stokID]; ?>" tabindex="-1" role="dialog" aria-hidden="true">
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
                                            <input type="text" id="miktar<?=$i?>" required="required" class="form-control col-md-7 col-xs-12" name="miktar<?=$i?>">
                                        </div>
                                    </div>
                                     <div class="divider-dashed"></div>
                                 </form>
        	             	</div>
        	             	<div class="modal-footer">
        		                <button onclick="KaydetUrun('<?=$Urun[stokID]; ?>', document.getElementById('miktar<?=$i?>').value, '<?=$MasaID?>')" class="btn btn-primary"> Kaydet</button>
        		            </div>
                    	</div>
                    </div>
                </div>
          <? } ?>
           </div>