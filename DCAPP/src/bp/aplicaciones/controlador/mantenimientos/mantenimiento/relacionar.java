package bp.aplicaciones.controlador.mantenimientos.mantenimiento;

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
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_mantenimiento_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_mantenimiento_mantenimiento;

@SuppressWarnings({ "serial", "deprecation" })
public class relacionar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zRelacionarMantenimiento;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Listbox lbxMantenimientos;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;

	List<modelo_mantenimiento> listaMantenimientos = new ArrayList<modelo_mantenimiento>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_mantenimiento mantenimiento = (modelo_mantenimiento) Sessions.getCurrent().getAttribute("mantenimiento");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxMantenimientos.setEmptyMessage("!No existen datos que mostrar¡.");
		cargarMantenimientos();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (int i = 0; i < mantenimiento.getRelaciones_mantenimiento_mantenimiento_1().size(); i++) {
			for (int j = 0; j < listaMantenimientos.size(); j++) {
				if (mantenimiento.getRelaciones_mantenimiento_mantenimiento_1().get(i).getMantenimiento2()
						.getId_mantenimiento() == listaMantenimientos.get(j).getId_mantenimiento()) {
					lbxMantenimientos.getItemAtIndex(j).setSelected(true);
					break;
				}
			}
		}
	}

	public List<modelo_mantenimiento> obtenerMantenimientos() {
		return listaMantenimientos;
	}

	public void cargarMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		listaMantenimientos = dao.consultarMantenimientos(0, 0, "", "", 0, 2);
		binder.loadComponent(lbxMantenimientos);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar los permisos?", ".:: Asignar permisos ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_relacion_mantenimiento_opcion dao = new dao_relacion_mantenimiento_opcion();
							List<modelo_relacion_mantenimiento_mantenimiento> listaRelacionMantenimientos = new ArrayList<modelo_relacion_mantenimiento_mantenimiento>();
							for (int i = 0; i < lbxMantenimientos.getItemCount(); i++) {
								modelo_relacion_mantenimiento_mantenimiento relacion = new modelo_relacion_mantenimiento_mantenimiento();
								if (lbxMantenimientos.getItemAtIndex(i).isSelected()) {
									relacion.setMantenimiento1(mantenimiento);
									relacion.setMantenimiento2(
											listaMantenimientos.get(lbxMantenimientos.getItemAtIndex(i).getIndex()));
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacionMantenimientos.add(relacion);
								}
							}
							try {
								for (int i = 0; i < mantenimiento.getRelaciones_mantenimiento_mantenimiento_1()
										.size(); i++) {
									dao.eliminarRelacionMantenimiento(
											mantenimiento.getRelaciones_mantenimiento_mantenimiento_1().get(i));
								}
								for (int i = 0; i < listaRelacionMantenimientos.size(); i++) {
									dao.insertarRelacionMantenimiento(listaRelacionMantenimientos.get(i));
								}
								Clients.showNotification("Los permisos se asignaron correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								Sessions.getCurrent().removeAttribute("mantenimiento");
								Events.postEvent(new Event("onClose", zRelacionarMantenimiento));
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
		Events.postEvent(new Event("onClose", zRelacionarMantenimiento));
	}

}
