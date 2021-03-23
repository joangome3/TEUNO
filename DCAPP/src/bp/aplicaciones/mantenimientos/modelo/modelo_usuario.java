package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_usuario {

	private long id_usuario;
	private String use_usuario;
	private String pas_usuario;
	private String nom_usuario;
	private String ape_usuario;
	private long id_perfil;
	private String nom_perfil;
	private String cam_password;
	private long id_localidad;
	private String nom_localidad;
	private String est_usuario;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_usuario() {
		super();
	}

	/**
	 * @param id_usuario
	 * @param use_usuario
	 * @param pas_usuario
	 * @param nom_usuario
	 * @param ape_usuario
	 * @param id_perfil
	 * @param nom_perfil
	 * @param cam_password
	 * @param id_localidad
	 * @param nom_localidad
	 * @param est_usuario
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_usuario(long id_usuario, String use_usuario, String pas_usuario, String nom_usuario,
			String ape_usuario, long id_perfil, String nom_perfil, String cam_password, long id_localidad,
			String nom_localidad, String est_usuario, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_usuario = id_usuario;
		this.use_usuario = use_usuario;
		this.pas_usuario = pas_usuario;
		this.nom_usuario = nom_usuario;
		this.ape_usuario = ape_usuario;
		this.id_perfil = id_perfil;
		this.nom_perfil = nom_perfil;
		this.cam_password = cam_password;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.est_usuario = est_usuario;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_usuario
	 * @param use_usuario
	 * @param pas_usuario
	 * @param nom_usuario
	 * @param ape_usuario
	 * @param id_perfil
	 * @param cam_password
	 * @param id_localidad
	 * @param est_usuario
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_usuario(long id_usuario, String use_usuario, String pas_usuario, String nom_usuario,
			String ape_usuario, long id_perfil, String cam_password, long id_localidad, String est_usuario,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_usuario = id_usuario;
		this.use_usuario = use_usuario;
		this.pas_usuario = pas_usuario;
		this.nom_usuario = nom_usuario;
		this.ape_usuario = ape_usuario;
		this.id_perfil = id_perfil;
		this.cam_password = cam_password;
		this.id_localidad = id_localidad;
		this.est_usuario = est_usuario;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the use_usuario
	 */
	public String getUse_usuario() {
		return use_usuario;
	}

	/**
	 * @param use_usuario the use_usuario to set
	 */
	public void setUse_usuario(String use_usuario) {
		this.use_usuario = use_usuario;
	}

	/**
	 * @return the pas_usuario
	 */
	public String getPas_usuario() {
		return pas_usuario;
	}

	/**
	 * @param pas_usuario the pas_usuario to set
	 */
	public void setPas_usuario(String pas_usuario) {
		this.pas_usuario = pas_usuario;
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
	 * @return the ape_usuario
	 */
	public String getApe_usuario() {
		return ape_usuario;
	}

	/**
	 * @param ape_usuario the ape_usuario to set
	 */
	public void setApe_usuario(String ape_usuario) {
		this.ape_usuario = ape_usuario;
	}

	/**
	 * @return the id_perfil
	 */
	public long getId_perfil() {
		return id_perfil;
	}

	/**
	 * @param id_perfil the id_perfil to set
	 */
	public void setId_perfil(long id_perfil) {
		this.id_perfil = id_perfil;
	}

	/**
	 * @return the nom_perfil
	 */
	public String getNom_perfil() {
		return nom_perfil;
	}

	/**
	 * @param nom_perfil the nom_perfil to set
	 */
	public void setNom_perfil(String nom_perfil) {
		this.nom_perfil = nom_perfil;
	}

	/**
	 * @return the cam_password
	 */
	public String getCam_password() {
		return cam_password;
	}

	/**
	 * @param cam_password the cam_password to set
	 */
	public void setCam_password(String cam_password) {
		this.cam_password = cam_password;
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
	 * @return the est_usuario
	 */
	public String getEst_usuario() {
		return est_usuario;
	}

	/**
	 * @param est_usuario the est_usuario to set
	 */
	public void setEst_usuario(String est_usuario) {
		this.est_usuario = est_usuario;
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
		return "modelo_usuario [id_usuario=" + id_usuario + ", use_usuario=" + use_usuario + ", pas_usuario="
				+ pas_usuario + ", nom_usuario=" + nom_usuario + ", ape_usuario=" + ape_usuario + ", id_perfil="
				+ id_perfil + ", nom_perfil=" + nom_perfil + ", cam_password=" + cam_password + ", id_localidad="
				+ id_localidad + ", nom_localidad=" + nom_localidad + ", est_usuario=" + est_usuario + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String verNombreCompleto() {
		return nom_usuario + " " + ape_usuario;
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_usuario.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_usuario.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_usuario.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_usuario.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_usuario.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_usuario.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String mostrarImagenCambiarPassword() {
		String imagen = "";
		if (cam_password.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String estiloImagenCambiarPassword() {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 4, id_usuario, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 4, id_usuario, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 4, id_usuario, 7);
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
