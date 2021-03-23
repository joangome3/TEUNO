package bp.aplicaciones.controlador.mantenimientos.localidad;

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
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxLocalidades;
	@Wire
	Combobox cmbLimite;
	@Wire
	Menuitem mSeguimiento, mSolicitar, mAccion;
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

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
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
			cargarLocalidades();
			txtBuscar.setDisabled(false);
			lbxLocalidades.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxLocalidades.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
			btnNuevo.setVisible(true);
		} else {
			btnNuevo.setDisabled(true);
			btnNuevo.setVisible(false);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = txtBuscar.getText();
		listaLocalidad = consultasABaseDeDatos.cargarLocalidades(criterio, 1, 0,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxLocalidades);
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
			buscarLocalidades();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarLocalidades();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarLocalidades();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = txtBuscar.getText();
		listaLocalidad = consultasABaseDeDatos.cargarLocalidades(criterio, 1, 0,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxLocalidades);
	}

	@Listen("onRightClick=#lbxLocalidades")
	public void onRightClick$lbxLocalidades() throws Throwable {
		if (lbxLocalidades.getSelectedItem() == null) {
			return;
		}
		int indice = lbxLocalidades.getSelectedIndex();
		buscarLocalidades();
		int tamanio_lista = lbxLocalidades.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxLocalidades.setSelectedIndex(indice);
		if (validarSiExisteSolicitudCreada(listaLocalidad.get(indice).getId_localidad()) == false
				&& validarSiExisteSolicitudPendienteEjecucion(listaLocalidad.get(indice).getId_localidad()) == false
				&& validarSiExisteSolicitudPendienteActualizacion(
						listaLocalidad.get(indice).getId_localidad()) == false) {
			mSeguimiento.setVisible(false);
			mSeguimiento.setDisabled(true);
			mSeparador1.setVisible(false);
			mSolicitar.setVisible(true);
			mSolicitar.setDisabled(false);
			mAccion.setVisible(false);
			mAccion.setDisabled(true);
		} else {
			mSeguimiento.setVisible(true);
			mSeguimiento.setDisabled(false);
			mSeparador1.setVisible(false);
			mSolicitar.setVisible(false);
			mSolicitar.setDisabled(true);
			mAccion.setVisible(false);
			mAccion.setDisabled(true);
		}
		if (validarSiExisteSolicitudPendienteEjecucion(listaLocalidad.get(indice).getId_localidad()) == true) {
			mSeparador1.setVisible(true);
			mAccion.setVisible(true);
			mAccion.setDisabled(false);
			if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 2) {
				mAccion.setLabel(" - Actualizar");
				mAccion.setIconSclass("z-icon-refresh");
			} else if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 3) {
				mAccion.setLabel(" - Activar");
				mAccion.setIconSclass("z-icon-font");
			} else if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 4) {
				mAccion.setLabel(" - Inactivar");
				mAccion.setIconSclass("z-icon-italic");
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaLocalidad.get(indice).getId_localidad()) == true) {
			mSeparador1.setVisible(true);
			mAccion.setVisible(true);
			mAccion.setDisabled(false);
			mAccion.setLabel(" - Actualizar");
			mAccion.setIconSclass("z-icon-refresh");
		}
	}

	public boolean validarSiExisteSolicitudCreada(long id_registro)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_creada = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("P") || estado.equals("R")) {
					existe_solicitud_creada = true;
				}
			}
		}
		return existe_solicitud_creada;
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion(long id_registro)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public boolean validarSiExisteSolicitudPendienteActualizacion(long id_registro)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public int validarTipoSolicitud(long id_registro)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		int tipo_solicitud = 0;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
		if (solicitud != null) {
			tipo_solicitud = (int) solicitud.getId_tip_solicitud();
		}
		return tipo_solicitud;
	}

	@Listen("onClick=#mSeguimiento")
	public void onClick$mSeguimiento() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxLocalidades.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxLocalidades.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaLocalidad.get(indice).getId_localidad(), 7);
		if (solicitud == null) {
			return;
		} else {
			if (solicitud.getId_solicitud() == 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("objeto", solicitud);
		Sessions.getCurrent().setAttribute("id_mantenimiento", id_mantenimiento);
		Sessions.getCurrent().setAttribute("id_opcion", (long) 0);
		Sessions.getCurrent().setAttribute("tipo_solicitud", 0);
		window = (Window) Executions.createComponents("/mantenimientos/solicitud/seguimiento.zul", null, null);
		mSeguimiento.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mSeguimiento.setDisabled(false);
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mSolicitar")
	public void onClick$mSolicitar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxLocalidades.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxLocalidades.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaLocalidad.get(indice).getId_localidad(), 7);
		if (solicitud != null) {
			if (solicitud.getId_solicitud() != 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("objeto", listaLocalidad.get(indice));
		Sessions.getCurrent().setAttribute("id_mantenimiento", id_mantenimiento);
		Sessions.getCurrent().setAttribute("id_opcion", (long) 0);
		Sessions.getCurrent().setAttribute("tipo_solicitud", validarTipoEnEstado(indice));
		window = (Window) Executions.createComponents("/mantenimientos/solicitud/solicitar.zul", null, null);
		mSolicitar.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mSolicitar.setDisabled(false);
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mAccion")
	public void onClick$mAccion() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxLocalidades.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxLocalidades.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaLocalidad.get(indice).getId_localidad(), 7);
		if (solicitud == null) {
			return;
		} else {
			if (solicitud.getId_solicitud() == 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("localidad", listaLocalidad.get(indice));
		if (validarSiExisteSolicitudPendienteEjecucion(listaLocalidad.get(indice).getId_localidad()) == true) {
			if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 2) {
				window = (Window) Executions.createComponents("/mantenimientos/localidad/modificar.zul", null, null);
			} else if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 3) {
				window = (Window) Executions.createComponents("/mantenimientos/localidad/activar.zul", null, null);
			} else if (validarTipoSolicitud(listaLocalidad.get(indice).getId_localidad()) == 4) {
				window = (Window) Executions.createComponents("/mantenimientos/localidad/desactivar.zul", null, null);
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaLocalidad.get(indice).getId_localidad()) == true) {
			window = (Window) Executions.createComponents("/mantenimientos/localidad/modificar.zul", null, null);
		}
		mAccion.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAccion.setDisabled(false);
					buscarLocalidades();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	public int validarTipoEnEstado(int indice) {
		int tipo = 0;
		if (listaLocalidad.get(indice).getEst_localidad().charAt(0) == 'A') {
			tipo = 2;
		}
		if (listaLocalidad.get(indice).getEst_localidad().charAt(0) == 'P') {
			tipo = 1;
		}
		if (listaLocalidad.get(indice).getEst_localidad().charAt(0) == 'I') {
			tipo = 3;
		}
		return tipo;
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/localidad/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarLocalidades();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
