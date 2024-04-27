package proyectofinal.proyectofinal;
import java.util.Scanner; //importar Scanner
public class Principal {
    public static void main(String[] args) {
        //llamar al menú
        mostrarMenu();
    }

    /**
     * método para mostrar el menú
     */
    public static void mostrarMenu(){
        //declaración de variables y activación de scanner
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        //bucle principal
        do {
            System.out.println("\n-------------BIENVENIDO A CYBERNOVA-------------");
            System.out.println("OPCIONES:");
            System.out.println("-------------");
            System.out.println("1. Añadir productos.");
            System.out.println("2. Eliminar productos.");
            System.out.println("3. Mostrar todos los productos.");
            System.out.println("-------------");
            System.out.println("4. Registrar un cliente.");
            System.out.println("5. Eliminar un cliente.");
            System.out.println("6. Mostrar todos los clientes.");
            System.out.println("-------------");
            System.out.println("7. Añadir a un empleado.");
            System.out.println("8. Eliminar a un empleado.");
            System.out.println("9. Mostrar todos los empleados.");
            System.out.println("-------------");
            System.out.println("10. Añadir al carrito.");
            System.out.println("11. Quiero hacer una reclamación.");
            System.out.println("12. Salir.");

            System.out.print("Por favor, elige una opción: ");
            opcion = sc.nextInt();

            //switch de la opción
            switch (opcion) {
                case 1:
                    anadirProducto();
                    break;

                case 2:
                    eliminarProducto();
                    break;

                case 3:
                    mostrarProductos();
                    break;

                case 4:
                    registrarCliente();
                    break;

                case 5:
                    eliminarCliente();
                    break;

                case 6:
                    mostrarClientes();
                    break;

                case 7:
                    anadirEmpleado();
                    break;

                case 8:
                    eliminarEmpleado();
                    break;

                case 9:
                    mostrarEmpleados();
                    break;

                case 10:
                    quieroComprar();
                    break;

                case 11:
                    quieroReclamar();
                    break;

                case 12:
                    System.out.println("Saliendo del programa...");
                    break;
            
                default:
                System.out.println("ERROR. OPCIÓN NO VÁLIDA.");
                pausar();
                    break;
            }
        } while (opcion != 12); //hacer todo lo anterior mientras la opción elegida no sea 12

        
    }

    /**
     * método para pausar la muestra de system out
     * al tener el menú tantas opciones y mostrarse instantáneamente no da tiempo a ver que se ha ...
     * ... introducido una opción incorrecta
     */
    public static void pausar(){
        try {
            Thread.sleep(2000); //pausa de 2 segundos
        } catch (InterruptedException e) {
            System.err.println("Error al pausar la ejecución.");
        } //esto es para una pausa, ya no da tiempo a leer que se ha introducido una opción no válida
    }

    /**
     * método para añadir un producto
     */
    public static void anadirProducto(){

    }

    /**
     * método para eliminar un producto
     */
    public static void eliminarProducto(){

    }

    /**
     * método para mostrar todos los productos
     */
    public static void mostrarProductos(){

    }

    /**
     * método para registrar un cliente
     */
    public static void registrarCliente(){

    }

    /**
     * método para eliminar a un cliente
     */
    public static void eliminarCliente(){

    }

    /**
     * método para mostrar a todos los clientes
     */
    public static void mostrarClientes(){

    }

    /**
     * método para añadir a un empleado
     */
    public static void anadirEmpleado(){

    }

    /**
     * método para eliminar a un empleado
     */
    public static void eliminarEmpleado(){

    }

    /**
     * método para mostrar a todos los empleados
     */
    public static void mostrarEmpleados(){

    }

    /**
     * método para comprar
     */
    public static void quieroComprar(){

    }

    /**
     * método para realizar una reclamación
     */
    public static void quieroReclamar(){

    }
}
