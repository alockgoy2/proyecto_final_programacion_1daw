//importar todo lo necesario
import java.sql.*;
import java.util.*;

public class EmpleadosDAO {
    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

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
            conexion = Conexion.getConexion();
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
            Principal.pausar();

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
                Principal.listaProductos.add(nuevoProducto);

            } else { //no se ha encontrado un empleado
                System.err.println("Identificador o contraseña incorrectos.");
                Principal.pausar();
            }

        } catch (SQLException e) {
            System.err.println("\nError conectando a la base de datos: " + e.getMessage());
            Principal.pausar(); //realizar una pausa si sale el error
        }
    }

        /**
     * método para añadir stock a un producto
     */
    public static void anadirStock(){
        //pedir el logueo del empleado
        System.out.print("\nPor favor, introduce tu ID de empleado: ");
        int idEmpleado = sc.nextInt();
        System.out.print("Por favor, introduce tu contraseña: ");
        String claveAccesoEmpleado = sc.next();

        try {
            conexion = Conexion.getConexion();

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

            if (nombreEmpleado != "") { //se ha encontrado un empleado
                //pedir el id del producto al que se desea añadir stock
                System.out.print("\nPor favor, introduce el ID del producto al que hay que sumar stock: ");
                int idProducto = sc.nextInt();

                //pedir la cantidad de unidades a sumar
                System.out.print("\nPor favor, introduce el número de unidades a sumar: ");
                int cantidadSumar = sc.nextInt();

                //consulta sql para obtener el stock actual
                String consultaStockActual = "select cantidad from productos where id = " + idProducto + ";";
                ResultSet rsStockActual = sentencia.executeQuery(consultaStockActual);
                int stockActual = 0;

                if (rsStockActual.next()) {
                    stockActual = rsStockActual.getInt("cantidad");
                }

                //consulta sql para actualizar el stock
                int nuevoStock = stockActual + cantidadSumar;
                String consultaActualizarStock = "update productos set cantidad = '" + nuevoStock + "' where id = '" + idProducto + "';";

                // cosas del sql
                sentencia = conexion.createStatement();
                int filasAfectadas = sentencia.executeUpdate(consultaActualizarStock);
                System.out.println("\nStock actualizado.");
            } else { //no se ha encontrado un empleado
                System.err.println("\nIdentificador o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
        }
    }

        /**
     * método para mostrar a todos los clientes
     * aparentemente funciona
     */
    public static void mostrarClientes(){

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            System.out.println("Conexión realizada.");
            Principal.pausar();

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
            Principal.pausar();
        }
    }

        /**
     * método para eliminar a un cliente
     * NOTA: Este método elimina al cliente de la base de datos, no del arraylist
     */
    public static void eliminarCliente(){
        //pedir el ID del cliente a eliminar
        System.out.print("Por favor, escribe el ID del cliente que quieres eliminar: ");
        int idClienteEliminar = sc.nextInt();

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            System.out.println("Conexión realizada.");
            Principal.pausar();

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

            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            Principal.pausar();
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
            conexion = Conexion.getConexion();
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
            Principal.pausar();

        //pedir la marca del producto que se desea eliminar
        System.out.print("\nPor favor, escribe la marca cuyos productos deseas eliminar: ");
    } catch (SQLException e){
        System.err.println("Error conectando a la base de datos: " + e.getMessage());
    }
}

        /**
     * método para mostrar a todos los empleados
     */
    public static void mostrarEmpleados(){

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            Principal.pausar();

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
            Principal.pausar();
        }
    }
}
