package bp.aplicaciones.controlador.controlcambio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.DemoCalendarData;
import bp.aplicaciones.controlador.DemoCalendarEvent;
import bp.aplicaciones.controlador.DemoCalendarModel;
import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings("deprecation")
public class calendario extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;
	@Wire
	private Window zCalendario;
	@Wire
	private Calendars calendario;
	@Wire
	private Button btnRefrescar, btnAnterior, btnSiguiente, btnHoy, btnDia, btnSemana, btnMes;
	@Wire
	private Textbox txtBuscar;
	@Wire
	private Combobox cmbCliente;
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

	long id_opcion = 2;

	boolean ingresa_a_modificar = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		inicializarListas();
		cargarInformacion();
	}

	public void cargarInformacion() throws ClassNotFoundException, FileNotFoundException, IOException {
		List<CalendarEvent> data = new DemoCalendarData(2, "", 1, 0, id_dc, 0, "", "").getCalendarEvents();
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

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		consultarInformacion(txtBuscar.getText().toUpperCase().trim(), id_cliente);
	}

	@Listen("onSelect=#cmbCliente")
	public void onSelect$cmbCliente() throws WrongValueException, NumberFormatException, ClassNotFoundException,
			FileNotFoundException, IOException {
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		consultarInformacion(txtBuscar.getText().toUpperCase().trim(), id_cliente);
	}

	public void consultarInformacion(String criterio, long id_cliente)
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		calendarioModel = new DemoCalendarModel(
				new DemoCalendarData(2, criterio, 1, id_cliente, id_dc, 0, "", "").getCalendarEvents());
		calendario.setModel(this.calendarioModel);
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
		long id_cliente = 0;
		if (cmbCliente.getSelectedItem() != null) {
			id_cliente = Long.valueOf(cmbCliente.getSelectedItem().getValue().toString());
		}
		consultarInformacion(txtBuscar.getText().toUpperCase().trim(), id_cliente);
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
			data = new DemoCalendarEvent();
			data.setBeginDate(event.getBeginDate());
			data.setEndDate(event.getEndDate());
			Sessions.getCurrent().setAttribute("data_calendario", data);
			window = (Window) Executions.createComponents("/control_cambio/nuevo.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						onOK$txtBuscar();
					}
				});
			}
			window.setParent(winList);
		} else {
			if (ingresa_a_modificar == false) {
				ingresa_a_modificar = true;
				data = (DemoCalendarEvent) event.getCalendarEvent();
				long id_control_cambio = Long
						.valueOf(data.getContent().substring(0, data.getContent().indexOf("-")).trim());
				modelo_control_cambio control_cambio = obtenerRegistro(id_control_cambio);
				Sessions.getCurrent().setAttribute("control_cambio", control_cambio);
				window = (Window) Executions.createComponents("/control_cambio/modificar.zul", null, null);
				if (window instanceof Window) {
					window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
						@Override
						public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
							onOK$txtBuscar();
							ingresa_a_modificar = false;
						}
					});
				}
				window.setParent(winList);
			}
		}
	}

	public void updateEvent(CalendarsEvent event) {
		return;
	}

	public modelo_control_cambio obtenerRegistro(long id_control_cambio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		modelo_control_cambio control_cambio = new modelo_control_cambio();
		List<modelo_control_cambio> listaControlCambio = new ArrayList<modelo_control_cambio>();
		listaControlCambio = consultasABaseDeDatos.cargarControlesDeCambio("", 1, 0, id_dc, 0);
		for (int i = 0; i < listaControlCambio.size(); i++) {
			if (id_control_cambio == listaControlCambio.get(i).getId_control_cambio()) {
				control_cambio = listaControlCambio.get(i);
				i = listaControlCambio.size() + 1;
			}
		}
		return control_cambio;
	}
}
