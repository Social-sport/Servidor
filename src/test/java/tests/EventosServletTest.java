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

import controlador.EventosServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventosServletTest {

	private EventosServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new EventosServlet();
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
	public void testAInsertarEventos() throws Exception {
		parameters.put("nombre", "tryTest");
		parameters.put("descripcion", "test servlet");
		parameters.put("fecha", "13/11/2016");
		parameters.put("hora", "19:26");
		parameters.put("deporte", "prueba");
		parameters.put("creador", "test");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El evento se ha insertado correctamente al deporte");
	}
	
	@Test
	public void testZBorrarEventos() throws Exception {
		parameters.put("nombre", "tryTest");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El evento se ha borrado correctamente");
	}
	
	@Test
	public void testListarEventosDeporte() throws Exception {
		parameters.put("deporte", "prueba");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"Eventos deporte");
	}
	
	@Test
	public void testListarEventosUsuario() throws Exception {
		parameters.put("usuario", "test");
		parameters.put("deporte", "prueba");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"El usuario no tiene eventos");
	}
	
}