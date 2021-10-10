package bp.aplicaciones.personal.modelo;

import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;

public class modelo_registro_permanencia {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Fechas fechas = new Fechas();

	private long id_registro_permanencia;
	private long id_solicitud;
	private long id_cliente;
	private String ticket;
	private Timestamp fec_ingreso;
	private Timestamp fec_salida;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_proveedor;
	private String nom_empresa;
	private String nom_proveedor;
	private String num_documento;
	private long id_solicitante;
	private long id_emp_solicitante;
	private String nom_emp_solicitante;
	private String descripcion;
	private String area;
	private String rack;
	private String area_rack;
	private String tipo_trabajo;
	private String num_tarjeta_dn;
	private String num_tarjeta_bp;
	private String tipo_autorizador;
	private String tipo_ingreso;
	private Timestamp fec_ingreso_su;
	private Timestamp fec_salida_su;
	private long id_localidad;
	private String nom_localidad;
	private String est_registro_permanencia;
	private String usu_ingresa;
	private String nom_usuario_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usuario_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_registro_permanencia() {
		super();
	}

	/**
	 * @param id_registro_permanencia
	 * @param id_solicitud
	 * @param fec_ingreso
	 * @param fec_salida
	 * @param id_proveedor
	 * @param num_tarjeta_dn
	 * @param num_tarjeta_bp
	 * @param fec_ingreso_su
	 * @param fec_salida_su
	 * @param id_localidad
	 * @param est_registro_permanencia
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_registro_permanencia(long id_registro_permanencia, long id_solicitud, Timestamp fec_ingreso,
			Timestamp fec_salida, long id_proveedor, String num_tarjeta_dn, String num_tarjeta_bp,
			Timestamp fec_ingreso_su, Timestamp fec_salida_su, long id_localidad, String est_registro_permanencia,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_registro_permanencia = id_registro_permanencia;
		this.id_solicitud = id_solicitud;
		this.fec_ingreso = fec_ingreso;
		this.fec_salida = fec_salida;
		this.id_proveedor = id_proveedor;
		this.num_tarjeta_dn = num_tarjeta_dn;
		this.num_tarjeta_bp = num_tarjeta_bp;
		this.fec_ingreso_su = fec_ingreso_su;
		this.fec_salida_su = fec_salida_su;
		this.id_localidad = id_localidad;
		this.est_registro_permanencia = est_registro_permanencia;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_registro_permanencia
	 * @param id_solicitud
	 * @param id_cliente
	 * @param ticket
	 * @param fec_ingreso
	 * @param fec_salida
	 * @param id_proveedor
	 * @param nom_empresa
	 * @param nom_proveedor
	 * @param num_documento
	 * @param descripcion
	 * @param area_rack
	 * @param tipo_trabajo
	 * @param num_tarjeta_dn
	 * @param num_tarjeta_bp
	 * @param tipo_autorizador
	 * @param tipo_ingreso
	 * @param fec_ingreso_su
	 * @param fec_salida_su
	 * @param id_localidad
	 * @param nom_localidad
	 * @param est_registro_permanencia
	 * @param usu_ingresa
	 * @param nom_usuario_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usuario_modifica
	 * @param fec_modifica
	 */
	public modelo_registro_permanencia(long id_registro_permanencia, long id_solicitud, long id_cliente, String ticket,
			Timestamp fec_ingreso, Timestamp fec_salida, long id_proveedor, String nom_empresa, String nom_proveedor,
			String num_documento, String descripcion, String area_rack, String tipo_trabajo, String num_tarjeta_dn,
			String num_tarjeta_bp, String tipo_autorizador, String tipo_ingreso, Timestamp fec_ingreso_su,
			Timestamp fec_salida_su, long id_localidad, String nom_localidad, String est_registro_permanencia,
			String usu_ingresa, String nom_usuario_ingresa, Timestamp fec_ingresa, String usu_modifica,
			String nom_usuario_modifica, Timestamp fec_modifica) {
		super();
		this.id_registro_permanencia = id_registro_permanencia;
		this.id_solicitud = id_solicitud;
		this.id_cliente = id_cliente;
		this.ticket = ticket;
		this.fec_ingreso = fec_ingreso;
		this.fec_salida = fec_salida;
		this.id_proveedor = id_proveedor;
		this.nom_empresa = nom_empresa;
		this.nom_proveedor = nom_proveedor;
		this.num_documento = num_documento;
		this.descripcion = descripcion;
		this.area_rack = area_rack;
		this.tipo_trabajo = tipo_trabajo;
		this.num_tarjeta_dn = num_tarjeta_dn;
		this.num_tarjeta_bp = num_tarjeta_bp;
		this.tipo_autorizador = tipo_autorizador;
		this.tipo_ingreso = tipo_ingreso;
		this.fec_ingreso_su = fec_ingreso_su;
		this.fec_salida_su = fec_salida_su;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.est_registro_permanencia = est_registro_permanencia;
		this.usu_ingresa = usu_ingresa;
		this.nom_usuario_ingresa = nom_usuario_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usuario_modifica = nom_usuario_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_solicitud
	 * @param id_cliente
	 * @param ticket
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_proveedor
	 * @param nom_empresa
	 * @param nom_proveedor
	 * @param num_documento
	 * @param id_solicitante
	 * @param id_emp_solicitante
	 * @param nom_emp_solicitante
	 * @param descripcion
	 * @param area
	 * @param rack
	 */
	public modelo_registro_permanencia(long id_solicitud, long id_cliente, String ticket, Timestamp fec_inicio,
			Timestamp fec_fin, long id_proveedor, String nom_empresa, String nom_proveedor, String num_documento,
			long id_solicitante, long id_emp_solicitante, String nom_emp_solicitante, String descripcion, String area,
			String rack) {
		super();
		this.id_solicitud = id_solicitud;
		this.id_cliente = id_cliente;
		this.ticket = ticket;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_proveedor = id_proveedor;
		this.nom_empresa = nom_empresa;
		this.nom_proveedor = nom_proveedor;
		this.num_documento = num_documento;
		this.id_solicitante = id_solicitante;
		this.id_emp_solicitante = id_emp_solicitante;
		this.nom_emp_solicitante = nom_emp_solicitante;
		this.descripcion = descripcion;
		this.area = area;
		this.rack = rack;
	}

	/**
	 * @param id_registro_permanencia
	 * @param num_tarjeta_bp
	 * @param num_tarjeta_dn
	 * @param fec_ingreso
	 * @param fec_salida
	 * @param fec_ingreso_su
	 * @param fec_salida_su
	 * @param id_solicitud
	 * @param id_cliente
	 * @param ticket
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_proveedor
	 * @param nom_empresa
	 * @param nom_proveedor
	 * @param num_documento
	 * @param id_solicitante
	 * @param id_emp_solicitante
	 * @param nom_emp_solicitante
	 * @param descripcion
	 * @param area
	 * @param rack
	 */
	public modelo_registro_permanencia(long id_registro_permanencia, String num_tarjeta_bp, String num_tarjeta_dn,
			Timestamp fec_ingreso, Timestamp fec_salida, Timestamp fec_ingreso_su, Timestamp fec_salida_su,
			long id_solicitud, long id_cliente, String ticket, Timestamp fec_inicio, Timestamp fec_fin,
			long id_proveedor, String nom_empresa, String nom_proveedor, String num_documento, long id_solicitante,
			long id_emp_solicitante, String nom_emp_solicitante, String descripcion, String area, String rack) {
		super();
		this.id_registro_permanencia = id_registro_permanencia;
		this.num_tarjeta_bp = num_tarjeta_bp;
		this.num_tarjeta_dn = num_tarjeta_dn;
		this.fec_ingreso = fec_ingreso;
		this.fec_salida = fec_salida;
		this.fec_ingreso_su = fec_ingreso_su;
		this.fec_salida_su = fec_salida_su;
		this.id_solicitud = id_solicitud;
		this.id_cliente = id_cliente;
		this.ticket = ticket;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_proveedor = id_proveedor;
		this.nom_empresa = nom_empresa;
		this.nom_proveedor = nom_proveedor;
		this.num_documento = num_documento;
		this.id_solicitante = id_solicitante;
		this.id_emp_solicitante = id_emp_solicitante;
		this.nom_emp_solicitante = nom_emp_solicitante;
		this.descripcion = descripcion;
		this.area = area;
		this.rack = rack;
	}

	/**
	 * @return the id_registro_permanencia
	 */
	public long getId_registro_permanencia() {
		return id_registro_permanencia;
	}

	/**
	 * @param id_registro_permanencia the id_registro_permanencia to set
	 */
	public void setId_registro_permanencia(long id_registro_permanencia) {
		this.id_registro_permanencia = id_registro_permanencia;
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
	 * @return the fec_ingreso
	 */
	public Timestamp getFec_ingreso() {
		return fec_ingreso;
	}

	/**
	 * @param fec_ingreso the fec_ingreso to set
	 */
	public void setFec_ingreso(Timestamp fec_ingreso) {
		this.fec_ingreso = fec_ingreso;
	}

	/**
	 * @return the fec_salida
	 */
	public Timestamp getFec_salida() {
		return fec_salida;
	}

	/**
	 * @param fec_salida the fec_salida to set
	 */
	public void setFec_salida(Timestamp fec_salida) {
		this.fec_salida = fec_salida;
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
	 * @return the id_proveedor
	 */
	public long getId_proveedor() {
		return id_proveedor;
	}

	/**
	 * @param id_proveedor the id_proveedor to set
	 */
	public void setId_proveedor(long id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	/**
	 * @return the nom_empresa
	 */
	public String getNom_empresa() {
		return nom_empresa;
	}

	/**
	 * @param nom_empresa the nom_empresa to set
	 */
	public void setNom_empresa(String nom_empresa) {
		this.nom_empresa = nom_empresa;
	}

	/**
	 * @return the nom_proveedor
	 */
	public String getNom_proveedor() {
		return nom_proveedor;
	}

	/**
	 * @param nom_proveedor the nom_proveedor to set
	 */
	public void setNom_proveedor(String nom_proveedor) {
		this.nom_proveedor = nom_proveedor;
	}

	/**
	 * @return the num_documento
	 */
	public String getNum_documento() {
		return num_documento;
	}

	/**
	 * @param num_documento the num_documento to set
	 */
	public void setNum_documento(String num_documento) {
		this.num_documento = num_documento;
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
	 * @return the id_emp_solicitante
	 */
	public long getId_emp_solicitante() {
		return id_emp_solicitante;
	}

	/**
	 * @param id_emp_solicitante the id_emp_solicitante to set
	 */
	public void setId_emp_solicitante(long id_emp_solicitante) {
		this.id_emp_solicitante = id_emp_solicitante;
	}

	/**
	 * @return the nom_emp_solicitante
	 */
	public String getNom_emp_solicitante() {
		return nom_emp_solicitante;
	}

	/**
	 * @param nom_emp_solicitante the nom_emp_solicitante to set
	 */
	public void setNom_emp_solicitante(String nom_emp_solicitante) {
		this.nom_emp_solicitante = nom_emp_solicitante;
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
	 * @return the area_rack
	 */
	public String getArea_rack() {
		return area_rack;
	}

	/**
	 * @param area_rack the area_rack to set
	 */
	public void setArea_rack(String area_rack) {
		this.area_rack = area_rack;
	}

	/**
	 * @return the tipo_trabajo
	 */
	public String getTipo_trabajo() {
		return tipo_trabajo;
	}

	/**
	 * @param tipo_trabajo the tipo_trabajo to set
	 */
	public void setTipo_trabajo(String tipo_trabajo) {
		this.tipo_trabajo = tipo_trabajo;
	}

	/**
	 * @return the num_tarjeta_dn
	 */
	public String getNum_tarjeta_dn() {
		return num_tarjeta_dn;
	}

	/**
	 * @param num_tarjeta_dn the num_tarjeta_dn to set
	 */
	public void setNum_tarjeta_dn(String num_tarjeta_dn) {
		this.num_tarjeta_dn = num_tarjeta_dn;
	}

	/**
	 * @return the num_tarjeta_bp
	 */
	public String getNum_tarjeta_bp() {
		return num_tarjeta_bp;
	}

	/**
	 * @param num_tarjeta_bp the num_tarjeta_bp to set
	 */
	public void setNum_tarjeta_bp(String num_tarjeta_bp) {
		this.num_tarjeta_bp = num_tarjeta_bp;
	}

	/**
	 * @return the tipo_autorizador
	 */
	public String getTipo_autorizador() {
		return tipo_autorizador;
	}

	/**
	 * @param tipo_autorizador the tipo_autorizador to set
	 */
	public void setTipo_autorizador(String tipo_autorizador) {
		this.tipo_autorizador = tipo_autorizador;
	}

	/**
	 * @return the tipo_ingreso
	 */
	public String getTipo_ingreso() {
		return tipo_ingreso;
	}

	/**
	 * @param tipo_ingreso the tipo_ingreso to set
	 */
	public void setTipo_ingreso(String tipo_ingreso) {
		this.tipo_ingreso = tipo_ingreso;
	}

	/**
	 * @return the fec_ingreso_su
	 */
	public Timestamp getFec_ingreso_su() {
		return fec_ingreso_su;
	}

	/**
	 * @param fec_ingreso_su the fec_ingreso_su to set
	 */
	public void setFec_ingreso_su(Timestamp fec_ingreso_su) {
		this.fec_ingreso_su = fec_ingreso_su;
	}

	/**
	 * @return the fec_salida_su
	 */
	public Timestamp getFec_salida_su() {
		return fec_salida_su;
	}

	/**
	 * @param fec_salida_su the fec_salida_su to set
	 */
	public void setFec_salida_su(Timestamp fec_salida_su) {
		this.fec_salida_su = fec_salida_su;
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
	 * @return the est_registro_permanencia
	 */
	public String getEst_registro_permanencia() {
		return est_registro_permanencia;
	}

	/**
	 * @param est_registro_permanencia the est_registro_permanencia to set
	 */
	public void setEst_registro_permanencia(String est_registro_permanencia) {
		this.est_registro_permanencia = est_registro_permanencia;
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

	@Override
	public String toString() {
		return "modelo_registro_permanencia [id_registro_permanencia=" + id_registro_permanencia + ", id_solicitud="
				+ id_solicitud + ", id_cliente=" + id_cliente + ", ticket=" + ticket + ", fec_ingreso=" + fec_ingreso
				+ ", fec_salida=" + fec_salida + ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin
				+ ", id_proveedor=" + id_proveedor + ", nom_empresa=" + nom_empresa + ", nom_proveedor=" + nom_proveedor
				+ ", num_documento=" + num_documento + ", id_solicitante=" + id_solicitante + ", id_emp_solicitante="
				+ id_emp_solicitante + ", nom_emp_solicitante=" + nom_emp_solicitante + ", descripcion=" + descripcion
				+ ", area=" + area + ", rack=" + rack + ", area_rack=" + area_rack + ", tipo_trabajo=" + tipo_trabajo
				+ ", num_tarjeta_dn=" + num_tarjeta_dn + ", num_tarjeta_bp=" + num_tarjeta_bp + ", tipo_autorizador="
				+ tipo_autorizador + ", tipo_ingreso=" + tipo_ingreso + ", fec_ingreso_su=" + fec_ingreso_su
				+ ", fec_salida_su=" + fec_salida_su + ", id_localidad=" + id_localidad + ", nom_localidad="
				+ nom_localidad + ", est_registro_permanencia=" + est_registro_permanencia + ", usu_ingresa="
				+ usu_ingresa + ", nom_usuario_ingresa=" + nom_usuario_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", nom_usuario_modifica=" + nom_usuario_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarFechaIngreso() {
		String fecha = "";
		fecha = fechas.obtenerFechaFormateada(fec_ingreso, "dd/MM/yyyy");
		return fecha;
	}

	public String mostrarHoraIngreso() {
		String fecha = "";
		fecha = fechas.obtenerFechaFormateada(fec_ingreso, "HH:mm");
		return fecha;
	}

	public String mostrarFechaHoraIngreso() {
		String fecha = "";
		if (fec_ingreso != null) {
			fecha = fechas.obtenerFechaFormateada(fec_ingreso, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaSalida() {
		String fecha = "";
		if (fec_salida != null) {
			fecha = fechas.obtenerFechaFormateada(fec_salida, "dd/MM/yyyy");
		}
		return fecha;
	}

	public String mostrarHoraSalida() {
		String fecha = "";
		if (fec_salida != null) {
			fecha = fechas.obtenerFechaFormateada(fec_salida, "HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaHoraSalida() {
		String fecha = "";
		if (fec_salida != null) {
			fecha = fechas.obtenerFechaFormateada(fec_salida, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaIngresoSU() {
		String fecha = "";
		if (fec_ingreso_su != null) {
			fecha = fechas.obtenerFechaFormateada(fec_ingreso_su, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaSalidaSU() {
		String fecha = "";
		if (fec_salida_su != null) {
			fecha = fechas.obtenerFechaFormateada(fec_salida_su, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaHoraInicio() {
		String fecha = "";
		if (fec_inicio != null) {
			fecha = fechas.obtenerFechaFormateada(fec_inicio, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarFechaHoraFin() {
		String fecha = "";
		if (fec_fin != null) {
			fecha = fechas.obtenerFechaFormateada(fec_fin, "dd/MM/yyyy HH:mm");
		}
		return fecha;
	}

	public String mostrarImagenFechaSalida() {
		String imagen = "";
		if (fec_salida == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenHoraSalida() {
		String imagen = "";
		if (fec_salida == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaHoraSalida() {
		String imagen = "";
		if (fec_salida == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaIngresoSU() {
		String imagen = "";
		if (fec_ingreso_su == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaSalidaSU() {
		String imagen = "";
		if (fec_salida_su == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenTarjetaDN() {
		String imagen = "";
		if (num_tarjeta_dn == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (num_tarjeta_dn.length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenTarjetaBP() {
		String imagen = "";
		if (num_tarjeta_bp == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (num_tarjeta_bp.length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenAreaRack() {
		String imagen = "";
		if (area_rack == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (area_rack.length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

}
