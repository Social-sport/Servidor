package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import modelo.Deporte;
import modelo.Evento;
import modelo.RepositorioDeporte;
import modelo.RepositorioEvento;

/**
 * Servlet de obtencion de eventos
 */
@MultipartConfig
@WebServlet(value = "/eventos", name = "EventosServlet")
public class EventosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioEvento repo = new RepositorioEvento();
	private static RepositorioDeporte repoDepor = new RepositorioDeporte();
	private Gson gson = new Gson();

	/**
	 * Metodo para insertar un evento a un deporte.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String nombre = null;
		String descripcion = null;
		String fecha = null;
		String hora = null;
		String deporte = null;
		String foto = null;
		Part partFoto = null;
		
		String email = (String)req.getSession().getAttribute("email");
		String tipo = req.getParameter("tipoPostEvent");
		System.out.println("tipoPost=: "+tipo);
		System.out.println("email=: "+email);
		
		if (tipo.equals("Crear")) {
			nombre = req.getParameter("nombre");
			descripcion = req.getParameter("descripcion");
			fecha = req.getParameter("fecha");
			hora = req.getParameter("hora");
			deporte = req.getParameter("deporte");
			partFoto = req.getPart("foto");
			
			if (partFoto!=null && !partFoto.equals("")) {
				foto = getFilename(partFoto);
				if (foto.equals("")) {
					foto = "/Servidor/img/event.jpg";
				}
				else {
					String uploads = getServletContext().getRealPath("/") + "img/uploads/event/";
					String rutaFoto = uploads + nombre + "-" + fecha + ".jpg";
					File folder = new File(uploads);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					
					InputStream is = partFoto.getInputStream();
					File fileFoto = new File(rutaFoto);
					FileOutputStream ous = new FileOutputStream(fileFoto);
					int dato = is.read();
					while (dato != -1) {
						ous.write(dato);
						dato = is.read();
					}
					is.close();
					ous.close();
					foto = "/Servidor/img/uploads/event/" + nombre + "-" + fecha + ".jpg";
				}
			}
			
			if (!nombre.equals("") && !descripcion.equals("") && !fecha.equals("") && 
					!deporte.equals("Selecciona un Deporte") && email!=null) {
				
				Evento evento = new Evento(nombre,descripcion,fecha,hora,deporte,email,foto);
				
				boolean realizado = repo.insertarEvento(evento);
				if (realizado) {
					//Inserta el evento del deporte en la BD
					resp.sendRedirect("profile.html");
					response = "El evento se ha insertado correctamente al deporte";
					resp.setStatus(HttpServletResponse.SC_OK);		
				}
			}
			else {
				response = "El evento no se ha podido insertar";
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		
		if (tipo.equals("Suscribirse")) {
			
			int id = Integer.parseInt(req.getParameter("idEvento"));
			
			boolean suscrito = repo.findSuscripcion(id, email);
			
			if (suscrito) {
				repo.darseDeBajaEvento(id, email);
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				System.out.println( "El usuario se ha dado del baja en el evento");
				
			}else{
				boolean suscribir = repo.suscribirseEvento(id, email);
			
				if (suscribir) {
					resp.setStatus(HttpServletResponse.SC_OK);			
					System.out.println("El usuario se ha suscrito correctamente al evento");
				}else{
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					System.out.println( "El usuario no se ha podido suscribir al evento");
				}
			}	
			
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
		String id = req.getParameter("idEvento");
		boolean realizado = repo.borrarEvento(id);
		if (realizado) {
			//borra el evento del deporte en la BD
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("profile.html");
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
		List<Evento> eventos = new LinkedList<Evento>();		
		String emailusuario = (String)req.getSession().getAttribute("email");
		String tipo = req.getParameter("tipo");

		if (tipo.equals("Buscar")) {
			String name = req.getParameter("search");
			eventos = repo.listarEventosCreados(name);

			response = gson.toJson(eventos);
			System.out.println("json con eventos buscados de " +name);
			System.out.println(response);

		}

		if (tipo.equals("listSportEvents")) {
			//Lista los eventos de los deportes suscritos
			List<Deporte> deportes = repoDepor.listarDeportesUsuario(emailusuario);
			for (Deporte deporte2 : deportes) {
				eventos.addAll(repo.listarEventosDeporte(deporte2.getNombre(),emailusuario));
			}
			resp.setStatus(HttpServletResponse.SC_OK);
			if (eventos.isEmpty()) {
				response = gson.toJson(eventos);
				System.out.println("No se encontraron eventos en la busqueda");
			} else {		
				response = gson.toJson(eventos);
				System.out.println("json con eventos buscados para el deportes suscritos ");
				System.out.println(response);
			}
		}
		
		if (tipo.equals("listEventsJustOneSport")) {
			//Lista los eventos de un solo deporte
			String deporte = req.getParameter("deporte");
			eventos = repo.listarEventosDeporte(deporte,emailusuario);			
			resp.setStatus(HttpServletResponse.SC_OK);
			
			if (eventos.isEmpty()) {
				response = gson.toJson(eventos);
				System.out.println("No se encontraron eventos en la busqueda");
			} else {		
				response = gson.toJson(eventos);
				System.out.println("json con eventos buscados para el deporte " + deporte);
				System.out.println(response);
			}
		}
		
		if (tipo.equals("listUserEvents")) {
			//Lista los eventos del usuario
			eventos = repo.listarEventosSuscritos(emailusuario);
			response = gson.toJson(eventos);			
			
			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos suscritos de " + emailusuario);
			}else{
				System.out.println("json con eventos del usuario "+ emailusuario);
				System.out.println(response);
			}

		}
		if (tipo.equals("listEventsCreated")) {
			//Lista los eventos creador por el usuario
			eventos = repo.listarMisEventos(emailusuario);
			response = gson.toJson(eventos);			
			
			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos suscritos de " + emailusuario);
			}else{
				System.out.println("json con eventos del usuario "+ emailusuario);
				System.out.println(response);
			}

		}
		if (tipo.equals("viewEvent")) {
			//Abre la pagina del evento
			String id = req.getParameter("idEvent");
			req.getSession().setAttribute("idEvent", id);
			resp.sendRedirect("eventPage.html");
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		
		if (tipo.equals("Event")) {
			//Carga la informacion en la pagina del evento
			String id = (String) req.getSession().getAttribute("idEvent");
			Evento evento = repo.findEventById(id);
			response = gson.toJson(evento);			
			if (evento!=null) {
				resp.setStatus(HttpServletResponse.SC_OK);
			}else{
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
	
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}
}