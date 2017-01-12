package tests;

import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;

import modelo.Amigo;
import modelo.Deporte;
import modelo.Evento;
import modelo.Notificacion;
import modelo.RepositorioAmigo;
//import modelo.RepositorioComentario;
import modelo.RepositorioDeporte;
import modelo.RepositorioEvento;
import modelo.RepositorioNotificacion;
//import modelo.RepositorioMensaje;
import modelo.RepositorioUsuario;
import modelo.Usuario;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con la base de datos y repositorios.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BDTest {

	private static RepositorioDeporte repoDeporte;
	private static RepositorioUsuario repoUsuario;
	//private static RepositorioComentario repoComentario;
	private static RepositorioEvento repoEvento;
	private static RepositorioAmigo repoAmigo;
	private static RepositorioNotificacion repoNotificacion;
	//private static RepositorioMensaje repoMensaje;

	@BeforeClass
	public static void setUp() {
		repoDeporte = new RepositorioDeporte();
		repoUsuario = new RepositorioUsuario();
		//repoComentario = new RepositorioComentario();
		repoEvento = new RepositorioEvento();
		//repoMensaje = new RepositorioMensaje();
		repoAmigo = new RepositorioAmigo();
		repoNotificacion = new RepositorioNotificacion();
	}
	
	/**
	 * Se prueba a buscar un deporte
	 */
	@Test
	public void testFindDeporte() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	/**
	 * Se prueba a listar deportes
	 */
	@Test
	public void testListarDeportes() {
		List<Deporte> deportes = repoDeporte.listarDeportes();
		assertTrue(deportes.size()>0);
	}
	
	/**
	 * Se prueba a listar los deportes suscritos de un usuario
	 */
	@Test
	public void testListarDeportesUsuario() {
		List<Deporte> deportes = repoDeporte.listarDeportesUsuario("prueba@social");
		assertTrue(deportes.size()>0);
	}
	
	/**
	 * Se prueba a suscribirse a un deporte
	 */
	@Test
	public void testASuscribirseDeporte() {
		assertTrue(repoDeporte.suscribirseDeporte("Futbol","prueba@social"));
	}
	
	/**
	 * Se prueba a darse de baja de un deporte
	 */
	@Test
	public void ztestDarseDeBajaDeporte() {
		assertTrue(repoDeporte.darseDeBajaDeporte("Futbol","prueba@social"));
	}

	/**
	 * Se prueba a buscar un usuario
	 */
	@Test
	public void testFindUsuario() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	/**
	 * Se prueba a insertar un usuario
	 */
	@Test
	public void atestInsertarUsuario() {
		Usuario usuario = new Usuario("prueba@social","prueba","prueba",
				"prueba", "1994-11-11", "prueba", "pruebaN");
		assertTrue(repoUsuario.insertarUsuario(usuario));
	}

	/**
	 * Se prueba a actualizar un usuario
	 */
	@Test
	public void testActualizarUsuario() {
		Usuario usuario = new Usuario("prueba@social","prueba","prueba",
				"prueba", "1994-12-06", "prueba", "pruebaNic");
		assertTrue(repoUsuario.actualizarUsuario(usuario));
	}

	/**
	 * Se prueba a eliminar
	 */
	@Test
	public void ztestEliminarUsuario() {
		assertTrue(repoUsuario.borrarUsuario("prueba@social"));
	}

	/**
	 * Se prueba a encontrar un comentario
	 */
	@Test
	public void testFindComentario() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	/**
	 * Se prueba a insertar un evento
	 */
	@Test
	public void btestInsertarEvento() {
		Evento evento = new Evento("prueba1","Evento","2017-01-01", "10:00:00", "Futbol", "prueba@social", "foto");
		assertTrue(repoEvento.insertarEvento(evento));
	}
	
	/**
	 * Se prueba a encontrar un evento
	 */
	@Test
	public void ctestFindEvento() {
		assertEquals(repoEvento.findEvento("prueba1").getDeporte(), "Futbol");
	}
	
	/**
	 * Se prueba a listar todos los eventos asociados a un deporte
	 */
	@Test
	public void testListarEventosDeporte() {
		assertFalse(repoEvento.listarEventosDeporte("Futbol","usuario@socialsport.com").isEmpty());
	}
	
	/**
	 * Se prueba a borrar un evento
	 */
	@Test
	public void ztestBorrarEvento() {
		assertTrue(repoEvento.borrarEventoByName("prueba1"));
	}
	
	/**
	 * Se prueba a insertar un amigo
	 */
	@Test
	public void ftestInsertarAmigo() {
		Amigo amigo = new Amigo("prueba","john@socialsport.com","1994-11-11");
		assertTrue(repoAmigo.insertarAmigo(amigo));
	}
	
	/**
	 * Se prueba a listar los usuarios seguidos por otro usuario
	 */
	@Test
	public void testListarSeguidos() {
		assertFalse(repoAmigo.listarSeguidos("prueba").isEmpty());
	}
	
	/**
	 * Se prueba a borrar un amigo
	 */
	@Test
	public void ztestBorrarAmigo() {
		assertTrue(repoAmigo.borrarAmigo("prueba", "john@socialsport.com"));
	}

	/**
	 * Se prueba a encontrar un mensaje
	 */
	@Test
	public void testFindMensaje() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}
	
	/**
	 * Se prueba a insertar una notificaci�n
	 */
	@Test
	public void testAInsertarNotificacion() {
		assertTrue(repoNotificacion.insertarNotificacion(new Notificacion("pruebaEnvia", "pruebaRecibe","nombreNotificacion","foto", "prueba descripcion", "12-06-1175", "18:03", "Evento",18)));
	}

	/**
	 * Se prueba a notificar a un usuario
	 */
	@Test
	public void testNotificar() {
		assertTrue(repoNotificacion.notificar("pruebaEnvia","pruebaRecibe","nombreNotificacion","foto", "evento", "nombreEnvia",18));
	}
	
	/**
	 * Se prueba a listar notificaciones
	 */
	@Test
	public void testListarNotificacion() {
		assertFalse(repoNotificacion.listarNotificaciones("pruebaRecibe").isEmpty());
	}
	
	/**
	 * Se prueba a borrar notificaciones
	 */
	@Test
	public void testZBorrarNotificacion() {
		assertTrue(repoNotificacion.borrarNotificacion("1"));
	}
}
