package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nocap_fila")
public class modelo_fila {

	@Id
	@Column(name = "id_fila", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_fila;
	@Column(name = "nom_fila", length = 50)
	private String nom_fila;
	@Column(name = "des_fila", length = 500)
	private String des_fila;
	@Column(name = "est_fila", length = 5)
	private String est_fila;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_localidad")
	private modelo_localidad localidad;

	@OneToMany(mappedBy = "fila", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_rack> racks;

	/**
	 * 
	 */
	public modelo_fila() {
	}

	/**
	 * @param nom_fila
	 * @param des_fila
	 * @param est_fila
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_fila(String nom_fila, String des_fila, String est_fila, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_fila = nom_fila;
		this.des_fila = des_fila;
		this.est_fila = est_fila;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_fila
	 * @param nom_fila
	 * @param des_fila
	 * @param est_fila
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_fila(long id_fila, String nom_fila, String des_fila, String est_fila, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_fila = id_fila;
		this.nom_fila = nom_fila;
		this.des_fila = des_fila;
		this.est_fila = est_fila;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_fila
	 */
	public long getId_fila() {
		return id_fila;
	}

	/**
	 * @param id_fila the id_fila to set
	 */
	public void setId_fila(long id_fila) {
		this.id_fila = id_fila;
	}

	/**
	 * @return the nom_fila
	 */
	public String getNom_fila() {
		return nom_fila;
	}

	/**
	 * @param nom_fila the nom_fila to set
	 */
	public void setNom_fila(String nom_fila) {
		this.nom_fila = nom_fila;
	}

	/**
	 * @return the des_fila
	 */
	public String getDes_fila() {
		return des_fila;
	}

	/**
	 * @param des_fila the des_fila to set
	 */
	public void setDes_fila(String des_fila) {
		this.des_fila = des_fila;
	}

	/**
	 * @return the est_fila
	 */
	public String getEst_fila() {
		return est_fila;
	}

	/**
	 * @param est_fila the est_fila to set
	 */
	public void setEst_fila(String est_fila) {
		this.est_fila = est_fila;
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
	 * @return the localidad
	 */
	public modelo_localidad getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(modelo_localidad localidad) {
		this.localidad = localidad;
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
		return "modelo_fila [id_fila=" + id_fila + ", nom_fila=" + nom_fila + ", des_fila=" + des_fila + ", est_fila="
				+ est_fila + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_fila.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_fila.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_fila.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_fila.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_fila.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_fila.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
