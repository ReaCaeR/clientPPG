<?php
	require_once('helper.php');

    if(!isset($_SESSION["username"])){       
        if((isset($_POST['username']) && isset($_POST['password'])) &&
            $_POST['username'] != "" && $_POST['password'] != ""){
			try{ 
				$function = 'loginRequest';
				$params = array('username' => $_POST['username'], 
								'password' => $_POST['password']);
				$user_id = SOAPCall($function, $params);
				$user_id = stripslashes(htmlspecialchars($user_id));
				//check if user and pass are valid
				if($user_id>0){
					$_SESSION['username'] = $user_id;
					echo 	'<form action="logout.php">
								<input type="submit" value="LOGOUT">
							</form>';
					/*$admin = isAdmin($_SESSION['username']);
					if($admin > 0){
						$_SESSION['admin'] = $admin;
					}*/
				}
				else{
					//Invalid username or password.
					printLoginForm(-1);
				}
			}catch (Exception $e){
				echo $e->getMessage();
			}
        }else{
            //normal login
            printLoginForm(0);
        }
    }else{
        echo 	'<form action="logout.php">
					<input type="submit" value="LOGOUT">
				</form>';
    }
?>