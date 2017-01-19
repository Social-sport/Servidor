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

import controlador.NotificacionesServlet;
import modelo.Evento;
import modelo.Notificacion;
import modelo.RepositorioEvento;
import modelo.RepositorioNotificacion;
import modelo.RepositorioUsuario;
import modelo.Usuario;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con Notificaciones
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificacionesServletTest {

	private static HttpServletRequest request;	
	private static NotificacionesServlet servlet;
	private static HttpSession session;
	private static RepositorioNotificacion repo;
	private static RepositorioUsuario repoUser;
	private static RepositorioEvento repoEvent;
	private static Evento evento;
	private static Usuario user;
	private static Gson gson;
	
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@BeforeClass
	public static void before() {
		gson = new Gson();		
		repo = new RepositorioNotificacion();
		servlet = new NotificacionesServlet();
		repoUser = new RepositorioUsuario();
		repoEvent = new RepositorioEvento();
		user = new Usuario("user@socialsport.com","Social","Sport",
					"2016-09-19","/Servidor/img/profile.jpg","test12");		
		repoUser.insertarUsuario(user);
		repoEvent.insertarEvento(new Evento("TestEvent","test servlet","13/11/2016", 
				"19:26","Futbol",user.getEmail(),""));
		evento = repoEvent.findEvento("TestEvent");
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
		repoEvent.borrarEventoByName("TestEvent");
		repoUser.borrarUsuario("user@socialsport.com");		
	}
		
	@Test
	public void testaNotificarEvento() throws Exception {
		parameters.put("idEvent", ""+evento.getId());
		parameters.put("tipo", "Evento");
		parameters.put("emailRecibe","userInvit@socialsport.com");
		servlet.doPost(request, response);
		assertEquals("Se ha enviado la notificacion",response_writer.toString());
	}
	
	@Test
	public void testbListaNotificaciones() throws Exception {
		servlet.doGet(request, response);
		List<Notificacion> notificaciones = repo.listarNotificaciones(user.getEmail());
		assertEquals(gson.toJson(notificaciones),response_writer.toString());
	}
	
	@Test
	public void testcBorrarcEvento() throws Exception {
		parameters.put("emailRecibe","userInvit@socialsport.com");
		servlet.doDelete(request, response);
		assertEquals("La notificaci�n se ha borrado correctamente",response_writer.toString());
	}
	
}