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
	 * Metodo creador
	 */
	public RepositorioNotificacion() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}
	
	public boolean insertarNotificacion(Notificacion notificacion) {
		String sql = "INSERT INTO Notificacion (usuarioEnvia,usuarioRecibe,"
					+ "nombre,foto,descripcion,fecha,hora,tipo) VALUES "
					+ "('"+notificacion.getUsuarioEnvia()+"','"
					+ notificacion.getUsuarioRecibe()+"','"
					+ notificacion.getNombre()+"', '"+  notificacion.getFoto()
					+ "', '"+notificacion.getDescripcion()
					+ "','"+notificacion.getFecha()+"','"+notificacion.getHora()
					+ "','"+notificacion.getTipo()+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar Notificacion por: " + e);
			return false;
		}
	}
	
	public boolean borrarNotificacion(String id) {
		String sql = "DELETE FROM Notificacion WHERE id=\""+id+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al borrar Notificacion");
			return false;
		}
	}
	
	public List<Notificacion> listarNotificaciones(String usuario) {
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();
		String sql = "SELECT * FROM Notificacion WHERE usuarioRecibe = '"+usuario+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				notificaciones.add(new Notificacion(rs.getString("usuarioEnvia"),
						rs.getString("usuarioRecibe"),rs.getString("nombre"),
						rs.getString("foto"),
						rs.getString("descripcion"), rs.getString("fecha"),
						rs.getString("hora"),rs.getString("tipo")));		
			}
			stmt.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Notificaciones" + e);
		}
		return notificaciones;
	}
	
	public boolean notificar(String emailUsuarioEnvia,String emailUsuarioRecibe, String nombreNotificacion, String fotoNotificacion, String tipo, String nombreUsuarioEnvia) {
		
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
		
		Notificacion notificacion = new Notificacion(emailUsuarioEnvia, emailUsuarioRecibe, nombreNotificacion, fotoNotificacion, descripcion, fecha, hora, tipo);
		
		return insertarNotificacion(notificacion);
	}
}