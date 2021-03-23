package bp.aplicaciones.bitacora.modelo;

public class modelo_panel3 {

	private String cumplimiento;
	private int cantidad;
	private int porcentaje;
	private String tipo_servicio;

	/**
	 * 
	 */
	public modelo_panel3() {
		super();
	}

	/**
	 * @param tipo_servicio
	 */
	public modelo_panel3(String tipo_servicio) {
		super();
		this.tipo_servicio = tipo_servicio;
	}
	
	

	/**
	 * @param cantidad
	 * @param tipo_servicio
	 */
	public modelo_panel3(int cantidad, String tipo_servicio) {
		super();
		this.cantidad = cantidad;
		this.tipo_servicio = tipo_servicio;
	}

	/**
	 * @param cumplimiento
	 * @param cantidad
	 * @param porcentaje
	 */
	public modelo_panel3(String cumplimiento, int cantidad, int porcentaje) {
		super();
		this.cumplimiento = cumplimiento;
		this.cantidad = cantidad;
		this.porcentaje = porcentaje;
	}

	/**
	 * @return the cumplimiento
	 */
	public String getCumplimiento() {
		return cumplimiento;
	}

	/**
	 * @param cumplimiento the cumplimiento to set
	 */
	public void setCumplimiento(String cumplimiento) {
		this.cumplimiento = cumplimiento;
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

	/**
	 * @return the porcentaje
	 */
	public int getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	/**
	 * @return the tipo_servicio
	 */
	public String getTipo_servicio() {
		return tipo_servicio;
	}

	/**
	 * @param tipo_servicio the tipo_servicio to set
	 */
	public void setTipo_servicio(String tipo_servicio) {
		this.tipo_servicio = tipo_servicio;
	}

	@Override
	public String toString() {
		return "modelo_panel2 [cumplimiento=" + cumplimiento + ", cantidad=" + cantidad + ", porcentaje=" + porcentaje
				+ ", tipo_servicio=" + tipo_servicio + "]";
	}

}
