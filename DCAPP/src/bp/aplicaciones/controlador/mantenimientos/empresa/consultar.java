package bp.aplicaciones.controlador.mantenimientos.empresa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
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
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
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
	Button btnNuevo, btnRefrescar, btnClonarPermisos;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxEmpresas;
	@Wire
	Combobox cmbLimite, cmbEmpresa;
	@Wire
	Menuitem mModificar, mRelacionar, mEstado;
	@Wire
	Menuseparator mSeparador1, mSeparador2;
	@Wire
	Checkbox chkClonarPermisos;
	@Wire
	Div winList;

	Window window;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

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

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_empresa> listaEmpresa1 = new ArrayList<modelo_empresa>();
	List<modelo_empresa> listaEmpresa2 = new ArrayList<modelo_empresa>();

	long id_mantenimiento = 8;

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		cargarEmpresas1();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarEmpresas2();
			txtBuscar.setDisabled(false);
			lbxEmpresas.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxEmpresas.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa2;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEmpresas1() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa1 = consultasABaseDeDatos.consultarEmpresas(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_empresa bean = (modelo_empresa) o2;
				return bean.getNom_empresa().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEmpresa1), myComparator, 15);
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

	public void cargarEmpresas2() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa2 = consultasABaseDeDatos.consultarEmpresas(id_dc, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxEmpresas);
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
	
	@Listen("onClick=#chkClonarPermisos")
	public void onCheck$chkClonarPermisos() {
		if(chkClonarPermisos.isChecked()) {
			cmbEmpresa.setDisabled(false);
			cmbEmpresa.setText("");
			btnClonarPermisos.setDisabled(false);
		}else {
			cmbEmpresa.setText("");
			cmbEmpresa.setDisabled(true);
			btnClonarPermisos.setDisabled(true);
		}
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarEmpresas();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarEmpresas();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarEmpresas();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		criterio1 = txtBuscar.getText().trim();
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaEmpresa2 = consultasABaseDeDatos.consultarEmpresas(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxEmpresas);
	}

	@Listen("onRightClick=#lbxEmpresas")
	public void onRightClick$lbxEmpresas() throws Throwable {
		if (lbxEmpresas.getSelectedItem() == null) {
			return;
		}
		int indice = lbxEmpresas.getSelectedIndex();
		buscarEmpresas();
		int tamanio_lista = lbxEmpresas.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxEmpresas.setSelectedIndex(indice);
		if (listaEmpresa2.get(indice).getEst_empresa().equals("AE")) {
			mEstado.setLabel(" - Inactivar");
			mEstado.setIconSclass("z-icon-times-circle-o");
		}
		if (listaEmpresa2.get(indice).getEst_empresa().equals("IE")) {
			mEstado.setLabel(" - Activar");
			mEstado.setIconSclass("z-icon-check-circle-o");
		}
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxEmpresas.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxEmpresas.getSelectedIndex();
		Sessions.getCurrent().setAttribute("empresa", listaEmpresa2.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/empresa/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxEmpresas.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxEmpresas.setDisabled(false);
					buscarEmpresas();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mEstado")
	public void onClick$mEstado() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxEmpresas.getSelectedItem() == null) {
			return;
		}
		int indice = lbxEmpresas.getSelectedIndex();
		String estado = "";
		if (listaEmpresa2.get(indice).getEst_empresa().equals("AE")) {
			estado = "IE";
		}
		if (listaEmpresa2.get(indice).getEst_empresa().equals("IE")) {
			estado = "AE";
		}
		modelo_empresa empresa = new modelo_empresa();
		dao_empresa dao = new dao_empresa();
		empresa = listaEmpresa2.get(indice);
		empresa.setEst_empresa(estado);
		empresa.setUsu_modifica(user);
		empresa.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
		dao.actualizarEmpresa(empresa);
		Clients.showNotification("La empresa se actualizó correctamente.", Clients.NOTIFICATION_TYPE_INFO, dSolicitudes,
				"dSolicitudes", 4000, true);
		buscarEmpresas();
		int tamanio_lista = lbxEmpresas.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxEmpresas.setSelectedIndex(indice);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/empresa/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarEmpresas();
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mRelacionar")
	public void onClick$mRelacionar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxEmpresas.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxEmpresas.getSelectedIndex();
		Sessions.getCurrent().setAttribute("empresa", listaEmpresa2.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/empresa/relacionar.zul", null, null);
		mRelacionar.setDisabled(true);
		lbxEmpresas.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mRelacionar.setDisabled(false);
					lbxEmpresas.setDisabled(false);
					buscarEmpresas();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnClonarPermisos")
	public void onClick$btnClonarPermisos() {
		if (lbxEmpresas.getSelectedItems().size() == 0) {
			Clients.showNotification("Seleccione alguno de los items de la lista.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "dSolicitudes", 4000, true);
			return;
		}
		if (cmbEmpresa.getSelectedItem() == null) {
			Clients.showNotification("Seleccione una empresa.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"dSolicitudes", 4000, true);
			cmbEmpresa.setFocus(true);
			return;
		}
		modelo_empresa empresa1 = (modelo_empresa) cmbEmpresa.getSelectedItem().getValue();
		dao_relacion_empresa dao = new dao_relacion_empresa();
		for (int i = 0; i < lbxEmpresas.getItemCount(); i++) {
			modelo_empresa empresa2 = new modelo_empresa();
			if (lbxEmpresas.getItemAtIndex(i).isSelected()) {
				empresa2 = listaEmpresa2.get(i);
				for (int j = 0; j < empresa2.getRelaciones_empresa_mantenimiento().size(); j++) {
					dao.eliminarRelacionMantenimiento(empresa2.getRelaciones_empresa_mantenimiento().get(j));
				}
				for (int j = 0; j < empresa2.getRelaciones_empresa_opcion().size(); j++) {
					dao.eliminarRelacionOpcion(empresa2.getRelaciones_empresa_opcion().get(j));
				}
				for (int j = 0; j < empresa2.getRelaciones_empresa_localidad().size(); j++) {
					dao.eliminarRelacionLocalidad(empresa2.getRelaciones_empresa_localidad().get(j));
				}
				for (int j = 0; j < empresa1.getRelaciones_empresa_mantenimiento().size(); j++) {
					empresa1.getRelaciones_empresa_mantenimiento().get(j).getEmpresa()
							.setId_empresa(empresa2.getId_empresa());
					empresa1.getRelaciones_empresa_mantenimiento().get(j).setUsu_modifica(user);
					empresa1.getRelaciones_empresa_mantenimiento().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionMantenimiento(empresa1.getRelaciones_empresa_mantenimiento().get(j));
				}
				for (int j = 0; j < empresa1.getRelaciones_empresa_opcion().size(); j++) {
					empresa1.getRelaciones_empresa_opcion().get(j).getEmpresa()
							.setId_empresa(empresa2.getId_empresa());
					empresa1.getRelaciones_empresa_opcion().get(j).setUsu_modifica(user);
					empresa1.getRelaciones_empresa_opcion().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionOpcion(empresa1.getRelaciones_empresa_opcion().get(j));
				}
				for (int j = 0; j < empresa1.getRelaciones_empresa_localidad().size(); j++) {
					empresa1.getRelaciones_empresa_localidad().get(j).getEmpresa()
							.setId_empresa(empresa2.getId_empresa());
					empresa1.getRelaciones_empresa_localidad().get(j).setUsu_modifica(user);
					empresa1.getRelaciones_empresa_localidad().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionLocalidad(empresa1.getRelaciones_empresa_localidad().get(j));
				}
			}
		}
		Clients.showNotification("El/Las empresas se actualizaron correctamente.", Clients.NOTIFICATION_TYPE_INFO,
				dSolicitudes, "dSolicitudes", 4000, true);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
