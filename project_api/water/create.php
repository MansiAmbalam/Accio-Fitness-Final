<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../connect/database.php';
include_once '../objects/water.php';
 include_once '../objects/user.php';
$database = new Database();
$db = $database->getConnection();
$water = new Water($db);
$user=new User($db); 
$data = json_decode(file_get_contents("php://input"));
if( !empty($data->uid) &&
    !empty($data->water_amount)
){
	$user->id=$data->uid;
	if(!$user->exists())
	{
		http_response_code(400);
		echo json_encode(array("message" => "User not found."));
	}
	else
	{
    $water->uid = $data->uid;
    $water->water_date = date('Y-m-d');
    $water->water_amount = $data->water_amount;
    if($water->create()){
        http_response_code(201);
        echo json_encode(array("message" => "water record was created."));
    }
    else{
        http_response_code(503);
        echo json_encode(array("message" => "Unable to create water record."));
    }
	}
}
else{
    http_response_code(400);
    echo json_encode(array("message" => "Unable to create water record. Data is incomplete."));
}
?>