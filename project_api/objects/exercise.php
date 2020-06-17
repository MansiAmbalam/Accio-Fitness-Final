<?php
class Exercise{
    private $conn;
	public $exType;
	public $id;
	public $path;
	public function __construct($db){
        $this->conn = $db;
    }
	function fetchImage()
{
	$query="select exercise_path from exercise_master where exercise_type_id=(select exercise_type_id from exercise_type_master where exercise_type=?)";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->exType);
    $stmt->execute();
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	$this->path=$row['exercise_path'];
}
}
?>