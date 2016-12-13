package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Notificacion;
import modelo.RepositorioNotificacion;

import com.google.gson.Gson;

/**
 * Servlet de obtencion de notificaciones
 */
@WebServlet(value = "/notificaciones", name = "NotificacionesServlet")
public class NotificacionesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioNotificacion repo = new RepositorioNotificacion();
	private Gson gson = new Gson();

	/**
	 * Metodo para insertar una notificaci�n a un usuario.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String emailEnvia = (String)req.getSession().getAttribute("email");
		String emailRecibe = req.getParameter("emailRecibe");
		String tipo = req.getParameter("tipo");

		boolean realizado = repo.notificar(emailEnvia,emailRecibe,"nombreNotificacion","foto",tipo,"nombreUsuarioEnvia");
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			//resp.sendRedirect("muro.html");
			response = "Nueva notificaci�n";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			//resp.sendRedirect("muro.html");
			response = "No hay nueva notificaci�n";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver las notificaciones del usuario.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String response = null;
		List<Notificacion> notificaciones = null;
		String email = (String) req.getSession().getAttribute("email");
		notificaciones = repo.listarNotificaciones(email);
		response = gson.toJson(notificaciones);			
		resp.setStatus(HttpServletResponse.SC_OK);
		setResponse(response, resp);
	}
	
	/**
	 * Metodo para borrar una notificaci�n del usuario.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String id = req.getParameter("id");
		boolean realizado = repo.borrarNotificacion(id);
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "La notificaci�n se ha borrado correctamente";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "La notificaci�n no se ha podido borrar";
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