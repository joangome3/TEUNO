package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;

public class modelo_relacion_empresa_mantenimiento {

  private long id_relacion;
  private long id_empresa;
  private long id_mantenimiento;
  private String est_relacion;
  private String usu_ingresa;
  private Timestamp fec_ingresa;
  private String usu_modifica;
  private Timestamp fec_modifica;

  /**
   * 
   */
  public modelo_relacion_empresa_mantenimiento() {
	 super();
  }

  /**
   * @param id_relacion
   * @param id_empresa
   * @param id_mantenimiento
   * @param est_relacion
   * @param usu_ingresa
   * @param fec_ingresa
   * @param usu_modifica
   * @param fec_modifica
   */

  @Override
  public String toString() {
	 return "modelo_relacion_empresa_mantenimiento [id_relacion=" + id_relacion + ", id_empresa=" + id_empresa
		  + ", id_mantenimiento=" + id_mantenimiento + ", est_relacion=" + est_relacion + ", usu_ingresa=" + usu_ingresa
		  + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
  }

  /**
   * @param id_relacion
   * @param id_empresa
   * @param id_mantenimiento
   * @param est_relacion
   * @param usu_ingresa
   * @param fec_ingresa
   * @param usu_modifica
   * @param fec_modifica
   */
  public modelo_relacion_empresa_mantenimiento(long id_relacion, long id_empresa, long id_mantenimiento,
		String est_relacion, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
	 super();
	 this.id_relacion = id_relacion;
	 this.id_empresa = id_empresa;
	 this.id_mantenimiento = id_mantenimiento;
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
   * @return the id_empresa
   */
  public long getId_empresa() {
	 return id_empresa;
  }

  /**
   * @param id_empresa the id_empresa to set
   */
  public void setId_empresa(long id_empresa) {
	 this.id_empresa = id_empresa;
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

  public String toStringEstado() {
	 if (est_relacion.equals("A")) {
		return "ACTIVO";
	 } else {
		return "INACTIVO";
	 }
  }
}
