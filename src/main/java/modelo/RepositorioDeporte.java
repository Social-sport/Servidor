package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioDeporte {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioDeporte() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	/**
	 * Devuelve la informacion del deporte con nombre <nombre> almacenado en la BD
	 */
	public Deporte findDeporte(String nombre) {
		Deporte deporte = null;
		String sql = "SELECT * FROM Deporte WHERE Nombre='"+nombre+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			deporte = new Deporte(rs.getString("Nombre"),rs.getString("Descripcion"),
					rs.getString("Foto")) ;	
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Deporte");
		}
		return deporte;
	}

	/**
	 * Lista los deportes disponibles para un usuario
	 */
	public List<Deporte> listarDeportesDisponibles(String email) {
		List<Deporte> deportes = new LinkedList<Deporte>();
		String sql = "SELECT * FROM Deporte WHERE Deporte.Nombre NOT IN (SELECT DeporteSuscrito.deporte"
				+ " FROM DeporteSuscrito WHERE DeporteSuscrito.usuario = '" + email + "')";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				deportes.add(new Deporte(rs.getString("Nombre"),rs.getString("Descripcion"),
										rs.getString("Foto")));				
			}
			System.out.println("listados los Deportes Disponibles");
			stmt.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Deportes" + e);
		}
		
		deportes = addNumSuscritos(deportes);
		
		return deportes;
	}
	
	
	/**
	 * añade la cantidad de suscritos a los deportes listados
	 */
	public List<Deporte> addNumSuscritos(List<Deporte> deportes) {
		
		for (Deporte deporte : deportes) {
			
			String sql = "SELECT COUNT(DeporteSuscrito.deporte) AS num FROM DeporteSuscrito WHERE DeporteSuscrito.deporte = '" + deporte.getNombre() + "')";
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					deporte.setNumSuscritos(rs.getString("num"));
				}
				System.out.println("adheridos la cant de suscritos a los deportes listados");
				stmt.close();
			}
			
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en añadir cant suscritos a Deportes" + e);
			}
			
		}
		
		return deportes;
	}
	
	
	/**
	 * Lista los deportes almacenados en la BD
	 */
	public List<Deporte> listarDeportes() {
		List<Deporte> deportes = new LinkedList<Deporte>();
		String sql = "SELECT * FROM Deporte";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				deportes.add(new Deporte(rs.getString("Nombre"),rs.getString("Descripcion"),
										rs.getString("Foto")));				
			}
			stmt.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Deportes" + e);
		}
		return deportes;
	}

	/**
	 * Suscribe al usuario con email <email> al deporte con nombre <nombre>
	 */
	public boolean suscribirseDeporte(String nombre, String email) {
		String sql = "INSERT INTO DeporteSuscrito (deporte,usuario) VALUES "
				+ "('"+nombre+"','"+email+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al suscribirse a deporte "+nombre+" del usuario "+email);
			return false;
		}
	}
	
	/**
	 * Da de baja al usuario con email <email> al deporte con nombre <nombre>
	 */
	public boolean darseDeBajaDeporte(String nombre, String email) {
		String sql = "DELETE FROM DeporteSuscrito WHERE deporte = '"+nombre+"' AND usuario = '"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			int rs = stmt.executeUpdate(sql);
			stmt.close();
			if (rs == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (SQLException e) {
			System.out.println("Error al dar de baja el deporte "+nombre+" al usuario "+email);
			return false;
		}
	}
	
	/**
	 * Lista los deportes del usuario con email <email>
	 */
	public List<Deporte> listarDeportesUsuario(String email) {
		List<Deporte> deportes = new LinkedList<Deporte>();
		String sql = "SELECT Deporte.Nombre, Deporte.Descripcion, Deporte.Foto FROM Deporte,DeporteSuscrito WHERE Deporte.Nombre=DeporteSuscrito.deporte AND DeporteSuscrito.usuario='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				deportes.add(new Deporte(rs.getString("Nombre"),rs.getString("Descripcion"),
						rs.getString("Foto")));	
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Deportes del usuario "+email);
		}
		return deportes;
	}
}