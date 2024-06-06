public class Empleados extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private String dni;
    private String apellidos;
    private String claveAcceso;
    private String contratacion;
    private String despido;
    private double sueldo;
    boolean esJefe;
    
    //constructor
    public Empleados (int identificacion, String nombre, String dni, String apellidos, String claveAcceso, double sueldo, String contratacion, String despido, boolean esJefe){
        super(identificacion, nombre);
        this.apellidos = apellidos;
        this.dni = dni;
        this.claveAcceso = claveAcceso;
        this.sueldo = sueldo;
        this.contratacion = contratacion;
        this.despido = despido;
        this.esJefe = esJefe;
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


    public boolean isEsJefe() {
        return this.esJefe;
    }

    public boolean getEsJefe() {
        return this.esJefe;
    }

    public void setEsJefe(boolean esJefe) {
        this.esJefe = esJefe;
    }


    //método to string

    @Override
    public String toString() {
        return "Empleado" +
            " dni='" + getDni() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", claveAcceso='" + getClaveAcceso() + "'" +
            ", contratacion='" + getContratacion() + "'" +
            ", despido='" + getDespido() + "'" +
            ", sueldo='" + getSueldo() + "'" +
            ", esJefe='" + isEsJefe();
    }
    
}
