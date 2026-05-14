<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Callypso Call | Postulación</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&family=Raleway:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/contacto.css">
    <style>
        /* Ajustes específicos para las preguntas del reclutamiento */
        .pregunta-card {
            background: #f9f9f9;
            padding: 15px;
            border-left: 4px solid #ca0000;
            margin-bottom: 15px;
            text-align: left;
        }
        .pregunta-card label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
            color: #0a1f44;
        }
        select {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .error-msg { color: #ca0000; font-weight: bold; }
        .success-msg { color: #28a745; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <h1 style="text-transform: uppercase;">Formulario de Reclutamiento</h1>
    <p>Completa tus datos y responde el cuestionario de cultura institucional.</p>

    <div class="form-container shadow">
        <c:if test="${param.exito == 'true'}">
            <p class="success-msg">¡Postulación enviada con éxito! Pronto nos contactaremos contigo.</p>
        </c:if>
        <c:if test="${param.error == 'duplicado'}">
            <p class="error-msg">Error: Este DNI ya registra una postulación activa.</p>
        </c:if>

        <form action="/reclutar/guardar" method="post">
            <div class="row">
                <input type="number" name="dni" placeholder="DNI (Identificación)" required>
                <input type="text" name="nombre" placeholder="Nombre Completo" required>
            </div>
            <div class="row">
                <input type="number" name="edad" placeholder="Edad" required>
                <input type="text" name="ubicacion" placeholder="Distrito / Ciudad" required>
            </div>

            <hr style="margin: 30px 0; opacity: 0.2;">
            <h3 style="color: #ca0000; margin-bottom: 20px;">Cuestionario de Aptitud</h3>
            <p style="font-size: 0.9rem; margin-bottom: 20px;">Califica del 0 (Totalmente en desacuerdo) al 4 (Totalmente de acuerdo)</p>

            <div class="row">
                <div class="col-md-6 pregunta-card">
                    <label>1. ¿Te consideras una persona proactiva?</label>
                    <select name="res1"><option>0</option><option>1</option><option>2</option><option>3</option><option value="4" selected>4</option></select>
                </div>
                <div class="col-md-6 pregunta-card">
                    <label>2. ¿Tienes facilidad de palabra?</label>
                    <select name="res2"><option>0</option><option>1</option><option>2</option><option>3</option><option value="4" selected>4</option></select>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 pregunta-card">
                    <label>3. ¿Trabajas bien bajo presión?</label>
                    <select name="res3"><option>0</option><option>1</option><option>2</option><option>3</option><option value="4" selected>4</option></select>
                </div>
                <div class="col-md-6 pregunta-card">
                    <label>4. ¿Manejas herramientas de oficina?</label>
                    <select name="res4"><option>0</option><option>1</option><option>2</option><option>3</option><option value="4" selected>4</option></select>
                </div>
            </div>

            <button type="submit" style="margin-top: 20px; width: 100%;">ENVIAR MI POSTULACIÓN</button>
        </form>
    </div>

    <div style="margin-top: 30px;">
        <a href="/" style="color: #666; text-decoration: none;">← Volver al inicio</a>
    </div>
</div>

</body>
</html>