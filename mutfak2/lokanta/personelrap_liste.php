<?
    
    include 'class/baglanti.php';

    $AranacakKelime = mysql_escape_string($_GET[AraKelime]);

    $SqlSorgu = "";

    if ($AranacakKelime == "") {
        $SqlSorgu = mysql_query("SELECT KULLANICIADI, GARSONKOD, BULUNDUGUYER FROM garson WHERE KULLANICIADI LIKE '%$AranacakKelime%' ORDER BY KULLANICIADI DESC LIMIT 9");
    } else {
        $SqlSorgu = mysql_query("SELECT KULLANICIADI, GARSONKOD, BULUNDUGUYER FROM garson WHERE KULLANICIADI LIKE '%$AranacakKelime%' ORDER BY KULLANICIADI DESC");
    
    }

    function GarsonToplamKazanc ($Garson_Kod) {
        $Toplam = 0;
        try {
            list($ToplamKazanc) = mysql_fetch_array(mysql_query("SELECT SUM(toplam) AS toplam_tutar FROM siparis WHERE sacankim='$Garson_Kod'"));
            $Toplam = $ToplamKazanc;
        } catch (Exception $e) {
            $Toplam = 0;
        }

        return $Toplam;
    }
    
    function BulunduguYer($katid) {
        list($yeradi) = mysql_fetch_array(mysql_query("SELECT acik FROM kat WHERE id='$katid'"));
        return $yeradi;
    }


?>
    <div class="clearfix"></div>

    <?  
        while ($Garson = mysql_fetch_array($SqlSorgu)) { ?>
            <div class="col-md-4 col-sm-4 col-xs-12 animated fadeInDown">
                <div class="well profile_view">
                    <div class="col-sm-12">
                        <h4 class="brief"><i>Garson</i></h4>
                        <div class="left col-xs-7">
                            <h2><?=$Garson[KULLANICIADI]; ?></h2>
                            <p><strong>Kod: </strong> <?=$Garson[GARSONKOD]; ?> </p>
                            <p><strong>Görev Yeri: </strong> <?=BulunduguYer($Garson[BULUNDUGUYER]) ?> </p>
                            <p><strong>Toplam Kazanç: </strong> <?=number_format(GarsonToplamKazanc($Garson[GARSONKOD]), 2, '.', ','); ?> <i class="fa fa-try"></i></p>
                            </br>
                        </div>
                        <div class="right col-xs-5 text-center">
                                <img src="images/img.jpg" alt="" class="img-circle img-responsive">
                        </div>
                    </div>
                    
                    <div class="col-xs-12 bottom text-center">
                        <div class="col-xs-12 col-sm-6 emphasis"></div>
                        <div class="col-xs-12 col-sm-6 emphasis">
                            <a href="" class="btn btn-success btn-xs"> <i class="fa fa-user">
                                </i> <i class="fa fa-comments-o"></i> </a>
                            <a href="" class="btn btn-primary btn-xs"> <i class="fa fa-user">
                                </i> İstatislik </a>
                        </div>
                    </div>
                </div>
            </div>
    <?  } ?>