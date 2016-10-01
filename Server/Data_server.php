<?php
$con=mysqli_connect("127.0.0.1","leweqnnh_ride","Lordofrings@1234","leweqnnh_ridealong") or die ("unable to connect");

?>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Data Server</title>
<style>
body{
margin:0 auto;
}

</style>
</head>

<body >
<?php
$data = mysqli_query($con,"SELECT * from users") or die("unable to query");
echo "<h1> DATA </h1>";
echo "<table border><tr>";
while($data1=mysqli_fetch_array($data)){
	echo "<tr><td>".$data1['sno']."</td><td>".$data1['name']."</td><td>".$data1['email']."</td><td>".$data1['created_at']."</td></tr>";
}
echo "</tr></table>";

?>

</body>
</html>