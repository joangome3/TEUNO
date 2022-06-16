package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import bp.aplicaciones.equipos.modelo.modelo_gestion_equipo;

@Entity
@Table(name = "nocap_estado_equipo")
public class modelo_estado_equipo {

	@Id
	@Column(name = "id_estado", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_estado;
	@Column(name = "nom_estado", length = 500)
	private String nom_estado;
	@Column(name = "est_estado", length = 5)
	private String est_estado;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "estado_equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_gestion_equipo> gestion_equipos;

	@OneToMany(mappedBy = "estado_equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_equipo> equipos;

	/**
	 * 
	 */
	public modelo_estado_equipo() {
	}

	/**
	 * @param nom_estado
	 * @param est_estado
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_estado_equipo(String nom_estado, String est_estado, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_estado = nom_estado;
		this.est_estado = est_estado;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_estado
	 * @param nom_estado
	 * @param est_estado
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_estado_equipo(long id_estado, String nom_estado, String est_estado, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_estado = id_estado;
		this.nom_estado = nom_estado;
		this.est_estado = est_estado;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_estado
	 */
	public long getId_estado() {
		return id_estado;
	}

	/**
	 * @param id_estado the id_estado to set
	 */
	public void setId_estado(long id_estado) {
		this.id_estado = id_estado;
	}

	/**
	 * @return the nom_estado
	 */
	public String getNom_estado() {
		return nom_estado;
	}

	/**
	 * @param nom_estado the nom_estado to set
	 */
	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}

	/**
	 * @return the est_estado
	 */
	public String getEst_estado() {
		return est_estado;
	}

	/**
	 * @param est_estado the est_estado to set
	 */
	public void setEst_estado(String est_estado) {
		this.est_estado = est_estado;
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
	 * @return the gestion_equipos
	 */
	public List<modelo_gestion_equipo> getGestion_equipos() {
		return gestion_equipos;
	}

	/**
	 * @param gestion_equipos the gestion_equipos to set
	 */
	public void setGestion_equipos(List<modelo_gestion_equipo> gestion_equipos) {
		this.gestion_equipos = gestion_equipos;
	}

	/**
	 * @return the equipos
	 */
	public List<modelo_equipo> getEquipos() {
		return equipos;
	}

	/**
	 * @param equipos the equipos to set
	 */
	public void setEquipos(List<modelo_equipo> equipos) {
		this.equipos = equipos;
	}

	@Override
	public String toString() {
		return "sibod_estado [id_estado=" + id_estado + ", nom_estado=" + nom_estado + ", est_estado=" + est_estado
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_estado.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_estado.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_estado.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_estado.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_estado.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_estado.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
