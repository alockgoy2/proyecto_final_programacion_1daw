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

        do {
            //mostrar primero el stock disponible
            Principal.mostrarProductos();

            //pedir el id del producto que se quiere comprar
            System.out.print("\nPor favor, introduce el ID del producto que quieres comprar: ");
            int idProducto = sc.nextInt();

            //pedir la cantidad de producto que se desea comprar
            System.out.print("\nPor favor, introduce la cantidad de productos que quieres comprar: ");
            int cantidadProducto = sc.nextInt();

            //variable para comprobar que hay unidades disponibles y variables del precio y la descripción
            int cantidadDisponible = 0;
            double precioProducto = 0;
            String descripcionProducto = "";

                //intentar la conexión con la base de datos
                try {
                    conexion = Conexion.getConexion();

                    //consulta de sql
                    String consultaObtenerCantidad = "select cantidad, precio, descripcion from productos where id = '" + idProducto + "';";

                    //cosas del sql
                    Statement sentencia = conexion.createStatement();
                    ResultSet rs = sentencia.executeQuery(consultaObtenerCantidad);

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
                        Principal.carrito.add(new Carrito(idProducto, descripcionProducto, cantidadProducto, precioProducto));
                    } else { //si no hay stock disponible
                        System.err.println("No hay suficiente stock para la cantidad que se desea comprar");
                    }

                } catch (SQLException e) {
                    System.err.println("Error comprobando el stock disponible: " + e.getMessage());
                }
            //preguntar si se desea seguir comprando
            System.out.print("\n¿Seguir comprando? (si,no): ");
            seguirComprando = sc.next();
        } while (!seguirComprando.equalsIgnoreCase("no"));
        //hacer todo lo anterior mientras no se especifique el deseo de dejar de comprar

        //variable para el precio total
        double precioTotal = 0;

        //mostrar el resumen de la compra
        System.out.println("\nResumen de compra: ");
        for (Carrito compra : Principal.carrito) {
            double subTotal = compra.cantidad * compra.precio;
            precioTotal += subTotal; //calcular el precio
            System.out.println("\nProducto: " + compra.descripcion + " Cantidad: " + compra.cantidad + " Precio por unidad: " + compra.precio);
        }

        //mostar el total sin descuento
        System.out.println("\nTotal sin descuento: " + precioTotal);

        //preguntar si es un cliente registrado
        System.out.print("¿Es un cliente registrado? (si,no): ");
        String clienteRegistrado = sc.next();

        //comprobación en caso de ser cliente registrado
        if (clienteRegistrado.equalsIgnoreCase("si")) {
            System.out.print("\nPor favor, introduce tu ID de cliente: ");
            int idCliente = sc.nextInt();

            //intentar la conexión a la base de datos y comprobar que el cliente existe
            try {
                conexion = Conexion.getConexion();

                //consulta para buscar al cliente
                String consultaBuscarCliente = "select vip from clientes where identificacion = '" + idCliente + "';";

                //cosas del sql
                Statement sentencia = conexion.createStatement();
                ResultSet rs = sentencia.executeQuery(consultaBuscarCliente);

                //comprobar si el cliente existe y si es un cliente vip
                if (rs.next()) {
                    boolean esVip = rs.getBoolean("vip");
                    double descuento = esVip ? 0.15 : 0.05;
                    double totalConDescuento = precioTotal * (1 - descuento);
                    System.out.println("Se ha aplicado un descuento del " + (descuento * 100) + "%.");
                    System.out.println("Total con descuento: " + totalConDescuento);
                } else {
                    System.err.println("\nCliente no encontrado.");
                }
            } catch (SQLException e) {
                System.err.println("Error buscando al cliente: " + e.getMessage());
            }
        }
    }
}
