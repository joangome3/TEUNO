package bp.aplicaciones.controlador.sibod.movimiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_baldosas extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Combobox cmbUbicacion, cmbFormato;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkUbicacion;

	String imagen = null;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		chkUbicacion.setChecked(true);
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarParametros();
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
	}

	public void cargarParametros() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onCheck=#chkUbicacion")
	public void onCheck$chkUbicacion() {
		if (!chkUbicacion.isChecked()) {
			cmbUbicacion.setDisabled(false);
		} else {
			cmbUbicacion.setDisabled(true);
			cmbUbicacion.setSelectedIndex(-1);
		}
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (!cmbUbicacion.isDisabled()) {
			if (cmbUbicacion.getSelectedItem() == null) {
				cmbUbicacion.setErrorMessage("Seleccione una ubicación.");
				return;
			}
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

		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getId_categoria_reporte_1() != 0) {
				parameters.put("categoria", String.valueOf(listaParametros.get(0).getId_categoria_reporte_1()));
			} else {
				Messagebox.show(
						"No se ha asignado la categoria para el reporte de baldosas en los parametros generales.",
						".:: Visualizar reporte ::.", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else {
			Messagebox.show("No se ha asignado la categoria para el reporte de baldosas en los parametros generales.",
					".:: Visualizar reporte ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		if (chkUbicacion.isChecked()) {
			parameters.put("ubicacion", null);
			parameters.put("p_ubicacion", "TODOS");
		} else {
			parameters.put("ubicacion", cmbUbicacion.getSelectedItem().getValue().toString());
			parameters.put("p_ubicacion", cmbUbicacion.getText().toString());
		}
		try {
			if (chkUbicacion.isChecked()) {
				reporte.setSrc("/sibod/movimiento/baldosas/Reporte_de_distribucion_de_baldosas.jasper");
			} else {
				if (cmbUbicacion.getSelectedItem().getValue().toString().equals("B")) {
					reporte.setSrc("/sibod/movimiento/baldosas/Reporte_de_distribucion_de_baldosas_BODEGA.jasper");
				}
				if (cmbUbicacion.getSelectedItem().getValue().toString().equals("S")) {
					reporte.setSrc("/sibod/movimiento/baldosas/Reporte_de_distribucion_de_baldosas_SALA_UTIL.jasper");
				}
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
		cmbUbicacion.setSelectedIndex(-1);
		cmbUbicacion.setErrorMessage("");
		cmbUbicacion.setText("");
		if (!chkUbicacion.isChecked()) {
			chkUbicacion.setChecked(true);
		}
		cmbUbicacion.setDisabled(true);
		cmbFormato.setSelectedIndex(-1);
		reporte.setVisible(false);
		binder.loadComponent(chkUbicacion);
	}

}
