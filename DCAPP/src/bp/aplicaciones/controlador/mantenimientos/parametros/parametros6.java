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
import bp.aplicaciones.mantenimientos.DAO.dao_criticidad;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_6;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_criticidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_6;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros6 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbCriticidad;
	@Wire
	Colorbox coxCriticidad;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_criticidad> listaCriticidad = new ArrayList<modelo_criticidad>();
	List<modelo_parametros_generales_6> listaRelacionCriticidad = new ArrayList<modelo_parametros_generales_6>();

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
		cargarCriticidades();
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

	public void cargarCriticidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_criticidad dao = new dao_criticidad();
		String criterio = "";
		try {
			listaCriticidad = dao.obtenerCriticidades(criterio, 1, 0, 0);
			binder.loadComponent(cmbCriticidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las criticidads. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar criticidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public List<modelo_criticidad> obtenerCriticidades() {
		return listaCriticidad;
	}

	@Listen("onSelect=#cmbCriticidad")
	public void onSelect$cmbCriticidad()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbCriticidad.getSelectedItem() == null) {
			return;
		}
		dao_parametros_generales_6 dao = new dao_parametros_generales_6();
		long id_criticidad = 0;
		id_criticidad = Long.parseLong(cmbCriticidad.getSelectedItem().getValue().toString());
		listaRelacionCriticidad = dao.obtenerRelacionesCriticidades("", "", id_dc, 2);
		int bandera = 0;
		String color = "";
		for (int i = 0; i < listaRelacionCriticidad.size(); i++) {
			if (listaRelacionCriticidad.get(i).getId_criticidad() == id_criticidad) {
				color = listaRelacionCriticidad.get(i).getColor();
				bandera = 1;
				i = listaRelacionCriticidad.size() + 1;
			}
		}
		if (bandera == 1) {
			coxCriticidad.setValue(color);
		} else {
			coxCriticidad.setValue(null);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbCriticidad.getSelectedItem() == null) {
			cmbCriticidad.setErrorMessage("Seleccione una criticidad.");
			cmbCriticidad.setFocus(true);
			return;
		}
		if (coxCriticidad.getValue() == null) {
			coxCriticidad.setFocus(true);
			Messagebox.show("seleccione un color.", ".:: Parametros generales #6 ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #6 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_criticidad = Long.valueOf(cmbCriticidad.getSelectedItem().getValue().toString());
							dao_parametros_generales_6 dao = new dao_parametros_generales_6();
							modelo_parametros_generales_6 relacion;
							List<modelo_parametros_generales_6> listaRelacion = new ArrayList<modelo_parametros_generales_6>();
							relacion = new modelo_parametros_generales_6();
							relacion.setId_criticidad(id_criticidad);
							relacion.setColor(coxCriticidad.getValue());
							relacion.setId_localidad(id_dc);
							relacion.setEst_relacion("A");
							relacion.setUsu_ingresa(user);
							relacion.setFec_ingresa(timestamp);
							listaRelacion.add(relacion);
							try {
								dao.insertarRelacion(listaRelacion, id_criticidad, id_dc);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #6 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #6 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
