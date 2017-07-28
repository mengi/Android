<?
/*mysql_query("SET NAMES 'latin5'");
                mysql_query("SET CHARACTER SET latin5");
                mysql_query("SET COLLATION_CONNECTION = 'latin5_turkish_ci'");*/
//mysql_set_charset('utf8');				
class mySQL
{
var $sql_link = "";
var $host = "";
var $username = "";
var $password = "";
var $fields;
function sql_connect($dbname)
{
$this->fields = array();
$this->sql_link = mysql_connect($this->host , $this->username , $this->password);
if(!$this->sql_link) {echo"Veri Tabani Baglanti Hatasi"; exit();}
if(!mysql_select_db($dbname , $this->sql_link)){echo"Tablo Mevcut Degil"; exit();}
}

function write_query($sorgu)
{
if($this->sql_link) return mysql_query($sorgu); else return false;
}

function read_query($sorgu)
{
if($this->sql_link) return mysql_unbuffered_query($sorgu); else return false;
}

function sql_fetchobject($sorgu)
{
if($this->sql_link) return mysql_fetch_object($sorgu); else return false;
}

function sql_freeresult($sorgu)
{
if($this->sql_link) return mysql_free_result($sorgu); else return false;
}

function sql_fetcharray($sorgu)
{
if($this->sql_link) return @mysql_fetch_array($sorgu); else return false;
}

function sql_fetchrow($sorgu)
{
if($this->sql_link) return @mysql_fetch_row($sorgu); else return false;
}

function sql_fieldname($sorgu , $i)
{
if($this->sql_link) return mysql_field_name($sorgu , $i); else return false;
}

function sql_nextid()
{
if($this->sql_link) return @mysql_insert_id($this->sql_link);	else return false;
}

function sql_numrows($sorgu)
{
if($this->sql_link) return @mysql_num_rows($sorgu); else return false;
}

function sql_error()
{
if($this->sql_link) return mysql_error(); else return "";
}

function sql_close()
{
if($this->sql_link) return mysql_close($this->sql_link); else return false;
}
function sql_insert($tablo,$p){
	 $r1=$this->write_query("show fields from $tablo");
	 while($rr=$this->sql_fetcharray($r1)){
		   $i++;
		   $vv=$rr[0];
		   if($i==1){
			  $k=$k."$rr[0]";
			  if($rr[5]=="auto_increment"){
				 $v=$v."null";
			  }else{
				 $v=$v."'$p[$vv]'";
			  }
		   }else{
			  $k=$k.",$rr[0]";
			  $v=$v.",'$p[$vv]'";
		   }
	 }
	// mysql_set_charset('latin5_turkish_ci');
	 //mysql_set_charset('utf8');
	 
	 $sql="insert into $tablo ($k) values ($v)";
	 return $this->write_query($sql);
}
public function sql_update($tablo,$p,$sart){
	 if($sart==""){
		echo $this->uyari("Sart bos olamaz");
		return;
	 }
	 $r1=$this->write_query("show fields from $tablo");
	 while($rr=$this->sql_fetcharray($r1)){
		   $i++;
		   $vv=$rr[0];
		   if($i==1){
			  $k=$k."$rr[0]='$p[$vv]'";
		   }else{
			  $k=$k.",$rr[0]='$p[$vv]'";
		   }
	 }
	 $sql="update $tablo set $k where $sart";
	 return $this->write_query($sql);

}
}//end db

	$db 			= new mySQL;
	$db->host 		= "127.0.0.1";
	$db->username 	= "root";
	$db->password 	= "mengi";
	$db->sql_connect("bislem_adisyon");
?>
