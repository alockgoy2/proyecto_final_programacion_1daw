public class Empleados extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private String contratacion;
    private String despido;
    private double sueldo;
    
    //constructor
    public Empleados (int identificacion, String nombre, String contratacion, String despido, double sueldo){
        super(identificacion, nombre);
        this.contratacion = contratacion;
        this.despido = despido;
        this.sueldo = sueldo;
    }
    
    //getters y setters

    public String getContratacion() {
        return this.contratacion;
    }

    public void setContratacion(String contratacion) {
        this.contratacion = contratacion;
    }

    public String getDespido() {
        return this.despido;
    }

    public void setDespido(String despido) {
        this.despido = despido;
    }

    public double getSueldo() {
        return this.sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    
}
