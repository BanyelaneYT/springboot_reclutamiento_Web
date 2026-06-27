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
        <a href="/gestion" class="logo">CallypsoCall</a>
        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="getstarted" href="/main">Cerrar Sesión</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="container-fluid CRUD-CATALOGO shadow-panel-recluta">
    <div class="row min-vh-100">
        <div class="col-md-12 content-pane">
            <div class="container-fluid px-4 py-5">
                <div class="d-flex justify-content-between align-items-center mb-5 flex-wrap gap-3">
                    <h2 class="title-panel m-0"><i class="fas fa-users-cog me-2"></i> Evaluación y Control de Candidatos</h2>
                </div>

                <div class="table-responsive shadow-lg rounded-4 overflow-hidden">
                    <table class="table table-hover align-middle m-0 text-white">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>DNI</th>
                                <th>Postulante</th>
                                <th>Edad</th>
                                <th>Puesto Solicitado</th>
                                <th>Estado de Solicitud</th>
                                <th class="text-center">Cambiar Estado</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="postulante" items="${listaPostulantes}">
                                <tr>
                                    <td class="fw-bold text-accent">${postulante.id}</td>
                                    <td>${postulante.dni}</td>
                                    <td class="fw-bold">${postulante.nombre}</td>
                                    <td>${postulante.edad} años</td>
                                    <td><span class="badge bg-dark">${postulante.nombrePuesto}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${postulante.estado == 'PENDIENTE EN EVALUACION'}">
                                                <span class="badge bg-warning text-dark"><i class="fas fa-clock me-1"></i>En Evaluación</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'ENTREVISTA'}">
                                                <span class="badge bg-info text-dark"><i class="fas fa-calendar-alt me-1"></i>Citado a Entrevista</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'APROBADO'}">
                                                <span class="badge bg-success"><i class="fas fa-check-circle me-1"></i>Aprobado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger"><i class="fas fa-times-circle me-1"></i>Rechazado</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="d-flex justify-content-center gap-2">
                                            <a href="/crudpostulantes/estado/${postulante.id}/entrevista" class="btn btn-sm btn-outline-info text-white">
                                                <i class="fas fa-video me-1"></i> Citar
                                            </a>
                                            <a href="/crudpostulantes/estado/${postulante.id}/aprobar" class="btn btn-sm btn-outline-success text-white">
                                                <i class="fas fa-check me-1"></i> Aprobar
                                            </a>
                                            <a href="/crudpostulantes/estado/${postulante.id}/rechazar" class="btn btn-sm btn-outline-danger text-white">
                                                <i class="fas fa-ban me-1"></i> Rechazar
                                            </a>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="/crudpostulantes/eliminar/${postulante.id}"
                                               class="btn-action btn-delete"
                                               onclick="return confirm('¿Deseas eliminar permanentemente esta postulación?');">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaPostulantes}">
                                <tr>
                                    <td colspan="8" class="text-center py-4 text-muted">No hay postulaciones registradas en este momento.</td>
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