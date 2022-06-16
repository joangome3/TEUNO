package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "nocap_observacion")
public class modelo_observacion {

	@Id
	@Column(name = "id_observacion", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_observacion;
	@Column(name = "nom_observacion", length = 500)
	private String nom_observacion;
	@Column(name = "est_observacion", length = 5)
	private String est_observacion;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_equipo")
	private modelo_equipo equipo;

	/**
	 * 
	 */
	public modelo_observacion() {
	}

	/**
	 * @param nom_observacion
	 * @param est_observacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_observacion(String nom_observacion, String est_observacion, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.nom_observacion = nom_observacion;
		this.est_observacion = est_observacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_observacion
	 * @param nom_observacion
	 * @param est_observacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_observacion(long id_observacion, String nom_observacion, String est_observacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_observacion = id_observacion;
		this.nom_observacion = nom_observacion;
		this.est_observacion = est_observacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_observacion
	 */
	public long getId_observacion() {
		return id_observacion;
	}

	/**
	 * @param id_observacion the id_observacion to set
	 */
	public void setId_observacion(long id_observacion) {
		this.id_observacion = id_observacion;
	}

	/**
	 * @return the nom_observacion
	 */
	public String getNom_observacion() {
		return nom_observacion;
	}

	/**
	 * @param nom_observacion the nom_observacion to set
	 */
	public void setNom_observacion(String nom_observacion) {
		this.nom_observacion = nom_observacion;
	}

	/**
	 * @return the est_observacion
	 */
	public String getEst_observacion() {
		return est_observacion;
	}

	/**
	 * @param est_observacion the est_observacion to set
	 */
	public void setEst_observacion(String est_observacion) {
		this.est_observacion = est_observacion;
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
	 * @return the equipo
	 */
	public modelo_equipo getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo the equipo to set
	 */
	public void setEquipo(modelo_equipo equipo) {
		this.equipo = equipo;
	}

	@Override
	public String toString() {
		return "sibod_observacion [id_observacion=" + id_observacion + ", nom_observacion=" + nom_observacion
				+ ", est_observacion=" + est_observacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
