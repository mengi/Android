<?php 
	
	include 'db_connect.php';
	
 	if (isset($_GET["regId"]) && isset($_GET["GarSonKod"])) {
 		$RegID = $_GET['regId'];
 		$GarsonKod = $_GET['GarSonKod'];

 		list($RegIDS) = mysql_fetch_array(mysql_query("SELECT RegistrationID FROM wp_gcm_kullanicilar WHERE GarsonKod='$GarSonKod'"));

 		if (!$RegIDS) {
 			$sql = mysql_query("INSERT INTO wp_gcm_kullanicilar (RegistrationID, GarsonKod) values ('$RegID', '$GarsonKod')");
 
		 	if(!$sql) { 
		 		die('MySQL query failed'.mysql_error());
			}
 		}
 	}
?>