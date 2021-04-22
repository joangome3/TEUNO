package bp.aplicaciones.controlador.bitacora;

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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
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
	Listbox lbxArea1, lbxArea2;
	@Wire
	Bandbox bdxArea;
	@Wire
	Textbox txtBuscarArea;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_movimiento movimiento = (modelo_movimiento) Sessions.getCurrent().getAttribute("movimiento");
	@SuppressWarnings("unchecked")
	List<modelo_tipo_ubicacion> listaArea3 = (List<modelo_tipo_ubicacion>) Sessions.getCurrent()
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxArea1.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxArea2.setEmptyMessage(informativos.getMensaje_informativo_2());
		inicializarListas();
		if (listaArea3.size() > 0) {
			setearAreas(listaArea3);
		}
	}

	public List<modelo_tipo_ubicacion> obtenerTipoUbicaciones() {
		return listaArea1;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaArea1 = consultasABaseDeDatos.cargarTipoUbicaciones("");
		binder.loadComponent(lbxArea1);
	}

	public void setearAreas(List<modelo_tipo_ubicacion> listaArea3) {
		for (int i = 0; i < listaArea3.size(); i++) {
			listaArea2.add(listaArea3.get(i));
			Listitem lItem;
			Listcell lCell;
			Button btnEliminar;
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaArea3.get(i).getId_tipo_ubicacion()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* NOMBRE */
			lCell = new Listcell();
			lCell.setLabel(listaArea3.get(i).getNom_tipo_ubicacion());
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setImage("/img/botones/ButtonClose.png");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(0);
					lbxArea2.removeItemAt(lItem.getIndex());
					for (int i = 0; i < listaArea2.size(); i++) {
						if (listaArea2.get(i).getId_tipo_ubicacion() == Long.valueOf(lCell.getLabel().toString())) {
							listaArea2.remove(i);
							i = listaArea2.size() + 1;
						}
					}
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxArea2.appendChild(lItem);
		}
	}

	@Listen("onSelect=#lbxArea1")
	public void onSelect$lbxArea1() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxArea1.getSelectedItem() == null) {
			return;
		}
		if (lbxArea1.getSelectedItems().size() > 1) {
			bdxArea.setText("");
			setearAreas(lbxArea1);
		} else {
			int indice = lbxArea1.getSelectedIndex();
			bdxArea.setText("");
			bdxArea.setText(listaArea1.get(indice).getNom_tipo_ubicacion());
		}
	}

	public void setearAreas(Listbox lbxAreas) {
		Listitem lItem;
		Listcell lCell;
		String rack = "";
		int i = 0;
		Iterator<Listitem> it = lbxAreas.getSelectedItems().iterator();
		while (it.hasNext()) {
			lItem = it.next();
			lCell = (Listcell) lItem.getChildren().get(1);
			if (i == 0) {
				rack = lCell.getLabel();
			} else {
				rack = rack + ", " + lCell.getLabel();
			}
			i++;
		}
		bdxArea.setText(rack);
		bdxArea.setTooltiptext(rack);
	}

	@Listen("onOK=#txtBuscarArea")
	public void onOK$txtBuscarArea() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		listaArea1 = consultasABaseDeDatos.cargarTipoUbicaciones(txtBuscarArea.getText().toUpperCase().trim());
		bdxArea.setText("");
		lbxArea1.clearSelection();
		binder.loadComponent(lbxArea1);
	}

	@Listen("onDoubleClick=#lbxArea1")
	public void onDoubleClick$lbxArea1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		// onClick$btnAnadir();
	}

	@Listen("onClick=#btnAnadir")
	public void onClick$btnAnadir()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		anadirItems();
	}

	public boolean validarItemEnLista(long id_area) {
		boolean existe = false;
		Listitem lItem;
		Listcell lCell;
		long id;
		for (int i = 0; i < lbxArea2.getItems().size(); i++) {
			lItem = lbxArea2.getItemAtIndex(i);
			lCell = (Listcell) lItem.getChildren().get(0);
			id = Long.valueOf(lCell.getLabel().toString());
			if (id_area == id) {
				existe = true;
				i = lbxArea2.getItems().size() + 1;
			}
		}
		return existe;
	}

	public void anadirItems() throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArea1.getSelectedItem() == null) {
			return;
		}
		Iterator<Listitem> it = lbxArea1.getSelectedItems().iterator();
		while (it.hasNext()) {
			Listitem lItem;
			Listcell lCell;
			lItem = it.next();
			int indice = lItem.getIndex();
			if (validarItemEnLista(listaArea1.get(indice).getId_tipo_ubicacion()) == false) {
				listaArea2.add(listaArea1.get(indice));
				Button btnEliminar;
				lItem = new Listitem();
				/* ID */
				lCell = new Listcell();
				lCell.setLabel(String.valueOf(listaArea1.get(indice).getId_tipo_ubicacion()));
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* NOMBRE */
				lCell = new Listcell();
				lCell.setLabel(listaArea1.get(indice).getNom_tipo_ubicacion());
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* ACCION */
				lCell = new Listcell();
				btnEliminar = new Button();
				btnEliminar.setImage("/img/botones/ButtonClose.png");
				btnEliminar.setTooltiptext("Eliminar");
				btnEliminar.setAutodisable("self");
				btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						Listitem lItem;
						Listcell lCell;
						lItem = (Listitem) btnEliminar.getParent().getParent();
						lCell = (Listcell) lItem.getChildren().get(0);
						lbxArea2.removeItemAt(lItem.getIndex());
						for (int i = 0; i < listaArea2.size(); i++) {
							if (listaArea2.get(i).getId_tipo_ubicacion() == Long.valueOf(lCell.getLabel().toString())) {
								listaArea2.remove(i);
								i = listaArea2.size() + 1;
							}
						}
					}
				});
				lCell.appendChild(btnEliminar);
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* ANADIR ITEM A LISTBOX */
				lbxArea2.appendChild(lItem);
			}
		}
		/* LIMPIAR CAMPOS */
		lbxArea1.clearSelection();
		bdxArea.setText("");
		txtBuscarArea.setText("");
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
								Sessions.getCurrent().setAttribute("lista_area", listaArea2);
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
