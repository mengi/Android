<?php
	include ('db_function.php');
	$response = array();

	$userId = $_REQUEST['userId'];
	$imagePath = $_REQUEST['imagePath'];
  	
	if (isset($userId) && isset($imagePath)) {
		$result = getImageInfo($userId, $imagePath);

		if (mysql_num_rows($result) > 0) {
			$response = array();
			while ($row = mysql_fetch_array($result)){
		      $response["userId"] = $row["userId"];
		      $response["imagePath"] = $row["imagePath"];
		      $response["latitude"] = $row["latitude"];
		      $response["longitude"] = $row["longitude"];
		    }
		    echo json_encode($response);
		} else {
			$response = array();
			$response["userId"] = "";
		    $response["imagePath"] = "";
		    $response["latitude"] = "";
		    $response["longitude"] = "";
		    echo json_encode($response);
		}
	} else {
		$response = array();
		$response["userId"] = "";
		$response["imagePath"] = "";
		$response["latitude"] = "";
		$response["longitude"] = "";
		echo json_encode($response);
	}	
?>