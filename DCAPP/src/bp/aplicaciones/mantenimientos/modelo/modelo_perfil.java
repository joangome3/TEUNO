package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_perfil {

	private long id_perfil;
	private String nom_perfil;
	private String consultar;
	private String insertar;
	private String modificar;
	private String relacionar;
	private String desactivar;
	private String eliminar;
	private String solicitar;
	private String revisar;
	private String aprobar;
	private String ejecutar;
	private String est_perfil;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_perfil() {
		super();
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
		super();
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

	@Override
	public String toString() {
		return "modelo_perfil [id_perfil=" + id_perfil + ", nom_perfil=" + nom_perfil + ", consultar=" + consultar
				+ ", insertar=" + insertar + ", modificar=" + modificar + ", relacionar=" + relacionar + ", desactivar="
				+ desactivar + ", eliminar=" + eliminar + ", solicitar=" + solicitar + ", revisar=" + revisar
				+ ", aprobar=" + aprobar + ", ejecutar=" + ejecutar + ", est_perfil=" + est_perfil + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_perfil.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}

}
