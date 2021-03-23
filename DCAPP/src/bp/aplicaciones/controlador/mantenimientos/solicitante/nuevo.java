package bp.aplicaciones.controlador.mantenimientos.solicitante;

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
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_documento;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_documento;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtDocumento, txtNombres, txtApellidos;
	@Wire
	Combobox cmbEmpresa, cmbTipoDocumento;

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_tipo_documento> listaTipoDocumento = new ArrayList<modelo_tipo_documento>();

	long id = 0;
	long id_mantenimiento = 1;

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
		txtDocumento.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDocumento.setText(validar.soloNumeros(txtDocumento.getText()));
			}
		});
		txtNombres.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombres.setText(validar.soloLetrasyNumeros(txtNombres.getText()));
			}
		});
		txtApellidos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtApellidos.setText(validar.soloLetrasyNumeros(txtApellidos.getText()));
			}
		});
		obtenerId();
		cargarEmpresas();
		cargarTipoDocumentos();
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_documento> obtenerTipoDocumentos() {
		return listaTipoDocumento;
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 1, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las empresas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoDocumentos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_documento dao = new dao_tipo_documento();
		String criterio = "";
		try {
			listaTipoDocumento = dao.obtenerTipoDocumentos(criterio);
			binder.loadComponent(cmbTipoDocumento);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipo de documentos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cagar tipo de documento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitante dao = new dao_solicitante();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings("static-access")
	@Listen("onBlur=#txtDocumento")
	public void onBlur$txtDocumento()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoDocumento.getSelectedItem() == null) {
			return;
		}
		if (txtDocumento.getText().length() <= 0) {
			return;
		}
		if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
			if (validar.validarCedula(txtDocumento.getText()) == 0) {
				txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
				return;
			}
		}
		dao_solicitante dao = new dao_solicitante();
		if (dao.obtenerSolicitantes(txtDocumento.getText(), 3, "", "", 0).size() > 0) {
			txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
			txtDocumento.setFocus(true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoDocumento.getSelectedItem() == null) {
			cmbTipoDocumento.setErrorMessage("Seleccione un tipo de documento.");
			cmbTipoDocumento.setFocus(true);
			return;
		}
		if (txtDocumento.getText().length() <= 0) {
			txtDocumento.setErrorMessage("Ingrese el numero de documento.");
			txtDocumento.setFocus(true);
			return;
		}
		if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
			if (validar.validarCedula(txtDocumento.getText()) == 0) {
				txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
				txtDocumento.setFocus(true);
				return;
			}
		}
		dao_solicitante dao = new dao_solicitante();
		if (dao.obtenerSolicitantes(txtDocumento.getText(), 3, "", "", 0).size() > 0) {
			txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
			txtDocumento.setFocus(true);
			return;
		}
		if (txtNombres.getText().length() <= 0) {
			txtNombres.setErrorMessage("Ingrese el nombre.");
			txtNombres.setFocus(true);
			return;
		}
		if (txtApellidos.getText().length() <= 0) {
			txtApellidos.setErrorMessage("Ingrese el apellido.");
			txtApellidos.setFocus(true);
			return;
		}
		if (cmbEmpresa.getSelectedItem() == null) {
			cmbEmpresa.setErrorMessage("Seleccione una empresa.");
			cmbEmpresa.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de guardar el solicitante?", ".:: Nuevo solicitante ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_solicitante dao = new dao_solicitante();
							modelo_solicitante solicitante = new modelo_solicitante();
							solicitante.setId_tip_documento(
									Long.parseLong(cmbTipoDocumento.getSelectedItem().getValue().toString()));
							solicitante
									.setId_empresa(Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()));
							solicitante.setNum_documento(txtDocumento.getText());
							solicitante.setNom_solicitante(txtNombres.getText());
							solicitante.setApe_solicitante(txtApellidos.getText());
							solicitante.setEst_solicitante("PAC");
							solicitante.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							solicitante.setFec_ingresa(timestamp);
							try {
								dao.insertarSolicitante(solicitante);
								Messagebox.show(
										"El solicitante se guardo correctamente. \n\n No olvide crear las relaciones para el solicitante.",
										".:: Nuevo solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el solicitante. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nuevo solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		txtDocumento.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		cmbTipoDocumento.setText("");
		cmbEmpresa.setText("");
	}

}
