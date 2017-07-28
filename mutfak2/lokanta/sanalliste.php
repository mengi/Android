<?
	
	session_start();
	include 'class/baglanti.php';

    $ParcaliToplamTutar = 0;

	function AyirParcali($Kelime) {
        $dizi = array('id' => "", 'adet' => "");
        $YeniKelime = "";
        for ($i = 0; $i < strlen($Kelime) ; $i++) {
            if ($Kelime[$i] == '@') {
                $sonsayac = $i - strlen($Kelime);
                $dizi['id'] = substr($Kelime, 0, $i);
                $dizi['adet'] = substr($Kelime, $sonsayac + 1);
             }
        }

        return $dizi;
    }


    $SqlMasaSiparisSorgu = "";
    $masad = "";
    $MasaID = "";
    $siparisid = "";

	$ParcaliListe = $_SESSION[uyeler];
	if (mysql_escape_string($_GET[SonIndex])) {
		(int) $index = mysql_escape_string($_GET[SonIndex]);
        $Miktar = mysql_escape_string($_GET[Miktar]);


        if ($index == -2) {
            $SanalDizi = AyirParcali($ParcaliListe[0]);;
            $SanalDiziID = $SanalDizi['id'];

            list($SanalAdet, $MasaID, $SiparisID) = mysql_fetch_array(mysql_query("SELECT sanaladet, masaid, siparisid FROM siparishareket WHERE id='$SanalDiziID'"));
            list($MasaAd) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
            $masad = $MasaAd;

            if (isset($SanalDiziID)) {
                $YeniSanalAdet = $SanalAdet - $Miktar;
                $Sonuc = mysql_query("UPDATE siparishareket SET sanaladet='$YeniSanalAdet' WHERE id='$SanalDiziID'");

                $MasaID = $MasaID;
                $siparisid = $SiparisID;
                
                $SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket 
                WHERE masaid='$MasaID' AND siparisid='$SiparisID' AND siparisdurum='2' AND durum='0'");
            }

            unset($ParcaliListe[0]);
        } else {
            $SanalDizi = AyirParcali($ParcaliListe[$index]);;
            $SanalDiziID = $SanalDizi['id'];

            list($SanalAdet, $MasaID, $SiparisID) = mysql_fetch_array(mysql_query("SELECT sanaladet, masaid, siparisid  FROM siparishareket WHERE id='$SanalDiziID'"));
            list($MasaAd) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
            $masad = $MasaAd;

            if (isset($SanalDiziID)) {
                $YeniSanalAdet = $SanalAdet - $Miktar;
                $Sonuc = mysql_query("UPDATE siparishareket SET sanaladet='$YeniSanalAdet' WHERE id='$SanalDiziID'");

                $MasaID = $MasaID;
                $siparisid = $SiparisID;

                $SqlMasaSiparisSorgu = mysql_query("SELECT id, stokid, urunadi, adet, siparisid, masaid, tutar, sanaladet, ikramadet FROM siparishareket 
                WHERE masaid='$MasaID' AND siparisid='$SiparisID' AND siparisdurum='2' AND durum='0'");
            }


            unset($ParcaliListe[$index]);
        }


		$ParcaliListe = array_values($ParcaliListe); 
	} else {
        print_r($ParcaliListe);
    }

	$_SESSION[uyeler] = array();
	$_SESSION[uyeler] = $ParcaliListe;



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
                                                $KalanMiktar = 0;
                                                while ($SiparisUrun = mysql_fetch_array($SqlMasaSiparisSorgu)) { 
                                                    $KalanMiktar = ($SiparisUrun[adet] - ($SiparisUrun[sanaladet] + $SiparisUrun[ikramadet]));
                                                    if ($KalanMiktar > 0) { ?>
                                                    
                                                        <a  onclick="UrunOdes('<?=$SiparisUrun[id]?>', '<?=$MasaID?>', document.getElementById('girilen_miktar').value, '<?=$siparisid?>')">                 
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

                                                                list($SanalUrunAdi, $SanalTutar) = mysql_fetch_array(mysql_query("SELECT urunadi, tutar FROM siparishareket WHERE id='$DiziSiparisId'"));
                                                                                            
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
                                                        <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '0')" class="btn btn-primary">Parçalı Nakit Ödeme</a>
                                                        <a onclick="NKOdemeTumu('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '1')" class="btn btn-success">Parçalı Kredi Ödeme</a>
                                                        <a onclick="ParcaliVeresiyeGor('<?=$MasaID; ?>', '<?=$SiparisID; ?>')" class="btn btn-warning">Parçalı Veresiye Ödeme</a>
                                                        <a onclick="UrunIkramEt('<?=$SiparisID; ?>', '<?=$MasaID; ?>')" class="btn btn-danger">İkram Oluştur</a>
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

