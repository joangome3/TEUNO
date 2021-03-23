package bp.aplicaciones.controlcambio.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class modelo_control_cambio {

	private long id_control_cambio;
	private long id_empresa_1;
	private long id_empresa_2;
	private long id_tipo_sistema;
	private long id_infraestructura;
	private long id_tipo_mantenimiento;
	private long id_criticidad;
	private long id_solicitante_1;
	private Timestamp fec_programada;
	private String descripcion;
	private long id_localidad;
	private String est_control_cambio;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_control_cambio() {
		super();
	}

	/**
	 * @param id_control_cambio
	 * @param id_empresa_1
	 * @param id_empresa_2
	 * @param id_tipo_sistema
	 * @param id_infraestructura
	 * @param id_tipo_mantenimiento
	 * @param id_criticidad
	 * @param id_solicitante_1
	 * @param fec_programada
	 * @param descripcion
	 * @param id_localidad
	 * @param est_control_cambio
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_control_cambio(long id_control_cambio, long id_empresa_1, long id_empresa_2, long id_tipo_sistema,
			long id_infraestructura, long id_tipo_mantenimiento, long id_criticidad, long id_solicitante_1,
			Timestamp fec_programada, String descripcion, long id_localidad, String est_control_cambio,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_control_cambio = id_control_cambio;
		this.id_empresa_1 = id_empresa_1;
		this.id_empresa_2 = id_empresa_2;
		this.id_tipo_sistema = id_tipo_sistema;
		this.id_infraestructura = id_infraestructura;
		this.id_tipo_mantenimiento = id_tipo_mantenimiento;
		this.id_criticidad = id_criticidad;
		this.id_solicitante_1 = id_solicitante_1;
		this.fec_programada = fec_programada;
		this.descripcion = descripcion;
		this.id_localidad = id_localidad;
		this.est_control_cambio = est_control_cambio;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_control_cambio
	 */
	public long getId_control_cambio() {
		return id_control_cambio;
	}

	/**
	 * @param id_control_cambio the id_control_cambio to set
	 */
	public void setId_control_cambio(long id_control_cambio) {
		this.id_control_cambio = id_control_cambio;
	}

	/**
	 * @return the id_empresa_1
	 */
	public long getId_empresa_1() {
		return id_empresa_1;
	}

	/**
	 * @param id_empresa_1 the id_empresa_1 to set
	 */
	public void setId_empresa_1(long id_empresa_1) {
		this.id_empresa_1 = id_empresa_1;
	}

	/**
	 * @return the id_empresa_2
	 */
	public long getId_empresa_2() {
		return id_empresa_2;
	}

	/**
	 * @param id_empresa_2 the id_empresa_2 to set
	 */
	public void setId_empresa_2(long id_empresa_2) {
		this.id_empresa_2 = id_empresa_2;
	}

	/**
	 * @return the id_tipo_sistema
	 */
	public long getId_tipo_sistema() {
		return id_tipo_sistema;
	}

	/**
	 * @param id_tipo_sistema the id_tipo_sistema to set
	 */
	public void setId_tipo_sistema(long id_tipo_sistema) {
		this.id_tipo_sistema = id_tipo_sistema;
	}

	/**
	 * @return the id_infraestructura
	 */
	public long getId_infraestructura() {
		return id_infraestructura;
	}

	/**
	 * @param id_infraestructura the id_infraestructura to set
	 */
	public void setId_infraestructura(long id_infraestructura) {
		this.id_infraestructura = id_infraestructura;
	}

	/**
	 * @return the id_tipo_mantenimiento
	 */
	public long getId_tipo_mantenimiento() {
		return id_tipo_mantenimiento;
	}

	/**
	 * @param id_tipo_mantenimiento the id_tipo_mantenimiento to set
	 */
	public void setId_tipo_mantenimiento(long id_tipo_mantenimiento) {
		this.id_tipo_mantenimiento = id_tipo_mantenimiento;
	}

	/**
	 * @return the id_criticidad
	 */
	public long getId_criticidad() {
		return id_criticidad;
	}

	/**
	 * @param id_criticidad the id_criticidad to set
	 */
	public void setId_criticidad(long id_criticidad) {
		this.id_criticidad = id_criticidad;
	}

	/**
	 * @return the id_solicitante_1
	 */
	public long getId_solicitante_1() {
		return id_solicitante_1;
	}

	/**
	 * @param id_solicitante_1 the id_solicitante_1 to set
	 */
	public void setId_solicitante_1(long id_solicitante_1) {
		this.id_solicitante_1 = id_solicitante_1;
	}

	/**
	 * @return the fec_programada
	 */
	public Timestamp getFec_programada() {
		return fec_programada;
	}

	/**
	 * @param fec_programada the fec_programada to set
	 */
	public void setFec_programada(Timestamp fec_programada) {
		this.fec_programada = fec_programada;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * @return the est_control_cambio
	 */
	public String getEst_control_cambio() {
		return est_control_cambio;
	}

	/**
	 * @param est_control_cambio the est_control_cambio to set
	 */
	public void setEst_control_cambio(String est_control_cambio) {
		this.est_control_cambio = est_control_cambio;
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
		return "modelo_control_cambio [id_control_cambio=" + id_control_cambio + ", id_empresa_1=" + id_empresa_1
				+ ", id_empresa_2=" + id_empresa_2 + ", id_tipo_sistema=" + id_tipo_sistema + ", id_infraestructura="
				+ id_infraestructura + ", id_tipo_mantenimiento=" + id_tipo_mantenimiento + ", id_criticidad="
				+ id_criticidad + ", id_solicitante_1=" + id_solicitante_1 + ", fec_programada=" + fec_programada
				+ ", descripcion=" + descripcion + ", id_localidad=" + id_localidad + ", est_control_cambio="
				+ est_control_cambio + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_control_cambio.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_control_cambio.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_control_cambio.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String toStringFecha() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_programada());
		return s;
	}

}
