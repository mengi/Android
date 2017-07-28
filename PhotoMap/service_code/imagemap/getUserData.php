<?php

	include ('db_function.php');
	$response = array();

	$userId = $_REQUEST['userId'];
  $result = getUserData($userId);

  if (mysql_num_rows($result) > 0) {
    while ($row = mysql_fetch_array($result)){
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
      $responseUser["message"] = "İşlem Başarılı...";
      echo json_encode($responseUser);
  
  } else {
    $responseUser = array();
    $responseUser["userId"] = $row["userId"];
    $responseUser["userName"] = $row["userName"];
    $responseUser["userEmail"] = $row["userEmail"];
    $responseUser["userBirthday"] = $row["userBirthday"];
    $responseUser["userGender"] = $row["userGender"];
    $responseUser["userUrl"] = $row["userUrl"];
    $responseUser["accessToken"] = $row["accessToken"];
    $responseUser["success"] = 0;
    $responseUser["message"] = "Kullanıcı Yok";
    echo json_encode($responseUser);
  }


?>