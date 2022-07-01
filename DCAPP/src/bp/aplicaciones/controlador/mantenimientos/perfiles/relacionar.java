package bp.aplicaciones.controlador.mantenimientos.perfiles;

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
	Window zRelacionarPerfil;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Listbox lbxMantenimientos, lbxOpciones;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

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
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (int i = 0; i < perfil.getRelaciones_perfil_mantenimiento().size(); i++) {
			for (int j = 0; j < listaMantenimientos.size(); j++) {
				if (perfil.getRelaciones_perfil_mantenimiento().get(i).getMantenimiento()
						.getId_mantenimiento() == listaMantenimientos.get(j).getId_mantenimiento()) {
					lbxMantenimientos.getItemAtIndex(j).setSelected(true);
					break;
				}
			}
		}
		for (int i = 0; i < perfil.getRelaciones_perfil_opcion().size(); i++) {
			for (int j = 0; j < listaOpciones.size(); j++) {
				if (perfil.getRelaciones_perfil_opcion().get(i).getOpcion().getId_opcion() == listaOpciones.get(j)
						.getId_opcion()) {
					lbxOpciones.getItemAtIndex(j).setSelected(true);
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

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		listaMantenimientos = dao.consultarMantenimientos(0, 0, "", "", 0, 2);
		binder.loadComponent(lbxMantenimientos);
	}

	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_opcion dao = new dao_opcion();
		listaOpciones = dao.consultarOpciones(0, 0, "", "", 0, 2);
		binder.loadComponent(lbxOpciones);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar los permisos?", ".:: Asignar permisos ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_relacion_perfil dao = new dao_relacion_perfil();
							List<modelo_relacion_perfil_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_perfil_mantenimiento>();
							List<modelo_relacion_perfil_opcion> listaRelacionOpciones = new ArrayList<modelo_relacion_perfil_opcion>();
							for (int i = 0; i < lbxMantenimientos.getItemCount(); i++) {
								modelo_relacion_perfil_mantenimiento relacion = new modelo_relacion_perfil_mantenimiento();
								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
									relacion.setPerfil(perfil);
									relacion.setMantenimiento(
											listaMantenimientos.get(lbxMantenimientos.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionMantenimientos.add(relacion);
								}
							}
							for (int i = 0; i < lbxOpciones.getItemCount(); i++) {
								modelo_relacion_perfil_opcion relacion = new modelo_relacion_perfil_opcion();
								if (lbxOpciones.getItemAtIndex(i).isSelected()) {
									relacion.setPerfil(perfil);
									relacion.setOpcion(listaOpciones.get(lbxOpciones.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionOpciones.add(relacion);
								}
							}
							try {
								for (int i = 0; i < perfil.getRelaciones_perfil_mantenimiento().size(); i++) {
									dao.eliminarRelacionMantenimiento(
											perfil.getRelaciones_perfil_mantenimiento().get(i));
								}
								for (int i = 0; i < perfil.getRelaciones_perfil_opcion().size(); i++) {
									dao.eliminarRelacionOpcion(perfil.getRelaciones_perfil_opcion().get(i));
								}
								for (int i = 0; i < listaRelacionMantenimientos.size(); i++) {
									dao.insertarRelacionMantenimiento(listaRelacionMantenimientos.get(i));
								}
								for (int i = 0; i < listaRelacionOpciones.size(); i++) {
									dao.insertarRelacionOpcion(listaRelacionOpciones.get(i));
								}
								Clients.showNotification("Los permisos se asignaron correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								Sessions.getCurrent().removeAttribute("perfil");
								Events.postEvent(new Event("onClose", zRelacionarPerfil));
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
		Events.postEvent(new Event("onClose", zRelacionarPerfil));
	}

}
