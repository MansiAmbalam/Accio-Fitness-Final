<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/disease.php';
$database = new Database();
$db = $database->getConnection();
$disease = new Disease($db);
$data = json_decode(file_get_contents("php://input"));
$disease->disease_name=$data->dname;
$symptoms=$data->symName;
$disease->sym_names=explode(",",$symptoms);
if($disease->updateDiseaseMap()==0){
    http_response_code(200);
    echo json_encode("Update Success!");
}
else{
    http_response_code(404);
    echo json_encode(
        array("Error in entry.")
    );
}