#crear y usar la base de datos
create database empleadosProyectoFinal;
use empleadosProyectoFinal;

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

