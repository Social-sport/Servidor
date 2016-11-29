package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;
import modelo.RepositorioUsuario;

import com.google.gson.Gson;

/**
 * Servlet de obtencion de usuaiors
 */
@WebServlet(value = "/usuarios", name = "UsuariosServlet")
public class UsuariosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioUsuario repo = new RepositorioUsuario();
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
		
		if (tipoPost.equals("Actualizar")) {
			
			email = req.getSession().getAttribute("email").toString();
			nick = req.getParameter("nick");
			nombre = req.getParameter("nombre");
			apellidos = req.getParameter("apellidos");
			contrasena = req.getParameter("contrasena");
			newContrasena = req.getParameter("newContrasena");
			confNewContrasena = req.getParameter("confirmNewContrasena");
			foto = req.getParameter("foto");
			fecha_nacimiento = req.getParameter("fecha_nacimiento");
			Usuario buscado = repo.findUsuario(email);

			if (!(newContrasena.equals(""))) {
				if (newContrasena.equals(confNewContrasena)) {
					if (contrasena.equals(buscado.getContrasena())) {
						contrasena = newContrasena;
					}
				}
			}
			if (buscado!=null) {
				if (nick.equals("")) {
					nick = buscado.getNick();
				}
				if (nombre.equals("")) {
					nombre = buscado.getNombre();
				}
				if (apellidos.equals("")) {
					apellidos = buscado.getApellidos();
				}
				if (contrasena == "") {
					contrasena = buscado.getContrasena();
				}
				if (foto.equals("")) {
					foto = buscado.getFoto();
				}
				if (fecha_nacimiento.equals("")) {
					fecha_nacimiento = buscado.getFecha_nacimiento();
				}

				Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
				boolean realizado = repo.actualizarUsuario(usuario);
				if (buscado!=null && realizado) {
					//Actualiza el usuario
					resp.sendRedirect("profile.html");
					response = "El usuario se ha actualizado correctamente";
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				else {
					response = "El usuario no se ha podido actualizar";
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
		}
		
		if (tipoPost.equals("Registro")) {
			email = req.getParameter("emailR");
			nick = req.getParameter("username");
			nombre = req.getParameter("nombre");
			apellidos = req.getParameter("apellidos");
			contrasena = req.getParameter("contrasenaR");
			foto = req.getParameter("foto");
			if (foto == null) {
				//Por defecto sin foto
				foto = "/Servidor/img/profile.jpg";
			}
			fecha_nacimiento = req.getParameter("fecha_nacimiento");
			
			if (email != "" && nick != "" && nombre != "" && apellidos != "" && contrasena != "" && fecha_nacimiento != "") {
				Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
				boolean realizado = repo.insertarUsuario(usuario);
				
				if (realizado) {
					//Inserta el usuario en la BD
					response = "El usuario se ha insertado correctamente";
					HttpSession session = req.getSession(); 
					createSession(session, usuario);
					resp.sendRedirect("muro.html");
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				else {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response = "El usuario no se ha podido insertar";
					resp.sendRedirect("signup.html");
				}
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
		System.out.println("tipo=: " + tipo);

		if (tipo.equals("initSesion")) {

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
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no se ha podido logear";
				resp.sendRedirect("signup.html");
			}
			setResponse(response, resp);

		} else {
			if (SessionisActive(req, resp)) {

				String email = (String) req.getSession().getAttribute("email");
				
				if (tipo.equals("infosesion")) {
					Usuario u = repo.findUsuario(email);
					response = gson.toJson(u);
					System.out.println("json con sesion: " + response);
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				if (tipo.equals("ListEveryUsers")) {
					List<Usuario> usuarios = repo.ListEveryUsers(email);
					if (usuarios.isEmpty()) {
						response = "No se encuentran usuarios";
						resp.setStatus(HttpServletResponse.SC_OK);
					} else {
						response = gson.toJson(usuarios);
						System.out.println("json con todos los usuarios");
						System.out.println(response);
						resp.setStatus(HttpServletResponse.SC_OK);
					}
				}
				if (tipo.equals("Buscar")) {
					String name = req.getParameter("search");
					List<Usuario> usuarios = repo.listarUsuariosBusqueda(name);
					if (usuarios.isEmpty()) {
						response = "No se encuentran usuarios por: " + name;
						resp.setStatus(HttpServletResponse.SC_OK);
					} else {
						response = gson.toJson(usuarios);
						System.out.println("json con usuarios buscados");
						System.out.println(response);
						resp.setStatus(HttpServletResponse.SC_OK);
					}
				}
				if (tipo.equals("closeSesion")) {
					req.getSession().invalidate();
					resp.setStatus(HttpServletResponse.SC_OK);
				}
				setResponse(response, resp);
			}
		}

	}

	private boolean SessionisActive(HttpServletRequest req, HttpServletResponse resp) {
		
		if (req.getSession(false) != null) {
			System.out.println("si existe sesion");
			return true;
		}else{
			System.out.println("no hay sesion iniciada");
			String response = "";
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
	
	private void createSession(HttpSession session, Usuario usuario){
		//session.setAttribute("usuario",usuario);
		session.setAttribute("email", usuario.getEmail());
		session.setAttribute("nick", usuario.getNick());
		session.setAttribute("nombre", usuario.getNombre());
		session.setAttribute("apellidos", usuario.getApellidos());
		session.setAttribute("fecha", usuario.getFecha_nacimiento());
		session.setAttribute("foto", usuario.getFoto());
	}
}