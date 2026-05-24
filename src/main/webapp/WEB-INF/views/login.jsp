<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--importa el JSTL Core para que funcione el c:if --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Callypso Call - Login</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="login-card">
    <h2>CALLYPSOCALL</h2>

    <c:if test="${not empty error}">
        <div style= "color:white;" class="alert alert-danger text-center">${error}</div>
    </c:if>
<form action="/login" method="POST">
    <input type="text" placeholder="Correo" name="correo" required>
    <input type="password" placeholder="Contraseña" name="contrasena" required>
    <button type="submit">Ingresar</button>
    </form>
</div>

<div style="margin-top: 15px; text-align: center;">
    <a href="/consultar-estado" style="color: #007bff; text-decoration: none; font-weight: bold;">¿Postulaste? Revisar solicitud</a>
</div>

</body>
</html>