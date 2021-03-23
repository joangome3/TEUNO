package bp.aplicaciones.controlador.sibod.movimiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_inventario extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Textbox txtBuscarArticulo, txtBuscarProveedor;
	@Wire
	Bandbox bdxArticulo, bdxSolicitantes;
	@Wire
	Combobox cmbEmpresa, cmbCategoria, cmbMovimiento, cmbFormato;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Listbox lbxArticulos, lbxSolicitantes;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkArticulo, chkCategoria, chkMovimiento, chkProveedor;

	String imagen = null;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		chkArticulo.setChecked(true);
		chkCategoria.setChecked(true);
		chkMovimiento.setChecked(true);
		chkProveedor.setChecked(true);
		lbxArticulos.setEmptyMessage("No existen datos que mostrar");
		lbxSolicitantes.setEmptyMessage("No existen datos que mostrar");
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarFechas();
		cargarEmpresas();
		cargarCategorias();
		cargarSolicitantes("");
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_articulo> obtenerArticulos() {
		return listaArticulo;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public void cargarFechas() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		dtxFechaInicio.setValue(primerDiaMes);
		dtxFechaFin.setValue(ultimoDiaMes);
	}

	public void cargarSolicitantes(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitante dao = new dao_solicitante();
		try {
			listaSolicitante = dao.obtenerSolicitantes(criterio, 2, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(lbxSolicitantes);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los solicitantes. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 2, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los clientes. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar cliente ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarArticulos(String criterio, String empresa)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), empresa, 2, 0);
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarCategorias() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria dao = new dao_categoria();
		String criterio = "";
		try {
			listaCategoria = dao.obtenerCategorias(criterio, String.valueOf(id_dc), 1, 0, 0);
			binder.loadComponent(cmbCategoria);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las categorias. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			bdxArticulo.setText("");
			bdxArticulo.setDisabled(true);
			chkArticulo.setChecked(true);
			return;
		}
		bdxArticulo.setText("");
		bdxArticulo.setTooltiptext("");
		bdxArticulo.setDisabled(false);
		chkArticulo.setChecked(false);
		lbxArticulos.clearSelection();
		cargarArticulos("", cmbEmpresa.getSelectedItem().getValue().toString());
	}

	@Listen("onOK=#txtBuscarArticulo")
	public void onOK$txtBuscarArticulo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarArticulos(txtBuscarArticulo.getText().toString(), cmbEmpresa.getSelectedItem().getValue().toString());
		txtBuscarArticulo.setTooltiptext("");
	}

	@Listen("onSelect=#lbxArticulos")
	public void onSelect$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'I') {
			Messagebox.show("El articulo se encuentra inactivo, debe estar activo para poder seleccionarlo.",
					".:: Añadir articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'P') {
			Messagebox.show(
					"El articulo se encuentra pendiente de que se apruebe su creación, debe estar aprobado y activo para poder seleccionarlo.",
					".:: Añadir articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		bdxArticulo.setText(listaArticulo.get(indice).getDes_articulo());
		bdxArticulo.setTooltiptext(listaArticulo.get(indice).getDes_articulo());
	}

	@Listen("onOK=#txtBuscarProveedor")
	public void onOK$txtBuscarProveedor()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarSolicitantes(txtBuscarProveedor.getText().toString());
		txtBuscarProveedor.setTooltiptext("");
	}

	@Listen("onSelect=#lbxSolicitantes")
	public void onSelect$lbxSolicitantes()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'I') {
			txtBuscarProveedor.setText("");
			cargarSolicitantes(txtBuscarProveedor.getText().toString());
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setTooltiptext("");
			Messagebox.show("El solicitante se encuentra inactivo, debe estar activo para poder seleccionarlo.",
					".:: Añadir solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'P') {
			txtBuscarProveedor.setText("");
			cargarSolicitantes(txtBuscarProveedor.getText().toString());
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setTooltiptext("");
			Messagebox.show(
					"El solicitante se encuentra pendiente de que se apruebe su creación, debe estar aprobado y activo para poder seleccionarlo.",
					".:: Añadir solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		bdxSolicitantes.setText(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
		bdxSolicitantes.setTooltiptext(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
	}

	@Listen("onCheck=#chkProveedor")
	public void onCheck$chkProveedor() {
		if (!chkProveedor.isChecked()) {
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setText("");
			bdxSolicitantes.setTooltiptext("");
			bdxSolicitantes.setDisabled(false);
			lbxSolicitantes.clearSelection();
		} else {
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setText("");
			bdxSolicitantes.setTooltiptext("");
			bdxSolicitantes.setDisabled(true);
			lbxSolicitantes.clearSelection();
		}
	}

	@Listen("onCheck=#chkCategoria")
	public void onCheck$chkCategoria() {
		if (!chkCategoria.isChecked()) {
			cmbCategoria.setDisabled(false);
		} else {
			cmbCategoria.setDisabled(true);
			cmbCategoria.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkArticulo")
	public void onCheck$chkArticulo() {
		if (!chkArticulo.isChecked()) {
			bdxArticulo.setText("");
			txtBuscarArticulo.setText("");
			bdxArticulo.setTooltiptext("");
			bdxArticulo.setDisabled(false);
			lbxArticulos.clearSelection();
		} else {
			bdxArticulo.setText("");
			txtBuscarArticulo.setText("");
			bdxArticulo.setTooltiptext("");
			bdxArticulo.setDisabled(true);
			lbxArticulos.clearSelection();
		}
	}

	@Listen("onCheck=#chkMovimiento")
	public void onCheck$chkMovimiento() {
		if (!chkMovimiento.isChecked()) {
			cmbMovimiento.setDisabled(false);
		} else {
			cmbMovimiento.setDisabled(true);
			cmbMovimiento.setSelectedIndex(-1);
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
		if (!bdxArticulo.isDisabled()) {
			if (bdxArticulo.getText().length() <= 0) {
				bdxArticulo.setErrorMessage("Seleccione un articulo.");
				return;
			}
		}
		if (!cmbCategoria.isDisabled()) {
			if (cmbCategoria.getSelectedItem() == null) {
				cmbCategoria.setErrorMessage("Seleccione una categoria.");
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
		if (!cmbMovimiento.isDisabled()) {
			if (cmbMovimiento.getSelectedItem() == null) {
				cmbMovimiento.setErrorMessage("Seleccione un movimiento.");
				return;
			}
		}
		if (cmbFormato.getSelectedItem() == null) {
			cmbFormato.setErrorMessage("Seleccione un formato.");
			return;
		}
		if (!bdxSolicitantes.isDisabled()) {
			if (bdxSolicitantes.getText().length() <= 0) {
				bdxSolicitantes.setErrorMessage("Seleccione un proveedor.");
				return;
			}
		}
		conexion conexion = new conexion();
		dao_localidad dao = new dao_localidad();
		List<modelo_localidad> localidad = new ArrayList<modelo_localidad>();
		localidad = dao.obtenerLocalidades(String.valueOf(id_dc), 1, 0, 0);
		Map<Object, Object> parameters = new HashMap<Object, Object>();

		parameters.put("logo", imagen);
		
		parameters.put("empresa", cmbEmpresa.getSelectedItem().getValue().toString());

		parameters.put("p_usuario", user);
		parameters.put("localidad", String.valueOf(id_dc));
		parameters.put("p_localidad", localidad.get(0).getNom_localidad());

		if (chkArticulo.isChecked()) {
			parameters.put("articulo", null);
			parameters.put("p_articulo", "TODOS");
		} else {
			int indice = lbxArticulos.getSelectedIndex();
			parameters.put("articulo", listaArticulo.get(indice).getId_articulo());
			parameters.put("p_articulo", listaArticulo.get(indice).getDes_articulo());
		}
		if (chkCategoria.isChecked()) {
			parameters.put("categoria", null);
			parameters.put("p_categoria", "TODAS");
		} else {
			parameters.put("categoria", cmbCategoria.getSelectedItem().getValue().toString());
			parameters.put("p_categoria", cmbCategoria.getText().toString());
		}
		if (chkMovimiento.isChecked()) {
			parameters.put("movimiento", null);
			parameters.put("p_movimiento", "TODOS");
		} else {
			parameters.put("movimiento", cmbMovimiento.getSelectedItem().getValue().toString());
			parameters.put("p_movimiento", cmbMovimiento.getText().toString());
		}
		if (chkProveedor.isChecked()) {
			parameters.put("proveedor", null);
			parameters.put("p_proveedor", "TODOS");
		} else {
			int indice = lbxSolicitantes.getSelectedIndex();
			parameters.put("proveedor", listaSolicitante.get(indice).getId_solicitante());
			parameters.put("p_proveedor", listaSolicitante.get(indice).getNom_solicitante() + " "
					+ listaSolicitante.get(indice).getApe_solicitante());
		}
		parameters.put("p_fechainicio", dtxFechaInicio.getValue());
		parameters.put("p_fechafin", dtxFechaFin.getValue());
		try {
			reporte.setSrc("/sibod/movimiento/inventario/Reporte_de_movimientos.jasper");
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
		cargarFechas();
		cmbEmpresa.setSelectedIndex(-1);
		bdxArticulo.setText("");
		bdxArticulo.setDisabled(true);
		chkArticulo.setChecked(true);
		bdxSolicitantes.setText("");
		bdxSolicitantes.setDisabled(true);
		chkProveedor.setChecked(true);
		cmbCategoria.setSelectedIndex(-1);
		cmbCategoria.setErrorMessage("");
		cmbCategoria.setText("");
		cmbCategoria.setDisabled(true);
		if (!chkCategoria.isChecked()) {
			chkCategoria.setChecked(true);
		}
		cmbMovimiento.setSelectedIndex(-1);
		cmbMovimiento.setErrorMessage("");
		cmbMovimiento.setText("");
		if (!chkMovimiento.isChecked()) {
			chkMovimiento.setChecked(true);
		}
		cmbMovimiento.setDisabled(true);
		cmbFormato.setSelectedIndex(-1);
		reporte.setVisible(false);
		cargarSolicitantes("");
		lbxArticulos.clearSelection();
		lbxSolicitantes.clearSelection();
		binder.loadComponent(chkArticulo);
		binder.loadComponent(chkCategoria);
		binder.loadComponent(chkMovimiento);
		binder.loadComponent(chkProveedor);
	}

}
