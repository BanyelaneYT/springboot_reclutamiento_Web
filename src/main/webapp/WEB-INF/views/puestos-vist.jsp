<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callypso Call | Convocatorias de Trabajo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,700,800" rel="stylesheet">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/publicidad.css">
</head>
<body>
<header id="header" class="fixed-top d-flex align-items-center">
    <div class="header-fullwidth d-flex align-items-center justify-content-between">
        <a href="/" class="logo">CallypsoCall</a>
        <nav id="navbar" class="navbar">
            <ul>
                <li><a href="/">Inicio</a></li>
                <li><a href="/evento">Convocatorias</a></li>
                <li><a href="/contacto">Contacto</a></li>
                <li><a href="/login" class="btn-login-header">Login</a></li>
            </ul>
        </nav>
    </div>
</header>

<main>
    <section class="work-with-us" id="vacantes" style="padding-top: 120px;">
        <div class="container text-center">
            <h2 class="mb-5 display-4 fw-bold">Convocatorias Vigentes en <span class="highlight">Callypso Call</span></h2>
            <div class="row g-4">
                <c:forEach var="puesto" items="${listaCatalogo}">
                    <c:if test="${puesto.estado == 1}">
                        <div class="col-md-4">
                            <div class="card-benefit p-4 shadow">
                                <h3 class="text-accent mb-2">${puesto.nombre}</h3>
                                <p class="text-white-50 mb-1"><i class="fas fa-map-marker-alt me-2"></i>${puesto.presRem} | ${puesto.tipo}</p>
                                <p class="text-white-50 mb-3"><i class="fas fa-clock me-2"></i>${puesto.horario}</p>
                                <p class="text-white text-start" style="font-size: 0.95rem;">${puesto.descripcion}</p>
                                <div class="mt-4 mb-3">
                                    <span class="fs-5 fw-bold text-success">Sueldo: S/. ${puesto.pago}</span>
                                </div>
                                <div>
                                    <a href="/postular?puestoId=${puesto.id}" class="btn-join shadow-lg w-100">
                                        POSTULAR AHORA <i class="fas fa-paper-plane ms-2"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </section>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>