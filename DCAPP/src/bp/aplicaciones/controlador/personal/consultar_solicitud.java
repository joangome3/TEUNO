package bp.aplicaciones.controlador.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;

import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar_solicitud extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxSolicitudesPersonal;
	@Wire
	Combobox cmbLimite, cmbCliente, cmbTurno, cmbEstado;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Menuitem mModificar, mEliminar, mCerrar;
	@Wire
	Div winList;

	Window window;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_parametros_generales_5> listaParametros5 = new ArrayList<modelo_parametros_generales_5>();
	List<modelo_solicitud_personal> listaSolicitudPersonal = new ArrayList<modelo_solicitud_personal>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	boolean cumple_condicion_modificar = false;
	boolean cumple_condicion_eliminar = false;

	boolean ingresa_a_modificar = false;

	long id_opcion = 5;
	long id_turno = 0;

	boolean es_turno_extendido = false;
	boolean se_inicia_turno = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	String turno = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(3);
		cmbEstado.setSelectedIndex(0);
		inicializarListas();
		inicializarPermisos();
		setearFechaActual();
		setearFechaInicio();
		setearFechaFin();
		validarTurno();
		if (cmbCliente.getItems().size() > 0) {
			cmbCliente.setSelectedIndex(1);
		}
		if (consultar.equals("S")) {
			setearSolicitudesPersonal();
			txtBuscar.setDisabled(false);
			lbxSolicitudesPersonal.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxSolicitudesPersonal.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public List<modelo_parametros_generales_5> obtenerParametros5() {
		return listaParametros5;
	}

	public List<modelo_perfil> obtenerPerfil() {
		return listaPerfil;
	}

	public List<modelo_empresa> obtenerClientes() {
		return listaCliente;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_registra_turno> obtenerRegistroTurno() {
		return listaRegistroTurno;
	}

	public List<modelo_solicitud_personal> obtenerSolicitudes() {
		return listaSolicitudPersonal;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
		listaParametros5 = consultasABaseDeDatos.cargarParametros5("", String.valueOf(id_opcion), 2);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbTurno);
	}

	public void setearSolicitudesPersonal()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		String turno = "";
		long id_cliente = 0;
		long id_estado = 1;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (cmbTurno.getSelectedItem() != null) {
			turno = cmbTurno.getSelectedItem().getValue().toString();
		}
		if (cmbEstado.getSelectedItem() != null) {
			id_estado = Long.valueOf(cmbEstado.getSelectedItem().getValue().toString());
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		listaSolicitudPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(criterio, 1, id_cliente, fecha_inicio,
				fecha_fin, fecha_inicio, fecha_fin, id_dc,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxSolicitudesPersonal);
	}

	public void inicializarPermisos() {
		if (listaPerfil.size() == 1) {
			if (listaPerfil.get(0).getConsultar().equals("S")) {
				consultar = "S";
			} else {
				consultar = "N";
			}
			if (listaPerfil.get(0).getInsertar().equals("S")) {
				insertar = "S";
			} else {
				insertar = "N";
			}
			if (listaPerfil.get(0).getModificar().equals("S")) {
				modificar = "S";
			} else {
				modificar = "N";
			}
			if (listaPerfil.get(0).getRelacionar().equals("S")) {
				relacionar = "S";
			} else {
				relacionar = "N";
			}
			if (listaPerfil.get(0).getDesactivar().equals("S")) {
				desactivar = "S";
			} else {
				desactivar = "N";
			}
			if (listaPerfil.get(0).getEliminar().equals("S")) {
				eliminar = "S";
			} else {
				eliminar = "N";
			}
			if (listaPerfil.get(0).getSolicitar().equals("S")) {
				solicitar = "S";
			} else {
				solicitar = "N";
			}
			if (listaPerfil.get(0).getRevisar().equals("S")) {
				revisar = "S";
			} else {
				revisar = "N";
			}
			if (listaPerfil.get(0).getAprobar().equals("S")) {
				aprobar = "S";
			} else {
				aprobar = "N";
			}
			if (listaPerfil.get(0).getEjecutar().equals("S")) {
				ejecutar = "S";
			} else {
				ejecutar = "N";
			}
		} else {
			Messagebox.show(error.getMensaje_error_3(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
	}

	public void validarTurno() {
		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;
		Date d5 = null;
		Date d6 = null;
		d1 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), new Date().getSeconds());
		for (int i = 0; i < listaTurno.size(); i++) {
			if (listaTurno.get(i).getEs_extendido().equals("N")) {
				es_turno_extendido = false;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 23, 59, 59);
				d4 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				d5 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				Date someDate = new Date();
				Date newDate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1));
				d6 = fechas.obtenerFechaArmada(newDate, newDate.getMonth(), newDate.getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d6;
				if ((d2.before(d1) && d1.before(d3)) || (d4.before(d1) && d1.before(d5))) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			}
		}
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaInicio() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		LocalDateTime localDateTime = null;
		LocalDate localDate = primerDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, 1, 1, 0, 0);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaInicio.setValue(d);
	}

	public void setearFechaFin() {
		Date fechaActual = new Date();
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		LocalDateTime localDateTime = null;
		LocalDate localDate = ultimoDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, 12, 31, 23, 59);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaFin.setValue(d);
	}

	public void buscarSolicitudesPersonal(String criterio, int tipo, long id_cliente, String fecha, String turno,
			String fecha_inicio, String fecha_fin, long id_estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
//		if (txtBuscar.getText().length() <= 0) {
//			listaSolicitudPersonal = consultasABaseDeDatos.cargarTareasProveedores(criterio, tipo, id_cliente, fecha,
//					turno, id_dc, fecha_inicio, fecha_fin, id_estado,
//					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		}
//		if (!txtBuscar.getValue().equals("")) {
//			listaSolicitudPersonal = consultasABaseDeDatos.cargarTareasProveedores(criterio, tipo, id_cliente, fecha,
//					turno, id_dc, fecha_inicio, fecha_fin, id_estado,
//					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		}
		binder.loadComponent(lbxSolicitudesPersonal);
	}

	@Listen("onClick=#mModificar")
	public void onClickmModificar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		onDoubleClicklbxSolicitudesPersonal();
	}

	@Listen("onDoubleClick=#lbxSolicitudesPersonal")
	public void onDoubleClicklbxSolicitudesPersonal()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			if (lbxSolicitudesPersonal.getSelectedItems().size() > 1) {
				ingresa_a_modificar = false;
				Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			int indice = lbxSolicitudesPersonal.getSelectedIndex();
			// Sessions.getCurrent().setAttribute("tarea_proveedor",
			// listaSolicitudPersonal.get(indice));
			window = (Window) Executions.createComponents("/bitacora/tarea_proveedor/modificar.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_modificar = false;
						consultarSolicitudesPersonal();
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	public void consultarSolicitudesPersonal() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		long id_cliente = 0;
		long id_estado = 1;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (cmbEstado.getSelectedItem() != null) {
			id_estado = Long.valueOf(cmbEstado.getSelectedItem().getValue().toString());
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		buscarSolicitudesPersonal(criterio, 1, id_cliente, "", "", fecha_inicio, fecha_fin, id_estado);
	}

	@Listen("onClick=#mCerrar")
	public void onClickmCerrar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		onClickbtnActualizarEstado();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnActualizarEstado")
	public void onClickbtnActualizarEstado()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		List<modelo_tarea_proveedor> _listaSolicitudPersonal;
		_listaSolicitudPersonal = actualizarEstadoConPermiso();
		if (cumple_condicion_modificar == false) {
			Messagebox.show(informativos.getMensaje_informativo_29(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (_listaSolicitudPersonal.size() == 0 && cumple_condicion_modificar == true) {
			Messagebox.show(informativos.getMensaje_informativo_28(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_tarea_proveedor dao = new dao_tarea_proveedor();
							try {
								// dao.cambiarEstadoSolicitudesPersonalMasivo(_listaSolicitudPersonal);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								recargarlistaSolicitudPersonalDespuesActualizar();
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(), informativos.getMensaje_informativo_24(),
										Messagebox.OK, Messagebox.ERROR);
							}
						} else {
							binder.loadComponent(lbxSolicitudesPersonal);
						}
					}
				});
	}

	@Listen("onClick=#mEliminar")
	public void onClickmEliminar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		onClickbtnEliminarRegistros();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnEliminarRegistros")
	public void onClickbtnEliminarRegistros()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		List<modelo_tarea_proveedor> _listaSolicitudPersonal;
		_listaSolicitudPersonal = eliminarRegistroConPermiso();
		if (cumple_condicion_eliminar == false) {
			Messagebox.show(informativos.getMensaje_informativo_31(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (_listaSolicitudPersonal.size() == 0 && cumple_condicion_eliminar == true) {
			Messagebox.show(informativos.getMensaje_informativo_30(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		boolean existe_tarea_en_log_eventos = false;
		Iterator<modelo_tarea_proveedor> it = _listaSolicitudPersonal.iterator();
		while (it.hasNext()) {
			modelo_tarea_proveedor tarea_proveedor = it.next();
			if (validarSiTareaExiste(tarea_proveedor.getTicket_externo()) == true) {
				existe_tarea_en_log_eventos = true;
				break;
			}
		}
		if (existe_tarea_en_log_eventos == true) {
			Messagebox.show(informativos.getMensaje_informativo_62(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_27(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_tarea_proveedor dao = new dao_tarea_proveedor();
							try {
								dao.eliminarTareasProgranadasMasivo(_listaSolicitudPersonal);
								Messagebox.show(informativos.getMensaje_informativo_59(),
										informativos.getMensaje_informativo_27(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								recargarlistaSolicitudPersonalDespuesEliminar();
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(), informativos.getMensaje_informativo_27(),
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	public boolean consultarPermisoUsuario() {
		boolean tiene_permiso = false;
		Iterator<modelo_parametros_generales_5> it = listaParametros5.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_5 modelo = it.next();
			if (modelo.getId_usuario() == id_user) {
				tiene_permiso = true;
				break;
			}
		}
		return tiene_permiso;
	}

	public boolean consultarPermisoServicio(long id_tipo_servicio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tiene_permiso = false;
		List<modelo_parametros_generales_10> listaParametros = new ArrayList<modelo_parametros_generales_10>();
		listaParametros = consultasABaseDeDatos.cargarParametros10(String.valueOf(id_opcion),
				String.valueOf(id_tipo_servicio), String.valueOf(id_dc), 1);
		Iterator<modelo_parametros_generales_10> it = listaParametros.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_10 modelo = it.next();
			if (modelo.getId_tipo_servicio() == id_tipo_servicio) {
				if (modelo.getSe_puede_eliminar().equals("S")) {
					tiene_permiso = true;
				} else {
					tiene_permiso = false;
				}
				break;
			}
		}
		return tiene_permiso;
	}

	public List<modelo_tarea_proveedor> actualizarEstadoSinPermiso() {
		List<modelo_tarea_proveedor> _listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
		Iterator<Listitem> it = lbxSolicitudesPersonal.getSelectedItems().iterator();
		cumple_condicion_modificar = false;
//		while (it.hasNext()) {
//			modelo_tarea_proveedor registro = new modelo_tarea_proveedor();
//			Listitem item = it.next();
//			int indice = item.getIndex();
//			Date d1 = listaSolicitudPersonal.get(indice).getFec_inicio();
//			Date d2 = listaSolicitudPersonal.get(indice).getFec_fin();
//			if ((fecha_inicio.before(d1) || fecha_inicio.equals(d1)) && (d1.before(d2) || d1.equals(d2))
//					&& (d2.before(fecha_fin) || d2.equals(fecha_fin))) {
//				registro = listaSolicitudPersonal.get(indice);
//				if (registro.getId_estado_bitacora() == 1) {
//					modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
//					tarea_proveedor = registro.clone();
//					tarea_proveedor.setId_estado_bitacora(2);
//					tarea_proveedor.setUsu_modifica(user);
//					tarea_proveedor.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
//					_listaSolicitudPersonal.add(tarea_proveedor);
//				}
//				cumple_condicion_modificar = true;
//			} else {
//				_listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
//				cumple_condicion_modificar = false;
//				break;
//			}
//		}
		return _listaSolicitudPersonal;
	}

	public List<modelo_tarea_proveedor> actualizarEstadoConPermiso() {
		List<modelo_tarea_proveedor> _listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
		Iterator<Listitem> it = lbxSolicitudesPersonal.getSelectedItems().iterator();
		cumple_condicion_modificar = false;
//		while (it.hasNext()) {
//			modelo_tarea_proveedor registro = new modelo_tarea_proveedor();
//			Listitem item = it.next();
//			int indice = item.getIndex();
//			registro = listaSolicitudPersonal.get(indice);
//			if (registro.getId_estado_bitacora() == 1) {
//				modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
//				tarea_proveedor = registro.clone();
//				tarea_proveedor.setId_estado_bitacora(2);
//				tarea_proveedor.setUsu_modifica(user);
//				tarea_proveedor.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
//				_listaSolicitudPersonal.add(tarea_proveedor);
//			}
//			cumple_condicion_modificar = true;
//		}
		return _listaSolicitudPersonal;
	}

	public List<modelo_tarea_proveedor> eliminarRegistroSinPermiso()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_tarea_proveedor> _listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
		Iterator<Listitem> it = lbxSolicitudesPersonal.getSelectedItems().iterator();
		cumple_condicion_eliminar = false;
//		while (it.hasNext()) {
//			modelo_tarea_proveedor registro = new modelo_tarea_proveedor();
//			Listitem item = it.next();
//			int indice = item.getIndex();
//			Date d1 = listaSolicitudPersonal.get(indice).getFec_inicio();
//			Date d2 = listaSolicitudPersonal.get(indice).getFec_fin();
//			if ((fecha_inicio.before(d1) || fecha_inicio.equals(d1)) && (d1.before(d2) || d1.equals(d2))
//					&& (d2.before(fecha_fin) || d2.equals(fecha_fin))) {
//				registro = listaSolicitudPersonal.get(indice);
//				if (consultarPermisoServicio(registro.getId_tipo_servicio()) == true) {
//					_listaSolicitudPersonal.add(registro);
//				} else {
//					_listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
//					cumple_condicion_eliminar = true;
//					break;
//				}
//				cumple_condicion_eliminar = true;
//			} else {
//				_listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
//				cumple_condicion_eliminar = false;
//				break;
//			}
//		}
		return _listaSolicitudPersonal;
	}

	public List<modelo_tarea_proveedor> eliminarRegistroConPermiso() {
		List<modelo_tarea_proveedor> _listaSolicitudPersonal = new ArrayList<modelo_tarea_proveedor>();
		Iterator<Listitem> it = lbxSolicitudesPersonal.getSelectedItems().iterator();
		cumple_condicion_eliminar = false;
//		while (it.hasNext()) {
//			modelo_tarea_proveedor registro = new modelo_tarea_proveedor();
//			Listitem item = it.next();
//			int indice = item.getIndex();
//			registro = listaSolicitudPersonal.get(indice);
//			_listaSolicitudPersonal.add(registro);
//			cumple_condicion_eliminar = true;
//		}
		return _listaSolicitudPersonal;
	}

	public void recargarlistaSolicitudPersonalDespuesActualizar()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	public void recargarlistaSolicitudPersonalDespuesEliminar()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	public boolean validarSiTareaExiste(String ticket_externo)
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		List<modelo_bitacora> listatareas = new ArrayList<modelo_bitacora>();
		listatareas = consultasABaseDeDatos.cargarBitacoras(ticket_externo, 4, 0, "", "", id_dc, "", "", 0, "", 0);
		if (listatareas.size() > 0) {
			existe_tarea = true;
		} else {
			existe_tarea = false;
		}
		return existe_tarea;
	}

}
