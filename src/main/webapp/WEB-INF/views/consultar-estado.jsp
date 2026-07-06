<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callypso Call - Consultar Solicitud</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/consultar-estado.css">
</head>
<body>
<div class="page-wrapper">
    <div class="status-card">
        <h2>REVISAR SOLICITUD</h2>
        <p>Ingresa tu DNI para ver el estado de todas tus postulaciones.</p>
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>
        <form action="/consultar-estado" method="POST">
            <input type="number" placeholder="Ingresa tu DNI" name="dni" required>
            <button type="submit">Consultar Estado</button>
        </form>
        <div style="margin-top: 15px;">
            <a href="/login">Volver al Login</a>
        </div>
    </div>
</div>
</body>
</html>
