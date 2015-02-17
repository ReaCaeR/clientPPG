<?php
	require_once("helper.php");

	$function = "DEBUGStructTransfer";
	$params = array();
	$res = SOAPCall($function, $params);
	echo $res;
?>