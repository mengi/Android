<?php
 	include 'db_config.php';

	function insertUser($userId, $userName, $userEmail, $userBirthday, $userGender, $userUrl, $accessToken) {
 		$result = mysql_query("INSERT INTO user(userId, userName, userEmail, userBirthday, userGender, userUrl, accessToken) 
 			VALUES('$userId','$userName','$userEmail', '$userBirthday', '$userGender','$userUrl','$accessToken')");
 		return $result;
	}

	function updateUser($userId, $userName, $userEmail, $userBirthday, $userGender) {
 		$result = mysql_query("UPDATE user SET userName = '$userName', userEmail = '$userEmail', userBirthday = '$userBirthday',
 			userGender = '$userGender' WHERE userId = '$userId'");
 		return $result;
	}

	function updateAccessToken($userId, $newAccessToken) {
		$result = mysql_query("UPDATE user SET accessToken = '$newAccessToken' WHERE userId = '$userId'");
 		return $result;
	}

	function getUserData($userId) {
		$result = mysql_query("SELECT * FROM user WHERE userId = '$userId'");
		return $result;
	}

	function getUserInfo($accessToken) {
		$result = mysql_query("SELECT * FROM user WHERE accessToken = '$accessToken'");
		return $result;
	}

	function insertMessage($complaintName, $complaintEmail, $complaintTitle, $complaintSubject, $complainDate) {
 		$result = mysql_query("INSERT INTO message(complaintName, complaintEmail, complaintTitle, complaintSubject, complainDate) 
 			VALUES('$complaintName','$complaintEmail','$complaintTitle', '$complaintSubject','$complainDate')");
 		return $result;
	}

	function insertImage($userId, $imagePath, $latitude, $longitude) {
 		$result = mysql_query("INSERT INTO image(userId, imagePath, latitude, longitude) 
 			VALUES('$userId','$imagePath','$latitude', '$longitude')");
 		return $result;
	}

	function deleteImage($userId, $imagePath) {
		$result = mysql_query("DELETE FROM image WHERE userId = '$userId' AND imagePath = '$imagePath'");
		return $result;
	}

	function getImageAll($userId) {
		$result = mysql_query("SELECT * FROM image WHERE userId = '$userId'");
		return $result;
	}
	function getImageInfo($userId, $imagePath) {
		$result = mysql_query("SELECT * FROM image WHERE userId='$userId' AND imagePath = '$imagePath' ");
		return $result;
	}
?>