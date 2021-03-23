package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_categoria {

	private long id_categoria;
	private String nom_categoria;
	private String des_categoria;
	private long id_localidad;
	private String nom_localidad;
	private String mos_marca;
	private String mos_modelo;
	private String mos_serie;
	private String mos_cod_activo;
	private String est_categoria;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_categoria() {
		super();
	}

	/**
	 * @param id_categoria
	 * @param nom_categoria
	 * @param des_categoria
	 * @param id_localidad
	 * @param nom_localidad
	 * @param mos_marca
	 * @param mos_modelo
	 * @param mos_serie
	 * @param mos_cod_activo
	 * @param est_categoria
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_categoria(long id_categoria, String nom_categoria, String des_categoria, long id_localidad,
			String nom_localidad, String mos_marca, String mos_modelo, String mos_serie, String mos_cod_activo,
			String est_categoria, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_categoria = id_categoria;
		this.nom_categoria = nom_categoria;
		this.des_categoria = des_categoria;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.mos_marca = mos_marca;
		this.mos_modelo = mos_modelo;
		this.mos_serie = mos_serie;
		this.mos_cod_activo = mos_cod_activo;
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
	 * @return the mos_marca
	 */
	public String getMos_marca() {
		return mos_marca;
	}

	/**
	 * @param mos_marca the mos_marca to set
	 */
	public void setMos_marca(String mos_marca) {
		this.mos_marca = mos_marca;
	}

	/**
	 * @return the mos_modelo
	 */
	public String getMos_modelo() {
		return mos_modelo;
	}

	/**
	 * @param mos_modelo the mos_modelo to set
	 */
	public void setMos_modelo(String mos_modelo) {
		this.mos_modelo = mos_modelo;
	}

	/**
	 * @return the mos_serie
	 */
	public String getMos_serie() {
		return mos_serie;
	}

	/**
	 * @param mos_serie the mos_serie to set
	 */
	public void setMos_serie(String mos_serie) {
		this.mos_serie = mos_serie;
	}

	/**
	 * @return the mos_cod_activo
	 */
	public String getMos_cod_activo() {
		return mos_cod_activo;
	}

	/**
	 * @param mos_cod_activo the mos_cod_activo to set
	 */
	public void setMos_cod_activo(String mos_cod_activo) {
		this.mos_cod_activo = mos_cod_activo;
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
		return "modelo_categoria [id_categoria=" + id_categoria + ", nom_categoria=" + nom_categoria
				+ ", des_categoria=" + des_categoria + ", id_localidad=" + id_localidad + ", nom_localidad="
				+ nom_localidad + ", mos_marca=" + mos_marca + ", mos_modelo=" + mos_modelo + ", mos_serie=" + mos_serie
				+ ", mos_cod_activo=" + mos_cod_activo + ", est_categoria=" + est_categoria + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
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

	public String mostrarImagenMarca() {
		String imagen = "";
		if (mos_marca.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenModelo() {
		String imagen = "";
		if (mos_modelo.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenSerie() {
		String imagen = "";
		if (mos_serie.equals("N")) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			imagen = "/img/botones/ButtonOK.png";
		}
		return imagen;
	}

	public String mostrarImagenCodigoActivo() {
		String imagen = "";
		if (mos_cod_activo.equals("N")) {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 5, id_categoria, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 5, id_categoria, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 5, id_categoria, 7);
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
