package modelo;

public class Mensaje {

	private int id;
	private String fecha;
	private String hora;
	private String texto;
	private String autor;
	private String destinatario;

	 /**
	  * Crea un nuevo objeto Mensaje que representa un mensaje
	  * @param id Identificador del mensaje
	  * @param fecha Fecha del mensaje
	  * @param hora Hora del mensaje
	  * @param texto Texto del mensaje
	  * @param autor Autor del mensaje
	  * @param destinatario Destinatario del mensaje
	  */
	public Mensaje(int id, String fecha, String hora, String texto,
			String autor, String destinatario) {
		this.id=id;
		this.fecha=fecha;
		this.hora=hora;
		this.texto=texto;
		this.autor=autor;
		this.destinatario=destinatario;
	}

	 /**
	  * Crea un nuevo objeto Mensaje que representa un mensaje
	  * @param fecha Fecha del mensaje
	  * @param hora Hora del mensaje
	  * @param texto Texto del mensaje
	  * @param autor Autor del mensaje
	  * @param destinatario Destinatario del mensaje
	  */
	public Mensaje(String fecha, String hora, String texto,
			String autor, String destinatario) {
		this.fecha=fecha;
		this.hora=hora;
		this.texto=texto;
		this.autor=autor;
		this.destinatario=destinatario;
	}
	
	/**
	 * Devuelve el identificador del mensaje
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador del mensaje
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve la fecha del mensaje
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del mensaje
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve la hora del mensaje
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Establece la hora del mensaje
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Devuelve el texto del mensaje
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Establece el texto del mensaje
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * Devuelve el autor del mensaje
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * Establece el autor del mensaje
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * Devuelve el destinatario del mensaje
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * Establece el destinatario del mensaje
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
}