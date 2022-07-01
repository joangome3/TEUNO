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

@Entity
@Table(name = "nocap_opcion")
public class modelo_opcion {

	@Id
	@Column(name = "id_opcion", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_opcion;
	@Column(name = "nom_opcion", length = 100)
	private String nom_opcion;
	@Column(name = "est_opcion", length = 5)
	private String est_opcion;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "opcion", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_perfil_opcion> relaciones_perfil_opcion;

	@OneToMany(mappedBy = "opcion", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_empresa_opcion> relaciones_empresa_opcion;

	@OneToMany(mappedBy = "opcion", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_opcion_mantenimiento> relaciones_opcion_mantenimiento;

	/**
	 * 
	 */
	public modelo_opcion() {
		super();
	}

	/**
	 * @param nom_opcion
	 * @param est_opcion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_opcion(String nom_opcion, String est_opcion, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.nom_opcion = nom_opcion;
		this.est_opcion = est_opcion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_opcion
	 * @param nom_opcion
	 * @param est_opcion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_opcion(long id_opcion, String nom_opcion, String est_opcion, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_opcion = id_opcion;
		this.nom_opcion = nom_opcion;
		this.est_opcion = est_opcion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_opcion
	 */
	public long getId_opcion() {
		return id_opcion;
	}

	/**
	 * @param id_opcion the id_opcion to set
	 */
	public void setId_opcion(long id_opcion) {
		this.id_opcion = id_opcion;
	}

	/**
	 * @return the nom_opcion
	 */
	public String getNom_opcion() {
		return nom_opcion;
	}

	/**
	 * @param nom_opcion the nom_opcion to set
	 */
	public void setNom_opcion(String nom_opcion) {
		this.nom_opcion = nom_opcion;
	}

	/**
	 * @return the est_opcion
	 */
	public String getEst_opcion() {
		return est_opcion;
	}

	/**
	 * @param est_opcion the est_opcion to set
	 */
	public void setEst_opcion(String est_opcion) {
		this.est_opcion = est_opcion;
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
	 * @return the relaciones_perfil_opcion
	 */
	public List<modelo_relacion_perfil_opcion> getRelaciones_perfil_opcion() {
		return relaciones_perfil_opcion;
	}

	/**
	 * @param relaciones_perfil_opcion the relaciones_perfil_opcion to set
	 */
	public void setRelaciones_perfil_opcion(List<modelo_relacion_perfil_opcion> relaciones_perfil_opcion) {
		this.relaciones_perfil_opcion = relaciones_perfil_opcion;
	}

	/**
	 * @return the relaciones_empresa_opcion
	 */
	public List<modelo_relacion_empresa_opcion> getRelaciones_empresa_opcion() {
		return relaciones_empresa_opcion;
	}

	/**
	 * @param relaciones_empresa_opcion the relaciones_empresa_opcion to set
	 */
	public void setRelaciones_empresa_opcion(List<modelo_relacion_empresa_opcion> relaciones_empresa_opcion) {
		this.relaciones_empresa_opcion = relaciones_empresa_opcion;
	}

	/**
	 * @return the relaciones_opcion_mantenimiento
	 */
	public List<modelo_relacion_opcion_mantenimiento> getRelaciones_opcion_mantenimiento() {
		return relaciones_opcion_mantenimiento;
	}

	/**
	 * @param relaciones_opcion_mantenimiento the relaciones_opcion_mantenimiento to
	 *                                        set
	 */
	public void setRelaciones_opcion_mantenimiento(
			List<modelo_relacion_opcion_mantenimiento> relaciones_opcion_mantenimiento) {
		this.relaciones_opcion_mantenimiento = relaciones_opcion_mantenimiento;
	}

	@Override
	public String toString() {
		return "modelo_opcion [id_opcion=" + id_opcion + ", nom_opcion=" + nom_opcion + ", est_opcion=" + est_opcion
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_opcion.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_opcion.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_opcion.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_opcion.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_opcion.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_opcion.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
