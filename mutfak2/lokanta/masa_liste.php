<?
    
    session_start();
    include 'class/baglanti.php';

    function KatAdi($id) {
        list($KatAdis) = mysql_fetch_array(mysql_query("SELECT acik FROM kat WHERE id='$id'"));
        return $KatAdis;
    }

    $SilmeDurumu = 0;
    $SqlSorgu = "";
    $BolumID = 0;

    if (mysql_escape_string($_GET[ID])) {
        $Sonuc = mysql_query("DELETE FROM masa WHERE id='$_GET[ID]'");

        if ($Sonuc) {
            $SilmeDurumu = 2;
        } else {
            $SilmeDurumu = 1;
        }
    }

    if (mysql_escape_string($_GET[BolumID])) {
        $BolumID = $_GET[BolumID];
        $SqlSorgu = mysql_query("SELECT id, acik, kat FROM masa WHERE kat='$BolumID'");
    } else {
        $SqlSorgu = mysql_query("SELECT id, acik, kat FROM masa");
    } ?>

    <?  if ($SilmeDurumu == 1) { ?>
            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Masa Silme İşlemi  Başarısız !!!</strong>
                </div>
            </div>
    <? } else if ($SilmeDurumu == 2) { ?>
            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-1">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Masa Silme İşlemi Başarılı !!!</strong>
                </div>
            </div>
    <? } ?>

    <script type="text/javascript" language="JavaScript">
        function SilBir(Id, BolumID) {
        //alert(Id + "   "+BolumID);
        $(".liste").load('masa_liste.php?ID='+Id+'&BolumID='+BolumID);
        };
    </script>

    <script type="text/javascript" language="JavaScript">
        function SilIki(Id) {
        //alert(Id);
        $(".liste").load('masa_liste.php?ID='+Id);
        };
    </script>

    <?
    
    if (mysql_escape_string($_GET[AraKelime])) {
        $SqlSorgu = mysql_query("SELECT id, acik, kat FROM masa where acik LIKE '%$_GET[AraKelime]%' ORDER BY acik DESC");
        
    ?>


        <div class="row top_tiles">
            <? while ($Masa = mysql_fetch_array($SqlSorgu)) { ?>
                
                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tile-stats">
                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-circle"></i></div>
                        <div class="count"></div>
                        <h3><?=$Masa[acik]; ?></h3>
                        <p>Kat Adı : <?=KatAdi($Masa[kat]); ?></p>
                        <p> 
                        <a onclick="Sil('<?=$Masa[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                        <a href="masa_dzn.php?MasaID=<?=$Masa[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                            </i> Düzenle </a>
                    </p>
                </div>
            </div>
                            
            <? } ?>
        </div>


    <? } else { 
            
            if ($BolumID) { ?>
                <div class="row top_tiles">
                    <? while ($Masa = mysql_fetch_array($SqlSorgu)) { ?>
                        
                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <div class="tile-stats">
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-circle"></i></div>
                                <div class="count"></div>
                                <h3><?=$Masa[acik]; ?></h3>
                                <p>Kat Adı : <?=KatAdi($Masa[kat]); ?></p>
                                <p> 
                                    <a onclick="SilBir('<?=$Masa[id]?>', '<?=$BolumID?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                                    <a href="masa_dzn.php?MasaID=<?=$Masa[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                                </p>
                            </div>
                        </div>
                            
                    <? } ?>
                </div>
                
        <?  } else { ?>
                <div class="row top_tiles">
                    <? while ($Masa = mysql_fetch_array($SqlSorgu)) { ?>
                        
                        <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <div class="tile-stats">
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-circle"></i></div>
                                <div class="count"></div>
                                <h3><?=$Masa[acik]; ?></h3>
                                <p>Kat Adı : <?=KatAdi($Masa[kat]); ?></p>
                                <p> 
                                    <a onclick="SilIki('<?=$Masa[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                                    <a href="masa_dzn.php?MasaID=<?=$Masa[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                                </p>
                            </div>
                        </div>
                            
                    <? } ?>
                </div>
            <?}?>
    <? } ?>