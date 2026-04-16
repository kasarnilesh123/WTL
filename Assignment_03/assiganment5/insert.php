<?php
include 'config.php';

if ($_POST) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $position = $_POST['position'];
    $salary = $_POST['salary'];

    $stmt = $pdo->prepare("INSERT INTO employees (name, email, phone, position, salary) VALUES (?, ?, ?, ?, ?)");
    if ($stmt->execute([$name, $email, $phone, $position, $salary])) {
        header("Location: index.php?success=1");
    } else {
        echo "Error adding employee.";
    }
}
?>
