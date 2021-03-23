package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.cintas.modelo.modelo_movimiento_detalle_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
//import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtIdAnterior, txtCodigoAnterior, txtDescripcionAnterior, txtIDContenedorAnterior,
			txtBuscarUbicacionAnterior, txtRemesaIngresoAnterior, txtRemesaSalidaAnterior, txtIdActual, txtCodigoActual,
			txtDescripcionActual, txtIDContenedorActual, txtBuscarUbicacionActual, txtRemesaIngresoActual,
			txtRemesaSalidaActual;
	@Wire
	Combobox cmbRespaldoAnterior, cmbRespaldoAnterior1, cmbRespaldoActual, cmbRespaldoActual1, cmbActualizaInventario;
	@Wire
	Row rwIngresaFechaAnterior, rwEsFechaAnterior, rwFechaRespaldoAnterior, rwFechaInicioAnterior, rwFechaFinAnterior,
			rwTipoRespaldoAnterior, rwIDContenedorAnterior, rwIngresaFechaActual, rwEsFechaActual,
			rwFechaRespaldoActual, rwFechaInicioActual, rwFechaFinActual, rwTipoRespaldoActual, rwIDContenedorActual;
	@Wire
	Checkbox chkIngresaFechaAnterior, chkEsFechaAnterior, chkIngresaFechaActual, chkEsFechaActual;
	@Wire
	Listbox lbxUbicacionesAnterior, lbxUbicacionesActual;
	@Wire
	Bandbox bdxUbicacionAnterior, bdxUbicacionActual;
	@Wire
	Datebox dtxFechaInicioAnterior, dtxFechaFinAnterior, dtxFechaInicioActual, dtxFechaFinActual;
	@Wire
	Timebox tmxHoraLlegadaAnterior, tmxHoraSalidaAnterior, tmxHoraLlegadaActual, tmxHoraSalidaActual;

	String vfechainicioAnterior, vfechafinAnterior, vtiporespaldoAnterior, vidcontenedorAnterior, vfechainicioActual,
			vfechafinActual, vtiporespaldoActual, vidcontenedorActual;

	List<modelo_categoria_dn> listaCategoriaAnterior = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacionAnterior = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_respaldo> listaRespaldoAnterior = new ArrayList<modelo_respaldo>();
	List<modelo_respaldo> listaRespaldoAnterior1 = new ArrayList<modelo_respaldo>();
	List<modelo_categoria_dn> listaCategoriaActual = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacionActual = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_respaldo> listaRespaldoActual = new ArrayList<modelo_respaldo>();
	List<modelo_respaldo> listaRespaldoActual1 = new ArrayList<modelo_respaldo>();

	long id = 0;

	/*
	 * Se crean variables para almacenar los valores iniciales para las consultas
	 * posteriores
	 */
	long id_ubicacion_inicial_anterior = 0;
	String codigo_inicial_anterior = "";
	String fecha_inicio_inicial_anterior = "";
	String fecha_fin_inicial_anterior = "";

	long id_ubicacion_inicial_actual = 0;
	String codigo_inicial_actual = "";
	String fecha_inicio_inicial_actual = "";
	String fecha_fin_inicial_actual = "";

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	modelo_movimiento_detalle_dn movimiento_detalle = (modelo_movimiento_detalle_dn) Sessions.getCurrent()
			.getAttribute("movimiento_detalle_1");

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	private Informativos informativos = new Informativos();
	// private Error error = new Error();
	private Validaciones validaciones = new Validaciones();

	private Fechas fechas = new Fechas();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Sessions.getCurrent().removeAttribute("movimiento_detalle");
		lbxUbicacionesAnterior.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtCodigoAnterior.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoAnterior.setText(txtCodigoAnterior.getText().toUpperCase());
			}
		});
		txtDescripcionAnterior.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcionAnterior.setText(txtDescripcionAnterior.getText().toUpperCase());
			}
		});
		txtIDContenedorAnterior.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtIDContenedorAnterior.setText(txtIDContenedorAnterior.getText().toUpperCase());
			}
		});
		lbxUbicacionesActual.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtCodigoActual.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoActual.setText(txtCodigoActual.getText().toUpperCase());
			}
		});
		txtDescripcionActual.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcionActual.setText(txtDescripcionActual.getText().toUpperCase());
			}
		});
		txtIDContenedorActual.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtIDContenedorActual.setText(txtIDContenedorActual.getText().toUpperCase());
			}
		});
		cmbActualizaInventario.setSelectedIndex(0);
		inicializarListas();
		cargarDatosAnterior();
		cargarDatosActual();
		desactivarCamposDeDatosAnteriores();
	}

	public void desactivarCamposDeDatosAnteriores() {
		txtCodigoAnterior.setDisabled(true);
		txtDescripcionAnterior.setDisabled(true);
		txtBuscarUbicacionAnterior.setDisabled(true);
		txtIDContenedorAnterior.setDisabled(true);
		txtRemesaIngresoAnterior.setDisabled(true);
		txtRemesaSalidaAnterior.setDisabled(true);
		cmbRespaldoAnterior.setDisabled(true);
		cmbRespaldoAnterior1.setDisabled(true);
		chkEsFechaAnterior.setDisabled(true);
		chkIngresaFechaAnterior.setDisabled(true);
		bdxUbicacionAnterior.setDisabled(true);
		tmxHoraLlegadaAnterior.setDisabled(true);
		tmxHoraSalidaAnterior.setDisabled(true);
		dtxFechaInicioAnterior.setDisabled(true);
		dtxFechaFinAnterior.setDisabled(true);
	}

	public List<modelo_categoria_dn> obtenerCategoriasAnterior() {
		return listaCategoriaAnterior;
	}

	public List<modelo_ubicacion_dn> obtenerUbicacionesAnterior() {
		return listaUbicacionAnterior;
	}

	public List<modelo_respaldo> obtenerRespaldosAnterior() {
		return listaRespaldoAnterior;
	}

	public List<modelo_respaldo> obtenerRespaldosAnterior1() {
		return listaRespaldoAnterior1;
	}

	public List<modelo_categoria_dn> obtenerCategoriasActual() {
		return listaCategoriaActual;
	}

	public List<modelo_ubicacion_dn> obtenerUbicacionesActual() {
		return listaUbicacionActual;
	}

	public List<modelo_respaldo> obtenerRespaldosActual() {
		return listaRespaldoActual;
	}

	public List<modelo_respaldo> obtenerRespaldosActual1() {
		return listaRespaldoActual1;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaCategoriaAnterior = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 10);
		listaRespaldoAnterior = consultasABaseDeDatos.cargarRespaldosDN("", 6, "", "", 0);
		listaRespaldoAnterior1 = consultasABaseDeDatos.cargarRespaldosDN("", 7, "", "", 0);
		listaUbicacionAnterior = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
		listaCategoriaActual = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 10);
		listaRespaldoActual = consultasABaseDeDatos.cargarRespaldosDN("", 6, "", "", 0);
		listaRespaldoActual1 = consultasABaseDeDatos.cargarRespaldosDN("", 7, "", "", 0);
		listaUbicacionActual = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
		binder.loadComponent(cmbRespaldoAnterior);
		binder.loadComponent(cmbRespaldoAnterior1);
		binder.loadComponent(lbxUbicacionesAnterior);
		binder.loadComponent(cmbRespaldoActual);
		binder.loadComponent(cmbRespaldoActual1);
		binder.loadComponent(lbxUbicacionesActual);
	}

	public void cargarDatosAnterior() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * Se almacenan los valores iniciales de Ubicacion Codigo Fecha inicio Fecha fin
		 */
		id_ubicacion_inicial_anterior = movimiento_detalle.getId_ubicacion_anterior();
		codigo_inicial_anterior = movimiento_detalle.getCod_articulo_anterior();
		if (movimiento_detalle.getFec_inicio_anterior() != null) {
			fecha_inicio_inicial_anterior = fechas.obtenerFechaFormateada(movimiento_detalle.getFec_inicio_anterior(),
					"yyyy/MM/dd");
		}
		if (movimiento_detalle.getFec_fin_anterior() != null) {
			fecha_fin_inicial_anterior = fechas.obtenerFechaFormateada(movimiento_detalle.getFec_fin_anterior(),
					"yyyy/MM/dd");
		}
		txtIdAnterior.setText(String.valueOf(movimiento_detalle.getId_articulo()));
		txtCodigoAnterior.setText(movimiento_detalle.getCod_articulo_anterior());
		txtDescripcionAnterior.setText(movimiento_detalle.getDes_articulo_anterior());
		for (int i = 0; i < listaRespaldoAnterior.size(); i++) {
			if (listaRespaldoAnterior.get(i).getId_respaldo() == Long
					.valueOf(movimiento_detalle.getTip_respaldo_anterior())) {
				cmbRespaldoAnterior.setText(listaRespaldoAnterior.get(i).toNombreRespaldo());
				i = listaRespaldoAnterior.size() + 1;
			}
		}
		for (int i = 0; i < listaRespaldoAnterior1.size(); i++) {
			if (String.valueOf(listaRespaldoAnterior1.get(i).getId_respaldo())
					.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_anterior()))) {
				cmbRespaldoAnterior1.setText(listaRespaldoAnterior1.get(i).toNombreRespaldo());
				i = listaRespaldoAnterior1.size() + 1;
			}
		}
		for (int i = 0; i < listaCategoriaAnterior.size(); i++) {
			if (listaCategoriaAnterior.get(i).getId_categoria() == movimiento_detalle.getId_cat_articulo_anterior()) {
				if (listaCategoriaAnterior.get(i).getMos_fec_inicio().equals("S")) {
					rwIngresaFechaAnterior.setVisible(true);
					vfechainicioAnterior = "S";
				} else {
					rwIngresaFechaAnterior.setVisible(false);
					chkIngresaFechaAnterior.setChecked(false);
					rwFechaRespaldoAnterior.setVisible(false);
					cmbRespaldoAnterior1.setText("");
					rwEsFechaAnterior.setVisible(false);
					chkEsFechaAnterior.setChecked(false);
					rwFechaInicioAnterior.setVisible(false);
					rwFechaFinAnterior.setVisible(false);
					dtxFechaInicioAnterior.setValue(null);
					dtxFechaFinAnterior.setValue(null);
					vfechainicioAnterior = "N";
				}
				if (listaCategoriaAnterior.get(i).getMos_fec_fin().equals("S")) {
					vfechafinAnterior = "S";
				} else {
					vfechafinAnterior = "N";
				}
				if (listaCategoriaAnterior.get(i).getMos_tip_respaldo().equals("S")) {
					rwTipoRespaldoAnterior.setVisible(true);
					vtiporespaldoAnterior = "S";
				} else {
					rwTipoRespaldoAnterior.setVisible(false);
					vtiporespaldoAnterior = "N";
				}
				if (listaCategoriaAnterior.get(i).getMos_id_contenedor().equals("S")) {
					rwIDContenedorAnterior.setVisible(true);
					vidcontenedorAnterior = "S";
					if (movimiento_detalle.getId_contenedor_anterior() != null) {
						if (movimiento_detalle.getId_contenedor_anterior().length() <= 0) {
							txtIDContenedorAnterior.setText("");
						} else {
							txtIDContenedorAnterior.setText(movimiento_detalle.getId_contenedor_anterior());
						}
					} else {
						txtIDContenedorAnterior.setText("");
					}
				} else {
					rwIDContenedorAnterior.setVisible(false);
					vidcontenedorAnterior = "N";
				}
				i = listaCategoriaAnterior.size() + 1;
			}
		}
		if (movimiento_detalle.getSi_ing_fec_inicio_fin_anterior().equals("S")) {
			chkIngresaFechaAnterior.setChecked(true);
			rwEsFechaAnterior.setVisible(true);
			if (movimiento_detalle.getEs_fecha_anterior().equals("S")) {
				rwFechaRespaldoAnterior.setVisible(false);
				cmbRespaldoAnterior1.setText("");
				chkEsFechaAnterior.setChecked(true);
				rwFechaInicioAnterior.setVisible(true);
				rwFechaFinAnterior.setVisible(true);
				if (movimiento_detalle.getFec_inicio_anterior() != null) {
					dtxFechaInicioAnterior.setValue(movimiento_detalle.getFec_inicio_anterior());
				} else {
					dtxFechaInicioAnterior.setValue(null);
				}
				if (movimiento_detalle.getFec_fin_anterior() != null) {
					dtxFechaFinAnterior.setValue(movimiento_detalle.getFec_fin_anterior());
				} else {
					dtxFechaFinAnterior.setValue(null);
				}
			} else {
				rwFechaRespaldoAnterior.setVisible(true);
				cmbRespaldoAnterior1.setVisible(true);
				chkEsFechaAnterior.setChecked(false);
				rwFechaInicioAnterior.setVisible(false);
				rwFechaFinAnterior.setVisible(false);
				dtxFechaInicioAnterior.setValue(null);
				dtxFechaFinAnterior.setValue(null);
			}
		} else {
			rwFechaRespaldoAnterior.setVisible(false);
			cmbRespaldoAnterior1.setText("");
			rwEsFechaAnterior.setVisible(false);
			chkIngresaFechaAnterior.setChecked(false);
			chkEsFechaAnterior.setChecked(false);
			dtxFechaInicioAnterior.setValue(null);
			dtxFechaFinAnterior.setValue(null);
		}
		/* Seteamos la ubicacion del articulo */
		int indice = 0;
		String nombre_ubicacion = "";
		Iterator<modelo_ubicacion_dn> it = listaUbicacionAnterior.iterator();
		while (it.hasNext()) {
			modelo_ubicacion_dn modelo_ubicacion = it.next();
			if (modelo_ubicacion.getId_ubicacion() == id_ubicacion_inicial_anterior) {
				nombre_ubicacion = modelo_ubicacion.getNom_ubicacion() + " - " + modelo_ubicacion.getPos_ubicacion();
				break;
			}
			indice++;
		}
		bdxUbicacionAnterior.setText(nombre_ubicacion);
		bdxUbicacionAnterior.setTooltiptext(nombre_ubicacion);
		lbxUbicacionesAnterior.setSelectedIndex(indice);
		if (movimiento_detalle.getHora_llegada_custodia_anterior() != 0) {
			tmxHoraLlegadaAnterior
					.setValue(fechas.obtenerFechaDeUnLong(movimiento_detalle.getHora_llegada_custodia_anterior()));
		}
		if (movimiento_detalle.getHora_salida_custodia_anterior() != 0) {
			tmxHoraSalidaAnterior
					.setValue(fechas.obtenerFechaDeUnLong(movimiento_detalle.getHora_salida_custodia_anterior()));
		}
		txtRemesaIngresoAnterior.setValue(movimiento_detalle.getRemesa_ingreso_custodia_anterior());
		txtRemesaSalidaAnterior.setValue(movimiento_detalle.getRemesa_salida_custodia_anterior());
	}

	public void cargarDatosActual() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * Se almacenan los valores iniciales de Ubicacion Codigo Fecha inicio Fecha fin
		 */
		id_ubicacion_inicial_actual = movimiento_detalle.getId_ubicacion_actual();
		codigo_inicial_actual = movimiento_detalle.getCod_articulo_actual();
		if (movimiento_detalle.getFec_inicio_actual() != null) {
			fecha_inicio_inicial_actual = fechas.obtenerFechaFormateada(movimiento_detalle.getFec_inicio_actual(),
					"yyyy/MM/dd");
		}
		if (movimiento_detalle.getFec_fin_actual() != null) {
			fecha_fin_inicial_actual = fechas.obtenerFechaFormateada(movimiento_detalle.getFec_fin_actual(),
					"yyyy/MM/dd");
		}
		txtIdActual.setText(String.valueOf(movimiento_detalle.getId_articulo()));
		txtCodigoActual.setText(movimiento_detalle.getCod_articulo_actual());
		txtDescripcionActual.setText(movimiento_detalle.getDes_articulo_actual());
		for (int i = 0; i < listaRespaldoActual.size(); i++) {
			if (listaRespaldoActual.get(i).getId_respaldo() == Long
					.valueOf(movimiento_detalle.getTip_respaldo_actual())) {
				cmbRespaldoActual.setText(listaRespaldoActual.get(i).toNombreRespaldo());
				i = listaRespaldoActual.size() + 1;
			}
		}
		for (int i = 0; i < listaRespaldoActual1.size(); i++) {
			if (String.valueOf(listaRespaldoActual1.get(i).getId_respaldo())
					.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_actual()))) {
				cmbRespaldoActual1.setText(listaRespaldoActual1.get(i).toNombreRespaldo());
				i = listaRespaldoActual1.size() + 1;
			}
		}
		for (int i = 0; i < listaCategoriaActual.size(); i++) {
			if (listaCategoriaActual.get(i).getId_categoria() == movimiento_detalle.getId_cat_articulo_actual()) {
				if (listaCategoriaActual.get(i).getMos_fec_inicio().equals("S")) {
					rwIngresaFechaActual.setVisible(true);
					vfechainicioActual = "S";
				} else {
					rwIngresaFechaActual.setVisible(false);
					chkIngresaFechaActual.setChecked(false);
					rwFechaRespaldoActual.setVisible(false);
					cmbRespaldoActual1.setText("");
					rwEsFechaActual.setVisible(false);
					chkEsFechaActual.setChecked(false);
					rwFechaInicioActual.setVisible(false);
					rwFechaFinActual.setVisible(false);
					dtxFechaInicioActual.setValue(null);
					dtxFechaFinActual.setValue(null);
					vfechainicioActual = "N";
				}
				if (listaCategoriaActual.get(i).getMos_fec_fin().equals("S")) {
					vfechafinActual = "S";
				} else {
					vfechafinActual = "N";
				}
				if (listaCategoriaActual.get(i).getMos_tip_respaldo().equals("S")) {
					rwTipoRespaldoActual.setVisible(true);
					vtiporespaldoActual = "S";
				} else {
					rwTipoRespaldoActual.setVisible(false);
					vtiporespaldoActual = "N";
				}
				if (listaCategoriaActual.get(i).getMos_id_contenedor().equals("S")) {
					rwIDContenedorActual.setVisible(true);
					vidcontenedorActual = "S";
					if (movimiento_detalle.getId_contenedor_actual() != null) {
						if (movimiento_detalle.getId_contenedor_actual().length() <= 0) {
							txtIDContenedorActual.setText("");
						} else {
							txtIDContenedorActual.setText(movimiento_detalle.getId_contenedor_actual());
						}
					} else {
						txtIDContenedorActual.setText("");
					}
				} else {
					rwIDContenedorActual.setVisible(false);
					vidcontenedorActual = "N";
				}
				i = listaCategoriaActual.size() + 1;
			}
		}
		if (movimiento_detalle.getSi_ing_fec_inicio_fin_actual().equals("S")) {
			chkIngresaFechaActual.setChecked(true);
			rwEsFechaActual.setVisible(true);
			if (movimiento_detalle.getEs_fecha_actual().equals("S")) {
				rwFechaRespaldoActual.setVisible(false);
				cmbRespaldoActual1.setText("");
				chkEsFechaActual.setChecked(true);
				rwFechaInicioActual.setVisible(true);
				rwFechaFinActual.setVisible(true);
				if (movimiento_detalle.getFec_inicio_actual() != null) {
					dtxFechaInicioActual.setValue(movimiento_detalle.getFec_inicio_actual());
				} else {
					dtxFechaInicioActual.setValue(null);
				}
				if (movimiento_detalle.getFec_fin_actual() != null) {
					dtxFechaFinActual.setValue(movimiento_detalle.getFec_fin_actual());
				} else {
					dtxFechaFinActual.setValue(null);
				}
			} else {
				rwFechaRespaldoActual.setVisible(true);
				cmbRespaldoActual1.setVisible(true);
				chkEsFechaActual.setChecked(false);
				rwFechaInicioActual.setVisible(false);
				rwFechaFinActual.setVisible(false);
				dtxFechaInicioActual.setValue(null);
				dtxFechaFinActual.setValue(null);
			}
		} else {
			rwFechaRespaldoActual.setVisible(false);
			cmbRespaldoActual1.setText("");
			rwEsFechaActual.setVisible(false);
			chkIngresaFechaActual.setChecked(false);
			chkEsFechaActual.setChecked(false);
			dtxFechaInicioActual.setValue(null);
			dtxFechaFinActual.setValue(null);
		}
		/* Seteamos la ubicacion del articulo */
		int indice = 0;
		String nombre_ubicacion = "";
		Iterator<modelo_ubicacion_dn> it = listaUbicacionActual.iterator();
		while (it.hasNext()) {
			modelo_ubicacion_dn modelo_ubicacion = it.next();
			if (modelo_ubicacion.getId_ubicacion() == id_ubicacion_inicial_actual) {
				nombre_ubicacion = modelo_ubicacion.getNom_ubicacion() + " - " + modelo_ubicacion.getPos_ubicacion();
				break;
			}
			indice++;
		}
		bdxUbicacionActual.setText(nombre_ubicacion);
		bdxUbicacionActual.setTooltiptext(nombre_ubicacion);
		lbxUbicacionesActual.setSelectedIndex(indice);
		if (movimiento_detalle.getHora_llegada_custodia_actual() != 0) {
			tmxHoraLlegadaActual
					.setValue(fechas.obtenerFechaDeUnLong(movimiento_detalle.getHora_llegada_custodia_actual()));
		}
		if (movimiento_detalle.getHora_salida_custodia_actual() != 0) {
			tmxHoraSalidaActual
					.setValue(fechas.obtenerFechaDeUnLong(movimiento_detalle.getHora_salida_custodia_actual()));
		}
		txtRemesaIngresoActual.setValue(movimiento_detalle.getRemesa_ingreso_custodia_actual());
		txtRemesaSalidaActual.setValue(movimiento_detalle.getRemesa_salida_custodia_actual());
	}

	@Listen("onOK=#txtBuscarUbicacionAnterior")
	public void onOKtxtBuscarUbicacionAnterior()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxUbicacionAnterior.setText("");
		bdxUbicacionAnterior.setTooltiptext("");
		listaUbicacionAnterior = consultasABaseDeDatos
				.cargarUbicacionesDN(txtBuscarUbicacionAnterior.getText().toString(), id_dc, 6, 0, 0, 0, 10);
		lbxUbicacionesAnterior.clearSelection();
		binder.loadComponent(lbxUbicacionesAnterior);
	}

	@Listen("onSelect=#lbxUbicacionesAnterior")
	public void onSelectlbxUbicacionesAnterior()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicacionesAnterior.getSelectedItem() == null) {
			return;
		}
		int index = lbxUbicacionesAnterior.getSelectedIndex();
		bdxUbicacionAnterior.setText(listaUbicacionAnterior.get(index).getNom_ubicacion() + " - "
				+ listaUbicacionAnterior.get(index).getPos_ubicacion());
		bdxUbicacionAnterior.setTooltiptext(listaUbicacionAnterior.get(index).getNom_ubicacion() + " - "
				+ listaUbicacionAnterior.get(index).getPos_ubicacion());
	}

	@Listen("onSelect=#cmbRespaldoAnterior")
	public void onSelectcmbRespaldoAnterior() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldoAnterior.getSelectedItem() == null) {
			cmbRespaldoAnterior.setTooltiptext("");
		} else if (cmbRespaldoAnterior.getText().length() <= 0) {
			cmbRespaldoAnterior.setTooltiptext("");
		} else {
			cmbRespaldoAnterior.setTooltiptext(cmbRespaldoAnterior.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbRespaldoAnterior1")
	public void onSelectcmbRespaldoAnterior1() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldoAnterior1.getSelectedItem() == null) {
			cmbRespaldoAnterior1.setTooltiptext("");
		} else if (cmbRespaldoAnterior1.getText().length() <= 0) {
			cmbRespaldoAnterior1.setTooltiptext("");
		} else {
			cmbRespaldoAnterior1.setTooltiptext(cmbRespaldoAnterior1.getSelectedItem().getLabel());
		}
	}

	@Listen("onOK=#cmbRespaldoAnterior")
	public void onOKcmbRespaldoAnterior() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldoAnterior.getText().toString().toUpperCase();
		listaRespaldoAnterior = consultasABaseDeDatos.cargarRespaldosDN(criterio, 6, "", "", 0);
	}

	@Listen("onOK=#cmbRespaldoAnterior1")
	public void onOKcmbRespaldoAnterior1() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldoAnterior1.getText().toString().toUpperCase();
		listaRespaldoAnterior1 = consultasABaseDeDatos.cargarRespaldosDN(criterio, 7, "", "", 0);
	}

	@Listen("onCheck=#chkIngresaFechaAnterior")
	public void onCheck$chkIngresaFechaAnterior() {
		if (chkIngresaFechaAnterior.isChecked()) {
			rwFechaRespaldoAnterior.setVisible(true);
			cmbRespaldoAnterior1.setText("");
			for (int i = 0; i < listaRespaldoAnterior1.size(); i++) {
				if (String.valueOf(listaRespaldoAnterior1.get(i).getId_respaldo())
						.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_anterior()))) {
					cmbRespaldoAnterior1.setText(listaRespaldoAnterior1.get(i).toNombreRespaldo());
					i = listaRespaldoAnterior1.size() + 1;
				}
			}
			rwEsFechaAnterior.setVisible(true);
		} else {
			rwFechaRespaldoAnterior.setVisible(false);
			cmbRespaldoAnterior1.setText("");
			rwEsFechaAnterior.setVisible(false);
			chkEsFechaAnterior.setChecked(false);
			rwFechaInicioAnterior.setVisible(false);
			rwFechaFinAnterior.setVisible(false);
			dtxFechaInicioAnterior.setValue(null);
			dtxFechaFinAnterior.setValue(null);
		}
	}

	@Listen("onCheck=#chkEsFechaAnterior")
	public void onCheck$chkEsFechaAnterior() {
		if (chkEsFechaAnterior.isChecked()) {
			rwFechaRespaldoAnterior.setVisible(false);
			cmbRespaldoAnterior1.setText("");
			if (vfechainicioAnterior.equals("S")) {
				rwFechaInicioAnterior.setVisible(true);
			} else {
				rwFechaInicioAnterior.setVisible(false);
			}
			if (vfechafinAnterior.equals("S")) {
				rwFechaFinAnterior.setVisible(true);
			} else {
				rwFechaFinAnterior.setVisible(false);
			}
			if (movimiento_detalle.getFec_inicio_anterior() != null) {
				dtxFechaInicioAnterior.setValue(movimiento_detalle.getFec_inicio_anterior());
			} else {
				dtxFechaInicioAnterior.setValue(null);
			}
			if (movimiento_detalle.getFec_fin_anterior() != null) {
				dtxFechaFinAnterior.setValue(movimiento_detalle.getFec_fin_anterior());
			} else {
				dtxFechaFinAnterior.setValue(null);
			}
		} else {
			rwFechaRespaldoAnterior.setVisible(true);
			cmbRespaldoAnterior1.setText("");
			for (int i = 0; i < listaRespaldoAnterior1.size(); i++) {
				if (String.valueOf(listaRespaldoAnterior1.get(i).getId_respaldo())
						.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_anterior()))) {
					cmbRespaldoAnterior1.setText(listaRespaldoAnterior1.get(i).toNombreRespaldo());
					i = listaRespaldoAnterior1.size() + 1;
				}
			}
			rwFechaInicioAnterior.setVisible(false);
			rwFechaFinAnterior.setVisible(false);
			dtxFechaInicioAnterior.setValue(null);
			dtxFechaFinAnterior.setValue(null);
		}
	}

	@Listen("onOK=#txtBuscarUbicacionActual")
	public void onOKtxtBuscarUbicacionActual()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxUbicacionActual.setText("");
		bdxUbicacionActual.setTooltiptext("");
		listaUbicacionActual = consultasABaseDeDatos.cargarUbicacionesDN(txtBuscarUbicacionActual.getText().toString(),
				id_dc, 6, 0, 0, 0, 10);
		lbxUbicacionesActual.clearSelection();
		binder.loadComponent(lbxUbicacionesActual);
	}

	@Listen("onSelect=#lbxUbicacionesActual")
	public void onSelectlbxUbicacionesActual()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicacionesActual.getSelectedItem() == null) {
			return;
		}
		int index = lbxUbicacionesActual.getSelectedIndex();
		bdxUbicacionActual.setText(listaUbicacionActual.get(index).getNom_ubicacion() + " - "
				+ listaUbicacionActual.get(index).getPos_ubicacion());
		bdxUbicacionActual.setTooltiptext(listaUbicacionActual.get(index).getNom_ubicacion() + " - "
				+ listaUbicacionActual.get(index).getPos_ubicacion());
	}

	@Listen("onSelect=#cmbRespaldoActual")
	public void onSelectcmbRespaldoActual() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldoActual.getSelectedItem() == null) {
			cmbRespaldoActual.setTooltiptext("");
		} else if (cmbRespaldoActual.getText().length() <= 0) {
			cmbRespaldoActual.setTooltiptext("");
		} else {
			cmbRespaldoActual.setTooltiptext(cmbRespaldoActual.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbRespaldoActual1")
	public void onSelectcmbRespaldoActual1() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldoActual1.getSelectedItem() == null) {
			cmbRespaldoActual1.setTooltiptext("");
		} else if (cmbRespaldoActual1.getText().length() <= 0) {
			cmbRespaldoActual1.setTooltiptext("");
		} else {
			cmbRespaldoActual1.setTooltiptext(cmbRespaldoActual1.getSelectedItem().getLabel());
		}
	}

	@Listen("onOK=#cmbRespaldoActual")
	public void onOKcmbRespaldoActual() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldoActual.getText().toString().toUpperCase();
		listaRespaldoActual = consultasABaseDeDatos.cargarRespaldosDN(criterio, 6, "", "", 0);
	}

	@Listen("onOK=#cmbRespaldoActual1")
	public void onOKcmbRespaldoActual1() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldoActual1.getText().toString().toUpperCase();
		listaRespaldoActual1 = consultasABaseDeDatos.cargarRespaldosDN(criterio, 7, "", "", 0);
	}

	@Listen("onCheck=#chkIngresaFechaActual")
	public void onCheck$chkIngresaFechaActual() {
		if (chkIngresaFechaActual.isChecked()) {
			rwFechaRespaldoActual.setVisible(true);
			cmbRespaldoActual1.setText("");
			for (int i = 0; i < listaRespaldoActual1.size(); i++) {
				if (String.valueOf(listaRespaldoActual1.get(i).getId_respaldo())
						.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_actual()))) {
					cmbRespaldoActual1.setText(listaRespaldoActual1.get(i).toNombreRespaldo());
					i = listaRespaldoActual1.size() + 1;
				}
			}
			rwEsFechaActual.setVisible(true);
		} else {
			rwFechaRespaldoActual.setVisible(false);
			cmbRespaldoActual1.setText("");
			rwEsFechaActual.setVisible(false);
			chkEsFechaActual.setChecked(false);
			rwFechaInicioActual.setVisible(false);
			rwFechaFinActual.setVisible(false);
			dtxFechaInicioActual.setValue(null);
			dtxFechaFinActual.setValue(null);
		}
	}

	@Listen("onCheck=#chkEsFechaActual")
	public void onCheck$chkEsFechaActual() {
		if (chkEsFechaActual.isChecked()) {
			rwFechaRespaldoActual.setVisible(false);
			cmbRespaldoActual1.setText("");
			if (vfechainicioActual.equals("S")) {
				rwFechaInicioActual.setVisible(true);
			} else {
				rwFechaInicioActual.setVisible(false);
			}
			if (vfechafinActual.equals("S")) {
				rwFechaFinActual.setVisible(true);
			} else {
				rwFechaFinActual.setVisible(false);
			}
			if (movimiento_detalle.getFec_inicio_actual() != null) {
				dtxFechaInicioActual.setValue(movimiento_detalle.getFec_inicio_actual());
			} else {
				dtxFechaInicioActual.setValue(null);
			}
			if (movimiento_detalle.getFec_fin_actual() != null) {
				dtxFechaFinActual.setValue(movimiento_detalle.getFec_fin_actual());
			} else {
				dtxFechaFinActual.setValue(null);
			}
		} else {
			rwFechaRespaldoActual.setVisible(true);
			cmbRespaldoActual1.setText("");
			for (int i = 0; i < listaRespaldoActual1.size(); i++) {
				if (String.valueOf(listaRespaldoActual1.get(i).getId_respaldo())
						.equals(String.valueOf(movimiento_detalle.getId_fec_respaldo_actual()))) {
					cmbRespaldoActual1.setText(listaRespaldoActual1.get(i).toNombreRespaldo());
					i = listaRespaldoActual1.size() + 1;
				}
			}
			rwFechaInicioActual.setVisible(false);
			rwFechaFinActual.setVisible(false);
			dtxFechaInicioActual.setValue(null);
			dtxFechaFinActual.setValue(null);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtCodigoActual.getText().length() <= 0) {
			txtCodigoActual.setErrorMessage(validaciones.getMensaje_validacion_1());
			return;
		}
		if (txtDescripcionActual.getText().length() <= 0) {
			txtDescripcionActual.setErrorMessage(validaciones.getMensaje_validacion_2());
			return;
		}
		if (vfechainicioActual.equals("S")) {
			if (chkIngresaFechaActual.isChecked()) {
				if (chkEsFechaActual.isChecked()) {
					if (dtxFechaInicioActual.getValue() == null) {
						dtxFechaInicioActual.setErrorMessage(validaciones.getMensaje_validacion_4());
						return;
					}
				} else {
					if (cmbRespaldoActual1.getSelectedItem() == null) {
						cmbRespaldoActual1.setErrorMessage(validaciones.getMensaje_validacion_4());
					}
				}
			}
		}
		if (lbxUbicacionesActual.getSelectedItem() == null) {
			bdxUbicacionActual.setErrorMessage(validaciones.getMensaje_validacion_6());
			return;
		}
		/*
		 * Se valida que no se supere la capacidad permitida en la ubicacion
		 * seleccionada
		 */
		/*
		 * int indexUbicacion = lbxUbicacionesActual.getSelectedIndex(); if
		 * (id_ubicacion_inicial !=
		 * listaUbicacionActual.get(indexUbicacion).getId_ubicacion()) { String
		 * seValidaCapacidad = consultasABaseDeDatos
		 * .seValidaCapacidadEnUbicacionDN(listaUbicacionActual.get(indexUbicacion).
		 * getId_ubicacion()); if (seValidaCapacidad.equals("S")) { int capacidadMaxima
		 * = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
		 * listaUbicacionActual.get(indexUbicacion).getId_ubicacion(),
		 * seValidaCapacidad); int totalArticulos = consultasABaseDeDatos
		 * .totalArticulosEnUbicacionDN(listaUbicacionActual.get(indexUbicacion).
		 * getId_ubicacion()); if ((totalArticulos + 1) > capacidadMaxima) {
		 * Messagebox.show(informativos.getMensaje_informativo_10(),
		 * informativos.getMensaje_informativo_24(), Messagebox.OK,
		 * Messagebox.INFORMATION); return; } } }
		 */
		if (tmxHoraLlegadaActual.getValue() != null && tmxHoraSalidaActual.getValue() == null) {
			tmxHoraSalidaActual.setFocus(true);
			tmxHoraSalidaActual.setErrorMessage(validaciones.getMensaje_validacion_22());
			return;
		}
		if (tmxHoraLlegadaActual.getValue() == null && tmxHoraSalidaActual.getValue() != null) {
			tmxHoraLlegadaActual.setFocus(true);
			tmxHoraLlegadaActual.setErrorMessage(validaciones.getMensaje_validacion_21());
			return;
		}
		if (tmxHoraLlegadaActual.getValue() != null && tmxHoraSalidaActual.getValue() != null) {
			if (tmxHoraLlegadaActual.getValue().after(tmxHoraSalidaActual.getValue())) {
				tmxHoraLlegadaActual.setFocus(true);
				tmxHoraLlegadaActual.setErrorMessage(validaciones.getMensaje_validacion_23());
				return;
			}
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							modelo_movimiento_detalle_dn movimiento_detalle = new modelo_movimiento_detalle_dn();
							movimiento_detalle = modificar.this.movimiento_detalle.clone();
							movimiento_detalle.setCod_articulo_actual(txtCodigoActual.getText().toUpperCase());
							movimiento_detalle.setDes_articulo_actual(txtDescripcionActual.getText().toUpperCase());
							movimiento_detalle.setId_cat_articulo_actual(
									modificar.this.movimiento_detalle.getId_cat_articulo_actual());
							movimiento_detalle.setNom_cat_articulo_actual(
									modificar.this.movimiento_detalle.getNom_cat_articulo_actual());
							movimiento_detalle.setId_cap_articulo_actual(
									modificar.this.movimiento_detalle.getId_cap_articulo_actual());
							movimiento_detalle.setNom_id_cap_articulo_actual(
									modificar.this.movimiento_detalle.getNom_id_cap_articulo_actual());
							int indice = lbxUbicacionesActual.getSelectedIndex();
							movimiento_detalle
									.setId_ubicacion_actual(listaUbicacionActual.get(indice).getId_ubicacion());
							movimiento_detalle
									.setNom_ubicacion_actual(listaUbicacionActual.get(indice).getNom_ubicacion() + " - "
											+ listaUbicacionActual.get(indice).getPos_ubicacion());
							if (chkIngresaFechaActual.isChecked()) {
								movimiento_detalle.setSi_ing_fec_inicio_fin_actual("S");
								if (chkEsFechaActual.isChecked()) {
									movimiento_detalle.setEs_fecha_actual("S");
									if (dtxFechaInicioActual.getValue() != null) {
										movimiento_detalle.setFec_inicio_actual(
												fechas.obtenerTimestampDeDate(dtxFechaInicioActual.getValue()));
									} else {
										movimiento_detalle.setFec_inicio_actual(null);
									}
								} else {
									movimiento_detalle.setEs_fecha_actual("N");
									if (cmbRespaldoActual1.getSelectedItem() != null) {
										indice = cmbRespaldoActual1.getSelectedIndex();
										movimiento_detalle.setId_fec_respaldo_actual(
												listaRespaldoActual1.get(indice).getId_respaldo());
										movimiento_detalle.setNom_id_fec_respaldo_actual(
												listaRespaldoActual1.get(indice).toNombreRespaldo());
									} else {
										movimiento_detalle.setId_fec_respaldo_actual(0);
										movimiento_detalle.setNom_id_fec_respaldo_actual(null);
									}
									movimiento_detalle.setFec_inicio_actual(null);
								}
							} else {
								movimiento_detalle.setSi_ing_fec_inicio_fin_actual("N");
								movimiento_detalle.setEs_fecha_actual("N");
								movimiento_detalle.setFec_inicio_actual(null);
								movimiento_detalle.setId_fec_respaldo_actual(0);
								movimiento_detalle.setNom_id_fec_respaldo_actual(null);
							}
							if (dtxFechaFinActual.getValue() != null) {
								movimiento_detalle
										.setFec_fin_actual(fechas.obtenerTimestampDeDate(dtxFechaFinActual.getValue()));
							} else {
								movimiento_detalle.setFec_fin_actual(null);
							}
							if (cmbRespaldoActual.getSelectedItem() != null) {
								indice = cmbRespaldoActual.getSelectedIndex();
								movimiento_detalle.setTip_respaldo_actual(
										String.valueOf(listaRespaldoActual.get(indice).getId_respaldo()));
								movimiento_detalle
										.setNom_tip_respaldo_actual(listaRespaldoActual.get(indice).toNombreRespaldo());
							} else {
								movimiento_detalle.setTip_respaldo_actual("0");
								movimiento_detalle.setNom_tip_respaldo_actual(null);
							}
							movimiento_detalle.setId_contenedor_actual(txtIDContenedorActual.getText().toUpperCase());
							if (tmxHoraLlegadaActual.getValue() != null) {
								movimiento_detalle
										.setHora_llegada_custodia_actual(tmxHoraLlegadaActual.getValue().getTime());
							} else {
								movimiento_detalle.setHora_llegada_custodia_actual(0);
							}
							if (tmxHoraSalidaActual.getValue() != null) {
								movimiento_detalle
										.setHora_salida_custodia_actual(tmxHoraSalidaActual.getValue().getTime());
							} else {
								movimiento_detalle.setHora_salida_custodia_actual(0);
							}
							movimiento_detalle
									.setRemesa_ingreso_custodia_actual(txtRemesaIngresoActual.getText().toUpperCase());
							movimiento_detalle
									.setRemesa_salida_custodia_actual(txtRemesaSalidaActual.getText().toUpperCase());
							movimiento_detalle.setActualiza_inventario(
									cmbActualizaInventario.getSelectedItem().getValue().toString());
							Sessions.getCurrent().setAttribute("movimiento_detalle_2", movimiento_detalle);
							Events.postEvent(new Event("onClose", zModificar));
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		// validarSesion();
		Events.postEvent(new Event("onClose", zModificar));
	}

}
