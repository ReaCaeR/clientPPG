<html>
<head>
	<title> PROFILO SQUADRA </title>
</head>

<body>
	<div id='profilename'><?php echo $_SESSION['username']; ?></div>
	<div id='profileimg'>IMMAGINE SQUADRA <?php /*echo $_SESSION['admin'];*/ ?></div> 
	<div id='profilemotto'>MOTTO <?php echo $_SESSION['user_id']; ?></div>
	<div id='nextmatches'>PROSSIME PARTITE</div>
</body> 
</html>