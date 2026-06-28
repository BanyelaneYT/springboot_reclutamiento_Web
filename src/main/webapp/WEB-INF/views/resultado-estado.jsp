<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estado de Solicitud</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
    <div class="d-flex align-items-center" style="height: 100vh;">

        <div class="general-card text-center" style="max-width: 500px; color: white;">
            <h3>Hola, ${postulante.NOMBRE}</h3>
            <p class="text-white-50">El estado de tu postulación es:</p>

            <c:choose>
                <%-- CASO 1: ENTREVISTA PROGRAMADA --%>
                <c:when test="${postulante.ESTADO == 'ENTREVISTA'}">
                    <div class="text-warning h2">Entrevista Programada</div>
                    <p>Fecha y Hora pactada: <strong>${postulante.FECHA_HORA_ENTREVISTA}</strong></p>

                    <c:choose>
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

                <%-- CASO 2: EN EVALUACIÓN --%>
                <c:when test="${postulante.ESTADO == 'PENDIENTE EN EVALUACION'}">
                    <div class="text-info h2">En Evaluación Manual</div>
                    <p>Tus respuestas han sido guardadas con éxito. Recursos Humanos está revisando tus datos.</p>
                </c:when>

                <%-- CASO 3: PROCESO APROBADO --%>
                <c:when test="${postulante.ESTADO == 'APROBADO'}">
                    <div class="text-success h2">¡Felicitaciones! Proceso Aprobado</div>
                    <p>Nos complace informarte que tu perfil ha sido seleccionado. Por favor, acércate a nuestra sede central a la brevedad posible para la firma de tu contrato e iniciar con tu proceso de inducción. ¡Bienvenido al equipo de CallypsoCall!</p>
                </c:when>

                <%-- CASO 4: PROCESO RECHAZADO (Cualquier otro estado o "RECHAZADO") --%>
                <c:otherwise>
                    <div class="text-danger h2">Proceso Terminado</div>
                    <p>Agradecemos profundamente tu participación en este proceso de selección. En esta oportunidad, tu perfil no se adecúa por completo a los criterios específicos requeridos para el puesto. Guardaremos tus datos en nuestra base de datos para futuras convocatorias. ¡Te deseamos el mayor de los éxitos en tu futuro laboral!</p>
                </c:otherwise>
            </c:choose>
            <div style="margin-top: 15px;">
                <a href="/main" style="color: #007bff; text-decoration: none; font-weight: bold;">Volver al Inicio</a>
            </div>
        </div>
    </div>
</body>
</html>