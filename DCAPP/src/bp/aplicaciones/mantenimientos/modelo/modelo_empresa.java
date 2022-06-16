package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

@Entity
@Table(name = "sibod_empresa")
public class modelo_empresa {

	@Id
	@Column(name = "id_empresa", nullable = false)
	private long id_empresa;
	@Column(name = "nom_empresa", length = 100)
	private String nom_empresa;
	@Column(name = "des_empresa", length = 2000)
	private String des_empresa;
	@Column(name = "est_empresa", length = 5)
	private String est_empresa;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;
	
	@OneToMany(mappedBy = "empresa", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_rack> racks;

	/**
	 * 
	 */
	public modelo_empresa() {
	}

	/**
	 * @param nom_empresa
	 * @param des_empresa
	 * @param est_empresa
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_empresa(String nom_empresa, String des_empresa, String est_empresa, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.nom_empresa = nom_empresa;
		this.des_empresa = des_empresa;
		this.est_empresa = est_empresa;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_empresa
	 * @param nom_empresa
	 * @param des_empresa
	 * @param est_empresa
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_empresa(long id_empresa, String nom_empresa, String des_empresa, String est_empresa,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_empresa = id_empresa;
		this.nom_empresa = nom_empresa;
		this.des_empresa = des_empresa;
		this.est_empresa = est_empresa;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
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
	 * @return the des_empresa
	 */
	public String getDes_empresa() {
		return des_empresa;
	}

	/**
	 * @param des_empresa the des_empresa to set
	 */
	public void setDes_empresa(String des_empresa) {
		this.des_empresa = des_empresa;
	}

	/**
	 * @return the est_empresa
	 */
	public String getEst_empresa() {
		return est_empresa;
	}

	/**
	 * @param est_empresa the est_empresa to set
	 */
	public void setEst_empresa(String est_empresa) {
		this.est_empresa = est_empresa;
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
	 * @return the racks
	 */
	public List<modelo_rack> getRacks() {
		return racks;
	}

	/**
	 * @param racks the racks to set
	 */
	public void setRacks(List<modelo_rack> racks) {
		this.racks = racks;
	}

	@Override
	public String toString() {
		return "modelo_empresa [id_empresa=" + id_empresa + ", nom_empresa=" + nom_empresa + ", des_empresa="
				+ des_empresa + ", est_empresa=" + est_empresa + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_empresa.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_empresa.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_empresa.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_empresa.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_empresa.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_empresa.charAt(0) == 'I') {
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 8, id_empresa, 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 8, id_empresa, 9);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 8, id_empresa, 7);
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
