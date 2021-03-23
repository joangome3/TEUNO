package bp.aplicaciones.controlador.sibod.movimiento;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_7;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_7;
import bp.aplicaciones.sibod.DAO.dao_movimiento;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;

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
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Listbox lbxMovimientos;
	@Wire
	Popup pop1;

	Popup pop = null;

	Image img = new Image();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	List<modelo_movimiento> listaMovimiento = new ArrayList<modelo_movimiento>();
	List<modelo_articulo> listaArticulo1, listaArticulo2 = new ArrayList<modelo_articulo>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_parametros_generales_7> listaParametros7 = new ArrayList<modelo_parametros_generales_7>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxMovimientos.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarEstados("", 1, id_dc);
		cargarFechas();
		cargarMovimientos();
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
	}

	public List<modelo_estado_articulo> obtenerEstados() {
		return listaEstados;
	}
	
	public List<modelo_parametros_generales_7> obtenerParametros7() {
		return listaParametros7;
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
	
	public void cargarParametros7(long id_criticidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_7 dao = new dao_parametros_generales_7();
		try {
			listaParametros7 = dao.obtenerRelacionesEstados(String.valueOf(id_criticidad), "", id_dc, 3);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarFechas() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		dtxFechaInicio.setValue(primerDiaMes);
		dtxFechaFin.setValue(ultimoDiaMes);
	}

	public void cargarMovimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento dao = new dao_movimiento();
		LocalDateTime localDateTime1 = null;
		LocalDateTime localDateTime2 = null;
		LocalDate localDate1 = dtxFechaInicio.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = dtxFechaFin.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year1 = localDate1.getYear();
		int year2 = localDate2.getYear();
		localDateTime1 = LocalDateTime.of(year1, dtxFechaInicio.getValue().getMonth() + 1,
				dtxFechaInicio.getValue().getDate(), 0, 0);
		localDateTime2 = LocalDateTime.of(year2, dtxFechaFin.getValue().getMonth() + 1,
				dtxFechaFin.getValue().getDate(), 23, 59);
		Date d1 = null;
		Date d2 = null;
		d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
		d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String f_i = sdf.format(d1);
		String f_f = sdf.format(d2);
		String criterio = "";
		try {
			listaMovimiento = dao.obtenerMovimientos(criterio, f_i, f_f, String.valueOf(id_dc));
			binder.loadComponent(lbxMovimientos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public List<modelo_movimiento> obtenerMovimientos() {
		return listaMovimiento;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			buscarMovimientos();
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio() {
		try {
			buscarMovimientos();
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin() {
		try {
			buscarMovimientos();
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarMovimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento dao = new dao_movimiento();
		LocalDateTime localDateTime1 = null;
		LocalDateTime localDateTime2 = null;
		LocalDate localDate1 = dtxFechaInicio.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = dtxFechaFin.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year1 = localDate1.getYear();
		int year2 = localDate2.getYear();
		localDateTime1 = LocalDateTime.of(year1, dtxFechaInicio.getValue().getMonth() + 1,
				dtxFechaInicio.getValue().getDate(), 0, 0);
		localDateTime2 = LocalDateTime.of(year2, dtxFechaFin.getValue().getMonth() + 1,
				dtxFechaFin.getValue().getDate(), 23, 59);
		Date d1 = null;
		Date d2 = null;
		d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
		d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String f_i = sdf.format(d1);
		String f_f = sdf.format(d2);
		String criterio = "";
		if (txtBuscar.getText().length() <= 0) {
			criterio = "";
			try {
				listaMovimiento = dao.obtenerMovimientos(criterio, f_i, f_f, String.valueOf(id_dc));
				binder.loadComponent(lbxMovimientos);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			criterio = txtBuscar.getText();
			try {
				listaMovimiento = dao.obtenerMovimientos(criterio, f_i, f_f, String.valueOf(id_dc));
				binder.loadComponent(lbxMovimientos);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los movimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		Component comp = Executions.createComponents("/sibod/movimiento/nuevo.zul", null, null);
		if (comp instanceof Window) {
			((Window) comp).doModal();
			comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					limpiarCampos();
					cargarMovimientos();
				}
			});
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() throws Throwable {
		try {
			buscarMovimientos();
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

	public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
		Graphics2D g = bufim.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return bufim;
	}

}
