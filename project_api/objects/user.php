<?php
class User{
    private $conn;
    private $table_name = "user_master";
    public $id;
    public $name;
    public $email;
    public $bdate;
    public $pwd;
    public $contact;
    public $age;
    public function __construct($db){
        $this->conn = $db;
    }
	function read(){
    $query = "SELECT * FROM " . $this->table_name;
    $stmt = $this->conn->prepare($query);
    $stmt->execute();
    return $stmt;
}
function create(){
    $query = "INSERT INTO
                " . $this->table_name . "
            SET
                user_name=:name, user_email=:email, user_bdate=:bdate, user_pwd=:pwd, user_contact=:contact, user_age=:age";
    $stmt = $this->conn->prepare($query);
    $this->name=htmlspecialchars(strip_tags($this->name));
    $this->email=htmlspecialchars(strip_tags($this->email));
    $this->bdate=htmlspecialchars(strip_tags($this->bdate));
    $this->pwd=htmlspecialchars(strip_tags($this->pwd));
    $this->contact=htmlspecialchars(strip_tags($this->contact));
	$today = date("Y-m-d");
	$diff=date_diff(date_create($this->bdate), date_create($today));
	$this->age=$diff->format('%y');
    $stmt->bindParam(":name", $this->name);
    $stmt->bindParam(":email", $this->email);
    $stmt->bindParam(":bdate", $this->bdate);
    $stmt->bindParam(":pwd", $this->pwd );
    $stmt->bindParam(":contact", $this->contact);
	$stmt->bindParam(":age", $this->age);
    if($stmt->execute()){
        return true;
    }
    return false;
}
function delete(){
    $query = "DELETE FROM " . $this->table_name . " WHERE user_id = ?";
	$stmt = $this->conn->prepare($query);
	$this->id=htmlspecialchars(strip_tags($this->id));
    $stmt->bindParam(1, $this->id);
    if($stmt->execute()){
        return true;
    }
    return false;
}
function login()
{
	$query = "SELECT user_id FROM " . $this->table_name . " WHERE user_email=:email AND user_pwd=:pwd";
	$stmt = $this->conn->prepare($query);
	$this->email=htmlspecialchars(strip_tags($this->email));
	$this->pwd=htmlspecialchars(strip_tags($this->pwd));
	$stmt->bindParam(":email", $this->email);
	$stmt->bindParam(":pwd", $this->pwd );
    $stmt->execute();
    return $stmt;
}
function exists()
{
	$query="SELECT * FROM " . $this->table_name . " WHERE user_id=?";
	$stmt = $this->conn->prepare($query);
	$this->id=htmlspecialchars(strip_tags($this->id));
	$stmt->bindParam(1, $this->id);
	if($stmt->execute()){
        return true;
    }
    return false;
}
function emailExists($mail)
{
	$query="select * from user_master where user_email=?";
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $mail);
	$stmt->execute();
	$num=$stmt->rowCount();
	if($num==1)
        return true;
	else
    return false;
}
}
?>
