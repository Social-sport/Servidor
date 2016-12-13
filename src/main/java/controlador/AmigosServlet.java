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
import modelo.Usuario;

/**
 * Servlet de obtencion de amigos
 */
@WebServlet(value = "/amigos", name = "AmigosServlet")
public class AmigosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioAmigo repoAmigo = new RepositorioAmigo();
	private static RepositorioNotificacion repoNotificacion = new RepositorioNotificacion();
	private Gson gson = new Gson();

	/**
	 * Metodo para seguir a un usuario.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = (HttpSession) req.getSession(); 
		String response = null;		
		String usuario = null;		
		String amigoSeguido = req.getParameter("emailAmigo");
		System.out.println("amigoSeguido: "+amigoSeguido);
		Date fech = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = format.format(fech);
        
        if (req.getParameter("email")!=null) {
			usuario = req.getParameter("email");
		}else{
			usuario = (String)session.getAttribute("email");
		}
        System.out.println("usuario: "+usuario);
		Amigo amigo = new Amigo(usuario,amigoSeguido,fecha);
		boolean realizado = repoAmigo.insertarAmigo(amigo);
		//inserta un amigo en la BD
		if (realizado) {
			//Notificamos
			
			repoNotificacion.notificar(usuario,amigoSeguido,"nombreSeguidor","foto","Seguidor","nombreSeguidor");
			response = "El amigo se ha insertado correctamente";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.html");
		}
		else {
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
		String tipo = req.getParameter("tipoRelacion");

		if (tipo.equals("test")) {

			tipo = req.getParameter("list");
			String emailt = req.getParameter("usuario");
			
			if (tipo.equals("listSeguidos")) {
				
				listSeguidos = repoAmigo.listarSeguidos(emailt);
				response = gson.toJson(listSeguidos);
				System.out.println("json lleno con Seguidos: " + response);
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			if (tipo.equals("listSeguidores")) {

				listSeguidores = repoAmigo.listarSeguidores(emailt);
				response = gson.toJson(listSeguidores);
				System.out.println("json lleno con Seguidores: " + response);
				resp.setStatus(HttpServletResponse.SC_OK);
			}
		} else {
			
			String email = req.getSession().getAttribute("email").toString();
			System.out.println("usuario a buscar seguidor/es: " + email);
			
			if (tipo.equals("listSeguidos")) {

				listSeguidos = repoAmigo.listarSeguidos(email);
				response = gson.toJson(listSeguidos);
				System.out.println("json lleno con Seguidos: " + response);
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			if (tipo.equals("listSeguidores")) {

				listSeguidores = repoAmigo.listarSeguidores(email);
				response = gson.toJson(listSeguidores);
				System.out.println("json lleno con Seguidores: " + response);
				resp.setStatus(HttpServletResponse.SC_OK);
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
		boolean realizado = repoAmigo.borrarAmigo(usuario,amigo);
		//elimina el amigo en la BD
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El amigo se ha borrado correctamente";
		}
		else {
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