package bp.aplicaciones.cintas.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class modelo_movimiento_detalle_dn extends modelo_movimiento_dn implements Cloneable {

	private long id_detalle_movimiento;
	private long id_movimiento;
	private long id_articulo;
	private String cod_articulo_anterior;
	private String des_articulo_anterior;
	private long id_cat_articulo_anterior;
	private String nom_cat_articulo_anterior;
	private String si_ing_fec_inicio_fin_anterior;
	private String es_fecha_anterior;
	private long id_fec_respaldo_anterior;
	private String nom_id_fec_respaldo_anterior;
	private Timestamp fec_inicio_anterior;
	private Timestamp fec_fin_anterior;
	private long id_cap_articulo_anterior;
	private String nom_id_cap_articulo_anterior;
	private String tip_respaldo_anterior;
	private String nom_tip_respaldo_anterior;
	private String id_contenedor_anterior;
	private long id_ubicacion_anterior;
	private String nom_ubicacion_anterior;
	private long hora_llegada_custodia_anterior;
	private long hora_salida_custodia_anterior;
	private String remesa_ingreso_custodia_anterior;
	private String remesa_salida_custodia_anterior;
	private String cod_articulo_actual;
	private String des_articulo_actual;
	private long id_cat_articulo_actual;
	private String nom_cat_articulo_actual;
	private String si_ing_fec_inicio_fin_actual;
	private String es_fecha_actual;
	private long id_fec_respaldo_actual;
	private String nom_id_fec_respaldo_actual;
	private Timestamp fec_inicio_actual;
	private Timestamp fec_fin_actual;
	private long id_cap_articulo_actual;
	private String nom_id_cap_articulo_actual;
	private String tip_respaldo_actual;
	private String nom_tip_respaldo_actual;
	private String id_contenedor_actual;
	private long id_ubicacion_actual;
	private String nom_ubicacion_actual;
	private long hora_llegada_custodia_actual;
	private long hora_salida_custodia_actual;
	private String remesa_ingreso_custodia_actual;
	private String remesa_salida_custodia_actual;
	private String revision_1;
	private String revision_2;
	private String revision_3;
	private String actualiza_inventario;
	private String est_detalle_movimiento;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_movimiento_detalle_dn clone() {
		modelo_movimiento_detalle_dn movimiento_detalle = new modelo_movimiento_detalle_dn(this.id_detalle_movimiento,
				this.id_movimiento, this.id_articulo, this.cod_articulo_anterior, this.des_articulo_anterior,
				this.id_cat_articulo_anterior, this.nom_cat_articulo_anterior, this.si_ing_fec_inicio_fin_anterior,
				this.es_fecha_anterior, this.id_fec_respaldo_anterior, this.nom_id_fec_respaldo_anterior,
				this.fec_inicio_anterior, this.fec_fin_anterior, this.id_cap_articulo_anterior,
				this.nom_id_cap_articulo_anterior, this.tip_respaldo_anterior, this.nom_tip_respaldo_anterior,
				this.id_contenedor_anterior, this.id_ubicacion_anterior, this.nom_ubicacion_anterior,
				this.hora_llegada_custodia_anterior, this.hora_salida_custodia_anterior,
				this.remesa_ingreso_custodia_anterior, this.remesa_salida_custodia_anterior, this.cod_articulo_actual,
				this.des_articulo_actual, this.id_cat_articulo_actual, this.nom_cat_articulo_actual,
				this.si_ing_fec_inicio_fin_actual, this.es_fecha_actual, this.id_fec_respaldo_actual,
				this.nom_id_fec_respaldo_actual, this.fec_inicio_actual, this.fec_fin_actual,
				this.id_cap_articulo_actual, this.nom_id_cap_articulo_actual, this.tip_respaldo_actual,
				this.nom_tip_respaldo_actual, this.id_contenedor_actual, this.id_ubicacion_actual,
				this.nom_ubicacion_actual, this.hora_llegada_custodia_actual, this.hora_salida_custodia_actual,
				this.remesa_ingreso_custodia_actual, this.remesa_salida_custodia_actual, this.revision_1,
				this.revision_2, this.revision_3, this.actualiza_inventario, this.est_detalle_movimiento,
				this.usu_ingresa, this.fec_ingresa, this.usu_modifica, this.fec_modifica);
		return movimiento_detalle;
	}

	/**
	 * 
	 */
	public modelo_movimiento_detalle_dn() {
		super();
	}

	/**
	 * @param id_movimiento
	 * @param tck_movimiento
	 * @param tip_pedido
	 * @param fec_solicitud
	 * @param hor_solicitud
	 * @param fec_respuesta
	 * @param hor_respuesta
	 * @param fec_ejecucion
	 * @param hor_ejecucion
	 * @param id_localidad
	 * @param nom_localidad
	 * @param id_solicitante
	 * @param nom_solicitante
	 * @param id_usuario
	 * @param nom_usuario
	 * @param tur_movimiento
	 * @param fec_movimiento
	 * @param obs_movimiento
	 * @param usu_revision_1
	 * @param usu_revision_2
	 * @param usu_revision_3
	 * @param est_movimiento
	 * @param est_validacion
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 * @param id_detalle_movimiento
	 * @param id_movimiento2
	 * @param id_articulo
	 * @param cod_articulo_anterior
	 * @param des_articulo_anterior
	 * @param id_cat_articulo_anterior
	 * @param nom_cat_articulo_anterior
	 * @param si_ing_fec_inicio_fin_anterior
	 * @param es_fecha_anterior
	 * @param id_fec_respaldo_anterior
	 * @param nom_id_fec_respaldo_anterior
	 * @param fec_inicio_anterior
	 * @param fec_fin_anterior
	 * @param id_cap_articulo_anterior
	 * @param nom_id_cap_articulo_anterior
	 * @param tip_respaldo_anterior
	 * @param nom_tip_respaldo_anterior
	 * @param id_contenedor_anterior
	 * @param id_ubicacion_anterior
	 * @param nom_ubicacion_anterior
	 * @param hora_llegada_custodia_anterior
	 * @param hora_salida_custodia_anterior
	 * @param remesa_ingreso_custodia_anterior
	 * @param remesa_salida_custodia_anterior
	 * @param cod_articulo_actual
	 * @param des_articulo_actual
	 * @param id_cat_articulo_actual
	 * @param nom_cat_articulo_actual
	 * @param si_ing_fec_inicio_fin_actual
	 * @param es_fecha_actual
	 * @param id_fec_respaldo_actual
	 * @param nom_id_fec_respaldo_actual
	 * @param fec_inicio_actual
	 * @param fec_fin_actual
	 * @param id_cap_articulo_actual
	 * @param nom_id_cap_articulo_actual
	 * @param tip_respaldo_actual
	 * @param nom_tip_respaldo_actual
	 * @param id_contenedor_actual
	 * @param id_ubicacion_actual
	 * @param nom_ubicacion_actual
	 * @param hora_llegada_custodia_actual
	 * @param hora_salida_custodia_actual
	 * @param remesa_ingreso_custodia_actual
	 * @param remesa_salida_custodia_actual
	 * @param revision_1
	 * @param revision_2
	 * @param revision_3
	 * @param actualiza_inventario
	 * @param est_detalle_movimiento
	 * @param usu_ingresa2
	 * @param fec_ingresa2
	 * @param usu_modifica2
	 * @param fec_modifica2
	 */
	public modelo_movimiento_detalle_dn(long id_movimiento, String tck_movimiento, String tip_pedido,
			Timestamp fec_solicitud, long hor_solicitud, Timestamp fec_respuesta, long hor_respuesta,
			Timestamp fec_ejecucion, long hor_ejecucion, long id_localidad, String nom_localidad, long id_solicitante,
			String nom_solicitante, long id_usuario, String nom_usuario, String tur_movimiento, String nom_turno,
			Timestamp fec_movimiento, String obs_movimiento, String usu_revision_1, String nom_usuario_revisa_1,
			String usu_revision_2, String nom_usuario_revisa_2, String usu_revision_3, String nom_usuario_revisa_3,
			String est_movimiento, String est_validacion, String usu_ingresa, String nom_usuario_ingresa,
			Timestamp fec_ingresa, String usu_modifica, String nom_usuario_modifica, Timestamp fec_modifica,
			long id_detalle_movimiento, long id_movimiento2, long id_articulo, String cod_articulo_anterior,
			String des_articulo_anterior, long id_cat_articulo_anterior, String nom_cat_articulo_anterior,
			String si_ing_fec_inicio_fin_anterior, String es_fecha_anterior, long id_fec_respaldo_anterior,
			String nom_id_fec_respaldo_anterior, Timestamp fec_inicio_anterior, Timestamp fec_fin_anterior,
			long id_cap_articulo_anterior, String nom_id_cap_articulo_anterior, String tip_respaldo_anterior,
			String nom_tip_respaldo_anterior, String id_contenedor_anterior, long id_ubicacion_anterior,
			String nom_ubicacion_anterior, long hora_llegada_custodia_anterior, long hora_salida_custodia_anterior,
			String remesa_ingreso_custodia_anterior, String remesa_salida_custodia_anterior, String cod_articulo_actual,
			String des_articulo_actual, long id_cat_articulo_actual, String nom_cat_articulo_actual,
			String si_ing_fec_inicio_fin_actual, String es_fecha_actual, long id_fec_respaldo_actual,
			String nom_id_fec_respaldo_actual, Timestamp fec_inicio_actual, Timestamp fec_fin_actual,
			long id_cap_articulo_actual, String nom_id_cap_articulo_actual, String tip_respaldo_actual,
			String nom_tip_respaldo_actual, String id_contenedor_actual, long id_ubicacion_actual,
			String nom_ubicacion_actual, long hora_llegada_custodia_actual, long hora_salida_custodia_actual,
			String remesa_ingreso_custodia_actual, String remesa_salida_custodia_actual, String revision_1,
			String revision_2, String revision_3, String actualiza_inventario, String est_detalle_movimiento,
			String usu_ingresa2, Timestamp fec_ingresa2, String usu_modifica2, Timestamp fec_modifica2) {
		super(id_movimiento, tck_movimiento, tip_pedido, fec_solicitud, hor_solicitud, fec_respuesta, hor_respuesta,
				fec_ejecucion, hor_ejecucion, id_localidad, nom_localidad, id_solicitante, nom_solicitante, id_usuario,
				nom_usuario, tur_movimiento, nom_turno, fec_movimiento, obs_movimiento, usu_revision_1,
				nom_usuario_revisa_1, usu_revision_2, nom_usuario_revisa_2, usu_revision_3, nom_usuario_revisa_3,
				est_movimiento, est_validacion, usu_ingresa, nom_usuario_ingresa, fec_ingresa, usu_modifica,
				nom_usuario_modifica, fec_modifica);
		this.id_detalle_movimiento = id_detalle_movimiento;
		this.id_movimiento = id_movimiento2;
		this.id_articulo = id_articulo;
		this.cod_articulo_anterior = cod_articulo_anterior;
		this.des_articulo_anterior = des_articulo_anterior;
		this.id_cat_articulo_anterior = id_cat_articulo_anterior;
		this.nom_cat_articulo_anterior = nom_cat_articulo_anterior;
		this.si_ing_fec_inicio_fin_anterior = si_ing_fec_inicio_fin_anterior;
		this.es_fecha_anterior = es_fecha_anterior;
		this.id_fec_respaldo_anterior = id_fec_respaldo_anterior;
		this.nom_id_fec_respaldo_anterior = nom_id_fec_respaldo_anterior;
		this.fec_inicio_anterior = fec_inicio_anterior;
		this.fec_fin_anterior = fec_fin_anterior;
		this.id_cap_articulo_anterior = id_cap_articulo_anterior;
		this.nom_id_cap_articulo_anterior = nom_id_cap_articulo_anterior;
		this.tip_respaldo_anterior = tip_respaldo_anterior;
		this.nom_tip_respaldo_anterior = nom_tip_respaldo_anterior;
		this.id_contenedor_anterior = id_contenedor_anterior;
		this.id_ubicacion_anterior = id_ubicacion_anterior;
		this.nom_ubicacion_anterior = nom_ubicacion_anterior;
		this.hora_llegada_custodia_anterior = hora_llegada_custodia_anterior;
		this.hora_salida_custodia_anterior = hora_salida_custodia_anterior;
		this.remesa_ingreso_custodia_anterior = remesa_ingreso_custodia_anterior;
		this.remesa_salida_custodia_anterior = remesa_salida_custodia_anterior;
		this.cod_articulo_actual = cod_articulo_actual;
		this.des_articulo_actual = des_articulo_actual;
		this.id_cat_articulo_actual = id_cat_articulo_actual;
		this.nom_cat_articulo_actual = nom_cat_articulo_actual;
		this.si_ing_fec_inicio_fin_actual = si_ing_fec_inicio_fin_actual;
		this.es_fecha_actual = es_fecha_actual;
		this.id_fec_respaldo_actual = id_fec_respaldo_actual;
		this.nom_id_fec_respaldo_actual = nom_id_fec_respaldo_actual;
		this.fec_inicio_actual = fec_inicio_actual;
		this.fec_fin_actual = fec_fin_actual;
		this.id_cap_articulo_actual = id_cap_articulo_actual;
		this.nom_id_cap_articulo_actual = nom_id_cap_articulo_actual;
		this.tip_respaldo_actual = tip_respaldo_actual;
		this.nom_tip_respaldo_actual = nom_tip_respaldo_actual;
		this.id_contenedor_actual = id_contenedor_actual;
		this.id_ubicacion_actual = id_ubicacion_actual;
		this.nom_ubicacion_actual = nom_ubicacion_actual;
		this.hora_llegada_custodia_actual = hora_llegada_custodia_actual;
		this.hora_salida_custodia_actual = hora_salida_custodia_actual;
		this.remesa_ingreso_custodia_actual = remesa_ingreso_custodia_actual;
		this.remesa_salida_custodia_actual = remesa_salida_custodia_actual;
		this.revision_1 = revision_1;
		this.revision_2 = revision_2;
		this.revision_3 = revision_3;
		this.actualiza_inventario = actualiza_inventario;
		this.est_detalle_movimiento = est_detalle_movimiento;
		this.usu_ingresa = usu_ingresa2;
		this.fec_ingresa = fec_ingresa2;
		this.usu_modifica = usu_modifica2;
		this.fec_modifica = fec_modifica2;
	}

	/**
	 * @param id_detalle_movimiento
	 * @param id_movimiento
	 * @param id_articulo
	 * @param cod_articulo_anterior
	 * @param des_articulo_anterior
	 * @param id_cat_articulo_anterior
	 * @param nom_cat_articulo_anterior
	 * @param si_ing_fec_inicio_fin_anterior
	 * @param es_fecha_anterior
	 * @param id_fec_respaldo_anterior
	 * @param nom_id_fec_respaldo_anterior
	 * @param fec_inicio_anterior
	 * @param fec_fin_anterior
	 * @param id_cap_articulo_anterior
	 * @param nom_id_cap_articulo_anterior
	 * @param tip_respaldo_anterior
	 * @param nom_tip_respaldo_anterior
	 * @param id_contenedor_anterior
	 * @param id_ubicacion_anterior
	 * @param nom_ubicacion_anterior
	 * @param hora_llegada_custodia_anterior
	 * @param hora_salida_custodia_anterior
	 * @param remesa_ingreso_custodia_anterior
	 * @param remesa_salida_custodia_anterior
	 * @param cod_articulo_actual
	 * @param des_articulo_actual
	 * @param id_cat_articulo_actual
	 * @param nom_cat_articulo_actual
	 * @param si_ing_fec_inicio_fin_actual
	 * @param es_fecha_actual
	 * @param id_fec_respaldo_actual
	 * @param nom_id_fec_respaldo_actual
	 * @param fec_inicio_actual
	 * @param fec_fin_actual
	 * @param id_cap_articulo_actual
	 * @param nom_id_cap_articulo_actual
	 * @param tip_respaldo_actual
	 * @param nom_tip_respaldo_actual
	 * @param id_contenedor_actual
	 * @param id_ubicacion_actual
	 * @param nom_ubicacion_actual
	 * @param hora_llegada_custodia_actual
	 * @param hora_salida_custodia_actual
	 * @param remesa_ingreso_custodia_actual
	 * @param remesa_salida_custodia_actual
	 * @param revision_1
	 * @param revision_2
	 * @param revision_3
	 * @param actualiza_inventario
	 * @param est_detalle_movimiento
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_movimiento_detalle_dn(long id_detalle_movimiento, long id_movimiento, long id_articulo,
			String cod_articulo_anterior, String des_articulo_anterior, long id_cat_articulo_anterior,
			String nom_cat_articulo_anterior, String si_ing_fec_inicio_fin_anterior, String es_fecha_anterior,
			long id_fec_respaldo_anterior, String nom_id_fec_respaldo_anterior, Timestamp fec_inicio_anterior,
			Timestamp fec_fin_anterior, long id_cap_articulo_anterior, String nom_id_cap_articulo_anterior,
			String tip_respaldo_anterior, String nom_tip_respaldo_anterior, String id_contenedor_anterior,
			long id_ubicacion_anterior, String nom_ubicacion_anterior, long hora_llegada_custodia_anterior,
			long hora_salida_custodia_anterior, String remesa_ingreso_custodia_anterior,
			String remesa_salida_custodia_anterior, String cod_articulo_actual, String des_articulo_actual,
			long id_cat_articulo_actual, String nom_cat_articulo_actual, String si_ing_fec_inicio_fin_actual,
			String es_fecha_actual, long id_fec_respaldo_actual, String nom_id_fec_respaldo_actual,
			Timestamp fec_inicio_actual, Timestamp fec_fin_actual, long id_cap_articulo_actual,
			String nom_id_cap_articulo_actual, String tip_respaldo_actual, String nom_tip_respaldo_actual,
			String id_contenedor_actual, long id_ubicacion_actual, String nom_ubicacion_actual,
			long hora_llegada_custodia_actual, long hora_salida_custodia_actual, String remesa_ingreso_custodia_actual,
			String remesa_salida_custodia_actual, String revision_1, String revision_2, String revision_3,
			String actualiza_inventario, String est_detalle_movimiento, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_detalle_movimiento = id_detalle_movimiento;
		this.id_movimiento = id_movimiento;
		this.id_articulo = id_articulo;
		this.cod_articulo_anterior = cod_articulo_anterior;
		this.des_articulo_anterior = des_articulo_anterior;
		this.id_cat_articulo_anterior = id_cat_articulo_anterior;
		this.nom_cat_articulo_anterior = nom_cat_articulo_anterior;
		this.si_ing_fec_inicio_fin_anterior = si_ing_fec_inicio_fin_anterior;
		this.es_fecha_anterior = es_fecha_anterior;
		this.id_fec_respaldo_anterior = id_fec_respaldo_anterior;
		this.nom_id_fec_respaldo_anterior = nom_id_fec_respaldo_anterior;
		this.fec_inicio_anterior = fec_inicio_anterior;
		this.fec_fin_anterior = fec_fin_anterior;
		this.id_cap_articulo_anterior = id_cap_articulo_anterior;
		this.nom_id_cap_articulo_anterior = nom_id_cap_articulo_anterior;
		this.tip_respaldo_anterior = tip_respaldo_anterior;
		this.nom_tip_respaldo_anterior = nom_tip_respaldo_anterior;
		this.id_contenedor_anterior = id_contenedor_anterior;
		this.id_ubicacion_anterior = id_ubicacion_anterior;
		this.nom_ubicacion_anterior = nom_ubicacion_anterior;
		this.hora_llegada_custodia_anterior = hora_llegada_custodia_anterior;
		this.hora_salida_custodia_anterior = hora_salida_custodia_anterior;
		this.remesa_ingreso_custodia_anterior = remesa_ingreso_custodia_anterior;
		this.remesa_salida_custodia_anterior = remesa_salida_custodia_anterior;
		this.cod_articulo_actual = cod_articulo_actual;
		this.des_articulo_actual = des_articulo_actual;
		this.id_cat_articulo_actual = id_cat_articulo_actual;
		this.nom_cat_articulo_actual = nom_cat_articulo_actual;
		this.si_ing_fec_inicio_fin_actual = si_ing_fec_inicio_fin_actual;
		this.es_fecha_actual = es_fecha_actual;
		this.id_fec_respaldo_actual = id_fec_respaldo_actual;
		this.nom_id_fec_respaldo_actual = nom_id_fec_respaldo_actual;
		this.fec_inicio_actual = fec_inicio_actual;
		this.fec_fin_actual = fec_fin_actual;
		this.id_cap_articulo_actual = id_cap_articulo_actual;
		this.nom_id_cap_articulo_actual = nom_id_cap_articulo_actual;
		this.tip_respaldo_actual = tip_respaldo_actual;
		this.nom_tip_respaldo_actual = nom_tip_respaldo_actual;
		this.id_contenedor_actual = id_contenedor_actual;
		this.id_ubicacion_actual = id_ubicacion_actual;
		this.nom_ubicacion_actual = nom_ubicacion_actual;
		this.hora_llegada_custodia_actual = hora_llegada_custodia_actual;
		this.hora_salida_custodia_actual = hora_salida_custodia_actual;
		this.remesa_ingreso_custodia_actual = remesa_ingreso_custodia_actual;
		this.remesa_salida_custodia_actual = remesa_salida_custodia_actual;
		this.revision_1 = revision_1;
		this.revision_2 = revision_2;
		this.revision_3 = revision_3;
		this.actualiza_inventario = actualiza_inventario;
		this.est_detalle_movimiento = est_detalle_movimiento;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_detalle_movimiento
	 */
	public long getId_detalle_movimiento() {
		return id_detalle_movimiento;
	}

	/**
	 * @param id_detalle_movimiento the id_detalle_movimiento to set
	 */
	public void setId_detalle_movimiento(long id_detalle_movimiento) {
		this.id_detalle_movimiento = id_detalle_movimiento;
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
	 * @return the cod_articulo_anterior
	 */
	public String getCod_articulo_anterior() {
		return cod_articulo_anterior;
	}

	/**
	 * @param cod_articulo_anterior the cod_articulo_anterior to set
	 */
	public void setCod_articulo_anterior(String cod_articulo_anterior) {
		this.cod_articulo_anterior = cod_articulo_anterior;
	}

	/**
	 * @return the des_articulo_anterior
	 */
	public String getDes_articulo_anterior() {
		return des_articulo_anterior;
	}

	/**
	 * @param des_articulo_anterior the des_articulo_anterior to set
	 */
	public void setDes_articulo_anterior(String des_articulo_anterior) {
		this.des_articulo_anterior = des_articulo_anterior;
	}

	/**
	 * @return the id_cat_articulo_anterior
	 */
	public long getId_cat_articulo_anterior() {
		return id_cat_articulo_anterior;
	}

	/**
	 * @param id_cat_articulo_anterior the id_cat_articulo_anterior to set
	 */
	public void setId_cat_articulo_anterior(long id_cat_articulo_anterior) {
		this.id_cat_articulo_anterior = id_cat_articulo_anterior;
	}

	/**
	 * @return the nom_cat_articulo_anterior
	 */
	public String getNom_cat_articulo_anterior() {
		return nom_cat_articulo_anterior;
	}

	/**
	 * @param nom_cat_articulo_anterior the nom_cat_articulo_anterior to set
	 */
	public void setNom_cat_articulo_anterior(String nom_cat_articulo_anterior) {
		this.nom_cat_articulo_anterior = nom_cat_articulo_anterior;
	}

	/**
	 * @return the si_ing_fec_inicio_fin_anterior
	 */
	public String getSi_ing_fec_inicio_fin_anterior() {
		return si_ing_fec_inicio_fin_anterior;
	}

	/**
	 * @param si_ing_fec_inicio_fin_anterior the si_ing_fec_inicio_fin_anterior to
	 *                                       set
	 */
	public void setSi_ing_fec_inicio_fin_anterior(String si_ing_fec_inicio_fin_anterior) {
		this.si_ing_fec_inicio_fin_anterior = si_ing_fec_inicio_fin_anterior;
	}

	/**
	 * @return the es_fecha_anterior
	 */
	public String getEs_fecha_anterior() {
		return es_fecha_anterior;
	}

	/**
	 * @param es_fecha_anterior the es_fecha_anterior to set
	 */
	public void setEs_fecha_anterior(String es_fecha_anterior) {
		this.es_fecha_anterior = es_fecha_anterior;
	}

	/**
	 * @return the id_fec_respaldo_anterior
	 */
	public long getId_fec_respaldo_anterior() {
		return id_fec_respaldo_anterior;
	}

	/**
	 * @param id_fec_respaldo_anterior the id_fec_respaldo_anterior to set
	 */
	public void setId_fec_respaldo_anterior(long id_fec_respaldo_anterior) {
		this.id_fec_respaldo_anterior = id_fec_respaldo_anterior;
	}

	/**
	 * @return the nom_id_fec_respaldo_anterior
	 */
	public String getNom_id_fec_respaldo_anterior() {
		return nom_id_fec_respaldo_anterior;
	}

	/**
	 * @param nom_id_fec_respaldo_anterior the nom_id_fec_respaldo_anterior to set
	 */
	public void setNom_id_fec_respaldo_anterior(String nom_id_fec_respaldo_anterior) {
		this.nom_id_fec_respaldo_anterior = nom_id_fec_respaldo_anterior;
	}

	/**
	 * @return the fec_inicio_anterior
	 */
	public Timestamp getFec_inicio_anterior() {
		return fec_inicio_anterior;
	}

	/**
	 * @param fec_inicio_anterior the fec_inicio_anterior to set
	 */
	public void setFec_inicio_anterior(Timestamp fec_inicio_anterior) {
		this.fec_inicio_anterior = fec_inicio_anterior;
	}

	/**
	 * @return the fec_fin_anterior
	 */
	public Timestamp getFec_fin_anterior() {
		return fec_fin_anterior;
	}

	/**
	 * @param fec_fin_anterior the fec_fin_anterior to set
	 */
	public void setFec_fin_anterior(Timestamp fec_fin_anterior) {
		this.fec_fin_anterior = fec_fin_anterior;
	}

	/**
	 * @return the id_cap_articulo_anterior
	 */
	public long getId_cap_articulo_anterior() {
		return id_cap_articulo_anterior;
	}

	/**
	 * @param id_cap_articulo_anterior the id_cap_articulo_anterior to set
	 */
	public void setId_cap_articulo_anterior(long id_cap_articulo_anterior) {
		this.id_cap_articulo_anterior = id_cap_articulo_anterior;
	}

	/**
	 * @return the nom_id_cap_articulo_anterior
	 */
	public String getNom_id_cap_articulo_anterior() {
		return nom_id_cap_articulo_anterior;
	}

	/**
	 * @param nom_id_cap_articulo_anterior the nom_id_cap_articulo_anterior to set
	 */
	public void setNom_id_cap_articulo_anterior(String nom_id_cap_articulo_anterior) {
		this.nom_id_cap_articulo_anterior = nom_id_cap_articulo_anterior;
	}

	/**
	 * @return the tip_respaldo_anterior
	 */
	public String getTip_respaldo_anterior() {
		return tip_respaldo_anterior;
	}

	/**
	 * @param tip_respaldo_anterior the tip_respaldo_anterior to set
	 */
	public void setTip_respaldo_anterior(String tip_respaldo_anterior) {
		this.tip_respaldo_anterior = tip_respaldo_anterior;
	}

	/**
	 * @return the nom_tip_respaldo_anterior
	 */
	public String getNom_tip_respaldo_anterior() {
		return nom_tip_respaldo_anterior;
	}

	/**
	 * @param nom_tip_respaldo_anterior the nom_tip_respaldo_anterior to set
	 */
	public void setNom_tip_respaldo_anterior(String nom_tip_respaldo_anterior) {
		this.nom_tip_respaldo_anterior = nom_tip_respaldo_anterior;
	}

	/**
	 * @return the id_contenedor_anterior
	 */
	public String getId_contenedor_anterior() {
		return id_contenedor_anterior;
	}

	/**
	 * @param id_contenedor_anterior the id_contenedor_anterior to set
	 */
	public void setId_contenedor_anterior(String id_contenedor_anterior) {
		this.id_contenedor_anterior = id_contenedor_anterior;
	}

	/**
	 * @return the id_ubicacion_anterior
	 */
	public long getId_ubicacion_anterior() {
		return id_ubicacion_anterior;
	}

	/**
	 * @param id_ubicacion_anterior the id_ubicacion_anterior to set
	 */
	public void setId_ubicacion_anterior(long id_ubicacion_anterior) {
		this.id_ubicacion_anterior = id_ubicacion_anterior;
	}

	/**
	 * @return the nom_ubicacion_anterior
	 */
	public String getNom_ubicacion_anterior() {
		return nom_ubicacion_anterior;
	}

	/**
	 * @param nom_ubicacion_anterior the nom_ubicacion_anterior to set
	 */
	public void setNom_ubicacion_anterior(String nom_ubicacion_anterior) {
		this.nom_ubicacion_anterior = nom_ubicacion_anterior;
	}

	/**
	 * @return the hora_llegada_custodia_anterior
	 */
	public long getHora_llegada_custodia_anterior() {
		return hora_llegada_custodia_anterior;
	}

	/**
	 * @param hora_llegada_custodia_anterior the hora_llegada_custodia_anterior to
	 *                                       set
	 */
	public void setHora_llegada_custodia_anterior(long hora_llegada_custodia_anterior) {
		this.hora_llegada_custodia_anterior = hora_llegada_custodia_anterior;
	}

	/**
	 * @return the hora_salida_custodia_anterior
	 */
	public long getHora_salida_custodia_anterior() {
		return hora_salida_custodia_anterior;
	}

	/**
	 * @param hora_salida_custodia_anterior the hora_salida_custodia_anterior to set
	 */
	public void setHora_salida_custodia_anterior(long hora_salida_custodia_anterior) {
		this.hora_salida_custodia_anterior = hora_salida_custodia_anterior;
	}

	/**
	 * @return the remesa_ingreso_custodia_anterior
	 */
	public String getRemesa_ingreso_custodia_anterior() {
		return remesa_ingreso_custodia_anterior;
	}

	/**
	 * @param remesa_ingreso_custodia_anterior the remesa_ingreso_custodia_anterior
	 *                                         to set
	 */
	public void setRemesa_ingreso_custodia_anterior(String remesa_ingreso_custodia_anterior) {
		this.remesa_ingreso_custodia_anterior = remesa_ingreso_custodia_anterior;
	}

	/**
	 * @return the remesa_salida_custodia_anterior
	 */
	public String getRemesa_salida_custodia_anterior() {
		return remesa_salida_custodia_anterior;
	}

	/**
	 * @param remesa_salida_custodia_anterior the remesa_salida_custodia_anterior to
	 *                                        set
	 */
	public void setRemesa_salida_custodia_anterior(String remesa_salida_custodia_anterior) {
		this.remesa_salida_custodia_anterior = remesa_salida_custodia_anterior;
	}

	/**
	 * @return the cod_articulo_actual
	 */
	public String getCod_articulo_actual() {
		return cod_articulo_actual;
	}

	/**
	 * @param cod_articulo_actual the cod_articulo_actual to set
	 */
	public void setCod_articulo_actual(String cod_articulo_actual) {
		this.cod_articulo_actual = cod_articulo_actual;
	}

	/**
	 * @return the des_articulo_actual
	 */
	public String getDes_articulo_actual() {
		return des_articulo_actual;
	}

	/**
	 * @param des_articulo_actual the des_articulo_actual to set
	 */
	public void setDes_articulo_actual(String des_articulo_actual) {
		this.des_articulo_actual = des_articulo_actual;
	}

	/**
	 * @return the id_cat_articulo_actual
	 */
	public long getId_cat_articulo_actual() {
		return id_cat_articulo_actual;
	}

	/**
	 * @param id_cat_articulo_actual the id_cat_articulo_actual to set
	 */
	public void setId_cat_articulo_actual(long id_cat_articulo_actual) {
		this.id_cat_articulo_actual = id_cat_articulo_actual;
	}

	/**
	 * @return the nom_cat_articulo_actual
	 */
	public String getNom_cat_articulo_actual() {
		return nom_cat_articulo_actual;
	}

	/**
	 * @param nom_cat_articulo_actual the nom_cat_articulo_actual to set
	 */
	public void setNom_cat_articulo_actual(String nom_cat_articulo_actual) {
		this.nom_cat_articulo_actual = nom_cat_articulo_actual;
	}

	/**
	 * @return the si_ing_fec_inicio_fin_actual
	 */
	public String getSi_ing_fec_inicio_fin_actual() {
		return si_ing_fec_inicio_fin_actual;
	}

	/**
	 * @param si_ing_fec_inicio_fin_actual the si_ing_fec_inicio_fin_actual to set
	 */
	public void setSi_ing_fec_inicio_fin_actual(String si_ing_fec_inicio_fin_actual) {
		this.si_ing_fec_inicio_fin_actual = si_ing_fec_inicio_fin_actual;
	}

	/**
	 * @return the es_fecha_actual
	 */
	public String getEs_fecha_actual() {
		return es_fecha_actual;
	}

	/**
	 * @param es_fecha_actual the es_fecha_actual to set
	 */
	public void setEs_fecha_actual(String es_fecha_actual) {
		this.es_fecha_actual = es_fecha_actual;
	}

	/**
	 * @return the id_fec_respaldo_actual
	 */
	public long getId_fec_respaldo_actual() {
		return id_fec_respaldo_actual;
	}

	/**
	 * @param id_fec_respaldo_actual the id_fec_respaldo_actual to set
	 */
	public void setId_fec_respaldo_actual(long id_fec_respaldo_actual) {
		this.id_fec_respaldo_actual = id_fec_respaldo_actual;
	}

	/**
	 * @return the nom_id_fec_respaldo_actual
	 */
	public String getNom_id_fec_respaldo_actual() {
		return nom_id_fec_respaldo_actual;
	}

	/**
	 * @param nom_id_fec_respaldo_actual the nom_id_fec_respaldo_actual to set
	 */
	public void setNom_id_fec_respaldo_actual(String nom_id_fec_respaldo_actual) {
		this.nom_id_fec_respaldo_actual = nom_id_fec_respaldo_actual;
	}

	/**
	 * @return the fec_inicio_actual
	 */
	public Timestamp getFec_inicio_actual() {
		return fec_inicio_actual;
	}

	/**
	 * @param fec_inicio_actual the fec_inicio_actual to set
	 */
	public void setFec_inicio_actual(Timestamp fec_inicio_actual) {
		this.fec_inicio_actual = fec_inicio_actual;
	}

	/**
	 * @return the fec_fin_actual
	 */
	public Timestamp getFec_fin_actual() {
		return fec_fin_actual;
	}

	/**
	 * @param fec_fin_actual the fec_fin_actual to set
	 */
	public void setFec_fin_actual(Timestamp fec_fin_actual) {
		this.fec_fin_actual = fec_fin_actual;
	}

	/**
	 * @return the id_cap_articulo_actual
	 */
	public long getId_cap_articulo_actual() {
		return id_cap_articulo_actual;
	}

	/**
	 * @param id_cap_articulo_actual the id_cap_articulo_actual to set
	 */
	public void setId_cap_articulo_actual(long id_cap_articulo_actual) {
		this.id_cap_articulo_actual = id_cap_articulo_actual;
	}

	/**
	 * @return the nom_id_cap_articulo_actual
	 */
	public String getNom_id_cap_articulo_actual() {
		return nom_id_cap_articulo_actual;
	}

	/**
	 * @param nom_id_cap_articulo_actual the nom_id_cap_articulo_actual to set
	 */
	public void setNom_id_cap_articulo_actual(String nom_id_cap_articulo_actual) {
		this.nom_id_cap_articulo_actual = nom_id_cap_articulo_actual;
	}

	/**
	 * @return the tip_respaldo_actual
	 */
	public String getTip_respaldo_actual() {
		return tip_respaldo_actual;
	}

	/**
	 * @param tip_respaldo_actual the tip_respaldo_actual to set
	 */
	public void setTip_respaldo_actual(String tip_respaldo_actual) {
		this.tip_respaldo_actual = tip_respaldo_actual;
	}

	/**
	 * @return the nom_tip_respaldo_actual
	 */
	public String getNom_tip_respaldo_actual() {
		return nom_tip_respaldo_actual;
	}

	/**
	 * @param nom_tip_respaldo_actual the nom_tip_respaldo_actual to set
	 */
	public void setNom_tip_respaldo_actual(String nom_tip_respaldo_actual) {
		this.nom_tip_respaldo_actual = nom_tip_respaldo_actual;
	}

	/**
	 * @return the id_contenedor_actual
	 */
	public String getId_contenedor_actual() {
		return id_contenedor_actual;
	}

	/**
	 * @param id_contenedor_actual the id_contenedor_actual to set
	 */
	public void setId_contenedor_actual(String id_contenedor_actual) {
		this.id_contenedor_actual = id_contenedor_actual;
	}

	/**
	 * @return the id_ubicacion_actual
	 */
	public long getId_ubicacion_actual() {
		return id_ubicacion_actual;
	}

	/**
	 * @param id_ubicacion_actual the id_ubicacion_actual to set
	 */
	public void setId_ubicacion_actual(long id_ubicacion_actual) {
		this.id_ubicacion_actual = id_ubicacion_actual;
	}

	/**
	 * @return the nom_ubicacion_actual
	 */
	public String getNom_ubicacion_actual() {
		return nom_ubicacion_actual;
	}

	/**
	 * @param nom_ubicacion_actual the nom_ubicacion_actual to set
	 */
	public void setNom_ubicacion_actual(String nom_ubicacion_actual) {
		this.nom_ubicacion_actual = nom_ubicacion_actual;
	}

	/**
	 * @return the hora_llegada_custodia_actual
	 */
	public long getHora_llegada_custodia_actual() {
		return hora_llegada_custodia_actual;
	}

	/**
	 * @param hora_llegada_custodia_actual the hora_llegada_custodia_actual to set
	 */
	public void setHora_llegada_custodia_actual(long hora_llegada_custodia_actual) {
		this.hora_llegada_custodia_actual = hora_llegada_custodia_actual;
	}

	/**
	 * @return the hora_salida_custodia_actual
	 */
	public long getHora_salida_custodia_actual() {
		return hora_salida_custodia_actual;
	}

	/**
	 * @param hora_salida_custodia_actual the hora_salida_custodia_actual to set
	 */
	public void setHora_salida_custodia_actual(long hora_salida_custodia_actual) {
		this.hora_salida_custodia_actual = hora_salida_custodia_actual;
	}

	/**
	 * @return the remesa_ingreso_custodia_actual
	 */
	public String getRemesa_ingreso_custodia_actual() {
		return remesa_ingreso_custodia_actual;
	}

	/**
	 * @param remesa_ingreso_custodia_actual the remesa_ingreso_custodia_actual to
	 *                                       set
	 */
	public void setRemesa_ingreso_custodia_actual(String remesa_ingreso_custodia_actual) {
		this.remesa_ingreso_custodia_actual = remesa_ingreso_custodia_actual;
	}

	/**
	 * @return the remesa_salida_custodia_actual
	 */
	public String getRemesa_salida_custodia_actual() {
		return remesa_salida_custodia_actual;
	}

	/**
	 * @param remesa_salida_custodia_actual the remesa_salida_custodia_actual to set
	 */
	public void setRemesa_salida_custodia_actual(String remesa_salida_custodia_actual) {
		this.remesa_salida_custodia_actual = remesa_salida_custodia_actual;
	}

	/**
	 * @return the revision_1
	 */
	public String getRevision_1() {
		return revision_1;
	}

	/**
	 * @param revision_1 the revision_1 to set
	 */
	public void setRevision_1(String revision_1) {
		this.revision_1 = revision_1;
	}

	/**
	 * @return the revision_2
	 */
	public String getRevision_2() {
		return revision_2;
	}

	/**
	 * @param revision_2 the revision_2 to set
	 */
	public void setRevision_2(String revision_2) {
		this.revision_2 = revision_2;
	}

	/**
	 * @return the revision_3
	 */
	public String getRevision_3() {
		return revision_3;
	}

	/**
	 * @param revision_3 the revision_3 to set
	 */
	public void setRevision_3(String revision_3) {
		this.revision_3 = revision_3;
	}

	/**
	 * @return the actualiza_inventario
	 */
	public String getActualiza_inventario() {
		return actualiza_inventario;
	}

	/**
	 * @param actualiza_inventario the actualiza_inventario to set
	 */
	public void setActualiza_inventario(String actualiza_inventario) {
		this.actualiza_inventario = actualiza_inventario;
	}

	/**
	 * @return the est_detalle_movimiento
	 */
	public String getEst_detalle_movimiento() {
		return est_detalle_movimiento;
	}

	/**
	 * @param est_detalle_movimiento the est_detalle_movimiento to set
	 */
	public void setEst_detalle_movimiento(String est_detalle_movimiento) {
		this.est_detalle_movimiento = est_detalle_movimiento;
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
		return "modelo_movimiento_detalle_dn [id_detalle_movimiento=" + id_detalle_movimiento + ", id_movimiento="
				+ id_movimiento + ", id_articulo=" + id_articulo + ", cod_articulo_anterior=" + cod_articulo_anterior
				+ ", des_articulo_anterior=" + des_articulo_anterior + ", id_cat_articulo_anterior="
				+ id_cat_articulo_anterior + ", nom_cat_articulo_anterior=" + nom_cat_articulo_anterior
				+ ", si_ing_fec_inicio_fin_anterior=" + si_ing_fec_inicio_fin_anterior + ", es_fecha_anterior="
				+ es_fecha_anterior + ", id_fec_respaldo_anterior=" + id_fec_respaldo_anterior
				+ ", nom_id_fec_respaldo_anterior=" + nom_id_fec_respaldo_anterior + ", fec_inicio_anterior="
				+ fec_inicio_anterior + ", fec_fin_anterior=" + fec_fin_anterior + ", id_cap_articulo_anterior="
				+ id_cap_articulo_anterior + ", nom_id_cap_articulo_anterior=" + nom_id_cap_articulo_anterior
				+ ", tip_respaldo_anterior=" + tip_respaldo_anterior + ", nom_tip_respaldo_anterior="
				+ nom_tip_respaldo_anterior + ", id_contenedor_anterior=" + id_contenedor_anterior
				+ ", id_ubicacion_anterior=" + id_ubicacion_anterior + ", nom_ubicacion_anterior="
				+ nom_ubicacion_anterior + ", hora_llegada_custodia_anterior=" + hora_llegada_custodia_anterior
				+ ", hora_salida_custodia_anterior=" + hora_salida_custodia_anterior
				+ ", remesa_ingreso_custodia_anterior=" + remesa_ingreso_custodia_anterior
				+ ", remesa_salida_custodia_anterior=" + remesa_salida_custodia_anterior + ", cod_articulo_actual="
				+ cod_articulo_actual + ", des_articulo_actual=" + des_articulo_actual + ", id_cat_articulo_actual="
				+ id_cat_articulo_actual + ", nom_cat_articulo_actual=" + nom_cat_articulo_actual
				+ ", si_ing_fec_inicio_fin_actual=" + si_ing_fec_inicio_fin_actual + ", es_fecha_actual="
				+ es_fecha_actual + ", id_fec_respaldo_actual=" + id_fec_respaldo_actual
				+ ", nom_id_fec_respaldo_actual=" + nom_id_fec_respaldo_actual + ", fec_inicio_actual="
				+ fec_inicio_actual + ", fec_fin_actual=" + fec_fin_actual + ", id_cap_articulo_actual="
				+ id_cap_articulo_actual + ", nom_id_cap_articulo_actual=" + nom_id_cap_articulo_actual
				+ ", tip_respaldo_actual=" + tip_respaldo_actual + ", nom_tip_respaldo_actual="
				+ nom_tip_respaldo_actual + ", id_contenedor_actual=" + id_contenedor_actual + ", id_ubicacion_actual="
				+ id_ubicacion_actual + ", nom_ubicacion_actual=" + nom_ubicacion_actual
				+ ", hora_llegada_custodia_actual=" + hora_llegada_custodia_actual + ", hora_salida_custodia_actual="
				+ hora_salida_custodia_actual + ", remesa_ingreso_custodia_actual=" + remesa_ingreso_custodia_actual
				+ ", remesa_salida_custodia_actual=" + remesa_salida_custodia_actual + ", revision_1=" + revision_1
				+ ", revision_2=" + revision_2 + ", revision_3=" + revision_3 + ", actualiza_inventario="
				+ actualiza_inventario + ", est_detalle_movimiento=" + est_detalle_movimiento + ", usu_ingresa="
				+ usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica="
				+ fec_modifica + "]";
	}

	public String mostrarTckMovimiento() {
		return "CT-" + getTck_movimiento();
	}

	public String mostrarFechaMovimiento() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_movimiento());
		return s;
	}

	public String mostrarFechaSolicitud() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_solicitud());
		return s;
	}

	public String mostrarFechaRespuesta() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_respuesta());
		return s;
	}

	public String mostrarFechaEjecucion() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_ejecucion());
		return s;
	}

	public String mostrarHoraSolicitud() {
		Date d = new Date(getHor_solicitud());
		String s = new SimpleDateFormat("HH:mm").format(d);
		return s;
	}

	public String mostrarHoraRespuesta() {
		Date d = new Date(getHor_respuesta());
		String s = new SimpleDateFormat("HH:mm").format(d);
		return s;
	}

	public String mostrarHoraEjecucion() {
		Date d = new Date(getHor_ejecucion());
		String s = new SimpleDateFormat("HH:mm").format(d);
		return s;
	}

	public String mostrarFechaInicioAnterior() {
		String s = "";
		if (getSi_ing_fec_inicio_fin_anterior().equals("S")) {
			if (getEs_fecha_anterior().equals("S")) {
				if (getFec_inicio_anterior() != null) {
					s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_inicio_anterior());
				}
			} else {
				if (getId_fec_respaldo_anterior() != 0) {
					s = getNom_id_fec_respaldo_anterior();
				}
			}
		}
		return s;
	}

	public String mostrarFechaFinAnterior() {
		String s = "";
		if (getSi_ing_fec_inicio_fin_anterior().equals("S")) {
			if (getEs_fecha_anterior().equals("S")) {
				if (getFec_fin_anterior() != null) {
					s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_fin_anterior());
				}
			}
		}
		return s;
	}

	public String mostrarFechaInicioActual() {
		String s = "";
		if (getSi_ing_fec_inicio_fin_actual().equals("S")) {
			if (getEs_fecha_actual().equals("S")) {
				if (getFec_inicio_actual() != null) {
					s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_inicio_actual());
				}
			} else {
				if (getId_fec_respaldo_actual() != 0) {
					s = getNom_id_fec_respaldo_actual();
				}
			}
		}
		return s;
	}

	public String mostrarFechaFinActual() {
		String s = "";
		if (getSi_ing_fec_inicio_fin_actual().equals("S")) {
			if (getEs_fecha_actual().equals("S")) {
				if (getFec_fin_actual() != null) {
					s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_fin_actual());
				}
			}
		}
		return s;
	}

	public String mostrarHoraIngresoCustodiaAnterior() {
		String s;
		if (getHora_llegada_custodia_anterior() == 0) {
			s = "";
		} else {
			Date d = new Date(getHora_llegada_custodia_anterior());
			s = new SimpleDateFormat("HH:mm").format(d);
		}
		return s;
	}

	public String mostrarHoraSalidaCustodiaAnterior() {
		String s;
		if (getHora_salida_custodia_anterior() == 0) {
			s = "";
		} else {
			Date d = new Date(getHora_salida_custodia_anterior());
			s = new SimpleDateFormat("HH:mm").format(d);
		}
		return s;
	}

	public String mostrarHoraIngresoCustodiaActual() {
		String s;
		if (getHora_llegada_custodia_actual() == 0) {
			s = "";
		} else {
			Date d = new Date(getHora_llegada_custodia_actual());
			s = new SimpleDateFormat("HH:mm").format(d);
		}
		return s;
	}

	public String mostrarHoraSalidaCustodiaActual() {
		String s;
		if (getHora_salida_custodia_actual() == 0) {
			s = "";
		} else {
			Date d = new Date(getHora_salida_custodia_actual());
			s = new SimpleDateFormat("HH:mm").format(d);
		}
		return s;
	}

	public String mostrarTipoPedido() {
		if (getTip_pedido().equals("M")) {
			return "MOVIMIENTO";
		} else {
			return "REQUERIMIENTO";
		}
	}

	public String mostrarEstado() {
		if (getEst_movimiento().equals("E")) {
			return "EJECUTADO";
		} else {
			return "NO EJECUTADO";
		}
	}

	public String mostrarUbicacionAnterior() {
		String ubicacion = "";
		if (getNom_ubicacion_anterior() != null) {
			ubicacion = getNom_ubicacion_anterior();
		}
		return ubicacion;
	}

	public String mostrarUbicacionActual() {
		String ubicacion = "";
		if (getNom_ubicacion_actual() != null) {
			ubicacion = getNom_ubicacion_actual();
		}
		return ubicacion;
	}

	public String mostrarTipoRespaldoAnterior() {
		String tipo_respaldo = "";
		if (getNom_tip_respaldo_anterior() != null) {
			tipo_respaldo = getNom_tip_respaldo_anterior();
		}
		return tipo_respaldo;
	}

	public String mostrarTipoRespaldoActual() {
		String tipo_respaldo = "";
		if (getNom_tip_respaldo_actual() != null) {
			tipo_respaldo = getNom_tip_respaldo_actual();
		}
		return tipo_respaldo;
	}

	public String mostrarIdContenedorAnterior() {
		String id_contenedor = "";
		if (getId_contenedor_anterior() != null) {
			id_contenedor = getId_contenedor_anterior();
		}
		return id_contenedor;
	}

	public String mostrarIdContenedorActual() {
		String id_contenedor = "";
		if (getId_contenedor_actual() != null) {
			id_contenedor = getId_contenedor_actual();
		}
		return id_contenedor;
	}

	public String mostrarRemesaIngresoAnterior() {
		String remesa = "";
		if (getRemesa_ingreso_custodia_anterior() != null) {
			remesa = getRemesa_ingreso_custodia_anterior();
		}
		return remesa;
	}

	public String mostrarRemesaSalidaAnterior() {
		String remesa = "";
		if (getRemesa_salida_custodia_anterior() != null) {
			remesa = getRemesa_salida_custodia_anterior();
		}
		return remesa;
	}

	public String mostrarRemesaIngresoActual() {
		String remesa = "";
		if (getRemesa_ingreso_custodia_actual() != null) {
			remesa = getRemesa_ingreso_custodia_actual();
		}
		return remesa;
	}

	public String mostrarRemesaSalidaActual() {
		String remesa = "";
		if (getRemesa_salida_custodia_actual() != null) {
			remesa = getRemesa_salida_custodia_actual();
		}
		return remesa;
	}

	public String mostrarActualizaInventario() {
		String actualiza_inventario = "";
		if (getActualiza_inventario().equals("S")) {
			actualiza_inventario = "SI";
		} else {
			actualiza_inventario = "NO";
		}
		return actualiza_inventario;
	}

	/* Imagenes */

	public String mostrarImagenUbicacionAnterior() {
		String imagen = "";
		if (getNom_ubicacion_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenUbicacionActual() {
		String imagen = "";
		if (getNom_ubicacion_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenTipoRespaldoAnterior() {
		String imagen = "";
		if (getNom_tip_respaldo_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenTipoRespaldoActual() {
		String imagen = "";
		if (getNom_tip_respaldo_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenIdContenedorAnterior() {
		String imagen = "";
		if (getId_contenedor_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getId_contenedor_anterior().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenIdContenedorActual() {
		String imagen = "";
		if (getId_contenedor_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getId_contenedor_actual().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaInicioAnterior() {
		String imagen = "";
		if (getSi_ing_fec_inicio_fin_anterior().equals("S")) {
			if (getEs_fecha_anterior().equals("S")) {
				if (getFec_inicio_anterior() == null) {
					imagen = "/img/botones/ButtonError.png";
				}
			} else {
				if (getId_fec_respaldo_anterior() == 0) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		} else {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaFinAnterior() {
		String imagen = "";
		if (getFec_fin_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaInicioActual() {
		String imagen = "";
		if (getSi_ing_fec_inicio_fin_actual().equals("S")) {
			if (getEs_fecha_actual().equals("S")) {
				if (getFec_inicio_actual() == null) {
					imagen = "/img/botones/ButtonError.png";
				}
			} else {
				if (getId_fec_respaldo_actual() == 0) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		} else {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaFinActual() {
		String imagen = "";
		if (getFec_fin_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenHoraIngresoCustodiaAnterior() {
		String imagen = "";
		if (getHora_llegada_custodia_anterior() == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenHoraSalidaCustodiaAnterior() {
		String imagen = "";
		if (getHora_salida_custodia_anterior() == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenHoraIngresoCustodiaActual() {
		String imagen = "";
		if (getHora_llegada_custodia_actual() == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenHoraSalidaCustodiaActual() {
		String imagen = "";
		if (getHora_salida_custodia_actual() == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenRemesaIngresoAnterior() {
		String imagen = "";
		if (getRemesa_ingreso_custodia_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getRemesa_ingreso_custodia_anterior().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenRemesaSalidaAnterior() {
		String imagen = "";
		if (getRemesa_salida_custodia_anterior() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getRemesa_salida_custodia_anterior().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenRemesaIngresoActual() {
		String imagen = "";
		if (getRemesa_ingreso_custodia_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getRemesa_ingreso_custodia_actual().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenRemesaSalidaActual() {
		String imagen = "";
		if (getRemesa_salida_custodia_actual() == null) {
			imagen = "/img/botones/ButtonError.png";
		} else if (getRemesa_salida_custodia_actual().length() <= 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	/* Estilos */

	public String estiloImagenUbicacionAnterior() {
		String estilo = "";
		if (getNom_ubicacion_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenUbicacionActual() {
		String estilo = "";
		if (getNom_ubicacion_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenTipoRespaldoAnterior() {
		String estilo = "";
		if (getNom_tip_respaldo_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenTipoRespaldoActual() {
		String estilo = "";
		if (getNom_tip_respaldo_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenIdContenedorAnterior() {
		String estilo = "text-align: center !important;";
		if (getId_contenedor_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getId_contenedor_anterior().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenIdContenedorActual() {
		String estilo = "text-align: center !important;";
		if (getId_contenedor_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getId_contenedor_actual().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenFechaInicioAnterior() {
		String estilo = "text-align: center !important;";
		if (getSi_ing_fec_inicio_fin_anterior().equals("S")) {
			if (getEs_fecha_anterior().equals("S")) {
				if (getFec_inicio_anterior() == null) {
					estilo = "text-align: center !important; color: transparent;";
				}
			} else {
				if (getId_fec_respaldo_anterior() == 0) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
		}
		return estilo;
	}

	public String estiloImagenFechaInicioActual() {
		String estilo = "text-align: center !important;";
		if (getSi_ing_fec_inicio_fin_actual().equals("S")) {
			if (getEs_fecha_actual().equals("S")) {
				if (getFec_inicio_actual() == null) {
					estilo = "text-align: center !important; color: transparent;";
				}
			} else {
				if (getId_fec_respaldo_actual() == 0) {
					estilo = "text-align: center !important; color: transparent;";
				}
			}
		}
		return estilo;
	}

	public String estiloImagenFechaFinAnterior() {
		String estilo = "text-align: center !important;";
		if (getFec_fin_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenFechaFinActual() {
		String estilo = "text-align: center !important;";
		if (getFec_fin_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenHoraIngresoCustodiaAnterior() {
		String estilo = "text-align: center !important;";
		if (getHora_llegada_custodia_anterior() == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenHoraSalidaCustodiaAnterior() {
		String estilo = "text-align: center !important;";
		if (getHora_salida_custodia_anterior() == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenHoraIngresoCustodiaActual() {
		String estilo = "text-align: center !important;";
		if (getHora_llegada_custodia_actual() == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenHoraSalidaCustodiaActual() {
		String estilo = "text-align: center !important;";
		if (getHora_salida_custodia_actual() == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenRemesaIngresoAnterior() {
		String estilo = "text-align: center !important;";
		if (getRemesa_ingreso_custodia_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getRemesa_ingreso_custodia_anterior().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenRemesaSalidaAnterior() {
		String estilo = "text-align: center !important;";
		if (getRemesa_salida_custodia_anterior() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getRemesa_salida_custodia_anterior().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenRemesaIngresoActual() {
		String estilo = "text-align: center !important;";
		if (getRemesa_ingreso_custodia_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getRemesa_ingreso_custodia_actual().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenRemesaSalidaActual() {
		String estilo = "text-align: center !important;";
		if (getRemesa_salida_custodia_actual() == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else if (getRemesa_salida_custodia_actual().length() <= 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloActualizaInventario() {
		String estilo = "";
		if (getActualiza_inventario().equals("S")) {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #C8FFC8 !important; text-align: center !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #FFE579 !important; text-align: center !important;";
		}
		return estilo;
	}

	public String estiloMostrarCategoria() {
		String estilo = "";
		if (id_cat_articulo_actual == 1) {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #d6ffba; text-align: center !important;";
		} else if (id_cat_articulo_actual == 2) {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #d4efed; text-align: center !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #ffbd62; text-align: center !important;";
		}
		return estilo;
	}

}
