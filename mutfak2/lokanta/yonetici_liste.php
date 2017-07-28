<?
    
    session_start();
    include 'class/baglanti.php';

    $SilmeDurumu = 0;

    if (mysql_escape_string($_GET[ID])) {
        $Sonuc = mysql_query("DELETE FROM yonetici WHERE id='$_GET[ID]'");

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
                    <strong > Yönetici Silme İşlemi  Başarısız !!!</strong>
                </div>
            </div>
    <? } else if ($SilmeDurumu == 2) { ?>
            <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-1">
                <div class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <strong > Yönetici Silme İşlemi Başarılı !!!</strong>
                </div>
            </div>
    <? } ?>

    <? if (mysql_escape_string($_GET[AraKelime])) {
        $SqlSorgu = mysql_query("SELECT id, kullanici, statu FROM yonetici where kullanici LIKE '%$_GET[AraKelime]%' ORDER BY kullanici DESC");
        
    ?>

        <div class="row top_tiles">
            <? while ($Yonetici = mysql_fetch_array($SqlSorgu)) { ?>
                
                <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tile-stats">
                        <div class="icon" style="color:#1ABB9C;"><i class="fa fa-user"></i>
                        </div>
                        <div class="count"> </div>
                        <br>
                        <h3><?=$Yonetici[kullanici]; ?></h3>
                        <p>Statü :
                            <? if ($Yonetici[statu] == "0") { ?>
                                Admin
                            <? } else if ($Yonetici[statu] == "1") { ?>
                                Kasa
                            <? } else if ($Yonetici[statu] == "2") { ?>
                                Mutfak
                            <?  } ?>
                        </p>
                        <p> 
                            <a onclick="Sil('<?=$Yonetici[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o">
                                        </i> Sil </a>
                            <a href="yonetici_dzn.php?YoneticiID=<?=$Yonetici[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                        </i> Düzenle </a>
                         </p>
                                                                       
                    </div>
                </div>                                                    
            <?}?>
        </div>
            
    <?  }  else { 
            $SqlSorguYonetici = mysql_query("SELECT id, kullanici, statu FROM yonetici");

            ?>
            <div class="row top_tiles">
                <? while ($Yonetici = mysql_fetch_array($SqlSorguYonetici)) { ?>
                    
                    <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <div class="icon" style="color:#1ABB9C;"><i class="fa fa-user"></i>
                            </div>
                            <div class="count"> </div>
                            <br>
                            <h3><?=$Yonetici[kullanici]; ?></h3>
                            <p>Statü :
                                <? if ($Yonetici[statu] == "0") { ?>
                                    Admin
                                <? } else if ($Yonetici[statu] == "1") { ?>
                                    Kasa
                                <? } else if ($Yonetici[statu] == "2") { ?>
                                    Mutfak
                                <?  } ?>
                            </p>
                            <p> 
                                <a onclick="SilBir('<?=$Yonetici[id]?>')" class="btn btn-danger btn-xs"> <i class="fa fa-trash-o">
                                            </i> Sil </a>
                                <a href="yonetici_dzn.php?YoneticiID=<?=$Yonetici[id]?>" class="btn btn-primary btn-xs"> <i class="fa fa-pencil">
                                            </i> Düzenle </a>
                             </p>
                                                                           
                        </div>
                    </div>                                                    
                <?}?>
            </div>
    <?  } ?>