package bp.aplicaciones.controlador.mantenimientos.solicitud;

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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxSolicitudes;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Combobox cmbEstado;
	@Wire
	Menuitem mSeguimiento, mAprobar;
	@Wire
	Menuseparator mSeparador1;
	@Wire
	Div winList;

	Window window;

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

	List<modelo_solicitud> listaSolicitud = new ArrayList<modelo_solicitud>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		setearFechaInicio();
		setearFechaFin();
		cargarPerfil();
		inicializarPermisos();
		cmbEstado.setSelectedIndex(0);
		if (consultar.equals("S")) {
			cargarSolicitudes();
			txtBuscar.setDisabled(false);
			lbxSolicitudes.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxSolicitudes.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
		} else {
			btnNuevo.setDisabled(true);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
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

	public List<modelo_solicitud> obtenerSolicitudes() {
		return listaSolicitud;
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
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

	public void cargarSolicitudes() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		String fecha_inicio = "", fecha_fin = "";
		String estado = "";
		String criterio = txtBuscar.getText();
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy/MM/dd HH:mm");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy/MM/dd HH:mm");
		}
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		listaSolicitud = consultasABaseDeDatos.cargarSolicitudes(criterio, fecha_inicio, fecha_fin, estado, 0, 0, 1);
		binder.loadComponent(lbxSolicitudes);
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() {
		try {
			buscarSolicitudes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() {
		try {
			buscarSolicitudes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelected$cmbEstado() {
		try {
			buscarSolicitudes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarSolicitudes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			buscarSolicitudes();
		} catch (Exception e) {
			Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void buscarSolicitudes() throws ClassNotFoundException, FileNotFoundException, IOException {
		String fecha_inicio = "", fecha_fin = "";
		String estado = "";
		String criterio = txtBuscar.getText();
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy/MM/dd HH:mm");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy/MM/dd HH:mm");
		}
		if (cmbEstado.getSelectedItem() != null) {
			estado = cmbEstado.getSelectedItem().getValue().toString();
		}
		listaSolicitud = consultasABaseDeDatos.cargarSolicitudes(criterio, fecha_inicio, fecha_fin, estado, 0, 0, 1);
		binder.loadComponent(lbxSolicitudes);
	}

	@Listen("onRightClick=#lbxSolicitudes")
	public void onRightClick$lbxSolicitudes() throws Throwable {
		if (lbxSolicitudes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitudes.getSelectedIndex();
		buscarSolicitudes();
		int tamanio_lista = lbxSolicitudes.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxSolicitudes.setSelectedIndex(indice);
		if (listaSolicitud.get(indice).getEst_solicitud().equals("P")
				|| listaSolicitud.get(indice).getEst_solicitud().equals("R")) {
			mSeguimiento.setVisible(false);
			mSeguimiento.setDisabled(true);
			mSeparador1.setVisible(false);
			mAprobar.setVisible(true);
			mAprobar.setDisabled(false);
		} else {
			mSeguimiento.setVisible(true);
			mSeguimiento.setDisabled(false);
			mSeparador1.setVisible(false);
			mAprobar.setVisible(false);
			mAprobar.setDisabled(true);
		}
		if (listaSolicitud.get(indice).getEst_solicitud().equals("P")) {
			mAprobar.setLabel(" - Revisar");
			mAprobar.setTooltiptext("Permite realizar la revisión de la solicitud.");
		}
		if (listaSolicitud.get(indice).getEst_solicitud().equals("R")) {
			mAprobar.setLabel(" - Aprobar");
			mAprobar.setTooltiptext("Permite realizar la aprobación de la solicitud.");
		}
	}

	@Listen("onClick=#mSeguimiento")
	public void onClick$mSeguimiento() {
		if (lbxSolicitudes.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxSolicitudes.getSelectedIndex();
		if (listaSolicitud.get(indice).getId_tip_solicitud() == 7) { // Valido para opciones
			Sessions.getCurrent().setAttribute("objeto", listaSolicitud.get(indice));
			Sessions.getCurrent().setAttribute("id_mantenimiento", (long) 0);
			Sessions.getCurrent().setAttribute("id_opcion", listaSolicitud.get(indice).getId_mantenimiento());
			Sessions.getCurrent().setAttribute("tipo_solicitud",
					(int) listaSolicitud.get(indice).getId_tip_solicitud());
		} else {
			Sessions.getCurrent().setAttribute("objeto", listaSolicitud.get(indice));
			Sessions.getCurrent().setAttribute("id_mantenimiento", listaSolicitud.get(indice).getId_mantenimiento());
			Sessions.getCurrent().setAttribute("id_opcion", (long) 0);
			Sessions.getCurrent().setAttribute("tipo_solicitud",
					(int) listaSolicitud.get(indice).getId_tip_solicitud());
		}
		window = (Window) Executions.createComponents("/mantenimientos/solicitud/seguimiento.zul", null, null);
		mSeguimiento.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mSeguimiento.setDisabled(false);
					buscarSolicitudes();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mAprobar")
	public void onClick$mAprobar() {
		if (lbxSolicitudes.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxSolicitudes.getSelectedIndex();
		Sessions.getCurrent().setAttribute("solicitud", listaSolicitud.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/solicitud/aprobar.zul", null, null);
		mAprobar.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mAprobar.setDisabled(false);
					buscarSolicitudes();
					ingresa_a_accion = false;
				}
			});
		}
		window.setParent(winList);
	}

}
