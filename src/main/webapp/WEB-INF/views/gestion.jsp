<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Callypso - Panel de Gestión</title>
    <link rel="stylesheet" href="/css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/static.css/gestion.css">
</head>
<body>

<div class="container mt-5">
    <div class="header-gestion d-flex justify-content-between align-items-center">
        <h2><i class="fas fa-user-shield me-2"></i>Panel de Gestión Administrativa</h2>
        <a href="/main" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
    </div>

    <p class="lead">Bienvenido al sistema interno. Seleccione un módulo para continuar:</p>

    <div class="row g-4 mt-2">
        <div class="col-md-4">
            <a href="#" class="gestion-card">
                <i class="fas fa-calendar-alt fa-3x mb-3 text-danger"></i>
                <h4>Eventos</h4>
                <p>Crear, ver y administrar eventos de reclutamiento y festividades.</p>
            </a>
        </div>

        <div class="col-md-4">
            <div class="gestion-card" style="opacity: 0.7; cursor: not-allowed;">
                <i class="fas fa-users-cog fa-3x mb-3 text-danger"></i>
                <h4>Usuarios</h4>
                <p>Gestión de personal y accesos al sistema (Próximamente).</p>
            </div>
        </div>

        <div class="col-md-4">
            <div class="gestion-card" style="opacity: 0.7; cursor: not-allowed;">
                <i class="fas fa-chart-line fa-3x mb-3 text-danger"></i>
                <h4>Reportes</h4>
                <p>Estadísticas de asistencia y métricas de desempeño.</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>