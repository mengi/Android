<?
	include 'db_connect.php';

	if (isset($_POST['masaid']) && isset($_POST['siparisid'])) {
        $MasaID = mysql_escape_string($_POST['masaid']);
        $SiparisID = mysql_escape_string($_POST['siparisid']);

        list($siparisid, $aktif) = mysql_fetch_array(mysql_query("SELECT id, aktif FROM siparis WHERE masa='$MasaID' AND aktif='1'"));
        if ($SiparisID == $siparisid) {

        	if ($aktif == "1") {

	        	$result = mysql_query("SELECT urunadi, adet, tutar, siparisdurum FROM siparishareket WHERE masaid='$MasaID' AND siparisid='$SiparisID' AND durum='0'");

	        	if (mysql_num_rows($result) > 0) {

		            $response["siparishereketler"] = array();
		            
		            while ($row = mysql_fetch_array($result)) {
		                $product = array();
		                $product["urunadi"] = $row["urunadi"];
		                $product["adet"] = $row["adet"];
		                $product["tutar"] = $row["tutar"];
		                $product["siparisdurum"] = $row["siparisdurum"];

		                array_push($response["siparishereketler"], $product);
		            }

		            $response["success"] = 1;
		            $response["message"] = "Başarılı";
		            echo json_encode($response);

		        } else if(mysql_num_rows($result) == 0) {
		        	$response["success"] = 2;
		            $response["message"] = "Mutfakta Sipariş Bulunmuyor !!!";
		            echo json_encode($response);
		        } else {

		            $response["success"] = 0;
		            $response["message"] = "Başarısız";
		            echo json_encode($response);
		        }

	        } else {
		        $response["success"] = 0;
		        $response["message"] = "no no no ";
		        echo json_encode($response);
	        }

        } else {
        	$response["success"] = 0;
	        $response["message"] = "no no no ";
	        echo json_encode($response);
        }

    } else {
        $response["success"] = 0;
        $response["message"] = "no no no ";
        echo json_encode($response);
    }
?>