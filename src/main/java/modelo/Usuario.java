package modelo;

public class Usuario {

	private String email;
	private String nombre;
	private String apellidos;
	private String contrasena;
	private String fecha_nacimiento;
	private String foto;
	private String nick;
	private String numSeguidores;

	 /**
	  * Crea un nuevo objeto Usuario que representa un amigo del usuario.
	  * @param email Email del usuario
	  * @param nombre Nombre del usuario
	  * @param apellidos Apellidos del usuario
	  * @param contrasena Contraseña del usuario
	  * @param fecha_nacimiento Fecha de nacimiento del usuario
	  * @param foto Foto del usuario
	  * @param nick Sobrenombre del usuario
	  */
	public Usuario(String email, String nombre, String apellidos,
			String contrasena, String fecha_nacimiento, String foto, String nick) {
		this.email=email;
		this.nick=nick;
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.contrasena=contrasena;
		this.foto=foto;
		this.fecha_nacimiento=fecha_nacimiento;
	}
	
	 /**
	  * Crea un nuevo objeto Usuario que representa un amigo del usuario.
	  * @param email Email del usuario
	  * @param nombre Nombre del usuario
	  * @param apellidos Apellidos del usuario
	  * @param fecha_nacimiento Fecha de nacimiento del usuario
	  * @param foto Foto del usuario
	  * @param nick Sobrenombre del usuario
	  */
	public Usuario(String email, String nombre, String apellidos, String fecha_nacimiento, String foto, String nick) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fecha_nacimiento = fecha_nacimiento;
		this.foto = foto;
		this.nick = nick;
	}

	/**
	 * Devuelve el número de seguidores de un usuario
	 */
	public String getNumSeguidores() {
		return numSeguidores;
	}

	/**
	 * Establece el número de seguidores de un usuario
	 */
	public void setNumSeguidores(String numSeguidores) {
		this.numSeguidores = numSeguidores;
	}

	/**
	 * Devuelve el sobrenombre de un usuario
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Establece el sobrenombre de un usuario
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Devuelve el email de un usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el email de un usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve el nombre de un usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de un usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos de un usuario
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos de un usuario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve la contrasena de un usuario
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de un usuario
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Devuelve la foto de un usuario
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * Establece la foto de un usuario
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * Devuelve la fecha de nacimiento
	 */
	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	/**
	 * Establece la fecha de nacimiento de un usuario
	 */
	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	/**
	 * Devuelve una cadena con información relativa al usuario
	 */
	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", contrasena="
				+ contrasena + ", fecha_nacimiento=" + fecha_nacimiento + ", foto=" + foto + ", nick=" + nick + "]";
	}
	
	
}