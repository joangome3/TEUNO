package bp.aplicaciones.controlador.controlcambio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.controlcambio.DAO.dao_control_cambio;
import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.DAO.dao_criticidad;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_infraestructura;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_5;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_6;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_sistema;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_criticidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_infraestructura;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_6;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_sistema;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxControlCambios;
	@Wire
	Combobox cmbLimite, cmbCliente;
	@Wire
	Div winList;

	Window window;
	int counter = 0;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_control_cambio> listaControlCambio = new ArrayList<modelo_control_cambio>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_empresa> listaCliente1 = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaCliente2 = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaProveedor = new ArrayList<modelo_empresa>();
	List<modelo_infraestructura> listaInfraestructura = new ArrayList<modelo_infraestructura>();
	List<modelo_tipo_sistema> listaTipoSistema = new ArrayList<modelo_tipo_sistema>();
	List<modelo_tipo_mantenimiento> listaTipoMantenimiento = new ArrayList<modelo_tipo_mantenimiento>();
	List<modelo_criticidad> listaCriticidad = new ArrayList<modelo_criticidad>();
	List<modelo_solicitante> listaResponsable = new ArrayList<modelo_solicitante>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_5> listaParametros5 = new ArrayList<modelo_parametros_generales_5>();
	List<modelo_parametros_generales_6> listaParametros6 = new ArrayList<modelo_parametros_generales_6>();

	long id_opcion = 2;

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarParametros1();
		cargarParametros5();
		cargarUsuarios();
		cargarClientes1();
		cargarClientes2("", 6);
		if (cmbCliente.getItems().size() > 0) {
			cmbCliente.setSelectedIndex(1);
		}
		cargarProveedores("", 2);
		cargarResponsables("", 6);
		cargarCriticidades("");
		cargarInfraestructuras("", 1, 0);
		cargarTipoSistemas("");
		cargarTipoMantenimientos("");
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarControlCambios();
			txtBuscar.setDisabled(false);
			lbxControlCambios.setEmptyMessage("!No existen datos que mostrar¡.");
		} else {
			txtBuscar.setDisabled(true);
			lbxControlCambios.setEmptyMessage("!No tiene permiso para ver los registros¡.");
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
			btnNuevo.setVisible(true);
		} else {
			btnNuevo.setDisabled(true);
			btnNuevo.setVisible(false);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getLocalidad_control_cambio() == 0) {
				btnNuevo.setDisabled(true);
				btnRefrescar.setDisabled(true);
				txtBuscar.setDisabled(true);
				cmbLimite.setDisabled(true);
				Messagebox.show("No se ha definido la localidad para el uso de la aplicación de control de cambios.",
						".:: Control de cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_parametros_generales_5> obtenerParametros5() {
		return listaParametros5;
	}

	public List<modelo_parametros_generales_6> obtenerParametros6() {
		return listaParametros6;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_control_cambio> obtenerControlCambios() {
		return listaControlCambio;
	}

	public List<modelo_empresa> obtenerClientes1() {
		return listaCliente1;
	}

	public List<modelo_empresa> obtenerClientes2() {
		return listaCliente2;
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

	public void cargarParametros5() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_5 dao = new dao_parametros_generales_5();
		try {
			listaParametros5 = dao.obtenerRelacionesOpciones("", String.valueOf(id_opcion), 2);
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

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		listaUsuario = dao.consultarUsuarios(id_dc, 0, "", "", 0, 2);
	}

	public void cargarControlCambios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_control_cambio dao = new dao_control_cambio();
		String criterio = "";
		try {
			listaControlCambio = dao.obtenerControlCambios(criterio, 1, 0, id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
			dibujarListaConsulta();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los controles de cambio. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Control de cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarClientes1() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaCliente1 = dao.obtenerEmpresas(criterio, 0, String.valueOf(id_dc), "", 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarClientes2(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		try {
			listaCliente2 = dao.obtenerEmpresas(criterio, tipo, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
			binder.loadComponent(cmbCliente);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarProveedores(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		try {
			listaProveedor = dao.obtenerEmpresas(criterio, tipo, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar proveedores ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoSistemas(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_sistema dao = new dao_tipo_sistema();
		try {
			listaTipoSistema = dao.obtenerTipoSistemas(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de sistemas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoMantenimientos(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_mantenimiento dao = new dao_tipo_mantenimiento();
		try {
			listaTipoMantenimiento = dao.obtenerTipoMantenimientos(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de mantenimientos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarResponsables(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaResponsable = consultasABaseDeDatos.consultarSolicitantes(id_opcion, id_dc, criterio.toUpperCase().trim(),
				"", 0, 7);
	}

	public void cargarCriticidades(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_criticidad dao = new dao_criticidad();
		try {
			listaCriticidad = dao.obtenerCriticidades(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las criticidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar criticidades ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarInfraestructuras(String criterio, int tipo, long id_tipo_sistema)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_infraestructura dao = new dao_infraestructura();
		try {
			listaInfraestructura = dao.obtenerInfraestructuras(criterio, tipo, id_tipo_sistema, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las infraestructuras. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar infraestructuras ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisos() {
		if (listaPerfil.size() == 1) {
			if (listaPerfil.get(0).getConsultar().equals("S")) {
				consultar = "S";
			} else {
				consultar = "N";
			}
			if (listaPerfil.get(0).getInsertar().equals("S")) {
				insertar = "S";
			} else {
				insertar = "N";
			}
			if (listaPerfil.get(0).getModificar().equals("S")) {
				modificar = "S";
			} else {
				modificar = "N";
			}
			if (listaPerfil.get(0).getRelacionar().equals("S")) {
				relacionar = "S";
			} else {
				relacionar = "N";
			}
			if (listaPerfil.get(0).getDesactivar().equals("S")) {
				desactivar = "S";
			} else {
				desactivar = "N";
			}
			if (listaPerfil.get(0).getEliminar().equals("S")) {
				eliminar = "S";
			} else {
				eliminar = "N";
			}
			if (listaPerfil.get(0).getSolicitar().equals("S")) {
				solicitar = "S";
			} else {
				solicitar = "N";
			}
			if (listaPerfil.get(0).getRevisar().equals("S")) {
				revisar = "S";
			} else {
				revisar = "N";
			}
			if (listaPerfil.get(0).getAprobar().equals("S")) {
				aprobar = "S";
			} else {
				aprobar = "N";
			}
			if (listaPerfil.get(0).getEjecutar().equals("S")) {
				ejecutar = "S";
			} else {
				ejecutar = "N";
			}
		} else {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaControlCambio.size(); i++) {
			lItem = new Listitem();
			Button btnGuardar, btnEliminar;
			Combobox cmbCliente, cmbTipoSistema, cmbInfraestructura, cmbProveedor, cmbTipoMantenimiento, cmbCriticidad,
					cmbResponsable;
			Datebox dtxFechaProgramada;
			Textbox txtDescripcion;
			Comboitem cItem;
			/* ACCION */
			lCell = new Listcell();
			btnGuardar = new Button();
			btnGuardar.setDisabled(false);
			btnGuardar.setImage("/img/botones/ButtonSave.png");
			btnGuardar.setTooltiptext("Grabar registro");
			btnGuardar.setAutodisable("false");
			btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					final long id;
					Combobox cmb1, cmb2, cmb3, cmb4, cmb5, cmb6, cmb7;
					Textbox txt1;
					Datebox dtx1;
					lItem = (Listitem) btnGuardar.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(1);
					id = Long.valueOf(lCell.getLabel().toString());
					lCell = (Listcell) lItem.getChildren().get(2);
					cmb1 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(3);
					cmb2 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(4);
					cmb3 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(5);
					dtx1 = (Datebox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(6);
					cmb4 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(7);
					cmb5 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(8);
					cmb6 = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(9);
					txt1 = (Textbox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(10);
					cmb7 = (Combobox) lCell.getChildren().get(0);
					if (cmb1.getSelectedItem() == null) {
						cmb1.setFocus(true);
						cmb1.setErrorMessage("Seleccione el cliente.");
						Messagebox.show("Seleccione el cliente para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb2.getSelectedItem() == null) {
						cmb2.setFocus(true);
						cmb2.setErrorMessage("Seleccione el sistema.");
						Messagebox.show("Seleccione el sistema para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb3.getSelectedItem() == null) {
						cmb3.setFocus(true);
						cmb3.setErrorMessage("Seleccione el componente de infraestructura.");
						Messagebox.show("Seleccione el componente de infraestructura para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (dtx1.getValue() == null) {
						dtx1.setFocus(true);
						dtx1.setErrorMessage("Indique la fecha programada.");
						Messagebox.show("Indique la fecha programada para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					LocalDateTime localDateTime1 = null;
					LocalDateTime localDateTime2 = null;
					LocalDate localDate1 = dtx1.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate localDate2 = new Date(System.currentTimeMillis()).toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
					int year1 = localDate1.getYear();
					int year2 = localDate2.getYear();
					localDateTime1 = LocalDateTime.of(year1, dtx1.getValue().getMonth() + 1, dtx1.getValue().getDate(),
							dtx1.getValue().getHours(), dtx1.getValue().getMinutes());
					localDateTime2 = LocalDateTime.of(year2, new Date(System.currentTimeMillis()).getMonth() + 1,
							new Date(System.currentTimeMillis()).getDate(),
							new Date(System.currentTimeMillis()).getHours(),
							new Date(System.currentTimeMillis()).getMinutes());
					Date d1 = null;
					Date d2 = null;
					d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
					d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
					if (d1.before(d2)) {
						dtx1.setFocus(true);
						dtx1.setErrorMessage("La fecha programada debe ser mayor a la fecha actual.");
						Messagebox.show(
								"La fecha programada debe ser mayor a la fecha actual para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb4.getSelectedItem() == null) {
						cmb4.setFocus(true);
						cmb4.setErrorMessage("Seleccione el proveedor.");
						Messagebox.show("Seleccione el proveedor para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb5.getSelectedItem() == null) {
						cmb5.setFocus(true);
						cmb5.setErrorMessage("Seleccione el tipo de mantenimiento.");
						Messagebox.show("Seleccione el tipo de mantenimiento para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb6.getSelectedItem() == null) {
						cmb6.setFocus(true);
						cmb6.setErrorMessage("Seleccione la criticidad.");
						Messagebox.show("Seleccione la criticidad para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (txt1.getValue() == null) {
						txt1.setFocus(true);
						txt1.setErrorMessage("Ingrese la descripción.");
						Messagebox.show("Ingrese la descripción para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmb7.getSelectedItem() == null) {
						cmb7.setFocus(true);
						cmb7.setErrorMessage("Seleccione el responsable.");
						Messagebox.show("Seleccione el responsable para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					Messagebox.show("Esta seguro de modificar el registro?", ".:: Modificar registro ::.",
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								@Override
								public void onEvent(Event event) throws Exception {
									if (event.getName().equals("onOK")) {
										dao_control_cambio dao = new dao_control_cambio();
										modelo_control_cambio control_cambio = new modelo_control_cambio();
										control_cambio.setId_control_cambio(id);
										control_cambio.setId_empresa_1(
												Long.valueOf(cmb1.getSelectedItem().getValue().toString()));
										control_cambio.setId_tipo_sistema(
												Long.valueOf(cmb2.getSelectedItem().getValue().toString()));
										control_cambio.setId_infraestructura(
												Long.valueOf(cmb3.getSelectedItem().getValue().toString()));
										java.util.Date date = null;
										Timestamp timestamp = null;
										date = dtx1.getValue();
										timestamp = new Timestamp(date.getTime());
										control_cambio.setFec_programada(timestamp);
										control_cambio.setId_empresa_2(
												Long.valueOf(cmb4.getSelectedItem().getValue().toString()));
										control_cambio.setId_tipo_mantenimiento(
												Long.valueOf(cmb5.getSelectedItem().getValue().toString()));
										control_cambio.setId_criticidad(
												Long.valueOf(cmb6.getSelectedItem().getValue().toString()));
										control_cambio.setDescripcion(txt1.getText().toUpperCase());
										control_cambio.setId_solicitante_1(
												Long.valueOf(cmb7.getSelectedItem().getValue().toString()));
										control_cambio.setId_localidad(id_dc);
										control_cambio.setEst_control_cambio("AE");
										control_cambio.setUsu_modifica(user);
										timestamp = new Timestamp(new Date().getTime());
										control_cambio.setFec_modifica(timestamp);
										try {
											dao.modificarControlCambio(control_cambio);
											Messagebox.show("El registro se modificó correctamente.",
													".:: Modificar registro ::.", Messagebox.OK,
													Messagebox.EXCLAMATION);
										} catch (Exception e) {
											Messagebox.show(
													"Error al modificar el registro. \n\n" + "Mensaje de error: \n\n"
															+ e.getMessage(),
													".:: Modificar registro ::.", Messagebox.OK,
													Messagebox.EXCLAMATION);
										}
									}
								}
							});
				}
			});
			lCell.appendChild(btnGuardar);
			Space sp = new Space();
			sp.setWidth("2px");
			lCell.appendChild(sp);
			btnEliminar = new Button();
			cargarParametros5();
			boolean existe_usuario = false;
			if (listaParametros5.size() > 0) {
				for (int j = 0; j < listaParametros5.size(); j++) {
					if (listaParametros5.get(j).getId_usuario() == id_user) {
						existe_usuario = true;
						j = listaParametros5.size() + 1;
					}
				}
			} else {
				btnEliminar.setDisabled(true);
			}
			if (existe_usuario == true) {
				btnEliminar.setDisabled(false);
			} else {
				btnEliminar.setDisabled(true);
			}
			btnEliminar.setImage("/img/botones/ButtonClose.png");
			btnEliminar.setTooltiptext("Eliminar registro");
			btnEliminar.setAutodisable("false");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public void onEvent(Event event) throws Exception {
					final long id;
					Listitem lItem;
					Listcell lCell;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(1);
					id = Long.valueOf(lCell.getLabel().toString());
					int index = lItem.getIndex();
					Messagebox.show("Esta seguro de eliminar el registro?", ".:: Eliminar registro ::.",
							Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								@Override
								public void onEvent(Event event) throws Exception {
									if (event.getName().equals("onOK")) {
										dao_control_cambio dao = new dao_control_cambio();
										try {
											dao.eliminarControlCambio(id);
											lbxControlCambios.removeItemAt(index);
											Messagebox.show("El registro se eliminó correctamente.",
													".:: Eliminar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
										} catch (Exception e) {
											Messagebox.show(
													"Error al eliminar el registro. \n\n" + "Mensaje de error: \n\n"
															+ e.getMessage(),
													".:: Eliminar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
										}
									}
								}
							});
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaControlCambio.get(i).getId_control_cambio()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* CLIENTE */
			lCell = new Listcell();
			cmbCliente = new Combobox();
			for (int j = 0; j < listaCliente2.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaCliente2.get(j).getNom_empresa());
				cItem.setValue(listaCliente2.get(j).getId_empresa());
				cItem.setTooltiptext(listaCliente2.get(j).getNom_empresa());
				cmbCliente.appendChild(cItem);
			}
			cmbCliente.setReadonly(false);
			cmbCliente.setWidth("250px");
			cmbCliente.setAutocomplete(false);
			cmbCliente.setInplace(true);
			cmbCliente.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbCliente.getText().toString().toUpperCase();
					cargarClientes2(criterio, 7);
					Comboitem lItem;
					for (int i = cmbCliente.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbCliente.getItemAtIndex(i);
						cmbCliente.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaCliente2.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaCliente2.get(i).getNom_empresa());
						lItem.setValue(listaCliente2.get(i).getId_empresa());
						lItem.setTooltiptext(listaCliente2.get(i).getNom_empresa());
						cmbCliente.appendChild(lItem);
					}
					binder.loadComponent(cmbCliente);
				}
			});
			cmbCliente.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbCliente.getSelectedItem() == null) {
						cmbCliente.setTooltiptext("");
					} else if (cmbCliente.getText().length() <= 0) {
						cmbCliente.setTooltiptext("");
					} else {
						cmbCliente.setTooltiptext(cmbCliente.getSelectedItem().getLabel());
					}
				}
			});
			cargarClientes2("", 6);
			for (int j = 0; j < listaCliente2.size(); j++) {
				if (listaCliente2.get(j).getId_empresa() == listaControlCambio.get(i).getId_empresa_1()) {
					cmbCliente.setText(listaCliente2.get(j).getNom_empresa());
					j = listaCliente2.size() + 1;
				}
			}
			cmbCliente.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbCliente);
			lItem.appendChild(lCell);
			/* SISTEMA */
			lCell = new Listcell();
			cmbTipoSistema = new Combobox();
			for (int j = 0; j < listaTipoSistema.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoSistema.get(j).getNom_tipo_sistema());
				cItem.setValue(listaTipoSistema.get(j).getId_tipo_sistema());
				cItem.setTooltiptext(listaTipoSistema.get(j).getNom_tipo_sistema());
				cmbTipoSistema.appendChild(cItem);
			}
			cmbTipoSistema.setReadonly(false);
			cmbTipoSistema.setWidth("250px");
			cmbTipoSistema.setAutocomplete(false);
			cmbTipoSistema.setInplace(true);
			cmbTipoSistema.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbTipoSistema.getText().toString().toUpperCase();
					Listitem lItem;
					Listcell lCell;
					Combobox cmb1;
					lItem = (Listitem) cmbTipoSistema.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(4);
					cmb1 = (Combobox) lCell.getChildren().get(0);
					cmb1.setText("");
					cmb1.setDisabled(true);
					cargarTipoSistemas(criterio);
					Comboitem cItem;
					for (int i = cmbTipoSistema.getItemCount() - 1; i >= 0; i--) {
						cItem = (Comboitem) cmbTipoSistema.getItemAtIndex(i);
						cmbTipoSistema.removeItemAt(cItem.getIndex());
					}
					for (int i = 0; i < listaTipoSistema.size(); i++) {
						cItem = new Comboitem();
						cItem.setLabel(listaTipoSistema.get(i).getNom_tipo_sistema());
						cItem.setValue(listaTipoSistema.get(i).getId_tipo_sistema());
						cItem.setTooltiptext(listaTipoSistema.get(i).getNom_tipo_sistema());
						cmbTipoSistema.appendChild(cItem);
					}
					binder.loadComponent(cmbTipoSistema);
				}
			});
			cmbTipoSistema.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					Combobox cmb1;
					lItem = (Listitem) cmbTipoSistema.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(4);
					cmb1 = (Combobox) lCell.getChildren().get(0);
					if (cmbTipoSistema.getSelectedItem() != null) {
						cargarInfraestructuras("", 6,
								Long.valueOf(cmbTipoSistema.getSelectedItem().getValue().toString()));
						cmb1.setText("");
						cmb1.setDisabled(false);
					} else {
						cmb1.setText("");
						cmb1.setDisabled(true);
					}
					Comboitem cItem;
					for (int i = cmb1.getItemCount() - 1; i >= 0; i--) {
						cItem = (Comboitem) cmb1.getItemAtIndex(i);
						cmb1.removeItemAt(cItem.getIndex());
					}
					for (int i = 0; i < listaInfraestructura.size(); i++) {
						cItem = new Comboitem();
						cItem.setLabel(listaInfraestructura.get(i).getNom_infraestructura());
						cItem.setValue(listaInfraestructura.get(i).getId_infraestructura());
						cItem.setTooltiptext(listaInfraestructura.get(i).getNom_infraestructura());
						cmb1.appendChild(cItem);
					}
					binder.loadComponent(cmb1);
					if (cmbTipoSistema.getSelectedItem() == null) {
						cmbTipoSistema.setTooltiptext("");
					} else if (cmbTipoSistema.getText().length() <= 0) {
						cmbTipoSistema.setTooltiptext("");
					} else {
						cmbTipoSistema.setTooltiptext(cmbTipoSistema.getSelectedItem().getLabel());
					}
				}
			});
			cargarTipoSistemas("");
			for (int j = 0; j < listaTipoSistema.size(); j++) {
				if (listaTipoSistema.get(j).getId_tipo_sistema() == listaControlCambio.get(i).getId_tipo_sistema()) {
					cmbTipoSistema.setText(listaTipoSistema.get(j).getNom_tipo_sistema());
					j = listaTipoSistema.size() + 1;
				}
			}
			cmbTipoSistema.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoSistema);
			lItem.appendChild(lCell);
			/* INFRAESTRUCTURA */
			lCell = new Listcell();
			cmbInfraestructura = new Combobox();
			cargarInfraestructuras("", 7, listaControlCambio.get(i).getId_tipo_sistema());
			for (int j = 0; j < listaInfraestructura.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaInfraestructura.get(j).getNom_infraestructura());
				cItem.setValue(listaInfraestructura.get(j).getId_infraestructura());
				cItem.setTooltiptext(listaInfraestructura.get(j).getNom_infraestructura());
				cmbInfraestructura.appendChild(cItem);
			}
			cmbInfraestructura.setReadonly(false);
			cmbInfraestructura.setWidth("250px");
			cmbInfraestructura.setAutocomplete(false);
			cmbInfraestructura.setInplace(true);
			cmbInfraestructura.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					final long id;
					Combobox cmb1;
					lItem = (Listitem) cmbInfraestructura.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(1);
					id = Long.valueOf(lCell.getLabel().toString());
					lCell = (Listcell) lItem.getChildren().get(3);
					cmb1 = (Combobox) lCell.getChildren().get(0);
					if (cmb1.getSelectedItem() == null) {
						cmb1.setErrorMessage("Seleccione el tipo de sistema.");
						cmb1.setFocus(true);
						cmbInfraestructura.setText("");
						cmbInfraestructura.setDisabled(true);
						Messagebox.show("Seleccione el tipo de sistema para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					String criterio = cmbInfraestructura.getText().toString().toUpperCase();
					cargarInfraestructuras(criterio, 7, Long.valueOf(cmb1.getSelectedItem().getValue().toString()));
					Comboitem cItem;
					for (int i = cmbInfraestructura.getItemCount() - 1; i >= 0; i--) {
						cItem = (Comboitem) cmbInfraestructura.getItemAtIndex(i);
						cmbInfraestructura.removeItemAt(cItem.getIndex());
					}
					for (int i = 0; i < listaInfraestructura.size(); i++) {
						cItem = new Comboitem();
						cItem.setLabel(listaInfraestructura.get(i).getNom_infraestructura());
						cItem.setValue(listaInfraestructura.get(i).getId_infraestructura());
						cItem.setTooltiptext(listaInfraestructura.get(i).getNom_infraestructura());
						cmbInfraestructura.appendChild(cItem);
					}
					binder.loadComponent(cmbInfraestructura);
				}
			});
			cmbInfraestructura.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					final long id;
					Combobox cmb1;
					lItem = (Listitem) cmbInfraestructura.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(1);
					id = Long.valueOf(lCell.getLabel().toString());
					lCell = (Listcell) lItem.getChildren().get(3);
					cmb1 = (Combobox) lCell.getChildren().get(0);
					if (cmb1.getSelectedItem() == null) {
						cmb1.setErrorMessage("Seleccione el tipo de sistema.");
						cmb1.setFocus(true);
						cmbInfraestructura.setText("");
						cmbInfraestructura.setDisabled(true);
						Messagebox.show("Seleccione el tipo de sistema para el registro con ID: " + id,
								".:: Modificar registro ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					if (cmbInfraestructura.getSelectedItem() == null) {
						cmbInfraestructura.setTooltiptext("");
					} else if (cmbInfraestructura.getText().length() <= 0) {
						cmbInfraestructura.setTooltiptext("");
					} else {
						cmbInfraestructura.setTooltiptext(cmbInfraestructura.getSelectedItem().getLabel());
					}
				}
			});
			cargarInfraestructuras("", 1, 0);
			for (int j = 0; j < listaInfraestructura.size(); j++) {
				if (listaInfraestructura.get(j).getId_infraestructura() == listaControlCambio.get(i)
						.getId_infraestructura()) {
					cmbInfraestructura.setText(listaInfraestructura.get(j).getNom_infraestructura());
					j = listaInfraestructura.size() + 1;
				}
			}
			cmbInfraestructura.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbInfraestructura);
			lItem.appendChild(lCell);
			/* FECHA PROGRAMADA */
			lCell = new Listcell();
			dtxFechaProgramada = new Datebox();
			dtxFechaProgramada.setWidth("150px");
			dtxFechaProgramada.setInplace(true);
			dtxFechaProgramada.setFormat("dd/MM/yyyy HH:mm");
			dtxFechaProgramada.setValue(listaControlCambio.get(i).getFec_programada());
			lCell.appendChild(dtxFechaProgramada);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* PROVEEDOR */
			lCell = new Listcell();
			cmbProveedor = new Combobox();
			for (int j = 0; j < listaProveedor.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaProveedor.get(j).getNom_empresa());
				cItem.setValue(listaProveedor.get(j).getId_empresa());
				cItem.setTooltiptext(listaProveedor.get(j).getNom_empresa());
				cmbProveedor.appendChild(cItem);
			}
			cmbProveedor.setReadonly(false);
			cmbProveedor.setWidth("250px");
			cmbProveedor.setAutocomplete(false);
			cmbProveedor.setInplace(true);
			cmbProveedor.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbProveedor.getText().toString().toUpperCase();
					cargarProveedores(criterio, 2);
					Comboitem lItem;
					for (int i = cmbProveedor.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbProveedor.getItemAtIndex(i);
						cmbProveedor.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaProveedor.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaProveedor.get(i).getNom_empresa());
						lItem.setValue(listaProveedor.get(i).getId_empresa());
						lItem.setTooltiptext(listaProveedor.get(i).getNom_empresa());
						cmbProveedor.appendChild(lItem);
					}
					binder.loadComponent(cmbProveedor);
				}
			});
			cmbProveedor.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbProveedor.getSelectedItem() == null) {
						cmbProveedor.setTooltiptext("");
					} else if (cmbProveedor.getText().length() <= 0) {
						cmbProveedor.setTooltiptext("");
					} else {
						cmbProveedor.setTooltiptext(cmbProveedor.getSelectedItem().getLabel());
					}
				}
			});
			cargarProveedores("", 2);
			for (int j = 0; j < listaProveedor.size(); j++) {
				if (listaProveedor.get(j).getId_empresa() == listaControlCambio.get(i).getId_empresa_2()) {
					cmbProveedor.setText(listaProveedor.get(j).getNom_empresa());
					j = listaProveedor.size() + 1;
				}
			}
			cmbProveedor.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbProveedor);
			lItem.appendChild(lCell);
			/* TIPO DE MANTENIMIENTO */
			lCell = new Listcell();
			cmbTipoMantenimiento = new Combobox();
			for (int j = 0; j < listaTipoMantenimiento.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoMantenimiento.get(j).getNom_tipo_mantenimiento());
				cItem.setValue(listaTipoMantenimiento.get(j).getId_tipo_mantenimiento());
				cItem.setTooltiptext(listaTipoMantenimiento.get(j).getNom_tipo_mantenimiento());
				cmbTipoMantenimiento.appendChild(cItem);
			}
			cmbTipoMantenimiento.setReadonly(false);
			cmbTipoMantenimiento.setWidth("250px");
			cmbTipoMantenimiento.setAutocomplete(false);
			cmbTipoMantenimiento.setInplace(true);
			cmbTipoMantenimiento.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbTipoMantenimiento.getText().toString().toUpperCase();
					cargarTipoMantenimientos(criterio);
					Comboitem lItem;
					for (int i = cmbTipoMantenimiento.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbTipoMantenimiento.getItemAtIndex(i);
						cmbTipoMantenimiento.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaTipoMantenimiento.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaTipoMantenimiento.get(i).getNom_tipo_mantenimiento());
						lItem.setValue(listaTipoMantenimiento.get(i).getId_tipo_mantenimiento());
						lItem.setTooltiptext(listaTipoMantenimiento.get(i).getNom_tipo_mantenimiento());
						cmbTipoMantenimiento.appendChild(lItem);
					}
					binder.loadComponent(cmbTipoMantenimiento);
				}
			});
			cmbTipoMantenimiento.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbTipoMantenimiento.getSelectedItem() == null) {
						cmbTipoMantenimiento.setTooltiptext("");
					} else if (cmbTipoMantenimiento.getText().length() <= 0) {
						cmbTipoMantenimiento.setTooltiptext("");
					} else {
						cmbTipoMantenimiento.setTooltiptext(cmbTipoMantenimiento.getSelectedItem().getLabel());
					}
				}
			});
			cargarTipoMantenimientos("");
			for (int j = 0; j < listaTipoMantenimiento.size(); j++) {
				if (listaTipoMantenimiento.get(j).getId_tipo_mantenimiento() == listaControlCambio.get(i)
						.getId_tipo_mantenimiento()) {
					cmbTipoMantenimiento.setText(listaTipoMantenimiento.get(j).getNom_tipo_mantenimiento());
					j = listaTipoMantenimiento.size() + 1;
				}
			}
			cmbTipoMantenimiento.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoMantenimiento);
			lItem.appendChild(lCell);
			/* CRITICIDAD */
			lCell = new Listcell();
			cmbCriticidad = new Combobox();
			for (int j = 0; j < listaCriticidad.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaCriticidad.get(j).getNom_criticidad());
				cItem.setValue(listaCriticidad.get(j).getId_criticidad());
				cItem.setTooltiptext(listaCriticidad.get(j).getNom_criticidad());
				cmbCriticidad.appendChild(cItem);
			}
			cmbCriticidad.setReadonly(false);
			cmbCriticidad.setWidth("150px");
			cmbCriticidad.setAutocomplete(false);
			cmbCriticidad.setInplace(true);
			cmbCriticidad.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbCriticidad.getText().toString().toUpperCase();
					cargarCriticidades(criterio);
					Comboitem lItem;
					for (int i = cmbCriticidad.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbCriticidad.getItemAtIndex(i);
						cmbCriticidad.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaCriticidad.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaCriticidad.get(i).getNom_criticidad());
						lItem.setValue(listaCriticidad.get(i).getId_criticidad());
						lItem.setTooltiptext(listaCriticidad.get(i).getNom_criticidad());
						cmbCriticidad.appendChild(lItem);
					}
					binder.loadComponent(cmbCriticidad);
				}
			});
			cmbCriticidad.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbCriticidad.getSelectedItem() == null) {
						cmbCriticidad.setTooltiptext("");
					} else if (cmbCriticidad.getText().length() <= 0) {
						cmbCriticidad.setTooltiptext("");
					} else {
						cmbCriticidad.setTooltiptext(cmbCriticidad.getSelectedItem().getLabel());
					}
				}
			});
			cargarCriticidades("");
			for (int j = 0; j < listaCriticidad.size(); j++) {
				if (listaCriticidad.get(j).getId_criticidad() == listaControlCambio.get(i).getId_criticidad()) {
					cmbCriticidad.setText(listaCriticidad.get(j).getNom_criticidad());
					j = listaCriticidad.size() + 1;
				}
			}
			cmbCriticidad.setStyle("font-style: italic !important;");
			long id_criticidad = 0;
			id_criticidad = listaControlCambio.get(i).getId_criticidad();
			String estilo = "";
			cargarParametros6(id_criticidad);
			if (listaParametros6.size() > 0) {
				estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
						+ listaParametros6.get(0).getColor() + " !important; text-align: center !important;";
				lCell.setStyle(estilo);
			} else {
				lCell.setStyle(
						"font-weight: bold !important; font-style: italic !important; text-align: center !important;");
			}
			lCell.appendChild(cmbCriticidad);
			lItem.appendChild(lCell);
			/* DESCRIPCION */
			lCell = new Listcell();
			txtDescripcion = new Textbox();
			txtDescripcion.setWidth("180px");
			txtDescripcion.setMaxlength(2000);
			txtDescripcion.setRows(5);
			txtDescripcion.setStyle("resize: none;");
			txtDescripcion.setInplace(true);
			txtDescripcion.setReadonly(false);
			txtDescripcion.setText(String.valueOf(listaControlCambio.get(i).getDescripcion()));
			lCell.appendChild(txtDescripcion);
			lItem.appendChild(lCell);
			/* RESPONSABLE */
			lCell = new Listcell();
			cmbResponsable = new Combobox();
			for (int j = 0; j < listaResponsable.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaResponsable.get(j).toStringSolicitante());
				cItem.setValue(listaResponsable.get(j).getId_solicitante());
				cItem.setTooltiptext(listaResponsable.get(j).toStringSolicitante());
				cmbResponsable.appendChild(cItem);
			}
			cmbResponsable.setReadonly(false);
			cmbResponsable.setWidth("250px");
			cmbResponsable.setAutocomplete(false);
			cmbResponsable.setInplace(true);
			cmbResponsable.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbResponsable.getText().toString().toUpperCase();
					cargarResponsables(criterio, 7);
					Comboitem lItem;
					for (int i = cmbResponsable.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbResponsable.getItemAtIndex(i);
						cmbResponsable.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaResponsable.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaResponsable.get(i).toStringSolicitante());
						lItem.setValue(listaResponsable.get(i).getId_solicitante());
						lItem.setTooltiptext(listaResponsable.get(i).toStringSolicitante());
						cmbResponsable.appendChild(lItem);
					}
					binder.loadComponent(cmbResponsable);
				}
			});
			cmbResponsable.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbResponsable.getSelectedItem() == null) {
						cmbResponsable.setTooltiptext("");
					} else if (cmbResponsable.getText().length() <= 0) {
						cmbResponsable.setTooltiptext("");
					} else {
						cmbResponsable.setTooltiptext(cmbResponsable.getSelectedItem().getLabel());
					}
				}
			});
			cargarResponsables("", 6);
			for (int j = 0; j < listaResponsable.size(); j++) {
				if (listaResponsable.get(j).getId_solicitante() == listaControlCambio.get(i).getId_solicitante_1()) {
					cmbResponsable.setText(listaResponsable.get(j).toStringSolicitante());
					j = listaResponsable.size() + 1;
				}
			}
			cmbResponsable.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbResponsable);
			lItem.appendChild(lCell);
			/* OPERADOR REGISTRA */
			lCell = new Listcell();
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaUsuario.get(j).getUse_usuario().equals(listaControlCambio.get(i).getUsu_ingresa())) {
					lCell.setLabel(listaUsuario.get(j).verNombreCompleto());
					j = listaUsuario.size() + 1;
				}
			}
			lItem.appendChild(lCell);
			/* OPERADOR MODIFICA */
			cargarUsuarios();
			int bandera = 0;
			String nom_usuario_modifica = "";
			lCell = new Listcell();
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaUsuario.get(j).getUse_usuario().equals(listaControlCambio.get(i).getUsu_modifica())) {
					bandera = 1;
					nom_usuario_modifica = listaUsuario.get(j).verNombreCompleto();
					j = listaUsuario.size() + 1;
				}
			}
			if (bandera == 1) {
				lCell.setLabel(nom_usuario_modifica);
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
				lCell.setLabel("");
				lCell.setStyle("text-align: center !important; color: transparent;");
			}
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxControlCambios.appendChild(lItem);
		}
	}

	public void borrarListaConsulta() {
		lbxControlCambios.clearSelection();
		Listitem lItem;
		for (int i = lbxControlCambios.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxControlCambios.getItemAtIndex(i);
			lbxControlCambios.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxControlCambios);
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			String criterio = txtBuscar.getText();
			long id = 0;
			if (cmbCliente.getSelectedItem() != null) {
				id = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
			}
			buscarControlCambios(criterio, 1, id);
		} catch (Exception e) {
			Messagebox.show(
					"Error al buscar en las control_cambios. \n\n" + "Codigo de error: "
							+ ((SQLException) e).getErrorCode() + "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() {
		try {
			String criterio = txtBuscar.getText();
			long id = 0;
			if (cmbCliente.getSelectedItem() != null) {
				id = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
			}
			buscarControlCambios(criterio, 1, id);
		} catch (Exception e) {
			Messagebox.show(
					"Error al buscar en las control_cambios. \n\n" + "Codigo de error: "
							+ ((SQLException) e).getErrorCode() + "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarControlCambios(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_control_cambio dao = new dao_control_cambio();
		if (txtBuscar.getText().length() <= 0) {
			try {
				listaControlCambio = dao.obtenerControlCambios(criterio, tipo, id, id_dc,
						Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show(
						"Error al buscar en las control_cambios. \n\n" + "Codigo de error: " + e.getErrorCode()
								+ "\n\nMensaje de error: \n\n" + e.getMessage(),
						".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			try {
				listaControlCambio = dao.obtenerControlCambios(criterio, tipo, id, id_dc,
						Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show(
						"Error al buscar en las control_cambios. \n\n" + "Codigo de error: " + e.getErrorCode()
								+ "\n\nMensaje de error: \n\n" + e.getMessage(),
						".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		btnNuevo.setDisabled(true);
		window = (Window) Executions.createComponents("/control_cambio/nuevo.zul", null, null);
		window.setId("Win" + (counter + 1));
		window.addEventListener(Events.ON_CLOSE, new EventListener() {
			public void onEvent(Event event) {
				btnNuevo.setDisabled(false);
				String id = "Btn" + event.getTarget().getId();
				if (winList.getFellowIfAny(id) != null) {
					winList.removeChild(winList.getFellow(id));
				}
			}
		});
		Button btn = new Button();
		btn.setImage("/img/botones/ButtonMaximize3.png");
		btn.setStyle("font-size: 12px !important;");
		btn.setId("BtnWin" + (counter + 1));
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event event) {
				String id = event.getTarget().getId().substring(3);
				if (winList.getFellowIfAny(id) != null) {
					if (((Window) winList.getFellowIfAny(id)).isMinimized()) {
						((Window) winList.getFellowIfAny(id)).setVisible(true);
					}
				}
			}
		});
		window.setParent(winList);
		btn.setParent(winList);
		counter++;
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			String criterio = txtBuscar.getText();
			long id = 0;
			if (cmbCliente.getSelectedItem() != null) {
				id = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
			}
			buscarControlCambios(criterio, 1, id);
		} catch (Exception e) {
			Messagebox.show(
					"Error al buscar en las control_cambios. \n\n" + "Codigo de error: "
							+ ((SQLException) e).getErrorCode() + "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			String criterio = txtBuscar.getText();
			buscarControlCambios(criterio, 1, 0);
		} catch (Exception e) {
			Messagebox.show(
					"Error al buscar en las control_cambios. \n\n" + "Codigo de error: "
							+ ((SQLException) e).getErrorCode() + "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Buscar control_cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		cmbCliente.setText("");
		txtBuscar.setText("");
		borrarListaConsulta();
	}

}
