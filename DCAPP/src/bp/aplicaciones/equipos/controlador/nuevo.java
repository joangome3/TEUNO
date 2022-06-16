package bp.aplicaciones.equipos.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mantenimientos.modelo.modelo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_marca;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_equipo_tipo_conector;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_rack_ur_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_conector;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevoMovimiento;
	@Wire
	Button btnGrabar, btnCancelar, btnAnadir, btnNuevoRack, btnNuevoEquipo;
	@Wire
	Textbox txtTicket, txtEmpresa, txtFila, txtNombreEquipo, txtTipoEquipo, txtMarca, txtModelo, txtSerie,
			txtCodigoActivo, txtCodigoProducto;
	@Wire
	Decimalbox decPeso, decProfundidad, decBTU;
	@Wire
	Listbox lbxFuentes, lbxUrs;
	@Wire
	Tab tab1, tab2, tab3, tab4, tab5, tab6;
	@Wire
	Combobox cmbTipoMovimiento, cmbRack, cmbEquipo, cmbEstado;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	@Wire
	Div winList;

	Window window;

	long id = 0;
	long id_opcion = 26;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Informativos informativos = new Informativos();

	List<modelo_rack> listaRacks = new ArrayList<modelo_rack>();
	List<modelo_equipo> listaEquipos = new ArrayList<modelo_equipo>();
	List<modelo_estado_equipo> listaEstadosEquipo = new ArrayList<modelo_estado_equipo>();
	List<modelo_tipo_clasificacion> listaTipoMovimientos = new ArrayList<modelo_tipo_clasificacion>();

	List<modelo_tipo_conector> listaTipoConector = new ArrayList<modelo_tipo_conector>();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxFuentes.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxUrs.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtTicket.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicket.setText(txtTicket.getText().toUpperCase().trim());
			}
		});
		cargarTipoMovimientos();
		cargarRacks();
		cargarEquipos();
		cargarEstadosEquipo();
	}

	public List<modelo_rack> obtenerTiposEquipo() {
		return listaRacks;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarTipoMovimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoMovimientos = consultasABaseDeDatos.cargarClasificaciones("", 3, 3, 0);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_tipo_clasificacion bean = (modelo_tipo_clasificacion) o2;
				return bean.getNom_tipo_clasificacion().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaTipoMovimientos), myComparator, 15);
		cmbTipoMovimiento.setModel(mySubModel);
		ComboitemRenderer<modelo_tipo_clasificacion> myRenderer = new ComboitemRenderer<modelo_tipo_clasificacion>() {
			@Override
			public void render(Comboitem item, modelo_tipo_clasificacion bean, int index) throws Exception {
				item.setLabel(bean.getNom_tipo_clasificacion());
				item.setValue(bean);
			}
		};
		cmbTipoMovimiento.setItemRenderer(myRenderer);
		binder.loadComponent(cmbRack);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarRacks() {
		listaRacks = consultasABaseDeDatos.consultarRacks(id_dc, 0, "", "", 0, 3);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_rack bean = (modelo_rack) o2;
				return bean.getCoord_rack().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaRacks), myComparator, 15);
		cmbRack.setModel(mySubModel);
		ComboitemRenderer<modelo_rack> myRenderer = new ComboitemRenderer<modelo_rack>() {
			@Override
			public void render(Comboitem item, modelo_rack bean, int index) throws Exception {
				item.setLabel(bean.getCoord_rack());
				item.setValue(bean);
			}
		};
		cmbRack.setItemRenderer(myRenderer);
		binder.loadComponent(cmbRack);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEquipos() {
		listaEquipos = consultasABaseDeDatos.consultarEquipos(0, 0, "", "", 0, 3);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_equipo bean = (modelo_equipo) o2;
				String nom_equipo = !bean.getNom_equipo().equals("N/A") ? bean.getNom_equipo() : "NO NOMBRE EQUIPO";
				String serie = !bean.getSerie().equals("N/A") ? bean.getSerie() : "NO SERIE";
				String cod_activo = !bean.getCod_activo().equals("N/A") ? bean.getCod_activo() : "NO CODIGO ACTIVO";
				return (bean.getId_equipo() + " - " + bean.getTipo_equipo().getNom_tipo_equipo() + " - " + nom_equipo
						+ " - " + serie + " - " + cod_activo).contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEquipos), myComparator, 15);
		cmbEquipo.setModel(mySubModel);
		ComboitemRenderer<modelo_equipo> myRenderer = new ComboitemRenderer<modelo_equipo>() {
			@Override
			public void render(Comboitem item, modelo_equipo bean, int index) throws Exception {
				String nom_equipo = !bean.getNom_equipo().equals("N/A") ? bean.getNom_equipo() : "NO NOMBRE EQUIPO";
				String serie = !bean.getSerie().equals("N/A") ? bean.getSerie() : "NO SERIE";
				String cod_activo = !bean.getCod_activo().equals("N/A") ? bean.getCod_activo() : "NO CODIGO ACTIVO";
				item.setLabel(bean.getId_equipo() + " - " + bean.getTipo_equipo().getNom_tipo_equipo() + " - "
						+ nom_equipo + " - " + serie + " - " + cod_activo);
				item.setValue(bean);
			}
		};
		cmbEquipo.setItemRenderer(myRenderer);
		binder.loadComponent(cmbEquipo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEstadosEquipo() {
		listaEstadosEquipo = consultasABaseDeDatos.consultarEstadosEquipo(0, 0, "", "", 0, 5);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_estado_equipo bean = (modelo_estado_equipo) o2;
				return bean.getNom_estado().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEstadosEquipo), myComparator, 15);
		cmbEstado.setModel(mySubModel);
		ComboitemRenderer<modelo_estado_equipo> myRenderer = new ComboitemRenderer<modelo_estado_equipo>() {
			@Override
			public void render(Comboitem item, modelo_estado_equipo bean, int index) throws Exception {
				item.setLabel(bean.getNom_estado());
				item.setValue(bean);
			}
		};
		cmbEstado.setItemRenderer(myRenderer);
		binder.loadComponent(cmbEstado);
	}

	@Listen("onSelect=#cmbRack")
	public void onSelect$cmbRack()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRack.getSelectedItem() == null) {
			return;
		}
		txtEmpresa.setText(((modelo_rack) cmbRack.getSelectedItem().getValue()).getEmpresa().getNom_empresa());
		txtFila.setText(((modelo_rack) cmbRack.getSelectedItem().getValue()).getFila().getNom_fila());
		borrarListaUrs();
		cargarUrs();
	}

	@Listen("onSelect=#cmbEquipo")
	public void onSelect$cmbEquipo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEquipo.getSelectedItem() == null) {
			return;
		}
		modelo_equipo equipo = (modelo_equipo) cmbEquipo.getSelectedItem().getValue();
		String resultado = !equipo.getNom_equipo().equals("N/A") == true ? equipo.getNom_equipo() : "NO REGISTRA";
		txtNombreEquipo.setText(resultado);
		txtTipoEquipo.setText(equipo.getTipo_equipo().getNom_tipo_equipo());
		txtMarca.setText(equipo.getMarca().getNom_marca());
		txtModelo.setText(equipo.getModelo().getNom_modelo());
		resultado = !equipo.getSerie().equals("N/A") == true ? equipo.getSerie() : "NO REGISTRA";
		txtSerie.setText(resultado);
		resultado = !equipo.getCod_activo().equals("N/A") == true ? equipo.getCod_activo() : "NO REGISTRA";
		txtCodigoActivo.setText(resultado);
		resultado = !equipo.getCod_producto().equals("N/A") == true ? equipo.getCod_producto() : "NO REGISTRA";
		txtCodigoProducto.setText(resultado);
		decPeso.setValue(BigDecimal.valueOf(equipo.getPeso()));
		decProfundidad.setValue(BigDecimal.valueOf(equipo.getProfundidad()));
		decBTU.setValue(BigDecimal.valueOf(equipo.getBtu()));
		borrarListaFuentes();
		cargarFuentes();
	}

	public void cargarFuentes() {
		modelo_equipo equipo = (modelo_equipo) cmbEquipo.getSelectedItem().getValue();
		listaTipoConector = consultasABaseDeDatos.consultarTipoConectores(0, 0, "", "", 0, 3);
		for (int i = 0; i < equipo.getRelacion_equipo_tipo_conector().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Combobox cmbTipoConector;
			Textbox txtVoltaje;
			Decimalbox decPotencia;
			Comboitem cItem;
			Button btnEliminar;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setIconSclass("z-icon-minus-circle");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxFuentes.removeItemAt(lItem.getIndex());
					// num_fuente = num_fuente - 1;
				}
			});
			btnEliminar.setDisabled(true);
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* TIPO CONECTOR */
			lCell = new Listcell();
			cmbTipoConector = new Combobox();
			for (int j = 0; j < listaTipoConector.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoConector.get(j).getNom_tipo_conector());
				cItem.setValue(listaTipoConector.get(j).getId_tipo_conector());
				cmbTipoConector.appendChild(cItem);
			}
			cmbTipoConector.setText(
					equipo.getRelacion_equipo_tipo_conector().get(i).getTipo_conector().getNom_tipo_conector());
			cmbTipoConector.setReadonly(true);
			cmbTipoConector.setWidth("180px");
			cmbTipoConector.setDisabled(true);
			lCell.appendChild(cmbTipoConector);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* VOLTAJE */
			lCell = new Listcell();
			txtVoltaje = new Textbox();
			txtVoltaje.setReadonly(true);
			txtVoltaje.setWidth("110px");
			txtVoltaje.setClass("z-textbox-2");
			txtVoltaje.setText(String.valueOf(
					equipo.getRelacion_equipo_tipo_conector().get(i).getTipo_conector().getVol_tipo_conector()));
			lCell.appendChild(txtVoltaje);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* POTENCIA */
			lCell = new Listcell();
			decPotencia = new Decimalbox();
			decPotencia.setDisabled(false);
			decPotencia.setWidth("110px");
			decPotencia.setFormat("#,##0.##");
			decPotencia.setValue(BigDecimal.valueOf(equipo.getRelacion_equipo_tipo_conector().get(i).getPotencia()));
			decPotencia.setTooltiptext(
					"El formato es de la siguiente manera #,##0.## la coma es el separador de decimales y el punto de miles.");
			decPotencia.setClass("z-textbox-2");
			decPotencia.setReadonly(true);
			lCell.appendChild(decPotencia);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxFuentes.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxFuentes.clearSelection();
			// num_fuente = num_fuente + 1;
		}
	}

	public void cargarUrs() {
		modelo_rack rack = (modelo_rack) cmbRack.getSelectedItem().getValue();
		for (int i = 0; i < rack.getRelacion_gestion_racks_urs_equipos().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Checkbox chkSeleccionar;
			Textbox txtUr, txtEstado;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			chkSeleccionar = new Checkbox();
			chkSeleccionar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					Textbox txtEstado;
					lItem = (Listitem) chkSeleccionar.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(2);
					txtEstado = (Textbox) lCell.getChildren().get(0);
					if (chkSeleccionar.isChecked()) {
						txtEstado.setText("OCUPADO");
					} else {
						txtEstado.setText("LIBRE");
					}
				}
			});
			if (rack.getRelacion_gestion_racks_urs_equipos().get(i).isOcupado()) {
				chkSeleccionar.setChecked(true);
				chkSeleccionar.setDisabled(true);
			} else {
				chkSeleccionar.setChecked(false);
				chkSeleccionar.setDisabled(false);
			}
			lCell.appendChild(chkSeleccionar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* UR */
			lCell = new Listcell();
			txtUr = new Textbox();
			txtUr.setText(String.valueOf(i + 1));
			txtUr.setClass("z-textbox-2");
			txtUr.setReadonly(true);
			lCell.appendChild(txtUr);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ESTADO */
			lCell = new Listcell();
			txtEstado = new Textbox();
			if (rack.getRelacion_gestion_racks_urs_equipos().get(i).isOcupado()) {
				txtEstado.setText("OCUPADO");
			} else {
				txtEstado.setText("LIBRE");
			}
			txtEstado.setClass("z-textbox-2");
			txtEstado.setReadonly(true);
			lCell.appendChild(txtEstado);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxUrs.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxUrs.clearSelection();
			// num_fuente = num_fuente + 1;
		}
	}

	@Listen("onClick=#btnNuevoRack")
	public void onClick$btnNuevoRack() {
		window = (Window) Executions.createComponents("/mantenimientos/rack/nuevo.zul", null, null);
		btnNuevoRack.setDisabled(true);
		btnNuevoEquipo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevoRack.setDisabled(false);
					btnNuevoEquipo.setDisabled(false);
					cargarRacks();
				}
			});
		}
		window.setParent(winList);
	}

	@Listen("onClick=#btnNuevoEquipo")
	public void onClick$btnNuevoEquipo() {
		window = (Window) Executions.createComponents("/mantenimientos/equipo/nuevo.zul", null, null);
		btnNuevoRack.setDisabled(true);
		btnNuevoEquipo.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevoRack.setDisabled(false);
					btnNuevoEquipo.setDisabled(false);
					cargarEquipos();
				}
			});
		}
		window.setParent(winList);
	}

//	@Listen("onSelect=#cmbRegistraFuente")
//	public void onSelect$cmbRegistraFuente()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
//		if (cmbRegistraFuente.getSelectedItem() == null) {
//			btnAnadir.setDisabled(true);
//			borrarListaFuentes();
//			return;
//		}
//		if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
//			btnAnadir.setDisabled(false);
//			borrarListaFuentes();
//		} else {
//			btnAnadir.setDisabled(true);
//			borrarListaFuentes();
//		}
//	}
//
//	@Listen("onClick=#btnAnadir")
//	public void onClick$btnAnadir()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
//		if (lbxFuentes.getItemCount() <= num_fuente) {
//			anadirItemALista();
//		} else {
//			tab2.setSelected(true);
//			Clients.showNotification(
//					"No se puede añadir mas de " + String.valueOf(num_fuente + 1) + " fuentes a la lista.",
//					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
//			return;
//		}
//	}
//
//	public void anadirItemALista()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
//		listaTipoConector = consultasABaseDeDatos.consultarTipoConectores(0, 0, "", "", 0, 3);
//		Listitem lItem;
//		Listcell lCell;
//		Combobox cmbTipoConector;
//		Textbox txtVoltaje;
//		Decimalbox decPotencia;
//		Comboitem cItem;
//		Button btnEliminar;
//		lItem = new Listitem();
//		/* ACCION */
//		lCell = new Listcell();
//		btnEliminar = new Button();
//		btnEliminar.setIconSclass("z-icon-minus-circle");
//		btnEliminar.setTooltiptext("Eliminar");
//		btnEliminar.setAutodisable("self");
//		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				Listitem lItem;
//				lItem = (Listitem) btnEliminar.getParent().getParent();
//				lbxFuentes.removeItemAt(lItem.getIndex());
//				// num_fuente = num_fuente - 1;
//			}
//		});
//		lCell.appendChild(btnEliminar);
//		lCell.setStyle("text-align: center !important;");
//		lItem.appendChild(lCell);
//		/* TIPO CONECTOR */
//		lCell = new Listcell();
//		cmbTipoConector = new Combobox();
//		for (int i = 0; i < listaTipoConector.size(); i++) {
//			cItem = new Comboitem();
//			cItem.setLabel(listaTipoConector.get(i).getNom_tipo_conector());
//			cItem.setValue(listaTipoConector.get(i).getId_tipo_conector());
//			cmbTipoConector.appendChild(cItem);
//		}
//		cmbTipoConector.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				Listitem lItem;
//				Listcell lCell;
//				Textbox txtVoltaje;
//				lItem = (Listitem) cmbTipoConector.getParent().getParent();
//				lCell = (Listcell) lItem.getChildren().get(2);
//				txtVoltaje = (Textbox) lCell.getChildren().get(0);
//				txtVoltaje.setText(String.valueOf(listaTipoConector
//						.get(Integer.valueOf(cmbTipoConector.getSelectedItem().getIndex())).getVol_tipo_conector()));
//			}
//		});
//		cmbTipoConector.setReadonly(true);
//		cmbTipoConector.setWidth("180px");
//		lCell.appendChild(cmbTipoConector);
//		lCell.setStyle("text-align: center !important;");
//		lItem.appendChild(lCell);
//		/* VOLTAJE */
//		lCell = new Listcell();
//		txtVoltaje = new Textbox();
//		txtVoltaje.setDisabled(true);
//		txtVoltaje.setWidth("110px");
//		txtVoltaje.setClass("z-textbox-2");
//		lCell.appendChild(txtVoltaje);
//		lCell.setStyle("text-align: center !important;");
//		lItem.appendChild(lCell);
//		/* POTENCIA */
//		lCell = new Listcell();
//		decPotencia = new Decimalbox();
//		decPotencia.setDisabled(false);
//		decPotencia.setWidth("110px");
//		decPotencia.setFormat("#,##0.##");
//		decPotencia.setTooltiptext(
//				"El formato es de la siguiente manera #,##0.## la coma es el separador de decimales y el punto de miles.");
//		decPotencia.setClass("z-textbox-2");
//		lCell.appendChild(decPotencia);
//		lCell.setStyle("text-align: center !important;");
//		lItem.appendChild(lCell);
//		/* ANADIR ITEM A LISTBOX */
//		lbxFuentes.appendChild(lItem);
//		/* LIMPIAR CAMPOS */
//		lbxFuentes.clearSelection();
//		// num_fuente = num_fuente + 1;
//	}
//
//	public boolean validarFuentesRegistradas() {
//		boolean exito = true;
//		Listcell lCell;
//		Combobox cmBox;
//		Decimalbox decP;
//		for (int i = 0; i < lbxFuentes.getItems().size(); i++) {
//			lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(1);
//			cmBox = (Combobox) lCell.getChildren().get(0);
//			if (cmBox.getSelectedItem() == null) {
//				tab2.setSelected(true);
//				cmBox.setFocus(true);
//				Clients.showNotification("Seleccione un tipo de conector.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				exito = false;
//				break;
//			}
//			lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(3);
//			decP = (Decimalbox) lCell.getChildren().get(0);
//			float rg1 = 0;
//			if (decP.getValue() == null) {
//				tab2.setSelected(true);
//				decP.setFocus(true);
//				Clients.showNotification("Ingrese la potencia de la fuente.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				exito = false;
//				break;
//			}
//			if (decP.getValue().floatValue() < rg1) {
//				tab2.setSelected(true);
//				decP.setFocus(true);
//				Clients.showNotification("Ingrese un valor correcto para la potencia de la fuente.",
//						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
//				exito = false;
//				break;
//			}
//		}
//		return exito;
//	}
//
//	@Listen("onClick=#chkNombreEquipo")
//	public void onClick$chkNombreEquipo() {
//		if (chkNombreEquipo.isChecked()) {
//			txtNombreEquipo.setDisabled(true);
//			txtNombreEquipo.setText("");
//		} else {
//			txtNombreEquipo.setDisabled(false);
//			txtNombreEquipo.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkSerie")
//	public void onClick$chkSerie() {
//		if (chkSerie.isChecked()) {
//			txtSerie.setDisabled(true);
//			txtSerie.setText("");
//		} else {
//			txtSerie.setDisabled(false);
//			txtSerie.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkCodigoActivo")
//	public void onClick$chkCodigoActivo() {
//		if (chkCodigoActivo.isChecked()) {
//			txtCodigoActivo.setDisabled(true);
//			txtCodigoActivo.setText("");
//		} else {
//			txtCodigoActivo.setDisabled(false);
//			txtCodigoActivo.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkCodigoProducto")
//	public void onClick$chkCodigoProducto() {
//		if (chkCodigoProducto.isChecked()) {
//			txtCodigoProducto.setDisabled(true);
//			txtCodigoProducto.setText("");
//		} else {
//			txtCodigoProducto.setDisabled(false);
//			txtCodigoProducto.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkPeso")
//	public void onClick$chkPeso() {
//		if (chkPeso.isChecked()) {
//			decPeso.setDisabled(true);
//			decPeso.setText("");
//		} else {
//			decPeso.setDisabled(false);
//			decPeso.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkProfundidad")
//	public void onClick$chkProfundidad() {
//		if (chkProfundidad.isChecked()) {
//			decProfundidad.setDisabled(true);
//			decProfundidad.setText("");
//		} else {
//			decProfundidad.setDisabled(false);
//			decProfundidad.setText("");
//		}
//	}
//
//	@Listen("onClick=#chkBTU")
//	public void onClick$chkBTU() {
//		if (chkBTU.isChecked()) {
//			decBTU.setDisabled(true);
//			decBTU.setText("");
//		} else {
//			decBTU.setDisabled(false);
//			decBTU.setText("");
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (cmbTipoEquipo.getSelectedItem() == null) {
//			tab1.setSelected(true);
//			cmbTipoEquipo.setFocus(true);
//			Clients.showNotification("Seleccione un tipo de equipo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//					"top_right", 2000, true);
//			return;
//		}
//		if (!chkNombreEquipo.isChecked()) {
//			if (txtNombreEquipo.getText().length() <= 0) {
//				tab1.setSelected(true);
//				txtNombreEquipo.setFocus(true);
//				Clients.showNotification("Ingrese el nombre de equipo.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		if (cmbMarca.getSelectedItem() == null) {
//			tab1.setSelected(true);
//			cmbMarca.setFocus(true);
//			Clients.showNotification("Seleccione una marca.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//					"top_right", 2000, true);
//			return;
//		}
//		if (cmbModelo.getSelectedItem() == null) {
//			tab1.setSelected(true);
//			cmbModelo.setFocus(true);
//			Clients.showNotification("Seleccione un modelo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//					"top_right", 2000, true);
//			return;
//		}
//		if (!chkSerie.isChecked()) {
//			if (txtSerie.getText().length() <= 0) {
//				tab1.setSelected(true);
//				txtSerie.setFocus(true);
//				Clients.showNotification("Ingrese la serie.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//						"top_right", 2000, true);
//				return;
//			}
//		}
//		if (!chkCodigoActivo.isChecked()) {
//			if (txtCodigoActivo.getText().length() <= 0) {
//				tab1.setSelected(true);
//				txtCodigoActivo.setFocus(true);
//				Clients.showNotification("Ingrese el código de activo.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		if (!chkCodigoProducto.isChecked()) {
//			if (txtCodigoProducto.getText().length() <= 0) {
//				tab1.setSelected(true);
//				txtCodigoProducto.setFocus(true);
//				Clients.showNotification("Ingrese el número de producto.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		float rg1 = 0;
//		float rg2 = 2;
//		if (!chkPeso.isChecked()) {
//			if (decPeso.getValue() == null) {
//				tab1.setSelected(true);
//				decPeso.setFocus(true);
//				Clients.showNotification("Ingrese el peso.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//						"top_right", 2000, true);
//				return;
//			}
//			if (decPeso.getValue().floatValue() < rg1) {
//				tab1.setSelected(true);
//				decPeso.setFocus(true);
//				Clients.showNotification("Ingrese un valor correcto para el peso.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		if (!chkProfundidad.isChecked()) {
//			if (decProfundidad.getValue() == null) {
//				tab1.setSelected(true);
//				decProfundidad.setFocus(true);
//				Clients.showNotification("Ingrese la profundidad.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//						"top_right", 2000, true);
//				return;
//			}
//			if (decProfundidad.getValue().floatValue() < rg1 || decProfundidad.getValue().floatValue() > rg2) {
//				tab1.setSelected(true);
//				decProfundidad.setFocus(true);
//				Clients.showNotification(
//						"Ingrese un valor correcto para la profundidad, el valor debe ser entre [0 - 2].",
//						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		if (!chkBTU.isChecked()) {
//			if (decBTU.getValue() == null) {
//				tab1.setSelected(true);
//				decBTU.setFocus(true);
//				Clients.showNotification("Ingrese los BTU.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
//						"top_right", 2000, true);
//				return;
//			}
//			if (decBTU.getValue().floatValue() < rg1) {
//				tab1.setSelected(true);
//				decBTU.setFocus(true);
//				Clients.showNotification("Ingrese un valor correcto para los BTU.", Clients.NOTIFICATION_TYPE_WARNING,
//						dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//		}
//		if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
//			if (lbxFuentes.getItemCount() == 0) {
//				tab2.setSelected(true);
//				Clients.showNotification("Registre al menos una fuente para el equipo.",
//						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
//				return;
//			}
//			if (validarFuentesRegistradas() == false) {
//				return;
//			}
//		}
//		Messagebox.show("Esta seguro de guardar el equipo?", ".:: Nuevo equipo ::.", Messagebox.OK | Messagebox.CANCEL,
//				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_equipo dao1 = new dao_equipo();
//							modelo_equipo equipo = new modelo_equipo();
//							if (!chkNombreEquipo.isChecked()) {
//								equipo.setNom_equipo(txtNombreEquipo.getText());
//							} else {
//								equipo.setNom_equipo("N/A");
//							}
//							if (!chkSerie.isChecked()) {
//								equipo.setSerie(txtSerie.getText());
//							} else {
//								equipo.setSerie("N/A");
//							}
//							if (!chkCodigoActivo.isChecked()) {
//								equipo.setCod_activo(txtCodigoActivo.getText());
//							} else {
//								equipo.setCod_activo("N/A");
//							}
//							if (!chkCodigoProducto.isChecked()) {
//								equipo.setCod_producto(txtCodigoProducto.getText());
//							} else {
//								equipo.setCod_producto("N/A");
//							}
//							if (!chkPeso.isChecked()) {
//								equipo.setPeso(decPeso.getValue().floatValue());
//							} else {
//								equipo.setPeso(0);
//							}
//							if (!chkProfundidad.isChecked()) {
//								equipo.setProfundidad(decProfundidad.getValue().floatValue());
//							} else {
//								equipo.setProfundidad(0);
//							}
//							if (!chkBTU.isChecked()) {
//								equipo.setBtu(decBTU.getValue().floatValue());
//							} else {
//								equipo.setBtu(0);
//							}
//							if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
//								equipo.setRegistra_fuente(true);
//							} else {
//								equipo.setRegistra_fuente(false);
//							}
//							equipo.setEst_equipo("AE");
//							equipo.setUsu_ingresa(user);
//							equipo.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
//							equipo.setTipo_equipo((modelo_rack) cmbTipoEquipo.getSelectedItem().getValue());
//							equipo.setMarca((modelo_marca) cmbMarca.getSelectedItem().getValue());
//							equipo.setModelo((modelo_modelo) cmbModelo.getSelectedItem().getValue());
//							if (obtenerLocalidad() != null) {
//								equipo.setLocalidad(obtenerLocalidad());
//							} else {
//								tab1.setSelected(true);
//								Clients.showNotification(
//										"Se presentó un error al guardar el equipo, no se pudo encontrar la localidad.",
//										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
//								return;
//							}
//							List<modelo_relacion_equipo_tipo_conector> lista = new ArrayList<modelo_relacion_equipo_tipo_conector>();
//							modelo_relacion_equipo_tipo_conector relacion_equipo_tipo_conector = new modelo_relacion_equipo_tipo_conector();
//							dao_relacion_equipo_tipo_conector dao2 = new dao_relacion_equipo_tipo_conector();
//							Listcell lCell;
//							Combobox cmBox;
//							Decimalbox decP;
//							for (int i = 0; i < lbxFuentes.getItems().size(); i++) {
//								lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(1);
//								cmBox = (Combobox) lCell.getChildren().get(0);
//								relacion_equipo_tipo_conector.setEquipo(equipo);
//								if (obtenerTipoConector(
//										Long.valueOf(cmBox.getSelectedItem().getValue().toString())) != null) {
//									relacion_equipo_tipo_conector.setTipo_conector(obtenerTipoConector(
//											Long.valueOf(cmBox.getSelectedItem().getValue().toString())));
//								} else {
//									tab2.setSelected(true);
//									Clients.showNotification(
//											"Se presentó un error al guardar el equipo, no se pudo encontrar el tipo de conector.",
//											Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
//									return;
//								}
//								lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(3);
//								decP = (Decimalbox) lCell.getChildren().get(0);
//								relacion_equipo_tipo_conector.setPotencia(decP.getValue().floatValue());
//								relacion_equipo_tipo_conector.setEst_relacion("AE");
//								relacion_equipo_tipo_conector.setUsu_ingresa(user);
//								relacion_equipo_tipo_conector.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
//								lista.add(relacion_equipo_tipo_conector);
//							}
//							try {
//								dao1.insertarEquipo(equipo);
//								if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
//									for (int i = 0; i < lista.size(); i++) {
//										dao2.insertarRelacionEquipoTipoConector(relacion_equipo_tipo_conector);
//									}
//								}
//								Clients.showNotification("El equipo se guardó correctamente.",
//										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
//								limpiarCampos();
//							} catch (Exception e) {
//								Clients.showNotification(
//										"Error al guardar el equipo. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
//										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
//							}
//						}
//					}
//				});
//	}
//
//	public modelo_localidad obtenerLocalidad() {
//		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
//		modelo_localidad localidad = new modelo_localidad();
//		lista = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 2);
//		if (lista.size() > 0) {
//			localidad = lista.get(0);
//		}
//		return localidad;
//	}
//
//	public modelo_tipo_conector obtenerTipoConector(long id) {
//		List<modelo_tipo_conector> lista = new ArrayList<modelo_tipo_conector>();
//		modelo_tipo_conector tipo_conector = new modelo_tipo_conector();
//		lista = consultasABaseDeDatos.consultarTipoConectores(id, 0, "", "", 0, 2);
//		if (lista.size() > 0) {
//			tipo_conector = lista.get(0);
//		}
//		return tipo_conector;
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Events.postEvent(new Event("onClose", zNuevo));
//	}
//
//	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		tab1.setSelected(true);
//		cmbTipoEquipo.setText("");
//		txtNombreEquipo.setText("");
//		lNombreEquipo.setValue("0/25");
//		cmbMarca.setText("");
//		cmbModelo.setText("");
//		txtSerie.setText("");
//		lSerie.setValue("0/25");
//		txtCodigoActivo.setText("");
//		lCodigoActivo.setValue("0/25");
//		txtCodigoProducto.setText("");
//		lCodigoProducto.setValue("0/25");
//		decPeso.setText("");
//		decProfundidad.setText("");
//		decBTU.setText("");
//		borrarListaFuentes();
//		chkNombreEquipo.setChecked(true);
//		chkSerie.setChecked(true);
//		chkCodigoActivo.setChecked(true);
//		chkCodigoProducto.setChecked(true);
//		chkPeso.setChecked(true);
//		chkProfundidad.setChecked(true);
//		chkBTU.setChecked(true);
//		txtNombreEquipo.setDisabled(true);
//		txtSerie.setDisabled(true);
//		txtCodigoActivo.setDisabled(true);
//		txtCodigoProducto.setDisabled(true);
//		decPeso.setDisabled(true);
//		decProfundidad.setDisabled(true);
//		decBTU.setDisabled(true);
//	}

	public void borrarListaFuentes() {
		Listitem lItem;
		for (int i = lbxFuentes.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxFuentes.getItemAtIndex(i);
			lbxFuentes.removeItemAt(lItem.getIndex());
		}
	}

	public void borrarListaUrs() {
		Listitem lItem;
		for (int i = lbxUrs.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxUrs.getItemAtIndex(i);
			lbxUrs.removeItemAt(lItem.getIndex());
		}
	}

}
