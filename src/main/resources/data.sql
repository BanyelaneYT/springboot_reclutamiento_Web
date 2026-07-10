-- 1. Categorías de puestos (catálogo real)
INSERT INTO categorias (nombre, descripcion, estado)
SELECT 'Call Center', 'Puestos de atención telefónica y ventas por contacto', 1
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Call Center');

INSERT INTO categorias (nombre, descripcion, estado)
SELECT 'Ventas', 'Puestos orientados a comercialización y asesoría de ventas', 1
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Ventas');

INSERT INTO categorias (nombre, descripcion, estado)
SELECT 'Soporte Técnico', 'Puestos de asistencia técnica y resolución de incidencias', 1
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Soporte Técnico');

-- 2. Puestos laborales vinculados a categoría
INSERT INTO categoria_puestos (id_categoria, nombre, descripcion, pres_rem, horario, estado, pago)
SELECT
    (SELECT MIN(id) FROM categorias WHERE nombre = 'Call Center'),
    'Atencion al Cliente',
    'Gestionar llamadas salientes (outbound) para la oferta de productos/servicios. Cumplir con objetivos diarios.',
    'Remoto', 'Lunes a Viernes 24/7', 1, 1200
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categoria_puestos WHERE nombre = 'Atencion al Cliente');

INSERT INTO categoria_puestos (id_categoria, nombre, descripcion, pres_rem, horario, estado, pago)
SELECT
    (SELECT MIN(id) FROM categorias WHERE nombre = 'Call Center'),
    'Asesor de Ventas',
    'Experiencia en ventas, manejo de CRM y cumplimiento de indicadores.',
    'Presencial', 'Lunes a Viernes 12:00 a 20:00', 1, 1250
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categoria_puestos WHERE nombre = 'Asesor de Ventas');

-- 3. Insertamos un usuario postulante de prueba inicial
INSERT INTO user_inf (dni, nombre, edad)
SELECT 12345678, 'Juan Martinez Perez', 25
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM user_inf WHERE dni = 12345678);

-- 4. Insertamos el usuario administrador para el login
INSERT INTO administradores (correo, contrasena)
SELECT 'admin123@gmail.com', '123456'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM administradores WHERE correo = 'admin123@gmail.com');

-- 5. Insertamos el registro en la bitácora
INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro)
SELECT
    (SELECT MIN(id) FROM administradores WHERE correo = 'admin123@gmail.com'),
    (SELECT MIN(id) FROM user_inf WHERE dni = 12345678),
    'Registro inicial de recluta en evento de bienvenida',
    CURRENT_TIMESTAMP()
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM bitacora
    WHERE id_recluta = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)
);

-- 6. Insertamos datos de prueba para Citas de Entrevista
INSERT INTO citas_entrevista (id_user, link_meet, fecha_hora_entrevista)
SELECT
    (SELECT MIN(id) FROM user_inf WHERE dni = 12345678),
    'https://meet.google.com/abc-defg-hij',
    '2026-07-15 10:00:00'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM citas_entrevista
    WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)
);

-- 7. Insertamos datos de prueba para Evaluaciones de Postulantes
INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita)
SELECT
    (SELECT MIN(id) FROM user_inf WHERE dni = 12345678),
    (SELECT MIN(id) FROM categoria_puestos WHERE nombre = 'Atencion al Cliente'),
    18,
    'Candidato con excelentes competencias técnicas',
    'EN EVALUACION',
    (SELECT MIN(id) FROM citas_entrevista WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678))
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM postulante_eva
    WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)
      AND id_puesto = (SELECT MIN(id) FROM categoria_puestos WHERE nombre = 'Atencion al Cliente')
);

-- Migración idempotente para bases existentes (campo tipo -> categorías reales)
ALTER TABLE categoria_puestos ADD COLUMN IF NOT EXISTS id_categoria INT;

INSERT INTO categorias (nombre, descripcion, estado)
SELECT DISTINCT cp.tipo, 'Migrado desde campo tipo', 1
FROM categoria_puestos cp
WHERE cp.tipo IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM categorias c WHERE c.nombre = cp.tipo);

UPDATE categoria_puestos cp
SET id_categoria = (SELECT MIN(c.id) FROM categorias c WHERE c.nombre = cp.tipo)
WHERE cp.id_categoria IS NULL AND cp.tipo IS NOT NULL;

INSERT INTO categorias (nombre, descripcion, estado)
SELECT 'General', 'Categoría por defecto', 1
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'General');

UPDATE categoria_puestos
SET id_categoria = (SELECT MIN(id) FROM categorias WHERE nombre = 'General')
WHERE id_categoria IS NULL;

UPDATE postulante_eva SET id_cita = 0 WHERE id_cita IS NULL;
UPDATE postulante_eva SET estado = 'PENDIENTE EN EVALUACION' WHERE estado = 'PENDIENTE';
