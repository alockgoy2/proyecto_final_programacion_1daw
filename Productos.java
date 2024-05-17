

public class Productos {
    //atributo(s)
    private int id;
    private String descripcion;
    private Double precio;
    private int cantidad;
    private String categoria;

    //constructor por parámetros
    public Productos (int id, String descripcion, Double precio, int cantidad, String categoria){
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
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

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    

    //método to string
    @Override
    public String toString() {
        return "Productos [descripcion=" + descripcion + ", precio=" + precio + ", cantidad=" + cantidad
                + ", categoria=" + categoria + "]";
    }


    
}
