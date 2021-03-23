package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_servicio {

	private long id_tipo_servicio;
	private String nom_tipo_servicio;
	private String des_tipo_servicio;
	private String est_tipo_servicio;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tipo_servicio() {
		super();
	}

	/**
	 * @param id_tipo_servicio
	 * @param nom_tipo_servicio
	 * @param des_tipo_servicio
	 * @param est_tipo_servicio
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_servicio(long id_tipo_servicio, String nom_tipo_servicio, String des_tipo_servicio,
			String est_tipo_servicio, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_tipo_servicio = id_tipo_servicio;
		this.nom_tipo_servicio = nom_tipo_servicio;
		this.des_tipo_servicio = des_tipo_servicio;
		this.est_tipo_servicio = est_tipo_servicio;
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
	 * @return the nom_tipo_servicio
	 */
	public String getNom_tipo_servicio() {
		return nom_tipo_servicio;
	}

	/**
	 * @param nom_tipo_servicio the nom_tipo_servicio to set
	 */
	public void setNom_tipo_servicio(String nom_tipo_servicio) {
		this.nom_tipo_servicio = nom_tipo_servicio;
	}

	/**
	 * @return the des_tipo_servicio
	 */
	public String getDes_tipo_servicio() {
		return des_tipo_servicio;
	}

	/**
	 * @param des_tipo_servicio the des_tipo_servicio to set
	 */
	public void setDes_tipo_servicio(String des_tipo_servicio) {
		this.des_tipo_servicio = des_tipo_servicio;
	}

	/**
	 * @return the est_tipo_servicio
	 */
	public String getEst_tipo_servicio() {
		return est_tipo_servicio;
	}

	/**
	 * @param est_tipo_servicio the est_tipo_servicio to set
	 */
	public void setEst_tipo_servicio(String est_tipo_servicio) {
		this.est_tipo_servicio = est_tipo_servicio;
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
		return "sibod_tipo_servicio [id_tipo_servicio=" + id_tipo_servicio + ", nom_tipo_servicio=" + nom_tipo_servicio
				+ ", des_tipo_servicio=" + des_tipo_servicio + ", est_tipo_servicio=" + est_tipo_servicio + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_tipo_servicio.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_servicio.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_servicio.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
