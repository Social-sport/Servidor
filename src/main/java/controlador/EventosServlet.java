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
 * Servlet relativo a la funcionalidad de creación de eventos.
 * 		POST /eventos. Petición para crear/actualizar eventos.
 * 		GET /eventos. Petición que devuelve una lista con los amigos de un usuario dado su email.
 * 		DELETE /eventos. Petición que elimina eventos de la base de datos.
 */

@MultipartConfig
@WebServlet(value = "/eventos", name = "EventosServlet")
public class EventosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static RepositorioEvento repoEvento = new RepositorioEvento();	//Repositorio de eventos
	private static RepositorioDeporte repoDepor = new RepositorioDeporte();	//Repositorio de deportes
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
		
		// Se guarda la foto que introduce el usuarios
		Part partFoto = null;
		
		// Se guarda el email del usuario
		String email = (String)req.getSession().getAttribute("email");
		if (email == null) {
			email = req.getParameter("email");
		}
		// Se guarda el tipo de evento
		String tipo = req.getParameter("tipoPostEvent");
		System.out.println("tipoPost=: "+tipo);
		System.out.println("email=: "+email);
		
		// Si se trata de crear un evento
		if (tipo.equals("Crear")) {
			//Obtención de datos relativos al evento
			nombre = req.getParameter("nombre");
			descripcion = req.getParameter("descripcion");
			fecha = req.getParameter("fecha");
			hora = req.getParameter("hora");
			deporte = req.getParameter("deporte");
			partFoto = req.getPart("foto");
			
			// Si el usuario ha insertado una foto
			if (partFoto!=null && !partFoto.equals("")) {
				
				// Se obtiene el nombre de la foto introducida por el usuario
				foto = getFilename(partFoto);
				
				// Si el nombre de la foto es vació
				if (foto.equals("")) {
					
					// Se pone como una foto por defecto, que se guarda internamente en el servidor
					foto = "/Servidor/img/event.jpg";
				}
				
				// Si el nombre de la foto no es vacío
				else {
					
					// Se coge el path del servidor y se le adjunta /img/uploads/event/
					String uploads = getServletContext().getRealPath("/") + "img/uploads/event/";
					
					// rutaFoto = ruta anterior + nombre del evento + la fecha +.jpg 
					String rutaFoto = uploads + nombre + "-" + fecha + ".jpg";
					
					//Esto servirá para guardar las fotos del evento en un folder en el servidor con nombre "event"
					File folder = new File(uploads);
					
					// Si no existe ese folder
					if (!folder.exists()) {
						// se crea
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
					
					// Ruta de la foto con el nombre de abajo.
					foto = "/Servidor/img/uploads/event/" + nombre + "-" + fecha + ".jpg";
				}
			}
			
			// Si tanto el nombre como la descripción como la fecha, como el email están correctamente insertados 
			// y además el usuario ha elegido un deporte
			if (!nombre.equals("") && !descripcion.equals("") && !fecha.equals("") && 
					!deporte.equals("Selecciona un Deporte") && email!=null) {
				
				// Objeto evento con los datos válidos
				Evento evento = new Evento(nombre,descripcion,fecha,hora,deporte,email,foto);
				
				// Intenta insertar el nuevo evento en la base de datos y devuelve true si lo consigue
				boolean realizado = repoEvento.insertarEvento(evento);
				
				// Si se ha insertado el evento
				if (realizado) {
					
					// Redirige al usuario a su página de perfil
					resp.sendRedirect("profile.html");
					response = "El evento se ha insertado correctamente al deporte";
					
					// Devuelve como ESTADO un 200 (OK)  y el mensaje "El evento se ha ..."
					resp.setStatus(HttpServletResponse.SC_OK);		
				}
				else {
					// Devuelve como ESTADO un 400 (BAD_REQUEST)
					response = "El evento no se ha podido insertar en la BD";
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			
			// Si hay algún campo o parámetro incorrecto, devuelve como ESTADO un 400, BAD REQUEST y el mensaje "El evento no se ha ..."
			else {
				response = "El evento no se ha podido insertar porque carece de alguno de los parÃ¡metros o no se ha elegido un deporte";
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		
		// Si se trata de suscribirse a un evento
		if (tipo.equals("Suscribirse")) {
			
			// Id del evento
			int id = Integer.parseInt(req.getParameter("idEvento"));
			
			// suscrito = true si el usuario ya está suscrito a ese evento
			boolean suscrito = repoEvento.findSuscripcion(id, email);
			
			// si el usuario ya está suscrito
			if (suscrito) {
				// Se le da de baja de ese evento
				repoEvento.darseDeBajaEvento(id, email);
				resp.setStatus(HttpServletResponse.SC_OK);
				response = gson.toJson("Suscribirse");
				System.out.println(response +" Se ha dado de bajo del evento");
				
			// Si NO estaba suscrito
			} else {
				
				// Se intenta suscribir al usuario en el evento y devolvemos true si se ha conseguido
				boolean suscribir = repoEvento.suscribirseEvento(id, email);
			
				// Si ha podido insertarlo en la base de datos
				if (suscribir) {
					// Devolvemos como ESTADO 200, OK.
					resp.setStatus(HttpServletResponse.SC_OK);	
					response = gson.toJson("Salir");
					System.out.println("El usuario se ha suscrito correctamente al evento");
				
				// Si no ha podido insertarlo en la BD
				} else {
					//Devolvemos como ESTADO 400, BAD REQUEST
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response = "El usuario no se ha podido suscribir al evento";
					System.out.println( "El usuario no se ha podido suscribir al evento");
				}
			}	
			
		}
		//Si se pide actualizar el evento
		if (tipo.equals("Actualizar")) {
			String id = req.getParameter("idEvento");
			nombre = req.getParameter("nombre");
			descripcion = req.getParameter("descripcion");
			fecha = req.getParameter("fecha");
			hora = req.getParameter("hora");
			deporte = req.getParameter("deporte");
			partFoto = req.getPart("foto");
			if (partFoto!=null && !partFoto.equals("")) {
				foto = getFilename(partFoto);
			}
			//Se busca el evento según la <id>
			Evento evento = repoEvento.findEventById(id, email);
			//Si el evento no es nulo
			if (evento != null) {
				String uploads = getServletContext().getRealPath("/") + "img/uploads/event/";
				String rutaFoto = uploads + nombre + "-" + fecha + ".jpg";
				File folder = new File(uploads);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				if (nombre.equals("")) {
					nombre = evento.getNombre();
				}
				if (descripcion.equals("")) {
					descripcion = evento.getDescripcion();
				}
				if (fecha.equals("")) {
					fecha = evento.getFecha();
				}
				if (hora.equals("")) {
					hora = evento.getHora();
				}
				if (deporte.equals("")) {
					deporte = evento.getDeporte();
				}
				if (foto.equals("")) {
					foto = evento.getFoto();
				} else {
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
				//Se crea el nuevo evento con los datos obtenidos
				Evento newEvento = new Evento(Integer.parseInt(id), nombre, 
						descripcion, fecha, hora, deporte, email, foto);
				//Se actualiza el evento
				boolean update = repoEvento.actualizarEvento(newEvento);
				//Si operación exitosa
				if (update) {
					resp.sendRedirect("eventPage.html");
					response = "El evento se ha actualizado correctamente";
					//Se devuelve código 200 (éxito)
					resp.setStatus(HttpServletResponse.SC_OK);
				} else {
					//Sino, se devuelve código 400 (petición no exitosa)
					response = "El evento no se ha podido actualizar";
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
		}
		
		//Si se pide eliminar evento
		if (tipo.equals("Eliminar")) {
			String id = (String) req.getParameter("idEvento");
			//Borra el evento del deporte en la BD
			boolean realizado = repoEvento.borrarEvento(id);
			//Si operación exitosa
			if (realizado) {
				resp.sendRedirect("muro.html");
				response = gson.toJson("El evento se ha borrado correctamente");
				//Se devuelve código 200 (éxito)
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			else {
				//Sino, se devuelve código 400 (petición no exitosa)
				response = gson.toJson("El evento no se ha podido borrar");
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		//Se pone el response con los parámetros anteriores.
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
		
		// Si se borra con Ã©xito el evento de id <id> de la BD, devuelve true.
		boolean realizado = repoEvento.borrarEvento(id);
		
		// Si se ha borrado, 
		if (realizado) {
			
			// ESTADO = 200, OK	
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// redirigimos al usuario a la pÃ¡gina de perfil con el mensaje "El evento ..."
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
		
		// Lista doblemente enlazada
		List<Evento> eventos = new LinkedList<Evento>();	

		// Email de la sesión del usuario
		String emailusuario = (String)req.getSession().getAttribute("email");
		if (emailusuario == null) {
			emailusuario = req.getParameter("email");
		}
		// Tipo de búsqueda de deporte
		String tipo = req.getParameter("tipo");

		// Si se pide 'Buscar' se devuelve una lista con los eventos creados
		if (tipo.equals("Buscar")) {
			
			// Se obtiene el contenido del campo de texto de la barra de búsqueda
			String name = req.getParameter("search");
			
			// Lista de eventos cuyo nombre coincide con el valor del parámetro "search" 
			eventos = repoEvento.listarEventosCreados(name);

			// Lista de eventos, convertida a Json
			response = gson.toJson(eventos);
			System.out.println("json con eventos buscados de " +name);
			System.out.println(response);

		}
		
		// Si el tipo es listSportEvents, entonces se listan los eventos de los deportes a los que el usuario está suscrito
		if (tipo.equals("listSportEvents")) {
			
			//Lista de los deportes a los que el usuario está suscrito
			List<Deporte> deportes = repoDepor.listarDeportesUsuario(emailusuario);
			
			//Para cada deorte al que el usuario está suscrito, añade los eventos de ese deporte a la lista <eventos>
			for (Deporte deporte2 : deportes) {
				eventos.addAll(repoEvento.listarEventosDeporte(deporte2.getNombre(),emailusuario));
			}
			// Devuelve el estado 200 OK en el response
			resp.setStatus(HttpServletResponse.SC_OK);

			//Se ponen los eventos que ha encontrado en la respuesta
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
			
			// Lista los eventos de un solo deporte
			// Deporte cuyos eventos se quieren mostrar
			String deporte = req.getParameter("deporte");
			
			// Lista de los eventos que son del deporte <deporte> y NO han sido creados por el usuario con el email <emailusuario>
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

			// Lista los eventos del usuario
			// Lista de los eventos a los que está suscrito el usuario con email <email>
			eventos = repoEvento.listarEventosSuscritos(emailusuario);

			// Ponemos la response con status 200
			resp.setStatus(HttpServletResponse.SC_OK);

			// Ponemos response con el json de los eventos
			response = gson.toJson(eventos);			

			if (eventos.isEmpty()) {
				System.out.println("No se encontraron eventos suscritos de " + emailusuario);
			} else {
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
			} else {
				System.out.println("json con eventos creados por el usuario "+ emailusuario);
				System.out.println(response);
			}

		}
		
		// Si se recibe el tipo de busqueda "viewEvent", se redirige al usuario a la página del evento con identificador "idEvent". 
		if (tipo.equals("viewEvent")) {
			
			// Id del evento que se recibe en el parámetro "idEvent" del request. 
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
			if (id == null) {
				id = req.getParameter("idEvent");
			}
			String email = (String) req.getSession().getAttribute("email");
			if (email == null) {
				email = req.getParameter("email");
			}
			
			// Buscamos en la BD el evento con id <id>
			Evento evento = repoEvento.findEventById(id, email);

			// enviamos como response el json del evento
			response = gson.toJson(evento);			
			
			// Si el evento ha sido encontrado, enviamos 200 
			if (evento!=null) {
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		}
		// Si se recibe el tipo de busqueda "Verificar", se comprueba si el usuario está suscrito.
		if (tipo.equals("Verificar")) {
			int id = Integer.parseInt((String) req.getSession().getAttribute("idEvent"));
			if ((String) req.getSession().getAttribute("idEvent") == null) {
				id = Integer.parseInt(req.getParameter("idEvent"));
			}
			boolean suscrito = repoEvento.findSuscripcion(id, emailusuario);
			if (suscrito) {
				response = gson.toJson("Salir");
			} else {
				response = gson.toJson("Suscribirse");
			}
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
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
	 * @param part
	 * @return String con el nombre del fichero
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