<?
    
    session_start();
    include 'class/baglanti.php';

    $SilmeDurumu = 0;
    function BulunduguYerAdi ($Id) {
        list($ad) = mysql_fetch_array(mysql_query("SELECT acik FROM kat WHERE id='$Id'"));
        return $ad;
    }

    if (mysql_escape_string($_GET[ID])) {
        $Sonuc = mysql_query("DELETE FROM garson WHERE ID='$_GET[ID]'");

        if ($Sonuc) {
            $SilmeDurumu = 2;
        } else {
            $SilmeDurumu = 1;
        }
    }

    if ($SilmeDurumu == 1) { ?>
            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Personel Silme İşlemi  Başarısız !!!</strong>
                </div>
            </div>
    <? } else if ($SilmeDurumu == 2) { ?>
            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-1">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Personel Silme İşlemi Başarılı !!!</strong>
                </div>
            </div>
    <? } ?>


    <? 

    if (mysql_escape_string($_GET[AraKelime])) {
        $SqlSorgu = mysql_query("SELECT ID, KULLANICIADI, BULUNDUGUYER, GARSONKOD FROM garson where KULLANICIADI LIKE '%$_GET[AraKelime]%' ORDER BY KULLANICIADI DESC");
        
    ?>

        <div class="row top_tiles">
            <? while ($Personel = mysql_fetch_array($SqlSorgu)) { ?>
                
                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tile-stats">
                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-user"></i>
                        </div>
                        <div class="count"> </div>
                        <br>
                        <h3><?=$Personel[KULLANICIADI]; ?></h3>
                        <P> Garson Kod : <?=$Personel[GARSONKOD]; ?></P>
                        <p> Görev Yeri : <?=BulunduguYerAdi($Personel[BULUNDUGUYER]); ?></p>
                        <p> 
                            <a onclick="Sil('<?=$Personel[ID]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o">
                                                                                        </i> Sil </a>
                            <a href="personel_dzn.php?PersonelID=<?=$Personel[ID]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                    </i> Düzenle </a>
                        </p>
                                                                       
                    </div>
                </div>
            <?}?>
        </div>
    <?  }  else { 

            $SqlSorguGarson = mysql_query("SELECT ID, KULLANICIADI, BULUNDUGUYER, GARSONKOD FROM garson");

        ?>
            <div class="row top_tiles">
                <? while ($Personel = mysql_fetch_array($SqlSorguGarson)) { ?>
                    
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon" style="color:#1ABB9C;"><i class="fa fa-user"></i>
                            </div>
                            <div class="count"> </div>
                            <br>
                            <h3><?=$Personel[KULLANICIADI]; ?></h3>
                            <P> Garson Kod : <?=$Personel[GARSONKOD]; ?></P>
                            <p> Görev Yeri : <?=BulunduguYerAdi($Personel[BULUNDUGUYER]); ?></p>
                            <p> 
                                <a onclick="SilBir('<?=$Personel[ID]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o">
                                                                                            </i> Sil </a>
                                <a href="personel_dzn.php?PersonelID=<?=$Personel[ID]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                            </p>
                                                                           
                        </div>
                    </div>
                <?}?>
            </div>
    <?  } ?>