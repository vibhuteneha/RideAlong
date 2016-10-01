<?php

require_once 'Functions.php';

$fun = new Functions();


if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
  $data = json_decode(file_get_contents("php://input"));

  if(isset($data -> operation)){

  	$operation = $data -> operation;

  	if(!empty($operation)){

  		if($operation == 'register'){

  			if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> name) 
  				&& isset($data -> user -> email) && isset($data -> user -> password)){

  				$user = $data -> user;
  				$name = $user -> name;
  				$email = $user -> email;
				$phone = $user -> phone;
  				$password = $user -> password;

          if ($fun -> isEmailValid($email)) {
            
            echo $fun -> registerUser($name, $email, $password,$phone);

          } else {

            echo $fun -> getMsgInvalidEmail();
          }

  			} else {

  				echo $fun -> getMsgInvalidParam();

  			}

  		}else if ($operation == 'login') {

        if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> email) && isset($data -> user -> password)){

          $user = $data -> user;
          $email = $user -> email;
          $password = $user -> password;

          echo $fun -> loginUser($email, $password);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      } 

else if($operation == 'driver_travel_info'){

				$driverDetails = $data -> driverDetails;
				
  				$userid = $driverDetails -> userId;
				echo "dd---".$driverDetails -> car_no;
  				$car_no = $driverDetails -> car_no;
  				$car_model = $driverDetails -> carModel;
				$from_place = $driverDetails -> from_place;
				$destination = $driverDetails -> destination;
				$LeavingDate = $driverDetails -> leavingDate;

         
            
            echo $fun -> registerDriver($userid,$car_no,$car_model,$from_place,$destination,$LeavingDate);

          

  			

  		}
		
		
		else if($operation == 'drivers_list'){

  				$passengerDetails = $data -> passengerDetails;

				$from= $passengerDetails -> from;
				echo "from---".$passengerDetails -> from;
  				$destination = $passengerDetails -> destination;
error_log("request==".$passengerDetails);
echo $fun -> driversList($from,$destination);
          


  		}
		
else if($operation == 'drivers_list_From'){

   $passengerDetails = $data -> passengerDetails;

    $from= $passengerDetails -> from;
     echo "from---".$passengerDetails -> from;

     echo $fun -> driversList_From($from);

  		}
		
		
 else if($operation == 'passenger_list'){

  				$driverDetails = $data -> passengerDetails;

				$from= $driverDetails -> from;
				echo "from---".$driverDetails -> from;
  				$destination = $driverDetails -> destination;

echo $fun -> passengersList($from,$destination);
          


  		}
		
else if($operation == 'passenger_From'){

   $passengerDetails = $data -> driverDetails;

    $from= $driverDetails -> from;
     echo "from---".$driverDetails -> from;

     echo $fun -> passengersList_From($from);

  		}
		
		
else if($operation == 'passenger_travel_info'){

  				$passengerDetails = $data -> passengerDetails;
				
  				$userid = $passengerDetails -> userId;
				
				$from= $passengerDetails -> from;
				echo "from---".$passengerDetails -> from;
  				$destination = $passengerDetails -> destination;
  				$leavingDate = $passengerDetails -> leavingDate;

            echo $fun -> registerPassenger($userid,$from,$destination,$leavingDate);


  		}

else if ($operation == 'chgPass') {

        if(isset($data -> user ) && !empty($data -> user) && isset($data -> user -> email) && isset($data -> user -> old_password) 
          && isset($data -> user -> new_password)){

          $user = $data -> user;
          $email = $user -> email;
          $old_password = $user -> old_password;
          $new_password = $user -> new_password;

          echo $fun -> changePassword($email, $old_password, $new_password);

        } else {

          echo $fun -> getMsgInvalidParam();

        }
      }

  	}else{

  		
  		echo $fun -> getMsgParamNotEmpty();

  	}
  } else {

  		echo $fun -> getMsgInvalidParam();

  }
} else if ($_SERVER['REQUEST_METHOD'] == 'GET'){


  echo "<h1>Lewebev RideAlong API</h1>";

}

