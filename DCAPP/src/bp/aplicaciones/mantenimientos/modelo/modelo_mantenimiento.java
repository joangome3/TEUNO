package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_mantenimiento {

  private long id_mantenimiento;
  private String nom_mantenimiento;
  private String est_mantenimiento;
  private String usu_ingresa;
  private Timestamp fec_ingresa;
  private String usu_modifica;
  private Timestamp fec_modifica;

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
  public modelo_mantenimiento(long id_mantenimiento, String nom_mantenimiento, String est_mantenimiento, String usu_ingresa, Timestamp fec_ingresa,
		String usu_modifica, Timestamp fec_modifica) {
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

  @Override
  public String toString() {
	 return "modelo_mantenimiento [id_mantenimiento=" + id_mantenimiento + ", nom_mantenimiento=" + nom_mantenimiento + ", est_mantenimiento=" + est_mantenimiento
		  + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
		  + ", fec_modifica=" + fec_modifica + "]";
  }

  public String toStringEstado() {
	 if (est_mantenimiento.equals("A")) {
		return "ACTIVO";
	 } else {
		return "INACTIVO";
	 }
  }

}
