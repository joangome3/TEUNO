package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "nocap_relacion_rack_ur_equipo")
public class modelo_relacion_rack_ur_equipo {

	@Id
	@Column(name = "id_relacion", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_relacion;
	@Column(name = "ocupado")
	private boolean ocupado;
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
	@JoinColumn(name = "id_rack")
	private modelo_rack rack;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_ur")
	private modelo_ur ur;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_equipo")
	private modelo_equipo equipo;

	/**
	 * 
	 */
	public modelo_relacion_rack_ur_equipo() {
	}

	/**
	 * @param ocupado
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_rack_ur_equipo(boolean ocupado, String est_relacion, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.ocupado = ocupado;
		this.est_relacion = est_relacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_relacion
	 * @param ocupado
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_rack_ur_equipo(long id_relacion, boolean ocupado, String est_relacion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_relacion = id_relacion;
		this.ocupado = ocupado;
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
	 * @return the ocupado
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	/**
	 * @param ocupado the ocupado to set
	 */
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
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
	 * @return the rack
	 */
	public modelo_rack getRack() {
		return rack;
	}

	/**
	 * @param rack the rack to set
	 */
	public void setRack(modelo_rack rack) {
		this.rack = rack;
	}

	/**
	 * @return the ur
	 */
	public modelo_ur getUr() {
		return ur;
	}

	/**
	 * @param ur the ur to set
	 */
	public void setUr(modelo_ur ur) {
		this.ur = ur;
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
		return "modelo_relacion_gestion_equipo_ur [id_relacion=" + id_relacion + ", ocupado=" + ocupado
				+ ", est_relacion=" + est_relacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
