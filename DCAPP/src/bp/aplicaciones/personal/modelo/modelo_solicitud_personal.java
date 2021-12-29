package bp.aplicaciones.personal.modelo;

import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;

public class modelo_solicitud_personal {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Fechas fechas = new Fechas();

	private long id_solicitud;
	private long id_cliente;
	private String nom_cliente;
	private String ticket;
	private long id_tipo_ingreso;
	private String nom_tipo_ingreso;
	private long id_tipo_aprobador;
	private String nom_tipo_aprobador;
	private long id_solicitante;
	private String nom_solicitante;
	private long id_tipo_trabajo;
	private String nom_tipo_trabajo;
	private String id_area;
	private String id_rack;
	private String area;
	private String rack;
	private Timestamp fec_solicitud;
	private Timestamp fec_respuesta;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private String descripcion;
	private long id_localidad;
	private String nom_id_localidad;
	private String est_solicitud;
	private String usu_ingresa;
	private String nom_usuario_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usuario_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_solicitud_personal() {
		super();
	}

	/**
	 * @param id_solicitud
	 * @param id_cliente
	 * @param ticket
	 * @param id_tipo_ingreso
	 * @param id_tipo_aprobador
	 * @param id_solicitante
	 * @param id_tipo_trabajo
	 * @param id_area
	 * @param id_rack
	 * @param area
	 * @param rack
	 * @param fec_solicitud
	 * @param fec_respuesta
	 * @param fec_inicio
	 * @param fec_fin
	 * @param descripcion
	 * @param id_localidad
	 * @param est_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitud_personal(long id_solicitud, long id_cliente, String ticket, long id_tipo_ingreso,
			long id_tipo_aprobador, long id_solicitante, long id_tipo_trabajo, String id_area, String id_rack,
			String area, String rack, Timestamp fec_solicitud, Timestamp fec_respuesta, Timestamp fec_inicio,
			Timestamp fec_fin, String descripcion, long id_localidad, String est_solicitud, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_solicitud = id_solicitud;
		this.id_cliente = id_cliente;
		this.ticket = ticket;
		this.id_tipo_ingreso = id_tipo_ingreso;
		this.id_tipo_aprobador = id_tipo_aprobador;
		this.id_solicitante = id_solicitante;
		this.id_tipo_trabajo = id_tipo_trabajo;
		this.id_area = id_area;
		this.id_rack = id_rack;
		this.area = area;
		this.rack = rack;
		this.fec_solicitud = fec_solicitud;
		this.fec_respuesta = fec_respuesta;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.descripcion = descripcion;
		this.id_localidad = id_localidad;
		this.est_solicitud = est_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_solicitud
	 * @param id_cliente
	 * @param nom_cliente
	 * @param ticket
	 * @param id_tipo_ingreso
	 * @param nom_tipo_ingreso
	 * @param id_tipo_aprobador
	 * @param nom_tipo_aprobador
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param id_tipo_trabajo
	 * @param nom_tipo_trabajo
	 * @param id_area
	 * @param id_rack
	 * @param area
	 * @param rack
	 * @param fec_solicitud
	 * @param fec_respuesta
	 * @param fec_inicio
	 * @param fec_fin
	 * @param descripcion
	 * @param id_localidad
	 * @param nom_id_localidad
	 * @param est_solicitud
	 * @param usu_ingresa
	 * @param nom_usuario_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usuario_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitud_personal(long id_solicitud, long id_cliente, String nom_cliente, String ticket,
			long id_tipo_ingreso, String nom_tipo_ingreso, long id_tipo_aprobador, String nom_tipo_aprobador,
			long id_solicitante, String nom_solicitante, long id_tipo_trabajo, String nom_tipo_trabajo, String id_area,
			String id_rack, String area, String rack, Timestamp fec_solicitud, Timestamp fec_respuesta,
			Timestamp fec_inicio, Timestamp fec_fin, String descripcion, long id_localidad, String nom_id_localidad,
			String est_solicitud, String usu_ingresa, String nom_usuario_ingresa, Timestamp fec_ingresa,
			String usu_modifica, String nom_usuario_modifica, Timestamp fec_modifica) {
		super();
		this.id_solicitud = id_solicitud;
		this.id_cliente = id_cliente;
		this.nom_cliente = nom_cliente;
		this.ticket = ticket;
		this.id_tipo_ingreso = id_tipo_ingreso;
		this.nom_tipo_ingreso = nom_tipo_ingreso;
		this.id_tipo_aprobador = id_tipo_aprobador;
		this.nom_tipo_aprobador = nom_tipo_aprobador;
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.id_tipo_trabajo = id_tipo_trabajo;
		this.nom_tipo_trabajo = nom_tipo_trabajo;
		this.id_area = id_area;
		this.id_rack = id_rack;
		this.area = area;
		this.rack = rack;
		this.fec_solicitud = fec_solicitud;
		this.fec_respuesta = fec_respuesta;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.descripcion = descripcion;
		this.id_localidad = id_localidad;
		this.nom_id_localidad = nom_id_localidad;
		this.est_solicitud = est_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.nom_usuario_ingresa = nom_usuario_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usuario_modifica = nom_usuario_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_solicitud
	 */
	public long getId_solicitud() {
		return id_solicitud;
	}

	/**
	 * @param id_solicitud the id_solicitud to set
	 */
	public void setId_solicitud(long id_solicitud) {
		this.id_solicitud = id_solicitud;
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
	 * @return the nom_cliente
	 */
	public String getNom_cliente() {
		return nom_cliente;
	}

	/**
	 * @param nom_cliente the nom_cliente to set
	 */
	public void setNom_cliente(String nom_cliente) {
		this.nom_cliente = nom_cliente;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the id_tipo_ingreso
	 */
	public long getId_tipo_ingreso() {
		return id_tipo_ingreso;
	}

	/**
	 * @param id_tipo_ingreso the id_tipo_ingreso to set
	 */
	public void setId_tipo_ingreso(long id_tipo_ingreso) {
		this.id_tipo_ingreso = id_tipo_ingreso;
	}

	/**
	 * @return the nom_tipo_ingreso
	 */
	public String getNom_tipo_ingreso() {
		return nom_tipo_ingreso;
	}

	/**
	 * @param nom_tipo_ingreso the nom_tipo_ingreso to set
	 */
	public void setNom_tipo_ingreso(String nom_tipo_ingreso) {
		this.nom_tipo_ingreso = nom_tipo_ingreso;
	}

	/**
	 * @return the id_tipo_aprobador
	 */
	public long getId_tipo_aprobador() {
		return id_tipo_aprobador;
	}

	/**
	 * @param id_tipo_aprobador the id_tipo_aprobador to set
	 */
	public void setId_tipo_aprobador(long id_tipo_aprobador) {
		this.id_tipo_aprobador = id_tipo_aprobador;
	}

	/**
	 * @return the nom_tipo_aprobador
	 */
	public String getNom_tipo_aprobador() {
		return nom_tipo_aprobador;
	}

	/**
	 * @param nom_tipo_aprobador the nom_tipo_aprobador to set
	 */
	public void setNom_tipo_aprobador(String nom_tipo_aprobador) {
		this.nom_tipo_aprobador = nom_tipo_aprobador;
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
	 * @return the id_tipo_trabajo
	 */
	public long getId_tipo_trabajo() {
		return id_tipo_trabajo;
	}

	/**
	 * @param id_tipo_trabajo the id_tipo_trabajo to set
	 */
	public void setId_tipo_trabajo(long id_tipo_trabajo) {
		this.id_tipo_trabajo = id_tipo_trabajo;
	}

	/**
	 * @return the nom_tipo_trabajo
	 */
	public String getNom_tipo_trabajo() {
		return nom_tipo_trabajo;
	}

	/**
	 * @param nom_tipo_trabajo the nom_tipo_trabajo to set
	 */
	public void setNom_tipo_trabajo(String nom_tipo_trabajo) {
		this.nom_tipo_trabajo = nom_tipo_trabajo;
	}

	/**
	 * @return the id_area
	 */
	public String getId_area() {
		return id_area;
	}

	/**
	 * @param id_area the id_area to set
	 */
	public void setId_area(String id_area) {
		this.id_area = id_area;
	}

	/**
	 * @return the id_rack
	 */
	public String getId_rack() {
		return id_rack;
	}

	/**
	 * @param id_rack the id_rack to set
	 */
	public void setId_rack(String id_rack) {
		this.id_rack = id_rack;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the rack
	 */
	public String getRack() {
		return rack;
	}

	/**
	 * @param rack the rack to set
	 */
	public void setRack(String rack) {
		this.rack = rack;
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
	 * @return the nom_id_localidad
	 */
	public String getNom_id_localidad() {
		return nom_id_localidad;
	}

	/**
	 * @param nom_id_localidad the nom_id_localidad to set
	 */
	public void setNom_id_localidad(String nom_id_localidad) {
		this.nom_id_localidad = nom_id_localidad;
	}

	/**
	 * @return the est_solicitud
	 */
	public String getEst_solicitud() {
		return est_solicitud;
	}

	/**
	 * @param est_solicitud the est_solicitud to set
	 */
	public void setEst_solicitud(String est_solicitud) {
		this.est_solicitud = est_solicitud;
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

	public String mostrarFechaSolicitud() {
		String fecha = "-";
		fecha = fechas.obtenerFechaFormateada(fec_solicitud, "dd/MM/yyyy HH:mm");
		return fecha;
	}

	public String mostrarFechaRespuesta() {
		String fecha = "-";
		fecha = fechas.obtenerFechaFormateada(fec_respuesta, "dd/MM/yyyy HH:mm");
		return fecha;
	}

	public String mostrarFechaInicio() {
		String fecha = "-";
		fecha = fechas.obtenerFechaFormateada(fec_inicio, "dd/MM/yyyy HH:mm");
		return fecha;
	}

	public String mostrarFechaFin() {
		String fecha = "-";
		fecha = fechas.obtenerFechaFormateada(fec_fin, "dd/MM/yyyy HH:mm");
		return fecha;
	}

	@Override
	public String toString() {
		return "modelo_solicitud_personal [id_solicitud=" + id_solicitud + ", id_cliente=" + id_cliente
				+ ", nom_cliente=" + nom_cliente + ", ticket=" + ticket + ", id_tipo_ingreso=" + id_tipo_ingreso
				+ ", nom_tipo_ingreso=" + nom_tipo_ingreso + ", id_tipo_aprobador=" + id_tipo_aprobador
				+ ", nom_tipo_aprobador=" + nom_tipo_aprobador + ", id_solicitante=" + id_solicitante
				+ ", nom_solicitante=" + nom_solicitante + ", id_tipo_trabajo=" + id_tipo_trabajo
				+ ", nom_tipo_trabajo=" + nom_tipo_trabajo + ", id_area=" + id_area + ", id_rack=" + id_rack + ", area="
				+ area + ", rack=" + rack + ", fec_solicitud=" + fec_solicitud + ", fec_respuesta=" + fec_respuesta
				+ ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin + ", descripcion=" + descripcion
				+ ", id_localidad=" + id_localidad + ", nom_id_localidad=" + nom_id_localidad + ", est_solicitud="
				+ est_solicitud + ", usu_ingresa=" + usu_ingresa + ", nom_usuario_ingresa=" + nom_usuario_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", nom_usuario_modifica="
				+ nom_usuario_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarArea() {
		String _area = "";
		if (area != null) {
			_area = getArea();
		}
		return _area;
	}

	public String mostrarRack() {
		String _rack = "";
		if (rack != null) {
			_rack = getRack();
		}
		return _rack;
	}

	public String mostrarImagenArea() {
		String imagen = "";
		if (area == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (area.length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenRack() {
		String imagen = "";
		if (rack == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (rack.length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String estiloImagenArea() {
		String estilo = "";
		if (area == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (area.length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenRack() {
		String estilo = "";
		if (rack == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (rack.length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

}
