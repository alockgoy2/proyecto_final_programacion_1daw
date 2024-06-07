//importar todo lo necesario
import java.util.*;
import java.sql.*;

public class CarritoDAO {

    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

    /**
     * método para comprar
     */
    public static void quieroComprar(){

        //variable para seguir comprando
        String seguirComprando = "no";

        //variables para el manejo de datos
        int cantidadProducto = 0;
        int cantidadDisponible = 0;
        int idProducto = 0;

        do {
            //mostrar primero el stock disponible
            Productos.mostrarProductos();

            //pedir el id del producto que se quiere comprar
            idProducto = Carrito.pedirIdProducto();

            //pedir la cantidad de producto que se desea comprar
            cantidadProducto = Carrito.pedirCantidadProducto();

            //variable para comprobar que hay unidades disponibles y variables del precio y la descripción
            cantidadDisponible = 0;
            double precioProducto = 0;
            String descripcionProducto = "";

                //intentar la conexión con la base de datos
                try {
                    conexion = Conexion.getConexion();

                    //consulta de sql
                    String consultaObtenerCantidad = "select cantidad, precio, descripcion from productos where id = ? ;";

                    //cosas del sql
                    PreparedStatement sentencia = conexion.prepareStatement(consultaObtenerCantidad);
                    sentencia.setInt(1, idProducto);
                    ResultSet rs = sentencia.executeQuery();

                    //ejecutar la sentencia y obtener la cantidad y otros datos
                    if (rs.next()) {
                        cantidadDisponible = rs.getInt("cantidad");
                        precioProducto = rs.getDouble("precio");
                        descripcionProducto = rs.getString("descripcion");
                    } else {
                        System.err.println("No se ha encontrado ningún producto con ID " + idProducto);
                        continue;  //saltar al siguiente ciclo del bucle do-while
                    }

                    //comprobar si hay cantidad disponible
                    if (cantidadProducto <= cantidadDisponible) { //si hay stock disponible
                        //actualizar la cantidad en la base de datos
                        String actualizarCantidad = "update productos set cantidad = " + (cantidadDisponible - cantidadProducto) + " where id = '" + idProducto + "';";
                        sentencia.executeUpdate(actualizarCantidad);

                        //añadir el producto comprado a la lista
                        Carrito.carrito.add(new Carrito(idProducto, descripcionProducto, cantidadProducto, precioProducto));
                    } else { //si no hay stock disponible
                        System.err.println("No hay suficiente stock para la cantidad que se desea comprar");
                    }

                } catch (SQLException e) {
                    System.err.println("Error comprobando el stock disponible: " + e.getMessage());
                }

            //preguntar si se desea seguir comprando
            seguirComprando = Carrito.preguntarSeguirComprando();

        } while (!seguirComprando.equalsIgnoreCase("no"));
        //hacer todo lo anterior mientras no se especifique el deseo de dejar de comprar

        //variable para el precio total
        double precioTotal = 0;

        //mostrar el resumen de la compra
        System.out.println("\nResumen de compra: ");
        for (Carrito compra : Carrito.carrito) {
            double subTotal = compra.cantidad * compra.precio;
            precioTotal += subTotal; //calcular el precio
            System.out.println("\nProducto: " + compra.descripcion + " Cantidad: " + compra.cantidad + " Precio por unidad: " + compra.precio);
        }

        //mostar el total sin descuento
        System.out.println("\nTotal sin descuento: " + precioTotal);

        Principal.pausar(); //realizar una pequeña pausa

        //preguntar si se desea proceder con la compra
        String estoySeguro = Carrito.seguroRealizarCompra();

        if (estoySeguro.equalsIgnoreCase("si")) { // el cliente quiere terminar la compra
            // variable para el descuento y el nombre del cliente
            double totalConDescuento = precioTotal;
            String nombreCliente = "cliente no registrado.";

            // preguntar si es un cliente registrado
            String clienteRegistrado = Carrito.preguntarClienteRegistrado();

            // comprobación en caso de ser cliente registrado
            if (clienteRegistrado.equalsIgnoreCase("si")) {
                int idCliente = Carrito.pedirIdCliente();

                // intentar la conexión a la base de datos y comprobar que el cliente existe
                try {
                    conexion = Conexion.getConexion();

                    // consulta para buscar al cliente
                    String consultaBuscarCliente = "select nombre, vip from clientes where identificacion = ? ;";

                    // cosas del sql
                    PreparedStatement sentencia = conexion.prepareStatement(consultaBuscarCliente);
                    sentencia.setInt(1, idCliente);
                    ResultSet rs = sentencia.executeQuery();

                    // comprobar si el cliente existe y si es un cliente vip
                    if (rs.next()) {
                        nombreCliente = rs.getString("nombre");
                        boolean esVip = rs.getBoolean("vip");
                        double descuento = esVip ? 0.15 : 0.05;
                        totalConDescuento = precioTotal * (1 - descuento);
                        System.out.println("Se ha aplicado un descuento del " + (descuento * 100) + "%.");
                        System.out.println("Total con descuento: " + totalConDescuento);
                        Principal.pausar(); // realizar una pequeña pausa
                    } else {
                        System.err.println("\nCliente no encontrado.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error buscando al cliente: " + e.getMessage());
                }
            }

            // guardar los datos en la base de datos
            try {
                conexion = Conexion.getConexion();
                String consultaInsertarCompra = "insert into compras (idProducto, descripcion, cantidad, precio, nombreCliente, totalConDescuento) values (?, ?, ?, ?, ?, ?)";
        
                for (Carrito compra : Carrito.carrito) {
                    PreparedStatement ps = conexion.prepareStatement(consultaInsertarCompra);
                    ps.setInt(1, compra.id);
                    ps.setString(2, compra.descripcion);
                    ps.setInt(3, compra.cantidad);
                    ps.setDouble(4, compra.precio);
                    ps.setString(5, nombreCliente);
                    ps.setDouble(6, totalConDescuento);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Error guardando los datos de la compra en la base de datos: " + e.getMessage());
            }
        } else if (estoySeguro.equalsIgnoreCase("no")) { // el cliente no quiere terminar la comra
            System.out.println("Compra anulada.");

            //"regresar" al stock original
            try {
                for (Carrito compra : Carrito.carrito) {
                    String consultaStockOriginal = "update productos set cantidad = cantidad + ? where id = ?";

                    // cosas del sql
                    PreparedStatement ps = conexion.prepareStatement(consultaStockOriginal);
                    ps.setInt(1, compra.cantidad);
                    ps.setInt(2, compra.id);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Error 'volviendo' al stock original: " + e.getMessage());
            }
        } else {
            System.err.println("Opción no válida, compra anulada.");

            //"regresar" al stock original
            try {
                for (Carrito compra : Carrito.carrito) {
                    String consultaStockOriginal = "update productos set cantidad = cantidad + ? where id = ?";

                    // cosas del sql
                    PreparedStatement ps = conexion.prepareStatement(consultaStockOriginal);
                    ps.setInt(1, compra.cantidad);
                    ps.setInt(2, compra.id);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.err.println("Error 'volviendo' al stock original: " + e.getMessage());
            }
        }
    }

    /**
     * método para ver el historial de compras
     */
    public static ArrayList verCompras (ArrayList<Carrito> compras){ //pasarle el arraylist como parámetro
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //consulta sql
            String consultaVerCompras = "select * from compras;";

            //cosas del sql
            Statement sentenciaVerCompras = conexion.createStatement();
            ResultSet rs = sentenciaVerCompras.executeQuery(consultaVerCompras);

            //bucle
            while (rs.next()) {
                int id = rs.getInt("idCompra");
                int idProducto = rs.getInt("idProducto");
                String descripcion = rs.getString("descripcion");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                String nombreCliente = rs.getString("nombreCliente");
                double totalConDescuento = rs.getDouble("totalConDescuento");
                String fecha = rs.getString("fecha");

                //crear el objeto y añadirlo al arraylist
                Carrito compra = new Carrito(id, idProducto, descripcion, cantidad, precio, nombreCliente, totalConDescuento, fecha);
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Error viendo el historial de compras: " + e.getMessage());
        }

        //devolver la lista
        return compras;
    }

}