package bp.aplicaciones.controlador.bitacora;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Textbox txtId, txtDescripcion, txtBuscarSolicitante;
	@Wire
	Bandbox bdxTicketInterno, bdxSolicitantes, bdxArea, bdxRack;
	@Wire
	Combobox cmbCliente, cmbTipoServicio, cmbTipoTarea, cmbEstado, cmbCumplimiento, cmbTurno, cmbUsuario;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin, dtxFechaInicioProgramada, dtxFechaFinProgramada;
	@Wire
	Label lTicketInterno1, lTicketInterno2, lDescripcion, lOperador;
	@Wire
	Checkbox chkTicketInterno, chkActividadProgramada;
	@Wire
	Listbox lbxSolicitantes;
	@Wire
	Div winList;

	Window window;

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
	List<modelo_estado_bitacora> listaEstado = new ArrayList<modelo_estado_bitacora>();
	List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_9> listaParametros9 = new ArrayList<modelo_parametros_generales_9>();
	List<modelo_parametros_generales_10> listaParametros10 = new ArrayList<modelo_parametros_generales_10>();
	List<modelo_parametros_generales_11> listaParametros11 = new ArrayList<modelo_parametros_generales_11>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_turno> listaTurnos1 = new ArrayList<modelo_turno>();
	List<modelo_turno> listaTurnos2 = new ArrayList<modelo_turno>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
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

	long id = 0;
	long id_opcion = 3;
	long id_turno = 0;
	long id_tarea_proveedor = 0;

	boolean ingresa_a_relacionar_ticket = false;
	boolean ingresa_a_area_rack = false;

	boolean es_turno_extendido = false;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
		txtDescripcion.setText("");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
		txtBuscarSolicitante.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarSolicitante.setText(txtBuscarSolicitante.getText().toUpperCase());
			}
		});
		obtenerId();
		inicializarListas();
		setearFechaActual();
		setearFechaHoraInicio();
		setearFechaHoraFin();
		setearTicketInterno();
		setearDescripcion();
		validarTurno();
		bdxTicketInterno.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				bdxTicketInterno.setText(bdxTicketInterno.getText().trim().toUpperCase().trim());
				lTicketInterno2
						.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
				lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
			}
		});
		if (cmbCumplimiento.getItems().size() > 0) {
			cmbCumplimiento.setSelectedIndex(0);
		}
		cmbTipoTarea.setDisabled(true);
		cmbCumplimiento.setDisabled(true);
		setearEstado();
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

	public List<modelo_estado_bitacora> obtenerEstados() {
		return listaEstado;
	}

	public List<modelo_tipo_tarea> obtenerTipoTarea() {
		return listaTipoTarea;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicio() {
		return listaTipoServicio;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_bitacora dao = new dao_bitacora();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaUsuario = consultasABaseDeDatos.cargarUsuarios(String.valueOf(id_dc), 4, 0);
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaEstado = consultasABaseDeDatos.cargarEstadosBitacora("", 1, String.valueOf(id_dc));
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTipoServicio = consultasABaseDeDatos.cargarTipoDeServicios("", 1, 0, 0);
		listaTurnos1 = consultasABaseDeDatos.cargarTurnos("");
		listaTurnos2 = consultasABaseDeDatos.cargarTurnos("A");
		binder.loadComponent(lbxSolicitantes);
		binder.loadComponent(cmbUsuario);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbEstado);
		binder.loadComponent(cmbTipoServicio);
		binder.loadComponent(cmbTipoTarea);
		binder.loadComponent(cmbTurno);
	}

	public boolean validarSiTicketPerteneceAlMismoCliente(String ticket_externo, long id_cliente)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean es_el_mismo_cliente = false;
		List<modelo_tarea_proveedor> listaTareaProveedor = new ArrayList<modelo_tarea_proveedor>();
		listaTareaProveedor = consultasABaseDeDatos.cargarTareasProveedores(ticket_externo, 4, 0, "", "", id_dc, "", "",
				0, "", 0, 0);
		if (listaTareaProveedor.size() > 0) {
			if (listaTareaProveedor.get(0).getId_cliente() == id_cliente) {
				es_el_mismo_cliente = true;
			} else {
				es_el_mismo_cliente = false;
			}
		} else {
			es_el_mismo_cliente = false;
		}
		return es_el_mismo_cliente;
	}

	public void setearTicketInterno() {
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getEtiqueta_bitacora() != null) {
				bdxTicketInterno.setText(listaParametros1.get(0).getEtiqueta_bitacora() + txtId.getText());
			} else {
				bdxTicketInterno.setText("TB-000" + txtId.getText());
			}
		} else {
			bdxTicketInterno.setText("TB-000" + txtId.getText());
		}
		bdxTicketInterno.setStyle("text-align: center; font-weight: bold; font-style: normal;");
		lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
	}

	public void setearDescripcion() {
		txtDescripcion.setText("");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
	}

	public void setearEstado() {
		Iterator<modelo_estado_bitacora> it = listaEstado.iterator();
		while (it.hasNext()) {
			modelo_estado_bitacora modelo = it.next();
			if (listaParametros1.size() > 0) {
				if (modelo.getId_estado() == listaParametros1.get(0).getEstado_bitacora_1()) {
					cmbEstado.setText(modelo.getNom_estado());
					break;
				}
			}
		}
	}

	public void setearTipoServicio(long id_tipo_servicio) {
		Iterator<modelo_tipo_servicio> it = listaTipoServicio.iterator();
		while (it.hasNext()) {
			modelo_tipo_servicio modelo = it.next();
			if (modelo.getId_tipo_servicio() == id_tipo_servicio) {
				cmbTipoServicio.setText(modelo.getNom_tipo_servicio());
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

	public void setearTipoTarea(long id_tipo_tarea) {
		Iterator<modelo_tipo_tarea> it = listaTipoTarea.iterator();
		while (it.hasNext()) {
			modelo_tipo_tarea modelo = it.next();
			if (modelo.getId_tipo_tarea() == id_tipo_tarea) {
				cmbTipoTarea.setText(modelo.getNom_tipo_tarea());
				break;
			}
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

	public void setearFechaHoraInicio() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), new Date().getSeconds());
		dtxFechaInicio.setValue(d);
	}

	public void setearFechaHoraFin() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		dtxFechaFin.setValue(d);
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

	@Listen("onDoubleClick=#bdxTicketInterno")
	public void onClick$bdxTicketInterno() {
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			cmbCliente.setFocus(true);
			return;
		}
		if (ingresa_a_relacionar_ticket == false) {
			ingresa_a_relacionar_ticket = true;
			Component comp = Executions.createComponents("/bitacora/log_eventos/consultar_ticket.zul", null, null);
			if (comp instanceof Window) {
				((Window) comp).doModal();
				comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_relacionar_ticket = false;
						modelo_tarea_proveedor tarea_proveedor = (modelo_tarea_proveedor) Sessions.getCurrent()
								.getAttribute("tarea_proveedor");
						Sessions.getCurrent().removeAttribute("tarea_proveedor");
						if (tarea_proveedor == null) {
							bdxTicketInterno.setText("");
							id_tarea_proveedor = 0;
						} else {
							if (tarea_proveedor.getId_cliente() == Long
									.valueOf(cmbCliente.getSelectedItem().getValue().toString())) {
								bdxTicketInterno.setText(tarea_proveedor.getTicket_externo());
								id_tarea_proveedor = tarea_proveedor.getId_tarea_proveedor();
								setearTipoServicio(tarea_proveedor.getId_tipo_servicio());
								cmbTipoTarea.setText("");
								cmbTipoTarea.setDisabled(false);
								listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 6,
										Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()), 0);
								binder.loadComponent(cmbTipoTarea);
								txtDescripcion.setText(tarea_proveedor.getDescripcion());
								setearSolicitante(tarea_proveedor.getId_solicitante());
							} else {
								bdxTicketInterno.setText("");
								id_tarea_proveedor = 0;
								bdxTicketInterno.setErrorMessage(informativos.getMensaje_informativo_32()
										+ cmbCliente.getSelectedItem().getLabel());
								bdxTicketInterno.setFocus(true);
							}
						}
					}
				});
			}
		}
	}

	@Listen("onClick=#chkActividadProgramada")
	public void onClick$chkActividadProgramada()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			cmbCliente.setFocus(true);
			chkActividadProgramada.setChecked(false);
			return;
		}
		if (chkActividadProgramada.isChecked()) {
			dtxFechaInicioProgramada.setDisabled(false);
			dtxFechaFinProgramada.setDisabled(false);
			dtxFechaInicioProgramada.setValue(null);
			dtxFechaFinProgramada.setValue(null);
		} else {
			dtxFechaInicioProgramada.setDisabled(true);
			dtxFechaFinProgramada.setDisabled(true);
			dtxFechaInicioProgramada.setValue(null);
			dtxFechaFinProgramada.setValue(null);
		}
	}

	@Listen("onClick=#chkTicketInterno")
	public void onClick$chkTicketInterno() throws ClassNotFoundException, FileNotFoundException, IOException {
		id_tarea_proveedor = 0;
		if (chkTicketInterno.isChecked()) {
			lTicketInterno1.setValue("TICKET INTERNO:");
			bdxTicketInterno.setDisabled(true);
			bdxTicketInterno.setMaxlength(20);
			setearTicketInterno();
		} else {
			if (cmbCliente.getSelectedItem() != null) {
				String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
				listaParametros9 = consultasABaseDeDatos.cargarParametros9(String.valueOf(id_empresa), "", id_dc, 3);
				if (listaParametros9.size() > 0) {
					bdxTicketInterno.setDisabled(false);
					bdxTicketInterno.setText("");
					lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
					lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/"
							+ listaParametros9.get(0).getCant_caracteres());
					bdxTicketInterno.setMaxlength(listaParametros9.get(0).getCant_caracteres());
				} else {
					lTicketInterno1.setValue("TICKET INTERNO:");
					bdxTicketInterno.setDisabled(true);
					bdxTicketInterno.setMaxlength(20);
					chkTicketInterno.setChecked(true);
					setearTicketInterno();
				}
			} else {
				cmbCliente.setFocus(true);
				cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
				lTicketInterno1.setValue("TICKET INTERNO:");
				bdxTicketInterno.setDisabled(true);
				bdxTicketInterno.setMaxlength(20);
				chkTicketInterno.setChecked(true);
				setearTicketInterno();
				return;
			}
		}
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

	@Listen("onSelect=#cmbTipoServicio")
	public void onSelect$cmbTipoServicio()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbTipoServicio.getSelectedItem() == null) {
			cmbTipoTarea.setText("");
			cmbTipoTarea.setDisabled(true);
			return;
		}
		if (consultarPermisoUsuario() == false) {
			long id_tipo_servicio = 0;
			id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
			boolean se_puede_registrar_tipo_servicio = true;
			se_puede_registrar_tipo_servicio = consultarPermisoTipoServicio(id_tipo_servicio);
			if (se_puede_registrar_tipo_servicio == false) {
				Messagebox.show(informativos.getMensaje_informativo_34() + cmbTipoServicio.getSelectedItem().getLabel(),
						informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
				cmbTipoServicio.setText("");
				cmbTipoTarea.setText("");
				cmbTipoTarea.setDisabled(true);
				return;
			}
		}
		cmbTipoTarea.setText("");
		cmbTipoTarea.setDisabled(false);
		listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 6,
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()), 0);
		binder.loadComponent(cmbTipoTarea);
		if (existeTipoServicio()) {
			lOperador.setValue("Operador asignado:");
			cmbUsuario.setDisabled(false);
			cmbUsuario.setText("");
		} else {
			cmbUsuario.setDisabled(true);
			lOperador.setValue("Operador registra:");
			setearUsuario();
		}
	}

	public boolean existeTipoServicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean existe_servicio = false;
		listaParametros11 = consultasABaseDeDatos.cargarParametros11(String.valueOf(id_opcion),
				cmbTipoServicio.getSelectedItem().getValue().toString(), String.valueOf(id_dc), 1);
		Iterator<modelo_parametros_generales_11> it = listaParametros11.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_11 modelo = it.next();
			if (modelo.getId_tipo_servicio() == Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString())) {
				existe_servicio = true;
				break;
			}
		}
		return existe_servicio;
	}

	@Listen("onSelect=#cmbTipoTarea")
	public void onSelect$cmbTipoTarea()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbTipoTarea.getSelectedItem() == null) {
			return;
		}
		if (bdxTicketInterno.getText().trim().length() <= 0) {
			bdxTicketInterno.setFocus(true);
			bdxTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (validarSiTareaExiste() == true && (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
			cmbTipoTarea.setFocus(true);
			Messagebox.show(
					informativos.getMensaje_informativo_37().replace("-?", cmbTipoTarea.getSelectedItem().getLabel())
							.replace("?-", bdxTicketInterno.getText().trim().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			cmbTipoTarea.setText("");
			return;
		}
		setearTicketTareaPlanificada();
	}

	public void setearTicketTareaPlanificada()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
		long secuencia2 = 0;
		if (listaParametros1.size() > 0) {
			secuencia2 = listaParametros1.get(0).getSecuencia_tarea_proveedor();
		}
		if (chkTicketInterno.isChecked()) {
			if (!cmbUsuario.isDisabled()) {
				secuencia2 = dao2.obtenerNuevoId();
				if (listaParametros1.size() > 0) {
					if (listaParametros1.get(0).getEtiqueta_tarea_proveedor() != null) {
						bdxTicketInterno.setText(listaParametros1.get(0).getEtiqueta_tarea_proveedor() + secuencia2);
						lTicketInterno2.setValue(
								bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
					} else {
						bdxTicketInterno.setText("TA-000" + secuencia2);
						lTicketInterno2.setValue(
								bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
					}
				} else {
					bdxTicketInterno.setText("TA-000" + secuencia2);
					lTicketInterno2.setValue(
							bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
				}
			} else {
				setearTicketInterno();
			}
		}
	}

	public boolean validarSiTareaExiste()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		int totalTareas = consultasABaseDeDatos.validarSiExisteTareaRegistrada(
				bdxTicketInterno.getText().trim().trim().toString().toUpperCase(),
				cmbTipoTarea.getSelectedItem().getValue().toString());
		if (totalTareas > 0) {
			existe_tarea = true;
		} else {
			existe_tarea = false;
		}
		return existe_tarea;
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCliente.getSelectedItem() == null) {
			return;
		}
		id_tarea_proveedor = 0;
		if (!chkTicketInterno.isChecked()) {
			String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
			listaParametros9 = consultasABaseDeDatos.cargarParametros9(String.valueOf(id_empresa), "", id_dc, 3);
			if (listaParametros9.size() > 0) {
				bdxTicketInterno.setDisabled(false);
				bdxTicketInterno.setText("");
				lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
				lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/"
						+ listaParametros9.get(0).getCant_caracteres());
				bdxTicketInterno.setMaxlength(listaParametros9.get(0).getCant_caracteres());
			} else {
				lTicketInterno1.setValue("TICKET INTERNO:");
				bdxTicketInterno.setDisabled(true);
				bdxTicketInterno.setMaxlength(20);
				chkTicketInterno.setChecked(true);
				setearTicketInterno();
			}
		} else {
			lTicketInterno1.setValue("TICKET INTERNO:");
			bdxTicketInterno.setDisabled(true);
			bdxTicketInterno.setMaxlength(20);
			setearTicketInterno();
		}
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

	public boolean consultarPermisoTipoServicio(long id_tipo_servicio)
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
		if (bdxTicketInterno.getValue() == null) {
			bdxTicketInterno.setFocus(true);
			bdxTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (bdxTicketInterno.getText().trim().length() <= 0) {
			bdxTicketInterno.setFocus(true);
			bdxTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		} /*
			 * if (validarSiTicketPerteneceAlMismoCliente(bdxTicketInterno.getText().trim().
			 * toUpperCase(),
			 * Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == false) {
			 * bdxTicketInterno.setText(""); id_tarea_proveedor = 0;
			 * bdxTicketInterno.setErrorMessage( informativos.getMensaje_informativo_32() +
			 * cmbCliente.getSelectedItem().getLabel()); bdxTicketInterno.setFocus(true);
			 * return; }
			 */
		if (cmbTipoServicio.getSelectedItem() == null) {
			cmbTipoServicio.setFocus(true);
			cmbTipoServicio.setErrorMessage(validaciones.getMensaje_validacion_14());
			return;
		}
		if (consultarPermisoUsuario() == false) {
			long id_tipo_servicio = 0;
			id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
			boolean se_puede_registrar_tipo_servicio = true;
			se_puede_registrar_tipo_servicio = consultarPermisoTipoServicio(id_tipo_servicio);
			if (se_puede_registrar_tipo_servicio == false) {
				Messagebox.show(informativos.getMensaje_informativo_34() + cmbTipoServicio.getSelectedItem().getLabel(),
						informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (cmbTipoTarea.getSelectedItem() == null) {
			cmbTipoTarea.setFocus(true);
			cmbTipoTarea.setErrorMessage(validaciones.getMensaje_validacion_15());
			return;
		}
		if (validarSiTareaExiste() == true && (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
			cmbTipoTarea.setFocus(true);
			Messagebox.show(
					informativos.getMensaje_informativo_37().replace("-?", cmbTipoTarea.getSelectedItem().getLabel())
							.replace("?-", bdxTicketInterno.getText().trim().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistePrimeroApertura(bdxTicketInterno.getText().trim(),
				Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()),
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString())) == false) {
			Messagebox.show(informativos.getMensaje_informativo_96().replace("?1", bdxTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage(validaciones.getMensaje_validacion_2());
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
		Date d1 = fechas.obtenerFechaArmada(dtxFechaInicio.getValue(), dtxFechaInicio.getValue().getMonth(),
				dtxFechaInicio.getValue().getDate(), dtxFechaInicio.getValue().getHours(),
				dtxFechaInicio.getValue().getMinutes(), 0);
		Date d2 = fechas.obtenerFechaArmada(dtxFechaFin.getValue(), dtxFechaFin.getValue().getMonth(),
				dtxFechaFin.getValue().getDate(), dtxFechaFin.getValue().getHours(),
				dtxFechaFin.getValue().getMinutes(), 0);
		if (d2.before(d1)) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_10());
			return;
		}
		if (consultarPermisoUsuario() == false) {
			if (seEncuentraEnRangoDeFechasPermitido() == false) {
				Messagebox.show(informativos.getMensaje_informativo_54(), informativos.getMensaje_informativo_17(),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (cmbTurno.getSelectedItem() == null) {
			cmbTurno.setFocus(true);
			cmbTurno.setErrorMessage(validaciones.getMensaje_validacion_16());
			return;
		}
		if (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5) {
			if (lbxSolicitantes.getSelectedItem() == null) {
				bdxSolicitantes.setFocus(true);
				bdxSolicitantes.setErrorMessage(validaciones.getMensaje_validacion_29());
				return;
			}
		}
		if (cmbUsuario.getSelectedItem() == null) {
			cmbUsuario.setFocus(true);
			cmbUsuario.setErrorMessage(validaciones.getMensaje_validacion_19());
			return;
		}
		if (cmbEstado.getSelectedItem() == null) {
			cmbEstado.setFocus(true);
			cmbEstado.setErrorMessage(validaciones.getMensaje_validacion_17());
			return;
		}
		if (cmbCumplimiento.getSelectedItem() == null) {
			cmbCumplimiento.setFocus(true);
			cmbCumplimiento.setErrorMessage(validaciones.getMensaje_validacion_18());
			return;
		}
		if (chkActividadProgramada.isChecked()) {
			mensaje = informativos.getMensaje_informativo_35();
			List<modelo_tarea_proveedor> lista = new ArrayList<modelo_tarea_proveedor>();
			lista = consultasABaseDeDatos.cargarTareasProveedores(bdxTicketInterno.getText().trim().toUpperCase(), 3, 0,
					"", "", 0, "", "", 0, "", 0, 0);
			if (lista.size() > 0) {
				bdxTicketInterno.setFocus(true);
				bdxTicketInterno.setErrorMessage(informativos.getMensaje_informativo_36());
				return;
			}
			if (dtxFechaInicioProgramada.getValue() == null) {
				dtxFechaInicioProgramada.setFocus(true);
				dtxFechaInicioProgramada.setErrorMessage(validaciones.getMensaje_validacion_4());
				return;
			}
			if (dtxFechaFinProgramada.getValue() == null) {
				dtxFechaFinProgramada.setFocus(true);
				dtxFechaFinProgramada.setErrorMessage(validaciones.getMensaje_validacion_4());
				return;
			}
			Date d3 = fechas.obtenerFechaArmada(dtxFechaInicioProgramada.getValue(),
					dtxFechaInicioProgramada.getValue().getMonth(), dtxFechaInicioProgramada.getValue().getDate(),
					dtxFechaInicioProgramada.getValue().getHours(), dtxFechaInicioProgramada.getValue().getMinutes(),
					0);
			Date d4 = fechas.obtenerFechaArmada(dtxFechaFinProgramada.getValue(),
					dtxFechaFinProgramada.getValue().getMonth(), dtxFechaFinProgramada.getValue().getDate(),
					dtxFechaFinProgramada.getValue().getHours(), dtxFechaFinProgramada.getValue().getMinutes(), 0);
			if (d4.before(d3)) {
				dtxFechaInicioProgramada.setFocus(true);
				dtxFechaInicioProgramada.setErrorMessage(validaciones.getMensaje_validacion_10());
				return;
			}
		}
		Messagebox.show(mensaje, informativos.getMensaje_informativo_17(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_bitacora dao1 = new dao_bitacora();
							dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
							modelo_bitacora bitacora = new modelo_bitacora();
							modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
							bitacora.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							bitacora.setTicket_externo(bdxTicketInterno.getText().trim().toUpperCase());
							validarTurno();
							bitacora.setId_turno(Long.valueOf(cmbTurno.getSelectedItem().getValue().toString()));
							bitacora.setId_tipo_servicio(
									Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()));
							bitacora.setId_tipo_tarea(
									Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()));
							if (lbxSolicitantes.getSelectedItem() != null) {
								int indice = 0;
								indice = lbxSolicitantes.getSelectedIndex();
								bitacora.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
								tarea_proveedor.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
							}
							bitacora.setDescripcion(txtDescripcion.getText());
							bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							bitacora.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							bitacora.setId_estado_bitacora(
									Long.valueOf(cmbEstado.getSelectedItem().getValue().toString()));
							bitacora.setCumplimiento(cmbCumplimiento.getSelectedItem().getValue().toString());
							bitacora.setId_localidad(id_dc);
							bitacora.setEst_bitacora("AE");
							bitacora.setUsu_ingresa(user);
							bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							long secuencia1 = 0, secuencia2 = 0;
							if (chkActividadProgramada.isChecked()) {
								tarea_proveedor.setId_cliente(
										Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
								tarea_proveedor
										.setId_turno(Long.valueOf(cmbTurno.getSelectedItem().getValue().toString()));
								tarea_proveedor.setId_tipo_servicio(
										Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()));
								tarea_proveedor.setId_tipo_tarea(
										Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()));
								tarea_proveedor.setDescripcion(txtDescripcion.getText());
								tarea_proveedor.setFec_inicio(
										fechas.obtenerTimestampDeDate(dtxFechaInicioProgramada.getValue()));
								tarea_proveedor
										.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFinProgramada.getValue()));
								if (listaParametros1.size() > 0) {
									tarea_proveedor
											.setId_estado_bitacora(listaParametros1.get(0).getEstado_bitacora_2());
								} else {
									tarea_proveedor.setId_estado_bitacora(
											Long.valueOf(cmbEstado.getSelectedItem().getValue().toString()));
								}
								tarea_proveedor
										.setCumplimiento(cmbCumplimiento.getSelectedItem().getValue().toString());
								tarea_proveedor.setId_localidad(id_dc);
								tarea_proveedor.setEst_tarea_proveedor("AE");
								boolean existe = false;
								String usuario = "";
								if (!cmbUsuario.isDisabled()) {
									for (int i = 0; i < listaUsuario.size(); i++) {
										if (listaUsuario.get(i).getId_usuario() == Long
												.valueOf(cmbUsuario.getSelectedItem().getValue().toString())) {
											existe = true;
											usuario = listaUsuario.get(i).getUse_usuario();
											i = listaUsuario.size() + 1;
										}
									}
								} else {
									tarea_proveedor.setUsu_ingresa(user);
								}
								if (existe) {
									tarea_proveedor.setUsu_ingresa(usuario);
								} else {
									tarea_proveedor.setUsu_ingresa(user);
								}
								tarea_proveedor.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							}
							if (listaParametros1.size() > 0) {
								secuencia1 = listaParametros1.get(0).getSecuencia_bitacora();
							}
							if (listaParametros1.size() > 0) {
								secuencia2 = listaParametros1.get(0).getSecuencia_tarea_proveedor();
							}
							try {
								/* Se valida si es una tarea operador planificada */
								if (chkActividadProgramada.isChecked()) {
									if (!cmbUsuario.isDisabled()) {
										secuencia2 = dao2.obtenerNuevoId();
										if (listaParametros1.size() > 0) {
											if (listaParametros1.get(0).getEtiqueta_tarea_proveedor() != null) {
												tarea_proveedor.setTicket_externo(
														listaParametros1.get(0).getEtiqueta_tarea_proveedor()
																+ secuencia2);
												bitacora.setTicket_externo(
														listaParametros1.get(0).getEtiqueta_tarea_proveedor()
																+ secuencia2);
											} else {
												bitacora.setTicket_externo("TA-000" + secuencia2);
												tarea_proveedor.setTicket_externo("TA-000" + secuencia2);
											}
										} else {
											bitacora.setTicket_externo("TA-000" + secuencia2);
											tarea_proveedor.setTicket_externo("TA-000" + secuencia2);
										}
									} else {
										/* Si no es una tarea de operador planificada */
										if (chkTicketInterno.isChecked()) {
											secuencia2 = dao2.obtenerNuevoId();
											if (listaParametros1.size() > 0) {
												if (listaParametros1.get(0).getEtiqueta_tarea_proveedor() != null) {
													tarea_proveedor.setTicket_externo(
															listaParametros1.get(0).getEtiqueta_tarea_proveedor()
																	+ secuencia2);
													bitacora.setTicket_externo(
															listaParametros1.get(0).getEtiqueta_tarea_proveedor()
																	+ secuencia2);
												} else {
													bitacora.setTicket_externo("TA-000" + secuencia2);
													tarea_proveedor.setTicket_externo("TA-000" + secuencia2);
												}
											} else {
												bitacora.setTicket_externo("TA-000" + secuencia2);
												tarea_proveedor.setTicket_externo("TA-000" + secuencia2);
											}
										} else {
											tarea_proveedor
													.setTicket_externo(bdxTicketInterno.getText().trim().toUpperCase());
										}
									}
									dao1.insertarBitacora(bitacora, secuencia2, tarea_proveedor, secuencia2);
								} else {
									dao1.insertarBitacora(bitacora, secuencia1);
								}
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_17(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								limpiarCampos1();
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
		Events.postEvent(new Event("onClose", zNuevo));
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
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
		limpiarCampos2();
	}

	public void limpiarCampos1() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		obtenerId();
		chkActividadProgramada.setChecked(false);
		dtxFechaInicioProgramada.setValue(null);
		dtxFechaFinProgramada.setValue(null);
		dtxFechaInicioProgramada.setDisabled(true);
		dtxFechaFinProgramada.setDisabled(true);
		validarTurno();
		if (chkTicketInterno.isChecked()) {
			lTicketInterno1.setValue("TICKET INTERNO:");
			bdxTicketInterno.setDisabled(true);
			bdxTicketInterno.setMaxlength(20);
			if (Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()) == 11) {
				setearTicketTareaPlanificada();
			} else {
				setearTicketInterno();
			}
		} else {
			if (cmbCliente.getSelectedItem() != null) {
				String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
				listaParametros9 = consultasABaseDeDatos.cargarParametros9(id_empresa, "", id_dc, 3);
				if (listaParametros9.size() > 0) {
					bdxTicketInterno.setDisabled(false);
					lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
					lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/"
							+ listaParametros9.get(0).getCant_caracteres());
					bdxTicketInterno.setMaxlength(listaParametros9.get(0).getCant_caracteres());
				} else {
					lTicketInterno1.setValue("TICKET INTERNO:");
					bdxTicketInterno.setDisabled(true);
					bdxTicketInterno.setMaxlength(20);
					chkTicketInterno.setChecked(true);
					setearTicketInterno();
				}
			} else {
				cmbCliente.setFocus(true);
				cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
				lTicketInterno1.setValue("TICKET INTERNO:");
				bdxTicketInterno.setDisabled(true);
				chkTicketInterno.setChecked(true);
				bdxTicketInterno.setMaxlength(20);
				setearTicketInterno();
				return;
			}
		}
	}

	public void limpiarCampos2() throws ClassNotFoundException, FileNotFoundException, IOException {
		obtenerId();
		id_tarea_proveedor = 0;
		chkActividadProgramada.setChecked(false);
		dtxFechaInicioProgramada.setValue(null);
		dtxFechaFinProgramada.setValue(null);
		dtxFechaInicioProgramada.setDisabled(true);
		dtxFechaFinProgramada.setDisabled(true);
		bdxTicketInterno.setText("");
		bdxSolicitantes.setText("");
		txtBuscarSolicitante.setText("");
		lbxSolicitantes.clearSelection();
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		binder.loadComponent(lbxSolicitantes);
		cmbCliente.setText("");
		cmbTipoServicio.setText("");
		chkTicketInterno.setChecked(true);
		bdxTicketInterno.setDisabled(true);
		lTicketInterno1.setValue("TICKET INTERNO:");
		bdxTicketInterno.setMaxlength(20);
		setearTicketInterno();
		setearEstado();
		setearUsuario();
		lOperador.setValue("Operador registra:");
		cmbUsuario.setDisabled(true);
		bdxTicketInterno.setStyle("text-align: center; font-weight: bold; font-style: normal;");
		cmbTipoTarea.setText("");
		setearFechaHoraInicio();
		setearFechaHoraFin();
		validarTurno();
		if (cmbCumplimiento.getItems().size() > 0) {
			cmbCumplimiento.setSelectedIndex(0);
		}
		cmbCumplimiento.setDisabled(true);
		lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
		txtDescripcion.setText("");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
	}

	public boolean seEncuentraEnRangoDeFechasPermitido() {
		boolean rango_permitido = false;
		int hora_inicio_actual = 0;
		int hora_turno_extendido_rango_1 = 0, hora_turno_extendido_rango_2 = 0, hora_turno_extendido_rango_3 = 0,
				hora_turno_extendido_rango_4 = 0;
		Date fecha_actual = new Date();
		if (es_turno_extendido == true) {
			hora_turno_extendido_rango_1 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_inicio);
			hora_turno_extendido_rango_2 = fechas
					.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_fin_turno_extendido); /* 23:59 */
			hora_turno_extendido_rango_3 = fechas
					.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_inicio_turno_extendido); /* 00:01 */
			hora_turno_extendido_rango_4 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_fin);
			hora_inicio_actual = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_actual);
			if ((hora_inicio_actual >= hora_turno_extendido_rango_3)
					&& (hora_inicio_actual <= hora_turno_extendido_rango_4)) {
				// Se trata de tareas para el dia siguiente en el rango [0-7], se resta 1 al dia
				// actual
				Date newDate = new Date(fecha_inicio.getTime() + TimeUnit.DAYS.toMillis(-1));
				if ((newDate.before(dtxFechaInicio.getValue()) || newDate.equals(dtxFechaInicio.getValue()))
						&& (dtxFechaInicio.getValue().before(dtxFechaFin.getValue())
								|| dtxFechaInicio.getValue().equals(dtxFechaFin.getValue()))
						&& (dtxFechaFin.getValue().before(fecha_fin) || dtxFechaFin.getValue().equals(fecha_fin))) {
					rango_permitido = true;
				} else {
					rango_permitido = false;
				}
			}
			if ((hora_inicio_actual >= hora_turno_extendido_rango_1)
					&& (hora_inicio_actual <= hora_turno_extendido_rango_2)) {
				// Se trata de tareas del mismo dia en el rango [20-23], se suma 1 al dia
				// siempre que se cumpla que hora inicio sea menor a hora final
				Date newDate = new Date(fecha_fin.getTime() + TimeUnit.DAYS.toMillis(1));
				if ((fecha_inicio.before(dtxFechaInicio.getValue()) || fecha_inicio.equals(dtxFechaInicio.getValue()))
						&& (dtxFechaInicio.getValue().before(dtxFechaFin.getValue())
								|| dtxFechaInicio.getValue().equals(dtxFechaFin.getValue()))
						&& (dtxFechaFin.getValue().before(newDate) || dtxFechaFin.getValue().equals(newDate))) {
					rango_permitido = true;
				} else {
					rango_permitido = false;
				}
			}
		} else {
			if ((fecha_inicio.before(dtxFechaInicio.getValue()) || fecha_inicio.equals(dtxFechaInicio.getValue()))
					&& (dtxFechaInicio.getValue().before(dtxFechaFin.getValue())
							|| dtxFechaInicio.getValue().equals(dtxFechaFin.getValue()))
					&& (dtxFechaFin.getValue().before(fecha_fin) || dtxFechaFin.getValue().equals(fecha_fin))) {
				rango_permitido = true;
			} else {
				rango_permitido = false;
			}
		}
		return rango_permitido;
	}

	public boolean validarSiExistePrimeroApertura(String ticket_externo, long id_tipo_tarea, long id_tipo_servicio)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * El metodo valida que exista primero una apertura antes de que se guarde un
		 * registro
		 */
		boolean existe_primero_apertura = true;
		if ((id_tipo_servicio == 13 && id_tipo_tarea == 7) || (id_tipo_servicio == 14 && id_tipo_tarea == 7)
				|| (id_tipo_servicio == 15 && id_tipo_tarea == 7) || (id_tipo_servicio == 10 && id_tipo_tarea == 8)
				|| id_tipo_tarea == 1) {
			existe_primero_apertura = true;
		} else {
			if (consultasABaseDeDatos.validarSiExisteTareaRegistrada(ticket_externo, "1") == 0) {
				existe_primero_apertura = false;
			} else {
				existe_primero_apertura = true;
			}
		}
		return existe_primero_apertura;
	}
	
	@Listen("onClick=#bdxArea")
	public void onClick$bdxArea() throws Throwable {
		if (ingresa_a_area_rack == false) {
			ingresa_a_area_rack = true;
			Sessions.getCurrent().setAttribute("lista_area", listaTipoUbicacion);
			window = (Window) Executions.createComponents("/personal/solicitud/area.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_area_rack = false;
						listaTipoUbicacion = (List<modelo_tipo_ubicacion>) Sessions.getCurrent()
								.getAttribute("lista_area");
						if (listaTipoUbicacion.size() == 0) {
							return;
						}
						setearAreas(listaTipoUbicacion);
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#bdxRack")
	public void onClick$bdxRack() throws Throwable {
		if (ingresa_a_area_rack == false) {
			ingresa_a_area_rack = true;
			Sessions.getCurrent().setAttribute("lista_rack", listaRack);
			window = (Window) Executions.createComponents("/personal/solicitud/rack.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_area_rack = false;
						listaRack = (List<modelo_rack>) Sessions.getCurrent().getAttribute("lista_rack");
						if (listaRack.size() == 0) {
							return;
						}
						setearRacks(listaRack);
					}
				});
			}
			window.setParent(winList);
		}
	}

	public void setearAreas(List<modelo_tipo_ubicacion> listaTipoUbicacion) {
		String tipo_ubicacion = "";
		for (int i = 0; i < listaTipoUbicacion.size(); i++) {
			if (i == 0) {
				tipo_ubicacion = listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
			} else {
				tipo_ubicacion = tipo_ubicacion + ", " + listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
			}
		}
		bdxArea.setText(tipo_ubicacion);
		bdxArea.setTooltiptext(tipo_ubicacion);
	}

	public void setearRacks(List<modelo_rack> listaRack) {
		String rack = "";
		for (int i = 0; i < listaRack.size(); i++) {
			if (i == 0) {
				rack = listaRack.get(i).getCoord_rack();
			} else {
				rack = rack + ", " + listaRack.get(i).getCoord_rack();
			}
		}
		bdxRack.setText(rack);
		bdxRack.setTooltiptext(rack);
	}

}
