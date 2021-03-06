package bp.aplicaciones.sibod.modelo;

import java.sql.Timestamp;

public class modelo_mail_parametros {

	private long id_parametro;
	private String mail_remitente;
	private String pass_remitente;
	private String mail_receptor;
	private String smtp_host;
	private String smtp_starttls;
	private String smtp_puerto;
	private String smtp_auth;
	private String smtp_trust;
	private String smtp_debug;
	private String est_parametro;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_mail_parametros() {
		super();
	}

	/**
	 * @param id_parametro
	 * @param mail_remitente
	 * @param pass_remitente
	 * @param mail_receptor
	 * @param smtp_host
	 * @param smtp_starttls
	 * @param smtp_puerto
	 * @param smtp_auth
	 * @param smtp_trust
	 * @param smtp_debug
	 * @param est_parametro
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_mail_parametros(long id_parametro, String mail_remitente, String pass_remitente, String mail_receptor,
			String smtp_host, String smtp_starttls, String smtp_puerto, String smtp_auth, String smtp_trust,
			String smtp_debug, String est_parametro, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_parametro = id_parametro;
		this.mail_remitente = mail_remitente;
		this.pass_remitente = pass_remitente;
		this.mail_receptor = mail_receptor;
		this.smtp_host = smtp_host;
		this.smtp_starttls = smtp_starttls;
		this.smtp_puerto = smtp_puerto;
		this.smtp_auth = smtp_auth;
		this.smtp_trust = smtp_trust;
		this.smtp_debug = smtp_debug;
		this.est_parametro = est_parametro;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the mail_remitente
	 */
	public String getMail_remitente() {
		return mail_remitente;
	}

	/**
	 * @param mail_remitente the mail_remitente to set
	 */
	public void setMail_remitente(String mail_remitente) {
		this.mail_remitente = mail_remitente;
	}

	/**
	 * @return the pass_remitente
	 */
	public String getPass_remitente() {
		return pass_remitente;
	}

	/**
	 * @param pass_remitente the pass_remitente to set
	 */
	public void setPass_remitente(String pass_remitente) {
		this.pass_remitente = pass_remitente;
	}

	/**
	 * @return the mail_receptor
	 */
	public String getMail_receptor() {
		return mail_receptor;
	}

	/**
	 * @param mail_receptor the mail_receptor to set
	 */
	public void setMail_receptor(String mail_receptor) {
		this.mail_receptor = mail_receptor;
	}

	/**
	 * @return the smtp_host
	 */
	public String getSmtp_host() {
		return smtp_host;
	}

	/**
	 * @param smtp_host the smtp_host to set
	 */
	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	/**
	 * @return the smtp_starttls
	 */
	public String getSmtp_starttls() {
		return smtp_starttls;
	}

	/**
	 * @param smtp_starttls the smtp_starttls to set
	 */
	public void setSmtp_starttls(String smtp_starttls) {
		this.smtp_starttls = smtp_starttls;
	}

	/**
	 * @return the smtp_puerto
	 */
	public String getSmtp_puerto() {
		return smtp_puerto;
	}

	/**
	 * @param smtp_puerto the smtp_puerto to set
	 */
	public void setSmtp_puerto(String smtp_puerto) {
		this.smtp_puerto = smtp_puerto;
	}

	/**
	 * @return the smtp_auth
	 */
	public String getSmtp_auth() {
		return smtp_auth;
	}

	/**
	 * @param smtp_auth the smtp_auth to set
	 */
	public void setSmtp_auth(String smtp_auth) {
		this.smtp_auth = smtp_auth;
	}

	/**
	 * @return the smtp_trust
	 */
	public String getSmtp_trust() {
		return smtp_trust;
	}

	/**
	 * @param smtp_trust the smtp_trust to set
	 */
	public void setSmtp_trust(String smtp_trust) {
		this.smtp_trust = smtp_trust;
	}

	/**
	 * @return the smtp_debug
	 */
	public String getSmtp_debug() {
		return smtp_debug;
	}

	/**
	 * @param smtp_debug the smtp_debug to set
	 */
	public void setSmtp_debug(String smtp_debug) {
		this.smtp_debug = smtp_debug;
	}

	/**
	 * @return the est_parametro
	 */
	public String getEst_parametro() {
		return est_parametro;
	}

	/**
	 * @param est_parametro the est_parametro to set
	 */
	public void setEst_parametro(String est_parametro) {
		this.est_parametro = est_parametro;
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
		return "modelo_mail_parametros [id_parametro=" + id_parametro + ", mail_remitente=" + mail_remitente
				+ ", pass_remitente=" + pass_remitente + ", mail_receptor=" + mail_receptor + ", smtp_host=" + smtp_host
				+ ", smtp_starttls=" + smtp_starttls + ", smtp_puerto=" + smtp_puerto + ", smtp_auth=" + smtp_auth
				+ ", smtp_trust=" + smtp_trust + ", smtp_debug=" + smtp_debug + ", est_parametro=" + est_parametro
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

}
