<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estado de Solicitud</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-white d-flex align-items-center justify-content-center" style="height: 100vh;">
    <div class="card bg-secondary p-5 text-center style-card" style="max-width: 500px;">
        <h3>Hola, ${postulante.nombre}</h3>
        <p>El estado de tu postulación es:</p>

        <c:choose>
            <c:when test="${postulante.ESTADO == 'ENTREVISTA'}">
                <div class="text-warning h2">Entrevista Programada</div>
                <p>Fecha y Hora pactada: <strong>${postulante.FECHA_HORA_ENTREVISTA}</strong></p>

                <c:choose>
                    <%-- Corregido: LINKHABILITADO debe ir en mayúsculas --%>
                    <c:when test="${postulante.LINKHABILITADO == 1}">
                        <div class="alert alert-success mt-3">
                            <strong>¡El enlace ya está activo!</strong><br>
                            <a href="${postulante.LINK_MEET}" target="_blank" class="btn btn-success mt-2">Ingresar a la Entrevista</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-danger mt-3">
                            <strong>El enlace se encuentra bloqueado.</strong><br>
                            Se habilitará automáticamente al llegar la fecha y hora indicada arriba.
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:when test="${postulante.ESTADO == 'PENDIENTE EN EVALUACION'}">
                <div class="text-info h2">En Evaluación Manual</div>
                <p>Tus respuestas han sido guardadas con éxito. Recursos Humanos está revisando tus datos.</p>
            </c:when>

            <c:otherwise>
                <div class="text-danger h2">Proceso Terminado</div>
                <p>Agradecemos tu participación. En esta oportunidad tu perfil no se adecúa a los criterios requeridos.</p>
            </c:otherwise>
        </c:choose>
        <a href="/main" class="btn btn-outline-light mt-3">Volver al Inicio</a>
    </div>
</body>
</html>