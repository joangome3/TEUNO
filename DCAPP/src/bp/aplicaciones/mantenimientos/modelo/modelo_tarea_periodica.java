package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class modelo_tarea_periodica {

	private long id_tarea_periodica;
	private long id_cliente;
	private String ticket_externo;
	private long id_turno;
	private long id_tipo_servicio;
	private long id_tipo_tarea;
	private String descripcion;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_estado_bitacora;
	private String cumplimiento;
	private String est_tarea_periodica;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tarea_periodica() {
		super();
	}

	/**
	 * @param id_tarea_periodica
	 * @param id_cliente
	 * @param ticket_externo
	 * @param id_turno
	 * @param id_tipo_servicio
	 * @param id_tipo_tarea
	 * @param descripcion
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_estado_bitacora
	 * @param cumplimiento
	 * @param est_tarea_periodica
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tarea_periodica(long id_tarea_periodica, long id_cliente, String ticket_externo, long id_turno,
			long id_tipo_servicio, long id_tipo_tarea, String descripcion, Timestamp fec_inicio, Timestamp fec_fin,
			long id_estado_bitacora, String cumplimiento, String est_tarea_periodica, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tarea_periodica = id_tarea_periodica;
		this.id_cliente = id_cliente;
		this.ticket_externo = ticket_externo;
		this.id_turno = id_turno;
		this.id_tipo_servicio = id_tipo_servicio;
		this.id_tipo_tarea = id_tipo_tarea;
		this.descripcion = descripcion;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_estado_bitacora = id_estado_bitacora;
		this.cumplimiento = cumplimiento;
		this.est_tarea_periodica = est_tarea_periodica;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tarea_periodica
	 */
	public long getId_tarea_periodica() {
		return id_tarea_periodica;
	}

	/**
	 * @param id_tarea_periodica the id_tarea_periodica to set
	 */
	public void setId_tarea_periodica(long id_tarea_periodica) {
		this.id_tarea_periodica = id_tarea_periodica;
	}

	/**
	 * @return the id_cliente
	 */
	public long getId_cliente() {
		return id_cliente;
	}

	/**
	 * @param id_cliente the id_cliente to set
	 */
	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}

	/**
	 * @return the ticket_externo
	 */
	public String getTicket_externo() {
		return ticket_externo;
	}

	/**
	 * @param ticket_externo the ticket_externo to set
	 */
	public void setTicket_externo(String ticket_externo) {
		this.ticket_externo = ticket_externo;
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
	 * @return the id_tipo_servicio
	 */
	public long getId_tipo_servicio() {
		return id_tipo_servicio;
	}

	/**
	 * @param id_tipo_servicio the id_tipo_servicio to set
	 */
	public void setId_tipo_servicio(long id_tipo_servicio) {
		this.id_tipo_servicio = id_tipo_servicio;
	}

	/**
	 * @return the id_tipo_tarea
	 */
	public long getId_tipo_tarea() {
		return id_tipo_tarea;
	}

	/**
	 * @param id_tipo_tarea the id_tipo_tarea to set
	 */
	public void setId_tipo_tarea(long id_tipo_tarea) {
		this.id_tipo_tarea = id_tipo_tarea;
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
	 * @return the id_estado_bitacora
	 */
	public long getId_estado_bitacora() {
		return id_estado_bitacora;
	}

	/**
	 * @param id_estado_bitacora the id_estado_bitacora to set
	 */
	public void setId_estado_bitacora(long id_estado_bitacora) {
		this.id_estado_bitacora = id_estado_bitacora;
	}

	/**
	 * @return the cumplimiento
	 */
	public String getCumplimiento() {
		return cumplimiento;
	}

	/**
	 * @param cumplimiento the cumplimiento to set
	 */
	public void setCumplimiento(String cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	/**
	 * @return the est_tarea_periodica
	 */
	public String getEst_tarea_periodica() {
		return est_tarea_periodica;
	}

	/**
	 * @param est_tarea_periodica the est_tarea_periodica to set
	 */
	public void setEst_tarea_periodica(String est_tarea_periodica) {
		this.est_tarea_periodica = est_tarea_periodica;
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
		return "modelo_tarea_periodica [id_tarea_periodica=" + id_tarea_periodica + ", id_cliente=" + id_cliente
				+ ", ticket_externo=" + ticket_externo + ", id_turno=" + id_turno + ", id_tipo_servicio="
				+ id_tipo_servicio + ", id_tipo_tarea=" + id_tipo_tarea + ", descripcion=" + descripcion
				+ ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin + ", id_estado_bitacora=" + id_estado_bitacora
				+ ", cumplimiento=" + cumplimiento + ", est_tarea_periodica=" + est_tarea_periodica + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String toStringEstado() {
		String estado = "";
		if (est_tarea_periodica.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tarea_periodica.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tarea_periodica.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String verCumplimiento() {
		String cumplimiento = "";
		if (getCumplimiento().equals("C")) {
			cumplimiento = "CUMPLIDO";
		}
		if (getCumplimiento().equals("I")) {
			cumplimiento = "INCUMPLIDO";
		}
		return cumplimiento;
	}

	public String verFechaInicio() {
		String s = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getFec_inicio());
		return s;
	}

	public String verFechaFin() {
		String s = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getFec_fin());
		return s;
	}

}
