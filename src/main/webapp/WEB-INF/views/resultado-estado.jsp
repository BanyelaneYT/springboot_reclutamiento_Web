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
                    <%-- CASO 1: YA SE LE GENERÓ LA ENTREVISTA O FUE APROBADO TOTALMENTE --%>
                    <c:when test="${postulante.estado == 'ENTREVISTA' || postulante.estado == 'APROBADO'}">
                        <div class="aprobado">Entrevista Programada</div>
                        <p>¡Felicidades! Tu entrevista ya está agendada.</p>

                        <div class="box-link">
                            <p class="m-0" style="font-size: 15px;">
                                <strong>Link de la Entrevista (Horario 3:30pm):</strong> <br>
                                <a href="https://meet.google.com/abc-defg-hij" target="_blank">https://meet.google.com/abc-defg-hij</a>
                            </p>
                        </div>
                    </c:when>

                    <%-- CASO 2: APROBÓ EL FORMULARIO AUTOMÁTICO, PERO ADMINISTRACIÓN AÚN NO LE GENERA EL LINK --%>
                    <c:when test="${postulante.estado == 'PENDIENTE EN EVALUACION'}">
                        <div class="pendiente">Aprobaste el Formulario</div>
                        <p>¡Buen trabajo! Has superado el puntaje mínimo del test técnico. Tu postulación está <strong>pendiente a entrevista</strong>. Por favor, vuelve a consultar esta página más tarde para ver tu enlace de reunión.</p>
                    </c:when>

                    <%-- CASO 3: DESAPROBÓ EL TEST O FUE RECHAZADO MANUALMENTE --%>
                    <c:otherwise>
                        <div class="desaprobado">Desaprobaste el formulario</div>
                        <p>Agradecemos tu tiempo y participación. En esta ocasión no alcanzaste el puntaje necesario para continuar con el proceso de selección.</p>
                    </c:otherwise>
                </c:choose>

        <a href="/main" class="btn btn-outline-light mt-4 w-100">Volver al Inicio</a>
    </div>
</body>
</html>