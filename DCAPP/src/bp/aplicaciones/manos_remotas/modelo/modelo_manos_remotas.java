package bp.aplicaciones.manos_remotas.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_8;

public class modelo_manos_remotas implements Cloneable {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Fechas fechas = new Fechas();

	private long id_mano_remota;
	private long id_tipo;
	private String nom_tipo;
	private long id_cliente;
	private String nom_cliente;
	private String ticket_externo;
	private long id_turno;
	private String nom_turno;
	private long id_tipo_servicio;
	private String nom_tipo_servicio;
	private long id_tipo_clasificacion;
	private String nom_tipo_clasificacion;
	private long id_tipo_tarea;
	private String nom_tipo_tarea;
	private long id_solicitante;
	private String nom_solicitante;
	private String emp_solicitante;
	private long id_area;
	private String nom_area;
	private long id_componente;
	private String nom_componente;
	private String serie;
	private String rack;
	private String ur;
	private String descripcion_tarea;
	private String descripcion_solucion;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_estado_bitacora;
	private String nom_estado_bitacora;
	private long id_localidad;
	private String nom_localidad;
	private String est_mano_remota;
	private String usu_ingresa;
	private String nom_usuario_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usuario_modifica;
	private Timestamp fec_modifica;

	public modelo_manos_remotas clone() {
		modelo_manos_remotas mano_remota = new modelo_manos_remotas(this.id_mano_remota, this.id_estado_bitacora,
				this.usu_modifica, this.fec_modifica);
		return mano_remota;
	}

	/**
	 * 
	 */
	public modelo_manos_remotas() {
		super();
	}

	/**
	 * @param id_mano_remota
	 * @param id_estado_bitacora
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_manos_remotas(long id_mano_remota, long id_estado_bitacora, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_mano_remota = id_mano_remota;
		this.id_estado_bitacora = id_estado_bitacora;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_mano_remota
	 * @param id_tipo
	 * @param id_cliente
	 * @param ticket_externo
	 * @param id_turno
	 * @param id_tipo_servicio
	 * @param id_tipo_clasificacion
	 * @param id_tipo_tarea
	 * @param id_solicitante
	 * @param id_area
	 * @param id_componente
	 * @param descripcion_tarea
	 * @param descripcion_solucion
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_estado_bitacora
	 * @param id_localidad
	 * @param est_mano_remota
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_manos_remotas(long id_mano_remota, long id_tipo, long id_cliente, String ticket_externo,
			long id_turno, long id_tipo_servicio, long id_tipo_clasificacion, long id_tipo_tarea, long id_solicitante,
			long id_area, long id_componente, String descripcion_tarea, String descripcion_solucion,
			Timestamp fec_inicio, Timestamp fec_fin, long id_estado_bitacora, long id_localidad, String est_mano_remota,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_mano_remota = id_mano_remota;
		this.id_tipo = id_tipo;
		this.id_cliente = id_cliente;
		this.ticket_externo = ticket_externo;
		this.id_turno = id_turno;
		this.id_tipo_servicio = id_tipo_servicio;
		this.id_tipo_clasificacion = id_tipo_clasificacion;
		this.id_tipo_tarea = id_tipo_tarea;
		this.id_solicitante = id_solicitante;
		this.id_area = id_area;
		this.id_componente = id_componente;
		this.descripcion_tarea = descripcion_tarea;
		this.descripcion_solucion = descripcion_solucion;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_estado_bitacora = id_estado_bitacora;
		this.id_localidad = id_localidad;
		this.est_mano_remota = est_mano_remota;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_mano_remota
	 * @param id_tipo
	 * @param id_cliente
	 * @param nom_cliente
	 * @param ticket_externo
	 * @param id_turno
	 * @param nom_turno
	 * @param id_tipo_servicio
	 * @param nom_tipo_servicio
	 * @param id_tipo_clasificacion
	 * @param nom_tipo_clasificacion
	 * @param id_tipo_tarea
	 * @param nom_tipo_tarea
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param emp_solicitante
	 * @param id_area
	 * @param nom_area
	 * @param id_componente
	 * @param nom_componente
	 * @param serie
	 * @param rack
	 * @param ur
	 * @param descripcion_tarea
	 * @param descripcion_solucion
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_estado_bitacora
	 * @param nom_estado_bitacora
	 * @param id_localidad
	 * @param nom_localidad
	 * @param est_mano_remota
	 * @param usu_ingresa
	 * @param nom_usuario_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usuario_modifica
	 * @param fec_modifica
	 */
	public modelo_manos_remotas(long id_mano_remota, long id_tipo, long id_cliente, String nom_cliente,
			String ticket_externo, long id_turno, String nom_turno, long id_tipo_servicio, String nom_tipo_servicio,
			long id_tipo_clasificacion, String nom_tipo_clasificacion, long id_tipo_tarea, String nom_tipo_tarea,
			long id_solicitante, String nom_solicitante, String emp_solicitante, long id_area, String nom_area,
			long id_componente, String nom_componente, String serie, String rack, String ur, String descripcion_tarea,
			String descripcion_solucion, Timestamp fec_inicio, Timestamp fec_fin, long id_estado_bitacora,
			String nom_estado_bitacora, long id_localidad, String nom_localidad, String est_mano_remota,
			String usu_ingresa, String nom_usuario_ingresa, Timestamp fec_ingresa, String usu_modifica,
			String nom_usuario_modifica, Timestamp fec_modifica) {
		super();
		this.id_mano_remota = id_mano_remota;
		this.id_tipo = id_tipo;
		this.id_cliente = id_cliente;
		this.nom_cliente = nom_cliente;
		this.ticket_externo = ticket_externo;
		this.id_turno = id_turno;
		this.nom_turno = nom_turno;
		this.id_tipo_servicio = id_tipo_servicio;
		this.nom_tipo_servicio = nom_tipo_servicio;
		this.id_tipo_clasificacion = id_tipo_clasificacion;
		this.nom_tipo_clasificacion = nom_tipo_clasificacion;
		this.id_tipo_tarea = id_tipo_tarea;
		this.nom_tipo_tarea = nom_tipo_tarea;
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.emp_solicitante = emp_solicitante;
		this.id_area = id_area;
		this.nom_area = nom_area;
		this.id_componente = id_componente;
		this.nom_componente = nom_componente;
		this.serie = serie;
		this.rack = rack;
		this.ur = ur;
		this.descripcion_tarea = descripcion_tarea;
		this.descripcion_solucion = descripcion_solucion;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_estado_bitacora = id_estado_bitacora;
		this.nom_estado_bitacora = nom_estado_bitacora;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.est_mano_remota = est_mano_remota;
		this.usu_ingresa = usu_ingresa;
		this.nom_usuario_ingresa = nom_usuario_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usuario_modifica = nom_usuario_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_mano_remota
	 */
	public long getId_mano_remota() {
		return id_mano_remota;
	}

	/**
	 * @param id_mano_remota the id_mano_remota to set
	 */
	public void setId_mano_remota(long id_mano_remota) {
		this.id_mano_remota = id_mano_remota;
	}

	/**
	 * @return the id_tipo
	 */
	public long getId_tipo() {
		return id_tipo;
	}

	/**
	 * @param id_tipo the id_tipo to set
	 */
	public void setId_tipo(long id_tipo) {
		this.id_tipo = id_tipo;
	}

	/**
	 * @return the nom_tipo
	 */
	public String getNom_tipo() {
		return nom_tipo;
	}

	/**
	 * @param nom_tipo the nom_tipo to set
	 */
	public void setNom_tipo(String nom_tipo) {
		this.nom_tipo = nom_tipo;
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
	 * @return the nom_tipo_servicio
	 */
	public String getNom_tipo_servicio() {
		return nom_tipo_servicio;
	}

	/**
	 * @param nom_tipo_servicio the nom_tipo_servicio to set
	 */
	public void setNom_tipo_servicio(String nom_tipo_servicio) {
		this.nom_tipo_servicio = nom_tipo_servicio;
	}

	/**
	 * @return the id_tipo_clasificacion
	 */
	public long getId_tipo_clasificacion() {
		return id_tipo_clasificacion;
	}

	/**
	 * @param id_tipo_clasificacion the id_tipo_clasificacion to set
	 */
	public void setId_tipo_clasificacion(long id_tipo_clasificacion) {
		this.id_tipo_clasificacion = id_tipo_clasificacion;
	}

	/**
	 * @return the nom_tipo_clasificacion
	 */
	public String getNom_tipo_clasificacion() {
		return nom_tipo_clasificacion;
	}

	/**
	 * @param nom_tipo_clasificacion the nom_tipo_clasificacion to set
	 */
	public void setNom_tipo_clasificacion(String nom_tipo_clasificacion) {
		this.nom_tipo_clasificacion = nom_tipo_clasificacion;
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
	 * @return the nom_tipo_tarea
	 */
	public String getNom_tipo_tarea() {
		return nom_tipo_tarea;
	}

	/**
	 * @param nom_tipo_tarea the nom_tipo_tarea to set
	 */
	public void setNom_tipo_tarea(String nom_tipo_tarea) {
		this.nom_tipo_tarea = nom_tipo_tarea;
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
	 * @return the emp_solicitante
	 */
	public String getEmp_solicitante() {
		return emp_solicitante;
	}

	/**
	 * @param emp_solicitante the emp_solicitante to set
	 */
	public void setEmp_solicitante(String emp_solicitante) {
		this.emp_solicitante = emp_solicitante;
	}

	/**
	 * @return the id_area
	 */
	public long getId_area() {
		return id_area;
	}

	/**
	 * @param id_area the id_area to set
	 */
	public void setId_area(long id_area) {
		this.id_area = id_area;
	}

	/**
	 * @return the nom_area
	 */
	public String getNom_area() {
		return nom_area;
	}

	/**
	 * @param nom_area the nom_area to set
	 */
	public void setNom_area(String nom_area) {
		this.nom_area = nom_area;
	}

	/**
	 * @return the id_componente
	 */
	public long getId_componente() {
		return id_componente;
	}

	/**
	 * @param id_componente the id_componente to set
	 */
	public void setId_componente(long id_componente) {
		this.id_componente = id_componente;
	}

	/**
	 * @return the nom_componente
	 */
	public String getNom_componente() {
		return nom_componente;
	}

	/**
	 * @param nom_componente the nom_componente to set
	 */
	public void setNom_componente(String nom_componente) {
		this.nom_componente = nom_componente;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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
	 * @return the ur
	 */
	public String getUr() {
		return ur;
	}

	/**
	 * @param ur the ur to set
	 */
	public void setUr(String ur) {
		this.ur = ur;
	}

	/**
	 * @return the descripcion_tarea
	 */
	public String getDescripcion_tarea() {
		return descripcion_tarea;
	}

	/**
	 * @param descripcion_tarea the descripcion_tarea to set
	 */
	public void setDescripcion_tarea(String descripcion_tarea) {
		this.descripcion_tarea = descripcion_tarea;
	}

	/**
	 * @return the descripcion_solucion
	 */
	public String getDescripcion_solucion() {
		return descripcion_solucion;
	}

	/**
	 * @param descripcion_solucion the descripcion_solucion to set
	 */
	public void setDescripcion_solucion(String descripcion_solucion) {
		this.descripcion_solucion = descripcion_solucion;
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
	 * @return the nom_estado_bitacora
	 */
	public String getNom_estado_bitacora() {
		return nom_estado_bitacora;
	}

	/**
	 * @param nom_estado_bitacora the nom_estado_bitacora to set
	 */
	public void setNom_estado_bitacora(String nom_estado_bitacora) {
		this.nom_estado_bitacora = nom_estado_bitacora;
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
	 * @return the est_mano_remota
	 */
	public String getEst_mano_remota() {
		return est_mano_remota;
	}

	/**
	 * @param est_mano_remota the est_mano_remota to set
	 */
	public void setEst_mano_remota(String est_mano_remota) {
		this.est_mano_remota = est_mano_remota;
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
		return "modelo_manos_remotas [id_mano_remota=" + id_mano_remota + ", id_tipo=" + id_tipo + ", nom_tipo="
				+ nom_tipo + ", id_cliente=" + id_cliente + ", nom_cliente=" + nom_cliente + ", ticket_externo="
				+ ticket_externo + ", id_turno=" + id_turno + ", nom_turno=" + nom_turno + ", id_tipo_servicio="
				+ id_tipo_servicio + ", nom_tipo_servicio=" + nom_tipo_servicio + ", id_tipo_clasificacion="
				+ id_tipo_clasificacion + ", nom_tipo_clasificacion=" + nom_tipo_clasificacion + ", id_tipo_tarea="
				+ id_tipo_tarea + ", nom_tipo_tarea=" + nom_tipo_tarea + ", id_solicitante=" + id_solicitante
				+ ", nom_solicitante=" + nom_solicitante + ", emp_solicitante=" + emp_solicitante + ", id_area="
				+ id_area + ", nom_area=" + nom_area + ", id_componente=" + id_componente + ", nom_componente="
				+ nom_componente + ", serie=" + serie + ", rack=" + rack + ", ur=" + ur + ", descripcion_tarea="
				+ descripcion_tarea + ", descripcion_solucion=" + descripcion_solucion + ", fec_inicio=" + fec_inicio
				+ ", fec_fin=" + fec_fin + ", id_estado_bitacora=" + id_estado_bitacora + ", nom_estado_bitacora="
				+ nom_estado_bitacora + ", id_localidad=" + id_localidad + ", nom_localidad=" + nom_localidad
				+ ", est_mano_remota=" + est_mano_remota + ", usu_ingresa=" + usu_ingresa + ", nom_usuario_ingresa="
				+ nom_usuario_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", nom_usuario_modifica=" + nom_usuario_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "-";
		if (est_mano_remota.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_mano_remota.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String mostrarSolicitante() {
		String solicitante = "";
		if (nom_solicitante != null) {
			solicitante = getNom_solicitante();
		}
		return solicitante;
	}

	public String mostrarUsuarioIngresa() {
		String usuario = "";
		if (nom_usuario_ingresa != null) {
			usuario = getNom_usuario_ingresa();
		}
		return usuario;
	}

	public String mostrarUsuarioModifica() {
		String usuario = "";
		if (nom_usuario_modifica != null) {
			usuario = getNom_usuario_modifica();
		}
		return usuario;
	}

	public String mostrarClasificacion() {
		String nom_tipo_clasificacion = "";
		if (nom_tipo_clasificacion != null) {
			nom_tipo_clasificacion = getNom_tipo_clasificacion();
		}
		return nom_tipo_clasificacion;
	}

	public String mostrarArea() {
		String _area = "";
		if (nom_area != null) {
			_area = getNom_area();
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

	/* Se sobreescribe el metodo String para mostrar imagenes en campos */

	public String mostrarImagenSolicitante() {
		String imagen = "";
		if (nom_solicitante == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenUsuarioIngresa() {
		String imagen = "";
		if (nom_usuario_ingresa == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenUsuarioModifica() {
		String imagen = "";
		if (nom_usuario_modifica == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenClasificacion() {
		String imagen = "";
		if (nom_tipo_clasificacion == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenArea() {
		String imagen = "";
		if (nom_area == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (nom_area.length() <= 0) {
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

	/* Se sobreescribe el metodo String para personalizar estilos en campos */

	public String estiloId() throws ClassNotFoundException, FileNotFoundException, IOException {
		String estilo = "";
		if (new Date().after(fec_fin)) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: #E84749 !important;";
		}
		return estilo;
	}

	public String estiloEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_parametros_generales_8> listaParametros = new ArrayList<modelo_parametros_generales_8>();
		listaParametros = consultasABaseDeDatos.cargarParametros8(String.valueOf(id_estado_bitacora), "", id_localidad,
				3);
		String estilo = "";
		if (listaParametros.size() > 0) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
					+ listaParametros.get(0).getColor() + " !important; text-align: center !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: italic !important; text-align: center !important;";
		}
		return estilo;
	}

	public String estiloImagenSolicitante() {
		String estilo = "";
		if (nom_solicitante == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenUsuarioIngresa() {
		String estilo = "";
		if (nom_usuario_ingresa == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenUsuarioModifica() {
		String estilo = "";
		if (nom_usuario_modifica == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenClasificacion() {
		String estilo = "";
		if (nom_tipo_clasificacion == null) {
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
