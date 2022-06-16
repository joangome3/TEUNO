package bp.aplicaciones.controlador.mantenimientos.estado_equipo;

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
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre;
	@Wire
	Label lNombre;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;
	long id_mantenimiento = 23;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_estado_equipo estado_equipo = (modelo_estado_equipo) Sessions.getCurrent().getAttribute("estado_equipo");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase().trim());
			}
		});
		cargarDatos();
	}

	public void cargarDatos() {
		txtId.setText(String.valueOf(estado_equipo.getId_estado()));
		txtNombre.setText(estado_equipo.getNom_estado());
		lNombre.setValue(txtNombre.getText().length() + "/100");
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_estado_equipo dao = new dao_estado_equipo();
		if (dao.consultarEstadosEquipo(estado_equipo.getId_estado(), 0, txtNombre.getText(), "", 0, 4).size() > 0) {
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
		dao_estado_equipo dao = new dao_estado_equipo();
		if (dao.consultarEstadosEquipo(estado_equipo.getId_estado(), 0, txtNombre.getText(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			;
			return;
		}
		Messagebox.show("Esta seguro de actualizar el estado de equipo?", ".:: Modificar estado de equipo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_estado_equipo dao = new dao_estado_equipo();
							modelo_estado_equipo estado_equipo = new modelo_estado_equipo();
							estado_equipo = modificar.this.estado_equipo;
							estado_equipo.setNom_estado(txtNombre.getText());
							estado_equipo.setEst_estado("AE");
							estado_equipo.setUsu_modifica(user);
							estado_equipo.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.actualizarEstadoEquipo(estado_equipo);
								Clients.showNotification("El estado de equipo se actualizó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "top_right", 4000, true);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al actualizar el estado de equipo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
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
		txtNombre.setText("");
		lNombre.setValue("0/100");
		Events.postEvent(new Event("onClose", zModificar));
	}

}
