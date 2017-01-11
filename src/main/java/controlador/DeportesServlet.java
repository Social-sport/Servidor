package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Deporte;
import modelo.RepositorioDeporte;

import com.google.gson.Gson;

/**
 * Servlet relativo a la funcionalidad de deportes.
 * 		POST /deportes. Petición para suscribir un usuario a un deporte.
 * 		GET /deportes. Petición para listar los deportes de la base de datos.
 * 		DELETE /deportes. Petición para dar de baja a un usuario de un deporte.
 */
@WebServlet(value = "/deportes", name = "DeportesServlet")
public class DeportesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioDeporte repo = new RepositorioDeporte();	//Repositorio de deportes
	private Gson gson = new Gson();

	/**
	 * Metodo para suscribir un usuario a un deporte.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String email = (String)req.getSession().getAttribute("email");
		String deporte = req.getParameter("deporte");
		//Se suscribir el usuario dado su <email> a un <deporte>
		boolean realizado = repo.suscribirseDeporte(deporte, email);
		//Si la operación se ha realizado con éxito
		if (realizado) {
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.html");
			response = "El usuario se ha suscrito correctamente al deporte";
		} else {
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.sendRedirect("muro.html");
			response = "El usuario no se ha podido suscribir al deporte";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para dar de baja un usuario a un deporte.
	 */
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		String email = req.getParameter("email");
		String deporte = req.getParameter("deporte");
		//Se da de baja un usuario dado su <amil> de un <deporte>
		boolean realizado = repo.darseDeBajaDeporte(deporte, email);
		if (realizado) {
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El usuario se ha dado de baja correctamente del deporte";
		}
		else {
			//Se devuelve código 400 (petición no exitosa)
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El usuario no se ha podido dar de baja del deporte";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver los deportes de la BD.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String response = null;
		List<Deporte> deportes = null;
		String email = (String) req.getSession().getAttribute("email");
		String tipo = req.getParameter("tipoDeport");
		String deporte = req.getParameter("deporte");
		
		//Si se pide los deportes disponibles
		if (tipo.equals("AvailableSports")) {
			deportes = repo.listarDeportesDisponibles(email);
			response = gson.toJson(deportes);
			System.out.println("json con deportes");
			System.out.println(response);
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		if (tipo.equals("DesSuscribe")) {
			if (repo.darseDeBajaDeporte(deporte, email)) {
				//Se devuelve código 200 (éxito)
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.sendRedirect("profile.html");
			} else {
				//Se devuelve código 400 (petición no exitosa)
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no existe";
			}
		}
		//Si se pide listar los deortes a los que está suscrito el usuario
		if (tipo.equals("ListUserSports")) {
			deportes = repo.listarDeportesUsuario(email);
			response = gson.toJson(deportes);
			System.out.println("si los Deportes usuario con json: " + response);			
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		//Si se pide listar todos los deportes
		if (tipo.equals("AllSports")) {
			deportes = repo.listarDeportes();
			response = gson.toJson(deportes);			
			//Se devuelve código 200 (éxito)
			resp.setStatus(HttpServletResponse.SC_OK);

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