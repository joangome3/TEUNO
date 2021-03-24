package bp.aplicaciones.controlador.bitacora.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_8;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_servicio;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_tarea;
import bp.aplicaciones.mantenimientos.DAO.dao_turno;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_8;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import tools.dynamia.zk.addons.chartjs.Chartjs;

@SuppressWarnings({ "serial", "deprecation" })
public class panel7 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zPastel;
	@Wire
	Chartjs cPastel;
	@Wire
	Timer timer;
	@Wire
	Label lDetalle;
	@Wire
	Listbox lbxTareasProveedores;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_tarea_proveedor> listaTareaProveedor1 = new ArrayList<modelo_tarea_proveedor>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_empresa> listaCliente1 = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaCliente2 = new ArrayList<modelo_empresa>();
	List<modelo_tipo_servicio> listaTipoServicio1 = new ArrayList<modelo_tipo_servicio>();
	List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
	List<modelo_estado_bitacora> listaEstadoBitacora = new ArrayList<modelo_estado_bitacora>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_turno> listaTurno1 = new ArrayList<modelo_turno>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_parametros_generales_8> listaParametros8 = new ArrayList<modelo_parametros_generales_8>();

	long id_opcion = 3;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxTareasProveedores.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarClientes1("");
		cargarClientes2("", 6);
		cargarEstadoBitacoras("");
		cargarTipoServicios1("");
		cargarParametros1();
		cargarTipoTareas("");
		cargarTurnos1();
		cargarUsuarios();
		cargarTareasProveedores(0);
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_tarea_proveedor> obtenerTareasProveedores() {
		return listaTareaProveedor1;
	}

	public List<modelo_empresa> obtenerClientes1() {
		return listaCliente1;
	}

	public List<modelo_empresa> obtenerClientes2() {
		return listaCliente2;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicio1() {
		return listaTipoServicio1;
	}

	public List<modelo_tipo_tarea> obtenerTipoTarea() {
		return listaTipoTarea;
	}

	public List<modelo_estado_bitacora> obtenerEstadoBitacora() {
		return listaEstadoBitacora;
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_parametros_generales_8> obtenerParametros8() {
		return listaParametros8;
	}

	public void cargarTurnos1() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_turno dao = new dao_turno();
		String criterio = "";
		try {
			listaTurno1 = dao.obtenerTurnos(criterio);
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

	public void cargarTareasProveedores(int dibujaLista)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		@SuppressWarnings("unused")
		dao_tarea_proveedor dao = new dao_tarea_proveedor();
		try {
			//listaTareaProveedor1 = dao.obtenerTareasProveedores("", 6, 0, "", "", id_dc, "", "", 0, 0);
			dibujarListaConsulta();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las tareas de proveedores. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Tarea de proveedores ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
					"Error al cargar los tipo de tareas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de tarea ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstadoBitacoras(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_bitacora dao = new dao_estado_bitacora();
		try {
			listaEstadoBitacora = dao.obtenerEstados(criterio, 1, "");
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los estados. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoServicios1(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		try {
			listaTipoServicio1 = dao.obtenerTipoServicios(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipo de servicios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de servicios ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public void cargarParametros8(long id_criticidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_8 dao = new dao_parametros_generales_8();
		try {
			listaParametros8 = dao.obtenerRelacionesEstados(String.valueOf(id_criticidad), "", id_dc, 3);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		int tamanio = 0;
		for (int i = 0; i < listaTareaProveedor1.size(); i++) {
			lItem = new Listitem();
			Datebox dtxFechaInicioInicio, dtxFechaInicioFin;
			Textbox txtTicket, txtDescripcion;
			Combobox cmbcliente, cmbTipoServicio, cmbTipoTarea, cmbEstadoBitacora;
			Comboitem cItem;
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
			cmbcliente.setAutocomplete(false);
			cmbcliente.setInplace(true);
			cmbcliente.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbcliente.getText().toString().toUpperCase();
					cargarClientes2(criterio, 7);
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
			cargarClientes2("", 6);
			for (int j = 0; j < listaCliente2.size(); j++) {
				if (listaCliente2.get(j).getId_empresa() == listaTareaProveedor1.get(i).getId_cliente()) {
					cmbcliente.setText(listaCliente2.get(j).getNom_empresa());
					j = listaCliente2.size() + 1;
				}
			}
			cmbcliente.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbcliente);
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* TICKET HPSM/PROACTIVANET */
			lCell = new Listcell();
			txtTicket = new Textbox();
			txtTicket.setReadonly(false);
			txtTicket.setInplace(true);
			txtTicket.setRows(2);
			txtTicket.setMaxlength(20);
			txtTicket.setStyle("resize: none;");
			txtTicket.setWidth("120px");
			if (listaTareaProveedor1.get(i).getTicket_externo() == null) {
				txtTicket.setValue("");
			} else {
				if (listaTareaProveedor1.get(i).getTicket_externo().length() <= 0) {
					txtTicket.setValue("");
				} else {
					txtTicket.setText(String.valueOf(listaTareaProveedor1.get(i).getTicket_externo()));
				}
			}
			lCell.appendChild(txtTicket);
			lCell.setStyle("text-align: left !important; font-weight: normal !important;");
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* SERVICIO/CATEGORIA */
			lCell = new Listcell();
			cmbTipoServicio = new Combobox();
			for (int j = 0; j < listaTipoServicio1.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoServicio1.get(j).getNom_tipo_servicio());
				cItem.setValue(listaTipoServicio1.get(j).getId_tipo_servicio());
				cItem.setTooltiptext(listaTipoServicio1.get(j).getNom_tipo_servicio());
				cmbTipoServicio.appendChild(cItem);
			}
			cmbTipoServicio.setReadonly(false);
			cmbTipoServicio.setWidth("200px");
			cmbTipoServicio.setAutocomplete(false);
			cmbTipoServicio.setInplace(true);
			cmbTipoServicio.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbTipoServicio.getText().toString().toUpperCase();
					cargarTipoServicios1(criterio);
					Comboitem lItem;
					for (int i = cmbTipoServicio.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbTipoServicio.getItemAtIndex(i);
						cmbTipoServicio.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaTipoServicio1.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaTipoServicio1.get(i).getNom_tipo_servicio());
						lItem.setValue(listaTipoServicio1.get(i).getId_tipo_servicio());
						lItem.setTooltiptext(listaTipoServicio1.get(i).getNom_tipo_servicio());
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
			cargarTipoServicios1("");
			for (int j = 0; j < listaTipoServicio1.size(); j++) {
				if (listaTipoServicio1.get(j).getId_tipo_servicio() == listaTareaProveedor1.get(i)
						.getId_tipo_servicio()) {
					cmbTipoServicio.setText(listaTipoServicio1.get(j).getNom_tipo_servicio());
					j = listaTipoServicio1.size() + 1;
				}
			}
			cmbTipoServicio.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoServicio);
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
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
			cmbTipoTarea.setAutocomplete(false);
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
				if (listaTipoTarea.get(j).getId_tipo_tarea() == listaTareaProveedor1.get(i).getId_tipo_tarea()) {
					cmbTipoTarea.setText(listaTipoTarea.get(j).getNom_tipo_tarea());
					j = listaTipoTarea.size() + 1;
				}
			}
			cmbTipoTarea.setStyle("text-align: left !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoTarea);
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* DESCRIPCION */
			lCell = new Listcell();
			txtDescripcion = new Textbox();
			txtDescripcion.setWidth("180px");
			txtDescripcion.setMaxlength(2000);
			txtDescripcion.setRows(5);
			txtDescripcion.setStyle("resize: none;");
			txtDescripcion.setInplace(true);
			txtDescripcion.setReadonly(false);
			txtDescripcion.setText(String.valueOf(listaTareaProveedor1.get(i).getDescripcion()));
			lCell.appendChild(txtDescripcion);
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* FECHA INICIO */
			lCell = new Listcell();
			dtxFechaInicioInicio = new Datebox();
			dtxFechaInicioInicio.setWidth("150px");
			dtxFechaInicioInicio.setInplace(true);
			dtxFechaInicioInicio.setFormat("dd/MM/yyyy HH:mm");
			dtxFechaInicioInicio.setValue(listaTareaProveedor1.get(i).getFec_inicio());
			lCell.appendChild(dtxFechaInicioInicio);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* FECHA FIN */
			lCell = new Listcell();
			dtxFechaInicioFin = new Datebox();
			dtxFechaInicioFin.setWidth("150px");
			dtxFechaInicioFin.setInplace(true);
			dtxFechaInicioFin.setFormat("dd/MM/yyyy HH:mm");
			dtxFechaInicioFin.setValue(listaTareaProveedor1.get(i).getFec_fin());
			lCell.appendChild(dtxFechaInicioFin);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* ESTADO */
			lCell = new Listcell();
			cmbEstadoBitacora = new Combobox();
			for (int j = 0; j < listaEstadoBitacora.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaEstadoBitacora.get(j).getNom_estado());
				cItem.setValue(listaEstadoBitacora.get(j).getId_estado());
				cItem.setTooltiptext(listaEstadoBitacora.get(j).getNom_estado());
				cmbEstadoBitacora.appendChild(cItem);
			}
			cmbEstadoBitacora.setReadonly(false);
			cmbEstadoBitacora.setWidth("150px");
			cmbEstadoBitacora.setAutocomplete(false);
			cmbEstadoBitacora.setInplace(true);
			cmbEstadoBitacora.addEventListener(Events.ON_OK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String criterio = cmbEstadoBitacora.getText().toString().toUpperCase();
					cargarEstadoBitacoras(criterio);
					Comboitem lItem;
					for (int i = cmbEstadoBitacora.getItemCount() - 1; i >= 0; i--) {
						lItem = (Comboitem) cmbEstadoBitacora.getItemAtIndex(i);
						cmbEstadoBitacora.removeItemAt(lItem.getIndex());
					}
					for (int i = 0; i < listaEstadoBitacora.size(); i++) {
						lItem = new Comboitem();
						lItem.setLabel(listaEstadoBitacora.get(i).getNom_estado());
						lItem.setValue(listaEstadoBitacora.get(i).getId_estado());
						lItem.setTooltiptext(listaEstadoBitacora.get(i).getNom_estado());
						cmbEstadoBitacora.appendChild(lItem);
					}
					binder.loadComponent(cmbEstadoBitacora);
				}
			});
			cmbEstadoBitacora.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbEstadoBitacora.getSelectedItem() == null) {
						cmbEstadoBitacora.setTooltiptext("");
					} else if (cmbEstadoBitacora.getText().length() <= 0) {
						cmbEstadoBitacora.setTooltiptext("");
					} else {
						cmbEstadoBitacora.setTooltiptext(cmbEstadoBitacora.getSelectedItem().getLabel());
					}
				}
			});
			cargarEstadoBitacoras("");
			for (int j = 0; j < listaEstadoBitacora.size(); j++) {
				if (listaEstadoBitacora.get(j).getId_estado() == listaTareaProveedor1.get(i).getId_estado_bitacora()) {
					cmbEstadoBitacora.setText(listaEstadoBitacora.get(j).getNom_estado());
					j = listaEstadoBitacora.size() + 1;
				}
			}
			cmbEstadoBitacora
					.setStyle("text-align: center !important; font-weight: normal !important; font-style: italic;");
			long id_estado = 0;
			id_estado = listaTareaProveedor1.get(i).getId_estado_bitacora();
			String estilo = "";
			cargarParametros8(id_estado);
			if (listaParametros8.size() > 0) {
				estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
						+ listaParametros8.get(0).getColor() + " !important; text-align: center !important;";
				lCell.setStyle(estilo);
			} else {
				lCell.setStyle(
						"font-weight: bold !important; font-style: italic !important; text-align: center !important;");
			}
			lCell.appendChild(cmbEstadoBitacora);
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* OPERADOR REGISTRA */
			cargarUsuarios();
			lCell = new Listcell();
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaUsuario.get(j).getUse_usuario().equals(listaTareaProveedor1.get(i).getUsu_ingresa())) {
					lCell.setLabel(listaUsuario.get(j).verNombreCompleto());
					j = listaUsuario.size() + 1;
				}
			}
			lItem.appendChild(lCell);
			tamanio = tamanio + 1;
			/* OPERADOR MODIFICA */
			cargarUsuarios();
			int bandera = 0;
			String nom_usuario_modifica = "";
			lCell = new Listcell();
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaUsuario.get(j).getUse_usuario().equals(listaTareaProveedor1.get(i).getUsu_modifica())) {
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
			tamanio = tamanio + 1;
			if (listaTareaProveedor1.get(i).getId_estado_bitacora() == id_estado) {
				LocalDateTime localDateTime1 = null;
				LocalDateTime localDateTime2 = null;
				LocalDate localDate1 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate localDate2 = listaTareaProveedor1.get(i).getFec_fin().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				int year1 = localDate1.getYear();
				int year2 = localDate2.getYear();
				localDateTime1 = LocalDateTime.of(year1, new Date().getMonth() + 1, new Date().getDate(),
						new Date().getHours(), new Date().getMinutes());
				localDateTime2 = LocalDateTime.of(year2, listaTareaProveedor1.get(i).getFec_fin().getMonth() + 1,
						listaTareaProveedor1.get(i).getFec_fin().getDate(),
						listaTareaProveedor1.get(i).getFec_fin().getHours(),
						listaTareaProveedor1.get(i).getFec_fin().getMinutes());
				Date d1 = null;
				Date d2 = null;
				d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
				d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
				if (d1.after(d2)) {
					Listcell lcell;
					for (int j = 0; j < 10; j++) {
						lcell = (Listcell) lItem.getChildren().get(j);
						if (!(j == 7)) {
							lcell.setStyle("background-color: red !important;");
						}
					}
				}
			}
			/* ANADIR ITEM A LISTBOX */
			lbxTareasProveedores.appendChild(lItem);
		}
	}

}
