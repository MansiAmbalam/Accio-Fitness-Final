<?php
class Sleep{
    private $conn;
    private $table_name = "user_sleep_track";
    public $id;
    public $uid;
    public $sleep_date;
    public $sleep_hours;
    public function __construct($db){
        $this->conn = $db;
    }
	function create()
	{
		 $query = "INSERT INTO
                " . $this->table_name . "
            SET
                user_id=:uid, user_sleep_date=:sleep_date, user_sleep_hours=:sleep_hours";
    $stmt = $this->conn->prepare($query);
    $this->uid=htmlspecialchars(strip_tags($this->uid));
    $this->sleep_date=htmlspecialchars(strip_tags($this->sleep_date));
    $this->sleep_hours=htmlspecialchars(strip_tags($this->sleep_hours));
    $stmt->bindParam(":uid", $this->uid);
    $stmt->bindParam(":sleep_date", $this->sleep_date);
    $stmt->bindParam(":sleep_hours", $this->sleep_hours);
    if($stmt->execute()){
        return true;
    }
    return false;
	}
	function read()
	{
		$query = "SELECT
                user_sleep_date as sleep_date , user_sleep_hours as sleep_hours FROM " . $this->table_name ."where user_id=?";
    $stmt = $this->conn->prepare($query);
	$this->uid=htmlspecialchars(strip_tags($this->uid));
	$stmt->bindParam(1, $this->uid);
    $stmt->execute();
    return $stmt;
	}
}
?>