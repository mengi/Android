<?php

    include 'mysqlclass.php';
    include 'db_connect.php';

    $response = array();

    if (isset($_POST['masaid']) && isset($_POST['garsonkodu'])) {

        $MasaID = $_POST['masaid'];
        $GarsonKod = $_POST['garsonkodu'];
        list($MasaAd, $MasaAktifMi) = mysql_fetch_array(mysql_query("SELECT acik, aktif FROM masa WHERE id='$MasaID'"));

        if ($MasaAktifMi == "off") {
            if ($MasaID) {
                $Sonuc = mysql_query("UPDATE masa SET aktif='on', acankim='$GarsonKod' WHERE id='$MasaID'");
            }
            
            $_POST['masa'] = $MasaID;
            $_POST['aktif'] = 1;
            $_POST['bastrh'] = date('Y-m-d H:i:s');
            $_POST['sacankim'] = $_POST['garsonkodu'];
            $_POST['oacankim'] = "Telefon";
            $_POST['siparistip'] = "0";

            $Sonuc1 = $db->sql_insert("siparis", $_POST) or die(); 

            if ($Sonuc1) {
                list($SiparisID) = mysql_fetch_array(mysql_query("SELECT id FROM siparis WHERE masa='$MasaID' AND aktif='1'"));

                $response["success"] = 1;
                $response["message"] = $SiparisID;
                echo json_encode($response); 
            } else {
                $response["success"] = 0;
                $response["message"] = "Required field(s) is missing";
                echo json_encode($response);
            }
        } else {
            list($SiparisID, $SiparisAcanKim) = mysql_fetch_array(mysql_query("SELECT id, sacankim FROM siparis WHERE masa='$MasaID' AND aktif='1'"));

            if ($SiparisAcanKim == $_POST['garsonkodu']) {
                $response["success"] = 1;
                $response["message"] = $SiparisID;
                echo json_encode($response); 
            } else {
                $response["success"] = 4;
                $response["message"] = "yetkiyok";
                echo json_encode($response); 
            }


        }

    } else {
        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing";

        echo json_encode($response);
    }
?>