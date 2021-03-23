package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Time;
import java.sql.Timestamp;

public class modelo_turno {

	private long id_turno;
	private String nom_turno;
	private Time hora_inicio;
	private Time hora_fin;
	private String es_extendido;
	private String est_turno;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_turno() {
		super();
	}

	/**
	 * @param id_turno
	 * @param nom_turno
	 * @param hora_inicio
	 * @param hora_fin
	 * @param es_extendido
	 * @param est_turno
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_turno(long id_turno, String nom_turno, Time hora_inicio, Time hora_fin, String es_extendido,
			String est_turno, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_turno = id_turno;
		this.nom_turno = nom_turno;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
		this.es_extendido = es_extendido;
		this.est_turno = est_turno;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_turno
	 */
	public long getId_turno() {
		return id_turno;
	}

	/**
	 * @param id_turno the id_turno to set
	 */
	public void setId_turno(long id_turno) {
		this.id_turno = id_turno;
	}

	/**
	 * @return the nom_turno
	 */
	public String getNom_turno() {
		return nom_turno;
	}

	/**
	 * @param nom_turno the nom_turno to set
	 */
	public void setNom_turno(String nom_turno) {
		this.nom_turno = nom_turno;
	}

	/**
	 * @return the hora_inicio
	 */
	public Time getHora_inicio() {
		return hora_inicio;
	}

	/**
	 * @param hora_inicio the hora_inicio to set
	 */
	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	/**
	 * @return the hora_fin
	 */
	public Time getHora_fin() {
		return hora_fin;
	}

	/**
	 * @param hora_fin the hora_fin to set
	 */
	public void setHora_fin(Time hora_fin) {
		this.hora_fin = hora_fin;
	}

	/**
	 * @return the es_extendido
	 */
	public String getEs_extendido() {
		return es_extendido;
	}

	/**
	 * @param es_extendido the es_extendido to set
	 */
	public void setEs_extendido(String es_extendido) {
		this.es_extendido = es_extendido;
	}

	/**
	 * @return the est_turno
	 */
	public String getEst_turno() {
		return est_turno;
	}

	/**
	 * @param est_turno the est_turno to set
	 */
	public void setEst_turno(String est_turno) {
		this.est_turno = est_turno;
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
		return "modelo_turno [id_turno=" + id_turno + ", nom_turno=" + nom_turno + ", hora_inicio=" + hora_inicio
				+ ", hora_fin=" + hora_fin + ", es_extendido=" + es_extendido + ", est_turno=" + est_turno
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

}
