package bp.aplicaciones.controlador.controlcambio;

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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.controlcambio.DAO.dao_control_cambio;
import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.DAO.dao_criticidad;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_infraestructura;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_6;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_sistema;
import bp.aplicaciones.mantenimientos.modelo.modelo_criticidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_infraestructura;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_6;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_sistema;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtDescripcion;
	@Wire
	Combobox cmbCliente, cmbTipoSistema, cmbInfraestructura, cmbProveedor, cmbTipoMantenimiento, cmbCriticidad,
			cmbResponsable;
	@Wire
	Datebox dtxFechaProgramada;
	@Wire
	Label lDescripcion;

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaProveedor = new ArrayList<modelo_empresa>();
	List<modelo_infraestructura> listaInfraestructura = new ArrayList<modelo_infraestructura>();
	List<modelo_tipo_sistema> listaTipoSistema = new ArrayList<modelo_tipo_sistema>();
	List<modelo_tipo_mantenimiento> listaTipoMantenimiento = new ArrayList<modelo_tipo_mantenimiento>();
	List<modelo_criticidad> listaCriticidad = new ArrayList<modelo_criticidad>();
	List<modelo_solicitante> listaResponsable = new ArrayList<modelo_solicitante>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_6> listaParametros6 = new ArrayList<modelo_parametros_generales_6>();

	long id = 0;
	long id_opcion = 2;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_control_cambio control_cambio = (modelo_control_cambio) Sessions.getCurrent().getAttribute("control_cambio");

	validar_datos validar = new validar_datos();
	
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
			}
		});
		cargarParametros1();
		cmbInfraestructura.setDisabled(true);
		cargarClientes();
		cargarProveedores();
		cargarResponsables();
		cargarCriticidades();
		cargarTipoSistemas();
		cargarTipoMantenimientos();
		cargarResponsables();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, IOException {
		for (int i = 0; i < listaCliente.size(); i++) {
			if (listaCliente.get(i).getId_empresa() == control_cambio.getId_empresa_1()) {
				cmbCliente.setText(listaCliente.get(i).getNom_empresa());
				i = listaCliente.size() + 1;
			}
		}
		binder.loadComponent(cmbCliente);
		for (int i = 0; i < listaTipoSistema.size(); i++) {
			if (listaTipoSistema.get(i).getId_tipo_sistema() == control_cambio.getId_tipo_sistema()) {
				cmbTipoSistema.setText(listaTipoSistema.get(i).getNom_tipo_sistema());
				i = listaTipoSistema.size() + 1;
			}
		}
		binder.loadComponent(cmbTipoSistema);
		cargarInfraestructuras(control_cambio.getId_tipo_sistema());
		for (int i = 0; i < listaInfraestructura.size(); i++) {
			if (listaInfraestructura.get(i).getId_infraestructura() == control_cambio.getId_infraestructura()) {
				cmbInfraestructura.setText(listaInfraestructura.get(i).getNom_infraestructura());
				i = listaInfraestructura.size() + 1;
			}
		}
		binder.loadComponent(cmbInfraestructura);
		cmbInfraestructura.setDisabled(false);
		for (int i = 0; i < listaProveedor.size(); i++) {
			if (listaProveedor.get(i).getId_empresa() == control_cambio.getId_empresa_2()) {
				cmbProveedor.setText(listaProveedor.get(i).getNom_empresa());
				i = listaProveedor.size() + 1;
			}
		}
		binder.loadComponent(cmbProveedor);
		for (int i = 0; i < listaTipoMantenimiento.size(); i++) {
			if (listaTipoMantenimiento.get(i).getId_tipo_mantenimiento() == control_cambio.getId_tipo_mantenimiento()) {
				cmbTipoMantenimiento.setText(listaTipoMantenimiento.get(i).getNom_tipo_mantenimiento());
				i = listaTipoMantenimiento.size() + 1;
			}
		}
		binder.loadComponent(cmbTipoMantenimiento);
		for (int i = 0; i < listaCriticidad.size(); i++) {
			if (listaCriticidad.get(i).getId_criticidad() == control_cambio.getId_criticidad()) {
				cmbCriticidad.setText(listaCriticidad.get(i).getNom_criticidad());
				i = listaCriticidad.size() + 1;
			}
		}
		binder.loadComponent(cmbCriticidad);
		for (int i = 0; i < listaResponsable.size(); i++) {
			if (listaResponsable.get(i).getId_solicitante() == control_cambio.getId_solicitante_1()) {
				cmbResponsable.setText(listaResponsable.get(i).toStringSolicitante());
				i = listaResponsable.size() + 1;
			}
		}
		binder.loadComponent(cmbResponsable);
		txtDescripcion.setText(control_cambio.getDescripcion());
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
		dtxFechaProgramada.setValue(control_cambio.getFec_programada());
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_parametros_generales_6> obtenerParametros6() {
		return listaParametros6;
	}

	public List<modelo_empresa> obtenerClientes() {
		return listaCliente;
	}

	public List<modelo_empresa> obtenerProveedores() {
		return listaProveedor;
	}

	public List<modelo_infraestructura> obtenerInfraestructura() {
		return listaInfraestructura;
	}

	public List<modelo_tipo_sistema> obtenerTipoSistema() {
		return listaTipoSistema;
	}

	public List<modelo_tipo_mantenimiento> obtenerTipoMantenimiento() {
		return listaTipoMantenimiento;
	}

	public List<modelo_criticidad> obtenerCriticidad() {
		return listaCriticidad;
	}

	public List<modelo_solicitante> obtenerResponsable() {
		return listaResponsable;
	}

	public void cargarParametros1() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros1 = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarParametros6(long id_criticidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_6 dao = new dao_parametros_generales_6();
		try {
			listaParametros6 = dao.obtenerRelacionesCriticidades(String.valueOf(id_criticidad), "", id_dc, 3);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarClientes() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaCliente = dao.obtenerEmpresas(criterio, 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
			binder.loadComponent(cmbCliente);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarProveedores() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaProveedor = dao.obtenerEmpresas(criterio, 2, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
			binder.loadComponent(cmbProveedor);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar proveedores ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoSistemas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_sistema dao = new dao_tipo_sistema();
		String criterio = "";
		try {
			listaTipoSistema = dao.obtenerTipoSistemas(criterio, 1, 0, 0);
			binder.loadComponent(cmbTipoSistema);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de sistemas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_mantenimiento dao = new dao_tipo_mantenimiento();
		String criterio = "";
		try {
			listaTipoMantenimiento = dao.obtenerTipoMantenimientos(criterio, 1, 0, 0);
			binder.loadComponent(cmbTipoMantenimiento);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de mantenimientos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarResponsables() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaResponsable = consultasABaseDeDatos.consultarSolicitantes(id_opcion, id_dc, "", "", 0, 6);
		binder.loadComponent(cmbResponsable);
	}

	public void cargarCriticidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_criticidad dao = new dao_criticidad();
		String criterio = "";
		try {
			listaCriticidad = dao.obtenerCriticidades(criterio, 1, 0, 0);
			binder.loadComponent(cmbCriticidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las criticidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar criticidades ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarInfraestructuras() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_infraestructura dao = new dao_infraestructura();
		String criterio = "";
		try {
			listaInfraestructura = dao.obtenerInfraestructuras(criterio, 6,
					Long.valueOf(cmbTipoSistema.getSelectedItem().getValue().toString()), 0);
			binder.loadComponent(cmbInfraestructura);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las infraestructuras. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar infraestructuras ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarInfraestructuras(long id_tipo_sistema)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_infraestructura dao = new dao_infraestructura();
		String criterio = "";
		try {
			listaInfraestructura = dao.obtenerInfraestructuras(criterio, 6, id_tipo_sistema, 0);
			binder.loadComponent(cmbInfraestructura);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las infraestructuras. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar infraestructuras ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_control_cambio dao = new dao_control_cambio();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbTipoSistema")
	public void onSelect$cmbTipoSistema()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbTipoSistema.getSelectedItem() == null) {
			return;
		}
		cargarInfraestructuras();
		if (listaInfraestructura.size() > 0) {
			cmbInfraestructura.setDisabled(false);
			cmbInfraestructura.setText("");
		} else {
			cmbInfraestructura.setDisabled(true);
			cmbInfraestructura.setText("");
		}
	}

	@Listen("onSelect=#cmbCriticidad")
	public void onSelect$cmbCriticidad()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbCriticidad.getSelectedItem() == null) {
			return;
		}
		long id_criticidad = 0;
		id_criticidad = Long.valueOf(cmbCriticidad.getSelectedItem().getValue().toString());
		String estilo = "";
		cargarParametros6(id_criticidad);
		Comboitem cmbi = new Comboitem();
		if (listaParametros6.size() > 0) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
					+ listaParametros6.get(0).getColor() + " !important;";
			cmbi = (Comboitem) cmbCriticidad.getChildren().get(cmbCriticidad.getSelectedIndex());
			cmbi.setStyle(estilo);
		} else {
			cmbCriticidad.setStyle("font-weight: bold !important; font-style: italic !important;");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbCliente.getSelectedItem() == null) {
			cmbCliente.setFocus(true);
			cmbCliente.setErrorMessage("Seleccione un cliente.");
			return;
		}
		if (cmbTipoSistema.getSelectedItem() == null) {
			cmbTipoSistema.setFocus(true);
			cmbTipoSistema.setErrorMessage("Seleccione un sistema.");
			return;
		}
		if (cmbInfraestructura.getSelectedItem() == null) {
			cmbInfraestructura.setFocus(true);
			cmbInfraestructura.setErrorMessage("Seleccione un componente de infraestructura.");
			return;
		}
		if (cmbProveedor.getSelectedItem() == null) {
			cmbProveedor.setFocus(true);
			cmbProveedor.setErrorMessage("Seleccione un proveedor.");
			return;
		}
		if (cmbTipoMantenimiento.getSelectedItem() == null) {
			cmbTipoMantenimiento.setFocus(true);
			cmbTipoMantenimiento.setErrorMessage("Seleccione un tipo de mantenimiento.");
			return;
		}
		if (cmbCriticidad.getSelectedItem() == null) {
			cmbCriticidad.setFocus(true);
			cmbCriticidad.setErrorMessage("Seleccione una criticidad.");
			return;
		}
		if (dtxFechaProgramada.getValue() == null) {
			dtxFechaProgramada.setFocus(true);
			dtxFechaProgramada.setErrorMessage("Indique la fecha programada.");
			return;
		}
		if (cmbResponsable.getSelectedItem() == null) {
			cmbResponsable.setFocus(true);
			cmbResponsable.setErrorMessage("Seleccione un responsable.");
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage("Ingrese la descripci?n.");
			return;
		}
		Messagebox.show("Esta seguro de guardar el control de cambio?", ".:: Modificar ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_control_cambio dao = new dao_control_cambio();
							modelo_control_cambio control_cambio = new modelo_control_cambio();
							control_cambio.setId_control_cambio(modificar.this.control_cambio.getId_control_cambio());
							control_cambio
									.setId_empresa_1(Long.valueOf(cmbCliente.getSelectedItem().getValue().toString()));
							control_cambio.setId_tipo_sistema(
									Long.valueOf(cmbTipoSistema.getSelectedItem().getValue().toString()));
							control_cambio.setId_infraestructura(
									Long.valueOf(cmbInfraestructura.getSelectedItem().getValue().toString()));
							control_cambio.setId_empresa_2(
									Long.valueOf(cmbProveedor.getSelectedItem().getValue().toString()));
							control_cambio.setId_tipo_mantenimiento(
									Long.valueOf(cmbTipoMantenimiento.getSelectedItem().getValue().toString()));
							control_cambio.setId_criticidad(
									Long.valueOf(cmbCriticidad.getSelectedItem().getValue().toString()));
							control_cambio.setId_solicitante_1(
									Long.valueOf(cmbResponsable.getSelectedItem().getValue().toString()));
							java.util.Date date = null;
							Timestamp timestamp = null;
							date = dtxFechaProgramada.getValue();
							timestamp = new Timestamp(date.getTime());
							control_cambio.setFec_programada(timestamp);
							control_cambio.setDescripcion(txtDescripcion.getText());
							control_cambio.setId_localidad(id_dc);
							control_cambio.setEst_control_cambio("A");
							control_cambio.setUsu_modifica(user);
							timestamp = new Timestamp(new Date().getTime());
							control_cambio.setFec_modifica(timestamp);
							try {
								dao.modificarControlCambio(control_cambio);
								Messagebox.show("El control de cambio se guard? correctamente.", ".:: Modificar ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el control de cambio. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		txtDescripcion.setText("");
		cmbCliente.setText("");
		cmbTipoSistema.setText("");
		cmbInfraestructura.setText("");
		cmbProveedor.setText("");
		cmbTipoMantenimiento.setText("");
		cmbCriticidad.setText("");
		cmbResponsable.setText("");
		dtxFechaProgramada.setValue(null);
		lDescripcion.setValue(txtDescripcion.getText().length() + "/" + txtDescripcion.getMaxlength());
	}

}
