package bp.aplicaciones.controlador.mantenimientos.capacidad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
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
	Textbox txtId, txtNombre, txtDescripcion, txtComentario;

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	long id = 0;
	long id_mantenimiento = 13;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_capacidad capacidad = (modelo_capacidad) Sessions.getCurrent().getAttribute("capacidad");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

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
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarDatos();
		validarCamposActualizar();
		validarSesion();
	}

	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {
	}

	public void validarCamposActualizar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtNombre.setDisabled(true);
		txtDescripcion.setDisabled(true);
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, capacidad.getId_capacidad(),
				7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					txtNombre.setDisabled(false);
					txtDescripcion.setDisabled(false);
				} else {
					if (solicitud.getId_campo() == 1) {
						txtNombre.setDisabled(false);
					} else {
						txtNombre.setDisabled(true);
					}
					if (solicitud.getId_campo() == 2) {
						txtDescripcion.setDisabled(false);
					} else {
						txtDescripcion.setDisabled(true);
					}
				}
			}
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(capacidad.getId_capacidad()));
		txtNombre.setText(capacidad.getNom_capacidad());
		txtDescripcion.setText(capacidad.getDes_capacidad());
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_capacidad dao = new dao_capacidad();
		if (dao.obtenerCapacidades(txtNombre.getText(), 3, capacidad.getId_capacidad(), 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, capacidad.getId_capacidad(),
				7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, capacidad.getId_capacidad(),
				7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, capacidad.getId_capacidad(),
				7);
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
		validarSesion();
		if (validarSiExisteSolicitudPendienteEjecucion() == false
				&& validarSiExisteSolicitudPendienteActualizacion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					if (txtNombre.getText().length() <= 0) {
						txtNombre.setErrorMessage("Ingrese el nombre.");
						return;
					}
					dao_capacidad dao1 = new dao_capacidad();
					if (dao1.obtenerCapacidades(txtNombre.getText(), 3, capacidad.getId_capacidad(), 0).size() > 0) {
						txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
						return;
					}
					if (txtDescripcion.getText().length() <= 0) {
						txtDescripcion.setErrorMessage("Ingrese la descripcion.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 1) {
						if (txtNombre.getText().length() <= 0) {
							txtNombre.setErrorMessage("Ingrese el nombre.");
							return;
						}
						dao_capacidad dao1 = new dao_capacidad();
						if (dao1.obtenerCapacidades(txtNombre.getText(), 3, capacidad.getId_capacidad(), 0)
								.size() > 0) {
							txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
							return;
						}
					}
					if (solicitud.getId_campo() == 2) {
						if (txtDescripcion.getText().length() <= 0) {
							txtDescripcion.setErrorMessage("Ingrese la descripcion.");
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
		Messagebox.show("Esta seguro de guardar el registro?", ".:: Modificar capacidad ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_capacidad dao = new dao_capacidad();
							modelo_capacidad capacidad = new modelo_capacidad();
							capacidad.setId_capacidad(Long.parseLong(txtId.getText()));
							capacidad.setNom_capacidad(txtNombre.getText());
							capacidad.setDes_capacidad(txtDescripcion.getText());
							capacidad.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							capacidad.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								capacidad.setEst_capacidad("PACP");
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
								capacidad.setEst_capacidad("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarCapacidad(capacidad, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("La capacidad se modificó correctamente.",
											".:: Modificar capacidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar capacidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("capacidad");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el registro. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar capacidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("capacidad");
		validarSesion();
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		txtDescripcion.setText("");
	}

}
