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

	public Notificacion(int id, String usuario, String descripcion, String fecha, String hora, String tipo) {
		this.id=id;
		this.usuarioEnvia=usuario;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.tipo=tipo;
	}	

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsuarioEnvia() {
		return usuarioEnvia;
	}

	public void setUsuarioEnvia(String usuarioEnvia) {
		this.usuarioEnvia = usuarioEnvia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setnombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuarioRecibe() {
		return usuarioRecibe;
	}

	public void setUsuarioRecibe(String usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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