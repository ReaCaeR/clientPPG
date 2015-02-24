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
        <div class='left'><?php echo isset($_SESSION['user_id']) ? include("profile.php") : 'NOT LOGGED IN'; ?></div>
        <div class='right'><?php if(isset($_SESSION['user_id'])) include("send_match.php");?></div>
        <div class='center'>
        	<?php 
        		if (!isset($_SESSION["user_id"])){
        			include("register.php");
        		}
        		else{
        			//include("send_post.php");
        			include("feeds.php");
        		}
    		?>
        </div>
    </div>
 </div>
</body>
</html>