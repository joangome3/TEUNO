package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Blob;
import java.sql.Timestamp;

public class modelo_manual {

	private long id_manual;
	private String nom_manual;
	private String des_manual;
	private long id_localidad;
	private String nom_localidad;
	private String ext_manual;
	private Blob dir_manual;
	private String est_manual;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_manual() {
		super();
	}

	/**
	 * @param id_manual
	 * @param nom_manual
	 * @param des_manual
	 * @param id_localidad
	 * @param ext_manual
	 * @param dir_manual
	 * @param est_manual
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_manual(long id_manual, String nom_manual, String des_manual, long id_localidad, String ext_manual,
			Blob dir_manual, String est_manual, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_manual = id_manual;
		this.nom_manual = nom_manual;
		this.des_manual = des_manual;
		this.id_localidad = id_localidad;
		this.ext_manual = ext_manual;
		this.dir_manual = dir_manual;
		this.est_manual = est_manual;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_manual
	 * @param nom_manual
	 * @param des_manual
	 * @param id_localidad
	 * @param nom_localidad
	 * @param ext_manual
	 * @param dir_manual
	 * @param est_manual
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_manual(long id_manual, String nom_manual, String des_manual, long id_localidad, String nom_localidad,
			String ext_manual, Blob dir_manual, String est_manual, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_manual = id_manual;
		this.nom_manual = nom_manual;
		this.des_manual = des_manual;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.ext_manual = ext_manual;
		this.dir_manual = dir_manual;
		this.est_manual = est_manual;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/* Se sobreescribe el metodo toString */

	/**
	 * @return the id_manual
	 */
	public long getId_manual() {
		return id_manual;
	}

	/**
	 * @param id_manual the id_manual to set
	 */
	public void setId_manual(long id_manual) {
		this.id_manual = id_manual;
	}

	/**
	 * @return the nom_manual
	 */
	public String getNom_manual() {
		return nom_manual;
	}

	/**
	 * @param nom_manual the nom_manual to set
	 */
	public void setNom_manual(String nom_manual) {
		this.nom_manual = nom_manual;
	}

	/**
	 * @return the des_manual
	 */
	public String getDes_manual() {
		return des_manual;
	}

	/**
	 * @param des_manual the des_manual to set
	 */
	public void setDes_manual(String des_manual) {
		this.des_manual = des_manual;
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
	 * @return the ext_manual
	 */
	public String getExt_manual() {
		return ext_manual;
	}

	/**
	 * @param ext_manual the ext_manual to set
	 */
	public void setExt_manual(String ext_manual) {
		this.ext_manual = ext_manual;
	}

	/**
	 * @return the dir_manual
	 */
	public Blob getDir_manual() {
		return dir_manual;
	}

	/**
	 * @param dir_manual the dir_manual to set
	 */
	public void setDir_manual(Blob dir_manual) {
		this.dir_manual = dir_manual;
	}

	/**
	 * @return the est_manual
	 */
	public String getEst_manual() {
		return est_manual;
	}

	/**
	 * @param est_manual the est_manual to set
	 */
	public void setEst_manual(String est_manual) {
		this.est_manual = est_manual;
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
		return "modelo_manual [id_manual=" + id_manual + ", nom_manual=" + nom_manual + ", des_manual=" + des_manual
				+ ", id_localidad=" + id_localidad + ", nom_localidad=" + nom_localidad + ", ext_manual=" + ext_manual
				+ ", dir_manual=" + dir_manual + ", est_manual=" + est_manual + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_manual.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_manual.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_manual.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_manual.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_manual.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_manual.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

}
