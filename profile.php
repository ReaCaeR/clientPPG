<html>
<head>
	<title> PROFILO SQUADRA </title>
</head>

<body>
	<div id='profilename'>
		<?php 
			echo $_SESSION['username'];
			if ($_SESSION['admin'] > 0) {
			//controlli username
			}
			
		?>
	</div>
	<div id='profileadm'>
	<?php
		if ($_SESSION['admin'] > 0)
			echo ('ADMIN LVL' . $_SESSION['admin']);
		else
			echo ('SQUADRA REGISTRATA');
	?>
	</div> 
	<div id='profileimg'>
		IMMAGINE SQUADRA
	</div> 
	<div id='profilemotto'>
		MOTTO <?php echo $_SESSION['user_id']; ?>
	</div>
	<div id='nextmatches'>
		PROSSIME PARTITE
	</div>
</body> 
</html>