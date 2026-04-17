CREATE TABLE IF NOT EXISTS evento (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(100),
    fecha VARCHAR(50),
    descripcion VARCHAR(500)
    );