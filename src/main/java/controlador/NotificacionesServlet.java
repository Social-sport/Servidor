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
 * Servlet relativo a la funcionalidad de notificaciones.
 * 		POST /notificaciones. Inserta una notificación en la base de datos
 * 		GET /notificaciones. Petición que devuelve las notificaciones de un usuario.
 * 		DELETE /notificaciones. Petición que elimina notificaciones de un usuario.
 */

@WebServlet(value = "/notificaciones", name = "NotificacionesServlet")
public class NotificacionesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioNotificacion repo = new RepositorioNotificacion();	//Repositorio notificaciones
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();	//Repositorio usuarios
	private static RepositorioEvento repoEvento = new RepositorioEvento();	//Repositorio eventos
	private Gson gson = new Gson();

	/**
	 * Metodo para insertar una notificación
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
		//Si se pide una notificación de evento
		if (tipo.equals("Evento")) {
			String idEvento = req.getParameter("idEvent");
			Evento evento = repoEvento.findEventById(idEvento, emailEnvia);
			if (evento != null) {
				//Se le notifica al usuario
				realizado = repo.notificar(emailEnvia, emailRecibe, evento.getNombre(),
						evento.getFoto(), tipo, seguidor.getNick(), evento.getId());
			} else {
				realizado = repo.notificar(emailEnvia,emailRecibe,"nombreEvento",
						"fotoEvento",tipo,"Nick", 1);
			}
		}
		//Si de pide una notificación de seguimiento
		if (tipo.equals("Seguidor")) {
			realizado = repo.notificar(emailEnvia,emailRecibe, seguidor.getNick(),
										seguidor.getFoto(), tipo,seguidor.getNick(), 0);
		}
		//Si la operación es exitosa
		if (realizado) {
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "Se ha enviado la notificacion";
		}
		else {
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "No hay nueva notificacion";
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
		//Si se pide contar las notificaciones
		if (tipo != null && tipo != "" && tipo.equals("contar")) {
			int cuenta = repo.contarNotificaciones(email);
			response = cuenta + "";
			setResponse(response, resp);
		}
		else {
			System.out.println("email notificaciones: " + email);
			notificaciones = repo.listarNotificaciones(email);
			response = gson.toJson(notificaciones);
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			setResponse(response, resp);
		}
	}
	
	/**
	 * Metodo para borrar una notificación del usuario.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String id = req.getParameter("id");
		boolean realizado = repo.borrarNotificacion(id);
		//Si la operación se ha ralizado con éxito
		if (realizado) {
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "La notificación se ha borrado correctamente";
		}
		else {
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "La notificación no se ha podido borrar";
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