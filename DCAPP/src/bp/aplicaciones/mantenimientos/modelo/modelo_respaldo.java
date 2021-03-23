package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_respaldo {

	private long id_respaldo;
	private long id_tip_respaldo;
	private String nom_tip_respaldo;
	private String dia_respaldo;
	private String es_fec_respaldo;
	private String est_respaldo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_respaldo() {
		super();
	}

	/**
	 * @param id_respaldo
	 * @param id_tip_respaldo
	 * @param dia_respaldo
	 * @param es_fec_respaldo
	 * @param est_respaldo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_respaldo(long id_respaldo, long id_tip_respaldo, String dia_respaldo, String es_fec_respaldo,
			String est_respaldo, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_respaldo = id_respaldo;
		this.id_tip_respaldo = id_tip_respaldo;
		this.dia_respaldo = dia_respaldo;
		this.es_fec_respaldo = es_fec_respaldo;
		this.est_respaldo = est_respaldo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_respaldo
	 * @param id_tip_respaldo
	 * @param nom_tip_respaldo
	 * @param dia_respaldo
	 * @param es_fec_respaldo
	 * @param est_respaldo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_respaldo(long id_respaldo, long id_tip_respaldo, String nom_tip_respaldo, String dia_respaldo,
			String es_fec_respaldo, String est_respaldo, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_respaldo = id_respaldo;
		this.id_tip_respaldo = id_tip_respaldo;
		this.nom_tip_respaldo = nom_tip_respaldo;
		this.dia_respaldo = dia_respaldo;
		this.es_fec_respaldo = es_fec_respaldo;
		this.est_respaldo = est_respaldo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_respaldo
	 */
	public long getId_respaldo() {
		return id_respaldo;
	}

	/**
	 * @param id_respaldo the id_respaldo to set
	 */
	public void setId_respaldo(long id_respaldo) {
		this.id_respaldo = id_respaldo;
	}

	/**
	 * @return the id_tip_respaldo
	 */
	public long getId_tip_respaldo() {
		return id_tip_respaldo;
	}

	/**
	 * @param id_tip_respaldo the id_tip_respaldo to set
	 */
	public void setId_tip_respaldo(long id_tip_respaldo) {
		this.id_tip_respaldo = id_tip_respaldo;
	}

	/**
	 * @return the nom_tip_respaldo
	 */
	public String getNom_tip_respaldo() {
		return nom_tip_respaldo;
	}

	/**
	 * @param nom_tip_respaldo the nom_tip_respaldo to set
	 */
	public void setNom_tip_respaldo(String nom_tip_respaldo) {
		this.nom_tip_respaldo = nom_tip_respaldo;
	}

	/**
	 * @return the dia_respaldo
	 */
	public String getDia_respaldo() {
		return dia_respaldo;
	}

	/**
	 * @param dia_respaldo the dia_respaldo to set
	 */
	public void setDia_respaldo(String dia_respaldo) {
		this.dia_respaldo = dia_respaldo;
	}

	/**
	 * @return the es_fec_respaldo
	 */
	public String getEs_fec_respaldo() {
		return es_fec_respaldo;
	}

	/**
	 * @param es_fec_respaldo the es_fec_respaldo to set
	 */
	public void setEs_fec_respaldo(String es_fec_respaldo) {
		this.es_fec_respaldo = es_fec_respaldo;
	}

	/**
	 * @return the est_respaldo
	 */
	public String getEst_respaldo() {
		return est_respaldo;
	}

	/**
	 * @param est_respaldo the est_respaldo to set
	 */
	public void setEst_respaldo(String est_respaldo) {
		this.est_respaldo = est_respaldo;
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
		return "modelo_respaldo [id_respaldo=" + id_respaldo + ", id_tip_respaldo=" + id_tip_respaldo
				+ ", nom_tip_respaldo=" + nom_tip_respaldo + ", dia_respaldo=" + dia_respaldo + ", es_fec_respaldo="
				+ es_fec_respaldo + ", est_respaldo=" + est_respaldo + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_respaldo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_respaldo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_respaldo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_respaldo.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_respaldo.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_respaldo.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String toNombreRespaldo() {
		return getNom_tip_respaldo() + " / " + getDia_respaldo();
	}

	public String mostrarImagenFechaRespaldo() {
		String imagen = "";
		if (es_fec_respaldo.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String estiloImagenFechaRespaldo() {
		String estilo = "";
		estilo = "text-align: center !important; color: transparent;";
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 12, id_respaldo, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 12, id_respaldo, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 12, id_respaldo, 7);
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
