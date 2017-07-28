<?

session_start();
include "class/baglanti.php";

$KullaniciAdi = mysql_escape_string($_GET['kullaniciadi']);
$Sifre = mysql_escape_string($_GET['parola']);


$zamanlimit = time() - 1800; //limit 30dk
mysql_query("DELETE FROM oturum WHERE zaman < '".$zamanlimit."'");

if (!isset($KullaniciAdi)) {
    unset($KullaniciAdi);
    session_destroy();
}

if (isset($KullaniciAdi) && isset($Sifre)) {
    list($Id, $Kullanici, $Statu) = mysql_fetch_array(mysql_query("SELECT id, kullanici, statu FROM yonetici WHERE kullanici='$KullaniciAdi' AND sifre = PASSWORD('$Sifre')"));
    if($Id > 0) {

        $sid = md5(uniqid(rand(), 1));
        @mysql_query("INSERT INTO oturum VALUES('".$KullaniciAdi."', '".$sid."', '".time()."')");
        $_SESSION[id] = $Id;
        $_SESSION[kullanici] = $Kullanici;
        $_SESSION[statu] = $Statu;
        $_SESSION[sid] = $sid;
        $_SESSION[uyeler] = array();
        

        if ($_SESSION[statu] == "0") { ?>
                <script type="text/javascript" language="JavaScript">location.href='admin.php'</script>
        <?  } else if ($_SESSION[statu] == "1") { ?>
                <script type="text/javascript" language="JavaScript">location.href='kasa.php'</script>
        <?  } else if ($_SESSION[statu] == "2") { ?>
                <script type="text/javascript" language="JavaScript">location.href='mutfak.php'</script>
        <?  } ?>

    <?
    } else { 
        unset($KullaniciAdi);
    ?>
        <div class="alert alert-danger alert-dismissible fade in" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
            <strong >Kullanıcı Adı veya Şifrele Hatalı !!!</strong>
        </div>
<? }
} 
?>
