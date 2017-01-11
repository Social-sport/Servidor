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

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.Gson;

import controlador.EventosServlet;
import controlador.UsuariosServlet;
import modelo.RepositorioEvento;
import modelo.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventosServletTest {

	// servlet = EventoServlet (donde está doPost, doGet y doDelete)
	private EventosServlet servletEventos;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private UsuariosServlet servletUsuarios;
	
	// Para capturar lo que se recibe del response
	private StringWriter response_writer;

	// Parametros que se meterán en el request
	private Map<String, String> parameters;
	
	// repositorio de eventos (con insertarEvento, buscarEvento, borrarEvento, etc)
	private RepositorioEvento repo;
	private Gson gson;

	// Método que inicializa la sesión y el servlet y se prepara para escuchar las peticiones
	@Before
	public void setUp() throws IOException {
		
		// inicializamos los parámetros con un HashMap vacío
		parameters = new HashMap<String, String>();
		
		// Inicializamos el servlet de eventos 
		servletEventos = new EventosServlet();
		
		// Crea un objeto mock de la clase HttpServletRequest (sirve para verificar)
		request = mock(HttpServletRequest.class);
		
		// Crea un objeto mock de la clase HttpServletResponse (sirve para verificar)
		response = mock(HttpServletResponse.class);
		
		// Crea un objeto mock de la clase HttpSession (sirve para verificar)
		session = mock(HttpSession.class);
		
		servletUsuarios = new UsuariosServlet();
		//session.setAttribute("email", "usuario@socialsport.com");

		// inicializa el writer que captura el response
		response_writer = new StringWriter();
		gson = new Gson();
		
		// inicializa el repositorio de eventos
		repo = new RepositorioEvento();
		
		// si detecto una petición de parámetro del request entonces respondo con un Answer, que es
		// lo que me devuelve el usuario y la sesión
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		
		// Si detecto que he recibido algo desde el response, lo devuelvo 
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
		
		// Si detecto que me han pedido la sesión desde el request, devuelvo la sesión. 
		when(request.getSession()).thenReturn(session);

		
		
		/*
		 * 
		 *
		
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("password")).thenReturn(UserManager.SUPER_SECRET_ADMIN_PASSWORD); 
		UsuariosServlet usuario  = new UsuariosServlet(); 
		when(usuario.isAuthenticated(anyString(), anyString())).thenReturn(true);*/

		
		
	}

	@Test
	public void testAInsertarEventos() throws Exception {
		

		//verify(session, times(1)).getAttribute("email");
		// Pongo como email de sesión "usuario@socialsport.com"
		
		request.getSession().setAttribute("email", "usuario@socialsport.com");
		String a = (String) request.getSession().getAttribute("email");
		
		System.out.println("PRUEBAAAAAAAA" + a);
		
		HttpServletRequest req;
		HttpServletResponse resp;
		
		req =  mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		
		req.getSession().setAttribute("email", "usuario@socialsport.com");
		parameters.put("email", "usuario@")
		servletUsuarios.doGet(req, resp);
		
		
		System.out.println("AQUIya"+ servletUsuarios.isAuthenticated(request, response));
		
		
		// Pongo como parámetros de la request el tipo "tipoPostEvent", con valor "Crear", para indicar que quiero crear un evento
		// [EventosServlet -> doPost -> if (tipo.equals("Crear"))]
		parameters.put("tipoPostEvent", "Crear");
		parameters.put("nombre", "Event");
		parameters.put("descripcion", "test servlet");
		parameters.put("fecha", "13/11/2016");
		parameters.put("hora", "19:26");
		parameters.put("deporte", "Futbol");
		
		// Llamamos al método doPost del servlet de eventos con la request rellena con los parámetros anteriores
		servletEventos.doPost(request, response);
		
		// Esperamos que la el response devuelto sea "El evento no se ha podido insertar" 
		// puesto que no hay ningún usuario con el email "usuario@socialsport.com"
		assertEquals(response_writer.toString(),"El evento se ha insertado correctamente al deporte");
	}
	
	
	@Test
	public void testZBorrarEventos() throws Exception {
		
		// Pongo como parametro del request el nombre "Event"
		parameters.put("nombre", "Event");
		
		// Invoco al método doDelete con el parámetro anterior
		servletEventos.doDelete(request, response);
		
		// Compruebo que el evento se ha borrado correctamente
		assertEquals(response_writer.toString(),"El evento se ha borrado correctamente");
	}
	
	@Test
	public void testListarEventosDeporte() throws Exception {
		
		// Pone como usuario de sesión al usuario con el email "usuario@socialsport.com"
		request.getSession().setAttribute("email", "usuario@socialsport.com");
		
		// Pone como parametro deporte "Futbol"
		parameters.put("deporte", "Futbol");
		
		// Queremos listar los eventos del deporte anterior
		parameters.put("tipo", "listSportEvents");
		
		// Invocamos al método doGet del servlet de Eventos con los parámetros anteriores
		servletEventos.doGet(request, response);
		
		// eventos = todos los eventos del deporte Futbl
		List<Evento> eventos = repo.listarEventosDeporte("Futbl","usuario@socialsport.com");
		
		// Comprobamos que los eventos devueltos se corresponden con los de la BD 
		assertEquals(response_writer.toString(),gson.toJson(eventos));
	}
	
	@Test
	public void testListarEventosBuscados() throws Exception {
		
		// ponemos como parámetro search el evento "encuentro de futbol"
		parameters.put("search", "encuentro de futbol");
		
		// tipo = Buscar
		parameters.put("tipo", "Buscar");
		
		// Invocamos al método doGet del servlet de Eventos con los parámetros anteriores
		servletEventos.doGet(request, response);
		
		// eventos = todos los eventos cuyo nombre coincide con "encuentro de futbol"
		List<Evento> eventos = repo.listarEventosCreados("encuentro de futbol");
		
		// Comprobamos que los eventos devueltos se corresponden con los de la BD 
		assertEquals(response_writer.toString(),gson.toJson(eventos));
	
	}
	
	@Test
	public void testListarEventosUsuario() throws Exception {
		
		// Ponemos como atributo de la sesion el email "usuario@socialsport.com"
		request.getSession().setAttribute("email", "usuario@socialsport.com");
		
		// ponemos como parametro deporte = Futbol
		parameters.put("deporte", "Futbol");
		
		// tipo = listUserEvents	
		parameters.put("tipo", "listUserEvents");
		
		// Invocamos al método doGet del servlet de Eventos con los parámetros anteriores
		servletEventos.doGet(request, response);
		
		// eventos = todos los eventos a los que está suscrito el usuario con el email "usuario@socialsport.com"
		List<Evento> eventos = repo.listarEventosSuscritos("usuario@socialsport.com");
		
		// Comprobamos que los eventos devueltos se corresponden con los de la BD 
		assertEquals(response_writer.toString(),gson.toJson(eventos));
	
	}
	
}