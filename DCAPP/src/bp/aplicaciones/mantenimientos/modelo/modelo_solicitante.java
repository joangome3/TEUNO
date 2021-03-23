package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_solicitante {

	private long id_solicitante;
	private long id_tip_documento;
	private String nom_tip_documento;
	private String num_documento;
	private String nom_solicitante;
	private String ape_solicitante;
	private long id_empresa;
	private String nom_empresa;
	private String est_solicitante;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_solicitante() {
		super();
	}

	public modelo_solicitante(long id_solicitante, long id_tip_documento, String num_documento, String nom_solicitante,
			String ape_solicitante, long id_empresa, String est_solicitante, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_solicitante = id_solicitante;
		this.id_tip_documento = id_tip_documento;
		this.num_documento = num_documento;
		this.nom_solicitante = nom_solicitante;
		this.ape_solicitante = ape_solicitante;
		this.id_empresa = id_empresa;
		this.est_solicitante = est_solicitante;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_solicitante
	 * @param id_tip_documento
	 * @param num_documento
	 * @param nom_solicitante
	 * @param ape_solicitante
	 * @param id_empresa
	 * @param nom_empresa
	 * @param est_solicitante
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */

	/**
	 * @return the nom_empresa
	 */
	public String getNom_empresa() {
		return nom_empresa;
	}

	/**
	 * @param id_solicitante
	 * @param id_tip_documento
	 * @param nom_tip_documento
	 * @param num_documento
	 * @param nom_solicitante
	 * @param ape_solicitante
	 * @param id_empresa
	 * @param nom_empresa
	 * @param est_solicitante
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitante(long id_solicitante, long id_tip_documento, String nom_tip_documento,
			String num_documento, String nom_solicitante, String ape_solicitante, long id_empresa, String nom_empresa,
			String est_solicitante, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_solicitante = id_solicitante;
		this.id_tip_documento = id_tip_documento;
		this.nom_tip_documento = nom_tip_documento;
		this.num_documento = num_documento;
		this.nom_solicitante = nom_solicitante;
		this.ape_solicitante = ape_solicitante;
		this.id_empresa = id_empresa;
		this.nom_empresa = nom_empresa;
		this.est_solicitante = est_solicitante;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the nom_tip_documento
	 */
	public String getNom_tip_documento() {
		return nom_tip_documento;
	}

	/**
	 * @param nom_tip_documento the nom_tip_documento to set
	 */
	public void setNom_tip_documento(String nom_tip_documento) {
		this.nom_tip_documento = nom_tip_documento;
	}

	/**
	 * @param nom_empresa the nom_empresa to set
	 */
	public void setNom_empresa(String nom_empresa) {
		this.nom_empresa = nom_empresa;
	}

	public long getId_solicitante() {
		return id_solicitante;
	}

	public void setId_solicitante(long id_solicitante) {
		this.id_solicitante = id_solicitante;
	}

	public long getId_tip_documento() {
		return id_tip_documento;
	}

	public void setId_tip_documento(long id_tip_documento) {
		this.id_tip_documento = id_tip_documento;
	}

	public String getNum_documento() {
		return num_documento;
	}

	public void setNum_documento(String num_documento) {
		this.num_documento = num_documento;
	}

	public String getNom_solicitante() {
		return nom_solicitante;
	}

	public void setNom_solicitante(String nom_solicitante) {
		this.nom_solicitante = nom_solicitante;
	}

	public String getApe_solicitante() {
		return ape_solicitante;
	}

	public void setApe_solicitante(String ape_solicitante) {
		this.ape_solicitante = ape_solicitante;
	}

	public long getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(long id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getEst_solicitante() {
		return est_solicitante;
	}

	public void setEst_solicitante(String est_solicitante) {
		this.est_solicitante = est_solicitante;
	}

	public String getUsu_ingresa() {
		return usu_ingresa;
	}

	public void setUsu_ingresa(String usu_ingresa) {
		this.usu_ingresa = usu_ingresa;
	}

	public Timestamp getFec_ingresa() {
		return fec_ingresa;
	}

	public void setFec_ingresa(Timestamp fec_ingresa) {
		this.fec_ingresa = fec_ingresa;
	}

	public String getUsu_modifica() {
		return usu_modifica;
	}

	public void setUsu_modifica(String usu_modifica) {
		this.usu_modifica = usu_modifica;
	}

	public Timestamp getFec_modifica() {
		return fec_modifica;
	}

	public void setFec_modifica(Timestamp fec_modifica) {
		this.fec_modifica = fec_modifica;
	}

	@Override
	public String toString() {
		return "modelo_solicitante [id_solicitante=" + id_solicitante + ", id_tip_documento=" + id_tip_documento
				+ ", nom_tip_documento=" + nom_tip_documento + ", num_documento=" + num_documento + ", nom_solicitante="
				+ nom_solicitante + ", ape_solicitante=" + ape_solicitante + ", id_empresa=" + id_empresa
				+ ", nom_empresa=" + nom_empresa + ", est_solicitante=" + est_solicitante + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

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
