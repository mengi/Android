<?php
	
	include ('db_function.php');
	$response = array();
	 
	$userId = $_REQUEST['userId'];
	$imagePath = $_REQUEST['imagePath'];
	$latitude = $_REQUEST['latitude'];
	$longitude = $_REQUEST['longitude'];

	if (isset($_REQUEST['userId']) && isset($_REQUEST['imagePath']) 
		&& isset($_REQUEST['latitude']) && isset($_REQUEST['longitude'])) {

		$result = insertImage($userId, $imagePath, $latitude, $longitude);
		if ($result) {
			$response["success"] = 1;
		  	$response["message"] = "Kayıt Başarılı"; 
		  	echo json_encode($response);
		} else {
			$response["success"] = 0;
		  	$response["message"] = "Ne demek başarısız"; 
		 	echo json_encode($response);
		}

	} else {
		$response["success"] = 0;
	 	$response["message"] = "Boş Göndermeyin";  
	 	echo json_encode($response);
	}

?>