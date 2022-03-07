package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_log_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_log_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar_log extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxLogArticulos;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_log_articulo_dn> listaLogArticulos = new ArrayList<modelo_log_articulo_dn>();
	List<modelo_perfil> listaPerfilUsuario = new ArrayList<modelo_perfil>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfilUsuario();
		inicializarPermisos();
		setearFechaInicio();
		setearFechaFin();
		if (consultar.equals("S")) {
			cargarLogArticulos();
			txtBuscar.setDisabled(false);
			lbxLogArticulos.setEmptyMessage("!No existen datos que mostrar¡.");
		} else {
			txtBuscar.setDisabled(true);
			lbxLogArticulos.setEmptyMessage("!No tiene permiso para ver los registros¡.");
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
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

	public void cargarLogArticulos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_log_articulo_dn dao = new dao_log_articulo_dn();
		String criterio = "";
		String fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy/MM/dd HH:mm");
		String fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy/MM/dd HH:mm");
		try {
			listaLogArticulos = dao.obtenerLogsArticulosDN(criterio, fecha_inicio, fecha_fin, 1);
			binder.loadComponent(lbxLogArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar logs ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarPerfilUsuario() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfilUsuario = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisos() {
		if (listaPerfilUsuario.size() == 0) {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaPerfilUsuario.size() > 1) {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaPerfilUsuario.get(0).getConsultar().equals("S")) {
			consultar = "S";
		} else {
			consultar = "N";
		}
		if (listaPerfilUsuario.get(0).getInsertar().equals("S")) {
			insertar = "S";
		} else {
			insertar = "N";
		}
		if (listaPerfilUsuario.get(0).getModificar().equals("S")) {
			modificar = "S";
		} else {
			modificar = "N";
		}
		if (listaPerfilUsuario.get(0).getRelacionar().equals("S")) {
			relacionar = "S";
		} else {
			relacionar = "N";
		}
		if (listaPerfilUsuario.get(0).getDesactivar().equals("S")) {
			desactivar = "S";
		} else {
			desactivar = "N";
		}
		if (listaPerfilUsuario.get(0).getEliminar().equals("S")) {
			eliminar = "S";
		} else {
			eliminar = "N";
		}
		if (listaPerfilUsuario.get(0).getSolicitar().equals("S")) {
			solicitar = "S";
		} else {
			solicitar = "N";
		}
		if (listaPerfilUsuario.get(0).getRevisar().equals("S")) {
			revisar = "S";
		} else {
			revisar = "N";
		}
		if (listaPerfilUsuario.get(0).getAprobar().equals("S")) {
			aprobar = "S";
		} else {
			aprobar = "N";
		}
		if (listaPerfilUsuario.get(0).getEjecutar().equals("S")) {
			ejecutar = "S";
		} else {
			ejecutar = "N";
		}
	}

	public List<modelo_log_articulo_dn> obtenerLogArticulos() {
		return listaLogArticulos;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			String criterio = txtBuscar.getText();
			buscarLogArticulos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			String criterio = txtBuscar.getText();
			buscarLogArticulos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		try {
			String criterio = txtBuscar.getText();
			buscarLogArticulos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		try {
			String criterio = txtBuscar.getText();
			buscarLogArticulos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarLogArticulos(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_log_articulo_dn dao = new dao_log_articulo_dn();
		String fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy/MM/dd HH:mm");
		String fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy/MM/dd HH:mm");
		if (txtBuscar.getText().length() <= 0) {
			try {
				listaLogArticulos = dao.obtenerLogsArticulosDN(criterio.toUpperCase().trim(), fecha_inicio, fecha_fin,
						tipo);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			try {
				listaLogArticulos = dao.obtenerLogsArticulosDN(criterio.toUpperCase().trim(), fecha_inicio, fecha_fin,
						tipo);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los logs. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		binder.loadComponent(lbxLogArticulos);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
