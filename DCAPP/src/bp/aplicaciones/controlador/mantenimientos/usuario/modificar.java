package bp.aplicaciones.controlador.mantenimientos.usuario;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.SH2;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
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
	Textbox txtId, txtUsuario, txtContrasena1, txtContrasena2, txtNombres, txtApellidos, txtComentario;
	@Wire
	Combobox cmbLocalidad, cmbPerfil;
	@Wire
	Label lblSeguridad;
	@Wire
	Progressmeter pgsSeguridad;
	@Wire
	Checkbox chkCambiarPassword;

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id = 0;
	long id_mantenimiento = 4;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_usuario usuario = (modelo_usuario) Sessions.getCurrent().getAttribute("usuario");

	String tmpPassword = usuario.getPas_usuario();

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@SuppressWarnings("static-access")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombres.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombres.setText(txtNombres.getText().toUpperCase());
			}
		});
		txtApellidos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtApellidos.setText(txtApellidos.getText().toUpperCase());
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarPerfiles();
		cargarLocalidades();
		cargarDatos();
		validarCamposActualizar();
	}

	public void validarCamposActualizar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, usuario.getId_usuario(), 7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					txtUsuario.setReadonly(false);
					txtUsuario.setDisabled(false);
					txtContrasena1.setDisabled(false);
					txtContrasena2.setDisabled(false);
					txtNombres.setDisabled(false);
					txtApellidos.setDisabled(false);
					cmbPerfil.setDisabled(false);
					chkCambiarPassword.setDisabled(false);
					cmbLocalidad.setDisabled(false);
				} else {
					txtUsuario.setReadonly(true);
					cmbPerfil.setDisabled(true);
					cmbLocalidad.setDisabled(true);
					if (solicitud.getId_campo() == 5) {
						txtNombres.setDisabled(false);
					} else {
						txtNombres.setDisabled(true);
					}
					if (solicitud.getId_campo() == 6) {
						txtApellidos.setDisabled(false);
					} else {
						txtApellidos.setDisabled(true);
					}
					if (solicitud.getId_campo() == 15) {
						txtContrasena1.setDisabled(false);
						txtContrasena2.setDisabled(false);
					} else {
						txtContrasena1.setDisabled(true);
						txtContrasena2.setDisabled(true);
					}
					if (solicitud.getId_campo() == 17) {
						chkCambiarPassword.setDisabled(false);
					} else {
						chkCambiarPassword.setDisabled(true);
					}
				}
			}
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(usuario.getId_usuario()));
		txtUsuario.setText(usuario.getUse_usuario());
		txtContrasena1.setText(usuario.getPas_usuario());
		txtContrasena2.setText(usuario.getPas_usuario());
		txtNombres.setText(usuario.getNom_usuario());
		txtApellidos.setText(usuario.getApe_usuario());
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == usuario.getId_localidad()) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		for (int i = 0; i < listaPerfil.size(); i++) {
			if (listaPerfil.get(i).getId_perfil() == usuario.getId_perfil()) {
				cmbPerfil.setText(listaPerfil.get(i).getNom_perfil());
				i = listaPerfil.size() + 1;
			}
		}
		if (usuario.getCam_password().equals("S")) {
			chkCambiarPassword.setChecked(true);
		} else {
			chkCambiarPassword.setChecked(false);
		}
	}

	public List<modelo_perfil> obtenerPerfiles() {
		return listaPerfil;
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public void cargarPerfiles() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 6, 0);
			binder.loadComponent(cmbPerfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 4, 0, 0);
			binder.loadComponent(cmbLocalidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings("static-access")
	@Listen("onChange=#txtContrasena1")
	public void onChange$txtContrasena1() {
		if (txtContrasena1.getText().length() <= 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
			return;
		}
		if (txtContrasena2.getText().length() <= 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
			return;
		}
		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Claves no iguales");
			return;
		}
		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
		}
		if (validar.validarPassword(txtContrasena2.getText()) == 1) {
			pgsSeguridad.setValue(100);
			lblSeguridad.setValue("Clave segura");
		}
	}

	@SuppressWarnings("static-access")
	@Listen("onChange=#txtContrasena2")
	public void onChange$txtContrasena2() {
		if (txtContrasena1.getText().length() <= 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
			return;
		}
		if (txtContrasena2.getText().length() <= 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
			return;
		}
		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Claves no iguales");
			return;
		}
		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
			pgsSeguridad.setValue(0);
			lblSeguridad.setValue("Clave no segura");
		}
		if (validar.validarPassword(txtContrasena2.getText()) == 1) {
			pgsSeguridad.setValue(100);
			lblSeguridad.setValue("Clave segura");
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, usuario.getId_usuario(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, usuario.getId_usuario(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, usuario.getId_usuario(), 7);
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

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
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
					if (txtUsuario.getText().length() <= 0) {
						txtUsuario.setErrorMessage("Ingrese el usuario.");
						return;
					}
					if (txtContrasena1.getText().length() <= 0) {
						txtContrasena1.setErrorMessage("Ingrese la contraseña.");
						return;
					}
					if (txtContrasena2.getText().length() <= 0) {
						txtContrasena2.setErrorMessage("Debe confirmar la contraseña.");
						return;
					}
					if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
						txtContrasena2.setErrorMessage("Las contraseñas no son iguales.");
						return;
					}
					if (!tmpPassword.equals(txtContrasena1.getText())) {
						if (validar.validarPassword(txtContrasena2.getText()) == 0) {
							txtContrasena2.setErrorMessage("La contraseña no es segura.");
							return;
						}
					}
					if (txtNombres.getText().length() <= 0) {
						txtNombres.setErrorMessage("Ingrese los nombres.");
						return;
					}
					if (txtApellidos.getText().length() <= 0) {
						txtApellidos.setErrorMessage("Ingrese los apellidos.");
						return;
					}
					if (cmbPerfil.getSelectedItem() == null) {
						cmbPerfil.setErrorMessage("Seleccione un perfil.");
						return;
					}
					if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("1")) {
						cmbPerfil.setErrorMessage(
								"El perfil seleccionado es de administrador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
						return;
					}
					if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("3")) {
						cmbPerfil.setErrorMessage(
								"El perfil seleccionado es de aprobador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
						return;
					}
					if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("5")) {
						cmbPerfil.setErrorMessage(
								"El perfil seleccionado es de coordinador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
						return;
					}
					if (cmbLocalidad.getSelectedItem() == null) {
						cmbLocalidad.setErrorMessage("Seleccione una localidad.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 5) {
						if (txtNombres.getText().length() <= 0) {
							txtNombres.setErrorMessage("Ingrese los nombres.");
							return;
						}
					}
					if (solicitud.getId_campo() == 6) {
						if (txtApellidos.getText().length() <= 0) {
							txtApellidos.setErrorMessage("Ingrese los apellidos.");
							return;
						}
					}
					if (solicitud.getId_campo() == 15) {
						if (txtContrasena1.getText().length() <= 0) {
							txtContrasena1.setErrorMessage("Ingrese la contraseña.");
							return;
						}
						if (txtContrasena2.getText().length() <= 0) {
							txtContrasena2.setErrorMessage("Debe confirmar la contraseña.");
							return;
						}
						if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
							txtContrasena2.setErrorMessage("Las contraseñas no son iguales.");
							return;
						}
						if (!tmpPassword.equals(txtContrasena1.getText())) {
							if (validar.validarPassword(txtContrasena2.getText()) == 0) {
								txtContrasena2.setErrorMessage("La contraseña no es segura.");
								return;
							}
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
		Messagebox.show("Esta seguro de modificar el usuario?", ".:: Modificar usuario ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_usuario dao = new dao_usuario();
							modelo_usuario usuario = new modelo_usuario();
							usuario.setId_usuario(Long.parseLong(txtId.getText()));
							usuario.setUse_usuario(txtUsuario.getText());
							if (tmpPassword.equals(txtContrasena1.getText())) {
								usuario.setPas_usuario(txtContrasena1.getText());
							} else {
								usuario.setPas_usuario(SH2.getSHA256(txtContrasena1.getText()));
							}
							usuario.setNom_usuario(txtNombres.getText());
							usuario.setApe_usuario(txtApellidos.getText());
							usuario.setId_perfil(Long.parseLong(cmbPerfil.getSelectedItem().getValue().toString()));
							if (chkCambiarPassword.isChecked()) {
								usuario.setCam_password("S");
								usuario.setPas_usuario(SH2.getSHA256("noc123"));
							} else {
								usuario.setCam_password("N");
							}
							usuario.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							usuario.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							usuario.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								usuario.setEst_usuario("PACP");
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
								usuario.setEst_usuario("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarUsuario(usuario, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("El usuario se modificó correctamente.",
											".:: Modificar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
									if (chkCambiarPassword.isChecked()) {
										Messagebox.show(
												"Para realizar el cambio de la contraseña debe ingresar noc123, como contraseña por defecto.",
												".:: Modificar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
									}
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("usuario");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar el usuario. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtUsuario.setText("");
		txtContrasena1.setText("");
		txtContrasena2.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		cmbPerfil.setText("");
		chkCambiarPassword.setChecked(false);
		cmbLocalidad.setText("");
		pgsSeguridad.setValue(0);
		lblSeguridad.setValue("Clave no segura");
	}

}
