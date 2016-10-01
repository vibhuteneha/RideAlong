<?php
//header('Content-Type: application/json');

	  $host = '127.0.0.1';
	  $user = 'leweqnnh_ride';
	 $db = 'leweqnnh_ridealong';
	  $pass = 'Lordofrings@1234';
	  $conn;

	  
	  $con = mysqli_connect($host,$user,$pass,$db);
//$data = json_decode(file_get_contents("php://input"));
$data=file_get_contents("php://input");
$data10=explode(",",$data);
//print_r($data10);
$data11=preg_replace("/[^0-9]/","", $data10);
//print_r($data11);
//$data1=array($data);
/*
for($i=0;$i<=count($data);$i++){
	echo $data[i];	
}
*/
//$data1=data->id;

//$passengerDetails = $data -> passgrFrom;
	//$from= $data ->passgrFrom;
	//$destination= $data ->passgrTo;
				//echo "from---".$passengerDetails -> from;
  				//$destination = $passengerDetails -> destination;

				//echo "data0=".$data1[0];
foreach($data11 as $id){
	//echo $id;
	ven($id,$con);
}


function ven($id,$con){
	$userData1=array();
	error_log("from=",0);

	
	//echo "<<dest>>".$destination;
	//echo "from>>".$from;
	//error_log($destination,0);
	//error_log($from,0);
	
	$sql="select * from users where sno='".$id."'";
	//echo $sql;

	$query=mysqli_query($con,$sql);
	//print_r($query);
	while($row=mysqli_fetch_assoc($query)){
		//echo $row['userid'];
		//$sql2="select * from users where sno='".$row['userid']."'";
		//echo $sql2;
		//$query1=mysqli_query($con,$sql2);
		//while($row1=mysqli_fetch_assoc($query1)){
			//echo $row1;
		array_push($userData1,$row);	
		//}
		//error_log($row['userid'],0);
		
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