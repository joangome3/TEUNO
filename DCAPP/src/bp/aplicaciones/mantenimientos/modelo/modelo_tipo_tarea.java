package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_tarea {

	private long id_tipo_tarea;
	private String nom_tipo_tarea;
	private String des_tipo_tarea;
	private String est_tipo_tarea;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tipo_tarea() {
		super();
	}

	/**
	 * @param id_tipo_tarea
	 * @param nom_tipo_tarea
	 * @param des_tipo_tarea
	 * @param est_tipo_tarea
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_tarea(long id_tipo_tarea, String nom_tipo_tarea, String des_tipo_tarea,
			String est_tipo_tarea, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_tipo_tarea = id_tipo_tarea;
		this.nom_tipo_tarea = nom_tipo_tarea;
		this.des_tipo_tarea = des_tipo_tarea;
		this.est_tipo_tarea = est_tipo_tarea;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_tarea
	 */
	public long getId_tipo_tarea() {
		return id_tipo_tarea;
	}

	/**
	 * @param id_tipo_tarea the id_tipo_tarea to set
	 */
	public void setId_tipo_tarea(long id_tipo_tarea) {
		this.id_tipo_tarea = id_tipo_tarea;
	}

	/**
	 * @return the nom_tipo_tarea
	 */
	public String getNom_tipo_tarea() {
		return nom_tipo_tarea;
	}

	/**
	 * @param nom_tipo_tarea the nom_tipo_tarea to set
	 */
	public void setNom_tipo_tarea(String nom_tipo_tarea) {
		this.nom_tipo_tarea = nom_tipo_tarea;
	}

	/**
	 * @return the des_tipo_tarea
	 */
	public String getDes_tipo_tarea() {
		return des_tipo_tarea;
	}

	/**
	 * @param des_tipo_tarea the des_tipo_tarea to set
	 */
	public void setDes_tipo_tarea(String des_tipo_tarea) {
		this.des_tipo_tarea = des_tipo_tarea;
	}

	/**
	 * @return the est_tipo_tarea
	 */
	public String getEst_tipo_tarea() {
		return est_tipo_tarea;
	}

	/**
	 * @param est_tipo_tarea the est_tipo_tarea to set
	 */
	public void setEst_tipo_tarea(String est_tipo_tarea) {
		this.est_tipo_tarea = est_tipo_tarea;
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
		return "sibod_tipo_tarea [id_tipo_tarea=" + id_tipo_tarea + ", nom_tipo_tarea=" + nom_tipo_tarea
				+ ", des_tipo_tarea=" + des_tipo_tarea + ", est_tipo_tarea=" + est_tipo_tarea + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_tipo_tarea.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_tarea.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_tarea.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
