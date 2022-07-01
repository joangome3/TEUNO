//package bp.aplicaciones.controlador.mantenimientos.solicitante;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
//import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
//import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
//import bp.aplicaciones.mantenimientos.DAO.dao_relacion_solicitante;
//import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
//import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_localidad;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_opcion;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class relacionar_bk extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zRelacionar;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtNombre;
//	@Wire
//	Listbox lbxMantenimientos, lbxOpciones, lbxLocalidades;
//
//	long id = 0;
//	long id_mantenimiento = 1;
//
//	List<modelo_mantenimiento> listaMantenimientos = new ArrayList<modelo_mantenimiento>();
//	List<modelo_opcion> listaOpciones = new ArrayList<modelo_opcion>();
//	List<modelo_localidad> listaLocalidades = new ArrayList<modelo_localidad>();
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//	modelo_solicitante solicitante = (modelo_solicitante) Sessions.getCurrent().getAttribute("solicitante");
//
//	validar_datos validar = new validar_datos();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		lbxMantenimientos.setEmptyMessage("!No existen datos que mostrar¡.");
//		lbxOpciones.setEmptyMessage("!No existen datos que mostrar¡.");
//		lbxLocalidades.setEmptyMessage("!No existen datos que mostrar¡.");
//		cargarMantenimientos();
//		cargarOpciones();
//		cargarLocalidades();
//		cargarDatos();
//	}
//
//	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		txtId.setText(String.valueOf(solicitante.getId_solicitante()));
//		txtNombre.setText(solicitante.getNom_solicitante() + " " + solicitante.getApe_solicitante());
//		dao_relacion_solicitante dao = new dao_relacion_solicitante();
//		try {
//			for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
//				if (dao.obtenerRelacionesMantenimientos(String.valueOf(solicitante.getId_solicitante()),
//						String.valueOf(listaMantenimientos.get(i).getId_mantenimiento())) == true) {
//					lbxMantenimientos.getItemAtIndex(i).setSelected(true);
//				}
//			}
//			for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
//				if (dao.obtenerRelacionesOpciones(String.valueOf(solicitante.getId_solicitante()),
//						String.valueOf(listaOpciones.get(i).getId_opcion())) == true) {
//					lbxOpciones.getItemAtIndex(i).setSelected(true);
//				}
//			}
//			for (int i = 0; i < lbxLocalidades.getItems().size(); i++) {
//				if (dao.obtenerRelacionesLocalidades(String.valueOf(solicitante.getId_solicitante()),
//						String.valueOf(listaLocalidades.get(i).getId_localidad())) == true) {
//					lbxLocalidades.getItemAtIndex(i).setSelected(true);
//				}
//			}
//		} catch (SQLException e) {
//			Messagebox.show("Error al validar relaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Validar relaciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public List<modelo_mantenimiento> obtenerMantenimientos() {
//		return listaMantenimientos;
//	}
//
//	public List<modelo_opcion> obtenerOpciones() {
//		return listaOpciones;
//	}
//
//	public List<modelo_localidad> obtenerLocalidades() {
//		return listaLocalidades;
//	}
//
//	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_mantenimiento dao = new dao_mantenimiento();
//		String criterio = "";
//		try {
//			listaMantenimientos = dao.obtenerMantenimientos(criterio, 4);
//			binder.loadComponent(lbxMantenimientos);
//		} catch (SQLException e) {
//			Messagebox.show("Error al cargar los mantenimientos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar mantenimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_opcion dao = new dao_opcion();
//		String criterio = "";
//		try {
//			listaOpciones = dao.obtenerOpciones(criterio);
//			binder.loadComponent(lbxOpciones);
//		} catch (SQLException e) {
//			Messagebox.show("Error al cargar las opciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar opciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_localidad dao = new dao_localidad();
//		String criterio = "";
//		try {
//			listaLocalidades = dao.obtenerLocalidades(criterio, 1, 0, 0);
//			binder.loadComponent(lbxLocalidades);
//		} catch (SQLException e) {
//			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		Messagebox.show("Esta seguro de guardar la relacion?", ".:: Relacionar solicitante ::.",
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_relacion_solicitante dao = new dao_relacion_solicitante();
//							List<modelo_relacion_solicitante_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_solicitante_mantenimiento>();
//							List<modelo_relacion_solicitante_opcion> listaRelacionOpciones = new ArrayList<modelo_relacion_solicitante_opcion>();
//							List<modelo_relacion_solicitante_localidad> listaRelacionLocalidades = new ArrayList<modelo_relacion_solicitante_localidad>();
//							for (int i = 0; i < lbxMantenimientos.getItems().size(); i++) {
//								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
//									modelo_relacion_solicitante_mantenimiento relacion_solicitante_mantenimiento = new modelo_relacion_solicitante_mantenimiento();
//									relacion_solicitante_mantenimiento
//											.setId_solicitante(Long.parseLong(txtId.getText()));
//									final int indice = lbxMantenimientos.getItemAtIndex(i).getIndex();
//									relacion_solicitante_mantenimiento
//											.setId_mantenimiento(listaMantenimientos.get(indice).getId_mantenimiento());
//									relacion_solicitante_mantenimiento.setEst_relacion("A");
//									relacion_solicitante_mantenimiento.setUsu_ingresa(user);
//									java.util.Date date = new Date();
//									Timestamp timestamp = new Timestamp(date.getTime());
//									relacion_solicitante_mantenimiento.setFec_ingresa(timestamp);
//									listaRelacionMantenimientos.add(relacion_solicitante_mantenimiento);
//								}
//							}
//							for (int i = 0; i < lbxOpciones.getItems().size(); i++) {
//								if (lbxOpciones.getItemAtIndex(i).isSelected()) {
//									modelo_relacion_solicitante_opcion relacion_solicitante_opcion = new modelo_relacion_solicitante_opcion();
//									relacion_solicitante_opcion.setId_solicitante(Long.parseLong(txtId.getText()));
//									final int indice = lbxOpciones.getItemAtIndex(i).getIndex();
//									relacion_solicitante_opcion.setId_opcion(listaOpciones.get(indice).getId_opcion());
//									relacion_solicitante_opcion.setEst_relacion("A");
//									relacion_solicitante_opcion.setUsu_ingresa(user);
//									java.util.Date date = new Date();
//									Timestamp timestamp = new Timestamp(date.getTime());
//									relacion_solicitante_opcion.setFec_ingresa(timestamp);
//									listaRelacionOpciones.add(relacion_solicitante_opcion);
//								}
//							}
//							for (int i = 0; i < lbxLocalidades.getItems().size(); i++) {
//								if (lbxLocalidades.getItemAtIndex(i).isSelected()) {
//									modelo_relacion_solicitante_localidad relacion_solicitante_localidad = new modelo_relacion_solicitante_localidad();
//									relacion_solicitante_localidad.setId_solicitante(Long.parseLong(txtId.getText()));
//									final int indice = lbxLocalidades.getItemAtIndex(i).getIndex();
//									relacion_solicitante_localidad
//											.setId_localidad(listaLocalidades.get(indice).getId_localidad());
//									relacion_solicitante_localidad.setEst_relacion("A");
//									relacion_solicitante_localidad.setUsu_ingresa(user);
//									java.util.Date date = new Date();
//									Timestamp timestamp = new Timestamp(date.getTime());
//									relacion_solicitante_localidad.setFec_ingresa(timestamp);
//									listaRelacionLocalidades.add(relacion_solicitante_localidad);
//								}
//							}
//							java.util.Date date = new Date();
//							Timestamp timestamp = new Timestamp(date.getTime());
//							solicitante.setEst_solicitante("AE");
//							solicitante.setUsu_modifica(user);
//							solicitante.setFec_modifica(timestamp);
//							int tipo = 2;
//							try {
//								dao.insertarRelacion(listaRelacionMantenimientos, listaRelacionOpciones,
//										listaRelacionLocalidades, Long.parseLong(txtId.getText()), solicitante, tipo);
//								Messagebox.show("La relacion se guardo correctamente.",
//										".:: Relacionar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//								Sessions.getCurrent().removeAttribute("solicitante");
//								Events.postEvent(new Event("onClose", zRelacionar));
//							} catch (Exception e) {
//								Messagebox.show(
//										"Error al guardar la relacion. \n\n" + "Mensaje de error: \n\n"
//												+ e.getMessage(),
//										".:: Relacionar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//							}
//						}
//					}
//				});
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Events.postEvent(new Event("onClose", zRelacionar));
//	}
//
//}
