package bp.aplicaciones.controlador.personal;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.personal.DAO.dao_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_registro_permanencia;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;

@SuppressWarnings({ "serial", "deprecation" })
public class registrar_salida extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar;
	@Wire
	Textbox txtBuscar, txtTarjetaBP, txtTarjetaDN;
	@Wire
	Combobox cmbEstado;
	@Wire
	Datebox dtxFechaIngresoDC, dtxFechaSalidaDC, dtxFechaIngresoSU, dtxFechaSalidaSU;
	@Wire
	Listbox lbxProveedores;
	@Wire
	Checkbox chkTarjetaBP, chkTarjetaDN;

	List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_9> listaParametros9 = new ArrayList<modelo_parametros_generales_9>();
	List<modelo_parametros_generales_10> listaParametros10 = new ArrayList<modelo_parametros_generales_10>();
	List<modelo_parametros_generales_11> listaParametros11 = new ArrayList<modelo_parametros_generales_11>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_turno> listaTurnos1 = new ArrayList<modelo_turno>();
	List<modelo_turno> listaTurnos2 = new ArrayList<modelo_turno>();

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
		obtenerId();
		lbxProveedores.setEmptyMessage(informativos.getMensaje_informativo_2());
		inicializarListas();
		setearFechaActual();
		setearFechaIngresaFormulario();
		setearFechaHoraSalidaDC();
		validarTurno();
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase().trim());
			}
		});
		if (cmbEstado.getItems().size() > 0) {
			cmbEstado.setSelectedIndex(0);
		}
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

	public List<modelo_registro_permanencia> obtenerRegistros() {
		return listaRegistroPermanencia;
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		try {
			id = dao.obtenerNuevoIdRegistroPermanencia();
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaTurnos1 = consultasABaseDeDatos.cargarTurnos("");
		listaTurnos2 = consultasABaseDeDatos.cargarTurnos("A");
		binder.loadComponent(cmbEstado);
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaHoraIngresoDC() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		dtxFechaIngresoDC.setValue(d);
	}

	public void setearFechaHoraSalidaDC() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		dtxFechaSalidaDC.setValue(d);
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
					id_turno = listaTurnos2.get(i).getId_turno();
					i = listaTurnos2.size() + 1;
				}
			}
		}
	}

	public void buscarRegistroPermanencia(String criterio, int tipo, long id_cliente, String fecha_solicitud_i,
			String fecha_solicitud_f, String fecha_inicio, String fecha_fin, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		if (txtBuscar.getText().length() <= 0) {
			listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia3(criterio, tipo, "", id_dc, 0,
					"", "");
		}
		if (!txtBuscar.getValue().equals("")) {
			listaRegistroPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia3(criterio, tipo, "", id_dc, 0,
					"", "");
		}
		chkTarjetaBP.setDisabled(true);
		chkTarjetaBP.setChecked(true);
		txtTarjetaBP.setDisabled(true);
		txtTarjetaBP.setText("");
		chkTarjetaDN.setDisabled(true);
		chkTarjetaDN.setChecked(true);
		txtTarjetaDN.setDisabled(true);
		txtTarjetaDN.setText("");
		lbxProveedores.setMultiple(true);
		lbxProveedores.setCheckmark(true);
		binder.loadComponent(lbxProveedores);
	}

	public void consultarRegistroPermanencia() throws ClassNotFoundException, FileNotFoundException, IOException {
		int tipo = 5;
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		if (criterio.length() <= 0) {
			listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
			binder.loadComponent(lbxProveedores);
			return;
		}
		buscarRegistroPermanencia(criterio, tipo, 0, fecha_inicio, fecha_fin, "", "", id_dc);
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

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarRegistroPermanencia();
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbEstado.getSelectedItem() == null) {
			return;
		}
		consultarRegistroPermanencia();
	}

	@Listen("onSelect=#lbxProveedores")
	public void onSelect$lbxProveedores()
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxProveedores.getSelectedItem() == null) {
			return;
		}
		if (lbxProveedores.getSelectedItems().size() > 1) {
			dtxFechaIngresoDC.setValue(null);
			dtxFechaIngresoSU.setValue(null);
			dtxFechaIngresoDC.setDisabled(true);
			dtxFechaIngresoSU.setDisabled(true);
			chkTarjetaBP.setDisabled(true);
			chkTarjetaBP.setChecked(true);
			txtTarjetaBP.setDisabled(true);
			txtTarjetaBP.setText("");
			chkTarjetaDN.setDisabled(true);
			chkTarjetaDN.setChecked(true);
			txtTarjetaDN.setDisabled(true);
			txtTarjetaDN.setText("");
		} else {
			int indice = lbxProveedores.getSelectedIndex();
			dtxFechaIngresoDC.setDisabled(false);
			dtxFechaIngresoDC.setValue(listaRegistroPermanencia.get(indice).getFec_ingreso());
			dtxFechaIngresoSU.setDisabled(false);
			dtxFechaIngresoSU.setValue(listaRegistroPermanencia.get(indice).getFec_ingreso_su());
//			if (validarSiEsClienteDiners() == true) {
			if (listaRegistroPermanencia.get(indice).getNum_tarjeta_dn() != null) {
				if (listaRegistroPermanencia.get(indice).getNum_tarjeta_dn().length() > 0) {
					chkTarjetaDN.setDisabled(false);
					chkTarjetaDN.setChecked(false);
					txtTarjetaDN.setText(listaRegistroPermanencia.get(indice).getNum_tarjeta_dn());
					txtTarjetaDN.setDisabled(false);
				} else {
					chkTarjetaDN.setDisabled(false);
					chkTarjetaDN.setChecked(true);
					txtTarjetaDN.setDisabled(true);
					txtTarjetaDN.setText("");
				}
			} else {
				chkTarjetaDN.setDisabled(false);
				chkTarjetaDN.setChecked(true);
				txtTarjetaDN.setDisabled(true);
				txtTarjetaDN.setText("");
			}
//			} else {
			if (listaRegistroPermanencia.get(indice).getNum_tarjeta_bp() != null) {
				if (listaRegistroPermanencia.get(indice).getNum_tarjeta_bp().length() > 0) {
					chkTarjetaBP.setDisabled(false);
					chkTarjetaBP.setChecked(false);
					txtTarjetaBP.setText(listaRegistroPermanencia.get(indice).getNum_tarjeta_bp());
					txtTarjetaBP.setDisabled(false);
				} else {
					chkTarjetaBP.setDisabled(false);
					chkTarjetaBP.setChecked(true);
					txtTarjetaBP.setDisabled(true);
					txtTarjetaBP.setText("");
				}
			} else {
				chkTarjetaBP.setDisabled(false);
				chkTarjetaBP.setChecked(true);
				txtTarjetaBP.setDisabled(true);
				txtTarjetaBP.setText("");
			}
//			}
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

	@Listen("onClick=#chkTarjetaBP")
	public void onCheck$chkTarjetaBP() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (!chkTarjetaBP.isChecked() || !chkTarjetaDN.isChecked()) {
			if (!chkTarjetaBP.isChecked()) {
				if (lbxProveedores.getSelectedItems().size() == 1) {
					int indice = lbxProveedores.getSelectedIndex();
					if (listaRegistroPermanencia.get(indice).getNum_tarjeta_bp() != null) {
						txtTarjetaBP.setText(listaRegistroPermanencia.get(indice).getNum_tarjeta_bp());
					} else {
						txtTarjetaBP.setText("");
					}
				} else {
					txtTarjetaBP.setText("");
				}
				txtTarjetaBP.setDisabled(false);
				lbxProveedores.setMultiple(false);
				lbxProveedores.setCheckmark(false);
				binder.loadComponent(lbxProveedores);
//				chkTarjetaDN.setChecked(true);
//				txtTarjetaDN.setText("");
//				txtTarjetaDN.setDisabled(true);
			} else {
				txtTarjetaBP.setText("");
				txtTarjetaBP.setDisabled(true);
			}
		} else {
			txtTarjetaBP.setText("");
			txtTarjetaBP.setDisabled(true);
			txtTarjetaDN.setText("");
			txtTarjetaDN.setDisabled(true);
			lbxProveedores.setMultiple(true);
			lbxProveedores.setCheckmark(true);
			binder.loadComponent(lbxProveedores);
		}
	}

	@Listen("onClick=#chkTarjetaDN")
	public void onCheck$chkTarjetaDN() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (!chkTarjetaDN.isChecked() || !chkTarjetaBP.isChecked()) {
			if (!chkTarjetaDN.isChecked()) {
				if (lbxProveedores.getSelectedItems().size() == 1) {
					int indice = lbxProveedores.getSelectedIndex();
					if (listaRegistroPermanencia.get(indice).getNum_tarjeta_dn() != null) {
						txtTarjetaDN.setText(listaRegistroPermanencia.get(indice).getNum_tarjeta_dn());
					} else {
						txtTarjetaDN.setText("");
					}
				} else {
					txtTarjetaDN.setText("");
				}
				txtTarjetaDN.setDisabled(false);
				lbxProveedores.setMultiple(false);
				lbxProveedores.setCheckmark(false);
				binder.loadComponent(lbxProveedores);
//				chkTarjetaBP.setChecked(true);
//				txtTarjetaBP.setText("");
//				txtTarjetaBP.setDisabled(true);
			} else {
				txtTarjetaDN.setText("");
				txtTarjetaDN.setDisabled(true);
			}
		} else {
			txtTarjetaDN.setText("");
			txtTarjetaDN.setDisabled(true);
			txtTarjetaBP.setText("");
			txtTarjetaBP.setDisabled(true);
			lbxProveedores.setMultiple(true);
			lbxProveedores.setCheckmark(true);
			binder.loadComponent(lbxProveedores);
		}
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
		if (lbxProveedores.getSelectedItem() == null) {
			txtBuscar.setErrorMessage(validaciones.getMensaje_validacion_33());
			return;
		}
		if (!chkTarjetaBP.isChecked()) {
			if (lbxProveedores.getSelectedItems().size() > 1) {
				txtTarjetaBP.setErrorMessage(informativos.getMensaje_informativo_7());
				return;
			}
		}
		if (!chkTarjetaBP.isChecked()) {
//			if (validarSiEsClienteDiners() == false) {
			if (txtTarjetaBP.getText().length() <= 0) {
				txtTarjetaBP.setErrorMessage(validaciones.getMensaje_validacion_36());
				return;
			}
//			} else {
//				txtTarjetaBP.setErrorMessage(validaciones.getMensaje_validacion_37());
//				return;
//			}
		}
		if (!chkTarjetaDN.isChecked()) {
			if (lbxProveedores.getSelectedItems().size() > 1) {
				txtTarjetaDN.setErrorMessage(informativos.getMensaje_informativo_7());
				return;
			}
		}
		if (!chkTarjetaDN.isChecked()) {
//			if (validarSiEsClienteDiners() == true) {
			if (txtTarjetaDN.getText().length() <= 0) {
				txtTarjetaDN.setErrorMessage(validaciones.getMensaje_validacion_36());
				return;
			}
//			} else {
//				txtTarjetaDN.setErrorMessage(validaciones.getMensaje_validacion_37());
//				return;
//			}
		}
		if (lbxProveedores.getSelectedItems().size() == 1) {
			if (dtxFechaIngresoDC.getValue() == null) {
				dtxFechaIngresoDC.setFocus(true);
				dtxFechaIngresoDC.setErrorMessage(validaciones.getMensaje_validacion_4());
				return;
			}
			if (dtxFechaSalidaDC.getValue() == null) {
				dtxFechaSalidaDC.setFocus(true);
				dtxFechaSalidaDC.setErrorMessage(validaciones.getMensaje_validacion_4());
				return;
			}
			Date d1 = fechas.obtenerFechaArmada(dtxFechaIngresoDC.getValue(), dtxFechaIngresoDC.getValue().getMonth(),
					dtxFechaIngresoDC.getValue().getDate(), dtxFechaIngresoDC.getValue().getHours(),
					dtxFechaIngresoDC.getValue().getMinutes(), 0);
			Date d2 = fechas.obtenerFechaArmada(dtxFechaSalidaDC.getValue(), dtxFechaSalidaDC.getValue().getMonth(),
					dtxFechaSalidaDC.getValue().getDate(), dtxFechaSalidaDC.getValue().getHours(),
					dtxFechaSalidaDC.getValue().getMinutes(), 0);
			if (d2.before(d1)) {
				dtxFechaSalidaDC.setFocus(true);
				dtxFechaSalidaDC.setErrorMessage(validaciones.getMensaje_validacion_10());
				return;
			}
			if (dtxFechaIngresoSU.getValue() != null) {
				if (dtxFechaSalidaSU.getValue() == null) {
					dtxFechaSalidaSU.setFocus(true);
					dtxFechaSalidaSU.setErrorMessage(validaciones.getMensaje_validacion_4());
					return;
				} else {
					d1 = fechas.obtenerFechaArmada(dtxFechaIngresoSU.getValue(),
							dtxFechaIngresoSU.getValue().getMonth(), dtxFechaIngresoSU.getValue().getDate(),
							dtxFechaIngresoSU.getValue().getHours(), dtxFechaIngresoSU.getValue().getMinutes(), 0);
					d2 = fechas.obtenerFechaArmada(dtxFechaSalidaSU.getValue(), dtxFechaSalidaSU.getValue().getMonth(),
							dtxFechaSalidaSU.getValue().getDate(), dtxFechaSalidaSU.getValue().getHours(),
							dtxFechaSalidaSU.getValue().getMinutes(), 0);
					if (d2.before(d1)) {
						dtxFechaSalidaSU.setFocus(true);
						dtxFechaSalidaSU.setErrorMessage(validaciones.getMensaje_validacion_10());
						return;
					}
				}
			} else {
				if (dtxFechaSalidaSU.getValue() != null) {
					dtxFechaIngresoSU.setFocus(true);
					dtxFechaIngresoSU.setErrorMessage(validaciones.getMensaje_validacion_4());
					return;
				}
			}
		} else {
			if (dtxFechaSalidaDC.getValue() == null) {
				dtxFechaSalidaDC.setFocus(true);
				dtxFechaSalidaDC.setErrorMessage(validaciones.getMensaje_validacion_4());
				return;
			}
		}
		Messagebox.show(mensaje, informativos.getMensaje_informativo_17(), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							long secuencia1 = 0;
							dao_solicitud_personal dao = new dao_solicitud_personal();
							List<modelo_registro_permanencia> listaPermanencia = new ArrayList<modelo_registro_permanencia>();
							List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
							modelo_registro_permanencia registro_permanencia = null;
							Iterator<Listitem> it = lbxProveedores.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = it.next();
								int indice = item.getIndex();
								registro_permanencia = new modelo_registro_permanencia();
								if (validarSiExistePrimeroApertura(listaRegistroPermanencia.get(indice).getTicket(),
										1) == false) {
									Messagebox.show(
											informativos.getMensaje_informativo_96().replace("?1",
													listaRegistroPermanencia.get(indice).getTicket()),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
									return;
								}
								registro_permanencia.setId_registro_permanencia(
										listaRegistroPermanencia.get(indice).getId_registro_permanencia());
								registro_permanencia
										.setId_solicitud(listaRegistroPermanencia.get(indice).getId_solicitud());
								if (lbxProveedores.getSelectedItems().size() > 1) {
									registro_permanencia
											.setFec_ingreso(listaRegistroPermanencia.get(indice).getFec_ingreso());
								} else {
									registro_permanencia.setFec_ingreso(
											fechas.obtenerTimestampDeDate(dtxFechaIngresoDC.getValue()));
								}
								if (dtxFechaSalidaDC.getValue() != null) {
									registro_permanencia
											.setFec_salida(fechas.obtenerTimestampDeDate(dtxFechaSalidaDC.getValue()));
								} else {
									registro_permanencia.setFec_salida(null);
								}
								registro_permanencia
										.setId_proveedor(listaRegistroPermanencia.get(indice).getId_proveedor());
								if (lbxProveedores.getSelectedItems().size() > 1) {
									registro_permanencia.setNum_tarjeta_bp(
											listaRegistroPermanencia.get(indice).getNum_tarjeta_bp());
									registro_permanencia.setNum_tarjeta_dn(
											listaRegistroPermanencia.get(indice).getNum_tarjeta_dn());
								} else {
									if (txtTarjetaBP.getText().length() <= 0) {
										registro_permanencia.setNum_tarjeta_bp(null);
									} else {
										registro_permanencia
												.setNum_tarjeta_bp(txtTarjetaBP.getText().toString().trim());
									}
									if (txtTarjetaDN.getText().length() <= 0) {
										registro_permanencia.setNum_tarjeta_dn(null);
									} else {
										registro_permanencia
												.setNum_tarjeta_dn(txtTarjetaDN.getText().toString().trim());
									}
								}
								if (lbxProveedores.getSelectedItems().size() > 1) {
									Date d1 = null, d2 = null;
									d1 = fechas.obtenerDateDeUnString(fechas.obtenerFechaFormateada(
											listaRegistroPermanencia.get(indice).getFec_ingreso(), "dd/MM/yyyy HH:mm"),
											"dd/MM/yyyy HH:mm");
									d2 = fechas.obtenerFechaArmada(dtxFechaSalidaDC.getValue(),
											dtxFechaSalidaDC.getValue().getMonth(),
											dtxFechaSalidaDC.getValue().getDate(),
											dtxFechaSalidaDC.getValue().getHours(),
											dtxFechaSalidaDC.getValue().getMinutes(), 0);
									if (d2.before(d1)) {
										Messagebox.show(
												informativos.getMensaje_informativo_99()
														.replace("?1",
																listaRegistroPermanencia.get(indice).getNom_proveedor())
														.replace("?2",
																listaRegistroPermanencia.get(indice).getTicket()),
												informativos.getMensaje_informativo_24(), Messagebox.OK,
												Messagebox.EXCLAMATION);
										return;
									}
									if (dtxFechaSalidaDC.getValue() != null) {
										registro_permanencia.setFec_salida(
												fechas.obtenerTimestampDeDate(dtxFechaSalidaDC.getValue()));
									} else {
										registro_permanencia.setFec_salida(null);
									}
									if (listaRegistroPermanencia.get(indice).getFec_ingreso_su() != null) {
										if (dtxFechaSalidaSU.getValue() == null) {
											Messagebox.show(
													informativos.getMensaje_informativo_98()
															.replace("?1",
																	listaRegistroPermanencia.get(indice)
																			.getNom_proveedor())
															.replace("?2",
																	listaRegistroPermanencia.get(indice).getTicket()),
													informativos.getMensaje_informativo_24(), Messagebox.OK,
													Messagebox.EXCLAMATION);
											return;
										}
										d1 = fechas
												.obtenerDateDeUnString(
														fechas.obtenerFechaFormateada(listaRegistroPermanencia
																.get(indice).getFec_ingreso_su(), "dd/MM/yyyy HH:mm"),
														"dd/MM/yyyy HH:mm");
										d2 = fechas.obtenerFechaArmada(dtxFechaSalidaSU.getValue(),
												dtxFechaSalidaSU.getValue().getMonth(),
												dtxFechaSalidaSU.getValue().getDate(),
												dtxFechaSalidaSU.getValue().getHours(),
												dtxFechaSalidaSU.getValue().getMinutes(), 0);
										if (d2.before(d1)) {
											Messagebox.show(
													informativos.getMensaje_informativo_100()
															.replace("?1",
																	listaRegistroPermanencia.get(indice)
																			.getNom_proveedor())
															.replace("?2",
																	listaRegistroPermanencia.get(indice).getTicket()),
													informativos.getMensaje_informativo_24(), Messagebox.OK,
													Messagebox.EXCLAMATION);
											return;
										}
										registro_permanencia.setFec_ingreso_su(
												listaRegistroPermanencia.get(indice).getFec_ingreso_su());
										if (dtxFechaSalidaSU.getValue() != null) {
											registro_permanencia.setFec_salida_su(
													fechas.obtenerTimestampDeDate(dtxFechaSalidaSU.getValue()));
										} else {
											registro_permanencia.setFec_salida_su(null);
										}
									}
								} else {
									if (dtxFechaIngresoSU.getValue() != null) {
										registro_permanencia.setFec_ingreso_su(
												fechas.obtenerTimestampDeDate(dtxFechaIngresoSU.getValue()));
									} else {
										registro_permanencia.setFec_ingreso_su(null);
									}
									if (dtxFechaSalidaSU.getValue() != null) {
										registro_permanencia.setFec_salida_su(
												fechas.obtenerTimestampDeDate(dtxFechaSalidaSU.getValue()));
									} else {
										registro_permanencia.setFec_salida_su(null);
									}
								}
								registro_permanencia.setId_localidad(id_dc);
								registro_permanencia.setEst_registro_permanencia("I");
								registro_permanencia.setUsu_modifica(user);
								registro_permanencia.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
								/* Se inicializa el objeto bitacora */
								modelo_bitacora bitacora = new modelo_bitacora();
								bitacora.setTicket_externo(listaRegistroPermanencia.get(indice).getTicket());
								bitacora.setId_cliente(listaRegistroPermanencia.get(indice).getId_emp_solicitante());
								bitacora.setId_solicitante(listaRegistroPermanencia.get(indice).getId_solicitante());
								bitacora.setId_tipo_servicio(2);
								bitacora.setId_tipo_clasificacion(obtenerIdTipoClasificacionAPartirDeTicket(
										listaRegistroPermanencia.get(indice).getTicket().toString(), 1, id_dc));
								bitacora.setId_tipo_tarea(7);
								bitacora.setId_estado_bitacora(2);
								bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaSalidaDC.getValue()));
								bitacora.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaSalidaDC.getValue()));
								bitacora.setCumplimiento("C");
								bitacora.setArea(listaRegistroPermanencia.get(indice).getArea());
								bitacora.setRack(listaRegistroPermanencia.get(indice).getRack());
								bitacora.setDescripcion("SE REGISTRA LA SALIDA DE "
										+ listaRegistroPermanencia.get(indice).getNom_proveedor() + ".");
								bitacora.setId_turno(id_turno);
								bitacora.setId_localidad(id_dc);
								bitacora.setEst_bitacora("AE");
								bitacora.setUsu_ingresa(user);
								bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								/* Se anaden a la lista los objetos */
								listaPermanencia.add(registro_permanencia);
								listaBitacora.add(bitacora);
							}
							if (listaParametros1.size() > 0) {
								secuencia1 = listaParametros1.get(0).getSecuencia_bitacora();
							}
							try {
								dao.modificarRegistroPermanencia(listaPermanencia, listaBitacora, secuencia1);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_17(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								Events.postEvent(new Event("onClose", zModificar));
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
		if (cmbEstado.getItems().size() > 0) {
			cmbEstado.setSelectedIndex(0);
		}
		txtBuscar.setText("");
		listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
		binder.loadComponent(lbxProveedores);
		setearFechaHoraIngresoDC();
		chkTarjetaBP.setChecked(true);
		txtTarjetaBP.setDisabled(true);
		txtTarjetaBP.setText("");
		chkTarjetaDN.setChecked(true);
		txtTarjetaDN.setDisabled(true);
		txtTarjetaDN.setText("");
		dtxFechaIngresoSU.setValue(null);
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

	public boolean validarSiEsClienteDiners() {
		boolean es_cliente_diners = false;
		if (lbxProveedores.getSelectedItem() != null) {
			int indice = lbxProveedores.getSelectedIndex();
			if (listaRegistroPermanencia.get(indice).getId_cliente() == 9) {
				es_cliente_diners = true;
			}
		}
		return es_cliente_diners;
	}

	public boolean validarSiProveedorYaEstaEnPermanencia(String id_proveedor, String id_solicitud)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean esta_en_permanencia = false;
		List<modelo_registro_permanencia> listaPermanencia = new ArrayList<modelo_registro_permanencia>();
		listaPermanencia = consultasABaseDeDatos.cargarRegistrosPermanencia3(id_proveedor, 6, id_solicitud, id_dc, 0,
				"", "");
		if (listaPermanencia.size() > 0) {
			esta_en_permanencia = true;
		} else {
			esta_en_permanencia = false;
		}
		return esta_en_permanencia;
	}

	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_clasificacion = 0;
		id_tipo_clasificacion = consultasABaseDeDatos.obtenerIdTipoClasificacionAPartirDeTicket(ticket, id_tipo_tarea,
				id_dc);
		return id_tipo_clasificacion;
	}

}
