package bp.aplicaciones.controlador.mantenimientos.respaldo;

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
import bp.aplicaciones.mantenimientos.DAO.dao_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
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
	Listbox lbxRespaldos;
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

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 12;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarRespaldos();
			txtBuscar.setDisabled(false);
			lbxRespaldos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxRespaldos.setEmptyMessage(informativos.getMensaje_informativo_22());
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
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public void cargarRespaldos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_respaldo dao = new dao_respaldo();
		String criterio = "";
		try {
			listaRespaldo = dao.obtenerRespaldos(criterio, 0, String.valueOf(id_dc), "",
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
			binder.loadComponent(lbxRespaldos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las respaldos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar respaldo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public List<modelo_respaldo> obtenerRespaldos() {
		return listaRespaldo;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarRespaldos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarRespaldos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarRespaldos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarRespaldos()
			throws ClassNotFoundException, FileNotFoundException, IOException, NumberFormatException, SQLException {
		dao_respaldo dao = new dao_respaldo();
		String criterio = txtBuscar.getText();
		listaRespaldo = dao.obtenerRespaldos(criterio, 0, String.valueOf(id_dc), "",
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxRespaldos);
	}

	@Listen("onRightClick=#lbxRespaldos")
	public void onRightClick$lbxRespaldos() throws Throwable {
		if (lbxRespaldos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxRespaldos.getSelectedIndex();
		buscarRespaldos();
		int tamanio_lista = lbxRespaldos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxRespaldos.setSelectedIndex(indice);
		if (validarSiExisteSolicitudCreada(listaRespaldo.get(indice).getId_respaldo()) == false
				&& validarSiExisteSolicitudPendienteEjecucion(listaRespaldo.get(indice).getId_respaldo()) == false
				&& validarSiExisteSolicitudPendienteActualizacion(
						listaRespaldo.get(indice).getId_respaldo()) == false) {
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
		if (validarSiExisteSolicitudPendienteEjecucion(listaRespaldo.get(indice).getId_respaldo()) == true) {
			mSeparador1.setVisible(true);
			mAccion.setVisible(true);
			mAccion.setDisabled(false);
			if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 2) {
				mAccion.setLabel(" - Actualizar");
				mAccion.setIconSclass("z-icon-refresh");
			} else if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 3) {
				mAccion.setLabel(" - Activar");
				mAccion.setIconSclass("z-icon-font");
			} else if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 4) {
				mAccion.setLabel(" - Inactivar");
				mAccion.setIconSclass("z-icon-italic");
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaRespaldo.get(indice).getId_respaldo()) == true) {
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
		if (lbxRespaldos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxRespaldos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaRespaldo.get(indice).getId_respaldo(), 7);
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
		if (lbxRespaldos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxRespaldos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaRespaldo.get(indice).getId_respaldo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_solicitud() != 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("objeto", listaRespaldo.get(indice));
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
		if (lbxRespaldos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxRespaldos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaRespaldo.get(indice).getId_respaldo(), 7);
		if (solicitud == null) {
			return;
		} else {
			if (solicitud.getId_solicitud() == 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("respaldo", listaRespaldo.get(indice));
		if (validarSiExisteSolicitudPendienteEjecucion(listaRespaldo.get(indice).getId_respaldo()) == true) {
			if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 2) {
				window = (Window) Executions.createComponents("/mantenimientos/respaldo/modificar.zul", null, null);
			} else if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 3) {
				window = (Window) Executions.createComponents("/mantenimientos/respaldo/activar.zul", null, null);
			} else if (validarTipoSolicitud(listaRespaldo.get(indice).getId_respaldo()) == 4) {
				window = (Window) Executions.createComponents("/mantenimientos/respaldo/desactivar.zul", null, null);
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaRespaldo.get(indice).getId_respaldo()) == true) {
			window = (Window) Executions.createComponents("/mantenimientos/respaldo/modificar.zul", null, null);
		}
		mAccion.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAccion.setDisabled(false);
					buscarRespaldos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	public int validarTipoEnEstado(int indice) {
		int tipo = 0;
		if (listaRespaldo.get(indice).getEst_respaldo().charAt(0) == 'A') {
			tipo = 2;
		}
		if (listaRespaldo.get(indice).getEst_respaldo().charAt(0) == 'P') {
			tipo = 1;
		}
		if (listaRespaldo.get(indice).getEst_respaldo().charAt(0) == 'I') {
			tipo = 3;
		}
		return tipo;
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/respaldo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarRespaldos();
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
