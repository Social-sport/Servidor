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

import controlador.EventosServlet;
import modelo.RepositorioEvento;
import modelo.RepositorioUsuario;
import modelo.Usuario;
import modelo.Evento;

/**
 * Clase que contiene los tests para probar el correcto funcionamiento de toda aquella
 * funcionalidad relacionada con Eventos
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventosServletTest {
	
	private static HttpServletRequest request;	
	private static EventosServlet servlet;
	private static HttpSession session;
	private static RepositorioEvento repo;
	private static RepositorioUsuario repoUser;
	private static Usuario user;
	private static Gson gson;
	private static Evento evento;
	
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;
	private Evento event;

	@BeforeClass
	public static void before() {
		gson = new Gson();		
		repo = new RepositorioEvento();
		servlet = new EventosServlet();
		repoUser = new RepositorioUsuario();
		user = new Usuario("user@socialsport.com","Social","Sport",
					"2016-09-19","/Servidor/img/profile.jpg","test12");
		evento = new Evento("TestEvent","test servlet","13/11/2016", 
				"19:26","Futbol",user.getEmail(),"");
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
		event = repo.findEvento("TestEvent");
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
	public void testaInsertarEventos() throws Exception {		
		parameters.put("tipoPostEvent", "Crear");
		parameters.put("nombre", evento.getNombre());
		parameters.put("descripcion", evento.getDescripcion());
		parameters.put("fecha", evento.getFecha());
		parameters.put("hora",evento.getHora());
		parameters.put("deporte", evento.getDeporte());
		parameters.put("foto", evento.getFoto());
		servlet.doPost(request, response);
		assertEquals("El evento se ha insertado correctamente al deporte",response_writer.toString());
	}	
	
	@Test
	public void testbModificarEventos() throws Exception {
		parameters.put("idEvento",""+event.getId());
		parameters.put("tipoPostEvent", "Actualizar");
		parameters.put("nombre", "TestEvent");
		parameters.put("descripcion", "Test Servlet Evento Actualizar");
		parameters.put("fecha", "13/11/2017");
		parameters.put("hora", "11:15:00");
		parameters.put("deporte", "Futbol");
		servlet.doPost(request, response);
		assertEquals("El evento se ha actualizado correctamente",response_writer.toString());
	}
	
	@Test
	public void testbSuscribirseEvento() throws Exception {
		parameters.put("tipoPostEvent", "Suscribirse");
		parameters.put("idEvento", ""+event.getId());
		servlet.doPost(request, response);
		assertEquals("El usuario se ha suscrito correctamente al evento",response_writer.toString());
	}	
	
	
	@Test
	public void testbListarEventosSuscritos() throws Exception {		
		parameters.put("deporte", "Futbol");	
		parameters.put("tipo", "listUserEvents");
		servlet.doGet(request, response);		
		List<Evento> eventos = repo.listarEventosSuscritos(user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(eventos));	 
	}
	
	@Test
	public void testbListarEventosCreados() throws Exception {		
		parameters.put("deporte", "Futbol");	
		parameters.put("tipo", "listEventsCreated");
		servlet.doGet(request, response);		
		List<Evento> eventos = repo.listarMisEventos(user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(eventos));	 
	}
	
	@Test
	public void testbListarEventosNoCreados() throws Exception {		
		parameters.put("deporte", "Futbol");	
		parameters.put("tipo", "listEventsJustOneSport");
		servlet.doGet(request, response);		
		List<Evento> eventos = repo.listarEventosDeporte("Futbol",user.getEmail());
		assertEquals(response_writer.toString(),gson.toJson(eventos));	 
	}
	
	@Test
	public void testcSalirEvento() throws Exception {
		parameters.put("tipoPostEvent", "Suscribirse");
		parameters.put("idEvento", ""+event.getId());
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"Se ha dado de baja del evento");
	}
	
	@Test
	public void testdBorrarEvento() throws Exception {		
		parameters.put("idEvento", ""+event.getId());
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El evento se ha borrado correctamente");
	}
	
}