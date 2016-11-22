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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controlador.DeportesServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeportesServletTest {

	private DeportesServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new DeportesServlet();
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
	public void testSuscribirseDeporteErroneo() throws Exception {
		parameters.put("email", "asd");
		parameters.put("deporte", "asdafgh");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido suscribir al deporte");
	}
	
	@Test
	public void testASuscribirseDeporteOK() throws Exception {
		parameters.put("deporte", "prueba");
		parameters.put("email", "try");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha suscrito correctamente al deporte");
	}
	
	@Test
	public void testZDarseDeBajaDeporteOK() throws Exception {
		parameters.put("deporte", "prueba");
		parameters.put("email", "try");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El usuario se ha dado de baja correctamente del deporte");
	}
	
	@Test
	public void testDarseDeBajaDeporteErroneo() throws Exception {
		parameters.put("deporte", "pruebaasdadada");
		parameters.put("email", "try");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El usuario no se ha podido dar de baja del deporte");
	}
	
	
	@Test
	public void testListarDeportes() throws Exception {
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"Todos los deportes");
	}
	
	@Test
	public void testListarDeportesUsuarioErroneo() throws Exception {
		parameters.put("email", "asdasdas");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario no existe");
	}
	
	@Test
	public void testListarDeportesUsuarioOK() throws Exception {
		parameters.put("email", "test");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"Deportes usuario");
	}
}