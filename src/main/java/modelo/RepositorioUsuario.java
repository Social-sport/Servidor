package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioUsuario {

	private Connection conexion = null;

	/**
	 * Inicia una conexion con la base de datos
	 */
	public RepositorioUsuario() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}
	
	/**
	 * Lista todos los usuarios almacenados
	 */
	public List<Usuario> listAll() {
		List<Usuario> Usuarios = new LinkedList<Usuario>();
		String sql = "SELECT * FROM Usuario";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"), rs.getString("contrasena"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
				Usuarios.add(u);			
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Usuarios" + e);
		}
		return Usuarios;
	}
	
	/**
	 * Lista los usuarios disponibles para seguir
	 */
	public List<Usuario> ListAvailableUsers(String email) {
		List<Usuario> Usuarios = new LinkedList<Usuario>();
		String sql = "SELECT * FROM Usuario WHERE Usuario.email NOT IN (SELECT Amigos.amigo"
				+ " FROM Amigos WHERE Amigos.usuario ='"+email+"') AND Usuario.email != '"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
				Usuarios.add(u);			
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Usuarios" + e);
		}
		return Usuarios;
	}
	
	/**
	 * A�ade la cantidad de Seguidores a los usuarios listados
	 */
	public Usuario addNumSeguidores(Usuario Usuario) {
		String sql = "SELECT COUNT(Amigos.usuario) AS num FROM Amigos WHERE Amigos.amigo = '" + Usuario.getEmail() + "'";
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Usuario.setNumSeguidores(rs.getString("num"));
				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en añadir cant Seguidores a Usuarios" + e);
			}
		return Usuario;
	}
	
	/**
	 * Lista todos los usuarios de la BD
	 */
	public List<Usuario> ListEveryUsers(String email) {
		List<Usuario> Usuarios = new LinkedList<Usuario>();		
		String sql = "SELECT * FROM Usuario WHERE email !='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
				Usuarios.add(u);		
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Usuarios " + e);
		}			
		return Usuarios;
	}
	
	/**
	 * Lista los usuarios almacenados en la BD
	 */
	public List<Usuario> listarUsuariosBusqueda(String name) {
		
		List<Usuario> Usuarios = new LinkedList<Usuario>();		
		String sql = "SELECT * FROM Usuario WHERE nombre='"+name+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Usuario u = new Usuario(rs.getString("email"),rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("fecha_nacimiento"),rs.getString("foto"),
						rs.getString("nick"));				
						u = addNumSeguidores(u);
						
				Usuarios.add(u);		
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Usuarios " + e);
		}
		Usuario u = findUsuario(name);
		if (u!=null) {
			Usuarios.add(u);
		}	
		return Usuarios;
	}

	/**
	 * Devuelve la informaci�n del usuario con email <email>
	 */
	public Usuario findUsuario(String email) {
		Usuario usuario = null;
		String sql = "SELECT * FROM Usuario WHERE email='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			usuario = new Usuario(rs.getString("email"),rs.getString("nombre"),
					rs.getString("apellidos"),rs.getString("contrasena"),rs.getString("fecha_nacimiento"),rs.getString("foto"),rs.getString("nick"));
			usuario = addNumSeguidores(usuario);
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("RepoError en buscar Usuario "+email);
		}
		return usuario;
	}

	/**
	 * Inserta el usuario <usuario> en la BD
	 */
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
		} catch (SQLException e) {
			System.out.println("Error al insertar usuario: " + e);
			return false;
		}
	}

	/**
	 * Borra el usuario con email <email> de la BD
	 */
	public boolean borrarUsuario(String email) {
		String sql = "DELETE FROM Usuario WHERE email=\""+email+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("RepoError al borrar usuario: " + email );
			System.out.println("RepoError: " + e );
			return false;
		}
	}

	/**
	 * Actualiza la informaci�n del usuario <usuario>
	 */
	public boolean actualizarUsuario (Usuario usuario) {
		String sql = "UPDATE Usuario SET nombre='"+usuario.getNombre()+"', "
				+ "apellidos='"+usuario.getApellidos()+"', contrasena='"+usuario.getContrasena()+"', "
				+ "foto='"+usuario.getFoto()+"', fecha_nacimiento='"+usuario.getFecha_nacimiento()+"', "
				+ "nick='"+usuario.getNick()+"' WHERE email='"+usuario.getEmail()+"'";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("RepoError al actualizar usuario" + usuario.getEmail());
			System.out.println("RepoError " + e);
			return false;
		}
	}
}