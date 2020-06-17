<?php
class Recipe{
    private $conn;
    private $table_name = "recipe_master";
    public $id;
    public $name;
    public $description;
	public $ingredient_arr=array();
	public $quantity_arr=array();
	public function __construct($db){
        $this->conn = $db;
    }
function read(){
    $query1 = "select recipe_id as id, recipe_name as name, recipe_description as description from ".$this->table_name." where recipe_name=? ";
    $stmt1 = $this->conn->prepare($query1);
	$stmt1->bindParam(1, $this->name);
    $stmt1->execute();
	$row1 = $stmt1->fetch(PDO::FETCH_ASSOC);
	$this->id= $row1['id'];
    $this->name = $row1['name'];
    $this->description = $row1['description'];	
	$query2 = "select ingredient_id from recipe_ingredient_map where recipe_id= ".$this->id;
	$query3 = "select ingredient_quantity from recipe_ingredient_map where recipe_id= ".$this->id;
	$stmt2 = $this->conn->prepare($query2);
	$stmt2->execute();
	$stmt3 = $this->conn->prepare($query3);
	$stmt3->execute();
	while ($row2 = $stmt2->fetch(PDO::FETCH_ASSOC)){
        extract($row2);
		$query4 = "select ingredient_name from ingredient_master where ingredient_id=".$ingredient_id;
		$stmt4=$this->conn->query($query4);
		$stmt4->execute();
		$row4 = $stmt4->fetch(PDO::FETCH_ASSOC);
		$row3 = $stmt3->fetch(PDO::FETCH_ASSOC);
		$this->quantity_arr[$row4['ingredient_name']]=$row3['ingredient_quantity'];
    }
}
function display()
{
	$query="select recipe_name from ".$this->table_name;
	$stmt = $this->conn->prepare($query);
	$stmt->execute();
	return $stmt;
}	
}
?>