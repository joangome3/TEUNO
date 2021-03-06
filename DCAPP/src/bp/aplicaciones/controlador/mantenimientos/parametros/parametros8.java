package bp.aplicaciones.controlador.mantenimientos.parametros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Colorbox;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_8;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_8;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros8 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbEstado;
	@Wire
	Colorbox coxEstado;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_estado_bitacora> listaEstado = new ArrayList<modelo_estado_bitacora>();
	List<modelo_parametros_generales_8> listaRelacionEstado = new ArrayList<modelo_parametros_generales_8>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfil();
		inicializarPermisos();
		if (insertar.equals("S")) {
			btnGrabar.setDisabled(false);
			btnGrabar.setVisible(true);
		} else {
			btnGrabar.setDisabled(true);
			btnGrabar.setVisible(false);
		}
		cargarEstados();
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

	public void cargarEstados() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_bitacora dao = new dao_estado_bitacora();
		String criterio = "";
		try {
			listaEstado = dao.obtenerEstados(criterio, 0, "");
			binder.loadComponent(cmbEstado);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ?Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public List<modelo_estado_bitacora> obtenerEstados() {
		return listaEstado;
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbEstado.getSelectedItem() == null) {
			return;
		}
		dao_parametros_generales_8 dao = new dao_parametros_generales_8();
		long id_estado = 0;
		id_estado = Long.parseLong(cmbEstado.getSelectedItem().getValue().toString());
		listaRelacionEstado = dao.obtenerRelacionesEstados("", "", id_dc, 2);
		int bandera = 0;
		String color = "";
		for (int i = 0; i < listaRelacionEstado.size(); i++) {
			if (listaRelacionEstado.get(i).getId_estado() == id_estado) {
				color = listaRelacionEstado.get(i).getColor();
				bandera = 1;
				i = listaRelacionEstado.size() + 1;
			}
		}
		if (bandera == 1) {
			coxEstado.setValue(color);
		} else {
			coxEstado.setValue(null);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbEstado.getSelectedItem() == null) {
			cmbEstado.setErrorMessage("Seleccione un estado.");
			cmbEstado.setFocus(true);
			return;
		}
		if (coxEstado.getValue() == null) {
			coxEstado.setFocus(true);
			Messagebox.show("seleccione un color.", ".:: Parametros generales #8 ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #8 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_estado = Long.valueOf(cmbEstado.getSelectedItem().getValue().toString());
							dao_parametros_generales_8 dao = new dao_parametros_generales_8();
							modelo_parametros_generales_8 relacion;
							List<modelo_parametros_generales_8> listaRelacion = new ArrayList<modelo_parametros_generales_8>();
							relacion = new modelo_parametros_generales_8();
							relacion.setId_estado(id_estado);
							relacion.setColor(coxEstado.getValue());
							relacion.setId_localidad(id_dc);
							relacion.setEst_relacion("A");
							relacion.setUsu_ingresa(user);
							relacion.setFec_ingresa(timestamp);
							listaRelacion.add(relacion);
							try {
								dao.insertarRelacion(listaRelacion, id_estado, id_dc);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #8 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #8 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
