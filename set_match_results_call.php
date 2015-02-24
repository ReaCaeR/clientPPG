<?php
	require_once("helper.php");
	
	$function = "modResults";
	$params = array('r1' => $_POST['r1'], 
					'r2' => $_POST['r2'],
					'match_id' => $_POST['match_id'],
					);				
	$res = SOAPCall($function, $params);
	echo $res;
	header("Refresh: 4;url=./index.php");
?>
