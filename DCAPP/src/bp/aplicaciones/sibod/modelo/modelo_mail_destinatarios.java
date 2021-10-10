package bp.aplicaciones.sibod.modelo;

import java.sql.Timestamp;

public class modelo_mail_destinatarios {

	private long id_destinatario;
	private long id_mail_parametro;
	private String email_destinatario;
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
	 * @param id_mail_parametro
	 * @param email_destinatario
	 * @param est_destinatario
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_mail_destinatarios(long id_destinatario, long id_mail_parametro, String email_destinatario,
			String est_destinatario, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_destinatario = id_destinatario;
		this.id_mail_parametro = id_mail_parametro;
		this.email_destinatario = email_destinatario;
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
	 * @return the id_mail_parametro
	 */
	public long getId_mail_parametro() {
		return id_mail_parametro;
	}

	/**
	 * @param id_mail_parametro the id_mail_parametro to set
	 */
	public void setId_mail_parametro(long id_mail_parametro) {
		this.id_mail_parametro = id_mail_parametro;
	}

	/**
	 * @return the email_destinatario
	 */
	public String getEmail_destinatario() {
		return email_destinatario;
	}

	/**
	 * @param email_destinatario the email_destinatario to set
	 */
	public void setEmail_destinatario(String email_destinatario) {
		this.email_destinatario = email_destinatario;
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
		return "modelo_mail_destinatarios [id_destinatario=" + id_destinatario + ", id_mail_parametro="
				+ id_mail_parametro + ", email_destinatario=" + email_destinatario + ", est_destinatario="
				+ est_destinatario + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
