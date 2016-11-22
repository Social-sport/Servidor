package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioEvento {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioEvento() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	public Evento findEvento(String nombre) {
		Evento evento = null;
		try {
			String sql = "SELECT * FROM Evento WHERE nombre='"+nombre+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			evento = new Evento(rs.getInt("id"),rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
					rs.getString("hora"),rs.getString("deporte"),rs.getString("creador"));
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Evento");
		}
		return evento;
	}

	public List<Evento> listarEventosDeporte(String deporte) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento WHERE deporte ='"+deporte+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Eventos de deporte");
		}
		return eventos;
	}

	public List<Evento> listarEventosUsuario(String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento,EventoSuscrito WHERE idEvento = id AND Usuario ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Eventos de usuario");
		}
		return eventos;
	}

	public List<Evento> listarMisEventos(String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento WHERE creador ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar mis Eventos");
		}
		return eventos;
	}

	public boolean insertarEvento(Evento evento) {
		String sql = "INSERT INTO Evento (nombre,descripcion,fecha,hora,deporte,creador) VALUES "
				+ "('"+evento.getNombre()+"','"+evento.getDescripcion()+"','"+evento.getFecha()+"','"+evento.getHora()+"','"+evento.getDeporte()+"','"+evento.getCreador()+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar evento");
			return false;
		}
	}

	public boolean borrarEvento(int id) {
		String sql = "DELETE FROM Evento WHERE id='"+id+"'";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al borrar evento");
			return false;
		}
	}

	public boolean suscribirseEvento(int id, String email) {
		String sql = "INSERT INTO EventoSuscrito (idEvento,usuario)VALUES "
				+ "('"+id+"','"+email+"')";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al suscribirse a evento");
			return false;
		}
	}

	public boolean darseDeBajaEvento(String email) {
		String sql = "DELETE FROM EventoSuscrito WHERE usuario='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al borrarse de evento");
			return false;
		}
	}
}