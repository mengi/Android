<?
    
    session_start();
    include 'class/baglanti.php';

    $SilmeDurumu = 0;

    if (mysql_escape_string($_GET[ID])) {
        $Sonuc = mysql_query("DELETE FROM urungrup WHERE id='$_GET[ID]'");

        if ($Sonuc) {
            $Sonuc2 = mysql_query("DELETE FROM stok WHERE urungrupid='$_GET[ID]'");
            if ($Sonuc2) {
                $SilmeDurumu = 2;
            }
        } else {
            $SilmeDurumu = 1;
        }
    } ?>

    <?  if ($SilmeDurumu == 1) { ?>
            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Ürün Grup Silme İşlemi  Başarısız !!!</strong>
                </div>
            </div>
    <? } else if ($SilmeDurumu == 2) { ?>
            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-1">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Ürün Grup Silme İşlemi Başarılı !!!</strong>
                </div>
            </div>
    <? } ?>

    <? 

    if (mysql_escape_string($_GET[AraKelime])) {
        $SqlSorgu = mysql_query("SELECT id, acik FROM urungrup where acik LIKE '%$_GET[AraKelime]%' ORDER BY acik DESC");
        
    ?>



        <? while ($UrunGrup = mysql_fetch_array($SqlSorgu)) { ?>
            
            <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                    <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cubes"></i>
                    </div>
                    <div class="count"> </div>
                    <br>
                    <h3><?=$UrunGrup[acik]; ?></h3>
                    <p>Ürün Sayısı : 8</p>
                    <p> 
                        <a onclick="Sil('<?=$UrunGrup[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                        <a href="urungrup_dzn.php?UrunGrupID=<?=$UrunGrup[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                            </i> Düzenle </a>
                    </p>
            </div>
        </div>
            
    <?  } ?>
    <?  }  else { ?>
        <? 
            $SqlSorgu = mysql_query("SELECT id, acik FROM urungrup");
            while ($UrunGrup = mysql_fetch_array($SqlSorgu)) { ?>
            
            <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                    <div class="icon" style="color:#1ABB9C;"><i class="fa fa-cubes"></i></div>
                    <div class="count"> </div>
                    <br>
                    <h3><?=$UrunGrup[acik]; ?></h3>
                    <p>Ürün Sayısı : 8</p>
                    <p> 
                        <a onclick="SilBir('<?=$UrunGrup[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o"></i> Sil </a>
                        <a href="urungrup_dzn.php?UrunGrupID=<?=$UrunGrup[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                            </i> Düzenle </a>
                    </p>
                                                                   
                </div>

            </div>
                                                            
        <?  } ?>
    <?  } ?>