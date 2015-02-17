<?php
    if(!isset($_SESSION["username"])){       
        if((isset($_POST['username']) && isset($_POST['password'])) &&
            $_POST['username'] != "" && $_POST['password'] != ""){
			try{ 
				$res = checkLogin($_POST['username'], $_POST['password']);
				//check if user and pass are valid
				if($res>0){
					$_SESSION['username'] = stripslashes(htmlspecialchars($res));
					echo 	'<form action="logout.php">
								<input type="submit" value="LOGOUT">
							</form>';
					$user_id = $_SESSION['username'];
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