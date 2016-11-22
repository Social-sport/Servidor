package controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;
import modelo.RepositorioUsuario;


/**
 * Servlet de obtencion de usuaiors
 */
@WebServlet(value = "/usuarios", name = "UsuariosServlet")
public class UsuariosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioUsuario repo = new RepositorioUsuario();

	/**
	 * Método para añadir usuarios a la BD a través del cliente.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String tipo = req.getParameter("tipo");
		
		if (tipo.equals("registro")) {
			if (session.getAttribute("email") != null) {
				resp.sendRedirect("muro.jsp");
			}
			else {
				String response = null;
				String email = req.getParameter("emailR");
				String nick = req.getParameter("username");
				String nombre = req.getParameter("nombre");
				String apellidos = req.getParameter("apellidos");
				String contrasena = req.getParameter("contrasenaR");
				String foto = req.getParameter("foto");
				if (foto == null) {
					foto = "";
				}
				String fecha_nacimiento = req.getParameter("fecha_nacimiento");
				Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
				boolean realizado = repo.insertarUsuario(usuario);
				if (realizado) {
					resp.setStatus(HttpServletResponse.SC_OK);
					createSession(session, usuario);
					response = "El usuario se ha insertado correctamente";
					resp.sendRedirect("muro.jsp");
				}
				else {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response = "El usuario no se ha podido insertar";
					resp.sendRedirect("signup.jsp");
				}
				setResponse(response, resp);
			}
		}
		if (tipo.equals("actualizar")){
			if (session.getAttribute("email") != null) {
				String response = null;
				String email = session.getAttribute("email").toString();
				String nick = req.getParameter("username");
				String nombre = req.getParameter("nombre");
				String apellidos = req.getParameter("apellidos");
				String contrasena = req.getParameter("contrasena");
				String newContrasena = req.getParameter("newContrasena");
				String confNewContrasena = req.getParameter("confirmNewContrasena");
				
				if (newContrasena != null){
					if (newContrasena.equals(confNewContrasena)){
						contrasena = confNewContrasena;
					}
				}
				
				String foto = req.getParameter("foto");
				String fecha_nacimiento = req.getParameter("fecha_nacimiento");
				Usuario buscado = repo.findUsuario(email);
				if (buscado!=null) {
					if (nick == null) {
						nombre = buscado.getNick();
					}
					if (nombre == null) {
						nombre = buscado.getNombre();
					}
					if (apellidos == null) {
						apellidos = buscado.getApellidos();
					}
					if (contrasena == null) {
						contrasena = buscado.getContrasena();
					}
					if (foto == null) {
						foto = buscado.getFoto();
					}
					if (fecha_nacimiento == null) {
						fecha_nacimiento = buscado.getFecha_nacimiento();
					}
				}
				Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
				boolean realizado = repo.actualizarUsuario(usuario);
				if (buscado!=null && realizado) {
					resp.setStatus(HttpServletResponse.SC_OK);
					createSession(session, usuario);
					response = "El usuario se ha actualizado correctamente";
					resp.sendRedirect("profile.jsp");
				}
				else {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response = "El usuario no se ha podido actualizar";
					resp.sendRedirect("signup.jsp");
				}
				setResponse(response, resp);
			}
			else {
				resp.sendRedirect("signup.jsp");
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("email") != null) {
			session.invalidate();
			resp.sendRedirect("signup.jsp");
		}
		else {
			String response = null;
			String email = req.getParameter("emailL");
			String contrasena = req.getParameter("contrasenaL");
			Usuario usuario = repo.findUsuario(email);
			if (usuario != null && contrasena.equals(usuario.getContrasena())) {
				resp.setStatus(HttpServletResponse.SC_OK);
				createSession(session, usuario);
				response = "El usuario se ha logeado correctamente";
				resp.sendRedirect("muro.jsp");
			}
			else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no se ha podido logear";
				resp.sendRedirect("signup.jsp");
			}
			setResponse(response, resp);
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
		session.setAttribute("email", usuario.getEmail());
		session.setAttribute("nick", usuario.getNick());
		session.setAttribute("nombre", usuario.getNombre());
		session.setAttribute("apellidos", usuario.getApellidos());
		session.setAttribute("fecha", usuario.getFecha_nacimiento());
		session.setAttribute("foto", usuario.getFoto());
	}
}