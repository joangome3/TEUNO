package bp.aplicaciones.sibod.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_7;

public class modelo_movimiento {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	private long id_movimiento;
	private String tck_movimiento;
	private long id_articulo;
	private long id_ubicacion;
	private long id_estado;
	private String cod_articulo;
	private String des_articulo;
	private String ubi_articulo;
	private String tip_movimiento;
	private int sto_anterior;
	private int sto_actual;
	private int can_afectada;
	private long id_localidad;
	private long id_solicitante;
	private String nom_solicitante;
	private String ape_solicitante;
	private long id_usuario;
	private String nom_usuario;
	private String ape_usuario;
	private String emp_solicitante;
	private String emp_ubicacion;
	private String cat_articulo;
	private String tur_movimiento;
	private Timestamp fec_movimiento;
	private String obs_movimiento;
	private String est_movimiento;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_movimiento() {
		super();
	}

	/**
	 * @param id_movimiento
	 * @param tck_movimiento
	 * @param id_articulo
	 * @param tip_movimiento
	 * @param sto_anterior
	 * @param sto_actual
	 * @param can_afectada
	 * @param id_localidad
	 * @param id_solicitante
	 * @param id_usuario
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param est_movimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_movimiento(long id_movimiento, String tck_movimiento, long id_articulo, String tip_movimiento,
			int sto_anterior, int sto_actual, int can_afectada, long id_localidad, long id_solicitante, long id_usuario,
			String tur_movimiento, Timestamp fec_movimiento, String obs_movimiento, String est_movimiento,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_movimiento = id_movimiento;
		this.tck_movimiento = tck_movimiento;
		this.id_articulo = id_articulo;
		this.tip_movimiento = tip_movimiento;
		this.sto_anterior = sto_anterior;
		this.sto_actual = sto_actual;
		this.can_afectada = can_afectada;
		this.id_localidad = id_localidad;
		this.id_solicitante = id_solicitante;
		this.id_usuario = id_usuario;
		this.tur_movimiento = tur_movimiento;
		this.fec_movimiento = fec_movimiento;
		this.obs_movimiento = obs_movimiento;
		this.est_movimiento = est_movimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_movimiento
	 * @param tck_movimiento
	 * @param id_articulo
	 * @param id_estado
	 * @param cod_articulo
	 * @param des_articulo
	 * @param ubi_articulo
	 * @param tip_movimiento
	 * @param sto_anterior
	 * @param sto_actual
	 * @param can_afectada
	 * @param id_localidad
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param ape_solicitante
	 * @param id_usuario
	 * @param nom_usuario
	 * @param ape_usuario
	 * @param emp_solicitante
	 * @param emp_ubicacion
	 * @param cat_articulo
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param est_movimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_movimiento(long id_movimiento, String tck_movimiento, long id_articulo, long id_estado,
			String cod_articulo, String des_articulo, String ubi_articulo, String tip_movimiento, int sto_anterior,
			int sto_actual, int can_afectada, long id_localidad, long id_solicitante, String nom_solicitante,
			String ape_solicitante, long id_usuario, String nom_usuario, String ape_usuario, String emp_solicitante,
			String emp_ubicacion, String cat_articulo, String tur_movimiento, Timestamp fec_movimiento,
			String obs_movimiento, String est_movimiento, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_movimiento = id_movimiento;
		this.tck_movimiento = tck_movimiento;
		this.id_articulo = id_articulo;
		this.id_estado = id_estado;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.ubi_articulo = ubi_articulo;
		this.tip_movimiento = tip_movimiento;
		this.sto_anterior = sto_anterior;
		this.sto_actual = sto_actual;
		this.can_afectada = can_afectada;
		this.id_localidad = id_localidad;
		this.id_solicitante = id_solicitante;
		this.nom_solicitante = nom_solicitante;
		this.ape_solicitante = ape_solicitante;
		this.id_usuario = id_usuario;
		this.nom_usuario = nom_usuario;
		this.ape_usuario = ape_usuario;
		this.emp_solicitante = emp_solicitante;
		this.emp_ubicacion = emp_ubicacion;
		this.cat_articulo = cat_articulo;
		this.tur_movimiento = tur_movimiento;
		this.fec_movimiento = fec_movimiento;
		this.obs_movimiento = obs_movimiento;
		this.est_movimiento = est_movimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_ubicacion
	 */
	public long getId_ubicacion() {
		return id_ubicacion;
	}

	/**
	 * @param id_ubicacion the id_ubicacion to set
	 */
	public void setId_ubicacion(long id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	/**
	 * @return the id_movimiento
	 */
	public long getId_movimiento() {
		return id_movimiento;
	}

	/**
	 * @param id_movimiento the id_movimiento to set
	 */
	public void setId_movimiento(long id_movimiento) {
		this.id_movimiento = id_movimiento;
	}

	/**
	 * @return the tck_movimiento
	 */
	public String getTck_movimiento() {
		return tck_movimiento;
	}

	/**
	 * @param tck_movimiento the tck_movimiento to set
	 */
	public void setTck_movimiento(String tck_movimiento) {
		this.tck_movimiento = tck_movimiento;
	}

	/**
	 * @return the id_articulo
	 */
	public long getId_articulo() {
		return id_articulo;
	}

	/**
	 * @param id_articulo the id_articulo to set
	 */
	public void setId_articulo(long id_articulo) {
		this.id_articulo = id_articulo;
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
	 * @return the id_estado
	 */
	public long getId_estado() {
		return id_estado;
	}

	/**
	 * @param id_estado the id_estado to set
	 */
	public void setId_estado(long id_estado) {
		this.id_estado = id_estado;
	}

	/**
	 * @return the tip_movimiento
	 */
	public String getTip_movimiento() {
		return tip_movimiento;
	}

	/**
	 * @param tip_movimiento the tip_movimiento to set
	 */
	public void setTip_movimiento(String tip_movimiento) {
		this.tip_movimiento = tip_movimiento;
	}

	/**
	 * @return the sto_anterior
	 */
	public int getSto_anterior() {
		return sto_anterior;
	}

	/**
	 * @param sto_anterior the sto_anterior to set
	 */
	public void setSto_anterior(int sto_anterior) {
		this.sto_anterior = sto_anterior;
	}

	/**
	 * @return the sto_actual
	 */
	public int getSto_actual() {
		return sto_actual;
	}

	/**
	 * @param sto_actual the sto_actual to set
	 */
	public void setSto_actual(int sto_actual) {
		this.sto_actual = sto_actual;
	}

	/**
	 * @return the can_afectada
	 */
	public int getCan_afectada() {
		return can_afectada;
	}

	/**
	 * @param can_afectada the can_afectada to set
	 */
	public void setCan_afectada(int can_afectada) {
		this.can_afectada = can_afectada;
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
	 * @return the id_solicitante
	 */
	public long getId_solicitante() {
		return id_solicitante;
	}

	/**
	 * @param id_solicitante the id_solicitante to set
	 */
	public void setId_solicitante(long id_solicitante) {
		this.id_solicitante = id_solicitante;
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
	 * @return the ape_solicitante
	 */
	public String getApe_solicitante() {
		return ape_solicitante;
	}

	/**
	 * @param ape_solicitante the ape_solicitante to set
	 */
	public void setApe_solicitante(String ape_solicitante) {
		this.ape_solicitante = ape_solicitante;
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
	 * @return the ape_usuario
	 */
	public String getApe_usuario() {
		return ape_usuario;
	}

	/**
	 * @param ape_usuario the ape_usuario to set
	 */
	public void setApe_usuario(String ape_usuario) {
		this.ape_usuario = ape_usuario;
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
	 * @return the tur_movimiento
	 */
	public String getTur_movimiento() {
		return tur_movimiento;
	}

	/**
	 * @param tur_movimiento the tur_movimiento to set
	 */
	public void setTur_movimiento(String tur_movimiento) {
		this.tur_movimiento = tur_movimiento;
	}

	/**
	 * @return the fec_movimiento
	 */
	public Timestamp getFec_movimiento() {
		return fec_movimiento;
	}

	/**
	 * @param fec_movimiento the fec_movimiento to set
	 */
	public void setFec_movimiento(Timestamp fec_movimiento) {
		this.fec_movimiento = fec_movimiento;
	}

	/**
	 * @return the obs_movimiento
	 */
	public String getObs_movimiento() {
		return obs_movimiento;
	}

	/**
	 * @param obs_movimiento the obs_movimiento to set
	 */
	public void setObs_movimiento(String obs_movimiento) {
		this.obs_movimiento = obs_movimiento;
	}

	/**
	 * @return the est_movimiento
	 */
	public String getEst_movimiento() {
		return est_movimiento;
	}

	/**
	 * @param est_movimiento the est_movimiento to set
	 */
	public void setEst_movimiento(String est_movimiento) {
		this.est_movimiento = est_movimiento;
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
		return "modelo_movimiento [id_movimiento=" + id_movimiento + ", tck_movimiento=" + tck_movimiento
				+ ", id_articulo=" + id_articulo + ", id_ubicacion=" + id_ubicacion + ", id_estado=" + id_estado
				+ ", cod_articulo=" + cod_articulo + ", des_articulo=" + des_articulo + ", ubi_articulo=" + ubi_articulo
				+ ", tip_movimiento=" + tip_movimiento + ", sto_anterior=" + sto_anterior + ", sto_actual=" + sto_actual
				+ ", can_afectada=" + can_afectada + ", id_localidad=" + id_localidad + ", id_solicitante="
				+ id_solicitante + ", nom_solicitante=" + nom_solicitante + ", ape_solicitante=" + ape_solicitante
				+ ", id_usuario=" + id_usuario + ", nom_usuario=" + nom_usuario + ", ape_usuario=" + ape_usuario
				+ ", emp_solicitante=" + emp_solicitante + ", emp_ubicacion=" + emp_ubicacion + ", cat_articulo="
				+ cat_articulo + ", tur_movimiento=" + tur_movimiento + ", fec_movimiento=" + fec_movimiento
				+ ", obs_movimiento=" + obs_movimiento + ", est_movimiento=" + est_movimiento + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String toStringTicketMovimiento() {
		return tck_movimiento;
	}

	public String toStringFecha() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_movimiento());
		return s;
	}

	public String toStringTipo() {
		if (tip_movimiento.equals("I")) {
			return "INGRESO";
		} else {
			return "EGRESO";
		}
	}

	public String estiloEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_parametros_generales_7> listaParametros = new ArrayList<modelo_parametros_generales_7>();
		listaParametros = consultasABaseDeDatos.cargarParametros7(String.valueOf(id_estado), "", id_localidad, 3);
		String estilo = "";
		if (listaParametros.size() > 0) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
					+ listaParametros.get(0).getColor() + " !important; text-align: center !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: italic !important; text-align: center !important;";
		}
		return estilo;
	}

	public String nombreEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_parametros_generales_7> listaParametros = new ArrayList<modelo_parametros_generales_7>();
		listaParametros = consultasABaseDeDatos.cargarParametros7(String.valueOf(id_estado), "", id_localidad, 3);
		String nombre = "";
		if (listaParametros.size() > 0) {
			nombre = listaParametros.get(0).getNom_estado();
		}
		return nombre;
	}

	public String nombreCompletoSolicitante() {
		String nombre_completo = "";
		nombre_completo = nom_solicitante + " " + ape_solicitante;
		return nombre_completo;
	}

}
