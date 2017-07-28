<?php

$response = array();

include 'db_connect.php';

$result = mysql_query("SELECT id, acik  FROM urungrup");
    // mysql update row with matched pid

if (mysql_num_rows($result) > 0) {
    $response["urunler"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["id"] = $row["id"];
        $product["acik"] = $row["acik"];


        // push single product into final response array
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

?>