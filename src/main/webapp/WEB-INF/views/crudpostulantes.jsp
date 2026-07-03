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

    <div class="container mt-5 pt-5">
        <div class="mb-4">
            <h2 class="text-white">Control de Postulantes</h2>
            <p class="text-white-50 mb-0">
                <c:choose>
                    <c:when test="${resultadoSeleccionado == 'aprobados'}">Mostrando postulantes aprobados</c:when>
                    <c:when test="${resultadoSeleccionado == 'desaprobados'}">Mostrando postulantes desaprobados</c:when>
                    <c:otherwise>Listado general de postulantes</c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="row g-3 mb-4 align-items-end">
            <div class="col-md-5">
                <label for="puestoId" class="form-label text-white">Filtrar por puesto</label>
                <form id="filtroPuesto" method="get" action="/crudpostulantes">
                    <c:if test="${not empty resultadoSeleccionado}">
                        <input type="hidden" name="resultado" value="${resultadoSeleccionado}">
                    </c:if>
                    <select id="puestoId" name="puestoId" class="form-select" onchange="this.form.submit()">
                        <option value="">Todos los puestos</option>
                        <c:forEach var="puesto" items="${listaPuestos}">
                            <option value="${puesto.id}" ${puestoSeleccionado == puesto.id ? 'selected' : ''}>
                                ${puesto.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </form>
            </div>
            <div class="col-md-7">
                <label class="form-label text-white d-block">Filtrar por resultado</label>
                <div class="btn-group" role="group">
                    <c:url var="urlTodos" value="/crudpostulantes">
                        <c:if test="${not empty puestoSeleccionado}">
                            <c:param name="puestoId" value="${puestoSeleccionado}"/>
                        </c:if>
                    </c:url>
                    <c:url var="urlAprobados" value="/crudpostulantes">
                        <c:param name="resultado" value="aprobados"/>
                        <c:if test="${not empty puestoSeleccionado}">
                            <c:param name="puestoId" value="${puestoSeleccionado}"/>
                        </c:if>
                    </c:url>
                    <c:url var="urlDesaprobados" value="/crudpostulantes">
                        <c:param name="resultado" value="desaprobados"/>
                        <c:if test="${not empty puestoSeleccionado}">
                            <c:param name="puestoId" value="${puestoSeleccionado}"/>
                        </c:if>
                    </c:url>
                    <a href="${urlTodos}" class="btn btn-outline-light ${empty resultadoSeleccionado ? 'active' : ''}">Todos</a>
                    <a href="${urlAprobados}" class="btn btn-outline-success ${resultadoSeleccionado == 'aprobados' ? 'active' : ''}">Aprobados</a>
                    <a href="${urlDesaprobados}" class="btn btn-outline-danger ${resultadoSeleccionado == 'desaprobados' ? 'active' : ''}">Desaprobados</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="table-responsive bg-dark p-3 rounded shadow">
                    <table class="table table-dark table-hover align-middle">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>DNI</th>
                                <th>Postulante</th>
                                <th>Puesto</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="postulante" items="${listaPostulantes}">
                                <tr>
                                    <td>${postulante.id}</td>
                                    <td>${postulante.dni}</td>
                                    <td class="fw-bold">${postulante.nombre}</td>
                                    <td class="text-accent">${postulante.nombrePuesto}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${postulante.estado == 'APROBADO'}">
                                                <span class="badge bg-success">${postulante.estado}</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'RECHAZADO'}">
                                                <span class="badge bg-danger">${postulante.estado}</span>
                                            </c:when>
                                            <c:when test="${postulante.estado == 'ENTREVISTA'}">
                                                <span class="badge bg-info">${postulante.estado}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">${postulante.estado}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${empty listaPostulantes}">
                        <p class="text-center text-white-50 mb-0 py-3">No hay postulantes con los filtros seleccionados.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>