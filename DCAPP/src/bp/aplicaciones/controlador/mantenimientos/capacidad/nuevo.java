package bp.aplicaciones.controlador.mantenimientos.capacidad;

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
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;

	long id = 0;
	long id_mantenimiento = 13;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
			}
		});
		obtenerId();
		validarSesion();
	}

	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_capacidad dao = new dao_capacidad();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_capacidad dao = new dao_capacidad();
		if (dao.obtenerCapacidades(txtNombre.getText(), 2, 0, 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		validarSesion();
		if (txtNombre.getText().length() <= 0) {
			txtNombre.setErrorMessage("Ingrese el nombre.");
			return;
		}
		dao_capacidad dao = new dao_capacidad();
		if (dao.obtenerCapacidades(txtNombre.getText(), 2, 0, 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setErrorMessage("Ingrese la descripcion.");
			return;
		}
		Messagebox.show("Esta seguro de guardar la capacidad?", ".:: Nueva capacidad ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_capacidad dao = new dao_capacidad();
							modelo_capacidad capacidad = new modelo_capacidad();
							capacidad.setNom_capacidad(txtNombre.getText());
							capacidad.setDes_capacidad(txtDescripcion.getText());
							capacidad.setEst_capacidad("PAC");
							capacidad.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							capacidad.setFec_ingresa(timestamp);
							try {
								dao.insertarCapacidad(capacidad);
								Messagebox.show("La capacidad se guardo correctamente.", ".:: Nueva capacidad ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la capacidad. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nueva capacidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		validarSesion();
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		txtDescripcion.setText("");
	}

}
