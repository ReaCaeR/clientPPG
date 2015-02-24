<?php
	require_once("helper.php");
?>
<html>
<head>
</head>
<body>

<?php
	SOAPCall("runTest", array());
?>

<!--
		<div class='flag' style='background-color:<?php echo getColor1($_SESSION['username']);?>'></br></br></div>
		<div class='flag' style='background-color:<?php echo getColor2($_SESSION['username']);?>'></br></br></div>
-->
</body>