INSERT INTO evento (nombre, tipo, fecha, descripcion)
SELECT 'Bienvenida Callypso', 'Motivacion', '2026-05-01', 'Evento inicial de cultura'
    WHERE NOT EXISTS (
    SELECT 1 FROM evento WHERE nombre = 'Bienvenida Callypso'
);
INSERT INTO preg_recluta (dni, nombre, edad,
    res1, res2, res3, res4, res5, res6, res7, res8,
    ubicacion, estado, res_eva
)
SELECT
    12345678, -- DNI
    'Juan Martinez Perez', -- Nombre
    25, -- Edad
    4, 2, 3, 1, 0, 4, 2, 1, -- Respuestas (0-4)
    'Lima, Perú', -- Ubicación
    'Pendiente', -- Estado
    'APROBADO' -- Res_eva
WHERE NOT EXISTS (
    SELECT 1 FROM preg_recluta WHERE dni = 12345678
);