<?php
	include ('db_function.php');
	$response = array();
	 
	$userId = $_REQUEST['userId'];
	$userName = $_REQUEST['userName'];
	$userEmail = $_REQUEST['userEmail'];
	$userBirthday = $_REQUEST['userBirthday'];
	$userGender = $_REQUEST['userGender'];

	if (isset($userId) && isset($userName) && isset($userEmail) && isset($userBirthday) && isset($userGender)) {
		$result = updateUser($userId, $userName, $userEmail, $userBirthday, $userGender);
		if ($result) {
		 	$resultTwo = getUserData($userId);

		 	if (mysql_num_rows($resultTwo) > 0) {
			    while ($row = mysql_fetch_array($resultTwo)){
			      $responseUser = array();
			      $responseUser["userId"] = $row["userId"];
			      $responseUser["userName"] = $row["userName"];
			      $responseUser["userEmail"] = $row["userEmail"];
			      $responseUser["userBirthday"] = $row["userBirthday"];
			      $responseUser["userGender"] = $row["userGender"];
			      $responseUser["userUrl"] = $row["userUrl"];
			      $responseUser["accessToken"] = $row["accessToken"];
			    }

			    $responseUser["success"] = 1;
			    $responseUser["message"] = "İşlem Başarılı";
			    echo json_encode($responseUser);
			} else {
				$responseUser = array();
			    $responseUser["userId"] = "";
			    $responseUser["userName"] = "";
			    $responseUser["userEmail"] = "";
			    $responseUser["userBirthday"] = "";
			    $responseUser["userGender"] = "";
			    $responseUser["userUrl"] = "";
			    $responseUser["accessToken"] = "";
			    $responseUser["success"] = 0;
			    $responseUser["message"] = "Kullanıcı Yok";
			    echo json_encode($responseUser);
			}
		} else {
			$responseUser = array();
		    $responseUser["userId"] = "";
		    $responseUser["userName"] = "";
		    $responseUser["userEmail"] = "";
		    $responseUser["userBirthday"] = "";
		    $responseUser["userGender"] = "";
		    $responseUser["userUrl"] = "";
		    $responseUser["accessToken"] = "";
			$responseUser["success"] = 0;
	  		$responseUser["message"] = "Güncelleme Başarısız"; 
	  		echo json_encode($responseUser);
		}
	} else {
			$responseUser = array();
		    $responseUser["userId"] = "";
		    $responseUser["userName"] = "";
		    $responseUser["userEmail"] = "";
		    $responseUser["userBirthday"] = "";
		    $responseUser["userGender"] = "";
		    $responseUser["userUrl"] = "";
		    $responseUser["accessToken"] = "";
			$responseUser["success"] = 0;
	  		$responseUser["message"] = "Güncelleme Başarısız"; 
	  		echo json_encode($responseUser);
		}
?>