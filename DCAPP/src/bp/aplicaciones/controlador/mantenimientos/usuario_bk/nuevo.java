//package bp.aplicaciones.controlador.mantenimientos.usuario_bk;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.WrongValueException;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.EventListener;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Progressmeter;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Checkbox;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.SH2;
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
//import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
//import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
//import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
//import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class nuevo extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zNuevo;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtUsuario, txtContrasena1, txtContrasena2, txtNombres, txtApellidos;
//	@Wire
//	Combobox cmbLocalidad, cmbPerfil;
//	@Wire
//	Label lblSeguridad;
//	@Wire
//	Progressmeter pgsSeguridad;
//	@Wire
//	Checkbox chkCambiarPassword;
//
//	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
//	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
//
//	long id = 0;
//	long id_mantenimiento = 4;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	validar_datos validar = new validar_datos();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		txtNombres.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			@SuppressWarnings("static-access")
//			public void onEvent(Event event) throws Exception {
//				txtNombres.setText(validar.soloLetrasyNumeros(txtNombres.getText()));
//			}
//		});
//		txtApellidos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			@SuppressWarnings("static-access")
//			public void onEvent(Event event) throws Exception {
//				txtApellidos.setText(validar.soloLetrasyNumeros(txtApellidos.getText()));
//			}
//		});
//		obtenerId();
//		cargarPerfiles();
//		cargarLocalidades();
//	}
//
//	public List<modelo_perfil> obtenerPerfiles() {
//		return listaPerfil;
//	}
//
//	public List<modelo_localidad> obtenerLocalidades() {
//		return listaLocalidad;
//	}
//
//	public void cargarPerfiles() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_perfil dao = new dao_perfil();
//		String criterio = "";
//		try {
//			listaPerfil = dao.obtenerPerfiles(criterio, 6, 0);
//			binder.loadComponent(cmbPerfil);
//		} catch (SQLException e) {
//			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_localidad dao = new dao_localidad();
//		String criterio = "";
//		try {
//			listaLocalidad = dao.obtenerLocalidades(criterio, 4, 0, 0);
//			binder.loadComponent(cmbLocalidad);
//		} catch (SQLException e) {
//			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_usuario dao = new dao_usuario();
//		try {
//			id = dao.obtenerNuevoId();
//			txtId.setText(String.valueOf(id));
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	@SuppressWarnings("static-access")
//	@Listen("onChange=#txtContrasena1")
//	public void onChange$txtContrasena1() {
//		if (txtContrasena1.getText().length() <= 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//			return;
//		}
//		if (txtContrasena2.getText().length() <= 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//			return;
//		}
//		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Claves no iguales");
//			return;
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 1) {
//			pgsSeguridad.setValue(100);
//			lblSeguridad.setValue("Clave segura");
//		}
//	}
//
//	@SuppressWarnings("static-access")
//	@Listen("onChange=#txtContrasena2")
//	public void onChange$txtContrasena2() {
//		if (txtContrasena1.getText().length() <= 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//			return;
//		}
//		if (txtContrasena2.getText().length() <= 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//			return;
//		}
//		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Claves no iguales");
//			return;
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 1) {
//			pgsSeguridad.setValue(100);
//			lblSeguridad.setValue("Clave segura");
//		}
//	}
//
//	@Listen("onBlur=#txtUsuario")
//	public void onBlur$txtUsuario()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//		if (txtUsuario.getText().length() <= 0) {
//			return;
//		}
//		dao_usuario dao = new dao_usuario();
//		if (dao.obtenerUsuario(txtUsuario.getText(), "", 1) != null) {
//			txtUsuario.setFocus(true);
//			txtUsuario.setErrorMessage("El usuario ya se encuentra registrado.");
//			return;
//		}
//
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//		dao_usuario dao = new dao_usuario();
//		if (txtUsuario.getText().length() <= 0) {
//			txtUsuario.setFocus(true);
//			txtUsuario.setErrorMessage("Ingrese el usuario.");
//			return;
//		}
//		if (dao.obtenerUsuario(txtUsuario.getText(), "", 1) != null) {
//			txtUsuario.setFocus(true);
//			txtUsuario.setErrorMessage("El usuario ya se encuentra registrado.");
//			return;
//		}
//		if (txtContrasena1.getText().length() <= 0) {
//			txtContrasena1.setFocus(true);
//			txtContrasena1.setErrorMessage("Ingrese la contraseña.");
//			return;
//		}
//		if (txtContrasena2.getText().length() <= 0) {
//			txtContrasena2.setFocus(true);
//			txtContrasena2.setErrorMessage("Debe confirmar la contraseña.");
//			return;
//		}
//		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
//			txtContrasena2.setFocus(true);
//			txtContrasena2.setErrorMessage("Las contraseñas no son iguales.");
//			return;
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
//			txtContrasena2.setFocus(true);
//			txtContrasena2.setErrorMessage("La contraseña no es segura.");
//			return;
//		}
//		if (txtNombres.getText().length() <= 0) {
//			txtNombres.setFocus(true);
//			txtNombres.setErrorMessage("Ingrese los nombres.");
//			return;
//		}
//		if (txtApellidos.getText().length() <= 0) {
//			txtApellidos.setFocus(true);
//			txtApellidos.setErrorMessage("Ingrese los apellidos.");
//			return;
//		}
//		if (cmbPerfil.getSelectedItem() == null) {
//			cmbPerfil.setFocus(true);
//			cmbPerfil.setErrorMessage("Seleccione un perfil.");
//			return;
//		}
//		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("1")) {
//			cmbPerfil.setFocus(true);
//			cmbPerfil.setErrorMessage(
//					"El perfil seleccionado es de administrador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
//			return;
//		}
//		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("3")) {
//			cmbPerfil.setFocus(true);
//			cmbPerfil.setErrorMessage(
//					"El perfil seleccionado es de aprobador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
//			return;
//		}
//		if (id_perfil != 1 && cmbPerfil.getSelectedItem().getValue().toString().equals("5")) {
//			cmbPerfil.setFocus(true);
//			cmbPerfil.setErrorMessage(
//					"El perfil seleccionado es de coordinador y usted no tiene los permisos suficientes, debe seleccionar un perfil diferente.");
//			return;
//		}
//		if (cmbLocalidad.getSelectedItem() == null) {
//			cmbLocalidad.setFocus(true);
//			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
//			return;
//		}
//		Messagebox.show("Esta seguro de guardar el usuario?", ".:: Nuevo usuario ::.",
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_usuario dao = new dao_usuario();
//							modelo_usuario_bk usuario = new modelo_usuario_bk();
//							usuario.setUse_usuario(txtUsuario.getText());
//							usuario.setPas_usuario(SH2.getSHA256(txtContrasena1.getText()));
//							usuario.setNom_usuario(txtNombres.getText());
//							usuario.setApe_usuario(txtApellidos.getText());
//							usuario.setId_perfil(Long.parseLong(cmbPerfil.getSelectedItem().getValue().toString()));
//							if (chkCambiarPassword.isChecked()) {
//								usuario.setCam_password("S");
//							} else {
//								usuario.setCam_password("N");
//							}
//							usuario.setId_localidad(
//									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
//							usuario.setEst_usuario("PAC");
//							usuario.setUsu_ingresa(user);
//							java.util.Date date = new Date();
//							Timestamp timestamp = new Timestamp(date.getTime());
//							usuario.setFec_ingresa(timestamp);
//							try {
//								dao.insertarUsuario(usuario);
//								Messagebox.show("El usuario se guardo correctamente.", ".:: Nuevo usuario ::.",
//										Messagebox.OK, Messagebox.EXCLAMATION);
//								limpiarCampos();
//								obtenerId();
//								obtenerLocalidades();
//							} catch (Exception e) {
//								Messagebox.show(
//										"Error al guardar el usuario. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//										".:: Nuevo usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//							}
//						}
//					}
//				});
//
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Events.postEvent(new Event("onClose", zNuevo));
//	}
//
//	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		txtUsuario.setText("");
//		txtContrasena1.setText("");
//		txtContrasena2.setText("");
//		txtNombres.setText("");
//		txtApellidos.setText("");
//		cmbPerfil.setText("");
//		chkCambiarPassword.setChecked(false);
//		cmbLocalidad.setText("");
//		pgsSeguridad.setValue(0);
//		lblSeguridad.setValue("Clave no segura");
//	}
//
//}
