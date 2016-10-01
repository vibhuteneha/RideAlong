<?php

class DBOperations{

	 private $host = '127.0.0.1';
	 private $user = 'leweqnnh_ride';
	 private $db = 'leweqnnh_ridealong';
	 private $pass = 'Lordofrings@1234';
	 private $conn;

public function __construct() {

	$this -> conn = new PDO("mysql:host=".$this -> host.";dbname=".$this -> db, $this -> user, $this -> pass);

}


 public function insertData($name,$email,$password,$phone){

 	$unique_id = uniqid('', true);
    $hash = $this->getHash($password);
    $encrypted_password = $hash["encrypted"];
	$salt = $hash["salt"];

 	$sql = 'INSERT INTO users SET unique_id =:unique_id,name =:name,
    email =:email,encrypted_password =:encrypted_password,salt =:salt,created_at = NOW(),phone=:phone';

 	$query = $this ->conn ->prepare($sql);
 	$query->execute(array('unique_id' => $unique_id, ':name' => $name, ':email' => $email,
     ':encrypted_password' => $encrypted_password, ':salt' => $salt,':phone'=>$phone));

    if ($query) {
        
        return true;

    } else {

        return false;

    }
 }


 public function insertDriverData($userid,$car_no,$car_model,$from_place,$destination,$date){

 	$sql = 'INSERT INTO driver SET userid =:userid,car_no =:car_no,
    car_model =:car_model,from_place=:from_place,destination =:destination,date =:date';

echo "userid== ".$userid;
 	$query = $this ->conn ->prepare($sql);
 	$query->execute(array('userid' =>$userid, ':car_no' => $car_no, ':car_model' => $car_model,
      ':from_place' => $from_place,':destination' => $destination, ':date' => $date));
	  
	 /*
	  mysql_query("insert into driver (userid,car_no,car_model,from_place,destination,date) values ('$userid','$car_no','$car_model','$from_place','$destination','$date') ") or die("mysql Driver query error");
*/
    if ($query) {
        
        return true;

    } else {

        return false;

    }
 }
 
  public function insertPassengerData($uid,$from_place,$destination,$leavingDate){

 	$sql = 'INSERT INTO passenger SET uid =:uid,from_place =:from_place,
    destination =:destination,date =:date';

echo "userid== ".$userid;
 	$query = $this ->conn ->prepare($sql);
	echo "userid-".$userid;
	echo "from-".$from;
	echo "destination=".$destination;
	echo "leavingDate=".$leavingDate;
 	$query->execute(array('uid' =>$uid,
    ':from_place' => $from_place,':destination' => $destination,':date' => $leavingDate)) or die("ps_error");
	 
//mysql_query("INSERT  INTO passenger(uid,from_place,destination,date) VALUES('$userid','$from','$destination','$leavingDate')") or die("error pass");

//$query->execute(array('userid' =>11,
     // ':from_place' => "sss",':destination' =>"fff",':date' =>"22")) or die("ps_error");	 

    if ($query) {
        echo "passenger query successful";
        return true;

    } else {
echo "passenger query UNsuccessful";
        return false;

    }
 }

 public function checkLogin($email, $password) {

    $sql = 'SELECT * FROM users WHERE email = :email';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':email' => $email));
    $data = $query -> fetchObject();
    $salt = $data -> salt;
    $db_encrypted_password = $data -> encrypted_password;

    if ($this -> verifyHash($password.$salt,$db_encrypted_password) ) {


        $user["id"] = $data -> sno;
		$user["name"] = $data -> name;
        $user["email"] = $data -> email;
        $user["unique_id"] = $data -> unique_id;
        return $user;

    } else {

        return false;
    }

 }
 
  public function driversList($from_place,$destination) {
 $userTable =array();
    $sql = 'select * from driver where from_place = :from_place and destination = :destination';
    $query = $this -> conn -> prepare($sql);
	echo '==from_place==' . $from_place;
    $query -> execute(array(':from_place' => $from_place,':destination' => $destination)) ;
	
$data = $query -> fetchAll();
  
print_r($data);
	//echo "data==".  $data;
   
	
foreach($data as $row) {
    $id = $row['userid'];
    echo "id>==>>".$id;
	
	
	 $sql3= 'select * from users where sno=:id';
    $query3 = $this -> conn -> prepare($sql3);
	
    $query3-> execute(array(':id' => $id)) ;
	$data2= $query3 -> fetchAll(PDO::FETCH_ASSOC);
	
	echo " data2==";
	print_r($data2);
	foreach($data2 as $row12){
			array_push($userTable,$row12);
		
	}
	

	
	
	
}
 echo "userTable";
   print_r($userTable);


       // $driverDetails["uid"] = $data -> userid;
		//echo "uid=="
		error_log("respose".$userTable);
        return $userTable;

 }
 
  public function driversList_From($from_place){
	  $sql = 'select * from driver where from_place = :from_place';
    $query = $this -> conn -> prepare($sql);
	echo '==from_place==' . $from_place;
    $query -> execute(array(':from_place' => $from_place)) ;
	
$data = $query -> fetchAll();

return $data ;
	 
 }
 
 //===================================List of passengers starts here ================
 
  public function passengersList($from_place,$destination) {
$userTable =array();
    $sql = 'select * from passenger where from_place = :from_place and destination = :destination';
    $query = $this -> conn -> prepare($sql);
	echo '==from_place==' . $from_place;
    $query -> execute(array(':from_place' => $from_place,':destination' => $destination)) ;
	
$data = $query -> fetchAll();



    
print_r($data);
	echo "data==".  $data;
   
	
	
foreach($data as $row) {
    $id = $row['userid'];
    echo "id>==>>".$id;
	
	
	 $sql3= 'select * from users where sno=:id';
    $query3 = $this -> conn -> prepare($sql3);
	
    $query3-> execute(array(':id' => $id)) ;
	$data2= $query3 -> fetchAll(PDO::FETCH_ASSOC);
	
	echo " data2==";
	print_r($data2);
	
	foreach($data2 as $row12){
			array_push($userTable,$row12);
		
	}
	
	
	
}
 echo "userTable";
   print_r($userTable);


        $driverDetails["uid"] = $data -> userid;
		echo "uid==".$driverDetails -> uid;

        return $userTable;

 }
 

 
  public function passengersList_From($from_place){
	  $sql = 'select * from passenger where from_place = :from_place';
    $query = $this -> conn -> prepare($sql);
	echo '==from_place==' . $from_place;
    $query -> execute(array(':from_place' => $from_place)) ;
	
$data = $query -> fetchAll();

return $data ;
	 
 }


//===================================List of passengers ends here ================
 public function changePassword($email, $password){


    $hash = $this -> getHash($password);
    $encrypted_password = $hash["encrypted"];
    $salt = $hash["salt"];

    $sql = 'UPDATE users SET encrypted_password = :encrypted_password, salt = :salt WHERE email = :email';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array(':email' => $email, ':encrypted_password' => $encrypted_password, ':salt' => $salt));

    if ($query) {
        
        return true;

    } else {

        return false;

    }

 }

 public function checkUserExist($email){

    $sql = 'SELECT COUNT(*) from users WHERE email =:email';
    $query = $this -> conn -> prepare($sql);
    $query -> execute(array('email' => $email));

    if($query){

        $row_count = $query -> fetchColumn();

        if ($row_count == 0){

            return false;

        } else {

            return true;

        }
    } else {

        return false;
    }
 }

 public function getHash($password) {

     $salt = sha1(rand());
     $salt = substr($salt, 0, 10);
     $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "encrypted" => $encrypted);

     return $hash;

}



public function verifyHash($password, $hash) {

    return password_verify ($password, $hash);
}
}




