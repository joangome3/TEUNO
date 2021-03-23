package bp.aplicaciones.sibod.modelo;

public class modelo_grafico_pastel {

	private String nombre;
	private int cantidad;

	/**
	 * 
	 */
	public modelo_grafico_pastel() {
		super();
	}

	/**
	 * @param nombre
	 * @param cantidad
	 */
	public modelo_grafico_pastel(String nombre, int cantidad) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "modelo_grafico_pastel [nombre=" + nombre + ", cantidad=" + cantidad + "]";
	}

}
