//package bp.aplicaciones.controlador.mantenimientos.perfiles_bk;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.Date;
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
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Checkbox;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
//import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class modificar extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zModificar;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtNombre;
//	@Wire
//	Checkbox chkConsultar, chkInsertar, chkModificar, chkRelacionar, chkDesactivar, chkEliminar, chkSolicitar,
//			chkRevisar, chkAprobar, chkEjecutar;
//
//	long id = 0;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	modelo_perfil perfil = (modelo_perfil) Sessions.getCurrent().getAttribute("perfil");
//
//	validar_datos validar = new validar_datos();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			@SuppressWarnings("static-access")
//			public void onEvent(Event event) throws Exception {
//				txtNombre.setText(validar.soloLetrasyNumeros(txtNombre.getText()));
//			}
//		});
//		cargarDatos();
//	}
//
//	public void cargarDatos() {
//		txtId.setText(String.valueOf(perfil.getId_perfil()));
//		txtNombre.setText(perfil.getNom_perfil());
//		if (perfil.getConsultar().equals("S")) {
//			chkConsultar.setChecked(true);
//		} else {
//			chkConsultar.setChecked(false);
//		}
//		if (perfil.getInsertar().equals("S")) {
//			chkInsertar.setChecked(true);
//		} else {
//			chkInsertar.setChecked(false);
//		}
//		if (perfil.getModificar().equals("S")) {
//			chkModificar.setChecked(true);
//		} else {
//			chkModificar.setChecked(false);
//		}
//		if (perfil.getRelacionar().equals("S")) {
//			chkRelacionar.setChecked(true);
//		} else {
//			chkRelacionar.setChecked(false);
//		}
//		if (perfil.getDesactivar().equals("S")) {
//			chkDesactivar.setChecked(true);
//		} else {
//			chkDesactivar.setChecked(false);
//		}
//		if (perfil.getEliminar().equals("S")) {
//			chkEliminar.setChecked(true);
//		} else {
//			chkEliminar.setChecked(false);
//		}
//		if (perfil.getSolicitar().equals("S")) {
//			chkSolicitar.setChecked(true);
//		} else {
//			chkSolicitar.setChecked(false);
//		}
//		if (perfil.getRevisar().equals("S")) {
//			chkRevisar.setChecked(true);
//		} else {
//			chkRevisar.setChecked(false);
//		}
//		if (perfil.getAprobar().equals("S")) {
//			chkAprobar.setChecked(true);
//		} else {
//			chkAprobar.setChecked(false);
//		}
//		if (perfil.getEjecutar().equals("S")) {
//			chkEjecutar.setChecked(true);
//		} else {
//			chkEjecutar.setChecked(false);
//		}
//	}
//
//	@Listen("onBlur=#txtNombre")
//	public void onBlur$txtNombre()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//		if (txtNombre.getText().length() <= 0) {
//			return;
//		}
//		dao_perfil dao = new dao_perfil();
//		if (dao.obtenerPerfiles(txtNombre.getText(), 3, perfil.getId_perfil()).size() > 0) {
//			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
//			return;
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (txtNombre.getText().length() <= 0) {
//			txtNombre.setErrorMessage("Ingrese el nombre.");
//			return;
//		}
//		dao_perfil dao = new dao_perfil();
//		if (dao.obtenerPerfiles(txtNombre.getText(), 3, perfil.getId_perfil()).size() > 0) {
//			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
//			return;
//		}
//		Messagebox.show("Esta seguro de guardar el perfil?", ".:: Modificar perfil ::.",
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_perfil dao = new dao_perfil();
//							modelo_perfil perfil = new modelo_perfil();
//							perfil.setId_perfil(Long.valueOf(txtId.getText()));
//							perfil.setNom_perfil(txtNombre.getText());
//							if (chkConsultar.isChecked()) {
//								perfil.setConsultar("S");
//							} else {
//								perfil.setConsultar("N");
//							}
//							if (chkInsertar.isChecked()) {
//								perfil.setInsertar("S");
//							} else {
//								perfil.setInsertar("N");
//							}
//							if (chkModificar.isChecked()) {
//								perfil.setModificar("S");
//							} else {
//								perfil.setModificar("N");
//							}
//							if (chkRelacionar.isChecked()) {
//								perfil.setRelacionar("S");
//							} else {
//								perfil.setRelacionar("N");
//							}
//							if (chkDesactivar.isChecked()) {
//								perfil.setDesactivar("S");
//							} else {
//								perfil.setDesactivar("N");
//							}
//							if (chkEliminar.isChecked()) {
//								perfil.setEliminar("S");
//							} else {
//								perfil.setEliminar("N");
//							}
//							if (chkSolicitar.isChecked()) {
//								perfil.setSolicitar("S");
//							} else {
//								perfil.setSolicitar("N");
//							}
//							if (chkRevisar.isChecked()) {
//								perfil.setRevisar("S");
//							} else {
//								perfil.setRevisar("N");
//							}
//							if (chkAprobar.isChecked()) {
//								perfil.setAprobar("S");
//							} else {
//								perfil.setAprobar("N");
//							}
//							if (chkEjecutar.isChecked()) {
//								perfil.setEjecutar("S");
//							} else {
//								perfil.setEjecutar("N");
//							}
//							perfil.setEst_perfil("A");
//							perfil.setUsu_ingresa(user);
//							java.util.Date date = new Date();
//							Timestamp timestamp = new Timestamp(date.getTime());
//							perfil.setFec_ingresa(timestamp);
//							try {
//								dao.modificarPerfil(perfil);
//								Messagebox.show("El perfil se guardo correctamente.", ".:: Modificar perfil ::.",
//										Messagebox.OK, Messagebox.EXCLAMATION);
//								limpiarCampos();
//								Sessions.getCurrent().removeAttribute("perfil");
//								Events.postEvent(new Event("onClose", zModificar));
//							} catch (Exception e) {
//								Messagebox.show(
//										"Error al guardar el perfil. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//										".:: Modificar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
//		txtNombre.setText("");
//		chkConsultar.setChecked(false);
//		chkInsertar.setChecked(false);
//		chkModificar.setChecked(false);
//		chkRelacionar.setChecked(false);
//		chkDesactivar.setChecked(false);
//		chkEliminar.setChecked(false);
//		chkSolicitar.setChecked(false);
//		chkRevisar.setChecked(false);
//		chkAprobar.setChecked(false);
//		chkEjecutar.setChecked(false);
//	}
//
//}
