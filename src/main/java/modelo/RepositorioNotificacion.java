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
		String sql = "INSERT INTO Notificacion (usuario,descripcion,fecha,hora,tipo) VALUES ('"+notificacion.getUsuario()+"','"+notificacion.getDescripcion()+"','"+notificacion.getFecha()+"','"+notificacion.getHora()+"','"+notificacion.getTipo()+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar amigo por: " + e);
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
			System.out.println("Error al borrar amigo");
			return false;
		}
	}
	
	public List<Notificacion> listarNotificaciones(String usuario) {
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();
		String sql = "SELECT * FROM Notificacion WHERE usuario = '"+usuario+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				notificaciones.add(new Notificacion(rs.getString("usuario"),
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
	
	public boolean notificar(String usuario, String tipo) {
		String descripcion = "";
		if (tipo.equals("Evento")) {
			descripcion = "Tiene una notificación nueva de un evento";
		}
		if (tipo.equals("Amigo")) {
			descripcion = "Tienes un nuevo amigo";
		}
		Date fech = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = format.format(fech);
		format = new SimpleDateFormat("HH:mm");
        String hora = format.format(fech);
		Notificacion notificacion = new Notificacion(usuario, descripcion, fecha, hora, tipo);
		return insertarNotificacion(notificacion);
	}
}