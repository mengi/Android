<? 
    include 'index.php';

?>

<body class="nav-md">

    <div class="container body">
        <script type="text/javascript" language="JavaScript">
            function Aramayap() {
                var IlkTarih = document.getElementById('ilk_tarih').value;
                var SonTarih = document.getElementById('son_tarih').value;
                var OdemeTip = document.getElementById('odemetip').value;

                if (!IlkTarih) {IlkTarih=-1;};
                if (!SonTarih) {SonTarih=-2;};
                //alert(BAraKelime);
                $(".liste").load('kasa_rap_liste.php?IlkTarih='+IlkTarih+'&SonTarih='+SonTarih+'&OdemeTip='+OdemeTip);
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
                                    <h2>Kasa</h2>
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
                                            <form class="form-horizontal form-label-left" action="" method="POST">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">İlk Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <input type="text" class="form-control has-feedback-left" id="ilk_tarih" aria-describedby="inputSuccess2Status2">
                                                                    <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                                    <span id="inputSuccess2Status2" class="sr-only">(success)</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Son Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <input type="text" class="form-control has-feedback-left" id="son_tarih"  aria-describedby="inputSuccess2Status2">
                                                                    <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                                                                    <span id="inputSuccess2Status2" class="sr-only">(success)</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Son Tarih</label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <fieldset>
                                                        <div class="control-group">
                                                            <div class="controls">
                                                                <div class="col-md-11 xdisplay_inputx form-group has-feedback">
                                                                    <select id="odemetip" class="select2_single form-control" tabindex="-1" name="odemetip">
                                                                        <option value="-1"> Ödeme Tipi Seçiniz</option>
                                                                        <option value="NAKIT"> NAKİT</option>
                                                                        <option value="KREDI"> KREDİ</option>
                                                                        <option value="VERESIYE"> VERESİYE</option>
                                                                        <option value="PARCALI"> PARÇALI</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="divider-dashed"></div>
                                                <div class="form-group">
                                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                        
                                                        <a onclick="Aramayap()" class="btn btn-success"> Arama Yap!</a>
                                                    </div>
                                                </div>
                                                        
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

    <!-- daterangepicker -->
    <script type="text/javascript" src="js/moment.min2.js"></script>
    <script type="text/javascript" src="js/datepicker/daterangepicker.js"></script>

    <!-- color picker -->
    <script src="js/colorpicker/bootstrap-colorpicker.js"></script>
    <script src="js/colorpicker/docs.js"></script>
    <!-- select2 -->
    <script src="js/select/select2.full.js"></script>


    <!-- /datepicker -->
    <script type="text/javascript">
        $(document).ready(function () {

            $('#ilk_tarih').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_2",
                dateFormat: 'dd-mm-yyyy'
            });            

            $('#son_tarih').daterangepicker({
                singleDatePicker: true,
                calender_style: "picker_2"
            });

        });
    </script>
    <script type=”text/javascript”>
        $(document).ready(function() {;
        $("#ilk_tarih").datepicker({ dateFormat: 'd-mm-yyyy' });
        });
    </script>

    <script>
            $(document).ready(function () {
                $(".select2_single").select2({
                    placeholder: "Select a state",
                    allowClear: true
                });
                $(".select2_group").select2({});
                $(".select2_multiple").select2({
                    maximumSelectionLength: 4,
                    placeholder: "With Max Selection limit 4",
                    allowClear: true
                });
            });
        </script>


</body>

</html>