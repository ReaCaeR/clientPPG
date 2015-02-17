<html>
<head>
	<title>PaintPG</title>
	<link href="main.css" rel="stylesheet" type="text/css" media="screen" />
	<?php require_once("helper.php"); ?>
</head>
<body>
<div class='wrap'>
    <div class='head'><?php include("menu_top.php"); ?></div>
    <div class='bodywrap'>
        <div class='left'><p><?php echo isset($_SESSION['username']) ? 'USER_ID: ' . $_SESSION['username'] : 'NOT LOGGED IN'; ?></p></div>
        <div class='right'></div>
        <div class='center'><?php if (!isset($_SESSION["username"])) include("register.php"); ?></div>
    </div>
 </div>
</body>
</html>