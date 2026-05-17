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
        <a href="/" class="logo">
            CallypsoCall
        </a>
        <nav id="navbar" class="navbar">
            <ul class="d-flex align-items-center m-0 p-0" style="list-style: none;">
                <li><a class="nav-link" href="/publicidad">Servicios</a></li>
                <li><a class="nav-link" href="/contacto">Contacto</a></li>
                <li><a class="nav-link" href="#">Eventos</a></li>
                <li><a class="nav-link" href="/postular">Postular</a></li>
                <li><a class="getstarted" href="/login">Login ></a></li>
            </ul>
        </nav>
    </div>
</header><br/>
<div class="container my-5">
    <h1 style="text-transform: uppercase; font-weight: 400; color: #0a1f44; margin-top: 50px;"><Big>Únete a Callypso Call</Big></h1>
    <p class="text-muted mb-4">Completa tu información personal y responde con total honestidad el cuestionario técnico de operaciones.</p>

    <div class="form-container shadow-sm p-4 bg-white rounded">

        <c:if test="${param.exito == 'true'}">
            <div class="success-msg">¡Tu postulación ha sido enviada con éxito! Evaluaremos tus respuestas de inmediato.</div>
        </c:if>
        <c:if test="${param.error == 'duplicado'}">
            <div class="error-msg">Atención: El DNI ingresado ya cuenta con una postulación registrada en nuestra base de datos.</div>
        </c:if>

        <form action="/reclutar/guardar" method="post">

            <div class="row">
                <div class="col-md-6">
                    <input type="number" name="dni" class="form-control" placeholder="Número de DNI" required>
                </div>
                <div class="col-md-6">
                    <input type="text" name="nombre" class="form-control" placeholder="Nombre y Apellidos" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input type="number" name="edad" class="form-control" placeholder="Edad" required>
                </div>
                <div class="col-md-6">
                    <input type="text" name="ubicacion" class="form-control" placeholder="Ciudad / Distrito de residencia" required>
                </div>
            </div>

            <hr class="my-4" style="opacity: 0.15;">
            <h3 class="text-start mb-3" style="color: #ca0000; font-weight: 700;">Cuestionario de Aptitud</h3>
            <p class="text-start text-muted small mb-4">Selecciona la alternativa que mejor describa tu forma de reaccionar en el entorno de trabajo.</p>

            <div class="row">
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>1. Si un usuario te interrumpe gritando en la llamada:</label>
                        <select name="res1" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Le cuelgo la llamada de inmediato</option>
                            <option value="2">Me quedo callado esperando que termine</option>
                            <option value="3">Le pido firmemente que se calme</option>
                            <option value="4">Escucho con paciencia y aplico empatía</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>2. ¿Cómo actúas frente a las metas diarias de ventas?</label>
                        <select name="res2" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Me estresan y prefiero no verlas</option>
                            <option value="2">Intento avanzar sin presionarme</option>
                            <option value="3">Hago lo necesario para cumplir la cuota</option>
                            <option value="4">Me motivan a superarme y ganar más</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>3. Si el cliente te hace una consulta que no conoces:</label>
                        <select name="res3" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Le invento una respuesta rápida</option>
                            <option value="2">Le indico amablemente que no poseo el dato</option>
                            <option value="3">Le solicito que espere en línea mientras busco</option>
                            <option value="4">Consulto al supervisor para aprender el proceso</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>4. Cuando un cliente objeta que el servicio es "muy caro":</label>
                        <select name="res4" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Le doy la razón y desisto</option>
                            <option value="2">Evito comentar sobre los costos</option>
                            <option value="3">Le propongo un descuento inmediato</option>
                            <option value="4">Revalorizo los beneficios exclusivos del servicio</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>5. ¿Cuál consideras que es tu principal fuerte al hablar?</label>
                        <select name="res5" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Hablo de manera directa y seria</option>
                            <option value="2">Me adapto a lo que me digan</option>
                            <option value="3">Mantengo una conversación fluida y cortés</option>
                            <option value="4">Tengo un tono dinámico, seguro y persuasivo</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>6. Tras recibir una respuesta negativa o rechazo continuo:</label>
                        <select name="res6" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Me desmotivo y prefiero pausar el sistema</option>
                            <option value="2">Me tomo unos minutos para calmarme</option>
                            <option value="3">Analizo brevemente qué se puede corregir</option>
                            <option value="4">Paso con entusiasmo a la siguiente llamada</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>7. Frente a situaciones críticas de alta presión laboral:</label>
                        <select name="res7" required>
                            <option value="0">Seleccione una opción</option>
                            <option value="1">Suelo bloquearme bajo mucha exigencia</option>
                            <option value="2">Expreso mi incomodidad con los compañeros</option>
                            <option value="3">Trabajo al ritmo habitual sin acelerar</option>
                            <option value="4">Mantengo el control absoluto y me enfoco en metas</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="pregunta-card">
                        <label>8. ¿Cómo reaccionas cuando tu supervisor te corrige?</label>
                        <select name="res8" required>
                            <option value="0">Seleccione una opción</option>
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