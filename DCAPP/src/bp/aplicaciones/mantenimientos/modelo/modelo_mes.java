package bp.aplicaciones.mantenimientos.modelo;

public class modelo_mes {

	private long id_mes;
	private int mes;
	private String descripcion;
	private String est_mes;

	/**
	 * 
	 */
	public modelo_mes() {
		super();
	}

	/**
	 * @param id_mes
	 * @param mes
	 * @param descripcion
	 * @param est_mes
	 */
	public modelo_mes(long id_mes, int mes, String descripcion, String est_mes) {
		super();
		this.id_mes = id_mes;
		this.mes = mes;
		this.descripcion = descripcion;
		this.est_mes = est_mes;
	}

	/**
	 * @return the id_mes
	 */
	public long getId_mes() {
		return id_mes;
	}

	/**
	 * @param id_mes the id_mes to set
	 */
	public void setId_mes(long id_mes) {
		this.id_mes = id_mes;
	}

	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the est_mes
	 */
	public String getEst_mes() {
		return est_mes;
	}

	/**
	 * @param est_mes the est_mes to set
	 */
	public void setEst_mes(String est_mes) {
		this.est_mes = est_mes;
	}

	@Override
	public String toString() {
		return "modelo_mes [id_mes=" + id_mes + ", mes=" + mes + ", descripcion=" + descripcion + ", est_mes=" + est_mes
				+ "]";
	}

	

}
