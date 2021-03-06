package bp.aplicaciones.controlador.mantenimientos.empresa;

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
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificarEmpresa;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;
	long id_mantenimiento = 23;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_empresa empresa = (modelo_empresa) Sessions.getCurrent().getAttribute("empresa");

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
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
			}
		});
		cargarDatos();
	}

	public void cargarDatos() {
		txtId.setText(String.valueOf(empresa.getId_empresa()));
		txtNombre.setText(empresa.getNom_empresa());
		txtDescripcion.setText(empresa.getDes_empresa());
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_empresa dao = new dao_empresa();
		if (dao.consultarEmpresas(empresa.getId_empresa(), 0, txtNombre.getText(), "", 0, 4).size() > 0) {
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
		dao_empresa dao = new dao_empresa();
		if (dao.consultarEmpresas(empresa.getId_empresa(), 0, txtNombre.getText(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			Clients.showNotification("Ingrese las descripci?n.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right",
					2000, true);
			return;
		}
		Messagebox.show("Esta seguro de actualizar la empresa?", ".:: Modificar empresa ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_empresa dao = new dao_empresa();
							modelo_empresa empresa = new modelo_empresa();
							empresa = modificar.this.empresa;
							empresa.setNom_empresa(txtNombre.getText());
							empresa.setDes_empresa(txtDescripcion.getText());
							empresa.setEst_empresa("AE");
							empresa.setUsu_modifica(user);
							empresa.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.actualizarEmpresa(empresa);
								Clients.showNotification("La empresa se actualiz? correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "top_right", 4000, true);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al actualizar la empresa. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificarEmpresa));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		txtDescripcion.setText("");
		Events.postEvent(new Event("onClose", zModificarEmpresa));
	}

}
