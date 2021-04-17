package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_tipo_ingreso {

	private long id_tipo_ingreso;
	private String nom_tipo_ingreso;
	private String des_tipo_ingreso;
	private String est_tipo_ingreso;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_tipo_ingreso() {
		super();
	}

	/**
	 * @param id_tipo_ingreso
	 * @param nom_tipo_ingreso
	 * @param des_tipo_ingreso
	 * @param est_tipo_ingreso
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_ingreso(long id_tipo_ingreso, String nom_tipo_ingreso, String des_tipo_ingreso,
			String est_tipo_ingreso, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_tipo_ingreso = id_tipo_ingreso;
		this.nom_tipo_ingreso = nom_tipo_ingreso;
		this.des_tipo_ingreso = des_tipo_ingreso;
		this.est_tipo_ingreso = est_tipo_ingreso;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the des_tipo_ingreso
	 */
	public String getDes_tipo_ingreso() {
		return des_tipo_ingreso;
	}

	/**
	 * @param des_tipo_ingreso the des_tipo_ingreso to set
	 */
	public void setDes_tipo_ingreso(String des_tipo_ingreso) {
		this.des_tipo_ingreso = des_tipo_ingreso;
	}

	/**
	 * @return the est_tipo_ingreso
	 */
	public String getEst_tipo_ingreso() {
		return est_tipo_ingreso;
	}

	/**
	 * @param est_tipo_ingreso the est_tipo_ingreso to set
	 */
	public void setEst_tipo_ingreso(String est_tipo_ingreso) {
		this.est_tipo_ingreso = est_tipo_ingreso;
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
		return "modelo_tipo_ingreso [id_tipo_ingreso=" + id_tipo_ingreso + ", nom_tipo_ingreso=" + nom_tipo_ingreso
				+ ", des_tipo_ingreso=" + des_tipo_ingreso + ", est_tipo_ingreso=" + est_tipo_ingreso + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_tipo_ingreso.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_ingreso.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_ingreso.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_tipo_ingreso.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_tipo_ingreso.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_tipo_ingreso.charAt(0) == 'I') {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 18, id_tipo_ingreso, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 18, id_tipo_ingreso, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 18, id_tipo_ingreso, 7);
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
