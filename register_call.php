<html>
<head>
	<title>Register call result</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		$function = "registrationRequest";
		$params = array('username' => $_POST['username'], 
						'password'=> $_POST['password'], 
						'passwordconfirm'=> $_POST['passwordconfirm'],
						'motto'=> $_POST['motto'],
						'color1'=> $_POST['color1'],
						'color2'=> $_POST['color2']);				
		$res = SOAPCall($function, $params);
		echo $res;
		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>