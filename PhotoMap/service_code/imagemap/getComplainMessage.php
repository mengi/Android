<?php
	include ('db_function.php');
	$response = array();
	 
	$complaintName = $_REQUEST['complaintName'];
	$complaintEmail = $_REQUEST['complaintEmail'];
	$complaintTitle = $_REQUEST['complaintTitle'];
	$complaintSubject = $_REQUEST['complaintSubject'];
	$complainDate = $_REQUEST['complainDate'];

	if (isset($_REQUEST['complaintName']) && isset($_REQUEST['complaintEmail']) 
		&& isset($_REQUEST['complaintTitle']) && isset($_REQUEST['complaintSubject'])) {
		$result = insertMessage($complaintName, $complaintEmail, $complaintTitle, $complaintSubject, $complainDate);
	 	if ($result) {
	 		$response["success"] = 1;		  		
	 		$response["message"] = "Şikayetiniz İletildi"; 
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