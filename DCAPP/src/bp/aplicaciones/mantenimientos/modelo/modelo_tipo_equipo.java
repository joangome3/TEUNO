package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "nocap_tipo_equipo")
public class modelo_tipo_equipo {

	@Id
	@Column(name = "id_tipo_equipo", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_tipo_equipo;
	@Column(name = "nom_tipo_equipo", length = 500)
	private String nom_tipo_equipo;
	@Column(name = "est_tipo_equipo", length = 5)
	private String est_tipo_equipo;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "tipo_equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_equipo> equipos;

	/**
	 * 
	 */
	public modelo_tipo_equipo() {
	}

	/**
	 * @param nom_tipo_equipo
	 * @param est_tipo_equipo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_equipo(String nom_tipo_equipo, String est_tipo_equipo, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_tipo_equipo = nom_tipo_equipo;
		this.est_tipo_equipo = est_tipo_equipo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_tipo_equipo
	 * @param nom_tipo_equipo
	 * @param est_tipo_equipo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_equipo(long id_tipo_equipo, String nom_tipo_equipo, String est_tipo_equipo, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_tipo_equipo = id_tipo_equipo;
		this.nom_tipo_equipo = nom_tipo_equipo;
		this.est_tipo_equipo = est_tipo_equipo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_equipo
	 */
	public long getId_tipo_equipo() {
		return id_tipo_equipo;
	}

	/**
	 * @param id_tipo_equipo the id_tipo_equipo to set
	 */
	public void setId_tipo_equipo(long id_tipo_equipo) {
		this.id_tipo_equipo = id_tipo_equipo;
	}

	/**
	 * @return the nom_tipo_equipo
	 */
	public String getNom_tipo_equipo() {
		return nom_tipo_equipo;
	}

	/**
	 * @param nom_tipo_equipo the nom_tipo_equipo to set
	 */
	public void setNom_tipo_equipo(String nom_tipo_equipo) {
		this.nom_tipo_equipo = nom_tipo_equipo;
	}

	/**
	 * @return the est_tipo_equipo
	 */
	public String getEst_tipo_equipo() {
		return est_tipo_equipo;
	}

	/**
	 * @param est_tipo_equipo the est_tipo_equipo to set
	 */
	public void setEst_tipo_equipo(String est_tipo_equipo) {
		this.est_tipo_equipo = est_tipo_equipo;
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

	@Override
	public String toString() {
		return "sibod_tipo_equipo [id_tipo_equipo=" + id_tipo_equipo + ", nom_tipo_equipo=" + nom_tipo_equipo
				+ ", est_tipo_equipo=" + est_tipo_equipo + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_tipo_equipo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_equipo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_equipo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_tipo_equipo.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_tipo_equipo.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_tipo_equipo.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
