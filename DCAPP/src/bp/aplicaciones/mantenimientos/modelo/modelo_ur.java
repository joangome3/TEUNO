package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "nocap_ur")
public class modelo_ur {

	@Id
	@Column(name = "id_ur", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_ur;
	@Column(name = "nom_ur", length = 500)
	private int nom_ur;
	@Column(name = "est_ur", length = 5)
	private String est_ur;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "ur", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_rack_ur_equipo> relacion_racks_urs_equipos;

	/**
	 * 
	 */
	public modelo_ur() {
	}

	/**
	 * @param nom_ur
	 * @param ocupado
	 * @param est_ur
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_ur(int nom_ur, String est_ur, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.nom_ur = nom_ur;
		this.est_ur = est_ur;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_ur
	 * @param nom_ur
	 * @param ocupado
	 * @param est_ur
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_ur(long id_ur, int nom_ur, String est_ur, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_ur = id_ur;
		this.nom_ur = nom_ur;
		this.est_ur = est_ur;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_ur
	 */
	public long getId_ur() {
		return id_ur;
	}

	/**
	 * @param id_ur the id_ur to set
	 */
	public void setId_ur(long id_ur) {
		this.id_ur = id_ur;
	}

	/**
	 * @return the nom_ur
	 */
	public int getNom_ur() {
		return nom_ur;
	}

	/**
	 * @param nom_ur the nom_ur to set
	 */
	public void setNom_ur(int nom_ur) {
		this.nom_ur = nom_ur;
	}

	/**
	 * @return the est_ur
	 */
	public String getEst_ur() {
		return est_ur;
	}

	/**
	 * @param est_ur the est_ur to set
	 */
	public void setEst_ur(String est_ur) {
		this.est_ur = est_ur;
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
	 * @return the relacion_racks_urs_equipos
	 */
	public List<modelo_relacion_rack_ur_equipo> getRelacion_racks_urs_equipos() {
		return relacion_racks_urs_equipos;
	}

	/**
	 * @param relacion_racks_urs_equipos the relacion_racks_urs_equipos to set
	 */
	public void setRelacion_racks_urs_equipos(List<modelo_relacion_rack_ur_equipo> relacion_racks_urs_equipos) {
		this.relacion_racks_urs_equipos = relacion_racks_urs_equipos;
	}

	@Override
	public String toString() {
		return "modelo_ur [id_ur=" + id_ur + ", nom_ur=" + nom_ur + ", est_ur=" + est_ur + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

}
