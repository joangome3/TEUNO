package bp.aplicaciones.sibod.modelo;

import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class modelo_detalle_movimiento_herencia extends modelo_detalle_movimiento {

	private long tck_mov;
	private String tck_noc;
	private String cod_articulo;
	private String des_articulo;
	private String ubi_articulo;
	private String cat_articulo;
	private String nom_usuario;
	private String nom_solicitante;
	private String emp_solicitante;
	private String emp_ubicacion;
	private Blob doc_movimiento;
	private String nom_documento;
	private String ext_documento;

	/**
	 * 
	 */
	public modelo_detalle_movimiento_herencia() {
		super();
	}

	/**
	 * @param doc_movimiento
	 * @param nom_documento
	 * @param ext_documento
	 */
	public modelo_detalle_movimiento_herencia(Blob doc_movimiento, String nom_documento, String ext_documento) {
		super();
		this.doc_movimiento = doc_movimiento;
		this.nom_documento = nom_documento;
		this.ext_documento = ext_documento;
	}

	/**
	 * @param id_det_movimiento
	 * @param id_movimiento
	 * @param id_articulo
	 * @param tip_movimiento
	 * @param sto_anterior
	 * @param sto_actual
	 * @param can_afectada
	 * @param id_solicitante
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param es_alcance
	 * @param est_det_movimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_detalle_movimiento_herencia(long id_det_movimiento, long id_movimiento, long id_articulo,
			String tip_movimiento, int sto_anterior, int sto_actual, int can_afectada, long id_solicitante,
			String tur_movimiento, Timestamp fec_movimiento, String obs_movimiento, String es_alcance,
			String est_det_movimiento, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica, long tck_mov, String tck_noc, String cod_articulo, String des_articulo,
			String ubi_articulo, String cat_articulo, String nom_usuario, String nom_solicitante,
			String emp_solicitante, String emp_ubicacion, Blob doc_movimiento, String nom_documento,
			String ext_documento) {
		super(id_det_movimiento, id_movimiento, id_articulo, tip_movimiento, sto_anterior, sto_actual, can_afectada,
				id_solicitante, tur_movimiento, fec_movimiento, obs_movimiento, es_alcance, est_det_movimiento,
				usu_ingresa, fec_ingresa, usu_modifica, fec_modifica);
		// TODO Auto-generated constructor stub
		this.tck_mov = tck_mov;
		this.tck_noc = tck_noc;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.ubi_articulo = ubi_articulo;
		this.cat_articulo = cat_articulo;
		this.nom_usuario = nom_usuario;
		this.nom_solicitante = nom_solicitante;
		this.emp_solicitante = emp_solicitante;
		this.emp_ubicacion = emp_ubicacion;
		this.doc_movimiento = doc_movimiento;
		this.nom_documento = nom_documento;
		this.ext_documento = ext_documento;
	}

	/**
	 * @return the tck_noc
	 */
	public String getTck_noc() {
		return tck_noc;
	}

	/**
	 * @param tck_noc the tck_noc to set
	 */
	public void setTck_noc(String tck_noc) {
		this.tck_noc = tck_noc;
	}

	/**
	 * @return the tck_mov
	 */
	public long getTck_mov() {
		return tck_mov;
	}

	/**
	 * @param tck_mov the tck_mov to set
	 */
	public void setTck_mov(long tck_mov) {
		this.tck_mov = tck_mov;
	}

	/**
	 * @return the cod_articulo
	 */
	public String getCod_articulo() {
		return cod_articulo;
	}

	/**
	 * @param cod_articulo the cod_articulo to set
	 */
	public void setCod_articulo(String cod_articulo) {
		this.cod_articulo = cod_articulo;
	}

	/**
	 * @return the des_articulo
	 */
	public String getDes_articulo() {
		return des_articulo;
	}

	/**
	 * @param des_articulo the des_articulo to set
	 */
	public void setDes_articulo(String des_articulo) {
		this.des_articulo = des_articulo;
	}

	/**
	 * @return the ubi_articulo
	 */
	public String getUbi_articulo() {
		return ubi_articulo;
	}

	/**
	 * @param ubi_articulo the ubi_articulo to set
	 */
	public void setUbi_articulo(String ubi_articulo) {
		this.ubi_articulo = ubi_articulo;
	}

	/**
	 * @return the cat_articulo
	 */
	public String getCat_articulo() {
		return cat_articulo;
	}

	/**
	 * @param cat_articulo the cat_articulo to set
	 */
	public void setCat_articulo(String cat_articulo) {
		this.cat_articulo = cat_articulo;
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
	 * @return the nom_solicitante
	 */
	public String getNom_solicitante() {
		return nom_solicitante;
	}

	/**
	 * @param nom_solicitante the nom_solicitante to set
	 */
	public void setNom_solicitante(String nom_solicitante) {
		this.nom_solicitante = nom_solicitante;
	}

	/**
	 * @return the emp_solicitante
	 */
	public String getEmp_solicitante() {
		return emp_solicitante;
	}

	/**
	 * @param emp_solicitante the emp_solicitante to set
	 */
	public void setEmp_solicitante(String emp_solicitante) {
		this.emp_solicitante = emp_solicitante;
	}

	/**
	 * @return the emp_ubicacion
	 */
	public String getEmp_ubicacion() {
		return emp_ubicacion;
	}

	/**
	 * @param emp_ubicacion the emp_ubicacion to set
	 */
	public void setEmp_ubicacion(String emp_ubicacion) {
		this.emp_ubicacion = emp_ubicacion;
	}

	/**
	 * @return the doc_movimiento
	 */
	public Blob getDoc_movimiento() {
		return doc_movimiento;
	}

	/**
	 * @param doc_movimiento the doc_movimiento to set
	 */
	public void setDoc_movimiento(Blob doc_movimiento) {
		this.doc_movimiento = doc_movimiento;
	}

	/**
	 * @return the nom_documento
	 */
	public String getNom_documento() {
		return nom_documento;
	}

	/**
	 * @param nom_documento the nom_documento to set
	 */
	public void setNom_documento(String nom_documento) {
		this.nom_documento = nom_documento;
	}

	/**
	 * @return the ext_documento
	 */
	public String getExt_documento() {
		return ext_documento;
	}

	/**
	 * @param ext_documento the ext_documento to set
	 */
	public void setExt_documento(String ext_documento) {
		this.ext_documento = ext_documento;
	}

	public String toStringTipo() {
		if (getTip_movimiento().equals("I")) {
			return "INGRESO";
		} else {
			return "EGRESO";
		}
	}

	public String toStringFecha() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_movimiento());
		return s;
	}

	public String toStringTicketMovimiento() {
		return "DB0" + getTck_mov();
	}

	@Override
	public String toString() {
		return "modelo_detalle_movimiento_herencia [tck_mov=" + tck_mov + ", tck_noc=" + tck_noc + ", cod_articulo="
				+ cod_articulo + ", des_articulo=" + des_articulo + ", ubi_articulo=" + ubi_articulo + ", cat_articulo="
				+ cat_articulo + ", nom_usuario=" + nom_usuario + ", nom_solicitante=" + nom_solicitante
				+ ", emp_solicitante=" + emp_solicitante + ", emp_ubicacion=" + emp_ubicacion + ", doc_movimiento="
				+ doc_movimiento + ", nom_documento=" + nom_documento + ", ext_documento=" + ext_documento + "]";
	}

}
