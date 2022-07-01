//package bp.aplicaciones.controlador.mantenimientos.solicitante;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.EventListener;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Menuitem;
//import org.zkoss.zul.Menuseparator;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Div;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
//import bp.aplicaciones.extensiones.Fechas;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
//import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
//import bp.aplicaciones.mensajes.Error;
//import bp.aplicaciones.mensajes.Informativos;
//import bp.aplicaciones.mensajes.Validaciones;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class consultar_bk extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zConsultar;
//	@Wire
//	Button btnNuevo, btnRefrescar;
//	@Wire
//	Textbox txtBuscar;
//	@Wire
//	Listbox lbxSolicitantes;
//	@Wire
//	Combobox cmbLimite;
//	@Wire
//	Menuitem mSeguimiento, mSolicitar, mAccion, mRelacionar;
//	@Wire
//	Menuseparator mSeparador1, mSeparador2;
//	@Wire
//	Div winList;
//
//	Window window;
//
//	boolean ingresa_a_accion = false;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	validar_datos validar = new validar_datos();
//
//	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
//	Fechas fechas = new Fechas();
//	Validaciones validaciones = new Validaciones();
//
//	Informativos informativos = new Informativos();
//	Error error = new Error();
//
//	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;
//
//	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
//	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
//
//	long id_mantenimiento = 1;
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		cmbLimite.setSelectedIndex(1);
//		cargarPerfil();
//		inicializarPermisos();
//		if (consultar.equals("S")) {
//			cargarSolicitantes();
//			txtBuscar.setDisabled(false);
//			lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_2());
//		} else {
//			txtBuscar.setDisabled(true);
//			lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_22());
//		}
//		if (insertar.equals("S")) {
//			btnNuevo.setDisabled(false);
//		} else {
//			btnNuevo.setDisabled(true);
//		}
//		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				txtBuscar.setText(txtBuscar.getText().toUpperCase());
//			}
//		});
//	}
//
//	public List<modelo_solicitante> obtenerSolicitantes() {
//		return listaSolicitante;
//	}
//
//	public void cargarSolicitantes() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String criterio = txtBuscar.getText();
//		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes(criterio, 0, String.valueOf(id_dc), "",
//				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		binder.loadComponent(lbxSolicitantes);
//	}
//
//	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
//		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
//	}
//
//	public void inicializarPermisos() {
//		if (listaPerfil.size() == 1) {
//			if (listaPerfil.get(0).getConsultar().equals("S")) {
//				consultar = "S";
//			} else {
//				consultar = "N";
//			}
//			if (listaPerfil.get(0).getInsertar().equals("S")) {
//				insertar = "S";
//			} else {
//				insertar = "N";
//			}
//			if (listaPerfil.get(0).getModificar().equals("S")) {
//				modificar = "S";
//			} else {
//				modificar = "N";
//			}
//			if (listaPerfil.get(0).getRelacionar().equals("S")) {
//				relacionar = "S";
//			} else {
//				relacionar = "N";
//			}
//			if (listaPerfil.get(0).getDesactivar().equals("S")) {
//				desactivar = "S";
//			} else {
//				desactivar = "N";
//			}
//			if (listaPerfil.get(0).getEliminar().equals("S")) {
//				eliminar = "S";
//			} else {
//				eliminar = "N";
//			}
//			if (listaPerfil.get(0).getSolicitar().equals("S")) {
//				solicitar = "S";
//			} else {
//				solicitar = "N";
//			}
//			if (listaPerfil.get(0).getRevisar().equals("S")) {
//				revisar = "S";
//			} else {
//				revisar = "N";
//			}
//			if (listaPerfil.get(0).getAprobar().equals("S")) {
//				aprobar = "S";
//			} else {
//				aprobar = "N";
//			}
//			if (listaPerfil.get(0).getEjecutar().equals("S")) {
//				ejecutar = "S";
//			} else {
//				ejecutar = "N";
//			}
//		} else {
//			Messagebox.show(error.getMensaje_error_3(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//			return;
//		}
//	}
//
//	@Listen("onOK=#txtBuscar")
//	public void onOK$txtBuscar() {
//		try {
//			buscarSolicitantes();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	@Listen("onSelect=#cmbLimite")
//	public void onSelect$cmbLimite() {
//		try {
//			buscarSolicitantes();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	@Listen("onClick=#btnRefrescar")
//	public void onClick$btnRefrescar() {
//		try {
//			buscarSolicitantes();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void buscarSolicitantes() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String criterio = txtBuscar.getText();
//		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes(criterio, 0, String.valueOf(id_dc), "",
//				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		binder.loadComponent(lbxSolicitantes);
//	}
//
//	@Listen("onRightClick=#lbxSolicitantes")
//	public void onRightClick$lbxSolicitantes() throws Throwable {
//		if (lbxSolicitantes.getSelectedItem() == null) {
//			return;
//		}
//		int indice = lbxSolicitantes.getSelectedIndex();
//		buscarSolicitantes();
//		int tamanio_lista = lbxSolicitantes.getItemCount();
//		if (indice >= tamanio_lista) {
//			return;
//		}
//		lbxSolicitantes.setSelectedIndex(indice);
//		if (validarSiExisteSolicitudCreada(listaSolicitante.get(indice).getId_solicitante()) == false
//				&& validarSiExisteSolicitudPendienteEjecucion(listaSolicitante.get(indice).getId_solicitante()) == false
//				&& validarSiExisteSolicitudPendienteActualizacion(
//						listaSolicitante.get(indice).getId_solicitante()) == false) {
//			mSeguimiento.setVisible(false);
//			mSeguimiento.setDisabled(true);
//			mSeparador1.setVisible(false);
//			mSolicitar.setVisible(true);
//			mSolicitar.setDisabled(false);
//			mAccion.setVisible(false);
//			mAccion.setDisabled(true);
//		} else {
//			mSeguimiento.setVisible(true);
//			mSeguimiento.setDisabled(false);
//			mSeparador1.setVisible(false);
//			mSolicitar.setVisible(false);
//			mSolicitar.setDisabled(true);
//			mAccion.setVisible(false);
//			mAccion.setDisabled(true);
//		}
//		if (validarSiExisteSolicitudPendienteEjecucion(listaSolicitante.get(indice).getId_solicitante()) == true) {
//			mSeparador1.setVisible(true);
//			mAccion.setVisible(true);
//			mAccion.setDisabled(false);
//			if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 2) {
//				mAccion.setLabel(" - Actualizar");
//				mAccion.setIconSclass("z-icon-refresh");
//			} else if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 3) {
//				mAccion.setLabel(" - Activar");
//				mAccion.setIconSclass("z-icon-font");
//			} else if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 4) {
//				mAccion.setLabel(" - Inactivar");
//				mAccion.setIconSclass("z-icon-italic");
//			}
//		}
//		if (validarSiExisteSolicitudPendienteActualizacion(listaSolicitante.get(indice).getId_solicitante()) == true) {
//			mSeparador1.setVisible(true);
//			mAccion.setVisible(true);
//			mAccion.setDisabled(false);
//			mAccion.setLabel(" - Actualizar");
//			mAccion.setIconSclass("z-icon-refresh");
//		}
//		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) != 'P') {
//			mSeparador2.setVisible(true);
//			mRelacionar.setVisible(true);
//			mRelacionar.setDisabled(false);
//		} else {
//			mSeparador2.setVisible(false);
//			mRelacionar.setVisible(false);
//			mRelacionar.setDisabled(true);
//		}
//	}
//
//	public boolean validarSiExisteSolicitudCreada(long id_registro)
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		boolean existe_solicitud_creada = false;
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
//		if (solicitud != null) {
//			String estado = solicitud.getEst_solicitud();
//			if (estado != null) {
//				if (estado.equals("P") || estado.equals("R")) {
//					existe_solicitud_creada = true;
//				}
//			}
//		}
//		return existe_solicitud_creada;
//	}
//
//	public boolean validarSiExisteSolicitudPendienteEjecucion(long id_registro)
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		boolean existe_solicitud_pendiente = false;
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
//		if (solicitud != null) {
//			String estado = solicitud.getEst_solicitud();
//			if (estado != null) {
//				if (estado.equals("S")) {
//					existe_solicitud_pendiente = true;
//				}
//			}
//		}
//		return existe_solicitud_pendiente;
//	}
//
//	public boolean validarSiExisteSolicitudPendienteActualizacion(long id_registro)
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		boolean existe_solicitud_pendiente = false;
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
//		if (solicitud != null) {
//			String estado = solicitud.getEst_solicitud();
//			if (estado != null) {
//				if (estado.equals("T")) {
//					existe_solicitud_pendiente = true;
//				}
//			}
//		}
//		return existe_solicitud_pendiente;
//	}
//
//	public int validarTipoSolicitud(long id_registro)
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		int tipo_solicitud = 0;
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, id_registro, 7);
//		if (solicitud != null) {
//			tipo_solicitud = (int) solicitud.getId_tip_solicitud();
//		}
//		return tipo_solicitud;
//	}
//
//	@Listen("onClick=#mSeguimiento")
//	public void onClick$mSeguimiento() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (lbxSolicitantes.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxSolicitantes.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaSolicitante.get(indice).getId_solicitante(), 7);
//		if (solicitud == null) {
//			return;
//		} else {
//			if (solicitud.getId_solicitud() == 0) {
//				return;
//			}
//		}
//		Sessions.getCurrent().setAttribute("objeto", solicitud);
//		Sessions.getCurrent().setAttribute("id_mantenimiento", id_mantenimiento);
//		Sessions.getCurrent().setAttribute("id_opcion", (long) 0);
//		Sessions.getCurrent().setAttribute("tipo_solicitud", 0);
//		window = (Window) Executions.createComponents("/mantenimientos/solicitud/seguimiento.zul", null, null);
//		mSeguimiento.setDisabled(true);
//		ingresa_a_accion = true;
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					mSeguimiento.setDisabled(false);
//					ingresa_a_accion = false;
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	@Listen("onClick=#mSolicitar")
//	public void onClick$mSolicitar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (lbxSolicitantes.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxSolicitantes.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaSolicitante.get(indice).getId_solicitante(), 7);
//		if (solicitud != null) {
//			if (solicitud.getId_solicitud() != 0) {
//				return;
//			}
//		}
//		Sessions.getCurrent().setAttribute("objeto", listaSolicitante.get(indice));
//		Sessions.getCurrent().setAttribute("id_mantenimiento", id_mantenimiento);
//		Sessions.getCurrent().setAttribute("id_opcion", (long) 0);
//		Sessions.getCurrent().setAttribute("tipo_solicitud", validarTipoEnEstado(indice));
//		window = (Window) Executions.createComponents("/mantenimientos/solicitud/solicitar.zul", null, null);
//		mSolicitar.setDisabled(true);
//		ingresa_a_accion = true;
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					mSolicitar.setDisabled(false);
//					ingresa_a_accion = false;
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	@Listen("onClick=#mAccion")
//	public void onClick$mAccion() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (lbxSolicitantes.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxSolicitantes.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaSolicitante.get(indice).getId_solicitante(), 7);
//		if (solicitud == null) {
//			return;
//		} else {
//			if (solicitud.getId_solicitud() == 0) {
//				return;
//			}
//		}
//		Sessions.getCurrent().setAttribute("solicitante", listaSolicitante.get(indice));
//		if (validarSiExisteSolicitudPendienteEjecucion(listaSolicitante.get(indice).getId_solicitante()) == true) {
//			if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 2) {
//				window = (Window) Executions.createComponents("/mantenimientos/solicitante/modificar.zul", null, null);
//			} else if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 3) {
//				window = (Window) Executions.createComponents("/mantenimientos/solicitante/activar.zul", null, null);
//			} else if (validarTipoSolicitud(listaSolicitante.get(indice).getId_solicitante()) == 4) {
//				window = (Window) Executions.createComponents("/mantenimientos/solicitante/desactivar.zul", null, null);
//			}
//		}
//		if (validarSiExisteSolicitudPendienteActualizacion(listaSolicitante.get(indice).getId_solicitante()) == true) {
//			window = (Window) Executions.createComponents("/mantenimientos/solicitante/modificar.zul", null, null);
//		}
//		mAccion.setDisabled(true);
//		ingresa_a_accion = true;
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					mAccion.setDisabled(false);
//					buscarSolicitantes();
//					ingresa_a_accion = false;
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	public int validarTipoEnEstado(int indice) {
//		int tipo = 0;
//		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'A') {
//			tipo = 2;
//		}
//		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'P') {
//			tipo = 1;
//		}
//		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'I') {
//			tipo = 3;
//		}
//		return tipo;
//	}
//
//	@Listen("onClick=#btnNuevo")
//	public void onClick$btnNuevo() {
//		window = (Window) Executions.createComponents("/mantenimientos/solicitante/nuevo.zul", null, null);
//		btnNuevo.setDisabled(true);
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					btnNuevo.setDisabled(false);
//					buscarSolicitantes();
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	@Listen("onClick=#mRelacionar")
//	public void onClick$mRelacionar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (lbxSolicitantes.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxSolicitantes.getSelectedIndex();
//		Sessions.getCurrent().setAttribute("solicitante", listaSolicitante.get(indice));
//		window = (Window) Executions.createComponents("/mantenimientos/solicitante/relacionar.zul", null, null);
//		mSolicitar.setDisabled(true);
//		ingresa_a_accion = true;
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					mSolicitar.setDisabled(false);
//					ingresa_a_accion = false;
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		txtBuscar.setText("");
//	}
//
//}
