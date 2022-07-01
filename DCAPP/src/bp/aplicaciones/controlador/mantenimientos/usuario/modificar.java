package bp.aplicaciones.controlador.mantenimientos.usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
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
	Window zModificarLocalidad;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtUsuario, txtContrasena1, txtContrasena2, txtNombres, txtApellidos;
	@Wire
	Combobox cmbLocalidad, cmbPerfil;
	@Wire
	Label lblSeguridad;
	@Wire
	Progressmeter pgsSeguridad;
	@Wire
	Checkbox chkCambiarPassword;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

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
		txtUsuario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtUsuario.setText(validar.soloLetrasyNumeros(txtUsuario.getText()).toLowerCase().trim());
			}
		});
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
		cargarPerfiles();
		cargarLocalidades();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(usuario.getId_usuario()));
		txtUsuario.setText(usuario.getUse_usuario());
		txtContrasena1.setText("");
		txtContrasena2.setText("");
		txtContrasena1.setReadonly(true);
		txtContrasena2.setReadonly(true);
		txtNombres.setText(usuario.getNom_usuario());
		txtApellidos.setText(usuario.getApe_usuario());
		cmbPerfil.setText(usuario.getPerfil().getNom_perfil());
		cmbLocalidad.setText(usuario.getLocalidad().getNom_localidad());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarPerfiles() {
		listaPerfil = consultasABaseDeDatos.consultarPerfiles(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_perfil bean = (modelo_perfil) o2;
				return bean.getNom_perfil().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaPerfil), myComparator, 15);
		cmbPerfil.setModel(mySubModel);
		ComboitemRenderer<modelo_perfil> myRenderer = new ComboitemRenderer<modelo_perfil>() {
			@Override
			public void render(Comboitem item, modelo_perfil bean, int index) throws Exception {
				item.setLabel(bean.getNom_perfil());
				item.setValue(bean);
			}
		};
		cmbPerfil.setItemRenderer(myRenderer);
		binder.loadComponent(cmbPerfil);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarLocalidades() {
		listaLocalidad = consultasABaseDeDatos.consultarLocalidades(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_localidad bean = (modelo_localidad) o2;
				return bean.getNom_localidad().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaLocalidad), myComparator, 15);
		cmbLocalidad.setModel(mySubModel);
		ComboitemRenderer<modelo_localidad> myRenderer = new ComboitemRenderer<modelo_localidad>() {
			@Override
			public void render(Comboitem item, modelo_localidad bean, int index) throws Exception {
				item.setLabel(bean.getNom_localidad());
				item.setValue(bean);
			}
		};
		cmbLocalidad.setItemRenderer(myRenderer);
		binder.loadComponent(cmbLocalidad);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtNombres.getText().length() <= 0) {
			txtNombres.setFocus(true);
			Clients.showNotification("Ingrese el/los nombres del usuario.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (txtApellidos.getText().length() <= 0) {
			txtApellidos.setFocus(true);
			Clients.showNotification("Ingrese el/los apellidos del usuario.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (cmbPerfil.getSelectedItem() == null) {
			cmbPerfil.setFocus(true);
			Clients.showNotification("Seleccione un perfil de la lista.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("1")) {
			cmbPerfil.setFocus(true);
			Clients.showNotification(
					"El perfil seleccionado es de administrador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("3")) {
			cmbPerfil.setFocus(true);
			Clients.showNotification(
					"El perfil seleccionado es de aprobador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("5")) {
			cmbPerfil.setFocus(true);
			Clients.showNotification(
					"El perfil seleccionado es de coordinador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setFocus(true);
			Clients.showNotification("Seleccione una localidad de la lista.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		Messagebox.show("Esta seguro de modificar el usuario?", ".:: Modificar usuario ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							int tipo = 0;
							dao_usuario dao = new dao_usuario();
							modelo_usuario usuario = new modelo_usuario();
							usuario = modificar.this.usuario;
							usuario.setId_usuario(Long.parseLong(txtId.getText()));
							usuario.setUse_usuario(txtUsuario.getText());
							if (tmpPassword.equals(txtContrasena1.getText())) {
								usuario.setPas_usuario(txtContrasena1.getText());
							} else {
								usuario.setPas_usuario(SH2.getSHA256(txtContrasena1.getText()));
							}
							usuario.setNom_usuario(txtNombres.getText());
							usuario.setApe_usuario(txtApellidos.getText());
							usuario.setPerfil(((modelo_perfil) cmbPerfil.getSelectedItem().getValue()));
							if (chkCambiarPassword.isChecked()) {
								usuario.setCam_password("S");
								usuario.setPas_usuario(SH2.getSHA256("noc123"));
								tipo = 1;
							} else {
								usuario.setCam_password("N");
							}
							usuario.setLocalidad((modelo_localidad) cmbLocalidad.getSelectedItem().getValue());
							usuario.setEst_usuario("AE");
							usuario.setUsu_modifica(user);
							usuario.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.actualizarUsuario(usuario);
								if (tipo == 1) {
									Clients.showNotification(
											"El usuario se actualizó correctamente, para realizar el cambio de la contraseña debe ingresar noc123, como contraseña por defecto.",
											Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								} else {
									Clients.showNotification("El usuario se actualizó correctamente.",
											Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("usuario");
								Events.postEvent(new Event("onClose", zModificarLocalidad));
							} catch (Exception e) {
								Clients.showNotification(
										"Error al actualizar el usuario. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificarLocalidad));
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
