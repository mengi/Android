<?php

/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['parola'])) {
    $Parola = $_POST['parola'];
    $GarsonKod = $_POST['garsonkod'];

    // include db connect class
    include 'db_connect.php';

    $result = mysql_query("SELECT KULLANICIADI, PAROLA, BULUNDUGUYER, GARSONKOD FROM garson WHERE GARSONKOD = '$GarsonKod' AND PAROLA = '$Parola'");
    // mysql update row with matched pid

    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
        $response["garson"] = array();
        
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $product = array();
            $product["KULLANICIADI"] = $row["KULLANICIADI"];
            $product["PAROLA"] = $row["PAROLA"];
            $product["BULUNDUGUYER"] = $row["BULUNDUGUYER"];
            $product["GARSONKOD"] = $row["GARSONKOD"];



            // push single product into final response array
            array_push($response["garson"], $product);
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