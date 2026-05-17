<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Contacto | Callypso Call Peru</title>

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>

.body {
    font-family: 'Poppins', sans-serif;
    background: #e9ecef;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

/* inicio */
#header {
    background: rgba(177, 0, 0, 0.9);
    padding: 15px 0;
    color: white;
    width: 100%;
    box-sizing: border-box;
}

#header .logo {
    font-family: "Raleway", sans-serif;
    font-size: 32px;
    font-weight: 700;
    letter-spacing: 2px;
    text-transform: uppercase;
    color: white;
}

#header .logo span {
    color: #a60000;
}

.container-header {
    width: 85%;
    max-width: 1200px;
    margin: auto;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

/* contacto */

.container-contacto {
    width: 85%;
    max-width: 1200px;
    margin: 40px auto;
    text-align: center;
    flex: 1; /* Empuja el footer abajo */
    box-sizing: border-box;
}

h1 {
    color: #0a1f44;
    font-size: 36px;
    margin-top: 10px;
    margin-bottom: 10px;
    font-weight: 600;
}

.subtitle {
    color: #555;
    font-size: 16px;
    margin-bottom: 40px;
}

.info {
    display: flex;
    justify-content: space-around;
    margin: 40px 0;
    gap: 20px;
}

.box {
    text-align: center;
    flex: 1;
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.02);
}

.box h3 {
    color: #0a1f44;
    font-size: 16px;
    margin-top: 0;
    margin-bottom: 8px;
    letter-spacing: 1px;
}

.box p {
    color: #555;
    margin: 0;
    font-size: 14px;
}

/* formulario */

.form-container {
    background: white;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.05);
    text-align: left;
    margin-bottom: 40px;
    box-sizing: border-box;
}



.row-form-inputs {
    display: flex;
    gap: 20px;
    width: 100%;
}

.input-group-half {
    flex: 1;
}

input, textarea {
    width: 100%;
    padding: 14px;
    margin: 12px 0;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
    font-size: 14px;
    background-color: #fff;
}

textarea {
    height: 150px;
    resize: vertical;
}

.button-container {
    display: flex;
    justify-content: center; /* Esto centra el botón horizontalmente */
    width: 100%;
    margin-top: 20px;
}

button {
    background: #ff2e4c;
    color: white;
    padding: 14px 40px;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 600;
    transition: background 0.3s ease;
}

button:hover {
    background: #e0263f;
}

.success {
    color: green;
    margin-bottom: 15px;
    font-weight: bold;
}

/* footer */

.footer-callypso {
    background-color: #000000;
    color: #ffffff;
    text-align: center;
    padding: 24px 0;
    width: 100%;
    margin-top: auto;
    box-sizing: border-box;
}

.footer-callypso p {
    margin: 0;
    font-family: "Raleway", sans-serif;
    font-size: 15px;
}

/*responsive*/

@media (max-width: 768px) {
    .info {
        flex-direction: column;
        gap: 20px;
    }
    .row-form-inputs {
        flex-direction: column;
        gap: 0;
    }
    .container-contacto, .container-header {
        width: 90%;
    }
    .form-container {
        padding: 20px;
    }
}
    </style>
</head>

<body>

<header id="header">
    <div class="container-header">
        <div class="logo">Callypso<span>Call</span></div>
    </div>
</header>

<div class="container-contacto">

    <h1>CONTÁCTANOS</h1>
    <p class="subtitle">¡Estamos aquí para escucharte! Ponte en contacto con nosotros y juntos crearemos soluciones excepcionales.</p>

    <div class="info">
        <div class="box">
            <h3>DIRECCIÓN</h3>
            <p>Jr Escorpio Mz C Lt 23 - Los Olivos</p>
        </div>

        <div class="box">
            <h3>TELÉFONO</h3>
            <p>+51 941 208 644</p>
        </div>

        <div class="box">
            <h3>EMAIL</h3>
            <p>rrhh.callypso.pe@gmail.com</p>
        </div>
    </div>

    <div class="form-container">

        <c:if test="${not empty mensajeExito}">
            <p class="success">${mensajeExito}</p>
        </c:if>

        <form action="/contacto/enviar" method="post">

            <div class="row-form-inputs">
                <div class="input-group-half">
                    <input type="text" name="nombre" placeholder="Nombre" required>
                </div>
                <div class="input-group-half">
                    <input type="email" name="correo" placeholder="Correo Electrónico" required>
                </div>
            </div>

            <input type="text" name="asunto" placeholder="Asunto" required>

            <textarea name="mensaje" placeholder="Mensaje" required></textarea>

            <div class="button-container">
                <button type="submit">Enviar Mensaje</button>
            </div>

        </form>

    </div>

</div>

<footer class="footer-callypso">
    <p>&copy; 2026 Callypso Call Peru. Todos los derechos reservados.</p>
</footer>

</body>
</html>