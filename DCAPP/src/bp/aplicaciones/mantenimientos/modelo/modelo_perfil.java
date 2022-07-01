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
@Table(name = "nocap_perfiles")
public class modelo_perfil {

	@Id
	@Column(name = "id_perfil", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_perfil;
	@Column(name = "nom_perfil", length = 50)
	private String nom_perfil;
	@Column(name = "consultar", length = 5)
	private String consultar;
	@Column(name = "insertar", length = 5)
	private String insertar;
	@Column(name = "modificar", length = 5)
	private String modificar;
	@Column(name = "relacionar", length = 5)
	private String relacionar;
	@Column(name = "desactivar", length = 5)
	private String desactivar;
	@Column(name = "eliminar", length = 5)
	private String eliminar;
	@Column(name = "solicitar", length = 5)
	private String solicitar;
	@Column(name = "revisar", length = 5)
	private String revisar;
	@Column(name = "aprobar", length = 5)
	private String aprobar;
	@Column(name = "ejecutar", length = 5)
	private String ejecutar;
	@Column(name = "est_perfil", length = 5)
	private String est_perfil;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@OneToMany(mappedBy = "perfil", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_usuario> usuarios;

	@OneToMany(mappedBy = "perfil", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_perfil_opcion> relaciones_perfil_opcion;

	@OneToMany(mappedBy = "perfil", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_perfil_mantenimiento> relaciones_perfil_mantenimiento;

	public modelo_perfil() {
	}

	/**
	 * @param nom_perfil
	 * @param consultar
	 * @param insertar
	 * @param modificar
	 * @param relacionar
	 * @param desactivar
	 * @param eliminar
	 * @param solicitar
	 * @param revisar
	 * @param aprobar
	 * @param ejecutar
	 * @param est_perfil
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_perfil(String nom_perfil, String consultar, String insertar, String modificar, String relacionar,
			String desactivar, String eliminar, String solicitar, String revisar, String aprobar, String ejecutar,
			String est_perfil, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.nom_perfil = nom_perfil;
		this.consultar = consultar;
		this.insertar = insertar;
		this.modificar = modificar;
		this.relacionar = relacionar;
		this.desactivar = desactivar;
		this.eliminar = eliminar;
		this.solicitar = solicitar;
		this.revisar = revisar;
		this.aprobar = aprobar;
		this.ejecutar = ejecutar;
		this.est_perfil = est_perfil;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_perfil
	 * @param nom_perfil
	 * @param consultar
	 * @param insertar
	 * @param modificar
	 * @param relacionar
	 * @param desactivar
	 * @param eliminar
	 * @param solicitar
	 * @param revisar
	 * @param aprobar
	 * @param ejecutar
	 * @param est_perfil
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_perfil(long id_perfil, String nom_perfil, String consultar, String insertar, String modificar,
			String relacionar, String desactivar, String eliminar, String solicitar, String revisar, String aprobar,
			String ejecutar, String est_perfil, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		this.id_perfil = id_perfil;
		this.nom_perfil = nom_perfil;
		this.consultar = consultar;
		this.insertar = insertar;
		this.modificar = modificar;
		this.relacionar = relacionar;
		this.desactivar = desactivar;
		this.eliminar = eliminar;
		this.solicitar = solicitar;
		this.revisar = revisar;
		this.aprobar = aprobar;
		this.ejecutar = ejecutar;
		this.est_perfil = est_perfil;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_perfil
	 */
	public long getId_perfil() {
		return id_perfil;
	}

	/**
	 * @param id_perfil the id_perfil to set
	 */
	public void setId_perfil(long id_perfil) {
		this.id_perfil = id_perfil;
	}

	/**
	 * @return the nom_perfil
	 */
	public String getNom_perfil() {
		return nom_perfil;
	}

	/**
	 * @param nom_perfil the nom_perfil to set
	 */
	public void setNom_perfil(String nom_perfil) {
		this.nom_perfil = nom_perfil;
	}

	/**
	 * @return the consultar
	 */
	public String getConsultar() {
		return consultar;
	}

	/**
	 * @param consultar the consultar to set
	 */
	public void setConsultar(String consultar) {
		this.consultar = consultar;
	}

	/**
	 * @return the insertar
	 */
	public String getInsertar() {
		return insertar;
	}

	/**
	 * @param insertar the insertar to set
	 */
	public void setInsertar(String insertar) {
		this.insertar = insertar;
	}

	/**
	 * @return the modificar
	 */
	public String getModificar() {
		return modificar;
	}

	/**
	 * @param modificar the modificar to set
	 */
	public void setModificar(String modificar) {
		this.modificar = modificar;
	}

	/**
	 * @return the relacionar
	 */
	public String getRelacionar() {
		return relacionar;
	}

	/**
	 * @param relacionar the relacionar to set
	 */
	public void setRelacionar(String relacionar) {
		this.relacionar = relacionar;
	}

	/**
	 * @return the desactivar
	 */
	public String getDesactivar() {
		return desactivar;
	}

	/**
	 * @param desactivar the desactivar to set
	 */
	public void setDesactivar(String desactivar) {
		this.desactivar = desactivar;
	}

	/**
	 * @return the eliminar
	 */
	public String getEliminar() {
		return eliminar;
	}

	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	/**
	 * @return the solicitar
	 */
	public String getSolicitar() {
		return solicitar;
	}

	/**
	 * @param solicitar the solicitar to set
	 */
	public void setSolicitar(String solicitar) {
		this.solicitar = solicitar;
	}

	/**
	 * @return the revisar
	 */
	public String getRevisar() {
		return revisar;
	}

	/**
	 * @param revisar the revisar to set
	 */
	public void setRevisar(String revisar) {
		this.revisar = revisar;
	}

	/**
	 * @return the aprobar
	 */
	public String getAprobar() {
		return aprobar;
	}

	/**
	 * @param aprobar the aprobar to set
	 */
	public void setAprobar(String aprobar) {
		this.aprobar = aprobar;
	}

	/**
	 * @return the ejecutar
	 */
	public String getEjecutar() {
		return ejecutar;
	}

	/**
	 * @param ejecutar the ejecutar to set
	 */
	public void setEjecutar(String ejecutar) {
		this.ejecutar = ejecutar;
	}

	/**
	 * @return the est_perfil
	 */
	public String getEst_perfil() {
		return est_perfil;
	}

	/**
	 * @param est_perfil the est_perfil to set
	 */
	public void setEst_perfil(String est_perfil) {
		this.est_perfil = est_perfil;
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

	@Override
	public String toString() {
		return "modelo_perfil [id_perfil=" + id_perfil + ", nom_perfil=" + nom_perfil + ", consultar=" + consultar
				+ ", insertar=" + insertar + ", modificar=" + modificar + ", relacionar=" + relacionar + ", desactivar="
				+ desactivar + ", eliminar=" + eliminar + ", solicitar=" + solicitar + ", revisar=" + revisar
				+ ", aprobar=" + aprobar + ", ejecutar=" + ejecutar + ", est_perfil=" + est_perfil + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_perfil.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_perfil.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_perfil.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_perfil.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_perfil.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_perfil.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
