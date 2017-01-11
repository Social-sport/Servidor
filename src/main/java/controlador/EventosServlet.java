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
 * Crear eventos
 * Unirse evento
 * Borrar evento
 * Filtrar en perfil eventos
 * Invitar (compartir) eventos (GUI)
 */
@MultipartConfig
@WebServlet(value = "/eventos", name = "EventosServlet")
public class EventosServlet extends HttpServlet {

	// Esto siempre se pone
	private static final long serialVersionUID = 1L;
	
	// repo = Repositorio del evento; contiene métodos que actúan de intermediarios entre BD y el servidor
	// repo.darseDeBajaEvento, insertarEvento, borrarEvento, findEvento, findEventoById, findSuscripcion,
	// listarEventosCreados, listarEventosDeporte, listarEventosSuscritos, listarMisEventos, suscribirseEvento
	private static RepositorioEvento repoEvento = new RepositorioEvento();

	// repoDepor = Repositorio del deporte; contiene métodos que actúan de intermediarios entre BD y el servidor
	// repoDepor.addNumSuscrito, darseDeBajaDeporte, findDeporte, listarDeportes, listarDeportesDisponibles,
	// listarDeportesUsuario, suscribirseDeporte
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
		
		// "Part" class represents a part or form item that was received within a multipart/form-data POST request.
		// Usamos partFoto para guardar la foto que introduce el usuario. 
		Part partFoto = null;
		
		// se guarda el email del usuario
		String email = (String)req.getSession().getAttribute("email");
		
		// se guarda el tipo de evento
		String tipo = req.getParameter("tipoPostEvent");
		System.out.println("tipoPost=: "+tipo);
		System.out.println("email=: "+email);
		
		// si se trata de crear un evento
		if (tipo.equals("Crear")) {
			
			// nombre = nombre del evento. Lo recibe desde el parámetro "nombre" del formulario de creación de evento, etc.
			nombre = req.getParameter("nombre");
			descripcion = req.getParameter("descripcion");
			fecha = req.getParameter("fecha");
			hora = req.getParameter("hora");
			deporte = req.getParameter("deporte");
			partFoto = req.getPart("foto");
			
			// Si el usuario ha insertado una foto
			if (partFoto!=null && !partFoto.equals("")) {
				
				// foto = nombre de la foto introducida por el usuario
				foto = getFilename(partFoto);
				
				// Si el nombre de la foto es vacío
				if (foto.equals("")) {
					
					// Lo ponemos como una foto por defecto, que guardamos internamente en el servidor
					foto = "/Servidor/img/event.jpg";
				}
				
				// Si el nombre de la foto no es vacío
				else {
					
					// uploads = cogemos el path del servidor y le adjuntamos /img/uploads/event/
					String uploads = getServletContext().getRealPath("/") + "img/uploads/event/";
					
					// rutaFoto = ruta anterior + nombre del evento + la fecha +.jpg 
					String rutaFoto = uploads + nombre + "-" + fecha + ".jpg";
					
					//Esto nos servirá para guardar las fotos del evento en un folder en el servidor con nombre "event"
					File folder = new File(uploads);
					
					// Si no existe ese folder
					if (!folder.exists()) {
						
						// creamos ese folder
						folder.mkdirs();
					}
					
					// El tratamiento de abajo es para copiar la imagen introducida por el usuario en la carpeta anterior 
					// con el nombre rutaFoto. Para eso, lee del objeto partFoto todos los datos y los escribe en el fichero. 
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
					
					// foto = ruta de la foto con el nombre de abajo.
					foto = "/Servidor/img/uploads/event/" + nombre + "-" + fecha + ".jpg";
				}
			}
			
			// Si tanto el nombre como la descripción como la fecha, como el email están correctamente insertados 
			// y además el usuario ha elegido un deporte
			if (!nombre.equals("") && !descripcion.equals("") && !fecha.equals("") && 
					!deporte.equals("Selecciona un Deporte") && email!=null) {
				
				// evento = objeto evento con los datos válidos.
				Evento evento = new Evento(nombre,descripcion,fecha,hora,deporte,email,foto);
				
				// intenta insertar el nuevo evento en la base de datos y devuelve true si lo consigue
				boolean realizado = repoEvento.insertarEvento(evento);
				
				// si ha insertado el evento
				if (realizado) {
					
					// Redirige al usuario a su página de perfil
					resp.sendRedirect("profile.html");
					response = "El evento se ha insertado correctamente al deporte";
					
					// Devuelve como ESTADO un 200 (OK)  y el mensaje "El evento se ha ..."
					resp.setStatus(HttpServletResponse.SC_OK);		
				}
				else {
					response = "El evento no se ha podido insertar en la BD";
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			
			// Si hay algún campo / parámetro incorrecto, devuelve como ESTADO un 400, BAD REQUEST y el mensaje "El evento no se ha ..."
			else {
				response = "El evento no se ha podido insertar porque carece de alguno de los parámetros o no se ha elegido un deporte";
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		
		// si se trata de suscribirse a un evento
		if (tipo.equals("Suscribirse")) {
			
			// id = id del evento
			int id = Integer.parseInt(req.getParameter("idEvento"));
			
			// suscrito = true si el usuario ya está suscrito a ese evento
			boolean suscrito = repoEvento.findSuscripcion(id, email);
			
			// si el usuario ya está suscrito
			if (suscrito) {
				
				// le damos de baja de ese evento
				repoEvento.darseDeBajaEvento(id, email);
				
				// Devolvemos como ESTADO 400, BAD REQUEST
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				System.out.println( "El usuario se ha dado del baja en el evento");
			
			// Si NO estaba suscrito 
			}else{
				
				// Intentamos suscribir al usuario en el evento y devolvemos true si se ha conseguido
				boolean suscribir = repoEvento.suscribirseEvento(id, email);
			
				// Si ha podido insertarlo en la base de datos
				if (suscribir) {
					
					// Devolvemos como ESTADO 200, OK.
					resp.setStatus(HttpServletResponse.SC_OK);			
					System.out.println("El usuario se ha suscrito correctamente al evento");
				
				// Si no ha podido insertarlo en la BD
				}else{
					
					//Devolvemos como ESTADO 400, BAD REQUEST
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					System.out.println( "El usuario no se ha podido suscribir al evento");
				}
			}	
			
		}
		
		// Devolvemos la response con el estado que hemos procesado anteriormente. 
		setResponse(response, resp);
	}

	/**
	 * Metodo para borrar un evento de un deporte.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		
		// id = id del evento
		String id = req.getParameter("idEvento");
		
		// Si se borra con éxito el evento de id <id> de la BD, devuelve true.
		boolean realizado = repoEvento.borrarEvento(id);
		
		// Si se ha borrado, 
		if (realizado) {
			
			// ESTADO = 200, OK	
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// redirigimos al usuario a la página de perfil con el mensaje "El evento ..."
			resp.sendRedirect("profile.html");
			response = "El evento se ha borrado correctamente";
		}
		
		// Si no se ha borrado
		else {
			
			// ESTADO = 400 BAD REQUEST
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			// Mandamos el mensaje "El evento ..."
			response = "El evento no se ha podido borrar";
		}
		
		// Enviamos la respuesta (como hacer el return)
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver eventos.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		
		// eventos = lista vacía doblemente enlazada
		List<Evento> eventos = new LinkedList<Evento>();	

		// emailUsuario = coge el email de la sesión del usuario
		String emailusuario = (String)req.getSession().getAttribute("email");
		// tipo de busqueda de deporte
		String tipo = req.getParameter("tipo");

		// Si el tipo de búsqueda es "Buscar"
		if (tipo.equals("Buscar")) {
			
			// name = cogemos lo que el usuario ha puesto en la ¿barra de búsqueda?
			String name = req.getParameter("search");
			
			// eventos = lista de eventos cuyo nombre coincide con el valor del parámetro "search" 
			// (lo que el usuario ha puesto en la barra de búsqueda)
			eventos = repoEvento.listarEventosCreados(name);

			// response = lista de eventos, convertida a Json
			response = gson.toJson(eventos);
			System.out.println("json con eventos buscados de " +name);
			System.out.println(response);

		}

		// Si el tipo es listSportEvents, entonces listamos los eventos de los deportes a los que el usuario está suscrito
		if (tipo.equals("listSportEvents")) {
			
			//deportes = Lista de los deportes a los que el usuario está suscrito
			List<Deporte> deportes = repoDepor.listarDeportesUsuario(emailusuario);
			
			// para cada deporte, al que el usuario está suscrito, añade los eventos de ese deporte a la lista <eventos>
			for (Deporte deporte2 : deportes) {
				eventos.addAll(repoEvento.listarEventosDeporte(deporte2.getNombre(),emailusuario));
			}
			// Pone el estado 200 OK en el response
			resp.setStatus(HttpServletResponse.SC_OK);

			//Ponemos los eventos que ha encontrado en la respuesta
			response = gson.toJson(eventos);
			
			// Si no hay eventos, lo indicamos 
			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos en la busqueda");
				
			// Si hay eventos, lo indicamos
			} else {		
				System.out.println("json con eventos buscados para el deportes suscritos ");
				System.out.println(response);
			}
		}
		
		// Si se recibe el tipo de busqueda "listEventsJustOneSport", lista los eventos de un solo deporte que estará en el 
		// parámetro deporte del request
		if (tipo.equals("listEventsJustOneSport")) {
			
			//Lista los eventos de un solo deporte
			//deporte = el deporte cuyos eventos se quieren mostrar
			String deporte = req.getParameter("deporte");
			
			// eventos = lista de los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <emailusuario>
			eventos = repoEvento.listarEventosDeporte(deporte,emailusuario);			
			
			// Ponemos la response con status 200
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// Ponemos en la response el Json con los eventos encontrados
			response = gson.toJson(eventos);
			
			//Si hay o no eventos, lo indicamos
			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos en la busqueda");
			} else {		
				System.out.println("json con eventos buscados para el deporte " + deporte);
				System.out.println(response);
			}
		}

		// Si se recibe el tipo de busqueda "listUserEvents", lista los eventos a los que está suscrito el usuario con email <email> 
		if (tipo.equals("listUserEvents")) {

			//Lista los eventos del usuario
			// eventos = lista de los eventos a los que está suscrito el usuario con email <email>
			eventos = repoEvento.listarEventosSuscritos(emailusuario);

			// Ponemos la response con status 200
			resp.setStatus(HttpServletResponse.SC_OK);

			// Ponemos response con el json de los eventos
			response = gson.toJson(eventos);			

			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos suscritos de " + emailusuario);
			}else{
				System.out.println("json con eventos del usuario "+ emailusuario);
				System.out.println(response);
			}

		}
		
		// Si se recibe el tipo de busqueda "listEventsCreated", lista los eventos que el usuario ha creado
		if (tipo.equals("listEventsCreated")) {
			
			//Lista los eventos creados por el usuario <emailUsuario>
			eventos = repoEvento.listarMisEventos(emailusuario);

			// Ponemos la response con status 200
			resp.setStatus(HttpServletResponse.SC_OK);

			// Ponemos response con el json de los eventos
			response = gson.toJson(eventos);			
			
			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos creados por " + emailusuario);
			}else{
				System.out.println("json con eventos creados por el usuario "+ emailusuario);
				System.out.println(response);
			}

		}
		
		// Si se recibe el tipo de busqueda "viewEvent", se redirige al usuario a la página del evento con identificador "idEvent". 
		if (tipo.equals("viewEvent")) {
			
			// id = id del evento que se recibe en el parámetro "idEvent" del request. 
			String id = req.getParameter("idEvent");
			
			// ponemos como atributo de la sesión el identificador del evento que queremos mostrar
			req.getSession().setAttribute("idEvent", id);
			
			// redirigimos a la página eventPage.html
			resp.sendRedirect("eventPage.html");
			
			// Ponemos el estado como 200 OK.
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		
		
		// Si se recibe el tipo de busqueda "Event", se muestra en pantalla la información del evento que corresponde
		if (tipo.equals("Event")) {
			
			//Carga la informacion en la pagina del evento
			
			// id = id del evento de la sesión actual
			String id = (String) req.getSession().getAttribute("idEvent");
			
			// evento = buscamos en la BD el evento con id <id>
			Evento evento = repoEvento.findEventById(id);
			
			// enviamos como response el json del evento
			response = gson.toJson(evento);			
			
			// Si el evento ha sido encontrado, enviamos 200 
			if (evento!=null) {
				resp.setStatus(HttpServletResponse.SC_OK);
			}else{
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		}
		
		// enviamos el response
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
	
	/**
	 * Se queda con el nombre del fichero
	 * @param part
	 * @return
	 */
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}
}