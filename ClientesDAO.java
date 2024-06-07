//importar todo lo necesario
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ClientesDAO {
    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

    /**
     * método para registrar un cliente
     */
    public static void registrarCliente(Clientes nuevoCliente){ //pasarle el objeto como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta de sql para añadir un cliente en la base de datos
            String consultaInsertarCliente = "insert into clientes (nombre, email, vip) values (?, ?, ?);";

            //cosas del sql
            PreparedStatement sentenciaInsertarCliente = conexion.prepareStatement(consultaInsertarCliente);
            sentenciaInsertarCliente.setString(1, nuevoCliente.getNombre());
            sentenciaInsertarCliente.setString(2, nuevoCliente.getEmail());
            sentenciaInsertarCliente.setBoolean(3, nuevoCliente.isVip());
            sentenciaInsertarCliente.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error registrando al cliente: " + e.getMessage());
        }
    }


    /**
     * método para modificar a un cliente
     */
    public static void modificarCliente(Clientes clienteModificado, int idClienteModificar){ //pasar los datos como parámetros
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaModificarCliente = "update clientes set nombre = ?, email = ?, vip = ? where identificacion = ?;";

            //cosas del sql
            PreparedStatement sentenciaModificarCliente = conexion.prepareStatement(consultaModificarCliente);
            sentenciaModificarCliente.setString(1, clienteModificado.getNombre());
            sentenciaModificarCliente.setString(2, clienteModificado.getEmail());
            sentenciaModificarCliente.setBoolean(3, clienteModificado.isVip());
            sentenciaModificarCliente.setInt(4, idClienteModificar);

            //ejecutar la sentencia
            sentenciaModificarCliente.executeUpdate();

            System.out.println("Cliente actualizado.");
            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error modificando los datos del cliente: " + e.getMessage());
        }
    }

    /**
     * método para eliminar a un cliente
     */
    public static void eliminarCliente(int idClienteEliminar){ //pasarle el ID como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaEliminarCliente = "delete from clientes where identificacion = ?;";

            //cosas del sql
            PreparedStatement sentenciaEliminarCliente = conexion.prepareStatement(consultaEliminarCliente);
            sentenciaEliminarCliente.setInt(1, idClienteEliminar);

            //ejecutar la sentencia
            sentenciaEliminarCliente.executeUpdate();

            System.out.print("\nCliente eliminado.");
            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error al cliente: " + e.getMessage());
        }
    }

    /**
     * método para mostrar todos los clientes
     */
    public static ArrayList mostrarClientes(ArrayList listaClientes){ //pasar el arraylist como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta de SQL
            String consultaSQL = "select * from clientes";

            //cosas del sql
            Statement verClientes = conexion.createStatement();
            ResultSet rs = verClientes.executeQuery(consultaSQL);

            //objeto de la clase clientes
            Clientes cliente = null;

            //bucle
            while (rs.next()) {
                int idCliente = rs.getInt("identificacion");
                String nombreCliente = rs.getString("nombre");
                String emailCliente = rs.getString("email");
                boolean esVip = rs.getBoolean("vip");

                //añadir los datos al objeto
                cliente = new Clientes(idCliente, nombreCliente, emailCliente, esVip);

                //añadir el objeto a la lista
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error mostrando los clientes: " + e.getMessage());
        }

        //devolver la lista
        return listaClientes;
    }

     /**
     * método para obtener los datos de un empleado
     */
    public static String obtenerCliente (int idClienteModificar){
        String clienteEncontrado = null;

        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaObtenerCliente = "select nombre from clientes where identificacion = ?";

            //cosas del sql
            PreparedStatement ps = conexion.prepareStatement(consultaObtenerCliente);
            ps.setInt(1, idClienteModificar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                clienteEncontrado = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.err.println("Error buscando al cliente: " + e.getMessage());
        }

        return clienteEncontrado;
    }

}

