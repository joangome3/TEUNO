package bp.aplicaciones.controlador.mantenimiento.manuales;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_manual;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;
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
	Listbox lbxManuales;
	@Wire
	Combobox cmbLimite;
	@Wire
	Menuitem mModificar, mVer, mDescargar;
	@Wire
	Menuseparator mSeparador1;
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

	List<modelo_manual> listaManual = new ArrayList<modelo_manual>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 20;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarManuales();
			txtBuscar.setDisabled(false);
			lbxManuales.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxManuales.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
		} else {
			btnNuevo.setDisabled(true);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public List<modelo_manual> obtenerManuales() {
		return listaManual;
	}

	public void cargarManuales() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_manual dao = new dao_manual();
		String criterio = "";
		try {
			listaManual = dao.obtenerManuales(criterio, String.valueOf(id_dc), 1, 0,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
			binder.loadComponent(lbxManuales);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los manuales. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar manual ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
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
			buscarManuales();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarManuales();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarManuales();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarManuales()
			throws ClassNotFoundException, FileNotFoundException, IOException, NumberFormatException, SQLException {
		dao_manual dao = new dao_manual();
		String criterio = txtBuscar.getText();
		listaManual = dao.obtenerManuales(criterio, String.valueOf(id_dc), 1, 0,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxManuales);
	}

	@Listen("onRightClick=#lbxManuales")
	public void onRightClick$lbxManuales() throws Throwable {
		if (lbxManuales.getSelectedItem() == null) {
			return;
		}
		int indice = lbxManuales.getSelectedIndex();
		buscarManuales();
		int tamanio_lista = lbxManuales.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxManuales.setSelectedIndex(indice);
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxManuales.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxManuales.getSelectedIndex();
		Sessions.getCurrent().setAttribute("objeto", listaManual.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/manuales/modificar.zul", null, null);
		mModificar.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/manuales/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarManuales();
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mVer")
	public void onClick$mVer() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxManuales.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxManuales.getSelectedIndex();
		Sessions.getCurrent().setAttribute("objeto", listaManual.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/manuales/ver_manual.zul", null, null);
		mVer.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mVer.setDisabled(false);
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mDescargar")
	public void onClick$mDescargar() throws SQLException {
		if (lbxManuales.getSelectedItem() == null) {
			return;
		}
		int indice = lbxManuales.getSelectedIndex();
		java.io.InputStream documento = listaManual.get(indice).getDir_manual().getBinaryStream();
		if (documento != null) {
			Filedownload.save(documento, "pdf", listaManual.get(indice).getNom_manual().toLowerCase().trim() + "."
					+ listaManual.get(indice).getExt_manual().toLowerCase().trim());
		}
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
