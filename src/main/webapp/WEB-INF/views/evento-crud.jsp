<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Eventos - Callypso</title>
    <link rel="stylesheet" href="/css/crudc.css">
</head>
<body>

<div class="container">
    <a href="/main" class="btn-volver">← Volver al Inicio</a>
    <h1>Registro de Eventos Callypso</h1>

    <div class="form-section">
        <h3 style="color: #131313;">Nuevo Evento / Sesión</h3>
        <form action="/eventos/guardar" method="POST">
            <div class="form-group">
                <label>Nombre del Evento:</label>
                <input type="text" name="nombre" required placeholder="Ej: Reclutamiento Masivo L1">
            </div>
            <div class="form-group">
                <label>Tipo de Evento:</label>
                <select name="tipo">
                    <option value="Reclutamiento">Reclutamiento Presencial</option>
                    <option value="Motivacion">Experiencia Gratuita / Motivación</option>
                    <option value="Festividad">Festividad (Navidad, Halloween, etc.)</option>
                </select>
            </div>
            <div class="form-group">
                <label>Fecha:</label>
                <input type="date" name="fecha" required>
            </div>
            <div class="form-group">
                <label>Descripción:</label>
                <input type="text" name="descripcion" placeholder="Detalles del evento...">
            </div>
            <button type="submit" class="btn-save">Registrar Evento</button>
        </form>
    </div>
</div>
</body>
</html>