package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "nocap_modelo")
public class modelo_modelo {

	@Id
	@Column(name = "id_modelo", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_modelo;
	@Column(name = "nom_modelo", length = 500)
	private String nom_modelo;
	@Column(name = "est_modelo", length = 5)
	private String est_modelo;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "modelo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_equipo> equipos;

	@OneToMany(mappedBy = "modelo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_rack> rack;

	/**
	 * 
	 */
	public modelo_modelo() {
	}

	/**
	 * @param nom_modelo
	 * @param est_modelo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_modelo(String nom_modelo, String est_modelo, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_modelo = nom_modelo;
		this.est_modelo = est_modelo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_modelo
	 * @param nom_modelo
	 * @param est_modelo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_modelo(long id_modelo, String nom_modelo, String est_modelo, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_modelo = id_modelo;
		this.nom_modelo = nom_modelo;
		this.est_modelo = est_modelo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_modelo
	 */
	public long getId_modelo() {
		return id_modelo;
	}

	/**
	 * @param id_modelo the id_modelo to set
	 */
	public void setId_modelo(long id_modelo) {
		this.id_modelo = id_modelo;
	}

	/**
	 * @return the nom_modelo
	 */
	public String getNom_modelo() {
		return nom_modelo;
	}

	/**
	 * @param nom_modelo the nom_modelo to set
	 */
	public void setNom_modelo(String nom_modelo) {
		this.nom_modelo = nom_modelo;
	}

	/**
	 * @return the est_modelo
	 */
	public String getEst_modelo() {
		return est_modelo;
	}

	/**
	 * @param est_modelo the est_modelo to set
	 */
	public void setEst_modelo(String est_modelo) {
		this.est_modelo = est_modelo;
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
	 * @return the equipos
	 */
	public List<modelo_equipo> getEquipos() {
		return equipos;
	}

	/**
	 * @param equipo the equipo to set
	 */
	public void setEquipos(List<modelo_equipo> equipos) {
		this.equipos = equipos;
	}

	/**
	 * @return the rack
	 */
	public List<modelo_rack> getRack() {
		return rack;
	}

	/**
	 * @param rack the rack to set
	 */
	public void setRack(List<modelo_rack> rack) {
		this.rack = rack;
	}

	@Override
	public String toString() {
		return "sibod_modelo [id_modelo=" + id_modelo + ", nom_modelo=" + nom_modelo + ", est_modelo=" + est_modelo
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_modelo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_modelo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_modelo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_modelo.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_modelo.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_modelo.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
