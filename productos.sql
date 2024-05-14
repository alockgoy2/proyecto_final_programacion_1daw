create database productosProyectoFinal;
use productosProyectoFinal;

create table productos(
categoria varchar(30) primary key,
descripcion varchar (60),
cantidad int,
precio double);

/* Categorías:
placas base
cpu
memoria ram
discos duros
tarjetas graficas
fuente de alimentacion
tarjeta de red
ordenadores montados */
INSERT INTO productos (categoria, descripcion, cantidad, precio) VALUES
('placas base', 'Placa base de alta calidad', 5, 150.00),
('cpu', 'Procesador de última generación', 5, 300.00),
('memoria ram', 'Memoria RAM DDR4 16GB', 5, 80.00),
('discos duros', 'Disco duro SSD 1TB', 5, 120.00),
('tarjetas graficas', 'Tarjeta gráfica de alto rendimiento', 5, 400.00),
('fuente de alimentacion', 'Fuente de alimentación 650W', 5, 70.00),
('tarjeta de red', 'Tarjeta de red Gigabit Ethernet', 5, 25.00),
('ordenadores montados', 'Ordenador montado completo', 5, 1000.00);

select * from productos;