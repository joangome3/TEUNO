package bp.aplicaciones.mantenimientos.modelo;

public class modelo_dia {

	private long id_dia;
	private int dia;
	private String descripcion;
	private String est_dia;

	/**
	 * 
	 */
	public modelo_dia() {
		super();
	}

	/**
	 * @param id_dia
	 * @param dia
	 * @param descripcion
	 * @param est_dia
	 */
	public modelo_dia(long id_dia, int dia, String descripcion, String est_dia) {
		super();
		this.id_dia = id_dia;
		this.dia = dia;
		this.descripcion = descripcion;
		this.est_dia = est_dia;
	}

	/**
	 * @return the id_dia
	 */
	public long getId_dia() {
		return id_dia;
	}

	/**
	 * @param id_dia the id_dia to set
	 */
	public void setId_dia(long id_dia) {
		this.id_dia = id_dia;
	}

	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
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
	 * @return the est_dia
	 */
	public String getEst_dia() {
		return est_dia;
	}

	/**
	 * @param est_dia the est_dia to set
	 */
	public void setEst_dia(String est_dia) {
		this.est_dia = est_dia;
	}

	@Override
	public String toString() {
		return "modelo_dia [id_dia=" + id_dia + ", dia=" + dia + ", descripcion=" + descripcion + ", est_dia=" + est_dia
				+ "]";
	}

}
