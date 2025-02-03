<?php

include 'conexion.php';
$codigo = $_GET['codigo'];

// Realiza la consulta a la base de datos
$consulta = "SELECT * FROM producto WHERE codigo = '$codigo'";
$resultado = $conexion->query($consulta);

// Inicializa un array para almacenar los productos
$producto = array();

// Usa fetch_assoc() para obtener solo los datos como un array asociativo (sin índices numéricos)
while ($fila = $resultado->fetch_assoc()) {
    // Codifica los valores a UTF-8 para evitar problemas con caracteres especiales
    $producto[] = array_map('utf8_encode', $fila);
}

// Convierte el array de productos a formato JSON y lo imprime
echo json_encode($producto);

// Cierra la conexión a la base de datos
$resultado->close();

?>
