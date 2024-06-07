import java.util.*; //importar todo lo necesario

public class Empleados extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private String dni;
    private String apellidos;
    private String claveAcceso;
    private String contratacion;
    private String despido;
    private double sueldo;
    boolean esJefe;

    //activar scanner
    static Scanner sc = new Scanner(System.in);
    
    //constructor
    public Empleados (int identificacion, String dni, String nombre, String apellidos, String claveAcceso, double sueldo, String contratacion, String despido, boolean esJefe){
        super(identificacion, nombre);
        this.dni = dni;
        this.apellidos = apellidos;
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
        return "Empleado: " +
            " id='" + getIdentificacion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", dni='" + getDni() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", claveAcceso='" + getClaveAcceso() + "'" +
            ", contratacion='" + getContratacion() + "'" +
            ", despido='" + getDespido() + "'" +
            ", sueldo='" + getSueldo() + "'" +
            ", esJefe='" + isEsJefe() + "'";
    }


    /**
     * método para mostrar a todos los empleados
     */
    public static void mostrarEmpleados(){
        //arraylist de los empleados
        ArrayList<Empleados> empleados = new ArrayList<>();

        //llamar al método que realiza la consulta en la base de datos
        EmpleadosDAO.mostrarEmpleados(empleados); //pasarle el arraylist como parámetro

        //mostrar el arraylist
        for (Empleados empleado : empleados) {
            System.out.println("\n" + empleado);
        }
    }

    /**
     * método para añadir a un empleado
     */
    public static void anadirEmpleado(){
        //pedir los datos del empleado
        System.out.println("Por favor, introduce los datos del empleado: ");

        System.out.print("\nId de empleado: ");
        int idEmpleado = sc.nextInt();
        sc.nextLine();

        System.out.print("\nDNI del empleado: ");
        String dniEmpleado = sc.next();

        System.out.print("\nNombre del empleado: ");
        sc.nextLine();
        String nombreEmpleado = sc.nextLine();

        System.out.print("\nApellidos del empleado: ");
        String apellidosEmpleado = sc.nextLine();

        System.out.print("\nClave de acceso (no puede contener espacios): ");
        String claveAcceso = sc.next();

        System.out.print("\nSueldo del empleado: ");
        double sueldoEmpleado = sc.nextDouble();
        sc.nextLine();

        System.out.print("\nFecha de contratación (dia/mes/año): ");
        String fechaContratacion = sc.nextLine();

        System.out.print("\nFecha de despido (opcional): ");
        String fechaDespido = sc.nextLine();

        System.out.print("\n¿Es un jefe? (si, no): ");
        String esJefe = sc.next();

        boolean comprobarJefe = false;

        if (esJefe.equalsIgnoreCase("si")) { //el empleado es un jefe
            comprobarJefe = true; //pasar esto a verdadero
            //crear el objeto
            Empleados nuevoEmpleado = new Empleados(idEmpleado, dniEmpleado, nombreEmpleado, apellidosEmpleado, claveAcceso, sueldoEmpleado, fechaContratacion, fechaDespido, comprobarJefe);

            //llamar al método de la clase DAO
            EmpleadosDAO.anadirEmpleado(nuevoEmpleado);
        } else if(esJefe.equalsIgnoreCase("no")){ //el empleado no es un jefe
            //crear el objeto
            Empleados nuevoEmpleado = new Empleados(idEmpleado, dniEmpleado, nombreEmpleado, apellidosEmpleado, claveAcceso, sueldoEmpleado, fechaContratacion, fechaDespido, comprobarJefe);

            //llamar al método de la clase DAO
            EmpleadosDAO.anadirEmpleado(nuevoEmpleado);
        } else{
            System.err.println("Opción no válida, operación abortada.");
        }
    }
    

    /**
     * método para ascender a un empleado a jefe
     */
    public static void ascenderEmpleado(){
        //pedir el ID del empleado que se quiere ascender
        System.out.print("\nPor favor, escribe el ID del empleado al que quieres ascender: ");
        int idEmpleadoAscender = sc.nextInt();

        //comprobar que se ha encontrado a alguien
        if (EmpleadosDAO.obtenerEmpleado(idEmpleadoAscender) == null) {
            System.err.println("No se encontró un empleado con ese ID.");
        } else {
            System.out.println("Empleado encontrado: " + EmpleadosDAO.obtenerEmpleado(idEmpleadoAscender));

            //preguntar si se desea ascender a ese empleado
            System.out.print("¿Ascender a este empleado? (si, no): ");
            String ascender = sc.next();

            if (ascender.equalsIgnoreCase("si")) {
                //llamar al método para ascender al cliente
                EmpleadosDAO.ascenderEmpleado(idEmpleadoAscender);
            } else if(ascender.equalsIgnoreCase("no")){
                System.out.println("Operación cancelada.");
                Principal.pausar();
            } else{
                System.err.println("Opción no válida, operación abortada.");
                Principal.pausar();
            }
        }

    }

    /**
     * método para eliminar a un empleado
     */
    public static void eliminarEmpleado(){
        //pedir el ID del empleado a eliminar
        System.out.print("Por favor, introduce el ID del empleado a eliminar: ");
        int idEmpleadoEliminar = sc.nextInt();

        //comprobar que se ha encontrado a alguien
        if (EmpleadosDAO.obtenerEmpleado(idEmpleadoEliminar) == null) {
            System.err.println("No se encontró un empleado con ese ID.");
        } else {
            System.out.println("Empleado encontrado: " + EmpleadosDAO.obtenerEmpleado(idEmpleadoEliminar));

            //preguntar si se desea ascender a ese empleado
            System.out.print("¿Eliminar a este empleado? (si, no): ");
            String ascender = sc.next();

            if (ascender.equalsIgnoreCase("si")) {
                //llamar al método para ascender al cliente
                EmpleadosDAO.eliminarEmpleado(idEmpleadoEliminar);
            } else if(ascender.equalsIgnoreCase("no")){
                System.out.println("Operación cancelada.");
                Principal.pausar();
            } else{
                System.err.println("Opción no válida, operación abortada.");
                Principal.pausar();
            }
        }
    }



}
