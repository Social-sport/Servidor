package modelo;

public class Deporte {

	private String nombre;
	private String Descripcion;
	private String Foto;

	

	public Deporte(String nombre, String descripcion, String foto) {
		this.nombre = nombre;
		Descripcion = descripcion;
		Foto = foto;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getFoto() {
		return Foto;
	}

	public void setFoto(String foto) {
		Foto = foto;
	}
	
	
}