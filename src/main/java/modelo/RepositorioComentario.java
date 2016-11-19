package modelo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import baseDatos.ConexionBD;

public class RepositorioComentario {

	private Connection conexion = null;

	/**
	 * Metodo creador
	 */
	public RepositorioComentario() {
		ConexionBD.iniciarConexion();
		this.conexion = ConexionBD.getConexion();
	}
	
	public Comentario findComentario(int id) {
		Comentario comentario = null;
		try {
			String sql = "SELECT * FROM Comentario WHERE id='"+id+"'";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			comentario = new Comentario(rs.getInt("id"),
					rs.getString("fecha"), rs.getString("texto"),
					rs.getString("hora"), rs.getString("usuario"),
					rs.getInt("evento"));
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en buscar Comentario");
		}
		return comentario;
	}

}