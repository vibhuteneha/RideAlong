<?php

	  $host = '127.0.0.1';
	  $user = 'leweqnnh_ride';
	 $db = 'leweqnnh_ridealong';
	  $pass = 'Lordofrings@1234';
	  $conn;

	  
	  $con = mysqli_connect($host,$user,$pass,$db);
$data = json_decode(file_get_contents("php://input"));



$id= $data ->id;



ven($id,$con);

function ven($id,$con){
	$userData1=array();
	error_log("from=",0);

	

	$sql="Select  * from users inner join driver on users.sno=driver.userid where driver.userid='".$id."'";
	//$sql="select * from passenger where from_place='".$from."' and destination='".$destination."'";
	//echo $sql;

	$query=mysqli_query($con,$sql);
	while($row=mysqli_fetch_assoc($query)){
		
		$query1=mysqli_query($con,$sql);
		
		array_push($userData1,$row);	
		
		error_log($row['userid'],0);
		

		
	}
	
	
	$Juser=json_encode($userData1);
	
	$js=(string)$Juser;

$jobj = json_encode(array('driverDetails' => $Juser), JSON_FORCE_OBJECT);

$jobj1=(string)$jobj;
//error_log(implode(',',$userData1),0);

echo $jobj1;
	return $jobj1;
	
}

  				
?>