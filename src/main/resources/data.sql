-- 1. Insertamos un usuario postulante de prueba inicial
INSERT INTO user_inf (dni, nombre, edad, id_quest, puesto, estado)
SELECT 12345678, 'Juan Martinez Perez', 25, NULL, 1, 'PENDIENTE EN EVALUACION'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (SELECT 1 FROM user_inf WHERE dni = 12345678);

-- 2. Insertamos las respuestas usando COMILLAS SIMPLES para cumplir con el tipo TEXT de la tabla
INSERT INTO preg_recluta (id_user, res1, res2, res3, res4, res5, res6, res7, res8, estado)
SELECT
    (SELECT id FROM user_inf WHERE dni = 12345678),
    '4', '2', '3', '0', '0', '4', '2', '0',
    'Pendiente'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM preg_recluta WHERE id_user = (SELECT id FROM user_inf WHERE dni = 12345678)
);

-- 3. Insertamos el usuario administrador para el login
INSERT INTO usuarios (correo, contrasena)
SELECT 'admin123@gmail.com', '123456'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'admin123@gmail.com'
);

-- 4. Insertamos el registro en la bitácora (CORREGIDO Y SEGURO)
INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro)
SELECT
    (SELECT MIN(id) FROM usuarios WHERE correo = 'admin123@gmail.com'),
    (SELECT MIN(id) FROM preg_recluta WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)),
    'Registro inicial de recluta en evento de bienvenida',
    CURRENT_TIMESTAMP()
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    -- Validamos de forma estricta que no exista ya la bitácora para ESTE usuario/recluta específico
    SELECT 1 FROM bitacora
    WHERE id_recluta = (SELECT MIN(id) FROM preg_recluta WHERE id_user = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678))
);