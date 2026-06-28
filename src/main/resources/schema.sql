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

CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_puesto INT, -- Enlazado a categoria_puestos
    preg1 VARCHAR(255), preg2 VARCHAR(255), preg3 VARCHAR(255),
    preg4 VARCHAR(255), preg5 VARCHAR(255), preg6 VARCHAR(255),
    preg7 VARCHAR(255), preg8 VARCHAR(255), estado VARCHAR(50),
    FOREIGN KEY (id_puesto) REFERENCES categoria_puestos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_inf (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni INT UNIQUE,
    nombre VARCHAR(150),
    edad INT,
    id_quest INT,
    puesto INT,
    estado VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS preg_recluta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    res1 TEXT, res2 TEXT, res3 TEXT, res4 TEXT,
    res5 TEXT, res6 TEXT, res7 TEXT, res8 TEXT,
    estado VARCHAR(100),
    FOREIGN KEY (id_user) REFERENCES user_inf(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS citas_entrevista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    link_meet VARCHAR(255),
    fecha_hora_entrevista DATETIME,
    FOREIGN KEY (id_user) REFERENCES user_inf(id) ON DELETE CASCADE
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
    accion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL,
    FOREIGN KEY (id_recluta) REFERENCES preg_recluta(id) ON DELETE SET NULL
);