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
</head>
<body>

<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h1 class="logo">Callypso<span>CALL</span></h1>
    </div>
    <ul class="nav-links">
        <li>
            <a href="/gestion" class="active">
                <i class="fas fa-home"></i>
                <span>Inicio</span>
            </a>
        </li>
        <li>
            <a href="/catalogo">
                <i class="fas fa-calendar-alt"></i>
                <span>Eventos</span>
            </a>
        </li>
        <li>
            <a href="/reclutamiento">
                <i class="fas fa-users-cog"></i>
                <span>Reclutamiento</span>
            </a>
        </li>
        <li>
            <a href="/">
                <i class="fas fa-sign-out-alt"></i>
                <span>Cerrar Sesión</span>
            </a>
        </li>
    </ul>
</div>

<div class="main-content" id="main-content">
    <nav class="navbar-custom">
        <button class="toggle-btn" id="toggle-btn">
            <i class="fas fa-bars"></i>
        </button>
    </nav>

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
                <div href="/evaluaciones" class="card-module">
                    <div class="icon-box">
                        <i class="fas fa-user-tie"></i>
                    </div>
                    <h4>Evaluación de Personal</h4>
                    <p>Seguimiento de candidatos y resultados de pruebas técnicas.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const toggleBtn = document.getElementById('toggle-btn');
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.getElementById('main-content');

    toggleBtn.addEventListener('click', () => {
        sidebar.classList.toggle('active');
        mainContent.classList.toggle('active');
    });
</script>
</body>
</html>