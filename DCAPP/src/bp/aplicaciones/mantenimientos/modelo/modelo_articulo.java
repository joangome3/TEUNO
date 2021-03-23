package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_articulo {

	private long id_articulo;
	private String cod_articulo;
	private String des_articulo;
	private String nom_empresa;
	private long id_categoria;
	private String nom_categoria;
	private long id_ubicacion;
	private String nom_ubicacion;
	private int sto_articulo;
	private Blob img_articulo;
	private long id_localidad;
	private String nom_localidad;
	private String marca;
	private String modelo;
	private String serie;
	private String codig_activo;
	private String est_articulo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_articulo() {
		super();
	}

	/**
	 * @param id_articulo
	 * @param cod_articulo
	 * @param des_articulo
	 * @param id_categoria
	 * @param id_ubicacion
	 * @param sto_articulo
	 * @param img_articulo
	 * @param id_localidad
	 * @param marca
	 * @param modelo
	 * @param serie
	 * @param codig_activo
	 * @param est_articulo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_articulo(long id_articulo, String cod_articulo, String des_articulo, long id_categoria,
			long id_ubicacion, int sto_articulo, Blob img_articulo, long id_localidad, String marca, String modelo,
			String serie, String codig_activo, String est_articulo, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_articulo = id_articulo;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.id_categoria = id_categoria;
		this.id_ubicacion = id_ubicacion;
		this.sto_articulo = sto_articulo;
		this.img_articulo = img_articulo;
		this.id_localidad = id_localidad;
		this.marca = marca;
		this.modelo = modelo;
		this.serie = serie;
		this.codig_activo = codig_activo;
		this.est_articulo = est_articulo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_articulo
	 * @param cod_articulo
	 * @param des_articulo
	 * @param nom_empresa
	 * @param id_categoria
	 * @param nom_categoria
	 * @param id_ubicacion
	 * @param nom_ubicacion
	 * @param sto_articulo
	 * @param img_articulo
	 * @param id_localidad
	 * @param nom_localidad
	 * @param marca
	 * @param modelo
	 * @param serie
	 * @param codig_activo
	 * @param est_articulo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_articulo(long id_articulo, String cod_articulo, String des_articulo, String nom_empresa,
			long id_categoria, String nom_categoria, long id_ubicacion, String nom_ubicacion, int sto_articulo,
			Blob img_articulo, long id_localidad, String nom_localidad, String marca, String modelo, String serie,
			String codig_activo, String est_articulo, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_articulo = id_articulo;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.nom_empresa = nom_empresa;
		this.id_categoria = id_categoria;
		this.nom_categoria = nom_categoria;
		this.id_ubicacion = id_ubicacion;
		this.nom_ubicacion = nom_ubicacion;
		this.sto_articulo = sto_articulo;
		this.img_articulo = img_articulo;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.marca = marca;
		this.modelo = modelo;
		this.serie = serie;
		this.codig_activo = codig_activo;
		this.est_articulo = est_articulo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the id_articulo
	 */
	public long getId_articulo() {
		return id_articulo;
	}

	/**
	 * @param id_articulo the id_articulo to set
	 */
	public void setId_articulo(long id_articulo) {
		this.id_articulo = id_articulo;
	}

	/**
	 * @return the cod_articulo
	 */
	public String getCod_articulo() {
		return cod_articulo;
	}

	/**
	 * @param cod_articulo the cod_articulo to set
	 */
	public void setCod_articulo(String cod_articulo) {
		this.cod_articulo = cod_articulo;
	}

	/**
	 * @return the des_articulo
	 */
	public String getDes_articulo() {
		return des_articulo;
	}

	/**
	 * @param des_articulo the des_articulo to set
	 */
	public void setDes_articulo(String des_articulo) {
		this.des_articulo = des_articulo;
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
	 * @return the sto_articulo
	 */
	public int getSto_articulo() {
		return sto_articulo;
	}

	/**
	 * @param sto_articulo the sto_articulo to set
	 */
	public void setSto_articulo(int sto_articulo) {
		this.sto_articulo = sto_articulo;
	}

	/**
	 * @return the img_articulo
	 */
	public Blob getImg_articulo() {
		return img_articulo;
	}

	/**
	 * @param img_articulo the img_articulo to set
	 */
	public void setImg_articulo(Blob img_articulo) {
		this.img_articulo = img_articulo;
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
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the codig_activo
	 */
	public String getCodig_activo() {
		return codig_activo;
	}

	/**
	 * @param codig_activo the codig_activo to set
	 */
	public void setCodig_activo(String codig_activo) {
		this.codig_activo = codig_activo;
	}

	/**
	 * @return the est_articulo
	 */
	public String getEst_articulo() {
		return est_articulo;
	}

	/**
	 * @param est_articulo the est_articulo to set
	 */
	public void setEst_articulo(String est_articulo) {
		this.est_articulo = est_articulo;
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
		return "modelo_articulo [id_articulo=" + id_articulo + ", cod_articulo=" + cod_articulo + ", des_articulo="
				+ des_articulo + ", nom_empresa=" + nom_empresa + ", id_categoria=" + id_categoria + ", nom_categoria="
				+ nom_categoria + ", id_ubicacion=" + id_ubicacion + ", nom_ubicacion=" + nom_ubicacion
				+ ", sto_articulo=" + sto_articulo + ", img_articulo=" + img_articulo + ", id_localidad=" + id_localidad
				+ ", nom_localidad=" + nom_localidad + ", marca=" + marca + ", modelo=" + modelo + ", serie=" + serie
				+ ", codig_activo=" + codig_activo + ", est_articulo=" + est_articulo + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_articulo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_articulo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_articulo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_articulo.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_articulo.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_articulo.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String toStringArticulo() {
		return getCod_articulo() + " - " + getDes_articulo();
	}

	public String mostrarMarca() {
		String nombre = marca;
		if (marca == null) {
			nombre = "";
		} else {
			if (marca.length() <= 0) {
				nombre = "";
			} else {
				if (marca.equals("N/A")) {
					nombre = "";
				}
			}
		}
		return nombre;
	}

	public String mostrarModelo() {
		String nombre = modelo;
		if (modelo == null) {
			nombre = "";
		} else {
			if (modelo.length() <= 0) {
				nombre = "";
			} else {
				if (modelo.equals("N/A")) {
					nombre = "";
				}
			}
		}
		return nombre;
	}

	public String mostrarSerie() {
		String nombre = serie;
		if (serie == null) {
			nombre = "";
		} else {
			if (serie.length() <= 0) {
				nombre = "";
			} else {
				if (serie.equals("N/A")) {
					nombre = "";
				}
			}
		}
		return nombre;
	}

	public String mostrarCodigoActivo() {
		String nombre = codig_activo;
		if (codig_activo == null) {
			nombre = "";
		} else {
			if (codig_activo.length() <= 0) {
				nombre = "";
			} else {
				if (codig_activo.equals("N/A")) {
					nombre = "";
				}
			}
		}
		return nombre;
	}

	public String mostrarImagenMarca() {
		String imagen = "";
		if (marca == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (marca.length() <= 0) {
				imagen = "/img/botones/ButtonError.png";
			} else {
				if (marca.equals("N/A")) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		}
		return imagen;
	}

	public String mostrarImagenModelo() {
		String imagen = "";
		if (modelo == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (modelo.length() <= 0) {
				imagen = "/img/botones/ButtonError.png";
			} else {
				if (modelo.equals("N/A")) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		}
		return imagen;
	}

	public String mostrarImagenSerie() {
		String imagen = "";
		if (serie == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (serie.length() <= 0) {
				imagen = "/img/botones/ButtonError.png";
			} else {
				if (serie.equals("N/A")) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		}
		return imagen;
	}

	public String mostrarImagenCodigoActivo() {
		String imagen = "";
		if (codig_activo == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (codig_activo.length() <= 0) {
				imagen = "/img/botones/ButtonError.png";
			} else {
				if (codig_activo.equals("N/A")) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		}
		return imagen;
	}

	public String estiloImagenMarca() {
		String estilo = "";
		if (marca == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (marca.length() <= 0) {
				estilo = "text-align: center !important; color: transparent;";
			} else {
				if (marca.equals("N/A")) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
		}
		return estilo;
	}

	public String estiloImagenModelo() {
		String estilo = "";
		if (modelo == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (modelo.length() <= 0) {
				estilo = "text-align: center !important; color: transparent;";
			} else {
				if (modelo.equals("N/A")) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
		}
		return estilo;
	}

	public String estiloImagenSerie() {
		String estilo = "";
		if (serie == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (serie.length() <= 0) {
				estilo = "text-align: center !important; color: transparent;";
			} else {
				if (serie.equals("N/A")) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
		}
		return estilo;
	}

	public String estiloImagenCodigoActivo() {
		String estilo = "";
		if (codig_activo == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (codig_activo.length() <= 0) {
				estilo = "text-align: center !important; color: transparent;";
			} else {
				if (codig_activo.equals("N/A")) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 6, id_articulo, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 6, id_articulo, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 6, id_articulo, 7);
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
