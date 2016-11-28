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
	public void testLoginErroneo() throws Exception {
		parameters.put("emailL", "asd");
		parameters.put("contrasenaL", "asdafgh");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido logear");
	}
	
	@Test
	public void testLoginOK() throws Exception {
		parameters.put("emailL", "test@test.com");
		parameters.put("contrasenaL", "test");
		parameters.put("tipo", "initSesion");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha logeado correctamente");
	}
	
	@Test
	public void testRegistrarOK() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("emailR", "trys@trys");
		parameters.put("nombre", "trys2");
		parameters.put("apellidos", "try");
		parameters.put("contrasenaR", "try");
		parameters.put("foto", "try");
		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("username", "trys2");
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		//borramos porque el usuario ya existe de test anteriores
		repoUsuario.borrarUsuario("trys@trys");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha insertado correctamente");
	}
	
	@Test
	public void testRegistrarErroneo() throws Exception {
		parameters.put("tipoPost", "Registro");
		parameters.put("email", "try");
		parameters.put("nombre", "try");
		parameters.put("apellidos", "try");
		parameters.put("contrasena", "try");
		parameters.put("foto", "try");
		parameters.put("fecha_nacimiento", "deberianserunosnumeros");
		parameters.put("username", "try");
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		//borramos porque el usuario ya existe de test anteriores
		repoUsuario.borrarUsuario("try");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido insertar");
	}
	
	/*@Test
	public void testActualizarOK() throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("email", "test");
		
		parameters.put("tipoPost", "Actualizar");
		
		parameters.put("email", "test");
		parameters.put("nombre", "test");
		parameters.put("apellidos", "test");
		parameters.put("contrasena", "test");
		parameters.put("foto", "/Servidor/img/profile.jpg");
		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("username", "test");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha actualizado correctamente");
	}*/
	
	/*@Test
	public void testActualizarErroneo() throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("email", "test");
		
		parameters.put("tipoPost", "Actualizar");
		
		parameters.put("nombre", "try");
		parameters.put("apellidos", "try");
		parameters.put("contrasena", "try");
		parameters.put("foto", "try");
		parameters.put("fecha_nacimiento", "asdasdasdasd");
		parameters.put("nick", "try");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido actualizar");
	}*/
}