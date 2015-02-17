<?php
	require_once("helper.php");

	$function = "loginRequest";
	$params = array('username' => 'vvv',
					 'password' => 'vvv'
					);
	$res = SOAPCall($function, $params);
	echo $res;
?>