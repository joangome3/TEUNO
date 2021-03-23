package bp.aplicaciones.controlador.mantenimientos.respaldo;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_respaldo;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_respaldo;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtDiaRespaldo;
	@Wire
	Combobox cmbTipoRespaldo;
	@Wire
	Checkbox chkRespaldo;

	long id = 0;
	long id_mantenimiento = 12;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	List<modelo_tipo_respaldo> listaTipoRespaldo = new ArrayList<modelo_tipo_respaldo>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtDiaRespaldo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDiaRespaldo.setText(validar.soloLetrasyNumeros(txtDiaRespaldo.getText()));
			}
		});
		obtenerId();
		cargarTipoRespaldos();
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_respaldo dao = new dao_respaldo();
		try {
			id = dao.obtenerNuevoId();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtId.setText(String.valueOf(id));
	}

	public List<modelo_tipo_respaldo> obtenerTipoRespaldos() {
		return listaTipoRespaldo;
	}

	public void cargarTipoRespaldos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_respaldo dao = new dao_tipo_respaldo();
		String criterio = "";
		try {
			listaTipoRespaldo = dao.obtenerTipoRespaldos(criterio);
			binder.loadComponent(cmbTipoRespaldo);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipos de respaldos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onBlur=#txtDiaRespaldo")
	public void onBlur$txtDiaRespaldo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtDiaRespaldo.getText().length() <= 0) {
			return;
		}
		if (cmbTipoRespaldo.getSelectedItem() == null) {
			return;
		}
		dao_respaldo dao = new dao_respaldo();
		if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 3, "",
				txtDiaRespaldo.getText(), 0).size() > 0) {
			txtDiaRespaldo.setErrorMessage(
					"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoRespaldo.getSelectedItem() == null) {
			cmbTipoRespaldo.setErrorMessage("Seleccione un tipo de respaldo.");
			return;
		}
		if (txtDiaRespaldo.getText().length() <= 0) {
			txtDiaRespaldo.setErrorMessage("Ingrese la descripción.");
			return;
		}
		dao_respaldo dao = new dao_respaldo();
		if (dao.obtenerRespaldos(cmbTipoRespaldo.getSelectedItem().getValue().toString(), 3, "",
				txtDiaRespaldo.getText(), 0).size() > 0) {
			txtDiaRespaldo.setErrorMessage(
					"La descripción ingresada ya se encuentra registrada para el tipo de respaldo seleccionado.");
			return;
		}
		Messagebox.show("Esta seguro de guardar el respaldo?", ".:: Nuevo respaldo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_respaldo dao = new dao_respaldo();
							modelo_respaldo respaldo = new modelo_respaldo();
							respaldo.setId_tip_respaldo(cmbTipoRespaldo.getSelectedItem().getValue());
							respaldo.setDia_respaldo(txtDiaRespaldo.getText());
							if (chkRespaldo.isChecked()) {
								respaldo.setEs_fec_respaldo("S");
							} else {
								respaldo.setEs_fec_respaldo("N");
							}
							respaldo.setEst_respaldo("PAC");
							respaldo.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							respaldo.setFec_ingresa(timestamp);
							try {
								dao.insertarRespaldo(respaldo);
								Messagebox.show("El respaldo se guardo correctamente.", ".:: Nuevo respaldo ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el respaldo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nuevo respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		cmbTipoRespaldo.setText("");
		txtDiaRespaldo.setText("");
		chkRespaldo.setChecked(false);
	}

}
