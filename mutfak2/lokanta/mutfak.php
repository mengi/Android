<? 
    include 'index.php';

    function MasaAdi($MasaID) {
        list($masaadi) = mysql_fetch_array(mysql_query("SELECT acik FROM masa WHERE id='$MasaID'"));
        return $masaadi;
    }

    function SureHesapla ($EklemeTarihi) {
        $BaslangicTarihi= strtotime($EklemeTarihi);
        $BitisTarihi = strtotime(date("Y-m-d H:i:s"));
        $Fark = abs($BitisTarihi - $BaslangicTarihi);
        $GecenSure = number_format($Fark / 60, 1, '.', '');
        
        return $GecenSure;
    }

    $SqlSorgu = mysql_query("SELECT id, stokid, urunadi, adet, masaid, eklemetrh, siparisdurum, ses FROM siparishareket WHERE siparisdurum='1' ORDER BY id ASC");

?>

<body class="nav-md" style="background-color: #ffffff;">
    
    <script type="text/javascript" language="JavaScript">
        function SiparisListeGoster (SiparisID, MasaID, StokID) {
            //alert("SiparisID = "+SiparisID+" MasaID = "+MasaID+" StokID="+StokID);
            $(".liste").load('mutfak_liste.php?SiparisID='+SiparisID+'&MasaID='+MasaID+'&StokID='+StokID);
        };
    </script>
    
    <script type="text/javascript">
    $(document).ready(function() {
       var refreshId = setInterval(function() {
          $(".liste").load("mutfak_refresh.php");
       }, 5000);
    });
    </script>
    <? include 'menu.php'; ?>

    <div class="container body">
        <div class="main_container">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_content">
                    <div class="liste">
                        <table class="table table-striped responsive-utilities jambo_table bulk_action">
                            <thead>
                                <tr class="headings">
                                    <th class="column-title"><h2> Masa Adı </h2></th>
                                    <th class="column-title"><h2> Ürün Adı </h2></th>
                                    <th class="column-title"><h2> Adet </h2></th>
                                    <th class="column-title"><h2> Süre </h2></th>
                                    <th style="width: 1%" class="column-title"><h2> İşlem </h2></th>
                                </tr>
                            </thead>

                            <tbody>
                            <? 
                                while ($SiparisUrun = mysql_fetch_array($SqlSorgu)) { ?>
                                    <tr class="even pointer">
                                        <td ><?=MasaAdi($SiparisUrun[masaid])?></td>
                                        <td ><?=$SiparisUrun[urunadi]; ?> </td>
                                        <td ><?=$SiparisUrun[adet]; ?> </td>
                                        <td ><?=SureHesapla($SiparisUrun[eklemetrh]); ?> Sn. <i class="success fa fa-clock-o"></i></td>
                                        <td >
                                            <a onclick="SiparisListeGoster('<?=$SiparisUrun[id]?>', '<?=$SiparisUrun[masaid]?>', '<?=$SiparisUrun[stokid]?>')" class="btn btn-danger pull-right"><i class="fa fa-bell-o"></i> Bekliyor</a>
                                        </td>
                                    </tr>

                            <?      
                                    if($SiparisUrun[ses]=='OKUNMADI')
                                    {?>
                                        <object height="0px" width="0px" data="images/sgeldi.mp3">
                                            <param name="autoplay" value="true">
                                            <param name="loop" value="0">
                                        </object>
                                    <?}    
                                mysql_query("UPDATE siparishareket SET ses='OKUNDU' WHERE id='$SiparisUrun[id]'");
                                } ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="custom_notifications" class="custom-notifications dsp_none">
        <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
        </ul>
        <div class="clearfix"></div>
        <div id="notif-group" class="tabbed_notifications"></div>
    </div>

    <script src="js/bootstrap.min.js"></script>


    <script src="js/custom.js"></script>

    <!-- /footer content -->
</body>

</html>
