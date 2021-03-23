package bp.aplicaciones.sibod.modelo;

public class modelo_grafico_lineal {

	private String mes;
	private int cantidad;

	/**
	 * 
	 */
	public modelo_grafico_lineal() {
		super();
	}

	/**
	 * @param mes
	 * @param cantidad
	 */
	public modelo_grafico_lineal(String mes, int cantidad) {
		super();
		this.mes = mes;
		this.cantidad = cantidad;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
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
		return "modelo_grafico_lineal [mes=" + mes + ", cantidad=" + cantidad + "]";
	}

}
