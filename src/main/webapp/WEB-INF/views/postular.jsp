<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso Call | Postulación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/contacto.css">
    <link rel="stylesheet" href="/css/postular.css">
</head>
<body>
<div class="container mt-5">
    <div class="form-container p-5 shadow-lg bg-dark text-white rounded">
        <h2 class="text-center mb-4">FICHA DE POSTULACIÓN TÉCNICA</h2>

        <form action="/postular" method="GET" class="mb-4">
            <div class="mb-3">
                <label class="text-white fw-bold">Puesto al que postula:</label>
                <div class="input-group">
                    <select name="puestoId" class="form-select" required>
                        <option value="">-- Selecciona un puesto --</option>
                        <c:forEach var="puesto" items="${listaCatalogo}">
                            <option value="${puesto.id}" ${puesto.id == puestoSeleccionadoId ? 'selected' : ''}>
                                ${puesto.nombre} (${puesto.presRem})
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-warning">Cargar Preguntas</button>
                </div>
            </div>
        </form>

        <form action="/reclutar/guardar" method="POST">
            <input type="hidden" name="puesto" value="${puestoSeleccionadoId}">

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Nombres Completos</label>
                    <input type="text" class="form-control" name="nombre" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">DNI</label>
                    <input type="number" class="form-control" name="dni" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Edad</label>
                    <input type="number" class="form-control" name="edad" required>
                </div>
            </div>

            <c:if test="${not empty puestoSeleccionadoId}">
                <div id="seccionPreguntas" class="mt-4">
                    <h4 class="text-warning mb-3">Evaluación Específica del Puesto</h4>
                    <div class="row">

                        <c:choose>
                            <c:when test="${not empty preguntasPuesto}">

                                <%-- Evaluamos e imprimimos cada pregunta si existe en el objeto --%>
                                <c:if test="${not empty preguntasPuesto.preg1}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">1. ${preguntasPuesto.preg1}</label>
                                            <input type="text" name="res1" class="form-control" placeholder="Escriba su respuesta aquí..." required>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg2}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">2. ${preguntasPuesto.preg2}</label>
                                            <input type="text" name="res2" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg3}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">3. ${preguntasPuesto.preg3}</label>
                                            <input type="text" name="res3" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg4}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">4. ${preguntasPuesto.preg4}</label>
                                            <input type="text" name="res4" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg5}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">5. ${preguntasPuesto.preg5}</label>
                                            <input type="text" name="res5" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg6}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">6. ${preguntasPuesto.preg6}</label>
                                            <input type="text" name="res6" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg7}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">7. ${preguntasPuesto.preg7}</label>
                                            <input type="text" name="res7" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty preguntasPuesto.preg8}">
                                    <div class="col-md-6 mb-3">
                                        <div class="p-3 bg-secondary rounded text-white">
                                            <label class="form-label fw-bold">8. ${preguntasPuesto.preg8}</label>
                                            <input type="text" name="res8" class="form-control" placeholder="Escriba su respuesta aquí...">
                                        </div>
                                    </div>
                                </c:if>

                            </c:when>
                            <c:otherwise>
                                <p class="text-muted ps-3">Este puesto no cuenta con preguntas de evaluación registradas.</p>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </c:if>

            <button type="submit" class="btn btn-danger w-100 py-3 mt-4">ENVIAR POSTULACIÓN</button>
        </form>
    </div>
</div>
</body>
</html>