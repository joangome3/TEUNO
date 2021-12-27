package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_ubicacion_dn {

	private long id_ubicacion;
	private long id_empresa;
	private String nom_empresa;
	private long id_tip_ubicacion;
	private String nom_ubicacion;
	private String pos_ubicacion;
	private String val_capacidad;
	private int cap_ubicacion;
	private long id_localidad;
	private String nom_localidad;
	private String est_ubicacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_ubicacion_dn() {
		super();
	}

	/**
	 * @param id_ubicacion
	 * @param id_empresa
	 * @param id_tip_ubicacion
	 * @param pos_ubicacion
	 * @param val_capacidad
	 * @param cap_ubicacion
	 * @param id_localidad
	 * @param est_ubicacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_ubicacion_dn(long id_ubicacion, long id_empresa, long id_tip_ubicacion, String pos_ubicacion,
			String val_capacidad, int cap_ubicacion, long id_localidad, String est_ubicacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_ubicacion = id_ubicacion;
		this.id_empresa = id_empresa;
		this.id_tip_ubicacion = id_tip_ubicacion;
		this.pos_ubicacion = pos_ubicacion;
		this.val_capacidad = val_capacidad;
		this.cap_ubicacion = cap_ubicacion;
		this.id_localidad = id_localidad;
		this.est_ubicacion = est_ubicacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_ubicacion
	 * @param id_empresa
	 * @param nom_empresa
	 * @param id_tip_ubicacion
	 * @param nom_ubicacion
	 * @param pos_ubicacion
	 * @param val_capacidad
	 * @param cap_ubicacion
	 * @param id_localidad
	 * @param nom_localidad
	 * @param est_ubicacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_ubicacion_dn(long id_ubicacion, long id_empresa, String nom_empresa, long id_tip_ubicacion,
			String nom_ubicacion, String pos_ubicacion, String val_capacidad, int cap_ubicacion, long id_localidad,
			String nom_localidad, String est_ubicacion, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_ubicacion = id_ubicacion;
		this.id_empresa = id_empresa;
		this.nom_empresa = nom_empresa;
		this.id_tip_ubicacion = id_tip_ubicacion;
		this.nom_ubicacion = nom_ubicacion;
		this.pos_ubicacion = pos_ubicacion;
		this.val_capacidad = val_capacidad;
		this.cap_ubicacion = cap_ubicacion;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.est_ubicacion = est_ubicacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the nom_ubicacion
	 */
	public String getNom_ubicacion() {
		return nom_ubicacion;
	}

	/**
	 * @param nom_ubicacion the nom_ubicacion to set
	 */
	public void setNom_ubicacion(String nom_ubicacion) {
		this.nom_ubicacion = nom_ubicacion;
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
	 * @return the id_ubicacion
	 */
	public long getId_ubicacion() {
		return id_ubicacion;
	}

	/**
	 * @param id_ubicacion the id_ubicacion to set
	 */
	public void setId_ubicacion(long id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	/**
	 * @return the id_empresa
	 */
	public long getId_empresa() {
		return id_empresa;
	}

	/**
	 * @param id_empresa the id_empresa to set
	 */
	public void setId_empresa(long id_empresa) {
		this.id_empresa = id_empresa;
	}

	/**
	 * @return the id_tip_ubicacion
	 */
	public long getId_tip_ubicacion() {
		return id_tip_ubicacion;
	}

	/**
	 * @param id_tip_ubicacion the id_tip_ubicacion to set
	 */
	public void setId_tip_ubicacion(long id_tip_ubicacion) {
		this.id_tip_ubicacion = id_tip_ubicacion;
	}

	/**
	 * @return the pos_ubicacion
	 */
	public String getPos_ubicacion() {
		return pos_ubicacion;
	}

	/**
	 * @param pos_ubicacion the pos_ubicacion to set
	 */
	public void setPos_ubicacion(String pos_ubicacion) {
		this.pos_ubicacion = pos_ubicacion;
	}

	/**
	 * @return the val_capacidad
	 */
	public String getVal_capacidad() {
		return val_capacidad;
	}

	/**
	 * @param val_capacidad the val_capacidad to set
	 */
	public void setVal_capacidad(String val_capacidad) {
		this.val_capacidad = val_capacidad;
	}

	/**
	 * @return the cap_ubicacion
	 */
	public int getCap_ubicacion() {
		return cap_ubicacion;
	}

	/**
	 * @param cap_ubicacion the cap_ubicacion to set
	 */
	public void setCap_ubicacion(int cap_ubicacion) {
		this.cap_ubicacion = cap_ubicacion;
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
	 * @return the est_ubicacion
	 */
	public String getEst_ubicacion() {
		return est_ubicacion;
	}

	/**
	 * @param est_ubicacion the est_ubicacion to set
	 */
	public void setEst_ubicacion(String est_ubicacion) {
		this.est_ubicacion = est_ubicacion;
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
		return "modelo_ubicacion [id_ubicacion=" + id_ubicacion + ", id_empresa=" + id_empresa + ", nom_empresa="
				+ nom_empresa + ", id_tip_ubicacion=" + id_tip_ubicacion + ", nom_ubicacion=" + nom_ubicacion
				+ ", pos_ubicacion=" + pos_ubicacion + ", val_capacidad=" + val_capacidad + ", cap_ubicacion="
				+ cap_ubicacion + ", id_localidad=" + id_localidad + ", nom_localidad=" + nom_localidad
				+ ", est_ubicacion=" + est_ubicacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringUbicacion() {
		return nom_ubicacion + " - " + pos_ubicacion;
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_ubicacion.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_ubicacion.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_ubicacion.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_ubicacion.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_ubicacion.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_ubicacion.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String mostrarImagenCapacidad() {
		String imagen = "";
		if (val_capacidad.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String estiloImagenTipos() {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 15, id_ubicacion, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 15, id_ubicacion, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 15, id_ubicacion, 7);
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

	public String obtenerNombreUbicacion() {
		return nom_ubicacion + " - " + pos_ubicacion;
	}

}
