<?php
$hostname= 'localhost';
$database= 'pruebavolley';
$username= 'root';
$password= '';
$conexion= new mysqli($hostname, $username, $password, $database);
if ($conexion->connect_errno) {
echo "lo sentimos, error al conectar";
}
?>