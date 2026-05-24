CREATE TABLE IF NOT EXISTS evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    tipo VARCHAR(255),
    fecha VARCHAR(100),
    descripcion VARCHAR(255)
    );
CREATE TABLE IF NOT EXISTS preg_recluta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni INT NOT NULL UNIQUE,
    nombre VARCHAR(100),
    edad INT,
    res1 INT, res2 INT, res3 INT, res4 INT,
    res5 INT, res6 INT, res7 INT, res8 INT,
    ubicacion VARCHAR(100),
    estado VARCHAR(50)
);

--@CODEX
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(255),
    contrasena VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS bitacora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_recluta INT,
    id_evento INT,
    accion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL,
    FOREIGN KEY (id_recluta) REFERENCES preg_recluta(id) ON DELETE SET NULL,
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE SET NULL
);