package bp.aplicaciones.cintas.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class modelo_movimiento_dn {

	private long id_movimiento;
	private String tck_movimiento;
	private String tip_pedido;
	private Timestamp fec_solicitud;
	private long hor_solicitud;
	private Timestamp fec_respuesta;
	private long hor_respuesta;
	private Timestamp fec_ejecucion;
	private long hor_ejecucion;
	private long id_localidad;
	private String nom_localidad;
	private long id_solicitante;
	private String nom_solicitante;
	private long id_usuario;
	private String nom_usuario;
	private String tur_movimiento;
	private String nom_turno;
	private Timestamp fec_movimiento;
	private String obs_movimiento;
	private String usu_revision_1;
	private String nom_usuario_revisa_1;
	private String usu_revision_2;
	private String nom_usuario_revisa_2;
	private String usu_revision_3;
	private String nom_usuario_revisa_3;
	private String est_movimiento;
	private String est_validacion;
	private String usu_ingresa;
	private String nom_usuario_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usuario_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_movimiento_dn() {
		super();
	}

	/**
	 * @param id_movimiento
	 * @param tck_movimiento
	 * @param tip_pedido
	 * @param fec_solicitud
	 * @param hor_solicitud
	 * @param fec_respuesta
	 * @param hor_respuesta
	 * @param fec_ejecucion
	 * @param hor_ejecucion
	 * @param id_localidad
	 * @param id_solicitante
	 * @param id_usuario
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param usu_revision_1
	 * @param usu_revision_2
	 * @param usu_revision_3
	 * @param est_movimiento
	 * @param est_validacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_movimiento_dn(long id_movimiento, String tck_movimiento, String tip_pedido, Timestamp fec_solicitud,
			long hor_solicitud, Timestamp fec_respuesta, long hor_respuesta, Timestamp fec_ejecucion,
			long hor_ejecucion, long id_localidad, long id_solicitante, long id_usuario, String tur_movimiento,
			Timestamp fec_movimiento, String obs_movimiento, String usu_revision_1, String usu_revision_2,
			String usu_revision_3, String est_movimiento, String est_validacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_movimiento = id_movimiento;
		this.tck_movimiento = tck_movimiento;
		this.tip_pedido = tip_pedido;
		this.fec_solicitud = fec_solicitud;
		this.hor_solicitud = hor_solicitud;
		this.fec_respuesta = fec_respuesta;
		this.hor_respuesta = hor_respuesta;
		this.fec_ejecucion = fec_ejecucion;
		this.hor_ejecucion = hor_ejecucion;
		this.id_localidad = id_localidad;
		this.id_solicitante = id_solicitante;
		this.id_usuario = id_usuario;
		this.tur_movimiento = tur_movimiento;
		this.fec_movimiento = fec_movimiento;
		this.obs_movimiento = obs_movimiento;
		this.usu_revision_1 = usu_revision_1;
		this.usu_revision_2 = usu_revision_2;
		this.usu_revision_3 = usu_revision_3;
		this.est_movimiento = est_movimiento;
		this.est_validacion = est_validacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_movimiento
	 * @param tck_movimiento
	 * @param tip_pedido
	 * @param fec_solicitud
	 * @param hor_solicitud
	 * @param fec_respuesta
	 * @param hor_respuesta
	 * @param fec_ejecucion
	 * @param hor_ejecucion
	 * @param id_localidad
	 * @param nom_localidad
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param id_usuario
	 * @param nom_usuario
	 * @param tur_movimiento
	 * @param nom_turno
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param usu_revision_1
	 * @param nom_usuario_revisa_1
	 * @param usu_revision_2
	 * @param nom_usuario_revisa_2
	 * @param usu_revision_3
	 * @param nom_usuario_revisa_3
	 * @param est_movimiento
	 * @param est_validacion
	 * @param usu_ingresa
	 * @param nom_usuario_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usuario_modifica
	 * @param fec_modifica
	 */
	public modelo_movimiento_dn(long id_movimiento, String tck_movimiento, String tip_pedido, Timestamp fec_solicitud,
			long hor_solicitud, Timestamp fec_respuesta, long hor_respuesta, Timestamp fec_ejecucion,
			long hor_ejecucion, long id_localidad, String nom_localidad, long id_solicitante, String nom_solicitante,
			long id_usuario, String nom_usuario, String tur_movimiento, String nom_turno, Timestamp fec_movimiento,
			String obs_movimiento, String usu_revision_1, String nom_usuario_revisa_1, String usu_revision_2,
			String nom_usuario_revisa_2, String usu_revision_3, String nom_usuario_revisa_3, String est_movimiento,
			String est_validacion, String usu_ingresa, String nom_usuario_ingresa, Timestamp fec_ingresa,
			String usu_modifica, String nom_usuario_modifica, Timestamp fec_modifica) {
		super();
		this.id_movimiento = id_movimiento;
		this.tck_movimiento = tck_movimiento;
		this.tip_pedido = tip_pedido;
		this.fec_solicitud = fec_solicitud;
		this.hor_solicitud = hor_solicitud;
		this.fec_respuesta = fec_respuesta;
		this.hor_respuesta = hor_respuesta;
		this.fec_ejecucion = fec_ejecucion;
		this.hor_ejecucion = hor_ejecucion;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.id_usuario = id_usuario;
		this.nom_usuario = nom_usuario;
		this.tur_movimiento = tur_movimiento;
		this.nom_turno = nom_turno;
		this.fec_movimiento = fec_movimiento;
		this.obs_movimiento = obs_movimiento;
		this.usu_revision_1 = usu_revision_1;
		this.nom_usuario_revisa_1 = nom_usuario_revisa_1;
		this.usu_revision_2 = usu_revision_2;
		this.nom_usuario_revisa_2 = nom_usuario_revisa_2;
		this.usu_revision_3 = usu_revision_3;
		this.nom_usuario_revisa_3 = nom_usuario_revisa_3;
		this.est_movimiento = est_movimiento;
		this.est_validacion = est_validacion;
		this.usu_ingresa = usu_ingresa;
		this.nom_usuario_ingresa = nom_usuario_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usuario_modifica = nom_usuario_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the tck_movimiento
	 */
	public String getTck_movimiento() {
		return tck_movimiento;
	}

	/**
	 * @param tck_movimiento the tck_movimiento to set
	 */
	public void setTck_movimiento(String tck_movimiento) {
		this.tck_movimiento = tck_movimiento;
	}

	/**
	 * @return the tip_pedido
	 */
	public String getTip_pedido() {
		return tip_pedido;
	}

	/**
	 * @param tip_pedido the tip_pedido to set
	 */
	public void setTip_pedido(String tip_pedido) {
		this.tip_pedido = tip_pedido;
	}

	/**
	 * @return the fec_solicitud
	 */
	public Timestamp getFec_solicitud() {
		return fec_solicitud;
	}

	/**
	 * @param fec_solicitud the fec_solicitud to set
	 */
	public void setFec_solicitud(Timestamp fec_solicitud) {
		this.fec_solicitud = fec_solicitud;
	}

	/**
	 * @return the hor_solicitud
	 */
	public long getHor_solicitud() {
		return hor_solicitud;
	}

	/**
	 * @param hor_solicitud the hor_solicitud to set
	 */
	public void setHor_solicitud(long hor_solicitud) {
		this.hor_solicitud = hor_solicitud;
	}

	/**
	 * @return the fec_respuesta
	 */
	public Timestamp getFec_respuesta() {
		return fec_respuesta;
	}

	/**
	 * @param fec_respuesta the fec_respuesta to set
	 */
	public void setFec_respuesta(Timestamp fec_respuesta) {
		this.fec_respuesta = fec_respuesta;
	}

	/**
	 * @return the hor_respuesta
	 */
	public long getHor_respuesta() {
		return hor_respuesta;
	}

	/**
	 * @param hor_respuesta the hor_respuesta to set
	 */
	public void setHor_respuesta(long hor_respuesta) {
		this.hor_respuesta = hor_respuesta;
	}

	/**
	 * @return the fec_ejecucion
	 */
	public Timestamp getFec_ejecucion() {
		return fec_ejecucion;
	}

	/**
	 * @param fec_ejecucion the fec_ejecucion to set
	 */
	public void setFec_ejecucion(Timestamp fec_ejecucion) {
		this.fec_ejecucion = fec_ejecucion;
	}

	/**
	 * @return the hor_ejecucion
	 */
	public long getHor_ejecucion() {
		return hor_ejecucion;
	}

	/**
	 * @param hor_ejecucion the hor_ejecucion to set
	 */
	public void setHor_ejecucion(long hor_ejecucion) {
		this.hor_ejecucion = hor_ejecucion;
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
	 * @return the nom_localidad
	 */
	public String getNom_localidad() {
		return nom_localidad;
	}

	/**
	 * @param nom_localidad the nom_localidad to set
	 */
	public void setNom_localidad(String nom_localidad) {
		this.nom_localidad = nom_localidad;
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
	 * @return the nom_solicitante
	 */
	public String getNom_solicitante() {
		return nom_solicitante;
	}

	/**
	 * @param nom_solicitante the nom_solicitante to set
	 */
	public void setNom_solicitante(String nom_solicitante) {
		this.nom_solicitante = nom_solicitante;
	}

	/**
	 * @return the id_usuario
	 */
	public long getId_usuario() {
		return id_usuario;
	}

	/**
	 * @param id_usuario the id_usuario to set
	 */
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * @return the nom_usuario
	 */
	public String getNom_usuario() {
		return nom_usuario;
	}

	/**
	 * @param nom_usuario the nom_usuario to set
	 */
	public void setNom_usuario(String nom_usuario) {
		this.nom_usuario = nom_usuario;
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
	 * @return the usu_revision_1
	 */
	public String getUsu_revision_1() {
		return usu_revision_1;
	}

	/**
	 * @param usu_revision_1 the usu_revision_1 to set
	 */
	public void setUsu_revision_1(String usu_revision_1) {
		this.usu_revision_1 = usu_revision_1;
	}

	/**
	 * @return the usu_revision_2
	 */
	public String getUsu_revision_2() {
		return usu_revision_2;
	}

	/**
	 * @param usu_revision_2 the usu_revision_2 to set
	 */
	public void setUsu_revision_2(String usu_revision_2) {
		this.usu_revision_2 = usu_revision_2;
	}

	/**
	 * @return the usu_revision_3
	 */
	public String getUsu_revision_3() {
		return usu_revision_3;
	}

	/**
	 * @param usu_revision_3 the usu_revision_3 to set
	 */
	public void setUsu_revision_3(String usu_revision_3) {
		this.usu_revision_3 = usu_revision_3;
	}

	/**
	 * @return the est_movimiento
	 */
	public String getEst_movimiento() {
		return est_movimiento;
	}

	/**
	 * @param est_movimiento the est_movimiento to set
	 */
	public void setEst_movimiento(String est_movimiento) {
		this.est_movimiento = est_movimiento;
	}

	/**
	 * @return the est_validacion
	 */
	public String getEst_validacion() {
		return est_validacion;
	}

	/**
	 * @param est_validacion the est_validacion to set
	 */
	public void setEst_validacion(String est_validacion) {
		this.est_validacion = est_validacion;
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
	 * @return the nom_usuario_revisa_1
	 */
	public String getNom_usuario_revisa_1() {
		return nom_usuario_revisa_1;
	}

	/**
	 * @param nom_usuario_revisa_1 the nom_usuario_revisa_1 to set
	 */
	public void setNom_usuario_revisa_1(String nom_usuario_revisa_1) {
		this.nom_usuario_revisa_1 = nom_usuario_revisa_1;
	}

	/**
	 * @return the nom_usuario_revisa_2
	 */
	public String getNom_usuario_revisa_2() {
		return nom_usuario_revisa_2;
	}

	/**
	 * @param nom_usuario_revisa_2 the nom_usuario_revisa_2 to set
	 */
	public void setNom_usuario_revisa_2(String nom_usuario_revisa_2) {
		this.nom_usuario_revisa_2 = nom_usuario_revisa_2;
	}

	/**
	 * @return the nom_usuario_revisa_3
	 */
	public String getNom_usuario_revisa_3() {
		return nom_usuario_revisa_3;
	}

	/**
	 * @param nom_usuario_revisa_3 the nom_usuario_revisa_3 to set
	 */
	public void setNom_usuario_revisa_3(String nom_usuario_revisa_3) {
		this.nom_usuario_revisa_3 = nom_usuario_revisa_3;
	}

	/**
	 * @return the nom_usuario_ingresa
	 */
	public String getNom_usuario_ingresa() {
		return nom_usuario_ingresa;
	}

	/**
	 * @param nom_usuario_ingresa the nom_usuario_ingresa to set
	 */
	public void setNom_usuario_ingresa(String nom_usuario_ingresa) {
		this.nom_usuario_ingresa = nom_usuario_ingresa;
	}

	/**
	 * @return the nom_usuario_modifica
	 */
	public String getNom_usuario_modifica() {
		return nom_usuario_modifica;
	}

	/**
	 * @param nom_usuario_modifica the nom_usuario_modifica to set
	 */
	public void setNom_usuario_modifica(String nom_usuario_modifica) {
		this.nom_usuario_modifica = nom_usuario_modifica;
	}

	@Override
	public String toString() {
		return "modelo_movimiento_dn [id_movimiento=" + id_movimiento + ", tck_movimiento=" + tck_movimiento
				+ ", tip_pedido=" + tip_pedido + ", fec_solicitud=" + fec_solicitud + ", hor_solicitud=" + hor_solicitud
				+ ", fec_respuesta=" + fec_respuesta + ", hor_respuesta=" + hor_respuesta + ", fec_ejecucion="
				+ fec_ejecucion + ", hor_ejecucion=" + hor_ejecucion + ", id_localidad=" + id_localidad
				+ ", nom_localidad=" + nom_localidad + ", id_solicitante=" + id_solicitante + ", nom_solicitante="
				+ nom_solicitante + ", id_usuario=" + id_usuario + ", nom_usuario=" + nom_usuario + ", tur_movimiento="
				+ tur_movimiento + ", nom_turno=" + nom_turno + ", fec_movimiento=" + fec_movimiento
				+ ", obs_movimiento=" + obs_movimiento + ", usu_revision_1=" + usu_revision_1
				+ ", nom_usuario_revisa_1=" + nom_usuario_revisa_1 + ", usu_revision_2=" + usu_revision_2
				+ ", nom_usuario_revisa_2=" + nom_usuario_revisa_2 + ", usu_revision_3=" + usu_revision_3
				+ ", nom_usuario_revisa_3=" + nom_usuario_revisa_3 + ", est_movimiento=" + est_movimiento
				+ ", est_validacion=" + est_validacion + ", usu_ingresa=" + usu_ingresa + ", nom_usuario_ingresa="
				+ nom_usuario_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", nom_usuario_modifica=" + nom_usuario_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarTipoPedido() {
		String tipo_pedido = "";
		if (getTip_pedido().equals("M")) {
			tipo_pedido = "MOVIMIENTO";
		} else {
			tipo_pedido = "REQUERIMIENTO";
		}
		return tipo_pedido;
	}
	
	public String mostrarFechaYHoraMovimiento() {
		String s = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getFec_movimiento());
		return s;
	}

	public String mostrarFechaYHoraSolicitud() {
		String sf = new SimpleDateFormat("dd/MM/yyyy").format(getFec_solicitud());
		String sh = new SimpleDateFormat("HH:mm").format(new Date(getHor_solicitud()));
		return sf + " " + sh;
	}

	public String mostrarFechaYHoraRespuesta() {
		String sf = new SimpleDateFormat("dd/MM/yyyy").format(getFec_respuesta());
		String sh = new SimpleDateFormat("HH:mm").format(new Date(getHor_respuesta()));
		return sf + " " + sh;
	}

	public String mostrarFechaYHoraEjecucion() {
		String sf = new SimpleDateFormat("dd/MM/yyyy").format(getFec_ejecucion());
		String sh = new SimpleDateFormat("HH:mm").format(new Date(getHor_ejecucion()));
		return sf + " " + sh;
	}
	
	public String mostrarEstado() {
		String estado = "";
		if (getEst_movimiento().equals("E")) {
			estado = "EJECUTADO";
		} else {
			estado = "NO EJECUTADO";
		}
		return estado;
	}

}
