package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Deporte;
import modelo.RepositorioDeporte;
import modelo.RepositorioUsuario;

import com.google.gson.Gson;

/**
 * Servlet de obtencion de usuaiors
 */
@WebServlet(value = "/deportes", name = "DeportesServlet")
public class DeportesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static RepositorioDeporte repo = new RepositorioDeporte();
	private static RepositorioUsuario repoUsuario = new RepositorioUsuario();
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
		
		boolean realizado = repo.suscribirseDeporte(deporte,email);
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.html");
		}
		else {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.sendRedirect("muro.html");
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
		boolean realizado = repo.darseDeBajaDeporte(deporte,email);
		if (realizado) {
			resp.setStatus(HttpServletResponse.SC_OK);
			response = "El usuario se ha dado de baja correctamente del deporte";
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response = "El usuario no se ha podido dar de baja del deporte";
		}
		setResponse(response, resp);
	}

	/**
	 * Metodo para devolver los deportes de la BD.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String response = null;
		List<Deporte> deportes = null;
		List<Deporte> deportesSus = null;
		List<Deporte> deportesDef = new LinkedList<Deporte>();
		String email = (String)req.getSession().getAttribute("email");
		String tipo = req.getParameter("tipoDeport");
		String deporte = req.getParameter("deporte");

		if (tipo.equals("ListAllSports")) {			
			deportes = repo.listarDeportes();	
			deportesSus = repo.listarDeportesUsuario(email);

			if (deportesSus.isEmpty()) {
				deportesDef = deportes;
			} else {
				for (int i = 0; i < deportes.size(); i++) {
					
					for (int j = 0; j < deportesSus.size(); j++) {
						
						if (!(deportes.get(i).getNombre().equals(deportesSus.get(j).getNombre()))) {
							
							if (!(deportesDef.contains(deportes.get(i)))& !(deportesSus.contains(deportes.get(i)))) {
								deportesDef.add(deportes.get(i));
							}
						}
						
					}
					
				}

			}							
				response= gson.toJson(deportesDef);	
				System.out.println("json con deportes");				
				System.out.println(response);
				resp.setStatus(HttpServletResponse.SC_OK);
			
		}
		if (tipo.equals("DesSuscribe")) {
			
			if(repo.darseDeBajaDeporte(deporte,email)){
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.sendRedirect("muro.html");
			}else{
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no existe";
			}
			
		}
		if (tipo.equals("ListUserSports")) {			
				
			if (repoUsuario.findUsuario(email) == null) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response = "El usuario no existe";
			}
			else {
				//Devuelve los deportes a los que esta suscrito el usuario
				deportes = repo.listarDeportesUsuario(email);
				if (deportes.isEmpty()) {
					resp.setStatus(HttpServletResponse.SC_OK);
					response = "{'Error': 'El usuario no ha suscrito deportes'}";
				}
				else {			
					response= gson.toJson(deportes);				
					System.out.println("si los Deportes usuario con json");
					System.out.println(response);
				}
				
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
		try {			
			PrintWriter out = resp.getWriter();
			out.print(response);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}