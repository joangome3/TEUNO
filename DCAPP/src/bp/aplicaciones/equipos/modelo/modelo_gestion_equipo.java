package bp.aplicaciones.equipos.modelo;

import java.sql.Timestamp;

import javax.persistence.*;

import bp.aplicaciones.mantenimientos.modelo.modelo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_etiquetado_pdu;
import bp.aplicaciones.mantenimientos.modelo.modelo_etiquetado_poder;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_observacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;

@Entity
@Table(name = "nocap_gestion_equipo")
public class modelo_gestion_equipo {

	@Id
	@Column(name = "id_gestion", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_gestion;
	@Column(name = "etiq_cds")
	private boolean etiq_cds;
	@Column(name = "pein_cds")
	private boolean pein_cds;
	@Column(name = "cone_ats")
	private boolean cone_ats;
	@Column(name = "fec_ingreso")
	private Timestamp fec_ingreso;
	@Column(name = "fec_inventario")
	private Timestamp fec_inventario;
	@Column(name = "est_gestion", length = 5)
	private String est_gestion;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_estado")
	private modelo_estado_equipo estado_equipo;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_rack")
	private modelo_rack rack;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_equipo")
	private modelo_equipo equipo;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_etiqueta_poder")
	private modelo_etiquetado_poder etiquetado_poder;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_etiqueta_pdu")
	private modelo_etiquetado_pdu etiquetado_pdu;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_observacion")
	private modelo_observacion observacion;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_localidad")
	private modelo_localidad localidad;

	/**
	 * 
	 */
	public modelo_gestion_equipo() {
	}

	/**
	 * @param etiq_cds
	 * @param pein_cds
	 * @param cone_ats
	 * @param fec_ingreso
	 * @param fec_inventario
	 * @param est_gestion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_gestion_equipo(boolean etiq_cds, boolean pein_cds, boolean cone_ats, Timestamp fec_ingreso,
			Timestamp fec_inventario, String est_gestion, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		this.etiq_cds = etiq_cds;
		this.pein_cds = pein_cds;
		this.cone_ats = cone_ats;
		this.fec_ingreso = fec_ingreso;
		this.fec_inventario = fec_inventario;
		this.est_gestion = est_gestion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_gestion
	 * @param etiq_cds
	 * @param pein_cds
	 * @param cone_ats
	 * @param fec_ingreso
	 * @param fec_inventario
	 * @param est_gestion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_gestion_equipo(long id_gestion, boolean etiq_cds, boolean pein_cds, boolean cone_ats,
			Timestamp fec_ingreso, Timestamp fec_inventario, String est_gestion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_gestion = id_gestion;
		this.etiq_cds = etiq_cds;
		this.pein_cds = pein_cds;
		this.cone_ats = cone_ats;
		this.fec_ingreso = fec_ingreso;
		this.fec_inventario = fec_inventario;
		this.est_gestion = est_gestion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_gestion
	 */
	public long getId_gestion() {
		return id_gestion;
	}

	/**
	 * @param id_gestion the id_gestion to set
	 */
	public void setId_gestion(long id_gestion) {
		this.id_gestion = id_gestion;
	}

	/**
	 * @return the etiq_cds
	 */
	public boolean isEtiq_cds() {
		return etiq_cds;
	}

	/**
	 * @param etiq_cds the etiq_cds to set
	 */
	public void setEtiq_cds(boolean etiq_cds) {
		this.etiq_cds = etiq_cds;
	}

	/**
	 * @return the pein_cds
	 */
	public boolean isPein_cds() {
		return pein_cds;
	}

	/**
	 * @param pein_cds the pein_cds to set
	 */
	public void setPein_cds(boolean pein_cds) {
		this.pein_cds = pein_cds;
	}

	/**
	 * @return the cone_ats
	 */
	public boolean isCone_ats() {
		return cone_ats;
	}

	/**
	 * @param cone_ats the cone_ats to set
	 */
	public void setCone_ats(boolean cone_ats) {
		this.cone_ats = cone_ats;
	}

	/**
	 * @return the fec_ingreso
	 */
	public Timestamp getFec_ingreso() {
		return fec_ingreso;
	}

	/**
	 * @param fec_ingreso the fec_ingreso to set
	 */
	public void setFec_ingreso(Timestamp fec_ingreso) {
		this.fec_ingreso = fec_ingreso;
	}

	/**
	 * @return the fec_inventario
	 */
	public Timestamp getFec_inventario() {
		return fec_inventario;
	}

	/**
	 * @param fec_inventario the fec_inventario to set
	 */
	public void setFec_inventario(Timestamp fec_inventario) {
		this.fec_inventario = fec_inventario;
	}

	/**
	 * @return the est_gestion
	 */
	public String getEst_gestion() {
		return est_gestion;
	}

	/**
	 * @param est_gestion the est_gestion to set
	 */
	public void setEst_gestion(String est_gestion) {
		this.est_gestion = est_gestion;
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
	 * @return the estado_equipo
	 */
	public modelo_estado_equipo getEstado_equipo() {
		return estado_equipo;
	}

	/**
	 * @param estado_equipo the estado_equipo to set
	 */
	public void setEstado_equipo(modelo_estado_equipo estado_equipo) {
		this.estado_equipo = estado_equipo;
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
	 * @return the etiquetado_poder
	 */
	public modelo_etiquetado_poder getEtiquetado_poder() {
		return etiquetado_poder;
	}

	/**
	 * @param etiquetado_poder the etiquetado_poder to set
	 */
	public void setEtiquetado_poder(modelo_etiquetado_poder etiquetado_poder) {
		this.etiquetado_poder = etiquetado_poder;
	}

	/**
	 * @return the etiquetado_pdu
	 */
	public modelo_etiquetado_pdu getEtiquetado_pdu() {
		return etiquetado_pdu;
	}

	/**
	 * @param etiquetado_pdu the etiquetado_pdu to set
	 */
	public void setEtiquetado_pdu(modelo_etiquetado_pdu etiquetado_pdu) {
		this.etiquetado_pdu = etiquetado_pdu;
	}

	/**
	 * @return the observacion
	 */
	public modelo_observacion getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(modelo_observacion observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the localidad
	 */
	public modelo_localidad getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(modelo_localidad localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "modelo_gestion_equipo [id_gestion=" + id_gestion + ", etiq_cds=" + etiq_cds + ", pein_cds=" + pein_cds
				+ ", cone_ats=" + cone_ats + ", fec_ingreso=" + fec_ingreso + ", fec_inventario=" + fec_inventario
				+ ", est_gestion=" + est_gestion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

}
