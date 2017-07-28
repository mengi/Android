<?php

$response = array();

if (isset($_POST['urungrupid'])) {
    $UrunGrupId = $_POST['urungrupid'];

    include 'db_connect.php';

    $result = mysql_query("SELECT stokID, acik, fiyat, urungrupid FROM stok WHERE urungrupid = '$UrunGrupId' AND durumd='0'");

    if (mysql_num_rows($result) > 0) {

        $response["urundetaylar"] = array();
        
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $product = array();
            $product["stokID"] = $row["id"];
            $product["acik"] = $row["acik"];
            $product["fiyat"] = $row["fiyat"];
            $product["urungrupid"] = $row["urungrupid"];



            // push single product into final response array
            array_push($response["urundetaylar"], $product);
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