package bp.aplicaciones.controlador.emergentes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_trabajo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;

@SuppressWarnings({ "serial", "deprecation" })
public class area extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zArea;
	@Wire
	Button btnGrabar, btnCancelar, btnAnadir;
	@Wire
	Listbox lbxArea1;
	@Wire
	Textbox txtBuscarArea;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_movimiento movimiento = (modelo_movimiento) Sessions.getCurrent().getAttribute("movimiento");
	@SuppressWarnings("unchecked")
	List<modelo_tipo_ubicacion> listaArea4 = (List<modelo_tipo_ubicacion>) Sessions.getCurrent()
			.getAttribute("lista_area");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_tipo_ubicacion> listaArea1 = new ArrayList<modelo_tipo_ubicacion>();
	List<modelo_tipo_ubicacion> listaArea2 = new ArrayList<modelo_tipo_ubicacion>();
	List<modelo_tipo_ubicacion> listaArea3 = new ArrayList<modelo_tipo_ubicacion>();
	List<modelo_tipo_trabajo> listaTipoTrabajo = new ArrayList<modelo_tipo_trabajo>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxArea1.setEmptyMessage(informativos.getMensaje_informativo_2());
		inicializarListas();
		if (listaArea4.size() > 0) {
			setearAreas(listaArea4);
		}
	}

	public List<modelo_tipo_ubicacion> obtenerTipoUbicaciones() {
		return listaArea2;
	}

	public List<modelo_tipo_trabajo> obtenerTipoTrabajo() {
		return listaTipoTrabajo;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaArea1 = consultasABaseDeDatos.cargarTipoUbicaciones("", 0, 1);
		listaArea2 = consultasABaseDeDatos.cargarTipoUbicaciones("", 0, 1);
		listaTipoTrabajo = consultasABaseDeDatos.cargarTipoTrabajos("");
		binder.loadComponent(lbxArea1);
	}

	public void setearAreas(List<modelo_tipo_ubicacion> listaArea4)
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		listaArea2 = consultasABaseDeDatos.cargarTipoUbicaciones(txtBuscarArea.getText().toUpperCase().trim(), 0, 1);
		binder.loadComponent(lbxArea1);
		Iterator<Listitem> it = lbxArea1.getItems().iterator();
		while (it.hasNext()) {
			Listitem item = it.next();
			int indice = item.getIndex();
			for (int i = 0; i < listaArea4.size(); i++) {
				if (listaArea4.get(i).getId_tipo_ubicacion() == listaArea2.get(indice).getId_tipo_ubicacion()) {
					lbxArea1.addItemToSelection(item);
					listaArea3.add(listaArea2.get(indice));
				}
			}
		}
	}

	@Listen("onSelect=#lbxArea1")
	public void onSelect$lbxArea1() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxArea1.getSelectedItem() == null) {
			return;
		}
		List<modelo_tipo_ubicacion> listaArea = new ArrayList<modelo_tipo_ubicacion>();
		Iterator<Listitem> it = lbxArea1.getSelectedItems().iterator();
		listaArea3 = new ArrayList<modelo_tipo_ubicacion>();
		// Se anaden a la lista3 los items seleccionados
		while (it.hasNext()) {
			Listitem item = it.next();
			int indice = item.getIndex();
			if (item.isSelected()) {
				listaArea.add(listaArea2.get(indice));
				listaArea3.add(listaArea2.get(indice));
			}
		}
	}

	@Listen("onOK=#txtBuscarArea")
	public void onOK$txtBuscarArea() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		listaArea2 = consultasABaseDeDatos.cargarTipoUbicaciones(txtBuscarArea.getText().toUpperCase().trim(), 0, 1);
		if (txtBuscarArea.getText().length() <= 0) {
			binder.loadComponent(lbxArea1);
			Iterator<Listitem> it = lbxArea1.getItems().iterator();
			while (it.hasNext()) {
				Listitem item = it.next();
				int indice = item.getIndex();
				for (int i = 0; i < listaArea3.size(); i++) {
					if (listaArea3.get(i).getId_tipo_ubicacion() == listaArea2.get(indice).getId_tipo_ubicacion()) {
						lbxArea1.addItemToSelection(item);
					}
				}
			}
		} else {
			for (int i = 0; i < listaArea2.size(); i++) {
				for (int j = 0; j < listaArea3.size(); j++) {
					if (listaArea2.get(i).getId_tipo_ubicacion() == listaArea3.get(j).getId_tipo_ubicacion()) {
						listaArea2.remove(i);
					}
				}
			}
			for (int i = 0; i < listaArea3.size(); i++) {
				listaArea2.add(listaArea3.get(i));
			}
			binder.loadComponent(lbxArea1);
			Iterator<Listitem> it = lbxArea1.getItems().iterator();
			while (it.hasNext()) {
				Listitem item = it.next();
				int indice = item.getIndex();
				for (int i = 0; i < listaArea3.size(); i++) {
					if (listaArea3.get(i).getId_tipo_ubicacion() == listaArea2.get(indice).getId_tipo_ubicacion()) {
						lbxArea1.addItemToSelection(item);
					}
				}
			}
		}
	}

	@Listen("onDoubleClick=#lbxArea1")
	public void onDoubleClick$lbxArea1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		// onClick$btnAnadir();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_1(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							try {
								Iterator<Listitem> it = lbxArea1.getSelectedItems().iterator();
								listaArea3 = new ArrayList<modelo_tipo_ubicacion>();
								// Se anaden a la lista3 los items seleccionados
								while (it.hasNext()) {
									Listitem item = it.next();
									int indice = item.getIndex();
									if (item.isSelected()) {
										listaArea3.add(listaArea2.get(indice));
									}
								}
								Sessions.getCurrent().setAttribute("lista_area", listaArea3);
								Events.postEvent(new Event("onClose", zArea));
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4(),
										informativos.getMensaje_informativo_1() + e.getMessage(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zArea));
	}

}
