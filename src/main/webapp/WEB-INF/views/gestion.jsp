<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callypso | Panel Administrativo</title>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/css/gestion.css">
    <link rel="stylesheet" href="/css/Header.css">
</head>
<body>
<header id="header" class="fixed-top d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
        <a href="/gestion" class="logo">
            CallypsoCall
        </a>
        
        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="getstarted" href="/main">Cerrar Sesión</a></li>
            </ul>
        </nav>
    </div>
</header>
<!-- Fin Header -->
<div class="main-content" id="main-content">        
    <div class="container-fluid p-4">
        <div class="row g-4">
            <div class="col-md-6">
                <a href="/catalogo" class="card-module">
                    <div class="icon-box">
                        <i class="fas fa-calendar-check"></i>
                    </div>
                    <h4>Gestión de Eventos</h4>
                    <p>Administre convocatorias, ferias y festividades institucionales.</p>
                </a>
            </div>
            <div class="col-md-6">
                <a href="/crudpostulante" class="card-module">
                    <div class="icon-box">
                        <i class="fas fas fa-user-tie"></i>
                    </div>
                    <h4>Gestión de Postulantes</h4>
                    <p>Administre los postulantes y su información personal.</p>
                </a>
            </div>
            <div class="col-md-6">
                <a href="/usuarios" class="card-module">
                    <div class="icon-box">
                        <i class="fas fa-user-tie"></i>
                    </div>
                    <h4>Gestión de Usuarios</h4>
                    <p>Administre los usuarios del sistema y sus permisos.</p>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>