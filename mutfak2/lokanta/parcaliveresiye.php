<?	
	session_start();
	include 'class/baglanti.php';
	$ParcaliListe; 

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

    $ParcaliListe = $_SESSION[uyeler];

	if (mysql_escape_string($_GET[MasaID])) {

		$MasaID = mysql_escape_string($_GET[MasaID]);
		$SiparisID = mysql_escape_string($_GET[SiparisID]);

		list($MasaAd) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
		$SqlVeresiyeSorgu = mysql_query("SELECT id, adsoyad FROM veresiye");
	}



?>

        <div class="clearfix"></div>
        	<div class="row">
		        <div class="col-md-6 col-xs-12">
		            <div class="x_panel">
		                <div class="x_title">
		                    <h2> <?=$MasaAd; ?> Ödeme</h2>
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
		                	<form data-parsley-validate class="form-horizontal form-label-left">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">Ad Soyad <span class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                         <input type="text" id="adsoyad" required="required" class="form-control col-md-7 col-xs-12" name="adsoyad">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Telefon Numarası <span class="required">*</span></label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <input type="text" id="telefon" class="form-control" data-inputmask="'mask' : '(999) 999-9999'" name="telefon">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Kat Seçiniz</label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <select id="veresiye" class="select2_single form-control" tabindex="-1" name="veresiye">
                                        <? while(list($id, $adsoyad) = mysql_fetch_array($SqlVeresiyeSorgu)) {?>
                                            <option value="<?=$id;?>"><?=$adsoyad;?></option>
                                        <? } ?>
                                        </select>
                                    </div>
                                </div>
                            </form>
		                </div>
		            </div>
		        </div>

		        <div class="col-md-6 col-xs-12">
		            <div class="x_panel">
		                <div class="x_title">
		                    <h2><?=$MasaAd; ?> Hesap </h2>
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
			            	<div class="col-md-12 col-sm-12 col-xs-12">
			            		<div class="veresiyeliste">
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
                                            $ParcaliToplamTutar = 0;
                                            $ParcaliIkramTutar = 0;

                                            for ($i = 0; $i < count($ParcaliListe); $i++) {
                                                $dizi = AyirParcali($ParcaliListe[$i]);
                                                $DiziSiparisId = $dizi['id'];
                                                $DiziSiparisAdet = $dizi['adet'];

                                                list($SanalMasaID, $SanalSiparisID, $SanalUrunAdi, $SanaTutar, $SanalIkram) = mysql_fetch_array(mysql_query("SELECT masaid, siparisid, urunadi, tutar, ikramadet FROM siparishareket WHERE id='$DiziSiparisId'"));
                                                    
                                                if ($i == 0) {
                                                    $SanalIkramSorgu = mysql_query("SELECT tutar, ikramadet FROM siparishareket WHERE masaid='$SanalMasaID' AND siparisid='$SanalSiparisID'");
                                                    while ($IkramSorgu = mysql_fetch_array($SanalIkramSorgu)) {
                                                        $ParcaliIkramTutar += ($IkramSorgu[ikramadet] * $IkramSorgu[tutar]);
                                                    }
                                                }
                                                
                                                if (isset($SanaTutar)) {
                                                    $ParcaliToplamTutar += ($SanaTutar * $DiziSiparisAdet);
                                        ?>

                                             		<tr class="even pointer">
                                                 		<td class="a-center"><?=$SanalUrunAdi; ?></td>
                                                 		<td class=" ">                                                    
                                                     		<div class="col-md-4">
                                                        		<input type="text" value="<?=$DiziSiparisAdet; ?>" class="form-control" id='sanal_miktars<?=$i;?>' readonly="readonly">
                                                     		</div>
                                                 		</td>
                                                 		<td class=" "><?=$SanaTutar; ?> <i class="fa fa-try"></i></td>
                                                 		<td class=" last"><a onclick="ParcaliSanalSil('<?=$i;?>', document.getElementById('sanal_miktars<?=$i;?>').value)" class="btn btn-danger btn-xs" >Sil</a></td>
                                             		</tr>
                                             <? }
                                            } ?>
                                        </tbody>
                                    </table>
				            	</div>
			         		</div>

                            <div class="x_content">
                                <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                    <input type="text" class="form-control has-feedback-right" id="ikramtutar" placeholder="Tutar Giriniz" 
                                    value="<?=number_format($ParcaliIkramTutar, 2, '.', ','); ?>">
                                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                </div>
                            </div>

                            <div class="x_content">
                                <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                    <input type="text" class="form-control has-feedback-right" id="indirim" placeholder="İndirim Tutarı Giriniz">
                                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                </div>

                                <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
                                    <input type="text" class="form-control" id="toplamtutar" placeholder="Tutar Giriniz" 
                                    value="<?=number_format($ParcaliToplamTutar, 2, '.', ','); ?>">
                                    <span class="fa fa-try form-control-feedback right" aria-hidden="true"></span>
                                </div>
                            </div>

			         	<div class="x_content">
				            <a onclick="ParcaliVeresiyeOde('<?=$MasaID; ?>', '<?=$SiparisID; ?>', '4', 
				            		document.getElementById('adsoyad').value, document.getElementById('indirim').value, 
                                    document.getElementById('ikramtutar').value, document.getElementById('toplamtutar').value, 
                                    document.getElementById('veresiye').value)"

				            	class="col-md-12 btn btn-warning">Parcalı Veresiye Ödeme</a>
			            </div>
		        	</div>
	     		</div>
	     	</div>