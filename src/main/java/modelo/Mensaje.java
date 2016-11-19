package modelo;

public class Mensaje {

	private int id;
	private String fecha;
	private String hora;
	private String texto;
	private String autor;
	private String destinatario;

	public Mensaje(int id, String fecha, String hora, String texto,
			String autor, String destinatario) {
		this.id=id;
		this.fecha=fecha;
		this.hora=hora;
		this.texto=texto;
		this.autor=autor;
		this.destinatario=destinatario;
	}

	public Mensaje(String fecha, String hora, String texto,
			String autor, String destinatario) {
		this.fecha=fecha;
		this.hora=hora;
		this.texto=texto;
		this.autor=autor;
		this.destinatario=destinatario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
}