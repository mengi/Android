<?	
	session_start();
	include 'class/baglanti.php';
	include 'class/mysqlclass.php';

	$hata = 0;
	$SqlMasaSiparisSorgu = "";

	function GenelToplamTutar ($MasaID, $SiparisID) {
		$ToplamTutar = 0;
		$SiparisToplamTutar = mysql_query("SELECT kesinadet, tutar FROM siparishareket WHERE masaid='$MasaID' AND siparisid='$SiparisID' AND durum='0' AND siparisdurum='2'");
		while ($SiparisTutar = mysql_fetch_array($SiparisToplamTutar)) {
			$ToplamTutar += ($SiparisTutar[kesinadet] * $SiparisTutar[tutar]);
		}

		return $ToplamTutar;
	}

	function ParcaliToplam ($MasaID, $SiparisID) {
		$ParcaliToplamTutar = 0;
		$ParcaliSToplamTutar = mysql_query("SELECT tutar, pindirim, pikramtutar FROM parcaliode WHERE masaid='$MasaID' AND siparisid='$SiparisID'");
		while ($PSiparisTutar = mysql_fetch_array($ParcaliSToplamTutar)) {
			$ParcaliToplamTutar += ($PSiparisTutar[tutar] + $PSiparisTutar[pindirim] + $PSiparisTutar[pikramtutar]);
		}

		return $ParcaliToplamTutar;
	}

	function ParcaliIndirim ($MasaID, $SiparisID) {
		$ParcaliToplamTutar = 0;
		$ParcaliSToplamTutar = mysql_query("SELECT pindirim FROM parcaliode WHERE masaid='$MasaID' AND siparisid='$SiparisID'");
		while ($PSiparisTutar = mysql_fetch_array($ParcaliSToplamTutar)) {
			$ParcaliToplamTutar += $PSiparisTutar[pindirim];
		}

		return $ParcaliToplamTutar;
	}

	function ParcaliIkram ($MasaID, $SiparisID) {
		$ParcaliToplamTutar = 0;
		$ParcaliSToplamTutar = mysql_query("SELECT pikramtutar FROM parcaliode WHERE masaid='$MasaID' AND siparisid='$SiparisID'");
		while ($PSiparisTutar = mysql_fetch_array($ParcaliSToplamTutar)) {
			$ParcaliToplamTutar += $PSiparisTutar[pikramtutar];
		}

		return $ParcaliToplamTutar;
	}

	$bttrh = date('Y-m-d H:i:s');

	$MasaID = mysql_escape_string($_GET[MasaID]);
	$SiparisID = mysql_escape_string($_GET[SiparisID]);

	if (mysql_escape_string($_GET[MasaID])) {
		$Durum = mysql_escape_string($_GET[Durum]);
		$ToplamTutar = mysql_escape_string($_GET[ToplamTutar]);

		$IkramTutar = mysql_escape_string($_GET[IkramTutar]);
		$Indirim = mysql_escape_string($_GET[Indirim]);

		$GenelToplam = GenelToplamTutar($MasaID, $SiparisID);
		$ParcaliToplam = ParcaliToplam($MasaID, $SiparisID);

		if ($GenelToplam > $ParcaliToplam) {
			$OdemeTur = "";
			if ($Durum == "0") {
				$OdemeTur = "NAKIT";
			} else if ($Durum == "1") {
				$OdemeTur = "KREDI";
			} else if ($Durum == "4") {
                $OdemeTur = "VERESIYE";
                list($VersiyeSID) = mysql_fetch_array(mysql_query("SELECT siparisid FROM veresiyesiparis WHERE siparisid='$SiparisID'"));

                if (!isset($VersiyeSID)) {
                    $_POST['siparisid'] = $SiparisID;
                    $_POST['veresiyeid'] = mysql_escape_string($_GET[VeresiyeID]);
                    $_POST['adsoyad'] = mysql_escape_string($_GET[AdSoyad]);

                    $Sonuc = $db->sql_insert("veresiyesiparis", $_POST) or die($hata=0);
                }

            }

			$ToplamTutar -= $Indirim;

			$_POST['siparisid'] = $SiparisID;
	        $_POST['masaid'] = $MasaID;
	        $_POST['odemetur'] = $OdemeTur;
	        $_POST['tutar'] = $ToplamTutar;
	        $_POST['pikramtutar'] = $IkramTutar;
	        $_POST['pindirim'] = $Indirim;
	        
	        $Sonuc = $db->sql_insert("parcaliode", $_POST) or die($hata=0);

	        if ($Sonuc) {
	        	$SiparisIkramListe = mysql_query("SELECT id, stokid, adet, ikramadet, sanaladet FROM siparishareket WHERE masaid='$MasaID' AND 
                    siparisid='$SiparisID' AND durum='0' AND siparisdurum='2'");

	        	while ($IkramListe = mysql_fetch_array($SiparisIkramListe)) {
	        		$ToplamIS = $IkramListe[sanaladet] + $IkramListe[ikramadet];
	        		$KalanISAdet = $IkramListe[adet] - $ToplamIS;
	        		$id = $IkramListe[id];

                    $SqlSiparisHar = mysql_query("SELECT stokid, urunadi, adet, sanaladet, ikramadet FROM siparishareket WHERE siparisid='$SiparisID'");
                    while ($SiparisHar = mysql_fetch_array($SqlSiparisHar)) {
                        list($SDurumY) = mysql_fetch_array(mysql_query("SELECT durumd FROM stok WHERE stokID='$SiparisHar[stokid]'"));

                        if ($SDurumY == 1) {

                            $YeniAdet = 0;
                            list($SqlStokBirTMiktar) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE acik='$SiparisHar[urunadi]'"));
                            $YeniAdet = $SqlStokBirTMiktar - $SiparisHar[sanaladet];
                            
                            /*
                            echo "Stok Adet = ".$SqlStokBirTMiktar."\n";
                            echo "Sanal Adet = ".$SiparisHar[sanaladet]."\n";
                            echo "Kalan Adet = ".$YeniAdet."\n";
                            */
                            
                            
                            $Sonuc4 = mysql_query("UPDATE stokbir SET tmiktar='$YeniAdet' WHERE acik='$SiparisHar[urunadi]'"); 
                        }
                    }


	        		$Sonuc1 = mysql_query("UPDATE siparishareket SET adet='$KalanISAdet', sanaladet='0', ikramadet='0' WHERE masaid='$MasaID' AND 
                        siparisid='$SiparisID' AND durum='0' AND siparisdurum='2' AND id='$id'");


	        		$_SESSION[uyeler] = array();

	        	}
	        	
	        	$GToplam = GenelToplamTutar($MasaID, $SiparisID);
				$PToplam = ParcaliToplam($MasaID, $SiparisID);
	        	if ($GToplam == $PToplam) {

	        		$InTutar = ParcaliIndirim($MasaID, $SiparisID);
	        		$IkTutar = ParcaliIkram($MasaID, $SiparisID);

					$GercekTutar = $GenelToplam - ($InTutar + $IkTutar);


	        		list($Kat, $AktifmiMasa) = mysql_fetch_array(mysql_query("SELECT kat, aktif FROM masa WHERE id='$MasaID'"));

	        		list($AcanKim) = mysql_fetch_array(mysql_query("SELECT sacankim FROM siparis WHERE id='$SiparisID'"));

	        		if ($AktifmiMasa == "on") {
	        			$Sonuc = mysql_query("UPDATE siparishareket SET durum='1', odemetur='$PARCALI' WHERE masaid='$MasaID' 
							AND siparisid='$SiparisID'");

						$Sonuc1 = mysql_query("UPDATE siparis SET aktif='0', bittrh='$bttrh', odemetur='PARCALI', toplam='$GercekTutar' , indirim='$InTutar', 
								oacankim='$_SESSION[kullanici]', ikramtutar='$IkTutar' WHERE id='$SiparisID' AND masa='$MasaID' AND aktif='1'");

						$Sonuc2 = mysql_query("UPDATE masa SET aktif='off' WHERE id='$MasaID'");

                        $SqlSiparisHar = mysql_query("SELECT stokid, urunadi, adet, sanaladet, ikramadet FROM siparishareket WHERE siparisid='$SiparisID'");
                        while ($SiparisHar = mysql_fetch_array($SqlSiparisHar)) {
                            list($SDurumY) = mysql_fetch_array(mysql_query("SELECT durumd FROM stok WHERE stokID='$SiparisHar[stokid]'"));

                            if ($SDurumY == 1) {

                                $YeniAdet = 0;
                                list($SqlStokBirTMiktar) = mysql_fetch_array(mysql_query("SELECT tmiktar FROM stokbir WHERE acik='$SiparisHar[urunadi]'"));
                                $YeniAdet = $SqlStokBirTMiktar - $SiparisHar[sanaladet];
                                
                                /*
                                echo "Stok Adet = ".$SqlStokBirTMiktar."\n";
                                echo "Sanal Adet = ".$SiparisHar[sanaladet]."\n";
                                echo "Kalan Adet = ".$YeniAdet."\n";
                                */
                                
                                
                                $Sonuc4 = mysql_query("UPDATE stokbir SET tmiktar='$YeniAdet' WHERE acik='$SiparisHar[urunadi]'"); 
                            }
                        }

						if ($Sonuc1) {
							if ($Sonuc) {

								$ek = 'kasa.php';
							} else {
								$hata = -4;
							}
						}
		        	
	        		}

	        	}

	        }

		}

	}
?>

                        <div class="row">
                            <div class="siparisliste">
                                <div class="col-md-6 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h2> <?=$masad; ?> Ödeme</h2>
                                            <ul class="nav navbar-right panel_toolbox">
                                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                                    <ul class="dropdown-menu" role="menu">
                                                        <li><a href="#">Settings 1</a>
                                                        </li>
                                                        <li><a href="#">Settings 2</a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                                </li>
                                            </ul>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="x_content">
                                            <br />

                                                
                                                <div class="col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
                                                    <div class="input-group">
                                                    	<span class="input-group-btn">
		                                                    <button type="button" onclick="UrunOdess('<?=$MasaID?>', '<?=$siparisid?>', '3')" class="btn btn-primary">Sepet Gör !</button> 
		                                                </span>
                                                        <input id="girilen_miktar" type="text" class="form-control"  name="girilen_miktar" placeholder="Adet Giriniz">
                                                        <span class="input-group-btn">
                                                            <button type="button" onclick="UrunOdeHepsi('<?=$MasaID?>', '<?=$siparisid?>')" class="btn btn-primary">Tümünü Öde !</button> 
                                                        </span>
                                                    </div>
                                                    
                                                </div>

                                                
                                            <div class="row top_tiles">
                                            <?  

									        	$SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket WHERE masaid='$MasaID' AND siparisid='$SiparisID' 
									        		AND siparisdurum='2' AND durum='0'");
                                                $KalanMiktar = 0;
                                                while ($SiparisUrun = mysql_fetch_array($SqlMasaSiparisSorgu)) { 
                                                    $KalanMiktar = ($SiparisUrun[adet] - ($SiparisUrun[sanaladet] + $SiparisUrun[ikramadet]));
                                                    if ($KalanMiktar > 0) { ?>
                                                    
                                                        <a  onclick="UrunOdes('<?=$SiparisUrun[id]?>', '<?=$MasaID?>', document.getElementById('girilen_miktar').value, '<?=$SiparisID?>')">                 
                                                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                                            <div class="tile-stats">
                                                                <div class="icon" style="color:#1ABB9C;">

                                                                </div>
                                                                <div class="count"> </div>
                                                                <br>
                                                                <h3><?=$SiparisUrun[urunadi]; ?></h3>
                                                                <p> Ürün Adeti : <?=$KalanMiktar; ?></p>
                                                            </div>
                                                        </div>
                                                        </a>
                                            <?      }
                                                }
                                            ?>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6 col-xs-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h2><?=$masad; ?> Hesap </h2>
                                            <ul class="nav navbar-right panel_toolbox">
                                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                                    <ul class="dropdown-menu" role="menu">
                                                        <li><a href="#">Settings 1</a>
                                                        </li>
                                                        <li><a href="#">Settings 2</a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                                </li>
                                            </ul>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="x_content">
                                            <br />
                                            <div class="row top_tiles">
                                            <div class="odemeliste">
                                                <div class="col-md-12 col-sm-12 col-xs-12">
                                                    <div class="x_content">
                                                        <table class="table table-striped responsive-utilities jambo_table bulk_action">
                                                            <thead>
                                                                <tr class="headings">
                                                                    <th class="column-title">Ürün Adı </th>
                                                                    <th class="column-title">Adet </th>
                                                                    <th class="column-title">Tutar </th>
                                                                    <th class="column-title">İslemler </th>
                                                                </tr>
                                                             </thead>
                                                        <tbody>

                                                        <?  
                                                            $k = 0;
                                                            for ($i=0; $i < count($ParcaliListe); $i++) {
                                                                $dizi = AyirParcali($ParcaliListe[$i]);
                                                                $DiziSiparisId = $dizi['id'];
                                                                $DiziSiparisAdet = $dizi['adet'];

                                                                list($SanalUrunAdi, $SanalTutar, $SanalIkram) = mysql_fetch_array(mysql_query("SELECT urunadi, tutar, ikramadet FROM siparishareket WHERE id='$DiziSiparisId'"));

                                                                $IkramTutar += ($SanalTutar * $SanalIkram);
                                                                                       
                                                                if (isset($SanalTutar)) {
                                                                    $ParcaliToplamTutar +=($dizi['adet'] * $SanalTutar);

                                                        ?>

                                                                    <tr class="even pointer">
                                                                        <td class="a-center"><?=$SanalUrunAdi; ?></td>
                                                                        <td class=" ">                                                    
                                                                            <div class="col-md-4">
                                                                                <input type="text" value="<?=$DiziSiparisAdet; ?>" class="form-control" id='sanal_miktars<?=$i;?>' readonly="readonly">
                                                                            </div>
                                                                        </td>
                                                                        <td class=" "><?=$SanalTutar; ?> <i class="fa fa-try"></i></td>
                                                                        <td class=" last"><a onclick="ParcaliSanalSil('<?=$i;?>', document.getElementById('sanal_miktars<?=$i;?>').value)" class="btn btn-danger btn-xs" >Sil</a></td>
                                                                    </tr>               
                                                              <? }
                                                                                        
                                                            } ?>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="x_content">
                                                    <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                                        <input type="text" class="form-control has-feedback-right" id="ikramtutar" placeholder="Tutar Giriniz" value="<?=number_format($IkramTutar, 2, '.', ','); ?>">
                                                        <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                                    </div>
                                                </div>

                                                <div class="x_content">
                                                    <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                                        <input type="text" class="form-control has-feedback-right" id="indirim" placeholder="İndirim Tutarı Giriniz">
                                                        <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                                    </div>

                                                    <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                                        <input type="text" class="form-control" id="toplamtutar" placeholder="Tutar Giriniz" value="<?=number_format($ParcaliToplamTutar, 2, '.', ','); ?>">
                                                        <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                                    </div>
                                                </div>

                                                <div class="x_content">
                                                    <div class = "col-md-2 col-sm-2 col-xs-12" ></div>
                                                    <div class="col-md-8 col-sm-8 col-xs-12 form-group has-feedback">
                                                        <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '0')" class="btn btn-primary">Nakit Ödeme</a>
                                                        <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '1')" class="btn btn-success">Kredi Ödeme</a>
                                                        <a onclick="ParcaliVeresiyeGor('<?=$MasaID; ?>', '<?=$SiparisID; ?>')" class="btn btn-warning">Cari Ödeme</a>
                                                        <a onclick="UrunIkramEt('<?=$SiparisID; ?>', '<?=$MasaID; ?>')" class="btn btn-danger">İkram Oluştur</a>
                                                        <a href="kasa.php" class="btn btn-danger">Kasa Dön</a>
                                                    </div>
                                                    <div class = "col-md-2 col-sm-2 col-xs-12" ></div>
                                                </div>
                                            </div>

                                            </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
