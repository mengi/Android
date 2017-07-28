<?
session_start();
$KullaniciAdi = $_SESSION[kullanici];
session_destroy();
include 'class/baglanti.php';
@$s_del = mysql_query("DELETE FROM oturum WHERE kullanici='$KullaniciAdi'");

header('Location: index.php')
?>
<!--
			<script type="text/javascript" language="JavaScript">
				alert("Guvenli Cikis Yapilmistir")
				location.href='index.php'
			</script>;
-->