package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import baseDatos.ConexionBD;

public class RepositorioUsuario {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioUsuario() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	public Usuario findUsuario(String email) {
		Usuario usuario = null;
		String sql = "SELECT * FROM Usuario WHERE email='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			usuario = new Usuario(rs.getString("email"),rs.getString("nombre"),
					rs.getString("apellidos"),rs.getString("contrasena"),rs.getString("fecha_nacimiento"),rs.getString("foto"),rs.getString("nick"));
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("Error en buscar Usuario "+email);
		}
		return usuario;
	}

	public boolean insertarUsuario(Usuario usuario) {
		String sql = "INSERT INTO Usuario"
				+ "(email, nombre, apellidos, contrasena, fecha_nacimiento, foto, nick)"
				+ " VALUES (\""+usuario.getEmail()+"\",\""+usuario.getNombre()+"\",\""+usuario.getApellidos()+"\",\""
				+usuario.getContrasena()+"\",\""+usuario.getFecha_nacimiento()+"\",\""+usuario.getFoto()+"\",\""+usuario.getNick()+"\")";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar usuario");
			return false;
		}
	}

	public boolean borrarUsuario(String email) {
		String sql = "DELETE FROM Usuario WHERE email=\""+email+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al borrar usuario");
			return false;
		}
	}

	public boolean actualizarUsuario (Usuario usuario) {
		String sql = "UPDATE Usuario SET nombre=\""+usuario.getNombre()+"\","
				+ "apellidos=\""+usuario.getApellidos()+"\", contrasena=\""+usuario.getContrasena()+"\", "
				+ " foto=\""+usuario.getFoto()+"\", fecha_nacimiento=\""+usuario.getFecha_nacimiento()+"\", nick=\""+usuario.getNick()+"\" "
				+ "WHERE email=\""+usuario.getEmail()+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al actualizar usuario");
			return false;
		}
	}
}