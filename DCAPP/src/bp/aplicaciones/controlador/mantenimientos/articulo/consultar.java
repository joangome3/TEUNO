package bp.aplicaciones.controlador.mantenimientos.articulo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
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
	Button btnNuevo, btnRefrescar, btnCargar, btnBorrar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxArticulos, lbxEstados;
	@Wire
	Combobox cmbLimite;
	@Wire
	Div dEstado;
	@Wire
	Popup pop1, pop2;
	@Wire
	Menuitem mSeguimiento, mSolicitar, mAccion, mDetalle, mFoto;
	@Wire
	Menuseparator mSeparador1;
	@Wire
	Div winList;

	Window window;

	int counter = 0;

	Image img = new Image();

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

	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	long id_mantenimiento = 6;

	byte[] buffer = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		inicializarPermisos();
		if (consultar.equals("S")) {
			try {
				cargarArticulos();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtBuscar.setDisabled(false);
			lbxArticulos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxArticulos.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
		} else {
			btnNuevo.setDisabled(true);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
		cargarEstados("", 1, id_dc);
		cargarParametros();
	}

	public List<modelo_estado_articulo> obtenerEstados() {
		return listaEstados;
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
	}

	public void cargarEstados(String criterio, int tipo, long localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_articulo dao = new dao_estado_articulo();
		try {
			listaEstados = dao.obtenerEstados(criterio, tipo, String.valueOf(localidad));
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarArticulos() throws WrongValueException, Throwable {
		dao_articulo dao = new dao_articulo();
		String criterio = "";
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), "", 1,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarParametros() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getActivar_guardar_imagen().equals("S")) {
				btnCargar.setDisabled(false);
			} else {
				btnCargar.setDisabled(true);
			}
			if (listaParametros.get(0).getActivar_borrar_imagen().equals("S")) {
				btnBorrar.setDisabled(false);
			} else {
				btnBorrar.setDisabled(true);
			}
		} else {
			btnCargar.setDisabled(true);
			btnBorrar.setDisabled(true);
		}
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
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

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event) throws WrongValueException, Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			Messagebox.show("Seleccione un item de la lista.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		cargarParametros();
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getActivar_guardar_imagen().equals("S")) {
				if (lbxArticulos.getSelectedItem() == null) {
					Messagebox.show("Seleccione el artículo al que le desea cargar la imagen.", ".:: Cargar imagen ::.",
							Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				int indice = lbxArticulos.getSelectedIndex();
				org.zkoss.util.media.Media media = event.getMedia();
				if (media == null) {
					Messagebox.show("No se añadio alguna imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
							Messagebox.INFORMATION);
					return;
				}
				if ((media.getName().indexOf(".jpg") == -1) && (media.getName().indexOf(".jpeg") == -1)
						&& (media.getName().indexOf(".png") == -1) && (media.getName().indexOf(".JPG") == -1)
						&& (media.getName().indexOf(".PNG") == -1) && (media.getName().indexOf(".JPEG") == -1)) {
					Messagebox.show("La imagen debe tener el formato (.jpg), (.jpeg), (.png).", ".:: Cargar Imagen ::.",
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				buffer = media.getByteData();
				java.util.Date date1 = null;
				Timestamp timestamp1 = null;
				date1 = new Date();
				timestamp1 = new Timestamp(date1.getTime());
				modelo_articulo articulo = new modelo_articulo();
				articulo.setId_articulo(listaArticulo.get(indice).getId_articulo());
				articulo.setImg_articulo(new SerialBlob(buffer));
				articulo.setUsu_modifica(user);
				articulo.setFec_modifica(timestamp1);
				dao_articulo dao = new dao_articulo();
				try {
					dao.actualizarImagenArticulo(articulo);
					Messagebox.show("La imagen se cargó correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
							Messagebox.INFORMATION);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Messagebox.show("La imagen no se cargó correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
							Messagebox.INFORMATION);
				}
				buscarArticulos();
			} else {
				Messagebox.show("No tiene permisos para cargar imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("No tiene permisos para cargar imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	@Listen("onClick=#btnBorrar")
	public void onClick$btnBorrar() throws WrongValueException, Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			Messagebox.show("Seleccione un item de la lista.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		cargarParametros();
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getActivar_borrar_imagen().equals("S")) {
				if (lbxArticulos.getSelectedItem() == null) {
					Messagebox.show("Seleccione el artículo al que le desea borrar la imagen.", ".:: Borrar imagen ::.",
							Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				int indice = lbxArticulos.getSelectedIndex();
				java.util.Date date1 = null;
				Timestamp timestamp1 = null;
				date1 = new Date();
				timestamp1 = new Timestamp(date1.getTime());
				modelo_articulo articulo = new modelo_articulo();
				articulo.setId_articulo(listaArticulo.get(indice).getId_articulo());
				articulo.setImg_articulo(null);
				articulo.setUsu_modifica(user);
				articulo.setFec_modifica(timestamp1);
				dao_articulo dao = new dao_articulo();
				try {
					dao.actualizarImagenArticulo(articulo);
					Messagebox.show("La imagen se borró correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
							Messagebox.INFORMATION);
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Messagebox.show("La imagen no se borró correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
							Messagebox.INFORMATION);
				}
				buscarArticulos();
			} else {
				Messagebox.show("No tiene permisos para borrar imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("No tiene permisos para borrar imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public List<modelo_articulo> obtenerArticulos() {
		return listaArticulo;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarArticulos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		String criterio = txtBuscar.getText();
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), "", 1,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onRightClick=#lbxArticulos")
	public void onRightClick$lbxArticulos() throws Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		buscarArticulos();
		int tamanio_lista = lbxArticulos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxArticulos.setSelectedIndex(indice);
		if (validarSiExisteSolicitudCreada(listaArticulo.get(indice).getId_articulo()) == false
				&& validarSiExisteSolicitudPendienteEjecucion(listaArticulo.get(indice).getId_articulo()) == false
				&& validarSiExisteSolicitudPendienteActualizacion(
						listaArticulo.get(indice).getId_articulo()) == false) {
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
		if (validarSiExisteSolicitudPendienteEjecucion(listaArticulo.get(indice).getId_articulo()) == true) {
			mSeparador1.setVisible(true);
			mAccion.setVisible(true);
			mAccion.setDisabled(false);
			if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 2) {
				mAccion.setLabel(" - Actualizar");
				mAccion.setIconSclass("z-icon-refresh");
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 3) {
				mAccion.setLabel(" - Activar");
				mAccion.setIconSclass("z-icon-font");
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 4) {
				mAccion.setLabel(" - Inactivar");
				mAccion.setIconSclass("z-icon-italic");
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaArticulo.get(indice).getId_articulo()) == true) {
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
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaArticulo.get(indice).getId_articulo(), 7);
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
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaArticulo.get(indice).getId_articulo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_solicitud() != 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("objeto", listaArticulo.get(indice));
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
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaArticulo.get(indice).getId_articulo(), 7);
		if (solicitud == null) {
			return;
		} else {
			if (solicitud.getId_solicitud() == 0) {
				return;
			}
		}
		Sessions.getCurrent().setAttribute("articulo", listaArticulo.get(indice));
		if (validarSiExisteSolicitudPendienteEjecucion(listaArticulo.get(indice).getId_articulo()) == true) {
			if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 2) {
				window = (Window) Executions.createComponents("/mantenimientos/articulo/modificar.zul", null, null);
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 3) {
				window = (Window) Executions.createComponents("/mantenimientos/articulo/activar.zul", null, null);
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 4) {
				window = (Window) Executions.createComponents("/mantenimientos/articulo/desactivar.zul", null, null);
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaArticulo.get(indice).getId_articulo()) == true) {
			window = (Window) Executions.createComponents("/mantenimientos/articulo/modificar.zul", null, null);
		}
		mAccion.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAccion.setDisabled(false);
					buscarArticulos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	public int validarTipoEnEstado(int indice) {
		int tipo = 0;
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'A') {
			tipo = 2;
		}
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'P') {
			tipo = 1;
		}
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'I') {
			tipo = 3;
		}
		return tipo;
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/articulo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					buscarArticulos();
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mDetalle")
	public void onClick$mDetalle() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("articulo", listaArticulo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/articulo/detalle.zul", null, null);
		mDetalle.setDisabled(true);
		lbxArticulos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mDetalle.setDisabled(false);
					lbxArticulos.setDisabled(false);
					buscarArticulos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mFoto")
	public void onClick$mFoto() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("articulo", listaArticulo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/articulo/foto.zul", null, null);
		mFoto.setDisabled(true);
		lbxArticulos.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mFoto.setDisabled(false);
					lbxArticulos.setDisabled(false);
					buscarArticulos();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
