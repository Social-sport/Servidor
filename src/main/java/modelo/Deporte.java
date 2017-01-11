package modelo;

public class Deporte {

	private String nombre;
	private String descripcion;
	private String foto;
	private String numSuscritos;	

	 /**
	  * Crea un nuevo objeto Deporte que representa un deporte
	  * @param nombre Nombre del deporte
	  * @param descripcion Descripción del deporte
	  * @param foto Foto adjunta al deporte
	  */
	public Deporte(String nombre, String descripcion, String foto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.foto = foto;
		
	}

	/**
	 * Devuelve el nombre del deporte
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del deporte
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del deporte
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del deporte
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la foto adjunta del deporte
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * Establece la foto adjunta del deporte
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * Devuelve el numero de usuarios suscritos al deporte.
	 */
	public String getNumSuscritos() {
		return numSuscritos;
	}

	/**
	 * Establece el numero de usuarios suscritos al deporte.
	 */
	public void setNumSuscritos(String numSuscritos) {
		this.numSuscritos = numSuscritos;
	}
	
	
}