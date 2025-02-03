<?php

include 'conexion.php';

// Definición correcta de la consulta SQL
$consulta = "SELECT * FROM producto";
$resultado = $conexion->query($consulta);

// Inicialización del array de productos
$productos = [];

while ($fila = $resultado->fetch_array()) {
    array_walk_recursive($fila, function(&$value) {
        $value = mb_convert_encoding($value, 'UTF-8', 'ISO-8859-1');
    });
    $productos[] = $fila;
}


// Codificación y salida en JSON
header('Content-Type: application/json; charset=UTF-8');
echo json_encode($productos);

// Cierre de la conexión
$resultado->close();
$conexion->close();

?>
