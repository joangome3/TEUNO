package bp.aplicaciones.controlador.mantenimientos.tipo_equipo;

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
	Listbox lbxTipoEquipos;
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

	List<modelo_tipo_equipo> listaTipoEquipo = new ArrayList<modelo_tipo_equipo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 25;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarTipoEquipos();
			txtBuscar.setDisabled(false);
			lbxTipoEquipos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxTipoEquipos.setEmptyMessage(informativos.getMensaje_informativo_22());
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

	public List<modelo_tipo_equipo> obtenerTipoEquipos() {
		return listaTipoEquipo;
	}

	public void cargarTipoEquipos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoEquipo = consultasABaseDeDatos.consultarTipoEquipos(0, 0, "", "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1);
		binder.loadComponent(lbxTipoEquipos);
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
			buscarTipoEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarTipoEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarTipoEquipos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarTipoEquipos() throws ClassNotFoundException, FileNotFoundException, IOException {
		long id1 = 0;
		long id2 = 0;
		String criterio1 = "";
		String criterio2 = "";
		int limite = 0;
		criterio1 = txtBuscar.getText().trim();
		limite = Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString());
		listaTipoEquipo = consultasABaseDeDatos.consultarTipoEquipos(id1, id2, criterio1, criterio2, limite, 1);
		binder.loadComponent(lbxTipoEquipos);
	}

	@Listen("onRightClick=#lbxTipoEquipos")
	public void onRightClick$lbxTipoEquipos() throws Throwable {
		if (lbxTipoEquipos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxTipoEquipos.getSelectedIndex();
		buscarTipoEquipos();
		int tamanio_lista = lbxTipoEquipos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxTipoEquipos.setSelectedIndex(indice);
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxTipoEquipos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxTipoEquipos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("tipo_equipo", listaTipoEquipo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/tipo_equipo/modificar.zul", null, null);
		mModificar.setDisabled(true);
		lbxTipoEquipos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					lbxTipoEquipos.setDisabled(false);
					buscarTipoEquipos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/tipo_equipo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarTipoEquipos();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
