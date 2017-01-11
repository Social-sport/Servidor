package modelo;

public class Notificacion {

	private int id;
	private String usuarioEnvia;	
	private String usuarioRecibe;
	private String nombre;
	private String foto;
	private String descripcion;
	private String fecha;
	private String hora;
	private String tipo;
	private int idEvent;

	 /**
	  * Crea un nuevo objeto Notificacion que representa un mensaje
	  * @param id Id de la notificación
	  * @param usuarioEnvia Usuario que envia la notificación
	  * @param usuarioRecibe Usuario que recibe la notificación
	  * @param descripcion Descripción de la notificación
	  * @param fecha Fecha de la notificación
	  * @param hora Hora de la notificación
	  * @param tipo Tipo de notificación
	  */
	public Notificacion(int id, String usuario, String descripcion, String fecha, String hora, String tipo) {
		this.id=id;
		this.usuarioEnvia=usuario;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.tipo=tipo;
	}	

	 /**
	  * Crea un nuevo objeto Mensaje que representa un mensaje
	  * @param usuarioEnvia Usuario que envia la notificación
	  * @param usuarioRecibe Usuario que recibe la notificación
	  * @param nombre Nombre de la notificación
	  * @param foto Foto adjunta a la notificación
	  * @param descripcion Descripción de la notificación
	  * @param fecha Fecha de la notificación
	  * @param hora Hora de la notificación
	  * @param tipo Tipo de notificación
	  */
	public Notificacion(String usuarioEnvia, String usuarioRecibe, String nombre, String foto, String descripcion,
			String fecha, String hora, String tipo) {
		super();
		this.usuarioEnvia = usuarioEnvia;
		this.usuarioRecibe = usuarioRecibe;
		this.nombre = nombre;
		this.foto = foto;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.tipo = tipo;
	}
	
	 /**
	  * Crea un nuevo objeto Notificacion que representa una notificacion
	  * @param usuarioEnvia Usuario que envia la notificación
	  * @param usuarioRecibe Usuario que recibe la notificación
	  * @param nombre Nombre de la notificación
	  * @param foto Foto adjunta a la notificación
	  * @param descripcion Descripción de la notificación
	  * @param fecha Fecha de la notificación
	  * @param hora Hora de la notificación
	  * @param tipo Tipo de notificación
	  * @param idEvent Id del evento asociado a la notificación
	  */
	public Notificacion(String usuarioEnvia, String usuarioRecibe, String nombre, String foto,
			String descripcion, String fecha, String hora, String tipo, int idEvent) {
		this.usuarioEnvia = usuarioEnvia;
		this.usuarioRecibe = usuarioRecibe;
		this.nombre = nombre;
		this.foto = foto;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.tipo = tipo;
		this.idEvent = idEvent;
	}

	/**
	 * Devuelve el Id del evento asociado a la notificacion
	 */
	public int getIdEvent() {
		return idEvent;
	}

	/**
	 * Establece el Id del evento asociado a la notificacion
	 */
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	/**
	 * Devuelve el Id asociado a la notificación
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el Id asociado a la notificación
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el nombre del usuario que envía la notificación
	 */
	public String getUsuarioEnvia() {
		return usuarioEnvia;
	}

	/**
	 * Establece el nombre del usuario que envía la notificación
	 */
	public void setUsuarioEnvia(String usuarioEnvia) {
		this.usuarioEnvia = usuarioEnvia;
	}

	/**
	 * Devuelve el nombre de la notificación
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre de la notificación
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setnombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el nombre del usuario que recibe la notificación
	 */
	public String getUsuarioRecibe() {
		return usuarioRecibe;
	}

	/**
	 * Establece el nombre del usuario que recibe la notificación
	 */
	public void setUsuarioRecibe(String usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}
	
	/**
	 * Devuelve el nombre de la foto asociada a la notificación
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * Establece el nombre de la foto asociada a la notificación
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * Devuelve la descripción de la notificación
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción de la notificación
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la fecha de la notificación
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha de la notificación
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve la hora de la notificación
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Establece la hora de la notificación
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Devuelve el tipo de la notificación
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de la notificación
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}