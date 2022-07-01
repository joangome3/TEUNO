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
@Table(name = "nocap_mantenimiento")
public class modelo_mantenimiento {

	@Id
	@Column(name = "id_mantenimiento", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_mantenimiento;
	@Column(name = "nom_mantenimiento", length = 100)
	private String nom_mantenimiento;
	@Column(name = "est_mantenimiento", length = 5)
	private String est_mantenimiento;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "mantenimiento", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_perfil_mantenimiento> relaciones_perfil_mantenimiento;

	@OneToMany(mappedBy = "mantenimiento", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_empresa_mantenimiento> relaciones_empresa_mantenimiento;

	@OneToMany(mappedBy = "mantenimiento", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_opcion_mantenimiento> relaciones_opcion_mantenimiento;

	@OneToMany(mappedBy = "mantenimiento1", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_mantenimiento_mantenimiento> relaciones_mantenimiento_mantenimiento_1;

	@OneToMany(mappedBy = "mantenimiento2", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_mantenimiento_mantenimiento> relaciones_mantenimiento_mantenimiento_2;

	/**
	 * 
	 */
	public modelo_mantenimiento() {
		super();
	}

	/**
	 * @param id_mantenimiento
	 * @param nom_mantenimiento
	 * @param est_mantenimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_mantenimiento(long id_mantenimiento, String nom_mantenimiento, String est_mantenimiento,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_mantenimiento = id_mantenimiento;
		this.nom_mantenimiento = nom_mantenimiento;
		this.est_mantenimiento = est_mantenimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_mantenimiento
	 */
	public long getId_mantenimiento() {
		return id_mantenimiento;
	}

	/**
	 * @param id_mantenimiento the id_mantenimiento to set
	 */
	public void setId_mantenimiento(long id_mantenimiento) {
		this.id_mantenimiento = id_mantenimiento;
	}

	/**
	 * @return the nom_mantenimiento
	 */
	public String getNom_mantenimiento() {
		return nom_mantenimiento;
	}

	/**
	 * @param nom_mantenimiento the nom_mantenimiento to set
	 */
	public void setNom_mantenimiento(String nom_mantenimiento) {
		this.nom_mantenimiento = nom_mantenimiento;
	}

	/**
	 * @return the est_mantenimiento
	 */
	public String getEst_mantenimiento() {
		return est_mantenimiento;
	}

	/**
	 * @param est_mantenimiento the est_mantenimiento to set
	 */
	public void setEst_mantenimiento(String est_mantenimiento) {
		this.est_mantenimiento = est_mantenimiento;
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
	 * @return the relaciones_perfil_mantenimiento
	 */
	public List<modelo_relacion_perfil_mantenimiento> getRelaciones_perfil_mantenimiento() {
		return relaciones_perfil_mantenimiento;
	}

	/**
	 * @param relaciones_perfil_mantenimiento the relaciones_perfil_mantenimiento to
	 *                                        set
	 */
	public void setRelaciones_perfil_mantenimiento(
			List<modelo_relacion_perfil_mantenimiento> relaciones_perfil_mantenimiento) {
		this.relaciones_perfil_mantenimiento = relaciones_perfil_mantenimiento;
	}

	/**
	 * @return the relaciones_empresa_mantenimiento
	 */
	public List<modelo_relacion_empresa_mantenimiento> getRelaciones_empresa_mantenimiento() {
		return relaciones_empresa_mantenimiento;
	}

	/**
	 * @param relaciones_empresa_mantenimiento the relaciones_empresa_mantenimiento
	 *                                         to set
	 */
	public void setRelaciones_empresa_mantenimiento(
			List<modelo_relacion_empresa_mantenimiento> relaciones_empresa_mantenimiento) {
		this.relaciones_empresa_mantenimiento = relaciones_empresa_mantenimiento;
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

	/**
	 * @return the relaciones_mantenimiento_mantenimiento_1
	 */
	public List<modelo_relacion_mantenimiento_mantenimiento> getRelaciones_mantenimiento_mantenimiento_1() {
		return relaciones_mantenimiento_mantenimiento_1;
	}

	/**
	 * @param relaciones_mantenimiento_mantenimiento_1 the
	 *                                                 relaciones_mantenimiento_mantenimiento_1
	 *                                                 to set
	 */
	public void setRelaciones_mantenimiento_mantenimiento_1(
			List<modelo_relacion_mantenimiento_mantenimiento> relaciones_mantenimiento_mantenimiento_1) {
		this.relaciones_mantenimiento_mantenimiento_1 = relaciones_mantenimiento_mantenimiento_1;
	}

	/**
	 * @return the relaciones_mantenimiento_mantenimiento_2
	 */
	public List<modelo_relacion_mantenimiento_mantenimiento> getRelaciones_mantenimiento_mantenimiento_2() {
		return relaciones_mantenimiento_mantenimiento_2;
	}

	/**
	 * @param relaciones_mantenimiento_mantenimiento_2 the
	 *                                                 relaciones_mantenimiento_mantenimiento_2
	 *                                                 to set
	 */
	public void setRelaciones_mantenimiento_mantenimiento_2(
			List<modelo_relacion_mantenimiento_mantenimiento> relaciones_mantenimiento_mantenimiento_2) {
		this.relaciones_mantenimiento_mantenimiento_2 = relaciones_mantenimiento_mantenimiento_2;
	}

	@Override
	public String toString() {
		return "modelo_mantenimiento [id_mantenimiento=" + id_mantenimiento + ", nom_mantenimiento=" + nom_mantenimiento
				+ ", est_mantenimiento=" + est_mantenimiento + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	/* Se sobreescribe el metodo toString */

	public String mostrarEstado() {
		String estado = "";
		if (est_mantenimiento.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_mantenimiento.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_mantenimiento.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_mantenimiento.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_mantenimiento.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_mantenimiento.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
