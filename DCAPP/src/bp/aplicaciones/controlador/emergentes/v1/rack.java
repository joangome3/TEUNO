package bp.aplicaciones.controlador.emergentes.v1;

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
import org.zkoss.zul.Combobox;
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
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;

@SuppressWarnings({ "serial", "deprecation" })
public class rack extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zRack;
	@Wire
	Button btnGrabar, btnCancelar, btnAnadir;
	@Wire
	Listbox lbxRack1, lbxRack2;
	@Wire
	Bandbox bdxRack;
	@Wire
	Textbox txtBuscarRack;
	@Wire
	Combobox cmbFilas;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_movimiento movimiento = (modelo_movimiento) Sessions.getCurrent().getAttribute("movimiento");
	@SuppressWarnings("unchecked")
	List<modelo_rack> listaRack3 = (List<modelo_rack>) Sessions.getCurrent().getAttribute("lista_rack");
	long id_cliente = (long) Sessions.getCurrent().getAttribute("cliente");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_rack> listaRack1 = new ArrayList<modelo_rack>();
	List<modelo_rack> listaRack2 = new ArrayList<modelo_rack>();

	List<modelo_fila> listaFila = new ArrayList<modelo_fila>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxRack1.setEmptyMessage(informativos.getMensaje_informativo_2());
		inicializarListas();
		if (listaRack3.size() > 0) {
			setearRacks(listaRack3);
		}
	}

	public List<modelo_rack> obtenerRacks() {
		return listaRack1;
	}

	public List<modelo_fila> obtenerFilas() {
		return listaFila;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaFila = consultasABaseDeDatos.cargarFilas("", id_dc, id_cliente, 1);
		listaRack1 = consultasABaseDeDatos.cargarRacks("", id_dc, id_cliente, 0, 1);
		binder.loadComponent(lbxRack1);
		binder.loadComponent(cmbFilas);
	}

	public void setearRacks(List<modelo_rack> listaRack3) {
		for (int i = 0; i < listaRack3.size(); i++) {
			listaRack2.add(listaRack3.get(i));
			Listitem lItem;
			Listcell lCell;
			Button btnEliminar;
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaRack3.get(i).getId_rack()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* CLIENTE */
			lCell = new Listcell();
			lCell.setLabel(listaRack3.get(i).getNom_cliente());
			lItem.appendChild(lCell);
			/* FILA */
			lCell = new Listcell();
			lCell.setLabel(listaRack3.get(i).getNom_fila());
			lItem.appendChild(lCell);
			/* COORDENADA */
			lCell = new Listcell();
			lCell.setLabel(listaRack3.get(i).getCoord_rack());
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
					lbxRack2.removeItemAt(lItem.getIndex());
					for (int i = 0; i < listaRack2.size(); i++) {
						if (listaRack2.get(i).getId_rack() == Long.valueOf(lCell.getLabel().toString())) {
							listaRack2.remove(i);
							i = listaRack2.size() + 1;
						}
					}
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxRack2.appendChild(lItem);
		}
	}

	@Listen("onSelect=#lbxRack1")
	public void onSelect$lbxRack1() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxRack1.getSelectedItem() == null) {
			return;
		}
		if (lbxRack1.getSelectedItems().size() > 1) {
			bdxRack.setText("");
			setearRacks(lbxRack1);
		} else {
			int indice = lbxRack1.getSelectedIndex();
			bdxRack.setText("");
			bdxRack.setText(listaRack1.get(indice).getCoord_rack());
		}
	}

	public void setearRacks(Listbox lbxRacks) {
		Listitem lItem;
		Listcell lCell;
		String rack = "";
		int i = 0;
		Iterator<Listitem> it = lbxRacks.getSelectedItems().iterator();
		while (it.hasNext()) {
			lItem = it.next();
			lCell = (Listcell) lItem.getChildren().get(3);
			if (i == 0) {
				rack = lCell.getLabel();
			} else {
				rack = rack + ", " + lCell.getLabel();
			}
			i++;
		}
		bdxRack.setText(rack);
		bdxRack.setTooltiptext(rack);
	}

	@Listen("onOK=#txtBuscarRack")
	public void onOK$txtBuscarRack() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		long id_fila = 0;
		if (cmbFilas.getSelectedItem() != null) {
			id_fila = Long.valueOf(cmbFilas.getSelectedItem().getValue().toString());
		}
		listaRack1 = consultasABaseDeDatos.cargarRacks(txtBuscarRack.getText().toUpperCase().trim(), id_dc, id_cliente,
				id_fila, 1);
		bdxRack.setText("");
		lbxRack1.clearSelection();
		binder.loadComponent(lbxRack1);
	}

	@Listen("onSelect=#cmbFilas")
	public void onSelect$cmbFilas() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		long id_fila = 0;
		if (cmbFilas.getSelectedItem() != null) {
			id_fila = Long.valueOf(cmbFilas.getSelectedItem().getValue().toString());
		}
		listaRack1 = consultasABaseDeDatos.cargarRacks(txtBuscarRack.getText().toUpperCase().trim(), id_dc, id_cliente,
				id_fila, 1);
		bdxRack.setText("");
		lbxRack1.clearSelection();
		binder.loadComponent(lbxRack1);
	}

	@Listen("onDoubleClick=#lbxRack1")
	public void onDoubleClick$lbxRack1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		anadirItems();
	}

	@Listen("onClick=#btnAnadir")
	public void onClick$btnAnadir()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		anadirItems();
	}

	public boolean validarItemEnLista(long id_rack) {
		boolean existe = false;
		Listitem lItem;
		Listcell lCell;
		long id;
		for (int i = 0; i < lbxRack2.getItems().size(); i++) {
			lItem = lbxRack2.getItemAtIndex(i);
			lCell = (Listcell) lItem.getChildren().get(0);
			id = Long.valueOf(lCell.getLabel().toString());
			if (id_rack == id) {
				existe = true;
				i = lbxRack2.getItems().size() + 1;
			}
		}
		return existe;
	}

	public void anadirItems() throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxRack1.getSelectedItem() == null) {
			return;
		}
		Iterator<Listitem> it = lbxRack1.getSelectedItems().iterator();
		while (it.hasNext()) {
			Listitem lItem;
			Listcell lCell;
			lItem = it.next();
			int indice = lItem.getIndex();
			if (validarItemEnLista(listaRack1.get(indice).getId_rack()) == false) {
				listaRack2.add(listaRack1.get(indice));
				Button btnEliminar;
				lItem = new Listitem();
				/* ID */
				lCell = new Listcell();
				lCell.setLabel(String.valueOf(listaRack1.get(indice).getId_rack()));
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* CLIENTE */
				lCell = new Listcell();
				lCell.setLabel(listaRack1.get(indice).getNom_cliente());
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* FILA */
				lCell = new Listcell();
				lCell.setLabel(listaRack1.get(indice).getNom_fila());
				lItem.appendChild(lCell);
				/* COORDENADA */
				lCell = new Listcell();
				lCell.setLabel(listaRack1.get(indice).getCoord_rack());
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
						lbxRack2.removeItemAt(lItem.getIndex());
						for (int i = 0; i < listaRack2.size(); i++) {
							if (listaRack2.get(i).getId_rack() == Long.valueOf(lCell.getLabel().toString())) {
								listaRack2.remove(i);
								i = listaRack2.size() + 1;
							}
						}
					}
				});
				lCell.appendChild(btnEliminar);
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* ANADIR ITEM A LISTBOX */
				lbxRack2.appendChild(lItem);
			}
		}
		/* LIMPIAR CAMPOS */
		lbxRack1.clearSelection();
		bdxRack.setText("");
		txtBuscarRack.setText("");
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
								Sessions.getCurrent().setAttribute("lista_rack", listaRack2);
								Events.postEvent(new Event("onClose", zRack));
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
		Events.postEvent(new Event("onClose", zRack));
	}

}
