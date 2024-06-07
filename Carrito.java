import java.security.Timestamp;
import java.util.ArrayList;

public class Carrito{
    //atributo(s)
    int id;
    String descripcion;
    int cantidad;
    double precio;

    static ArrayList<Carrito> carrito = new ArrayList<>(); //lista del carrito al momento de realizar la compra

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

}
