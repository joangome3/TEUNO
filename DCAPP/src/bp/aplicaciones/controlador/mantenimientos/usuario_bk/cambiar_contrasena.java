//package bp.aplicaciones.controlador.mantenimientos.usuario_bk;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.Date;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Progressmeter;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.SH2;
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class cambiar_contrasena extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zModificar;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtContrasena1, txtContrasena2;
//	@Wire
//	Label lblSeguridad;
//	@Wire
//	Progressmeter pgsSeguridad;
//
//	validar_datos validar = new validar_datos();
//
//	modelo_usuario_bk usuario = (modelo_usuario_bk) Sessions.getCurrent().getAttribute("usuario");
//
//	long id = usuario.getId_usuario();
//	String user = usuario.getUse_usuario();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
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
//	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar() {
//		if (txtContrasena1.getText().length() <= 0) {
//			txtContrasena1.setErrorMessage("Ingrese la contraseña.");
//			return;
//		}
//		if (txtContrasena2.getText().length() <= 0) {
//			txtContrasena2.setErrorMessage("Debe confirmar la contraseña.");
//			return;
//		}
//		if (!txtContrasena1.getText().equals(txtContrasena2.getText())) {
//			txtContrasena2.setErrorMessage("Las contraseñas no son iguales.");
//			return;
//		}
//		if (validar.validarPassword(txtContrasena2.getText()) == 0) {
//			pgsSeguridad.setValue(0);
//			lblSeguridad.setValue("Clave no segura");
//		}
//		Messagebox.show("Esta seguro de cambiar la contraseña?", ".:: Cambiar contraseña ::.",
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_usuario dao = new dao_usuario();
//							modelo_usuario_bk usuario = new modelo_usuario_bk();
//							usuario.setId_usuario(id);
//							usuario.setPas_usuario(SH2.getSHA256(txtContrasena2.getText()));
//							usuario.setCam_password("N");
//							usuario.setEst_usuario("AE");
//							usuario.setUsu_modifica(user);
//							java.util.Date date = new Date();
//							Timestamp timestamp = new Timestamp(date.getTime());
//							usuario.setFec_modifica(timestamp);
//							try {
//								dao.cambiarContrasena(usuario);
//								Messagebox.show("La contraseña se cambio correctamente.", ".:: Cambiar contraseña ::.",
//										Messagebox.OK, Messagebox.EXCLAMATION);
//								limpiarCampos();
//								Sessions.getCurrent().removeAttribute("usuario");
//								Events.postEvent(new Event("onClose", zModificar));
//							} catch (Exception e) {
//								Messagebox.show(
//										"Error al cambiar la contraseña. \n\n" + "Mensaje de error: \n\n"
//												+ e.getMessage(),
//										".:: Cambiar contraseña ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//							}
//						}
//					}
//				});
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Events.postEvent(new Event("onClose", zModificar));
//	}
//
//	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		txtContrasena1.setText("");
//		txtContrasena2.setText("");
//		pgsSeguridad.setValue(0);
//		lblSeguridad.setValue("Clave no segura");
//	}
//
//}
