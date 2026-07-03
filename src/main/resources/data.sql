-- 1. Insertamos datos de prueba para categoría de puesto
INSERT INTO categoria_puestos (nombre, tipo, descripcion, pres_rem, horario, estado, pago)
SELECT 'Desarrollador Java', 'Full Stack', 'Desarrollo de aplicaciones web', 'Remoto', 'Lunes a Viernes', 1, 12000
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM categoria_puestos WHERE nombre = 'Desarrollador Java'
);

-- 2. Insertamos un usuario postulante de prueba inicial
INSERT INTO user_inf (dni, nombre, edad, id_puesto)
SELECT 12345678, 'Juan Martinez Perez', 25,
       (SELECT MIN(id) FROM categoria_puestos WHERE nombre = 'Desarrollador Java')
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM user_inf WHERE dni = 12345678);

-- 3. Insertamos el usuario administrador para el login
INSERT INTO administradores (correo, contrasena)
SELECT 'admin123@gmail.com', '123456'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM administradores WHERE correo = 'admin123@gmail.com'
);

-- 4. Insertamos el registro en la bitácora (CORREGIDO Y SEGURO)
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

-- 5. Insertamos datos de prueba para Citas de Entrevista
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

-- 6. Insertamos datos de prueba para Evaluaciones de Postulantes
INSERT INTO postulante_eva (id_user, id_puesto, puntaje, descripcion, estado, id_cita)
SELECT 
    (SELECT MIN(id) FROM user_inf WHERE dni = 12345678),
    (SELECT MIN(id) FROM categoria_puestos WHERE nombre = 'Desarrollador Java'),
    18,
    'Candidato con excelentes competencias técnicas y experiencia relevante',
    'EN EVALUACION',
    (SELECT MIN(id) FROM citas_entrevista WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678))
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM postulante_eva 
    WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)
      AND id_puesto = (SELECT MIN(id) FROM categoria_puestos WHERE nombre = 'Desarrollador Java')
);

-- Migración idempotente: normaliza registros legacy de postulante_eva al iniciar
UPDATE postulante_eva SET id_cita = 0 WHERE id_cita IS NULL;
UPDATE postulante_eva SET estado = 'PENDIENTE EN EVALUACION' WHERE estado = 'PENDIENTE';