package modelo;

public class Evento {

	private int id;
	private String nombre;
	private String descripcion;
	private String fecha;
	private String hora;
	private String deporte;
	private String creador;

	public Evento(int id, String nombre, String descripcion, String fecha, String hora, String deporte, String creador) {
		this.id=id;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.deporte=deporte;
		this.creador=creador;
	}

	public Evento(String nombre, String descripcion, String fecha, String hora, String deporte, String creador) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.deporte=deporte;
		this.creador=creador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getCreador() {
		return creador;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}
}