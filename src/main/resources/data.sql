-- 1. Primero insertamos los datos personales en user_inf
INSERT INTO user_inf (dni, nombre, edad, id_quest, puesto, estado)
SELECT 12345678, 'Juan Martinez Perez', 25, 1, 1, 'Pendiente'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM user_inf WHERE dni = 12345678
);

-- 2. Luego insertamos las respuestas en preg_recluta usando el ID generado de user_inf
INSERT INTO preg_recluta (id_user, res1, res2, res3, res4, res5, res6, res7, res8, estado)
SELECT
    (SELECT id FROM user_inf WHERE dni = 12345678), -- Busca el ID del usuario creado arriba
    4, 2, 3, 0, 0, 4, 2, 0,
    'Pendiente'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM preg_recluta WHERE id_user = (SELECT id FROM user_inf WHERE dni = 12345678)
);

-- 3. Insertamos el usuario administrador
INSERT INTO usuarios (correo, contrasena)
SELECT 'admin123@gmail.com', '123456'
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'admin123@gmail.com'
);

-- 4. Insertamos el registro en la bitácora
INSERT INTO bitacora (id_usuario, id_recluta, accion, fecha_registro)
SELECT
    (SELECT id FROM usuarios WHERE correo = 'admin123@gmail.com'),
    (SELECT id FROM preg_recluta WHERE id_user = (SELECT id FROM user_inf WHERE dni = 12345678)),
    'Registro inicial de recluta en evento de bienvenida',
    CURRENT_TIMESTAMP()
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    SELECT 1 FROM bitacora WHERE accion = 'Registro inicial de recluta en evento de bienvenida'
);