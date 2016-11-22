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
@WebServlet(value = "/deportesSuscritos", name = "DeportesSuscritosServlet")
public class DeportesSuscritosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioDeporte repo = new RepositorioDeporte();


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Gson gson = new Gson();
		
		String response = null;
		List<Deporte> deportes = null;
		String email = session.getAttribute("email").toString();
		
		if (email != null) {
			deportes = repo.listarDeportesUsuario(email);
			
			resp.setStatus(HttpServletResponse.SC_OK);
			response = gson.toJson(deportes);
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("signup.jsp");
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