<html>
<head>
	<title>Modify results Call</title>
</head>

<body>
<?php
	require_once("helper.php");
		if (isset($_POST['send_admin'])){
			$function = "modResults";
			$params = array('r1' => $_POST['r1'],
							'r2' => $_POST['r2'],
							'match_id' => $_POST['modify']);	
			$res = SOAPCall($function, $params);
			echo $res;
		}
		else{
			echo 'ERRORE';
		}
		header("Refresh: 4;url=./index.php");
?>
</body>
</html>