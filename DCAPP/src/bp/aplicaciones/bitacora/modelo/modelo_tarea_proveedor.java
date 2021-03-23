package bp.aplicaciones.bitacora.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_8;

public class modelo_tarea_proveedor implements Cloneable {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Fechas fechas = new Fechas();

	private long id_tarea_proveedor;
	private long id_cliente;
	private String nom_cliente;
	private String ticket_externo;
	private long id_turno;
	private String nom_turno;
	private long id_tipo_servicio;
	private String nom_tipo_servicio;
	private long id_tipo_tarea;
	private String nom_tipo_tarea;
	private long id_solicitante;
	private String nom_solicitante;
	private String descripcion;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_estado_bitacora;
	private String nom_estado_bitacora;
	private String cumplimiento;
	private long id_localidad;
	private String nom_localidad;
	private String est_tarea_proveedor;
	private String usu_ingresa;
	private String nom_usuario_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private String nom_usuario_modifica;
	private Timestamp fec_modifica;
	private String cor_revisa;
	private String nom_usuario_revisa;
	private Timestamp fec_revision;
	private String obs_coordinador;

	public modelo_tarea_proveedor clone() {
		modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor(this.id_tarea_proveedor,
				this.id_estado_bitacora, this.usu_modifica, this.fec_modifica);
		return tarea_proveedor;
	}

	/**
	 * 
	 */
	public modelo_tarea_proveedor() {
		super();
	}

	/**
	 * @param id_tarea_proveedor
	 * @param id_estado_bitacora
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tarea_proveedor(long id_tarea_proveedor, long id_estado_bitacora, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_tarea_proveedor = id_tarea_proveedor;
		this.id_estado_bitacora = id_estado_bitacora;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_tarea_proveedor
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
	 * @param id_localidad
	 * @param est_tarea_proveedor
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 * @param cor_revisa
	 * @param fec_revision
	 * @param obs_coordinador
	 */
	public modelo_tarea_proveedor(long id_tarea_proveedor, long id_cliente, String ticket_externo, long id_turno,
			long id_tipo_servicio, long id_tipo_tarea, String descripcion, Timestamp fec_inicio, Timestamp fec_fin,
			long id_estado_bitacora, String cumplimiento, long id_localidad, String est_tarea_proveedor,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica, String cor_revisa,
			Timestamp fec_revision, String obs_coordinador) {
		super();
		this.id_tarea_proveedor = id_tarea_proveedor;
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
		this.id_localidad = id_localidad;
		this.est_tarea_proveedor = est_tarea_proveedor;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
		this.cor_revisa = cor_revisa;
		this.fec_revision = fec_revision;
		this.obs_coordinador = obs_coordinador;
	}

	/**
	 * @param id_tarea_proveedor
	 * @param id_cliente
	 * @param nom_cliente
	 * @param ticket_externo
	 * @param id_turno
	 * @param nom_turno
	 * @param id_tipo_servicio
	 * @param nom_tipo_servicio
	 * @param id_tipo_tarea
	 * @param nom_tipo_tarea
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param descripcion
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_estado_bitacora
	 * @param nom_estado_bitacora
	 * @param cumplimiento
	 * @param id_localidad
	 * @param nom_localidad
	 * @param est_tarea_proveedor
	 * @param usu_ingresa
	 * @param nom_usuario_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param nom_usuario_modifica
	 * @param fec_modifica
	 * @param cor_revisa
	 * @param nom_usuario_revisa
	 * @param fec_revision
	 * @param obs_coordinador
	 */
	public modelo_tarea_proveedor(long id_tarea_proveedor, long id_cliente, String nom_cliente, String ticket_externo,
			long id_turno, String nom_turno, long id_tipo_servicio, String nom_tipo_servicio, long id_tipo_tarea,
			String nom_tipo_tarea, long id_solicitante, String nom_solicitante, String descripcion,
			Timestamp fec_inicio, Timestamp fec_fin, long id_estado_bitacora, String nom_estado_bitacora,
			String cumplimiento, long id_localidad, String nom_localidad, String est_tarea_proveedor,
			String usu_ingresa, String nom_usuario_ingresa, Timestamp fec_ingresa, String usu_modifica,
			String nom_usuario_modifica, Timestamp fec_modifica, String cor_revisa, String nom_usuario_revisa,
			Timestamp fec_revision, String obs_coordinador) {
		super();
		this.id_tarea_proveedor = id_tarea_proveedor;
		this.id_cliente = id_cliente;
		this.nom_cliente = nom_cliente;
		this.ticket_externo = ticket_externo;
		this.id_turno = id_turno;
		this.nom_turno = nom_turno;
		this.id_tipo_servicio = id_tipo_servicio;
		this.nom_tipo_servicio = nom_tipo_servicio;
		this.id_tipo_tarea = id_tipo_tarea;
		this.nom_tipo_tarea = nom_tipo_tarea;
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.descripcion = descripcion;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_estado_bitacora = id_estado_bitacora;
		this.nom_estado_bitacora = nom_estado_bitacora;
		this.cumplimiento = cumplimiento;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.est_tarea_proveedor = est_tarea_proveedor;
		this.usu_ingresa = usu_ingresa;
		this.nom_usuario_ingresa = nom_usuario_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.nom_usuario_modifica = nom_usuario_modifica;
		this.fec_modifica = fec_modifica;
		this.cor_revisa = cor_revisa;
		this.nom_usuario_revisa = nom_usuario_revisa;
		this.fec_revision = fec_revision;
		this.obs_coordinador = obs_coordinador;
	}

	/**
	 * @return the id_tarea_proveedor
	 */
	public long getId_tarea_proveedor() {
		return id_tarea_proveedor;
	}

	/**
	 * @param id_tarea_proveedor the id_tarea_proveedor to set
	 */
	public void setId_tarea_proveedor(long id_tarea_proveedor) {
		this.id_tarea_proveedor = id_tarea_proveedor;
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
	 * @return the est_tarea_proveedor
	 */
	public String getEst_tarea_proveedor() {
		return est_tarea_proveedor;
	}

	/**
	 * @param est_tarea_proveedor the est_tarea_proveedor to set
	 */
	public void setEst_tarea_proveedor(String est_tarea_proveedor) {
		this.est_tarea_proveedor = est_tarea_proveedor;
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

	/**
	 * @return the cor_revisa
	 */
	public String getCor_revisa() {
		return cor_revisa;
	}

	/**
	 * @param cor_revisa the cor_revisa to set
	 */
	public void setCor_revisa(String cor_revisa) {
		this.cor_revisa = cor_revisa;
	}

	/**
	 * @return the nom_usuario_revisa
	 */
	public String getNom_usuario_revisa() {
		return nom_usuario_revisa;
	}

	/**
	 * @param nom_usuario_revisa the nom_usuario_revisa to set
	 */
	public void setNom_usuario_revisa(String nom_usuario_revisa) {
		this.nom_usuario_revisa = nom_usuario_revisa;
	}

	/**
	 * @return the fec_revision
	 */
	public Timestamp getFec_revision() {
		return fec_revision;
	}

	/**
	 * @param fec_revision the fec_revision to set
	 */
	public void setFec_revision(Timestamp fec_revision) {
		this.fec_revision = fec_revision;
	}

	/**
	 * @return the obs_coordinador
	 */
	public String getObs_coordinador() {
		return obs_coordinador;
	}

	/**
	 * @param obs_coordinador the obs_coordinador to set
	 */
	public void setObs_coordinador(String obs_coordinador) {
		this.obs_coordinador = obs_coordinador;
	}

	@Override
	public String toString() {
		return "modelo_tarea_proveedor [id_tarea_proveedor=" + id_tarea_proveedor + ", id_cliente=" + id_cliente
				+ ", nom_cliente=" + nom_cliente + ", ticket_externo=" + ticket_externo + ", id_turno=" + id_turno
				+ ", nom_turno=" + nom_turno + ", id_tipo_servicio=" + id_tipo_servicio + ", nom_tipo_servicio="
				+ nom_tipo_servicio + ", id_tipo_tarea=" + id_tipo_tarea + ", nom_tipo_tarea=" + nom_tipo_tarea
				+ ", id_solicitante=" + id_solicitante + ", nom_solicitante=" + nom_solicitante + ", descripcion="
				+ descripcion + ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin + ", id_estado_bitacora="
				+ id_estado_bitacora + ", nom_estado_bitacora=" + nom_estado_bitacora + ", cumplimiento=" + cumplimiento
				+ ", id_localidad=" + id_localidad + ", nom_localidad=" + nom_localidad + ", est_tarea_proveedor="
				+ est_tarea_proveedor + ", usu_ingresa=" + usu_ingresa + ", nom_usuario_ingresa=" + nom_usuario_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", nom_usuario_modifica="
				+ nom_usuario_modifica + ", fec_modifica=" + fec_modifica + ", cor_revisa=" + cor_revisa
				+ ", nom_usuario_revisa=" + nom_usuario_revisa + ", fec_revision=" + fec_revision + ", obs_coordinador="
				+ obs_coordinador + "]";
	}

	public String mostrarEstado() {
		String estado = "-";
		if (est_tarea_proveedor.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tarea_proveedor.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String mostrarCumplimiento() {
		String _cumplimiento = "-";
		if (cumplimiento.equals("C")) {
			_cumplimiento = "CUMPLIDO";
		}
		if (cumplimiento.equals("I")) {
			_cumplimiento = "INCUMPLIDO";
		}
		return _cumplimiento;
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

	public String mostrarUsuarioRevisa() {
		String usuario = "";
		if (nom_usuario_revisa != null) {
			usuario = getNom_usuario_revisa();
		}
		return usuario;
	}

	public String mostrarObservacion() {
		String observacion = "";
		if (obs_coordinador != null) {
			observacion = getObs_coordinador();
		}
		return observacion;
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

	public String mostrarImagenUsuarioRevisa() {
		String imagen = "";
		if (nom_usuario_revisa == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenObservacion() {
		String imagen = "";
		if (obs_coordinador == null) {
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

	public String estiloCumplimiento() {
		String estilo = "";
		if (cumplimiento.equals("C")) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: #ffffcc !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: #ff6633 !important;";
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

	public String estiloImagenUsuarioRevisa() {
		String estilo = "";
		if (nom_usuario_revisa == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenObservacion() {
		String estilo = "";
		if (obs_coordinador == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

}
