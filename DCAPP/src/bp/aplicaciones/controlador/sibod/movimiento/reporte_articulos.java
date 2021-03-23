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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_articulos extends SelectorComposer<Component> {
	
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Textbox txtBuscarArticulo, txtBuscarProveedor;
	@Wire
	Bandbox bdxArticulo;
	@Wire
	Combobox cmbEmpresa, cmbCategoria, cmbFormato, cmbEstados;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Listbox lbxArticulos;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkArticulo, chkCategoria, chkEstados;

	String imagen = null;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		chkArticulo.setChecked(true);
		chkCategoria.setChecked(true);
		chkEstados.setChecked(true);
		lbxArticulos.setEmptyMessage("No existen datos que mostrar");
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarEmpresas();
		cargarCategorias();
		cargarEstados("", 1, id_dc);
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

	public List<modelo_estado_articulo> obtenerEstados() {
		return listaEstados;
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

	public void cargarEstados(String criterio, int tipo, long localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_articulo dao = new dao_estado_articulo();
		try {
			listaEstados = dao.obtenerEstados(criterio, tipo, String.valueOf(localidad));
			binder.loadComponent(cmbEstados);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	@Listen("onCheck=#chkCategoria")
	public void onCheck$chkCategoria() {
		if (!chkCategoria.isChecked()) {
			cmbCategoria.setDisabled(false);
		} else {
			cmbCategoria.setDisabled(true);
			cmbCategoria.setSelectedIndex(-1);
		}
	}

	@Listen("onCheck=#chkEstados")
	public void onCheck$chkEstados() {
		if (!chkEstados.isChecked()) {
			cmbEstados.setDisabled(false);
		} else {
			cmbEstados.setDisabled(true);
			cmbEstados.setSelectedIndex(-1);
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
		if (chkEstados.isChecked()) {
			parameters.put("estado", null);
			parameters.put("p_estado", "TODOS");
		} else {
			parameters.put("estado", cmbEstados.getSelectedItem().getValue().toString());
			parameters.put("p_estado", cmbEstados.getText().toString());
		}
		try {
			if (chkEstados.isChecked()) {
				reporte.setSrc("/sibod/movimiento/articulos/A65_Registro_de_inventario_de_materiales.jasper");
			} else {
				reporte.setSrc("/sibod/movimiento/articulos2/A65_Registro_de_inventario_de_materiales.jasper");
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
		cmbEmpresa.setSelectedIndex(-1);
		bdxArticulo.setText("");
		bdxArticulo.setDisabled(true);
		chkArticulo.setChecked(true);
		cmbCategoria.setSelectedIndex(-1);
		cmbCategoria.setErrorMessage("");
		cmbCategoria.setText("");
		cmbCategoria.setDisabled(true);
		if (!chkCategoria.isChecked()) {
			chkCategoria.setChecked(true);
		}
		cmbFormato.setSelectedIndex(-1);
		reporte.setVisible(false);
		lbxArticulos.clearSelection();
		binder.loadComponent(chkArticulo);
		binder.loadComponent(chkCategoria);
	}

}
