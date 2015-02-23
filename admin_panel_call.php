<html>
<head>
	<title>Admin Panel Call</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		if ($_POST['username'] == $_POST['confirm_username']){
		$user_id = getUserid($_POST['username']);
		
			if ($_POST['send_admin']){
				$function = "setAdmin";
				$params = array('user_id' => $user_id);	
				$res = SOAPCall($function, $params);
				echo $res;
			}
			else if ($_POST['send_delete']){
				$function = "rmvUser";
				$params = array('user_id' => $user_id);	
				$res = SOAPCall($function, $params);
				echo $res;
			}
		}
		else{
			echo 'I NOMI NON CORRISPONDONO!';
		}
		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>