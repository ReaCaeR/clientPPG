<?php
	require_once("helper.php");

	$function = "loginRequest";
	$params = array('username' => 'vvvzzz',
					 'password' => 'vvv'
					);
	$res = SOAPCall($function, $params);
	echo $res;
?>