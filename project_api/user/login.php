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
    !empty($data->email) &&
    !empty($data->pwd)
){
    $user->email = $data->email;
	$user->pwd = $data->pwd;
	$stmt = $user->login();
	$num = $stmt->rowCount();
    if($num!=0){
        http_response_code(200);
        echo json_encode("User logged in.");
    }
    else{
        http_response_code(503);
        echo json_encode("Unable to login. Invalid data.");
    }
}
else{
    http_response_code(400);
    echo json_encode("Unable to login. Data is incomplete.");
}
?>
