<?php 

	include ('db_function.php');
	$response = array();

	$userId = $_REQUEST['userId'];
	$imagePath = $_REQUEST['imagePath'];

	if (isset($imagePath) && isset($userId)) {
		$result = deleteImage($userId, $imagePath);

		if ($result) {
			$response["success"] = 1;
		 	$response["message"] = "İşlem Başarılı";  
		 	echo json_encode($response);
		} else {
			$response["success"] = 0;
		 	$response["message"] = "İşlem Başarısız";  
		 	echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
	 	$response["message"] = "Boş Göndermeyin";  
	 	echo json_encode($response);
	}

?>