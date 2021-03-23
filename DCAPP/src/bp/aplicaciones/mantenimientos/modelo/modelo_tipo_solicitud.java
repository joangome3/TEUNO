package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_solicitud {

	private long id_tipo_solicitud;
	private String nom_tipo_solicitud;
	private String est_tipo_solicitud;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_tipo_solicitud() {
		super();
	}

	/**
	 * @param id_tipo_solicitud
	 * @param nom_tipo_solicitud
	 * @param est_tipo_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_solicitud(long id_tipo_solicitud, String nom_tipo_solicitud, String est_tipo_solicitud,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tipo_solicitud = id_tipo_solicitud;
		this.nom_tipo_solicitud = nom_tipo_solicitud;
		this.est_tipo_solicitud = est_tipo_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_solicitud
	 */
	public long getId_tipo_solicitud() {
		return id_tipo_solicitud;
	}

	/**
	 * @param id_tipo_solicitud the id_tipo_solicitud to set
	 */
	public void setId_tipo_solicitud(long id_tipo_solicitud) {
		this.id_tipo_solicitud = id_tipo_solicitud;
	}

	/**
	 * @return the nom_tipo_solicitud
	 */
	public String getNom_tipo_solicitud() {
		return nom_tipo_solicitud;
	}

	/**
	 * @param nom_tipo_solicitud the nom_tipo_solicitud to set
	 */
	public void setNom_tipo_solicitud(String nom_tipo_solicitud) {
		this.nom_tipo_solicitud = nom_tipo_solicitud;
	}

	/**
	 * @return the est_tipo_solicitud
	 */
	public String getEst_tipo_solicitud() {
		return est_tipo_solicitud;
	}

	/**
	 * @param est_tipo_solicitud the est_tipo_solicitud to set
	 */
	public void setEst_tipo_solicitud(String est_tipo_solicitud) {
		this.est_tipo_solicitud = est_tipo_solicitud;
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
		return "modelo_tipo_solicitud [id_tipo_solicitud=" + id_tipo_solicitud + ", nom_tipo_solicitud="
				+ nom_tipo_solicitud + ", est_tipo_solicitud=" + est_tipo_solicitud + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

}
