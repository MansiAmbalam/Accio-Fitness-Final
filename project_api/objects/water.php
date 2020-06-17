<?php
class Water{
    private $conn;
    private $table_name = "user_water_track";
    public $id;
    public $uid;
    public $water_date;
    public $water_amount;
    public function __construct($db){
        $this->conn = $db;
    }
	function create()
	{
		 $query = "INSERT INTO
                " . $this->table_name . "
            SET
                user_id=:uid, user_water_date=:water_date, user_water_amount=:water_amount";
    $stmt = $this->conn->prepare($query);
    $this->uid=htmlspecialchars(strip_tags($this->uid));
    $this->water_amount=htmlspecialchars(strip_tags($this->water_amount));
    $stmt->bindParam(":uid", $this->uid);
    $stmt->bindParam(":water_date", $this->water_date);
    $stmt->bindParam(":water_amount", $this->water_amount);
    if($stmt->execute()){
        return true;
    }
    return false;
	}
}
?>