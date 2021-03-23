package bp.aplicaciones.controlador.cintas;

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
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_articulos1 extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Textbox txtBuscarArticulo, txtBuscarUbicacion;
	@Wire
	Bandbox bdxArticulo, bdxUbicacion;
	@Wire
	Combobox cmbEmpresa, cmbCategoria, cmbFormato;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Listbox lbxArticulos, lbxUbicaciones;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkArticulo, chkCategoria, chkUbicacion;

	String imagen = null;

	long id_opcion = 4;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();

	ConsultasABaseDeDatos ConsultasABaseDeDatos = new ConsultasABaseDeDatos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		chkArticulo.setChecked(true);
		chkCategoria.setChecked(true);
		chkUbicacion.setChecked(true);
		lbxArticulos.setEmptyMessage("No existen datos que mostrar");
		lbxUbicaciones.setEmptyMessage("No existen datos que mostrar");
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarEmpresas();
		cargarCategorias();
		cargarUbicaciones("");
		validarSesion();
	}

	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {

	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_articulo_dn> obtenerArticulos() {
		return listaArticulo;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaEmpresa = ConsultasABaseDeDatos.cargarEmpresas("", 2, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		binder.loadComponent(cmbEmpresa);
	}

	public void cargarArticulos(String criterio, String empresa)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), empresa, 2, 0, "", "");
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarCategorias() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria_dn dao = new dao_categoria_dn();
		String criterio = "";
		try {
			listaCategoria = dao.obtenerCategorias(criterio, String.valueOf(id_dc), 1, 0, 0);
			binder.loadComponent(cmbCategoria);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las categorias. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_ubicacion_dn dao = new dao_ubicacion_dn();
		try {
			listaUbicacion = dao.obtenerUbicaciones(criterio, String.valueOf(id_dc), 6, 0, 0, 0, 10);
			binder.loadComponent(lbxUbicaciones);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOK$txtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarUbicaciones(txtBuscarUbicacion.getText().toString());
		txtBuscarUbicacion.setTooltiptext("");
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

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelect$lbxUbicaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxUbicaciones.getSelectedItem() == null) {
			return;
		}
		int indice = lbxUbicaciones.getSelectedIndex();
		bdxUbicacion.setText(listaUbicacion.get(indice).toStringUbicacion());
		bdxUbicacion.setTooltiptext(listaUbicacion.get(indice).toStringUbicacion());
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

	@Listen("onCheck=#chkUbicacion")
	public void onCheck$chkUbicacion() {
		if (!chkUbicacion.isChecked()) {
			bdxUbicacion.setText("");
			txtBuscarUbicacion.setText("");
			bdxUbicacion.setTooltiptext("");
			bdxUbicacion.setDisabled(false);
			lbxUbicaciones.clearSelection();
		} else {
			bdxUbicacion.setText("");
			txtBuscarUbicacion.setText("");
			bdxUbicacion.setTooltiptext("");
			bdxUbicacion.setDisabled(true);
			lbxUbicaciones.clearSelection();
		}
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		validarSesion();
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
		if (!chkUbicacion.isChecked()) {
			if (bdxUbicacion.getText().length() <= 0) {
				bdxUbicacion.setErrorMessage("Seleccione una ubicación.");
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
			parameters.put("p_categoria", "TODOS");
		} else {
			parameters.put("categoria", cmbCategoria.getSelectedItem().getValue().toString());
			parameters.put("p_categoria", cmbCategoria.getText().toString());
		}
		if (chkUbicacion.isChecked()) {
			parameters.put("ubicacion", null);
			parameters.put("p_ubicacion", "TODAS");
		} else {
			int indice = lbxUbicaciones.getSelectedIndex();
			parameters.put("ubicacion", listaUbicacion.get(indice).getId_ubicacion());
			parameters.put("p_ubicacion", listaUbicacion.get(indice).toStringUbicacion());
		}
		try {
			reporte.setSrc("/cintas/reportes/articulos1/FD08_Inventario_de_cintas_Diners.jasper");
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
		bdxUbicacion.setText("");
		bdxArticulo.setDisabled(true);
		bdxUbicacion.setDisabled(true);
		chkArticulo.setChecked(true);
		chkUbicacion.setChecked(true);
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
		lbxUbicaciones.clearSelection();
		binder.loadComponent(chkArticulo);
		binder.loadComponent(chkUbicacion);
		binder.loadComponent(chkCategoria);
	}

}
