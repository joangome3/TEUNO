package bp.aplicaciones.controlador.mantenimientos.empresa;

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
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevaEmpresa;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;
	@Wire
	Label lNombre, lDescripcion;

	long id = 0;
	long id_mantenimiento = 8;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(validar.soloLetrasyNumeros(txtNombre.getText()));
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(validar.soloLetrasyNumeros(txtDescripcion.getText()));
			}
		});
		obtenerId();
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		try {
			id = dao.obtenerNuevoId();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtId.setText(String.valueOf(id));
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_empresa dao = new dao_empresa();
		if (dao.obtenerEmpresas(txtNombre.getText(), 3, "", "", 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			txtNombre.setFocus(true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtNombre.getText().length() <= 0) {
			txtNombre.setErrorMessage("Ingrese el nombre.");
			txtNombre.setFocus(true);
			return;
		}
		dao_empresa dao = new dao_empresa();
		if (dao.obtenerEmpresas(txtNombre.getText(), 3, "", "", 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			txtNombre.setFocus(true);
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setErrorMessage("Ingrese la descripción.");
			txtDescripcion.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de guardar la empresa?", ".:: Nueva empresa ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_empresa dao = new dao_empresa();
							modelo_empresa empresa = new modelo_empresa();
							empresa.setNom_empresa(txtNombre.getText());
							empresa.setDes_empresa(txtDescripcion.getText());
							empresa.setEst_empresa("PAC");
							empresa.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							empresa.setFec_ingresa(timestamp);
							try {
								dao.insertarEmpresa(empresa);
								Messagebox.show(
										"La empresa se guardó correctamente. \n\n No olvide crear las relaciones para la empresa.",
										".:: Nueva empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la empresa. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										".:: Nueva empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevaEmpresa));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		txtDescripcion.setText("");
		lNombre.setValue("0/500");
		lDescripcion.setValue("0/2");
	}

}
