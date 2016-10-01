<?php

	  $host = '127.0.0.1';
	  $user = 'leweqnnh_ride';
	 $db = 'leweqnnh_ridealong';
	  $pass = 'Lordofrings@1234';
	  $conn;

	  
	  $con = mysqli_connect($host,$user,$pass,$db);
$data = json_decode(file_get_contents("php://input"));



$from= $data ->driverFrom;
	$destination= $data ->driverTo;


ven($from,$destination,$con);

function ven($from,$destination,$con){
	$userData1=array();
	error_log("from=",0);

	
	//echo "dest".$destination;
	//echo "from>>".$from;
	$sql="select * from passenger where from_place='".$from."' and destination='".$destination."'";
	//echo $sql;

	$query=mysqli_query($con,$sql);
	while($row=mysqli_fetch_assoc($query)){
		//echo $row['userid'];
		$sql2="select * from users where sno='".$row['uid']."'";
		//echo $sql2;
		$query1=mysqli_query($con,$sql2);
		while($row1=mysqli_fetch_assoc($query1)){
			//echo $row1;
		array_push($userData1,$row1);	
		}
		//error_log($row['userid'],0);
		
		//echo $row['userid'];
		//echo $row['name'];
		
	}
	
	
	$Juser=json_encode($userData1);
	
	$js=(string)$Juser;

$jobj = json_encode(array('passengerList' => $Juser), JSON_FORCE_OBJECT);

$jobj1=(string)$jobj;
//error_log(implode(',',$userData1),0);

echo $jobj1;
	return $jobj1;
	
}

  				
?>