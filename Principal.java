import java.util.*;
import java.io.*;
import java.sql.*;
//importar todo lo necesario

public class Principal {

    //cosas que se pueden usar en varios métodos
    static Scanner sc = new Scanner(System.in); //scanner
    static ArrayList<Productos> listaProductos = new ArrayList<>(); //arraylist de los productos
    public static void main(String[] args) {
        //llamar al menú
        mostrarMenu();
    }

    /**
     * método para mostrar el menú
     */
    public static void mostrarMenu(){
        //declaración de variables y activación de scanner
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        //bucle principal
        do {
            System.out.println("\n-------------BIENVENIDO A CYBERNOVA-------------");
            System.out.println("OPCIONES:");
            System.out.println("-------------");
            System.out.println("1. Añadir productos.");
            System.out.println("2. Eliminar productos.");
            System.out.println("3. Mostrar todos los productos.");
            System.out.println("-------------");
            System.out.println("4. Registrar un cliente.");
            System.out.println("5. Eliminar un cliente.");
            System.out.println("6. Mostrar todos los clientes.");
            System.out.println("-------------");
            System.out.println("7. Añadir a un empleado.");
            System.out.println("8. Eliminar a un empleado.");
            System.out.println("9. Mostrar todos los empleados.");
            System.out.println("-------------");
            System.out.println("10. Añadir al carrito.");
            System.out.println("11. Quiero hacer una reclamación.");
            System.out.println("12. Salir.");

            System.out.print("Por favor, elige una opción: ");
            opcion = sc.nextInt();

            //switch de la opción
            switch (opcion) {
                case 1:
                    anadirProducto();
                    break;

                case 2:
                    eliminarProducto();
                    break;

                case 3:
                    mostrarProductos();
                    break;

                case 4:
                    registrarCliente();
                    break;

                case 5:
                    eliminarCliente();
                    break;

                case 6:
                    mostrarClientes();
                    break;

                case 7:
                    anadirEmpleado();
                    break;

                case 8:
                    eliminarEmpleado();
                    break;

                case 9:
                    mostrarEmpleados();
                    break;

                case 10:
                    quieroComprar();
                    break;

                case 11:
                    quieroReclamar();
                    break;

                case 12:
                    System.out.println("Saliendo del programa...");
                    break;
            
                default:
                System.out.println("ERROR. OPCIÓN NO VÁLIDA.");
                pausar();
                    break;
            }
        } while (opcion != 12); //hacer todo lo anterior mientras la opción elegida no sea 12

        
    }

    /**
     * método para pausar la muestra de system out
     * al tener el menú tantas opciones y mostrarse instantáneamente no da tiempo a ver que se ha ...
     * ... introducido una opción incorrecta
     */
    public static void pausar(){
        try {
            Thread.sleep(2000); //pausa de 2 segundos
        } catch (InterruptedException e) {
            System.err.println("Error al pausar la ejecución.");
        } //esto es para una pausa, ya no da tiempo a leer que se ha introducido una opción no válida
    }

    /**
     * método para añadir un producto
     * como solo los empleados pueden añadir un producto, primero se tiene que hacer un log-in para verificar que es un empleado
     * esto último se comprobará con una consulta parecida a "select nombre from empleados where id = X and claveAcceso = X"
     * si esta consulta devuelve un resultado se podrá añadir un producto
     */
    public static void anadirProducto(){
        //datos de conexión a la base de datos
        String baseDatos = "jdbc:mysql://localhost:3306/empleadosProyectoFinal";
        String usuario = "root";
        String claveAcceso = "franceselquemehackee";

        //pedir el logueo del empleado
        System.out.print("\nPor favor, introduce tu ID de empleado: ");
        int idEmpleado = sc.nextInt();
        System.out.print("Por favor, introduce tu contraseña: ");
        String claveAccesoEmpleado = sc.next();

        //try-catch para realizar la conexión a la base de datos
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            //System.out.println("Conexión realizada.");

            //realizar la consulta
            String consultaSql = "select nombre from empleados where id ='" + idEmpleado + "' and claveAcceso ='" + claveAccesoEmpleado + "' ;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaSql);
            String nombreEmpleado = "";

            //iterar sobre el result set y mostrar los resultados
            while (rs.next()) {
                //obtener el nombre
                nombreEmpleado = rs.getString("nombre");
                System.out.println("Empleado encontrado: " + nombreEmpleado);
            }

            //realizar una pausa
            pausar();

            //comprobar que se ha logueado alguien correctamente, si es así, puede añadir un producto
            if (nombreEmpleado != "") { //se ha encontrado un empleado
                //pedir la descripción (nombre) del producto
                System.out.print("\nPor favor, introduce la descripción del producto:");
                String descripcionProducto = sc.next();

                //pedir el precio del producto
                System.out.print("\nPor favor, introduce el precio del producto: ");
                double precioProducto = sc.nextDouble();

                //pedir la cantidad de productos a añadir
                System.out.print("\nPor favor, introduce la cantidad de productos que deseas añadir: ");
                int cantidadProducto = sc.nextInt();

                //pedir la categoria del producto
                System.out.print("\nPor favor, escribe la categoría del producto: ");
                String categoriaProducto = sc.next();

                //añadir los datos a la lista de productos
                Productos nuevoProducto = new Productos(descripcionProducto, precioProducto, cantidadProducto, categoriaProducto);
                listaProductos.add(nuevoProducto);
            } else { //no se ha encontrado un empleado
                System.err.println("Identificador o contraseña incorrectos.");
                pausar();
            }

            //cerrar la conexión
            conexion.close();
        } catch (SQLException e) {
            System.err.println("\nError conectando a la base de datos: " + e.getMessage());
            pausar(); //realizar una pausa si sale el error
        }
    }

    /**
     * método para eliminar un producto
     */
    public static void eliminarProducto(){

    }

    /**
     * método para mostrar todos los productos
     */
    public static void mostrarProductos(){

    }

    /**
     * método para registrar un cliente
     */
    public static void registrarCliente(){

    }

    /**
     * método para eliminar a un cliente
     */
    public static void eliminarCliente(){

    }

    /**
     * método para mostrar a todos los clientes
     */
    public static void mostrarClientes(){

    }

    /**
     * método para añadir a un empleado
     */
    public static void anadirEmpleado(){

    }

    /**
     * método para eliminar a un empleado
     */
    public static void eliminarEmpleado(){

    }

    /**
     * método para mostrar a todos los empleados
     */
    public static void mostrarEmpleados(){

    }

    /**
     * método para comprar
     */
    public static void quieroComprar(){

    }

    /**
     * método para realizar una reclamación
     */
    public static void quieroReclamar(){

    }
}
