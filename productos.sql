create database productosProyectoFinal;
use productosProyectoFinal;

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
