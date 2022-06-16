package bp.aplicaciones.controlador.mantenimientos.equipo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxEquipos;
	@Wire
	Combobox cmbLimite, cmbTipoEquipo;
	@Wire
	Menuitem mModificar, mFuentes;
	@Wire
	Div winList;

	Window window;

	boolean ingresa_a_accion = false;

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

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	List<modelo_equipo> listaEquipo = new ArrayList<modelo_equipo>();
	List<modelo_tipo_equipo> listaTipoEquipo = new ArrayList<modelo_tipo_equipo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	long id_mantenimiento = 26;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		cargarTipoEquipos();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarEquipos();
			txtBuscar.setDisabled(false);
			lbxEquipos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxEquipos.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
			btnNuevo.setVisible(true);
		} else {
			btnNuevo.setDisabled(true);
			btnNuevo.setVisible(false);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase().trim());
			}
		});
	}

	public List<modelo_equipo> obtenerEquipos() {
		return listaEquipo;
	}

	public List<modelo_tipo_equipo> obtenerTipoEquipos() {
		return listaTipoEquipo;
	}

	public void cargarEquipos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEquipo = consultasABaseDeDatos.consultarEquipos(0, id_dc, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxEquipos);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarTipoEquipos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoEquipo = consultasABaseDeDatos.consultarTipoEquipos(0, 0, "", "", 0, 3);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_tipo_equipo bean = (modelo_tipo_equipo) o2;
				return bean.getNom_tipo_equipo().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaTipoEquipo), myComparator, 15);
		cmbTipoEquipo.setModel(mySubModel);
		ComboitemRenderer<modelo_tipo_equipo> myRenderer = new ComboitemRenderer<modelo_tipo_equipo>() {
			@Override
			public void render(Comboitem item, modelo_tipo_equipo bean, int index) throws Exception {
				item.setLabel(bean.getNom_tipo_equipo());
				item.setValue(bean);
			}
		};
		cmbTipoEquipo.setItemRenderer(myRenderer);
		binder.loadComponent(cmbTipoEquipo);
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
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
			Messagebox.show(error.getMensaje_error_3(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbTipoEquipo")
	public void onSelect$cmbTipoEquipo() {
		try {
			buscarEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarEquipos() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		if (cmbTipoEquipo.getSelectedItem() != null) {
			id1 = ((modelo_tipo_equipo) cmbTipoEquipo.getSelectedItem().getValue()).getId_tipo_equipo();
		}
		id2 = id_dc;
		criterio1 = txtBuscar.getText();
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaEquipo = consultasABaseDeDatos.consultarEquipos(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxEquipos);
	}

	@Listen("onRightClick=#lbxEquipos")
	public void onRightClick$lbxEquipos() throws Throwable {
		if (lbxEquipos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxEquipos.getSelectedIndex();
		buscarEquipos();
		int tamanio_lista = lbxEquipos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxEquipos.setSelectedIndex(indice);
	}

	@Listen("onClick=#mFuentes")
	public void onClick$mFuentes() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxEquipos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxEquipos.getSelectedIndex();
		if (listaEquipo.get(indice).getRelacion_equipo_tipo_conector().size() == 0) {
			Clients.showNotification("No existen fuentes asociadas al registro seleccionado.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		Sessions.getCurrent().setAttribute("relacion", listaEquipo.get(indice).getRelacion_equipo_tipo_conector());
		window = (Window) Executions.createComponents("/emergentes/fuente.zul", null, null);
		mFuentes.setDisabled(true);
		lbxEquipos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mFuentes.setDisabled(false);
					lbxEquipos.setDisabled(false);
					buscarEquipos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxEquipos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxEquipos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("equipo", listaEquipo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/equipo/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxEquipos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxEquipos.setDisabled(false);
					buscarEquipos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/equipo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarEquipos();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
