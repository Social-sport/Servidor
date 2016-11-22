package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import baseDatos.ConexionBD;

public class RepositorioAmigo {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioAmigo() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}

	public boolean insertarAmigo(Amigo amigo) {
		String sql = "INSERT INTO Amigos"
				+ "(usuario, amigo, fecha)"
				+ " VALUES (\""+amigo.getUsuario()+"\",\""+amigo.getAmigo()+"\",\""+amigo.getFecha()+"\")";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al insertar amigo");
			return false;
		}
	}

	public boolean borrarAmigo(String usuario, String amigo) {
		String sql = "DELETE FROM Amigos WHERE usuario=\""+usuario+"\" AND amigo=\""+amigo+"\"";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error al borrar amigo");
			return false;
		}
	}

	public List<Amigo> listarAmigos (String usuario) {
		List<Amigo> amigos = new LinkedList<Amigo>();
		String sql = "SELECT * FROM Amigos WHERE usuario ='"+usuario+"'";
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				amigos.add(new Amigo(rs.getString("usuario"),rs.getString("amigo"),
						rs.getString("fecha")));
			}
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en listar mis Eventos");
		}
		return amigos;
	}
}