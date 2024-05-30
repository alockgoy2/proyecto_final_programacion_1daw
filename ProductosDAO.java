//importar todo lo necesario
import java.sql.*;
import java.util.*;

public class ProductosDAO {
    //cosas para la conexión
    static Connection conexion;

        /**
     * método para guardar los productos al cerrar el programa
     */
    public static void guardarProductos(){
        //intentar la conexión con la base de datos
        try {
            conexion = Conexion.getConexion();
            String consultaInsertarProductos = "insert into productos values (?, ?, ?, ?, ?, ?);";

            //intentar insertar los productos
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarProductos)) {
                for (Productos producto : Principal.listaProductos) {
                    ps.setInt(1, producto.getId());
                    ps.setString(2, producto.getCategoria());
                    ps.setString(3, producto.getDescripcion());
                    ps.setInt(4, producto.getCantidad());
                    ps.setString(5, producto.getMarca());  
                    ps.setDouble(6, producto.getPrecio());
                    ps.executeUpdate();
                }
            } catch (SQLException ex){
                System.err.println("\nError guardando los productos en la base de datos: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("\nError guardando los productos en la base de datos: " + e.getMessage());
        }
    }
}
