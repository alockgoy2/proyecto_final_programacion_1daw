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
                    System.out.print("\n¿Qué quieres hacer? \n1. Loguearme    \n2. Registrarme. \nElige: ");
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
        //preguntar si se es un jefe o un empleado
        System.out.print("\n¿Jefe o empleado?: ");
        String quienSoy = sc.next();

        //comprobar
        if (quienSoy.equalsIgnoreCase("jefe")) {
            menuJefes();
        } else if(quienSoy.equalsIgnoreCase("empleado")){
            menuEmpleados();
        } else{
            System.err.println("Error, opción no válida.");
        }
    }

    /**
     * menú para los jefes
     */
    public static void menuJefes(){
        //pedir el logueo del jefe
        System.out.print("\nPor favor, introduce tu ID de jefe: ");//pedir el id del jefe
        int idJefe = sc.nextInt();

        System.out.print("Por favor, introduce tu clave de acceso: "); //pedir la clave del jefe
        String claveAccesoJefe = sc.next();

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //realizar la consulta
            String consultaSql = "select nombre from jefes where id ='" + idJefe + "' and claveAcceso ='" + claveAccesoJefe + "' ;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaSql);
            String nombreJefe = "";

            //iterar sobre el result set y mostrar los resultados
            while (rs.next()) {
                //obtener el nombre
                nombreJefe = rs.getString("nombre");
                System.out.println("Jefe encontrado: " + nombreJefe);
            }

            if (nombreJefe != "") { //se ha encontrado un jefe
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

                    //switch con las opciones
                    switch (opcionJefe) {
                        case 1:
                            JefesDAO.anadirEmpleado();
                            break;

                        case 2:
                            JefesDAO.eliminarEmpleado();
                            break;

                        case 3:
                            
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
                            nuevoMenu();
                            break;
                    
                        default:
                        System.err.println("Opción no válida.");
                            break;
                    }
                } while (opcionJefe != 8);
                
            } else {
                System.err.println("Identificador o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            System.err.println("Ha habido un error: " + e.getMessage());
        }
    }

    /**
     * menú para los empleados
     */
    public static void menuEmpleados(){
        
    }
}