create database proyectoFinalProgramacion;
use proyectoFinalProgramacion; 

create table productos(
categoria varchar(30) primary key,
descripcion varchar (60),
cantidad int, /* mínimo 5 para cada producto */
marca varchar(30), /*intel o amd para procesadores, razer para periféricos por ejemplo, etc... */
precio double);

/* Categorías:
placas base
cpu
memoria ram
discos duros
tarjetas graficas
fuente de alimentacion
tarjeta de red
ordenadores montados 
periféricos*/

insert into productos values
("placas base", "placa base para gaming", 10, "asus", 150.00),
("cpu", "procesador de portátil", 5, "intel", 199.99),
("memoria ram", "memoria ram ddr4 8gb", 15, "corsair", 60.00),
("discos duros", "disco duro ssd 1tb", 20, "samsung", 120.00),
("tarjetas graficas", "tarjeta gráfica rtx 3060", 8, "nvidia", 350.00),
("fuente de alimentacion", "fuente de alimentación 750w", 12, "corsair", 85.00),
("tarjeta de red", "tarjeta de red wifi 6", 7, "tp-link", 40.00),
("ordenadores montados", "ordenador de sobremesa gaming", 6, "hp", 1000.00),
("periféricos", "teclado mecánico", 15, "razer", 120.00);

#crear la tabla empleados
create table empleados(
id int primary key,
dni varchar(9),
nombre varchar(30),
apellidos varchar(60),
claveAcceso varchar(50),
salario double, 
fechaContratacion varchar(10),
fechaDespido varchar(10)
);

#añadir valores 
INSERT INTO empleados (id, dni, nombre, apellidos, claveAcceso, salario, fechaContratacion, fechaDespido) VALUES
(101, '12345678A', 'Juan', 'García Pérez', 'clave123', 1800.00, '15/01/2023', NULL),
(102, '23456789B', 'María', 'López Martínez', 'password', 1500.00, '20/11/2022', NULL),
(103, '34567890C', 'Pedro', 'Sánchez Rodríguez', '123abc', 1700.00, '10/03/2023', NULL),
(104, '45678901D', 'Ana', 'Martín Gómez', 'contraseña', 1900.00, '05/02/2023', NULL),
(105, '56789012E', 'Laura', 'Hernández García', 'secure123', 1600.00, '08/12/2022', NULL),
(106, '67890123F', 'Carlos', 'Díaz Fernández', 'abc123', 1750.00, '18/04/2023', NULL),
(107, '78901234G', 'Sofía', 'Ruiz Sánchez', 'password123', 1950.00, '22/05/2023', NULL),
(108, '89012345H', 'David', 'González López', 'access123', 1850.00, '30/10/2022', NULL),
(109, '90123456I', 'Elena', 'Pérez Martínez', 'securepass', 1700.00, '10/01/2023', '25/04/2024'),
(110, '01234567J', 'Javier', 'Fernández García', 'admin123', 1600.00, '28/02/2023', NULL); #todo insertado

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

select * from empleados;