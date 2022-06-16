package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "nocap_relacion_equipo_tipo_conector")
public class modelo_relacion_equipo_tipo_conector {

	@Id
	@Column(name = "id_relacion", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_relacion;
	@Column(name = "potencia")
	private float potencia;
	@Column(name = "est_relacion", length = 5)
	private String est_relacion;
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

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_tipo_conector")
	private modelo_tipo_conector tipo_conector;

	/**
	 * 
	 */
	public modelo_relacion_equipo_tipo_conector() {
	}

	/**
	 * @param potencia
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_equipo_tipo_conector(float potencia, String est_relacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.potencia = potencia;
		this.est_relacion = est_relacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_relacion
	 * @param potencia
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_equipo_tipo_conector(long id_relacion, float potencia, String est_relacion,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_relacion = id_relacion;
		this.potencia = potencia;
		this.est_relacion = est_relacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_relacion
	 */
	public long getId_relacion() {
		return id_relacion;
	}

	/**
	 * @param id_relacion the id_relacion to set
	 */
	public void setId_relacion(long id_relacion) {
		this.id_relacion = id_relacion;
	}

	/**
	 * @return the potencia
	 */
	public float getPotencia() {
		return potencia;
	}

	/**
	 * @param potencia the potencia to set
	 */
	public void setPotencia(float potencia) {
		this.potencia = potencia;
	}

	/**
	 * @return the est_relacion
	 */
	public String getEst_relacion() {
		return est_relacion;
	}

	/**
	 * @param est_relacion the est_relacion to set
	 */
	public void setEst_relacion(String est_relacion) {
		this.est_relacion = est_relacion;
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

	/**
	 * @return the tipo_conector
	 */
	public modelo_tipo_conector getTipo_conector() {
		return tipo_conector;
	}

	/**
	 * @param tipo_conector the tipo_conector to set
	 */
	public void setTipo_conector(modelo_tipo_conector tipo_conector) {
		this.tipo_conector = tipo_conector;
	}

	@Override
	public String toString() {
		return "modelo_relacion_equipo_tipo_conector [id_relacion=" + id_relacion + ", potencia=" + potencia
				+ ", est_relacion=" + est_relacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
