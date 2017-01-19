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
 * 		POST /notificaciones. Inserta una notificaci�n en la base de datos
 * 		GET /notificaciones. Petici�n que devuelve las notificaciones de un usuario.
 * 		DELETE /notificaciones. Petici�n que elimina notificaciones de un usuario.
 */

@WebServlet(value = "/notificaciones", name = "NotificacionesServlet")
public class NotificacionesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioNotificacion repo = new RepositorioNotificacion();	//Repositorio notificaciones
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();	//Repositorio usuarios
	private static RepositorioEvento repoEvento = new RepositorioEvento();	//Repositorio eventos
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
		//Si se pide una notificaci�n de evento
		if (tipo.equals("Evento")) {
			String idEvento = req.getParameter("idEvent");
			Evento evento = repoEvento.findEventById(idEvento, emailEnvia);
			if (evento != null) {
				//Se le notifica al usuario
				realizado = repo.notificar(emailEnvia, emailRecibe, evento.getNombre(),
						evento.getFoto(), tipo, seguidor.getNick(), evento.getId());
			} 
		}
		//Si de pide una notificaci�n de seguimiento
		if (tipo.equals("Seguidor")) {
			realizado = repo.notificar(emailEnvia,emailRecibe, seguidor.getNick(),
										seguidor.getFoto(), tipo,seguidor.getNick(), 0);
		}
		//Si la operaci�n es exitosa
		if (realizado) {
			//Se devuelve c�digo 200 (�xito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "Se ha enviado la notificacion";
		}
		else {
			//Se devuelve c�digo 400 (petici�n no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "No hay nueva notificacion";
		}
		resp.sendRedirect("eventPage.html");
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
			//Se devuelve c�digo 200 (�xito)
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
		//Si la operaci�n se ha ralizado con �xito
		if (realizado) {
			//Se devuelve c�digo 200 (�xito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "La notificaci�n se ha borrado correctamente";
		}
		else {
			//Se devuelve c�digo 400 (petici�n no exitosa)
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