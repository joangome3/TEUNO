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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_servicio;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_11;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros11 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Textbox txtOpcion;
	@Wire
	Listbox lbxTipoServicios;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_parametros_generales_11> listaRelacionTipoServicio = new ArrayList<modelo_parametros_generales_11>();

	long id_opcion = 3;

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
		cargarOpciones();
		cargarTipoServicios("");
		for (int i = 0; i < listaOpcion.size(); i++) {
			if (listaOpcion.get(i).getId_opcion() == id_opcion) {
				txtOpcion.setText(listaOpcion.get(i).getNom_opcion());
				i = listaOpcion.size() + 1;
			}
		}
		cargarDatos();
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

	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_opcion dao = new dao_opcion();
		String criterio = "";
		try {
			listaOpcion = dao.obtenerOpciones(criterio);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los opcions. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar opcion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoServicios(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		try {
			listaTipoServicio = dao.obtenerTipoServicios(criterio, 1, 0, 0);
			binder.loadComponent(lbxTipoServicios);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipo de servicios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar tipo de servicios ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_parametros_generales_11 dao = new dao_parametros_generales_11();
		long id_tipo_servicio = 0;
		for (int i = 0; i < lbxTipoServicios.getItems().size(); i++) {
			id_tipo_servicio = listaTipoServicio.get(lbxTipoServicios.getItemAtIndex(i).getIndex())
					.getId_tipo_servicio();
			listaRelacionTipoServicio = dao.obtenerRelacionesOpciones(String.valueOf(id_opcion),
					String.valueOf(id_tipo_servicio), String.valueOf(id_dc), 1);
			if (listaRelacionTipoServicio.size() > 0) {
				lbxTipoServicios.getItemAtIndex(i).setSelected(true);
			} else {
				lbxTipoServicios.getItemAtIndex(i).setSelected(false);
			}
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
					"Existen inconsistencias con los permisos del perfil asignado al tipo_servicio. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public List<modelo_opcion> obtenerOpciones() {
		return listaOpcion;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicios() {
		return listaTipoServicio;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #10 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_tipo_servicio = 0;
							dao_parametros_generales_11 dao = new dao_parametros_generales_11();
							modelo_parametros_generales_11 relacion;
							List<modelo_parametros_generales_11> listaRelacion = new ArrayList<modelo_parametros_generales_11>();
							for (int i = 0; i < lbxTipoServicios.getItems().size(); i++) {
								id_tipo_servicio = listaTipoServicio.get(lbxTipoServicios.getItemAtIndex(i).getIndex())
										.getId_tipo_servicio();
								if (lbxTipoServicios.getItemAtIndex(i).isSelected()) {
									relacion = new modelo_parametros_generales_11();
									relacion.setId_tipo_servicio(id_tipo_servicio);
									relacion.setId_opcion(id_opcion);
									relacion.setId_localidad(id_dc);
									relacion.setEst_relacion("A");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(timestamp);
									listaRelacion.add(relacion);
								}
							}
							try {
								dao.insertarRelacion(listaRelacion, id_opcion, id_dc);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #10 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #10 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
