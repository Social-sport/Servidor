package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexion a la Base de Datos
 */
public class ConexionBD {

	private static Connection conexion = null;
	private static String db4free = "jdbc:mysql://db4free.net:3306/socialsport?user=socialsport&password=socialsport";
	//private static String openshift = "jdbc:mysql://127.11.148.2:3306/socialsport?user=adminNhxVfzE&password=wf-QyTaE11-l";

	/**
	 * Inicia conexion con una base de datos MySQL.
	 * 
	 * @return true si se ha iniciado la conexion correctamente, o false si no
	 *         se ha iniciado la conexion correctamente
	 */
	public static boolean iniciarConexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(db4free);
			if (conexion != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Conexion fallida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC de MySQL no encontrado");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Devuelve la conexion a la base de datos MySQL inicializada
	 * anteriormente
	 */
	public static Connection getConexion() {
		return conexion;
	}

	/**
	 * Cierra la conexion de la base de datos MySQL inicializada
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