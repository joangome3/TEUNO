package bp.aplicaciones.controlador.tareaproveedor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_12;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Textbox txtId, txtDescripcion, txtBuscarSolicitante, txtObservacion, txtTicketInterno;
	@Wire
	Bandbox bdxSolicitantes;
	@Wire
	Combobox cmbCliente, cmbTipoServicio, cmbTipoTarea, cmbEstado, cmbCumplimiento, cmbTurno, cmbUsuario;
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

	boolean es_turno_extendido = false;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_tarea_proveedor tarea_proveedor = (modelo_tarea_proveedor) Sessions.getCurrent()
			.getAttribute("tarea_proveedor");

	validar_datos validar = new validar_datos();

	long id_tipo_tarea = 0;

	String ticket_externo = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lTicketInterno2.setValue(txtTicketInterno.getText().length() + "/" + txtTicketInterno.getMaxlength());
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
		txtTicketInterno.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicketInterno.setText(txtTicketInterno.getText().toUpperCase());
				lTicketInterno2.setValue(txtTicketInterno.getText().length() + "/" + txtTicketInterno.getMaxlength());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
				lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
			}
		});
		txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtObservacion.setText(txtObservacion.getText().toUpperCase());
				lObservacion.setValue(txtObservacion.getText().length() + "/" + txtObservacion.getMaxlength());
			}
		});
		if (cmbCumplimiento.getItems().size() > 0) {
			cmbCumplimiento.setSelectedIndex(0);
		}
		cmbTipoTarea.setDisabled(true);
		cmbCumplimiento.setDisabled(true);
		setearEstado();
		setearUsuario();
		cargarDatosTareaProveedor();
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

	public void cargarDatosTareaProveedor() throws ClassNotFoundException, FileNotFoundException, IOException {
		/* ID **/
		txtId.setText(String.valueOf(tarea_proveedor.getId_tarea_proveedor()));
		/** TICKET INTERNO **/
		txtTicketInterno.setText(tarea_proveedor.getTicket_externo());
		txtTicketInterno.setDisabled(false);
		chkTicketInterno.setChecked(false);
		ticket_externo = tarea_proveedor.getTicket_externo();
		/* CLIENTE **/
		Iterator<modelo_empresa> it_cliente = listaCliente.iterator();
		while (it_cliente.hasNext()) {
			modelo_empresa empresa = it_cliente.next();
			if (empresa.getId_empresa() == tarea_proveedor.getId_cliente()) {
				cmbCliente.setText(empresa.getNom_empresa());
				break;
			}
		}
		/* TIPO DE SERVICIOS **/
		long id_tipo_servicio = tarea_proveedor.getId_tipo_servicio();
		Iterator<modelo_tipo_servicio> it_tipo_servicio = listaTipoServicio.iterator();
		while (it_tipo_servicio.hasNext()) {
			modelo_tipo_servicio tipo_servicio = it_tipo_servicio.next();
			if (tipo_servicio.getId_tipo_servicio() == id_tipo_servicio) {
				cmbTipoServicio.setText(tipo_servicio.getNom_tipo_servicio());
				break;
			}
		}
		/* TIPO DE TAREAS **/
		listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 6, id_tipo_servicio, 0);
		binder.loadComponent(cmbTipoTarea);
		Iterator<modelo_tipo_tarea> it_tipo_tarea = listaTipoTarea.iterator();
		while (it_tipo_tarea.hasNext()) {
			modelo_tipo_tarea tipo_tarea = it_tipo_tarea.next();
			if (tipo_tarea.getId_tipo_tarea() == tarea_proveedor.getId_tipo_tarea()) {
				cmbTipoTarea.setText(tipo_tarea.getNom_tipo_tarea());
				break;
			}
		}
		id_tipo_tarea = tarea_proveedor.getId_tipo_tarea();
		cmbTipoTarea.setDisabled(false);
		/** ESTADO **/
		if (tarea_proveedor.getId_estado_bitacora() == 1) {
			cmbEstado.setText("ABIERTO");
		} else {
			cmbEstado.setText("CERRADO");
		}
		/* FECHAS **/
		dtxFechaInicio.setValue(tarea_proveedor.getFec_inicio());
		dtxFechaFin.setValue(tarea_proveedor.getFec_fin());
		/* CUMPLIMIENTO **/
		activarCampoCumplimiento();
		if (tarea_proveedor.getCumplimiento().equals("C")) {
			cmbCumplimiento.setText("CUMPLIDO");
		} else {
			cmbCumplimiento.setText("INCUMPLIDO");
		}
		if (tarea_proveedor.getObs_coordinador() != null) {
			if (tarea_proveedor.getObs_coordinador().length() > 0) {
				txtObservacion.setText(tarea_proveedor.getObs_coordinador());
			}
		}
		mostrarObservacion();
		setearObservacion();
		/* DESCRIPCION **/
		txtDescripcion.setText(tarea_proveedor.getDescripcion());
		/* TURNO **/
		Iterator<modelo_turno> it_turno = listaTurnos1.iterator();
		while (it_turno.hasNext()) {
			modelo_turno turno = it_turno.next();
			if (turno.getId_turno() == tarea_proveedor.getId_turno()) {
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
			if (solicitante.getId_solicitante() == tarea_proveedor.getId_solicitante()) {
				bdxSolicitantes.setText(solicitante.getNom_solicitante() + " " + solicitante.getApe_solicitante());
				lbxSolicitantes.setSelectedIndex(indice);
				break;
			}
			indice++;
		}
		/* OPERADOR **/
		if (tarea_proveedor.getUsu_modifica() != null) {
			Iterator<modelo_usuario> it_usuario = listaUsuario.iterator();
			while (it_usuario.hasNext()) {
				modelo_usuario usuario = it_usuario.next();
				if (usuario.getUse_usuario().equals(tarea_proveedor.getUsu_modifica())) {
					cmbUsuario.setText(usuario.verNombreCompleto());
					break;
				}
			}
			lOperador.setValue("Operador modifica:");
		} else {
			Iterator<modelo_usuario> it_usuario = listaUsuario.iterator();
			while (it_usuario.hasNext()) {
				modelo_usuario usuario = it_usuario.next();
				if (usuario.getUse_usuario().equals(tarea_proveedor.getUsu_ingresa())) {
					cmbUsuario.setText(usuario.verNombreCompleto());
					break;
				}
			}
			lOperador.setValue("Operador registra:");
		}
		cmbUsuario.setDisabled(true);
		activarCampoOperador(existeTipoServicio(1, String.valueOf(tarea_proveedor.getId_tipo_servicio())));
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

	public void activarCampoOperador(boolean existe_tipo_servicio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		if (validarSiUsuarioEsRevisor() == true || existe_tipo_servicio == true) {
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
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 2, String.valueOf(id_dc),
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
			if (listaParametros1.get(0).getEtiqueta_tarea_proveedor() != null) {
				txtTicketInterno.setText(listaParametros1.get(0).getEtiqueta_tarea_proveedor() + txtId.getText());
			} else {
				txtTicketInterno.setText("TB-000" + txtId.getText());
			}
		} else {
			txtTicketInterno.setText("TB-000" + txtId.getText());
		}
		txtTicketInterno.setStyle("text-align: center; font-weight: bold; font-style: italic;");
		lTicketInterno2.setValue(txtTicketInterno.getText().length() + "/" + txtTicketInterno.getMaxlength());
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
				if (modelo.getId_estado() == listaParametros1.get(0).getEstado_bitacora_2()) {
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

	@Listen("onClick=#chkTicketInterno")
	public void onClick$chkTicketInterno() throws ClassNotFoundException, FileNotFoundException, IOException {
		id_tarea_proveedor = 0;
		if (chkTicketInterno.isChecked()) {
			lTicketInterno1.setValue("TICKET INTERNO:");
			txtTicketInterno.setDisabled(true);
			txtTicketInterno.setMaxlength(20);
			setearTicketInterno();
		} else {
			if (cmbCliente.getSelectedItem() != null) {
				String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
				listaParametros9 = consultasABaseDeDatos.cargarParametros9(String.valueOf(id_empresa), "", id_dc, 3);
				if (listaParametros9.size() > 0) {
					txtTicketInterno.setDisabled(false);
					txtTicketInterno.setText("");
					lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
					lTicketInterno2.setValue(
							txtTicketInterno.getText().length() + "/" + listaParametros9.get(0).getCant_caracteres());
					txtTicketInterno.setMaxlength(listaParametros9.get(0).getCant_caracteres());
				} else {
					lTicketInterno1.setValue("TICKET INTERNO:");
					txtTicketInterno.setDisabled(true);
					txtTicketInterno.setMaxlength(20);
					chkTicketInterno.setChecked(true);
					setearTicketInterno();
				}
			} else {
				cmbCliente.setFocus(true);
				cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
				lTicketInterno1.setValue("TICKET INTERNO:");
				txtTicketInterno.setDisabled(true);
				txtTicketInterno.setMaxlength(20);
				chkTicketInterno.setChecked(true);
				setearTicketInterno();
				return;
			}
		}
	}

	@Listen("onOK=#txtBuscarSolicitante")
	public void onOK$txtBuscarSolicitante()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes(txtBuscarSolicitante.getText().toUpperCase(), 2,
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
		long id_tipo_servicio = 0;
		id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
		if (consultarPermisoUsuario() == false) {
			boolean se_puede_registrar_tipo_servicio = true;
			se_puede_registrar_tipo_servicio = consultarPermisoTipoServicio(id_tipo_servicio);
			if (se_puede_registrar_tipo_servicio == false
					&& id_tipo_servicio != modificar.this.tarea_proveedor.getId_tipo_servicio()) {
				Messagebox.show(informativos.getMensaje_informativo_34() + cmbTipoServicio.getSelectedItem().getLabel(),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				cmbTipoServicio.setText("");
				cmbTipoTarea.setText("");
				cmbTipoTarea.setDisabled(true);
				return;
			}
		}
		if (consultarPermisoUsuario() == false) {
			String nom_tipo_servicio = "";
			if (consultarPermisoTipoServicio(modificar.this.tarea_proveedor.getId_tipo_servicio()) == false
					&& id_tipo_servicio != modificar.this.tarea_proveedor.getId_tipo_servicio()) {
				Iterator<modelo_tipo_servicio> it_tipo_servicio = listaTipoServicio.iterator();
				while (it_tipo_servicio.hasNext()) {
					modelo_tipo_servicio tipo_servicio = it_tipo_servicio.next();
					if (tipo_servicio.getId_tipo_servicio() == modificar.this.tarea_proveedor.getId_tipo_servicio()) {
						nom_tipo_servicio = tipo_servicio.getNom_tipo_servicio();
						break;
					}
				}
				Messagebox.show(informativos.getMensaje_informativo_34() + nom_tipo_servicio,
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
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
		boolean existe_tipo_servicio = existeTipoServicio(2, "");
		if (existe_tipo_servicio) {
			lOperador.setValue("Operador asignado:");
			cmbUsuario.setDisabled(false);
			cmbUsuario.setText("");
		} else {
			cmbUsuario.setDisabled(true);
			lOperador.setValue("Operador modifica:");
			setearUsuario();
		}
		activarCampoOperador(existe_tipo_servicio);
	}

	public boolean existeTipoServicio(int tipo, String tipo_servicio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean existe_servicio = false;
		String id_tipo_servicio = "";
		if (tipo == 1) {
			id_tipo_servicio = tipo_servicio;
		} else {
			id_tipo_servicio = cmbTipoServicio.getSelectedItem().getValue().toString();
		}
		listaParametros11 = consultasABaseDeDatos.cargarParametros11(String.valueOf(id_opcion), id_tipo_servicio,
				String.valueOf(id_dc), 1);
		Iterator<modelo_parametros_generales_11> it = listaParametros11.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_11 modelo = it.next();
			if (modelo.getId_tipo_servicio() == Long.valueOf(id_tipo_servicio)) {
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
		if (txtTicketInterno.getText().length() <= 0) {
			txtTicketInterno.setFocus(true);
			txtTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (validarSiTareaExiste() == true && (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
			cmbTipoTarea.setFocus(true);
			Messagebox.show(
					informativos.getMensaje_informativo_37().replace("-?", cmbTipoTarea.getSelectedItem().getLabel())
							.replace("?-", txtTicketInterno.getText()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			cmbTipoTarea.setText("");
			return;
		}
	}

	public boolean validarSiTareaExiste()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		int totalTareas = consultasABaseDeDatos.validarSiExisteTareaRegistrada(
				txtTicketInterno.getText().toString().toUpperCase(),
				cmbTipoTarea.getSelectedItem().getValue().toString());
		if (!ticket_externo.equals(txtTicketInterno.getText().toUpperCase())) {
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
		id_tarea_proveedor = 0;
		if (!chkTicketInterno.isChecked()) {
			String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
			listaParametros9 = consultasABaseDeDatos.cargarParametros9(String.valueOf(id_empresa), "", id_dc, 3);
			if (listaParametros9.size() > 0) {
				txtTicketInterno.setDisabled(false);
				txtTicketInterno.setText("");
				lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
				lTicketInterno2.setValue(
						txtTicketInterno.getText().length() + "/" + listaParametros9.get(0).getCant_caracteres());
				txtTicketInterno.setMaxlength(listaParametros9.get(0).getCant_caracteres());
			} else {
				lTicketInterno1.setValue("TICKET INTERNO:");
				txtTicketInterno.setDisabled(true);
				txtTicketInterno.setMaxlength(20);
				chkTicketInterno.setChecked(true);
				setearTicketInterno();
			}
		} else {
			lTicketInterno1.setValue("TICKET INTERNO:");
			txtTicketInterno.setDisabled(true);
			txtTicketInterno.setMaxlength(20);
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
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setFocus(true);
			cmbCliente.setErrorMessage(validaciones.getMensaje_validacion_12());
			return;
		}
		if (txtTicketInterno.getValue() == null) {
			txtTicketInterno.setFocus(true);
			txtTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (txtTicketInterno.getText().length() <= 0) {
			txtTicketInterno.setFocus(true);
			txtTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (cmbTipoServicio.getSelectedItem() == null) {
			cmbTipoServicio.setFocus(true);
			cmbTipoServicio.setErrorMessage(validaciones.getMensaje_validacion_14());
			return;
		}
		if (cmbTipoTarea.getSelectedItem() == null) {
			cmbTipoTarea.setFocus(true);
			cmbTipoTarea.setErrorMessage(validaciones.getMensaje_validacion_15());
			return;
		}
		/* Se valida si la actividad programada ya se encuentra creada */
		if (validarSiSeAceptaTicketRepetido(id_opcion,
				Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == false
				&& !ticket_externo.equals(txtTicketInterno.getText().toUpperCase())) {
			if (validarSiTareaProgramadaExiste(txtTicketInterno.getText()) == true) {
				txtTicketInterno.setFocus(true);
				Messagebox.show(informativos.getMensaje_informativo_60().replace("?", txtTicketInterno.getText()),
						informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		/*
		 * Se valida si la actividad programada ya se encuentra creada para otro cliente
		 */
		if (validarSiTareaEsDeOtroCliente(txtTicketInterno.getText(),
				Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == true
				&& !ticket_externo.equals(txtTicketInterno.getText().toUpperCase())) {
			txtTicketInterno.setFocus(true);
			Messagebox.show(informativos.getMensaje_informativo_61().replace("?", txtTicketInterno.getText()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		/*
		 * if (validarSiTareaExiste() == true &&
		 * (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1 ||
		 * Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
		 * cmbTipoTarea.setFocus(true); Messagebox.show(
		 * informativos.getMensaje_informativo_37().replace("-?",
		 * cmbTipoTarea.getSelectedItem().getLabel()) .replace("?-",
		 * txtTicketInterno.getText()), informativos.getMensaje_informativo_24(),
		 * Messagebox.OK, Messagebox.EXCLAMATION); return; }
		 */
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
							dao_tarea_proveedor dao1 = new dao_tarea_proveedor();
							modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
							tarea_proveedor
									.setId_tarea_proveedor(modificar.this.tarea_proveedor.getId_tarea_proveedor());
							tarea_proveedor
									.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							tarea_proveedor.setTicket_externo(txtTicketInterno.getText().toUpperCase());
							validarTurno();
							tarea_proveedor.setId_turno(Long.valueOf(cmbTurno.getSelectedItem().getValue().toString()));
							tarea_proveedor.setId_tipo_servicio(
									Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()));
							tarea_proveedor.setId_tipo_tarea(
									Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()));
							if (lbxSolicitantes.getSelectedItem() != null) {
								int indice = 0;
								indice = lbxSolicitantes.getSelectedIndex();
								tarea_proveedor.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
							}
							tarea_proveedor.setDescripcion(txtDescripcion.getText());
							tarea_proveedor.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							tarea_proveedor.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							tarea_proveedor.setId_estado_bitacora(
									Long.valueOf(cmbEstado.getSelectedItem().getValue().toString()));
							tarea_proveedor.setCumplimiento(cmbCumplimiento.getSelectedItem().getValue().toString());
							tarea_proveedor.setId_localidad(id_dc);
							tarea_proveedor.setEst_tarea_proveedor("AE");
							if (validarSiUsuarioEsRevisor() == true
									&& cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
								tipo = 1;
								if (modificar.this.tarea_proveedor.getUsu_modifica() == null) {
									tarea_proveedor.setUsu_ingresa(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									tarea_proveedor.setFec_ingresa(modificar.this.tarea_proveedor.getFec_ingresa());
								} else {
									tarea_proveedor.setUsu_ingresa(modificar.this.tarea_proveedor.getUsu_ingresa());
									tarea_proveedor.setFec_ingresa(modificar.this.tarea_proveedor.getFec_ingresa());
									tarea_proveedor.setUsu_modifica(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									tarea_proveedor.setFec_modifica(modificar.this.tarea_proveedor.getFec_modifica());
								}
								tarea_proveedor.setObs_coordinador(txtObservacion.getText().toUpperCase());
								tarea_proveedor.setCor_revisa(user);
								tarea_proveedor.setFec_revision(fechas.obtenerTimestampDeDate(new Date()));
							} else if (validarSiUsuarioEsRevisor() == true
									&& cmbCumplimiento.getSelectedItem().getValue().toString().equals("C")) {
								tipo = 1;
								if (modificar.this.tarea_proveedor.getUsu_modifica() == null) {
									tarea_proveedor.setUsu_ingresa(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									tarea_proveedor.setFec_ingresa(modificar.this.tarea_proveedor.getFec_ingresa());
								} else {
									tarea_proveedor.setUsu_ingresa(modificar.this.tarea_proveedor.getUsu_ingresa());
									tarea_proveedor.setFec_ingresa(modificar.this.tarea_proveedor.getFec_ingresa());
									tarea_proveedor.setUsu_modifica(obtenerUsuario(
											Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())));
									tarea_proveedor.setFec_modifica(modificar.this.tarea_proveedor.getFec_modifica());
								}
								tarea_proveedor.setObs_coordinador(null);
								tarea_proveedor.setCor_revisa(null);
								tarea_proveedor.setFec_revision(null);
							} else {
								tipo = 0;
								tarea_proveedor.setUsu_ingresa(modificar.this.tarea_proveedor.getUsu_ingresa());
								tarea_proveedor.setFec_ingresa(modificar.this.tarea_proveedor.getFec_ingresa());
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
									tarea_proveedor.setUsu_modifica(user);
								}
								if (existe) {
									tarea_proveedor.setUsu_modifica(usuario);
								} else {
									tarea_proveedor.setUsu_modifica(user);
								}
								tarea_proveedor.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
								if (cmbCumplimiento.getSelectedItem().getValue().toString().equals("I")) {
									tarea_proveedor
											.setObs_coordinador(modificar.this.tarea_proveedor.getObs_coordinador());
									tarea_proveedor.setCor_revisa(modificar.this.tarea_proveedor.getCor_revisa());
									tarea_proveedor.setFec_revision(modificar.this.tarea_proveedor.getFec_revision());
								} else {
									tarea_proveedor.setObs_coordinador(null);
									tarea_proveedor.setCor_revisa(null);
									tarea_proveedor.setFec_revision(null);
								}
							}
							try {
								dao1.modificarTareaProveedor(tarea_proveedor, tipo);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								Events.postEvent(new Event("onClose", zModificar));
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

	public boolean validarSiSeAceptaTicketRepetido(long id_opcion, long id_cliente)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean se_acepta_ticket_repetido = false;
		List<modelo_parametros_generales_12> parametros = consultasABaseDeDatos
				.cargarParametros12(String.valueOf(id_cliente), String.valueOf(id_opcion), id_dc, 1);
		if (parametros.size() > 0) {
			if (parametros.get(0).getSe_acepta_ticket_repetido().equals("S")) {
				se_acepta_ticket_repetido = true;
			} else {
				se_acepta_ticket_repetido = false;
			}
		} else {
			se_acepta_ticket_repetido = false;
		}
		return se_acepta_ticket_repetido;
	}

	public boolean validarSiTareaEsDeOtroCliente(String ticket_externo, long id_cliente)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tarea_es_de_otro_cliente = false;
		List<modelo_tarea_proveedor> listaTareasProgramadas = new ArrayList<modelo_tarea_proveedor>();
		listaTareasProgramadas = consultasABaseDeDatos.cargarTareasProveedores(ticket_externo, 4, 0, "", "", id_dc, "",
				"", 0, "", 0, 0);
		if (listaTareasProgramadas.size() > 0) {
			if (listaTareasProgramadas.get(0).getId_cliente() != id_cliente) {
				tarea_es_de_otro_cliente = true;
			} else {
				tarea_es_de_otro_cliente = false;
			}
		} else {
			tarea_es_de_otro_cliente = false;
		}
		return tarea_es_de_otro_cliente;
	}

	public boolean validarSiTareaProgramadaExiste(String ticket_externo)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea_programada = false;
		if (consultasABaseDeDatos.validarSiTareaProgramadaExiste(ticket_externo,
				cmbTipoTarea.getSelectedItem().getValue().toString()) > 0) {
			existe_tarea_programada = true;
		} else {
			existe_tarea_programada = false;
		}
		return existe_tarea_programada;
	}

}
