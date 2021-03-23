package bp.aplicaciones.bitacora.modelo;

import java.sql.Timestamp;

public class modelo_registra_turno {

	private long id_registra_turno;
	private long id_turno;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_localidad;
	private String est_registra_turno;
	private String usu_inicia;
	private String usu_termina;

	/**
	 * 
	 */
	public modelo_registra_turno() {
		super();
	}

	/**
	 * @param id_registra_turno
	 * @param id_turno
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_localidad
	 * @param est_registra_turno
	 * @param usu_inicia
	 * @param usu_termina
	 */
	public modelo_registra_turno(long id_registra_turno, long id_turno, Timestamp fec_inicio, Timestamp fec_fin,
			long id_localidad, String est_registra_turno, String usu_inicia, String usu_termina) {
		super();
		this.id_registra_turno = id_registra_turno;
		this.id_turno = id_turno;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_localidad = id_localidad;
		this.est_registra_turno = est_registra_turno;
		this.usu_inicia = usu_inicia;
		this.usu_termina = usu_termina;
	}

	/**
	 * @return the id_registra_turno
	 */
	public long getId_registra_turno() {
		return id_registra_turno;
	}

	/**
	 * @param id_registra_turno the id_registra_turno to set
	 */
	public void setId_registra_turno(long id_registra_turno) {
		this.id_registra_turno = id_registra_turno;
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
	 * @return the fec_inicio
	 */
	public Timestamp getFec_inicio() {
		return fec_inicio;
	}

	/**
	 * @param fec_inicio the fec_inicio to set
	 */
	public void setFec_inicio(Timestamp fec_inicio) {
		this.fec_inicio = fec_inicio;
	}

	/**
	 * @return the fec_fin
	 */
	public Timestamp getFec_fin() {
		return fec_fin;
	}

	/**
	 * @param fec_fin the fec_fin to set
	 */
	public void setFec_fin(Timestamp fec_fin) {
		this.fec_fin = fec_fin;
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
	 * @return the est_registra_turno
	 */
	public String getEst_registra_turno() {
		return est_registra_turno;
	}

	/**
	 * @param est_registra_turno the est_registra_turno to set
	 */
	public void setEst_registra_turno(String est_registra_turno) {
		this.est_registra_turno = est_registra_turno;
	}

	/**
	 * @return the usu_inicia
	 */
	public String getUsu_inicia() {
		return usu_inicia;
	}

	/**
	 * @param usu_inicia the usu_inicia to set
	 */
	public void setUsu_inicia(String usu_inicia) {
		this.usu_inicia = usu_inicia;
	}

	/**
	 * @return the usu_termina
	 */
	public String getUsu_termina() {
		return usu_termina;
	}

	/**
	 * @param usu_termina the usu_termina to set
	 */
	public void setUsu_termina(String usu_termina) {
		this.usu_termina = usu_termina;
	}

	@Override
	public String toString() {
		return "modelo_registra_turno [id_registra_turno=" + id_registra_turno + ", id_turno=" + id_turno
				+ ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin + ", id_localidad=" + id_localidad
				+ ", est_registra_turno=" + est_registra_turno + ", usu_inicia=" + usu_inicia + ", usu_termina="
				+ usu_termina + "]";
	}

}
