<?php
include './utils/conn.php';

//$idJardiner = $_SESSION['idjardiner'];

?>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ruta Actual</title>
<link rel="stylesheet" href="../css/full_ruta.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57P+Mzgj5y3g+1rKsz8SY3sbQjl+hpKHoNU6Oevo3Im0Ft0" crossorigin="anonymous">
</head>
<body>
<!-- NAVEGATION BAR -->
<nav class="navbar navbar-expand-lg" id="navBar">
    <div class="container-fluid">
        <a class="navbar-brand" href="main.php">
            <img src="../img/CasCatalaNB-NoText.png" alt="Logo" width="60" height="60" class="d-inline-block align-text-top">
        </a>
        <a class="navbar-brand" href="main.php">Cas Català</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto d-flex align-items-center">
                <!-- Botón desplegable con varias opciones -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Accions
                    </a>
                    <!-- Opciones del menú -->
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <?php if (isset($_SESSION['tipusUsuari'])): ?>
                        <li><a class="dropdown-item" href="equips.php">Equips</a></li>
                        <?php if ($_SESSION['tipusUsuari'] == 1): ?>
                            <li><a class="dropdown-item" href="usuaris.php">Usuaris</a></li>
                            <li><a class="dropdown-item" href="quotes.php">Quotes</a></li>
                            <li><a class="dropdown-item" href="rivals.php">Rivals</a></li>
                        <?php elseif ($_SESSION['tipusUsuari'] == 2): ?>
                            <li><a class="dropdown-item" href="calendari.php">Calendari</a></li>
                        <?php elseif ($_SESSION['tipusUsuari'] == 3): ?>
                            <li><a class="dropdown-item" href="rivals.php">Rivals</a></li>
                            <li><a class="dropdown-item" href="calendari.php">Calendari</a></li>
                        <?php endif; ?>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="../logout.php">Tanca Sessió</a></li>
                    <?php endif; ?>
                    </ul>
                </li>
                <li class="nav-item mx-2">
                    <a class="nav-link" href="carrito.php" title="Carrito de compras">
                        <i class="fas fa-shopping-cart"></i>
                    </a>
                </li>
                <!-- Botón con el ícono de un mensaje -->
                <li class="nav-item mx-2">
                    <a class="nav-link" href="mensajes.php"><i class="fas fa-envelope"></i></a>
                </li>
                <!-- Label con nombre de una persona -->
                <li class="nav-item mx-2">
                    <span class="navbar-text">
                        <?php
                            // Comprova si les variables estan establertes a la sessi贸
                            if(isset($_SESSION['nomUsuari']) && isset($_SESSION['llinatges'])) {
                                echo $_SESSION['nomUsuari'] . " " . $_SESSION['llinatges'];
                            } else {
                                echo "Diego Bermejo Cabañas";
                            }
                        ?>
                    </span>
                </li>
                <!-- Botón con el ícono de un usuario para el perfil -->
                <li class="nav-item mx-2">
                    <a class="nav-link" href="perfil.php"><i class="fas fa-user"></i></a>
                </li>
            </ul>
        </div>
    </div>
</nav>    
<!-- END NAVEGATION BAR -->

<div class="container">
  <div class="header">
    <h1>Ruta Actual</h1>
    <div>23/10/2024</div>
    <div class="participants">
        <strong>Participants:</strong> Jaume Adrover, Joan Balaguer, Marc Cañellas, Diego Bermejo
    </div>
    <div class="vehicle">
      <span class="vehicle-icon">🚐</span> <!-- Emoji de furgoneta -->
      <span>Matrícula: 1234 ABC</span>
    </div>
  
</div>
  
  
  <?php
// Assuming $conexio is your database connection and it's already established

$query = 'SELECT tt.* FROM TipusTasca tt JOIN Tasca t ON tt.id_tipus_tasca = t.id_tipus_tasca JOIN Intervencio i ON t.id_tasca = i.id_tasca WHERE i.id_jardiner = 1';
$stmt = $conexio->prepare($query);
$stmt->execute();
$tareas = $stmt->get_result();

// Check if there are any tasks for the gardener
if ($tareas->num_rows > 0) {
    // Loop through each task and display its content
    while ($tarea = $tareas->fetch_assoc()) {
        echo '<div class="task">';
        echo '<div class="task-title">' . htmlspecialchars($tarea['tipus_tasca']) . '</div>';
        echo '<p>' . htmlspecialchars($tarea['descripcio']) . '</p>';
        echo '</div>';
    }
} else {
    echo '<p>No hay tareas asignadas para este jardinero.</p>';
}

$stmt->close();
$conexio->close();
?>


</div>
</body>
</html>
