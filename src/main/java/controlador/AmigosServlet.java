package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Amigo;
import modelo.RepositorioAmigo;
import modelo.RepositorioUsuario;
import modelo.Usuario;

/**
 * Servlet de obtencion de usuaiors
 */
@WebServlet(value = "/amigos", name = "AmigosServlet")
public class AmigosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioAmigo repoAmigo = new RepositorioAmigo();
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();

	/**
	 * M�todo para a�adir usuarios a la BD a trav�s del cliente.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String usuario = req.getParameter("usuario");
		String amigoSeguido = req.getParameter("amigo");
		String fecha = req.getParameter("fecha");
		Amigo amigo = new Amigo(usuario,amigoSeguido,fecha);
		boolean realizado = repoAmigo.insertarAmigo(amigo);
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El amigo se ha insertado correctamente";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El amigo no se ha podido insertar";
		}
		setResponse(response, resp);
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String usuario = req.getParameter("usuario");
		String amigo = req.getParameter("amigo");
		boolean realizado = repoAmigo.borrarAmigo(usuario,amigo);
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

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		List<Amigo> amigos = null;
		String usuario = req.getParameter("usuario");
		Usuario buscado = repoUsuario.findUsuario(usuario);
		if (usuario == null || buscado == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El usuario no existe";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_OK);
			amigos = repoAmigo.listarAmigos(usuario);
			if (amigos.isEmpty()) {
				response = "El usuario no tiene amigos";
			}
			else {
				response = "Amigos";
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
		System.out.println(resp.getStatus()+ " hola");
		try {
			PrintWriter out = resp.getWriter();
			out.print(response);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}