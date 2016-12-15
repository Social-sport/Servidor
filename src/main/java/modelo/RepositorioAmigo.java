package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioAmigo {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioAmigo() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	/**
	 * Inserta el amigo <amigo> a la BD
	 */
	public boolean insertarAmigo(Amigo amigo) {
		String sql = "INSERT INTO Amigos"
				+ "(usuario, amigo, fecha)"
				+ " VALUES (\""+amigo.getUsuario()+"\",\""+amigo.getAmigo()+"\",\""+amigo.getFecha()+"\")";
		
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

	/**
	 * Borra un amigo <amigo> del usuario <usuario> de la BD
	 */
	public boolean borrarAmigo(String usuario, String amigo) {
		String sql = "DELETE FROM Amigos WHERE usuario=\""+usuario+"\" AND amigo=\""+amigo+"\"";
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
		
	public List<Usuario> listarSeguidos (String email) {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		String sql = "SELECT * FROM Usuario WHERE Usuario.email IN (SELECT Amigos.amigo"
				+ " FROM Amigos WHERE Amigos.usuario ='"+email+"')";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
						
				usuarios.add(u);
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar seguidos");
		}
		return usuarios;
	}
	
	public List<Usuario> listarSeguidores (String email) {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		String sql = "SELECT * FROM Usuario WHERE Usuario.email IN (SELECT Amigos.usuario"
				+ " FROM Amigos WHERE Amigos.amigo ='"+email+"')";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
						
				usuarios.add(u);
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar mis seguidores");
		}
		return usuarios;
	}
	
	/**
	 * añade la cantidad de Seguidores a los usuarios listados
	 */
	public Usuario addNumSeguidores(Usuario Usuario) {
		
		String sql = "SELECT COUNT(Amigos.usuario) AS num FROM Amigos WHERE Amigos.amigo = '" + Usuario.getEmail() + "'";
			
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Usuario.setNumSeguidores(rs.getString("num"));
				}
				System.out.println("adheridos la cant de Seguidores a los Usuarios listados");
				stmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en añadir cant Seguidores a Usuarios" + e);
			}	
		
		
		return Usuario;
	}
	
}