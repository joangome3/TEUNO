package bp.aplicaciones.controlador.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar_solicitud extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxSolicitudesPersonal;
	@Wire
	Combobox cmbLimite, cmbCliente, cmbTurno, cmbEstado;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Menuitem mModificar, mEliminar, mCerrar, mAlcance;
	@Wire
	Div winList;

	Window window;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_parametros_generales_5> listaParametros5 = new ArrayList<modelo_parametros_generales_5>();
	List<modelo_solicitud_personal> listaSolicitudPersonal = new ArrayList<modelo_solicitud_personal>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	boolean cumple_condicion_modificar = false;
	boolean cumple_condicion_eliminar = false;

	boolean ingresa_a_modificar = false;

	long id_opcion = 5;
	long id_turno = 0;

	boolean es_turno_extendido = false;
	boolean se_inicia_turno = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	String turno = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(3);
		cmbEstado.setSelectedIndex(0);
		inicializarListas();
		inicializarPermisos();
		setearFechaActual();
		setearFechaInicio();
		setearFechaFin();
		validarTurno();
		if (cmbCliente.getItems().size() > 0) {
			cmbCliente.setSelectedIndex(1);
		}
		if (consultar.equals("S")) {
			setearSolicitudesPersonal();
			txtBuscar.setDisabled(false);
			lbxSolicitudesPersonal.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxSolicitudesPersonal.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public List<modelo_parametros_generales_5> obtenerParametros5() {
		return listaParametros5;
	}

	public List<modelo_perfil> obtenerPerfil() {
		return listaPerfil;
	}

	public List<modelo_empresa> obtenerClientes() {
		return listaCliente;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_registra_turno> obtenerRegistroTurno() {
		return listaRegistroTurno;
	}

	public List<modelo_solicitud_personal> obtenerSolicitudes() {
		return listaSolicitudPersonal;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
		listaParametros5 = consultasABaseDeDatos.cargarParametros5("", String.valueOf(id_opcion), 2);
		binder.loadComponent(cmbCliente);
		binder.loadComponent(cmbTurno);
	}

	public void setearSolicitudesPersonal()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		String estado = "";
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (cmbTurno.getSelectedItem() != null) {
			turno = cmbTurno.getSelectedItem().getValue().toString();
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		listaSolicitudPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(criterio, 1, id_cliente, fecha_inicio,
				fecha_fin, estado, "", id_dc, Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		binder.loadComponent(lbxSolicitudesPersonal);
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

	public void validarTurno() {
		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;
		Date d5 = null;
		Date d6 = null;
		d1 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), new Date().getSeconds());
		for (int i = 0; i < listaTurno.size(); i++) {
			if (listaTurno.get(i).getEs_extendido().equals("N")) {
				es_turno_extendido = false;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 23, 59, 59);
				d4 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				d5 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				Date someDate = new Date();
				Date newDate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1));
				d6 = fechas.obtenerFechaArmada(newDate, newDate.getMonth(), newDate.getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d6;
				if ((d2.before(d1) && d1.before(d3)) || (d4.before(d1) && d1.before(d5))) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			}
		}
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaInicio() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		dtxFechaInicio.setValue(primerDiaMes);
	}

	public void setearFechaFin() {
		Date fechaActual = new Date();
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0, 23, 59, 0);
		dtxFechaFin.setValue(ultimoDiaMes);
	}

	public void buscarSolicitudesPersonal(String criterio, int tipo, long id_cliente, String fecha_solicitud_i,
			String fecha_solicitud_f, String fecha_inicio, String fecha_fin, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		if (txtBuscar.getText().length() <= 0) {
			listaSolicitudPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(criterio, tipo, id_cliente,
					fecha_solicitud_i, fecha_solicitud_f, fecha_inicio, fecha_fin, id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		}
		if (!txtBuscar.getValue().equals("")) {
			listaSolicitudPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(criterio, tipo, id_cliente,
					fecha_solicitud_i, fecha_solicitud_f, fecha_inicio, fecha_fin, id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()));
		}
		binder.loadComponent(lbxSolicitudesPersonal);
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado() throws ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	public void consultarSolicitudesPersonal() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
		String estado = "";
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		buscarSolicitudesPersonal(criterio, 1, id_cliente, fecha_inicio, fecha_fin, estado, "", id_dc);
	}

	@Listen("onClick=#mCerrar")
	public void onClickmCerrar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

	}

	public boolean validarSiTareaExiste(String ticket_externo)
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_tarea = false;
		List<modelo_bitacora> listatareas = new ArrayList<modelo_bitacora>();
		listatareas = consultasABaseDeDatos.cargarBitacoras(ticket_externo, 4, 0, "", "", id_dc, "", "", 0, 0, "", 0);
		if (listatareas.size() > 0) {
			existe_tarea = true;
		} else {
			existe_tarea = false;
		}
		return existe_tarea;
	}

	@Listen("onRightClick=#lbxMovimientos")
	public void onRightClick$lbxMovimientos() throws Throwable {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitudesPersonal.getSelectedIndex();
		consultarSolicitudesPersonal();
		int tamanio_lista = lbxSolicitudesPersonal.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxSolicitudesPersonal.setSelectedIndex(indice);
	}

	@Listen("onClick=#mModificar")
	public void onClickmModificar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			return;
		}
		if (lbxSolicitudesPersonal.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxSolicitudesPersonal.getSelectedIndex();
		Tabbox tTab = (Tabbox) zConsultar.getParent().getParent().getParent().getParent().getParent().getParent();
		Tabpanels tPanel = (Tabpanels) zConsultar.getParent().getParent().getParent().getParent().getParent();
		long id_solicitud = listaSolicitudPersonal.get(indice).getId_solicitud();
		String ticket = listaSolicitudPersonal.get(indice).getTicket();
		Sessions.getCurrent().setAttribute("solicitud_personal", listaSolicitudPersonal.get(indice));
		crearPestanaParaRevision(tTab, tPanel, id_solicitud, ticket, 1);
	}

	@Listen("onClick=#mAlcance")
	public void onClickmAlcance() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxSolicitudesPersonal.getSelectedItem() == null) {
			return;
		}
		if (lbxSolicitudesPersonal.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = lbxSolicitudesPersonal.getSelectedIndex();
		Tabbox tTab = (Tabbox) zConsultar.getParent().getParent().getParent().getParent().getParent().getParent();
		Tabpanels tPanel = (Tabpanels) zConsultar.getParent().getParent().getParent().getParent().getParent();
		long id_solicitud = listaSolicitudPersonal.get(indice).getId_solicitud();
		String ticket = listaSolicitudPersonal.get(indice).getTicket();
		Sessions.getCurrent().setAttribute("solicitud_personal", listaSolicitudPersonal.get(indice));
		crearPestanaParaRevision(tTab, tPanel, id_solicitud, ticket, 2);
	}

	public void crearPestanaParaRevision(Tabbox tTab, Tabpanels tPanel, long id_solicitud, String ticket, int tipo) {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + id_solicitud)) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + id_solicitud);
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			if (tipo == 1) {
				tab.setLabel("GESTION DE PERSONAL - MODIFICAR | SOLICITUD " + ticket);
			} else {
				tab.setLabel("GESTION DE PERSONAL - ALCANCE | SOLICITUD " + ticket);
			}
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + id_solicitud);
			tab.setImage("/img/botones/ButtonSearch4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = null;
			if (tipo == 1) {
				include = new Include("/personal/solicitud/modificar.zul");
			} else {
				include = new Include("/personal/solicitud/alcance.zul");
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

}
