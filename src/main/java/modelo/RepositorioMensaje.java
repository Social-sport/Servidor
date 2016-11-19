package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioMensaje {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioMensaje() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	public List<Mensaje> listarMensajesUsuario(String email) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		String sql = "SELECT * FROM Mensajes WHERE autor ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mensajes.add(new Mensaje(rs.getInt("id"), rs.getString("fecha"),
						rs.getString("hora"), rs.getString("texto"),
						rs.getString("autor"), rs.getString("destinatario")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Mensajes enviados");
		}
		return mensajes;
	}
	
	public boolean insertarMensaje(Mensaje mensaje) {
		String sql = "INSERT INTO Mensaje (fecha,hora,texto,autor,destinatario) VALUES "
				+ "('"+mensaje.getFecha()+"','"+mensaje.getHora()+"','"+mensaje.getTexto()+"','"+mensaje.getAutor()+"','"+mensaje.getDestinatario()+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar mensaje");
			return false;
		}
	}
	
	public List<Mensaje> listarMensajesRecibidos(String email) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		String sql = "SELECT * FROM Mensajes WHERE destinatario ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mensajes.add(new Mensaje(rs.getInt("id"), rs.getString("fecha"),
						rs.getString("hora"), rs.getString("texto"),
						rs.getString("autor"), rs.getString("destinatario")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Mensajes recibidos");
		}
		return mensajes;
	}
}