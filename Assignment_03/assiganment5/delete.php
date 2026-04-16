<?php
include 'config.php';

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    $stmt = $pdo->prepare("DELETE FROM employees WHERE id = ?");
    if ($stmt->execute([$id])) {
        header("Location: index.php?success=3");
    } else {
        echo "Error deleting employee.";
    }
} else {
    header("Location: index.php");
}
?>
