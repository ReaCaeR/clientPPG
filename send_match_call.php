<html>
<head>
	<title>PaintPG - Send Match Result</title>
</head>
	<body>
	<?php
		require_once("helper.php");
		
		$function = "setMatch";
		$params = array('user_id' => $_SESSION['user_id'], 
						'match_date' => $_POST['year']"-"$_POST['month']"-$_POST['day']");				
		$res = SOAPCall($function, $params);
		echo $res;
		header("Refresh: 4;url=./index.php");
	?>
	</body>
</html>