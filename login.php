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
					$_SESSION['user_id'] = $user_id;
					$function2 = 'getUsername';
					$params= array('user_id' => $_SESSION['user_id']);
					$_SESSION['username'] = SOAPCall($function2, $params);
					$function3 = 'isAdmin';
					$admin = SOAPCall($function3, $params);
					if($admin > 0)
						$_SESSION['admin'] = $admin;
					echo 	'<form action="logout.php">
								<input type="submit" value="LOGOUT">
							</form>';
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