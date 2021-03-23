package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_mantenimiento {

	private long id_tipo_mantenimiento;
	private String nom_tipo_mantenimiento;
	private String des_tipo_mantenimiento;
	private String est_tipo_mantenimiento;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tipo_mantenimiento() {
		super();
	}

	/**
	 * @param id_tipo_mantenimiento
	 * @param nom_tipo_mantenimiento
	 * @param des_tipo_mantenimiento
	 * @param est_tipo_mantenimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_mantenimiento(long id_tipo_mantenimiento, String nom_tipo_mantenimiento, String des_tipo_mantenimiento, String est_tipo_mantenimiento,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tipo_mantenimiento = id_tipo_mantenimiento;
		this.nom_tipo_mantenimiento = nom_tipo_mantenimiento;
		this.des_tipo_mantenimiento = des_tipo_mantenimiento;
		this.est_tipo_mantenimiento = est_tipo_mantenimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_mantenimiento
	 */
	public long getId_tipo_mantenimiento() {
		return id_tipo_mantenimiento;
	}

	/**
	 * @param id_tipo_mantenimiento the id_tipo_mantenimiento to set
	 */
	public void setId_tipo_mantenimiento(long id_tipo_mantenimiento) {
		this.id_tipo_mantenimiento = id_tipo_mantenimiento;
	}

	/**
	 * @return the nom_tipo_mantenimiento
	 */
	public String getNom_tipo_mantenimiento() {
		return nom_tipo_mantenimiento;
	}

	/**
	 * @param nom_tipo_mantenimiento the nom_tipo_mantenimiento to set
	 */
	public void setNom_tipo_mantenimiento(String nom_tipo_mantenimiento) {
		this.nom_tipo_mantenimiento = nom_tipo_mantenimiento;
	}

	/**
	 * @return the des_tipo_mantenimiento
	 */
	public String getDes_tipo_mantenimiento() {
		return des_tipo_mantenimiento;
	}

	/**
	 * @param des_tipo_mantenimiento the des_tipo_mantenimiento to set
	 */
	public void setDes_tipo_mantenimiento(String des_tipo_mantenimiento) {
		this.des_tipo_mantenimiento = des_tipo_mantenimiento;
	}

	/**
	 * @return the est_tipo_mantenimiento
	 */
	public String getEst_tipo_mantenimiento() {
		return est_tipo_mantenimiento;
	}

	/**
	 * @param est_tipo_mantenimiento the est_tipo_mantenimiento to set
	 */
	public void setEst_tipo_mantenimiento(String est_tipo_mantenimiento) {
		this.est_tipo_mantenimiento = est_tipo_mantenimiento;
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
		return "sibod_tipo_mantenimiento [id_tipo_mantenimiento=" + id_tipo_mantenimiento + ", nom_tipo_mantenimiento=" + nom_tipo_mantenimiento + ", des_tipo_mantenimiento="
				+ des_tipo_mantenimiento + ", est_tipo_mantenimiento=" + est_tipo_mantenimiento + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_tipo_mantenimiento.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_mantenimiento.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_mantenimiento.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
