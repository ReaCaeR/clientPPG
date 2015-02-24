<html>
<head>
	<title>PaintPG - Accept Challenge</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		$function = "acceptChallenge";
		$params = array('username' => $_SESSION['username'], 
						'match_id' => $_POST['submit']);				
		$res = SOAPCall($function, $params);
		if ($res == 0)
			echo 'SFIDA ACCETTATA':
		else
			echo 'ERRORE';

		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>