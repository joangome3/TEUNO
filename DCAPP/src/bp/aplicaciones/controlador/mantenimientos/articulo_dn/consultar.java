package bp.aplicaciones.controlador.mantenimientos.articulo_dn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.ConsultasEnEnums;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar, txtBuscarUbicacion;
	@Wire
	Listbox lbxArticulos;
	@Wire
	Bandbox bdxUbicacion;
	@Wire
	Listbox lbxUbicaciones;
	@Wire
	Combobox cmbCategoria, cmbLimite, cmbEstado, cmbEmpresa1, cmbTipoUbicacion;
	@Wire
	Menuitem mSeguimiento, mSolicitar, mAccion, mModificar, mActivar, mInactivar;
	@Wire
	Menuseparator mSeparador1;
	@Wire
	Div winList;

	Window window;

	boolean ingresa_a_accion = false;

	int counter = 0;

	int tipo_accion = 0;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	String estado = "";

	validar_datos validar = new validar_datos();

	List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
	List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();
	List<modelo_empresa> listaEmpresa1 = new ArrayList<modelo_empresa>();
	List<modelo_tipo_ubicacion> listaTipoUbicacion = new ArrayList<modelo_tipo_ubicacion>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	ConsultasEnEnums consultasEnEnums = new ConsultasEnEnums();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	long id_mantenimiento = 16;

	int accion = 1;

	long id_opcion = 4;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxUbicaciones.setEmptyMessage(informativos.getMensaje_informativo_2());
		cmbLimite.setSelectedIndex(2);
		cmbEstado.setSelectedIndex(0);
		cargarPerfil();
		cargarEmpresas1();
		cargarTipoUbicaciones();
		inicializarPermisos();
		if (consultar.equals("S")) {
			try {
				inicializarListas();
			} catch (Throwable e) {
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
				txtBuscar.setText(txtBuscar.getText().toUpperCase().trim());
			}
		});
	}

	public List<modelo_articulo_dn> obtenerArticulos() {
		return listaArticulo;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public List<modelo_empresa> obtenerEmpresas1() {
		return listaEmpresa1;
	}

	public List<modelo_tipo_ubicacion> obtenerTipoUbicacion() {
		return listaTipoUbicacion;
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
	}

	public void inicializarListas() throws WrongValueException, Throwable {
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
				cmbEstado.getSelectedItem().getValue().toString(), 1,
				Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), "0", "0");
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN("", 0, "", "", 0);
		listaCapacidad = consultasABaseDeDatos.cargarCapacidadesDN("", 1, 0, 0);
		listaCategoria = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 10);
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
		binder.loadComponent(cmbCategoria);
		binder.loadComponent(lbxUbicaciones);
		binder.loadComponent(lbxArticulos);
	}

	public void cargarUbicaciones(String criterio, long empresa, long tipo_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_ubicacion_dn dao = new dao_ubicacion_dn();
		try {
			listaUbicacion = dao.obtenerUbicaciones(criterio, String.valueOf(id_dc), 7, empresa, tipo_ubicacion, 0, 0);
			binder.loadComponent(lbxUbicaciones);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	public void cargarEmpresas1() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa1 = consultasABaseDeDatos.cargarEmpresas("", 2, String.valueOf(id_dc), String.valueOf(id_opcion),
				0);
		binder.loadComponent(cmbEmpresa1);
	}

	public void cargarTipoUbicaciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		String criterio = "";
		try {
			listaTipoUbicacion = dao.obtenerTipoUbicaciones(criterio, 0, 1);
			binder.loadComponent(cmbTipoUbicacion);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicacions. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws Throwable {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws Throwable {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws Throwable {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbCategoria")
	public void onSelect$cmbCategoria() throws Throwable {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws Throwable {
		try {
			buscarArticulos();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEmpresa1")
	public void onSelect$cmbEmpresa1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		long empresa = 0, tipo_ubicacion = 0;
		if (cmbEmpresa1.getSelectedItem() == null) {
			empresa = 0;
		} else {
			empresa = Long.valueOf(cmbEmpresa1.getSelectedItem().getValue().toString());
		}
		cmbTipoUbicacion.setText("");
		if (cmbTipoUbicacion.getSelectedItem() == null) {
			tipo_ubicacion = 0;
		} else {
			tipo_ubicacion = Long.valueOf(cmbTipoUbicacion.getSelectedItem().getValue().toString());
		}
		cargarUbicaciones(txtBuscarUbicacion.getText().toString(), empresa, tipo_ubicacion);
		txtBuscarUbicacion.setTooltiptext("");
	}

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOK$txtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		long empresa = 0, tipo_ubicacion = 0;
		if (cmbEmpresa1.getSelectedItem() == null) {
			empresa = 0;
		} else {
			empresa = Long.valueOf(cmbEmpresa1.getSelectedItem().getValue().toString());
		}
		if (cmbTipoUbicacion.getSelectedItem() == null) {
			tipo_ubicacion = 0;
		} else {
			tipo_ubicacion = Long.valueOf(cmbTipoUbicacion.getSelectedItem().getValue().toString());
		}
		cargarUbicaciones(txtBuscarUbicacion.getText().toString(), empresa, tipo_ubicacion);
		txtBuscarUbicacion.setTooltiptext("");
	}

	@Listen("onSelect=#cmbTipoUbicacion")
	public void onSelect$cmbTipoUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		long empresa = 0, tipo_ubicacion = 0;
		if (cmbEmpresa1.getSelectedItem() == null) {
			empresa = 0;
		} else {
			empresa = Long.valueOf(cmbEmpresa1.getSelectedItem().getValue().toString());
		}
		if (cmbTipoUbicacion.getSelectedItem() == null) {
			tipo_ubicacion = 0;
		} else {
			tipo_ubicacion = Long.valueOf(cmbTipoUbicacion.getSelectedItem().getValue().toString());
		}
		cargarUbicaciones(txtBuscarUbicacion.getText().toString(), empresa, tipo_ubicacion);
		txtBuscarUbicacion.setTooltiptext("");
	}

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelectlbxUbicaciones() throws Throwable {
		if (lbxUbicaciones.getSelectedItem() == null) {
			bdxUbicacion.setText("");
			bdxUbicacion.setTooltiptext("");
		} else {
			int index = lbxUbicaciones.getSelectedIndex();
			bdxUbicacion.setText(listaUbicacion.get(index).getNom_ubicacion() + " - "
					+ listaUbicacion.get(index).getPos_ubicacion());
			bdxUbicacion.setTooltiptext(listaUbicacion.get(index).getNom_ubicacion() + " - "
					+ listaUbicacion.get(index).getPos_ubicacion());
		}
		buscarArticulos();
	}

	public void buscarArticulos() throws WrongValueException, Throwable {
		String criterio = txtBuscar.getText().trim();
		long id_categoria = 0;
		long id_ubicacion = 0;
		if (cmbCategoria.getSelectedItem() != null) {
			id_categoria = Long.valueOf(cmbCategoria.getSelectedItem().getValue().toString());
		}
		if (lbxUbicaciones.getSelectedItem() != null) {
			id_ubicacion = listaUbicacion.get(lbxUbicaciones.getSelectedIndex()).getId_ubicacion();
		}
		if (txtBuscar.getText().length() <= 0) {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(criterio, id_dc,
					cmbEstado.getSelectedItem().getValue().toString(), 1,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), String.valueOf(id_categoria),
					String.valueOf(id_ubicacion));
		}
		if (!txtBuscar.getValue().equals("")) {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(criterio, id_dc,
					cmbEstado.getSelectedItem().getValue().toString(), 1,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), String.valueOf(id_categoria),
					String.valueOf(id_ubicacion));
		}
		binder.loadComponent(lbxArticulos);
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
		int indice = lbxArticulos.getSelectedIndex();
		if (!listaArticulo.get(indice).getEst_articulo().equals("PAC")) {
			if (validarSiExisteSolicitudCreada(listaArticulo.get(indice).getId_articulo()) == false) {
				if (validarSiExisteSolicitudPendienteActualizacion(
						listaArticulo.get(indice).getId_articulo()) == false) {
					Messagebox.show(informativos.getMensaje_informativo_108(), informativos.getMensaje_informativo_24(),
							Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		if (ingresa_a_accion == true) {
			return;
		}
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaArticulo.get(indice).getId_articulo(), 7);
		if (solicitud == null) {
			Messagebox.show(informativos.getMensaje_informativo_109(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			if (solicitud.getId_solicitud() == 0) {
				Messagebox.show(informativos.getMensaje_informativo_109(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
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
		if (lbxArticulos.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (!listaArticulo.get(indice).getEst_articulo().equals("PAC")) {
			if (validarSiExisteSolicitudCreada(listaArticulo.get(indice).getId_articulo()) == false) {
				if (validarSiExisteSolicitudPendienteActualizacion(
						listaArticulo.get(indice).getId_articulo()) == true) {
					Messagebox.show(informativos.getMensaje_informativo_110(), informativos.getMensaje_informativo_24(),
							Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				} else {
					Messagebox.show(informativos.getMensaje_informativo_108(), informativos.getMensaje_informativo_24(),
							Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		if (ingresa_a_accion == true) {
			return;
		}
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				listaArticulo.get(indice).getId_articulo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_solicitud() != 0) {
				Messagebox.show(informativos.getMensaje_informativo_110(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
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

	@Listen("onClick=#mActivar")
	public void onClick$mActivar() throws WrongValueException, Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		List<modelo_articulo_dn> _listaArticulo = new ArrayList<modelo_articulo_dn>();
		Iterator<Listitem> it = lbxArticulos.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_articulo_dn registro = new modelo_articulo_dn();
			Listitem item = it.next();
			int indice = item.getIndex();
			registro = listaArticulo.get(indice);
			if (registro.getEst_articulo().charAt(0) == 'I' && registro.getId_categoria() == 2) {
				modelo_articulo_dn articulo_dn = new modelo_articulo_dn();
				articulo_dn = registro.clone();
				articulo_dn.setEst_articulo("AE");
				articulo_dn.setUsu_modifica(user);
				articulo_dn.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
				_listaArticulo.add(articulo_dn);
			}
		}
		if (_listaArticulo.size() > 0) {
			dao_articulo_dn dao = new dao_articulo_dn();
			dao.activarDesactivarArticulo(_listaArticulo);
			buscarArticulos();
			Messagebox.show(informativos.getMensaje_informativo_89(), informativos.getMensaje_informativo_21(),
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			Messagebox.show(informativos.getMensaje_informativo_91(), informativos.getMensaje_informativo_21(),
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#mModificar")
	public void onClick$mModificar() throws WrongValueException, Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		if (lbxArticulos.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'I') {
			Messagebox.show(informativos.getMensaje_informativo_92(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaArticulo.get(indice).getEst_articulo().equals("PAC")) {
			if (validarSiExisteSolicitudPendienteActualizacion(listaArticulo.get(indice).getId_articulo()) == false) {
				Messagebox.show(informativos.getMensaje_informativo_107(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (ingresa_a_accion == true) {
			return;
		}
		Sessions.getCurrent().setAttribute("articulo", listaArticulo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/modificar.zul", null, null);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAccion.setDisabled(false);
					try {
						buscarArticulos();
						lbxArticulos.setSelectedIndex(indice);
					} catch (Throwable e) {
						e.printStackTrace();
					}
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mInactivar")
	public void onClick$mInactivar() throws WrongValueException, Throwable {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		List<modelo_articulo_dn> _listaArticulo = new ArrayList<modelo_articulo_dn>();
		Iterator<Listitem> it = lbxArticulos.getSelectedItems().iterator();
		while (it.hasNext()) {
			modelo_articulo_dn registro = new modelo_articulo_dn();
			Listitem item = it.next();
			int indice = item.getIndex();
			registro = listaArticulo.get(indice);
			if (registro.getEst_articulo().charAt(0) == 'A' && registro.getId_categoria() == 2) {
				modelo_articulo_dn articulo_dn = new modelo_articulo_dn();
				articulo_dn = registro.clone();
				articulo_dn.setEst_articulo("IE");
				articulo_dn.setUsu_modifica(user);
				articulo_dn.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
				_listaArticulo.add(articulo_dn);
			}
		}
		if (_listaArticulo.size() > 0) {
			dao_articulo_dn dao = new dao_articulo_dn();
			dao.activarDesactivarArticulo(_listaArticulo);
			buscarArticulos();
			Messagebox.show(informativos.getMensaje_informativo_88(), informativos.getMensaje_informativo_23(),
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			Messagebox.show(informativos.getMensaje_informativo_90(), informativos.getMensaje_informativo_23(),
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
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
				window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/modificar.zul", null, null);
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 3) {
				window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/activar.zul", null, null);
			} else if (validarTipoSolicitud(listaArticulo.get(indice).getId_articulo()) == 4) {
				window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/desactivar.zul", null, null);
			}
		}
		if (validarSiExisteSolicitudPendienteActualizacion(listaArticulo.get(indice).getId_articulo()) == true) {
			window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/modificar.zul", null, null);
		}
		mAccion.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAccion.setDisabled(false);
					try {
						buscarArticulos();
					} catch (Throwable e) {
						e.printStackTrace();
					}
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
		window = (Window) Executions.createComponents("/mantenimientos/articulo_dn/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					try {
						buscarArticulos();
					} catch (WrongValueException e) {
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
