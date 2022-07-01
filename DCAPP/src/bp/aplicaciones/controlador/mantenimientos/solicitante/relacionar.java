package bp.aplicaciones.controlador.mantenimientos.solicitante;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "serial", "deprecation" })
public class relacionar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zRelacionarSolicitante;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Listbox lbxMantenimientos, lbxOpciones, lbxLocalidades;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;
	long id_mantenimiento = 1;

	List<modelo_mantenimiento> listaMantenimientos = new ArrayList<modelo_mantenimiento>();
	List<modelo_opcion> listaOpciones = new ArrayList<modelo_opcion>();
	List<modelo_localidad> listaLocalidades = new ArrayList<modelo_localidad>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_solicitante solicitante = (modelo_solicitante) Sessions.getCurrent().getAttribute("solicitante");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxMantenimientos.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxOpciones.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarMantenimientos();
		cargarOpciones();
		cargarLocalidades();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (int i = 0; i < solicitante.getRelaciones_solicitante_mantenimiento().size(); i++) {
			for (int j = 0; j < listaMantenimientos.size(); j++) {
				if (solicitante.getRelaciones_solicitante_mantenimiento().get(i).getMantenimiento()
						.getId_mantenimiento() == listaMantenimientos.get(j).getId_mantenimiento()) {
					lbxMantenimientos.getItemAtIndex(j).setSelected(true);
					break;
				}
			}
		}
		for (int i = 0; i < solicitante.getRelaciones_solicitante_opcion().size(); i++) {
			for (int j = 0; j < listaOpciones.size(); j++) {
				if (solicitante.getRelaciones_solicitante_opcion().get(i).getOpcion().getId_opcion() == listaOpciones
						.get(j).getId_opcion()) {
					lbxOpciones.getItemAtIndex(j).setSelected(true);
					break;
				}
			}
		}
		for (int i = 0; i < solicitante.getRelaciones_solicitante_localidad().size(); i++) {
			for (int j = 0; j < listaLocalidades.size(); j++) {
				if (solicitante.getRelaciones_solicitante_localidad().get(i).getLocalidad()
						.getId_localidad() == listaLocalidades.get(j).getId_localidad()) {
					lbxLocalidades.getItemAtIndex(j).setSelected(true);
					break;
				}
			}
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
		listaMantenimientos = dao.consultarMantenimientos(id_mantenimiento, 0, "", "", 0, 6);
		binder.loadComponent(lbxMantenimientos);
	}

	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_opcion dao = new dao_opcion();
		listaOpciones = dao.consultarOpciones(id_mantenimiento, 0, "", "", 0, 6);
		binder.loadComponent(lbxOpciones);
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		listaLocalidades = dao.consultarLocalidades(0, 0, "", "", 0, 2);
		binder.loadComponent(lbxLocalidades);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar los permisos?", ".:: Asignar permisos ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_relacion_solicitante dao = new dao_relacion_solicitante();
							List<modelo_relacion_solicitante_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_solicitante_mantenimiento>();
							List<modelo_relacion_solicitante_opcion> listaRelacionOpciones = new ArrayList<modelo_relacion_solicitante_opcion>();
							List<modelo_relacion_solicitante_localidad> listaRelacionLocalidades = new ArrayList<modelo_relacion_solicitante_localidad>();
							for (int i = 0; i < lbxMantenimientos.getItemCount(); i++) {
								modelo_relacion_solicitante_mantenimiento relacion = new modelo_relacion_solicitante_mantenimiento();
								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
									relacion.setSolicitante(solicitante);
									relacion.setMantenimiento(
											listaMantenimientos.get(lbxMantenimientos.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionMantenimientos.add(relacion);
								}
							}
							for (int i = 0; i < lbxOpciones.getItemCount(); i++) {
								modelo_relacion_solicitante_opcion relacion = new modelo_relacion_solicitante_opcion();
								if (lbxOpciones.getItemAtIndex(i).isSelected()) {
									relacion.setSolicitante(solicitante);
									relacion.setOpcion(listaOpciones.get(lbxOpciones.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionOpciones.add(relacion);
								}
							}
							for (int i = 0; i < lbxLocalidades.getItemCount(); i++) {
								modelo_relacion_solicitante_localidad relacion = new modelo_relacion_solicitante_localidad();
								if (lbxLocalidades.getItemAtIndex(i).isSelected()) {
									relacion.setSolicitante(solicitante);
									relacion.setLocalidad(
											listaLocalidades.get(lbxLocalidades.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionLocalidades.add(relacion);
								}
							}
							try {
								for (int i = 0; i < solicitante.getRelaciones_solicitante_mantenimiento().size(); i++) {
									dao.eliminarRelacionMantenimiento(
											solicitante.getRelaciones_solicitante_mantenimiento().get(i));
								}
								for (int i = 0; i < solicitante.getRelaciones_solicitante_opcion().size(); i++) {
									dao.eliminarRelacionOpcion(solicitante.getRelaciones_solicitante_opcion().get(i));
								}
								for (int i = 0; i < solicitante.getRelaciones_solicitante_localidad().size(); i++) {
									dao.eliminarRelacionLocalidad(
											solicitante.getRelaciones_solicitante_localidad().get(i));
								}
								for (int i = 0; i < listaRelacionMantenimientos.size(); i++) {
									dao.insertarRelacionMantenimiento(listaRelacionMantenimientos.get(i));
								}
								for (int i = 0; i < listaRelacionOpciones.size(); i++) {
									dao.insertarRelacionOpcion(listaRelacionOpciones.get(i));
								}
								for (int i = 0; i < listaRelacionLocalidades.size(); i++) {
									dao.insertarRelacionLocalidad(listaRelacionLocalidades.get(i));
								}
								Clients.showNotification("Los permisos se asignaron correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								Sessions.getCurrent().removeAttribute("solicitante");
								Events.postEvent(new Event("onClose", zRelacionarSolicitante));
							} catch (Exception e) {
								Clients.showNotification(
										"Error al asignar los permisos. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zRelacionarSolicitante));
	}

}
