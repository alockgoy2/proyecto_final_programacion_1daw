import java.security.Timestamp;
import java.util.*;

public class Carrito{
    //atributo(s)
    int id;
    String descripcion;
    int cantidad;
    double precio;

    static ArrayList<Carrito> carrito = new ArrayList<>(); //lista del carrito al momento de realizar la compra
    static Scanner sc = new Scanner(System.in); //scanner

    //atributos adicionales (para ver el historial de compras)
    private int idProducto;
    private String nombreCliente;
    private double totalConDescuento;
    private String fecha;
    
    //constructor con todos los parámetros
    public Carrito(int id, int idProducto, String descripcion, int cantidad, double precio, String nombreCliente, double totalConDescuento, String fecha) {
        this.id = id;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombreCliente = nombreCliente;
        this.totalConDescuento = totalConDescuento;
        this.fecha = fecha;
    }

    //constructor con los parámetros para el método del carrito
    public Carrito(int id, String descripcion, int cantidad, double precio){
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    //getters y setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getTotalConDescuento() {
        return this.totalConDescuento;
    }

    public void setTotalConDescuento(double totalConDescuento) {
        this.totalConDescuento = totalConDescuento;
    }

    public int getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    //método to string
    @Override
    public String toString() {
        return "Compra: " +
            " id='" + getId() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad='" + getCantidad() + "'" +
            ", precio='" + getPrecio() + "'" +
            ", idProducto='" + getIdProducto() + "'" +
            ", nombreCliente='" + getNombreCliente() + "'" +
            ", totalConDescuento='" + getTotalConDescuento() + "'" +
            ", fecha='" + getFecha();
    }

    /**
     * método para ver el historial de compras
     */
    public static void verCompras(){
        //arraylist a pasar como parámetro
        ArrayList<Carrito> compras = new ArrayList<>();

        //llamar al método de la clase DAO
        CarritoDAO.verCompras(compras); //pasarle el arraylist como parámetro

        //mostrar los datos del arraylist
        for (Carrito compra : compras) {
            System.out.println("\n" + compra);
        }
    }

    //solicitudes de datos para el método de compras de la clase CarritosDAO (no he sabido hacerlo de otra forma)
    public static int pedirIdProducto(){
        //pedir el id del producto que se quiere comprar
        System.out.print("\nPor favor, introduce el ID del producto que quieres comprar: ");
        int idProducto = sc.nextInt();

        return idProducto;
    }

    public static int pedirCantidadProducto(){
        //pedir la cantidad de producto que se desea comprar
        System.out.print("\nPor favor, introduce la cantidad de productos que quieres comprar: ");
        int cantidadProducto = sc.nextInt();

        return cantidadProducto;
    }

    public static String preguntarSeguirComprando(){
        //preguntar si se desea seguir comprando
        System.out.print("\n¿Seguir comprando? (si,no): ");
        String seguirComprando = sc.next();

        return seguirComprando;
    }

    public static String seguroRealizarCompra(){
        //preguntar si se desea proceder con la compra
        System.out.print("\n¿Estás seguro de querer realizar esta compra? (si, no): ");
        String estoySeguro = sc.next();

        return estoySeguro;
    }

    public static String preguntarClienteRegistrado(){
        // preguntar si es un cliente registrado
        System.out.print("\n¿Es un cliente registrado? (si,no): ");
        String clienteRegistrado = sc.next();

        return clienteRegistrado;
    }

    public static int pedirIdCliente(){
        //pedir el ID del cliente
        System.out.print("\nPor favor, introduce tu ID de cliente: ");
        int idCliente = sc.nextInt();

        return idCliente;
    }
}