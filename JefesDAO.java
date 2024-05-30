//importar todo lo necesario
import java.sql.*;
import java.util.*;

public class JefesDAO {
    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

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
        Principal.listaEmpleados.add(nuevoEmpleado);
        System.out.println("Empleado añadido.");

    }

        /**
     * método para eliminar a un empleado
     * NOTA: Este método elimina al cliente de la base de datos, no del arraylist
     */
    public static void eliminarEmpleado(){
        //pedir el id del empleado que se quiere eliminar
        System.out.print("Por favor, introduce el identificador del empleado que quieres eliminar: ");
        int idEmpleado = sc.nextInt();

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            System.out.println("Conexión realizada.");
            Principal.pausar();

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

            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            Principal.pausar();
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
     * NOTA: Este método elimina al empleado de la base de datos, no del arraylist
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
     * método para guardar los empleados al cerrar el programa
     */
    public static void guardarEmpleados(){
        //intentar la conexión con la base de datos
        try {
            conexion = Conexion.getConexion();
            String consultaInsertarEmpleados = "insert into empleados values (?, ?, ?, ?, ?, ?, ?, ?);";

            //intentar insertar los empleados
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarEmpleados)) {
                for (Empleados empleado : Principal.listaEmpleados) {
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
            } catch (SQLException ex){
                System.err.println("\nError guardando los empleados en la base de datos: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error guardando los empleados en la base de datos: " + e.getMessage());
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
