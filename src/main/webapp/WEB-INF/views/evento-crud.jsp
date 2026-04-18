<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Eventos - Callypso</title>
    <style>
        /* Fondo oscuro para que combine con el Main */
        body {
            font-family: 'Raleway', sans-serif;
            background-color: rgb(11, 11, 11);
            margin: 40px;
            color: white;
        }

        .container {
            max-width: 1000px;
            margin: auto;
            background: rgb(37, 37, 37);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.5);
            color: #333; /* Texto oscuro dentro del contenedor blanco */
        }

        /* Títulos en Rojo Callypso */
        h1 {
            color: #ffffff;
            border-bottom: 3px solid #9c0000;
            padding-bottom: 10px;
            font-weight: 800;
        }

        .form-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 30px;
            border-left: 5px solid #9c0000;
        }

        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #131313; }

        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        /* Botón Rojo */
        .btn-save {
            background-color: #9c0000;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 50px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            transition: 0.3s;
        }

        .btn-volver {
            display: inline-block;
            margin-bottom: 20px;
            text-decoration: none;
            color: #ffffff;
            font-weight: bold;
        }
    </style>
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

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Tipo</th>
            <th>Fecha</th>
            <th>Descripción</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listaEventos}" var="ev">
            <tr>
                <td>${ev.id}</td>
                <td><strong>${ev.nombre}</strong></td>
                <td>
                    <span class="badge ${ev.tipo == 'Reclutamiento' ? 'bg-recru' : (ev.tipo == 'Motivacion' ? 'bg-motiv' : 'bg-fest')}">
                            ${ev.tipo}
                    </span>
                </td>
                <td>${ev.fecha}</td>
                <td>${ev.descripcion}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>