package modelo;

public class Comentario {

	private int id;
	private String fecha;
	private String texto;
	private String hora;
	private String usuario;
	private int evento;

	public Comentario(int id, String fecha, String texto,
			String hora, String usuario, int evento) {
		this.id=id;
		this.fecha=fecha;
		this.texto=texto;
		this.hora=hora;
		this.usuario=usuario;
		this.evento=evento;
	}
	
	public Comentario(String fecha, String texto,
			String hora, String usuario, int evento) {
		this.fecha=fecha;
		this.texto=texto;
		this.hora=hora;
		this.usuario=usuario;
		this.evento=evento;
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getEvento() {
		return evento;
	}

	public void setEvento(int evento) {
		this.evento = evento;
	}
}