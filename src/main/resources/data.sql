-- ============================================
-- DATOS INICIALES - BIBLIOTECA API
-- ============================================

-- Autores
INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento, biografia) VALUES
('Gabriel', 'García Márquez', 'Colombiana', '1927-03-06', 'Premio Nobel de Literatura 1982. Padre del realismo mágico.'),
('J.K.', 'Rowling', 'Británica', '1965-07-31', 'Autora de la saga Harry Potter, una de las series más vendidas de todos los tiempos.'),
('George', 'Orwell', 'Británica', '1903-06-25', 'Escritor y periodista. Conocido por 1984 y Rebelión en la granja.'),
('Isabel', 'Allende', 'Chilena', '1942-08-02', 'Reconocida autora latinoamericana, conocida por La casa de los espíritus.'),
('Franz', 'Kafka', 'Checa', '1883-07-03', 'Escritor de habla alemana, conocido por La metamorfosis y El proceso.');

-- Libros
INSERT INTO libros (titulo, isbn, anio_publicacion, genero, descripcion, total_ejemplares, ejemplares_disponibles, autor_id) VALUES
('Cien años de soledad', '978-0-06-088328-7', 1967, 'FICCION', 'La novela cumbre del realismo mágico latinoamericano.', 5, 5, 1),
('El amor en los tiempos del cólera', '978-0-14-303943-3', 1985, 'ROMANCE', 'Historia de amor que dura más de cincuenta años.', 3, 3, 1),
('Harry Potter y la piedra filosofal', '978-84-9838-000-4', 1997, 'FANTASIA', 'El inicio de la mágica aventura del joven mago Harry Potter.', 8, 8, 2),
('Harry Potter y la cámara secreta', '978-84-9838-001-1', 1998, 'FANTASIA', 'Harry regresa a Hogwarts en su segundo año.', 6, 6, 2),
('1984', '978-0-452-28423-4', 1949, 'CIENCIA_FICCION', 'Novela distópica sobre un régimen totalitario omnipresente.', 4, 4, 3),
('Rebelión en la granja', '978-0-452-28424-1', 1945, 'FICCION', 'Alegoría política sobre la corrupción del poder.', 3, 3, 3),
('La casa de los espíritus', '978-84-397-1205-7', 1982, 'REALISMO_MAGICO', 'Saga familiar que abarca cuatro generaciones en Chile.', 4, 4, 4),
('La metamorfosis', '978-84-376-0494-7', 1915, 'FICCION', 'Gregor Samsa se despierta convertido en un insecto gigante.', 5, 5, 5);

-- Socios
INSERT INTO socios (nombre, apellido, email, telefono, direccion, fecha_registro, activo) VALUES
('Carlos', 'Martínez', 'carlos.martinez@email.com', '612345678', 'Calle Mayor 10, Madrid', CURRENT_DATE, true),
('Ana', 'González', 'ana.gonzalez@email.com', '623456789', 'Av. Libertad 25, Barcelona', CURRENT_DATE, true),
('Luis', 'Pérez', 'luis.perez@email.com', '634567890', 'Paseo del Prado 5, Madrid', CURRENT_DATE, true),
('María', 'López', 'maria.lopez@email.com', '645678901', 'Gran Vía 100, Bilbao', CURRENT_DATE, true),
('Pedro', 'Sánchez', 'pedro.sanchez@email.com', '656789012', 'Calle Real 7, Sevilla', CURRENT_DATE, false);
