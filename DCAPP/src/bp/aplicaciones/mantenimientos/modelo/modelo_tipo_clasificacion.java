package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_clasificacion {

	private long id_tipo_clasificacion;
	private long id_tipo_servicio;
	private String cod_tipo_clasificacion;
	private String nom_tipo_clasificacion;
	private String des_tipo_clasificacion;
	private String est_tipo_clasificacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tipo_clasificacion() {
		super();
	}

	/**
	 * @param id_tipo_clasificacion
	 * @param id_tipo_servicio
	 * @param cod_tipo_clasificacion
	 * @param nom_tipo_clasificacion
	 * @param des_tipo_clasificacion
	 * @param est_tipo_clasificacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_clasificacion(long id_tipo_clasificacion, long id_tipo_servicio, String cod_tipo_clasificacion,
			String nom_tipo_clasificacion, String des_tipo_clasificacion, String est_tipo_clasificacion,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tipo_clasificacion = id_tipo_clasificacion;
		this.id_tipo_servicio = id_tipo_servicio;
		this.cod_tipo_clasificacion = cod_tipo_clasificacion;
		this.nom_tipo_clasificacion = nom_tipo_clasificacion;
		this.des_tipo_clasificacion = des_tipo_clasificacion;
		this.est_tipo_clasificacion = est_tipo_clasificacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_servicio
	 */
	public long getId_tipo_servicio() {
		return id_tipo_servicio;
	}

	/**
	 * @param id_tipo_servicio the id_tipo_servicio to set
	 */
	public void setId_tipo_servicio(long id_tipo_servicio) {
		this.id_tipo_servicio = id_tipo_servicio;
	}

	/**
	 * @return the cod_tipo_clasificacion
	 */
	public String getCod_tipo_clasificacion() {
		return cod_tipo_clasificacion;
	}

	/**
	 * @param cod_tipo_clasificacion the cod_tipo_clasificacion to set
	 */
	public void setCod_tipo_clasificacion(String cod_tipo_clasificacion) {
		this.cod_tipo_clasificacion = cod_tipo_clasificacion;
	}

	/**
	 * @return the id_tipo_clasificacion
	 */
	public long getId_tipo_clasificacion() {
		return id_tipo_clasificacion;
	}

	/**
	 * @param id_tipo_clasificacion the id_tipo_clasificacion to set
	 */
	public void setId_tipo_clasificacion(long id_tipo_clasificacion) {
		this.id_tipo_clasificacion = id_tipo_clasificacion;
	}

	/**
	 * @return the nom_tipo_clasificacion
	 */
	public String getNom_tipo_clasificacion() {
		return nom_tipo_clasificacion;
	}

	/**
	 * @param nom_tipo_clasificacion the nom_tipo_clasificacion to set
	 */
	public void setNom_tipo_clasificacion(String nom_tipo_clasificacion) {
		this.nom_tipo_clasificacion = nom_tipo_clasificacion;
	}

	/**
	 * @return the des_tipo_clasificacion
	 */
	public String getDes_tipo_clasificacion() {
		return des_tipo_clasificacion;
	}

	/**
	 * @param des_tipo_clasificacion the des_tipo_clasificacion to set
	 */
	public void setDes_tipo_clasificacion(String des_tipo_clasificacion) {
		this.des_tipo_clasificacion = des_tipo_clasificacion;
	}

	/**
	 * @return the est_tipo_clasificacion
	 */
	public String getEst_tipo_clasificacion() {
		return est_tipo_clasificacion;
	}

	/**
	 * @param est_tipo_clasificacion the est_tipo_clasificacion to set
	 */
	public void setEst_tipo_clasificacion(String est_tipo_clasificacion) {
		this.est_tipo_clasificacion = est_tipo_clasificacion;
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
		return "sibod_tipo_clasificacion [id_tipo_clasificacion=" + id_tipo_clasificacion + ", nom_tipo_clasificacion="
				+ nom_tipo_clasificacion + ", des_tipo_clasificacion=" + des_tipo_clasificacion
				+ ", est_tipo_clasificacion=" + est_tipo_clasificacion + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_tipo_clasificacion.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_clasificacion.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_clasificacion.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
