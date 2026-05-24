<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Control de Postulantes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/css/catcrud.css">
    <link rel="stylesheet" href="/css/Header.css">
</head>
<body>
<header id="header" class="fixed-top d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
        <a href="/gestion" class="logo">
            CallypsoCall
        </a>
        
        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="getstarted" href="/main">Cerrar Sesión</a></li>
            </ul>
        </nav>
    </div>
</header>
<!-- Fin Header -->
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-12">
            <div class="main-card">
                <h2 class="section-title">Control de Postulantes</h2>
                <p class="text-center text-muted mb-4">Lista de aspirantes en base a las respuestas del cuestionario técnico.</p>

                <div class="table-responsive">
                    <table class="table table-striped table-hover table-bordered align-middle text-center mt-3">
                        <thead class="table-light">
                            <tr>
                                <th>DNI</th>
                                <th>Nombre Completo</th>
                                <th>Edad</th>
                                <th>Ubicación</th>
                                <th>Respuestas [1-8]</th>
                                <th>Estado</th>
                                <th>Procesamiento</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="postulante" items="${listaPostulantes}">
                                <tr>
                                    <td>${postulante.dni}</td>
                                    <td class="text-start fw-bold">${postulante.nombre}</td>
                                    <td>${postulante.edad} años</td>
                                    <td>${postulante.ubicacion}</td>
                                    <td>
                                        <small class="text-muted">
                                            [${postulante.res1}, ${postulante.res2}, ${postulante.res3}, ${postulante.res4},
                                             ${postulante.res5}, ${postulante.res6}, ${postulante.res7}, ${postulante.res8}]
                                        </small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${postulante.estado == 'APROBADO'}">
                                                <span class="badge-aprobado"><i class="fas fa-check-circle me-1"></i> APROBADO</span> </c:when>
                                            <c:when test="${postulante.estado == 'ENTREVISTA'}">
                                                <span class="badge-entrevista"><i class="fas fa-calendar-alt me-1"></i> ENTREVISTA</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'RECHAZADO'}">
                                                <span class="badge-rechazado"><i class="fas fa-user-times me-1"></i> RECHAZADO</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'PENDIENTE EN EVALUACION'}">
                                                <span class="badge-pendiente-eval"><i class="fas fa-hourglass-half me-1"></i> PEND. EVALUACIÓN</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-desaprobado"><i class="fas fa-times-circle me-1"></i> RECHAZADO</span> </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <div class="d-flex flex-column align-items-center">
                                            <a href="/crudpostulantes/estado/${postulante.id}/entrevista"
                                               class="btn btn-primary btn-procesar btn-sm w-100">
                                               <i class="fas fa-user-check me-1"></i> Generar Entrevista
                                            </a>
                                            <a href="/crudpostulantes/estado/${postulante.id}/rechazar"
                                               class="btn btn-secondary btn-procesar btn-sm w-100"
                                               onclick="return confirm('¿Seguro que deseas rechazar a este postulante?');">
                                               <i class="fas fa-ban me-1"></i> Rechazar
                                            </a>
                                            <a href="/crudpostulantes/estado/${postulante.id}/aprobar"
                                               class="btn btn-success btn-procesar btn-sm w-100">
                                               <i class="fas fa-check me-1"></i> Aprobar
                                            </a>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="/crudpostulantes/eliminar/${postulante.id}"
                                               class="btn-action btn-delete"
                                               onclick="return confirm('¿Deseas eliminar la postulación de este candidato?');">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaPostulantes}">
                                <tr>
                                    <td colspan="9" class="text-center py-4 text-muted">No hay postulaciones registradas en este momento.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>