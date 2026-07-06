<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estado de Solicitud | Callypso Call</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/consultar-estado.css">
</head>
<body>
<div class="page-wrapper">
    <div class="result-card">
        <h3 class="text-center">Hola, ${postulante.nombre}</h3>
        <p class="text-center text-muted mb-4">Tus postulaciones registradas:</p>
        <c:forEach var="solicitud" items="${postulaciones}" varStatus="status">
            <div class="postulacion-item ${status.first ? 'active' : ''}">
                <div class="d-flex justify-content-between align-items-start mb-2">
                    <h5 class="mb-0">${solicitud.nombrePuesto}</h5>
                </div>
                <c:choose>
                    <c:when test="${solicitud.estado == 'ENTREVISTA'}">
                        <div class="text-warning fw-bold">Entrevista Programada</div>
                        <p class="mb-2">Fecha y Hora pactada: <strong>${solicitud.fecha_hora_entrevista}</strong></p>
                        <c:choose>
                            <c:when test="${solicitud.linkhabilitado == 1}">
                                <div class="alert alert-success py-2">
                                    <strong>¡El enlace ya está activo!</strong><br>
                                    <a href="${solicitud.link_meet}" target="_blank" class="btn btn-success btn-sm mt-2">Ingresar a la Entrevista</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-danger py-2 mb-0">
                                    El enlace se habilitará al llegar la fecha y hora indicada.
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${solicitud.estado == 'PENDIENTE EN EVALUACION'}">
                        <div class="text-info fw-bold">En Evaluación Manual</div>
                        <p class="mb-0">Tu postulación fue recibida y está siendo revisada por Recursos Humanos.</p>
                    </c:when>
                    <c:when test="${solicitud.estado == 'APROBADO'}">
                        <div class="text-success fw-bold">¡Felicitaciones! Proceso Aprobado</div>
                        <p class="mb-0">Tu perfil fue seleccionado para este puesto. Acércate a nuestra sede para continuar con tu contrato e inducción.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="text-danger fw-bold">Proceso Terminado</div>
                        <p class="mb-0">En esta oportunidad tu perfil no se adecuó a los criterios del puesto. Guardaremos tus datos para futuras convocatorias.</p>
                    </c:otherwise>
                </c:choose>
                <div class="mt-3 pt-2 border-top">
                    <p class="mb-1"><strong>Nota:</strong>
                        <c:choose>
                            <c:when test="${not empty solicitud.puntaje}">${solicitud.puntaje} / 20</c:when>
                            <c:otherwise>No disponible</c:otherwise>
                        </c:choose>
                    </p>
                    <p class="mb-1"><strong>Estado:</strong> ${solicitud.estado}</p>
                    <p class="mb-0"><strong>Descripción:</strong>
                        <c:choose>
                            <c:when test="${not empty solicitud.descripcion}">${solicitud.descripcion}</c:when>
                            <c:otherwise>Sin descripción registrada.</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </c:forEach>
        <div class="text-center mt-3 result-links">
            <a href="/postular">Postular a otro puesto</a>
            <span class="mx-2 text-muted">|</span>
            <a href="/main">Volver al Inicio</a>
        </div>
    </div>
</div>
</body>
</html>
