package bp.aplicaciones.controlador.personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.DemoCalendarData;
import bp.aplicaciones.controlador.DemoCalendarEvent;
import bp.aplicaciones.controlador.DemoCalendarModel;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;

@SuppressWarnings("deprecation")
public class calendario extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;
	@Wire
	private Div zCalendario;
	@Wire
	private Calendars calendario;
	@Wire
	private Button btnRefrescar, btnAnterior, btnSiguiente, btnHoy, btnDia, btnSemana, btnMes, btnLimpiar;
	@Wire
	private Textbox txtBuscar;
	@Wire
	private Combobox cmbCliente;
	@Wire
	private Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Div winList;

	Window window;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	private CalendarsEvent calendarioEvent = null;
	private DemoCalendarModel calendarioModel;

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_empresa> listaCliente = new ArrayList<modelo_empresa>();

	long id_opcion = 5;

	boolean ingresa_a_modificar = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		setearFechaActual();
		setearFechaInicio();
		setearFechaFin();
		inicializarListas();
		cargarInformacion();
		btnLimpiar.setDisabled(false);
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

	public void cargarInformacion() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		List<CalendarEvent> data = new DemoCalendarData(5, "", 1, 0, id_dc, 0, fecha_inicio, fecha_fin)
				.getCalendarEvents();
		calendarioModel = new DemoCalendarModel(data);
		calendario.setModel(this.calendarioModel);
	}

	public List<modelo_empresa> obtenerClientes() {
		return listaCliente;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaCliente = consultasABaseDeDatos.cargarEmpresas("", 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		binder.loadComponent(cmbCliente);
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
		cmbCliente.setText("");
		setearFechaInicio();
		setearFechaFin();
		consultarSolicitudesPersonal();
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws WrongValueException, NumberFormatException, ClassNotFoundException,
			FileNotFoundException, IOException {
		consultarSolicitudesPersonal();
	}

	@Listen("onClick=#btnHoy")
	public void gotoToday() {
		TimeZone timeZone = calendario.getDefaultTimeZone();
		calendario.setCurrentDate(Calendar.getInstance(timeZone).getTime());
	}

	@Listen("onClick=#btnSiguiente")
	public void gotoNext() {
		calendario.nextPage();
	}

	@Listen("onClick=#btnAnterior")
	public void gotoPrev() {
		calendario.previousPage();
	}

	@Listen("onClick=#btnDia")
	public void changeToDay() {
		calendario.setMold("default");
		calendario.setDays(1);
	}

	@Listen("onClick=#btnSemana")
	public void changeToWeek() {
		calendario.setMold("default");
		calendario.setDays(7);
	}

	@Listen("onClick=#btnMes")
	public void changeToYear() {
		calendario.setMold("month");
	}

	@Listen("onClick=#btnRefrescar")
	public void refrescarLista()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
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

	public void consultarSolicitudesPersonal() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String criterio = txtBuscar.getText();
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
		calendarioModel = new DemoCalendarModel(
				new DemoCalendarData(5, criterio, 1, id_cliente, id_dc, 0, fecha_inicio, fecha_fin)
						.getCalendarEvents());
		calendario.setModel(this.calendarioModel);
	}

	// listen to the calendar-create and edit of a event data
	@Listen("onEventCreate=#calendario; onEventEdit=#calendario")
	public void NewEvent(CalendarsEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {
		try {
			createEvent(event);
		} catch (org.zkoss.zk.ui.UiException e) {
			// System.out.println(""+e);
		}
	}

	// //listen to the calendar-update of event data, usually send when user
	// drag the event data
	@Listen("onEventUpdate=#calendario")
	public void updEvent(CalendarsEvent event) {
		try {
			updateEvent(event);
		} catch (org.zkoss.zk.ui.UiException e) {
			// System.out.println(""+e);
		}
	}

	public void createEvent(CalendarsEvent event) throws ClassNotFoundException, FileNotFoundException, IOException {
		calendarioEvent = event;
		// to display a shadow when editing
		calendarioEvent.stopClearGhost();
		DemoCalendarEvent data = (DemoCalendarEvent) event.getCalendarEvent();
		if (data == null) {
			return;
		} else {
			data = (DemoCalendarEvent) event.getCalendarEvent();
			long id_solicitud = Long.valueOf(data.getContent().substring(0, data.getContent().indexOf("-")).trim());
			modelo_solicitud_personal solicitud_personal = obtenerRegistro(id_solicitud);
			Tabbox tTab = (Tabbox) zCalendario.getParent().getParent().getParent().getParent().getParent().getParent();
			Tabpanels tPanel = (Tabpanels) zCalendario.getParent().getParent().getParent().getParent().getParent();
			String ticket = solicitud_personal.getTicket();
			Sessions.getCurrent().setAttribute("solicitud_personal", solicitud_personal);
			crearPestanaParaRevision(tTab, tPanel, id_solicitud, ticket);
		}
	}

	public void updateEvent(CalendarsEvent event) {
		return;
	}

	public modelo_solicitud_personal obtenerRegistro(long id_solicitud)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		modelo_solicitud_personal solicitud_personal = new modelo_solicitud_personal();
		List<modelo_solicitud_personal> listaSolicitudPersonal = new ArrayList<modelo_solicitud_personal>();
		listaSolicitudPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(String.valueOf(id_solicitud), 2, 0, "",
				"", "", "", id_dc, 0);
		for (int i = 0; i < listaSolicitudPersonal.size(); i++) {
			if (id_solicitud == listaSolicitudPersonal.get(i).getId_solicitud()) {
				solicitud_personal = listaSolicitudPersonal.get(i);
				i = listaSolicitudPersonal.size() + 1;
			}
		}
		return solicitud_personal;
	}

	public void crearPestanaParaRevision(Tabbox tTab, Tabpanels tPanel, long id_solicitud, String ticket) {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + id_solicitud)) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + id_solicitud);
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL - MODIFICAR | SOLICITUD " + ticket);
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
			include = new Include("/personal/solicitud/modificar.zul");
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
