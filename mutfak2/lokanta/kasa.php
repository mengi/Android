<? 
    
    include 'index.php';

    function MasaSayisi ($BolumId) {
        list($sayac) = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM masa WHERE kat='$BolumId'"));
        return $sayac;
    }

    function AcikkMasalar ($BolumId) {
        list($sayac) = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM masa WHERE kat='$BolumId' AND aktif='on'"));
        return $sayac;
    }

    function KapalikMasalar ($BolumId) {
        list($sayac) = mysql_fetch_array(mysql_query("SELECT COUNT(*) FROM masa WHERE kat='$BolumId' AND aktif='off'"));
        return $sayac;
    }


    $SorguBolum = mysql_query("SELECT id, acik FROM kat");

?>

<body class="nav-md" style="background-color: #ffffff;">

    <? include 'menu.php'; ?>

        <div class="main_container">
        	 <div class="right_col" role="main">

                <!-- top tiles -->

                    <div class="row top_tiles">
                        <? while ($Bolum = mysql_fetch_array($SorguBolum)) { ?>
                            <a href="masa.php?BolumId=<?=$Bolum[id];?>">                                  
                            <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="tile-stats">
                                    <div class="icon" style="color:#1ABB9C;"><i class="fa fa-circle-o"></i>
                                    </div>
                                    <div class="count"><?=$Bolum[acik]; ?></div>
                                    <h3></h3>
                                    <p>Masa Sayısı : <?=MasaSayisi($Bolum[id]); ?> | Açık Masa Sayısı : <?=AcikkMasalar($Bolum[id]); ?>  | Kapalı Masa Sayıısı : <?=KapalikMasalar($Bolum[id]); ?>  </p>
                                </div>
                            </div>
                            </a>
                            
                        <?}?>
                        <a href="sicak_satis.php">
                            <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="tile-stats">
                                    <div class="icon" style="color:#39cccc;"><i class="fa fa-paper-plane"></i>
                                    </div>
                                    <div class="count">Sıcak Satış</div>
                                    <h3> </h3>
                                    <p>Satış Tipi : Sıcak</p>
                                </div>
                            </div>
                        </a>


                        <a href="paket_satis.php">
                            <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="tile-stats">
                                    <div class="icon" style="color:#39cccc;"><i class="fa fa-shopping-cart"></i>
                                    </div>
                                    <div class="count">Paket Satış</div>
                                    <h3></h3>
                                    <p>Satış Tipi : Paket</p>
                                </div>
                            </div>
                        </a>
                        <a href="odeme.php"> 
                            <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="tile-stats">
                                    <div class="icon" style="color:#ff4136;"><i class="fa fa-try"></i>
                                    </div>
                                    <div class="count">Ödeme</div>
                                    <h3></h3>
                                    <p>Ödeme Tipleri : Nakit, Kredi</p>
                                </div>
                            </div>
                        </a>
                        <!--
                        <a href="veresiye.php"> 
                            <div class="animated flipInY col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                <div class="tile-stats">
                                    <div class="icon" style="color:#ff851b;"><i class="fa fa-try"></i>
                                    </div>
                                    <div class="count">Veresiye</div>
                                    <h3></h3>
                                    <p>Ödeme Tipi: Veresiye</p>
                                </div>
                            </div>
                        </a>
                        -->
                    </div>

                
                <!-- /top tiles -->

               
                <br />
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
