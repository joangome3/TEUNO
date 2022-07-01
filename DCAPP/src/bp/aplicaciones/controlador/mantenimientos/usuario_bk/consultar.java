//package bp.aplicaciones.controlador.mantenimientos.usuario_bk;
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
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
//import bp.aplicaciones.mensajes.Error;
//import bp.aplicaciones.mensajes.Informativos;
//import bp.aplicaciones.mensajes.Validaciones;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class consultar extends SelectorComposer<Component> {
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
//	Listbox lbxUsuarios;
//	@Wire
//	Combobox cmbLimite;
//	@Wire
//	Menuitem mSeguimiento, mSolicitar, mAccion;
//	@Wire
//	Menuseparator mSeparador1;
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
//	List<modelo_usuario_bk> listaUsuario = new ArrayList<modelo_usuario_bk>();
//	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
//
//	long id_mantenimiento = 4;
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
//			cargarUsuarios();
//			txtBuscar.setDisabled(false);
//			lbxUsuarios.setEmptyMessage(informativos.getMensaje_informativo_2());
//		} else {
//			txtBuscar.setDisabled(true);
//			lbxUsuarios.setEmptyMessage(informativos.getMensaje_informativo_22());
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
//	public List<modelo_usuario_bk> obtenerUsuarios() {
//		return listaUsuario;
//	}
//
//	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String criterio = txtBuscar.getText();
//		listaUsuario = consultasABaseDeDatos.cargarUsuarios(criterio, 1,
//				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		binder.loadComponent(lbxUsuarios);
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
//			buscarUsuarios();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	@Listen("onSelect=#cmbLimite")
//	public void onSelect$cmbLimite() {
//		try {
//			buscarUsuarios();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	@Listen("onClick=#btnRefrescar")
//	public void onClick$btnRefrescar() {
//		try {
//			buscarUsuarios();
//		} catch (Exception e) {
//			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
//					Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void buscarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String criterio = txtBuscar.getText();
//		listaUsuario = consultasABaseDeDatos.cargarUsuarios(criterio, 1,
//				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
//		binder.loadComponent(lbxUsuarios);
//	}
//
//	@Listen("onRightClick=#lbxUsuarios")
//	public void onRightClick$lbxUsuarios() throws Throwable {
//		if (lbxUsuarios.getSelectedItem() == null) {
//			return;
//		}
//		int indice = lbxUsuarios.getSelectedIndex();
//		buscarUsuarios();
//		int tamanio_lista = lbxUsuarios.getItemCount();
//		if (indice >= tamanio_lista) {
//			return;
//		}
//		lbxUsuarios.setSelectedIndex(indice);
//		if (validarSiExisteSolicitudCreada(listaUsuario.get(indice).getId_usuario()) == false
//				&& validarSiExisteSolicitudPendienteEjecucion(listaUsuario.get(indice).getId_usuario()) == false
//				&& validarSiExisteSolicitudPendienteActualizacion(listaUsuario.get(indice).getId_usuario()) == false) {
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
//		if (validarSiExisteSolicitudPendienteEjecucion(listaUsuario.get(indice).getId_usuario()) == true) {
//			mSeparador1.setVisible(true);
//			mAccion.setVisible(true);
//			mAccion.setDisabled(false);
//			if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 2) {
//				mAccion.setLabel(" - Actualizar");
//				mAccion.setIconSclass("z-icon-refresh");
//			} else if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 3) {
//				mAccion.setLabel(" - Activar");
//				mAccion.setIconSclass("z-icon-font");
//			} else if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 4) {
//				mAccion.setLabel(" - Inactivar");
//				mAccion.setIconSclass("z-icon-italic");
//			}
//		}
//		if (validarSiExisteSolicitudPendienteActualizacion(listaUsuario.get(indice).getId_usuario()) == true) {
//			mSeparador1.setVisible(true);
//			mAccion.setVisible(true);
//			mAccion.setDisabled(false);
//			mAccion.setLabel(" - Actualizar");
//			mAccion.setIconSclass("z-icon-refresh");
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
//		if (lbxUsuarios.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxUsuarios.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaUsuario.get(indice).getId_usuario(), 7);
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
//		if (lbxUsuarios.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxUsuarios.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaUsuario.get(indice).getId_usuario(), 7);
//		if (solicitud != null) {
//			if (solicitud.getId_solicitud() != 0) {
//				return;
//			}
//		}
//		Sessions.getCurrent().setAttribute("objeto", listaUsuario.get(indice));
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
//		if (lbxUsuarios.getSelectedItem() == null) {
//			return;
//		}
//		if (ingresa_a_accion == true) {
//			return;
//		}
//		int indice = lbxUsuarios.getSelectedIndex();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
//				listaUsuario.get(indice).getId_usuario(), 7);
//		if (solicitud == null) {
//			return;
//		} else {
//			if (solicitud.getId_solicitud() == 0) {
//				return;
//			}
//		}
//		Sessions.getCurrent().setAttribute("usuario", listaUsuario.get(indice));
//		if (validarSiExisteSolicitudPendienteEjecucion(listaUsuario.get(indice).getId_usuario()) == true) {
//			if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 2) {
//				window = (Window) Executions.createComponents("/mantenimientos/usuario/modificar.zul", null, null);
//			} else if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 3) {
//				window = (Window) Executions.createComponents("/mantenimientos/usuario/activar.zul", null, null);
//			} else if (validarTipoSolicitud(listaUsuario.get(indice).getId_usuario()) == 4) {
//				window = (Window) Executions.createComponents("/mantenimientos/usuario/desactivar.zul", null, null);
//			}
//		}
//		if (validarSiExisteSolicitudPendienteActualizacion(listaUsuario.get(indice).getId_usuario()) == true) {
//			window = (Window) Executions.createComponents("/mantenimientos/usuario/modificar.zul", null, null);
//		}
//		mAccion.setDisabled(true);
//		ingresa_a_accion = true;
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					mAccion.setDisabled(false);
//					buscarUsuarios();
//					ingresa_a_accion = false;
//				}
//			});
//		}
//		window.setParent(winList);
//	}
//
//	public int validarTipoEnEstado(int indice) {
//		int tipo = 0;
//		if (listaUsuario.get(indice).getEst_usuario().charAt(0) == 'A') {
//			tipo = 2;
//		}
//		if (listaUsuario.get(indice).getEst_usuario().charAt(0) == 'P') {
//			tipo = 1;
//		}
//		if (listaUsuario.get(indice).getEst_usuario().charAt(0) == 'I') {
//			tipo = 3;
//		}
//		return tipo;
//	}
//
//	@Listen("onClick=#btnNuevo")
//	public void onClick$btnNuevo() {
//		window = (Window) Executions.createComponents("/mantenimientos/usuario/nuevo.zul", null, null);
//		btnNuevo.setDisabled(true);
//		if (window instanceof Window) {
//			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
//				@Override
//				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
//					btnNuevo.setDisabled(false);
//					buscarUsuarios();
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
