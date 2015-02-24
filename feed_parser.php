<?php
	$url = "feeds.xml";
	$xml = simplexml_load_file($url);

	function printPendingChallenge($match)
	{
		echo
		'<div class="feed">' .
			'<div class="challenge_pending">' .
				'<div class="challenge_date">' .
					$match->match_date .
				'</div>' .
				'<div class="team_looking_for_challenger">' .
					$match->squadra_1[0]->nome_squadra_1 .
					'<br/>' .
					$match->squadra_1[0]->motto_squadra_1 .
					'<br/>' .
					'<div class="accept_challenge_button">' .
					'<form name="accept" action="challenge_call.php" method="post">' .
						'<input type="hidden" name="match_id" value='.$match->match_id.'>' .
						'<input type="submit" value="ACCETTA LA SFIDA!"/>' .
					'</form>' .

					'</div>' .
				'</div>' .
			'</div>' .
		'</div>';
		
	}

	function printAcceptedChallenge($match)
	{
		echo
		'<div class="feed">' .
			'<div class="challenge_accepted">' .
				'<div class="challenge_date">' .
					$match->match_date .
				'</div>' .
				'<div class="teams">' .
					'<div class="team_1">' .
						$match->squadra_1[0]->nome_squadra_1 .
						'<br/>' .
						$match->squadra_1[0]->motto_squadra_1 .
					'</div>' .
					'<div class="team_2">' .
						$match->squadra_2[0]->nome_squadra_2 .
						'<br/>' .
						$match->squadra_2[0]->motto_squadra_2 .
					'</div>' .
				'</div>' .
			'</div>' .
		'</div>';
	}

	function printEndedChallenge($match)
	{
		echo
		'<div class="feed">' .
			'<div class="challenge_ended">' .
				'<div class="challenge_date">' .
					$match->match_date .
				'</div>' .
				'<div class="teams">' .
					'<div class="team_1">' .
						$match->squadra_1[0]->nome_squadra_1 .
						'<br/>' .
						$match->squadra_1[0]->motto_squadra_1 .
						'<br/>' .
						$match->match_results[0]->match_results_1 .
					'</div>' .
					'<div class="team_2">' .
						$match->squadra_2[0]->nome_squadra_2 .
						'<br/>' .
						$match->squadra_2[0]->motto_squadra_2 .
						'<br/>' .
						$match->match_results[0]->match_results_2 .
					'</div>' .
				'</div>' .
			'</div>' .
		'</div>';
	}

	foreach($xml->match as $match)
	{
		if($match->challenge_status == 'PENDING')
		{
			printPendingChallenge($match);
		} 
		else if ($match->challenge_status == 'ACCEPTED')
		{
			printAcceptedChallenge($match);
		}
		else if ($match->challenge_status == 'ENDED')
		{
			printEndedChallenge($match);
		}
	}
?>