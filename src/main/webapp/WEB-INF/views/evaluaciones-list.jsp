<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Evaluación de Postulantes</title>
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
            <div class="main-card bg-white shadow-sm p-4 rounded">
                <h2 class="section-title">Evaluación de Postulantes</h2>
                <div class="table-responsive">
                    <table class="table table-striped table-hover align-middle">
                        <thead>
                        <tr>
                            <th>ID Usuario</th>
                            <th>Nombre Postulante</th>
                            <th>Puesto</th>
                            <th>Evaluación</th>
                            <th>Estado</th>
                            <th>Descripción</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="eva" items="${listaEvaluaciones}">
                            <tr>
                                <td>${eva.idUser}</td>
                                <td>${eva.nombreUsuario}</td>
                                <td>${eva.nombrePuesto}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${eva.estado == 'ENTREVISTA' || eva.estado == 'APROBADO' || eva.estado == 'RECHAZADO'}">
                                            <span class="badge bg-info">${eva.puntaje} / 20</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">sin cita</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${eva.estado == 'EN EVALUACION'}">
                                            <span class="badge bg-warning text-dark">En Evaluación</span>
                                        </c:when>
                                        <c:when test="${eva.estado == 'APROBADO'}">
                                            <span class="badge bg-success">Aprobado</span>
                                        </c:when>
                                        <c:when test="${eva.estado == 'RECHAZADO'}">
                                            <span class="badge bg-danger">Rechazado</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">${eva.estado}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${eva.descripcion}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="/evaluaciones?agendarCita=${eva.idUser}" class="btn btn-sm btn-info text-white">
                                        Citar</a>
                                        <a href="/evaluaciones?editarEvaluacion=${eva.id}" class="btn btn-sm btn-secondary">
                                        Editar</a>
                                        <a href="/evaluaciones/aprobar/${eva.id}" class="btn btn-sm btn-success">
                                        Aprobar</a>
                                        <a href="/evaluaciones/rechazar/${eva.id}" class="btn btn-sm btn-danger">
                                        Rechazar</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${empty listaEvaluaciones}">
                    <p class="text-center text-muted mt-4">No hay evaluaciones registradas</p>
                </c:if>
                <c:if test="${mostrarAgendarCita}">
                    <div class="card mt-4 p-4 border-primary">
                        <h4>Agendar Entrevista para ${nombreUsuarioCita}</h4>
                        <form action="/evaluaciones/agendar-cita" method="POST">
                            <input type="hidden" name="idUser" value="${idUserCita}" />
                            <div class="mb-3">
                                <label class="form-label">Enlace de Google Meet</label>
                                <input type="url" name="linkMeet" class="form-control" required placeholder="https://meet.google.com/abc-defg-hij" />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Fecha y Hora Programada</label>
                                <input type="datetime-local" name="fechaHora" class="form-control" required />
                            </div>
                            <div class="d-flex justify-content-between">
                                <a href="/evaluaciones" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Agendar Entrevista</button>
                            </div>
                        </form>
                    </div>
                </c:if>

                <c:if test="${mostrarEditarEvaluacion}">
                    <div class="card mt-4 p-4 border-secondary">
                        <h4>Editar Evaluación de ${evaluacion.nombreUsuario}</h4>
                        <form action="/evaluaciones/actualizar" method="POST">
                            <input type="hidden" name="id" value="${evaluacion.id}" />
                            <input type="hidden" name="idUser" value="${evaluacion.idUser}" />
                            <input type="hidden" name="idPuesto" value="${evaluacion.idPuesto}" />
                            <div class="mb-3">
                                <label class="form-label">Puntaje</label>
                                <input type="number" name="puntaje" class="form-control" min="0" max="20" value="${evaluacion.puntaje}" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Descripción</label>
                                <textarea name="descripcion" class="form-control" rows="4" required>${evaluacion.descripcion}</textarea>
                            </div>
                            <!-- Estado no editable desde aquí: solo se modifica la evaluación (puntaje y descripción) -->
                            <div class="d-flex justify-content-between">
                                <a href="/evaluaciones" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Guardar cambios</button>
                            </div>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
