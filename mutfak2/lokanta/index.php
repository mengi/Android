<?
	session_start();
	include 'class/baglanti.php';
	$sid = $_SESSION[sid];

	function cikart() {
		unset($_SESSION[kullanici]);
		session_destroy();
		?><script language="JavaScript" type="text/javascript">location.href='login.php'</script><?
	}

	if ((!isset($_SESSION[kullanici])) AND (!isset($sid))) { 
		include "login.php"; 
		exit;
	} else { 
		$zamanlimit = time() - 1800; //limit 15dk
		mysql_query("DELETE FROM oturum WHERE zaman < '".$zamanlimit."'");
		$S_Oturum = mysql_query("SELECT kullanici  FROM oturum WHERE sid='".$sid."'");
	if(mysql_num_rows($S_Oturum) == 1) {
		while (list($S_KULLANICIADI) = mysql_fetch_array($S_Oturum)) {

			if($S_KULLANICIADI != $_SESSION[kullanici])	{
				cikart();
			}
		  	
		}
        $time = time();
		@mysql_query("UPDATE oturum SET zaman ='$time' WHERE sid='".$sid."'");
	} else {
		cikart();
 	}
  }

?>


<!DOCTYPE html>
<html lang="tr">

<head>
  <!--   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    Meta, title, CSS, favicons, etc. -->

<META http-equiv="content-type content=text/html;charset=iso-8859-9">
<META http-equiv="content-type content=text/html;charset=windows-1254">
<META http-equiv="content-type content=text/html;charset=x-mac-turkish">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Lokanta! | </title>
    
    <!-- Bootstrap core CSS -->

    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">

    <!-- Custom styling plus plugins -->
    <link href="css/custom.css" rel="stylesheet">
    <link href="css/icheck/flat/green.css" rel="stylesheet">

    <!-- select2 -->
    <link href="css/select/select2.min.css" rel="stylesheet">
    <!-- switchery -->
    <link rel="stylesheet" href="css/switchery/switchery.min.css" />

    <script src="js/jquery.min.js"></script>

    <!-- colorpicker -->
    <link href="css/colorpicker/bootstrap-colorpicker.min.css" rel="stylesheet">

            <!-- bootstrap progress js -->
    <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
    
    <!--[if lt IE 9]>
        <script src="../assets/js/ie8-responsive-file-warning.js"></script>
        <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

</head>