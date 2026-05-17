<!--@CODEX--!>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Gestión de Usuarios</title>
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
                        Ingresar Nuevo Usuario
                    </button>
                </div>
                <h2 class="section-title">Administración de Usuarios</h2>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                        <tr>
                            <th>Correo</th>
                            <th>Contraseña</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="e" items="${listaUsuarios}">
                            <tr>
                                <td class="fw-bold">${e.correo}</td>
                                <td class="text-description" >${e.contrasena}</td>
                                <td>
                                    <div class="action-buttons">
                                        <button class="btn-action btn-edit"
                                                onclick="llenarDatosModal('${e.id}', '${e.correo}', '${e.contrasena}')"
                                                data-bs-toggle="modal" data-bs-target="#modalUpdate">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <a href="/usuarios/eliminar/${e.id}" class="btn-action btn-delete"
                                           onclick="return confirm('¿Deseas eliminar este usuario?')">
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
        <form action="/usuarios/actualizar" method="POST" class="modal-content custom-modal">
            <div class="modal-header">
                <h5 class="modal-title">Actualizar Usuarios</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="hidden" name="id" id="upd_id">
                <div class="mb-3">
                    <label class="form-label">Correo</label>
                    <input type="text" name="correo" id="upd_correo" class="form-control" required>
                 </div>
                  <div class="mb-3">
                 <label class="form-label">Contraseña</label>
                 <input type="password" name="contrasena" id="upd_contrasena" class="form-control" required>
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
        <form action="/usuarios/guardar" method="POST" class="modal-content custom-modal">
            <div class="modal-body p-4">
                <div class="mb-3">
                    <label class="form-label">Correo</label>
                    <input type="text" name="correo" class="form-control" required placeholder="Ej: admin123@gmail.com">
                </div>
                <div class="mb-3">
                    <label class="form-label">Contraseña</label>
                    <input type="password" name="contrasena" class="form-control" required placeholder="Ej: admin@123">
                     </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-save w-100">GUARDAR</button>
            </div>
        </form>
    </div>
</div>

<script>
    function llenarDatosModal(id, correo, contrasena) {
        document.getElementById('upd_id').value = id;
        document.getElementById('upd_correo').value = correo;
        document.getElementById('upd_contrasena').value = contrasena;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>