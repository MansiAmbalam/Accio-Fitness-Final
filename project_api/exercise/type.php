<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/exercise.php';
$database = new Database();
$db = $database->getConnection();
$exercise = new Exercise($db);
$data = json_decode(file_get_contents("php://input"));
$exercise->exType = $data->exType;
$stmt=$exercise->fetchImage();
if($exercise->exType!=null){
    http_response_code(200);
    echo json_encode($exercise->path);
}
else{
    http_response_code(404);
    echo json_encode("No id found.");
}
?>