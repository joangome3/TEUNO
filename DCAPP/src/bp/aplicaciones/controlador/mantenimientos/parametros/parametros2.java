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
import bp.aplicaciones.mantenimientos.DAO.dao_campo;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_campo;
import bp.aplicaciones.mantenimientos.modelo.modelo_campo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros2 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbMantenimiento;
	@Wire
	Listbox lbxCampos;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_mantenimiento> listaMantenimiento = new ArrayList<modelo_mantenimiento>();
	List<modelo_campo> listaCampo = new ArrayList<modelo_campo>();
	List<modelo_relacion_campo_mantenimiento> listaRelacionCampo = new ArrayList<modelo_relacion_campo_mantenimiento>();

	long id_mantenimiento = 10;

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
		cargarMantenimientos();
		cargarCampos("", 1, 0);
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

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		String criterio = "";
		try {
			listaMantenimiento = dao.obtenerMantenimientos(criterio, 1);
			binder.loadComponent(cmbMantenimiento);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los mantenimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarCampos(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_campo dao = new dao_campo();
		try {
			listaCampo = dao.obtenerCampos(criterio, tipo, id);
			binder.loadComponent(lbxCampos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los campos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar campo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public List<modelo_mantenimiento> obtenerMantenimientos() {
		return listaMantenimiento;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_campo> obtenerCampos() {
		return listaCampo;
	}

	@Listen("onSelect=#cmbMantenimiento")
	public void onSelect$cmbMantenimiento()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbMantenimiento.getSelectedItem() == null) {
			return;
		}
		dao_relacion_campo dao = new dao_relacion_campo();
		long id_campo = 0;
		long id_mantenimiento = Long.parseLong(cmbMantenimiento.getSelectedItem().getValue().toString());
		for (int i = 0; i < lbxCampos.getItems().size(); i++) {
			id_campo = lbxCampos.getItemAtIndex(i).getIndex();
			listaRelacionCampo = dao.obtenerRelacionesMantenimientos(String.valueOf(id_campo),
					String.valueOf(id_mantenimiento), 1);
			if (listaRelacionCampo.size() > 0) {
				lbxCampos.getItemAtIndex(i).setSelected(true);
			} else {
				lbxCampos.getItemAtIndex(i).setSelected(false);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbMantenimiento.getSelectedItem() == null) {
			cmbMantenimiento.setErrorMessage("Seleccione un mantenimiento.");
			cmbMantenimiento.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #2 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_mantenimiento = Long
									.valueOf(cmbMantenimiento.getSelectedItem().getValue().toString());
							long id_campo = 0;
							dao_relacion_campo dao = new dao_relacion_campo();
							modelo_relacion_campo_mantenimiento relacion;
							List<modelo_relacion_campo_mantenimiento> listaRelacion = new ArrayList<modelo_relacion_campo_mantenimiento>();
							for (int i = 0; i < lbxCampos.getItems().size(); i++) {
								id_campo = lbxCampos.getItemAtIndex(i).getIndex();
								if (lbxCampos.getItemAtIndex(i).isSelected()) {
									relacion = new modelo_relacion_campo_mantenimiento();
									relacion.setId_campo(id_campo);
									relacion.setId_mantenimiento(id_mantenimiento);
									relacion.setEst_relacion("A");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(timestamp);
									listaRelacion.add(relacion);
								}
							}
							try {
								dao.insertarRelacion(listaRelacion, id_mantenimiento);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #2 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #2 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
