package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_infraestructura {

	private long id_infraestructura;
	private long id_tipo_sistema;
	private String nom_infraestructura;
	private String des_infraestructura;
	private String est_infraestructura;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_infraestructura() {
		super();
	}

	/**
	 * @param id_infraestructura
	 * @param id_tipo_sistema
	 * @param nom_infraestructura
	 * @param des_infraestructura
	 * @param est_infraestructura
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_infraestructura(long id_infraestructura, long id_tipo_sistema, String nom_infraestructura,
			String des_infraestructura, String est_infraestructura, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_infraestructura = id_infraestructura;
		this.id_tipo_sistema = id_tipo_sistema;
		this.nom_infraestructura = nom_infraestructura;
		this.des_infraestructura = des_infraestructura;
		this.est_infraestructura = est_infraestructura;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_infraestructura
	 */
	public long getId_infraestructura() {
		return id_infraestructura;
	}

	/**
	 * @param id_infraestructura the id_infraestructura to set
	 */
	public void setId_infraestructura(long id_infraestructura) {
		this.id_infraestructura = id_infraestructura;
	}

	/**
	 * @return the id_tipo_sistema
	 */
	public long getId_tipo_sistema() {
		return id_tipo_sistema;
	}

	/**
	 * @param id_tipo_sistema the id_tipo_sistema to set
	 */
	public void setId_tipo_sistema(long id_tipo_sistema) {
		this.id_tipo_sistema = id_tipo_sistema;
	}

	/**
	 * @return the nom_infraestructura
	 */
	public String getNom_infraestructura() {
		return nom_infraestructura;
	}

	/**
	 * @param nom_infraestructura the nom_infraestructura to set
	 */
	public void setNom_infraestructura(String nom_infraestructura) {
		this.nom_infraestructura = nom_infraestructura;
	}

	/**
	 * @return the des_infraestructura
	 */
	public String getDes_infraestructura() {
		return des_infraestructura;
	}

	/**
	 * @param des_infraestructura the des_infraestructura to set
	 */
	public void setDes_infraestructura(String des_infraestructura) {
		this.des_infraestructura = des_infraestructura;
	}

	/**
	 * @return the est_infraestructura
	 */
	public String getEst_infraestructura() {
		return est_infraestructura;
	}

	/**
	 * @param est_infraestructura the est_infraestructura to set
	 */
	public void setEst_infraestructura(String est_infraestructura) {
		this.est_infraestructura = est_infraestructura;
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
		return "modelo_infraestructura [id_infraestructura=" + id_infraestructura + ", id_tipo_sistema="
				+ id_tipo_sistema + ", nom_infraestructura=" + nom_infraestructura + ", des_infraestructura="
				+ des_infraestructura + ", est_infraestructura=" + est_infraestructura + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_infraestructura.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_infraestructura.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_infraestructura.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
