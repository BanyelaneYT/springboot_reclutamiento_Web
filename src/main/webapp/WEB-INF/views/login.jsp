<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Callypso Call - Login</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-card { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); text-align: center; width: 300px; }
        h2 { color: #004a99; margin-bottom: 20px; }
        input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background-color: #004a99; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        button:hover { background-color: #003366; }
    </style>
</head>
<body>
<div class="login-card">
    <h2>Callypso Call</h2>
    <p>Panel de Eventos</p>
    <input type="text" placeholder="Usuario">
    <input type="password" placeholder="Contraseña">
    <form action="/main" method="GET">
        <button type="submit">Entrar al Sistema</button>
    </form>
</div>
</body>
</html>