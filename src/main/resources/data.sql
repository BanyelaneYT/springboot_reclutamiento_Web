-- 1. Insertamos un usuario postulante de prueba inicial
INSERT INTO user_inf (dni, nombre, edad, puesto, estado)
SELECT 12345678, 'Juan Martinez Perez', 25, 1, 'PENDIENTE EN EVALUACION'
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
INSERT INTO bitacora (id, id_recluta, accion, fecha_registro)
SELECT
    (SELECT MIN(id) FROM administradores WHERE correo = 'admin123@gmail.com'),
    (SELECT MIN(id) FROM user_inf WHERE dni = 12345678),
    'Registro inicial de recluta en evento de bienvenida',
    CURRENT_TIMESTAMP()
FROM SYSTEM_RANGE(1, 1)
WHERE NOT EXISTS (
    -- Validamos de forma estricta que no exista ya la bitácora para ESTE usuario/recluta específico
    SELECT 1 FROM bitacora
    WHERE id_recluta = (SELECT MIN(id) FROM user_inf WHERE dni = 12345678)
);