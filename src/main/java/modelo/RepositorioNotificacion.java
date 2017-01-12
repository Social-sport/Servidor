package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioNotificacion {

	private Connection conexion = null;

	/**
	 * Inicia una conexion con la base de datos
	 */
	public RepositorioNotificacion() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	/**
	 * Inserta una Notificacion <notificacion> en la Base de datos y devuelve
	 * [true] si ha habido �xito, [false] en caso contrario.
	 */
	public boolean insertarNotificacion(Notificacion notificacion) {
		String sql = "INSERT INTO Notificacion (usuarioEnvia,usuarioRecibe,"
				+ "nombre,foto,descripcion,fecha,hora,tipo,idEvent) VALUES "
				+ "('"+notificacion.getUsuarioEnvia()+"','"
				+ notificacion.getUsuarioRecibe()+"','"
				+ notificacion.getNombre()+"', '"+  notificacion.getFoto()
				+ "', '"+notificacion.getDescripcion()
				+ "','"+notificacion.getFecha()+"','"+notificacion.getHora()
				+ "','"+notificacion.getTipo()
				+ "','"+notificacion.getIdEvent()+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error al insertar Notificacion por: " + e);
			return false;
		}
	}

	/**
	 * Elimina una Notificacion de la base de datos dada su <id> y devuelve
	 * [true] si ha habido �xito, [false] en caso contrario.
	 */
	public boolean borrarNotificacion(String id) {
		String sql = "DELETE FROM Notificacion WHERE id=\""+id+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error al borrar Notificacion");
			return false;
		}
	}

	/**
	 * Devuelve una lista de las notificaciones de un usuario dado su nombre.
	 */
	public List<Notificacion> listarNotificaciones(String usuario) {
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();
		String sql = "SELECT * FROM Notificacion WHERE usuarioRecibe = '"+usuario+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Notificacion n = new Notificacion(rs.getString("usuarioEnvia"),
						rs.getString("usuarioRecibe"),rs.getString("nombre"),
						rs.getString("foto"),
						rs.getString("descripcion"), rs.getString("fecha"),
						rs.getString("hora"),rs.getString("tipo"),rs.getInt("idEvent"));
								
				notificaciones.add(n);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Notificaciones" + e);
		}
		return notificaciones;
	}

	/**
	 * Notifica a un usuario seg�n el <tipo>, devuelve [true] en caso de exito, [false] en caso contrario.
	 */
	public boolean notificar(String emailUsuarioEnvia,String emailUsuarioRecibe, String nombreNotificacion, 
						String fotoNotificacion, String tipo, String nombreUsuarioEnvia, int idEvent) {
		Date fech = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String fecha = format.format(fech);
		format = new SimpleDateFormat("HH:mm");
		String hora = format.format(fech);
		String descripcion = "";
		
		if (tipo.equals("Evento")) {
			descripcion = nombreUsuarioEnvia + "te ha invitado a este evento";
		}
		if (tipo.equals("Seguidor")) {
			descripcion = "Ha comenzado a seguirte";
		}

		Notificacion notificacion = new Notificacion(emailUsuarioEnvia, emailUsuarioRecibe, nombreNotificacion, fotoNotificacion, descripcion, fecha, hora, tipo, idEvent);
		return insertarNotificacion(notificacion);
	}

	/**
	 * Devuelve el n�mero de notificaciones de un usuario dado su <email>
	 */
	public int contarNotificaciones(String email) {
		int cuenta = 0;
		String sql = "SELECT COUNT(*) AS count FROM Notificacion WHERE usuarioRecibe = '"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				cuenta = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en contar Notificaciones" + e);
		}
		return cuenta;
	}
}