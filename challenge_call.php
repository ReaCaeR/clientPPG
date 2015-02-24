<html>
<head>
	<title>PaintPG - Accept Challenge</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		$function = "acceptChallenge";
		$params = array('id_sfidante' => $_SESSION['user_id'], 
						'match_id' => $_POST['match_id']);				
		$res = SOAPCall($function, $params);
		if ($res == 0)
			echo 'SFIDA ACCETTATA';
		else
			echo 'ERRORE' . $res;

		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>