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
     * 
     */
    public static void anadirProducto(){

        //try-catch para realizar la conexión a la base de datos
        try {
            conexion = Conexion.getConexion();
            
            // pedir el identificador del producto
            System.out.print("\nPor favor, introduce el ID del producto: ");
            int idProducto = sc.nextInt();

            // pedir la categoria del producto
            System.out.print("\nPor favor, escribe la categoría del producto: ");
            String categoriaProducto = sc.next();

            // pedir la descripción (nombre) del producto
            System.out.print("\nPor favor, introduce la descripción del producto: ");
            sc.nextLine();
            String descripcionProducto = sc.nextLine();

            // pedir la cantidad de productos a añadir
            System.out.print("\nPor favor, introduce la cantidad de productos que deseas añadir: ");
            int cantidadProducto = sc.nextInt();

            // pedir la marca del producto
            System.out.print("\nPor favor, escribe la marca del producto: ");
            sc.nextLine();
            String marcaProducto = sc.nextLine();

            // pedir el precio del producto
            System.out.print("\nPor favor, introduce el precio del producto: ");
            double precioProducto = sc.nextDouble();

            // añadir los datos a la lista de productos
            Productos nuevoProducto = new Productos(idProducto, categoriaProducto, descripcionProducto,
            cantidadProducto, marcaProducto, precioProducto);
            Principal.listaProductos.add(nuevoProducto);

        } catch (Exception e) {
            System.err.println("\nError añadiendo un producto: " + e.getMessage());
            Principal.pausar(); //realizar una pausa si sale el error
        }
    }

    /**
     * método para añadir stock a un producto
     */
    public static void anadirStock(){

        try {
            conexion = Conexion.getConexion();

                //pedir el id del producto al que se desea añadir stock
                System.out.print("\nPor favor, introduce el ID del producto al que hay que sumar stock: ");
                int idProducto = sc.nextInt();

                //pedir la cantidad de unidades a sumar
                System.out.print("\nPor favor, introduce el número de unidades a sumar: ");
                int cantidadSumar = sc.nextInt();

                //consulta sql para obtener el stock actual
                String consultaStockActual = "select cantidad from productos where id = " + idProducto + ";";
                Statement sentencia = conexion.createStatement();
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

        } catch (SQLException e) {
            System.err.println("Error actualizando el stock del producto: " + e.getMessage());
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

         //try-catch para realizar la conexión a la base de datos
         try {
            conexion = Conexion.getConexion();

            // pedir la marca cuyos productos deseas eliminar
            System.out.print("\nPor favor, escribe la marca cuyos productos deseas eliminar: ");
            String marcaProductoEliminar = sc.next();

            // consulta sql
            String consultaEliminarMarca = "delete from productos where marca = '" + marcaProductoEliminar + "';";

            // cosas del sql
            Statement sentencia = conexion.createStatement();
            int rs = sentencia.executeUpdate(consultaEliminarMarca);

            System.out.println("Marca eliminada.");

            // realizar una pausa
            Principal.pausar();

    } catch (SQLException e){
        System.err.println("Error eliminando los productos: " + e.getMessage());
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

    /**
     * método para comprobar si es un jefe o un empleado
     */
    public static Empleados comprobarUsuario (int idTrabajador, String claveTrabajador){
        //objeto de la clase empleado
        Empleados empleado = null;

        //consulta de sql
        String consultaComprobarJefe = "select * from empleados where id = ? and claveAcceso = ?";

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //cosas del sql
            PreparedStatement ps = conexion.prepareStatement(consultaComprobarJefe);
            ps.setInt(1, idTrabajador);
            ps.setString(2, claveTrabajador);
            ResultSet rs = ps.executeQuery();

            //comprobar el resultado
            if (rs.next()) {
                //datos del empleado
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String claveAcceso = rs.getString("claveAcceso");
                double salario = rs.getDouble("salario");
                String fechaContratacion = rs.getString("fechaContratacion");
                String fechaDespido = rs.getString("fechaDespido");
                boolean esJefe = rs.getBoolean("esJefe");

                //crear el objeto de empleado
                empleado = new Empleados(id, nombre, dni, apellidos, claveAcceso, salario, fechaContratacion, fechaDespido, esJefe);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error comprobando el tipo de empleado: " + e.getMessage());
        }
        //devolver el resultado
        return empleado;
    }
}
