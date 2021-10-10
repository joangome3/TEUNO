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
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
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
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Textbox txtId, txtDescripcion, txtBuscarSolicitante, txtObservacion, txtComentarioIncumplimientoSLA;
	@Wire
	Bandbox bdxTicketInterno, bdxSolicitantes, bdxArea, bdxRack;
	@Wire
	Combobox cmbCliente, cmbTipoServicio, cmbTipoTarea, cmbEstado, cmbCumplimiento, cmbTurno, cmbUsuario,
			cmbClasificacion, cmbCumplimientoSLA;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Label lTicketInterno1, lTicketInterno2, lDescripcion, lOperador, lObservacion;
	@Wire
	Checkbox chkTicketInterno;
	@Wire
	Listbox lbxSolicitantes;
	@Wire
	Row rwObservacion;
	@Wire
	Div winList;
	@Wire
	Tab tInformacionGeneral, tCumplimientoSLA;

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
	List<modelo_tipo_clasificacion> listaClasificacion = new ArrayList<modelo_tipo_clasificacion>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	bp.aplicaciones.mensajes.Error error = new bp.aplicaciones.mensajes.Error();

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
	modelo_bitacora bitacora = (modelo_bitacora) Sessions.getCurrent().getAttribute("bitacora");

	validar_datos validar = new validar_datos();

	long id_tipo_tarea = 0;

	String ticket_externo = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lTicketInterno2.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
		txtDescripcion.setText("PROVEEDOR: \nTAREA: \n¡REA DE TRABAJO: \nOTRO DETALLE: ");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
		txtBuscarSolicitante.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarSolicitante.setText(txtBuscarSolicitante.getText().toUpperCase());
			}
		});
		inicializarListas();
		setearFechaActual();
		setearFechaHoraInicio();
		setearFechaHoraFin();
		setearTicketInterno();
		setearDescripcion();
		validarTurno();
		bdxTicketInterno.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				bdxTicketInterno.setText(bdxTicketInterno.getText().trim().toUpperCase());
				lTicketInterno2
						.setValue(bdxTicketInterno.getText().trim().length() + "/" + bdxTicketInterno.getMaxlength());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
				lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
			}
		});
		txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtObservacion.setText(txtObservacion.getText().toUpperCase().trim());
				lObservacion.setValue(txtObservacion.getText().length() + "/" + txtObservacion.getMaxlength());
			}
		});
		txtComentarioIncumplimientoSLA.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentarioIncumplimientoSLA.setText(txtComentarioIncumplimientoSLA.getText().toUpperCase().trim());
			}
		});
		if (cmbCumplimiento.getItems().size() > 0) {
			cmbCumplimiento.setSelectedIndex(0);
		}
		tCumplimientoSLA.setDisabled(true);
		cmbTipoTarea.setDisabled(true);
		cmbCumplimiento.setDisabled(true);
		setearEstado();
		setearUsuario();
		cargarDatosBitacora();
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

	public List<modelo_tipo_clasificacion> obtenerClasificaciones() {
		return listaClasificacion;
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

	public void cargarDatosBitacora() throws ClassNotFoundException, FileNotFoundException, IOException {
		/* ID **/
		txtId.setText(String.valueOf(bitacora.getId_bitacora()));
		/** TICKET INTERNO **/
		bdxTicketInterno.setText(bitacora.getTicket_externo());
		bdxTicketInterno.setDisabled(false);
		chkTicketInterno.setChecked(false);
		ticket_externo = bitacora.getTicket_externo();
		/* CLIENTE **/
		Iterator<modelo_empresa> it_cliente = listaCliente.iterator();
		while (it_cliente.hasNext()) {
			modelo_empresa empresa = it_cliente.next();
			if (empresa.getId_empresa() == bitacora.getId_cliente()) {
				cmbCliente.setText(empresa.getNom_empresa());
				break;
			}
		}
		/* TIPO DE SERVICIOS **/
		long id_tipo_servicio = bitacora.getId_tipo_servicio();
		Iterator<modelo_tipo_servicio> it_tipo_servicio = listaTipoServicio.iterator();
		while (it_tipo_servicio.hasNext()) {
			modelo_tipo_servicio tipo_servicio = it_tipo_servicio.next();
			if (tipo_servicio.getId_tipo_servicio() == id_tipo_servicio) {
				cmbTipoServicio.setText(tipo_servicio.getNom_tipo_servicio());
				break;
			}
		}
		/* TIPO DE CLASIFICACIONES **/
		listaClasificacion = consultasABaseDeDatos.cargarClasificaciones("", 2, id_tipo_servicio, 0);
		binder.loadComponent(cmbClasificacion);
		Iterator<modelo_tipo_clasificacion> it_tipo_clasificacion = listaClasificacion.iterator();
		while (it_tipo_clasificacion.hasNext()) {
			modelo_tipo_clasificacion tipo_clasificacion = it_tipo_clasificacion.next();
			if (tipo_clasificacion.getId_tipo_clasificacion() == bitacora.getId_tipo_clasificacion()) {
				cmbClasificacion.setText(tipo_clasificacion.getNom_tipo_clasificacion());
				break;
			}
		}
		if (listaClasificacion.size() > 0) {
			cmbClasificacion.setDisabled(false);
		} else {
			cmbClasificacion.setDisabled(true);
		}
		/* TIPO DE TAREAS **/
		listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 6, id_tipo_servicio, 0);
		binder.loadComponent(cmbTipoTarea);
		Iterator<modelo_tipo_tarea> it_tipo_tarea = listaTipoTarea.iterator();
		while (it_tipo_tarea.hasNext()) {
			modelo_tipo_tarea tipo_tarea = it_tipo_tarea.next();
			if (tipo_tarea.getId_tipo_tarea() == bitacora.getId_tipo_tarea()) {
				cmbTipoTarea.setText(tipo_tarea.getNom_tipo_tarea());
				break;
			}
		}
		id_tipo_tarea = bitacora.getId_tipo_tarea();
		cmbTipoTarea.setDisabled(false);
		/** ESTADO **/
		if (bitacora.getId_estado_bitacora() == 1) {
			cmbEstado.setText("ABIERTO");
		} else {
			cmbEstado.setText("CERRADO");
		}
		/* FECHAS **/
		dtxFechaInicio.setValue(bitacora.getFec_inicio());
		dtxFechaFin.setValue(bitacora.getFec_fin());
		/* CUMPLIMIENTO **/
		activarCampoCumplimiento();
		if (bitacora.getCumplimiento().equals("C")) {
			cmbCumplimiento.setText("CUMPLIDO");
		} else {
			cmbCumplimiento.setText("INCUMPLIDO");
		}
		if (bitacora.getObs_coordinador() != null) {
			if (bitacora.getObs_coordinador().length() > 0) {
				txtObservacion.setText(bitacora.getObs_coordinador());
			}
		}
		mostrarObservacion();
		setearObservacion();
		/* DESCRIPCION **/
		txtDescripcion.setText(bitacora.getDescripcion());
		bdxArea.setText(bitacora.getArea());
		bdxArea.setTooltiptext(bitacora.getArea());
		bdxRack.setText(bitacora.getRack());
		bdxRack.setTooltiptext(bitacora.getRack());
		/* TURNO **/
		Iterator<modelo_turno> it_turno = listaTurnos1.iterator();
		while (it_turno.hasNext()) {
			modelo_turno turno = it_turno.next();
			if (turno.getId_turno() == bitacora.getId_turno()) {
				cmbTurno.setText(turno.getNom_turno());
				id_turno = turno.getId_turno();
				break;
			}
		}
		/** SOLICITANTE **/
		Iterator<modelo_solicitante> it_solicitante = listaSolicitante.iterator();
		int indice = 0;
		while (it_solicitante.hasNext()) {
			modelo_solicitante solicitante = it_solicitante.next();
			if (solicitante.getId_solicitante() == bitacora.getId_solicitante()) {
				bdxSolicitantes.setText(solicitante.getNom_solicitante() + " " + solicitante.getApe_solicitante());
				lbxSolicitantes.setSelectedIndex(indice);
				break;
			}
			indice++;
		}
		/* OPERADOR **/
		if (bitacora.getUsu_modifica() != null) {
			Iterator<modelo_usuario> it_usuario = listaUsuario.iterator();
			while (it_usuario.hasNext()) {
				modelo_usuario usuario = it_usuario.next();
				if (usuario.getUse_usuario().equals(bitacora.getUsu_modifica())) {
					cmbUsuario.setText(usuario.verNombreCompleto());
					break;
				}
			}
			lOperador.setValue("Operador modifica:");
		} else {
			Iterator<modelo_usuario> it_usuario = listaUsuario.iterator();
			while (it_usuario.hasNext()) {
				modelo_usuario usuario = it_usuario.next();
				if (usuario.getUse_usuario().equals(bitacora.getUsu_ingresa())) {
					cmbUsuario.setText(usuario.verNombreCompleto());
					break;
				}
			}
			lOperador.setValue("Operador registra:");
		}
		cmbUsuario.setDisabled(true);
		activarCampoOperador();
		/* CUMPLIMIENTO SLA */
		if (bitacora.getCumplimientoSLA() == null) {
			cmbCumplimientoSLA.setText("");
			txtComentarioIncumplimientoSLA.setReadonly(true);
			txtComentarioIncumplimientoSLA.setText(informativos.getMensaje_informativo_113());
		}
		if (bitacora.getCumplimientoSLA() != null) {
			if (bitacora.getCumplimientoSLA().trim().equals("NO")) {
				cmbCumplimientoSLA.setText(" NO CUMPLE SLA");
				txtComentarioIncumplimientoSLA.setReadonly(false);
				txtComentarioIncumplimientoSLA.setText(bitacora.getComentarioCumplimientoSLA());
			} else {
				cmbCumplimientoSLA.setText(" SI CUMPLE SLA");
				txtComentarioIncumplimientoSLA.setReadonly(true);
				txtComentarioIncumplimientoSLA.setText(informativos.getMensaje_informativo_113());
			}
		}
		if (bitacora.getId_tipo_tarea() == 5) {
			tCumplimientoSLA.setDisabled(false);
		} else {
			tCumplimientoSLA.setDisabled(true);
		}
	}

	public boolean validarSiUsuarioEsRevisor() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean usuario_es_revisor = false;
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getId_perfil_revisor_bitacora() == 0) {
				usuario_es_revisor = false;
			} else {
				if (id_perfil == listaParametros1.get(0).getId_perfil_revisor_bitacora()) {
					usuario_es_revisor = true;
				} else {
					usuario_es_revisor = false;
				}
			}
		} else {
			usuario_es_revisor = false;
		}
		return usuario_es_revisor;
	}

	public void activarCampoCumplimiento() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (validarSiUsuarioEsRevisor() == true) {
			cmbCumplimiento.setDisabled(false);
			txtObservacion.setDisabled(false);
		} else {
			cmbCumplimiento.setDisabled(true);
			txtObservacion.setDisabled(true);
		}
	}

	public void activarCampoOperador() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (validarSiUsuarioEsRevisor() == true) {
			cmbUsuario.setDisabled(false);
		} else {
			cmbUsuario.setDisabled(true);
		}
	}

	public void mostrarObservacion() {
		if (cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
			rwObservacion.setVisible(true);
		} else {
			rwObservacion.setVisible(false);
		}
	}

	public void setearObservacion() {
		if (cmbCumplimiento.getSelectedItem().getValue().toString().equals("C")) {
			txtObservacion.setText("");
		}
		lObservacion.setValue(txtObservacion.getText().length() + "/" + txtObservacion.getMaxlength());
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
		txtDescripcion.setText("PROVEEDOR: \nTAREA: \n¡REA DE TRABAJO: \nOTRO DETALLE: ");
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

	public String obtenerUsuario(long usuario) {
		String use_usuario = "";
		Iterator<modelo_usuario> it = listaUsuario.iterator();
		while (it.hasNext()) {
			modelo_usuario modelo = it.next();
			if (modelo.getId_usuario() == usuario) {
				use_usuario = modelo.getUse_usuario();
				break;
			}
		}
		return use_usuario;
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
					// cmbTurno.setText(listaTurnos2.get(i).getNom_turno());
					// id_turno = listaTurnos2.get(i).getId_turno();
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
					// cmbTurno.setText(listaTurnos2.get(i).getNom_turno());
					// id_turno = listaTurnos2.get(i).getId_turno();
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
								listaClasificacion = consultasABaseDeDatos.cargarClasificaciones("", 2,
										tarea_proveedor.getId_tipo_servicio(), 0);
								if (listaClasificacion.size() > 0) {
									cmbClasificacion.setText("");
									cmbClasificacion.setDisabled(false);
									binder.loadComponent(cmbClasificacion);
									for (int i = 0; i < listaClasificacion.size(); i++) {
										if (listaClasificacion.get(i).getId_tipo_clasificacion() == tarea_proveedor
												.getId_tipo_clasificacion()) {
											cmbClasificacion
													.setText(listaClasificacion.get(i).getNom_tipo_clasificacion());
											i = listaClasificacion.size();
										}
									}
								} else {
									cmbClasificacion.setText("");
									cmbClasificacion.setDisabled(true);
								}
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

	@Listen("onSelect=#cmbCumplimientoSLA")
	public void onSelect$cmbCumplimientoSLA()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCumplimientoSLA.getSelectedItem() == null) {
			txtComentarioIncumplimientoSLA.setReadonly(true);
			txtComentarioIncumplimientoSLA.setText(informativos.getMensaje_informativo_113());
			return;
		}
		if (cmbCumplimientoSLA.getSelectedItem().getValue().toString().equals("NO")) {
			txtComentarioIncumplimientoSLA.setReadonly(false);
			txtComentarioIncumplimientoSLA.setText("");
		} else {
			txtComentarioIncumplimientoSLA.setReadonly(true);
			txtComentarioIncumplimientoSLA.setText(informativos.getMensaje_informativo_113());
		}
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
			cmbClasificacion.setText("");
			cmbClasificacion.setDisabled(true);
			return;
		}
		long id_tipo_servicio = 0;
		id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
		if (consultarPermisoUsuario() == false) {
			boolean se_puede_registrar_tipo_servicio = true;
			se_puede_registrar_tipo_servicio = consultarPermisoTipoServicio(id_tipo_servicio);
			if (se_puede_registrar_tipo_servicio == false
					&& id_tipo_servicio != modificar.this.bitacora.getId_tipo_servicio()) {
				Messagebox.show(informativos.getMensaje_informativo_34() + cmbTipoServicio.getSelectedItem().getLabel(),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				cmbTipoServicio.setText("");
				cmbTipoTarea.setText("");
				cmbTipoTarea.setDisabled(true);
				cmbClasificacion.setText("");
				cmbClasificacion.setDisabled(true);
				return;
			}
		}
		if (consultarPermisoUsuario() == false) {
			String nom_tipo_servicio = "";
			if (consultarPermisoTipoServicio(modificar.this.bitacora.getId_tipo_servicio()) == false
					&& id_tipo_servicio != modificar.this.bitacora.getId_tipo_servicio()) {
				Iterator<modelo_tipo_servicio> it_tipo_servicio = listaTipoServicio.iterator();
				while (it_tipo_servicio.hasNext()) {
					modelo_tipo_servicio tipo_servicio = it_tipo_servicio.next();
					if (tipo_servicio.getId_tipo_servicio() == modificar.this.bitacora.getId_tipo_servicio()) {
						nom_tipo_servicio = tipo_servicio.getNom_tipo_servicio();
						break;
					}
				}
				Messagebox.show(informativos.getMensaje_informativo_34() + nom_tipo_servicio,
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				cmbTipoServicio.setText("");
				cmbTipoTarea.setText("");
				cmbTipoTarea.setDisabled(true);
				cmbClasificacion.setText("");
				cmbClasificacion.setDisabled(true);
				return;
			}
		}
		cmbTipoTarea.setText("");
		cmbTipoTarea.setDisabled(false);
		listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 6,
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()), 0);
		binder.loadComponent(cmbTipoTarea);
		listaClasificacion = consultasABaseDeDatos.cargarClasificaciones("", 2,
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()), 0);
		if (listaClasificacion.size() > 0) {
			cmbClasificacion.setText("");
			cmbClasificacion.setDisabled(false);
			binder.loadComponent(cmbClasificacion);
		} else {
			cmbClasificacion.setText("");
			cmbClasificacion.setDisabled(true);
		}
		if (existeTipoServicio()) {
			lOperador.setValue("Operador asignado:");
			cmbUsuario.setDisabled(false);
			setearUsuario();
		} else {
			cmbUsuario.setDisabled(true);
			lOperador.setValue("Operador modifica:");
			setearUsuario();
		}
		activarCampoOperador();
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
							.replace("?-", bdxTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			cmbTipoTarea.setText("");
			return;
		}
		if (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5) {
			tCumplimientoSLA.setDisabled(false);
		} else {
			tCumplimientoSLA.setDisabled(true);
		}
	}

	public boolean validarSiTareaExiste()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		int totalTareas = consultasABaseDeDatos.validarSiExisteTareaRegistrada(
				bdxTicketInterno.getText().trim().toString().toUpperCase(),
				cmbTipoTarea.getSelectedItem().getValue().toString());
		if (!ticket_externo.equals(bdxTicketInterno.getText().trim().toUpperCase())) {
			if (totalTareas > 0) {
				existe_tarea = true;
			} else {
				existe_tarea = false;
			}
		} else {
			if (totalTareas > 0
					&& id_tipo_tarea != Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString())) {
				existe_tarea = true;
			} else {
				existe_tarea = false;
			}
		}
		return existe_tarea;
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCliente.getSelectedItem() == null) {
			return;
		}
		bdxRack.setText("");
		bdxRack.setTooltiptext("");
		listaRack = new ArrayList<modelo_rack>();
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

	@Listen("onSelect=#cmbCumplimiento")
	public void onSelect$cmbCumplimiento()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCumplimiento.getSelectedItem() == null) {
			return;
		}
		mostrarObservacion();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String mensaje = informativos.getMensaje_informativo_16();
		if (validarSiSeIniciaTurno() == false && validarSiUsuarioEsRevisor() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true && validarSiUsuarioEsRevisor() == false) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (cmbCliente.getSelectedItem() == null) {
			tInformacionGeneral.setSelected(true);
			cmbCliente.setFocus(true);
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			return;
		}
		if (bdxTicketInterno.getValue() == null) {
			tInformacionGeneral.setSelected(true);
			bdxTicketInterno.setFocus(true);
			bdxTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (bdxTicketInterno.getText().trim().length() <= 0) {
			tInformacionGeneral.setSelected(true);
			bdxTicketInterno.setFocus(true);
			bdxTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		/*
		 * if (validarSiTicketPerteneceAlMismoCliente(bdxTicketInterno.getText().trim().
		 * toUpperCase(),
		 * Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == false &&
		 * ticket_externo != bdxTicketInterno.getText().trim().toUpperCase()) {
		 * bdxTicketInterno.setText(""); id_tarea_proveedor = 0;
		 * bdxTicketInterno.setErrorMessage( informativos.getMensaje_informativo_32() +
		 * cmbCliente.getSelectedItem().getLabel()); bdxTicketInterno.setFocus(true);
		 * return; }
		 */
		if (cmbTipoServicio.getSelectedItem() == null) {
			tInformacionGeneral.setSelected(true);
			cmbTipoServicio.setFocus(true);
			cmbTipoServicio.setErrorMessage(validaciones.getMensaje_validacion_14());
			return;
		}
		long id_tipo_servicio = 0;
		id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
		if (consultarPermisoUsuario() == false) {
			boolean se_puede_registrar_tipo_servicio = true;
			se_puede_registrar_tipo_servicio = consultarPermisoTipoServicio(id_tipo_servicio);
			if (se_puede_registrar_tipo_servicio == false
					&& id_tipo_servicio != modificar.this.bitacora.getId_tipo_servicio()) {
				Messagebox.show(informativos.getMensaje_informativo_34() + cmbTipoServicio.getSelectedItem().getLabel(),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (consultarPermisoUsuario() == false) {
			String nom_tipo_servicio = "";
			if (consultarPermisoTipoServicio(modificar.this.bitacora.getId_tipo_servicio()) == false
					&& id_tipo_servicio != modificar.this.bitacora.getId_tipo_servicio()) {
				Iterator<modelo_tipo_servicio> it_tipo_servicio = listaTipoServicio.iterator();
				while (it_tipo_servicio.hasNext()) {
					modelo_tipo_servicio tipo_servicio = it_tipo_servicio.next();
					if (tipo_servicio.getId_tipo_servicio() == modificar.this.bitacora.getId_tipo_servicio()) {
						nom_tipo_servicio = tipo_servicio.getNom_tipo_servicio();
						break;
					}
				}
				Messagebox.show(informativos.getMensaje_informativo_34() + nom_tipo_servicio,
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (listaClasificacion.size() > 0) {
			if (cmbClasificacion.getSelectedItem() == null) {
				tInformacionGeneral.setSelected(true);
				cmbClasificacion.setFocus(true);
				cmbClasificacion.setErrorMessage(validaciones.getMensaje_validacion_38());
				return;
			}
		}
		if (cmbTipoTarea.getSelectedItem() == null) {
			tInformacionGeneral.setSelected(true);
			cmbTipoTarea.setFocus(true);
			cmbTipoTarea.setErrorMessage(validaciones.getMensaje_validacion_15());
			return;
		}
		if (validarSiTareaExiste() == true && (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
			tInformacionGeneral.setSelected(true);
			cmbTipoTarea.setFocus(true);
			Messagebox.show(
					informativos.getMensaje_informativo_37().replace("-?", cmbTipoTarea.getSelectedItem().getLabel())
							.replace("?-", bdxTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistePrimeroApertura(bdxTicketInterno.getText().trim(),
				Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()),
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString())) == false
				&& validarSiUsuarioEsRevisor() == false) {
			Messagebox.show(informativos.getMensaje_informativo_96().replace("?1", bdxTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			tInformacionGeneral.setSelected(true);
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage(validaciones.getMensaje_validacion_2());
			return;
		}
		if (dtxFechaInicio.getValue() == null) {
			tInformacionGeneral.setSelected(true);
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_4());
			return;
		}
		if (dtxFechaFin.getValue() == null) {
			tInformacionGeneral.setSelected(true);
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
			tInformacionGeneral.setSelected(true);
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_10());
			return;
		}
		if (consultarPermisoUsuario() == false && validarSiUsuarioEsRevisor() == false) {
			if (seEncuentraEnRangoDeFechasPermitido() == false) {
				Messagebox.show(informativos.getMensaje_informativo_54(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (cmbTurno.getSelectedItem() == null) {
			tInformacionGeneral.setSelected(true);
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
		if (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5) {
			if (cmbCumplimientoSLA.getSelectedItem() == null) {
				tCumplimientoSLA.setSelected(true);
				cmbCumplimientoSLA.setFocus(true);
				cmbCumplimientoSLA.setErrorMessage(informativos.getMensaje_informativo_114());
				return;
			}
			if (cmbCumplimientoSLA.getSelectedItem().getValue().toString().equals("NO")) {
				if (txtComentarioIncumplimientoSLA.getText().length() <= 0) {
					tCumplimientoSLA.setSelected(true);
					txtComentarioIncumplimientoSLA.setErrorMessage(informativos.getMensaje_informativo_115());
					txtComentarioIncumplimientoSLA.setFocus(true);
					return;
				}
			}
		}
		if (validarSiUsuarioEsRevisor() == true
				&& cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
			if (txtObservacion.getText().length() <= 0) {
				txtObservacion.setFocus(true);
				txtObservacion.setErrorMessage(validaciones.getMensaje_validacion_20());
				return;
			}
		}
		Messagebox.show(mensaje, informativos.getMensaje_informativo_24(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							int tipo = 0;
							dao_bitacora dao1 = new dao_bitacora();
							modelo_bitacora bitacora = new modelo_bitacora();
							bitacora.setId_bitacora(modificar.this.bitacora.getId_bitacora());
							bitacora.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							bitacora.setTicket_externo(bdxTicketInterno.getText().trim().toUpperCase());
							validarTurno();
							bitacora.setId_turno(Long.valueOf(cmbTurno.getSelectedItem().getValue().toString()));
							bitacora.setId_tipo_servicio(
									Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()));
							if (listaClasificacion.size() > 0) {
								bitacora.setId_tipo_clasificacion(
										Long.valueOf(cmbClasificacion.getSelectedItem().getValue().toString()));
							} else {
								bitacora.setId_tipo_clasificacion(0);
							}
							bitacora.setId_tipo_tarea(
									Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()));
							if (lbxSolicitantes.getSelectedItem() != null) {
								int indice = 0;
								indice = lbxSolicitantes.getSelectedIndex();
								bitacora.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
							}
							bitacora.setDescripcion(txtDescripcion.getText());
							bitacora.setArea(bdxArea.getText().toString());
							bitacora.setRack(bdxRack.getText().toString());
							bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							bitacora.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							bitacora.setId_estado_bitacora(
									Long.valueOf(cmbEstado.getSelectedItem().getValue().toString()));
							bitacora.setCumplimiento(cmbCumplimiento.getSelectedItem().getValue().toString());
							if (cmbCumplimientoSLA.getSelectedItem() == null) {
								bitacora.setCumplimientoSLA(null);
								bitacora.setComentarioCumplimientoSLA(null);
							} else {
								bitacora.setCumplimientoSLA(cmbCumplimientoSLA.getSelectedItem().getValue().toString());
								if (cmbCumplimientoSLA.getSelectedItem().getValue().toString().equals("NO")) {
									bitacora.setComentarioCumplimientoSLA(txtComentarioIncumplimientoSLA.getText());
								} else {
									bitacora.setComentarioCumplimientoSLA(null);
								}
							}
							bitacora.setId_localidad(id_dc);
							bitacora.setEst_bitacora("AE");
							if (validarSiUsuarioEsRevisor() == true
									&& cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
								tipo = 1;
								if (modificar.this.bitacora.getUsu_modifica() == null) {
									bitacora.setUsu_ingresa(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									bitacora.setFec_ingresa(modificar.this.bitacora.getFec_ingresa());
								} else {
									bitacora.setUsu_ingresa(modificar.this.bitacora.getUsu_ingresa());
									bitacora.setFec_ingresa(modificar.this.bitacora.getFec_ingresa());
									bitacora.setUsu_modifica(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									bitacora.setFec_modifica(modificar.this.bitacora.getFec_modifica());
								}
								bitacora.setObs_coordinador(txtObservacion.getText().toUpperCase());
								bitacora.setCor_revisa(user);
								bitacora.setFec_revision(fechas.obtenerTimestampDeDate(new Date()));
							} else if (validarSiUsuarioEsRevisor() == true
									&& cmbCumplimiento.getSelectedItem().getValue().toString().equals("C")) {
								tipo = 1;
								if (modificar.this.bitacora.getUsu_modifica() == null) {
									bitacora.setUsu_ingresa(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									bitacora.setFec_ingresa(modificar.this.bitacora.getFec_ingresa());
								} else {
									bitacora.setUsu_ingresa(modificar.this.bitacora.getUsu_ingresa());
									bitacora.setFec_ingresa(modificar.this.bitacora.getFec_ingresa());
									bitacora.setUsu_modifica(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									bitacora.setFec_modifica(modificar.this.bitacora.getFec_modifica());
								}
								bitacora.setObs_coordinador(null);
								bitacora.setCor_revisa(null);
								bitacora.setFec_revision(null);
							} else {
								tipo = 0;
								bitacora.setUsu_ingresa(modificar.this.bitacora.getUsu_ingresa());
								bitacora.setFec_ingresa(modificar.this.bitacora.getFec_ingresa());
								bitacora.setUsu_modifica(user);
								bitacora.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
								if (cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
									bitacora.setObs_coordinador(modificar.this.bitacora.getObs_coordinador());
									bitacora.setCor_revisa(modificar.this.bitacora.getCor_revisa());
									bitacora.setFec_revision(modificar.this.bitacora.getFec_revision());
								} else {
									bitacora.setObs_coordinador(null);
									bitacora.setCor_revisa(null);
									bitacora.setFec_revision(null);
								}
							}
							try {
								dao1.modificarBitacora(bitacora, tipo);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(), informativos.getMensaje_informativo_24(),
										Messagebox.OK, Messagebox.EXCLAMATION);
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

	public boolean validarSiTipoServicioInicialTienePermiso(long id_tipo_servicio)
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
		if (listaTipoUbicacion.size() > 0) {
			for (int i = 0; i < listaTipoUbicacion.size(); i++) {
				if (i == 0) {
					tipo_ubicacion = listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
				} else {
					tipo_ubicacion = tipo_ubicacion + ", " + listaTipoUbicacion.get(i).getNom_tipo_ubicacion();
				}
			}
		} else {
			tipo_ubicacion = "";
		}

		bdxArea.setText(tipo_ubicacion);
		bdxArea.setTooltiptext(tipo_ubicacion);
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

}
