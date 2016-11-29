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

import controlador.DeportesServlet;
import modelo.Deporte;
import modelo.RepositorioDeporte;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeportesServletTest {

	private DeportesServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private StringWriter response_writer;
	private Map<String, String> parameters;
	private RepositorioDeporte repo;
	private Gson gson;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new DeportesServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		response_writer = new StringWriter();
		gson = new Gson();
		repo = new RepositorioDeporte();
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void testSuscribirseDeporteErroneo() throws Exception {
		request.getSession().setAttribute("email", "test@test.com");
		parameters.put("deporte", "asdafgh");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido suscribir al deporte");
		//assertEquals(response.getStatus(),HttpServletResponse.SC_BAD_REQUEST);
	}
	
	/*@Test
	public void testASuscribirseDeporteOK() throws Exception {		
		request.getSession().setAttribute("email", "test@test.com"); // No esta iniciando un sesion valida		
		parameters.put("deporte", "Futbol");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha suscrito correctamente al deporte");
	}*/
	
	/*@Test
	public void testZDarseDeBajaDeporteOK() throws Exception {
		parameters.put("deporte", "Futbol");
		parameters.put("email", "test@test.com");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha dado de baja correctamente del deporte");
	}*/
	
	@Test
	public void testDarseDeBajaDeporteErroneo() throws Exception {
		parameters.put("deporte", "pruebaasdadada");
		parameters.put("email", "try");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido dar de baja del deporte");
	}
	
	
	@Test
	public void testListarDeportes() throws Exception {
		parameters.put("tipoDeport", "AllSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportes();
		assertEquals(response_writer.toString(),gson.toJson(deportes));
	}

	@Test 
	public void testListarDeportesUsuarioOK() throws Exception {
		parameters.put("email", "test");
		parameters.put("tipoDeport", "ListUserSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportesUsuario("test");
		assertEquals(response_writer.toString(),gson.toJson(deportes));
	}
	@Test 
	public void testListarDeportesDisponiblesOK() throws Exception {
		parameters.put("email", "test");
		parameters.put("tipoDeport", "AvailableSports");
		servlet.doGet(request, response);
		List<Deporte> deportes = repo.listarDeportesDisponibles("test");
		assertEquals(response_writer.toString(),gson.toJson(deportes));
	}
}