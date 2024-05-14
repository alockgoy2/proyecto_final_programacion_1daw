public class Persona {
    //atributo(s)
    private int identificacion;
    private String nombre;

    //constructor por parámetros
    public Persona(int identificacion, String nombre){
        this.identificacion = identificacion;
        this.nombre = nombre;
    }

    //getters y setters

    public int getIdentificacion() {
        return this.identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
