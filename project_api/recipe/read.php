<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/recipe.php';
$database = new Database();
$db = $database->getConnection();
$recipe = new Recipe($db);
$data = json_decode(file_get_contents("php://input"));
$recipe->name = $data->name;
$stmt = $recipe->read();
if($recipe->name!=null){
    $recipe_arr = array(
        "Name" => $recipe->name,
        "Description" => $recipe->description,
	"Ingredients"=> $recipe->quantity_arr);
    http_response_code(200);
    echo json_encode($recipe_arr);
}
else{
    http_response_code(404);
    echo json_encode("No recipes found.");
}
?>