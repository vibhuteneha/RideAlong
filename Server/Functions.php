<?php

require_once 'DBOperations.php';

class Functions{

private $db;

public function __construct() {

      $this -> db = new DBOperations();

}


public function registerUser($name, $email, $password,$phone) {

	$db = $this -> db;

	if (!empty($name) && !empty($email) && !empty($password)) {

  		if ($db -> checkUserExist($email)) {

  			$response["result"] = "failure";
  			$response["message"] = "User Already Registered !";
  			return json_encode($response);

  		} else {

  			$result = $db -> insertData($name, $email, $password,$phone);

  			if ($result) {

				  $response["result"] = "success";
  				$response["message"] = "User Registered Successfully !";
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "failure";
  				$response["message"] = "Registration Failure";
  				return json_encode($response);

  			}
  		}					
  	} else {

  		return $this -> getMsgParamNotEmpty();

  	}
}


 public function registerDriver($userid,$car_no,$car_model,$from_place,$destination,$date) {

	$db = $this -> db;

	//if (!empty($userid) && !empty($car_no) && !empty($car_model)) {
		/*
if ($db -> checkDriverExist($userid)) {

  			$response["result"] = "failure";
  			$response["message"] = "Passenger Already Registered !";
  			return json_encode($response);

  		} else {
  		
*/
  			$result = $db -> insertDriverData($userid,$car_no,$car_model,$from_place,$destination,$date);

  			if ($result) {

				  $response["result"] = "Dsuccess";
  				$response["message"] = "Driver Registered Successfully !";
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "Dfailure";
  				$response["message"] = "Driver Register Failure";
  				return json_encode($response);

  			}
		//}				
  	/*} 
	
	else {

  		return $this -> getMsgParamNotEmpty();

  	}
	*/
	
}


 public function registerPassenger($userid,$from,$destination,$leavingDate) {

	$db = $this -> db;

	//if (!empty($userid) && !empty($car_no) && !empty($car_model)) {

  		

  			$result = $db -> insertPassengerData($userid,$from,$destination,$leavingDate);

  			if ($result) {

				  $response["result"] = "Psuccess";
  				$response["message"] = "Passenger Registered Successfully !";
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "Pfailure";
  				$response["message"] = "Passenger Register Failure";
  				return json_encode($response);

  			}
  							
  	/*} 
	
	else {

  		return $this -> getMsgParamNotEmpty();

  	}
	*/
	
}

public function loginUser($email, $password) {

  $db = $this -> db;

  if (!empty($email) && !empty($password)) {

    if ($db -> checkUserExist($email)) {

       $result =  $db -> checkLogin($email, $password);


       if(!$result) {

        $response["result"] = "failure";
        $response["message"] = "Invaild Login Credentials";
        return json_encode($response);

       } else {

        $response["result"] = "success";
        $response["message"] = "Login Successful";
        $response["user"] = $result;
        return json_encode($response);

       }

    } else {

      $response["result"] = "failure";
      $response["message"] = "Invaild Login Credentials";
      return json_encode($response);

    }
  } else {

      return $this -> getMsgParamNotEmpty();
    }

}

public function driversList($from_place,$destination) {

  $db = $this -> db;


  			$result = $db -> driversList($from_place,$destination);

  			if ($result) {

				  $response["result"] = "DLsucces list Successfully !";

				  $response["user"] = $result;
				  
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "DLfailure";
  				$response["message"] = "Driver listRetrival Failure";
  				return json_encode($response);

  			}

}

public function driversList_From($from_place) {

  $db = $this -> db;


  			$result = $db -> driversList_From($from_place);

  			if ($result) {

				  $response["result"] = "DLsucces list Successfully !";

				  $response["user"] = $result;
				  
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "DLfailure";
  				$response["message"] = "Driver listRetrival Failure";
  				return json_encode($response);

  			}

}


public function passengersList($from_place,$destination) {

  $db = $this -> db;


  			$result = $db -> passengersList($from_place,$destination);

  			if ($result) {

				  $response["result"] = "DLsucces list Successfully !";

				  $response["user"] = $result;
				  
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "DLfailure";
  				$response["message"] = "Driver listRetrival Failure";
  				return json_encode($response);

  			}

}

public function passengersList_From($from_place) {

  $db = $this -> db;


  			$result = $db -> passengersList_From($from_place);

  			if ($result) {

				  $response["result"] = "DLsucces list Successfully !";

				  $response["user"] = $result;
				  
  				return json_encode($response);
  						
  			} else {

  				$response["result"] = "DLfailure";
  				$response["message"] = "Driver listRetrival Failure";
  				return json_encode($response);

  			}

}

public function changePassword($email, $old_password, $new_password) {

  $db = $this -> db;

  if (!empty($email) && !empty($old_password) && !empty($new_password)) {

    if(!$db -> checkLogin($email, $old_password)){

      $response["result"] = "failure";
      $response["message"] = 'Invalid Old Password';
      return json_encode($response);

    } else {


    $result = $db -> changePassword($email, $new_password);

      if($result) {

        $response["result"] = "success";
        $response["message"] = "Password Changed Successfully";
        return json_encode($response);

      } else {

        $response["result"] = "failure";
        $response["message"] = 'Error Updating Password';
        return json_encode($response);

      }

    } 
  } else {

      return $this -> getMsgParamNotEmpty();
  }

}

public function isEmailValid($email){

  return filter_var($email, FILTER_VALIDATE_EMAIL);
}

public function getMsgParamNotEmpty(){


  $response["result"] = "failure";
  $response["message"] = "Parameters should not be empty !";
  return json_encode($response);

}

public function getMsgInvalidParam(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Parameters";
  return json_encode($response);

}

public function getMsgInvalidEmail(){

  $response["result"] = "failure";
  $response["message"] = "Invalid Email";
  return json_encode($response);

}

}
