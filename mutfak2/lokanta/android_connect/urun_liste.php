<?php

$response = array();

if (isset($_POST['id'])) {
    $ID = $_POST['id'];

    include 'db_connect.php';

    $result = mysql_query("SELECT stokID, acik, fiyat, urungrupid FROM stok WHERE urungrupid= '$ID'");

    if (mysql_num_rows($result) > 0) {
        $response["urunler"] = array();
        
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $product = array();
            $product["stokID"] = $row["stokID"];
            $product["acik"] = $row["acik"];
            $product["fiyat"] = $row["fiyat"];
            $product["urungrupid"] = $row["urungrupid"];

            array_push($response["urunler"], $product);
        }
        // success
        $response["success"] = 1;

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "Başarısız";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "no no no ";

    // echoing JSON response
    echo json_encode($response);
}
?>