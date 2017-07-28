<? 
    include 'index.php';

?>

<body class="nav-md">

    <div class="container body">
        <script type="text/javascript" language="JavaScript">
            function Aramayap() {
                var AraKelime = document.getElementById('aranacak_kelime').value
                var BAraKelime = AraKelime.replace(" ", "~");
                //alert(BAraKelime);
                $(".liste").load('personelrap_liste.php?AraKelime='+BAraKelime);
            };
        </script>

        <div class="main_container">
        <? include 'menu.php' ; ?>

            <div class="right_col" role="main">

                <? include 'alt_menu.php'; ?>
                <br />
                <div class="">
                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Garsonlar</h2>
                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                        </li>
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                        </li>
                                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">

                                    <div class="row">
                                        <div class="clearfix"></div>
                                        <div class="x_content">
                                            <form class="form-horizontal form-label-left">

                                                <div class="form-group">
                                                    <label class="col-sm-3 control-label"></label>

                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <div class="input-group">
                                                            <input id="aranacak_kelime" type="text" class="form-control"  name="aranacak_kelime" placeholder="Garson Ara">
                                                            <span class="input-group-btn">
                                                                <button type="button" onclick="Aramayap()" class="btn btn-primary">Arama Yap !</button> 
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="divider-dashed"></div>                 
                                            </form>
                                            
                                            <div class="liste">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script src="js/bootstrap.min.js"></script>

    <!-- chart js -->
    <script src="js/chartjs/chart.min.js"></script>
    <!-- bootstrap progress js -->
    <script src="js/progressbar/bootstrap-progressbar.min.js"></script>
    <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
    <!-- icheck -->
    <script src="js/icheck/icheck.min.js"></script>

    <script src="js/custom.js"></script>

</body>

</html>