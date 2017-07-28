<?php

	include ('db_function.php');
	$response = array();

	$accessToken = $_REQUEST['accessToken'];
  $result = getUserInfo($accessToken);

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

    if ($result) {
      $response["success"] = 1;
      $response["message"] = json_encode($responseUser);
      echo json_encode($response);
    } else {
      $response["success"] = 0;
      $response["message"] = "İşlem Başarısız";
      echo json_encode($response);
    }
  } else {
    $response["success"] = 0;
    $response["message"] = "Kullanıcı Yok";
    echo json_encode($response);
  }


?>