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
            <h2 class="text-white">Evaluación y Control de Candidatos</h2>
        </div>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <c:choose>
                    <c:when test="${param.error == 'idInvalido'}">ID de postulante inválido.</c:when>
                    <c:when test="${param.error == 'linkInvalido'}">El enlace de Meet es obligatorio.</c:when>
                    <c:when test="${param.error == 'fechaInvalida'}">La fecha y hora son obligatorias.</c:when>
                    <c:when test="${param.error == 'errorGeneral'}">Error al agendar la cita. Inténtalo nuevamente.</c:when>
                    <c:otherwise>Error desconocido.</c:otherwise>
                </c:choose>
            </div>
        </c:if>
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
                                <tr class="${idPostulanteCitar == postulante.id ? 'table-active' : ''}">
                                    <td>${postulante.id}</td>
                                    <td>${postulante.dni}</td>
                                    <td class="fw-bold">${postulante.nombre}</td>
                                    <td class="text-accent">${postulante.nombrePuesto}</td>
                                    <td><span class="badge bg-warning text-dark">${postulante.estado}</span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>