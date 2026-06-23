<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Control de Puestos</title>
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

<div class="container-fluid CRUD-CATALOGO">
    <div class="row min-vh-100">
        <div class="col-md-12 content-pane">
            <div class="container-fluid px-4 py-5">
                <div class="d-flex justify-content-between align-items-center mb-5 flex-wrap gap-3">
                    <h2 class="title-panel m-0"><i class="fas fa-briefcase me-2"></i> Mantenimiento de Categoría de Puestos</h2>
                    <button class="btn btn-add shadow-lg" data-bs-toggle="modal" data-bs-target="#modalAgregar">
                        <i class="fas fa-plus-circle me-2"></i>NUEVO PUESTO
                    </button>
                </div>

                <div class="table-responsive shadow-lg rounded-4 overflow-hidden">
                    <table class="table table-hover align-middle m-0 text-white">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre Puesto</th>
                                <th>Tipo</th>
                                <th>Modalidad</th>
                                <th>Horario</th>
                                <th>Sueldo/Pago</th>
                                <th>Estado</th>
                                <th class="text-center" colspan="2">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="puesto" items="${listaCatalogo}">
                                <tr>
                                    <td class="fw-bold text-accent">${puesto.id}</td>
                                    <td>${puesto.nombre}</td>
                                    <td><span class="badge bg-secondary">${puesto.tipo}</span></td>
                                    <td>${puesto.presRem}</td>
                                    <td>${puesto.horario}</td>
                                    <td class="text-success fw-bold">S/. ${puesto.pago}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${puesto.estado == 1}"><span class="badge bg-success">Activo</span></c:when>
                                            <c:otherwise><span class="badge bg-danger">Inactivo</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <button class="btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#modalActualizar"
                                                onclick="llenarDatosModal('${puesto.id}', '${puesto.nombre}', '${puesto.tipo}', '${puesto.descripcion}', '${puesto.presRem}', '${puesto.horario}', '${puesto.estado}', '${puesto.pago}')">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                    </td>
                                    <td>
                                        <a href="/catalogo/eliminar/${puesto.id}" class="btn-action btn-delete" onclick="return confirm('¿Eliminar este puesto?');">
                                            <i class="fas fa-trash-alt"></i>
                                        </a>
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
        <form action="/catalogo/guardar" method="POST" class="modal-content text-white">
            <div class="modal-header">
                <h5 class="modal-title"><i class="fas fa-folder-plus me-2"></i> Registrar Nuevo Puesto</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-shadow="modal" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Nombre del Puesto</label>
                        <input type="text" name="nombre" class="form-control" required placeholder="Ej. Asesor de Ventas">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo</label>
                        <input type="text" name="tipo" class="form-control" required placeholder="Ej. Call Center">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Modalidad (pres_rem)</label>
                        <select name="presRem" class="form-select" required>
                            <option value="Presencial">Presencial</option>
                            <option value="Remoto">Remoto</option>
                            <option value="Hibrido">Híbrido</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Horario</label>
                        <input type="text" name="horario" class="form-control" required placeholder="Ej. Lun-Vie 8am a 5pm">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Sueldo / Pago</label>
                        <input type="number" name="pago" class="form-control" required placeholder="1025">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Estado Inicial</label>
                        <select name="estado" class="form-select">
                            <option value="1">Activo</option>
                            <option value="0">Inactivo</option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-3">
                        <label class="form-label">Descripción de Requisitos y Experiencia</label>
                        <textarea name="descripcion" class="form-control" rows="3" required placeholder="Detalle de experiencia requerida..."></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-save w-100">GUARDAR PUESTO</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="modalActualizar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <form action="/catalogo/actualizar" method="POST" class="modal-content text-white">
            <input type="hidden" name="id" id="upd_id">
            <div class="modal-header">
                <h5 class="modal-title"><i class="fas fa-edit me-2"></i> Modificar Puesto</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Nombre del Puesto</label>
                        <input type="text" name="nombre" id="upd_nombre" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo</label>
                        <input type="text" name="tipo" id="upd_tipo" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Modalidad</label>
                        <select name="presRem" id="upd_presRem" class="form-select" required>
                            <option value="Presencial">Presencial</option>
                            <option value="Remoto">Remoto</option>
                            <option value="Hibrido">Híbrido</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Horario</label>
                        <input type="text" name="horario" id="upd_horario" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Sueldo / Pago</label>
                        <input type="number" name="pago" id="upd_pago" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Estado</label>
                        <select name="estado" id="upd_estado" class="form-select">
                            <option value="1">Activo</option>
                            <option value="0">Inactivo</option>
                        </select>
                    </div>
                    <div class="col-md-12 mb-3">
                        <label class="form-label">Descripción</label>
                        <textarea name="descripcion" id="upd_descripcion" class="form-control" rows="3" required></textarea>
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
    function llenarDatosModal(id, nombre, tipo, descripcion, presRem, horario, estado, pago) {
        document.getElementById('upd_id').value = id;
        document.getElementById('upd_nombre').value = nombre;
        document.getElementById('upd_tipo').value = tipo;
        document.getElementById('upd_descripcion').value = descripcion;
        document.getElementById('upd_presRem').value = presRem;
        document.getElementById('upd_horario').value = horario;
        document.getElementById('upd_estado').value = estado;
        document.getElementById('upd_pago').value = pago;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>