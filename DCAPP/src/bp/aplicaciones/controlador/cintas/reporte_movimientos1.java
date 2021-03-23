package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_movimientos1 extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Combobox cmbFormato;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Jasperreport reporte;

	String imagen = null;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();
	List<modelo_tipo_ubicacion> listaUbicacion = new ArrayList<modelo_tipo_ubicacion>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarFechaHoraInicio();
		cargarFechaHoraFin();
	}

	public void cargarFechaHoraInicio() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		LocalDateTime localDateTime = null;
		LocalDate localDate = primerDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, primerDiaMes.getMonth() + 1, primerDiaMes.getDate(), 0, 0);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaInicio.setValue(d);
	}

	public void cargarFechaHoraFin() {
		Date fechaActual = new Date();
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		LocalDateTime localDateTime = null;
		LocalDate localDate = ultimoDiaMes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, ultimoDiaMes.getMonth() + 1, ultimoDiaMes.getDate(), 23, 59);
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		dtxFechaFin.setValue(d);
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (dtxFechaInicio.getValue() == null) {
			dtxFechaInicio.setErrorMessage("Seleccione la fecha.");
			return;
		}
		if (dtxFechaFin.getValue() == null) {
			dtxFechaFin.setErrorMessage("Seleccione la fecha.");
			return;
		}
		if (cmbFormato.getSelectedItem() == null) {
			cmbFormato.setErrorMessage("Seleccione un formato.");
			return;
		}
		conexion conexion = new conexion();
		dao_localidad dao = new dao_localidad();
		List<modelo_localidad> localidad = new ArrayList<modelo_localidad>();
		localidad = dao.obtenerLocalidades(String.valueOf(id_dc), 1, 0, 0);
		Map<Object, Object> parameters = new HashMap<Object, Object>();

		parameters.put("logo", imagen);

		parameters.put("p_usuario", user);

		parameters.put("localidad", String.valueOf(id_dc));
		parameters.put("p_localidad", localidad.get(0).getNom_localidad());

		parameters.put("p_fechainicio", dtxFechaInicio.getValue());
		parameters.put("p_fechafin", dtxFechaFin.getValue());
		String f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaInicio.getValue());
		String f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaFin.getValue());
		parameters.put("fechainicio", f1);
		parameters.put("fechafin", f2);

		try {
			reporte.setSrc("/cintas/reportes/movimientos1/FD07_Registro_de_movimiento_de_cintas.jasper");
			reporte.setParameters(parameters);
			reporte.setDataConnection(conexion.abrir());
			reporte.setType(cmbFormato.getSelectedItem().getValue());
			reporte.setVisible(true);
		} catch (Exception e) {
			Messagebox.show("Error al visualizar el reporte. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Visualizar reporte ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar() throws ClassNotFoundException, FileNotFoundException, IOException {
		cargarFechaHoraInicio();
		cargarFechaHoraFin();
	}

}
