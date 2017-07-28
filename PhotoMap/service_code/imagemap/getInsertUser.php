<?php
	include ('db_function.php');
	$response = array();
	 
	$userId = $_REQUEST['userId'];
	$userName = $_REQUEST['userName'];
	$userEmail = $_REQUEST['userEmail'];
	$userBirthday = $_REQUEST['userBirthday'];
	$userGender = $_REQUEST['userGender'];
	$userUrl = $_REQUEST['userUrl'];
	$accessToken = $_REQUEST['accessToken'];

	$result_one = mysql_query("SELECT * FROM user WHERE userId = '$userId'");

	if (isset($_REQUEST['userId']) && isset($_REQUEST['userName']) && isset($_REQUEST['userEmail']) && isset($_REQUEST['userBirthday']) 
		&& isset($_REQUEST['accessToken']) && isset($_REQUEST['userGender']) && isset($_REQUEST['userUrl'])) {

		if (mysql_num_rows($result_one) > 0) {
			$response["success"] = 0;
	  		$response["message"] = "Email Daha Önce Kayıtlıdır."; 
	  		echo json_encode($response);
		} else {
			 
			$result = insertUser ($userId, $userName, $userEmail, $userBirthday,
				$userGender, $userUrl, $accessToken);
		 	if ($result) {
		 		$response["success"] = 1;
		  		$response["message"] = "Kayıt Başarılı"; 
		  		echo json_encode($response);
		 	} else {
		  		$response["success"] = 0;
		  		$response["message"] = "Ne demek başarısız"; 
		  		echo json_encode($response);
			}
		}
	} else {
		$response["success"] = 0;
	 	$response["message"] = "Boş Göndermeyin";  
	 	echo json_encode($response);
	}

?>