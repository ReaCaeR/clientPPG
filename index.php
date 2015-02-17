<html>
<head>
	<title>PaintPG</title>
	<link href="main.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	<?php require_once("helper.php"); ?>
	<div id="menu_top">
		<?php include("menu_top.php"); ?>
	</div>
	
	<div id="body_container">
		<div id="left">
			<p><?php echo isset($_SESSION['username']) ? $_SESSION['username'] : 'NOT LOGGED IN'; ?></p>
			[FEEDS HERE]
		</div>
		<div id="right">
			<?php 
			if (!isset($_SESSION["username"]))
				include("register.php"); 
			else
			{
				?>
					<div id="in_left"> [PROVAS] </div>
					<div id="in_right"> [PROVAD] </div>
				<?php
			}
			?>
		</div>
	</div>
</body>
</html>