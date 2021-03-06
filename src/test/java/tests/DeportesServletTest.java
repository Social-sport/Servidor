package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.Gson;

import controlador.DeportesServlet;
import modelo.Deporte;
import modelo.RepositorioDeporte;
import modelo.RepositorioUsuario;
import modelo.Usuario;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con Deportes
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeportesServletTest {
	
	private static HttpServletRequest request;	
	private static DeportesServlet servlet;
	private static HttpSession session;
	private static RepositorioDeporte repo;
	private static RepositorioUsuario repoUser;
	private static Usuario user;
	private static Gson gson;
	
	private HttpServletResponse response;	
	private StringWriter response_writer;
	private Map<String, String> parameters;
	
	@BeforeClass
	public static void before() {
		gson = new Gson();				
		repo = new RepositorioDeporte();
		servlet = new DeportesServlet();
		repoUser = new RepositorioUsuario();
		user = new Usuario("user@socialsport.com","Social","Sport","2016-09-19","/Servidor/img/profile.jpg","test12");
		repoUser.insertarUsuario(user);
		session = mock(HttpSession.class);
		request = mock(HttpServletRequest.class);		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("email")).thenReturn(user.getEmail());
	}

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();		
		response = mock(HttpServletResponse.class);		
		response_writer = new StringWriter();		
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
	}
	
	@AfterClass
	public static void after() {
		repoUser.borrarUsuario("user@socialsport.com");
	}
	
	/**
	 * Se prueba a suscribirse a un deporte
	 */
	
	@Test
	public void testaSuscribirseDeporteOK() throws Exception {		
		parameters.put("deporte", "Futbol");
		servlet.doPost(request, response);
		assertEquals("El usuario se ha suscrito correctamente al deporte", response_writer.toString());
	}
	@Test
	public void testSuscribirseDeporteErroneo() throws Exception {
		parameters.put("deporte", "asdafgh");
		servlet.doPost(request, response);
		assertEquals("El usuario no se ha podido suscribir al deporte", response_writer.toString());		
	}
		
	/**
	 * Se prueba a listar todos los deportes
	 */
	@Test
	public void testListarTodosDeportes() throws Exception {
		parameters.put("tipoDeport", "AllSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportes();
		assertEquals(response_writer.toString(),gson.toJson(deportes));
	}

	/**
	 * Se prueba a listar los deportes de un usuario
	 */
	@Test 
	public void testListarDeportesUsuarioOK() throws Exception {
		parameters.put("tipoDeport", "ListUserSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportesUsuario(user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(deportes));
	}
	
	/**
	 * Se prueba a listar los deportes disponibles
	 */
	@Test 
	public void testListarDeportesDisponiblesOK() throws Exception {
		parameters.put("tipoDeport", "AvailableSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportesDisponibles(user.getEmail());
		assertEquals(response_writer.toString(), gson.toJson(deportes));
	}
	
	/**
	 * Se prueba a darse de baja de un deporte err�neo
	 */
	@Test
	public void testDarseDeBajaDeporteErroneo() throws Exception {
		parameters.put("deporte", "Padel");
		servlet.doDelete(request, response);
		assertEquals("El usuario no se ha podido dar de baja del deporte", response_writer.toString());
	}
	
	@Test
	public void testbDarseDeBajaDeporteOK() throws Exception {
		parameters.put("deporte", "Futbol");
		servlet.doDelete(request, response);
		assertEquals("El usuario se ha dado de baja correctamente del deporte",response_writer.toString());
	}
}