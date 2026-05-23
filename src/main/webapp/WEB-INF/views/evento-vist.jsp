<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callypso Call | Publicidad y Reclutamiento</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,700,800" rel="stylesheet">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/publicidad.css">
</head>

<body>
<!-- Header -->    
<header id="header" class="fixed-top d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
        <a href="/" class="logo">
            CallypsoCall
        </a>
        
        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="nav-link" href="/publicidad">Servicios</a></li>
                <li><a class="nav-link" href="/contacto">Contacto</a></li>
                <li><a class="nav-link" href="/evento">Eventos</a></li>
                <li><a class="getstarted" href="/login">Login ></a></li>
            </ul>
        </nav>
    </div>
</header>
<!-- Fin Header -->

<main>
    <section class="work-with-us" id="vacantes">
        <div class="container text-center">
            <h2 class="mb-5 display-4 fw-bold">Eventos de <span class="highlight">Callypso Call</span></h2>
            <div class="row g-4">
                           <c:forEach var="evento" items="${listaEventos}">
                                <div class="col-md-4">
                                    <div class="card-benefit">
                                        <h3>${evento.nombre}</h3>
                                        <p>${evento.fecha}</p>
                                        <p>${evento.descripcion}</p>
                                        <div>
                                            <a href="/postular" class="btn-join shadow-lg">POSTULAR AHORA <i class="fas fa-paper-plane ms-2"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>


            </div>
        </div>
    </section>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
