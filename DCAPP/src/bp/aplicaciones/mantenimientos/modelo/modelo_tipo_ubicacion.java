package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_ubicacion {

	private long id_tipo_ubicacion;
	private String nom_tipo_ubicacion;
	private String est_tipo_ubicacion;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_tipo_ubicacion() {
		super();
	}

	public modelo_tipo_ubicacion(long id_tipo_ubicacion, String nom_tipo_ubicacion, String est_tipo_ubicacion,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tipo_ubicacion = id_tipo_ubicacion;
		this.nom_tipo_ubicacion = nom_tipo_ubicacion;
		this.est_tipo_ubicacion = est_tipo_ubicacion;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	public long getId_tipo_ubicacion() {
		return id_tipo_ubicacion;
	}

	public void setId_tipo_ubicacion(long id_tipo_ubicacion) {
		this.id_tipo_ubicacion = id_tipo_ubicacion;
	}

	public String getNom_tipo_ubicacion() {
		return nom_tipo_ubicacion;
	}

	public void setNom_tipo_ubicacion(String nom_tipo_ubicacion) {
		this.nom_tipo_ubicacion = nom_tipo_ubicacion;
	}

	public String getEst_tipo_ubicacion() {
		return est_tipo_ubicacion;
	}

	public void setEst_tipo_ubicacion(String est_tipo_ubicacion) {
		this.est_tipo_ubicacion = est_tipo_ubicacion;
	}

	public String getUsu_ingresa() {
		return usu_ingresa;
	}

	public void setUsu_ingresa(String usu_ingresa) {
		this.usu_ingresa = usu_ingresa;
	}

	public Timestamp getFec_ingresa() {
		return fec_ingresa;
	}

	public void setFec_ingresa(Timestamp fec_ingresa) {
		this.fec_ingresa = fec_ingresa;
	}

	public String getUsu_modifica() {
		return usu_modifica;
	}

	public void setUsu_modifica(String usu_modifica) {
		this.usu_modifica = usu_modifica;
	}

	public Timestamp getFec_modifica() {
		return fec_modifica;
	}

	public void setFec_modifica(Timestamp fec_modifica) {
		this.fec_modifica = fec_modifica;
	}

	@Override
	public String toString() {
		return "modelo_tipo_ubicacion [id_tipo_ubicacion=" + id_tipo_ubicacion + ", nom_tipo_ubicacion="
				+ nom_tipo_ubicacion + ", est_tipo_ubicacion=" + est_tipo_ubicacion + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

}
