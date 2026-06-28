<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso | Control de Preguntas</title>
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

    <div class="container-fluid CRUD-CATALOGO" style="padding-top: 110px;">
        <div class="row min-vh-100">
            <div class="col-md-12 content-pane">
                <div class="container-fluid px-4">

                    <div class="d-flex justify-content-between align-items-center mb-5 flex-wrap gap-3">
                        <h2 class="title-panel m-0 text-white">Mantenimiento de Preguntas por Puesto</h2>
                        <button class="btn btn-save shadow-lg" data-bs-toggle="modal" data-bs-target="#modalAgregar">CONFIGURAR PREGUNTAS</button>
                    </div>

                    <div class="table-responsive shadow-lg rounded-4 overflow-hidden">
                        <table class="table table-hover align-middle m-0 text-white">
                            <thead>
                                <tr>
                                    <th>Puesto Laboral</th>
                                    <th>Pregunta 1</th>
                                    <th>Pregunta 2</th>
                                    <th>Pregunta 3</th>
                                    <th>Estado</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${listaPreguntas}">
                                    <tr>
                                        <td class="fw-bold text-accent">${item.NOMBREPUESTO}</td>
                                        <td>${item.PREG1}</td>
                                        <td>${item.PREG2}</td>
                                        <td>${item.PREG3}</td>
                                        <td>
                                            <span class="badge bg-success">${item.ESTADO}</span>
                                        </td>
                                        <td class="text-center">
                                            <div class="action-buttons">
                                                <button class="btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#modalActualizar"
                                                        onclick="llenarDatosModal('${item.ID}', '${item.ID_PUESTO}', '${item.PREG1}', '${item.PREG2}', '${item.PREG3}', '${item.PREG4}', '${item.PREG5}', '${item.PREG6}', '${item.PREG7}', '${item.PREG8}')">
                                                    <i class="fas fa-edit"></i>
                                                </button>
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
            <form action="/preguntas/guardar" method="POST" class="modal-content custom-modal text-white">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="fas fa-folder-plus me-2"></i> Registrar Nueva Configuración</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="mb-3">
                        <label class="form-label">Seleccione el Puesto Laboral</label>
                        <select name="idPuesto" class="form-select" required>
                            <option value="">-- Seleccionar --</option>
                            <c:forEach var="p" items="${listaPuestos}">
                                <option value="${p.ID}">${p.NOMBRE}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 1</label><input type="text" name="preg1" class="form-control" required placeholder="Obligatoria"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 2</label><input type="text" name="preg2" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 3</label><input type="text" name="preg3" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 4</label><input type="text" name="preg4" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 5</label><input type="text" name="preg5" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 6</label><input type="text" name="preg6" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 7</label><input type="text" name="preg7" class="form-control" placeholder="Opcional"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 8</label><input type="text" name="preg8" class="form-control" placeholder="Opcional"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-save w-100">GUARDAR CONFIGURACIÓN</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal fade" id="modalActualizar" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <form action="/preguntas/editar" method="POST" class="modal-content custom-modal text-white">
                <input type="hidden" name="id" id="upd_id">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="fas fa-edit me-2"></i> Modificar / Visualizar Preguntas</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="mb-3">
                        <label class="form-label">Puesto Laboral Asociado</label>
                        <select name="idPuesto" id="upd_idPuesto" class="form-select" required>
                            <option value="">-- Seleccionar --</option>
                            <c:forEach var="p" items="${listaPuestos}">
                                <option value="${p.ID}">${p.NOMBRE}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 1</label><input type="text" name="preg1" id="upd_preg1" class="form-control" required></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 2</label><input type="text" name="preg2" id="upd_preg2" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 3</label><input type="text" name="preg3" id="upd_preg3" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 4</label><input type="text" name="preg4" id="upd_preg4" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 5</label><input type="text" name="preg5" id="upd_preg5" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 6</label><input type="text" name="preg6" id="upd_preg6" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 7</label><input type="text" name="preg7" id="upd_preg7" class="form-control"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">Pregunta 8</label><input type="text" name="preg8" id="upd_preg8" class="form-control"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-save w-100">ACTUALIZAR CONFIGURACIÓN</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function llenarDatosModal(id, idPuesto, preg1, preg2, preg3, preg4, preg5, preg6, preg7, preg8) {
            document.getElementById('upd_id').value = id;
            document.getElementById('upd_idPuesto').value = idPuesto;
            document.getElementById('upd_preg1').value = preg1;
            document.getElementById('upd_preg2').value = preg2;
            document.getElementById('upd_preg3').value = preg3;
            document.getElementById('upd_preg4').value = preg4;
            document.getElementById('upd_preg5').value = preg5;
            document.getElementById('upd_preg6').value = preg6;
            document.getElementById('upd_preg7').value = preg7;
            document.getElementById('upd_preg8').value = preg8;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>