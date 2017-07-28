<?php

    include 'db_connect.php';

    function MasaSiparisId ($MasaId) {
        list($SiparisID) = mysql_fetch_array(mysql_query("SELECT id FROM siparis WHERE masa='$MasaId' AND aktif='1'"));
        return $SiparisID;
    }

    $response = array();

    if (isset($_POST['kat'])) {
        $Kat = $_POST['kat'];

        $result = mysql_query("SELECT id, acik, aktif, siparis FROM masa WHERE kat= '$Kat'");

        if (mysql_num_rows($result) > 0) {
            $response["masalar"] = array();
            
            while ($row = mysql_fetch_array($result)) {
                $product = array();
                $product["id"] = $row["id"];
                $product["acik"] = $row["acik"];
                $product["aktif"] = $row["aktif"];
                $product["siparis"] = $row["siparis"];
                $product["siparisid"] = MasaSiparisId($row["id"]);

                array_push($response["masalar"], $product);
            }

            $response["success"] = 1;
            echo json_encode($response);
        } else {

            $response["success"] = 0;
            $response["message"] = "Başarısız";
            echo json_encode($response);
        }
    } else {
        
        $response["success"] = 0;
        $response["message"] = "no no no ";
        echo json_encode($response);
    }
?>