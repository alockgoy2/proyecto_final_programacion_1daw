//importar todo lo necesario
import java.sql.*;
import java.util.*;

public class EmpleadosDAO {
    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

    /**
     * método para mostrar a todos los empleados
     */
    public static ArrayList mostrarEmpleados(ArrayList<Empleados> empleados){ //pasarle el arraylist como parámetro

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();
            Principal.pausar();

            //consulta sql
            String consultaMostrarEmpleados = "select * from empleados;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarEmpleados);

            //objeto de la clase empleados
            Empleados empleado = null;

            //mostrar los datos de los empleados
            while (rs.next()) {
                //añadir los datos al objeto 
                int idEmpleado = rs.getInt("id");
                String dniEmpleado = rs.getString("dni");
                String nombreEmpleado = rs.getString("nombre");
                String apellidosEmpleado = rs.getString("apellidos");
                String claveAcceso = "************";
                double salarioEmpleado = rs.getDouble("salario");
                String fechaContratacion = rs.getString("fechaContratacion");
                String fechaDespido = rs.getString("fechaDespido");
                boolean esJefe = rs.getBoolean("esJefe");

                empleado = new Empleados(idEmpleado, dniEmpleado, nombreEmpleado, apellidosEmpleado, claveAcceso, salarioEmpleado, fechaContratacion, fechaDespido, esJefe);

            //añadir el empleado al arraylist
            empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            Principal.pausar();
        }

        //devolver la lista de empleados
        return empleados;
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

    /**
     * método para registrar un empleado
     */
    public static void anadirEmpleado (Empleados nuevoEmpleado){ //pasar el objeto como atributo
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaAnadirEmpleado = "insert into empleados (id, dni, nombre, apellidos, claveAcceso, salario, fechaContratacion, fechaDespido, esJefe) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            //cosas del sql
            PreparedStatement sentenciaAnadirEmpleado = conexion.prepareStatement(consultaAnadirEmpleado);
            sentenciaAnadirEmpleado.setInt(1, nuevoEmpleado.getIdentificacion());
            sentenciaAnadirEmpleado.setString(2, nuevoEmpleado.getDni());
            sentenciaAnadirEmpleado.setString(3, nuevoEmpleado.getNombre());
            sentenciaAnadirEmpleado.setString(4, nuevoEmpleado.getApellidos());
            sentenciaAnadirEmpleado.setString(5, nuevoEmpleado.getClaveAcceso());
            sentenciaAnadirEmpleado.setDouble(6, nuevoEmpleado.getSueldo());
            sentenciaAnadirEmpleado.setString(7, nuevoEmpleado.getContratacion());
            sentenciaAnadirEmpleado.setString(8, nuevoEmpleado.getDespido());
            sentenciaAnadirEmpleado.setBoolean(9, nuevoEmpleado.isEsJefe());

            //ejecutar la sentencia
            sentenciaAnadirEmpleado.executeUpdate();

            System.out.println("Trabajador añadido.");
        } catch (SQLException e) {
            System.err.println("Error añadiendo al empleado: " + e.getMessage());
        }
    }

    /**
     * método para ascender un empleado a jefe
     */
    public static void ascenderEmpleado (int idEmpleadoAscender){ //pasarle el ID como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta para pasar a jefe
            String consultaAscenderEmpleado = "update empleados set esJefe = 1 where id = ?;";

            //cosas del sql
            PreparedStatement sentenciaAscender = conexion.prepareStatement(consultaAscenderEmpleado);
            sentenciaAscender.setInt(1, idEmpleadoAscender);

            //ejecutar la sentencia
            sentenciaAscender.executeUpdate();

            System.out.println("Empleado ascendido.");
        } catch (SQLException e) {
            System.err.println("Error ascendiendo al empleado: " + e.getMessage());
        }
    }

    /**
     * método para obtener los datos de un empleado
     */
    public static String obtenerEmpleado (int idEmpleadoAscender){
        String empleadoEncontrado = null;

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaObtenerEmpleado = "select nombre, apellidos from empleados where id = ?";

            //cosas del sql
            PreparedStatement ps = conexion.prepareStatement(consultaObtenerEmpleado);
            ps.setInt(1, idEmpleadoAscender);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                empleadoEncontrado = rs.getString("nombre") + " " + rs.getString("apellidos");
            }
        } catch (SQLException e) {
            System.err.println("Error buscando al empleado: " + e.getMessage());
        }

        return empleadoEncontrado;
    }

    /**
     * método para eliminar a un empleado en base a su ID
     */
    public static void eliminarEmpleado (int idEmpleadoEliminar){ //pasar el ID como atributo
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaEliminarEmpleado = "delete from empleados where id = ?";

            //cosas del sql
            PreparedStatement sentenciaEliminarEmpleado = conexion.prepareStatement(consultaEliminarEmpleado);
            sentenciaEliminarEmpleado.setInt(1, idEmpleadoEliminar);

            sentenciaEliminarEmpleado.executeUpdate();

            System.out.println("Empleado eliminado.");
        } catch (SQLException e) {
            System.err.println("Error eliminando al empleado: " + e.getMessage());
        }
    }

}
