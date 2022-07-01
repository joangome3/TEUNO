package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

@Entity
@Table(name = "sibod_solicitante")
public class modelo_solicitante {

	@Id
	@Column(name = "id_solicitante", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_solicitante;
	@Column(name = "nom_solicitante", length = 100)
	private String nom_solicitante;
	@Column(name = "ape_solicitante", length = 100)
	private String ape_solicitante;
	@Column(name = "num_documento", length = 100)
	private String num_documento;
	@Column(name = "est_solicitante", length = 5)
	private String est_solicitante;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "solicitante", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_solicitante_localidad> relaciones_solicitante_localidad;

	@OneToMany(mappedBy = "solicitante", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_solicitante_mantenimiento> relaciones_solicitante_mantenimiento;

	@OneToMany(mappedBy = "solicitante", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_solicitante_opcion> relaciones_solicitante_opcion;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_tipo_documento")
	private modelo_tipo_documento tipo_documento;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_empresa")
	private modelo_empresa empresa;

	public modelo_solicitante() {
		super();
	}

	/**
	 * @param nom_solicitante
	 * @param ape_solicitante
	 * @param num_documento
	 * @param est_solicitante
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitante(String nom_solicitante, String ape_solicitante, String num_documento,
			String est_solicitante, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.nom_solicitante = nom_solicitante;
		this.ape_solicitante = ape_solicitante;
		this.num_documento = num_documento;
		this.est_solicitante = est_solicitante;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param ape_solicitante
	 * @param num_documento
	 * @param est_solicitante
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitante(long id_solicitante, String nom_solicitante, String ape_solicitante, String num_documento,
			String est_solicitante, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.ape_solicitante = ape_solicitante;
		this.num_documento = num_documento;
		this.est_solicitante = est_solicitante;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the ape_solicitante
	 */
	public String getApe_solicitante() {
		return ape_solicitante;
	}

	/**
	 * @param ape_solicitante the ape_solicitante to set
	 */
	public void setApe_solicitante(String ape_solicitante) {
		this.ape_solicitante = ape_solicitante;
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
	 * @return the est_solicitante
	 */
	public String getEst_solicitante() {
		return est_solicitante;
	}

	/**
	 * @param est_solicitante the est_solicitante to set
	 */
	public void setEst_solicitante(String est_solicitante) {
		this.est_solicitante = est_solicitante;
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
	 * @return the relaciones_solicitante_localidad
	 */
	public List<modelo_relacion_solicitante_localidad> getRelaciones_solicitante_localidad() {
		return relaciones_solicitante_localidad;
	}

	/**
	 * @param relaciones_solicitante_localidad the relaciones_solicitante_localidad
	 *                                         to set
	 */
	public void setRelaciones_solicitante_localidad(
			List<modelo_relacion_solicitante_localidad> relaciones_solicitante_localidad) {
		this.relaciones_solicitante_localidad = relaciones_solicitante_localidad;
	}

	/**
	 * @return the relaciones_solicitante_mantenimiento
	 */
	public List<modelo_relacion_solicitante_mantenimiento> getRelaciones_solicitante_mantenimiento() {
		return relaciones_solicitante_mantenimiento;
	}

	/**
	 * @param relaciones_solicitante_mantenimiento the
	 *                                             relaciones_solicitante_mantenimiento
	 *                                             to set
	 */
	public void setRelaciones_solicitante_mantenimiento(
			List<modelo_relacion_solicitante_mantenimiento> relaciones_solicitante_mantenimiento) {
		this.relaciones_solicitante_mantenimiento = relaciones_solicitante_mantenimiento;
	}

	/**
	 * @return the relaciones_solicitante_opcion
	 */
	public List<modelo_relacion_solicitante_opcion> getRelaciones_solicitante_opcion() {
		return relaciones_solicitante_opcion;
	}

	/**
	 * @param relaciones_solicitante_opcion the relaciones_solicitante_opcion to set
	 */
	public void setRelaciones_solicitante_opcion(
			List<modelo_relacion_solicitante_opcion> relaciones_solicitante_opcion) {
		this.relaciones_solicitante_opcion = relaciones_solicitante_opcion;
	}

	/**
	 * @return the tipo_documento
	 */
	public modelo_tipo_documento getTipo_documento() {
		return tipo_documento;
	}

	/**
	 * @param tipo_documento the tipo_documento to set
	 */
	public void setTipo_documento(modelo_tipo_documento tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	/**
	 * @return the empresa
	 */
	public modelo_empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(modelo_empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "modelo_solicitante [id_solicitante=" + id_solicitante + ", nom_solicitante=" + nom_solicitante
				+ ", ape_solicitante=" + ape_solicitante + ", num_documento=" + num_documento + ", est_solicitante="
				+ est_solicitante + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_solicitante.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_solicitante.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_solicitante.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_solicitante.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_solicitante.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_solicitante.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String toStringSolicitante() {
		return getNom_solicitante() + " " + getApe_solicitante();
	}

	public String mostrarImagenEstadoSolicitud()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String imagen = "";
		if (validarSiExisteSolicitudCreada() == true) {
			imagen = "/img/botones/ButtonEye.png";
		} else if (validarSiExisteSolicitudPendienteEjecucionOActualizacion()) {
			imagen = "/img/botones/ButtonOK.png";
		} else {
			imagen = "/img/botones/ButtonRequire.png";
		}
		return imagen;
	}

	public boolean validarSiExisteSolicitudCreada()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_creada = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 1, id_solicitante, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("P") || estado.equals("R")) {
					existe_solicitud_creada = true;
				}
			}
		}
		return existe_solicitud_creada;
	}

	public boolean validarSiExisteSolicitudCerrada()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_cerrada = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 1, id_solicitante, 9);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("E") || estado.equals("N") || estado.equals("A")) {
					existe_solicitud_cerrada = true;
				}
			}
		}
		return existe_solicitud_cerrada;
	}

	public boolean validarSiExisteSolicitudPendienteEjecucionOActualizacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_pendiente = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 1, id_solicitante, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S") || estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

}
