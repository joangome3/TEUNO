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

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_capacidad;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;

@SuppressWarnings({ "deprecation", "serial" })
public class reporte_articulos2 extends SelectorComposer<Component> {
	AnnotateDataBinder binder;
	@Wire
	Window zReporte;
	@Wire
	Textbox txtBuscarUbicacion;
	@Wire
	Bandbox bdxUbicacion;
	@Wire
	Combobox cmbCapacidad, cmbFormato, cmbCategoria;
	@Wire
	Button btnConsultar, btnLimpiar;
	@Wire
	Listbox lbxUbicaciones;
	@Wire
	Jasperreport reporte;
	@Wire
	Checkbox chkCapacidad, chkUbicacion, chkCategoria;

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
		chkCapacidad.setChecked(true);
		chkUbicacion.setChecked(true);
		chkCategoria.setChecked(true);
		lbxUbicaciones.setEmptyMessage("No existen datos que mostrar");
		imagen = Sessions.getCurrent().getWebApp().getRealPath("/img/principal/logo_teuno.png");
		cargarCapacidades();
		cargarCategorias();
		cargarUbicaciones("");
		validarSesion();
	}

	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {

	}

	public List<modelo_capacidad> obtenerCapacidades() {
		return listaCapacidad;
	}

	public List<modelo_tipo_ubicacion> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public void cargarCapacidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_capacidad dao = new dao_capacidad();
		String criterio = "";
		try {
			listaCapacidad = dao.obtenerCapacidades(criterio, 4, 0, 0);
			binder.loadComponent(cmbCapacidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las capacidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar capacidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		try {
			listaUbicacion = dao.obtenerTipoUbicaciones("");
			binder.loadComponent(lbxUbicaciones);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOK$txtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarUbicaciones(txtBuscarUbicacion.getText().toString());
		txtBuscarUbicacion.setTooltiptext("");
	}

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelect$lbxUbicaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxUbicaciones.getSelectedItem() == null) {
			return;
		}
		int indice = lbxUbicaciones.getSelectedIndex();
		bdxUbicacion.setText(listaUbicacion.get(indice).getNom_tipo_ubicacion());
		bdxUbicacion.setTooltiptext(listaUbicacion.get(indice).getNom_tipo_ubicacion());
	}

	@Listen("onCheck=#chkCapacidad")
	public void onCheck$chkCapacidad() {
		if (!chkCapacidad.isChecked()) {
			cmbCapacidad.setDisabled(false);
		} else {
			cmbCapacidad.setDisabled(true);
			cmbCapacidad.setSelectedIndex(-1);
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

	@Listen("onCheck=#chkCategoria")
	public void onCheck$chkCategoria() {
		if (!chkCategoria.isChecked()) {
			cmbCategoria.setDisabled(false);
		} else {
			cmbCategoria.setDisabled(true);
			cmbCategoria.setSelectedIndex(-1);
		}
	}

	@Listen("onClick=#btnConsultar")
	public void onClick$btnConsultar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		validarSesion();
		if (!chkUbicacion.isChecked()) {
			if (bdxUbicacion.getText().length() <= 0) {
				bdxUbicacion.setErrorMessage("Seleccione una ubicación.");
				return;
			}
		}
		if (!cmbCapacidad.isDisabled()) {
			if (cmbCapacidad.getSelectedItem() == null) {
				cmbCapacidad.setErrorMessage("Seleccione una capacidad.");
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

		parameters.put("p_usuario", user);

		parameters.put("localidad", String.valueOf(id_dc));
		parameters.put("p_localidad", localidad.get(0).getNom_localidad());

		if (chkCapacidad.isChecked()) {
			parameters.put("capacidad", null);
			parameters.put("p_capacidad", "TODAS");
		} else {
			parameters.put("capacidad", cmbCapacidad.getSelectedItem().getValue().toString());
			parameters.put("p_capacidad", cmbCapacidad.getText().toString());
		}
		if (chkUbicacion.isChecked()) {
			parameters.put("ubicacion", null);
			parameters.put("p_ubicacion", "TODAS");
		} else {
			int indice = lbxUbicaciones.getSelectedIndex();
			parameters.put("ubicacion", listaUbicacion.get(indice).getId_tipo_ubicacion());
			parameters.put("p_ubicacion", listaUbicacion.get(indice).getNom_tipo_ubicacion());
		}
		if (chkCategoria.isChecked()) {
			parameters.put("categoria", null);
			parameters.put("p_categoria", "TODOS");
		} else {
			parameters.put("categoria", cmbCategoria.getSelectedItem().getValue().toString());
			parameters.put("p_categoria", cmbCategoria.getText().toString());
		}
		try {
			reporte.setSrc("/cintas/reportes/articulos2/FD08_Total_de_cintas_por_capacidad_y_ubicacion.jasper");
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
		bdxUbicacion.setText("");
		bdxUbicacion.setDisabled(true);
		chkUbicacion.setChecked(true);
		cmbCapacidad.setSelectedIndex(-1);
		cmbCapacidad.setErrorMessage("");
		cmbCapacidad.setText("");
		cmbCapacidad.setDisabled(true);
		if (!chkCapacidad.isChecked()) {
			chkCapacidad.setChecked(true);
		}
		cmbFormato.setSelectedIndex(-1);
		reporte.setVisible(false);
		lbxUbicaciones.clearSelection();
		binder.loadComponent(chkUbicacion);
		binder.loadComponent(chkCapacidad);
	}

}
