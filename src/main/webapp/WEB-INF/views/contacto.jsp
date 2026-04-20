<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Contacto</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/contacto.css">
</head>

<body>

<div class="container">

<h1>CONTÁCTANOS</h1>
    <p>¡Estamos aquí para escucharte!Ponte en contacto con nosotros y juntos crearemos soluciones excepcionales.</p>

    <!-- INFO -->
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

    <!-- FORM -->
    <div class="form-container">

        <!-- Mensaje -->
        <c:if test="${not empty mensajeExito}">
            <p class="success">${mensajeExito}</p>
        </c:if>

        <form action="/contacto/enviar" method="post">

            <div class="row">
                <input type="text" name="nombre" placeholder="Nombre" required>
                <input type="email" name="correo" placeholder="Correo Electrónico" required>
            </div>

            <input type="text" name="asunto" placeholder="Asunto" required>

            <textarea name="mensaje" placeholder="Mensaje" required></textarea>

            <button type="submit">Enviar Mensaje</button>

        </form>

    </div>

</div>

</body>
</html>