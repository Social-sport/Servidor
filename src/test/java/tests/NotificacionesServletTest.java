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

import controlador.NotificacionesServlet;
import modelo.Notificacion;
import modelo.RepositorioNotificacion;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificacionesServletTest {

	private NotificacionesServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private StringWriter response_writer;
	private Map<String, String> parameters;
	private RepositorioNotificacion repo;
	private Gson gson;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new NotificacionesServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		response_writer = new StringWriter();
		gson = new Gson();
		repo = new RepositorioNotificacion();
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void testNotificar() throws Exception {
		request.getSession().setAttribute("email", "john@socialsport.com");
		parameters.put("tipo", "Evento");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"No hay nueva notificación");
	}
	
	@Test
	public void testListaNotificaciones() throws Exception {
		request.getSession().setAttribute("email", "john@socialsport.com");
		servlet.doGet(request, response);
		List<Notificacion> notificaciones = repo.listarNotificaciones("john@socialsport.com");
		assertEquals(response_writer.toString(),gson.toJson(notificaciones));
	}
	
	@Test
	public void testBorrarNotificacion() throws Exception {
		parameters.put("id", "1");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"La notificación se ha borrado correctamente");
	}
}