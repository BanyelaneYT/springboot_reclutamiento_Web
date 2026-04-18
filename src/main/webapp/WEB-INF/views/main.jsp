<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Callypso Call Peru | Inicio</title>

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700|Raleway:400,700,800" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>

    <style>
        body { font-family: "Raleway", sans-serif; color: #131313;background: rgba(14, 14, 14, 0.9); }

        /* Header Estilo Callypso */
        #header { background: rgba(177, 0, 0, 0.9); padding: 15px 0; color: white; transition: all 0.5s; }
        #header .logo { font-size: 32px; font-weight: 700; letter-spacing: 2px; text-transform: uppercase; }
        #header .logo span { color: #a60000; }

        /* Sección Hero (Portada) */
        #hero {
            width: 100%; height: 80vh;
            background: linear-gradient(rgba(188, 0, 0, 0.65), rgba(163, 3, 3, 0.65)),
            url('https://images.unsplash.com/photo-1521737711867-e3b97375f902?auto=format&fit=crop&w=1920&q=80') center center;
            background-size: cover;
            display: flex; align-items: center; justify-content: center;
            color: #fff; text-align: center;
        }
        #hero h1 { font-size: 56px; font-weight: 800; margin-bottom: 20px; }
        #hero p { font-size: 24px; margin-bottom: 30px; }

        /* El botón que lleva al Login */
        .btn-get-started {
            font-weight: 500; font-size: 16px; letter-spacing: 1px;
            display: inline-block; padding: 12px 40px; border-radius: 50px;
            transition: 0.5s; margin: 10px; color: #fff;
            background: #710000; text-decoration: none;
            border: 2px solid #710000;
        }
        .btn-get-started:hover { background: none; color: #fff; border-color: #fff; }

        /* Servicios / Info */
        .services { padding: 60px 0; }
        .section-title { text-align: center; padding-bottom: 30px;color: white }
        .section-title h2 { font-size: 32px; font-weight: bold; text-transform: uppercase; color: #9c0000; }
    </style>
</head>

<body>

<header id="header" class="fixed-top d-flex align-items-center">
    <div class="container d-flex align-items-center justify-content-between">
        <div class="logo">Callypso<span>.</span></div>
    </div>
</header>

<section id="hero" class="animate__animated animate__fadeIn">
    <div class="hero-container">
        <h1>Callypso Call Peru</h1>
        <p>Líderes en soluciones de contact center y gestión de talento.</p>
        <a href="/" class="btn-get-started animate__animated animate__fadeInUp">Ir al Login</a>
    </div>
</section>

<main id="main">
    <section class="services">
        <div class="container">
            <div class="section-title">
                <h2><b>Nuestros Servicios</b></h2>
                <p>Conectando personas, impulsando negocios.</p>
            </div>

            <div class="row text-center g-4 justify-content-center">

                <div class="col-12 col-md-4 col-lg-3">
                    <div class="p-4 h-100" style="background: white; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
                        <i class="fa fa-headset fa-3x mb-3" style="color: #9f0000;"></i>
                        <h4 style="color: black;">Atención al Cliente</h4>
                        <p style="color: black; margin-bottom: 0;">Soporte especializado para grandes marcas en Perú.</p>
                    </div>
                </div>

                <div class="col-12 col-md-4 col-lg-3">
                    <div class="p-4 h-100" style="background: white; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
                        <i class="fa fa-users fa-3x mb-3" style="color: #9f0000;"></i>
                        <h4 style="color: black;">Reclutamiento</h4>
                        <p style="color: black; margin-bottom: 0;">Gestión de talento humano y capacitaciones constantes.</p>
                    </div>
                </div>

                <div class="col-12 col-md-4 col-lg-3">
                    <div class="p-4 h-100" style="background: white; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
                        <i class="fa fa-chart-bar fa-3x mb-3" style="color: #9f0000;"></i>
                        <h4 style="color: black;">Ventas</h4>
                        <p style="color: black; margin-bottom: 0;">Estrategias de telemarketing de alto impacto.</p>
                    </div>
                </div>

            </div>
        </div>
    </section>
</main>

<footer class="bg-black text-white text-center py-4">
    <p>&copy; 2026 Callypso Call Peru. Todos los derechos reservados.</p>
</footer>

</body>
</html>