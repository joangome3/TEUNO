package bp.aplicaciones.controlador.mantenimientos.parametros;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_tarea_periodica;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_servicio;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_tarea;
import bp.aplicaciones.mantenimientos.DAO.dao_turno;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.DAO.dao_dia;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_4;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tarea_periodica;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_dia;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_4;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros4 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbDia;
	@Wire
	Listbox lbxTareas;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_dia> listaDia = new ArrayList<modelo_dia>();
	List<modelo_tarea_periodica> listaTarea = new ArrayList<modelo_tarea_periodica>();
	List<modelo_parametros_generales_4> listaRelacionTarea = new ArrayList<modelo_parametros_generales_4>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
	List<modelo_estado_bitacora> listaEstadoTareaPeriodica = new ArrayList<modelo_estado_bitacora>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_empresa> listaCliente1 = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaCliente2 = new ArrayList<modelo_empresa>();

	long id_dia = 10;
	long id_opcion = 3;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfil();
		inicializarPermisos();
		if (insertar.equals("S")) {
			btnGrabar.setDisabled(false);
			btnGrabar.setVisible(true);
		} else {
			btnGrabar.setDisabled(true);
			btnGrabar.setVisible(false);
		}
		cargarDias();
		cargarClientes1("");
		cargarUsuarios();
		cargarTipoServicios("");
		cargarTipoTareas("");
		cargarEstadoTareaPeriodicas("");
		cargarTurnos();
		cargarTareas("", 1, 0);
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

	public void cargarDias() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_dia dao = new dao_dia();
		String criterio = "";
		try {
			listaDia = dao.obtenerDias(criterio);
			binder.loadComponent(cmbDia);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los dias. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar dia ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTareas(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tarea_periodica dao = new dao_tarea_periodica();
		try {
			listaTarea = dao.obtenerTareasPeriodicas(criterio, tipo, 0, null, "", 0);
			borrarListaConsulta();
			dibujarListaConsulta();
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las tareas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tarea ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTurnos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_turno dao = new dao_turno();
		String criterio = "";
		try {
			listaTurno = dao.obtenerTurnos(criterio);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los turnos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar turnos ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		String criterio = "";
		try {
			listaUsuario = dao.obtenerUsuarios(criterio, 1, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los usuarios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarClientes1(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
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
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoTareas(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_tarea dao = new dao_tipo_tarea();
		try {
			listaTipoTarea = dao.obtenerTipoTareas(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de sistemas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstadoTareaPeriodicas(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_bitacora dao = new dao_estado_bitacora();
		try {
			listaEstadoTareaPeriodica = dao.obtenerEstados(criterio, 1, "");
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de mantenimientos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoServicios(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		try {
			listaTipoServicio = dao.obtenerTipoServicios(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las tipo_servicios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo_servicios ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public List<modelo_dia> obtenerDias() {
		return listaDia;
	}

	public List<modelo_tarea_periodica> obtenerTareas() {
		return listaTarea;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_empresa> obtenerClientes1() {
		return listaCliente1;
	}

	public List<modelo_empresa> obtenerClientes2() {
		return listaCliente2;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicio() {
		return listaTipoServicio;
	}

	public List<modelo_tipo_tarea> obtenerTipoTarea() {
		return listaTipoTarea;
	}

	@Listen("onSelect=#cmbDia")
	public void onSelect$cmbDia()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbDia.getSelectedItem() == null) {
			return;
		}
		borrarListaConsulta();
		dibujarListaConsulta();
		dao_parametros_generales_4 dao = new dao_parametros_generales_4();
		long id_tarea = 0;
		long id_dia = Long.parseLong(cmbDia.getSelectedItem().getValue().toString());
		for (int i = 0; i < lbxTareas.getItems().size(); i++) {
			id_tarea = listaTarea.get(lbxTareas.getItemAtIndex(i).getIndex()).getId_tarea_periodica();
			listaRelacionTarea = dao.obtenerRelacionesDias(String.valueOf(id_dia), String.valueOf(id_tarea), id_dc, 1);
			if (listaRelacionTarea.size() > 0) {
				lbxTareas.getItemAtIndex(i).setSelected(true);
			} else {
				lbxTareas.getItemAtIndex(i).setSelected(false);
			}
		}
	}

	public void borrarListaConsulta() {
		lbxTareas.clearSelection();
		Listitem lItem;
		for (int i = lbxTareas.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxTareas.getItemAtIndex(i);
			lbxTareas.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxTareas);
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaTarea.size(); i++) {
			lItem = new Listitem();
			Datebox dtxFechaInicio, dtxFechaFin;
			Textbox txtDescripcion;
			Combobox cmbcliente, cmbTipoServicio, cmbTipoTarea, cmbEstadoTareaPeriodica, cmbCumplimiento;
			Comboitem cItem;
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaTarea.get(i).getId_tarea_periodica()));
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* CLIENTE */
			lCell = new Listcell();
			cmbcliente = new Combobox();
			for (int j = 0; j < listaCliente2.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaCliente2.get(j).getNom_empresa());
				cItem.setValue(listaCliente2.get(j).getId_empresa());
				cItem.setTooltiptext(listaCliente2.get(j).getNom_empresa());
				cmbcliente.appendChild(cItem);
			}
			cmbcliente.setReadonly(false);
			cmbcliente.setWidth("250px");
			cmbcliente.setInplace(true);
			cmbcliente.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbcliente.getText().toString().toUpperCase();
					cargarClientes2(criterio, 8);
					Comboitem lItem;
					for (int i = cmbcliente.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbcliente.getItemAtIndex(i);
						cmbcliente.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaCliente2.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaCliente2.get(i).getNom_empresa());
						lItem.setValue(listaCliente2.get(i).getId_empresa());
						lItem.setTooltiptext(listaCliente2.get(i).getNom_empresa());
						cmbcliente.appendChild(lItem);
					}
					binder.loadComponent(cmbcliente);
				}
			});
			cmbcliente.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbcliente.getSelectedItem() == null) {
						cmbcliente.setTooltiptext("");
					} else if (cmbcliente.getText().length() <= 0) {
						cmbcliente.setTooltiptext("");
					} else {
						cmbcliente.setTooltiptext(cmbcliente.getSelectedItem().getLabel());
					}
				}
			});
			cargarClientes2("", 9);
			for (int j = 0; j < listaCliente2.size(); j++) {
				if (listaCliente2.get(j).getId_empresa() == listaTarea.get(i).getId_cliente()) {
					cmbcliente.setText(listaCliente2.get(j).getNom_empresa());
					j = listaCliente2.size() + 1;
				}
			}
			cmbcliente.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbcliente);
			lItem.appendChild(lCell);
			/* SERVICIO/CATEGORIA */
			lCell = new Listcell();
			cmbTipoServicio = new Combobox();
			for (int j = 0; j < listaTipoServicio.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoServicio.get(j).getNom_tipo_servicio());
				cItem.setValue(listaTipoServicio.get(j).getId_tipo_servicio());
				cItem.setTooltiptext(listaTipoServicio.get(j).getNom_tipo_servicio());
				cmbTipoServicio.appendChild(cItem);
			}
			cmbTipoServicio.setReadonly(false);
			cmbTipoServicio.setWidth("200px");
			cmbTipoServicio.setInplace(true);
			cmbTipoServicio.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbTipoServicio.getText().toString().toUpperCase();
					cargarTipoServicios(criterio);
					Comboitem lItem;
					for (int i = cmbTipoServicio.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbTipoServicio.getItemAtIndex(i);
						cmbTipoServicio.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaTipoServicio.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaTipoServicio.get(i).getNom_tipo_servicio());
						lItem.setValue(listaTipoServicio.get(i).getId_tipo_servicio());
						lItem.setTooltiptext(listaTipoServicio.get(i).getNom_tipo_servicio());
						cmbTipoServicio.appendChild(lItem);
					}
					binder.loadComponent(cmbTipoServicio);
				}
			});
			cmbTipoServicio.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbTipoServicio.getSelectedItem() == null) {
						cmbTipoServicio.setTooltiptext("");
					} else if (cmbTipoServicio.getText().length() <= 0) {
						cmbTipoServicio.setTooltiptext("");
					} else {
						cmbTipoServicio.setTooltiptext(cmbTipoServicio.getSelectedItem().getLabel());
					}
				}
			});
			cargarTipoServicios("");
			for (int j = 0; j < listaTipoServicio.size(); j++) {
				if (listaTipoServicio.get(j).getId_tipo_servicio() == listaTarea.get(i).getId_tipo_servicio()) {
					cmbTipoServicio.setText(listaTipoServicio.get(j).getNom_tipo_servicio());
					j = listaTipoServicio.size() + 1;
				}
			}
			cmbTipoServicio.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoServicio);
			lItem.appendChild(lCell);
			/* TIPO TAREA */
			lCell = new Listcell();
			cmbTipoTarea = new Combobox();
			for (int j = 0; j < listaTipoTarea.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoTarea.get(j).getNom_tipo_tarea());
				cItem.setValue(listaTipoTarea.get(j).getId_tipo_tarea());
				cItem.setTooltiptext(listaTipoTarea.get(j).getNom_tipo_tarea());
				cmbTipoTarea.appendChild(cItem);
			}
			cmbTipoTarea.setReadonly(false);
			cmbTipoTarea.setWidth("200px");
			cmbTipoTarea.setInplace(true);
			cmbTipoTarea.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbTipoTarea.getText().toString().toUpperCase();
					cargarTipoTareas(criterio);
					Comboitem lItem;
					for (int i = cmbTipoTarea.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbTipoTarea.getItemAtIndex(i);
						cmbTipoTarea.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaTipoTarea.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaTipoTarea.get(i).getNom_tipo_tarea());
						lItem.setValue(listaTipoTarea.get(i).getId_tipo_tarea());
						lItem.setTooltiptext(listaTipoTarea.get(i).getNom_tipo_tarea());
						cmbTipoTarea.appendChild(lItem);
					}
					binder.loadComponent(cmbTipoTarea);
				}
			});
			cmbTipoTarea.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbTipoTarea.getSelectedItem() == null) {
						cmbTipoTarea.setTooltiptext("");
					} else if (cmbTipoTarea.getText().length() <= 0) {
						cmbTipoTarea.setTooltiptext("");
					} else {
						cmbTipoTarea.setTooltiptext(cmbTipoTarea.getSelectedItem().getLabel());
					}
				}
			});
			cargarTipoTareas("");
			for (int j = 0; j < listaTipoTarea.size(); j++) {
				if (listaTipoTarea.get(j).getId_tipo_tarea() == listaTarea.get(i).getId_tipo_tarea()) {
					cmbTipoTarea.setText(listaTipoTarea.get(j).getNom_tipo_tarea());
					j = listaTipoTarea.size() + 1;
				}
			}
			cmbTipoTarea.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoTarea);
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
			txtDescripcion.setText(String.valueOf(listaTarea.get(i).getDescripcion()));
			lCell.appendChild(txtDescripcion);
			lItem.appendChild(lCell);
			/* FECHA INICIO */
			lCell = new Listcell();
			dtxFechaInicio = new Datebox();
			dtxFechaInicio.setWidth("150px");
			dtxFechaInicio.setInplace(true);
			dtxFechaInicio.setFormat("dd/MM/yyyy HH:mm");
			dtxFechaInicio.setValue(listaTarea.get(i).getFec_inicio());
			lCell.appendChild(dtxFechaInicio);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* FECHA FIN */
			lCell = new Listcell();
			dtxFechaFin = new Datebox();
			dtxFechaFin.setWidth("150px");
			dtxFechaFin.setInplace(true);
			dtxFechaFin.setFormat("dd/MM/yyyy HH:mm");
			dtxFechaFin.setValue(listaTarea.get(i).getFec_fin());
			lCell.appendChild(dtxFechaFin);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ESTADO */
			lCell = new Listcell();
			cmbEstadoTareaPeriodica = new Combobox();
			for (int j = 0; j < listaEstadoTareaPeriodica.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaEstadoTareaPeriodica.get(j).getNom_estado());
				cItem.setValue(listaEstadoTareaPeriodica.get(j).getId_estado());
				cItem.setTooltiptext(listaEstadoTareaPeriodica.get(j).getNom_estado());
				cmbEstadoTareaPeriodica.appendChild(cItem);
			}
			cmbEstadoTareaPeriodica.setReadonly(false);
			cmbEstadoTareaPeriodica.setWidth("150px");
			cmbEstadoTareaPeriodica.setInplace(true);
			cmbEstadoTareaPeriodica.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbEstadoTareaPeriodica.getText().toString().toUpperCase();
					cargarEstadoTareaPeriodicas(criterio);
					Comboitem lItem;
					for (int i = cmbEstadoTareaPeriodica.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbEstadoTareaPeriodica.getItemAtIndex(i);
						cmbEstadoTareaPeriodica.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaEstadoTareaPeriodica.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaEstadoTareaPeriodica.get(i).getNom_estado());
						lItem.setValue(listaEstadoTareaPeriodica.get(i).getId_estado());
						lItem.setTooltiptext(listaEstadoTareaPeriodica.get(i).getNom_estado());
						cmbEstadoTareaPeriodica.appendChild(lItem);
					}
					binder.loadComponent(cmbEstadoTareaPeriodica);
				}
			});
			cmbEstadoTareaPeriodica.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbEstadoTareaPeriodica.getSelectedItem() == null) {
						cmbEstadoTareaPeriodica.setTooltiptext("");
					} else if (cmbEstadoTareaPeriodica.getText().length() <= 0) {
						cmbEstadoTareaPeriodica.setTooltiptext("");
					} else {
						cmbEstadoTareaPeriodica.setTooltiptext(cmbEstadoTareaPeriodica.getSelectedItem().getLabel());
					}
				}
			});
			cargarEstadoTareaPeriodicas("");
			for (int j = 0; j < listaEstadoTareaPeriodica.size(); j++) {
				if (listaEstadoTareaPeriodica.get(j).getId_estado() == listaTarea.get(i).getId_estado_bitacora()) {
					cmbEstadoTareaPeriodica.setText(listaEstadoTareaPeriodica.get(j).getNom_estado());
					j = listaEstadoTareaPeriodica.size() + 1;
				}
			}
			cmbEstadoTareaPeriodica.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbEstadoTareaPeriodica);
			lItem.appendChild(lCell);
			/* CUMPLIMIENTO */
			lCell = new Listcell();
			cmbCumplimiento = new Combobox();
			cmbCumplimiento.setWidth("150px");
			cmbCumplimiento.setInplace(true);
			cItem = new Comboitem();
			cItem.setLabel("CUMPLIDO");
			cItem.setValue("C");
			cmbCumplimiento.appendChild(cItem);
			cItem = new Comboitem();
			cItem.setLabel("INCUMPLIDO");
			cItem.setValue("I");
			cmbCumplimiento.appendChild(cItem);
			cmbCumplimiento.setText(listaTarea.get(i).verCumplimiento());
			cmbCumplimiento.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbCumplimiento);
			lItem.appendChild(lCell);
			/* TURNO */
			lCell = new Listcell();
			for (int j = 0; j < listaTurno.size(); j++) {
				if (listaTurno.get(j).getId_turno() == listaTarea.get(i).getId_turno()) {
					lCell.setLabel(listaTurno.get(j).getNom_turno());
					j = listaTurno.size() + 1;
				}
			}
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxTareas.appendChild(lItem);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbDia.getSelectedItem() == null) {
			cmbDia.setErrorMessage("Seleccione un dia.");
			cmbDia.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #4 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_dia = Long.valueOf(cmbDia.getSelectedItem().getValue().toString());
							long id_tarea = 0;
							dao_parametros_generales_4 dao = new dao_parametros_generales_4();
							modelo_parametros_generales_4 relacion;
							List<modelo_parametros_generales_4> listaRelacion = new ArrayList<modelo_parametros_generales_4>();
							for (int i = 0; i < lbxTareas.getItems().size(); i++) {
								id_tarea = listaTarea.get(lbxTareas.getItemAtIndex(i).getIndex())
										.getId_tarea_periodica();
								if (lbxTareas.getItemAtIndex(i).isSelected()) {
									relacion = new modelo_parametros_generales_4();
									relacion.setId_tarea_periodica(id_tarea);
									relacion.setId_localidad(id_dc);
									relacion.setId_dia(id_dia);
									relacion.setEst_relacion("A");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(timestamp);
									listaRelacion.add(relacion);
								}
							}
							try {
								dao.insertarRelacion(listaRelacion, id_dia, id_dc);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #4 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #4 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
