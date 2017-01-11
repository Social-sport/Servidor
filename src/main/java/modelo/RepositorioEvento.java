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
			
			// Devuelve los eventos cuyo nombre sea "nombre"
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
			
			// Devuelve el evento de id <id>
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
			
			// Selecciona la suscripcion del usuario <email> al evento <id>, si está presente en la tabla.
			String sql = "SELECT * FROM EventoSuscrito WHERE idEvento = "+id+" AND usuario ='"+email+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// Solamente habrá un resultado en rs en caso de que el usuario <email> esté suscrito al evento <id>. 
			// Entonces, si hay una fila en rs, lo siguiente (rs.first) devuelve true. 
			rs.first();
			System.out.println("encuentra en la sucripcion "+ rs.getString(1));
			evento = true;
			stmt.close();
		}
		catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("No hay suscripcion a este  Evento");
		}
		return evento;
	}

	/**
	 * Lista los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <email>
	 */
	public List<Evento> listarEventosDeporte(String deporte, String email) {
		
		// lista nueva de eventos
		List<Evento> eventos = new LinkedList<Evento>();
		
		// Statement sql que selecciona los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <email>
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
	 * Lista los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <email>
	 */
	public List<Evento> listarEventosDeporteGeneral(String deporte, String email) {
		
		// lista nueva de eventos
		List<Evento> eventos = new LinkedList<Evento>();
		
		// Statement sql que selecciona los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <email>
		String sql = "SELECT * FROM Evento WHERE deporte ='"+deporte+"' "  ;
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
	 * Lista los eventos a los que está suscrito el usuario con email <email>
	 */
	public List<Evento> listarEventosSuscritos(String email) {
		List<Evento> eventos = new LinkedList<Evento>();
		
		// Selecciona los eventos a los que está suscrito el usuario con email <email>
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
	 * Lista los eventos cuyo nombre es <buscar>
	 */
	public List<Evento> listarEventosCreados(String buscar) {
		
		// eventos =  Lista de eventos vacía
		List<Evento> eventos = new LinkedList<Evento>();
		
		// Statement sql para seleccionar todos los eventos cuyo nombre es <buscar>
		String sql = "SELECT * FROM Evento WHERE nombre = '"+buscar+"'";
		try {
			
			// Crea la conexión con la BD.
			Statement stmt = conexion.createStatement();
			
			// ResultSet = A table of data representing a database result set, which is usually generated by 
			// executing a statement that queries the database.
			
			// rs = resultado de la query anterior (todas las filas que han hecho match)
			ResultSet rs = stmt.executeQuery(sql);
			
			// mientras haya filas en rs, quedate con la siguiente y añadelo en la lista de los eventos declarada arriba
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
		
		// Statement sql que inserta un evento en la tabla Evento de la BD, con los campos correspondientes.
		String sql = "INSERT INTO Evento (nombre,descripcion,fecha,hora,deporte,creador,foto) VALUES "
				+ "(\""+evento.getNombre()+"\",\""+evento.getDescripcion()+"\",\""+evento.getFecha()+"\",\""+evento.getHora()+"\",\""+evento.getDeporte()+"\",\""+evento.getCreador()+"\",\""+evento.getFoto()+"\")";
		try {
			
			// Crea conexión, ejecuta la query y cierra la conexión
			Statement stmt = conexion.createStatement();
			stmt.execute(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			
			// No se ha podido insertar el evento 
			System.out.println("Error al insertar evento "+evento.getNombre());
			return false;
		}
	}

	/**
	 * Borra el evento con id <id> de la BD
	 */
	public boolean borrarEvento(String id) {
		
		// Statement sql para borrar el evento de id <id> de la tabla Evento
		String sql = "DELETE FROM Evento WHERE id='"+id+"'";
		try {
			
			// stmt = conexión con la BD
			Statement stmt = conexion.createStatement();
			
			// Ejecutamos la consulta anterior
			stmt.executeUpdate(sql);
			
			// cerramos la conexión
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
		
		// Sentencia sql que inserta al usuario con email <email> al evento de id <id>
		String sql = "INSERT INTO EventoSuscrito (idEvento,usuario)VALUES "
				+ "('"+id+"','"+email+"')";
		try {
			
			// Crea una conexión SQL
			Statement stmt = conexion.createStatement();
			
			// Ejecuta la consulta anterior
			stmt.executeUpdate(sql);
			
			// Cierra la conexión
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
		
		// Busca en la tabla EventoSuscrito al usuario con email <email> suscrito al evento <id> y lo borra.
		String sql = "DELETE FROM EventoSuscrito WHERE usuario='"+email+"' AND idEvento = "+id ;
		try {
			
			// Crea una conexion con la bbdd
			Statement stmt = conexion.createStatement();
			
			// Ejecuta la consulta anterior
			stmt.executeUpdate(sql);
			
			// Cierra la conexión
			stmt.close();
			return true;
			
		}
		catch (SQLException e) {
			System.out.println("Error al borrarse de evento");
			return false;
		}
	}
}