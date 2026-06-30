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

        <c:if test="${param.success != null}">
            <div class="alert alert-success">
                ¡Entrevista agendada correctamente!
            </div>
        </c:if>
        <div class="row">
            <div class="${(not empty respuestasSeleccionadas || not empty idPostulanteCitar) ? 'col-md-7' : 'col-md-12'}">
                <div class="table-responsive bg-dark p-3 rounded shadow">
                    <table class="table table-dark table-hover align-middle">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>DNI</th>
                                <th>Postulante</th>
                                <th>Puesto</th>
                                <th>Cuestionario</th>
                                <th>Estado</th>
                                <th>Respuestas</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="postulante" items="${listaPostulantes}">
                                <tr class="${idPostulanteVer == postulante.id || idPostulanteCitar == postulante.id ? 'table-active' : ''}">
                                    <td>${postulante.id}</td>
                                    <td>${postulante.dni}</td>
                                    <td class="fw-bold">${postulante.nombre}</td>
                                    <td class="text-accent">${postulante.nombrePuesto}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${postulante.idQuest > 0}">
                                                <span class="badge bg-secondary">ID: ${postulante.idQuest}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted small">No aplica</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="badge bg-warning text-dark">${postulante.estado}</span></td>
                                    <td>
                                        <a href="/crudpostulantes?verRespuestasId=${postulante.id}" class="btn btn-sm btn-primary">
                                            <i class="fas fa-eye"></i> Ver Respuestas
                                        </a>
                                    </td>
                                    <td class="text-center">
                                        <div class="btn-group">
                                            <a href="/crudpostulantes?citarId=${postulante.id}" class="btn btn-sm btn-info text-white">Citar</a>
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

            <c:if test="${not empty respuestasSeleccionadas}">
                <div class="col-md-5">
                    <div class="card bg-dark text-white p-4 rounded shadow border border-secondary">
                        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom border-secondary pb-2">
                            <h4 class="text-accent m-0">Respuestas (ID: ${idPostulanteVer})</h4>
                            <a href="/crudpostulantes" class="btn-close btn-close-white"></a>
                        </div>
                        <div style="max-height: 450px; overflow-y: auto;" class="pe-2">
                            <c:forEach var="i" begin="1" end="8">
                                <c:set var="pregKey" value="PREG${i}"/>
                                <c:set var="resKey" value="RES${i}"/>
                                <c:if test="${not empty respuestasSeleccionadas[pregKey]}">
                                    <div class="mb-3 bg-secondary p-2 rounded">
                                        <p class="mb-1 text-warning small"><strong>Pregunta ${i}:</strong> ${respuestasSeleccionadas[pregKey]}</p>
                                        <p class="mb-0 text-white"><strong>R:</strong> ${respuestasSeleccionadas[resKey]}</p>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty idPostulanteCitar}">
                <div class="col-md-5">
                    <div class="card bg-dark text-white p-4 rounded shadow border border-secondary">
                        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom border-secondary pb-2">
                            <h4 class="text-success m-0">Agendar Entrevista (ID: ${idPostulanteCitar})</h4>
                            <a href="/crudpostulantes" class="btn-close btn-close-white"></a>
                        </div>
                        <form action="/crudpostulantes/agendar-cita" method="POST">
                            <input type="hidden" name="idUser" value="${idPostulanteCitar}" required>
                            <div class="mb-3">
                                <label class="form-label">Enlace de Google Meet</label>
                                <input type="url" name="linkMeet" class="form-control bg-secondary text-white border-0" required placeholder="https://meet.google.com/abc-defg-hij">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Fecha y Hora Programada</label>
                                <input type="datetime-local" name="fechaHora" class="form-control bg-secondary text-white border-0" required>
                            </div>
                            <button type="submit" class="btn btn-save w-100 mt-3">AGENDAR ENTREVISTA</button>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>