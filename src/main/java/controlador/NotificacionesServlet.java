package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Evento;
import modelo.Notificacion;
import modelo.RepositorioEvento;
import modelo.RepositorioNotificacion;
import modelo.RepositorioUsuario;
import modelo.Usuario;

import com.google.gson.Gson;

/**
 * Servlet de obtencion de notificaciones
 */
@WebServlet(value = "/notificaciones", name = "NotificacionesServlet")
public class NotificacionesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioNotificacion repo = new RepositorioNotificacion();
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();
	private static RepositorioEvento repoEvento = new RepositorioEvento();
	private Gson gson = new Gson();

	/**
	 * Metodo para insertar una notificaci�n 
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String emailEnvia = (String)req.getSession().getAttribute("email");
		String emailRecibe = req.getParameter("emailRecibe");
		String tipo = req.getParameter("tipo");
		
		boolean realizado = false;
		Usuario seguidor = repoUsuario.findUsuario(emailEnvia);
		
		if (tipo.equals("Evento")) {
			
			String idEvento = req.getParameter("idEvento");
			Evento evento = repoEvento.findEvento(idEvento);
			
			if (evento!=null) {
				realizado = repo.notificar(emailEnvia,emailRecibe,evento.getNombre(),
						evento.getFoto(),tipo,seguidor.getNick());
			}else{
				realizado = repo.notificar(emailEnvia,emailRecibe,"nombreEvento",
						"fotoEvento",tipo,"Nick");
			}
			
			
		}
		if (tipo.equals("Seguidor")) {
			
			realizado = repo.notificar(emailEnvia,emailRecibe,seguidor.getNick(),
										seguidor.getFoto(),tipo,seguidor.getNick());
			
		}
	
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "Nueva notificaci�n";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "No hay nueva notificaci�n";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver las notificaciones del usuario.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
								throws ServletException, IOException {
		String response = null;
		List<Notificacion> notificaciones = null;
		String email = (String) req.getSession().getAttribute("email");
		String tipo = req.getParameter("tipo");
		if (tipo!=null && tipo!="") {
			int cuenta = repo.contarNotificaciones(email);
			response = cuenta+"";
			setResponse(response, resp);
		}
		else {
			System.out.println("email notificaciones: "+email);
			notificaciones = repo.listarNotificaciones(email);
			response = gson.toJson(notificaciones);
			resp.setStatus(HttpServletResponse.SC_OK);
			setResponse(response, resp);
		}
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