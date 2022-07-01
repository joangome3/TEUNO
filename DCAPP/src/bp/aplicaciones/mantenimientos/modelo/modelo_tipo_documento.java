package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sibod_tipo_documento")
public class modelo_tipo_documento {

	@Id
	@Column(name = "id_tipo_documento", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_tipo_documento;
	@Column(name = "nom_tipo_documento", length = 100)
	private String nom_tipo_documento;
	@Column(name = "est_tipo_documento", length = 5)
	private String est_tipo_documento;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "tipo_documento", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_solicitante> solicitantes;

	public modelo_tipo_documento() {
	}

	/**
	 * @param nom_tipo_documento
	 * @param est_tipo_documento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_documento(String nom_tipo_documento, String est_tipo_documento, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.nom_tipo_documento = nom_tipo_documento;
		this.est_tipo_documento = est_tipo_documento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_tipo_documento
	 * @param nom_tipo_documento
	 * @param est_tipo_documento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_documento(long id_tipo_documento, String nom_tipo_documento, String est_tipo_documento,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_tipo_documento = id_tipo_documento;
		this.nom_tipo_documento = nom_tipo_documento;
		this.est_tipo_documento = est_tipo_documento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_documento
	 */
	public long getId_tipo_documento() {
		return id_tipo_documento;
	}

	/**
	 * @param id_tipo_documento the id_tipo_documento to set
	 */
	public void setId_tipo_documento(long id_tipo_documento) {
		this.id_tipo_documento = id_tipo_documento;
	}

	/**
	 * @return the nom_tipo_documento
	 */
	public String getNom_tipo_documento() {
		return nom_tipo_documento;
	}

	/**
	 * @param nom_tipo_documento the nom_tipo_documento to set
	 */
	public void setNom_tipo_documento(String nom_tipo_documento) {
		this.nom_tipo_documento = nom_tipo_documento;
	}

	/**
	 * @return the est_tipo_documento
	 */
	public String getEst_tipo_documento() {
		return est_tipo_documento;
	}

	/**
	 * @param est_tipo_documento the est_tipo_documento to set
	 */
	public void setEst_tipo_documento(String est_tipo_documento) {
		this.est_tipo_documento = est_tipo_documento;
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
	 * @return the solicitantes
	 */
	public List<modelo_solicitante> getSolicitantes() {
		return solicitantes;
	}

	/**
	 * @param solicitantes the solicitantes to set
	 */
	public void setSolicitantes(List<modelo_solicitante> solicitantes) {
		this.solicitantes = solicitantes;
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_tipo_documento.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_documento.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_documento.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_tipo_documento.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_tipo_documento.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_tipo_documento.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
