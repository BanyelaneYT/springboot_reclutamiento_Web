<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Contacto | Callypso Call Peru</title>

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/contacto.css">
</head>

<body>

<header id="header" class="fixed-top d-flex align-items-center">
    <div class="header-fullwidth d-flex align-items-center justify-content-between">
        <a href="/" class="logo">
            CallypsoCall
        </a>

        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="nav-link" href="/publicidad">Servicios</a></li>
                <li><a class="nav-link" href="/contacto">Contacto</a></li>
                <li><a class="nav-link" href="/evento">Eventos</a></li>
                <li><a class="nav-link" href="/postular">Postular</a></li>
                <li><a class="getstarted" href="/login">Login ></a></li>
            </ul>
        </nav>
    </div>
</header>
<div class="container-contacto">

    <h1>CONTÁCTANOS</h1>
    <p class="subtitle">¡Estamos aquí para escucharte! Ponte en contacto con nosotros y juntos crearemos soluciones excepcionales.</p>

    <div class="info">
        <div class="box">
            <h3>DIRECCIÓN</h3>
            <p>Jr Escorpio Mz C Lt 23 - Los Olivos</p>
        </div>

        <div class="box">
            <h3>TELÉFONO</h3>
            <p>+51 941 208 644</p>
        </div>

        <div class="box">
            <h3>EMAIL</h3>
            <p>rrhh.callypso.pe@gmail.com</p>
        </div>
    </div>

    <div class="form-container">

        <c:if test="${not empty mensajeExito}">
            <p class="success">${mensajeExito}</p>
        </c:if>

        <form action="/contacto/enviar" method="post">

            <div class="row-form-inputs">
                <div class="input-group-half">
                    <input type="text" name="nombre" placeholder="Nombre" required>
                </div>
                <div class="input-group-half">
                    <input type="email" name="correo" placeholder="Correo Electrónico" required>
                </div>
            </div>

            <input type="text" name="asunto" placeholder="Asunto" required>

            <textarea name="mensaje" placeholder="Mensaje" required></textarea>

            <div class="button-container">
                <button type="submit">Enviar Mensaje</button>
            </div>

        </form>

    </div>

</div>

<footer class="footer-simple">
    <p>&copy; 2026 Callypso Call Peru. Todos los derechos reservados.</p>
</footer>

</body>
</html>