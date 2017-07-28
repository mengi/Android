<?php

	include ('db_function.php');
	$response = array();

	$userId = $_REQUEST['userId'];

	if (isset($userId)) {
		$result = getImageAll($userId);

		if (mysql_num_rows($result) > 0) {
			while ($row = mysql_fetch_array($result)) {
				$data = array();
				$data["userId"] =  $row["userId"];
				$data["imagePath"] =  $row["imagePath"];
				$data["latitude"] =  $row["latitude"];
				$data["longitude"] =  $row["longitude"];
				$response[] = $data;
			}
			
			if ($result) {
				echo json_encode(array_values($response));
			}

		} else {
			$data = array();
			$data["userId"] =  "";
			$data["imagePath"] =  "";
			$data["latitude"] =  "";
			$data["longitude"] =  "";
		 	echo json_encode($response);
		}
	} else {
		$data = array();
		$data["userId"] =  "";
		$data["imagePath"] =  "";
		$data["latitude"] =  "";
		$data["longitude"] =  "";
	 	echo json_encode($response);
	}

?>