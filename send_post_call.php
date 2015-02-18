<html>
<head>
	<title>Send post result</title>
</head>
<body>

<?php
require_once("helper.php");

try{
	$function = "setPost";
		$params = array('post_body' => $_POST['post_body'], 
						'user_id'=> $_POST['user_id']);				
		$res = SOAPCall($function, $params);
		echo $res;
    echo "<h2>Invio: " . $res->return . "</h2>";
	//Richiama il webmethod updatePostXML
	$function2 = "updatePostXML";
	$params = array();
    include_once(SOAPCall($function2, $params));
    header("location: index.php");
} catch (Exception $e) {
	echo $e->getMessage();
}	
?>
</body>
</html>