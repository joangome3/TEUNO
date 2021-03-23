package bp.aplicaciones.controlador.mantenimientos.informativo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import bp.aplicaciones.mantenimientos.DAO.dao_informativo;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_informativo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

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
	Listbox lbxInformativos;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Combobox cmbLimite;
	@Wire
	Menuitem mLeido, mModificar;
	@Wire
	Menuseparator mSeparador1;
	@Wire
	Div winList;

	Window window;

	boolean ingresa_a_accion = false;

	int counter = 0;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	List<modelo_informativo> listaInformativo = new ArrayList<modelo_informativo>();
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	long id_mantenimiento = 8;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cmbLimite.setSelectedIndex(1);
		cargarPerfil();
		cargarFechas();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarInformativos();
			txtBuscar.setDisabled(false);
			lbxInformativos.setEmptyMessage(informativos.getMensaje_informativo_2());
		} else {
			txtBuscar.setDisabled(true);
			lbxInformativos.setEmptyMessage(informativos.getMensaje_informativo_22());
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
			btnNuevo.setVisible(true);
		} else {
			btnNuevo.setDisabled(true);
			btnNuevo.setVisible(false);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(txtBuscar.getText().toUpperCase());
			}
		});
	}

	public void cargarFechas() {
		Date fechaActual = new Date();
		Date primerDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth(), 1);
		Date ultimoDiaMes = new Date(fechaActual.getYear(), fechaActual.getMonth() + 1, 0);
		dtxFechaInicio.setValue(primerDiaMes);
		dtxFechaFin.setValue(ultimoDiaMes);
	}

	public void cargarInformativos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_informativo dao = new dao_informativo();
		String criterio = "";
		String fecha_inicio = "", fecha_fin = "";
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		try {
			listaInformativo = dao.obtenerInformativos(criterio, 1, id_dc,
					Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_inicio, fecha_fin);
			binder.loadComponent(lbxInformativos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
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

	public List<modelo_informativo> obtenerInformativos() {
		return listaInformativo;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			String criterio = txtBuscar.getText();
			buscarInformativos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbLimite")
	public void onSelect$cmbLimite() {
		try {
			String criterio = txtBuscar.getText();
			buscarInformativos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show(
					"Error al buscar en informativos. \n\n" + "Codigo de error: " + ((SQLException) e).getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Buscar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			String criterio = txtBuscar.getText();
			buscarInformativos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaInicio")
	public void onChange$dtxFechaInicio()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		try {
			String criterio = txtBuscar.getText();
			buscarInformativos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onChange=#dtxFechaFin")
	public void onChange$dtxFechaFin()
			throws NumberFormatException, ClassNotFoundException, FileNotFoundException, IOException {
		try {
			String criterio = txtBuscar.getText();
			buscarInformativos(criterio, 1);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarInformativos(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_informativo dao = new dao_informativo();
		String fecha_inicio = "", fecha_fin = "";
		if (dtxFechaInicio.getValue() != null) {
			fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (dtxFechaFin.getValue() != null) {
			fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(), "yyyy-MM-dd HH:mm:ss");
		}
		if (txtBuscar.getText().length() <= 0) {
			try {
				listaInformativo = dao.obtenerInformativos(criterio, tipo, id_dc,
						Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_inicio, fecha_fin);
				binder.loadComponent(lbxInformativos);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			try {
				listaInformativo = dao.obtenerInformativos(criterio, tipo, id_dc,
						Integer.valueOf(cmbLimite.getSelectedItem().getValue().toString()), fecha_inicio, fecha_fin);
				binder.loadComponent(lbxInformativos);
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en informativos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Listen("onRightClick=#lbxInformativos")
	public void onRightClick$lbxInformativos() throws Throwable {
		if (lbxInformativos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxInformativos.getSelectedIndex();
		String criterio = txtBuscar.getText();
		buscarInformativos(criterio, 1);
		int tamanio_lista = lbxInformativos.getItemCount();
		if (indice >= tamanio_lista) {
			return;
		}
		lbxInformativos.setSelectedIndex(indice);
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/informativo/nuevo.zul", null, null);
		btnNuevo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevo.setDisabled(false);
					String criterio = txtBuscar.getText();
					buscarInformativos(criterio, 1);
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mModificar")
	public void onDoubleClick$lbxInformativos() {
		if (lbxInformativos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxInformativos.getSelectedIndex();
		Sessions.getCurrent().setAttribute("informativo", listaInformativo.get(indice));
		window = (Window) Executions.createComponents("/mantenimientos/informativo/modificar.zul", null, null);
		mModificar.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mModificar.setDisabled(false);
					if (consultar.equals("S")) {
						cargarInformativos();
						ingresa_a_accion = false;
					}
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#mLeido")
	public void onClick$mLeido() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxInformativos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_accion == true) {
			return;
		}
		int indice = lbxInformativos.getSelectedIndex();
		long id = listaInformativo.get(indice).getId_informativo();
		List<modelo_relacion_informativo_usuario> listaRelacion = new ArrayList<modelo_relacion_informativo_usuario>();
		dao_relacion_informativo dao = new dao_relacion_informativo();
		listaRelacion = dao.obtenerRelacionesInformativos(String.valueOf(id), String.valueOf(id_user), 2);
		Sessions.getCurrent().setAttribute("listaRelacion", listaRelacion);
		window = (Window) Executions.createComponents("/mantenimientos/informativo/leido_usuario.zul", null, null);
		mLeido.setDisabled(true);
		ingresa_a_accion = true;
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					mLeido.setDisabled(false);
					if (consultar.equals("S")) {
						cargarInformativos();
						ingresa_a_accion = false;
					}
				}
			});
		}
		window.setParent(winList);
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
	}

}
