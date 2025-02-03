<?php
include 'conexion.php';

// Recoge y sanitiza el parámetro recibido
$codigo = isset($_GET['codigo']) ? $_GET['codigo'] : '';

// Usar una consulta preparada para evitar inyección SQL
$stmt = $conexion->prepare("SELECT * FROM producto WHERE codigo = ?");
$stmt->bind_param("s", $codigo);
$stmt->execute();
$resultado = $stmt->get_result();

$producto = []; // Inicialización del array

while ($fila = $resultado->fetch_assoc()) {
    $producto[] = array_map(fn($value) => mb_convert_encoding($value, 'UTF-8', 'ISO-8859-1'), $fila);
}

// Envío de la respuesta en JSON
header('Content-Type: application/json; charset=UTF-8');
echo json_encode($producto);

$resultado->close();
$stmt->close();
$conexion->close();
?>
