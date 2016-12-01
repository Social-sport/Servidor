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

import controlador.AmigosServlet;
import modelo.Usuario;
import modelo.RepositorioAmigo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AmigosServletTest {

	private AmigosServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private StringWriter response_writer;
	private Map<String, String> parameters;
	private RepositorioAmigo repo;
	private Gson gson;

	@Before
	public void setUp() throws IOException {
		parameters = new HashMap<String, String>();
		servlet = new AmigosServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		repo = new RepositorioAmigo();
		gson = new Gson();
		response_writer = new StringWriter();
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void atestAInsertarAmigos() throws Exception {
		parameters.put("email", "usuario@socialsport.com");
		parameters.put("emailAmigo", "usuario1@socialsport.com");		
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha insertado correctamente");
	}
	
	@Test
	public void btestListarSeguidores() throws Exception {
		parameters.put("list", "listSeguidores");
		parameters.put("tipoRelacion", "test");
		parameters.put("usuario", "usuario@socialsport.com");
		servlet.doGet(request, response);
		List<Usuario> seguidores = repo.listarSeguidores("usuario@socialsport.com");
		assertEquals(response_writer.toString(),gson.toJson(seguidores));
	}
	
	@Test
	public void ctestListarSeguidos() throws Exception {
		parameters.put("list", "listSeguidos");
		parameters.put("tipoRelacion", "test");
		parameters.put("usuario", "usuario@socialsport.com");
		servlet.doGet(request, response);
		List<Usuario> seguidos = repo.listarSeguidos("usuario@socialsport.com");
		assertEquals(response_writer.toString(),gson.toJson(seguidos));
	}
	
	@Test
	public void dtestZBorrarAmigos() throws Exception {
		parameters.put("usuario", "usuario@socialsport.com");
		parameters.put("amigo", "usuario1@socialsport.com");
		servlet.doDelete(request, response);
		assertEquals(response_writer.toString(),"El amigo se ha borrado correctamente");
	}
	
	
	
}