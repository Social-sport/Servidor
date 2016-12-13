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
	
	@Test
	public void testFindDeporte() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	@Test
	public void testListarDeportes() {
		List<Deporte> deportes = repoDeporte.listarDeportes();
		assertTrue(deportes.size()>0);
	}
	
	@Test
	public void testListarDeportesUsuario() {
		List<Deporte> deportes = repoDeporte.listarDeportesUsuario("prueba");
		assertTrue(deportes.size()>0);
	}
	
	@Test
	public void testASuscribirseDeporte() {
		assertTrue(repoDeporte.suscribirseDeporte("Futbol","prueba"));
	}
	
	@Test
	public void ztestDarseDeBajaDeporte() {
		assertTrue(repoDeporte.darseDeBajaDeporte("Futbol","prueba"));
	}

	@Test
	public void testFindUsuario() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	@Test
	public void atestInsertarUsuario() {
		Usuario usuario = new Usuario("prueba","prueba","prueba",
				"prueba", "1994-11-11", "prueba", "prueba");
		assertTrue(repoUsuario.insertarUsuario(usuario));
	}

	@Test
	public void testActualizarUsuario() {
		Usuario usuario = new Usuario("prueba","prueba","prueba",
				"prueba", "1994-12-06", "prueba", "prueba");
		assertTrue(repoUsuario.actualizarUsuario(usuario));
	}

	@Test
	public void ztestEliminarUsuario() {
		assertTrue(repoUsuario.borrarUsuario("prueba"));
	}

	@Test
	public void testFindComentario() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}

	@Test
	public void btestInsertarEvento() {
		Evento evento = new Evento("prueba","try","00", "00", "Futbol", "prueba", "foto");
		assertTrue(repoEvento.insertarEvento(evento));
	}
	
	@Test
	public void ctestFindEvento() {
		assertEquals(repoEvento.findEvento("prueba").getDeporte(), "Futbol");
	}
	
	@Test
	public void testListarEventosDeporte() {
		assertFalse(repoEvento.listarEventosDeporte("Futbol").isEmpty());
	}
	
	@Test
	public void ztestBorrarEvento() {
		assertTrue(repoEvento.borrarEvento("prueba"));
	}
	
	@Test
	public void ftestInsertarAmigo() {
		Amigo amigo = new Amigo("prueba","john@socialsport.com","1994-11-11");
		assertTrue(repoAmigo.insertarAmigo(amigo));
	}
	
	@Test
	public void testListarSeguidos() {
		assertFalse(repoAmigo.listarSeguidos("prueba").isEmpty());
	}
	
	@Test
	public void ztestBorrarAmigo() {
		assertTrue(repoAmigo.borrarAmigo("prueba", "john@socialsport.com"));
	}

	@Test
	public void testFindMensaje() {
		assertEquals(repoDeporte.findDeporte("Futbol").getNombre(), "Futbol");
	}
	
	@Test
	public void testAInsertarNotificacion() {
		assertTrue(repoNotificacion.insertarNotificacion(new Notificacion("pruebaEnvia", "pruebaRecibe","nombreNotificacion","foto", "prueba descripcion", "12-06-1175", "18:03", "Evento")));
	}

	@Test
	public void testNotificar() {
		assertTrue(repoNotificacion.notificar("pruebaEnvia","pruebaRecibe","nombreNotificacion","foto", "evento", "nombreEnvia"));
	}
	
	@Test
	public void testListarNotificacion() {
		assertFalse(repoNotificacion.listarNotificaciones("pruebaRecibe").isEmpty());
	}
	
	@Test
	public void testZBorrarNotificacion() {
		assertTrue(repoNotificacion.borrarNotificacion("1"));
	}
}
