package bp.aplicaciones.controlador.mantenimientos.mantenimiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
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
	Listbox lbxMantenimientos;
	@Wire
	Combobox cmbLimite;
	@Wire
	Menuitem mModificar, mRelacionar, mEstado;
	@Wire
	Menuseparator mSeparador1, mSeparador2;
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

	List<modelo_mantenimiento> listaMantenimiento = new ArrayList<modelo_mantenimiento>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 3;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarMantenimientos();
			txtBuscar.setDisabled(false);
			lbxMantenimientos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxMantenimientos.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_mantenimiento> obtenerMantenimientos() {
		return listaMantenimiento;
	}
	
	public List<modelo_mantenimiento> getMantenimientos() {
		ListModelList<modelo_mantenimiento> listado = new  ListModelList<modelo_mantenimiento>(listaMantenimiento);
		return listado;
	}

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaMantenimiento = consultasABaseDeDatos.consultarMantenimientos(0, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxMantenimientos);
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
			buscarMantenimientos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarMantenimientos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarMantenimientos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		criterio1 = txtBuscar.getText().trim();
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaMantenimiento = consultasABaseDeDatos.consultarMantenimientos(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxMantenimientos);
	}

	@Listen("onRightClick=#lbxMantenimientos")
	public void onRightClick$lbxMantenimientos() throws Throwable {
		if (lbxMantenimientos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxMantenimientos.getSelectedIndex();
		buscarMantenimientos();
		int tamanio_lista = lbxMantenimientos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxMantenimientos.setSelectedIndex(indice);
		if (listaMantenimiento.get(indice).getEst_mantenimiento().equals("AE")) {
			mEstado.setLabel(" - Inactivar");
			mEstado.setIconSclass("z-icon-times-circle-o");
		}
		if (listaMantenimiento.get(indice).getEst_mantenimiento().equals("IE")) {
			mEstado.setLabel(" - Activar");
			mEstado.setIconSclass("z-icon-check-circle-o");
		}
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxMantenimientos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxMantenimientos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("mantenimiento", listaMantenimiento.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/mantenimiento/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxMantenimientos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxMantenimientos.setDisabled(false);
					buscarMantenimientos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}
	
	@Listen("onClick=#mEstado")
	public void onClick$mEstado() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxMantenimientos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxMantenimientos.getSelectedIndex();
		String estado = "";
		if (listaMantenimiento.get(indice).getEst_mantenimiento().equals("AE")) {
			estado = "IE";
		}
		if (listaMantenimiento.get(indice).getEst_mantenimiento().equals("IE")) {
			estado = "AE";
		}
		modelo_mantenimiento mantenimiento = new modelo_mantenimiento();
		dao_mantenimiento dao = new dao_mantenimiento();
		mantenimiento = listaMantenimiento.get(indice);
		mantenimiento.setEst_mantenimiento(estado);
		mantenimiento.setUsu_modifica(user);
		mantenimiento.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
		dao.actualizarMantenimiento(mantenimiento);
		Clients.showNotification("El mantenimiento se actualizó correctamente.", Clients.NOTIFICATION_TYPE_INFO, dSolicitudes,
				"dSolicitudes", 4000, true);
		buscarMantenimientos();
		int tamanio_lista = lbxMantenimientos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxMantenimientos.setSelectedIndex(indice);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/mantenimiento/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarMantenimientos();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}
	
	@Listen("onClick=#mRelacionar")
	public void onClick$mRelacionar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxMantenimientos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxMantenimientos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("mantenimiento", listaMantenimiento.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/mantenimiento/relacionar.zul", null, null);
		mRelacionar.setDisabled(true);
		lbxMantenimientos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mRelacionar.setDisabled(false);
					lbxMantenimientos.setDisabled(false);
					buscarMantenimientos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

}
