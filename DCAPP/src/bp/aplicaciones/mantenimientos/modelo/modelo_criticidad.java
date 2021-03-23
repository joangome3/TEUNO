package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_criticidad {

	private long id_criticidad;
	private String nom_criticidad;
	private String des_criticidad;
	private String est_criticidad;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_criticidad() {
		super();
	}

	/**
	 * @param id_criticidad
	 * @param nom_criticidad
	 * @param des_criticidad
	 * @param est_criticidad
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_criticidad(long id_criticidad, String nom_criticidad, String des_criticidad, String est_criticidad,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_criticidad = id_criticidad;
		this.nom_criticidad = nom_criticidad;
		this.des_criticidad = des_criticidad;
		this.est_criticidad = est_criticidad;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_criticidad
	 */
	public long getId_criticidad() {
		return id_criticidad;
	}

	/**
	 * @param id_criticidad the id_criticidad to set
	 */
	public void setId_criticidad(long id_criticidad) {
		this.id_criticidad = id_criticidad;
	}

	/**
	 * @return the nom_criticidad
	 */
	public String getNom_criticidad() {
		return nom_criticidad;
	}

	/**
	 * @param nom_criticidad the nom_criticidad to set
	 */
	public void setNom_criticidad(String nom_criticidad) {
		this.nom_criticidad = nom_criticidad;
	}

	/**
	 * @return the des_criticidad
	 */
	public String getDes_criticidad() {
		return des_criticidad;
	}

	/**
	 * @param des_criticidad the des_criticidad to set
	 */
	public void setDes_criticidad(String des_criticidad) {
		this.des_criticidad = des_criticidad;
	}

	/**
	 * @return the est_criticidad
	 */
	public String getEst_criticidad() {
		return est_criticidad;
	}

	/**
	 * @param est_criticidad the est_criticidad to set
	 */
	public void setEst_criticidad(String est_criticidad) {
		this.est_criticidad = est_criticidad;
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
		return "sibod_criticidad [id_criticidad=" + id_criticidad + ", nom_criticidad=" + nom_criticidad + ", des_criticidad="
				+ des_criticidad + ", est_criticidad=" + est_criticidad + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_criticidad.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_criticidad.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_criticidad.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

}
