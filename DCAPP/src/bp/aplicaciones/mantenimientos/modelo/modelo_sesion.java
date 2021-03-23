package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class modelo_sesion {

	private long id_sesion;
	private String cod_sesion;
	private long id_usuario_1;
	private String nom_usuario;
	private long id_localidad;
	private String est_sesion;
	private Timestamp fec_sesion_1;
	private long id_usuario_2;
	private Timestamp fec_sesion_2;

	/**
	 * 
	 */
	public modelo_sesion() {
		super();
	}

	/**
	 * @param id_sesion
	 * @param cod_sesion
	 * @param id_usuario_1
	 * @param nom_usuario
	 * @param id_localidad
	 * @param est_sesion
	 * @param fec_sesion_1
	 * @param id_usuario_2
	 * @param fec_sesion_2
	 */
	public modelo_sesion(long id_sesion, String cod_sesion, long id_usuario_1, String nom_usuario, long id_localidad,
			String est_sesion, Timestamp fec_sesion_1, long id_usuario_2, Timestamp fec_sesion_2) {
		super();
		this.id_sesion = id_sesion;
		this.cod_sesion = cod_sesion;
		this.id_usuario_1 = id_usuario_1;
		this.nom_usuario = nom_usuario;
		this.id_localidad = id_localidad;
		this.est_sesion = est_sesion;
		this.fec_sesion_1 = fec_sesion_1;
		this.id_usuario_2 = id_usuario_2;
		this.fec_sesion_2 = fec_sesion_2;
	}

	/**
	 * @return the id_sesion
	 */
	public long getId_sesion() {
		return id_sesion;
	}

	/**
	 * @param id_sesion the id_sesion to set
	 */
	public void setId_sesion(long id_sesion) {
		this.id_sesion = id_sesion;
	}

	/**
	 * @return the cod_sesion
	 */
	public String getCod_sesion() {
		return cod_sesion;
	}

	/**
	 * @param cod_sesion the cod_sesion to set
	 */
	public void setCod_sesion(String cod_sesion) {
		this.cod_sesion = cod_sesion;
	}

	/**
	 * @return the id_usuario_1
	 */
	public long getId_usuario_1() {
		return id_usuario_1;
	}

	/**
	 * @param id_usuario_1 the id_usuario_1 to set
	 */
	public void setId_usuario_1(long id_usuario_1) {
		this.id_usuario_1 = id_usuario_1;
	}

	/**
	 * @return the nom_usuario
	 */
	public String getNom_usuario() {
		return nom_usuario;
	}

	/**
	 * @param nom_usuario the nom_usuario to set
	 */
	public void setNom_usuario(String nom_usuario) {
		this.nom_usuario = nom_usuario;
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
	 * @return the est_sesion
	 */
	public String getEst_sesion() {
		return est_sesion;
	}

	/**
	 * @param est_sesion the est_sesion to set
	 */
	public void setEst_sesion(String est_sesion) {
		this.est_sesion = est_sesion;
	}

	/**
	 * @return the fec_sesion_1
	 */
	public Timestamp getFec_sesion_1() {
		return fec_sesion_1;
	}

	/**
	 * @param fec_sesion_1 the fec_sesion_1 to set
	 */
	public void setFec_sesion_1(Timestamp fec_sesion_1) {
		this.fec_sesion_1 = fec_sesion_1;
	}

	/**
	 * @return the id_usuario_2
	 */
	public long getId_usuario_2() {
		return id_usuario_2;
	}

	/**
	 * @param id_usuario_2 the id_usuario_2 to set
	 */
	public void setId_usuario_2(long id_usuario_2) {
		this.id_usuario_2 = id_usuario_2;
	}

	/**
	 * @return the fec_sesion_2
	 */
	public Timestamp getFec_sesion_2() {
		return fec_sesion_2;
	}

	/**
	 * @param fec_sesion_2 the fec_sesion_2 to set
	 */
	public void setFec_sesion_2(Timestamp fec_sesion_2) {
		this.fec_sesion_2 = fec_sesion_2;
	}

	@Override
	public String toString() {
		return "modelo_sesion [id_sesion=" + id_sesion + ", cod_sesion=" + cod_sesion + ", id_usuario_1=" + id_usuario_1
				+ ", nom_usuario=" + nom_usuario + ", id_localidad=" + id_localidad + ", est_sesion=" + est_sesion
				+ ", fec_sesion_1=" + fec_sesion_1 + ", id_usuario_2=" + id_usuario_2 + ", fec_sesion_2=" + fec_sesion_2
				+ "]";
	}
	
	public String toStringFechaSesion() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_sesion_1());
		return s;
	}

}
