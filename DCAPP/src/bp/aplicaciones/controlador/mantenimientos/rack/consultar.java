package bp.aplicaciones.controlador.mantenimientos.rack;

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
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;
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
	Listbox lbxRacks;
	@Wire
	Combobox cmbLimite, cmbEmpresa, cmbFila;
	@Wire
	Menuitem mModificar;
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

	List<modelo_rack> listaRack = new ArrayList<modelo_rack>();
	List<modelo_fila> listaFila = new ArrayList<modelo_fila>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	
	@SuppressWarnings("rawtypes")
	ListModelList racks = new ListModelList();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	long id_mantenimiento = 21;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		cargarEmpresas();
		cargarFilas();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarRacks();
			txtBuscar.setDisabled(false);
			lbxRacks.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxRacks.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_rack> obtenerRacks() {
		return listaRack;
	}

	public List<modelo_fila> obtenerFilas() {
		return listaFila;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	@SuppressWarnings("unchecked")
	public void cargarRacks() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaRack = consultasABaseDeDatos.consultarRacks(id_dc, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		racks.add(listaRack);
		binder.loadComponent(lbxRacks);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarFilas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaFila = consultasABaseDeDatos.consultarFilas(0, id_dc, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_fila bean = (modelo_fila) o2;
				return bean.getNom_fila().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaFila), myComparator, 15);
		cmbFila.setModel(mySubModel);
		ComboitemRenderer<modelo_fila> myRenderer = new ComboitemRenderer<modelo_fila>() {
			@Override
			public void render(Comboitem item, modelo_fila bean, int index) throws Exception {
				item.setLabel(bean.getNom_fila());
				item.setValue(bean);
			}
		};
		cmbFila.setItemRenderer(myRenderer);
		binder.loadComponent(cmbFila);
	}

	@Listen("onChanging=#bdxEmpresas")
	public void onChanging$bdxEmpresas() {
		try {
			cargarEmpresas();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa = consultasABaseDeDatos.consultarEmpresas(id_dc, id_mantenimiento, "", "", 0, 6);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_empresa bean = (modelo_empresa) o2;
				return bean.getNom_empresa().contains(input.toUpperCase().trim()) ? 0 : 1;
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
			buscarRacks();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarRacks();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa() {
		try {
			buscarRacks();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbFila")
	public void onSelect$cmbFila() {
		try {
			buscarRacks();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarRacks();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarRacks() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		id1 = id_dc;
		if (cmbEmpresa.getSelectedItem() != null) {
			id2 = ((modelo_empresa) cmbEmpresa.getSelectedItem().getValue()).getId_empresa();
		}
		criterio1 = txtBuscar.getText().trim();
		if (cmbFila.getSelectedItem() != null) {
			criterio2 = String.valueOf(((modelo_fila) cmbFila.getSelectedItem().getValue()).getId_fila());
		}
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaRack = consultasABaseDeDatos.consultarRacks(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxRacks);
	}

	@Listen("onRightClick=#lbxRacks")
	public void onRightClick$lbxRacks() throws Throwable {
		if (lbxRacks.getSelectedItem() == null) {
			return;
		}
		int indice = lbxRacks.getSelectedIndex();
		buscarRacks();
		int tamanio_lista = lbxRacks.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxRacks.setSelectedIndex(indice);
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxRacks.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxRacks.getSelectedIndex();
		Sessions.getCurrent().setAttribute("rack", listaRack.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/rack/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxRacks.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxRacks.setDisabled(false);
					buscarRacks();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/rack/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarRacks();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
