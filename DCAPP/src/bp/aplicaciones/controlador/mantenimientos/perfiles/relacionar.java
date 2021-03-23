package bp.aplicaciones.controlador.mantenimientos.perfiles;

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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_perfil_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_perfil_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings({ "serial", "deprecation" })
public class relacionar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zRelacionar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre;
	@Wire
	Listbox lbxMantenimientos, lbxOpciones;

	long id = 0;

	List<modelo_mantenimiento> listaMantenimientos = new ArrayList<modelo_mantenimiento>();
	List<modelo_opcion> listaOpciones = new ArrayList<modelo_opcion>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_perfil perfil = (modelo_perfil) Sessions.getCurrent().getAttribute("perfil");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxMantenimientos.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxOpciones.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarMantenimientos();
		cargarOpciones();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(perfil.getId_perfil()));
		txtNombre.setText(perfil.getNom_perfil());
		dao_relacion_perfil dao = new dao_relacion_perfil();
		try {
			for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
				if (dao.obtenerRelacionesMantenimientos(String.valueOf(perfil.getId_perfil()),
						String.valueOf(listaMantenimientos.get(i).getId_mantenimiento()), 1) == true) {
					lbxMantenimientos.getItemAtIndex(i).setSelected(true);
				}
			}
			for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
				if (dao.obtenerRelacionesOpciones(String.valueOf(perfil.getId_perfil()),
						String.valueOf(listaOpciones.get(i).getId_opcion()), 2) == true) {
					lbxOpciones.getItemAtIndex(i).setSelected(true);
				}
			}
		} catch (SQLException e) {
			Messagebox.show("Error al validar relaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Validar relaciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public List<modelo_mantenimiento> obtenerMantenimientos() {
		return listaMantenimientos;
	}

	public List<modelo_opcion> obtenerOpciones() {
		return listaOpciones;
	}

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		String criterio = "";
		try {
			listaMantenimientos = dao.obtenerMantenimientos(criterio, 1);
			binder.loadComponent(lbxMantenimientos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los mantenimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_opcion dao = new dao_opcion();
		String criterio = "";
		try {
			listaOpciones = dao.obtenerOpciones(criterio);
			binder.loadComponent(lbxOpciones);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las opciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar opciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar la relacion?", ".:: Relacionar perfil ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_relacion_perfil dao = new dao_relacion_perfil();
							List<modelo_relacion_perfil_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_perfil_mantenimiento>();
							List<modelo_relacion_perfil_opcion> listaRelacionOpciones = new ArrayList<modelo_relacion_perfil_opcion>();
							for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
									modelo_relacion_perfil_mantenimiento relacion_perfil_mantenimiento = new modelo_relacion_perfil_mantenimiento();
									relacion_perfil_mantenimiento
											.setId_perfil(Long.parseLong(txtId.getText()));
									final int indice = lbxMantenimientos.getItemAtIndex(i).getIndex();
									relacion_perfil_mantenimiento
											.setId_mantenimiento(listaMantenimientos.get(indice).getId_mantenimiento());
									relacion_perfil_mantenimiento.setEst_relacion("A");
									relacion_perfil_mantenimiento.setUsu_ingresa(user);
									java.util.Date date = new Date();
									Timestamp timestamp = new Timestamp(date.getTime());
									relacion_perfil_mantenimiento.setFec_ingresa(timestamp);
									listaRelacionMantenimientos.add(relacion_perfil_mantenimiento);
								}
							}
							for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
								if (lbxOpciones.getItemAtIndex(i).isSelected()) {
									modelo_relacion_perfil_opcion relacion_perfil_opcion = new modelo_relacion_perfil_opcion();
									relacion_perfil_opcion.setId_perfil(Long.parseLong(txtId.getText()));
									final int indice = lbxOpciones.getItemAtIndex(i).getIndex();
									relacion_perfil_opcion.setId_opcion(listaOpciones.get(indice).getId_opcion());
									relacion_perfil_opcion.setEst_relacion("A");
									relacion_perfil_opcion.setUsu_ingresa(user);
									java.util.Date date = new Date();
									Timestamp timestamp = new Timestamp(date.getTime());
									relacion_perfil_opcion.setFec_ingresa(timestamp);
									listaRelacionOpciones.add(relacion_perfil_opcion);
								}
							}
							try {
								dao.insertarRelacion(listaRelacionMantenimientos, listaRelacionOpciones, Long.parseLong(txtId.getText()));
								Messagebox.show("La relacion se guardo correctamente.",
										".:: Relacionar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								Sessions.getCurrent().removeAttribute("perfil");
								Events.postEvent(new Event("onClose", zRelacionar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la relacion. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Relacionar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zRelacionar));
	}

}
