<?php
class Diet{
    private $conn;
    private $table_name = "diet_suggestion";
	public $dname;
	public $disease_id;
	public $food_id;
	public $food_name;
	public $bf_arr=array();
	public $lu_arr=array();
	public $di_arr=array();
    public function __construct($db){
        $this->conn = $db;
    }
	function read()
	{
		$query1="select disease_id from disease_master where disease_name=?";
		$stmt1=$this->conn->prepare($query1);
		$stmt1->bindParam(1, $this->dname);
		$stmt1->execute();
		$row1 = $stmt1->fetch(PDO::FETCH_ASSOC);
		$did=$row1['disease_id'];
		$query2="select food_id from diet_suggestion where disease_id=?";
		$stmt2=$this->conn->prepare($query2);
		$stmt2->bindParam(1, $did);
		$stmt2->execute();
		while($row2 = $stmt2->fetch(PDO::FETCH_ASSOC))
		{
			extract($row2);
			$query3="select day_division from food_time_division_master where food_time_division_id=(select food_time_division_id from diet_suggestion where disease_id=? and food_id=?)";
			$stmt3=$this->conn->prepare($query3);
			$stmt3->bindParam(1, $did);
			$stmt3->bindParam(2, $food_id);
			$stmt3->execute();
			$row3 = $stmt3->fetch(PDO::FETCH_ASSOC);
			$query4="select food_name from food_master where food_id=?";
			$stmt4=$this->conn->prepare($query4);
			$stmt4->bindParam(1, $food_id);
			$stmt4->execute();
			$row4 = $stmt4->fetch(PDO::FETCH_ASSOC);
			if($row3['day_division']=="Breakfast")
				array_push($this->bf_arr,$row4['food_name']);
			else if($row3['day_division']=="Lunch")
				array_push($this->lu_arr,$row4['food_name']);
			else
				array_push($this->di_arr,$row4['food_name']);
		}
	}
function display()
{
	$query="select disease_name from disease_master";
	$stmt = $this->conn->prepare($query);
	$stmt->execute();
	return $stmt;
}
function foodDisplay()
{
	$query="select food_name from food_master";
	$stmt = $this->conn->prepare($query);
	$stmt->execute();
	return $stmt;
}
function foodExists()
{
	$query="select food_id from food_master where food_name=?";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->food_name);
	$stmt->execute();
	if($stmt->rowCount()==0)
	{
		$this->insertFood();
		$this->foodExists();
	}
	else
	{
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$this->food_id=$row['food_id'];
	}
}
function insertFood()
{
	$query="insert into food_master(food_name) values(?)";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->food_name);
	$stmt->execute();
}
function updateDietMap()
{
	$len=count($this->bf_arr);
	$div=1;
	$query1="select * from diet_suggestion where disease_id=? and food_id=? and food_time_division_id=?";
	$stmt1 = $this->conn->prepare($query1);
	$query2="insert into diet_suggestion(disease_id,food_id,food_time_division_id) values(?,?,?)";
	$stmt2 = $this->conn->prepare($query2);
	$len=$len-1;
	while($len-1>=0)
	{
		$this->food_name=$this->bf_arr[$len-1];
		$this->foodExists();
		$stmt1->bindParam(1, $this->disease_id);
		$stmt1->bindParam(2, $this->food_id);
		$stmt1->bindParam(3, $div);
		$stmt1->execute();
		if($stmt1->rowCount()==0)
		{
			$stmt2->bindParam(1, $this->disease_id);
			$stmt2->bindParam(2, $this->food_id);
			$stmt2->bindParam(3, $div);
			$stmt2->execute();
		}
		$len=$len-1;
	}
	$count=$len;
	$len=count($this->lu_arr);
	$div=2;
	$len=$len-1;
	while($len-1>=0)
	{
		$this->food_name=$this->lu_arr[$len-1];
		$this->foodExists();
		$stmt1->bindParam(1, $this->disease_id);
		$stmt1->bindParam(2, $this->food_id);
		$stmt1->bindParam(3, $div);
		$stmt1->execute();
		if($stmt1->rowCount()==0)
		{
			$stmt2->bindParam(1, $this->disease_id);
			$stmt2->bindParam(2, $this->food_id);
			$stmt2->bindParam(3, $div);
			$stmt2->execute();
		}
		$len=$len-1;
	}
	$count=$count+$len;
	$len=count($this->di_arr);
	$div=3;
	$len=$len-1;
	while($len-1>=0)
	{
		$this->food_name=$this->di_arr[$len-1];
		$this->foodExists();
		$stmt1->bindParam(1, $this->disease_id);
		$stmt1->bindParam(2, $this->food_id);
		$stmt1->bindParam(3, $div);
		$stmt1->execute();
		if($stmt1->rowCount()==0)
		{
			$stmt2->bindParam(1, $this->disease_id);
			$stmt2->bindParam(2, $this->food_id);
			$stmt2->bindParam(3, $div);
			$stmt2->execute();
		}
		$len=$len-1;
	}
	$count=$count+$len;
	return $count;
}
}
?>