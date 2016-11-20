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
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new UsuariosServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		response_writer = new StringWriter();
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
	}

	@Test
	public void testLoginErroneo() throws Exception {
		parameters.put("username", "asd");
		parameters.put("contrasena", "asdafgh");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido logear");
	}
	
	@Test
	public void testLoginOK() throws Exception {
		parameters.put("email", "test");
		parameters.put("contrasena", "test");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha logeado correctamente");
	}
	
	
	@Test
	public void testRegistrarOK() throws Exception {
		parameters.put("email", "try");
		parameters.put("nombre", "try");
		parameters.put("apellidos", "try");
		parameters.put("contrasena", "try");
		parameters.put("foto", "try");
		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("username", "try");
		RepositorioUsuario repoUsuario = new RepositorioUsuario();
		//borramos porque el usuario ya existe de test anteriores
		repoUsuario.borrarUsuario("try");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha insertado correctamente");
	}
	
	@Test
	public void testRegistrarErroneo() throws Exception {
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
	
	@Test
	public void testActualizarOK() throws Exception {
		parameters.put("email", "test");
		parameters.put("nombre", "test");
		parameters.put("apellidos", "test");
		parameters.put("contrasena", "test");
		parameters.put("foto", "test");
		parameters.put("fecha_nacimiento", "1900-10-10");
		parameters.put("username", "test");
		servlet.doPut(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha actualizado correctamente");
	}
	
	@Test
	public void testActualizarErroneo() throws Exception {
		parameters.put("email", "test");
		parameters.put("nombre", "try");
		parameters.put("apellidos", "try");
		parameters.put("contrasena", "try");
		parameters.put("foto", "try");
		parameters.put("fecha_nacimiento", "asdasdasdasd");
		parameters.put("nick", "try");
		servlet.doPut(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido actualizar");
	}
}