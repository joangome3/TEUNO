package bp.aplicaciones.controlador.mantenimientos.categoria_dn;

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

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
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
	Checkbox chkMarca, chkModelo, chkSerie, chkCodigoActivo, chkCapacidad, chkFechaInicio, chkFechaFin, chkTipoRespaldo,
			chkIDContenedor;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id = 0;
	long id_mantenimiento = 14;

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
		validarSesion();
	}
	
	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {
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
		dao_categoria_dn dao = new dao_categoria_dn();
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
		dao_categoria_dn dao = new dao_categoria_dn();
		if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 2, 0, 0).size() > 0) {
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
			txtNombre.setErrorMessage("Ingrese la categoria.");
			return;
		}
		dao_categoria_dn dao = new dao_categoria_dn();
		if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 2, 0, 0).size() > 0) {
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setErrorMessage("Ingrese la contraseña.");
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			return;
		}
		Messagebox.show("Esta seguro de guardar la categoria?", ".:: Nueva categoria ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_categoria_dn dao = new dao_categoria_dn();
							modelo_categoria_dn categoria = new modelo_categoria_dn();
							categoria.setNom_categoria(txtNombre.getText());
							categoria.setDes_categoria(txtDescripcion.getText());
							categoria.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (chkCapacidad.isChecked()) {
								categoria.setMos_capacidad("S");
							} else {
								categoria.setMos_capacidad("N");
							}
							if (chkFechaInicio.isChecked()) {
								categoria.setMos_fec_inicio("S");
							} else {
								categoria.setMos_fec_inicio("N");
							}
							if (chkFechaFin.isChecked()) {
								categoria.setMos_fec_fin("S");
							} else {
								categoria.setMos_fec_fin("N");
							}
							if (chkTipoRespaldo.isChecked()) {
								categoria.setMos_tip_respaldo("S");
							} else {
								categoria.setMos_tip_respaldo("N");
							}
							if (chkIDContenedor.isChecked()) {
								categoria.setMos_id_contenedor("S");
							} else {
								categoria.setMos_id_contenedor("N");
							}
							categoria.setEst_categoria("PAC");
							categoria.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							categoria.setFec_ingresa(timestamp);
							try {
								dao.insertarCategoria(categoria);
								Messagebox.show("La categoria se guardo correctamente.", ".:: Nueva categoria ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarLocalidades();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la categoria. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nueva categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		cmbLocalidad.setText("");
		chkMarca.setChecked(false);
		chkModelo.setChecked(false);
		chkSerie.setChecked(false);
		chkCodigoActivo.setChecked(false);
		chkCapacidad.setChecked(false);
		chkFechaInicio.setChecked(false);
		chkFechaFin.setChecked(false);
		chkTipoRespaldo.setChecked(false);
		chkIDContenedor.setChecked(false);
	}

}
