package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_rack {

	private long id_rack;
	private String coord_rack;
	private long id_cliente;
	private String nom_cliente;
	private long id_localidad;
	private String est_rack;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_rack() {
		super();
	}

	/**
	 * @param id_rack
	 * @param coord_rack
	 * @param id_cliente
	 * @param nom_cliente
	 * @param id_localidad
	 * @param est_rack
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_rack(long id_rack, String coord_rack, long id_cliente, String nom_cliente, long id_localidad,
			String est_rack, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_rack = id_rack;
		this.coord_rack = coord_rack;
		this.id_cliente = id_cliente;
		this.nom_cliente = nom_cliente;
		this.id_localidad = id_localidad;
		this.est_rack = est_rack;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_rack
	 */
	public long getId_rack() {
		return id_rack;
	}

	/**
	 * @param id_rack the id_rack to set
	 */
	public void setId_rack(long id_rack) {
		this.id_rack = id_rack;
	}

	/**
	 * @return the nom_rack
	 */
	public String getCoord_rack() {
		return coord_rack;
	}

	/**
	 * @param nom_rack the nom_rack to set
	 */
	public void setCoord_rack(String coord_rack) {
		this.coord_rack = coord_rack;
	}

	/**
	 * @return the des_rack
	 */
	public long getId_cliente() {
		return id_cliente;
	}

	/**
	 * @param des_rack the des_rack to set
	 */
	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNom_cliente() {
		return nom_cliente;
	}

	public void setNom_cliente(String nom_cliente) {
		this.nom_cliente = nom_cliente;
	}

	public long getId_localidad() {
		return id_localidad;
	}

	public void setId_localidad(long id_localidad) {
		this.id_localidad = id_localidad;
	}

	/**
	 * @return the est_rack
	 */
	public String getEst_rack() {
		return est_rack;
	}

	/**
	 * @param est_rack the est_rack to set
	 */
	public void setEst_rack(String est_rack) {
		this.est_rack = est_rack;
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

	/* Se sobreescribe el metodo toString */

	@Override
	public String toString() {
		return "modelo_rack [id_rack=" + id_rack + ", coord_rack=" + coord_rack + ", id_cliente=" + id_cliente
				+ ", nom_cliente=" + nom_cliente + ", id_localidad=" + id_localidad + ", est_rack=" + est_rack
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_rack.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_rack.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_rack.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_rack.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_rack.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_rack.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 22, id_rack, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 22, id_rack, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 22, id_rack, 7);
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
