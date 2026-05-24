<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estado de Solicitud</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #1d1d1d; color: white; display: flex; justify-content: center; align-items: center; height: 100vh; font-family: 'Raleway', sans-serif;}
        .status-card { background-color: #2b2b2b; padding: 40px; border-radius: 12px; width: 100%; max-width: 500px; text-align: center; box-shadow: 0 8px 16px rgba(0,0,0,0.5); }
        .aprobado { color: #28a745; font-weight: bold; font-size: 2rem; margin: 20px 0;}
        .desaprobado { color: #dc3545; font-weight: bold; font-size: 2rem; margin: 20px 0;}
        .pendiente { color: #ffc107; font-weight: bold; font-size: 2rem; margin: 20px 0;}
        .box-link { background-color: #1a1a1a; padding: 15px; border-radius: 8px; border-left: 4px solid #28a745; margin-top: 20px; text-align: left; }
        .box-link a { color: #38ef7d; text-decoration: none; word-break: break-all; }
    </style>
</head>
<body>
    <div class="status-card">
        <h3>Hola, ${postulante.nombre}</h3>
        <p class="text">El estado actual de tu postulación es:</p>

        <c:choose>
            <%-- CASO: APROBADO --%>
            <c:when test="${postulante.estado == 'APROBADO' || postulante.estado == 'Aprobado'}">
                <div class="aprobado">Aprobado</div>
                <p>¡Felicidades! Has superado la evaluación inicial de manera exitosa.</p>

                <div class="box-link">
                    <p class="m-0" style="font-size: 15px;">
                        <strong>Link de la Entrevista (Horario 3:30pm):</strong> <br>
                        <a href="https://meet.google.com/abc-defg-hij" target="_blank">https://meet.google.com/abc-defg-hij</a>
                    </p>
                </div>
            </c:when>

            <%-- CASO: DESAPROBADO / RECHAZADO --%>
            <c:when test="${postulante.estado == 'DESAPROBADO' || postulante.estado == 'Desaprobado' || postulante.estado == 'RECHAZADO'}">
                <div class="desaprobado">Desaprobado</div>
                <p>Agradecemos tu tiempo y participación. En este momento no podemos continuar con tu proceso de selección.</p>
            </c:when>

            <%-- CASO DEFAULT: PENDIENTE EN EVALUACIÓN --%>
            <c:otherwise>
                <div class="pendiente">Pendiente en Evaluación</div>
                <p>Tu solicitud se encuentra en revisión. Te sugerimos volver a consultar los resultados más tarde.</p>
            </c:otherwise>
        </c:choose>

        <a href="/main" class="btn btn-outline-light mt-4 w-100">Volver al Inicio</a>
    </div>
</body>
</html>