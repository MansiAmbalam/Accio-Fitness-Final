<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/diet.php';
$database = new Database();
$db = $database->getConnection();
$diet = new Diet($db);
$data = json_decode(file_get_contents("php://input"));
$diet->dname=$data->name;
$stmt=$diet->read();
if($data->name!=null){
$diet_arr = array(
        "Breakfast" => $diet->bf_arr,
		"Lunch" => $diet->lu_arr,
		"Dinner" => $diet->di_arr);
    http_response_code(200);
    echo json_encode($diet_arr);
}
else{
    http_response_code(404);
    echo json_encode(array("message" => "No diet chart found.")
    );
}
?>
