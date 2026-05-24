<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Callypso Call - Consultar Solicitud</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="login-card">
    <h2>REVISAR SOLICITUD</h2>
    <p style="color: #ccc; text-align: center; font-size: 14px; margin-bottom: 20px;">Ingresa tu DNI para ver el estado de tu postulación.</p>

    <c:if test="${not empty error}">
        <div style="color: white; background-color: #dc3545; padding: 10px; border-radius: 5px; text-align: center; margin-bottom: 15px;">
            ${error}
        </div>
    </c:if>

    <form action="/consultar-estado" method="POST">
        <input type="number" placeholder="Ingresa tu DNI" name="dni" required>
        <button type="submit">Consultar Estado</button>
    </form>

    <div style="margin-top: 15px; text-align: center;">
        <a href="/login" style="color: white; text-decoration: underline; font-size: 14px;">Volver al Login</a>
    </div>
</div>
</body>
</html>