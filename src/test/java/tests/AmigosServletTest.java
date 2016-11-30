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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controlador.AmigosServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AmigosServletTest {

	private AmigosServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new AmigosServlet();
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

	/*@Test
	public void testAInsertarAmigos() throws Exception {
		request.getSession().setAttribute("email", "test");
		parameters.put("emailAmigo", "prueba3");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha insertado correctamente");
	}*/
	
	@Test
	public void testZBorrarAmigos() throws Exception {
		parameters.put("usuario", "null");
		parameters.put("amigo", "prueba3");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha borrado correctamente");
	}
	
	@Test
	public void testListarAmigos() throws Exception {
		parameters.put("usuario", "null");
		servlet.doGet(request, response);
		assertEquals(response_writer.toString(),"Amigos");
	}
	
}