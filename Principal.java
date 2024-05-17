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
        //declaración de variables
        int opcion = 0;

        //bucle principal
        do {
            System.out.println("\n-------------BIENVENIDO A CYBERNOVA-------------");
            System.out.println("OPCIONES:");
            System.out.println("-------------");
            System.out.println("1. Añadir productos.");
            System.out.println("2. Eliminar productos por marca.");
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
                    System.out.println("Guardando clientes...");
                    guardarClientes();
                    pausar();
                    System.out.println("Guardando empleados...");
                    guardarEmpleados();
                    pausar();
                    System.out.println("Guardando productos...");
                    guardarProductos();
                    pausar();
                    System.out.println("Saliendo del programa...");
                    pausar();
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

                //pedir el identificador del producto
                System.out.print("\nPor favor, introduce el ID del producto: ");
                int idProducto = sc.nextInt();

                //pedir la categoria del producto
                System.out.print("\nPor favor, escribe la categoría del producto: ");
                String categoriaProducto = sc.next();

                //pedir la descripción (nombre) del producto
                System.out.print("\nPor favor, introduce la descripción del producto: ");
                sc.nextLine();
                String descripcionProducto = sc.nextLine();

                //pedir la cantidad de productos a añadir
                System.out.print("\nPor favor, introduce la cantidad de productos que deseas añadir: ");
                int cantidadProducto = sc.nextInt();

                //pedir la marca del producto
                System.out.print("\nPor favor, escribe la marca del producto: ");
                sc.nextLine();
                String marcaProducto = sc.nextLine();

                //pedir el precio del producto
                System.out.print("\nPor favor, introduce el precio del producto: ");
                double precioProducto = sc.nextDouble();

                //añadir los datos a la lista de productos
                Productos nuevoProducto = new Productos(idProducto, categoriaProducto, descripcionProducto, cantidadProducto, marcaProducto, precioProducto);
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
     * 
     */
    public static void eliminarProducto(){

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

            //comprobar que se ha logueado alguien correctamente, si es así, puede añadir un producto
            if (nombreEmpleado != "") { //se ha encontrado un empleado
                //pedir la marca cuyos productos deseas eliminar
                System.out.print("\nPor favor, escribe la marca cuyos productos deseas eliminar: ");
                sc.nextLine();
                String marcaProductoEliminar = sc.nextLine();

                //consulta sql
                String consultaEliminarMarca = "delete from productos where marca = '" + marcaProductoEliminar + "';";

                // cosas del sql
                sentencia = conexion.createStatement();
                int filasAfectadas = sentencia.executeUpdate(consultaEliminarMarca);   

                if (filasAfectadas > 0) {
                    System.out.println("Marca eliminada.");
                } else {
                    System.out.println("No se encontró una marca con ese nombre.");
                }

            } else {
                System.err.println("Identificador o contraseña incorrectos.");
            }

            //realizar una pausa
            pausar();

        //pedir la marca del producto que se desea eliminar
        System.out.print("\nPor favor, escribe la marca cuyos productos deseas eliminar: ");
    } catch (SQLException e){
        System.err.println("Error conectando a la base de datos: " + e.getMessage());
    }
}
    /**
     * método para mostrar todos los productos
     * aparentemente funciona
     */
    public static void mostrarProductos(){

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
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
     * método para registrar un cliente
     */
    public static void registrarCliente(){
        //pedir los datos del cliente
        System.out.println("Por favor, introduce los datos del cliente: ");
        System.out.print("\nIdentificador: ");
        int idCliente = sc.nextInt(); //pedir el id del cliente
        System.out.print("\nNombre: ");
        sc.nextLine();
        String nombreCliente = sc.nextLine(); //pedir el nombre
        
        System.out.print("\n¿VIP? (1 = si, 0 = no): ");
        int comprobarVip = sc.nextInt(); //preguntar si es vip

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
     * NOTA: Este método elimina al empleado de la base de datos, no del arraylist
     */
    public static void eliminarCliente(){
        //pedir el ID del cliente a eliminar
        System.out.print("Por favor, escribe el ID del cliente que quieres eliminar: ");
        int idClienteEliminar = sc.nextInt();

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            System.out.println("Conexión realizada.");
            pausar();

            //sentencia sql
            String consultaEliminarCliente = "delete from clientes where identificacion = " + idClienteEliminar + ";";

            // cosas del sql
            Statement sentencia = conexion.createStatement();
            int filasAfectadas = sentencia.executeUpdate(consultaEliminarCliente);

            if (filasAfectadas > 0) {
                System.out.println("Cliente eliminado.");
            } else {
                System.out.println("No se encontró ningún cliente con el id proporcionado.");
            }

            pausar();
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            pausar();
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
        //pedir los datos del empleado
        System.out.println("Por favor, introduce los datos del empleado: ");
        System.out.print("\nId de empleado: ");
        int idEmpleado = sc.nextInt();
        System.out.print("\nNombre del empleado: ");
        sc.nextLine();
        String nombreEmpleado = sc.nextLine();
        System.out.print("\nApellidos del empleado: ");
        String apellidosEmpleado = sc.nextLine();
        System.out.print("\nDNI del empleado: ");
        String dniEmpleado = sc.nextLine();
        System.out.print("\nClave de acceso (no puede contener espacios): ");
        String claveAcceso = sc.next();
        System.out.print("\nSueldo del empleado: ");
        double sueldoEmpleado = sc.nextDouble();
        System.out.print("\nFecha de contratación (dia/mes/año): ");
        sc.nextLine();
        String fechaContratacion = sc.nextLine();
        System.out.print("\nFecha de despido (opcional): ");
        String fechaDespido = sc.nextLine();

        //crear el objeto y añadirlo a la lista
        Empleados nuevoEmpleado = new Empleados(idEmpleado, dniEmpleado, nombreEmpleado, apellidosEmpleado, claveAcceso, sueldoEmpleado, fechaContratacion, fechaDespido);
        listaEmpleados.add(nuevoEmpleado);
        System.out.println("Empleado añadido.");

    }

    /**
     * método para eliminar a un empleado
     * NOTA: Este método elimina al empleado de la base de datos, no del arraylist
     */
    public static void eliminarEmpleado(){
        //pedir el id del empleado que se quiere eliminar
        System.out.print("Por favor, introduce el identificador del empleado que quieres eliminar: ");
        int idEmpleado = sc.nextInt();

        //intentar la conexión
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            System.out.println("Conexión realizada.");
            pausar();

            //sentencia sql
            String consultaEliminarEmpleado = "delete from empleados where id = " + idEmpleado + ";";

            // cosas del sql
            Statement sentencia = conexion.createStatement();
            int filasAfectadas = sentencia.executeUpdate(consultaEliminarEmpleado);

            if (filasAfectadas > 0) {
                System.out.println("Empleado eliminado.");
            } else {
                System.out.println("No se encontró ningún empleado con el id proporcionado.");
            }

            pausar();
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            pausar();
        }

        

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
                System.out.println("Clave de acceso: " + "*************");
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

        //variable para seguir comprando
        String seguirComprando = "no";

        do {
            //mostrar primero el stock disponible
            mostrarProductos();

            //pedir el id del producto que se quiere comprar
            System.out.print("\nPor favor, introduce el ID del producto que quieres comprar: ");
            int idProducto = sc.nextInt();

            //pedir la cantidad de producto que se desea comprar
            System.out.print("\nPor favor, introduce la cantidad de productos que quieres comprar: ");
            int cantidadProducto = sc.nextInt();

            //comprobar que hay unidades disponibles
            int cantidadDisponible = 0;

                //intentar la conexión con la base de datos
                try {
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                    //MODIFICAR EL TIPO DE EXCEPCIÓN
                } catch (Exception e) {
                    System.err.println("Error comprobando el stock disponible: " + e.getMessage());
                }
            //preguntar si se desea seguir comprando
            System.out.print("¿Seguir comprando? (si,no): ");
            seguirComprando = sc.next();
        } while (!seguirComprando.equalsIgnoreCase("no"));
        //hacer todo lo anterior mientras no se especifique el deseo de dejar de comprar
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
        //intentar la conexión con la base de datos
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            String consultaInsertarEmpleados = "insert into empleados values (?, ?, ?, ?, ?, ?, ?, ?);";

            //intentar insertar los empleados
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarEmpleados)) {
                for (Empleados empleado : listaEmpleados) {
                    ps.setInt(1, empleado.getIdentificacion());
                    ps.setString(2, empleado.getDni());
                    ps.setString(3, empleado.getNombre());
                    ps.setString(4, empleado.getApellidos());
                    ps.setString(5, empleado.getClaveAcceso());  
                    ps.setDouble(6, empleado.getSueldo());
                    ps.setString(7, empleado.getContratacion());
                    ps.setString(8, empleado.getDespido());
                    ps.executeUpdate();
                }
            } catch (Exception ex){
                System.err.println("\nError guardando los empleados en la base de datos: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error guardando los empleados en la base de datos: " + e.getMessage());
        }
    }

    /**
     * método para guardar los clientes al cerrar el programa
     */
    public static void guardarClientes(){
        //intentar la conexión con la base de datos
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            String consultaInsertarClientes = "insert into clientes values (?, ?, ?);";

            //intentar insertar los clientes
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarClientes)) {
                for (Clientes cliente : listaClientes) {
                    ps.setInt(1, cliente.getIdentificacion());
                    ps.setString(2, cliente.getNombre());
                    ps.setBoolean(3, cliente.getVip());
                    ps.executeUpdate();
                }
            } catch (Exception ex){
                System.err.println("Error guardando los clientes en la base de datos: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("\nError guardando los clientes en la base de datos: " + e.getMessage());
        }
    }

    /**
     * método para guardar los productos al cerrar el programa
     */
    public static void guardarProductos(){
        //intentar la conexión con la base de datos
        try {
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, claveAcceso);
            String consultaInsertarProductos = "insert into productos values (?, ?, ?, ?, ?, ?);";

            //intentar insertar los productos
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarProductos)) {
                for (Productos producto : listaProductos) {
                    ps.setInt(1, producto.getId());
                    ps.setString(2, producto.getCategoria());
                    ps.setString(3, producto.getDescripcion());
                    ps.setInt(4, producto.getCantidad());
                    ps.setString(5, producto.getMarca());  
                    ps.setDouble(6, producto.getPrecio());
                    ps.executeUpdate();
                }
            } catch (Exception ex){
                System.err.println("\nError guardando los productos en la base de datos: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("\nError guardando los productos en la base de datos: " + e.getMessage());
        }
    }

}