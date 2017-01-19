package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controlador.UsuariosServlet;
import modelo.RepositorioUsuario;


/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con Usuarios
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuariosServletTest {

	
	private static HttpServletRequest request;	
	private static UsuariosServlet servlet;
	private static HttpSession session;
	private static RepositorioUsuario repoUser;
	
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@BeforeClass
	public static void before() {
		servlet = new UsuariosServlet();
		session = mock(HttpSession.class);
		request = mock(HttpServletRequest.class);
		repoUser = new RepositorioUsuario();
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("email")).thenReturn("user@socialsport.com");
	}
		
	@Before
	public void setUp() throws IOException, ServletException {
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
		
	@Test
	public void testaRegistrarOK() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("emailR", "user@socialsport.com");
		parameters.put("nombre", "Social");
		parameters.put("apellidos", "Sport");
		parameters.put("contrasenaR", "test");
		parameters.put("foto", "/Servidor/img/profile.jpg");
		parameters.put("fecha_nacimiento", "2016-09-19");
		parameters.put("username", "sSport");
		servlet.doPost(request, response);
		assertEquals("El usuario se ha insertado correctamente",response_writer.toString());
	}
	
	@Test
	public void testbRegistrarErroneo() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("emailR", "user@socialsport.com");
		parameters.put("nombre", "");
		parameters.put("apellidos", "");
		parameters.put("contrasenaR", "test");
		parameters.put("foto", "/Servidor/img/profile.jpg");
		parameters.put("fecha_nacimiento", "2016-09-19");
		parameters.put("username", "");
		servlet.doPost(request, response);
		assertEquals("El usuario no se ha podido insertar",response_writer.toString());
	}

	@Test
	public void testbLoginErroneo() throws Exception {
		parameters.put("contrasenaL", "asdafgh");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals("El usuario no se ha podido logear",response_writer.toString());
	}
	
	@Test
	public void testbLoginOK() throws Exception {
		parameters.put("emailL","user@socialsport.com");
		parameters.put("contrasenaL", "test");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals("El usuario se ha logeado correctamente",response_writer.toString());
	}
	
	@Test
	public void testcUpdateOK() throws Exception {
		parameters.put("tipoPost", "Actualizar");
		parameters.put("nombre", "test");
		parameters.put("apellidos", "test");
		parameters.put("contrasena", "test");
		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("nick", "");
		servlet.doPost(request, response);
		assertEquals("El usuario se ha actualizado correctamente", response_writer.toString());
	}
	
	
}