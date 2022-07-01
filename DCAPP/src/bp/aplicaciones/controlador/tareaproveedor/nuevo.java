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

import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_12;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Textbox txtId, txtTicketInterno, txtDescripcion, txtBuscarSolicitante;
	@Wire
	Combobox cmbCliente, cmbTipoServicio, cmbTipoTarea, cmbEstado, cmbCumplimiento, cmbTurno, cmbUsuario,
			cmbClasificacion;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Label lTicketInterno1, lTicketInterno2, lDescripcion, lOperador;
	@Wire
	Checkbox chkTicketInterno;
	@Wire
	Listbox lbxSolicitantes;
	@Wire
	Bandbox bdxSolicitantes, bdxArea, bdxRack;
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
	List<modelo_tipo_clasificacion> listaClasificacion = new ArrayList<modelo_tipo_clasificacion>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	bp.aplicaciones.mensajes.Error error = new bp.aplicaciones.mensajes.Error();

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	boolean ingresa_a_area_rack = false;

	boolean es_turno_extendido = false;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	long id = 0;
	long id_opcion = 3;
	long id_turno = 0;
	long id_tarea_proveedor = 0;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String id_tipo_ubicacion = "";

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/" + txtTicketInterno.getMaxlength());
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
		txtTicketInterno.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicketInterno.setText(txtTicketInterno.getText().trim().toUpperCase());
				lTicketInterno2
						.setValue(txtTicketInterno.getText().trim().length() + "/" + txtTicketInterno.getMaxlength());
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

	public List<modelo_tipo_clasificacion> obtenerClasificaciones() {
		return listaClasificacion;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tarea_proveedor dao = new dao_tarea_proveedor();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaUsuario = consultasABaseDeDatos.consultarUsuarios(id_dc, 0, "", "", 0, 2);
		listaSolicitante = consultasABaseDeDatos.consultarSolicitantes(id_opcion, id_dc, "", "", 0, 6);
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
				txtTicketInterno.setText("TA-000" + txtId.getText());
			}
		} else {
			txtTicketInterno.setText("TA-000" + txtId.getText());
		}
		txtTicketInterno.setStyle("text-align: center; font-weight: bold; font-style: normal;");
		lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/" + txtTicketInterno.getMaxlength());
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

	@Listen("onOK=#txtBuscarSolicitante")
	public void onOK$txtBuscarSolicitante()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		listaSolicitante = consultasABaseDeDatos.consultarSolicitantes(id_opcion, id_dc,
				txtBuscarSolicitante.getText().toUpperCase().toUpperCase().trim(), "", 0, 7);
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
			cmbClasificacion.setText("");
			cmbClasificacion.setDisabled(true);
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
		if (txtTicketInterno.getText().trim().length() <= 0) {
			txtTicketInterno.setFocus(true);
			txtTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (validarSiTareaExiste() == true && (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1
				|| Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
			cmbTipoTarea.setFocus(true);
			Messagebox.show(
					informativos.getMensaje_informativo_37().replace("-?", cmbTipoTarea.getSelectedItem().getLabel())
							.replace("?-", txtTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			cmbTipoTarea.setText("");
			return;
		}
	}

	public boolean validarSiTareaExiste()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		int totalTareas = consultasABaseDeDatos.validarSiExisteTareaRegistradaTareaProveedor(
				txtTicketInterno.getText().trim().toString().toUpperCase(),
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
		bdxRack.setText("");
		bdxRack.setTooltiptext("");
		listaRack = new ArrayList<modelo_rack>();
		id_tarea_proveedor = 0;
		if (!chkTicketInterno.isChecked()) {
			String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
			listaParametros9 = consultasABaseDeDatos.cargarParametros9(String.valueOf(id_empresa), "", id_dc, 3);
			if (listaParametros9.size() > 0) {
				txtTicketInterno.setDisabled(false);
				txtTicketInterno.setText("");
				lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
				lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/"
						+ listaParametros9.get(0).getCant_caracteres());
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
		listaParametros = consultasABaseDeDatos.consultarParametrosGenerales10(id_opcion, id_tipo_servicio, "",
				String.valueOf(id_dc), 0, 5);
		Iterator<modelo_parametros_generales_10> it = listaParametros.iterator();
		while (it.hasNext()) {
			modelo_parametros_generales_10 modelo = it.next();
			if (modelo.getTipo_servicio().getId_tipo_servicio() == id_tipo_servicio) {
				if (modelo.isSe_puede_crear_modificar_eliminar() == true) {
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
		if (txtTicketInterno.getText().trim().length() <= 0) {
			txtTicketInterno.setFocus(true);
			txtTicketInterno.setErrorMessage(validaciones.getMensaje_validacion_13());
			return;
		}
		if (cmbTipoServicio.getSelectedItem() == null) {
			cmbTipoServicio.setFocus(true);
			cmbTipoServicio.setErrorMessage(validaciones.getMensaje_validacion_14());
			return;
		}
		if (listaClasificacion.size() > 0) {
			if (cmbClasificacion.getSelectedItem() == null) {
				cmbClasificacion.setFocus(true);
				cmbClasificacion.setErrorMessage(validaciones.getMensaje_validacion_38());
				return;
			}
		}
		if (cmbTipoTarea.getSelectedItem() == null) {
			cmbTipoTarea.setFocus(true);
			cmbTipoTarea.setErrorMessage(validaciones.getMensaje_validacion_15());
			return;
		}
		/* Se valida si la actividad programada ya se encuentra creada */
		if (validarSiSeAceptaTicketRepetido(id_opcion,
				Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == false) {
			if (validarSiTareaProgramadaExiste(txtTicketInterno.getText().trim()) == true) {
				txtTicketInterno.setFocus(true);
				Messagebox.show(
						informativos.getMensaje_informativo_60().replace("?", txtTicketInterno.getText().trim()),
						informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		/*
		 * Se valida si la actividad programada ya se encuentra creada para otro cliente
		 */
		if (validarSiTareaEsDeOtroCliente(txtTicketInterno.getText().trim(),
				Long.valueOf(cmbCliente.getSelectedItem().getValue().toString())) == true) {
			txtTicketInterno.setFocus(true);
			Messagebox.show(informativos.getMensaje_informativo_61().replace("?", txtTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistePrimeroApertura(txtTicketInterno.getText().trim(),
				Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()),
				Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString())) == false) {
			Messagebox.show(informativos.getMensaje_informativo_96().replace("?1", txtTicketInterno.getText().trim()),
					informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		/*
		 * if (validarSiTareaExiste() == true &&
		 * (Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 1 ||
		 * Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()) == 5)) {
		 * cmbTipoTarea.setFocus(true); Messagebox.show(
		 * informativos.getMensaje_informativo_37().replace("-?",
		 * cmbTipoTarea.getSelectedItem().getLabel()) .replace("?-",
		 * txtTicketInterno.getText().trim()), informativos.getMensaje_informativo_17(),
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
		Messagebox.show(mensaje, informativos.getMensaje_informativo_17(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_tarea_proveedor dao = new dao_tarea_proveedor();
							modelo_tarea_proveedor tarea_proveedor = new modelo_tarea_proveedor();
							tarea_proveedor
									.setId_cliente(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							tarea_proveedor.setTicket_externo(txtTicketInterno.getText().trim().toUpperCase());
							validarTurno();
							tarea_proveedor.setId_turno(Long.valueOf(cmbTurno.getSelectedItem().getValue().toString()));
							tarea_proveedor.setId_tipo_servicio(
									Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString()));
							if (listaClasificacion.size() > 0) {
								tarea_proveedor.setId_tipo_clasificacion(
										Long.valueOf(cmbClasificacion.getSelectedItem().getValue().toString()));
							} else {
								tarea_proveedor.setId_tipo_clasificacion(0);
							}
							tarea_proveedor.setId_tipo_tarea(
									Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString()));
							if (lbxSolicitantes.getSelectedItem() != null) {
								int indice = 0;
								indice = lbxSolicitantes.getSelectedIndex();
								tarea_proveedor.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
							}
							tarea_proveedor.setDescripcion(txtDescripcion.getText());
							tarea_proveedor.setArea(bdxArea.getText().toString());
							tarea_proveedor.setRack(bdxRack.getText().toString());
							tarea_proveedor.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
							tarea_proveedor.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
							tarea_proveedor.setId_estado_bitacora(
									Long.valueOf(cmbEstado.getSelectedItem().getValue().toString()));
							tarea_proveedor.setCumplimiento(cmbCumplimiento.getSelectedItem().getValue().toString());
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
							long secuencia = 0;
							if (listaParametros1.size() > 0) {
								secuencia = listaParametros1.get(0).getSecuencia_tarea_proveedor();
							}
							try {
								/* Se valida si es una tarea operador planificada */
								dao.insertarTareaProveedor(tarea_proveedor, secuencia);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_17(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								limpiarCampos1();
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(), informativos.getMensaje_informativo_17(),
										Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});

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
					lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/"
							+ listaParametros9.get(0).getCant_caracteres());
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
		limpiarCampos2();
	}

	public void limpiarCampos1() throws ClassNotFoundException, FileNotFoundException, IOException {
		obtenerId();
		validarTurno();
		if (chkTicketInterno.isChecked()) {
			lTicketInterno1.setValue("TICKET INTERNO:");
			txtTicketInterno.setDisabled(true);
			txtTicketInterno.setMaxlength(20);
			setearTicketInterno();
		} else {
			if (cmbCliente.getSelectedItem() != null) {
				String id_empresa = cmbCliente.getSelectedItem().getValue().toString();
				listaParametros9 = consultasABaseDeDatos.cargarParametros9(id_empresa, "", id_dc, 3);
				if (listaParametros9.size() > 0) {
					txtTicketInterno.setDisabled(false);
					lTicketInterno1.setValue("TICKET " + listaParametros9.get(0).getNom_ticket() + ":");
					lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/"
							+ listaParametros9.get(0).getCant_caracteres());
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
				chkTicketInterno.setChecked(true);
				txtTicketInterno.setMaxlength(20);
				setearTicketInterno();
				return;
			}
		}
	}

	public void limpiarCampos2() throws ClassNotFoundException, FileNotFoundException, IOException {
		obtenerId();
		id_tarea_proveedor = 0;
		txtTicketInterno.setText("");
		cmbCliente.setText("");
		cmbTipoServicio.setText("");
		cmbClasificacion.setText("");
		chkTicketInterno.setChecked(true);
		txtTicketInterno.setDisabled(true);
		lTicketInterno1.setValue("TICKET INTERNO:");
		txtTicketInterno.setMaxlength(20);
		bdxSolicitantes.setText("");
		txtBuscarSolicitante.setText("");
		lbxSolicitantes.clearSelection();
		listaSolicitante = consultasABaseDeDatos.consultarSolicitantes(id_opcion, id_dc, "", "", 0, 6);
		binder.loadComponent(lbxSolicitantes);
		setearTicketInterno();
		setearEstado();
		setearUsuario();
		lOperador.setValue("Operador registra:");
		cmbUsuario.setDisabled(true);
		txtTicketInterno.setStyle("text-align: center; font-weight: bold; font-style: normal;");
		cmbTipoTarea.setText("");
		setearFechaHoraInicio();
		setearFechaHoraFin();
		validarTurno();
		if (cmbCumplimiento.getItems().size() > 0) {
			cmbCumplimiento.setSelectedIndex(0);
		}
		cmbCumplimiento.setDisabled(true);
		lTicketInterno2.setValue(txtTicketInterno.getText().trim().length() + "/" + txtTicketInterno.getMaxlength());
		txtDescripcion.setText("");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
		bdxArea.setText("");
		listaTipoUbicacion = new ArrayList<modelo_tipo_ubicacion>();
		bdxRack.setText("");
		listaRack = new ArrayList<modelo_rack>();
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
			if (consultasABaseDeDatos.validarSiTareaProgramadaExiste(ticket_externo, "1") == 0) {
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

}
