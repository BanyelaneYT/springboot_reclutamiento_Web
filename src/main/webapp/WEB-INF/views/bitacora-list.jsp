<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Panel de Auditoría</title>
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

<div class="container-fluid content-container" style="margin-top: 100px; padding: 0 40px;">
    <div class="row">
        <div class="col-12">
            <div class="card-body-postulante p-4">

                <div class="table-responsive">
                    <table class="table table-dark table-hover align-middle m-0" id="tablaBitacora">
                        <thead>
                            <tr>
                                <th style="width: 8%">ID Log</th>
                                <th style="width: 22%"><i class="fa-solid fa-user me-2"></i>Operador (Usuario)</th>
                                <th style="width: 22%"><i class="fa-solid fa-address-card me-2"></i>Postulante</th>
                                <th style="width: 20%"><i class="fa-solid fa-bolt me-2"></i>Evento</th>
                                <th><i class="fa-solid fa-paragraph me-2"></i>Acción / Descripción</th>
                                <th style="width: 15%"><i class="fa-solid fa-calendar me-2"></i>Fecha y Hora</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${listaBitacora}">
                                <tr>
                                    <td class="fw-bold" style="color: #a0aec0;">#${item.id}</td>
                                    
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.usuarioCorreo}">
                                                <span class="badge px-3 py-2 rounded-pill" style="background-color: #2b6cb0; color: #ebf8ff;">
                                                    <i class="fa-solid fa-user-shield me-1"></i>${item.usuarioCorreo}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted small" style="font-style: italic;">Sistema</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.reclutaNombre}">
                                                <span class="badge px-3 py-2 rounded-pill" style="background-color: #2c7a7b; color: #e6fffa;">
                                                    <i class="fa-solid fa-user-tag me-1"></i>${item.reclutaNombre}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted small">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.eventoNombre}">
                                                <span class="badge px-3 py-2 rounded" style="background-color: #b7791f; color: #fefcbf;">
                                                    <i class="fa-solid fa-flag me-1"></i>${item.eventoNombre}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted small">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <span class="text-white fw-semibold">${item.accion}</span>
                                    </td>

                                    <td style="color: #cbd5e0; font-size: 0.9rem;">
                                        <i class="fa-regular fa-clock me-1" style="color: #a0aec0;"></i> ${item.fechaRegistro}
                                    </td>
                                </tr>
                            </c:forEach>
                            
                            <c:if test="${empty listaBitacora}">
                                <tr>
                                    <td colspan="6" class="text-center py-5 text-muted">
                                        <i class="fa-solid fa-folder-open fa-2x mb-2 d-block"></i>
                                        No se encontraron registros de auditoría en la bitácora.
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>