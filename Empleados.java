public class Empleados extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private String dni;
    private String apellidos;
    private String claveAcceso;
    private String contratacion;
    private String despido;
    private double sueldo;
    
    //constructor
    public Empleados (int identificacion, String dni, String nombre, String apellidos, String claveAcceso, double sueldo, String contratacion, String despido){
        super(identificacion, nombre);
        this.apellidos = apellidos;
        this.dni = dni;
        this.claveAcceso = claveAcceso;
        this.sueldo = sueldo;
        this.contratacion = contratacion;
        this.despido = despido;
    }
    
    //getters y setters

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClaveAcceso() {
        return this.claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

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
