DROP DATABASE IF EXISTS gymcontrol;
CREATE DATABASE gymcontrol CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gymcontrol;

-- ============================================
-- TABLA USUARIOS
-- ============================================

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL DEFAULT 'USUARIO',
    activo BOOLEAN DEFAULT TRUE,
    cliente_id BIGINT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA CLIENTES
-- ============================================

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    fecha_registro DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA ENTRENADORES
-- ============================================

CREATE TABLE entrenadores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA MEMBRESÍAS
-- ============================================

CREATE TABLE membresias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'ACTIVA',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA PAGOS
-- ============================================

CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA RUTINAS
-- ============================================

CREATE TABLE rutinas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- TABLA ASISTENCIAS
-- ============================================

CREATE TABLE asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora_ingreso TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Relaciones para HU-16 y HU-17
ALTER TABLE usuarios ADD CONSTRAINT fk_usuario_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);
ALTER TABLE pagos ADD CONSTRAINT fk_pago_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);
ALTER TABLE rutinas ADD CONSTRAINT fk_rutina_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);

-- ============================================
-- DATOS DE PRUEBA
-- ============================================

INSERT INTO usuarios (usuario, contraseña, nombre, email, rol, activo)
VALUES
('admin', SHA2('admin123',256), 'Administrador', 'admin@gymcontrol.com', 'ADMIN', TRUE),
('entrenador1', SHA2('pass123',256), 'Juan García', 'juan@gymcontrol.com', 'ENTRENADOR', TRUE),
('recepcion', SHA2('pass123',256), 'María López', 'maria@gymcontrol.com', 'RECEPCION', TRUE);

INSERT INTO entrenadores (nombre, especialidad, telefono)
VALUES
('Juan García','Musculación','555-0101'),
('Carlos Rodríguez','Cardio','555-0102'),
('Ana Martínez','Pilates','555-0103'),
('Miguel Fernández','Crossfit','555-0104');

INSERT INTO rutinas (nombre, descripcion)
VALUES
('Rutina Principiante','Rutina básica para nuevos miembros. Enfocada en técnica y adaptación.'),
('Rutina Intermedia','Rutina de desarrollo muscular con progresión constante.'),
('Rutina Avanzada','Rutina de alto rendimiento para usuarios experimentados.'),
('Rutina Cardio','Enfocada en resistencia cardiovascular y quema de calorías.');


INSERT INTO clientes (nombre, cedula, telefono, correo, fecha_registro)
VALUES
('Carlos Hernández Mora', '115670891', '8888-4521', 'carlos.hernandez@gmail.com', '2026-01-15'),
('María Fernanda Rojas Solís', '207890456', '8712-3345', 'maria.rojas@hotmail.com', '2026-02-03'),
('José Andrés Vargas Castro', '304560789', '8623-7812', 'jose.vargas@yahoo.com', '2026-02-18'),
('Daniela González Ramírez', '118900234', '8415-9921', 'daniela.gonzalez@gmail.com', '2026-03-10'),
('Luis Alberto Jiménez Araya', '502340678', '8799-1254', 'luis.jimenez@outlook.com', '2026-03-28');

INSERT INTO membresias (tipo, fecha_inicio, fecha_fin, estado)
VALUES
('Básica','2024-01-15','2024-04-15','ACTIVA'),
('Premium','2024-01-20','2024-07-20','ACTIVA'),
('Gold','2024-02-01','2024-08-01','ACTIVA'),
('Básica','2024-02-10','2024-05-10','ACTIVA'),
('VIP','2024-02-15','2025-02-15','ACTIVA');

INSERT INTO pagos (monto, fecha_pago)
VALUES
(25000.00, '2026-01-15'),
(35000.00, '2026-02-03'),
(45000.00, '2026-02-18'),
(30000.00, '2026-03-10'),
(55000.00, '2026-03-28');
INSERT INTO asistencias (fecha, hora_ingreso)
VALUES
('2024-07-08','06:00:00'),
('2024-07-08','06:15:00'),
('2024-07-08','07:00:00'),
('2024-07-09','05:45:00'),
('2024-07-09','06:30:00'),
('2024-07-09','07:15:00'),
('2024-07-09','08:00:00');


UPDATE rutinas SET cliente_id = 1 WHERE id = 1;
UPDATE pagos SET cliente_id = 1 WHERE id = 1;
INSERT INTO usuarios (usuario, contraseña, nombre, email, rol, activo, cliente_id)
VALUES ('cliente1', SHA2('cliente123',256), 'Carlos Hernández Mora', 'cliente1@gymcontrol.com', 'CLIENTE', TRUE, 1);

-- ============================================
-- ÍNDICES
-- ============================================

CREATE INDEX idx_usuarios_email
ON usuarios(email);

CREATE INDEX idx_usuarios_usuario
ON usuarios(usuario);

CREATE INDEX idx_clientes_cedula
ON clientes(cedula);

CREATE INDEX idx_clientes_correo
ON clientes(correo);

CREATE INDEX idx_asistencias_fecha
ON asistencias(fecha);

CREATE INDEX idx_membresias_estado
ON membresias(estado);

-- ============================================
-- CONSULTAS DE VERIFICACIÓN
-- ============================================

SELECT * FROM usuarios;
SELECT * FROM clientes;
SELECT * FROM entrenadores;
SELECT * FROM membresias;
SELECT * FROM pagos;
SELECT * FROM rutinas;
SELECT * FROM asistencias;

SELECT 'Base de datos GymControl creada correctamente.' AS Mensaje;