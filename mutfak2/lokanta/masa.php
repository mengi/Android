<? 

    include 'index.php';

    $BolumID = mysql_escape_string($_GET[BolumId]);

    list($BolumAd) = mysql_fetch_array(mysql_query("SELECT acik FROM kat WHERE id='$BolumID'"));

    $SorguMasa = "";
    if ($BolumID) {
        $SorguMasa = mysql_query("SELECT id, acik, aktif FROM masa WHERE kat='$BolumID'");
    }
?>

<body style="background-color: #ffffff;">

    <script type="text/javascript" language="JavaScript">
        function MasaKapat(Id, BolumID) {
            //alert(Id +'   '+ BolumID);
            $(".masakapat").load('masa_kapat.php?ID='+Id+'&BolumID='+BolumID);
        };
    </script>
    <? include 'menu.php'; ?>
    <div class="masakapat">

        <div class="container" style="margin-right:0px;">
            <? 
            while ($Masa = mysql_fetch_array($SorguMasa)) { ?>
                <a href="masa_urun_ekle.php?MasaID=<?=$Masa[id];?>">
                    <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        <div class="tile-stats">
                            <? if ($Masa[aktif] == "on") { ?>
                                <div class="icon" style="color:#1ABB9C;"><i class="fa fa-check"></i></div>
                            <? } else if ($Masa[aktif] == "off") {?>
                                <div class="icon" style="color:#ff4136;"><i class="fa fa-times"></i></div>
                            <? }?>
                                            
                            <div class="count"><?=$Masa[acik];?></div>
                            <h3><?=$BolumAd; ?></h3>
                            <p> 
                                <a onclick="masa_urun_ekle.php?MasaID=<?=$Masa[id];?>" class="btn btn-success btn-xs"> <i class="fa fa-circle">
                                </i> Masa Aç </a>
                                <a onclick="MasaKapat('<?=$Masa[id];?>', '<?=$BolumID?>')" class="btn btn-danger btn-xs"> <i class="fa fa-circle">
                                </i> Masa Kapat </a>
                                <? if ($Masa[aktif] == "on") { ?>
                                    <a href="odeme.php?MasaID=<?=$Masa[id];?>" class="btn btn-danger btn-xs"> <i class="fa fa-circle">
                                    </i> Ödeme </a>
                                <? } ?>
                            </p>
                                            
                        </div>
                    </div>
                </a>
            <?}?>
        </div>
    </div>
    <br />
    <div id="custom_notifications" class="custom-notifications dsp_none">
        <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
        </ul>
        <div class="clearfix"></div>
        <div id="notif-group" class="tabbed_notifications"></div>
    </div>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
</body>
</html>