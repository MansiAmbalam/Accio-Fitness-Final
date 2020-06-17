<?php
class Disease{
    private $conn;
    private $table_name = "disease_master";
    public $disease_id;
    public $disease_name;
	public $sname;
	public $symptom_names;
	public $sym_names=array();
    public function __construct($db){
        $this->conn = $db;
    }
function read(){
    $query = "SELECT d.disease_id,d.disease_name FROM " . $this->table_name . " d LEFT JOIN disease_symptom_map s ON d.disease_id=s.disease_id where s.symptom_id=(SELECT symptom_id from symptom_master WHERE symptom_name=?)";
    $stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->sname);
    $stmt->execute();
    return $stmt;
}
function display()
{
	$query="select symptom_name from symptom_master";
	$stmt = $this->conn->prepare($query);
	$stmt->execute();
	return $stmt;
}
function symptomExists()
{
	$query="select symptom_id from symptom_master where symptom_name=?";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->sname);
	$stmt->execute();
	if($stmt->rowCount()==0)
	{
		$this->insertSymptom();
		$this->symptomExists();
	}
	else
	{
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$this->symptom_id=$row['symptom_id'];
	}
}
function insertSymptom()
{
	$query="insert into symptom_master(symptom_name) values(?)";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->sname);
	$stmt->execute();
}
function diseaseExists()
{
	$query="select disease_id from disease_master where disease_name=?";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->disease_name);
	$stmt->execute();
	if($stmt->rowCount()==0)
	{
		$this->insertDisease();
		$this->diseaseExists();
	}
	else
	{
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$this->disease_id=$row['disease_id'];
	}
}
function insertDisease()
{
	$query="insert into disease_master(disease_name) values(?)";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $this->disease_name);
	$stmt->execute();
}
function updateDiseaseMap()
{
	$len=count($this->sym_names);
	$this->diseaseExists();
	$query1="select * from disease_symptom_map where disease_id=? and symptom_id=?";
	$stmt1 = $this->conn->prepare($query1);
	$query2="insert into disease_symptom_map(disease_id,symptom_id) values(?,?)";
	$stmt2 = $this->conn->prepare($query2);
	while($len-1>=0)
	{
		$this->sname=$this->sym_names[$len-1];
		$this->symptomExists();
		$stmt1->bindParam(1, $this->disease_id);
		$stmt1->bindParam(2, $this->symptom_id);
		$stmt1->execute();
		if($stmt1->rowCount()==0)
		{
			$stmt2->bindParam(1, $this->disease_id);
			$stmt2->bindParam(2, $this->symptom_id);
			$stmt2->execute();
		}
		$len=$len-1;
	}
	return $len;
}
}
?>