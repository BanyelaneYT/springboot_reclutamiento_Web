<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Callypso Call - Menú Principal</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f0f2f5; margin: 0; padding: 0; }
        .navbar { background-color: #004a99; color: white; padding: 15px 30px; text-align: center; }
        .container { display: flex; justify-content: center; gap: 20px; padding: 50px; flex-wrap: wrap; }
        .card {
            background: white; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 250px; padding: 30px; text-align: center; transition: transform 0.3s;
            cursor: pointer; text-decoration: none; color: #333;
        }
        .card:hover { transform: translateY(-10px); }
        .card i { font-size: 50px; color: #004a99; display: block; margin-bottom: 15px; }
        .card h3 { margin: 10px 0; }
        .card p { font-size: 14px; color: #666; }
    </style>
</head>
<body>
<div class="navbar">
    <h1>Bienvenido a Callypso Call</h1>
</div>

<div class="container">
    <a href="#" class="card">
        <h3>Gestión de Eventos</h3>
        <p>Crea, visualiza y administra los eventos de la empresa.</p>
    </a>

    <div class="card" style="opacity: 0.6; cursor: not-allowed;">
        <h3>Reportes</h3>
        <p>Sección en desarrollo (Próximamente)</p>
    </div>
</div>
</body>
</html>