

public class Productos {
    //atributo(s)
    private int id;
    private String categoria;
    private String descripcion;
    private int cantidad;
    private String marca;
    private double precio;

    //constructor por parámetros
    public Productos (int id, String categoria, String descripcion, int cantidad, String marca, double precio){
        this.id = id;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.marca = marca;
        this.precio = precio;
    }

    //getters y setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //método to string
    @Override
    public String toString() {
        return "Productos [id=" + id + ", categoria=" + categoria + ", descripcion=" + descripcion + ", cantidad="
                + cantidad + ", marca=" + marca + ", precio=" + precio + "]";
    }


    
    
}
