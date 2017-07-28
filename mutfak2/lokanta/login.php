

<!DOCTYPE html>
<html lang="tr">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Lokanta |  Resturant</title>

    <!-- Bootstrap core CSS -->

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">

    <!-- Custom styling plus plugins -->
    <link href="css/custom.css" rel="stylesheet">
    <link href="css/icheck/flat/green.css" rel="stylesheet">
    <script src="js/jquery.min.js"></script>

    <link rel="stylesheet" type="text/css" href="css/progressbar/bootstrap-progressbar-3.3.0.css">

    <script src="js/jquery.min.js"></script>

    <script type="text/javascript" language="JavaScript">
    function Giris() {
        var kullaniciadi = document.getElementById('kullaniciadi').value
        var parola = document.getElementById('parola').value
        //alert("kullaniciadi = "+kullaniciadi+"parola"+parola);
        $(".GirisYap").load('giris_kontrol.php?kullaniciadi='+kullaniciadi+'&parola='+parola);
    };
</script>

</head>

<body style="background:#F7F7F7;">
    
    <div class="">
        <div id="wrapper">

            <div id="login" class="animate form">
                <section class="login_content">
                    <div class="GirisYap">

                    </div>
                    <form method="POST">
                        <h1>Giriş Paneli</h1>
                        <div>
                            <input id="kullaniciadi" type="text" class="form-control" name="kullaniciadi" placeholder="Kullanıcı Adı" required="" />
                        </div>
                        <div>
                            <input id="parola" type="password" class="form-control" name="parola" placeholder="Parola" required="" />

                        </div>
                        <div>
                            <a class="btn btn-default submit" onclick="Giris()">Giriş Yap</a>
                        </div>
                    </form>
                    <!-- form -->
                </section>
                <!-- content -->
            </div>
        </div>
    </div>

</body>
<script src="js/bootstrap.min.js"></script>

</html>
