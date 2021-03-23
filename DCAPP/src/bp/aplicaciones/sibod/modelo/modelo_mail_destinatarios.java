package bp.aplicaciones.sibod.modelo;

import java.sql.Timestamp;

public class modelo_mail_destinatarios {

	private long id_destinatario;
	private long id_parametro;
	private long id_tipo_destinatario;
	private String nom_tipo_destinatario;
	private String mail_destinatario;
	private String est_destinatario;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_mail_destinatarios() {
		super();
	}

	/**
	 * @param id_destinatario
	 * @param id_parametro
	 * @param id_tipo_destinatario
	 * @param nom_tipo_destinatario
	 * @param mail_destinatario
	 * @param est_destinatario
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_mail_destinatarios(long id_destinatario, long id_parametro, long id_tipo_destinatario,
			String nom_tipo_destinatario, String mail_destinatario, String est_destinatario, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_destinatario = id_destinatario;
		this.id_parametro = id_parametro;
		this.id_tipo_destinatario = id_tipo_destinatario;
		this.nom_tipo_destinatario = nom_tipo_destinatario;
		this.mail_destinatario = mail_destinatario;
		this.est_destinatario = est_destinatario;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_destinatario
	 */
	public long getId_destinatario() {
		return id_destinatario;
	}

	/**
	 * @param id_destinatario the id_destinatario to set
	 */
	public void setId_destinatario(long id_destinatario) {
		this.id_destinatario = id_destinatario;
	}

	/**
	 * @return the id_parametro
	 */
	public long getId_parametro() {
		return id_parametro;
	}

	/**
	 * @param id_parametro the id_parametro to set
	 */
	public void setId_parametro(long id_parametro) {
		this.id_parametro = id_parametro;
	}

	/**
	 * @return the id_tipo_destinatario
	 */
	public long getId_tipo_destinatario() {
		return id_tipo_destinatario;
	}

	/**
	 * @param id_tipo_destinatario the id_tipo_destinatario to set
	 */
	public void setId_tipo_destinatario(long id_tipo_destinatario) {
		this.id_tipo_destinatario = id_tipo_destinatario;
	}

	/**
	 * @return the nom_tipo_destinatario
	 */
	public String getNom_tipo_destinatario() {
		return nom_tipo_destinatario;
	}

	/**
	 * @param nom_tipo_destinatario the nom_tipo_destinatario to set
	 */
	public void setNom_tipo_destinatario(String nom_tipo_destinatario) {
		this.nom_tipo_destinatario = nom_tipo_destinatario;
	}

	/**
	 * @return the mail_destinatario
	 */
	public String getMail_destinatario() {
		return mail_destinatario;
	}

	/**
	 * @param mail_destinatario the mail_destinatario to set
	 */
	public void setMail_destinatario(String mail_destinatario) {
		this.mail_destinatario = mail_destinatario;
	}

	/**
	 * @return the est_destinatario
	 */
	public String getEst_destinatario() {
		return est_destinatario;
	}

	/**
	 * @param est_destinatario the est_destinatario to set
	 */
	public void setEst_destinatario(String est_destinatario) {
		this.est_destinatario = est_destinatario;
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
		return "modelo_mail_destinatarios [id_destinatario=" + id_destinatario + ", id_parametro=" + id_parametro
				+ ", id_tipo_destinatario=" + id_tipo_destinatario + ", nom_tipo_destinatario=" + nom_tipo_destinatario
				+ ", mail_destinatario=" + mail_destinatario + ", est_destinatario=" + est_destinatario
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

}
