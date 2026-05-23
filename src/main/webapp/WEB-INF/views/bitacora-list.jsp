<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Gestión de Bitácora</title>
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


<div class="container-fluid mt-5">
    <div class="row justify-content-center">
        <div class="col-md-11">
            <div class="main-card">
            <div class="d-flex content-right">
                <button class="btn btn-dark btn-sm rounded-pill px-4" type="button"
                            data-bs-toggle="modal" data-bs-target="#modalGuardar">
                        Ingresar nuevo evento
                </button>
            </div>
                <h2 class="section-title">Administración de Bitacora</h2>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>ID Log</th>
                                <th>Operador (Usuario)</th>
                                <th>Postulante</th>
                                <th>Evento</th>
                                <th>Acción / Descripción</th>
                                <th>Fecha y Hora</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${listaBitacora}">
                                <tr>
                                    <td class="fw-bold" >${item.id}</td>
                                    <td >
                                        <c:choose>
                                            <c:when test="${not empty item.usuarioCorreo}">
                                                <c:out value="${item.usuarioCorreo}" />
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted" style="font-style: italic;">Sistema</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.reclutaNombre}">
                                                <c:out value="${item.reclutaNombre}" />
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.eventoNombre}">
                                                <c:out value="${item.eventoNombre}" />
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:out value="${item.accion}" />
                                    </td>
                                    <td>
                                        <c:out value="${item.fechaRegistro}" />
                                    </td>
                                </tr>
                            </c:forEach>
                            
                            <c:if test="${empty listaBitacora}">
                                <tr>
                                    <td colspan="6" class="text-center py-4 text-muted" style="border-bottom: 1px solid #2d3243;">
                                        No hay registros de auditoría en la bitácora actualmente.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            </div> 
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>