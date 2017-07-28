<?
    
    include 'class/baglanti.php';

    $AranacakKelime = mysql_escape_string($_GET[AraKelime]);
    $SqlSorgu = "";

    if ($AraKelime == "") {
       
        $SqlSorgu = mysql_query("SELECT acik, fiyat, urungrupid FROM stok WHERE acik LIKE '%$AranacakKelime%' ORDER BY acik DESC LIMIT 9");
 
    } else {

        $SqlSorgu = mysql_query("SELECT acik, fiyat, urungrupid FROM stok WHERE acik LIKE '%$AranacakKelime%' ORDER BY acik DESC");

    }

    function UrunKategori ($katid) {
        list($urunadi) = mysql_fetch_array(mysql_query("SELECT acik FROM urungrup WHERE id='$katid'"));
        return $urunadi;
    }

?>
    <div class="clearfix"></div>

        <? 

        while ($Urun = mysql_fetch_array($SqlSorgu)) { ?>

        <div class="col-md-4 col-sm-4 col-xs-12 animated fadeInDown">
            <div class="well profile_view">
                <div class="col-sm-12">
                    <h4 class="brief"><i>Garson</i></h4>
                    <div class="left col-xs-7">
                        <h2><?=$Urun[acik]; ?></h2>
                        <p><strong>Fiyat: </strong> <?=$Urun[fiyat]; ?> TL</p>
                        <p><strong>Kategori: </strong> <?=UrunKategori($Urun[urungrupid]); ?> </p>
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
    <? } ?>