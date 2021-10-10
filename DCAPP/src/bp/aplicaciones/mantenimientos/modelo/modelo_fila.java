package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;


public class modelo_fila {

	private long id_fila;
	private String nom_fila;
	private String des_fila;
	private long id_localidad;
	private String est_fila;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_fila() {
		super();
	}

	/**
	 * @param id_fila
	 * @param nom_fila
	 * @param des_fila
	 * @param id_localidad
	 * @param est_fila
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_fila(long id_fila, String nom_fila, String des_fila, long id_localidad, String est_fila,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_fila = id_fila;
		this.nom_fila = nom_fila;
		this.des_fila = des_fila;
		this.id_localidad = id_localidad;
		this.est_fila = est_fila;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_fila
	 */
	public long getId_fila() {
		return id_fila;
	}

	/**
	 * @param id_fila the id_fila to set
	 */
	public void setId_fila(long id_fila) {
		this.id_fila = id_fila;
	}

	/**
	 * @return the nom_fila
	 */
	public String getNom_fila() {
		return nom_fila;
	}

	/**
	 * @param nom_fila the nom_fila to set
	 */
	public void setNom_fila(String nom_fila) {
		this.nom_fila = nom_fila;
	}

	/**
	 * @return the des_fila
	 */
	public String getDes_fila() {
		return des_fila;
	}

	/**
	 * @param des_fila the des_fila to set
	 */
	public void setDes_fila(String des_fila) {
		this.des_fila = des_fila;
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
	 * @return the est_fila
	 */
	public String getEst_fila() {
		return est_fila;
	}

	/**
	 * @param est_fila the est_fila to set
	 */
	public void setEst_fila(String est_fila) {
		this.est_fila = est_fila;
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
		return "modelo_fila [id_fila=" + id_fila + ", nom_fila=" + nom_fila + ", des_fila=" + des_fila
				+ ", id_localidad=" + id_localidad + ", est_fila=" + est_fila + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

}
