package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_relacion_informativo_usuario {

	private long id_relacion;
	private long id_informativo;
	private long id_usuario;
	private String esta_leido;
	private String est_relacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_relacion_informativo_usuario() {
		super();
	}

	/**
	 * @param id_relacion
	 * @param id_informativo
	 * @param id_usuario
	 * @param esta_leido
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_relacion_informativo_usuario(long id_relacion, long id_informativo, long id_usuario,
			String esta_leido, String est_relacion, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_relacion = id_relacion;
		this.id_informativo = id_informativo;
		this.id_usuario = id_usuario;
		this.esta_leido = esta_leido;
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
	 * @return the id_informativo
	 */
	public long getId_informativo() {
		return id_informativo;
	}

	/**
	 * @param id_informativo the id_informativo to set
	 */
	public void setId_informativo(long id_informativo) {
		this.id_informativo = id_informativo;
	}

	/**
	 * @return the id_usuario
	 */
	public long getId_usuario() {
		return id_usuario;
	}

	/**
	 * @param id_usuario the id_usuario to set
	 */
	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * @return the esta_leido
	 */
	public String getEsta_leido() {
		return esta_leido;
	}

	/**
	 * @param esta_leido the esta_leido to set
	 */
	public void setEsta_leido(String esta_leido) {
		this.esta_leido = esta_leido;
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

	@Override
	public String toString() {
		return "modelo_relacion_informativo_usuario [id_relacion=" + id_relacion + ", id_informativo=" + id_informativo
				+ ", id_usuario=" + id_usuario + ", esta_leido=" + esta_leido + ", est_relacion=" + est_relacion
				+ ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
				+ ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_relacion.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}

}
