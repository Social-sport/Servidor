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

	/**
	 * Devuelve la informacion del evento con nombre <nombre>
	 */
	public Evento findEvento(String nombre) {
		Evento evento = null;
		try {
			String sql = "SELECT * FROM Evento WHERE nombre ='"+nombre+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			evento = new Evento(rs.getInt("id"),rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
					rs.getString("hora"),rs.getString("deporte"),rs.getString("creador"),rs.getString("foto"));
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Evento "+nombre + " por " + e);
		}
		return evento;
	}
	
	/**
	 * Devuelve la informacion del evento con nombre <nombre>
	 */
	public Evento findEventById(String id) {
		Evento evento = null;
		try {
			String sql = "SELECT * FROM Evento WHERE id ='"+id+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			evento = new Evento(rs.getInt("id"),rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
					rs.getString("hora"),rs.getString("deporte"),rs.getString("creador"),rs.getString("foto"));
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Evento "+id + " por " + e);
		}
		return evento;
	}
	
	/**
	 * Devuelve true si el usuario esta suscrito al evento
	 */
	public boolean findSuscripcion(int id, String email) {
		boolean evento = false;
		try {
			String sql = "SELECT * FROM EventoSuscrito WHERE idEvento = '"+id+"' AND usuario ='"+email+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			evento = true;
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar suscripcion Evento por " + e);
		}
		return evento;
	}

	/**
	 * Lista los eventos del deporte con nombre <deporte>
	 */
	public List<Evento> listarEventosDeporte(String deporte, String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento WHERE deporte ='"+deporte+"'"+ " AND creador !='"+email+"' "  ;
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador"), rs.getString("foto")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Eventos de deporte");
		}
		return eventos;
	}

	/**
	 * Lista los eventos del usuario con email <email>
	 */
	public List<Evento> listarEventosSuscritos(String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento,EventoSuscrito WHERE idEvento = id AND Usuario ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador"), rs.getString("foto")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Eventos de usuario");
		}
		return eventos;
	}
	
	/**
	 * Lista los eventos buscados por un usuario con email <email>
	 */
	public List<Evento> listarEventosCreados(String buscar) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento WHERE nombre = '"+buscar+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador"), rs.getString("foto")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar Eventos Buscados porar "+ buscar + e);
		}
		return eventos;
	}

	/**
	 * Lista los eventos creados por el usuario con email <email>
	 */
	public List<Evento> listarMisEventos(String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		String sql = "SELECT * FROM Evento WHERE creador ='"+email+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("id"), rs.getString("nombre"),rs.getString("descripcion"),rs.getString("fecha"),
						rs.getString("hora"), rs.getString("deporte"),
						rs.getString("creador"), rs.getString("foto"),"propietario"));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar mis Eventos");
		}
		return eventos;
	}

	/**
	 * Inserta el evento <evento> en la BD
	 */
	public boolean insertarEvento(Evento evento) {
		String sql = "INSERT INTO Evento (nombre,descripcion,fecha,hora,deporte,creador,foto) VALUES "
				+ "(\""+evento.getNombre()+"\",\""+evento.getDescripcion()+"\",\""+evento.getFecha()+"\",\""+evento.getHora()+"\",\""+evento.getDeporte()+"\",\""+evento.getCreador()+"\",\""+evento.getFoto()+"\")";
		try {
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar evento "+evento.getNombre());
			return false;
		}
	}

	/**
	 * Borra el evento con id <id> de la BD
	 */
	public boolean borrarEvento(String id) {
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

	/**
	 * Suscribe al usuario con email <email> al evento con id <id>
	 */
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

	/**
	 * Da de baja al usuario con email <email> del evento con id <id>
	 */
	public boolean darseDeBajaEvento(int id, String email) {
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