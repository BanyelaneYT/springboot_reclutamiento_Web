<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Eventos - Callypso</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #fafafa; margin: 40px; }
        .container { max-width: 1000px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        h1 { color: #004a99; border-bottom: 2px solid #004a99; padding-bottom: 10px; }
        .form-section { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-save { background-color: #28a745; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; font-size: 16px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #dee2e6; padding: 12px; text-align: left; }
        th { background-color: #004a99; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .badge { padding: 5px 10px; border-radius: 12px; font-size: 12px; color: white; }
        .bg-recru { background-color: #17a2b8; }
        .bg-motiv { background-color: #fd7e14; }
        .bg-fest { background-color: #6f42c1; }
    </style>
</head>
<body>
<div class="container">
    <h1>Registro de Eventos Callypso</h1>

    <div class="form-section">
        <h3>Nuevo Evento / Sesión</h3>
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
                <td>${ev.nombre}</td>
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