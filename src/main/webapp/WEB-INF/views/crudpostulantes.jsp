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
                                <th class="text-center">Acciones</th>
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
                                    <td class="text-center">
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-sm btn-info text-white btn-citar" data-bs-toggle="modal" data-bs-target="#citaModal" data-id="${postulante.id}" data-name="${postulante.nombre}">Citar</button>
                                            <a href="/crudpostulantes/estado/${postulante.id}/aprobar" class="btn btn-sm btn-success">Aprobar</a>
                                            <a href="/crudpostulantes/estado/${postulante.id}/rechazar" class="btn btn-sm btn-danger">Rechazar</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="citaModal" tabindex="-1" aria-labelledby="citaModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content bg-dark text-white border-secondary">
                <div class="modal-header border-bottom border-secondary">
                    <h5 class="modal-title" id="citaModalLabel">Agendar Entrevista</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <form action="/crudpostulantes/agendar-cita" method="POST">
                    <div class="modal-body">
                        <input type="hidden" id="idUserInput" name="idUser" value="" />
                        <div class="mb-3">
                            <label class="form-label">Enlace de Google Meet</label>
                            <input type="url" name="linkMeet" class="form-control bg-secondary text-white border-0" required placeholder="https://meet.google.com/abc-defg-hij" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Fecha y Hora Programada</label>
                            <input type="datetime-local" name="fechaHora" class="form-control bg-secondary text-white border-0" required />
                        </div>
                    </div>
                    <div class="modal-footer border-top border-secondary">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-save">AGENDAR ENTREVISTA</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>