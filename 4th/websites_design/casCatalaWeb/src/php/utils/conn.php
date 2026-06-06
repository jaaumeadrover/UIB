<?php
$conexio = mysqli_connect("localhost", "root", "") 
    or die("Error connecting to database");
$db = mysqli_select_db($conexio, "cas_catala")
    or die("Error selecting database");
?>