package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

import bp.aplicaciones.extensiones.Fechas;

public class modelo_log_articulo_dn {

	private long id_log;
	private long id_articulo;
	private String des_log;
	private String mot_log;
	private String est_log;
	private String usu_ingresa;
	private String nom_usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_log_articulo_dn() {
		super();
	}

	/**
	 * @param id_log
	 * @param id_articulo
	 * @param des_log
	 * @param mot_log
	 * @param est_log
	 * @param usu_ingresa
	 * @param nom_usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usu_modifica
	 * @param fec_modifica
	 */
	public modelo_log_articulo_dn(long id_log, long id_articulo, String des_log, String mot_log, String est_log,
			String usu_ingresa, String nom_usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			String nom_usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_log = id_log;
		this.id_articulo = id_articulo;
		this.des_log = des_log;
		this.mot_log = mot_log;
		this.est_log = est_log;
		this.usu_ingresa = usu_ingresa;
		this.nom_usu_ingresa = nom_usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usu_modifica = nom_usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_log
	 * @param id_articulo
	 * @param des_log
	 * @param mot_log
	 * @param est_log
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_log_articulo_dn(long id_log, long id_articulo, String des_log, String mot_log, String est_log,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_log = id_log;
		this.id_articulo = id_articulo;
		this.des_log = des_log;
		this.mot_log = mot_log;
		this.est_log = est_log;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_log
	 */
	public long getId_log() {
		return id_log;
	}

	/**
	 * @param id_log the id_log to set
	 */
	public void setId_log(long id_log) {
		this.id_log = id_log;
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
	 * @return the des_log
	 */
	public String getDes_log() {
		return des_log;
	}

	/**
	 * @param des_log the des_log to set
	 */
	public void setDes_log(String des_log) {
		this.des_log = des_log;
	}

	/**
	 * @return the mot_log
	 */
	public String getMot_log() {
		return mot_log;
	}

	/**
	 * @param mot_log the mot_log to set
	 */
	public void setMot_log(String mot_log) {
		this.mot_log = mot_log;
	}

	/**
	 * @return the est_log
	 */
	public String getEst_log() {
		return est_log;
	}

	/**
	 * @param est_log the est_log to set
	 */
	public void setEst_log(String est_log) {
		this.est_log = est_log;
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

	/**
	 * @return the nom_usu_ingresa
	 */
	public String getNom_usu_ingresa() {
		return nom_usu_ingresa;
	}

	/**
	 * @param nom_usu_ingresa the nom_usu_ingresa to set
	 */
	public void setNom_usu_ingresa(String nom_usu_ingresa) {
		this.nom_usu_ingresa = nom_usu_ingresa;
	}

	/**
	 * @return the nom_usu_modifica
	 */
	public String getNom_usu_modifica() {
		return nom_usu_modifica;
	}

	/**
	 * @param nom_usu_modifica the nom_usu_modifica to set
	 */
	public void setNom_usu_modifica(String nom_usu_modifica) {
		this.nom_usu_modifica = nom_usu_modifica;
	}

	public String toStringEstado() {
		if (est_log.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}

	@Override
	public String toString() {
		return "modelo_log_articulo_dn [id_log=" + id_log + ", id_articulo=" + id_articulo + ", des_log=" + des_log
				+ ", mot_log=" + mot_log + ", est_log=" + est_log + ", usu_ingresa=" + usu_ingresa
				+ ", nom_usu_ingresa=" + nom_usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", nom_usu_modifica=" + nom_usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toFec_ingresa() {
		String fec_ingresa = "";
		Fechas fechas = new Fechas();
		fec_ingresa = fechas.obtenerFechaFormateada(this.fec_ingresa, "dd/MM/yyyy HH:mm");
		return fec_ingresa;
	}

}
