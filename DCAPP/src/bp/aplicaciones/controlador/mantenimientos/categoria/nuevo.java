package bp.aplicaciones.controlador.mantenimientos.categoria;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;
	@Wire
	Combobox cmbLocalidad;
	@Wire
	Checkbox chkMarca, chkModelo, chkSerie, chkCodigoActivo;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id = 0;
	long id_mantenimiento = 5;

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
		cargarLocalidades();
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 1, 0, 0);
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

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria dao = new dao_categoria();
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
		dao_categoria dao = new dao_categoria();
		if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 2, 0, 0).size() > 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtNombre.getText().length() <= 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("Ingrese la categoria.");
			return;
		}
		dao_categoria dao = new dao_categoria();
		if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 2, 0, 0).size() > 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage("Ingrese la contraseÃ±a.");
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setFocus(true);
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			return;
		}
		Messagebox.show("Esta seguro de guardar la categoría?", ".:: Nueva categoría ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_categoria dao = new dao_categoria();
							modelo_categoria categoria = new modelo_categoria();
							categoria.setNom_categoria(txtNombre.getText());
							categoria.setDes_categoria(txtDescripcion.getText());
							categoria.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (chkMarca.isChecked()) {
								categoria.setMos_marca("S");
							} else {
								categoria.setMos_marca("N");
							}
							if (chkModelo.isChecked()) {
								categoria.setMos_modelo("S");
							} else {
								categoria.setMos_modelo("N");
							}
							if (chkSerie.isChecked()) {
								categoria.setMos_serie("S");
							} else {
								categoria.setMos_serie("N");
							}
							if (chkCodigoActivo.isChecked()) {
								categoria.setMos_cod_activo("S");
							} else {
								categoria.setMos_cod_activo("N");
							}
							categoria.setEst_categoria("PAC");
							categoria.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							categoria.setFec_ingresa(timestamp);
							try {
								dao.insertarCategoria(categoria);
								Messagebox.show("La categoría se guardó correctamente.", ".:: Nueva categoría ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarLocalidades();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la categoría. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nueva categoría ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		txtNombre.setText("");
		txtDescripcion.setText("");
		cmbLocalidad.setText("");
		chkMarca.setChecked(false);
		chkModelo.setChecked(false);
		chkSerie.setChecked(false);
		chkCodigoActivo.setChecked(false);
	}

}
