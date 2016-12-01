package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controlador.UsuariosServlet;
import modelo.RepositorioUsuario;

public class UsuariosServletTest {

	private UsuariosServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private StringWriter response_writer;
	private Map<String, String> parameters;


	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new UsuariosServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		response_writer = new StringWriter();
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void btestLoginErroneo() throws Exception {
		parameters.put("emailL", "asd");
		parameters.put("contrasenaL", "asdafgh");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido logear");
	}

	@Test
	public void ctestRegistrarOK() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("emailR", "usuario@socialsport.com");
		parameters.put("nombre", "Social");
		parameters.put("apellidos", "Sport");
		parameters.put("contrasenaR", "test");
		parameters.put("foto", "/Servidor/img/profile.jpg");
		parameters.put("fecha_nacimiento", "2016-09-19");
		parameters.put("username", "SocialSport");
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		//borramos porque el usuario ya existe de test anteriores
		repoUsuario.borrarUsuario("usuario@socialsport.com");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha insertado correctamente");
	}

	@Test
	public void atestLoginOK() throws Exception {
		parameters.put("emailL", "usuario@socialsport.com");
		parameters.put("contrasenaL", "test");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha logeado correctamente");
	}

	@Test
	public void dtestRegistrarErroneo() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("emailR", "usuario@socialsport.com");
		parameters.put("nombre", "");
		parameters.put("apellidos", "");
		parameters.put("contrasenaR", "test");
		parameters.put("foto", "/Servidor/img/profile.jpg");
		parameters.put("fecha_nacimiento", "2016-09-19");
		parameters.put("username", "");
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		//borramos porque el usuario ya existe de test anteriores
		repoUsuario.borrarUsuario("usuario@socialsport.com");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido insertar");
	}

	@Test
	public void testActualizarOK() throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("email", "usuario@socialsport.com");

		parameters.put("tipoPost", "Actualizar");

		parameters.put("nombre", "test");
		parameters.put("apellidos", "test");
		parameters.put("contrasena", "test");

		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("nick", "test");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"null");
	}
}