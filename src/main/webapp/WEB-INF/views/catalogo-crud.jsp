<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Gestión de Eventos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/css/catcrud.css">
</head>
<body>

<header id="header">
    <div class="container d-flex justify-content-between align-items-center">
        <h1 class="logo">CallypsoCall</h1>
        <a href="/main" class="btn btn-outline-light btn-sm rounded-pill px-4">Volver al Inicio</a>
    </div>
</header>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-11">
            <div class="main-card">
                <div class="d-flex content-right">
                    <button class="btn btn-dark btn-sm rounded-pill px-4" type="button"
                            data-bs-toggle="modal" data-bs-target="#modalGuardar">
                        Ingresar nuevo evento
                    </button>
                </div>
                <h2 class="section-title">Administración de Eventos</h2>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Tipo</th>
                            <th>Fecha</th>
                            <th>Descripción</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="e" items="${listaCatalogo}">
                            <tr>
                                <td class="fw-bold">${e.nombre}</td>
                                <td><span class="badge-tipo">${e.tipo}</span></td>
                                <td class="text-description" >${e.fecha}</td>
                                <td class="text-description" >${e.descripcion}</td>
                                <td>
                                    <div class="action-buttons">
                                        <button class="btn-action btn-edit"
                                                onclick="llenarDatosModal('${e.id}', '${e.nombre}', '${e.tipo}', '${e.fecha}', '${e.descripcion}')"
                                                data-bs-toggle="modal" data-bs-target="#modalUpdate">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <a href="/catalogo/eliminar/${e.id}" class="btn-action btn-delete"
                                           onclick="return confirm('¿Deseas eliminar este evento?')">
                                            <i class="fas fa-trash-alt"></i>
                                        </a>
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

<div class="modal fade" id="modalUpdate" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <form action="/catalogo/actualizar" method="POST" class="modal-content custom-modal">
            <div class="modal-header">
                <h5 class="modal-title">Actualizar Evento</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="hidden" name="id" id="upd_id">
                <div class="mb-3">
                    <label class="form-label">Nombre del Evento</label>
                    <input type="text" name="nombre" id="upd_nombre" class="form-control" required>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo</label>
                        <select name="tipo" id="upd_tipo" class="form-select">
                            <option value="Motivacion">Motivación</option>
                            <option value="Reclutamiento">Reclutamiento</option>
                            <option value="Festividad">Festividad</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Fecha</label>
                        <input type="date" name="fecha" id="upd_fecha" class="form-control" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <textarea name="descripcion" id="upd_descripcion" class="form-control" rows="3"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-save w-100">GUARDAR CAMBIOS</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="modalGuardar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <form action="/eventos/guardar" method="POST" class="modal-content custom-modal">
            <div class="modal-body p-4">
                <div class="mb-3">
                    <label class="form-label">Nombre del Evento</label>
                    <input type="text" name="nombre" class="form-control" required placeholder="Ej: Reclutamiento Masivo L1">
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tipo</label>
                        <select name="tipo" class="form-select">
                            <option value="Reclutamiento">Reclutamiento Presencial</option>
                            <option value="Motivacion">Experiencia Gratuita / Motivación</option>
                            <option value="Festividad">Festividad (Navidad, Halloween, etc.)</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Fecha</label>
                        <input type="date" name="fecha" class="form-control" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <textarea name="descripcion" class="form-control" rows="3"  placeholder="Detalles del evento..."></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-save w-100">GUARDAR</button>
            </div>
        </form>
    </div>
</div>

<script>
    function llenarDatosModal(id, nombre, tipo, fecha, descripcion) {
        document.getElementById('upd_id').value = id;
        document.getElementById('upd_nombre').value = nombre;
        document.getElementById('upd_tipo').value = tipo;
        document.getElementById('upd_fecha').value = fecha;
        document.getElementById('upd_descripcion').value = descripcion;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>