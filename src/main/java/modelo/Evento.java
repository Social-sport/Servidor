package modelo;

public class Evento {

	private int id;
	private String nombre;
	private String descripcion;
	private String fecha;
	private String hora;
	private String deporte;
	private String creador;
	private String foto;
	private String propietario;
	private String NumSuscritos;

	
	 /**
	  * Crea un nuevo objeto Evento que representa un evento
	  * @param id Identificador del evento
	  * @param nombre Nombre del evento
	  * @param descripcion Descripción del evento
	  * @param fecha Fecha del evento
	  * @param hora Hora del evento
	  * @param deporte Deporte asociado al evento
	  * @param creador Nombre del creador
	  * @param foto Foto asociada al evento
	  */
	public Evento(int id, String nombre, String descripcion, String fecha, String hora, String deporte, String creador, String foto) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.deporte = deporte;
		this.creador = creador;
		this.foto = foto;
	}

	 /**
	  * Crea un nuevo objeto Evento que representa un evento
	  * @param nombre Nombre del evento
	  * @param descripcion Descripción del evento
	  * @param fecha Fecha del evento
	  * @param hora Hora del evento
	  * @param deporte Deporte asociado al evento
	  * @param creador Nombre del creador
	  * @param foto Foto asociada al evento
	  */
	public Evento(String nombre, String descripcion, String fecha, String hora, String deporte, String creador, String foto) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.fecha=fecha;
		this.hora=hora;
		this.deporte=deporte;
		this.creador=creador;
		this.foto=foto;
	}
	
	 /**
	  * Crea un nuevo objeto Evento que representa un evento
	  * @param id Identificador del evento
	  * @param nombre Nombre del evento
	  * @param descripcion Descripción del evento
	  * @param fecha Fecha del evento
	  * @param hora Hora del evento
	  * @param deporte Deporte asociado al evento
	  * @param creador Nombre del creador
	  * @param foto Foto asociada al evento
	  * @param propietario Propietario asociado al evento
	  */
	public Evento(int id, String nombre, String descripcion, String fecha, String hora, String deporte, String creador,
			String foto, String propietario) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.deporte = deporte;
		this.creador = creador;
		this.foto = foto;
		this.propietario = propietario;
	}
	
	/**
	 * Devuelve el número de usuarios suscritos al evento
	 */
	public String getNumSuscritos() {
		return NumSuscritos;
	}

	/**
	 * Establece el número de usuarios suscritos al evento
	 */
	public void setNumSuscritos(String numSuscritos) {
		NumSuscritos = numSuscritos;
	}

	/**
	 * Devuelve el nombre del propietario asociado al evento
	 */
	public String getPropietario() {
		return propietario;
	}

	/**
	 * Establece el nombre del propietario asociado al evento
	 */
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	/**
	 * Devuelve el identificador del evento
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Establece el identificador del evento
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el nombre del evento
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del evento
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del evento
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del evento
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve la fecha asociada al evento
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha asociada al evento
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve la hora asociada al evento
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Establece la hora asociada al evento
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Devuelve el deporte asociado al evento
	 */
	public String getDeporte() {
		return deporte;
	}

	/**
	 * Establece el deporte asociado al evento
	 */
	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	/**
	 * Devuelve el creador del evento
	 */
	public String getCreador() {
		return creador;
	}
	
	/**
	 * Establece el creador del evento
	 */
	public void setCreador(String creador) {
		this.creador = creador;
	}

	/**
	 * Devuelve la foto asociada al evento
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * Establece la foto asociada al evento
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}
}