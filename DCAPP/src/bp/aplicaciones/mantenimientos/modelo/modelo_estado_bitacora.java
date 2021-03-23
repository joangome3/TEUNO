package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_estado_bitacora {

	private long id_estado;
	private String nom_estado;
	private String des_estado;
	private String est_estado;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_estado_bitacora() {
		super();
	}

	/**
	 * @param id_estado
	 * @param nom_estado
	 * @param des_estado
	 * @param est_estado
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_estado_bitacora(long id_estado, String nom_estado, String des_estado, String est_estado,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_estado = id_estado;
		this.nom_estado = nom_estado;
		this.des_estado = des_estado;
		this.est_estado = est_estado;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the nom_estado
	 */
	public String getNom_estado() {
		return nom_estado;
	}

	/**
	 * @param nom_estado the nom_estado to set
	 */
	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}

	/**
	 * @return the des_estado
	 */
	public String getDes_estado() {
		return des_estado;
	}

	/**
	 * @param des_estado the des_estado to set
	 */
	public void setDes_estado(String des_estado) {
		this.des_estado = des_estado;
	}

	/**
	 * @return the est_estado
	 */
	public String getEst_estado() {
		return est_estado;
	}

	/**
	 * @param est_estado the est_estado to set
	 */
	public void setEst_estado(String est_estado) {
		this.est_estado = est_estado;
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
		return "modelo_estado [id_estado=" + id_estado + ", nom_estado=" + nom_estado + ", des_estado=" + des_estado
				+ ", est_estado=" + est_estado + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_estado.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_estado.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_estado.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
