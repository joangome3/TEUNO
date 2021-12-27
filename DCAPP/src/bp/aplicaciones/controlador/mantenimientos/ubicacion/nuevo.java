package bp.aplicaciones.controlador.mantenimientos.ubicacion;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtPosicion;
	@Wire
	Combobox cmbLocalidad, cmbEmpresa, cmbUbicacion;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_tipo_ubicacion> listaUbicacion = new ArrayList<modelo_tipo_ubicacion>();

	long id = 0;
	long id_mantenimiento = 2;

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
		txtPosicion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtPosicion.setText(validar.soloLetrasyNumeros(txtPosicion.getText()));
			}
		});
		obtenerId();
		cargarLocalidades();
		cargarEmpresas();
		cargarUbicaciones();
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_ubicacion> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 4, 0, 0);
			binder.loadComponent(cmbLocalidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == id_dc) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 1, String.valueOf(id_dc), "2", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las empresas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		String criterio = "";
		try {
			listaUbicacion = dao.obtenerTipoUbicaciones(criterio, 0, 1);
			binder.loadComponent(cmbUbicacion);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicacions. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_ubicacion dao = new dao_ubicacion();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onBlur=#txtPosicion")
	public void onBlur$txtPosicion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbEmpresa.getSelectedItem() == null) {
			return;
		}
		if (cmbUbicacion.getSelectedItem() == null) {
			return;
		}
		if (txtPosicion.getText().length() <= 0) {
			return;
		}
		dao_ubicacion dao = new dao_ubicacion();
		if (dao.obtenerUbicaciones(txtPosicion.getText(), String.valueOf(id_dc), 2,
				Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()),
				Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()), 0, 0).size() > 0) {
			txtPosicion.setErrorMessage(
					"La posición ya se encuentra registrada para la empresa y ubicación seleccionada.");
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws WrongValueException, NumberFormatException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			cmbEmpresa.setErrorMessage("Seleccione una empresa.");
			return;
		}
		if (cmbUbicacion.getSelectedItem() == null) {
			cmbUbicacion.setErrorMessage("Seleccione una ubicacion.");
			return;
		}
		if (txtPosicion.getText().length() <= 0) {
			txtPosicion.setErrorMessage("Ingrese la ubicacion.");
			return;
		}
		dao_ubicacion dao = new dao_ubicacion();
		if (dao.obtenerUbicaciones(txtPosicion.getText(), String.valueOf(id_dc), 2,
				Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()),
				Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()), 0, 0).size() > 0) {
			txtPosicion.setErrorMessage(
					"La posición ya se encuentra registrada para la empresa y ubicación seleccionada.");
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			return;
		}
		Messagebox.show("Esta seguro de guardar la ubicacion?", ".:: Nueva ubicacion ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_ubicacion dao = new dao_ubicacion();
							modelo_ubicacion ubicacion = new modelo_ubicacion();
							ubicacion.setId_tip_ubicacion(
									Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()));
							ubicacion.setId_empresa(Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()));
							ubicacion.setPos_ubicacion(txtPosicion.getText());
							ubicacion.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							ubicacion.setEst_ubicacion("PAC");
							ubicacion.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							ubicacion.setFec_ingresa(timestamp);
							try {
								dao.insertarUbicacion(ubicacion);
								Messagebox.show("La ubicacion se guardo correctamente.", ".:: Nueva ubicacion ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarLocalidades();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la ubicacion. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nueva ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});

	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtPosicion.setText("");
		cmbLocalidad.setText("");
		cmbUbicacion.setText("");
		cmbEmpresa.setText("");
	}

}
