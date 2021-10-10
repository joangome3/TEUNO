package bp.aplicaciones.controlador.personal;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_permanencia1 extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Combobox cmbFormato, cmbEmpresa;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Checkbox chkEmpresa;
	@Wire
	Jasperreport reporte;

	String imagen1 = null, imagen2 = null;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		imagen1 = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		imagen2 = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarEmpresas();
		cargarFechaHoraInicio();
		cargarFechaHoraFin();
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 2, String.valueOf(id_dc), "5", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los clientes. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar cliente ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
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

	@Listen("onCheck=#chkEmpresa")
	public void onCheck$chkEmpresa() {
		if (chkEmpresa.isChecked()) {
			cmbEmpresa.setDisabled(true);
			cmbEmpresa.setText("");
		} else {
			cmbEmpresa.setDisabled(false);
			cmbEmpresa.setText("");
		}
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (!chkEmpresa.isChecked()) {
			if (cmbEmpresa.getSelectedItem() == null) {
				cmbEmpresa.setErrorMessage("Seleccione un cliente.");
				return;
			}
		}
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

		if (cmbEmpresa.getSelectedItem().getValue().toString().equals("9")) {
			parameters.put("logo", imagen2);
		} else {
			parameters.put("logo", imagen1);
		}

		parameters.put("p_usuario", user);

		if (chkEmpresa.isChecked()) {
			parameters.put("empresa", null);
			parameters.put("p_empresa", "TODAS");
		} else {
			parameters.put("empresa", cmbEmpresa.getSelectedItem().getValue().toString());
			parameters.put("p_empresa", cmbEmpresa.getText().toString());
		}

		parameters.put("localidad", String.valueOf(id_dc));
		parameters.put("p_localidad", localidad.get(0).getNom_localidad());

		parameters.put("p_fechainicio", dtxFechaInicio.getValue());
		parameters.put("p_fechafin", dtxFechaFin.getValue());
		String f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaInicio.getValue());
		String f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaFin.getValue());
		parameters.put("fechainicio", f1);
		parameters.put("fechafin", f2);

		try {
			if (cmbEmpresa.getSelectedItem().getValue().toString().equals("9")) {
				reporte.setSrc("/personal/reportes/acceso_permanencia2/Reporte_de_acceso_permanencia.jasper");
			} else if (cmbEmpresa.getSelectedItem().getValue().toString().equals("3")
					|| cmbEmpresa.getSelectedItem().getValue().toString().equals("7")) {
				reporte.setSrc("/personal/reportes/acceso_permanencia3/Reporte_de_acceso_permanencia.jasper");
			} else {
				reporte.setSrc("/personal/reportes/acceso_permanencia1/Reporte_de_acceso_permanencia.jasper");
			}
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
		cmbEmpresa.setText("");
		cargarEmpresas();
		cargarFechaHoraInicio();
		cargarFechaHoraFin();
	}

}
