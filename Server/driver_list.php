<?php
//header('Content-Type: application/json');

	  $host = '127.0.0.1';
	  $user = 'leweqnnh_ride';
	 $db = 'leweqnnh_ridealong';
	  $pass = 'Lordofrings@1234';
	  $conn;

	  
	  $con = mysqli_connect($host,$user,$pass,$db);
$data = json_decode(file_get_contents("php://input"));



//$passengerDetails = $data -> passgrFrom;
	$from= $data ->passgrFrom;
	$destination= $data ->passgrTo;
				//echo "from---".$passengerDetails -> from;
  				//$destination = $passengerDetails -> destination;


ven($from,$destination,$con);

function ven($from,$destination,$con){
	$userData1=array();
	error_log("from=",0);

	
	//echo "<<dest>>".$destination;
	//echo "from>>".$from;
	error_log($destination,0);
	error_log($from,0);
	
	$sql="select * from driver where from_place='".$from."' and destination='".$destination."'";
	//echo $sql;

	$query=mysqli_query($con,$sql);
	//print_r($query);
	while($row=mysqli_fetch_assoc($query)){
		//echo $row['userid'];
		$sql2="select * from users where sno='".$row['userid']."'";
		//echo $sql2;
		$query1=mysqli_query($con,$sql2);
		while($row1=mysqli_fetch_assoc($query1)){
			//print_r($row);
		array_push($userData1,$row1);	
		}
		error_log($row['userid'],0);
		
		//echo $row['userid'];
		//echo $row['name'];
		
	}
	
	
	$Juser=json_encode($userData1);
	
	$js=(string)$Juser;

$jobj = json_encode(array('driverList' => $Juser), JSON_FORCE_OBJECT);

$jobj1=(string)$jobj;
//error_log(implode(',',$userData1),0);

echo $jobj1;
	return $jobj1;
	
}

  				
?>