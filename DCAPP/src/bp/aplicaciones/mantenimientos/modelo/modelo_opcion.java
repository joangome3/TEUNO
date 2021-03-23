package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_opcion {

  private long id_opcion;
  private String nom_opcion;
  private String est_opcion;
  private String usu_ingresa;
  private Timestamp fec_ingresa;
  private String usu_modifica;
  private Timestamp fec_modifica;

  /**
   * 
   */
  public modelo_opcion() {
	 super();
  }

  /**
   * @param id_opcion
   * @param nom_opcion
   * @param est_opcion
   * @param usu_ingresa
   * @param fec_ingresa
   * @param usu_modifica
   * @param fec_modifica
   */
  public modelo_opcion(long id_opcion, String nom_opcion, String est_opcion, String usu_ingresa, Timestamp fec_ingresa,
		String usu_modifica, Timestamp fec_modifica) {
	 super();
	 this.id_opcion = id_opcion;
	 this.nom_opcion = nom_opcion;
	 this.est_opcion = est_opcion;
	 this.usu_ingresa = usu_ingresa;
	 this.fec_ingresa = fec_ingresa;
	 this.usu_modifica = usu_modifica;
	 this.fec_modifica = fec_modifica;
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
   * @return the nom_opcion
   */
  public String getNom_opcion() {
	 return nom_opcion;
  }

  /**
   * @param nom_opcion the nom_opcion to set
   */
  public void setNom_opcion(String nom_opcion) {
	 this.nom_opcion = nom_opcion;
  }

  /**
   * @return the est_opcion
   */
  public String getEst_opcion() {
	 return est_opcion;
  }

  /**
   * @param est_opcion the est_opcion to set
   */
  public void setEst_opcion(String est_opcion) {
	 this.est_opcion = est_opcion;
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
	 return "modelo_opcion [id_opcion=" + id_opcion + ", nom_opcion=" + nom_opcion + ", est_opcion=" + est_opcion
		  + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica
		  + ", fec_modifica=" + fec_modifica + "]";
  }

  public String toStringEstado() {
	 if (est_opcion.equals("A")) {
		return "ACTIVO";
	 } else {
		return "INACTIVO";
	 }
  }

}
