<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Gestión de Citas</title>
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

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-11">
            <div class="main-card">
                <div class="d-flex content-right">
                    <button class="btn btn-dark btn-sm rounded-pill px-4" type="button"
                            data-bs-toggle="modal" data-bs-target="#modalGuardar">
                        Nueva Cita
                    </button>
                </div>
                <h2 class="section-title">Gestión de Citas de Entrevista</h2>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                        <tr>
                            <th>ID Cita</th>
                            <th>Usuario</th>
                            <th>Fecha y Hora</th>
                            <th>Link Google Meet</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="cita" items="${listaCitas}">
                            <tr>
                                <td>${cita.id}</td>
                                <td>${cita.nombreUsuario}</td>
                                <td>${cita.fechaHoraEntrevista}</td>
                                <td>
                                    <a href="${cita.linkMeet}" target="_blank" class="btn btn-sm btn-info">
                                        <i class="fas fa-video"></i> Ir a Meet
                                    </a>
                                </td>
                                <td>
                                    <button class="btn btn-warning btn-sm" type="button"
                                            data-bs-toggle="modal" data-bs-target="#modalEditar"
                                            onclick="cargarEdicion(${cita.id}, '${cita.linkMeet}', '${cita.fechaHoraEntrevista}')">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <a href="/citas/eliminar?id=${cita.id}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('¿Está seguro de eliminar esta cita?')">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${empty listaCitas}">
                    <p class="text-center text-muted mt-4">No hay citas registradas</p>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Guardar -->
<div class="modal fade" id="modalGuardar" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Nueva Cita de Entrevista</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="/citas/guardar">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="idUser" class="form-label">ID Usuario</label>
                        <input type="number" class="form-control" id="idUser" name="idUser" required>
                    </div>
                    <div class="mb-3">
                        <label for="linkMeet" class="form-label">Link Google Meet</label>
                        <input type="url" class="form-control" id="linkMeet" name="linkMeet" placeholder="https://meet.google.com/..." required>
                    </div>
                    <div class="mb-3">
                        <label for="fechaHoraEntrevista" class="form-label">Fecha y Hora</label>
                        <input type="datetime-local" class="form-control" id="fechaHoraEntrevista" name="fechaHoraEntrevista" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal para Editar -->
<div class="modal fade" id="modalEditar" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Editar Cita de Entrevista</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="/citas/actualizar">
                <div class="modal-body">
                    <input type="hidden" id="editId" name="id">
                    <div class="mb-3">
                        <label for="editLinkMeet" class="form-label">Link Google Meet</label>
                        <input type="url" class="form-control" id="editLinkMeet" name="linkMeet" placeholder="https://meet.google.com/..." required>
                    </div>
                    <div class="mb-3">
                        <label for="editFechaHoraEntrevista" class="form-label">Fecha y Hora</label>
                        <input type="datetime-local" class="form-control" id="editFechaHoraEntrevista" name="fechaHoraEntrevista" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function cargarEdicion(id, linkMeet, fechaHora) {
        document.getElementById('editId').value = id;
        document.getElementById('editLinkMeet').value = linkMeet;
        document.getElementById('editFechaHoraEntrevista').value = fechaHora.replace(' ', 'T');
    }
</script>
</body>
</html>
