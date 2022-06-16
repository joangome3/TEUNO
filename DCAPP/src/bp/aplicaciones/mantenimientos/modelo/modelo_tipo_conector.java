package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "nocap_tipo_conector")
public class modelo_tipo_conector {

	@Id
	@Column(name = "id_tipo_conector", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_tipo_conector;
	@Column(name = "nom_tipo_conector", length = 500)
	private String nom_tipo_conector;
	@Column(name = "vol_tipo_conector")
	private int vol_tipo_conector;
	@Column(name = "est_tipo_conector", length = 5)
	private String est_tipo_conector;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "tipo_conector", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	private List<modelo_relacion_equipo_tipo_conector> relacion_equipo_tipo_conector;

	/**
	 * 
	 */
	public modelo_tipo_conector() {
	}

	/**
	 * @param nom_tipo_conector
	 * @param vol_tipo_conector
	 * @param est_tipo_conector
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_conector(String nom_tipo_conector, int vol_tipo_conector, String est_tipo_conector,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.nom_tipo_conector = nom_tipo_conector;
		this.vol_tipo_conector = vol_tipo_conector;
		this.est_tipo_conector = est_tipo_conector;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_tipo_conector
	 * @param nom_tipo_conector
	 * @param vol_tipo_conector
	 * @param est_tipo_conector
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_tipo_conector(long id_tipo_conector, String nom_tipo_conector, int vol_tipo_conector,
			String est_tipo_conector, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		this.id_tipo_conector = id_tipo_conector;
		this.nom_tipo_conector = nom_tipo_conector;
		this.vol_tipo_conector = vol_tipo_conector;
		this.est_tipo_conector = est_tipo_conector;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_tipo_conector
	 */
	public long getId_tipo_conector() {
		return id_tipo_conector;
	}

	/**
	 * @param id_tipo_conector the id_tipo_conector to set
	 */
	public void setId_tipo_conector(long id_tipo_conector) {
		this.id_tipo_conector = id_tipo_conector;
	}

	/**
	 * @return the nom_tipo_conector
	 */
	public String getNom_tipo_conector() {
		return nom_tipo_conector;
	}

	/**
	 * @param nom_tipo_conector the nom_tipo_conector to set
	 */
	public void setNom_tipo_conector(String nom_tipo_conector) {
		this.nom_tipo_conector = nom_tipo_conector;
	}

	/**
	 * @return the vol_tipo_conector
	 */
	public int getVol_tipo_conector() {
		return vol_tipo_conector;
	}

	/**
	 * @param vol_tipo_conector the vol_tipo_conector to set
	 */
	public void setVol_tipo_conector(int vol_tipo_conector) {
		this.vol_tipo_conector = vol_tipo_conector;
	}

	/**
	 * @return the est_tipo_conector
	 */
	public String getEst_tipo_conector() {
		return est_tipo_conector;
	}

	/**
	 * @param est_tipo_conector the est_tipo_conector to set
	 */
	public void setEst_tipo_conector(String est_tipo_conector) {
		this.est_tipo_conector = est_tipo_conector;
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
	 * @return the relacion_equipo_tipo_conector
	 */
	public List<modelo_relacion_equipo_tipo_conector> getRelacion_equipo_tipo_conector() {
		return relacion_equipo_tipo_conector;
	}

	/**
	 * @param relacion_equipo_tipo_conector the relacion_equipo_tipo_conector to set
	 */
	public void setRelacion_equipo_tipo_conector(
			List<modelo_relacion_equipo_tipo_conector> relacion_equipo_tipo_conector) {
		this.relacion_equipo_tipo_conector = relacion_equipo_tipo_conector;
	}

	@Override
	public String toString() {
		return "modelo_tipo_conector [id_tipo_conector=" + id_tipo_conector + ", nom_tipo_conector=" + nom_tipo_conector
				+ ", vol_tipo_conector=" + vol_tipo_conector + ", est_tipo_conector=" + est_tipo_conector
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_tipo_conector.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_tipo_conector.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_tipo_conector.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_tipo_conector.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_tipo_conector.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_tipo_conector.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
