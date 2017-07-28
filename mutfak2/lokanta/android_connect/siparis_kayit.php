<?php
    
    include 'mysqlclass.php';
    include 'db_connect.php';
    $response = array();

    if (isset($_POST['stokid']) && isset($_POST['masaid']) && isset($_POST['adet'])) {
        $UrunID = mysql_escape_string($_POST['stokid']);
        $Miktar = mysql_escape_string($_POST['adet']);
        $MasaID = mysql_escape_string($_POST['masaid']);

        list($siparisid, $aktif) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
        if ($aktif == "1") {
            list($stokid, $stokadi, $fiyat, $urungrupid) = mysql_fetch_array(mysql_query("SELECT stokID, acik, fiyat, urungrupid FROM stok WHERE stokID='$UrunID'"));
            $SqlSorgu = mysql_query("SELECT stokID, acik, fiyat, aciklama, urungrupid FROM stok WHERE urungrupid='$urungrupid'");

            $_POST['stokid'] = $stokid;
            $_POST['urunadi'] = $stokadi;
            $_POST['adet'] = $Miktar;
            $_POST['siparisid'] = $siparisid;
            $_POST['masaid'] = $MasaID;
            $_POST['eklemetrh'] = date('Y-m-d H:i:s');
            $_POST['tutar']= $fiyat;
            $_POST['durum']= "0";
            $_POST['siparisdurum']= "1";
            $_POST['siparissekli']= "NORMAL";
            $_POST['sanaladet']= "0";
            $_POST['kesinadet']= $Miktar;
            $_POST['ses']= "OKUNMADI";

            $Sonuc = $db->sql_insert("siparishareket", $_POST) or die();

            if ($Sonuc) {
                $response["success"] = 1;
                $response["message"] = $siparisid;
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
                echo json_encode($response);
            } 
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing";
        echo json_encode($response);
    }
?>