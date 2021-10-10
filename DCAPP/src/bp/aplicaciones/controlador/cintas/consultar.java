package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_detalle_dn;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar, txtBuscarDetalle;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Listbox lbxMovimientos, lbxDetalleMovimientoAnterior, lbxDetalleMovimientoActual;
	@Wire
	Combobox cmbLimite, cmbEstado, cmbTipoBusqueda;
	@Wire
	Menupopup editPopup;
	@Wire
	Menuitem mValidar;
	@Wire
	Div winList;

	Window window;

	long id_opcion = 4;

	Date fecha_actual = null;

	boolean ingresa_a_revisar_registro = false;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_movimiento_dn> listaMovimiento = new ArrayList<modelo_movimiento_dn>();
	List<modelo_movimiento_detalle_dn> listaMovimientoDetalle = new ArrayList<modelo_movimiento_detalle_dn>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbTipoBusqueda.setSelectedIndex(0);
		cmbLimite.setSelectedIndex(3);
		cmbEstado.setSelectedIndex(0);
		inicializarListas();
		inicializarPermisos();
		setearFechaActual();
		setearFechaInicio();
		setearFechaFin();
		if (consultar.equals("S")) {
			setearMovimientos();
			txtBuscar.setDisabled(false);
			lbxMovimientos.setEmptyMessage(informativos.getMensaje_informativo_2());
			lbxDetalleMovimientoAnterior.setEmptyMessage(informativos.getMensaje_informativo_2());
			lbxDetalleMovimientoActual.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxMovimientos.setEmptyMessage(informativos.getMensaje_informativo_22());
			lbxDetalleMovimientoAnterior.setEmptyMessage(informativos.getMensaje_informativo_22());
			lbxDetalleMovimientoActual.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase().trim());
			}
		});
		txtBuscarDetalle.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarDetalle.setText(txtBuscarDetalle.getText().toUpperCase().trim());
			}
		});
	}

	public List<modelo_perfil> obtenerPerfil() {
		return listaPerfil;
	}

	public List<modelo_movimiento_dn> obtenerMovimientos() {
		return listaMovimiento;
	}

	public List<modelo_movimiento_detalle_dn> obtenerDetalleMovimientos() {
		return listaMovimientoDetalle;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaInicio() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		LocalDateTime localDateTime = null;
		LocalDate localDate = primerDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, 1, 1, 0, 0);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaInicio.setValue(d);
	}

	public void setearFechaFin() {
		Date fechaActual = new Date();
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		LocalDateTime localDateTime = null;
		LocalDate localDate = ultimoDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, 12, 31, 23, 59);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaFin.setValue(d);
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

	public void setearMovimientos()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText().trim();
		String tipo_busqueda = "LP";
		String estado = "";
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (tipo_busqueda.equals("LP")) {
			listaMovimiento = consultasABaseDeDatos.cargarMovimientosDN(criterio, fecha_inicio, fecha_fin,
					String.valueOf(id_dc), Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 2,
					estado);
		}
		binder.loadComponent(lbxMovimientos);
	}

	public void consultarMovimientos()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText().trim();
		String estado = "";
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		buscarMovimientos(criterio, fecha_inicio, fecha_fin, estado);
	}

	public void consultarDetalleMovimientos()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText().trim();
		String estado = "";
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}

		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		buscarDetalleMovimientos(criterio, fecha_inicio, fecha_fin, estado);
	}

	public void consultarDetalleMovimientos1()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscarDetalle.getText().trim();
		String estado = "";
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxMovimientos.getSelectedIndex();
		fecha_inicio = String.valueOf(listaMovimiento.get(indice).getId_movimiento());
		buscarDetalleMovimientos1(criterio, fecha_inicio, fecha_fin, estado);
	}

	public void buscarMovimientos(String criterio, String fecha_inicio, String fecha_fin, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaMovimiento = consultasABaseDeDatos.cargarMovimientosDN(criterio, fecha_inicio, fecha_fin,
				String.valueOf(id_dc), Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 2, estado);
		binder.loadComponent(lbxMovimientos);
		listaMovimientoDetalle = new ArrayList<modelo_movimiento_detalle_dn>();
		binder.loadComponent(lbxDetalleMovimientoAnterior);
		binder.loadComponent(lbxDetalleMovimientoActual);
	}

	public void buscarDetalleMovimientos(String criterio, String fecha_inicio, String fecha_fin, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaMovimientoDetalle = consultasABaseDeDatos.cargarDetalleMovimientosDN(criterio, fecha_inicio, fecha_fin,
				String.valueOf(id_dc), Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 1, estado);
		binder.loadComponent(lbxDetalleMovimientoAnterior);
		binder.loadComponent(lbxDetalleMovimientoActual);
		listaMovimiento = new ArrayList<modelo_movimiento_dn>();
		binder.loadComponent(lbxMovimientos);
	}

	public void buscarDetalleMovimientos1(String criterio, String fecha_inicio, String fecha_fin, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaMovimientoDetalle = consultasABaseDeDatos.cargarDetalleMovimientosDN(criterio, fecha_inicio, fecha_fin,
				String.valueOf(id_dc), Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), 6, estado);
		binder.loadComponent(lbxDetalleMovimientoAnterior);
		binder.loadComponent(lbxDetalleMovimientoActual);
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onOK=#txtBuscarDetalle")
	public void onOK$txtBuscarDetalle()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		consultarDetalleMovimientos1();
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws Throwable {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws Throwable {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws Throwable {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onSelect=#cmbTipoBusqueda")
	public void onSelect$cmbTipoBusqueda() throws Throwable {
		String tipo_busqueda = "LP";
		if (cmbTipoBusqueda.getSelectedItem() != null) {
			tipo_busqueda = cmbTipoBusqueda.getSelectedItem().getValue().toString();
		}
		if (tipo_busqueda.equals("LP")) {
			consultarMovimientos();
		} else {
			consultarDetalleMovimientos();
		}
	}

	@Listen("onSelect=#lbxMovimientos")
	public void onSelect$lbxMovimientos() throws Throwable {
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		txtBuscarDetalle.setText("");
		int indice = lbxMovimientos.getSelectedIndex();
		if (cmbTipoBusqueda.getSelectedItem().getValue().toString().equals("LP")) {
			listaMovimientoDetalle = consultasABaseDeDatos.cargarDetalleMovimientosDN(
					String.valueOf(listaMovimiento.get(indice).getId_movimiento()), "", "", String.valueOf(id_dc), 0, 3,
					"");
			binder.loadComponent(lbxDetalleMovimientoActual);
			binder.loadComponent(lbxDetalleMovimientoAnterior);
		}
	}

	@Listen("onRightClick=#lbxMovimientos")
	public void onRightClick$lbxMovimientos() throws Throwable {
		if (cmbTipoBusqueda.getSelectedItem().getValue().equals("LD")) {
			editPopup.close();
			return;
		}
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxMovimientos.getSelectedIndex();
		consultarMovimientos();
		int tamanio_lista = lbxMovimientos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxMovimientos.setSelectedIndex(indice);
		if (listaMovimiento.get(lbxMovimientos.getSelectedIndex()).getEst_validacion().equals("RV1")) {
			mValidar.setLabel(" - Validación operador en turno");
			mValidar.setTooltiptext(
					"Permite realizar la primera validación del pedido por parte del operador en turno.");
			mValidar.setDisabled(false);
			mValidar.setVisible(true);
		} else if (listaMovimiento.get(lbxMovimientos.getSelectedIndex()).getEst_validacion().equals("RV2")) {
			mValidar.setLabel(" - Validación en horario de [00:00 - 07:59]");
			mValidar.setTooltiptext(
					"Permite realizar la segunda validación del pedido por parte del operador en el horario de [00:00 - 07:59].");
			mValidar.setDisabled(false);
			mValidar.setVisible(true);
		} else if (listaMovimiento.get(lbxMovimientos.getSelectedIndex()).getEst_validacion().equals("RV3")) {
			mValidar.setLabel(" - Revisión por parte del auditor");
			mValidar.setTooltiptext(
					"Permite realizar la tercera validación del pedido pero esta vez por parte de un auditor.");
			mValidar.setDisabled(false);
			mValidar.setVisible(true);
		} else {
			mValidar.setLabel("");
			mValidar.setTooltiptext("");
			mValidar.setDisabled(true);
			mValidar.setVisible(false);
			editPopup.close();
		}
		if (cmbTipoBusqueda.getSelectedItem().getValue().toString().equals("LP")) {
			listaMovimientoDetalle = consultasABaseDeDatos.cargarDetalleMovimientosDN(
					String.valueOf(listaMovimiento.get(indice).getId_movimiento()), "", "", String.valueOf(id_dc), 0, 3,
					"");
			binder.loadComponent(lbxDetalleMovimientoActual);
			binder.loadComponent(lbxDetalleMovimientoAnterior);
		}
	}

	@Listen("onSelect=#lbxDetalleMovimientoAnterior")
	public void onSelect$lbxDetalleMovimientoAnterior() throws Throwable {
		if (lbxDetalleMovimientoAnterior.getSelectedItem() == null) {
			return;
		}
		if (cmbTipoBusqueda.getSelectedItem().getValue().toString().equals("LD")) {
			int indice = lbxDetalleMovimientoAnterior.getSelectedIndex();
			listaMovimiento = consultasABaseDeDatos.cargarMovimientosDN(
					String.valueOf(listaMovimientoDetalle.get(indice).getId_movimiento()), "", "",
					String.valueOf(id_dc), 0, 4, "");
			binder.loadComponent(lbxMovimientos);
		}
	}

	@Listen("onSelect=#lbxDetalleMovimientoActual")
	public void onSelect$lbxDetalleMovimientoActual() throws Throwable {
		if (lbxDetalleMovimientoActual.getSelectedItem() == null) {
			return;
		}
		if (cmbTipoBusqueda.getSelectedItem().getValue().toString().equals("LD")) {
			int indice = lbxDetalleMovimientoActual.getSelectedIndex();
			listaMovimiento = consultasABaseDeDatos.cargarMovimientosDN(
					String.valueOf(listaMovimientoDetalle.get(indice).getId_movimiento()), "", "",
					String.valueOf(id_dc), 0, 4, "");
			binder.loadComponent(lbxMovimientos);
		}
	}

	@Listen("onClick=#mValidar")
	public void onClick$btnmValidar() throws Throwable {
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		if (lbxMovimientos.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxMovimientos.getSelectedIndex();
		Tabbox tTab = (Tabbox) zConsultar.getParent().getParent().getParent().getParent().getParent().getParent();
		Tabpanels tPanel = (Tabpanels) zConsultar.getParent().getParent().getParent().getParent().getParent();
		long id_pedido = listaMovimiento.get(indice).getId_movimiento();
		String tipo_pedido = "movimiento";
		if (listaMovimiento.get(indice).getTip_pedido().equals("R")) {
			tipo_pedido = "requerimiento";
		}
		String ticket = listaMovimiento.get(indice).getTck_movimiento();
		Sessions.getCurrent().setAttribute("movimiento", listaMovimiento.get(indice));
		if (listaMovimiento.get(indice).getEst_validacion().equals("RV1")) {
			if (validarSiEsMismoUsuarioEnRV1(indice) == true) {
				Messagebox.show(informativos.getMensaje_informativo_72().replace("?", tipo_pedido),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			crearPestanaParaRevision(tTab, tPanel, id_pedido, tipo_pedido, ticket, 1);
		}
		if (listaMovimiento.get(indice).getEst_validacion().equals("RV2")) {
			if (validarSiEstaDentroDeRangoPermitidoRV2() == false) {
				Messagebox.show(informativos.getMensaje_informativo_76().replace("?", tipo_pedido),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if (validarSiEsMismoUsuarioEnRV2(indice) == true) {
				Messagebox.show(informativos.getMensaje_informativo_72().replace("?", tipo_pedido),
						informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			crearPestanaParaRevision(tTab, tPanel, id_pedido, tipo_pedido, ticket, 2);
		}
		if (listaMovimiento.get(indice).getEst_validacion().equals("RV3")) {
			crearPestanaParaRevision(tTab, tPanel, id_pedido, tipo_pedido, ticket, 3);
		}
	}

	public void crearPestanaParaRevision(Tabbox tTab, Tabpanels tPanel, long id_pedido, String tipo_pedido,
			String ticket, int tipo_revision) {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + id_pedido)) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + id_pedido);
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - MOVIMIENTOS | REVISION DE " + ticket);
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + id_pedido);
			tab.setImage("/img/botones/ButtonSearch4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = null;
			if (tipo_revision == 1) {
				include = new Include("/cintas/movimiento/revision1.zul");
			}
			if (tipo_revision == 2) {
				include = new Include("/cintas/movimiento/revision2.zul");
			}
			if (tipo_revision == 3) {
				include = new Include("/cintas/movimiento/revision3.zul");
			}
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validarSiEsMismoUsuarioEnRV1(int indice) {
		boolean es_mismo_usuario = false;
		if (id_perfil == 1) {
			es_mismo_usuario = false;
		} else {
			if (listaMovimiento.get(indice).getUsu_ingresa().equals(user)) {
				es_mismo_usuario = true;
			}
		}
		return es_mismo_usuario;
	}

	public boolean validarSiEsMismoUsuarioEnRV2(int indice) {
		boolean es_mismo_usuario = false;
		if (id_perfil == 1) {
			es_mismo_usuario = false;
		} else {
			if (listaMovimiento.get(indice).getUsu_ingresa().equals(user)) {
				es_mismo_usuario = true;
			}
		}
		return es_mismo_usuario;
	}

	public boolean validarSiEstaDentroDeRangoPermitidoRV2() {
		boolean esta_en_rango_permitido = false;
		String horaInicial = "00:00";
		String horaFinal = "07:59";
		String horaActual = "";
		if (id_perfil == 1) {
			esta_en_rango_permitido = true;
		} else {
			try {
				DateFormat format = new SimpleDateFormat("HH:mm");
				Date horaA;
				horaA = new Date();
				horaActual = format.format(horaA);
				Date horaI;
				Date horaF;
				horaI = format.parse(horaInicial);
				horaF = format.parse(horaFinal);
				horaA = format.parse(horaActual);
				if ((horaA.compareTo(horaI) > 0) && (horaA.compareTo(horaF) < 0)) {
					esta_en_rango_permitido = true;
				} else {
					esta_en_rango_permitido = false;
				}
			} catch (java.text.ParseException e) {
				esta_en_rango_permitido = false;
			}
		}
		return esta_en_rango_permitido;
	}

}
