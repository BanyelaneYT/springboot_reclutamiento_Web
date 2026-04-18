INSERT INTO evento (nombre, tipo, fecha, descripcion)
SELECT 'Bienvenida Callypso', 'Motivacion', '2026-05-01', 'Evento inicial de cultura'
    WHERE NOT EXISTS (
    SELECT 1 FROM evento WHERE nombre = 'Bienvenida Callypso'
);