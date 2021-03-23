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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_9;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros9 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Combobox cmbEmpresa;
	@Wire
	Textbox txtNomTicket;
	@Wire
	Intbox intCantidadCaracteres;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_parametros_generales_9> listaRelacionEmpresa = new ArrayList<modelo_parametros_generales_9>();

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
		cargarEmpresas("", 0, 0);
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

	public void cargarEmpresas(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 0, String.valueOf(id_dc), "", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las empresas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbEmpresa.getSelectedItem() == null) {
			return;
		}
		dao_parametros_generales_9 dao = new dao_parametros_generales_9();
		long id_empresa = 0;
		id_empresa = Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString());
		listaRelacionEmpresa = dao.obtenerRelacionesEmpresas("", "", id_dc, 2);
		int bandera = 0;
		String nom_ticket = "";
		int cant_caracteres = 0;
		for (int i = 0; i < listaRelacionEmpresa.size(); i++) {
			if (listaRelacionEmpresa.get(i).getId_empresa() == id_empresa) {
				nom_ticket = listaRelacionEmpresa.get(i).getNom_ticket();
				cant_caracteres = listaRelacionEmpresa.get(i).getCant_caracteres();
				bandera = 1;
				i = listaRelacionEmpresa.size() + 1;
			}
		}
		if (bandera == 1) {
			txtNomTicket.setValue(nom_ticket);
			intCantidadCaracteres.setValue(cant_caracteres);
		} else {
			txtNomTicket.setValue("");
			intCantidadCaracteres.setValue(0);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbEmpresa.getSelectedItem() == null) {
			cmbEmpresa.setErrorMessage("Seleccione un empresa.");
			cmbEmpresa.setFocus(true);
			return;
		}
		if (txtNomTicket.getValue() == null) {
			txtNomTicket.setFocus(true);
			Messagebox.show("Registre una etiqueta para ser usada en la empresa seleccionada",
					".:: Parametros generales #9 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtNomTicket.getText().length() <= 0) {
			txtNomTicket.setFocus(true);
			txtNomTicket.setErrorMessage("Registre una etiqueta para ser usada en la empresa seleccionada");
			return;
		}
		if (intCantidadCaracteres.getValue() == 0 || intCantidadCaracteres.getValue() > 20) {
			intCantidadCaracteres.setFocus(true);
			intCantidadCaracteres.setErrorMessage("La cantidad de caracteres a validar debe ser entre [1-20]");
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #9 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							long id_empresa = Long.valueOf(cmbEmpresa.getSelectedItem().getValue().toString());
							dao_parametros_generales_9 dao = new dao_parametros_generales_9();
							modelo_parametros_generales_9 relacion;
							List<modelo_parametros_generales_9> listaRelacion = new ArrayList<modelo_parametros_generales_9>();
							relacion = new modelo_parametros_generales_9();
							relacion.setId_empresa(id_empresa);
							relacion.setNom_ticket(txtNomTicket.getText().toUpperCase());
							relacion.setCant_caracteres(intCantidadCaracteres.getValue());
							relacion.setId_localidad(id_dc);
							relacion.setEst_relacion("A");
							relacion.setUsu_ingresa(user);
							relacion.setFec_ingresa(timestamp);
							listaRelacion.add(relacion);
							try {
								dao.insertarRelacion(listaRelacion, id_empresa, id_dc);
								Messagebox.show("Los parametros se guardaron correctamente.",
										".:: Parametros generales #9 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Parametros generales #9 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

}
