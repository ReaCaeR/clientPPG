<html>
<head>
	<title> PaintPG - Register </title>
	
	<link href="main.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	<div id="contact">
	<h1>
		Effettua la registrazione
	</h1>
	<form name="register" action="register_call.php" method="post">
		<fieldset>
			<label for="username">Nome Squadra:</label>
			<input class="input" type="text" name="username" placeholder="Inserire nome squadra" />
			</br>
			<label for="password">Password:</label>
			<input class="input" type="password" name="password" placeholder="Inserire una password" />		
			</br>
			<label for="password">Conferma:</label>
			<input class="input" type="password" name="passwordconfirm" placeholder="Inserire una password" />
			</br>
			<label for="motto">Motto:</label></br>
			<textarea rows="5" cols="20" name="motto" wrap="soft" placeholder="Inserisci motto squadra"></textarea>
			</br>
			<label for="colore1">Colore Primario:</label>
			<select name="colore1">
				<option value="000000">Nero</option>
				<option value="FFFFFF">Bianco</option>
				<option value="00CC00">Verde</option>
				<option value="0000CC">Blu</option>
				<option value="CC0000" selected="selected">Rosso</option>
				<option value="CCCC00">Giallo</option>
			</select>
			</br>
			<label for="colore2">Colore Secondario:</label>
			<select name="colore2">
				<option value="000000">Nero</option>
				<option value="FFFFFF" selected="selected">Bianco</option>
				<option value="00CC00">Verde</option>
				<option value="0000CC">Blu</option>
				<option value="CC0000">Rosso</option>
				<option value="CCCC00">Giallo</option>
			</select>
			</br>
			<input class="input" type="submit" name="send_registration" value="Registrati" />		
		</fieldset>
	</form>
</div>
</body>
</html>