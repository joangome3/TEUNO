package bp.aplicaciones.controlador.mantenimientos.respaldo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_respaldo;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_respaldo;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtDiaRespaldo, txtComentario;
	@Wire
	Combobox cmbTipoRespaldo;
	@Wire
	Checkbox chkRespaldo;

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	long id = 0;
	long id_mantenimiento = 12;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_respaldo respaldo = (modelo_respaldo) Sessions.getCurrent().getAttribute("respaldo");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	List<modelo_tipo_respaldo> listaTipoRespaldo = new ArrayList<modelo_tipo_respaldo>();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtDiaRespaldo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDiaRespaldo.setText(validar.soloLetrasyNumeros(txtDiaRespaldo.getText()));
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarTipoRespaldos();
		cargarDatos();
		validarCamposActualizar();
	}

	public List<modelo_tipo_respaldo> obtenerTipoRespaldos() {
		return listaTipoRespaldo;
	}

	public void cargarTipoRespaldos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_respaldo dao = new dao_tipo_respaldo();
		String criterio = "";
		try {
			listaTipoRespaldo = dao.obtenerTipoRespaldos(criterio);
			binder.loadComponent(cmbTipoRespaldo);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipos de respaldos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(respaldo.getId_respaldo()));
		if (respaldo.getEs_fec_respaldo().equals("S")) {
			chkRespaldo.setChecked(true);
		} else {
			chkRespaldo.setChecked(false);
		}
		for (int i = 0; i < listaTipoRespaldo.size(); i++) {
			if (listaTipoRespaldo.get(i).getId_tipo_respaldo() == respaldo.getId_tip_respaldo()) {
				cmbTipoRespaldo.setText(listaTipoRespaldo.get(i).getNom_tipo_respaldo());
				i = listaTipoRespaldo.size() + 1;
			}
		}
		txtDiaRespaldo.setText(respaldo.getDia_respaldo());

	}

	public void validarCamposActualizar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, respaldo.getId_respaldo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					cmbTipoRespaldo.setDisabled(false);
					txtDiaRespaldo.setDisabled(false);
					chkRespaldo.setDisabled(false);
				} else {
					if (solicitud.getId_campo() == 33) {
						cmbTipoRespaldo.setDisabled(false);
					} else {
						cmbTipoRespaldo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 34) {
						txtDiaRespaldo.setDisabled(false);
					} else {
						txtDiaRespaldo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 38) {
						chkRespaldo.setDisabled(false);
					} else {
						chkRespaldo.setDisabled(true);
					}
				}
			}
		}
	}

	@Listen("onSelect=#cmbTipoRespaldo")
	public void onSelect$cmbTipoRespaldo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtDiaRespaldo.getText().length() <= 0) {
			return;
		}
		if (cmbTipoRespaldo.getSelectedItem() == null) {
			return;
		}
		dao_respaldo dao = new dao_respaldo();
		if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 4,
				String.valueOf(txtId.getText()), txtDiaRespaldo.getText(), 0).size() > 0) {
			txtDiaRespaldo.setErrorMessage(
					"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
			return;
		}
	}

	@Listen("onBlur=#txtDiaRespaldo")
	public void onBlur$txtDiaRespaldo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtDiaRespaldo.getText().length() <= 0) {
			return;
		}
		if (cmbTipoRespaldo.getSelectedItem() == null) {
			return;
		}
		dao_respaldo dao = new dao_respaldo();
		if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 4,
				String.valueOf(txtId.getText()), txtDiaRespaldo.getText(), 0).size() > 0) {
			txtDiaRespaldo.setErrorMessage(
					"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
			return;
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, respaldo.getId_respaldo(), 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public boolean validarSiExisteSolicitudPendienteActualizacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, respaldo.getId_respaldo(), 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public void setearDatosAntesDeGuardar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, respaldo.getId_respaldo(), 7);
		if (solicitud != null) {
			comentario_1 = solicitud.getComentario_1();
			comentario_2 = solicitud.getComentario_4();
			fecha_comentario_1 = solicitud.getFecha_1();
			fecha_comentario_2 = solicitud.getFecha_2();
			fecha_comentario_3 = solicitud.getFecha_4();
		}
	}

	public String setearComentario() {
		String comentario = "";
		comentario = "EN LA FECHA " + fechas.obtenerFechaFormateada(fecha_comentario_1, "dd/MM/yyyy HH:mm") + " "
				+ comentario_1 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_2, "dd/MM/yyyy HH:mm") + " EL APROBADOR INDICA QUE "
				+ comentario_2 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_3, "dd/MM/yyyy HH:mm")
				+ " SE REALIZA EL CAMBIO SOLICITADO Y SE REGISTRA COMO COMENTARIO ";
		return comentario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (validarSiExisteSolicitudPendienteEjecucion() == false
				&& validarSiExisteSolicitudPendienteActualizacion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					if (cmbTipoRespaldo.getSelectedItem() == null) {
						cmbTipoRespaldo.setErrorMessage("Seleccione el tipo de respaldo.");
						return;
					}
					if (txtDiaRespaldo.getText().length() <= 0) {
						txtDiaRespaldo.setErrorMessage("Ingrese el día.");
						return;
					}
					dao_respaldo dao = new dao_respaldo();
					if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 4,
							String.valueOf(txtId.getText()), txtDiaRespaldo.getText(), 0).size() > 0) {
						txtDiaRespaldo.setErrorMessage(
								"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 33) {
						if (cmbTipoRespaldo.getSelectedItem() == null) {
							cmbTipoRespaldo.setErrorMessage("Seleccione el tipo de respaldo.");
							return;
						}
						if (txtDiaRespaldo.getText().length() <= 0) {
							txtDiaRespaldo.setErrorMessage("Ingrese la descripción.");
							return;
						}
						dao_respaldo dao = new dao_respaldo();
						if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 4,
								String.valueOf(txtId.getText()), txtDiaRespaldo.getText(), 0).size() > 0) {
							txtDiaRespaldo.setErrorMessage(
									"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
							return;
						}
					}
					if (solicitud.getId_campo() == 34) {
						if (txtDiaRespaldo.getText().length() <= 0) {
							txtDiaRespaldo.setErrorMessage("Ingrese la descripción.");
							return;
						}
					}
				}
			}
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		setearDatosAntesDeGuardar();
		Messagebox.show("Esta seguro de modificar el respaldo?", ".:: Modificar respaldo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_respaldo dao = new dao_respaldo();
							modelo_respaldo respaldo = new modelo_respaldo();
							respaldo.setId_respaldo(Long.parseLong(txtId.getText()));
							respaldo.setId_tip_respaldo(
									Long.parseLong(cmbTipoRespaldo.getSelectedItem().getValue().toString()));
							respaldo.setDia_respaldo(txtDiaRespaldo.getText());
							if (chkRespaldo.isChecked()) {
								respaldo.setEs_fec_respaldo("S");
							} else {
								respaldo.setEs_fec_respaldo("N");
							}
							respaldo.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							respaldo.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								respaldo.setEst_respaldo("PACP");
								solicitud.setEst_solicitud("R");
								solicitud.setComentario_1(setearComentario() + txtComentario.getText().toUpperCase());
								solicitud.setComentario_2("");
								solicitud.setComentario_3("");
								solicitud.setComentario_4("");
								solicitud.setComentario_5("");
								solicitud.setId_user_1(id_user);
								solicitud.setId_user_2(0);
								solicitud.setId_user_3(0);
								solicitud.setId_user_4(0);
								solicitud.setId_user_5(0);
								solicitud.setFecha_1(fechas.obtenerTimestampDeDate(fecha_comentario_1));
								solicitud.setFecha_2(null);
								solicitud.setFecha_3(null);
								solicitud.setFecha_4(null);
								solicitud.setFecha_5(null);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							} else {
								respaldo.setEst_respaldo("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarRespaldo(respaldo, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("El respaldo se modificó correctamente.",
											".:: Modificar respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("respaldo");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar el respaldo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("respaldo");
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		cmbTipoRespaldo.setText("");
		txtDiaRespaldo.setText("");
	}

}
