package bp.aplicaciones.controlador.bitacora;

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
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_servicio;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_tarea;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_bitacora_3 extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Combobox cmbEmpresa, cmbTipoServicio, cmbTipoTarea, cmbEstado, cmbCumplimiento, cmbFormato, cmbUsuario;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkEmpresa, chkTipoServicio, chkTipoTarea, chkEstado, chkCumplimiento, chkUsuario;

	String imagen = null;

	long id_opcion = 3;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_estado_bitacora> listaEstado = new ArrayList<modelo_estado_bitacora>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		chkEmpresa.setChecked(true);
		chkTipoServicio.setChecked(true);
		chkTipoTarea.setChecked(true);
		chkEstado.setChecked(true);
		chkCumplimiento.setChecked(true);
		chkUsuario.setChecked(true);
		cmbEmpresa.setDisabled(true);
		cmbTipoServicio.setDisabled(true);
		cmbTipoTarea.setDisabled(true);
		cmbEstado.setDisabled(true);
		cmbCumplimiento.setDisabled(true);
		cmbUsuario.setDisabled(true);
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarClientes();
		cargarUsuarios();
		cargarEstados();
		cargarTipoServicios();
		cargarTipoTareas();
		cargarFechaHoraInicio();
		cargarFechaHoraFin();
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicios() {
		return listaTipoServicio;
	}

	public List<modelo_tipo_tarea> obtenerTipoTareas() {
		return listaTipoTarea;
	}

	public List<modelo_estado_bitacora> obtenerEstados() {
		return listaEstado;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		listaUsuario = dao.consultarUsuarios(id_dc, 0, "", "", 0, 2);
		binder.loadComponent(cmbUsuario);
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

	public void cargarClientes() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 6, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoServicios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		String criterio = "";
		try {
			listaTipoServicio = dao.obtenerTipoServicios(criterio, 1, 0, 0);
			binder.loadComponent(cmbTipoServicio);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los tipo de servicios. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de servicio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoTareas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_tarea dao = new dao_tipo_tarea();
		String criterio = "";
		try {
			listaTipoTarea = dao.obtenerTipoTareas(criterio, 1, 0, 0);
			binder.loadComponent(cmbTipoTarea);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las tipo_tareas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de tareas ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstados() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_bitacora dao = new dao_estado_bitacora();
		String criterio = "";
		try {
			listaEstado = dao.obtenerEstados(criterio, 1, String.valueOf(id_dc));
			binder.loadComponent(cmbEstado);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estados ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onCheck=#chkEmpresa")
	public void onCheck$chkEmpresa() {
		if (!chkEmpresa.isChecked()) {
			cmbEmpresa.setDisabled(false);
		} else {
			cmbEmpresa.setDisabled(true);
			cmbEmpresa.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkCumplimiento")
	public void onCheck$chkCumplimiento() {
		if (!chkCumplimiento.isChecked()) {
			cmbCumplimiento.setDisabled(false);
		} else {
			cmbCumplimiento.setDisabled(true);
			cmbCumplimiento.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkTipoTarea")
	public void onCheck$chkTipoTarea() {
		if (!chkTipoTarea.isChecked()) {
			cmbTipoTarea.setDisabled(false);
		} else {
			cmbTipoTarea.setDisabled(true);
			cmbTipoTarea.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkTipoServicio")
	public void onCheck$chkTipoServicio() {
		if (!chkTipoServicio.isChecked()) {
			cmbTipoServicio.setDisabled(false);
		} else {
			cmbTipoServicio.setDisabled(true);
			cmbTipoServicio.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkEstado")
	public void onCheck$chkEstado() {
		if (!chkEstado.isChecked()) {
			cmbEstado.setDisabled(false);
		} else {
			cmbEstado.setDisabled(true);
			cmbEstado.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkUsuario")
	public void onCheck$chkUsuario() {
		if (!chkUsuario.isChecked()) {
			cmbUsuario.setDisabled(false);
		} else {
			cmbUsuario.setDisabled(true);
			cmbUsuario.setSelectedIndex(-1);
		}
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (!cmbEmpresa.isDisabled()) {
			if (cmbEmpresa.getSelectedItem() == null) {
				cmbEmpresa.setErrorMessage("Seleccione un cliente.");
				return;
			}
		}
		if (!cmbTipoServicio.isDisabled()) {
			if (cmbTipoServicio.getSelectedItem() == null) {
				cmbTipoServicio.setErrorMessage("Seleccione un tipo de servicio.");
				return;
			}
		}
		if (!cmbTipoTarea.isDisabled()) {
			if (cmbTipoTarea.getSelectedItem() == null) {
				cmbTipoTarea.setErrorMessage("Seleccione una tipo de tarea.");
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
		if (!cmbEstado.isDisabled()) {
			if (cmbEstado.getSelectedItem() == null) {
				cmbEstado.setErrorMessage("Seleccione un estado_bitacora.");
				return;
			}
		}
		if (cmbFormato.getSelectedItem() == null) {
			cmbFormato.setErrorMessage("Seleccione un formato.");
			return;
		}
		if (!cmbCumplimiento.isDisabled()) {
			if (cmbCumplimiento.getSelectedItem() == null) {
				cmbCumplimiento.setErrorMessage("Seleccione un cumplimiento.");
				return;
			}
		}
		if (!cmbUsuario.isDisabled()) {
			if (cmbUsuario.getSelectedItem() == null) {
				cmbUsuario.setErrorMessage("Seleccione un operador.");
				return;
			}
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

		if (chkEmpresa.isChecked()) {
			parameters.put("empresa", null);
			parameters.put("p_empresa", "TODOS");
		} else {
			parameters.put("empresa", cmbEmpresa.getSelectedItem().getValue().toString());
			parameters.put("p_empresa", cmbEmpresa.getText().toString());
		}
		if (chkTipoServicio.isChecked()) {
			parameters.put("tipo_servicio", null);
			parameters.put("p_tipo_servicio", "TODOS");
		} else {
			parameters.put("tipo_servicio", cmbTipoServicio.getSelectedItem().getValue().toString());
			parameters.put("p_tipo_servicio", cmbTipoServicio.getText().toString());
		}
		if (chkTipoTarea.isChecked()) {
			parameters.put("tipo_tarea", null);
			parameters.put("p_tipo_tarea", "TODAS");
		} else {
			parameters.put("tipo_tarea", cmbTipoTarea.getSelectedItem().getValue().toString());
			parameters.put("p_tipo_tarea", cmbTipoTarea.getText().toString());
		}
		if (chkEstado.isChecked()) {
			parameters.put("estado", null);
			parameters.put("p_estado", "TODOS");
		} else {
			parameters.put("estado", cmbEstado.getSelectedItem().getValue().toString());
			parameters.put("p_estado", cmbEstado.getText().toString());
		}
		if (chkCumplimiento.isChecked()) {
			parameters.put("cumplimiento", null);
			parameters.put("p_cumplimiento", "TODOS");
		} else {
			parameters.put("cumplimiento", cmbCumplimiento.getSelectedItem().getValue().toString());
			parameters.put("p_cumplimiento", cmbCumplimiento.getText().toString());
		}
		if (chkUsuario.isChecked()) {
			parameters.put("operador", null);
			parameters.put("p_operador", "TODOS");
		} else {
			parameters.put("operador", cmbUsuario.getSelectedItem().getValue().toString());
			parameters.put("p_operador", cmbUsuario.getText().toString());
		}
		parameters.put("p_fechainicio", dtxFechaInicio.getValue());
		parameters.put("p_fechafin", dtxFechaFin.getValue());
		String f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaInicio.getValue());
		String f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dtxFechaFin.getValue());
		parameters.put("fechainicio", f1);
		parameters.put("fechafin", f2);
		try {
			reporte.setSrc("/bitacora/log_eventos/reporte3/Reporte_de_log_de_eventos_detallado.jasper");
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
		cmbEmpresa.setSelectedIndex(-1);
		chkEmpresa.setChecked(true);
		cmbTipoServicio.setSelectedIndex(-1);
		chkTipoServicio.setChecked(true);
		cmbCumplimiento.setSelectedIndex(-1);
		chkCumplimiento.setChecked(true);
		cmbTipoTarea.setSelectedIndex(-1);
		chkTipoTarea.setChecked(true);
		cmbEstado.setSelectedIndex(-1);
		cmbEstado.setDisabled(true);
		chkEstado.setChecked(true);
		cmbFormato.setSelectedIndex(-1);
		cmbEmpresa.setDisabled(true);
		cmbTipoServicio.setDisabled(true);
		cmbTipoTarea.setDisabled(true);
		cmbEstado.setDisabled(true);
		cmbCumplimiento.setDisabled(true);
		reporte.setVisible(false);
		binder.loadComponent(chkEmpresa);
		binder.loadComponent(chkTipoServicio);
		binder.loadComponent(chkTipoTarea);
		binder.loadComponent(chkEstado);
		binder.loadComponent(chkCumplimiento);
	}

}
