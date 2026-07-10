<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Categorías de Puestos</title>
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

    <div class="container-fluid CRUD-CATEGORIA" style="padding-top: 110px;">
        <div class="row min-vh-100">
            <div class="col-md-12 content-pane">
                <div class="container-fluid px-4">

                    <div class="d-flex justify-content-between align-items-center mb-5 flex-wrap gap-3">
                        <h2 class="title-panel m-0 text-white">Catálogo de Categorías</h2>
                        <button class="btn btn-save shadow-lg" data-bs-toggle="modal" data-bs-target="#modalAgregar">NUEVA CATEGORÍA</button>
                    </div>

                    <c:if test="${param.error == 'EN_USO'}">
                        <div class="alert alert-warning mb-4">
                            No se puede desactivar una categoría que tiene puestos asociados. Reasigna o desactiva los puestos primero.
                        </div>
                    </c:if>

                    <div class="table-responsive shadow-lg rounded-4 overflow-hidden">
                        <table class="table table-hover align-middle m-0 text-white">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Estado</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cat" items="${listaCategorias}">
                                    <tr>
                                        <td class="fw-bold text-id">${cat.id}</td>
                                        <td>${cat.nombre}</td>
                                        <td>${cat.descripcion}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${cat.estado == 1}"><span class="badge bg-success">Activa</span></c:when>
                                                <c:otherwise><span class="badge bg-danger">Inactiva</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <div class="action-buttons">
                                                <button class="btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#modalActualizar"
                                                        onclick="llenarDatosModal('${cat.id}', '${cat.nombre}', '${cat.descripcion}', '${cat.estado}')"
                                                        title="Editar">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <c:choose>
                                                    <c:when test="${cat.estado == 1}">
                                                        <form action="/categorias/cambiar-estado" method="POST" class="d-inline"
                                                              onsubmit="return confirm('¿Desactivar esta categoría?');">
                                                            <input type="hidden" name="id" value="${cat.id}">
                                                            <input type="hidden" name="estado" value="0">
                                                            <button type="submit" class="btn-action btn-delete" title="Desactivar">
                                                                <i class="fas fa-toggle-off"></i>
                                                            </button>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form action="/categorias/cambiar-estado" method="POST" class="d-inline"
                                                              onsubmit="return confirm('¿Activar esta categoría?');">
                                                            <input type="hidden" name="id" value="${cat.id}">
                                                            <input type="hidden" name="estado" value="1">
                                                            <button type="submit" class="btn-action btn-activate" title="Activar">
                                                                <i class="fas fa-toggle-on"></i>
                                                            </button>
                                                        </form>
                                                    </c:otherwise>
                                                </c:choose>
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
    </div>

    <div class="modal fade" id="modalAgregar" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <form action="/categorias/guardar" method="POST" class="modal-content custom-modal text-white">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="fas fa-tags me-2"></i> Registrar Nueva Categoría</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Nombre de la Categoría</label>
                            <input type="text" name="nombre" class="form-control" required placeholder="Ej. Call Center">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Estado Inicial</label>
                            <select name="estado" class="form-select">
                                <option value="1">Activa</option>
                                <option value="0">Inactiva</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Descripción</label>
                            <textarea name="descripcion" class="form-control" rows="3" placeholder="Descripción de la categoría..."></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-save w-100">GUARDAR CATEGORÍA</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal fade" id="modalActualizar" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <form action="/categorias/actualizar" method="POST" class="modal-content custom-modal text-white">
                <input type="hidden" name="id" id="upd_id">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="fas fa-edit me-2"></i> Modificar Categoría</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Nombre de la Categoría</label>
                            <input type="text" name="nombre" id="upd_nombre" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Estado</label>
                            <select name="estado" id="upd_estado" class="form-select">
                                <option value="1">Activa</option>
                                <option value="0">Inactiva</option>
                            </select>
                        </div>
                        <div class="col-md-12 mb-3">
                            <label class="form-label">Descripción</label>
                            <textarea name="descripcion" id="upd_descripcion" class="form-control" rows="3"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-save w-100">ACTUALIZAR CAMBIOS</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function llenarDatosModal(id, nombre, descripcion, estado) {
            document.getElementById('upd_id').value = id;
            document.getElementById('upd_nombre').value = nombre;
            document.getElementById('upd_descripcion').value = descripcion;
            document.getElementById('upd_estado').value = estado;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
