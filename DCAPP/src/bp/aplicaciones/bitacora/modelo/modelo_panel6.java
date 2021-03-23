package bp.aplicaciones.bitacora.modelo;

public class modelo_panel6 {

	private String tiempo_invertido;

	/**
	 * 
	 */
	public modelo_panel6() {
		super();
	}

	/**
	 * @param tiempo_invertido
	 */
	public modelo_panel6(String tiempo_invertido) {
		super();
		this.tiempo_invertido = tiempo_invertido;
	}

	/**
	 * @return the tiempo_invertido
	 */
	public String getTiempo_invertido() {
		return tiempo_invertido;
	}

	/**
	 * @param tiempo_invertido the tiempo_invertido to set
	 */
	public void setTiempo_invertido(String tiempo_invertido) {
		this.tiempo_invertido = tiempo_invertido;
	}

	@Override
	public String toString() {
		return "modelo_panel6 [tiempo_invertido=" + tiempo_invertido + "]";
	}

}
