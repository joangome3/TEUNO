package bp.aplicaciones.controlador.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_trabajo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.personal.DAO.dao_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_detalle_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_aprobador;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_dispositivo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ingreso;

@SuppressWarnings({ "serial", "deprecation" })
public class alcance_solicitud extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Textbox txtId, txtDescripcion, txtBuscarSolicitante, txtBuscarProveedor, txtTicket;
	@Wire
	Bandbox bdxSolicitantes, bdxProveedores, bdxArea, bdxRack;
	@Wire
	Combobox cmbCliente, cmbTipoIngreso, cmbTipoAprobador, cmbEstado, cmbTurno, cmbUsuario, cmbTipoTrabajo, cmbEmpresa;
	@Wire
	Datebox dtxFechaSolicitud, dtxFechaRespuesta, dtxFechaInicio, dtxFechaFin;
	@Wire
	Label lDescripcion, lOperador;
	@Wire
	Listbox lbxSolicitantes, lbxProveedores1, lbxProveedores2;
	@Wire
	Div winList;

	Window window;

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
	List<modelo_solicitante> listaProveedor = new ArrayList<modelo_solicitante>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_9> listaParametros9 = new ArrayList<modelo_parametros_generales_9>();
	List<modelo_parametros_generales_10> listaParametros10 = new ArrayList<modelo_parametros_generales_10>();
	List<modelo_parametros_generales_11> listaParametros11 = new ArrayList<modelo_parametros_generales_11>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_turno> listaTurnos1 = new ArrayList<modelo_turno>();
	List<modelo_turno> listaTurnos2 = new ArrayList<modelo_turno>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_tipo_ingreso> listaTipoIngreso = new ArrayList<modelo_tipo_ingreso>();
	List<modelo_tipo_trabajo> listaTipoTrabajo = new ArrayList<modelo_tipo_trabajo>();
	List<modelo_tipo_aprobador> listaTipoAprobador = new ArrayList<modelo_tipo_aprobador>();
	List<modelo_tipo_dispositivo> listaTipoDispositivo = new ArrayList<modelo_tipo_dispositivo>();
	List<modelo_tipo_ubicacion> listaTipoUbicacion = new ArrayList<modelo_tipo_ubicacion>();
	List<modelo_rack> listaRack = new ArrayList<modelo_rack>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	Date fecha_ingresa_formulario = null;

	long id = 0;
	long id_opcion = 5;
	long id_turno = 0;
	long tipo_trabajo = 0;

	boolean ingresa_a_relacionar_ticket = false;
	boolean ingresa_a_area_rack = false;

	boolean es_turno_extendido = false;

	String ticket = "";

	String id_tipo_ubicacion = "";

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_solicitud_personal solicitud_personal = (modelo_solicitud_personal) Sessions.getCurrent()
			.getAttribute("solicitud_personal");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		setearFechaActual();
		setearFechaIngresaFormulario();
		Sessions.getCurrent().removeAttribute("solicitud_personal");
		ticket = solicitud_personal.getTicket();
		lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxProveedores1.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxProveedores2.setEmptyMessage(informativos.getMensaje_informativo_2());
		inicializarListas();
		validarTurno();
		cargarInformacionCabecera();
		cargarInformacionDetalle(String.valueOf(solicitud_personal.getId_solicitud()));
		txtTicket.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicket.setText(txtTicket.getText().trim().toUpperCase().trim());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
				lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
			}
		});
		setearUsuario();
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_parametros_generales_9> obtenerParametros9() {
		return listaParametros9;
	}

	public List<modelo_parametros_generales_10> obtenerParametros10() {
		return listaParametros10;
	}

	public List<modelo_parametros_generales_11> obtenerParametros11() {
		return listaParametros11;
	}

	public List<modelo_turno> obtenerTurnos1() {
		return listaTurnos1;
	}

	public List<modelo_turno> obtenerTurnos2() {
		return listaTurnos2;
	}

	public List<modelo_empresa> obtenerClientes() {
		return listaCliente;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_tipo_ingreso> obtenerTipoIngresos() {
		return listaTipoIngreso;
	}

	public List<modelo_tipo_aprobador> obtenerTipoAprobadores() {
		return listaTipoAprobador;
	}

	public List<modelo_tipo_trabajo> obtenerTipoTrabajos() {
		return listaTipoTrabajo;
	}

	public List<modelo_tipo_dispositivo> obtenerTipoDispositivos() {
		return listaTipoDispositivo;
	}

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public List<modelo_solicitante> obtenerProveedores() {
		return listaProveedor;
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaUsuario = consultasABaseDeDatos.cargarUsuarios(String.valueOf(id_dc), 4, 0);
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		listaProveedor = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaEmpresa = consultasABaseDeDatos.cargarEmpresas("", 10, String.valueOf(id_dc), "", 0);
		listaTurnos1 = consultasABaseDeDatos.cargarTurnos("");
		listaTurnos2 = consultasABaseDeDatos.cargarTurnos("A");
		listaTipoIngreso = consultasABaseDeDatos.cargarTipoIngresos("");
		listaTipoAprobador = consultasABaseDeDatos.cargarTipoAprobadores("");
		listaTipoTrabajo = consultasABaseDeDatos.cargarTipoTrabajos("");
		binder.loadComponent(lbxSolicitantes);
		binder.loadComponent(lbxProveedores1);
		binder.loadComponent(cmbUsuario);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbEmpresa);
		binder.loadComponent(cmbEstado);
		binder.loadComponent(cmbTurno);
		binder.loadComponent(cmbTipoIngreso);
		binder.loadComponent(cmbTipoAprobador);
		binder.loadComponent(cmbTipoTrabajo);
	}

	public void cargarInformacionCabecera() throws ClassNotFoundException, FileNotFoundException, IOException {
		obtenerId();
		setearCliente(solicitud_personal.getId_cliente());
		setearTipoAprobador(solicitud_personal.getId_tipo_aprobador());
		// setearSolicitante(solicitud_personal.getId_solicitante());
		// txtTicket.setText(solicitud_personal.getTicket());
		setearTipoIngreso(solicitud_personal.getId_tipo_ingreso());
		// dtxFechaSolicitud.setValue(fechas.obtenerFechaDeUnLong(solicitud_personal.getFec_solicitud().getTime()));
		// dtxFechaRespuesta.setValue(fechas.obtenerFechaDeUnLong(solicitud_personal.getFec_respuesta().getTime()));
		setearFechaHoraSolicitud();
		setearFechaHoraRespuesta();
		// setearFechaHoraInicio();
		// setearFechaHoraFin();
		if (cmbEstado.getItems().size() > 1) {
			cmbEstado.setSelectedIndex(0);
		}
		dtxFechaInicio.setValue(fechas.obtenerFechaDeUnLong(solicitud_personal.getFec_inicio().getTime()));
		dtxFechaFin.setValue(fechas.obtenerFechaDeUnLong(solicitud_personal.getFec_fin().getTime()));
		/* AREA */
		id_tipo_ubicacion = solicitud_personal.getId_area();
		bdxArea.setText(solicitud_personal.getArea());
		bdxArea.setTooltiptext(solicitud_personal.getArea());
		cargarUbicaciones();
		/* RACK */
		bdxRack.setText(solicitud_personal.getRack());
		bdxRack.setTooltiptext(solicitud_personal.getRack());
		setearTipoTrabajo(solicitud_personal.getId_tipo_trabajo());
		txtDescripcion.setText(solicitud_personal.getDescripcion());
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
	}

	public void cargarInformacionDetalle(String id_solicitud)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_detalle_solicitud_personal> listaDetalleSolicitudPersonal = consultasABaseDeDatos
				.cargarDetalleSolicitudPersonal(id_solicitud, 2);
		listaTipoDispositivo = consultasABaseDeDatos.cargarTipoDispositivos("");
		for (int i = 0; i < listaDetalleSolicitudPersonal.size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Combobox cmbDispositivo;
			Comboitem cItem;
			Button btnEliminar;
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaDetalleSolicitudPersonal.get(i).getId_proveedor()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* DOCUMENTO */
			lCell = new Listcell();
			lCell.setLabel(listaDetalleSolicitudPersonal.get(i).getNum_documento_proveedor());
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* NOMBRE */
			lCell = new Listcell();
			lCell.setLabel(listaDetalleSolicitudPersonal.get(i).getNom_proveedor());
			lItem.appendChild(lCell);
			/* EMPRESA */
			lCell = new Listcell();
			lCell.setLabel(listaDetalleSolicitudPersonal.get(i).getNom_emp_proveedor());
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* DISPOSITIVO */
			lCell = new Listcell();
			cmbDispositivo = new Combobox();
			for (int j = 0; j < listaTipoDispositivo.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoDispositivo.get(j).getNom_tipo_dispositivo());
				cItem.setValue(listaTipoDispositivo.get(j).getId_tipo_dispositivo());
				cmbDispositivo.appendChild(cItem);
			}
			Iterator<modelo_tipo_dispositivo> it = listaTipoDispositivo.iterator();
			while (it.hasNext()) {
				modelo_tipo_dispositivo tipo_dispositivo = it.next();
				if (tipo_dispositivo.getId_tipo_dispositivo() == listaDetalleSolicitudPersonal.get(i)
						.getId_dispositivo()) {
					cmbDispositivo.setText(tipo_dispositivo.getNom_tipo_dispositivo());
					break;
				}
			}
			cmbDispositivo.setReadonly(true);
			cmbDispositivo.setWidth("160px");
			lCell.appendChild(cmbDispositivo);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setImage("/img/botones/ButtonClose.png");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxProveedores2.removeItemAt(lItem.getIndex());
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxProveedores2.appendChild(lItem);
		}
	}

	public void cargarUbicaciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_tipo_ubicacion> _listaTipoUbicacion = new ArrayList<modelo_tipo_ubicacion>();
		_listaTipoUbicacion = consultasABaseDeDatos.cargarTipoUbicaciones("", 0, 1);
		List<Long> id_ubicaciones = new ArrayList<Long>();
		if (solicitud_personal.getId_area() != null) {
			if (solicitud_personal.getId_area().length() > 0) {
				String[] ubicaciones = solicitud_personal.getId_area().split(", ");
				// System.out.println(ubicaciones.length);
				for (int i = 0; i < ubicaciones.length; i++) {
					id_ubicaciones.add(Long.valueOf(ubicaciones[i]));
				}
				// System.out.println(tipo_trabajo);
				for (int i = 0; i < _listaTipoUbicacion.size(); i++)
					for (int j = 0; j < id_ubicaciones.size(); j++) {
						// System.out.println(id_ubicaciones.get(i));
						if (_listaTipoUbicacion.get(i).getId_tipo_ubicacion() == id_ubicaciones.get(j)) {
							listaTipoUbicacion.add(_listaTipoUbicacion.get(i));
						}
					}
			}
		}
	}

	public void setearCliente(long id_empresa) {
		Iterator<modelo_empresa> it = listaCliente.iterator();
		while (it.hasNext()) {
			modelo_empresa empresa = it.next();
			if (empresa.getId_empresa() == id_empresa) {
				cmbCliente.setText(empresa.getNom_empresa());
				break;
			}
		}
	}

	public void setearTipoDispositivo(long id_tipo_dispositivo) {

	}

	public void setearTipoIngreso(long id_tipo_ingreso) {
		Iterator<modelo_tipo_ingreso> it = listaTipoIngreso.iterator();
		while (it.hasNext()) {
			modelo_tipo_ingreso tipo_ingreso = it.next();
			if (tipo_ingreso.getId_tipo_ingreso() == id_tipo_ingreso) {
				cmbTipoIngreso.setText(tipo_ingreso.getNom_tipo_ingreso());
				break;
			}
		}
	}

	public void setearTipoAprobador(long id_tipo_aprobador) {
		Iterator<modelo_tipo_aprobador> it = listaTipoAprobador.iterator();
		while (it.hasNext()) {
			modelo_tipo_aprobador tipo_aprobador = it.next();
			if (tipo_aprobador.getId_tipo_aprobador() == id_tipo_aprobador) {
				cmbTipoAprobador.setText(tipo_aprobador.getNom_tipo_aprobador());
				break;
			}
		}
	}

	public void setearTipoTrabajo(long id_tipo_trabajo) {
		Iterator<modelo_tipo_trabajo> it = listaTipoTrabajo.iterator();
		while (it.hasNext()) {
			modelo_tipo_trabajo tipo_trabajo = it.next();
			if (tipo_trabajo.getId_tipo_trabajo() == id_tipo_trabajo) {
				cmbTipoTrabajo.setText(tipo_trabajo.getNom_tipo_trabajo());
				break;
			}
		}
	}

	public void setearSolicitante(long id_solicitante)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		binder.loadComponent(lbxSolicitantes);
		Iterator<modelo_solicitante> it = listaSolicitante.iterator();
		int indice = 0;
		boolean se_encuentra = false;
		while (it.hasNext()) {
			modelo_solicitante modelo = it.next();
			if (modelo.getId_solicitante() == id_solicitante) {
				bdxSolicitantes.setText(modelo.toStringSolicitante());
				lbxSolicitantes.setSelectedIndex(indice);
				se_encuentra = true;
				break;
			}
			indice++;
		}
		if (se_encuentra == false) {
			bdxSolicitantes.setText("");
		}
	}

	public void setearUsuario() {
		Iterator<modelo_usuario> it = listaUsuario.iterator();
		while (it.hasNext()) {
			modelo_usuario modelo = it.next();
			if (modelo.getId_usuario() == id_user) {
				cmbUsuario.setText(modelo.verNombreCompleto());
				break;
			}
		}
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaHoraSolicitud() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		dtxFechaSolicitud.setValue(d);
	}

	public void setearFechaHoraRespuesta() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		dtxFechaRespuesta.setValue(d);
	}

	public void setearFechaHoraInicio() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		dtxFechaInicio.setValue(d);
	}

	public void setearFechaHoraFin() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		dtxFechaFin.setValue(d);
	}

	public void setearFechaIngresaFormulario() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		fecha_ingresa_formulario = d;
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
		for (int i = 0; i < listaTurnos2.size(); i++) {
			if (listaTurnos2.get(i).getEs_extendido().equals("N")) {
				es_turno_extendido = false;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurnos2.get(i).getHora_inicio().getHours(),
						listaTurnos2.get(i).getHora_inicio().getMinutes(),
						listaTurnos2.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurnos2.get(i).getHora_fin().getHours(), listaTurnos2.get(i).getHora_fin().getMinutes(),
						listaTurnos2.get(i).getHora_fin().getSeconds());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
					cmbTurno.setText(listaTurnos2.get(i).getNom_turno());
					id_turno = listaTurnos2.get(i).getId_turno();
					i = listaTurnos2.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurnos2.get(i).getHora_inicio().getHours(),
						listaTurnos2.get(i).getHora_inicio().getMinutes(),
						listaTurnos2.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 23, 59, 59);
				fecha_fin_turno_extendido = d3;
				d4 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 1);
				fecha_inicio_turno_extendido = d4;
				d5 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurnos2.get(i).getHora_fin().getHours(), listaTurnos2.get(i).getHora_fin().getMinutes(),
						listaTurnos2.get(i).getHora_fin().getSeconds());
				d6 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurnos2.get(i).getHora_fin().getHours(), listaTurnos2.get(i).getHora_fin().getMinutes(),
						listaTurnos2.get(i).getHora_fin().getSeconds());
				fecha_fin = d6;
				if ((d2.before(d1) && d1.before(d3)) || (d4.before(d1) && d1.before(d5))) {
					cmbTurno.setText(listaTurnos2.get(i).getNom_turno());
					id_turno = listaTurnos2.get(i).getId_turno();
					i = listaTurnos2.size() + 1;
				}
			}
		}
	}

	public boolean validarSiSeIniciaTurno() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean se_inicio_turno = false;
		listaRegistroTurno = consultasABaseDeDatos.cargarRegistroTurnos("", 2, id_turno,
				fechas.obtenerFechaFormateada(fecha_actual, "yyyy/MM/dd"), id_dc);
		if (listaRegistroTurno.size() == 0) {
			se_inicio_turno = false;
		} else {
			if (listaRegistroTurno.get(0).getEst_registra_turno().equals("A")) {
				se_inicio_turno = true;
			} else {
				se_inicio_turno = false;
			}
		}
		return se_inicio_turno;
	}

	public boolean validarSiExistenTareasVencidas()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existen_tareas_vencidas = false;
		int totalTareasVencidas = consultasABaseDeDatos.validarSiExistenTareasProgramadasVencidas("1");
		if (totalTareasVencidas > 0) {
			existen_tareas_vencidas = true;
		} else {
			existen_tareas_vencidas = false;
		}
		return existen_tareas_vencidas;
	}

	public boolean validarSiExisteSolicitudPersonal(String ticket)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud = false;
		int totalSolicitudes = consultasABaseDeDatos.validarSiExisteSolicitudPersonal(ticket);
		if (totalSolicitudes > 0) {
			existe_solicitud = true;
		} else {
			existe_solicitud = false;
		}
		return existe_solicitud;
	}

	public boolean validarSiTareaProgramadaExiste(String ticket_externo)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea_programada = false;
		if (consultasABaseDeDatos.validarSiTareaProgramadaExiste(ticket_externo, String.valueOf(1)) > 0) {
			existe_tarea_programada = true;
		} else {
			existe_tarea_programada = false;
		}
		return existe_tarea_programada;
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCliente.getSelectedItem() == null) {
			return;
		}
		bdxRack.setText("");
		bdxRack.setTooltiptext("");
		listaRack = new ArrayList<modelo_rack>();
	}

	@Listen("onOK=#txtBuscarSolicitante")
	public void onOK$txtBuscarSolicitante()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes(txtBuscarSolicitante.getText().toUpperCase(), 8,
				String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		bdxSolicitantes.setText("");
		lbxSolicitantes.clearSelection();
		binder.loadComponent(lbxSolicitantes);
	}

	@Listen("onSelect=#lbxSolicitantes")
	public void onSelect$lbxSolicitantes()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		bdxSolicitantes.setText(listaSolicitante.get(indice).toStringSolicitante());
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		int id_empresa = 0;
		if (cmbEmpresa.getSelectedItem() != null) {
			id_empresa = Integer.valueOf(cmbEmpresa.getSelectedItem().getValue().toString());
		}
		listaProveedor = consultasABaseDeDatos.cargarSolicitantes(txtBuscarProveedor.getText().toUpperCase(), 8,
				String.valueOf(id_dc), String.valueOf(id_opcion), id_empresa);
		bdxProveedores.setText("");
		lbxProveedores1.clearSelection();
		binder.loadComponent(lbxProveedores1);
	}

	@Listen("onOK=#txtBuscarProveedor")
	public void onOK$txtBuscarProveedor()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		int id_empresa = 0;
		if (cmbEmpresa.getSelectedItem() != null) {
			id_empresa = Integer.valueOf(cmbEmpresa.getSelectedItem().getValue().toString());
		}
		listaProveedor = consultasABaseDeDatos.cargarSolicitantes(txtBuscarProveedor.getText().toUpperCase(), 8,
				String.valueOf(id_dc), String.valueOf(id_opcion), id_empresa);
		bdxProveedores.setText("");
		lbxProveedores1.clearSelection();
		binder.loadComponent(lbxProveedores1);
	}

	@Listen("onSelect=#lbxProveedores1")
	public void onSelect$lbxProveedores1()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxProveedores1.getSelectedItem() == null) {
			return;
		}
		int indice = lbxProveedores1.getSelectedIndex();
		bdxProveedores.setText(listaProveedor.get(indice).toStringSolicitante());
	}

	@Listen("onDoubleClick=#lbxProveedores1")
	public void onDoubleClick$lbxProveedores1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		onClick$btnAnadir();
	}

	public boolean validarItemEnLista(long id_articulo) {
		boolean existe = false;
		Listitem lItem;
		Listcell lCell;
		long id;
		for (int i = 0; i < lbxProveedores2.getItems().size(); i++) {
			lItem = lbxProveedores2.getItemAtIndex(i);
			lCell = (Listcell) lItem.getChildren().get(0);
			id = Long.valueOf(lCell.getLabel().toString());
			if (id_articulo == id) {
				existe = true;
				i = lbxProveedores2.getItems().size() + 1;
			}
		}
		return existe;
	}

	public void onClick$btnAnadir()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxProveedores1.getSelectedItem() == null) {
			return;
		}
		int indice = lbxProveedores1.getSelectedIndex();
		if (validarItemEnLista(listaProveedor.get(indice).getId_solicitante()) == true) {
			Messagebox.show("El proveedor ya se encuentra en la lista.", ".:: Nueva solicitud ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		listaTipoDispositivo = consultasABaseDeDatos.cargarTipoDispositivos("");
		Listitem lItem;
		Listcell lCell;
		Combobox cmbDispositivo;
		Comboitem cItem;
		Button btnEliminar;
		lItem = new Listitem();
		/* ID */
		lCell = new Listcell();
		lCell.setLabel(String.valueOf(listaProveedor.get(indice).getId_solicitante()));
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* DOCUMENTO */
		lCell = new Listcell();
		lCell.setLabel(listaProveedor.get(indice).getNum_documento());
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* NOMBRE */
		lCell = new Listcell();
		lCell.setLabel(listaProveedor.get(indice).toStringSolicitante());
		lItem.appendChild(lCell);
		/* EMPRESA */
		lCell = new Listcell();
		lCell.setLabel(listaProveedor.get(indice).getNom_empresa());
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* DISPOSITIVO */
		lCell = new Listcell();
		cmbDispositivo = new Combobox();
		for (int i = 0; i < listaTipoDispositivo.size(); i++) {
			cItem = new Comboitem();
			cItem.setLabel(listaTipoDispositivo.get(i).getNom_tipo_dispositivo());
			cItem.setValue(listaTipoDispositivo.get(i).getId_tipo_dispositivo());
			cmbDispositivo.appendChild(cItem);
		}
		cmbDispositivo.setReadonly(true);
		cmbDispositivo.setWidth("160px");
		if (cmbDispositivo.getItems().size() > 0) {
			cmbDispositivo.setSelectedIndex(0);
		}
		lCell.appendChild(cmbDispositivo);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setImage("/img/botones/ButtonClose.png");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxProveedores2.removeItemAt(lItem.getIndex());
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxProveedores2.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxProveedores1.clearSelection();
		bdxProveedores.setText("");
		txtBuscarProveedor.setText("");
	}

	@Listen("onClick=#bdxArea")
	public void onClick$bdxArea() throws Throwable {
		if (ingresa_a_area_rack == false) {
			ingresa_a_area_rack = true;
			Sessions.getCurrent().setAttribute("lista_area", listaTipoUbicacion);
			window = (Window) Executions.createComponents("/emergentes/area.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_area_rack = false;
						listaTipoUbicacion = (List<modelo_tipo_ubicacion>) Sessions.getCurrent()
								.getAttribute("lista_area");
						setearAreas(listaTipoUbicacion);
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#bdxRack")
	public void onClick$bdxRack() throws Throwable {
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			ingresa_a_area_rack = false;
			return;
		}
		id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		if (ingresa_a_area_rack == false) {
			ingresa_a_area_rack = true;
			Sessions.getCurrent().setAttribute("cliente", id_cliente);
			Sessions.getCurrent().setAttribute("lista_rack", listaRack);
			window = (Window) Executions.createComponents("/emergentes/rack.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_area_rack = false;
						listaRack = (List<modelo_rack>) Sessions.getCurrent().getAttribute("lista_rack");
						setearRacks(listaRack);
					}
				});
			}
			window.setParent(winList);
		}
	}

	public void setearAreas(List<modelo_tipo_ubicacion> listaTipoUbicacion) {
		String tipo_ubicacion = "";
		id_tipo_ubicacion = "";
		if (listaTipoUbicacion.size() > 0) {
			for (int i = 0; i < listaTipoUbicacion.size(); i++) {
				if (i == 0) {
					tipo_ubicacion = listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
					id_tipo_ubicacion = String.valueOf(listaTipoUbicacion.get(i).getId_tipo_ubicacion());
				} else {
					tipo_ubicacion = tipo_ubicacion + ", " + listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
					id_tipo_ubicacion = id_tipo_ubicacion + ", "
							+ String.valueOf(listaTipoUbicacion.get(i).getId_tipo_ubicacion());
				}
			}
			bdxArea.setText(tipo_ubicacion);
			bdxArea.setTooltiptext(tipo_ubicacion);
		} else {
			tipo_ubicacion = "";
			id_tipo_ubicacion = "";
			bdxArea.setText(tipo_ubicacion);
			bdxArea.setTooltiptext(tipo_ubicacion);
		}
	}

	public void setearRacks(List<modelo_rack> listaRack) {
		String rack = "";
		if (listaRack.size() > 0) {
			for (int i = 0; i < listaRack.size(); i++) {
				if (i == 0) {
					rack = listaRack.get(i).getCoord_rack();
				} else {
					rack = rack + ", " + listaRack.get(i).getCoord_rack();
				}
			}
		} else {
			rack = "";
		}
		bdxRack.setText(rack);
		bdxRack.setTooltiptext(rack);
	}

	public boolean consultarPermisoUsuario() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tiene_permiso = false;
		List<modelo_parametros_generales_5> listaParametros = new ArrayList<modelo_parametros_generales_5>();
		listaParametros = consultasABaseDeDatos.cargarParametros5("", String.valueOf(id_opcion), 2);
		Iterator<modelo_parametros_generales_5> it = listaParametros.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_5 modelo = it.next();
			if (modelo.getId_usuario() == id_user) {
				tiene_permiso = true;
				break;
			}
		}
		return tiene_permiso;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String mensaje = informativos.getMensaje_informativo_16();
		if (validarSiSeIniciaTurno() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_17(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_17(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setFocus(true);
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			return;
		}
		if (txtTicket.getValue() == null) {
			txtTicket.setFocus(true);
			txtTicket.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (txtTicket.getText().trim().length() <= 0) {
			txtTicket.setFocus(true);
			txtTicket.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (validarSiExistePrimeroApertura(txtTicket.getText().trim(), 1) == true) {
			Messagebox.show(informativos.getMensaje_informativo_102().replace("?1", txtTicket.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExisteSolicitudPersonal(txtTicket.getText().toUpperCase().trim()) == true) {
			Messagebox.show(informativos.getMensaje_informativo_97().replace("?1", txtTicket.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (cmbTipoIngreso.getSelectedItem() == null) {
			cmbTipoIngreso.setFocus(true);
			cmbTipoIngreso.setErrorMessage(validaciones.getMensaje_validacion_33());
			return;
		}
		if (cmbTipoAprobador.getSelectedItem() == null) {
			cmbTipoAprobador.setFocus(true);
			cmbTipoAprobador.setErrorMessage(validaciones.getMensaje_validacion_33());
			return;
		}
		if (lbxSolicitantes.getSelectedItem() == null) {
			bdxSolicitantes.setFocus(true);
			bdxSolicitantes.setErrorMessage(validaciones.getMensaje_validacion_33());
			return;
		}
		if (dtxFechaSolicitud.getValue() == null) {
			dtxFechaSolicitud.setFocus(true);
			dtxFechaSolicitud.setErrorMessage(validaciones.getMensaje_validacion_4());
			return;
		}
		if (dtxFechaRespuesta.getValue() == null) {
			dtxFechaRespuesta.setFocus(true);
			dtxFechaRespuesta.setErrorMessage(validaciones.getMensaje_validacion_4());
			return;
		}
		Date d1 = fechas.obtenerFechaArmada(dtxFechaSolicitud.getValue(), dtxFechaSolicitud.getValue().getMonth(),
				dtxFechaSolicitud.getValue().getDate(), dtxFechaSolicitud.getValue().getHours(),
				dtxFechaSolicitud.getValue().getMinutes(), 0);
		Date d2 = fechas.obtenerFechaArmada(dtxFechaRespuesta.getValue(), dtxFechaRespuesta.getValue().getMonth(),
				dtxFechaRespuesta.getValue().getDate(), dtxFechaRespuesta.getValue().getHours(),
				dtxFechaRespuesta.getValue().getMinutes(), 0);
		if (d2.before(d1)) {
			dtxFechaSolicitud.setFocus(true);
			dtxFechaSolicitud.setErrorMessage(validaciones.getMensaje_validacion_26());
			return;
		}
		if (dtxFechaInicio.getValue() == null) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_4());
			return;
		}
		if (dtxFechaFin.getValue() == null) {
			dtxFechaFin.setFocus(true);
			dtxFechaFin.setErrorMessage(validaciones.getMensaje_validacion_4());
			return;
		}
		d1 = fechas.obtenerFechaArmada(dtxFechaInicio.getValue(), dtxFechaInicio.getValue().getMonth(),
				dtxFechaInicio.getValue().getDate(), dtxFechaInicio.getValue().getHours(),
				dtxFechaInicio.getValue().getMinutes(), 0);
		d2 = fechas.obtenerFechaArmada(dtxFechaFin.getValue(), dtxFechaFin.getValue().getMonth(),
				dtxFechaFin.getValue().getDate(), dtxFechaFin.getValue().getHours(),
				dtxFechaFin.getValue().getMinutes(), 0);
		if (d2.before(d1)) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_10());
			return;
		}
		if (d1.equals(d2)) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_39());
			return;
		}
		if (cmbTipoTrabajo.getSelectedItem() == null) {
			cmbTipoTrabajo.setFocus(true);
			cmbTipoTrabajo.setErrorMessage(validaciones.getMensaje_validacion_33());
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage(validaciones.getMensaje_validacion_2());
			return;
		}
		if (lbxProveedores2.getItems().size() == 0) {
			bdxProveedores.setFocus(true);
			bdxProveedores.setErrorMessage(validaciones.getMensaje_validacion_34());
			return;
		}
		Messagebox.show(mensaje, informativos.getMensaje_informativo_17(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							long secuencia1 = 0, secuencia2 = 0;
							dao_solicitud_personal dao = new dao_solicitud_personal();
							modelo_solicitud_personal solicitud = new modelo_solicitud_personal();
							/* Se inicializa el objeto solicitud */
							solicitud.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							solicitud.setTicket(txtTicket.getText().toUpperCase().trim());
							solicitud.setId_tipo_ingreso(
									Long.valueOf(cmbTipoIngreso.getSelectedItem().getValue().toString()));
							solicitud.setId_tipo_aprobador(
									Long.valueOf(cmbTipoAprobador.getSelectedItem().getValue().toString()));
							solicitud.setId_solicitante(
									listaSolicitante.get(lbxSolicitantes.getSelectedIndex()).getId_solicitante());
							solicitud.setFec_solicitud(fechas.obtenerTimestampDeDate(dtxFechaSolicitud.getValue()));
							solicitud.setFec_respuesta(fechas.obtenerTimestampDeDate(dtxFechaRespuesta.getValue()));
							solicitud.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							solicitud.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							solicitud.setId_area(id_tipo_ubicacion);
							solicitud.setId_rack(bdxRack.getText().toUpperCase().trim());
							solicitud.setArea(bdxArea.getText().toUpperCase().trim());
							solicitud.setRack(bdxRack.getText().toUpperCase().trim());
							solicitud.setId_tipo_trabajo(
									Long.valueOf(cmbTipoTrabajo.getSelectedItem().getValue().toString()));
							solicitud.setDescripcion(txtDescripcion.getText().toUpperCase().trim());
							solicitud.setId_localidad(id_dc);
							solicitud.setEst_solicitud(cmbEstado.getSelectedItem().getValue().toString());
							solicitud.setUsu_ingresa(user);
							solicitud.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							/* Se inicializa el objeto detalle solicitud */
							List<modelo_detalle_solicitud_personal> detalle = new ArrayList<modelo_detalle_solicitud_personal>();
							Listcell lCell;
							Combobox cmBox;
							for (int i = 0; i < lbxProveedores2.getItems().size(); i++) {
								modelo_detalle_solicitud_personal dt = new modelo_detalle_solicitud_personal();
								lCell = (Listcell) lbxProveedores2.getItemAtIndex(i).getChildren().get(0);
								dt.setId_proveedor(Long.valueOf(lCell.getLabel().toString()));
								lCell = (Listcell) lbxProveedores2.getItemAtIndex(i).getChildren().get(4);
								cmBox = (Combobox) lCell.getChildren().get(0);
								dt.setId_dispositivo(Long.valueOf(cmBox.getSelectedItem().getValue().toString()));
								dt.setEst_detalle_solicitud(cmbEstado.getSelectedItem().getValue().toString());
								dt.setUsu_ingresa(user);
								dt.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								detalle.add(dt);
							}
							/* Se inicializa el objeto bitacora */
							modelo_bitacora bitacora = new modelo_bitacora();
							bitacora.setTicket_externo(txtTicket.getText().toUpperCase().trim());
							bitacora.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							bitacora.setId_solicitante(
									listaSolicitante.get(lbxSolicitantes.getSelectedIndex()).getId_solicitante());
							bitacora.setId_tipo_servicio(2);
							if (solicitud.getId_tipo_ingreso() == 1) {
								bitacora.setId_tipo_clasificacion(10);
							}
							if (solicitud.getId_tipo_ingreso() == 2) {
								bitacora.setId_tipo_clasificacion(22);
							}
							if (solicitud.getId_tipo_ingreso() == 3) {
								bitacora.setId_tipo_clasificacion(20);
							}
							if (solicitud.getId_tipo_ingreso() == 4) {
								bitacora.setId_tipo_clasificacion(21);
							}
							bitacora.setId_tipo_tarea(1);
							bitacora.setId_estado_bitacora(2);
							bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaSolicitud.getValue()));
							bitacora.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaRespuesta.getValue()));
							bitacora.setCumplimiento("C");
							bitacora.setId_area(id_tipo_ubicacion);
							bitacora.setId_rack(bdxRack.getText().toString());
							bitacora.setArea(bdxArea.getText().toString());
							bitacora.setRack(bdxRack.getText().toString());
							bitacora.setDescripcion(
									"SE APERTURA LA SOLICITUD DE INGRESO DE PERSONAL, DONDE SE REALIZARÁ "
											+ txtDescripcion.getText().trim() + ", EN LA(S) SIGUIENTE(S) ÁREA(S) "
											+ bdxArea.getText() + ", y RACK(S) " + bdxRack.getText());
							bitacora.setId_turno(id_turno);
							bitacora.setId_localidad(id_dc);
							bitacora.setEst_bitacora("AE");
							bitacora.setUsu_ingresa(user);
							bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							if (listaParametros1.size() > 0) {
								secuencia1 = listaParametros1.get(0).getSecuencia_bitacora();
							}
							/* Se inicializa el objeto tarea proveedor */
							List<modelo_tarea_proveedor> lista_tarea_proveedor = consultasABaseDeDatos
									.cargarTareasProveedores(alcance_solicitud.this.solicitud_personal.getTicket(), 4,
											0, "", "", id_dc, "", "", 0, "", 0, 0);
							modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
							if (lista_tarea_proveedor.size() > 0) {
								tarea_proveedor
										.setId_tarea_proveedor(lista_tarea_proveedor.get(0).getId_tarea_proveedor());
							}
							tarea_proveedor.setTicket_externo(txtTicket.getText().toUpperCase().trim());
							tarea_proveedor
									.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							tarea_proveedor.setId_solicitante(
									listaSolicitante.get(lbxSolicitantes.getSelectedIndex()).getId_solicitante());
							tarea_proveedor.setId_tipo_servicio(2);
							if (solicitud.getId_tipo_ingreso() == 1) {
								tarea_proveedor.setId_tipo_clasificacion(10);
							}
							if (solicitud.getId_tipo_ingreso() == 2) {
								tarea_proveedor.setId_tipo_clasificacion(22);
							}
							if (solicitud.getId_tipo_ingreso() == 3) {
								tarea_proveedor.setId_tipo_clasificacion(20);
							}
							if (solicitud.getId_tipo_ingreso() == 4) {
								tarea_proveedor.setId_tipo_clasificacion(21);
							}
							tarea_proveedor.setId_area(id_tipo_ubicacion);
							tarea_proveedor.setId_rack(bdxRack.getText().toString());
							tarea_proveedor.setArea(bdxArea.getText().toString());
							tarea_proveedor.setRack(bdxRack.getText().toString());
							tarea_proveedor.setId_tipo_tarea(1);
							tarea_proveedor.setId_estado_bitacora(1);
							tarea_proveedor.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							tarea_proveedor.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							tarea_proveedor.setCumplimiento("C");
							tarea_proveedor.setDescripcion(
									"SE REGISTRA LA SOLICITUD DE INGRESO DE PERSONAL, DONDE SE REALIZARÁ "
											+ txtDescripcion.getText().trim() + ", EN LA(S) SIGUIENTE(S) ÁREA(S) "
											+ bdxArea.getText() + ", y RACK(S) " + bdxRack.getText());
							tarea_proveedor.setId_turno(id_turno);
							tarea_proveedor.setId_localidad(id_dc);
							tarea_proveedor.setEst_tarea_proveedor("AE");
							tarea_proveedor.setUsu_ingresa(solicitud_personal.getUsu_ingresa());
							tarea_proveedor.setFec_ingresa(solicitud_personal.getFec_ingresa());
							tarea_proveedor.setUsu_modifica(user);
							tarea_proveedor.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							if (listaParametros1.size() > 0) {
								secuencia2 = listaParametros1.get(0).getSecuencia_tarea_proveedor();
							}
							int bandera = 0;
							if (validarSiTareaProgramadaExiste(txtTicket.getText().toUpperCase().trim()) == true) {
								bandera = 0;
							} else {
								bandera = 1;
							}
							alcance_solicitud.this.solicitud_personal.setEst_solicitud("NE");
							alcance_solicitud.this.solicitud_personal.setUsu_modifica(user);
							alcance_solicitud.this.solicitud_personal
									.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.insertarSolicitudPersonal1(solicitud, detalle, bitacora, secuencia1,
										tarea_proveedor, secuencia2, bandera,
										alcance_solicitud.this.solicitud_personal);
								setearFechaIngresaFormulario();
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_17(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(),
										informativos.getMensaje_informativo_17() + e.getMessage(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (validarSiSeIniciaTurno() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		limpiarCampos2();
	}

	public void limpiarCampos1() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		obtenerId();
		cmbCliente.setText("");
		txtTicket.setText("");
		cmbTipoIngreso.setText("");
		cmbTipoAprobador.setText("");
		bdxSolicitantes.setText("");
		txtBuscarSolicitante.setText("");
		lbxSolicitantes.clearSelection();
		setearFechaIngresaFormulario();
		setearFechaHoraSolicitud();
		setearFechaHoraRespuesta();
		setearFechaHoraInicio();
		setearFechaHoraFin();
		bdxArea.setText("");
		bdxArea.setTooltiptext("");
		bdxRack.setText("");
		bdxRack.setTooltiptext("");
		cmbTipoTrabajo.setText("");
		txtDescripcion.setText("");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
		bdxProveedores.setText("");
		txtBuscarProveedor.setText("");
		lbxProveedores1.clearSelection();
		borrarListaProveedores();
	}

	public void limpiarCampos2() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		limpiarCampos1();
	}

	public boolean validarSiExistePrimeroApertura(String ticket_externo, long id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * El metodo valida que exista primero una apertura antes de que se guarde un
		 * registro
		 */
		boolean existe_primero_apertura = true;
		if (consultasABaseDeDatos.validarSiExisteTareaRegistrada(ticket_externo, String.valueOf(id_tipo_tarea)) == 0) {
			existe_primero_apertura = false;
		}
		return existe_primero_apertura;
	}

	public long obtenerIDApertura(String ticket_externo, long id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * El metodo obteiene el ID del registro de apertura
		 */
		long id_apertura = 0;
		List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
		listaBitacora = consultasABaseDeDatos.cargarBitacoras(ticket_externo, 7, id_tipo_tarea, "", "", id_dc, "", "",
				0, 0, "", 0);
		if (listaBitacora.size() > 0) {
			id_apertura = listaBitacora.get(0).getId_bitacora();
		}
		return id_apertura;
	}

	public void borrarListaProveedores() {
		Listitem lItem;
		for (int i = lbxProveedores2.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxProveedores2.getItemAtIndex(i);
			lbxProveedores2.removeItemAt(lItem.getIndex());
		}
	}

	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_clasificacion = 0;
		id_tipo_clasificacion = consultasABaseDeDatos.obtenerIdTipoClasificacionAPartirDeTicket(ticket, id_tipo_tarea,
				id_dc);
		return id_tipo_clasificacion;
	}

}
