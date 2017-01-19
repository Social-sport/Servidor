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

import controlador.AmigosServlet;
import modelo.Usuario;
import modelo.RepositorioAmigo;
import modelo.RepositorioUsuario;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con Amigos
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AmigosServletTest {

	private static HttpServletRequest request;	
	private static AmigosServlet servlet;
	private static HttpSession session;
	private static RepositorioAmigo repo;
	private static RepositorioUsuario repoUser;
	private static Usuario user;
	private static Gson gson;
	
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;
	
	@BeforeClass
	public static void before() {
		gson = new Gson();				
		repo = new RepositorioAmigo();
		servlet = new AmigosServlet();
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

	@Test
	public void testaInsertarAmigos() throws Exception {
		parameters.put("emailAmigo", "usuario1@socialsport.com");		
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha insertado correctamente");
	}
	
	/**
	 * Se prueba a listar los usuarios seguidores
	 * @throws Exception
	 */
	@Test
	public void testListarSeguidores() throws Exception {
		parameters.put("tipoRelacion", "listSeguidores");
		servlet.doGet(request, response);
		List<Usuario> seguidores = repo.listarSeguidores(user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(seguidores));
	}
	
	/**
	 * Se prueba a listar los usuarios seguidos
	 * @throws Exception
	 */
	@Test
	public void testListarSeguidos() throws Exception {
		parameters.put("tipoRelacion", "listSeguidos");
		servlet.doGet(request, response);
		List<Usuario> seguidos = repo.listarSeguidos(user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(seguidos));
	}
	
	/**
	 * Se prueba a eliminar amigos
	 * @throws Exception
	 */
	@Test
	public void testdBorrarAmigos() throws Exception {
		parameters.put("amigo", "usuario1@socialsport.com");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha borrado correctamente");
	}	
}