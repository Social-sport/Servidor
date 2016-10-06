import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexion a la Base de Datos
 */
public class ConexionBD {

	private static Connection conexion = null;

	/**
	 * Inicia conexion con una base de datos MySQL.
	 * 
	 * @return true si se ha iniciado la conexion correctamente, o false si no
	 *         se ha iniciado la conexion correctamente
	 */
	public static boolean iniciarConexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager
					.getConnection("jdbc:mysql://localhost/TuBaseDeDatos?user=TuUsuario&password=TuPass");
			if (conexion != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Conexion fallida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC de PostgreSQL no encontrado");
		}
		return false;
	}

	/**
	 * Devuelve la conexion a la base de datos PostgreSQL inicializada
	 * anteriormente
	 */
	public static Connection getConexion() {
		return conexion;
	}

	/**
	 * Cierra la conexion de la base de datos PostgreSQL inicializada
	 * anteriormente
	 */
	public static void cerrarConexion() {
		if (conexion != null) {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}