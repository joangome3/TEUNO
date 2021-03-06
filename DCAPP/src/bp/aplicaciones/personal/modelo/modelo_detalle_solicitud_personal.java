package bp.aplicaciones.personal.modelo;

import java.sql.Timestamp;

public class modelo_detalle_solicitud_personal {

	private long id_detalle_solicitud;
	private long id_solicitud;
	private long id_proveedor;
	private String num_documento_proveedor;
	private String nom_proveedor;
	private long id_emp_proveedor;
	private String nom_emp_proveedor;
	private long id_dispositivo;
	private String nom_dispositivo;
	private String est_detalle_solicitud;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_detalle_solicitud_personal() {
		super();
	}

	/**
	 * @param id_detalle_solicitud
	 * @param id_solicitud
	 * @param id_proveedor
	 * @param id_dispositivo
	 * @param est_detalle_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_detalle_solicitud_personal(long id_detalle_solicitud, long id_solicitud, long id_proveedor,
			long id_dispositivo, String est_detalle_solicitud, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_detalle_solicitud = id_detalle_solicitud;
		this.id_solicitud = id_solicitud;
		this.id_proveedor = id_proveedor;
		this.id_dispositivo = id_dispositivo;
		this.est_detalle_solicitud = est_detalle_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_detalle_solicitud
	 * @param id_solicitud
	 * @param id_proveedor
	 * @param num_documento_proveedor
	 * @param nom_proveedor
	 * @param id_emp_proveedor
	 * @param nom_emp_proveedor
	 * @param id_dispositivo
	 * @param nom_dispositivo
	 * @param est_detalle_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_detalle_solicitud_personal(long id_detalle_solicitud, long id_solicitud, long id_proveedor,
			String num_documento_proveedor, String nom_proveedor, long id_emp_proveedor, String nom_emp_proveedor,
			long id_dispositivo, String nom_dispositivo, String est_detalle_solicitud, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_detalle_solicitud = id_detalle_solicitud;
		this.id_solicitud = id_solicitud;
		this.id_proveedor = id_proveedor;
		this.num_documento_proveedor = num_documento_proveedor;
		this.nom_proveedor = nom_proveedor;
		this.id_emp_proveedor = id_emp_proveedor;
		this.nom_emp_proveedor = nom_emp_proveedor;
		this.id_dispositivo = id_dispositivo;
		this.nom_dispositivo = nom_dispositivo;
		this.est_detalle_solicitud = est_detalle_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_detalle_solicitud
	 */
	public long getId_detalle_solicitud() {
		return id_detalle_solicitud;
	}

	/**
	 * @param id_detalle_solicitud the id_detalle_solicitud to set
	 */
	public void setId_detalle_solicitud(long id_detalle_solicitud) {
		this.id_detalle_solicitud = id_detalle_solicitud;
	}

	/**
	 * @return the id_solicitud
	 */
	public long getId_solicitud() {
		return id_solicitud;
	}

	/**
	 * @param id_solicitud the id_solicitud to set
	 */
	public void setId_solicitud(long id_solicitud) {
		this.id_solicitud = id_solicitud;
	}

	/**
	 * @return the id_proveedor
	 */
	public long getId_proveedor() {
		return id_proveedor;
	}

	/**
	 * @param id_proveedor the id_proveedor to set
	 */
	public void setId_proveedor(long id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	/**
	 * @return the num_documento_proveedor
	 */
	public String getNum_documento_proveedor() {
		return num_documento_proveedor;
	}

	/**
	 * @param num_documento_proveedor the num_documento_proveedor to set
	 */
	public void setNum_documento_proveedor(String num_documento_proveedor) {
		this.num_documento_proveedor = num_documento_proveedor;
	}

	/**
	 * @return the nom_proveedor
	 */
	public String getNom_proveedor() {
		return nom_proveedor;
	}

	/**
	 * @param nom_proveedor the nom_proveedor to set
	 */
	public void setNom_proveedor(String nom_proveedor) {
		this.nom_proveedor = nom_proveedor;
	}

	/**
	 * @return the id_emp_proveedor
	 */
	public long getId_emp_proveedor() {
		return id_emp_proveedor;
	}

	/**
	 * @param id_emp_proveedor the id_emp_proveedor to set
	 */
	public void setId_emp_proveedor(long id_emp_proveedor) {
		this.id_emp_proveedor = id_emp_proveedor;
	}

	/**
	 * @return the nom_emp_proveedor
	 */
	public String getNom_emp_proveedor() {
		return nom_emp_proveedor;
	}

	/**
	 * @param nom_emp_proveedor the nom_emp_proveedor to set
	 */
	public void setNom_emp_proveedor(String nom_emp_proveedor) {
		this.nom_emp_proveedor = nom_emp_proveedor;
	}

	/**
	 * @return the id_dispositivo
	 */
	public long getId_dispositivo() {
		return id_dispositivo;
	}

	/**
	 * @param id_dispositivo the id_dispositivo to set
	 */
	public void setId_dispositivo(long id_dispositivo) {
		this.id_dispositivo = id_dispositivo;
	}

	/**
	 * @return the nom_dispositivo
	 */
	public String getNom_dispositivo() {
		return nom_dispositivo;
	}

	/**
	 * @param nom_dispositivo the nom_dispositivo to set
	 */
	public void setNom_dispositivo(String nom_dispositivo) {
		this.nom_dispositivo = nom_dispositivo;
	}

	/**
	 * @return the est_detalle_solicitud
	 */
	public String getEst_detalle_solicitud() {
		return est_detalle_solicitud;
	}

	/**
	 * @param est_detalle_solicitud the est_detalle_solicitud to set
	 */
	public void setEst_detalle_solicitud(String est_detalle_solicitud) {
		this.est_detalle_solicitud = est_detalle_solicitud;
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
		return "modelo_detalle_solicitud_personal [id_detalle_solicitud=" + id_detalle_solicitud + ", id_solicitud="
				+ id_solicitud + ", id_proveedor=" + id_proveedor + ", num_documento_proveedor="
				+ num_documento_proveedor + ", nom_proveedor=" + nom_proveedor + ", id_emp_proveedor="
				+ id_emp_proveedor + ", nom_emp_proveedor=" + nom_emp_proveedor + ", id_dispositivo=" + id_dispositivo
				+ ", nom_dispositivo=" + nom_dispositivo + ", est_detalle_solicitud=" + est_detalle_solicitud
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

}
