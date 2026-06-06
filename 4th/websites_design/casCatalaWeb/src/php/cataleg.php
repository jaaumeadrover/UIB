<?php
include './utils/conn.php';
?>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cas Català</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/mainStyles.css">
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


<!-- MAIN CONTENT -->
<div class="container mt-4">
    <div class="row">
        <div class="col-12">
            <h2 class="text-center mb-4">CERCADOR DE PRODUCTES</h2>
            <!-- Search bar -->
            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Cerca un producte..." aria-label="Cerca un producte..." aria-describedby="button-cercar">
                <button class="btn btn-success" type="button" id="button-cercar">Cercar</button>
            </div>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <!-- Product Item -->
                <?php
                $image_paths = array("../img/tomato.png", "../img/aloevera.png", "../img/tomato.png");
                $query = 'SELECT nom_cientific, "../img/tomato.png" as imatge, preu_unitat FROM Planta';
                $result = mysqli_query($conexio, $query);
                $index  = 0;
                // Suponiendo que tienes una variable $products que contiene tus productos
                while ($product = mysqli_fetch_assoc($result)) {
                    echo '<div class="col">';
                    echo '    <div class="card h-100">';
                    echo '        <img src="' . $image_paths[$index] . '" class="card-img-top" alt="' . $product['nom_cientific'] . '">';
                    echo '        <div class="card-body">';
                    echo '            <h5 class="card-title">' . $product['nom_cientific'] . '</h5>';
                    echo '            <p class="card-text">' . $product['preu_unitat'] . ' $</p>';
                    echo '        </div>';
                    echo '        <div class="card-footer d-flex justify-content-between align-items-center">';
                    echo '            <button class="btn btn-success">Add to Cart</button>';
                    echo '            <button class="btn btn-primary">Buy Now</button>';
                    echo '            <small class="text-muted">⭐⭐⭐⭐⭐</small>';
                    echo '        </div>';
                    echo '    </div>';
                    echo '</div>';     
                    $index = $index + 1;        
                }
                ?>
            </div>
        </div>
    </div>
</div>
<!-- END MAIN CONTENT -->


<!-- Bootstrap JavaScript Bundle with Popper -->
<script src="../js/bootstrap.bundle.min.js"></script>

</body>

</html>
