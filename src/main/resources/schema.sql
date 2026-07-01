CREATE TABLE IF NOT EXISTS categoria_puestos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150),
    tipo VARCHAR(100),
    descripcion TEXT,
    pres_rem VARCHAR(50),
    horario VARCHAR(100),
    estado INT,
    pago INT
);

CREATE TABLE IF NOT EXISTS user_inf (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni INT UNIQUE,
    nombre VARCHAR(150),
    edad INT,
    puesto INT,
    estado VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS citas_entrevista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    link_meet VARCHAR(255),
    fecha_hora_entrevista DATETIME,
    FOREIGN KEY (id_user) REFERENCES user_inf(id) ON DELETE CASCADE
);

--@CODEX
CREATE TABLE IF NOT EXISTS administradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(255),
    contrasena VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS bitacora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_recluta INT,
    accion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES administradores(id) ON DELETE SET NULL,
    FOREIGN KEY (id_recluta) REFERENCES user_inf(id) ON DELETE SET NULL
);