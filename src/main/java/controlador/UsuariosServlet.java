package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String email = req.getParameter("email");
		String nick = req.getParameter("username");
		String nombre = req.getParameter("nombre");
		String apellidos = req.getParameter("apellidos");
		String contrasena = req.getParameter("contrasena");
		String foto = req.getParameter("foto");
		if (foto == null) {
			//Por defecto sin foto
			foto = "";
		}
		String fecha_nacimiento = req.getParameter("fecha_nacimiento");
		Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
		boolean realizado = repo.insertarUsuario(usuario);
		if (realizado) {
			//Inserta el usuario en la BD
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El usuario se ha insertado correctamente";
			resp.sendRedirect("muro.html");
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El usuario no se ha podido insertar";
			resp.sendRedirect("signup.html");
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver informacion de un usuario.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String tipo = req.getParameter("tipo");
		String email = req.getParameter("emailL");
		String contrasena = req.getParameter("contrasenaL");
		
		if (tipo.equals("Buscar")) {
			
			String name = req.getParameter("search");
			List<Usuario> usuarios = repo.listarUsuariosBusqueda(name);				
			if (usuarios.isEmpty()) {				
				response = "No se encuentran usuarios por: " +name;
				resp.setStatus(HttpServletResponse.SC_OK);
			}else{					
				response= gson.toJson(usuarios);				
				System.out.println("json con usuarios buscados");				
				System.out.println(response);
				resp.setStatus(HttpServletResponse.SC_OK);
			}
		}
		if (tipo.equals("initSesion")) {

			
			System.out.println("email: " + email + " | pass: " + contrasena);
			Usuario usuario = repo.findUsuario(email);

			if (usuario != null && contrasena.equals(usuario.getContrasena())) {
				//El usuario existe y tiene esa contrasena, logeado
				
				response = "El usuario se ha logeado correctamente";
				resp.sendRedirect("muro.html");
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no se ha podido logear";
				resp.sendRedirect("signup.html");
			}
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para actualizar los datos de un usuario.
	 */
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String email = req.getParameter("email");
		String nick = req.getParameter("username");
		String nombre = req.getParameter("nombre");
		String apellidos = req.getParameter("apellidos");
		String contrasena = req.getParameter("contrasena");
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

			Usuario usuario = new Usuario(email,nombre,apellidos,contrasena,fecha_nacimiento,foto,nick);
			boolean realizado = repo.actualizarUsuario(usuario);
			if (buscado!=null && realizado) {
				//Actualiza el usuario
				resp.setStatus(HttpServletResponse.SC_OK);
				response = "El usuario se ha actualizado correctamente";
			}
			else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no se ha podido actualizar";
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
}