<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callypso Call | Postulación</title>

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <link rel="stylesheet" href="/css/Header.css">
    <link rel="stylesheet" href="/css/contacto.css">

    <style>
        body {
            background-color: #1d1d1d;
            font-family: 'Poppins', sans-serif;
            color: #fff;
        }
        .page-title {
            font-family: 'Raleway', sans-serif;
            font-weight: 800;
            letter-spacing: 2px;
            color: #fff;
            margin-bottom: 10px;
        }
        /* Estilización de inputs para que luzcan idénticos a los de contacto.jsp */
        .form-container input[type="text"],
        .form-container input[type="number"],
        .form-container select {
            width: 100%;
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-bottom: 2px solid rgba(255, 255, 255, 0.2);
            padding: 12px 15px;
            color: #fff;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-container input:focus,
        .form-container select:focus {
            background: rgba(255, 255, 255, 0.1);
            outline: none;
            box-shadow: none;
        }
        .form-container select option {
            background: #1d1d1d;
            color: #fff;
        }
        .question-box {
            background: rgba(255, 255, 255, 0.03);
            padding: 20px;
            border-radius: 6px;
            height: 100%;
        }
        .question-label {
            font-size: 14px;
            font-weight: 600;
            color: #e5e5e5;
            margin-bottom: 10px;
            display: block;
        }
        .btn-submit-call {
            background: #e03a3c;
            color: #fff;
            border: none;
            padding: 15px 30px;
            font-family: 'Raleway', sans-serif;
            font-weight: 700;
            font-size: 15px;
            letter-spacing: 1px;
            border-radius: 4px;
            width: 100%;
        }
        .btn-submit-call:hover {
            background: #ca2a2c;
        }
    </style>
</head>
<body>

<header id="header" class="fixed-top d-flex align-items-center">
    <div class="header-fullwidth d-flex align-items-center justify-content-between">
        <a href="/" class="logo">CallypsoCall</a>
        <nav id="navbar" class="navbar">
            <ul>
                <li><a href="/">Inicio</a></li>
                <li><a href="/evento">Convocatorias</a></li>
                <li><a href="/contacto">Contacto</a></li>
                <li><a href="/login" class="btn-login-header">Login</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="container" style="padding-top: 120px; padding-bottom: 60px;">
    <div class="text-center mb-5">
        <h2 class="page-title">FICHA DE POSTULACIÓN TÉCNICA</h2>
        <p style="color: rgba(255,255,255,0.6); font-size: 15px;">Completa tus datos personales y responde el cuestionario del puesto seleccionado.</p>
    </div>

    <div class="form-container m-auto" style="max-width: 950px; background: rgba(255,255,255,0.02); padding: 40px; border-radius: 8px; border: 1px solid rgba(255,255,255,0.05);">

        <form action="/postular" method="GET" class="mb-4">
            <div class="mb-3">
                <label class="fw-bold mb-2" style="color: #e03a3c; font-size: 14px; letter-spacing: 0.5px;">PUESTO AL QUE POSTULA:</label>
                <div class="input-group">
                    <select name="puestoId" onchange="this.form.submit()" required>
                        <option value="">-- Selecciona un puesto laboral para cargar las preguntas --</option>
                        <c:forEach var="puesto" items="${listaCatalogo}">
                            <option value="${puesto.id}" ${puesto.id == puestoSeleccionadoId ? 'selected' : ''}>
                                ${puesto.nombre} (${puesto.presRem} | ${puesto.tipo})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>

        <form action="/postular/guardar" method="POST">
            <input type="hidden" name="puesto" value="${puestoSeleccionadoId}">

            <h4 class="mb-4 pb-2 border-bottom border-secondary" style="font-family: 'Raleway', sans-serif; font-weight: 700; font-size: 18px;">
                <i class="fas fa-user me-2" style="color: #e03a3c;"></i>Datos Personales
            </h4>

            <div class="row g-4 mb-5">
                <div class="col-md-4">
                    <label class="form-label small text-white-50">Documento de Identidad (DNI)</label>
                    <input type="number" name="dni" required placeholder="Ej. 74589632">
                </div>
                <div class="col-md-5">
                    <label class="form-label small text-white-50">Nombre Completo</label>
                    <input type="text" name="nombre" required placeholder="Ej. Carlos Mendoza Ramos">
                </div>
                <div class="col-md-3">
                    <label class="form-label small text-white-50">Edad Actual</label>
                    <input type="number" name="edad" required placeholder="Ej. 24" min="18" max="99">
                </div>
            </div>

            <c:if test="${not empty puestoSeleccionadoId}">
                <h4 class="mb-4 pb-2 border-bottom border-secondary" style="font-family: 'Raleway', sans-serif; font-weight: 700; font-size: 18px;">
                    <i class="fas fa-file-alt me-2" style="color: #e03a3c;"></i>Evaluación de Aptitud Técnica
                </h4>

                <div class="row g-4">
                    <c:choose>
                        <c:when test="${not empty preguntasPuesto}">

                            <c:if test="${not empty preguntasPuesto.preg1}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">1. ${preguntasPuesto.preg1}</label>
                                        <input type="text" name="res1" required placeholder="Escriba su respuesta obligatoria...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg2}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">2. ${preguntasPuesto.preg2}</label>
                                        <input type="text" name="res2" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg3}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">3. ${preguntasPuesto.preg3}</label>
                                        <input type="text" name="res3" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg4}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">4. ${preguntasPuesto.preg4}</label>
                                        <input type="text" name="res4" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg5}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">5. ${preguntasPuesto.preg5}</label>
                                        <input type="text" name="res5" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg6}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">6. ${preguntasPuesto.preg6}</label>
                                        <input type="text" name="res6" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg7}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">7. ${preguntasPuesto.preg7}</label>
                                        <input type="text" name="res7" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty preguntasPuesto.preg8}">
                                <div class="col-md-6">
                                    <div class="question-box">
                                        <label class="question-label">8. ${preguntasPuesto.preg8}</label>
                                        <input type="text" name="res8" placeholder="Respuesta opcional...">
                                    </div>
                                </div>
                            </c:if>

                        </c:when>
                        <c:otherwise>
                            <div class="col-12 text-center py-3">
                                <p class="text-white-50 m-0"><i class="fas fa-info-circle me-2"></i>Este puesto no requiere responder preguntas de evaluación previas. Puede proceder al envío.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <div class="mt-5">
                <button type="submit" class="btn-submit-call">
                    ENVIAR MI POSTULACIÓN <i class="fas fa-paper-plane ms-2"></i>
                </button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>