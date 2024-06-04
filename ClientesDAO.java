//importar todo lo necesario
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ClientesDAO {
    //cosas para la conexión
    static Connection conexion;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

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
        System.out.print("\nPor favor, escribe tu nombre: ");
        //sc.nextLine();
        String nombreCliente = sc.nextLine(); //nombre del cliente

        System.out.print("\nPor favor, escribe tus apellidos (al menos el primero): ");
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
     * método para registrar un cliente
     */
    public static void registrarCliente(){
        //pedir los datos del cliente
        System.out.println("Por favor, introduce los datos del cliente: ");
        System.out.print("\nIdentificador: ");
        int idCliente = sc.nextInt(); //pedir el id del cliente
        System.out.print("\nNombre: ");
        sc.nextLine();
        String nombreCliente = sc.nextLine(); //pedir el nombre
        
        System.out.print("\n¿VIP? (1 = si, 0 = no): ");
        int comprobarVip = sc.nextInt(); //preguntar si es vip

        boolean clienteVip = false;

        //comprobar si es un cliente vip
        if (comprobarVip == 1) {
            clienteVip = true;
        }

        //crear el objeto de cliente y añadirlo a la lista
        Clientes nuevoCliente = new Clientes(idCliente, nombreCliente, clienteVip);
        Principal.listaClientes.add(nuevoCliente);
    }

        /**
     * método para guardar los clientes al cerrar el programa
     */
    public static void guardarClientes(){
        //intentar la conexión con la base de datos
        try {
            conexion = Conexion.getConexion();
            String consultaInsertarClientes = "insert into clientes values (?, ?, ?);";

            //intentar insertar los clientes
            try (PreparedStatement ps = conexion.prepareStatement(consultaInsertarClientes)) {
                for (Clientes cliente : Principal.listaClientes) {
                    ps.setInt(1, cliente.getIdentificacion());
                    ps.setString(2, cliente.getNombre());
                    ps.setBoolean(3, cliente.getVip());
                    ps.executeUpdate();
                }
            } catch (SQLException ex){
                System.err.println("Error guardando los clientes en la base de datos: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("\nError guardando los clientes en la base de datos: " + e.getMessage());
        }
    }

    /**
     * método para modificar a un cliente
     */
    public static void modificarCliente(){
        //pedir el id del cliente a modificar
        System.out.print("\nPor favor, escribe el id del cliente a modificar: ");
        int idClienteModificar = sc.nextInt();

        //buscar al cliente
        String consultaBuscarCliente = "select * from clientes where identificacion = '" + idClienteModificar + "';";
    
        //intentar la conexión
        try {
            conexion = Conexion.getConexion();

            //cosas del sql
            Statement sentencia1 = conexion.createStatement();
            ResultSet rs1 = sentencia1.executeQuery(consultaBuscarCliente);

            //mostrar al cliente
            int idEncontrado = 0;
            String nombreEncontrado = null;
            boolean vipEncontrado = false;
            if (rs1.next()) {
                idEncontrado = rs1.getInt("identificacion");
                nombreEncontrado = rs1.getString("nombre");
                vipEncontrado = rs1.getBoolean("vip");
                System.out.println("Cliente encontrado: " + nombreEncontrado);
            }

            if (nombreEncontrado == null) {
                System.err.println("No se ha encontrado un cliente con ese ID.");
            } else {
                System.out.println("\nPor favor, introduce los nuevos datos del cliente: ");
                System.out.print("Nuevo nombre: ");
                sc.nextLine();
                String nuevoNombre = sc.nextLine();
                System.out.print("¿VIP? (1 = si, 0 = no): ");
                int nuevoVip = sc.nextInt();

                boolean comprobarVip = false;

                // comprobar si es un cliente vip
                if (nuevoVip == 1) {
                    comprobarVip = true;
                }

                //consulta para insertar los valores
                String consultaModificarCliente = "update clientes set nombre = ?, vip = ? where identificacion = '" + idClienteModificar + "' ; ";

                try (PreparedStatement ps = conexion.prepareStatement(consultaModificarCliente)){
                    ps.setString(1, nuevoNombre);
                    ps.setBoolean(2, comprobarVip);
                    ps.executeUpdate();
                    System.out.println("Datos actualizados.");
                } catch (Exception e) {
                    System.err.println("\nError actualizando los datos del cliente: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error modificando los datos del cliente: " + e.getMessage());
        }
    }
}
