<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include_once '../connect/database.php';
include_once '../objects/diet.php';
$database = new Database();
$db = $database->getConnection();
$diet = new Diet($db);
$stmt = $diet->foodDisplay();
if($stmt->rowCount()>0){
    $row_all = $stmt->fetchall(PDO::FETCH_ASSOC);
	http_response_code(200);
    echo json_encode($row_all);
}
else{
    http_response_code(404);
    echo json_encode("No food items found.");
}
?>