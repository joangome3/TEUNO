package bp.aplicaciones.controlador.sibod.movimiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.sibod.DAO.dao_movimiento;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar_ticket extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtTicketAnterior, txtTicketActual, txtComentario;
	@Wire
	Label lblInformacion1, lblInformacion2;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_movimiento movimiento = (modelo_movimiento) Sessions.getCurrent().getAttribute("movimiento");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
	List<modelo_movimiento> listaMovimiento = new ArrayList<modelo_movimiento>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();

	long id_turno = 0;

	boolean es_turno_extendido = false;
	boolean se_inicia_turno = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	String turno = "";

	int total_registros_bodega_anterior = 0, total_registros_bitacora_anterior = 0, total_registros_bodega_actual = 0,
			total_registros_bitacora_actual = 0;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtTicketActual.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicketActual.setText(txtTicketActual.getText().trim().toUpperCase());
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		setearFechaActual();
		inicializarListas();
		inicializarDatosEnPantalla();
		validarTurno();
	}

	public List<modelo_bitacora> obtenerBitacoras() {
		return listaBitacora;
	}

	public List<modelo_movimiento> obtenerMovimientos() {
		return listaMovimiento;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
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
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
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
				}
				// Si es despues de las 00:00
				if (d4.before(d1) && d1.before(d5)) {
				}
				if ((d2.before(d1) && d1.before(d3)) || (d4.before(d1) && d1.before(d5))) {
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			}
		}
	}

	public void inicializarDatosEnPantalla() throws WrongValueException, NumberFormatException, ClassNotFoundException,
			FileNotFoundException, IOException, SQLException {
		setearTicketAnterior();
		total_registros_bitacora_anterior = cantidadRegistrosEnListaBitacora(txtTicketAnterior.getText());
		total_registros_bodega_anterior = cantidadRegistrosEnListaMovimiento(txtTicketAnterior.getText());
		setearInformacion1();
	}

	public void setearTicketAnterior() {
		txtTicketAnterior.setText(movimiento.getTck_movimiento());
	}

	public int cantidadRegistrosEnListaBitacora(String ticketMovimiento)
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		int total_registros = 0;
		String fecha_inicio = "", fecha_fin = "";
		String criterio = ticketMovimiento;
		String turno = "";
		long id_tipo_tarea = 7;
		long id_tipo_servicio = 6;
		String use_usuario = "";
		listaBitacora = consultasABaseDeDatos.cargarBitacoras(criterio, 6, id_tipo_tarea, "", turno, id_dc,
				fecha_inicio, fecha_fin, id_tipo_servicio, use_usuario, Integer.valueOf("0"));
		total_registros = listaBitacora.size();
		return total_registros;
	}

	public int cantidadRegistrosEnListaMovimiento(String ticketMovimiento)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		int total_registros = 0;
		String criterio = ticketMovimiento;
		String f_i = "";
		String f_f = "";
		dao_movimiento dao = new dao_movimiento();
		listaMovimiento = dao.obtenerMovimientos(criterio, f_i, f_f, String.valueOf(id_dc), 2);
		total_registros = listaMovimiento.size();
		return total_registros;
	}

	public void setearInformacion1() {
		if (total_registros_bitacora_anterior > 0 && total_registros_bodega_anterior > 0) {
			lblInformacion1.setValue(informativos.getMensaje_informativo_93()
					.replace("?1", String.valueOf(total_registros_bodega_anterior))
					.replace("?2", String.valueOf(total_registros_bitacora_anterior)));
		} else {
			lblInformacion1.setValue("");
		}
	}

	public void setearInformacion2() throws WrongValueException, NumberFormatException, ClassNotFoundException,
			FileNotFoundException, IOException, SQLException {
		if (txtTicketActual.getText().trim().length() <= 0) {
			lblInformacion2.setValue("");
			return;
		}
		if (txtTicketAnterior.getText().equals(txtTicketActual.getText().trim())) {
			lblInformacion2.setValue("");
			return;
		}
		total_registros_bitacora_actual = cantidadRegistrosEnListaBitacora(txtTicketActual.getText().trim());
		total_registros_bodega_actual = cantidadRegistrosEnListaMovimiento(txtTicketActual.getText().trim());
		if (total_registros_bitacora_actual > 0 && total_registros_bodega_actual > 0) {
			lblInformacion2.setValue(informativos.getMensaje_informativo_95()
					.replace("?1", String.valueOf(total_registros_bodega_actual))
					.replace("?2", String.valueOf(total_registros_bitacora_actual))
					.replace("?3", txtTicketActual.getText().trim().toUpperCase()));
		} else {
			lblInformacion2.setValue("");
		}
	}

	@Listen("onBlur=#txtTicketActual")
	public void onBlur$txtTicketActual()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		setearInformacion2();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
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
		if (txtTicketActual.getText().trim().length() <= 0) {
			txtTicketActual.setErrorMessage("Ingrese el ticket.");
			return;
		}
		if (txtTicketAnterior.getText().equals(txtTicketActual.getText().trim())) {
			txtTicketActual.setErrorMessage("El ticket actual debe ser diferente del anterior.");
			return;
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_movimiento dao = new dao_movimiento();
							total_registros_bitacora_anterior = cantidadRegistrosEnListaBitacora(
									txtTicketAnterior.getText());
							total_registros_bodega_anterior = cantidadRegistrosEnListaMovimiento(
									txtTicketAnterior.getText());
							String comentario = "";
							for (int i = 0; i < listaMovimiento.size(); i++) {
								listaMovimiento.get(i).setTck_movimiento(txtTicketActual.getText().trim());
								comentario = listaMovimiento.get(i).getObs_movimiento();
								listaMovimiento.get(i).setObs_movimiento(comentario + " - " + txtComentario.getText());
								listaMovimiento.get(i).setUsu_modifica(user);
								listaMovimiento.get(i).setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							}
							for (int i = 0; i < listaBitacora.size(); i++) {
								listaBitacora.get(i).setTicket_externo(txtTicketActual.getText().trim());
								comentario = listaBitacora.get(i).getDescripcion();
								listaBitacora.get(i).setDescripcion(comentario + " - " + txtComentario.getText());
								listaBitacora.get(i).setUsu_modifica(user);
								listaBitacora.get(i).setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							}
							try {
								dao.modificarMovimiento(listaMovimiento, listaBitacora, 1, 2);
								Messagebox.show(informativos.getMensaje_informativo_20(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(),
										informativos.getMensaje_informativo_24() + e.getMessage(), Messagebox.OK,
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

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

}
