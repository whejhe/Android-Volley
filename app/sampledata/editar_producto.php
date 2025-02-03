<?php
include 'conexion.php';

// Recoger datos usando POST
$codigo = $_POST['codigo'];
$producto = $_POST['producto'];
$precio = $_POST['precio'];
$fabricante = $_POST['fabricante'];

// Verificación de errores en la conexión
if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}

// Declaración preparada para prevenir inyección SQL
$stmt = $conexion->prepare("UPDATE producto SET producto = ?, precio = ?, fabricante = ? WHERE codigo = ?");
$stmt->bind_param("sdss", $producto, $precio, $fabricante, $codigo); // 's' para string, 'd' para double

if ($stmt->execute()) {
    // Respuesta exitosa
    echo json_encode(["mensaje" => "Producto actualizado correctamente."]);
} else {
    // Manejo de errores
    echo json_encode(["error" => "Error al actualizar: " . $stmt->error]);
}

// Cerrar la declaración y la conexión
$stmt->close();
$conexion->close();
?>
