package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import modelo.Usuario;
import modelo.RepositorioUsuario;

import com.google.gson.Gson;

/**
 * Servlet relativo a la funcionalidad de usuarios.
 * 		POST /usuarios. Petición para insertar/actualizar un usuario en la base de datos.
 * 		GET /usuarios. Petición para devolver información de un usuario.
 * 		DELETE /usuarios. Petición para dar de baja a un usuario de la base de datos.
 */

@MultipartConfig
@WebServlet(value = "/usuarios", name = "UsuariosServlet")
public class UsuariosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioUsuario repo = new RepositorioUsuario();	//Repositorio de usuarios
	private Gson gson = new Gson();

	/**
	 * Metodo para insertar usuarios a la BD.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String response = null;
		String email = null;
		String nick = null;
		String nombre = null;
		String apellidos = null;
		String contrasena = null;
		String newContrasena = null;
		String confNewContrasena = null;
		String foto = null;
		String fecha_nacimiento = null;

		String tipoPost = req.getParameter("tipoPost");
		System.out.println("tipoPost=: "+tipoPost);
		//Si se pide actualizar el usuario
		if (tipoPost.equals("Actualizar")) {
			//Obtención de los diferentes campos
			email = (String) req.getSession().getAttribute("email");
			if (email == null) {
				email = req.getParameter("email");
			}
			nick = req.getParameter("nick");
			nombre = req.getParameter("nombre");
			apellidos = req.getParameter("apellidos");
			contrasena = req.getParameter("contrasena");
			newContrasena = req.getParameter("newContrasena");
			confNewContrasena = req.getParameter("confirmNewContrasena");
			Part partFoto = req.getPart("foto");
			if (partFoto!=null && !partFoto.equals("")) {
				foto = getFilename(partFoto);
			}
			fecha_nacimiento = req.getParameter("fecha_nacimiento");
			System.out.println("EMAIL BUSCADOOO: " + email);
			Usuario buscado = repo.findUsuario(email);
			//Si la nueva contraseña es no nula y no vacía
			if (newContrasena != null && !(newContrasena.equals(""))) {
				//Si la nueva contraseña es correctamente confirmada
				if (newContrasena.equals(confNewContrasena)) {
					//Si la contraseña coincide con la del usuario
					if (contrasena.equals(buscado.getContrasena())) {
						//Se actualiza la contraseña
						contrasena = newContrasena;
					}
				}
			}

			if (buscado != null) {
				String uploads = getServletContext().getRealPath("/") + "img/uploads/profile/";
				String rutaFoto = uploads + buscado.getNick() + ".jpg";
				File folder = new File(uploads);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				if (nick.equals("")) {
					nick = buscado.getNick();
				}
				if (nombre.equals("")) {
					nombre = buscado.getNombre();
				}
				if (apellidos.equals("")) {
					apellidos = buscado.getApellidos();
				}
				if (contrasena.equals("")) {
					contrasena = buscado.getContrasena();
				}
				if (fecha_nacimiento.equals("")) {
					fecha_nacimiento = buscado.getFecha_nacimiento();
				}
				if (foto.equals("")) {
					foto = buscado.getFoto();
				}
				else {
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
					foto = "/Servidor/img/uploads/profile/" + buscado.getNick() + ".jpg";
				}
				//Se actualiza el usuario
				Usuario usuario = new Usuario(email, nombre, apellidos, contrasena, fecha_nacimiento, foto,nick);
				boolean realizado = repo.actualizarUsuario(usuario);
				//Si la operación se ha ralizado con éxito
				if (buscado != null && realizado) {
					resp.sendRedirect("profile.html");
					response = "El usuario se ha actualizado correctamente";
					//Se devuelve código 200 (éxito)
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				else {
					response = "El usuario no se ha podido actualizar";
					//Se devuelve código 400 (petición no exitosa)
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
		}
		//Si se pide registar un usuario
		if (tipoPost.equals("Registro")) {
			//Obtención de los diferentes campos
			email = req.getParameter("emailR");
			nick = req.getParameter("username");
			nombre = req.getParameter("nombre");
			apellidos = req.getParameter("apellidos");
			contrasena = req.getParameter("contrasenaR");
			foto = req.getParameter("foto");
			if (foto == null) {
				// Por defecto sin foto
				foto = "/Servidor/img/profile.jpg";
			}
			fecha_nacimiento = req.getParameter("fecha_nacimiento");

			if (email != "" && nick != "" && nombre != "" && apellidos != "" && contrasena != ""
					&& fecha_nacimiento != "") {
				Usuario usuario = new Usuario(email, nombre, apellidos, contrasena, fecha_nacimiento, foto, nick);
				//Se inserta el usuario en la base de datos
				boolean realizado = repo.insertarUsuario(usuario);
				//Si la operación se ha ralizado con éxito
				if (realizado) {
					// Inserta el usuario en la BD
					response = "El usuario se ha insertado correctamente";
					HttpSession session = req.getSession();
					createSession(session, usuario);
					resp.sendRedirect("muro.html");
					//Se devuelve código 200 (éxito)
					resp.setStatus(HttpServletResponse.SC_OK);
				}

			} else {
				//Se devuelve código 400 (petición no exitosa)
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no se ha podido insertar";
				resp.sendRedirect("signup.html");
			}
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver informacion de un usuario.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String response = null;
		String tipo = req.getParameter("tipo");
		System.out.println("tipo= " + tipo);
		//Si se pide iniciar sesión
		if (tipo.equals("initSesion")) {
			//Se obtienen los campos correspondientes
			String email = req.getParameter("emailL");
			String contrasena = req.getParameter("contrasenaL");
			
			System.out.println("email: " + email + " | pass: " + contrasena);
			Usuario usuario = repo.findUsuario(email);

			if (usuario != null && contrasena.equals(usuario.getContrasena())) {
				// El usuario existe y tiene esa contrasena, logeado
				HttpSession session = req.getSession();
				createSession(session, usuario);
				response = "El usuario se ha logeado correctamente";
				resp.sendRedirect("muro.html");
				//Se devuelve código 200 (éxito)
				resp.setStatus(HttpServletResponse.SC_OK);
				System.out.println(response);
			} else {				
				response = "El usuario no se ha podido logear";
				System.out.println(response);
				resp.sendRedirect("signup.html");
				//Se devuelve código 400 (petición no exitosa)
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			}
			setResponse(response, resp);

		} else {
			if (SessionisActive(req, resp)) {

				String email = (String) req.getSession().getAttribute("email");
				if (email == null) {
					email = req.getParameter("email");
				}
				if (tipo.equals("infosesion")) {
					Usuario u = repo.findUsuario(email);
					response = gson.toJson(u);
					System.out.println("json con sesion: " + response);
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				if (tipo.equals("ListAvailableUsers")) {
					List<Usuario> usuarios = repo.ListAvailableUsers(email);
					if (usuarios.isEmpty()) {
						response = "No se encuentran usuarios";
						//Se devuelve código 200 (éxito)
						resp.setStatus(HttpServletResponse.SC_OK);
					} else {
						response = gson.toJson(usuarios);
						System.out.println("json con los usuarios sin seguir aÃºn");
						System.out.println(response);
						//Se devuelve código 200 (éxito)
						resp.setStatus(HttpServletResponse.SC_OK);
					}
				}
				//Si se pide buscar un usuario
				if (tipo.equals("Buscar")) {
					String name = req.getParameter("search");
					List<Usuario> usuarios = repo.listarUsuariosBusqueda(name);
					//Si la lista de usuarios obtenida es no vacía
					if (usuarios.isEmpty()) {
						response = "No se encuentran usuarios por: " + name;
						//Se devuelve código 200 (éxito)
						resp.setStatus(HttpServletResponse.SC_OK);
					} else {
						response = gson.toJson(usuarios);
						System.out.println("json con usuarios buscados");
						System.out.println(response);
						//Se devuelve código 200 (éxito)
						resp.setStatus(HttpServletResponse.SC_OK);
					}
				}
				//Si se pide cerrar sesión
				if (tipo.equals("closeSesion")) {
					req.getSession().invalidate();
					//Se devuelve código 200 (éxito)
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				setResponse(response, resp);
			}
		}

	}
	
	/**
	 * @return [true] si la sesión está activa, [false] en caso contrario
	 */
	private boolean SessionisActive(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getSession(false) != null) {
			System.out.println("si existe sesion");
			return true;
		} else {
			System.out.println("no hay sesion iniciada");
			String response = "";
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("signup.html");
			setResponse(response, resp);
			return false;
		}	

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
	 * 
	 * Establece los atributos de la sesión
	 * @param session
	 * @param usuario
	 */
	private void createSession(HttpSession session, Usuario usuario){
		//session.setAttribute("usuario",usuario);
		session.setAttribute("email", usuario.getEmail());
		session.setAttribute("nick", usuario.getNick());
		session.setAttribute("nombre", usuario.getNombre());
		session.setAttribute("apellidos", usuario.getApellidos());
		session.setAttribute("fecha", usuario.getFecha_nacimiento());
		session.setAttribute("foto", usuario.getFoto());
	}

	/**
	 * 
	 * @param part
	 * @return String con el nombre del fichero
	 */
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

	/**
	 * @return [true] si la sesión está autentificada, [false] en caso contrario
	 */
	public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
		try {
			return SessionisActive(request, response);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}