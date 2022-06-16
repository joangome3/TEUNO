package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "nocap_marca")
public class modelo_marca {

	@Id
	@Column(name = "id_marca", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_marca;
	@Column(name = "nom_marca", length = 500)
	private String nom_marca;
	@Column(name = "est_marca", length = 5)
	private String est_marca;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "marca", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_equipo> equipos;

	@OneToMany(mappedBy = "marca", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_rack> rack;

	/**
	 * 
	 */
	public modelo_marca() {
	}

	/**
	 * @param nom_marca
	 * @param est_marca
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_marca(String nom_marca, String est_marca, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_marca = nom_marca;
		this.est_marca = est_marca;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_marca
	 * @param nom_marca
	 * @param est_marca
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_marca(long id_marca, String nom_marca, String est_marca, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.id_marca = id_marca;
		this.nom_marca = nom_marca;
		this.est_marca = est_marca;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_marca
	 */
	public long getId_marca() {
		return id_marca;
	}

	/**
	 * @param id_marca the id_marca to set
	 */
	public void setId_marca(long id_marca) {
		this.id_marca = id_marca;
	}

	/**
	 * @return the nom_marca
	 */
	public String getNom_marca() {
		return nom_marca;
	}

	/**
	 * @param nom_marca the nom_marca to set
	 */
	public void setNom_marca(String nom_marca) {
		this.nom_marca = nom_marca;
	}

	/**
	 * @return the est_marca
	 */
	public String getEst_marca() {
		return est_marca;
	}

	/**
	 * @param est_marca the est_marca to set
	 */
	public void setEst_marca(String est_marca) {
		this.est_marca = est_marca;
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
		return "sibod_marca [id_marca=" + id_marca + ", nom_marca=" + nom_marca + ", est_marca=" + est_marca
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_marca.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_marca.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_marca.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_marca.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_marca.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_marca.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
