package modelo;

public class Notificacion {

	private int id;
	private String usuario;
	private String descripcion;
	private String fecha;
	private String hora;
	private String tipo;

	public Notificacion(int id, String usuario, String descripcion, String fecha, String hora, String tipo) {
		this.id=id;
		this.usuario=usuario;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.tipo=tipo;
	}

	public Notificacion(String usuario, String descripcion, String fecha, String hora, String tipo) {
		this.usuario=usuario;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.tipo=tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}