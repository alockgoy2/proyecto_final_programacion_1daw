//importar lo necesario
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Clientes extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private String email;
    private boolean vip;

    //activar Scanner
    static Scanner sc = new Scanner(System.in);

    //constructor
    public Clientes (int identificacion, String nombre, String email, boolean vip){
        super(identificacion, nombre);
        this.email = email;
        this.vip = vip;
    }

    //getters y setters

    public boolean isVip() {
        return this.vip;
    }

    public boolean getVip() {
        return this.vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //método to string
    @Override
    public String toString() {
        return "Cliente: " +
            " id= " + getIdentificacion() +
            " nombre= " + getNombre() +
            " email='" + getEmail() + "'" +
            ", vip='" + isVip();
    }

    /**
     * método para registrar un cliente
     */
    public static void registrarCliente(){
        //pedir los datos del cliente
        System.out.println("\nPor favor, introduce los datos del cliente: ");
        System.out.print("Nombre: ");
        String nombreCliente = sc.nextLine();
        sc.nextLine();

        System.out.print("Correo electrónico: ");
        String emailCliente = sc.next();

        System.out.print("\n¿VIP? (1 = si, 0 = no): ");
        int comprobarVip = sc.nextInt(); //preguntar si es vip

        boolean clienteVip = false;

        //comprobar si es un cliente vip
        if (comprobarVip == 1) {
            clienteVip = true;
        }

        //crear el objeto
        Clientes nuevoCliente = new Clientes(0, nombreCliente, emailCliente, clienteVip);

        System.out.println("\nAñadiendo cliente...");
        Principal.pausar();

        //llamar al método que realiza la consulta en la base de datos
        ClientesDAO.registrarCliente(nuevoCliente); //pasarle el objeto como parámetro
    }

    /**
     * método para eliminar un cliente
     */
    public static void eliminarCliente(){
        //pedir el id del cliente a eliminar
        System.out.print("\nPor favor, introduce el ID del cliente a eliminar: ");
        int idClienteEliminar = sc.nextInt();

        //llamar al método de la clase DAO
        ClientesDAO.eliminarCliente(idClienteEliminar); //pasarle el ID como atributo
    }

    /**
     * método para mostrar todos los clientes
     */
    public static void mostrarClientes(){
        //arraylist a pasar como parámetro
        ArrayList<Clientes> listaClientes = new ArrayList<>();

        //llamar al método de la clase DAO
        ClientesDAO.mostrarClientes(listaClientes); //pasar el arraylist como parámetro

        //mostrar los datos
        for (Clientes cliente : listaClientes) {
            System.out.println("\n" + cliente);
        }
    }

    /**
     * método para realizar una reclamación
     * aparentemente funciona
     */
    public static void quieroReclamar(){
        //archivo para las reclamaciones
        String archivoReclamaciones = "reclamaciones.txt";

        //activar el filewriter y el buffered writer
        FileWriter reclamar = null;
        BufferedWriter bufferReclamar = null;

        //pedir los datos para la reclamación
        System.out.println("\n¡ATENCIÓN! Si tienes más de un nombre (Ejemplo: Miguel Ángel) escribe tu segundo nombre en la parte de los apellidos.");
        System.out.print("\nPor favor, escribe tu nombre: ");
        String nombreCliente = sc.next(); //nombre del cliente

        System.out.print("\nPor favor, escribe tus apellidos (al menos el primero): ");
        sc.nextLine();
        String apellidosCliente = sc.nextLine(); //apellidos del cliente

        System.out.print("\n(OPCIONAL) Escribe tu teléfono: ");
        String telefonoCliente = sc.nextLine(); //teléfono del cliente

        System.out.print("\nPor favor, escribe tu email: ");
        String emailCliente = sc.nextLine(); //correo electrónico del cliente

        System.out.print("\nPor favor, escribe el motivo de la reclamación (producto o empleado): ");
        String motivoReclamacion = sc.nextLine(); //motivo de la reclamación

        System.out.print("\nPor favor, describe el problema: ");
        String descripcionProblema = sc.nextLine(); //descripción del problema

        //intentar escribir los datos en el archivo
        try {
            //crear un filewriter con opción para añadir al final del archivo
            reclamar = new FileWriter(archivoReclamaciones, true);

            //crear un buffered writer para mejorar el rendimiento
            bufferReclamar = new BufferedWriter(reclamar);

            //escribir los datos
            bufferReclamar.newLine();
            bufferReclamar.write("-------------------------------");
            bufferReclamar.newLine();
            bufferReclamar.write("Nombre: " + nombreCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Apellido(s): " + apellidosCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Teléfono: " + telefonoCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Correo electrónico: " + emailCliente);
            bufferReclamar.newLine();
            bufferReclamar.write("Motivo de la reclamación: " + motivoReclamacion);
            bufferReclamar.newLine();
            bufferReclamar.write("Descripción del problema: " + descripcionProblema);

            //vaciar el buffer de salida
            bufferReclamar.flush();

            //cerrar el buffered writer
            bufferReclamar.close();

            System.out.println("\nReclamación guardada.");
            Principal.pausar();
        } catch (IOException e) {
            System.err.println("Error realizando la reclamación: " + e.getMessage());
            Principal.pausar();
        }
    }

    /**
     * método para modificar los datos de un cliente
     */
    public static void modificarCliente(){
        //pedir el id del cliente a modificar
        System.out.print("\nPor favor, escribe el id del cliente a modificar: ");
        int idClienteModificar = sc.nextInt();

        //llamar al método
        ClientesDAO.obtenerCliente(idClienteModificar);

        if (ClientesDAO.obtenerCliente(idClienteModificar) == null) {
            System.err.println("No se encontró ningún cliente con ese ID.");
            Principal.pausar();
        } else {
            System.out.println("\nCliente encontrado: " + ClientesDAO.obtenerCliente(idClienteModificar));

            System.out.print("¿Quieres modificar a este cliente? (si,no): ");
            String quieroModificar = sc.next();

            if (quieroModificar.equalsIgnoreCase("si")) {
                System.out.println("\nPor favor, introduce los nuevos datos del cliente: ");

                System.out.print("Nuevo nombre: ");
                sc.nextLine();
                String nuevoNombre = sc.nextLine();
                

                System.out.print("Nuevo email: ");
                String nuevoEmail = sc.next();

                System.out.print("\n¿VIP? (1 = si, 0 = no): ");
                int comprobarVip = sc.nextInt(); // preguntar si es vip

                boolean clienteVip = false;

                // comprobar si es un cliente vip
                if (comprobarVip == 1) {
                    clienteVip = true;
                }

                //crear el objeto
                Clientes clienteModificado = new Clientes(0, nuevoNombre, nuevoEmail, clienteVip);

                //llamar al método de la clase DAO
                ClientesDAO.modificarCliente(clienteModificado, idClienteModificar); //pasar los datos como parámetros
            } else if(quieroModificar.equalsIgnoreCase("no")){
                System.out.println("Operación cancelada.");
            } else{
                System.err.println("Opción no válida, operación abortada.");
            }
        }
    }

}
