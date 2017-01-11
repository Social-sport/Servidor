package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import modelo.Amigo;
import modelo.RepositorioAmigo;
import modelo.RepositorioNotificacion;
import modelo.RepositorioUsuario;
import modelo.Usuario;

/**
 * Servlet relativo a la funcionalidad de amigos.
 * 		POST /amigos. Petición para añadir (seguir) amigos. Se notifica al usuario .
 * 		GET /amigos. Petición que devuelve una lista con los amigos de un usuario dado su email.
 * 		DELETE /amigos. Petición que elimina un amigo de la base de datos.
 */
@WebServlet(value = "/amigos", name = "AmigosServlet")
public class AmigosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioAmigo repoAmigo = new RepositorioAmigo();	//Repositorio de amigos
	private static RepositorioNotificacion repoNotificacion = new RepositorioNotificacion();	//Repositorio de notificaciones
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();	//Repositorio de usuarios
	private Gson gson = new Gson();

	/**
	 * Metodo para seguir a un usuario.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = (HttpSession) req.getSession(); 
		String response = null;		
		String emailSeguidor = null;		
		String amigoSeguido = req.getParameter("emailAmigo");
		System.out.println("amigoSeguido: "+ amigoSeguido);
		//Fecha en la que el amigo se ha añadido
		Date fech = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = format.format(fech);
      
        if (req.getParameter("email") != null) {
			emailSeguidor = req.getParameter("email");
		} else {
			emailSeguidor = (String) session.getAttribute("email");
		}
        System.out.println("usuario: " + emailSeguidor);
        
        //Se inserta el amigo en la base de Datos
		Amigo amigo = new Amigo(emailSeguidor, amigoSeguido, fecha);
		boolean realizado = repoAmigo.insertarAmigo(amigo);
		//Si la operación se ha ralizado con éxito
		if (realizado) {
			//Se notifica al usuario
			Usuario seguidor = repoUsuario.findUsuario(emailSeguidor);
			repoNotificacion.notificar(emailSeguidor, amigoSeguido, seguidor.getNick(),
					seguidor.getFoto(), "Seguidor", seguidor.getNick(), 0);
			//Se devuelve código 200 (éxito)
			response = "El amigo se ha insertado correctamente";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.html");
		}
		else {
			//Se devuelve código 400 (petición no exitosa)
			response = "El amigo no se ha podido insertar";
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("muro.html");
		}
		setResponse(response, resp);
	}
	
	/**
	 * Metodo para devolver los amigos de un usuario.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String response = null;		
		List<Usuario> listSeguidores = new LinkedList<>();
		List<Usuario> listSeguidos = new LinkedList<>();
		String email = (String) req.getSession().getAttribute("email");		
		String tipo = req.getParameter("tipoRelacion");
		//Si se pide la lista de usuarios seguidos
		if (tipo.equals("listSeguidos")) {
			System.out.println("usuario a buscar seguidos: " + email);
			listSeguidos = repoAmigo.listarSeguidos(email);
			response = gson.toJson(listSeguidos);
			System.out.println("json lleno con Seguidos: " + response);
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		//Si se pide la lista de seguidores
		if (tipo.equals("listSeguidores")) {
			System.out.println("usuario a buscar seguidores: " + email);
			listSeguidores = repoAmigo.listarSeguidores(email);
			response = gson.toJson(listSeguidores);
			System.out.println("json lleno con Seguidores: " + response);
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		if (tipo.equals("Eliminar")) {
			String amigo = req.getParameter("amigoEliminar");
			if (repoAmigo.borrarAmigo(email, amigo)) {
				resp.setStatus(HttpServletResponse.SC_OK);
				response = "Amigo borrado correctamente";
				resp.sendRedirect("profile.html");
			} else {
				//Se devuelve código 400 (petición no exitosa)
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.sendRedirect("profile.html");
				response = "No se pudo borrar el amigo";
			}
		}
		
		if (tipo.equals("Seguir")) {
			String amigoNuevo = req.getParameter("amigoSeguir");
			Date fech = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	        String fecha = format.format(fech);
	        //Se inserta el amigo en la base de Datos
			Amigo amigo = new Amigo(email,amigoNuevo,fecha);
			boolean realizado = repoAmigo.insertarAmigo(amigo);
			//Si la operación se ha ralizado con éxito
			if (realizado) {
				//Se notifica al usuario
				Usuario seguidor = repoUsuario.findUsuario(email);
				repoNotificacion.notificar(email, amigoNuevo, seguidor.getNick(), seguidor.getFoto(),
						"Seguidor", seguidor.getNick(), 0);
				
				response = "El amigo se ha insertado correctamente";
				//Se devuelve código 200 (éxito)
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.sendRedirect("profile.html");
			}
			else {
				//Se devuelve código 400 (petición no exitosa)
				response = "El amigo no se ha podido insertar";
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.sendRedirect("profile.html");
			}
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para borrar un amigo del usuario.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String usuario = req.getParameter("usuario");
		String amigo = req.getParameter("amigo");
		//Se elimina el amigo de la Base de Datos
		boolean realizado = repoAmigo.borrarAmigo(usuario, amigo);
		//Si la operación se ha realizado con éxito
		if (realizado) {
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El amigo se ha borrado correctamente";
		}
		//Si la operación ha fallado
		else {
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El amigo no se ha podido borrar";
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