package bp.aplicaciones.controlador.mantenimientos.modelo;

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
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;
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
	Listbox lbxModelos;
	@Wire
	Combobox cmbLimite;
	@Wire
	Menuitem mModificar;
	@Wire
	Menuseparator mSeparador1, mSeparador2;
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

	List<modelo_modelo> listaModelo = new ArrayList<modelo_modelo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 24;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarModelos();
			txtBuscar.setDisabled(false);
			lbxModelos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxModelos.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_modelo> obtenerModelos() {
		return listaModelo;
	}

	public void cargarModelos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaModelo = consultasABaseDeDatos.consultarModelos(id_dc, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxModelos);
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
			buscarModelos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarModelos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarModelos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarModelos() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		criterio1 = txtBuscar.getText().trim();
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaModelo = consultasABaseDeDatos.consultarModelos(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxModelos);
	}

	@Listen("onRightClick=#lbxModelos")
	public void onRightClick$lbxModelos() throws Throwable {
		if (lbxModelos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxModelos.getSelectedIndex();
		buscarModelos();
		int tamanio_lista = lbxModelos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxModelos.setSelectedIndex(indice);
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxModelos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxModelos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("modelo", listaModelo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/modelo/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxModelos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxModelos.setDisabled(false);
					buscarModelos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/modelo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarModelos();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
