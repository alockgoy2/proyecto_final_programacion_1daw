import java.util.*;
import java.io.*;
import java.sql.*;
//importar todo lo necesario

public class Principal {

    //cosas que se pueden usar en varios métodos
    static Scanner sc = new Scanner(System.in); //scanner
    static ArrayList<Productos> listaProductos = new ArrayList<>(); //arraylist de los productos
    static ArrayList<Empleados> listaEmpleados = new ArrayList<>(); //arraylist de los empleados
    static ArrayList<Clientes> listaClientes = new ArrayList<>(); //arraylist de los clientes

    //datos de conexión a la base de datos
    static String baseDatos = "jdbc:mysql://localhost:3306/proyectoFinalProgramacion";
    static String usuario = "root";
    static String claveAcceso = "franceselquemehackee";

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
            System.out.println("12. Salir y guardar cambios.");

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
                    System.out.println("Saliendo del programa y guardando cambios...");
                    guardarClientes();
                    guardarEmpleados();
                    guardarProductos();
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
                System.out.print("\nPor favor, introduce la descripción del producto: ");
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
     * aparentemente funciona
     */
    public static void mostrarProductos(){

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            System.out.println("Conexión realizada.");
            pausar();

            //consulta sql
            String consultaMostrarProductos = "select * from productos;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarProductos);

            //mostrar los datos de los empleados
            while (rs.next()) {
                System.out.println("--------------------------------");
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
     * método para registrar un cliente
     */
    public static void registrarCliente(){
        //pedir los datos del cliente
        System.out.println("Por favor, introduce los datos del cliente: ");
        System.out.print("\nNombre: ");
        String nombreCliente = sc.nextLine(); //pedir el nombre
        System.out.print("\nIdentificador: ");
        int idCliente = sc.nextInt();
        System.out.print("\n¿VIP? (1 = si, 0 = no)");
        int comprobarVip = sc.nextInt();

        boolean clienteVip = false;

        //comprobar si es un cliente vip
        if (comprobarVip == 1) {
            clienteVip = true;
        }

        //crear el objeto de cliente y añadirlo a la lista
        Clientes nuevoCliente = new Clientes(idCliente, nombreCliente, clienteVip);
        listaClientes.add(nuevoCliente);
    }

    /**
     * método para eliminar a un cliente
     */
    public static void eliminarCliente(){
        //pedir el ID del cliente a eliminar
        System.out.print("Por favor, escribe el ID del cliente que quieres eliminar: ");
        int idClienteEliminar = sc.nextInt();

        //buscar el cliente
        Iterator<Clientes> iterador = listaClientes.iterator();
        boolean clienteEliminado = false; //variable booleana para facilitar el mensaje de que ha sido eliminado
        while (iterador.hasNext()) {
            Clientes clienteEliminar = iterador.next();
            if (clienteEliminar.getIdentificacion() == idClienteEliminar) { //comprobar que el identificador coincide
                iterador.remove(); //eliminar al cliente
                System.out.println("El cliente con el identificador " + idClienteEliminar + " ha sido eliminado.");
                clienteEliminado = true;
                break;
            }
        }

        //si no se ha encontrado un cliente con ese id
        if (!clienteEliminado) {
            System.err.println("No se ha encontrado un cliente con ese identificador.");
        }
    }

    /**
     * método para mostrar a todos los clientes
     * aparentemente funciona
     */
    public static void mostrarClientes(){

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            System.out.println("Conexión realizada.");
            pausar();

            //consulta sql
            String consultaMostrarClientes = "select * from clientes;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarClientes);

            //mostrar los datos de los empleados
            while (rs.next()) {
                System.out.println("--------------------------------");
                System.out.println("Identificación: " + rs.getInt("identificacion"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("¿VIP?: " + rs.getBoolean("vip"));
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            pausar();
        }
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

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            System.out.println("Conexión realizada.");
            pausar();

            //consulta sql
            String consultaMostrarEmpleados = "select * from empleados;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarEmpleados);

            //mostrar los datos de los empleados
            while (rs.next()) {
                System.out.println("--------------------------------");
                System.out.println("Identificador: " + rs.getInt("id"));
                System.out.println("DNI: " + rs.getString("dni"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Apellidos: " + rs.getString("apellidos"));
                System.out.println("Clave de acceso: " + rs.getString("claveAcceso"));
                System.out.println("Salario: " + rs.getDouble("salario"));
                System.out.println("Fecha de contratación: " + rs.getString("fechaContratacion"));
                System.out.println("Fecha de despido: " + rs.getString("fechaDespido"));
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            pausar();
        }
    }

    /**
     * método para comprar
     */
    public static void quieroComprar(){

    }

    /**
     * método para realizar una reclamación
     * aparentemente funciona
     */
    public static void quieroReclamar(){
        //archivo para las reclamaciones
        String archivoReclamaciones = "reclamaciones.txt";

        //activar el filewriter y el buffered writer
        FileWriter reclamar = null;
        BufferedWriter bufferReclamar = null;

        //pedir los datos para la reclamación
        System.out.print("\nPor favor, escribe tu nombre: ");
        String nombreCliente = sc.nextLine(); //nombre del cliente

        System.out.print("\nPor favor, escribe tus apellidos (al menos el primero): ");
        String apellidosCliente = sc.nextLine(); //apellidos del cliente

        System.out.print("\n(OPCIONAL) Escribe tu teléfono: ");
        String telefonoCliente = sc.nextLine(); //teléfono del cliente

        System.out.print("\nPor favor, escribe tu email: ");
        String emailCliente = sc.nextLine(); //correo electrónico del cliente

        System.out.print("\nPor favor, escribe el motivo de la reclamación (producto o problemas con el personal): ");
        String motivoReclamacion = sc.nextLine(); //motivo de la reclamación

        System.out.print("\nPor favor, describe el problema: ");
        String descripcionProblema = sc.nextLine(); //descripción del problema

        //intentar escribir los datos en el archivo
        try {
            //crear un filewriter con opción para añadir al final del archivo
            reclamar = new FileWriter(archivoReclamaciones, true);

            //crear un buffered writer para mejorar el rendimiento
            bufferReclamar = new BufferedWriter(reclamar);

            //escribir los datos
            bufferReclamar.write("-------------------------------");
            bufferReclamar.newLine();
            bufferReclamar.write("Nombre: " + nombreCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Apellido(s): " + apellidosCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Teléfono: " + telefonoCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Correo electrónico: " + emailCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Motivo de la reclamación: " + motivoReclamacion);
            bufferReclamar.newLine();
            bufferReclamar.write("Descripción del problema: " + descripcionProblema);

            //vaciar el buffer de salida
            bufferReclamar.flush();

            //cerrar el buffered writer
            bufferReclamar.close();

            System.out.println("\nReclamación guardada.");
            pausar();
        } catch (IOException e) {
            System.err.println("Error realizando la reclamación: " + e.getMessage());
            pausar();
        }

    }

    /**
     * método para guardar los empleados al cerrar el programa
     */
    public static void guardarEmpleados(){

    }

    /**
     * método para guardar los clientes al cerrar el programa
     */
    public static void guardarClientes(){

    }

    /**
     * método para guardar los productos al cerrar el programa
     */
    public static void guardarProductos(){

    }

}
