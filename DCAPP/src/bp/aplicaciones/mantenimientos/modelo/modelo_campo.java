package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_campo {

	private long id_campo;
	private String nom_campo;
	private String est_campo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_campo() {
		super();
	}

	/**
	 * @param id_campo
	 * @param nom_campo
	 * @param est_campo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_campo(long id_campo, String nom_campo, String est_campo, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_campo = id_campo;
		this.nom_campo = nom_campo;
		this.est_campo = est_campo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_campo
	 */
	public long getId_campo() {
		return id_campo;
	}

	/**
	 * @param id_campo the id_campo to set
	 */
	public void setId_campo(long id_campo) {
		this.id_campo = id_campo;
	}

	/**
	 * @return the nom_campo
	 */
	public String getNom_campo() {
		return nom_campo;
	}

	/**
	 * @param nom_campo the nom_campo to set
	 */
	public void setNom_campo(String nom_campo) {
		this.nom_campo = nom_campo;
	}

	/**
	 * @return the est_campo
	 */
	public String getEst_campo() {
		return est_campo;
	}

	/**
	 * @param est_campo the est_campo to set
	 */
	public void setEst_campo(String est_campo) {
		this.est_campo = est_campo;
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
		return "modelo_campo [id_campo=" + id_campo + ", nom_campo=" + nom_campo + ", est_campo=" + est_campo
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_campo.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}

}
