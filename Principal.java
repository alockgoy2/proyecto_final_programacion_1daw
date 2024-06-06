//importar todo lo necesario
import java.util.*;
import java.io.*;
import java.sql.*;

public class Principal {

    //cosas que se pueden usar en varios métodos
    static Scanner sc = new Scanner(System.in); //scanner
    public static ArrayList<Productos> listaProductos = new ArrayList<>(); //arraylist de los productos
    public static ArrayList<Carrito> carrito = new ArrayList<>(); //lista del carrito al momento de realizar la compra
    public static ArrayList<Empleados> listaEmpleados = new ArrayList<>(); //arraylist de los empleados
    public static ArrayList<Clientes> listaClientes = new ArrayList<>(); //arraylist de los clientes

    //datos de conexión a la base de datos
    static Connection conexion;

    public static void main(String[] args) {
        //llamar al menú
        nuevoMenu();
    }


    /**
     * método para pausar la muestra de system out
     * al tener el menú tantas opciones y mostrarse instantáneamente no da tiempo a ver que se ha ...
     * ... introducido una opción incorrecta
     */
    public static void pausar(){
        try {
            Thread.sleep(1000); //pausa de 1 segundo
        } catch (InterruptedException e) {
            System.err.println("Error al pausar la ejecución.");
        } //esto es para una pausa, ya no da tiempo a leer que se ha introducido una opción no válida
    }
    
    /**
     * método para mostrar todos los productos
     * aparentemente funciona
     */
    public static void mostrarProductos(){

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            pausar();

            //consulta sql
            String consultaMostrarProductos = "select * from productos;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarProductos);

            //mostrar los datos de los empleados
            while (rs.next()) {
                System.out.println("--------------------------------");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Categoría: " + rs.getString("categoria"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Cantidad: " + rs.getInt("cantidad"));
                System.out.println("Precio: " + rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            pausar();
        }
    }


    /**
     * método para el nuevo menú
     */
    public static void nuevoMenu(){
        //variable para la conexión
        int opcion = 0;

        //bucle principal
        do {
            System.out.println("\n-------------BIENVENIDO A CYBERNOVA-------------");
            System.out.println("OPCIONES:");
            System.out.println("-------------");
            System.out.println("1.  Iniciar sesión (empleado o jefe) o registrarse (cliente).");
            System.out.println("2.  Mostrar todos los productos");
            System.out.println("3.  Comprar.");
            System.out.println("4.  Reclamar.");
            System.out.println("5.  Salir"); 

            //pedir la opción
            System.out.print("\nPor favor, elige una opción: ");
            opcion = sc.nextInt();

            //switch con las opciones
            switch (opcion) {
                case 1:
                    //preguntar lo que se quiere hacer
                    System.out.print("\n¿Qué quieres hacer? \n1. Loguearme    \n2. Registrarme. \n\nElige: ");
                    int hacer = sc.nextInt();
                    if (hacer == 1) { //si la opción es 1, loguearse como jefe o empleado
                        loguearse();
                    } else if (hacer == 2){ //si la opción es 2, registrarse como cliente
                        ClientesDAO.registrarCliente();
                    } else{
                        System.err.println("Opción no válida.");
                    }
                    break;

                case 2:
                    mostrarProductos();
                    break;

                case 3:
                CarritoDAO.quieroComprar();
                    break;

                case 4:
                ClientesDAO.quieroReclamar();    
                    break;

                case 5:
                System.out.println("Guardando clientes...");
                ClientesDAO.guardarClientes();
                pausar();
                System.out.println("Guardando empleados...");
                JefesDAO.guardarEmpleados();
                pausar();
                System.out.println("Guardando productos...");
                ProductosDAO.guardarProductos();
                pausar();
                System.out.println("Saliendo del programa...");
                pausar();
                    break;
            
                default:
                System.err.println("Opción no válida.");
                    break;
            }
        } while (opcion != 5); //hacer todo lo anterior mientras la opción elegida no sea 4
    }

    /**
     * método para loguearse como empleado o como jefe
     */
    public static void loguearse(){
        //pedir el id del emplado
        System.out.print("\nPor favor, introduce tu ID: ");
        int idTrabajador = sc.nextInt();

        //pedir la clave
        System.out.print("Por favor, introduce tu contraseña: ");
        String claveTrabajador= sc.next();

        //comprobar los valores obtenidos
        Empleados empleado = EmpleadosDAO.comprobarUsuario(idTrabajador, claveTrabajador);

        //comprobar los datos
        if (empleado != null) {
            System.out.println("Empleado encontrado: " + empleado.getNombre() + ", " + (empleado.getEsJefe() ? "Jefe" : "Empleado"));
            Principal.pausar();

            //dependiendo del empleado encontrado lanzar un menú u otro
            if (empleado.getEsJefe()) {
                menuJefes();
            } else {
                menuEmpleados();
            }
        } else {
            System.err.println("Identificador o contraseña incorrectos.");
        }
    }

    /**
     * menú para los jefes
     */
    public static void menuJefes(){

        // variable para la opción
        int opcionJefe = 0;
        do {
            System.out.println("\nOpciones para los jefes: ");
            System.out.println("1.  Añadir un empleado.");
            System.out.println("2.  Eliminar un empleado.");
            System.out.println("3.  Ascender a jefe a un empleado.");
            System.out.println("4.  Mostrar todos los empleados.");
            System.out.println("5.  Modificar un cliente.");
            System.out.println("6.  Eliminar un cliente.");
            System.out.println("7.  Mostrar todos los productos.");
            System.out.println("8.  Volver al menú principal.");

            // pedir la opción
            System.out.print("\nPor favor, escoge una opción: ");
            opcionJefe = sc.nextInt();

            // switch con las opciones
            switch (opcionJefe) {
                case 1:
                    JefesDAO.anadirEmpleado();
                    break;

                case 2:
                    JefesDAO.eliminarEmpleado();
                    break;

                case 3:
                    JefesDAO.ascenderEmpleado();
                    break;

                case 4:
                    JefesDAO.mostrarEmpleados();
                    break;

                case 5:
                    ClientesDAO.modificarCliente();
                    break;

                case 6:
                    JefesDAO.eliminarCliente();
                    break;

                case 7:
                    mostrarProductos();
                    break;

                case 8:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.err.println("Opción no válida.");
                    break;
            }
        } while (opcionJefe != 8); //hacer todo lo anterior mientras la opción elegida no sea 8

    }
        

    /**
     * menú para los empleados
     */
    public static void menuEmpleados(){
        
        // variable para la opción del empleado
        int opcionEmpleado = 0;

        // bucle principal
        do {
            System.out.println("\nOpciones para los empleados: ");
            System.out.println("1.  Añadir un producto.");
            System.out.println("2.  Eliminar un producto por marca.");
            System.out.println("3.  Añadir stock de un producto.");
            System.out.println("4.  Mostrar todos los productos.");
            System.out.println("5.  Mostrar todos los empleados.");
            System.out.println("6.  Mostrar todos los clientes.");
            System.out.println("7.  Modificar un cliente.");
            System.out.println("8.  Eliminar un cliente.");
            System.out.println("9.  Volver al menú principal.");

            System.out.print("\nPor favor, escoge una opción: ");
            opcionEmpleado = sc.nextInt();

            // switch con las opciones
            switch (opcionEmpleado) {
                case 1:
                    EmpleadosDAO.anadirProducto();
                    break;

                case 2:
                    EmpleadosDAO.eliminarProducto();
                    break;

                case 3:
                    EmpleadosDAO.anadirStock();
                    break;

                case 4:
                    mostrarProductos();
                    break;

                case 5:
                    EmpleadosDAO.mostrarEmpleados();
                    break;

                case 6:
                    EmpleadosDAO.mostrarClientes();
                    break;

                case 7:
                    ClientesDAO.modificarCliente();
                    break;

                case 8:
                    EmpleadosDAO.eliminarCliente();
                    break;

                case 9:
                    System.out.println("\nVolviendo al menú principal...");
                    pausar();
                    break;

                default:
                    System.err.println("\nOpción no válida.");
                    break;
            }
        } while (opcionEmpleado != 9); // hacer todo lo anterior mientras la opción elegida no sea 9
    }
}