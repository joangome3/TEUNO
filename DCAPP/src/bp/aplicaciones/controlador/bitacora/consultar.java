package bp.aplicaciones.controlador.bitacora;

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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.DAO.dao_registra_turno;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.mail;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tarea_periodica;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.sibod.DAO.dao_mail;
import bp.aplicaciones.sibod.modelo.modelo_mail_destinatarios;
import bp.aplicaciones.sibod.modelo.modelo_mail_parametros;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnTurno, btnNuevo, btnRefrescar, btnActualizarEstado, btnEliminarRegistros;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxBitacora;
	@Wire
	Combobox cmbLimite, cmbCliente, cmbTurno, cmbTipoServicio, cmbUsuario, cmbTipoTarea;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Menuitem mModificar, mEliminar, mCerrar, mCerrarRegistro;
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

	List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_parametros_generales_5> listaParametros5 = new ArrayList<modelo_parametros_generales_5>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	boolean cumple_condicion_modificar = false;
	boolean cumple_condicion_eliminar = false;

	boolean ingresa_a_modificar = false;

	long id_opcion = 3;
	long id_turno = 0;

	boolean es_turno_extendido = false;
	boolean se_inicia_turno = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	String turno = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(3);
		inicializarListas();
		inicializarPermisos();
		setearFechaActual();
		validarTurno();
		if (cmbCliente.getItems().size() > 0) {
			cmbCliente.setSelectedIndex(1);
		}
		if (consultar.equals("S")) {
			setearBitacora();
			txtBuscar.setDisabled(false);
			lbxBitacora.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxBitacora.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
			btnNuevo.setVisible(false);
		} else {
			btnNuevo.setDisabled(true);
			btnNuevo.setVisible(false);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase().trim());
			}
		});
		setearRegistroDeTurnos();
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

	public List<modelo_tipo_servicio> obtenerTipoServicio() {
		return listaTipoServicio;
	}

	public List<modelo_tipo_tarea> obtenerTipoTarea() {
		return listaTipoTarea;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_registra_turno> obtenerRegistroTurno() {
		return listaRegistroTurno;
	}

	public List<modelo_bitacora> obtenerBitacoras() {
		return listaBitacora;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTipoServicio = consultasABaseDeDatos.cargarTipoDeServicios("", 1, 0, 0);
		listaTipoTarea = consultasABaseDeDatos.cargarTipoDeTareas("", 1, 0, 0);
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
		listaParametros5 = consultasABaseDeDatos.cargarParametros5("", String.valueOf(id_opcion), 2);
		listaUsuario = consultasABaseDeDatos.consultarUsuarios(id_dc, 0, "", "", 0, 2);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbTipoServicio);
		binder.loadComponent(cmbTipoTarea);
		binder.loadComponent(cmbTurno);
		binder.loadComponent(cmbUsuario);
	}

	public void setearBitacora()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		String turno = "";
		long id_cliente = 0;
		long id_tipo_servicio = 0;
		long id_tipo_tarea = 0;
		String use_usuario = "";
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (cmbTipoServicio.getSelectedItem() != null) {
			id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
		}
		if (cmbTipoTarea.getSelectedItem() != null) {
			id_tipo_tarea = Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString());
		}
		if (cmbUsuario.getSelectedItem() != null) {
			use_usuario = cmbUsuario.getSelectedItem().getValue().toString();
		}
		if (cmbTurno.getSelectedItem() != null) {
			turno = cmbTurno.getSelectedItem().getValue().toString();
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		listaBitacora = consultasABaseDeDatos.cargarBitacoras(criterio, 1, id_cliente, "", turno, id_dc, fecha_inicio,
				fecha_fin, id_tipo_servicio, id_tipo_tarea, use_usuario,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxBitacora);
	}

	public void setearRegistroDeTurnos()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException, JRException {
		String fecha_actual = fechas.obtenerFechaFormateada(new Date(), "yyyy-MM-dd");
		listaRegistroTurno = consultasABaseDeDatos.cargarRegistroTurnos("", 2, id_turno, fecha_actual, id_dc);
		if (listaRegistroTurno.size() == 0) {
			btnTurno.setLabel("INICIAR TURNO");
			se_inicia_turno = false;
			btnTurno.setStyle("background-color: green; color: white;");
			btnNuevo.setDisabled(true);
		} else {
			if (listaRegistroTurno.get(0).getEst_registra_turno().equals("A")) {
				btnTurno.setLabel("CERRAR TURNO");
				se_inicia_turno = true;
				btnTurno.setStyle("background-color: red; color: white;");
				btnNuevo.setDisabled(false);
			} else {
				btnTurno.setLabel("INICIAR TURNO");
				se_inicia_turno = false;
				btnTurno.setStyle("background-color: green; color: white;");
				btnNuevo.setDisabled(true);
			}
		}
	}

	public void enviarCorreoConAdjunto()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException, JRException {
		Fechas fechas = new Fechas();
		mail mail = new mail();
		dao_mail dao = new dao_mail();
		String destinatarios[] = null;
		String remitente = "", clave = "", asunto = "", cuerpo = "", host = "", starttls = "", port = "", auth = "",
				ssl = "", debug = "";
		List<modelo_mail_parametros> lista_parametros = new ArrayList<modelo_mail_parametros>();
		List<modelo_mail_destinatarios> lista_destinatarios = new ArrayList<modelo_mail_destinatarios>();
		lista_parametros = dao.obtenerParametros("1", 2);
		if (lista_parametros.size() == 1) {
			remitente = lista_parametros.get(0).getMail_remitente();
			clave = lista_parametros.get(0).getPass_remitente();
			host = lista_parametros.get(0).getSmtp_host();
			starttls = lista_parametros.get(0).getSmtp_starttls();
			port = lista_parametros.get(0).getSmtp_puerto();
			auth = lista_parametros.get(0).getSmtp_auth();
			debug = lista_parametros.get(0).getSmtp_debug();
			ssl = lista_parametros.get(0).getSmtp_trust();
			lista_destinatarios = dao.obtenerDestinatarios(String.valueOf(lista_parametros.get(0).getId_parametro()),
					1);
		} else {
			return;
		}
		if (lista_destinatarios.size() > 0) {
			destinatarios = new String[lista_destinatarios.size()];
			for (int i = 0; i < lista_destinatarios.size(); i++) {
				destinatarios[i] = lista_destinatarios.get(i).getEmail_destinatario();
			}
		} else {
			return;
		}
		dao_localidad dao1 = new dao_localidad();
		List<modelo_localidad> localidad = new ArrayList<modelo_localidad>();
		localidad = dao1.obtenerLocalidades(String.valueOf(id_dc), 1, 0, 0);
		if (localidad.size() == 0) {
			return;
		}
		asunto = "DC - REGISTROS ABIERTOS EN REPORTE SERVICIOS POSTVENTA DATACENTER - "
				+ fechas.obtenerFechaFormateada(new Date(), "yyyy-MM-dd");
		cuerpo = "<p><strong>Estimad@s,</strong></p>\r\n"
				+ "<p>Se adjunta el reporte \"FO-PV-07 Registro servicios postventa Datacenter\" con los registros que se encuentran <strong>abiertos</strong> desde el <strong>"
				+ fechas.obtenerFechaFormateada(cargarFechaHoraInicio(), "yyyy-MM-dd") + " hasta el "
				+ fechas.obtenerFechaFormateada(cargarFechaHoraFin(), "yyyy-MM-dd") + "</strong>.</p>";
		mail.enviarMailMasivoConAdjunto(remitente, clave, destinatarios, asunto, cuerpo, host, starttls, port, auth,
				ssl, debug, id_dc, localidad.get(0).getNom_localidad(), cargarFechaHoraInicio(), cargarFechaHoraFin());

	}

	public Date cargarFechaHoraInicio() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		LocalDateTime localDateTime = null;
		LocalDate localDate = primerDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, primerDiaMes.getMonth() + 1, primerDiaMes.getDate(), 0, 0);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return d;
	}

	public Date cargarFechaHoraFin() {
		Date fechaActual = new Date();
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		LocalDateTime localDateTime = null;
		LocalDate localDate = ultimoDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, ultimoDiaMes.getMonth() + 1, ultimoDiaMes.getDate(), 23, 59);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return d;
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
					dtxFechaInicio.setValue(d2);
					dtxFechaFin.setValue(d3);
					i = listaTurno.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 23, 59, 59);
				fecha_fin_turno_extendido = d3;
				d4 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				fecha_inicio_turno_extendido = d4;
				d5 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				Date someDate = new Date();
				Date newDate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1));
				d6 = fechas.obtenerFechaArmada(newDate, newDate.getMonth(), newDate.getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d6;
				// Si es antes de las 23:59
				if (d2.before(d1) && d1.before(d3)) {
					dtxFechaInicio.setValue(d2);
					dtxFechaFin.setValue(d3);
				}
				// Si es despues de las 00:00
				if (d4.before(d1) && d1.before(d5)) {
					dtxFechaInicio.setValue(d4);
					dtxFechaFin.setValue(d5);
				}
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

	public void buscarBitacoras(String criterio, int tipo, long id_cliente, String fecha, String turno,
			String fecha_inicio, String fecha_fin, long id_tipo_servicio, long id_tipo_tarea, String use_usuario)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		if (txtBuscar.getText().length() <= 0) {
			listaBitacora = consultasABaseDeDatos.cargarBitacoras(criterio, tipo, id_cliente, fecha, turno, id_dc,
					fecha_inicio, fecha_fin, id_tipo_servicio, id_tipo_tarea, use_usuario,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		}
		if (!txtBuscar.getValue().equals("")) {
			listaBitacora = consultasABaseDeDatos.cargarBitacoras(criterio, tipo, id_cliente, fecha, turno, id_dc,
					fecha_inicio, fecha_fin, id_tipo_servicio, id_tipo_tarea, use_usuario,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		}
		binder.loadComponent(lbxBitacora);
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onSelect=#cmbTipoServicio")
	public void onSelect$cmbTipoServicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onSelect=#cmbTipoTarea")
	public void onSelect$cmbTipoTarea() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	@Listen("onSelect=#cmbUsuario")
	public void onSelect$cmbUsuario() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	public void consultarBitacora() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		long id_cliente = 0;
		long id_tipo_servicio = 0;
		long id_tipo_tarea = 0;
		String use_usuario = "";
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (cmbTipoServicio.getSelectedItem() != null) {
			id_tipo_servicio = Long.valueOf(cmbTipoServicio.getSelectedItem().getValue().toString());
		}
		if (cmbTipoTarea.getSelectedItem() != null) {
			id_tipo_tarea = Long.valueOf(cmbTipoTarea.getSelectedItem().getValue().toString());
		}
		if (cmbUsuario.getSelectedItem() != null) {
			use_usuario = cmbUsuario.getSelectedItem().getValue().toString();
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		buscarBitacoras(criterio, 1, id_cliente, "", "", fecha_inicio, fecha_fin, id_tipo_servicio, id_tipo_tarea,
				use_usuario);
	}

	@Listen("onClick=#mCerrar")
	public void onClickmCerrar()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException, JRException {
		onClickbtnActualizarEstado();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnActualizarEstado")
	public void onClickbtnActualizarEstado()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException, JRException {
		if (lbxBitacora.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		setearRegistroDeTurnos();
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
		List<modelo_bitacora> _listaBitacora;
		if (consultarPermisoUsuario()) {
			_listaBitacora = actualizarEstadoConPermiso();
		} else {
			_listaBitacora = actualizarEstadoSinPermiso();
		}
		if (cumple_condicion_modificar == false) {
			Messagebox.show(informativos.getMensaje_informativo_29(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (_listaBitacora.size() == 0 && cumple_condicion_modificar == true) {
			Messagebox.show(informativos.getMensaje_informativo_28(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_bitacora dao = new dao_bitacora();
							try {
								dao.cambiarEstadoBitacoraMasivo(_listaBitacora);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								recargarListaBitacoraDespuesActualizar();
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(), informativos.getMensaje_informativo_24(),
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Listen("onClick=#mEliminar")
	public void onClickmEliminar()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException, JRException {
		onClickbtnEliminarRegistros();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnEliminarRegistros")
	public void onClickbtnEliminarRegistros()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException, JRException {
		if (lbxBitacora.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		setearRegistroDeTurnos();
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
		List<modelo_bitacora> _listaBitacora;
		if (consultarPermisoUsuario()) {
			_listaBitacora = eliminarRegistroConPermiso();
		} else {
			_listaBitacora = eliminarRegistroSinPermiso();
		}
		if (cumple_condicion_eliminar == false) {
			Messagebox.show(informativos.getMensaje_informativo_31(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (_listaBitacora.size() == 0 && cumple_condicion_eliminar == true) {
			Messagebox.show(informativos.getMensaje_informativo_30(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_27(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_bitacora dao = new dao_bitacora();
							try {
								dao.eliminarBitacoraMasivo(_listaBitacora);
								Messagebox.show(informativos.getMensaje_informativo_59(),
										informativos.getMensaje_informativo_27(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								recargarListaBitacoraDespuesEliminar();
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

	public List<modelo_bitacora> actualizarEstadoSinPermiso() {
		List<modelo_bitacora> _listaBitacora = new ArrayList<modelo_bitacora>();
		Iterator<Listitem> it = lbxBitacora.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_bitacora registro = new modelo_bitacora();
			Listitem item = it.next();
			int indice = item.getIndex();
			Date d1 = listaBitacora.get(indice).getFec_inicio();
			Date d2 = listaBitacora.get(indice).getFec_fin();
			if (seEncuentraEnRangoDeFechasPermitido(d1, d2) == true) {
				registro = listaBitacora.get(indice);
				if (registro.getId_estado_bitacora() == 1) {
					modelo_bitacora bitacora = new modelo_bitacora();
					bitacora = registro.clone();
					bitacora.setId_estado_bitacora(2);
					bitacora.setUsu_modifica(user);
					bitacora.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					_listaBitacora.add(bitacora);
				}
				cumple_condicion_modificar = true;
			} else {
				_listaBitacora = new ArrayList<modelo_bitacora>();
				cumple_condicion_modificar = false;
				break;
			}
		}
		return _listaBitacora;
	}

	public List<modelo_bitacora> actualizarEstadoConPermiso() {
		List<modelo_bitacora> _listaBitacora = new ArrayList<modelo_bitacora>();
		Iterator<Listitem> it = lbxBitacora.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_bitacora registro = new modelo_bitacora();
			Listitem item = it.next();
			int indice = item.getIndex();
			registro = listaBitacora.get(indice);
			if (registro.getId_estado_bitacora() == 1) {
				modelo_bitacora bitacora = new modelo_bitacora();
				bitacora = registro.clone();
				bitacora.setId_estado_bitacora(2);
				bitacora.setUsu_modifica(user);
				bitacora.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
				_listaBitacora.add(bitacora);
			}
			cumple_condicion_modificar = true;
		}
		return _listaBitacora;
	}

	public List<modelo_bitacora> eliminarRegistroSinPermiso()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_bitacora> _listaBitacora = new ArrayList<modelo_bitacora>();
		Iterator<Listitem> it = lbxBitacora.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_bitacora registro = new modelo_bitacora();
			Listitem item = it.next();
			int indice = item.getIndex();
			Date d1 = listaBitacora.get(indice).getFec_inicio();
			Date d2 = listaBitacora.get(indice).getFec_fin();
			if (seEncuentraEnRangoDeFechasPermitido(d1, d2)) {
				registro = listaBitacora.get(indice);
				if (consultarPermisoServicio(registro.getId_tipo_servicio()) == true) {
					_listaBitacora.add(registro);
				} else {
					_listaBitacora = new ArrayList<modelo_bitacora>();
					cumple_condicion_eliminar = true;
					break;
				}
				cumple_condicion_eliminar = true;
			} else {
				_listaBitacora = new ArrayList<modelo_bitacora>();
				cumple_condicion_eliminar = false;
				break;
			}
		}
		return _listaBitacora;
	}

	public List<modelo_bitacora> eliminarRegistroConPermiso() {
		List<modelo_bitacora> _listaBitacora = new ArrayList<modelo_bitacora>();
		Iterator<Listitem> it = lbxBitacora.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_bitacora registro = new modelo_bitacora();
			Listitem item = it.next();
			int indice = item.getIndex();
			registro = listaBitacora.get(indice);
			_listaBitacora.add(registro);
			cumple_condicion_eliminar = true;
		}
		return _listaBitacora;
	}

	public void recargarListaBitacoraDespuesActualizar()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	public void recargarListaBitacoraDespuesEliminar()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarBitacora();
	}

	public boolean seEncuentraEnRangoDeFechasPermitido(Date d1, Date d2) {
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
				if ((newDate.before(d1) || newDate.equals(d1)) && (d1.before(d2) || d1.equals(d2))
						&& (d2.before(fecha_fin) || d2.equals(fecha_fin))) {
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
				if ((fecha_inicio.before(d1) || fecha_inicio.equals(d1)) && (d1.before(d2) || d1.equals(d2))
						&& (d2.before(newDate) || d2.equals(newDate))) {
					rango_permitido = true;
				} else {
					rango_permitido = false;
				}
			}
		} else {
			if ((fecha_inicio.before(d1) || fecha_inicio.equals(d1)) && (d1.before(d2) || d1.equals(d2))
					&& (d2.before(fecha_fin) || d2.equals(fecha_fin))) {
				rango_permitido = true;
			} else {
				rango_permitido = false;
			}
		}
		return rango_permitido;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnTurno")
	public void onClickbtnTurno() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (obtenerEtiquetaTicketExternoBitacora().equals("")) {
			Messagebox.show(informativos.getMensaje_informativo_50(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (obtenerSecuenciaTicketExternoBitacora() == 0) {
			Messagebox.show(informativos.getMensaje_informativo_51(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (obtenerEstadoParaTareasPeriodicas() == 0) {
			Messagebox.show(informativos.getMensaje_informativo_52(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (obtenerEstadoParaCerrarTareas() == 0) {
			Messagebox.show(informativos.getMensaje_informativo_56(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		validarTurno();
		if (validarSiSeIniciaTurno() == false) {
			// Iniciar turno
			Messagebox.show(informativos.getMensaje_informativo_39(), informativos.getMensaje_informativo_40(),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								if (validarSiSeIniciaTurno() == true) {
									String nombre_usuario = "";
									if (listaRegistroTurno.size() > 0) {
										nombre_usuario = consultarUsuario(listaRegistroTurno.get(0).getUsu_inicia());
										if (nombre_usuario.equals("")) {
											Messagebox.show(informativos.getMensaje_informativo_46(),
													informativos.getMensaje_informativo_40(), Messagebox.OK,
													Messagebox.EXCLAMATION);
										} else {
											Messagebox.show(
													informativos.getMensaje_informativo_44()
															.replace("-?", nombre_usuario).replace("?-",
																	fechas.obtenerFechaFormateada(
																			listaRegistroTurno.get(0).getFec_inicio(),
																			"dd/MM/yyyy H:mm:ss")),
													informativos.getMensaje_informativo_40(), Messagebox.OK,
													Messagebox.EXCLAMATION);
										}

									} else {
										Messagebox.show(informativos.getMensaje_informativo_46(),
												informativos.getMensaje_informativo_40(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									}
									setearRegistroDeTurnos();
									return;
								}
								//
								List<modelo_bitacora> tareas_periodicas_bitacora = new ArrayList<modelo_bitacora>();
								modelo_registra_turno registro_tuno = new modelo_registra_turno();
								dao_registra_turno dao1 = new dao_registra_turno();
								dao_bitacora dao2 = new dao_bitacora();
								boolean se_deben_cargar_tareas_periodicas = false;
								// Si es la primera creacion de un turno
								if (listaRegistroTurno.size() == 0) {
									registro_tuno = setearParametrosRegistroTurno(1);
									se_deben_cargar_tareas_periodicas = true;
									tareas_periodicas_bitacora = convertirTareasPeriodicasATareasDeBitacora();
								}
								// Si ya existen turnos creados pero estan cerrados
								if (listaRegistroTurno.size() > 0) {
									if (listaRegistroTurno.get(0).getEst_registra_turno().equals("C")) {
										int se_crearon_tareas_periodicas = consultasABaseDeDatos
												.validarSiTareasAutomaticasSeCrearon(
														fechas.obtenerFechaFormateada(fecha_inicio,
																"yyyy/MM/dd HH:mm:ss"),
														fechas.obtenerFechaFormateada(fecha_fin,
																"yyyy/MM/dd HH:mm:ss"));
										registro_tuno = setearParametrosRegistroTurno(1);
										// Si el turno es diferente al turno actual se crean las tareas periodicas
										Date d1 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(),
												new Date().getDate(), new Date().getHours(), new Date().getMinutes(),
												new Date().getSeconds());
										Date d2 = fechas.obtenerFechaArmada(new Date(),
												listaRegistroTurno.get(0).getFec_fin().getMonth(),
												listaRegistroTurno.get(0).getFec_fin().getDate(),
												listaRegistroTurno.get(0).getFec_fin().getHours(),
												listaRegistroTurno.get(0).getFec_fin().getMinutes(),
												listaRegistroTurno.get(0).getFec_fin().getSeconds());
										// Si el turno no es extendido
										if (es_turno_extendido == false) {
											if (listaRegistroTurno.get(0).getId_turno() != id_turno && d1.after(d2)
													&& se_crearon_tareas_periodicas == 0) {
												se_deben_cargar_tareas_periodicas = true;
												tareas_periodicas_bitacora = convertirTareasPeriodicasATareasDeBitacora();
											} else if (listaRegistroTurno.get(0).getId_turno() == id_turno
													&& d1.after(d2) && se_crearon_tareas_periodicas == 0) {
												se_deben_cargar_tareas_periodicas = true;
												tareas_periodicas_bitacora = convertirTareasPeriodicasATareasDeBitacora();
											} else {
												se_deben_cargar_tareas_periodicas = false;
											}
										} else { // Si el turno es extendido
											// Para turnos extendidos solo se permite la creacion de tareas automaticas
											// hasta las 23:59:59
											Date d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(),
													new Date().getDate(), 23, 59, 59);
											if (fecha_inicio.before(d1) && d1.before(d3)) {
												if (listaRegistroTurno.get(0).getId_turno() != id_turno && d1.after(d2)
														&& se_crearon_tareas_periodicas == 0) {
													se_deben_cargar_tareas_periodicas = true;
													tareas_periodicas_bitacora = convertirTareasPeriodicasATareasDeBitacora();
												} else if (listaRegistroTurno.get(0).getId_turno() == id_turno
														&& d1.after(d2) && se_crearon_tareas_periodicas == 0) {
													se_deben_cargar_tareas_periodicas = true;
													tareas_periodicas_bitacora = convertirTareasPeriodicasATareasDeBitacora();
												} else {
													se_deben_cargar_tareas_periodicas = false;
												}
											}
										}
									}
								}
								try {
									dao1.insertarRegistroTurno(registro_tuno);
									if (se_deben_cargar_tareas_periodicas == true) {
										String etiqueta_bitacora = obtenerEtiquetaTicketExternoBitacora();
										long secuencia_bitacora = obtenerSecuenciaTicketExternoBitacora();
										dao2.insertarTareas(tareas_periodicas_bitacora, etiqueta_bitacora,
												secuencia_bitacora);
										Messagebox.show(
												informativos.getMensaje_informativo_53().replace("?",
														String.valueOf(tareas_periodicas_bitacora.size())),
												informativos.getMensaje_informativo_40(), Messagebox.OK,
												Messagebox.EXCLAMATION);
										onClick$btnRefrescar();
									} else {
										Messagebox.show(informativos.getMensaje_informativo_49(),
												informativos.getMensaje_informativo_40(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									}
									setearRegistroDeTurnos();
								} catch (Exception e) {
									Messagebox.show(
											error.getMensaje_error_4() + error.getMensaje_error_1() + e.getMessage(),
											informativos.getMensaje_informativo_40(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
							}
						}
					});
		} else {
			// Cerrar turno
			Messagebox.show(informativos.getMensaje_informativo_41(), informativos.getMensaje_informativo_42(),
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								System.out.println(System.getProperty("user.dir"));
								// enviarCorreoConAdjunto();
								if (validarSiSeIniciaTurno() == false) {
									String nombre_usuario = "";
									if (listaRegistroTurno.size() > 0) {
										nombre_usuario = consultarUsuario(listaRegistroTurno.get(0).getUsu_termina());
										if (nombre_usuario.equals("")) {
											Messagebox.show(informativos.getMensaje_informativo_45(),
													informativos.getMensaje_informativo_42(), Messagebox.OK,
													Messagebox.EXCLAMATION);
										} else {
											Messagebox.show(
													informativos.getMensaje_informativo_43()
															.replace("-?", nombre_usuario).replace("?-",
																	fechas.obtenerFechaFormateada(
																			listaRegistroTurno.get(0).getFec_fin(),
																			"dd/MM/yyyy H:mm:ss")),
													informativos.getMensaje_informativo_42(), Messagebox.OK,
													Messagebox.EXCLAMATION);
										}

									} else {
										Messagebox.show(informativos.getMensaje_informativo_45(),
												informativos.getMensaje_informativo_42(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									}
									setearRegistroDeTurnos();
									return;
								}
								//
								modelo_registra_turno registro_tuno = new modelo_registra_turno();
								dao_registra_turno dao1 = new dao_registra_turno();
								if (listaRegistroTurno.size() > 0) {
									if (listaRegistroTurno.get(0).getEst_registra_turno().equals("A")) {
										registro_tuno = setearParametrosRegistroTurno(2);
										registro_tuno
												.setId_registra_turno(listaRegistroTurno.get(0).getId_registra_turno());
										registro_tuno.setId_turno(listaRegistroTurno.get(0).getId_turno());
										registro_tuno.setUsu_inicia(listaRegistroTurno.get(0).getUsu_inicia());
										registro_tuno.setFec_inicio(listaRegistroTurno.get(0).getFec_inicio());
									}
								}
								try {
									dao1.modificarRegistroTurno(registro_tuno);
									int cantidad_tareas_cerradas = cambiarEstadoTareasAbiertas(
											obtenerEstadoParaCerrarTareas());
									setearRegistroDeTurnos();
									if (cantidad_tareas_cerradas == 0) {
										Messagebox.show(informativos.getMensaje_informativo_48(),
												informativos.getMensaje_informativo_42(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									} else if (cantidad_tareas_cerradas == 1) {
										Messagebox.show(
												informativos.getMensaje_informativo_57().replace("?",
														String.valueOf(cantidad_tareas_cerradas)),
												informativos.getMensaje_informativo_42(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									} else {
										Messagebox.show(
												informativos.getMensaje_informativo_58().replace("?",
														String.valueOf(cantidad_tareas_cerradas)),
												informativos.getMensaje_informativo_42(), Messagebox.OK,
												Messagebox.EXCLAMATION);
									}
									onClick$btnRefrescar();
								} catch (Exception e) {
									Messagebox.show(
											error.getMensaje_error_4() + error.getMensaje_error_1() + e.getMessage(),
											informativos.getMensaje_informativo_40(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
							}
						}
					});
		}
	}

	public String consultarUsuario(String usuario) throws ClassNotFoundException, FileNotFoundException, IOException {
		String nombre_usuario = "";
		List<modelo_usuario> listaUsuarios = new ArrayList<modelo_usuario>();
		listaUsuarios = consultasABaseDeDatos.consultarUsuarios(id_dc, 0, "", "", 0, 2);
		Iterator<modelo_usuario> it = listaUsuarios.iterator();
		while (it.hasNext()) {
			modelo_usuario modelo = it.next();
			if (modelo.getUse_usuario().equals(usuario)) {
				nombre_usuario = modelo.verNombreCompleto();
				break;
			}
		}
		return nombre_usuario;
	}

	public modelo_registra_turno setearParametrosRegistroTurno(int tipo) {
		modelo_registra_turno registro;
		registro = new modelo_registra_turno();
		// Iniciar turno
		if (tipo == 1) {
			registro.setId_turno(id_turno);
			registro.setFec_inicio(fechas.obtenerTimestampDeDate(new Date()));
			registro.setId_localidad(id_dc);
			registro.setEst_registra_turno("A");
			registro.setUsu_inicia(user);
		}
		// Cerrar turno
		if (tipo == 2) {
			registro.setFec_fin(fechas.obtenerTimestampDeDate(new Date()));
			registro.setId_localidad(id_dc);
			registro.setEst_registra_turno("C");
			registro.setUsu_termina(user);
		}
		return registro;
	}

	public List<modelo_tarea_periodica> cargarTareasPeriodicasDelDia()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		int dia = 0;
		int hora_turno_extendido_rango_1 = 0, hora_turno_extendido_rango_2 = 0, hora_turno_extendido_rango_3 = 0,
				hora_turno_extendido_rango_4 = 0;
		int hora_inicio_tarea_periodica = 0;
		dia = fechas.obtenerEnteroDelDiaAPartirUnaFecha(
				fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		List<modelo_tarea_periodica> listaTareasPeriodicasDelDia = new ArrayList<modelo_tarea_periodica>();
		List<modelo_tarea_periodica> listaTareasPeriodicasDelDiaFormateada = new ArrayList<modelo_tarea_periodica>();
		listaTareasPeriodicasDelDia = consultasABaseDeDatos.cargarTareasPeriodicas(String.valueOf(dia), 3, id_dc, "",
				"", 0);
		Iterator<modelo_tarea_periodica> it = listaTareasPeriodicasDelDia.iterator();
		while (it.hasNext()) {
			modelo_tarea_periodica tarea_periodica = it.next();
			hora_inicio_tarea_periodica = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(tarea_periodica.getFec_inicio());
			if (es_turno_extendido == true) {
				hora_turno_extendido_rango_1 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_inicio);
				hora_turno_extendido_rango_2 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_fin_turno_extendido);
				hora_turno_extendido_rango_3 = fechas
						.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_inicio_turno_extendido);
				hora_turno_extendido_rango_4 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_fin);
				if ((hora_inicio_tarea_periodica >= hora_turno_extendido_rango_3)
						&& (hora_inicio_tarea_periodica <= hora_turno_extendido_rango_4)) {
					// Se trata de tareas para el dia siguiente en el rango [0-7], se suma 1 al dia
					// actual
					Date someDate = new Date();
					Date newDate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1));
					tarea_periodica.setFec_inicio(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(newDate,
							newDate.getMonth(), newDate.getDate(), tarea_periodica.getFec_inicio().getHours(),
							tarea_periodica.getFec_inicio().getMinutes(),
							tarea_periodica.getFec_inicio().getSeconds())));
					tarea_periodica.setFec_fin(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(newDate,
							newDate.getMonth(), newDate.getDate(), tarea_periodica.getFec_fin().getHours(),
							tarea_periodica.getFec_fin().getMinutes(), tarea_periodica.getFec_fin().getSeconds())));
				}
				if ((hora_inicio_tarea_periodica >= hora_turno_extendido_rango_1)
						&& (hora_inicio_tarea_periodica <= hora_turno_extendido_rango_2)) {
					// Se trata de tareas del mismo dia en el rango [20-23], no se suma al dia
					// siempre que se cumpla que hora inicio sea menor a hora final
					tarea_periodica.setFec_inicio(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(),
							new Date().getMonth(), new Date().getDate(), tarea_periodica.getFec_inicio().getHours(),
							tarea_periodica.getFec_inicio().getMinutes(),
							tarea_periodica.getFec_inicio().getSeconds())));
					tarea_periodica.setFec_fin(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(),
							new Date().getMonth(), new Date().getDate(), tarea_periodica.getFec_fin().getHours(),
							tarea_periodica.getFec_fin().getMinutes(), tarea_periodica.getFec_fin().getSeconds())));
				}
				listaTareasPeriodicasDelDiaFormateada.add(tarea_periodica);
			} else {
				hora_turno_extendido_rango_1 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_inicio);
				hora_turno_extendido_rango_2 = fechas.obtenerEnteroDeLaHoraAPartirUnaFecha(fecha_fin);
				// Se trata de tareas del mismo dia en el rango [8-19] o [8-15] o [16-23] o
				// [0-7], no se suma al dia siempre que se cumpla que hora inicio sea menor a
				// hora final.
				if ((hora_inicio_tarea_periodica >= hora_turno_extendido_rango_1)
						&& (hora_inicio_tarea_periodica <= hora_turno_extendido_rango_2)) {
					tarea_periodica.setFec_inicio(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(),
							new Date().getMonth(), new Date().getDate(), tarea_periodica.getFec_inicio().getHours(),
							tarea_periodica.getFec_inicio().getMinutes(),
							tarea_periodica.getFec_inicio().getSeconds())));
					tarea_periodica.setFec_fin(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(),
							new Date().getMonth(), new Date().getDate(), tarea_periodica.getFec_fin().getHours(),
							tarea_periodica.getFec_fin().getMinutes(), tarea_periodica.getFec_fin().getSeconds())));
				}
				listaTareasPeriodicasDelDiaFormateada.add(tarea_periodica);
			}
		}
		return listaTareasPeriodicasDelDiaFormateada;
	}

	public List<modelo_tarea_periodica> obtenerTareasPeriodicasDelTurno()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		long estado_tarea_periodica = obtenerEstadoParaTareasPeriodicas();
		Date fecha_inicio_turno = null, fecha_fin_turno = null;
		fecha_inicio_turno = fechas.obtenerFechaArmada(new Date(), fecha_inicio.getMonth(), fecha_inicio.getDate(),
				fecha_inicio.getHours(), fecha_inicio.getMinutes(), 0);
		fecha_fin_turno = fechas.obtenerFechaArmada(fecha_fin, fecha_fin.getMonth(), fecha_fin.getDate(),
				fecha_fin.getHours(), fecha_fin.getMinutes(), 0);
		List<modelo_tarea_periodica> listaTareasPeriodicasDelDia = new ArrayList<modelo_tarea_periodica>();
		List<modelo_tarea_periodica> listaTareasPeriodicasDelTurno = new ArrayList<modelo_tarea_periodica>();
		listaTareasPeriodicasDelDia = cargarTareasPeriodicasDelDia();
		Iterator<modelo_tarea_periodica> it = listaTareasPeriodicasDelDia.iterator();
		while (it.hasNext()) {
			modelo_tarea_periodica tarea_periodica = it.next();
			if ((fecha_inicio_turno.before(tarea_periodica.getFec_inicio())
					|| fecha_inicio_turno.equals(tarea_periodica.getFec_inicio()))
					&& (tarea_periodica.getFec_inicio().before(fecha_fin_turno))) {
				tarea_periodica.setId_estado_bitacora(estado_tarea_periodica);
				listaTareasPeriodicasDelTurno.add(tarea_periodica);
			}
		}
		return listaTareasPeriodicasDelTurno;
	}

	public List<modelo_bitacora> convertirTareasPeriodicasATareasDeBitacora()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_bitacora> listaTareasBitacora = new ArrayList<modelo_bitacora>();
		List<modelo_tarea_periodica> listaTareasPeriodicasDelTurno = new ArrayList<modelo_tarea_periodica>();
		listaTareasPeriodicasDelTurno = obtenerTareasPeriodicasDelTurno();
		Iterator<modelo_tarea_periodica> it = listaTareasPeriodicasDelTurno.iterator();
		while (it.hasNext()) {
			modelo_tarea_periodica tarea_periodica = it.next();
			modelo_bitacora bitacora = new modelo_bitacora();
			bitacora.setId_cliente(tarea_periodica.getId_cliente());
			bitacora.setId_turno(id_turno);
			// bitacora.setId_solicitante(id_user);
			bitacora.setId_tipo_servicio(tarea_periodica.getId_tipo_servicio());
			if (bitacora.getId_tipo_servicio() == 13) {
				bitacora.setId_tipo_clasificacion(2);
			}
			bitacora.setId_tipo_tarea(tarea_periodica.getId_tipo_tarea());
			bitacora.setDescripcion(tarea_periodica.getDescripcion());
			bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(tarea_periodica.getFec_inicio()));
			bitacora.setFec_fin(fechas.obtenerTimestampDeDate(tarea_periodica.getFec_fin()));
			bitacora.setId_estado_bitacora(tarea_periodica.getId_estado_bitacora());
			bitacora.setCumplimiento(tarea_periodica.getCumplimiento());
			bitacora.setId_localidad(id_dc);
			bitacora.setEst_bitacora(tarea_periodica.getEst_tarea_periodica());
			bitacora.setUsu_ingresa(user);
			bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
			listaTareasBitacora.add(bitacora);
		}
		return listaTareasBitacora;
	}

	public String obtenerEtiquetaTicketExternoBitacora()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		String etiqueta_bitacora = "";
		List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getEtiqueta_bitacora() != null) {
				etiqueta_bitacora = listaParametros1.get(0).getEtiqueta_bitacora();
			} else {
				etiqueta_bitacora = "TB-000";
			}
		} else {
			etiqueta_bitacora = "TB-000";
		}
		return etiqueta_bitacora;
	}

	public long obtenerSecuenciaTicketExternoBitacora()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		long secuencia_bitacora = 0;
		List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		if (listaParametros1.size() > 0) {
			secuencia_bitacora = listaParametros1.get(0).getSecuencia_bitacora();
		}
		return secuencia_bitacora;
	}

	public long obtenerEstadoParaTareasPeriodicas() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id_estado = 0;
		List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		if (listaParametros1.size() > 0) {
			id_estado = listaParametros1.get(0).getEstado_bitacora_2();
		}
		return id_estado;
	}

	public long obtenerEstadoParaCerrarTareas() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id_estado = 0;
		List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		if (listaParametros1.size() > 0) {
			id_estado = listaParametros1.get(0).getEstado_bitacora_1();
		}
		return id_estado;
	}

//	@Listen("onRightClick=#lbxBitacora")
//	public void onRightClick$lbxMovimientos() throws Throwable {
//		if (lbxBitacora.getSelectedItem() == null) {
//			return;
//		}
//		int indice = lbxBitacora.getSelectedIndex();
//		consultarBitacora();
//		int tamanio_lista = lbxBitacora.getItemCount();
//		if (indice >= tamanio_lista) {
//			return;
//		}
//		lbxBitacora.setSelectedIndex(indice);
//	}

	@Listen("onClick=#mModificar")
	public void onClickmModificar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxBitacora.getSelectedItem() == null) {
			return;
		}
		if (lbxBitacora.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxBitacora.getSelectedIndex();
		Tabbox tTab = (Tabbox) zConsultar.getParent().getParent().getParent().getParent().getParent().getParent();
		Tabpanels tPanel = (Tabpanels) zConsultar.getParent().getParent().getParent().getParent().getParent();
		long id_bitacora = listaBitacora.get(indice).getId_bitacora();
		String ticket = listaBitacora.get(indice).getTicket_externo();
		Sessions.getCurrent().setAttribute("bitacora", listaBitacora.get(indice));
		crearPestanaParaModificar(tTab, tPanel, id_bitacora, ticket);
	}

	@Listen("onClick=#mCerrarRegistro")
	public void onClickmCerrarRegistro()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxBitacora.getSelectedItem() == null) {
			return;
		}
		if (lbxBitacora.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxBitacora.getSelectedIndex();
		Tabbox tTab = (Tabbox) zConsultar.getParent().getParent().getParent().getParent().getParent().getParent();
		Tabpanels tPanel = (Tabpanels) zConsultar.getParent().getParent().getParent().getParent().getParent();
		long id_bitacora = listaBitacora.get(indice).getId_bitacora();
		String ticket = listaBitacora.get(indice).getTicket_externo();
		if (listaBitacora.get(indice).getId_tipo_tarea() != 1) {
			Messagebox.show(informativos.getMensaje_informativo_117(), informativos.getMensaje_informativo_116(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiYaExisteRegistroCierre(ticket, 5) == true) {
			Messagebox.show(informativos.getMensaje_informativo_37().replace("-?", "CIERRE").replace("?-", ticket),
					informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Sessions.getCurrent().setAttribute("bitacora", listaBitacora.get(indice));
		crearPestanaParaCerrarRegistro(tTab, tPanel, id_bitacora, ticket);
	}

	public void crearPestanaParaModificar(Tabbox tTab, Tabpanels tPanel, long id_bitacora, String ticket) {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab - LE:" + id_bitacora)) {
				Tab tab2 = (Tab) tTab.getFellow("Tab - LE:" + id_bitacora);
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("LOG DE EVENTOS - MODIFICAR | REGISTRO " + ticket);
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab - LE:" + id_bitacora);
			tab.setImage("/img/botones/ButtonSearch4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = null;
			include = new Include("/bitacora/log_eventos/modificar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crearPestanaParaCerrarRegistro(Tabbox tTab, Tabpanels tPanel, long id_bitacora, String ticket) {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab - LEC:" + id_bitacora)) {
				Tab tab2 = (Tab) tTab.getFellow("Tab - LEC:" + id_bitacora);
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("LOG DE EVENTOS - CERRAR | REGISTRO " + ticket);
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab - LEC:" + id_bitacora);
			tab.setImage("/img/botones/ButtonSearch4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = null;
			include = new Include("/bitacora/log_eventos/cerrar_registro.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int cambiarEstadoTareasAbiertas(long id_estado) throws ClassNotFoundException, FileNotFoundException,
			IOException, MySQLIntegrityConstraintViolationException, SQLException {
		int cantidad_tareas_cerradas = 0;
		List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
		List<modelo_parametros_generales_10> listaParametros10 = new ArrayList<modelo_parametros_generales_10>();
		listaParametros10 = consultasABaseDeDatos.consultarParametrosGenerales10(0, id_opcion, "",
				String.valueOf(id_dc), 0, 4);
		listaBitacora = consultasABaseDeDatos.cargarBitacoras("", 3, 0, "", "", id_dc, "", "", 0, 0, "", 0);
		listaTipoServicio = consultasABaseDeDatos.cargarTipoDeServicios("", 1, 0, 0);
		dao_bitacora dao = new dao_bitacora();
		long id_bitacora = 0;
		long id_tipo_servicio = 0;
		long id_estado_bitacora = 0;
		int contador = 0;
		for (int i = 0; i < listaBitacora.size(); i++) {
			id_bitacora = listaBitacora.get(i).getId_bitacora();
			id_tipo_servicio = listaBitacora.get(i).getId_tipo_servicio();
			id_estado_bitacora = listaBitacora.get(i).getId_estado_bitacora();
			if (id_estado_bitacora == 1) {
				modelo_bitacora bitacora = new modelo_bitacora();
				for (int j = 0; j < listaParametros10.size(); j++) {
					if (listaParametros10.get(j).getTipo_servicio().getId_tipo_servicio() == id_tipo_servicio) {
						contador = contador + 1;
						bitacora.setId_bitacora(id_bitacora);
						bitacora.setId_estado_bitacora(id_estado);
						bitacora.setCumplimiento("I");
						bitacora.setObs_coordinador(
								"INCUMPLIMIENTO EN TAREA, NO SE CAMBIA DE ESTADO A CERRADO ANTES DEL CIERRE DEL TURNO.");
						dao.cambiarEstadoBitacora(bitacora);
						cantidad_tareas_cerradas++;
					}
				}
			}
		}
		return cantidad_tareas_cerradas;
	}

	public boolean validarSiYaExisteRegistroCierre(String ticket_externo, long id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * El metodo valida que no exista un registro de cierre previo
		 */
		boolean existe_registro_cierre = true;
		if (consultasABaseDeDatos.validarSiExisteTareaRegistrada(ticket_externo, String.valueOf(id_tipo_tarea)) == 0) {
			existe_registro_cierre = false;
		}
		return existe_registro_cierre;
	}

}
