<html>
<head>
	<title> PROFILO SQUADRA </title>
	<link href="main.css" rel="stylesheet" type="text/css" media="screen" />
</head>

<body>
	<div id='profilename'>
		<h2><?php echo $_SESSION['username'];?></h2>
	</div>
	</br>
	<div id='profileadm'>
	<?php
		if ($_SESSION['admin'] > 0)
			echo ('ADMIN LVL' . $_SESSION['admin']);
		else
			echo ('SQUADRA REGISTRATA');
	?>
	</div> 
	</br>
	<div id='profileimg'>
		<div class='flag' style='background-color:<?php echo getColor1($_SESSION['username']);?>'></br></br></div>
		<div class='flag' style='background-color:<?php echo getColor2($_SESSION['username']);?>'></br></br></div>
	</div> 
	</br>
	<div id='profilemotto'>
		<?php echo getMotto($_SESSION['username']); ?>
	</div>
	</br>
	<?php
		if ($_SESSION['admin'] > 0)
		include("admin_panel.php");
	?>
</body> 
</html>