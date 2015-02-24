<html>
<head>
	<title>ADMIN PANEL</title>
	<link href="main.css" rel="stylesheet" type="text/css" media="screen" />
	<?php require_once("helper.php"); ?>
</head>
<body>
	<div id='adminpanel'>
	<form name="adminpanel" action="admin_panel_call.php" method="post">
		<fieldset>
			<label for="username">Nome Squadra:</label>
			<input class="input" type="text" name="username" placeholder="Inserire nome squadra" />
			</br>
			<label for="username"> Conferma Nome Squadra:</label>
			<input class="input" type="text" name="confirm_username" placeholder="Reinserire nome squadra" />
			</br>
			</br>
			<input class="input" type="submit" name="send_admin" value="Promuovi ad Admin" />
			</br>
			</br>
			<input class="input" type="submit" name="send_delete" value="Rimuovi Squadra" />	
		</fieldset>
		</br>
		NB: Le azioni eseguite sono permanenti! 
	</div>
</body>
</html>