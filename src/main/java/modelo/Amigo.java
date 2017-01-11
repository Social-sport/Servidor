package modelo;

public class Amigo {
	
	private String usuario;
	private String amigo;
	private String fecha;
	
	 /**
	  * Crea un nuevo objeto Amigo que representa un amigo del usuario.
	  * @param usuario String del nombre del usuario.
	  * @param amigo String del nombre del usuario amigo.
	  * @param fecha Fecha en la que se añadió como amigo.
	  */
	public Amigo(String usuario, String amigo, String fecha) {
		this.usuario = usuario;
		this.amigo = amigo;
		this.fecha = fecha;
	}

	/**
	 * Devuelve el nombre del usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre del usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Devuelve el nombre del usuario amigo
	 */
	public String getAmigo() {
		return amigo;
	}

	/**
	 * Establece el nombre del usuario amigo
	 */
	public void setAmigo(String amigo) {
		this.amigo = amigo;
	}


	/**
	 * Devuelve la fecha en la que se añadió como amigo
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha en la que se añadió como amigo
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
