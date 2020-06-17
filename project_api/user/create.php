<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../connect/database.php';
include_once '../objects/user.php';
$database = new Database();
$db = $database->getConnection();
$user = new User($db);
$data = json_decode(file_get_contents("php://input"));
if(
    !empty($data->name) &&
    !empty($data->email) &&
    !empty($data->bdate) &&
    !empty($data->pwd) &&
	!empty($data->contact)
){
	if(!filter_var($data->email, FILTER_VALIDATE_EMAIL) )
	{
		http_response_code(400);
        echo json_encode("Unable to create user. Invalid email.");
	}
	else if($user->emailExists($data->email))
	{
		http_response_code(400);
        echo json_encode("Unable to create user. User already exists.");
	}
	else if(!($data->pwd==$data->cpwd))
	{
		http_response_code(400);
        echo json_encode("Unable to create user. Invalid password.");
	}
	else if(!preg_match('/^\d{10}$/', $data->contact))
	{
		http_response_code(400);
        echo json_encode("Unable to create user. Invalid contact number.");
	}
	else
	{
    $user->name = $data->name;
    $user->email = $data->email;
    $user->bdate = $data->bdate;
	$user->pwd = $data->pwd;
    $user->contact = $data->contact;
    if($user->create()){
        http_response_code(200);
        echo json_encode(array("message" => "User was created."));
    }
    else{
        http_response_code(503);
        echo json_encode(array("message" => "Unable to create user."));
    }
	}
}
else{
    http_response_code(400);
    echo json_encode(array("message" => "Unable to create user. Data is incomplete."));
}
?>