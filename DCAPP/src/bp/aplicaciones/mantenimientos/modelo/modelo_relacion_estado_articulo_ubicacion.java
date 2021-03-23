package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_relacion_estado_articulo_ubicacion {

	private long id_relacion;
	private long id_estado;
	private long id_articulo;
	private long id_ubicacion;
	private long id_relacion_articulo_ubicacion;
	private long stock;
	private String est_relacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	* 
	*/
	public modelo_relacion_estado_articulo_ubicacion() {
		super();
	}

	/**
	 * @param id_relacion
	 * @param id_estado
	 * @param id_articulo
	 * @param id_ubicacion
	 * @param id_relacion_articulo_ubicacion
	 * @param stock
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_estado_articulo_ubicacion(long id_relacion, long id_estado, long id_articulo,
			long id_ubicacion, long id_relacion_articulo_ubicacion, long stock, String est_relacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_relacion = id_relacion;
		this.id_estado = id_estado;
		this.id_articulo = id_articulo;
		this.id_ubicacion = id_ubicacion;
		this.id_relacion_articulo_ubicacion = id_relacion_articulo_ubicacion;
		this.stock = stock;
		this.est_relacion = est_relacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_relacion
	 */
	public long getId_relacion() {
		return id_relacion;
	}

	/**
	 * @param id_relacion the id_relacion to set
	 */
	public void setId_relacion(long id_relacion) {
		this.id_relacion = id_relacion;
	}

	/**
	 * @return the id_estado
	 */
	public long getId_estado() {
		return id_estado;
	}

	/**
	 * @param id_estado the id_estado to set
	 */
	public void setId_estado(long id_estado) {
		this.id_estado = id_estado;
	}

	/**
	 * @return the id_articulo
	 */
	public long getId_articulo() {
		return id_articulo;
	}

	/**
	 * @param id_articulo the id_articulo to set
	 */
	public void setId_articulo(long id_articulo) {
		this.id_articulo = id_articulo;
	}

	/**
	 * @return the id_ubicacion
	 */
	public long getId_ubicacion() {
		return id_ubicacion;
	}

	/**
	 * @param id_ubicacion the id_ubicacion to set
	 */
	public void setId_ubicacion(long id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	/**
	 * @return the id_relacion_articulo_ubicacion
	 */
	public long getId_relacion_articulo_ubicacion() {
		return id_relacion_articulo_ubicacion;
	}

	/**
	 * @param id_relacion_articulo_ubicacion the id_relacion_articulo_ubicacion to
	 *                                       set
	 */
	public void setId_relacion_articulo_ubicacion(long id_relacion_articulo_ubicacion) {
		this.id_relacion_articulo_ubicacion = id_relacion_articulo_ubicacion;
	}

	/**
	 * @return the stock
	 */
	public long getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(long stock) {
		this.stock = stock;
	}

	/**
	 * @return the est_relacion
	 */
	public String getEst_relacion() {
		return est_relacion;
	}

	/**
	 * @param est_relacion the est_relacion to set
	 */
	public void setEst_relacion(String est_relacion) {
		this.est_relacion = est_relacion;
	}

	/**
	 * @return the usu_ingresa
	 */
	public String getUsu_ingresa() {
		return usu_ingresa;
	}

	/**
	 * @param usu_ingresa the usu_ingresa to set
	 */
	public void setUsu_ingresa(String usu_ingresa) {
		this.usu_ingresa = usu_ingresa;
	}

	/**
	 * @return the fec_ingresa
	 */
	public Timestamp getFec_ingresa() {
		return fec_ingresa;
	}

	/**
	 * @param fec_ingresa the fec_ingresa to set
	 */
	public void setFec_ingresa(Timestamp fec_ingresa) {
		this.fec_ingresa = fec_ingresa;
	}

	/**
	 * @return the usu_modifica
	 */
	public String getUsu_modifica() {
		return usu_modifica;
	}

	/**
	 * @param usu_modifica the usu_modifica to set
	 */
	public void setUsu_modifica(String usu_modifica) {
		this.usu_modifica = usu_modifica;
	}

	/**
	 * @return the fec_modifica
	 */
	public Timestamp getFec_modifica() {
		return fec_modifica;
	}

	/**
	 * @param fec_modifica the fec_modifica to set
	 */
	public void setFec_modifica(Timestamp fec_modifica) {
		this.fec_modifica = fec_modifica;
	}

	@Override
	public String toString() {
		return "modelo_relacion_estado_articulo_ubicacion [id_relacion=" + id_relacion + ", id_estado=" + id_estado
				+ ", id_articulo=" + id_articulo + ", id_ubicacion=" + id_ubicacion
				+ ", id_relacion_articulo_ubicacion=" + id_relacion_articulo_ubicacion + ", stock=" + stock
				+ ", est_relacion=" + est_relacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_relacion.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}

}
