package bp.aplicaciones.controlador.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.personal.DAO.dao_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_registro_permanencia;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar_permanencia extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnExit, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxRegistroPermanencia;
	@Wire
	Combobox cmbLimite, cmbCliente, cmbTurno, cmbEstado;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Menuitem mModificar, mRegistrarSalida, mEliminar;
	@Wire
	Menuseparator mSeparador1;
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
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_5> listaParametros5 = new ArrayList<modelo_parametros_generales_5>();
	List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();

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

	Date fecha_ingresa_formulario = null;

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
			setearRegistroPermanencia();
			txtBuscar.setDisabled(false);
			lbxRegistroPermanencia.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxRegistroPermanencia.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_registro_permanencia> obtenerRegistros() {
		return listaRegistroPermanencia;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaParametros5 = consultasABaseDeDatos.cargarParametros5("", String.valueOf(id_opcion), 2);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbTurno);
	}

	public void setearRegistroPermanencia()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = txtBuscar.getText();
		String fecha_inicio = "", fecha_fin = "";
		fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia(criterio, 1,
				cmbEstado.getSelectedItem().getValue().toString(), id_dc,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_inicio, fecha_fin);
		binder.loadComponent(lbxRegistroPermanencia);
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
		localDateTime = LocalDateTime.of(year, primerDiaMes.getMonth() + 1, primerDiaMes.getDate(), 0, 0);
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
		localDateTime = LocalDateTime.of(year, ultimoDiaMes.getMonth() + 1, ultimoDiaMes.getDate(), 23, 59);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaFin.setValue(d);
	}

	public void setearFechaIngresaFormulario() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		fecha_ingresa_formulario = d;
	}

	public void buscarRegistroPermanencia(String criterio, int tipo, long id_cliente, String fecha_solicitud_i,
			String fecha_solicitud_f, String fecha_inicio, String fecha_fin, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		if (txtBuscar.getText().length() <= 0) {
			listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia(criterio, 1,
					cmbEstado.getSelectedItem().getValue().toString(), id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_solicitud_i,
					fecha_solicitud_f);
		}
		if (!txtBuscar.getValue().equals("")) {
			listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia(criterio, 1,
					cmbEstado.getSelectedItem().getValue().toString(), id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_solicitud_i,
					fecha_solicitud_f);
		}
		binder.loadComponent(lbxRegistroPermanencia);
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	public void consultarRegistroPermanencia() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		buscarRegistroPermanencia(criterio, 1, id_cliente, fecha_inicio, fecha_fin, "", "", id_dc);
	}

	@Listen("onClick=#mCerrar")
	public void onClickmCerrar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

	}

	public boolean validarSiTareaExiste(String ticket_externo)
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		List<modelo_bitacora> listatareas = new ArrayList<modelo_bitacora>();
		listatareas = consultasABaseDeDatos.cargarBitacoras(ticket_externo, 4, 0, "", "", id_dc, "", "", 0, 0, "", 0);
		if (listatareas.size() > 0) {
			existe_tarea = true;
		} else {
			existe_tarea = false;
		}
		return existe_tarea;
	}

	@Listen("onRightClick=#lbxRegistroPermanencia")
	public void onRightClick$lbxRegistroPermanencia() throws Throwable {
		if (lbxRegistroPermanencia.getSelectedItem() == null) {
			return;
		}
		if (cmbEstado.getSelectedItem().getValue().equals("A")) {
			mModificar.setVisible(false);
			mModificar.setDisabled(true);
			mSeparador1.setVisible(false);
			mRegistrarSalida.setDisabled(true);
			mRegistrarSalida.setVisible(false);
		} else {
			mModificar.setLabel(" - Modificar salida");
			mModificar.setVisible(true);
			mModificar.setDisabled(false);
			mSeparador1.setVisible(true);
			mRegistrarSalida.setDisabled(true);
			mRegistrarSalida.setVisible(false);
		}
	}

	@Listen("onClick=#mModificar")
	public void onClickmModificar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxRegistroPermanencia.getSelectedItem() == null) {
			return;
		}
		int indice = lbxRegistroPermanencia.getSelectedIndex();
		modelo_registro_permanencia registro_permanencia = new modelo_registro_permanencia();
		registro_permanencia = listaRegistroPermanencia.get(indice);
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			Sessions.getCurrent().setAttribute("registro_permanencia", registro_permanencia);
			if (cmbEstado.getSelectedItem().getValue().equals("A")) {
				window = (Window) Executions.createComponents("/personal/registro_permanencia/modificar_ingreso.zul",
						null, null);
			} else {
				window = (Window) Executions.createComponents("/personal/registro_permanencia/modificar_salida.zul",
						null, null);
			}
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						consultarRegistroPermanencia();
						ingresa_a_modificar = false;
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#mRegistrarSalida")
	public void onClickmRegistrarSalida()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxRegistroPermanencia.getSelectedItem() == null) {
			return;
		}
		if (lbxRegistroPermanencia.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxRegistroPermanencia.getSelectedIndex();
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			Sessions.getCurrent().setAttribute("registro_permanencia", listaRegistroPermanencia.get(indice));
			window = (Window) Executions.createComponents("/personal/registro_permanencia/nuevo_salida.zul", null,
					null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						consultarRegistroPermanencia();
						ingresa_a_modificar = false;
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			window = (Window) Executions.createComponents("/personal/registro_permanencia/nuevo_ingreso.zul", null,
					null);
			btnNuevo.setDisabled(true);
			btnExit.setDisabled(true);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						btnNuevo.setDisabled(false);
						btnExit.setDisabled(false);
						ingresa_a_modificar = false;
						consultarRegistroPermanencia();
					}
				});
			}
			window.setParent(winList);
		}
	}

	@Listen("onClick=#btnExit")
	public void onClick$btnExit() {
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			window = (Window) Executions.createComponents("/personal/registro_permanencia/nuevo_salida.zul", null,
					null);
			btnNuevo.setDisabled(true);
			btnExit.setDisabled(true);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						btnNuevo.setDisabled(false);
						btnExit.setDisabled(false);
						ingresa_a_modificar = false;
						consultarRegistroPermanencia();
					}
				});
			}
			window.setParent(winList);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#mEliminar")
	public void onClick$mEliminar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		String mensaje = informativos.getMensaje_informativo_16();
		if (validarSiSeIniciaTurno() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_27(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (lbxRegistroPermanencia.getSelectedItem() == null) {
			return;
		}
		setearFechaIngresaFormulario();
		Messagebox.show(mensaje, informativos.getMensaje_informativo_27(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							String fecha_inicio = "", fecha_fin = "";
							long secuencia1 = 0;
							int indice = lbxRegistroPermanencia.getSelectedIndex();
							dao_solicitud_personal dao = new dao_solicitud_personal();
							modelo_registro_permanencia registro_permanencia = new modelo_registro_permanencia();
							if (validarSiExistePrimeroApertura(listaRegistroPermanencia.get(indice).getTicket(),
									1) == false) {
								Messagebox.show(
										informativos.getMensaje_informativo_96().replace("?1",
												listaRegistroPermanencia.get(indice).getTicket()),
										informativos.getMensaje_informativo_27(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								return;
							}
							registro_permanencia
									.setId_registro_permanencia(consultar_permanencia.this.listaRegistroPermanencia
											.get(indice).getId_registro_permanencia());
							fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(),
									"yyyy-MM-dd HH:mm:ss");
							fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
							List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
							listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia2(
									String.valueOf(consultar_permanencia.this.listaRegistroPermanencia.get(indice)
											.getId_proveedor()),
									4, String.valueOf(consultar_permanencia.this.listaRegistroPermanencia.get(indice)
											.getId_solicitud()),
									id_dc, 0, fecha_inicio, fecha_fin);
							if (listaRegistroPermanencia.size() == 0) {
								return;
							}
							/* Se inicializa el objeto bitacora */
							modelo_bitacora bitacora = new modelo_bitacora();
							bitacora.setTicket_externo(listaRegistroPermanencia.get(0).getTicket());
							bitacora.setId_cliente(listaRegistroPermanencia.get(0).getId_emp_solicitante());
							bitacora.setId_solicitante(listaRegistroPermanencia.get(0).getId_solicitante());
							bitacora.setId_tipo_servicio(2);
							bitacora.setId_tipo_clasificacion(obtenerIdTipoClasificacionAPartirDeTicket(
									listaRegistroPermanencia.get(0).getTicket().toString(), 1, id_dc));
							bitacora.setId_tipo_tarea(7);
							bitacora.setId_estado_bitacora(2);
							bitacora.setFec_inicio(
									consultar_permanencia.this.listaRegistroPermanencia.get(indice).getFec_ingreso());
							bitacora.setFec_fin(
									consultar_permanencia.this.listaRegistroPermanencia.get(indice).getFec_ingreso());
							bitacora.setCumplimiento("C");
							bitacora.setArea(listaRegistroPermanencia.get(0).getArea());
							bitacora.setRack(listaRegistroPermanencia.get(0).getRack());
							bitacora.setDescripcion("SE ELIMINA EL REGISTRO DE "
									+ listaRegistroPermanencia.get(0).getNom_proveedor() + ".");
							bitacora.setId_turno(id_turno);
							bitacora.setId_localidad(id_dc);
							bitacora.setEst_bitacora("AE");
							bitacora.setUsu_ingresa(user);
							bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							if (listaParametros1.size() > 0) {
								secuencia1 = listaParametros1.get(0).getSecuencia_bitacora();
							}
							try {
								dao.eliminarRegistroPermanencia(registro_permanencia.getId_registro_permanencia(),
										bitacora, secuencia1);
								setearFechaIngresaFormulario();
								consultarRegistroPermanencia();
								Messagebox.show(informativos.getMensaje_informativo_59(),
										informativos.getMensaje_informativo_27(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(),
										informativos.getMensaje_informativo_27() + e.getMessage(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});
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

	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_clasificacion = 0;
		id_tipo_clasificacion = consultasABaseDeDatos.obtenerIdTipoClasificacionAPartirDeTicket(ticket, id_tipo_tarea,
				id_dc);
		return id_tipo_clasificacion;
	}

}
