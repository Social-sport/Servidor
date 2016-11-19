package modelo;

public class Amigo {
	
	private String usuario;
	private String amigo;
	private String fecha;
	
	public Amigo(String usuario, String amigo, String fecha) {
		this.usuario=usuario;
		this.amigo=amigo;
		this.fecha=fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAmigo() {
		return amigo;
	}

	public void setAmigo(String amigo) {
		this.amigo = amigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
