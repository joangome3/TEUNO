package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "nocap_etiquetado_pdu")
public class modelo_etiquetado_pdu {

	@Id
	@Column(name = "id_etiqueta_pdu", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_etiqueta_pdu;
	@Column(name = "nom_etiqueta", length = 500)
	private String nom_etiqueta;
	@Column(name = "est_etiqueta", length = 5)
	private String est_etiqueta;
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
	public modelo_etiquetado_pdu() {
	}

	/**
	 * @param nom_etiqueta
	 * @param est_etiqueta
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_etiquetado_pdu(String nom_etiqueta, String est_etiqueta, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.nom_etiqueta = nom_etiqueta;
		this.est_etiqueta = est_etiqueta;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_etiqueta_pdu
	 * @param nom_etiqueta
	 * @param est_etiqueta
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_etiquetado_pdu(long id_etiqueta_pdu, String nom_etiqueta, String est_etiqueta, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_etiqueta_pdu = id_etiqueta_pdu;
		this.nom_etiqueta = nom_etiqueta;
		this.est_etiqueta = est_etiqueta;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_etiqueta_pdu
	 */
	public long getId_etiqueta_pdu() {
		return id_etiqueta_pdu;
	}

	/**
	 * @param id_etiqueta_pdu the id_etiqueta_pdu to set
	 */
	public void setId_etiqueta_pdu(long id_etiqueta_pdu) {
		this.id_etiqueta_pdu = id_etiqueta_pdu;
	}

	/**
	 * @return the nom_etiqueta
	 */
	public String getNom_etiqueta() {
		return nom_etiqueta;
	}

	/**
	 * @param nom_etiqueta the nom_etiqueta to set
	 */
	public void setNom_etiqueta(String nom_etiqueta) {
		this.nom_etiqueta = nom_etiqueta;
	}

	/**
	 * @return the est_etiqueta
	 */
	public String getEst_etiqueta() {
		return est_etiqueta;
	}

	/**
	 * @param est_etiqueta the est_etiqueta to set
	 */
	public void setEst_etiqueta(String est_etiqueta) {
		this.est_etiqueta = est_etiqueta;
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
		return "modelo_etiquetado_pdu [id_etiqueta_pdu=" + id_etiqueta_pdu + ", nom_etiqueta=" + nom_etiqueta
				+ ", est_etiqueta=" + est_etiqueta + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
