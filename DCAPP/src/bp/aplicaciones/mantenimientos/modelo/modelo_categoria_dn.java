package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_categoria_dn {

	private long id_categoria;
	private String nom_categoria;
	private String des_categoria;
	private long id_localidad;
	private String nom_localidad;
	private String mos_capacidad;
	private String mos_fec_inicio;
	private String mos_fec_fin;
	private String mos_tip_respaldo;
	private String mos_id_contenedor;
	private String est_categoria;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_categoria_dn() {
		super();
	}

	/**
	 * @param id_categoria
	 * @param nom_categoria
	 * @param des_categoria
	 * @param id_localidad
	 * @param nom_localidad
	 * @param mos_capacidad
	 * @param mos_fec_inicio
	 * @param mos_fec_fin
	 * @param mos_tip_respaldo
	 * @param mos_id_contenedor
	 * @param est_categoria
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_categoria_dn(long id_categoria, String nom_categoria, String des_categoria, long id_localidad,
			String nom_localidad, String mos_capacidad, String mos_fec_inicio, String mos_fec_fin,
			String mos_tip_respaldo, String mos_id_contenedor, String est_categoria, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_categoria = id_categoria;
		this.nom_categoria = nom_categoria;
		this.des_categoria = des_categoria;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.mos_capacidad = mos_capacidad;
		this.mos_fec_inicio = mos_fec_inicio;
		this.mos_fec_fin = mos_fec_fin;
		this.mos_tip_respaldo = mos_tip_respaldo;
		this.mos_id_contenedor = mos_id_contenedor;
		this.est_categoria = est_categoria;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_categoria
	 */
	public long getId_categoria() {
		return id_categoria;
	}

	/**
	 * @param id_categoria the id_categoria to set
	 */
	public void setId_categoria(long id_categoria) {
		this.id_categoria = id_categoria;
	}

	/**
	 * @return the nom_categoria
	 */
	public String getNom_categoria() {
		return nom_categoria;
	}

	/**
	 * @param nom_categoria the nom_categoria to set
	 */
	public void setNom_categoria(String nom_categoria) {
		this.nom_categoria = nom_categoria;
	}

	/**
	 * @return the des_categoria
	 */
	public String getDes_categoria() {
		return des_categoria;
	}

	/**
	 * @param des_categoria the des_categoria to set
	 */
	public void setDes_categoria(String des_categoria) {
		this.des_categoria = des_categoria;
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
	 * @return the mos_capacidad
	 */
	public String getMos_capacidad() {
		return mos_capacidad;
	}

	/**
	 * @param mos_capacidad the mos_capacidad to set
	 */
	public void setMos_capacidad(String mos_capacidad) {
		this.mos_capacidad = mos_capacidad;
	}

	/**
	 * @return the mos_fec_inicio
	 */
	public String getMos_fec_inicio() {
		return mos_fec_inicio;
	}

	/**
	 * @param mos_fec_inicio the mos_fec_inicio to set
	 */
	public void setMos_fec_inicio(String mos_fec_inicio) {
		this.mos_fec_inicio = mos_fec_inicio;
	}

	/**
	 * @return the mos_fec_fin
	 */
	public String getMos_fec_fin() {
		return mos_fec_fin;
	}

	/**
	 * @param mos_fec_fin the mos_fec_fin to set
	 */
	public void setMos_fec_fin(String mos_fec_fin) {
		this.mos_fec_fin = mos_fec_fin;
	}

	/**
	 * @return the mos_tip_respaldo
	 */
	public String getMos_tip_respaldo() {
		return mos_tip_respaldo;
	}

	/**
	 * @param mos_tip_respaldo the mos_tip_respaldo to set
	 */
	public void setMos_tip_respaldo(String mos_tip_respaldo) {
		this.mos_tip_respaldo = mos_tip_respaldo;
	}

	/**
	 * @return the mos_id_contenedor
	 */
	public String getMos_id_contenedor() {
		return mos_id_contenedor;
	}

	/**
	 * @param mos_id_contenedor the mos_id_contenedor to set
	 */
	public void setMos_id_contenedor(String mos_id_contenedor) {
		this.mos_id_contenedor = mos_id_contenedor;
	}

	/**
	 * @return the est_categoria
	 */
	public String getEst_categoria() {
		return est_categoria;
	}

	/**
	 * @param est_categoria the est_categoria to set
	 */
	public void setEst_categoria(String est_categoria) {
		this.est_categoria = est_categoria;
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
		return "modelo_categoria_dn [id_categoria=" + id_categoria + ", nom_categoria=" + nom_categoria
				+ ", des_categoria=" + des_categoria + ", id_localidad=" + id_localidad + ", nom_localidad="
				+ nom_localidad + ", mos_capacidad=" + mos_capacidad + ", mos_fec_inicio=" + mos_fec_inicio
				+ ", mos_fec_fin=" + mos_fec_fin + ", mos_tip_respaldo=" + mos_tip_respaldo + ", mos_id_contenedor="
				+ mos_id_contenedor + ", est_categoria=" + est_categoria + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_categoria.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_categoria.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_categoria.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_categoria.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_categoria.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_categoria.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String mostrarImagenCapacidad() {
		String imagen = "";
		if (mos_capacidad.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaInicio() {
		String imagen = "";
		if (mos_fec_inicio.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaFin() {
		String imagen = "";
		if (mos_fec_fin.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenTipoRespaldo() {
		String imagen = "";
		if (mos_tip_respaldo.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenIdContenedor() {
		String imagen = "";
		if (mos_id_contenedor.equals("N")) {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 14, id_categoria, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 14, id_categoria, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 14, id_categoria, 7);
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
