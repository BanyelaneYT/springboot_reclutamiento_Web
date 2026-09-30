# Sistema de Gestión de Reclutamiento y Selección de Personal

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-brightgreen?style=flat-square&logo=springsecurity)
![Maven](https://img.shields.io/badge/Maven-Build-blue?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)

Un sistema web robusto diseñado para automatizar y optimizar los procesos de contratación, gestión de vacantes, seguimiento de candidatos y procesos de selección en empresas u organizaciones.

---

## Tabla de Contenidos

- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Variables de Entorno](#-variables-de-entorno)
- [Endpoints Principales](#-endpoints-principales)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

---

## Características Principales

* **Gestión de Vacantes:** Creación, actualización, cierre y publicación de ofertas laborales.
* **Portal de Candidatos:** Registro de postulantes, carga de perfil profesional y postulación a ofertas activas.
* **Módulo de Reclutadores / Administradores:**
  * Revisión de solicitudes y estado de candidatos.
  * Flujo de selección (Filtro, Entrevista, Evaluación, Contratado / Rechazado).
* **Seguridad y Control de Acceso:** Autenticación y autorización basada en roles (RBAC: `ADMIN`, `RECRUITER`, `CANDIDATE`).
* **Notificaciones:** Confirmación de postulaciones y actualizaciones de estado por correo electrónico (opcional).

---

## Tecnologías Utilizadas

* **Lenguaje:** Java 17+
* **Framework Backend:** Spring Boot 3.x
  * Spring Data JPA (Persistencia de datos)
  * Spring Security (Autenticación y Autorización)
  * Spring Web (Controladores y REST/MVC)
* **Base de Datos:** MySQL / PostgreSQL
* **Motor de Plantillas / Frontend:** Thymeleaf (o integración con API REST)
* **Gestor de Dependencias:** Apache Maven
* **Utilidades:** Lombok, Bean Validation (`jakarta.validation`)

---

## Estructura del Proyecto

```text
springboot_reclutamiento_Web/
├── src/
│   ├── main/
│   │   ├── java/com/reclutamiento/
│   │   │   ├── config/          # Configuraciones (Security, Web, etc.)
│   │   │   ├── controllers/     # Controladores MVC / REST
│   │   │   ├── dto/             # Objetos de transferencia de datos
│   │   │   ├── models/          # Entidades JPA (User, Candidate, JobPosition)
│   │   │   ├── repositories/    # Interfaces JPA Repository
│   │   │   ├── services/        # Lógica de negocio y servicios
│   │   │   └── ReclutamientoApplication.java
│   │   └── resources/
│   │       ├── static/          # Archivos estáticos (CSS, JS, Imágenes)
│   │       ├── templates/       # Plantillas Thymeleaf (HTML)
│   │       └── application.yml  # Configuración del sistema
│   └── test/                    # Pruebas unitarias e integración
├── pom.xml                      # Archivo de configuración de Maven
├── .gitignore
└── README.md
```

---

## Requisitos Previos

Asegúrate de tener instalado lo siguiente en tu entorno local:

* **Java Development Kit (JDK):** Versión 17 o superior.
* **Apache Maven:** Versión 3.8+ (o usa el Maven Wrapper incluido `./mvnw`).
* **Base de Datos:** MySQL 8.0+ o PostgreSQL 14+.
* **IDE Recomendado:** IntelliJ IDEA, Eclipse o VS Code.

---

## Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/BanyelaneYT/springboot_reclutamiento_Web.git
   cd springboot_reclutamiento_Web
   ```

2. **Configurar la Base de Datos:**
   Crea la base de datos localmente en tu gestor de base de datos preferido:
   ```sql
   CREATE DATABASE reclutamiento_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Configurar `application.properties` o `application.yml`:**
   Edita el archivo `src/main/resources/application.yml` con tus credenciales locales:

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/reclutamiento_db?useSSL=false&serverTimezone=UTC
       username: TU_USUARIO_DB
       password: TU_PASSWORD_DB
       driver-class-name: com.mysql.cj.jdbc.Driver

     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
       properties:
         hibernate:
           format_sql: true
   ```

4. **Compilar e Iniciar la aplicación:**
   ```bash
   # Linux / macOS
   ./mvnw clean spring-boot:run

   # Windows
   mvnw.cmd clean spring-boot:run
   ```

5. **Acceder a la aplicación:**
   Abre tu navegador e ingresa a `http://localhost:8080`.

---

## Variables de Entorno Recomendadas

Para desplegar en ambientes de producción o test sin exponer credenciales en el código, se sugiere hacer uso de variables de entorno:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `DB_URL` | URL de conexión a la base de datos | `jdbc:mysql://localhost:3306/reclutamiento_db` |
| `DB_USERNAME` | Usuario de la BD | `root` |
| `DB_PASSWORD` | Contraseña de la BD | `password` |
| `SERVER_PORT` | Puerto donde corre la app | `8080` |

---

## Contribución

1. Haz un Fork del proyecto.
2. Crea una rama para tu nueva característica (`git checkout -b feature/NuevaCaracteristica`).
3. Guarda tus cambios (`git commit -m 'Añade nueva característica'`).
4. Sube la rama (`git push origin feature/NuevaCaracteristica`).
5. Abre un **Pull Request**.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para obtener más información.