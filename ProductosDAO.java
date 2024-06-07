//importar todo lo necesario
import java.sql.*;
import java.util.*;

public class ProductosDAO {
    //cosas para la conexión
    static Connection conexion;

    /**
     * método para eliminar un producto por marca
     */
    public static void eliminarPorMarca(String marcaProductoEliminar){
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaEliminarMarca = "delete from productos where marca = ?;";

            //cosas del sql
            PreparedStatement sentenciaEliminarMarca = conexion.prepareStatement(consultaEliminarMarca);
            sentenciaEliminarMarca.setString(1, marcaProductoEliminar);

            //ejecutar la sentencia
            sentenciaEliminarMarca.executeUpdate();

            System.out.print("\nMarca eliminada.");
            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error borrando la marca: " + e.getMessage());
        }
    }

    /**
     * método para añadir un producto
     */
    public static void anadirProducto (Productos nuevProducto){ //pasar el objeto como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaAnadirProducto = "insert into productos values (?, ?, ?, ?, ?, ?);";

            //cosas del sql
            PreparedStatement sentenciaAnadirProducto = conexion.prepareStatement(consultaAnadirProducto);
            sentenciaAnadirProducto.setInt(1, nuevProducto.getId());
            sentenciaAnadirProducto.setString(2, nuevProducto.getCategoria());
            sentenciaAnadirProducto.setString(3, nuevProducto.getDescripcion());
            sentenciaAnadirProducto.setInt(4, nuevProducto.getCantidad());
            sentenciaAnadirProducto.setString(5, nuevProducto.getMarca());
            sentenciaAnadirProducto.setDouble(6, nuevProducto.getPrecio());

            //ejecutar la sentencia
            sentenciaAnadirProducto.executeUpdate();

            System.out.println("Producto añadido.");
        } catch (SQLException e) {
            System.err.println("Error añadiendo el producto: " + e.getMessage());
        }
    }

    /**
     * método para mostrar todos los productos
     */
    public static ArrayList mostrarProductos (ArrayList listaProductos){ //pasar el arraylist como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaMostrarProductos = "select * from productos;";

            //cosas del sql
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(consultaMostrarProductos);

            //objeto de la clase Productos
            Productos producto = null;

            //bucle
            while (rs.next()) {
                //añadir los datos al objeto
                int idProducto = rs.getInt("id");
                String categoriaProducto = rs.getString("categoria");
                String descripcionProducto = rs.getString("descripcion");
                int cantidadProducto = rs.getInt("cantidad");
                String marcaProducto = rs.getString("marca");
                double precioProducto = rs.getDouble("precio");

                producto = new Productos(idProducto, categoriaProducto, descripcionProducto, cantidadProducto, marcaProducto, precioProducto);

                //añadir el objeto a la lista
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error mostrando los productos: " + e.getMessage());
        }

        //devolver la lista
        return listaProductos;
    }

    /**
     * método para añadir stock a un producto
     */
    public static void anadirStock(int idProducto, int cantidadSumar){ //pasar los datos como parámetros
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql para obtener el stock actual
            String consultaStockActual = "select cantidad from productos where id = ? ;";

            //cosas del sql
            PreparedStatement sentencia1 = conexion.prepareStatement(consultaStockActual);
            sentencia1.setInt(1, idProducto);

            //ejecutar la sentencia
            ResultSet rsStockActual = sentencia1.executeQuery();
            int stockActual = 0;

            if (rsStockActual.next()) {
                stockActual = rsStockActual.getInt("cantidad");
            }

            //consulta sql para actualizar el stock
            int nuevoStock = stockActual + cantidadSumar;
            String consultaActualizarStock = "update productos set cantidad = ? where id = ? ;";
            PreparedStatement sentencia2 = conexion.prepareStatement(consultaActualizarStock);
            sentencia2.setInt(1, nuevoStock);
            sentencia2.setInt(2, idProducto);

            //ejecutar la sentencia
            sentencia2.executeUpdate();

            System.out.println("\nStock actualizado.");
            Principal.pausar();
        } catch (SQLException e) {
            System.err.println("Error actualizando el stock: " + e.getMessage());
        }
    }


}
