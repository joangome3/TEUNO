package bp.aplicaciones.controlador.mantenimientos.perfiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevoPerfil;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtNombre;
	@Wire
	Checkbox chkConsultar, chkInsertar, chkModificar, chkRelacionar, chkDesactivar, chkEliminar, chkSolicitar,
			chkRevisar, chkAprobar, chkEjecutar;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;

	long id_mantenimiento = 7;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase().trim());
			}
		});
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_perfil dao = new dao_perfil();
		if (dao.consultarPerfiles(0, 0, txtNombre.getText(), "", 0, 3).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtNombre.getText().length() <= 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("Ingrese el nombre.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right",
					2000, true);
			return;
		}
		dao_perfil dao = new dao_perfil();
		if (dao.consultarPerfiles(0, 0, txtNombre.getText(), "", 0, 3).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
		}
		Messagebox.show("Esta seguro de guardar el perfil?", ".:: Nuevo perfil ::.", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_perfil dao = new dao_perfil();
							modelo_perfil perfil = new modelo_perfil();
							perfil.setNom_perfil(txtNombre.getText());
							if (chkConsultar.isChecked()) {
								perfil.setConsultar("S");
							} else {
								perfil.setConsultar("N");
							}
							if (chkInsertar.isChecked()) {
								perfil.setInsertar("S");
							} else {
								perfil.setInsertar("N");
							}
							if (chkModificar.isChecked()) {
								perfil.setModificar("S");
							} else {
								perfil.setModificar("N");
							}
							if (chkRelacionar.isChecked()) {
								perfil.setRelacionar("S");
							} else {
								perfil.setRelacionar("N");
							}
							if (chkDesactivar.isChecked()) {
								perfil.setDesactivar("S");
							} else {
								perfil.setDesactivar("N");
							}
							if (chkEliminar.isChecked()) {
								perfil.setEliminar("S");
							} else {
								perfil.setEliminar("N");
							}
							if (chkSolicitar.isChecked()) {
								perfil.setSolicitar("S");
							} else {
								perfil.setSolicitar("N");
							}
							if (chkRevisar.isChecked()) {
								perfil.setRevisar("S");
							} else {
								perfil.setRevisar("N");
							}
							if (chkAprobar.isChecked()) {
								perfil.setAprobar("S");
							} else {
								perfil.setAprobar("N");
							}
							if (chkEjecutar.isChecked()) {
								perfil.setEjecutar("S");
							} else {
								perfil.setEjecutar("N");
							}
							perfil.setEst_perfil("AE");
							perfil.setUsu_ingresa(user);
							perfil.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.insertarPerfil(perfil);
								Clients.showNotification("El perfil se guardó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "top_right", 4000);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al guardar el perfil. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevoPerfil));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		chkConsultar.setChecked(false);
		chkInsertar.setChecked(false);
		chkModificar.setChecked(false);
		chkRelacionar.setChecked(false);
		chkDesactivar.setChecked(false);
		chkEliminar.setChecked(false);
		chkSolicitar.setChecked(false);
		chkRevisar.setChecked(false);
		chkAprobar.setChecked(false);
		chkEjecutar.setChecked(false);
	}

}
