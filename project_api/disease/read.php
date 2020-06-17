<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/disease.php';
$database = new Database();
$db = $database->getConnection();
$disease = new Disease($db);
$data = json_decode(file_get_contents("php://input"));
$disease->sname=$data->name;
$stmt = $disease->read();
$num = $stmt->rowCount();
if($num>0){
    $diseases_arr=array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);
        array_push($diseases_arr, $disease_name);
    }
    http_response_code(200);
    echo json_encode($diseases_arr);
}
else{
    http_response_code(404);
    echo json_encode(
        array("message" => "No diseases found.")
    );
}