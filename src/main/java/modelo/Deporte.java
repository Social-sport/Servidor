package modelo;

public class Deporte {

	private String nombre;
	private String descripcion;
	private String foto;

	public Deporte(String nombre, String descripcion, String foto) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.foto=foto;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
}