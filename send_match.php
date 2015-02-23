<html>
<head>
	<title> PaintPG - SEND MATCH </title>
</head>
<body>
	<div id="contact">
	<h1>
		Prenota un match! 
	</h1>
	<form name="send_match" action="send_match_call.php" method="post">
	<fieldset>
		<select name="day">
			<option value="">Giorno</option>
			<?php for ($day = 1; $day <= 31; $day++) { ?>
				<option value="<?php echo strlen($day)==1 ? '0'.$day : $day;?>"
				<?php if($day == date('d')) echo 'selected=true';?>>
				<?php echo strlen($day)==1 ? '0'.$day : $day; ?></option>
			<?php } ?>
		</select>
		
		<select name="month">
			<option value="">Mese</option>
				<?php for ($month = 1; $month <= 12; $month++) { ?>
					<option value="<?php echo strlen($month)==1 ? '0'.$month : $month; ?>"
					<?php if($month == date('m')) echo 'selected=true';?>>
					<?php echo strlen($month)==1 ? '0'.$month : $month; ?></option>
				<?php } ?>
		</select>
		</br></br>
		<select name="year">
			<option value="">Anno</option>
				<?php for ($year = date('Y')+5; $year >= date('Y'); $year--) { ?>
					<option value="<?php echo $year; ?>"
					<?php if($year == date('Y')) echo 'selected=true';?>>
					<?php echo $year; ?></option>
				<?php } ?>
		</select>
		</br></br>	
		<input class="input" type="submit" name="book_match" value="PRENOTA" />		
	</fieldset>
	</form>
</div>
</body>
</html>