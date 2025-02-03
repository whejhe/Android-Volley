<?php
header("Content-Type: application/json");
include 'conexion.php';

if ($conexion->connect_error) {
    die("Conexión fallida: " . $conexion->connect_error);
}

$consulta = "SELECT codigo, p.producto, fabricante, precio FROM producto p";
$resultado = $conexion->query($consulta);

$productos = array();
while ($fila = $resultado->fetch_assoc()) {
    $productos[] = $fila;
}

echo json_encode($productos);
$conexion->close();
