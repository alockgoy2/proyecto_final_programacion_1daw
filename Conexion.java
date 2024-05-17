//importar todo lo de sql
import java.sql.*;

public class Conexion {
    //datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/proyectoFinalProgramacion";
    private static final String USUARIO = "root";
    private static final String PASSWD = "franceselquemehackee";

    // Objeto de conexión a la base de datos
    private static Connection conexion;

    // Método para obtener la conexión a la base de datos
    // Si ya existe una conexión, no crea una nueva, sino que la devuelve
    // Así evitamos tener muchas conexiones abiertas con la bbdd
    public static Connection getConexion() {
        if (conexion == null) {
            try {
                // Cargar el driver de MySQL
                //Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWD);
                System.out.println("Conexión OK");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos");
                e.printStackTrace();
            }
        }
        return conexion;
    }

    // Método para cerrar la conexión a la base de datos
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada con la bbdd");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la base de datos");
                e.printStackTrace();
            }
        }
    }
}
