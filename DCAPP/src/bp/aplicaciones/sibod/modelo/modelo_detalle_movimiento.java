package bp.aplicaciones.sibod.modelo;

import java.sql.Timestamp;

public class modelo_detalle_movimiento {

	private long id_det_movimiento;
	private long id_movimiento;
	private long id_articulo;
	private String tip_movimiento;
	private int sto_anterior;
	private int sto_actual;
	private int can_afectada;
	private long id_solicitante;
	private String tur_movimiento;
	private Timestamp fec_movimiento;
	private String obs_movimiento;
	private String es_alcance;
	private String est_det_movimiento;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_detalle_movimiento() {
		super();
	}

	/**
	 * @param id_det_movimiento
	 * @param id_movimiento
	 * @param id_articulo
	 * @param tip_movimiento
	 * @param sto_anterior
	 * @param sto_actual
	 * @param can_afectada
	 * @param id_solicitante
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param es_alcance
	 * @param est_det_movimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_detalle_movimiento(long id_det_movimiento, long id_movimiento, long id_articulo,
			String tip_movimiento, int sto_anterior, int sto_actual, int can_afectada, long id_solicitante,
			String tur_movimiento, Timestamp fec_movimiento, String obs_movimiento, String es_alcance,
			String est_det_movimiento, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_det_movimiento = id_det_movimiento;
		this.id_movimiento = id_movimiento;
		this.id_articulo = id_articulo;
		this.tip_movimiento = tip_movimiento;
		this.sto_anterior = sto_anterior;
		this.sto_actual = sto_actual;
		this.can_afectada = can_afectada;
		this.id_solicitante = id_solicitante;
		this.tur_movimiento = tur_movimiento;
		this.fec_movimiento = fec_movimiento;
		this.obs_movimiento = obs_movimiento;
		this.es_alcance = es_alcance;
		this.est_det_movimiento = est_det_movimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_det_movimiento
	 */
	public long getId_det_movimiento() {
		return id_det_movimiento;
	}

	/**
	 * @param id_det_movimiento the id_det_movimiento to set
	 */
	public void setId_det_movimiento(long id_det_movimiento) {
		this.id_det_movimiento = id_det_movimiento;
	}

	/**
	 * @return the id_movimiento
	 */
	public long getId_movimiento() {
		return id_movimiento;
	}

	/**
	 * @param id_movimiento the id_movimiento to set
	 */
	public void setId_movimiento(long id_movimiento) {
		this.id_movimiento = id_movimiento;
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
	 * @return the tip_movimiento
	 */
	public String getTip_movimiento() {
		return tip_movimiento;
	}

	/**
	 * @param tip_movimiento the tip_movimiento to set
	 */
	public void setTip_movimiento(String tip_movimiento) {
		this.tip_movimiento = tip_movimiento;
	}

	/**
	 * @return the sto_anterior
	 */
	public int getSto_anterior() {
		return sto_anterior;
	}

	/**
	 * @param sto_anterior the sto_anterior to set
	 */
	public void setSto_anterior(int sto_anterior) {
		this.sto_anterior = sto_anterior;
	}

	/**
	 * @return the sto_actual
	 */
	public int getSto_actual() {
		return sto_actual;
	}

	/**
	 * @param sto_actual the sto_actual to set
	 */
	public void setSto_actual(int sto_actual) {
		this.sto_actual = sto_actual;
	}

	/**
	 * @return the can_afectada
	 */
	public int getCan_afectada() {
		return can_afectada;
	}

	/**
	 * @param can_afectada the can_afectada to set
	 */
	public void setCan_afectada(int can_afectada) {
		this.can_afectada = can_afectada;
	}

	/**
	 * @return the id_solicitante
	 */
	public long getId_solicitante() {
		return id_solicitante;
	}

	/**
	 * @param id_solicitante the id_solicitante to set
	 */
	public void setId_solicitante(long id_solicitante) {
		this.id_solicitante = id_solicitante;
	}

	/**
	 * @return the tur_movimiento
	 */
	public String getTur_movimiento() {
		return tur_movimiento;
	}

	/**
	 * @param tur_movimiento the tur_movimiento to set
	 */
	public void setTur_movimiento(String tur_movimiento) {
		this.tur_movimiento = tur_movimiento;
	}

	/**
	 * @return the fec_movimiento
	 */
	public Timestamp getFec_movimiento() {
		return fec_movimiento;
	}

	/**
	 * @param fec_movimiento the fec_movimiento to set
	 */
	public void setFec_movimiento(Timestamp fec_movimiento) {
		this.fec_movimiento = fec_movimiento;
	}

	/**
	 * @return the obs_movimiento
	 */
	public String getObs_movimiento() {
		return obs_movimiento;
	}

	/**
	 * @param obs_movimiento the obs_movimiento to set
	 */
	public void setObs_movimiento(String obs_movimiento) {
		this.obs_movimiento = obs_movimiento;
	}

	/**
	 * @return the es_alcance
	 */
	public String getEs_alcance() {
		return es_alcance;
	}

	/**
	 * @param es_alcance the es_alcance to set
	 */
	public void setEs_alcance(String es_alcance) {
		this.es_alcance = es_alcance;
	}

	/**
	 * @return the est_det_movimiento
	 */
	public String getEst_det_movimiento() {
		return est_det_movimiento;
	}

	/**
	 * @param est_det_movimiento the est_det_movimiento to set
	 */
	public void setEst_det_movimiento(String est_det_movimiento) {
		this.est_det_movimiento = est_det_movimiento;
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
		return "modelo_detalle_movimiento [id_det_movimiento=" + id_det_movimiento + ", id_movimiento=" + id_movimiento
				+ ", id_articulo=" + id_articulo + ", tip_movimiento=" + tip_movimiento + ", sto_anterior="
				+ sto_anterior + ", sto_actual=" + sto_actual + ", can_afectada=" + can_afectada + ", id_solicitante="
				+ id_solicitante + ", tur_movimiento=" + tur_movimiento + ", fec_movimiento=" + fec_movimiento
				+ ", obs_movimiento=" + obs_movimiento + ", es_alcance=" + es_alcance + ", est_det_movimiento="
				+ est_det_movimiento + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
