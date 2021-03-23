package bp.aplicaciones.controlador.mantenimientos.empresa;

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
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_empresa_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_empresa_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_empresa_opcion;

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
	Listbox lbxMantenimientos, lbxOpciones, lbxLocalidades;

	long id = 0;
	long id_mantenimiento = 8;

	List<modelo_mantenimiento> listaMantenimientos = new ArrayList<modelo_mantenimiento>();
	List<modelo_opcion> listaOpciones = new ArrayList<modelo_opcion>();
	List<modelo_localidad> listaLocalidades = new ArrayList<modelo_localidad>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_empresa empresa = (modelo_empresa) Sessions.getCurrent().getAttribute("empresa");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxMantenimientos.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxOpciones.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxLocalidades.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarMantenimientos();
		cargarOpciones();
		cargarLocalidades();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(empresa.getId_empresa()));
		txtNombre.setText(empresa.getNom_empresa());
		dao_relacion_empresa dao = new dao_relacion_empresa();
		try {
			for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
				if (dao.obtenerRelacionesMantenimientos(String.valueOf(empresa.getId_empresa()),
						String.valueOf(listaMantenimientos.get(i).getId_mantenimiento())) == true) {
					lbxMantenimientos.getItemAtIndex(i).setSelected(true);
				}
			}
			for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
				if (dao.obtenerRelacionesOpciones(String.valueOf(empresa.getId_empresa()),
						String.valueOf(listaOpciones.get(i).getId_opcion())) == true) {
					lbxOpciones.getItemAtIndex(i).setSelected(true);
				}
			}
			for (int i = 0; i < lbxLocalidades.getItems().size(); i++) {
				if (dao.obtenerRelacionesLocalidades(String.valueOf(empresa.getId_empresa()),
						String.valueOf(listaLocalidades.get(i).getId_localidad())) == true) {
					lbxLocalidades.getItemAtIndex(i).setSelected(true);
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

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidades;
	}

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		String criterio = "";
		try {
			listaMantenimientos = dao.obtenerMantenimientos(criterio, 3);
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

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidades = dao.obtenerLocalidades(criterio, 4, 0, 0);
			binder.loadComponent(lbxLocalidades);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		Messagebox.show("Esta seguro de guardar la relacion?", ".:: Relacionar empresa ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_relacion_empresa dao = new dao_relacion_empresa();
							List<modelo_relacion_empresa_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_empresa_mantenimiento>();
							List<modelo_relacion_empresa_opcion> listaRelacionOpciones = new ArrayList<modelo_relacion_empresa_opcion>();
							List<modelo_relacion_empresa_localidad> listaRelacionLocalidades = new ArrayList<modelo_relacion_empresa_localidad>();
							for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
									modelo_relacion_empresa_mantenimiento relacion_empresa_mantenimiento = new modelo_relacion_empresa_mantenimiento();
									relacion_empresa_mantenimiento.setId_empresa(Long.parseLong(txtId.getText()));
									final int indice = lbxMantenimientos.getItemAtIndex(i).getIndex();
									relacion_empresa_mantenimiento
											.setId_mantenimiento(listaMantenimientos.get(indice).getId_mantenimiento());
									relacion_empresa_mantenimiento.setEst_relacion("A");
									relacion_empresa_mantenimiento.setUsu_ingresa(user);
									java.util.Date date = new Date();
									Timestamp timestamp = new Timestamp(date.getTime());
									relacion_empresa_mantenimiento.setFec_ingresa(timestamp);
									listaRelacionMantenimientos.add(relacion_empresa_mantenimiento);
								}
							}
							for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
								if (lbxOpciones.getItemAtIndex(i).isSelected()) {
									modelo_relacion_empresa_opcion relacion_empresa_opcion = new modelo_relacion_empresa_opcion();
									relacion_empresa_opcion.setId_empresa(Long.parseLong(txtId.getText()));
									final int indice = lbxOpciones.getItemAtIndex(i).getIndex();
									relacion_empresa_opcion.setId_opcion(listaOpciones.get(indice).getId_opcion());
									relacion_empresa_opcion.setEst_relacion("A");
									relacion_empresa_opcion.setUsu_ingresa(user);
									java.util.Date date = new Date();
									Timestamp timestamp = new Timestamp(date.getTime());
									relacion_empresa_opcion.setFec_ingresa(timestamp);
									listaRelacionOpciones.add(relacion_empresa_opcion);
								}
							}
							for (int i = 0; i < lbxLocalidades.getItems().size(); i++) {
								if (lbxLocalidades.getItemAtIndex(i).isSelected()) {
									modelo_relacion_empresa_localidad relacion_empresa_localidad = new modelo_relacion_empresa_localidad();
									relacion_empresa_localidad.setId_empresa(Long.parseLong(txtId.getText()));
									final int indice = lbxLocalidades.getItemAtIndex(i).getIndex();
									relacion_empresa_localidad
											.setId_localidad(listaLocalidades.get(indice).getId_localidad());
									relacion_empresa_localidad.setEst_relacion("A");
									relacion_empresa_localidad.setUsu_ingresa(user);
									java.util.Date date = new Date();
									Timestamp timestamp = new Timestamp(date.getTime());
									relacion_empresa_localidad.setFec_ingresa(timestamp);
									listaRelacionLocalidades.add(relacion_empresa_localidad);
								}
							}
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							empresa.setEst_empresa("AE");
							empresa.setUsu_modifica(user);
							empresa.setFec_modifica(timestamp);
							int tipo = 2;
							try {
								dao.insertarRelacion(listaRelacionMantenimientos, listaRelacionOpciones,
										listaRelacionLocalidades, Long.parseLong(txtId.getText()), empresa, tipo);
								Messagebox.show("La relacion se guardo correctamente.", ".:: Relacionar empresa ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								Sessions.getCurrent().removeAttribute("empresa");
								Events.postEvent(new Event("onClose", zRelacionar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar la relacion. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Relacionar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
