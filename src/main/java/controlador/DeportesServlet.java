package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Deporte;
import modelo.RepositorioDeporte;

/**
 * Servlet de obtencion de usuaiors
 */
@WebServlet(value = "/deportes", name = "DeportesServlet")
public class DeportesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioDeporte repo = new RepositorioDeporte();

	/**
	 * M�todo para a�adir usuarios a la BD a trav�s del cliente.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String response = null;
		String email = session.getAttribute("email").toString();
		String deporte = req.getParameter("deporte");
		boolean suscrito = repo.verificarSuscripcionDeporte(email);
		boolean realizado = repo.suscribirseDeporte(deporte,email);
		if (!(suscrito) && realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.jsp");
			response = "El usuario se ha suscrito correctamente al deporte";
		}
		else if (suscrito){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("muro.jsp");
			response = "El usuario ya se encuentra suscrito al deporte";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("muro.jsp");
			response = "El usuario no se ha podido suscribir al deporte";
		}
		setResponse(response, resp);
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String email = req.getParameter("email");
		String deporte = req.getParameter("deporte");
		boolean realizado = repo.darseDeBajaDeporte(deporte,email);
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El usuario se ha dado de baja correctamente del deporte";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El usuario no se ha podido dar de baja del deporte";
		}
		setResponse(response, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Gson gson = new Gson();
		
		String response = null;
		List<Deporte> deportes = null;
		deportes = repo.listarDeportes();
		
		if (deportes.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No existen deportes");
		}
		else {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = gson.toJson(deportes);
		}
		setResponse(response, resp);
	}

	/**
	 * Agrega una respuesta a la response
	 * 
	 * @param response
	 *            respuesta
	 * @param resp
	 *            response
	 */
	private void setResponse(String response, HttpServletResponse resp) {
		resp.setContentType("application/json");
		try {
			PrintWriter out = resp.getWriter();
			out.print(response);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}