

public class Productos {
    //atributo(s)
    String descripcion;
    Double precio;
    int cantidad;
    String categoria;

    //constructor por parámetros
    public Productos (String descripcion, Double precio, int cantidad, String categoria){
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    //getters y setters


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
