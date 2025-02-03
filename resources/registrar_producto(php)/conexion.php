<?php

$hostname= 'localhost';
$database= 'registro_producto';
$username= 'root';
$password= '';

$conexion= new mysqli($hostname, $username, $password, $database);
if ($conexion->connect_errno) {
	echo "lo sentimos, error al conectar";
}

?>