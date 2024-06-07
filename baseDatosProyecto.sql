create database proyectoFinalProgramacion;
use proyectoFinalProgramacion; 

create table productos(
id int primary key,
categoria varchar(30),
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

insert into productos (id, categoria, descripcion, cantidad, marca, precio) values
(1, 'placas base', 'placa base para gaming', 10, 'asus', 150.00),
(2, 'cpu', 'procesador de portátil', 5, 'intel', 199.99),
(3, 'memoria ram', 'memoria ram ddr4 8gb', 15, 'corsair', 60.00),
(4, 'discos duros', 'disco duro ssd 1tb', 20, 'samsung', 120.00),
(5, 'tarjetas graficas', 'tarjeta gráfica rtx 3060', 8, 'nvidia', 350.00),
(6, 'fuente de alimentacion', 'fuente de alimentación 750w', 12, 'corsair', 85.00),
(7, 'tarjeta de red', 'tarjeta de red wifi 6', 7, 'tp-link', 40.00),
(8, 'ordenadores montados', 'ordenador de sobremesa gaming', 6, 'hp', 1000.00),
(9, 'periféricos', 'teclado mecánico', 15, 'razer', 120.00);

#crear la tabla empleados
create table empleados(
id int primary key,
dni varchar(9),
nombre varchar(30),
apellidos varchar(60),
claveAcceso varchar(50),
salario double, 
fechaContratacion varchar(10),
fechaDespido varchar(10),
esJefe boolean
);

#crear la tabla de compras
create table compras(
idCompra int auto_increment primary key,
idProducto int,
descripcion varchar(60),
cantidad int,
precio double,
nombreCliente varchar(60),
totalConDescuento double,
fecha timestamp default current_timestamp
);

#añadir valores 
INSERT INTO empleados (id, dni, nombre, apellidos, claveAcceso, salario, fechaContratacion, fechaDespido, esJefe) VALUES
(101, '12345678A', 'Juan', 'García Pérez', 'clave123', 1800.00, '15/01/2023', NULL, FALSE),
(102, '23456789B', 'María', 'López Martínez', 'password', 1500.00, '20/11/2022', NULL, FALSE),
(103, '34567890C', 'Pedro', 'Sánchez Rodríguez', '123abc', 1700.00, '10/03/2023', NULL, FALSE),
(104, '45678901D', 'Ana', 'Martín Gómez', 'contraseña', 1900.00, '05/02/2023', NULL, FALSE),
(105, '56789012E', 'Laura', 'Hernández García', 'secure123', 1600.00, '08/12/2022', NULL, FALSE),
(106, '67890123F', 'Carlos', 'Díaz Fernández', 'abc123', 1750.00, '18/04/2023', NULL, FALSE),
(107, '78901234G', 'Sofía', 'Ruiz Sánchez', 'password123', 1950.00, '22/05/2023', NULL, FALSE),
(108, '89012345H', 'David', 'González López', 'access123', 1850.00, '30/10/2022', NULL, FALSE),
(109, '90123456I', 'Elena', 'Pérez Martínez', 'securepass', 1700.00, '10/01/2023', '25/04/2024', TRUE),
(110, '01234567J', 'Javier', 'Fernández García', 'admin123', 1600.00, '28/02/2023', NULL, TRUE);
 #todo insertado

create table clientes(
identificacion int auto_increment primary key,
nombre varchar(30),
email varchar(100),
vip boolean);

INSERT INTO clientes (identificacion, nombre, email, vip) VALUES
(1, 'Juan Pérez', 'juan@example.com', 1),     -- VIP
(2, 'María Rodríguez', 'maria@example.com', 0),
(3, 'Carlos Gómez', 'carlos@example.com', 0),
(4, 'Ana Martínez', 'ana@example.com', 1),   -- VIP
(5, 'Luis García', 'luis@example.com', 0),
(6, 'Sofía López', 'sofia@example.com', 0),
(7, 'Pedro Hernández', 'pedro@example.com', 0),
(8, 'Laura Díaz', 'laura@example.com', 1),     -- VIP
(9, 'Jorge Sánchez', 'jorge@example.com', 0),
(10, 'Elena Vázquez', 'elena@example.com', 0);

#crear las tablas de las relaciones
create table jefe_empleado(
id_empleado int,
id_jefe int
);

create table empleado_producto (
id_empleado int,
id_producto int
);

create table compra(
identificacion_cliente int,
id_producto int
);

create table empleado_cliente(
id_empleado int,
identificacion_cliente int
);

select * from empleados;
select * from clientes;
select * from productos;
select * from compras;