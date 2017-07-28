<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
include 'db_connect.php';

// connecting to db
// get all products from products table
$result = mysql_query("SELECT * FROM garson") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["KULLANICIADI"] = $row["KULLANICIADI"];
        $product["PAROLA"] = $row["PAROLA"];
        $product["BULUNDUGUYER"] = $row["BULUNDUGUYER"];



        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
?>
