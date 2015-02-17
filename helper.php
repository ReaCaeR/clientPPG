<?php

	ini_set('soap.wsdl_cache_enabled', '0'); 
	ini_set('soap.wsdl_cache_ttl', '0');
	session_start();
	$wsdl = "http://localhost:8084/PPGServer/PPGService?wsdl";
	$client = new SoapClient($wsdl, array('trace' => 1));
	
	function SOAPCall($function, $params){
		global $client;
		$res = -1;
		try{
			$res = $client->__soapCall($function, array($params));
		}
		catch (Exception $e){
			echo "PHP_SOAP_CALL_ERROR: " . $e->getMessage();
		}
		return $res->return;
	}
	
	function printLoginForm($value){
		if ($value == -1){
			echo '<form action="index.php" method="post" name="login">
				 <span style="color:red">(Username o password sbagliati)</span>
                 User:<input class="login_textarea" name="username" type="text" size="10" maxlength="15" />&nbsp;
                 Password:<input class="login_textarea" name="password" type="password" size="10" maxlength="15" />&nbsp;
                 <input name="submit" class="coloredinput" type="submit" value="LOGIN" onMouseOver="mouse_over_button(this.form.name,this.name)" onMouseOut="mouse_out_button(this.form.name,this.name)" />
                 </form>';
		}else{
			echo '<form action="index.php" method="post" name="login">
                 User:<input class="login_textarea" name="username" type="text" size="10" maxlength="15" />&nbsp;
                 Password:<input class="login_textarea" name="password" type="password" size="10" maxlength="15" />&nbsp;
                 <input name="submit" class="coloredinput" type="submit" value="LOGIN" onMouseOver="mouse_over_button(this.form.name,this.name)" onMouseOut="mouse_out_button(this.form.name,this.name)" />
                 </form>';    
		}
	}

	function checkLogin($username, $password){
		$function = "loginRequest";
		$params = array('username' => $username,
						'password' => $password);
		$res = SOAPCall($function, $params);
		return $res;
	}
?>