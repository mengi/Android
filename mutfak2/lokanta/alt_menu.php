<?  
    include 'class/totaltut.php';

?>

    <div class="row tile_count">
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-circle-o"></i> Bölüm Sayısı</span>
                <div><h2><?=BolumSayisi(); ?> Ad.</h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  Bölüm Göster </a></span>
            </div>
        </div>
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-circle"></i> Masa Sayısı</span>
                <div ><h2><?=MasaSayisi(); ?> Ad.</h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  Masa Göster </a></span>
             </div>
        </div>
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-user"></i>  Garson Sayısı</span>
                <div><h2><?=GarsonSayisi(); ?> Ad.</h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  Garson Göster </a></span>
            </div>
        </div>
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-user"></i> V. Müşteri Sayısı</span>
                <div ><h2><?=VMuseteriSayisi();?> Ad.</h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  V. Müşteri Göster </a></span>
            </div>
        </div>
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-pie-chart"></i> Günlük Kazanç</span>
                <div><h2><?=number_format(GunclukKazanc(), 2, '.', ','); ?> <i class="fa fa-try"></i></h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  Günlük Kazanç </a></span>
            </div>
        </div>
        <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
            <div class="left"></div>
            <div class="right">
                <span class="count_top"><i class="fa fa-university"></i> Toplam Kazanç </span>
                <div ><h2><?=number_format(ToplamKazanc(), 2, '.', ','); ?> <i class="fa fa-try"></i></h2></div>
                <span class="count_bottom"><a class="btn btn-success btn-xs"> <i class="fa fa-eye"></i>  İstatislik Göster </a></span>
            </div>
        </div>
    </div>