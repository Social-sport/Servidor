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
import modelo.RepositorioEvento;

/**
 * Servlet de obtencion de eventos
 */
@WebServlet(value = "/eventos", name = "EventosServlet")
public class EventosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioEvento repo = new RepositorioEvento();

	/**
	 * Metodo para insertar un evento a un deporte.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String nombre = req.getParameter("nombre");
		String descripcion = req.getParameter("descripcion");
		String fecha = req.getParameter("fecha");
		String hora = req.getParameter("hora");
		String deporte = req.getParameter("deporte");
		String creador = req.getParameter("creador");
		String foto = req.getParameter("foto");
		Evento evento = new Evento(nombre,descripcion,fecha,hora,deporte,creador,foto);
		boolean realizado = repo.insertarEvento(evento);
		if (realizado) {
			//Inserta el evento del deporte en la BD
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El evento se ha insertado correctamente al deporte";			
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El evento no se ha podido insertar";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para borrar un evento de un deporte.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String nombre = req.getParameter("nombre");
		boolean realizado = repo.borrarEvento(nombre);
		if (realizado) {
			//borra el evento del deporte en la BD
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El evento se ha borrado correctamente";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El evento no se ha podido borrar";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver eventos.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		List<Evento> eventos = null;
		String deporte = req.getParameter("deporte");
		String usuario = req.getParameter("usuario");
		if (deporte == null) {
			//probablemente nueva funcionalidad
		}
		else {
			if (usuario == null) {
				//Lista los eventos del deporte
				eventos = repo.listarEventosDeporte(deporte);
				resp.setStatus(HttpServletResponse.SC_OK);
				if (eventos.isEmpty()) {
					response = "El deporte no tiene eventos";
				}
				else {
					response = "Eventos deporte";
				}
			}
			else {
				//Lista los eventos del usuario
				eventos = repo.listarEventosUsuario(usuario);
				if (eventos.isEmpty()) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response = "El usuario no tiene eventos";
				}
				else {
					resp.setStatus(HttpServletResponse.SC_OK);
					response = "Eventos usuario";
				}
			}
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