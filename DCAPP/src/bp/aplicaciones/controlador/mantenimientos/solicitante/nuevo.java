package bp.aplicaciones.controlador.mantenimientos.solicitante;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_documento;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevoSolicitante;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtDocumento, txtNombres, txtApellidos;
	@Wire
	Combobox cmbEmpresa, cmbTipoDocumento;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

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

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtDocumento.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDocumento.setText(validar.soloNumeros(txtDocumento.getText().trim()));
			}
		});
		txtNombres.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombres.setText(txtNombres.getText().toUpperCase().trim());
			}
		});
		txtApellidos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtApellidos.setText(txtApellidos.getText().toUpperCase().trim());
			}
		});
		cargarEmpresas();
		cargarTipoDocumentos();
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_documento> obtenerTipoDocumentos() {
		return listaTipoDocumento;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa = consultasABaseDeDatos.consultarEmpresas(id_dc, id_mantenimiento, "", "", 0, 12);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_empresa bean = (modelo_empresa) o2;
				return (bean.getNom_empresa()).contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEmpresa), myComparator, 15);
		cmbEmpresa.setModel(mySubModel);
		ComboitemRenderer<modelo_empresa> myRenderer = new ComboitemRenderer<modelo_empresa>() {
			@Override
			public void render(Comboitem item, modelo_empresa bean, int index) throws Exception {
				item.setLabel(bean.getNom_empresa());
				item.setValue(bean);
			}
		};
		cmbEmpresa.setItemRenderer(myRenderer);
		binder.loadComponent(cmbEmpresa);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarTipoDocumentos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoDocumento = consultasABaseDeDatos.consultarTipoDocumentos(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_tipo_documento bean = (modelo_tipo_documento) o2;
				return (bean.getNom_tipo_documento()).contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaTipoDocumento), myComparator, 15);
		cmbTipoDocumento.setModel(mySubModel);
		ComboitemRenderer<modelo_tipo_documento> myRenderer = new ComboitemRenderer<modelo_tipo_documento>() {
			@Override
			public void render(Comboitem item, modelo_tipo_documento bean, int index) throws Exception {
				item.setLabel(bean.getNom_tipo_documento());
				item.setValue(bean);
			}
		};
		cmbTipoDocumento.setItemRenderer(myRenderer);
		binder.loadComponent(cmbTipoDocumento);
	}

	@SuppressWarnings("static-access")
	@Listen("onBlur=#txtDocumento")
	public void onBlur$txtDocumento()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtDocumento.getText().length() <= 0) {
			return;
		}
		if (cmbTipoDocumento.getSelectedItem() != null) {
			modelo_tipo_documento tipo_documento = (modelo_tipo_documento) cmbTipoDocumento.getSelectedItem()
					.getValue();
			if (tipo_documento.getId_tipo_documento() == 2) {
				if (validar.validarCedula(txtDocumento.getText()) == 0) {
					txtDocumento.setFocus(true);
					Clients.showNotification("El número de documento es incorrecto.", Clients.NOTIFICATION_TYPE_WARNING,
							dSolicitudes, "top_right", 2000, true);
					return;
				}
			}
		}
		dao_solicitante dao = new dao_solicitante();
		if (dao.consultarSolicitantes(0, 0, txtDocumento.getText(), "", 0, 3).size() > 0) {
			txtDocumento.setFocus(true);
			Clients.showNotification("El número de documento ya se encuentra registrado.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoDocumento.getSelectedItem() == null) {
			Clients.showNotification("Seleccione el tipo de documento.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			cmbTipoDocumento.setFocus(true);
			return;
		}
		if (txtDocumento.getText().length() <= 0) {
			Clients.showNotification("Seleccione el número de documento.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			txtDocumento.setFocus(true);
			return;
		}
		if (cmbTipoDocumento.getSelectedItem() != null) {
			modelo_tipo_documento tipo_documento = (modelo_tipo_documento) cmbTipoDocumento.getSelectedItem()
					.getValue();
			if (tipo_documento.getId_tipo_documento() == 2) {
				if (validar.validarCedula(txtDocumento.getText()) == 0) {
					txtDocumento.setFocus(true);
					Clients.showNotification("El número de documento es incorrecto.", Clients.NOTIFICATION_TYPE_WARNING,
							dSolicitudes, "top_right", 2000, true);
					return;
				}
			}
		}
		dao_solicitante dao = new dao_solicitante();
		if (dao.consultarSolicitantes(0, 0, txtDocumento.getText(), "", 0, 3).size() > 0) {
			txtDocumento.setFocus(true);
			Clients.showNotification("El número de documento ya se encuentra registrado.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (txtNombres.getText().length() <= 0) {
			Clients.showNotification("Ingrese el/los nombre(s).", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			txtNombres.setFocus(true);
			return;
		}
		if (txtApellidos.getText().length() <= 0) {
			Clients.showNotification("Ingrese el/los apellido(s).", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			txtApellidos.setFocus(true);
			return;
		}
		if (cmbEmpresa.getSelectedItem() == null) {
			Clients.showNotification("Seleccione la empresa.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
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
							modelo_tipo_documento tipo_documento = (modelo_tipo_documento) cmbTipoDocumento
									.getSelectedItem().getValue();
							modelo_empresa empresa = (modelo_empresa) cmbEmpresa.getSelectedItem().getValue();
							solicitante.setTipo_documento(tipo_documento);
							solicitante.setNum_documento(txtDocumento.getText());
							solicitante.setNom_solicitante(txtNombres.getText());
							solicitante.setApe_solicitante(txtApellidos.getText());
							solicitante.setEmpresa(empresa);
							solicitante.setEst_solicitante("AE");
							solicitante.setUsu_ingresa(user);
							solicitante.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.insertarSolicitante(solicitante);
								Clients.showNotification("El solicitante se guardó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "top_right", 4000);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al guardar el solicitante. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});

	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevoSolicitante));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtDocumento.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		cmbTipoDocumento.setText("");
		cmbEmpresa.setText("");
	}

}
