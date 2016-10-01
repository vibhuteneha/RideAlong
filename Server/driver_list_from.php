<?php

	  $host = '127.0.0.1';
	  $user = 'leweqnnh_ride';
	 $db = 'leweqnnh_ridealong';
	  $pass = 'Lordofrings@1234';
	  $conn;

	  
	  $con = mysqli_connect($host,$user,$pass,$db);
$data = json_decode(file_get_contents("php://input"));

$from= $data ->passgrOnlyFrm;
	//$destination= $data ->passgrTo;


ven($from,$con);

function ven($from,$con){
	$userData1=array();
	$ud=array();
	error_log("from=",0);

	
	
	//echo "from>>".$from;
	$sql="select * from driver where from_place='".$from."'";
	//echo $sql;

	$query=mysqli_query($con,$sql);
	//print_r($query);
while($row=mysqli_fetch_assoc($query)){
		//echo $row['userid'];
		
		array_push($ud,$row);
//print_r($ud);		
		}
		
	
	
	//print_r($ud);
	$Juser=json_encode($ud);
	
	$js=(string)$Juser;

$jobj = json_encode(array('driverListFrom' => $Juser), JSON_FORCE_OBJECT);

$jobj1=(string)$jobj;
//error_log(implode(',',$userData1),0);

echo $jobj1;
	return $jobj1;
	
}


  				
?>