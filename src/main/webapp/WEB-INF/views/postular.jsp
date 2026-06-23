<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso Call | Postulación Masiva</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&family=Raleway:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/contacto.css">
    <link rel="stylesheet" href="/css/postular.css">
    <link rel="stylesheet" href="/css/Header.css">
</head>
<body>
<header id="header" class="fixed-top d-flex align-items-center">
    <div class="header-fullwidth d-flex align-items-center justify-content-between">
        <a href="/" class="logo">CallypsoCall</a>
    </div>
</header>

<div class="container container-postular">
    <div class="form-container shadow-lg">
        <h2 class="text-center text-white mb-4 fw-bold style-title-postular">FICHA DE POSTULACIÓN TÉCNICA</h2>

        <c:if test="${param.error == 'duplicado'}">
            <div class="alert alert-danger text-center fw-bold">Usted ya cuenta con una postulación activa vinculada a este DNI.</div>
        </c:if>

        <form action="/reclutar/guardar" method="POST">
            <input type="hidden" name="idQuest" value="1"> <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white">Nombres y Apellidos Completos</label>
                    <input type="text" class="form-control" name="nombre" placeholder="Ingrese nombre completo" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white">Documento de Identidad (DNI)</label>
                    <input type="number" class="form-control" name="dni" placeholder="DNI de 8 dígitos" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white">Edad</label>
                    <input type="number" class="form-control" name="edad" placeholder="Edad actual" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white">Puesto al que Postula</label>
                    <select name="puesto" class="form-select" required>
                        <option value="1">Asesor Call Center - Ventas España</option>
                        <option value="2">Atención al Cliente - Turno Mañana</option>
                    </select>
                </div>
            </div>

            <hr style="border-color: #ca0000; margin: 30px 0;">
            <h4 class="text-white mb-4"><i class="fas fa-clipboard-check me-2"></i> Evaluación Psicotécnica y Comercial</h4>

            <div class="row">
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>1. Si un cliente te corta abruptamente la llamada diciendo "No me interesa", ¿qué haces?</label>
                        <select name="res1" required>
                            <option value="1">Me molesto y no insisto</option>
                            <option value="2">Paso inmediatamente a la siguiente llamada sin importar</option>
                            <option value="3">Intento registrar qué le molestó para mejorar</option>
                            <option value="4">Mantengo la calma y aplico una técnica de rebatir objeciones antes que cuelgue</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>2. ¿Cuál consideras que es la clave principal para cerrar una venta difícil?</label>
                        <select name="res2" required>
                            <option value="1">Hablar rápido sin dejar interrupciones</option>
                            <option value="2">Ofrecer rebajas o descuentos desesperadamente</option>
                            <option value="3">Escuchar con atención las necesidades reales del cliente</option>
                            <option value="4">Generar un sentido de urgencia absoluto y confianza</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>8. ¿Cómo reaccionas cuando tu supervisor te corrige?</label>
                        <select name="res8" required>
                            <option value="1">Me incomoda y siento que me expone</option>
                            <option value="2">Escucho pero sigo trabajando a mi manera</option>
                            <option value="3">Acepto los comentarios para evitar roces</option>
                            <option value="4">Agradezco el feedback para potenciar mis ventas</option>
                        </select>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-danger w-100 py-3 mt-3 fw-bold" style="background-color: #ca0000; border: none; font-size: 1.1rem;">
                ENVIAR POSTULACIÓN AHORA
            </button>
        </form>
    </div>
</div>
<footer class="footer-simple">
    <p>&copy; 2026 Callypso Call Peru. Todos los derechos reservados.</p>
</footer>
</body>
</html>