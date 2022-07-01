package bp.aplicaciones.controlador.mantenimientos.solicitante;

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
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
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
	Listbox lbxSolicitantes;
	@Wire
	Combobox cmbEmpresa, cmbLimite, cmbSolicitante;
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
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_solicitante> listaSolicitante1 = new ArrayList<modelo_solicitante>();
	List<modelo_solicitante> listaSolicitante2 = new ArrayList<modelo_solicitante>();

	long id_mantenimiento = 1;

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		cargarEmpresas();
		cargarSolicitantes1();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarSolicitantes2();
			txtBuscar.setDisabled(false);
			lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante2;
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
	public void cargarSolicitantes1() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaSolicitante1 = consultasABaseDeDatos.consultarSolicitantes(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_solicitante bean = (modelo_solicitante) o2;
				return (bean.getNom_solicitante() + " " + bean.getApe_solicitante())
						.contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaSolicitante1), myComparator, 15);
		cmbSolicitante.setModel(mySubModel);
		ComboitemRenderer<modelo_solicitante> myRenderer = new ComboitemRenderer<modelo_solicitante>() {
			@Override
			public void render(Comboitem item, modelo_solicitante bean, int index) throws Exception {
				item.setLabel(bean.getNom_solicitante() + " " + bean.getApe_solicitante());
				item.setValue(bean);
			}
		};
		cmbSolicitante.setItemRenderer(myRenderer);
		binder.loadComponent(cmbSolicitante);
	}

	public void cargarSolicitantes2() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaSolicitante2 = consultasABaseDeDatos.consultarSolicitantes(id_dc, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxSolicitantes);
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
			cmbSolicitante.setDisabled(false);
			cmbSolicitante.setText("");
			btnClonarPermisos.setDisabled(false);
		}else {
			cmbSolicitante.setText("");
			cmbSolicitante.setDisabled(true);
			btnClonarPermisos.setDisabled(true);
		}
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarSolicitantes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa() {
		try {
			buscarSolicitantes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarSolicitantes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarSolicitantes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarSolicitantes() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		criterio1 = txtBuscar.getText().trim();
		if (cmbEmpresa.getSelectedItem() != null) {
			modelo_empresa empresa = (modelo_empresa) cmbEmpresa.getSelectedItem().getValue();
			id2 = empresa.getId_empresa();
		}
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaSolicitante2 = consultasABaseDeDatos.consultarSolicitantes(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxSolicitantes);
	}

	@Listen("onRightClick=#lbxSolicitantes")
	public void onRightClick$lbxSolicitantes() throws Throwable {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		buscarSolicitantes();
		int tamanio_lista = lbxSolicitantes.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxSolicitantes.setSelectedIndex(indice);
		if (listaSolicitante2.get(indice).getEst_solicitante().equals("AE")) {
			mEstado.setLabel(" - Inactivar");
			mEstado.setIconSclass("z-icon-times-circle-o");
		}
		if (listaSolicitante2.get(indice).getEst_solicitante().equals("IE")) {
			mEstado.setLabel(" - Activar");
			mEstado.setIconSclass("z-icon-check-circle-o");
		}
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		Sessions.getCurrent().setAttribute("solicitante", listaSolicitante2.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/solicitante/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxSolicitantes.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxSolicitantes.setDisabled(false);
					buscarSolicitantes();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mEstado")
	public void onClick$mEstado() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		String estado = "";
		if (listaSolicitante2.get(indice).getEst_solicitante().equals("AE")) {
			estado = "IE";
		}
		if (listaSolicitante2.get(indice).getEst_solicitante().equals("IE")) {
			estado = "AE";
		}
		modelo_solicitante solicitante = new modelo_solicitante();
		dao_solicitante dao = new dao_solicitante();
		solicitante = listaSolicitante2.get(indice);
		solicitante.setEst_solicitante(estado);
		solicitante.setUsu_modifica(user);
		solicitante.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
		dao.actualizarSolicitante(solicitante);
		Clients.showNotification("La solicitante se actualizó correctamente.", Clients.NOTIFICATION_TYPE_INFO,
				dSolicitudes, "dSolicitudes", 4000, true);
		buscarSolicitantes();
		int tamanio_lista = lbxSolicitantes.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxSolicitantes.setSelectedIndex(indice);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/solicitante/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarSolicitantes();
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mRelacionar")
	public void onClick$mRelacionar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		Sessions.getCurrent().setAttribute("solicitante", listaSolicitante2.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/solicitante/relacionar.zul", null, null);
		mRelacionar.setDisabled(true);
		lbxSolicitantes.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mRelacionar.setDisabled(false);
					lbxSolicitantes.setDisabled(false);
					buscarSolicitantes();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnClonarPermisos")
	public void onClick$btnClonarPermisos() {
		if (lbxSolicitantes.getSelectedItems().size() == 0) {
			Clients.showNotification("Seleccione alguno de los items de la lista.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "dSolicitudes", 4000, true);
			return;
		}
		if (cmbSolicitante.getSelectedItem() == null) {
			Clients.showNotification("Seleccione una solicitante.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"dSolicitudes", 4000, true);
			cmbSolicitante.setFocus(true);
			return;
		}
		modelo_solicitante solicitante1 = (modelo_solicitante) cmbSolicitante.getSelectedItem().getValue();
		dao_relacion_solicitante dao = new dao_relacion_solicitante();
		for (int i = 0; i < lbxSolicitantes.getItemCount(); i++) {
			modelo_solicitante solicitante2 = new modelo_solicitante();
			if (lbxSolicitantes.getItemAtIndex(i).isSelected()) {
				solicitante2 = listaSolicitante2.get(i);
				for (int j = 0; j < solicitante2.getRelaciones_solicitante_mantenimiento().size(); j++) {
					dao.eliminarRelacionMantenimiento(solicitante2.getRelaciones_solicitante_mantenimiento().get(j));
				}
				for (int j = 0; j < solicitante2.getRelaciones_solicitante_opcion().size(); j++) {
					dao.eliminarRelacionOpcion(solicitante2.getRelaciones_solicitante_opcion().get(j));
				}
				for (int j = 0; j < solicitante2.getRelaciones_solicitante_localidad().size(); j++) {
					dao.eliminarRelacionLocalidad(solicitante2.getRelaciones_solicitante_localidad().get(j));
				}
				for (int j = 0; j < solicitante1.getRelaciones_solicitante_mantenimiento().size(); j++) {
					solicitante1.getRelaciones_solicitante_mantenimiento().get(j).getSolicitante()
							.setId_solicitante(solicitante2.getId_solicitante());
					solicitante1.getRelaciones_solicitante_mantenimiento().get(j).setUsu_modifica(user);
					solicitante1.getRelaciones_solicitante_mantenimiento().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionMantenimiento(solicitante1.getRelaciones_solicitante_mantenimiento().get(j));
				}
				for (int j = 0; j < solicitante1.getRelaciones_solicitante_opcion().size(); j++) {
					solicitante1.getRelaciones_solicitante_opcion().get(j).getSolicitante()
							.setId_solicitante(solicitante2.getId_solicitante());
					solicitante1.getRelaciones_solicitante_opcion().get(j).setUsu_modifica(user);
					solicitante1.getRelaciones_solicitante_opcion().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionOpcion(solicitante1.getRelaciones_solicitante_opcion().get(j));
				}
				for (int j = 0; j < solicitante1.getRelaciones_solicitante_localidad().size(); j++) {
					solicitante1.getRelaciones_solicitante_localidad().get(j).getSolicitante()
							.setId_solicitante(solicitante2.getId_solicitante());
					solicitante1.getRelaciones_solicitante_localidad().get(j).setUsu_modifica(user);
					solicitante1.getRelaciones_solicitante_localidad().get(j)
							.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
					dao.insertarRelacionLocalidad(solicitante1.getRelaciones_solicitante_localidad().get(j));
				}
			}
		}
		Clients.showNotification("El/Las solicitantes se actualizaron correctamente.", Clients.NOTIFICATION_TYPE_INFO,
				dSolicitudes, "dSolicitudes", 4000, true);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
