<?
    
    session_start();
    include 'class/baglanti.php';

    $SilmeDurumu = 0;
    $SqlSorgu = "";
    $UrunGrupID = 0;

    if (mysql_escape_string($_GET[ID])) {
        $Sonuc = mysql_query("DELETE FROM stok WHERE stokID='$_GET[ID]'");

        if ($Sonuc) {
            $SilmeDurumu = 2;
        } else {
            $SilmeDurumu = 1;
        }
    }


    if (mysql_escape_string($_GET[UrunGrupID])) {
        $UrunGrupID = $_GET[UrunGrupID];
        $SqlSorgu = mysql_query("SELECT stokID, acik, fiyat, aciklama, urungrupid  FROM stok WHERE urungrupid='$UrunGrupID'");
    } else {
        $SqlSorgu = mysql_query("SELECT stokID, acik, fiyat, aciklama, urungrupid  FROM stok");
    }

    function UrunGrupAdi($UrunGrupID) {
        list($urungrupadi) = mysql_fetch_array(mysql_query("SELECT acik FROM urungrup WHERE id='$UrunGrupID'"));
        return $urungrupadi;
    } 
    
?>

        <?  if ($SilmeDurumu == 1) { ?>
            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Ürün Silme İşlemi  Başarısız !!!</strong>
                </div>
            </div>
    <? } else if ($SilmeDurumu == 2) { ?>
            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-1">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Ürün Silme İşlemi Başarılı !!!</strong>
                </div>
            </div>
    <? } ?>

    <script type="text/javascript" language="JavaScript">
        function SilBir(Id, UrunGrupID) {
        //alert(Id + "   "+BolumID);
        $(".liste").load('urun_liste.php?ID='+Id+'&UrunGrupID='+UrunGrupID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function SilIki(Id) {
        //alert(Id);
        $(".liste").load('urun_liste.php?ID='+Id);
        };
    </script>


    <?if (mysql_escape_string($_GET[AraKelime])) {
        $SqlSorgu = mysql_query("SELECT stokID, acik, fiyat, aciklama, urungrupid FROM stok where acik LIKE '%$_GET[AraKelime]%' ORDER BY acik DESC");
        
    ?>


        <div class="row top_tiles">
            <? while ($Urun= mysql_fetch_array($SqlSorgu)) { ?>
                
                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tile-stats">
                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cube"></i></div>
                        <div class="count"></div>
                        <h3><?=$Urun[acik]; ?></h3>
                        <p> Fiyat : <?=$Urun[fiyat]; ?> <i class="fa fa-try"></i></p>
                        <p>Ürün Kategori Adı : <?=UrunGrupAdi($Urun[urungrupid]); ?></p>
                        <p>
                            <a onclick="Sil('<?=$Urun[stokID]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                            <a href="urun_dzn.php?UrunID=<?=$Urun[stokID]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                </i> Düzenle </a>
                        </p>
                    </div>
                </div>
                            
            <? } ?>
        </div>



    <?  }  else {
            if ($UrunGrupID) { ?>
                <div class="row top_tiles">
                    <? while ($Urun= mysql_fetch_array($SqlSorgu)) { ?>
                        
                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <div class="tile-stats">
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cube"></i></div>
                                <div class="count"></div>
                                <h3><?=$Urun[acik]; ?></h3>
                                <p> Fiyat : <?=$Urun[fiyat]; ?> <i class="fa fa-try"></i></p>
                                <p>Ürün Kategori Adı : <?=UrunGrupAdi($Urun[urungrupid]); ?></p>
                                <p>
                                    <a onclick="SilBir('<?=$Urun[stokID]?>', '<?=$UrunGrupID?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                                    <a href="urun_dzn.php?UrunID=<?=$Urun[stokID]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                                </p>
                            </div>
                        </div>
                                    
                    <? } ?>
                </div>
                
        <?  } else { ?>
                <div class="row top_tiles">
                    <? while ($Urun= mysql_fetch_array($SqlSorgu)) { ?>
                        
                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <div class="tile-stats">
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cube"></i></div>
                                <div class="count"></div>
                                <h3><?=$Urun[acik]; ?></h3>
                                <p> Fiyat : <?=$Urun[fiyat]; ?> <i class="fa fa-try"></i></p>
                                <p>Ürün Kategori Adı : <?=UrunGrupAdi($Urun[urungrupid]); ?></p>
                                <p>
                                    <a onclick="SilIki('<?=$Urun[stokID]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                                    <a href="urun_dzn.php?UrunID=<?=$Urun[stokID]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                                </p>
                            </div>
                        </div>
                                    
                    <? } ?>
                </div>
            <?}?>
    <?  } ?>