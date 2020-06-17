<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/disease.php';
include_once '../objects/diet.php';
$database = new Database();
$db = $database->getConnection();
$disease = new Disease($db);
$diet = new Diet($db);
$data = json_decode(file_get_contents("php://input"));
$disease->disease_name=$data->dname;
$disease->diseaseExists();
$diet->disease_id=$disease->disease_id;
$breakfast=$data->bf;
$lunch=$data->lu;
$dinner=$data->di;
$diet->bf_arr=explode(", ",$breakfast);
$diet->lu_arr=explode(", ",$lunch);
$diet->di_arr=explode(", ",$dinner);
if($diet->updateDietMap()==0){
    http_response_code(200);
    echo json_encode("Update Success!");
}
else{
    http_response_code(404);
    echo json_encode(
        array("Error in entry.")
    );
}