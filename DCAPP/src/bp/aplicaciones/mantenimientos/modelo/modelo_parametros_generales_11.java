package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_parametros_generales_11 {

	private long id_relacion;
	private long id_opcion;
	private long id_tipo_servicio;
	private long id_localidad;
	private String est_relacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_parametros_generales_11() {
		super();
	}

	/**
	 * @param id_relacion
	 * @param id_opcion
	 * @param id_tipo_servicio
	 * @param id_localidad
	 * @param est_relacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_parametros_generales_11(long id_relacion, long id_opcion, long id_tipo_servicio, long id_localidad,
			String est_relacion, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_relacion = id_relacion;
		this.id_opcion = id_opcion;
		this.id_tipo_servicio = id_tipo_servicio;
		this.id_localidad = id_localidad;
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
	 * @return the id_tipo_servicio
	 */
	public long getId_tipo_servicio() {
		return id_tipo_servicio;
	}

	/**
	 * @param id_tipo_servicio the id_tipo_servicio to set
	 */
	public void setId_tipo_servicio(long id_tipo_servicio) {
		this.id_tipo_servicio = id_tipo_servicio;
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
		return "modelo_parametros_generales_10 [id_relacion=" + id_relacion + ", id_opcion=" + id_opcion
				+ ", id_tipo_servicio=" + id_tipo_servicio + ", id_localidad=" + id_localidad + ", est_relacion="
				+ est_relacion + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_relacion.equals("A")) {
			return "ACTIVO";
		} else {
			return "INACTIVO";
		}
	}
}
