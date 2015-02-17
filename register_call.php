<html>
<head>
	<title>Register call result</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		session_start();
		$function = "registrationRequest";
		$params = array('username' => $_POST['username'], 
						'password'=> $_POST['password'], 
						'passwordconfirm'=> $_POST['passwordconfirm'],
						'motto'=> $_POST['motto'],
						'colore1'=> $_POST['colore1'],
						'colore2'=> $_POST['colore2']);				
		$res = SOAPCall($function, $params);
		echo $res;
		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>