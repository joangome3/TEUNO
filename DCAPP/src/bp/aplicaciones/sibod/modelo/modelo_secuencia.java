package bp.aplicaciones.sibod.modelo;

public class modelo_secuencia {

	private long id_secuencia;
	private long num_secuencia;
	private long id_localidad;
	private String est_secuencia;

	/**
	 * 
	 */
	public modelo_secuencia() {
		super();
	}

	/**
	 * @param id_secuencia
	 * @param num_secuencia
	 * @param id_localidad
	 * @param est_secuencia
	 */
	public modelo_secuencia(long id_secuencia, long num_secuencia, long id_localidad, String est_secuencia) {
		super();
		this.id_secuencia = id_secuencia;
		this.num_secuencia = num_secuencia;
		this.id_localidad = id_localidad;
		this.est_secuencia = est_secuencia;
	}

	/**
	 * @return the id_secuencia
	 */
	public long getId_secuencia() {
		return id_secuencia;
	}

	/**
	 * @param id_secuencia the id_secuencia to set
	 */
	public void setId_secuencia(long id_secuencia) {
		this.id_secuencia = id_secuencia;
	}

	/**
	 * @return the num_secuencia
	 */
	public long getNum_secuencia() {
		return num_secuencia;
	}

	/**
	 * @param num_secuencia the num_secuencia to set
	 */
	public void setNum_secuencia(long num_secuencia) {
		this.num_secuencia = num_secuencia;
	}

	/**
	 * @return the id_localidad
	 */
	public long getId_localidad() {
		return id_localidad;
	}

	/**
	 * @param id_localidad the id_localidad to set
	 */
	public void setId_localidad(long id_localidad) {
		this.id_localidad = id_localidad;
	}

	/**
	 * @return the est_secuencia
	 */
	public String getEst_secuencia() {
		return est_secuencia;
	}

	/**
	 * @param est_secuencia the est_secuencia to set
	 */
	public void setEst_secuencia(String est_secuencia) {
		this.est_secuencia = est_secuencia;
	}

	@Override
	public String toString() {
		return "modelo_secuencia [id_secuencia=" + id_secuencia + ", num_secuencia=" + num_secuencia + ", id_localidad="
				+ id_localidad + ", est_secuencia=" + est_secuencia + "]";
	}

}
