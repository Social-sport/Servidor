package modelo;

public class Comentario {

	private int id;
	private String fecha;
	private String texto;
	private String hora;
	private String usuario;
	private int evento;

	 /**
	  * Crea un nuevo objeto Comentario que representa un comentario
	  * @param id Identificador del comentario
	  * @param fecha Fecha del comentario
	  * @param texto Cuerpo del comentario
	  * @param hora Hora de publicación del comentario
	  * @param usuario Nombre del autor del comentario
	  * @param evento Id del evento al que pertenece el comentario
	  */
	public Comentario(int id, String fecha, String texto,
			String hora, String usuario, int evento) {
		this.id = id;
		this.fecha = fecha;
		this.texto = texto;
		this.hora = hora;
		this.usuario = usuario;
		this.evento = evento;
	}
	
	 /**
	  * Crea un nuevo objeto Comentario que representa un comentario
	  * @param fecha Fecha del comentario
	  * @param texto Cuerpo del comentario
	  * @param hora Hora de publicación del comentario
	  * @param usuario Nombre del autor del comentario
	  * @param evento Id del evento al que pertenece el comentario
	  */
	public Comentario(String fecha, String texto,
			String hora, String usuario, int evento) {
		this.fecha = fecha;
		this.texto = texto;
		this.hora = hora;
		this.usuario = usuario;
		this.evento = evento;
	}

	/**
	 * Devuelve el identificador
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve la fecha del comentario
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del comentario
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el cuerpo del comentario
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Establece el cuerpo del comentario
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * Devuelve la hora del comentario
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Establece la hora del comentario
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	/**
	 * Devuelve el nombre del autor del comentario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre del autor del comentario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Devuelve el id del evento al que pertenece el comentario
	 */
	public int getEvento() {
		return evento;
	}

	/**
	 * Establece el id del evento al que pertenece el comentario
	 */
	public void setEvento(int evento) {
		this.evento = evento;
	}
}