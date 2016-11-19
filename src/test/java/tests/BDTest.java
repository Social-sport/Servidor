package tests;

import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;

import modelo.Amigo;
import modelo.Deporte;
import modelo.Evento;
import modelo.RepositorioAmigo;
//import modelo.RepositorioComentario;
import modelo.RepositorioDeporte;
import modelo.RepositorioEvento;
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
	//private static RepositorioMensaje repoMensaje;

	@BeforeClass
	public static void setUp() {
		repoDeporte = new RepositorioDeporte();
		repoUsuario = new RepositorioUsuario();
		//repoComentario = new RepositorioComentario();
		repoEvento = new RepositorioEvento();
		//repoMensaje = new RepositorioMensaje();
		repoAmigo = new RepositorioAmigo();
	}

	@Test
	public void testFindDeporte() {
		assertEquals(repoDeporte.findDeporte("prueba").getNombre(), "prueba");
	}

	@Test
	public void testListarDeportes() {
		List<Deporte> deportes = repoDeporte.listarDeportes();
		assertTrue(deportes.size()>0);
	}
	
	@Test
	public void testListarDeportesUsuario() {
		List<Deporte> deportes = repoDeporte.listarDeportesUsuario("test");
		assertTrue(deportes.size()>0);
	}
	
	@Test
	public void testASuscribirseDeporte() {
		assertTrue(repoDeporte.suscribirseDeporte("prueba","try"));
	}
	
	@Test
	public void testZDarseDeBajaDeporte() {
		assertTrue(repoDeporte.darseDeBajaDeporte("prueba","try"));
	}

	@Test
	public void testFindUsuario() {
		assertEquals(repoDeporte.findDeporte("prueba").getNombre(), "prueba");
	}

	@Test
	public void testAInsertarUsuario() {
		Usuario usuario = new Usuario("prueba","prueba3","prueba",
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
	public void testZEliminarUsuario() {
		assertTrue(repoUsuario.borrarUsuario("prueba"));
	}

	@Test
	public void testFindComentario() {
		assertEquals(repoDeporte.findDeporte("prueba").getNombre(), "prueba");
	}

	@Test
	public void testFindEvento() {
		assertEquals(repoEvento.findEvento("prueba").getDeporte(), "prueba");
	}
	
	@Test
	public void testAInsertarEvento() {
		Evento evento = new Evento("prueba","try","00", "00", "prueba", "test");
		assertTrue(repoEvento.insertarEvento(evento));
	}
	
	@Test
	public void testListarEventosDeporte() {
		assertFalse(repoEvento.listarEventosDeporte("prueba").isEmpty());
	}
	
	@Test
	public void testZBorrarEvento() {
		int id = repoEvento.findEvento("prueba").getId();
		assertTrue(repoEvento.borrarEvento(id));
	}
	
	@Test
	public void testAInsertarAmigo() {
		Amigo amigo = new Amigo("test","prueba3","1994-11-11");
		assertTrue(repoAmigo.insertarAmigo(amigo));
	}
	
	@Test
	public void testListarAmigos() {
		assertFalse(repoAmigo.listarAmigos("test").isEmpty());
	}
	
	@Test
	public void testZBorrarAmigo() {
		assertTrue(repoAmigo.borrarAmigo("test", "prueba3"));
	}

	@Test
	public void testFindMensaje() {
		assertEquals(repoDeporte.findDeporte("prueba").getNombre(), "prueba");
	}
}
