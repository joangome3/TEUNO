package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import bp.aplicaciones.equipos.modelo.modelo_gestion_equipo;

@Entity
@Table(name = "sibod_localidad")
public class modelo_localidad {

	@Id
	@Column(name = "id_localidad", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_localidad;
	@Column(name = "nom_localidad", length = 100)
	private String nom_localidad;
	@Column(name = "des_localidad", length = 500)
	private String des_localidad;
	@Column(name = "est_localidad", length = 5)
	private String est_localidad;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_usuario> usuarios;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_equipo> equipos;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_manual> manuales;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_fila> filas;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_rack> racks;
	
	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_parametros_generales_10> parametros_10;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_gestion_equipo> gestion_equipos;

	@OneToMany(mappedBy = "localidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_empresa_localidad> relaciones_empresa_localidad;

	/**
	 * 
	 */
	public modelo_localidad() {
	}

	/**
	 * @param nom_localidad
	 * @param des_localidad
	 * @param est_localidad
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_localidad(String nom_localidad, String des_localidad, String est_localidad, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.nom_localidad = nom_localidad;
		this.des_localidad = des_localidad;
		this.est_localidad = est_localidad;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_localidad
	 * @param nom_localidad
	 * @param des_localidad
	 * @param est_localidad
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_localidad(long id_localidad, String nom_localidad, String des_localidad, String est_localidad,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.des_localidad = des_localidad;
		this.est_localidad = est_localidad;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_localidad
	 */
	public long getId_localidad() {
		return id_localidad;
	}

	/**
	 * @param id_localidad the id_localidad to set
	 */
	public void setId_localidad(long id_localidad) {
		this.id_localidad = id_localidad;
	}

	/**
	 * @return the nom_localidad
	 */
	public String getNom_localidad() {
		return nom_localidad;
	}

	/**
	 * @param nom_localidad the nom_localidad to set
	 */
	public void setNom_localidad(String nom_localidad) {
		this.nom_localidad = nom_localidad;
	}

	/**
	 * @return the des_localidad
	 */
	public String getDes_localidad() {
		return des_localidad;
	}

	/**
	 * @param des_localidad the des_localidad to set
	 */
	public void setDes_localidad(String des_localidad) {
		this.des_localidad = des_localidad;
	}

	/**
	 * @return the est_localidad
	 */
	public String getEst_localidad() {
		return est_localidad;
	}

	/**
	 * @param est_localidad the est_localidad to set
	 */
	public void setEst_localidad(String est_localidad) {
		this.est_localidad = est_localidad;
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
	 * @return the usuarios
	 */
	public List<modelo_usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<modelo_usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the equipos
	 */
	public List<modelo_equipo> getEquipos() {
		return equipos;
	}

	/**
	 * @param equipo the equipos to set
	 */
	public void setEquipos(List<modelo_equipo> equipos) {
		this.equipos = equipos;
	}

	/**
	 * @return the manuales
	 */
	public List<modelo_manual> getManuales() {
		return manuales;
	}

	/**
	 * @param manuales the manuales to set
	 */
	public void setManuales(List<modelo_manual> manuales) {
		this.manuales = manuales;
	}

	/**
	 * @return the filas
	 */
	public List<modelo_fila> getFilas() {
		return filas;
	}

	/**
	 * @param filas the filas to set
	 */
	public void setFilas(List<modelo_fila> filas) {
		this.filas = filas;
	}

	/**
	 * @return the racks
	 */
	public List<modelo_rack> getRacks() {
		return racks;
	}

	/**
	 * @param racks the racks to set
	 */
	public void setRacks(List<modelo_rack> racks) {
		this.racks = racks;
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
	 * @return the relaciones_empresa_localidad
	 */
	public List<modelo_relacion_empresa_localidad> getRelaciones_empresa_localidad() {
		return relaciones_empresa_localidad;
	}

	/**
	 * @param relaciones_empresa_localidad the relaciones_empresa_localidad to set
	 */
	public void setRelaciones_empresa_localidad(List<modelo_relacion_empresa_localidad> relaciones_empresa_localidad) {
		this.relaciones_empresa_localidad = relaciones_empresa_localidad;
	}

	@Override
	public String toString() {
		return "sibod_localidad [id_localidad=" + id_localidad + ", nom_localidad=" + nom_localidad + ", des_localidad="
				+ des_localidad + ", est_localidad=" + est_localidad + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_localidad.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_localidad.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_localidad.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_localidad.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_localidad.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_localidad.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
