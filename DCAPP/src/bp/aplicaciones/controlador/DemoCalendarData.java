package bp.aplicaciones.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.calendar.api.CalendarEvent;

import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;

public class DemoCalendarData {

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	generadorColores color = new generadorColores();

	List<modelo_control_cambio> listaControlCambio = new ArrayList<modelo_control_cambio>();
	List<modelo_solicitud_personal> listaPersonal = new ArrayList<modelo_solicitud_personal>();

	private List<CalendarEvent> calendarEvents = new LinkedList<CalendarEvent>();

	public DemoCalendarData(int opcion, String criterio, int tipo, long id_cliente, long id_localidad, int limite,
			String fecha_inicio, String fecha_fin, String estado) throws ClassNotFoundException, FileNotFoundException, IOException {
		if (opcion == 2) { // GESTION DE CONTROL DE CAMBIOS
			inicializarInformacionControlDeCambio(criterio, tipo, id_cliente, id_localidad, limite);
		}
		if (opcion == 5) { // GESTION DE PERSONAL
			inicializarInformacionPersonal(criterio, tipo, id_cliente, id_localidad, limite, fecha_inicio, fecha_fin, estado);
		}
	}

	public List<CalendarEvent> getCalendarEvents() {
		return calendarEvents;
	}

	@SuppressWarnings("deprecation")
	public void inicializarInformacionControlDeCambio(String criterio, int tipo, long id_cliente, long id_localidad,
			int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		inicializarListasControlDeCambios(criterio, tipo, id_cliente, id_localidad, limite);
		Date f_i = null;
		Date f_f = null;
		String color1 = "";
		String color2 = "";
		for (int i = 0; i < listaControlCambio.size(); i++) {
			f_i = fechas.obtenerFechaArmada(listaControlCambio.get(i).getFec_programada(),
					listaControlCambio.get(i).getFec_programada().getMonth(),
					listaControlCambio.get(i).getFec_programada().getDate(), 6, 0, 0);
			f_f = fechas.obtenerFechaArmada(listaControlCambio.get(i).getFec_programada(),
					listaControlCambio.get(i).getFec_programada().getMonth(),
					listaControlCambio.get(i).getFec_programada().getDate(), 10, 0, 0);
			color1 = color.generarColoresSolidos();
			color2 = color.generarColoresCalidos();
			calendarEvents.add(
					new DemoCalendarEvent(f_i, f_f, color1, color2, listaControlCambio.get(i).getId_control_cambio()
							+ " - " + listaControlCambio.get(i).getDescripcion()));
		}
	}

	@SuppressWarnings("deprecation")
	public void inicializarInformacionPersonal(String criterio, int tipo, long id_cliente, long id_localidad,
			int limite, String fecha_inicio, String fecha_fin, String estado)
					throws ClassNotFoundException, FileNotFoundException, IOException {
		inicializarListasPersonal(criterio, tipo, id_cliente, id_localidad, limite, fecha_inicio, fecha_fin, estado);
		Date f_i = null;
		Date f_f = null;
		String color1 = "";
		String color2 = "";
		for (int i = 0; i < listaPersonal.size(); i++) {
			f_i = fechas.obtenerFechaArmada(listaPersonal.get(i).getFec_inicio(),
					listaPersonal.get(i).getFec_inicio().getMonth(), listaPersonal.get(i).getFec_inicio().getDate(),
					listaPersonal.get(i).getFec_inicio().getHours(), listaPersonal.get(i).getFec_inicio().getMinutes(),
					listaPersonal.get(i).getFec_inicio().getSeconds());
			f_f = fechas.obtenerFechaArmada(listaPersonal.get(i).getFec_fin(),
					listaPersonal.get(i).getFec_fin().getMonth(), listaPersonal.get(i).getFec_fin().getDate(),
					listaPersonal.get(i).getFec_fin().getHours(), listaPersonal.get(i).getFec_fin().getMinutes(),
					listaPersonal.get(i).getFec_fin().getSeconds());
			color1 = color.generarColoresSolidos();
			color2 = color.generarColoresCalidos();
			calendarEvents.add(new DemoCalendarEvent(f_i, f_f, color1, color2, listaPersonal.get(i).getId_solicitud()
					+ " - " + listaPersonal.get(i).getTicket() + " - " + listaPersonal.get(i).getDescripcion()));
		}
	}

	public void inicializarListasControlDeCambios(String criterio, int tipo, long id_cliente, long id_localidad,
			int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		listaControlCambio = consultasABaseDeDatos.cargarControlesDeCambio(criterio, tipo, id_cliente, id_localidad,
				limite);
	}

	public void inicializarListasPersonal(String criterio, int tipo, long id_cliente, long id_localidad, int limite,
			String fecha_inicio, String fecha_fin, String estado) throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPersonal = consultasABaseDeDatos.cargarSolicitudesPersonal(criterio, 1, id_cliente, fecha_inicio,
				fecha_fin, "", "", id_localidad, estado, limite);
	}

}
