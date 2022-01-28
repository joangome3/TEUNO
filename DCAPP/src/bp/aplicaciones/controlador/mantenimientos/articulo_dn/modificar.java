package bp.aplicaciones.controlador.mantenimientos.articulo_dn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_log_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Error;
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
	Textbox txtId, txtCodigo, txtDescripcion, txtIDContenedor, txtBuscarUbicacion, txtComentario, txtMotivo;
	@Wire
	Intbox txtCantidad;
	@Wire
	Combobox cmbLocalidad, cmbCategoria, cmbRespaldo, cmbRespaldo1, cmbCapacidad;
	@Wire
	Row rwCapacidad, rwIngresaFecha, rwEsFecha, rwFechaRespaldo, rwFechaInicio, rwFechaFin, rwTipoRespaldo,
			rwIDContenedor;
	@Wire
	Checkbox chkIngresaFecha, chkEsFecha;
	@Wire
	Listbox lbxUbicaciones;
	@Wire
	Bandbox bdxUbicacion;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;

	String vcapacidad, vfechainicio, vfechafin, vtiporespaldo, vidcontenedor;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
	List<modelo_respaldo> listaRespaldo1 = new ArrayList<modelo_respaldo>();
	List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	long id = 0;
	long id_mantenimiento = 16;

	/*
	 * Se crean variables para almacenar los valores iniciales para las consultas
	 * posteriores
	 */
	long id_ubicacion_inicial = 0;
	String codigo_inicial = "";
	String fecha_inicio_inicial = "";
	String fecha_fin_inicial = "";

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	modelo_articulo_dn articulo = (modelo_articulo_dn) Sessions.getCurrent().getAttribute("articulo");

	modelo_solicitud solicitud = new modelo_solicitud();

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxUbicaciones.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtCodigo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigo.setText(txtCodigo.getText().toUpperCase().trim());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
			}
		});
		txtCantidad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtCantidad.setText(validar.soloNumeros(txtCantidad.getText()));
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase().trim());
			}
		});
		txtIDContenedor.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtIDContenedor.setText(txtIDContenedor.getText().toUpperCase().trim());
			}
		});
		txtMotivo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtMotivo.setText(txtMotivo.getText().toUpperCase().trim());
			}
		});
		inicializarListas();
		setearLocalidad();
		cargarDatos();
		// validarSesion();
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public List<modelo_respaldo> obtenerRespaldos() {
		return listaRespaldo;
	}

	public List<modelo_respaldo> obtenerRespaldos1() {
		return listaRespaldo1;
	}

	public List<modelo_capacidad> obtenerCapacidades() {
		return listaCapacidad;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN("", 6, "", "", 0);
		listaRespaldo1 = consultasABaseDeDatos.cargarRespaldosDN("", 7, "", "", 0);
		listaLocalidad = consultasABaseDeDatos.cargarLocalidades("", 4, 0, 10);
		listaCategoria = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 10);
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
		listaCapacidad = consultasABaseDeDatos.cargarCapacidadesDN("", 4, 0, 0);
		binder.loadComponent(cmbRespaldo);
		binder.loadComponent(cmbRespaldo1);
		binder.loadComponent(cmbLocalidad);
		binder.loadComponent(cmbCategoria);
		binder.loadComponent(lbxUbicaciones);
		binder.loadComponent(cmbCapacidad);
	}

	public void setearLocalidad() {
		Iterator<modelo_localidad> it = listaLocalidad.iterator();
		while (it.hasNext()) {
			modelo_localidad localidad = it.next();
			if (localidad.getId_localidad() == id_dc) {
				cmbLocalidad.setText(localidad.getNom_localidad());
				break;
			}
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * Se almacenan los valores iniciales de Ubicacion Codigo Fecha inicio Fecha fin
		 */
		id_ubicacion_inicial = articulo.getId_ubicacion();
		codigo_inicial = articulo.getCod_articulo();
		if (articulo.getFec_inicio() != null) {
			fecha_inicio_inicial = fechas.obtenerFechaFormateada(articulo.getFec_inicio(), "yyyy/MM/dd");
		}
		if (articulo.getFec_fin() != null) {
			fecha_fin_inicial = fechas.obtenerFechaFormateada(articulo.getFec_fin(), "yyyy/MM/dd");
		}
		txtId.setText(String.valueOf(articulo.getId_articulo()));
		txtCodigo.setText(articulo.getCod_articulo());
		txtDescripcion.setText(articulo.getDes_articulo());
		txtCantidad.setText(String.valueOf(articulo.getSto_articulo()));
		for (int i = 0; i < listaRespaldo.size(); i++) {
			if (listaRespaldo.get(i).getId_respaldo() == articulo.getId_tip_respaldo()) {
				cmbRespaldo.setText(listaRespaldo.get(i).toNombreRespaldo());
				i = listaRespaldo.size() + 1;
			}
		}
		for (int i = 0; i < listaRespaldo1.size(); i++) {
			if (String.valueOf(listaRespaldo1.get(i).getId_respaldo())
					.equals(String.valueOf(articulo.getId_fec_respaldo()))) {
				cmbRespaldo1.setText(listaRespaldo1.get(i).toNombreRespaldo());
				i = listaRespaldo1.size() + 1;
			}
		}
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == articulo.getId_localidad()) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		for (int i = 0; i < listaCapacidad.size(); i++) {
			if (listaCapacidad.get(i).getId_capacidad() == articulo.getId_capacidad()) {
				cmbCapacidad.setText(listaCapacidad.get(i).getNom_capacidad());
				i = listaCapacidad.size() + 1;
			}
		}
		for (int i = 0; i < listaCategoria.size(); i++) {
			if (listaCategoria.get(i).getId_categoria() == articulo.getId_categoria()) {
				cmbCategoria.setText(listaCategoria.get(i).getNom_categoria());
				if (listaCategoria.get(i).getMos_capacidad().equals("S")) {
					rwCapacidad.setVisible(true);
					vcapacidad = "S";
				} else {
					rwCapacidad.setVisible(false);
					vcapacidad = "N";
				}
				if (listaCategoria.get(i).getMos_fec_inicio().equals("S")) {
					rwIngresaFecha.setVisible(true);
					vfechainicio = "S";
				} else {
					rwIngresaFecha.setVisible(false);
					chkIngresaFecha.setChecked(false);
					rwFechaRespaldo.setVisible(false);
					cmbRespaldo1.setText("");
					rwEsFecha.setVisible(false);
					chkEsFecha.setChecked(false);
					rwFechaInicio.setVisible(false);
					rwFechaFin.setVisible(false);
					dtxFechaInicio.setValue(null);
					dtxFechaFin.setValue(null);
					vfechainicio = "N";
				}
				if (listaCategoria.get(i).getMos_fec_fin().equals("S")) {
					vfechafin = "S";
				} else {
					vfechafin = "N";
				}
				if (listaCategoria.get(i).getMos_tip_respaldo().equals("S")) {
					rwTipoRespaldo.setVisible(true);
					vtiporespaldo = "S";
				} else {
					rwTipoRespaldo.setVisible(false);
					vtiporespaldo = "N";
				}
				if (listaCategoria.get(i).getMos_id_contenedor().equals("S")) {
					rwIDContenedor.setVisible(true);
					vidcontenedor = "S";
					if (articulo.getId_contenedor() != null) {
						if (articulo.getId_contenedor().length() <= 0) {
							txtIDContenedor.setText("");
						} else {
							txtIDContenedor.setText(articulo.getId_contenedor());
						}
					} else {
						txtIDContenedor.setText("");
					}
				} else {
					rwIDContenedor.setVisible(false);
					vidcontenedor = "N";
				}
				i = listaCategoria.size() + 1;
			}
		}
		if (articulo.getSi_ing_fec_inicio_fin().equals("S")) {
			chkIngresaFecha.setChecked(true);
			rwEsFecha.setVisible(true);
			if (articulo.getEs_fecha().equals("S")) {
				rwFechaRespaldo.setVisible(false);
				cmbRespaldo1.setText("");
				chkEsFecha.setChecked(true);
				rwFechaInicio.setVisible(true);
				rwFechaFin.setVisible(true);
				if (articulo.getFec_inicio() != null) {
					dtxFechaInicio.setValue(articulo.getFec_inicio());
				} else {
					dtxFechaInicio.setValue(null);
				}
				if (articulo.getFec_fin() != null) {
					dtxFechaFin.setValue(articulo.getFec_fin());
				} else {
					dtxFechaFin.setValue(null);
				}
			} else {
				rwFechaRespaldo.setVisible(true);
				cmbRespaldo1.setVisible(true);
				chkEsFecha.setChecked(false);
				rwFechaInicio.setVisible(false);
				rwFechaFin.setVisible(false);
				dtxFechaInicio.setValue(null);
				dtxFechaFin.setValue(null);
			}
		} else {
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(false);
			chkIngresaFecha.setChecked(false);
			chkEsFecha.setChecked(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		}
		dao_relacion_articulo_dn dao1 = new dao_relacion_articulo_dn();
		String str = "";
		for (int i = 0; i < lbxUbicaciones.getItems().size(); i++) {
			if (dao1.obtenerRelacionArticuloUbicacion(0, articulo.getId_articulo(),
					listaUbicacion.get(i).getId_ubicacion(), 2) == true) {
				lbxUbicaciones.getItemAtIndex(i).setSelected(true);
				if (!str.isEmpty()) {
					str += ", ";
				}
				str += listaUbicacion.get(i).getNom_ubicacion() + " - " + listaUbicacion.get(i).getPos_ubicacion();
				id_ubicacion_inicial = listaUbicacion.get(i).getId_ubicacion();
			}
		}
		bdxUbicacion.setText(str);
		bdxUbicacion.setTooltiptext(str);
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					txtCantidad.setDisabled(false);
					cmbCategoria.setDisabled(false);
					bdxUbicacion.setDisabled(false);
					txtCodigo.setReadonly(false);
					txtDescripcion.setReadonly(false);
					cmbCapacidad.setDisabled(false);
					cmbRespaldo.setDisabled(false);
					cmbRespaldo1.setDisabled(false);

				} else {
					txtCodigo.setReadonly(true);
					txtDescripcion.setReadonly(true);
					cmbCategoria.setDisabled(true);
					cmbCapacidad.setDisabled(true);
					cmbRespaldo.setDisabled(true);
					cmbRespaldo1.setDisabled(true);
					if (solicitud.getId_campo() == 21) {
						bdxUbicacion.setDisabled(false);
					} else {
						bdxUbicacion.setDisabled(true);
					}
				}
			}
		}
	}

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOKtxtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxUbicacion.setText("");
		bdxUbicacion.setTooltiptext("");
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN(txtBuscarUbicacion.getText().toString(), id_dc, 6, 0,
				0, 0, 10);
		lbxUbicaciones.clearSelection();
		binder.loadComponent(lbxUbicaciones);
	}

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelectlbxUbicaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicaciones.getSelectedItem() == null) {
			return;
		}
		int index = lbxUbicaciones.getSelectedIndex();
		bdxUbicacion.setText(
				listaUbicacion.get(index).getNom_ubicacion() + " - " + listaUbicacion.get(index).getPos_ubicacion());
		bdxUbicacion.setTooltiptext(
				listaUbicacion.get(index).getNom_ubicacion() + " - " + listaUbicacion.get(index).getPos_ubicacion());
	}

	@Listen("onSelect=#cmbCategoria")
	public void onSelectcmbCategoria()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (listaCategoria.size() == 0) {
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			return;
		}
		int index = cmbCategoria.getSelectedIndex();
		if (listaCategoria.get(index).getMos_capacidad().equals("S")) {
			rwCapacidad.setVisible(true);
			vcapacidad = "S";
		} else {
			rwCapacidad.setVisible(false);
			vcapacidad = "N";
		}
		if (listaCategoria.get(index).getMos_fec_inicio().equals("S")) {
			rwIngresaFecha.setVisible(true);
			vfechainicio = "S";
		} else {
			rwIngresaFecha.setVisible(false);
			chkIngresaFecha.setChecked(false);
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(false);
			chkEsFecha.setChecked(false);
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
			vfechainicio = "N";
		}
		if (listaCategoria.get(index).getMos_fec_fin().equals("S")) {
			vfechafin = "S";
		} else {
			vfechafin = "N";
		}
		if (listaCategoria.get(index).getMos_tip_respaldo().equals("S")) {
			rwTipoRespaldo.setVisible(true);
			vtiporespaldo = "S";
		} else {
			rwTipoRespaldo.setVisible(false);
			vtiporespaldo = "N";
		}
		if (listaCategoria.get(index).getMos_id_contenedor().equals("S")) {
			rwIDContenedor.setVisible(true);
			vidcontenedor = "S";
		} else {
			rwIDContenedor.setVisible(false);
			vidcontenedor = "N";
		}
	}

	@Listen("onSelect=#cmbRespaldo")
	public void onSelectcmbRespaldo() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldo.getSelectedItem() == null) {
			cmbRespaldo.setTooltiptext("");
		} else if (cmbRespaldo.getText().length() <= 0) {
			cmbRespaldo.setTooltiptext("");
		} else {
			cmbRespaldo.setTooltiptext(cmbRespaldo.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbRespaldo1")
	public void onSelectcmbRespaldo1() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldo1.getSelectedItem() == null) {
			cmbRespaldo1.setTooltiptext("");
		} else if (cmbRespaldo1.getText().length() <= 0) {
			cmbRespaldo1.setTooltiptext("");
		} else {
			cmbRespaldo1.setTooltiptext(cmbRespaldo1.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbCapacidad")
	public void onSelectcmbCapacidad() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbCapacidad.getSelectedItem() == null) {
			cmbCapacidad.setTooltiptext("");
		} else if (cmbCapacidad.getText().length() <= 0) {
			cmbCapacidad.setTooltiptext("");
		} else {
			cmbCapacidad.setTooltiptext(cmbCapacidad.getSelectedItem().getLabel());
		}
	}

	@Listen("onOK=#cmbRespaldo")
	public void onOKcmbRespaldo() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldo.getText().toString().toUpperCase();
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN(criterio, 6, "", "", 0);
	}

	@Listen("onOK=#cmbRespaldo1")
	public void onOKcmbRespaldo1() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldo1.getText().toString().toUpperCase();
		listaRespaldo1 = consultasABaseDeDatos.cargarRespaldosDN(criterio, 7, "", "", 0);
	}

	@Listen("onCheck=#chkIngresaFecha")
	public void onCheck$chkIngresaFecha() {
		if (chkIngresaFecha.isChecked()) {
			rwFechaRespaldo.setVisible(true);
			cmbRespaldo1.setText("");
			for (int i = 0; i < listaRespaldo1.size(); i++) {
				if (String.valueOf(listaRespaldo1.get(i).getId_respaldo())
						.equals(String.valueOf(articulo.getId_fec_respaldo()))) {
					cmbRespaldo1.setText(listaRespaldo1.get(i).toNombreRespaldo());
					i = listaRespaldo1.size() + 1;
				}
			}
			rwEsFecha.setVisible(true);
		} else {
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(false);
			chkEsFecha.setChecked(false);
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		}
	}

	@Listen("onCheck=#chkEsFecha")
	public void onCheck$chkEsFecha() {
		if (chkEsFecha.isChecked()) {
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			if (vfechainicio.equals("S")) {
				rwFechaInicio.setVisible(true);
			} else {
				rwFechaInicio.setVisible(false);
			}
			if (vfechafin.equals("S")) {
				rwFechaFin.setVisible(true);
			} else {
				rwFechaFin.setVisible(false);
			}
			if (articulo.getFec_inicio() != null) {
				dtxFechaInicio.setValue(articulo.getFec_inicio());
			} else {
				dtxFechaInicio.setValue(null);
			}
			if (articulo.getFec_fin() != null) {
				dtxFechaFin.setValue(articulo.getFec_fin());
			} else {
				dtxFechaFin.setValue(null);
			}
		} else {
			rwFechaRespaldo.setVisible(true);
			cmbRespaldo1.setText("");
			for (int i = 0; i < listaRespaldo1.size(); i++) {
				if (String.valueOf(listaRespaldo1.get(i).getId_respaldo())
						.equals(String.valueOf(articulo.getId_fec_respaldo()))) {
					cmbRespaldo1.setText(listaRespaldo1.get(i).toNombreRespaldo());
					i = listaRespaldo1.size() + 1;
				}
			}
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public boolean validarSiExisteSolicitudPendienteActualizacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public void setearDatosAntesDeGuardar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_solicitud() != 0) {
				comentario_1 = solicitud.getComentario_1();
				comentario_2 = solicitud.getComentario_4();
				fecha_comentario_1 = solicitud.getFecha_1();
				fecha_comentario_2 = solicitud.getFecha_2();
				fecha_comentario_3 = solicitud.getFecha_4();
			}
		}
	}

	public String setearComentario() {
		String comentario = "";
		comentario = "EN LA FECHA " + fechas.obtenerFechaFormateada(fecha_comentario_1, "dd/MM/yyyy HH:mm") + " "
				+ comentario_1 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_2, "dd/MM/yyyy HH:mm") + " EL APROBADOR INDICA QUE "
				+ comentario_2 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_3, "dd/MM/yyyy HH:mm")
				+ " SE REALIZA EL CAMBIO SOLICITADO.";
		return comentario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtCodigo.getText().length() <= 0) {
			txtCodigo.setErrorMessage(validaciones.getMensaje_validacion_1());
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setErrorMessage(validaciones.getMensaje_validacion_2());
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			cmbCategoria.setErrorMessage(validaciones.getMensaje_validacion_3());
			return;
		}
		if (vcapacidad.equals("S")) {
			if (cmbCapacidad.getSelectedItem() == null) {
				cmbCapacidad.setErrorMessage(validaciones.getMensaje_validacion_7());
				return;
			}
		}
		if (vfechainicio.equals("S")) {
			if (chkIngresaFecha.isChecked()) {
				if (chkEsFecha.isChecked()) {
					if (dtxFechaInicio.getValue() == null) {
						dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_4());
						return;
					}
				} else {
					if (cmbRespaldo1.getSelectedItem() == null) {
						cmbRespaldo1.setErrorMessage(validaciones.getMensaje_validacion_4());
					}
				}
			}
		}
		if (lbxUbicaciones.getSelectedItem() == null) {
			bdxUbicacion.setErrorMessage(validaciones.getMensaje_validacion_6());
			return;
		}
		if (txtCantidad.getText().length() <= 0) {
			txtCantidad.setErrorMessage(validaciones.getMensaje_validacion_8());
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setErrorMessage(validaciones.getMensaje_validacion_9());
			return;
		}
		if (txtMotivo.getText().length() <= 0) {
			txtMotivo.setErrorMessage(validaciones.getMensaje_validacion_40());
			txtMotivo.setFocus(true);
			return;
		}
		/*
		 * Se valida que no se supere la capacidad permitida en la ubicacion
		 * seleccionada
		 */
		int indexUbicacion = lbxUbicaciones.getSelectedIndex();
		if (id_ubicacion_inicial != listaUbicacion.get(indexUbicacion).getId_ubicacion()) {
			String seValidaCapacidad = consultasABaseDeDatos
					.seValidaCapacidadEnUbicacionDN(listaUbicacion.get(indexUbicacion).getId_ubicacion());
			if (seValidaCapacidad.equals("S")) {
				int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
						listaUbicacion.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
				int totalArticulos = consultasABaseDeDatos
						.totalArticulosEnUbicacionDN(listaUbicacion.get(indexUbicacion).getId_ubicacion());
				if ((totalArticulos + 1) > capacidadMaxima) {
					Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_24(),
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
			}
		}
		setearDatosAntesDeGuardar();
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_articulo_dn dao = new dao_articulo_dn();
							modelo_articulo_dn articulo = new modelo_articulo_dn();
							articulo.setId_articulo(Long.parseLong(txtId.getText()));
							articulo.setId_categoria(
									Long.parseLong(cmbCategoria.getSelectedItem().getValue().toString()));
							articulo.setCod_articulo(txtCodigo.getText().toString().trim());
							articulo.setDes_articulo(txtDescripcion.getText().toString().trim());
							articulo.setSto_articulo(Integer.valueOf(txtCantidad.getText().toString()));
							articulo.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (vcapacidad.equals("S")) {
								articulo.setId_capacidad(
										Long.valueOf(cmbCapacidad.getSelectedItem().getValue().toString()));
							} else {
								articulo.setId_capacidad(0);
							}
							if (vfechainicio.equals("S")) {
								if (chkIngresaFecha.isChecked()) {
									if (chkEsFecha.isChecked()) {
										articulo.setFec_inicio(
												fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
										articulo.setSi_ing_fec_inicio_fin("S");
										articulo.setEs_fecha("S");
									} else {
										articulo.setId_fec_respaldo(
												Long.parseLong(cmbRespaldo1.getSelectedItem().getValue().toString()));
										articulo.setSi_ing_fec_inicio_fin("S");
										articulo.setEs_fecha("N");
									}
								} else {
									articulo.setFec_inicio(null);
									articulo.setSi_ing_fec_inicio_fin("N");
									articulo.setEs_fecha("N");
								}
							} else {
								articulo.setSi_ing_fec_inicio_fin("N");
								articulo.setEs_fecha("N");
								articulo.setFec_inicio(null);
							}
							if (vfechafin.equals("S")) {
								if (chkIngresaFecha.isChecked()) {
									if (chkEsFecha.isChecked()) {
										if (dtxFechaFin.getValue() == null) {
											articulo.setFec_fin(null);
										} else {
											articulo.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
										}
									} else {
										articulo.setFec_fin(null);
									}
								} else {
									articulo.setFec_fin(null);
								}
							} else {
								articulo.setFec_fin(null);
							}
							if (vtiporespaldo.equals("S")) {
								if (cmbRespaldo.getSelectedItem() == null) {
									articulo.setId_tip_respaldo(0);
								} else {
									articulo.setId_tip_respaldo(
											Long.valueOf(cmbRespaldo.getSelectedItem().getValue().toString()));
								}
							} else {
								articulo.setId_tip_respaldo(0);
							}
							if (vidcontenedor.equals("S")) {
								if (txtIDContenedor.getText().length() <= 0) {
									articulo.setId_contenedor("");
								} else {
									articulo.setId_contenedor(txtIDContenedor.getText());
								}
							} else {
								articulo.setId_contenedor("");
							}
							if (dtxFechaFin.getValue() != null) {
								if (dtxFechaFin.getValue().compareTo(dtxFechaInicio.getValue()) < 0) {
									dtxFechaFin.setErrorMessage(
											"La fecha fin de respaldo debe ser mayor a la fecha inicio de respaldo");
									dtxFechaFin.setFocus(true);
									return;
								}
							}
							if (chkIngresaFecha.isChecked()) {
								if (chkEsFecha.isChecked()) {
									/*
									 * Se valida si un articulo tiene el mismo codigo, fecha inicio
									 * 
									 */
									String fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(),
											"yyyy/MM/dd");
									if (dtxFechaFin.getValue() == null) {
										if (!(codigo_inicial.equals(txtCodigo.getText())
												&& fecha_inicio_inicial.equals(fecha_inicio))) {
											int totalArticulos = consultasABaseDeDatos
													.validarSiCodigoYFechaDeInicioDeArticuloExiste(txtCodigo.getText(),
															fecha_inicio, articulo.getId_categoria());
											if (totalArticulos > 0) {
												Messagebox.show(informativos.getMensaje_informativo_18(),
														informativos.getMensaje_informativo_24(), Messagebox.OK,
														Messagebox.INFORMATION);
												return;
											}
										}
									} else {
										/*
										 * Se valida si un articulo tiene el mismo codigo, fecha inicio y fecha de fin
										 */
										String fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(),
												"yyyy/MM/dd");
										if (!(codigo_inicial.equals(txtCodigo.getText())
												&& fecha_inicio_inicial.equals(fecha_inicio)
												&& fecha_fin_inicial.equals(fecha_fin))) {
											int totalArticulos = consultasABaseDeDatos
													.validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(
															txtCodigo.getText(), fecha_inicio, fecha_fin,
															articulo.getId_categoria());
											if (totalArticulos > 0) {
												Messagebox.show(informativos.getMensaje_informativo_19(),
														informativos.getMensaje_informativo_24(), Messagebox.OK,
														Messagebox.INFORMATION);
												return;
											}
										}
									}
								}
							}
							articulo.setUsu_modifica(user);
							articulo.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
							int pos_ubicacion = 0;
							Iterator<Listitem> it = lbxUbicaciones.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = it.next();
								final int indice = item.getIndex();
								modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
								relacion_articulo_ubicacion.setId_articulo(Long.parseLong(txtId.getText()));
								relacion_articulo_ubicacion
										.setId_ubicacion(listaUbicacion.get(indice).getId_ubicacion());
								relacion_articulo_ubicacion.setSto_articulo(1);
								pos_ubicacion = consultasABaseDeDatos.posicionMaximaEnUbicacionDN(
										(listaUbicacion.get(indice).getId_ubicacion())) + 1;
								relacion_articulo_ubicacion.setPos_ubicacion(pos_ubicacion);
								relacion_articulo_ubicacion.setEst_relacion("A");
								relacion_articulo_ubicacion.setUsu_ingresa(user);
								relacion_articulo_ubicacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								listaRelacionArticulos.add(relacion_articulo_ubicacion);
							}
							if (solicitud != null) {
								if (solicitud.getId_solicitud() != 0) {
									if (solicitud.getId_tip_solicitud() == 2) {
										if (solicitud.getEst_solicitud().equals("T")) {
											articulo.setEst_articulo("PAC");
										} else {
											articulo.setEst_articulo("AE");
										}
									} else {
										articulo.setEst_articulo("AE");
									}
								} else {
									articulo.setEst_articulo("AE");
								}
							} else {
								articulo.setEst_articulo("AE");
							}
							if (solicitud != null) {
								if (solicitud.getId_solicitud() != 0) {
									if (solicitud.getEst_solicitud().equals("T")) {
										articulo.setEst_articulo("PACP");
										solicitud.setEst_solicitud("R");
										solicitud.setComentario_1(
												setearComentario() + txtComentario.getText().toUpperCase());
										solicitud.setComentario_2("");
										solicitud.setComentario_3("");
										solicitud.setComentario_4("");
										solicitud.setComentario_5("");
										solicitud.setId_user_1(id_user);
										solicitud.setId_user_2(0);
										solicitud.setId_user_3(0);
										solicitud.setId_user_4(0);
										solicitud.setId_user_5(0);
										solicitud.setFecha_1(fechas.obtenerTimestampDeDate(fecha_comentario_1));
										solicitud.setFecha_2(null);
										solicitud.setFecha_3(null);
										solicitud.setFecha_4(null);
										solicitud.setFecha_5(null);
										solicitud.setUsu_modifica(user);
										solicitud.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
									}
								}
							}
							int tipo = 1;
							String log = generarLogModificacionInventario(modificar.this.articulo, articulo,
									listaRelacionArticulos.get(0));
							if (articulo.getId_categoria() == 2) {
								String nom_ubicacion = "";
								for (int i = 0; i < listaUbicacion.size(); i++) {
									if (listaUbicacion.get(i).getId_ubicacion() == listaRelacionArticulos.get(0)
											.getId_ubicacion()) {
										nom_ubicacion = listaUbicacion.get(i).toStringUbicacion();
										i = listaUbicacion.size() + 1;
									}
								}
								if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
										|| listaRelacionArticulos.get(0).getId_ubicacion() >= 134) {
									Messagebox.show(
											informativos.getMensaje_informativo_120().replace("?1", nom_ubicacion),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
									return;
								}
							}
							modelo_log_articulo_dn modelo_log = new modelo_log_articulo_dn();
							modelo_log.setId_articulo(articulo.getId_articulo());
							modelo_log.setDes_log(log);
							modelo_log.setMot_log(txtMotivo.getText().toUpperCase().trim());
							modelo_log.setEst_log("AE");
							modelo_log.setUsu_ingresa(user);
							modelo_log.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.modificarArticulo(articulo, listaRelacionArticulos, solicitud, modelo_log, tipo);
								if (tipo == 1) {
									Messagebox.show(informativos.getMensaje_informativo_20(),
											informativos.getMensaje_informativo_24(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								} else {
									Messagebox.show(informativos.getMensaje_informativo_26(),
											informativos.getMensaje_informativo_24(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
								// System.out.println(log);
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("articulo");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										error.getMensaje_error_4() + error.getMensaje_error_1() + e.getMessage(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("articulo");
		// validarSesion();
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtCantidad.setText("0");
		cmbLocalidad.setText("");
		lbxUbicaciones.clearSelection();
		bdxUbicacion.setText("");
		bdxUbicacion.setTooltiptext("");
		txtBuscarUbicacion.setText("");
		cmbCategoria.setText("");
	}

	public String generarLogModificacionInventario(modelo_articulo_dn articulo_antes,
			modelo_articulo_dn articulo_despues, modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion) {
		String log = "";
		List<String> listaLogs = new ArrayList<String>();
		if (!articulo_antes.getCod_articulo().equals(articulo_despues.getCod_articulo())) {
			log = "<b>* CODIGO ANTES DEL CAMBIO:</b> " + articulo_antes.getCod_articulo()
					+ ", <b>CODIGO DESPUES DEL CAMBIO:</b> " + articulo_despues.getCod_articulo();
			listaLogs.add(log);
		}
		if (!articulo_antes.getDes_articulo().equals(articulo_despues.getDes_articulo())) {
			log = "<b>* DESCRIPCION ANTES DEL CAMBIO:</b> " + articulo_antes.getCod_articulo()
					+ ", <b>DESCRIPCION DESPUES DEL CAMBIO:</b> " + articulo_despues.getCod_articulo();
			listaLogs.add(log);
		}
		if (articulo_antes.getId_categoria() != articulo_despues.getId_categoria()) {
			String nom_categoria_antes = "N/A", nom_categoria_despues = "N/A";
			for (int i = 0; i < listaCategoria.size(); i++) {
				if (articulo_antes.getId_categoria() == listaCategoria.get(i).getId_categoria()) {
					nom_categoria_antes = listaCategoria.get(i).getNom_categoria();
					i = listaCategoria.size() + 1;
				}
			}
			for (int i = 0; i < listaCategoria.size(); i++) {
				if (articulo_despues.getId_categoria() == listaCategoria.get(i).getId_categoria()) {
					nom_categoria_despues = listaCategoria.get(i).getNom_categoria();
					i = listaCategoria.size() + 1;
				}
			}
			log = "<b>* CATEGORIA ANTES DEL CAMBIO:</b> " + nom_categoria_antes
					+ ", <b>CATEGORIA DESPUES DEL CAMBIO:</b> " + nom_categoria_despues;
			listaLogs.add(log);
		}
		if (articulo_antes.getId_capacidad() != articulo_despues.getId_capacidad()) {
			String nom_capacidad_antes = "N/A", nom_capacidad_despues = "N/A";
			for (int i = 0; i < listaCapacidad.size(); i++) {
				if (articulo_antes.getId_capacidad() == listaCapacidad.get(i).getId_capacidad()) {
					nom_capacidad_antes = listaCapacidad.get(i).getNom_capacidad();
					i = listaCapacidad.size() + 1;
				}
			}
			for (int i = 0; i < listaCapacidad.size(); i++) {
				if (articulo_despues.getId_capacidad() == listaCapacidad.get(i).getId_capacidad()) {
					nom_capacidad_despues = listaCapacidad.get(i).getNom_capacidad();
					i = listaCapacidad.size() + 1;
				}
			}
			log = "<b>* CAPACIDAD ANTES DEL CAMBIO:</b> " + nom_capacidad_antes
					+ ", <b>CAPACIDAD DESPUES DEL CAMBIO:</b> " + nom_capacidad_despues;
			listaLogs.add(log);
		}
		if (!articulo_antes.getSi_ing_fec_inicio_fin().equals(articulo_despues.getSi_ing_fec_inicio_fin())) {
			String si_ing_fec_inicio_fin_antes = "NO", si_ing_fec_inicio_fin_despues = "NO";
			if (articulo_antes.getSi_ing_fec_inicio_fin().equals("S")) {
				si_ing_fec_inicio_fin_antes = "SI";
			} else {
				si_ing_fec_inicio_fin_antes = "NO";
			}
			if (articulo_despues.getSi_ing_fec_inicio_fin().equals("S")) {
				si_ing_fec_inicio_fin_despues = "SI";
			} else {
				si_ing_fec_inicio_fin_despues = "NO";
			}
			log = "<b>* DESEA REGISTRAR FECHA DE RESPALDO ANTES DEL CAMBIO:</b> " + si_ing_fec_inicio_fin_antes
					+ ", <b>DESEA REGISTRAR FECHA DE RESPALDO DESPUES DEL CAMBIO:</b> " + si_ing_fec_inicio_fin_despues;
			listaLogs.add(log);
		}
		if (!articulo_antes.getEs_fecha().equals(articulo_despues.getEs_fecha())) {
			String es_fecha_antes = "NO", es_fecha_despues = "NO";
			if (articulo_antes.getEs_fecha().equals("S")) {
				es_fecha_antes = "SI";
			} else {
				es_fecha_antes = "NO";
			}
			if (articulo_despues.getEs_fecha().equals("S")) {
				es_fecha_despues = "SI";
			} else {
				es_fecha_despues = "NO";
			}
			log = "<b>* LA FECHA DE RESPALDO ES DEL TIPO FECHA ANTES DEL CAMBIO:</b> " + es_fecha_antes
					+ ", <b>LA FECHA DE RESPALDO ES DEL TIPO FECHA DESPUES DEL CAMBIO:</b> " + es_fecha_despues;
			listaLogs.add(log);
		}
		if (articulo_antes.getId_fec_respaldo() != articulo_despues.getId_fec_respaldo()) {
			String nom_fec_respaldo_antes = "N/A", nom_fec_respaldo_despues = "N/A";
			for (int i = 0; i < listaRespaldo1.size(); i++) {
				if (articulo_antes.getId_fec_respaldo() == listaRespaldo1.get(i).getId_respaldo()) {
					nom_fec_respaldo_antes = listaRespaldo1.get(i).toNombreRespaldo();
					i = listaRespaldo1.size() + 1;
				}
			}
			for (int i = 0; i < listaRespaldo1.size(); i++) {
				if (articulo_despues.getId_fec_respaldo() == listaRespaldo1.get(i).getId_respaldo()) {
					nom_fec_respaldo_despues = listaRespaldo1.get(i).toNombreRespaldo();
					i = listaRespaldo1.size() + 1;
				}
			}
			log = "<b>* FECHA DE RESPALDO ANTES DEL CAMBIO:</b> " + nom_fec_respaldo_antes
					+ ", <b>FECHA DE RESPALDO DESPUES DEL CAMBIO:</b> " + nom_fec_respaldo_despues;
			listaLogs.add(log);
		}
		if (articulo_antes.getFec_inicio() != null) {
			if (articulo_despues.getFec_inicio() != null) {
				if (!articulo_antes.getFec_inicio().equals(articulo_despues.getFec_inicio())) {
					String fec_antes = "N/A", fec_despues = "N/A";
					if (articulo_antes.getFec_inicio() != null) {
						fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_inicio(), "dd/MM/yyyy");
					} else {
						fec_antes = "N/A";
					}
					if (articulo_despues.getFec_inicio() != null) {
						fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_inicio(), "dd/MM/yyyy");
					} else {
						fec_despues = "N/A";
					}
					log = "<b>* FECHA DE INICIO ANTES DEL CAMBIO:</b> " + fec_antes
							+ ", <b>FECHA DE INICIO DESPUES DEL CAMBIO:</b> " + fec_despues;
					listaLogs.add(log);
				}
			} else {
				String fec_antes = "N/A", fec_despues = "N/A";
				if (articulo_antes.getFec_inicio() != null) {
					fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_inicio(), "dd/MM/yyyy");
				} else {
					fec_antes = "N/A";
				}
				if (articulo_despues.getFec_inicio() != null) {
					fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_inicio(), "dd/MM/yyyy");
				} else {
					fec_despues = "N/A";
				}
				log = "<b>* FECHA DE INICIO ANTES DEL CAMBIO:</b> " + fec_antes
						+ ", <b>FECHA DE INICIO DESPUES DEL CAMBIO:</b> " + fec_despues;
				listaLogs.add(log);
			}
		} else {
			if (articulo_despues.getFec_inicio() != null) {
				String fec_antes = "N/A", fec_despues = "N/A";
				if (articulo_antes.getFec_inicio() != null) {
					fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_inicio(), "dd/MM/yyyy");
				} else {
					fec_antes = "N/A";
				}
				if (articulo_despues.getFec_inicio() != null) {
					fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_inicio(), "dd/MM/yyyy");
				} else {
					fec_despues = "N/A";
				}
				log = "<b>* FECHA DE INICIO ANTES DEL CAMBIO:</b> " + fec_antes
						+ ", <b>FECHA DE INICIO DESPUES DEL CAMBIO:</b> " + fec_despues;
				listaLogs.add(log);
			}
		}
		if (articulo_antes.getFec_fin() != null) {
			if (articulo_despues.getFec_fin() != null) {
				if (!articulo_antes.getFec_fin().equals(articulo_despues.getFec_fin())) {
					String fec_antes = "N/A", fec_despues = "N/A";
					if (articulo_antes.getFec_fin() != null) {
						fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_fin(), "dd/MM/yyyy");
					} else {
						fec_antes = "N/A";
					}
					if (articulo_despues.getFec_fin() != null) {
						fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_fin(), "dd/MM/yyyy");
					} else {
						fec_despues = "N/A";
					}
					log = "<b>* FECHA FIN ANTES DEL CAMBIO:</b> " + fec_antes
							+ ", <b>FECHA FIN DESPUES DEL CAMBIO:</b> " + fec_despues;
					listaLogs.add(log);
				}
			} else {
				String fec_antes = "N/A", fec_despues = "N/A";
				if (articulo_antes.getFec_fin() != null) {
					fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_fin(), "dd/MM/yyyy");
				} else {
					fec_antes = "N/A";
				}
				if (articulo_despues.getFec_fin() != null) {
					fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_fin(), "dd/MM/yyyy");
				} else {
					fec_despues = "N/A";
				}
				log = "<b>* FECHA FIN ANTES DEL CAMBIO:</b> " + fec_antes + ", <b>FECHA FIN DESPUES DEL CAMBIO:</b> "
						+ fec_despues;
				listaLogs.add(log);
			}
		} else {
			if (articulo_despues.getFec_fin() != null) {
				String fec_antes = "N/A", fec_despues = "N/A";
				if (articulo_antes.getFec_fin() != null) {
					fec_antes = fechas.obtenerFechaFormateada(articulo_antes.getFec_fin(), "dd/MM/yyyy");
				} else {
					fec_antes = "N/A";
				}
				if (articulo_despues.getFec_fin() != null) {
					fec_despues = fechas.obtenerFechaFormateada(articulo_despues.getFec_fin(), "dd/MM/yyyy");
				} else {
					fec_despues = "N/A";
				}
				log = "<b>* FECHA FIN ANTES DEL CAMBIO:</b> " + fec_antes + ", <b>FECHA FIN DESPUES DEL CAMBIO:</b> "
						+ fec_despues;
				listaLogs.add(log);
			}
		}
		if (articulo_antes.getId_tip_respaldo() != articulo_despues.getId_tip_respaldo()) {
			String nom_tip_respaldo_antes = "N/A", nom_tip_respaldo_despues = "N/A";
			for (int i = 0; i < listaRespaldo.size(); i++) {
				if (articulo_antes.getId_tip_respaldo() == listaRespaldo.get(i).getId_respaldo()) {
					nom_tip_respaldo_antes = listaRespaldo.get(i).toNombreRespaldo();
					i = listaRespaldo.size() + 1;
				}
			}
			for (int i = 0; i < listaRespaldo.size(); i++) {
				if (articulo_despues.getId_tip_respaldo() == listaRespaldo.get(i).getId_respaldo()) {
					nom_tip_respaldo_despues = listaRespaldo.get(i).toNombreRespaldo();
					i = listaRespaldo.size() + 1;
				}
			}
			log = "<b>* TIPO DE RESPALDO ANTES DEL CAMBIO:</b> " + nom_tip_respaldo_antes
					+ ", <b>TIPO DE RESPALDO DESPUES DEL CAMBIO:</b> " + nom_tip_respaldo_despues;
			listaLogs.add(log);
		}
		if (!articulo_antes.getId_contenedor().equals(articulo_despues.getId_contenedor())) {
			String id_contenedor_antes = "N/A", id_contenedor_despues = "N/A";
			if (articulo_antes.getId_contenedor() != null) {
				if (articulo_antes.getId_contenedor().length() > 0) {
					id_contenedor_antes = articulo_antes.getId_contenedor();
				}
			}
			if (articulo_despues.getId_contenedor() != null) {
				if (articulo_despues.getId_contenedor().length() > 0) {
					id_contenedor_despues = articulo_despues.getId_contenedor();
				}
			}
			log = "<b>* ID CONTENEDOR ANTES DEL CAMBIO:</b> " + id_contenedor_antes
					+ ", <b>ID CONTENEDOR DESPUES DEL CAMBIO:</b> " + id_contenedor_despues;
			listaLogs.add(log);
		}
		if (articulo_antes.getId_ubicacion() != relacion_articulo_ubicacion.getId_ubicacion()) {
			String nom_ubicacion_antes = "N/A", nom_ubicacion_despues = "N/A";
			for (int i = 0; i < listaUbicacion.size(); i++) {
				if (articulo_antes.getId_ubicacion() == listaUbicacion.get(i).getId_ubicacion()) {
					nom_ubicacion_antes = listaUbicacion.get(i).obtenerNombreUbicacion();
					i = listaUbicacion.size() + 1;
				}
			}
			for (int i = 0; i < listaUbicacion.size(); i++) {
				if (relacion_articulo_ubicacion.getId_ubicacion() == listaUbicacion.get(i).getId_ubicacion()) {
					nom_ubicacion_despues = listaUbicacion.get(i).obtenerNombreUbicacion();
					i = listaUbicacion.size() + 1;
				}
			}
			log = "<b>* UBICACION ANTES DEL CAMBIO:</b> " + nom_ubicacion_antes
					+ ", <b>UBICACION DESPUES DEL CAMBIO:</b> " + nom_ubicacion_despues;
			listaLogs.add(log);
		}
		log = "LOS CAMBIOS REALIZADOS EN EL ARTICULO <b>" + articulo_antes.getCod_articulo() + "</b> SON:</br>  ";
		if (listaLogs.size() > 0) {
			for (int i = 0; i < listaLogs.size(); i++) {
				log = log + listaLogs.get(i) + ".</br> ";
			}
		} else {
			log = "";
		}
		return log.trim();
	}

}
