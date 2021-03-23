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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_5;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros5 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbOpcion;
	@Wire
	Listbox lbxUsuarios;

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
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_parametros_generales_5> listaRelacionUsuario = new ArrayList<modelo_parametros_generales_5>();

	long id_opcion = 10;

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
		cargarUsuarios("", 0, 0);
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
			binder.loadComponent(cmbOpcion);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los opcions. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar opcion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUsuarios(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		try {
			listaUsuario = dao.obtenerUsuarios(criterio, 1, 0);
			binder.loadComponent(lbxUsuarios);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los usuarios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public List<modelo_opcion> obtenerOpciones() {
		return listaOpcion;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	@Listen("onSelect=#cmbOpcion")
	public void onSelect$cmbOpcion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbOpcion.getSelectedItem() == null) {
			return;
		}
		dao_parametros_generales_5 dao = new dao_parametros_generales_5();
		long id_usuario = 0;
		long id_opcion = Long.parseLong(cmbOpcion.getSelectedItem().getValue().toString());
		for (int i = 0; i < lbxUsuarios.getItems().size(); i++) {
			id_usuario = listaUsuario.get(lbxUsuarios.getItemAtIndex(i).getIndex()).getId_usuario();
			listaRelacionUsuario = dao.obtenerRelacionesOpciones(String.valueOf(id_usuario), String.valueOf(id_opcion),
					1);
			if (listaRelacionUsuario.size() > 0) {
				lbxUsuarios.getItemAtIndex(i).setSelected(true);
			} else {
				lbxUsuarios.getItemAtIndex(i).setSelected(false);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbOpcion.getSelectedItem() == null) {
			cmbOpcion.setErrorMessage("Seleccione un opcion.");
			cmbOpcion.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #3 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_opcion = Long.valueOf(cmbOpcion.getSelectedItem().getValue().toString());
							long id_usuario = 0;
							dao_parametros_generales_5 dao = new dao_parametros_generales_5();
							modelo_parametros_generales_5 relacion;
							List<modelo_parametros_generales_5> listaRelacion = new ArrayList<modelo_parametros_generales_5>();
							for (int i = 0; i < lbxUsuarios.getItems().size(); i++) {
								id_usuario = listaUsuario.get(lbxUsuarios.getItemAtIndex(i).getIndex()).getId_usuario();
								if (lbxUsuarios.getItemAtIndex(i).isSelected()) {
									relacion = new modelo_parametros_generales_5();
									relacion.setId_usuario(id_usuario);
									relacion.setId_opcion(id_opcion);
									relacion.setEst_relacion("A");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(timestamp);
									listaRelacion.add(relacion);
								}
							}
							try {
								dao.insertarRelacion(listaRelacion, id_opcion);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #3 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #3 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
