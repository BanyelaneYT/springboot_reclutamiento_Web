INSERT INTO evento (nombre, tipo, fecha, descripcion)
SELECT 'Bienvenida Callypso', 'Motivacion', '2026-05-01', 'Evento inicial de cultura'
FROM SYSTEM_RANGE(1, 1)
    WHERE NOT EXISTS (
    SELECT 1 FROM evento WHERE nombre = 'Bienvenida Callypso'
);


INSERT INTO preg_recluta (dni, nombre, edad,
    res1, res2, res3, res4, res5, res6, res7, res8,
    ubicacion, estado
)
SELECT
    12345678, -- DNI
    'Juan Martinez Perez', -- Nombre
    25, -- Edad
    4, 2, 3, 0, 0, 4, 2, 0, -- Respuestas (0-4)
    'Lima, Perú', -- Ubicación
    'Pendiente'-- Estado
FROM SYSTEM_RANGE(1, 1)
    WHERE NOT EXISTS (
    SELECT 1 FROM preg_recluta WHERE dni = 12345678
);

--@CODEX
INSERT INTO usuarios (correo, contrasena)
SELECT 'admin123@gmail.com', '123456'
FROM SYSTEM_RANGE(1, 1)
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'admin123@gmail.com'
);

INSERT INTO bitacora (id_usuario, id_recluta, id_evento, accion, fecha_registro)
SELECT 
    (SELECT id FROM usuarios WHERE correo = 'admin123@gmail.com'),
    (SELECT id FROM preg_recluta WHERE dni = 12345678),
    (SELECT id FROM evento WHERE nombre = 'Bienvenida Callypso'),
    'Registro inicial de recluta en evento de bienvenida',
    CURRENT_TIMESTAMP()
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM bitacora 
    WHERE id_usuario = (SELECT id FROM usuarios WHERE correo = 'admin123@gmail.com')
      AND id_recluta = (SELECT id FROM preg_recluta WHERE dni = 12345678)
);