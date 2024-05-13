create database clientesProyectoFinal;
use clientesProyectoFinal;

create table clientes(
identificacion int primary key,
nombre varchar(30),
vip boolean);

INSERT INTO clientes (identificacion, nombre, vip) VALUES
(1, 'Juan Pérez', 1),     -- VIP
(2, 'María Rodríguez', 0),
(3, 'Carlos Gómez', 0),
(4, 'Ana Martínez', 1),   -- VIP
(5, 'Luis García', 0),
(6, 'Sofía López', 0),
(7, 'Pedro Hernández', 0),
(8, 'Laura Díaz', 1),     -- VIP
(9, 'Jorge Sánchez', 0),
(10, 'Elena Vázquez', 0);