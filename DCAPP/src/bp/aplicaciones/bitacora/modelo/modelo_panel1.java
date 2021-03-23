package bp.aplicaciones.bitacora.modelo;

public class modelo_panel1 {

	private String fecha;
	private int cantidad;

	/**
	 * 
	 */
	public modelo_panel1() {
		super();
	}

	/**
	 * @param fecha
	 * @param cantidad
	 */
	public modelo_panel1(String fecha, int cantidad) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
		return "modelo_barra1 [fecha=" + fecha + ", cantidad=" + cantidad + "]";
	}

}
