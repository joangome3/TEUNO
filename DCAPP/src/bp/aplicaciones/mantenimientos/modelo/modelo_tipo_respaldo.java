package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_tipo_respaldo {

	private long id_tipo_respaldo;
	private String nom_tipo_respaldo;
	private String est_tipo_respaldo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_tipo_respaldo() {
		super();
	}

	public modelo_tipo_respaldo(long id_tipo_respaldo, String nom_tipo_respaldo, String est_tipo_respaldo,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_tipo_respaldo = id_tipo_respaldo;
		this.nom_tipo_respaldo = nom_tipo_respaldo;
		this.est_tipo_respaldo = est_tipo_respaldo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	public long getId_tipo_respaldo() {
		return id_tipo_respaldo;
	}

	public void setId_tipo_respaldo(long id_tipo_respaldo) {
		this.id_tipo_respaldo = id_tipo_respaldo;
	}

	public String getNom_tipo_respaldo() {
		return nom_tipo_respaldo;
	}

	public void setNom_tipo_respaldo(String nom_tipo_respaldo) {
		this.nom_tipo_respaldo = nom_tipo_respaldo;
	}

	public String getEst_tipo_respaldo() {
		return est_tipo_respaldo;
	}

	public void setEst_tipo_respaldo(String est_tipo_respaldo) {
		this.est_tipo_respaldo = est_tipo_respaldo;
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
		return "modelo_tipo_respaldo [id_tipo_respaldo=" + id_tipo_respaldo + ", nom_tipo_respaldo="
				+ nom_tipo_respaldo + ", est_tipo_respaldo=" + est_tipo_respaldo + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

}
