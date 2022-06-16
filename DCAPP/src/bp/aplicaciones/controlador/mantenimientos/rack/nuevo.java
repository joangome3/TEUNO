package bp.aplicaciones.controlador.mantenimientos.rack;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_rack;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_rack_ur_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_rack_ur_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_ur;
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_marca;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevoRack;
	@Wire
	Button btnGrabar, btnCancelar, btnNuevaEmpresa, btnNuevaMarca, btnNuevoModelo, btnNuevaFila;
	@Wire
	Textbox txtCoordenada, txtSerie, txtCodigoActivo;
	@Wire
	Label lCoordenada, lSerie, lCodigoActivo;
	@Wire
	Combobox cmbEmpresa, cmbFila, cmbMarca, cmbModelo;
	@Wire
	Intbox intCantidadUR;
	@Wire
	Checkbox chkSerie, chkCodigoActivo;
	@Wire
	Div winList;

	Window window;

	boolean ingresa_a_accion = false;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;
	long id_mantenimiento = 21;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	List<modelo_empresa> listaEmpresas = new ArrayList<modelo_empresa>();
	List<modelo_fila> listaFilas = new ArrayList<modelo_fila>();
	List<modelo_marca> listaMarcas = new ArrayList<modelo_marca>();
	List<modelo_modelo> listaModelos = new ArrayList<modelo_modelo>();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtCoordenada.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCoordenada.setText(txtCoordenada.getText().toUpperCase().trim());
			}
		});
		txtSerie.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtSerie.setText(txtSerie.getText().toUpperCase().trim());
			}
		});
		txtCodigoActivo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoActivo.setText(txtCodigoActivo.getText().toUpperCase().trim());
			}
		});
		// Sessions.getCurrent().removeAttribute("div");
		cargarEmpresas();
		cargarFilas();
		cargarMarcas();
		cargarModelos();
		chkSerie.setChecked(true);
		chkCodigoActivo.setChecked(true);
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresas;
	}

	public List<modelo_fila> obtenerFilas() {
		return listaFilas;
	}

	public List<modelo_marca> obtenerMarcas() {
		return listaMarcas;
	}

	public List<modelo_modelo> obtenerModelos() {
		return listaModelos;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEmpresas() {
		listaEmpresas = consultasABaseDeDatos.consultarEmpresas(id_dc, id_mantenimiento, "", "", 0, 6);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_empresa bean = (modelo_empresa) o2;
				return bean.getNom_empresa().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEmpresas), myComparator, 15);
		cmbEmpresa.setModel(mySubModel);
		ComboitemRenderer<modelo_empresa> myRenderer = new ComboitemRenderer<modelo_empresa>() {
			@Override
			public void render(Comboitem item, modelo_empresa bean, int index) throws Exception {
				item.setLabel(bean.getNom_empresa());
				item.setValue(bean);
			}
		};
		cmbEmpresa.setItemRenderer(myRenderer);
		binder.loadComponent(cmbEmpresa);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarFilas() {
		listaFilas = consultasABaseDeDatos.consultarFilas(0, id_dc, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_fila bean = (modelo_fila) o2;
				return bean.getNom_fila().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaFilas), myComparator, 15);
		cmbFila.setModel(mySubModel);
		ComboitemRenderer<modelo_fila> myRenderer = new ComboitemRenderer<modelo_fila>() {
			@Override
			public void render(Comboitem item, modelo_fila bean, int index) throws Exception {
				item.setLabel(bean.getNom_fila());
				item.setValue(bean);
			}
		};
		cmbFila.setItemRenderer(myRenderer);
		binder.loadComponent(cmbFila);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarMarcas() {
		listaMarcas = consultasABaseDeDatos.consultarMarcas(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_marca bean = (modelo_marca) o2;
				return bean.getNom_marca().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaMarcas), myComparator, 15);
		cmbMarca.setModel(mySubModel);
		ComboitemRenderer<modelo_marca> myRenderer = new ComboitemRenderer<modelo_marca>() {
			@Override
			public void render(Comboitem item, modelo_marca bean, int index) throws Exception {
				item.setLabel(bean.getNom_marca());
				item.setValue(bean);
			}
		};
		cmbMarca.setItemRenderer(myRenderer);
		binder.loadComponent(cmbMarca);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarModelos() {
		listaModelos = consultasABaseDeDatos.consultarModelos(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_modelo bean = (modelo_modelo) o2;
				return bean.getNom_modelo().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaModelos), myComparator, 15);
		cmbModelo.setModel(mySubModel);
		ComboitemRenderer<modelo_modelo> myRenderer = new ComboitemRenderer<modelo_modelo>() {
			@Override
			public void render(Comboitem item, modelo_modelo bean, int index) throws Exception {
				item.setLabel(bean.getNom_modelo());
				item.setValue(bean);
			}
		};
		cmbModelo.setItemRenderer(myRenderer);
		binder.loadComponent(cmbModelo);
	}

	@Listen("onBlur=#txtCoordenada")
	public void onBlur$txtCoordenada()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtCoordenada.getText().length() <= 0) {
			return;
		}
		dao_rack dao = new dao_rack();
		if (dao.consultarRacks(id_dc, 0, txtCoordenada.getText(), "", 0, 3).size() > 0) {
			txtCoordenada.setFocus(true);
			Clients.showNotification("La coordenada ya se encuentra registrada.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
	}

	@Listen("onClick=#chkSerie")
	public void onClick$chkSerie() {
		if (chkSerie.isChecked()) {
			txtSerie.setDisabled(true);
			txtSerie.setText("");
		} else {
			txtSerie.setDisabled(false);
			txtSerie.setText("");
		}
	}

	@Listen("onClick=#chkCodigoActivo")
	public void onClick$chkCodigoActivo() {
		if (chkCodigoActivo.isChecked()) {
			txtCodigoActivo.setDisabled(true);
			txtCodigoActivo.setText("");
		} else {
			txtCodigoActivo.setDisabled(false);
			txtCodigoActivo.setText("");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			cmbEmpresa.setFocus(true);
			Clients.showNotification("Seleccione una empresa.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (cmbFila.getSelectedItem() == null) {
			cmbFila.setFocus(true);
			Clients.showNotification("Seleccione una fila.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (txtCoordenada.getText().length() <= 0) {
			txtCoordenada.setFocus(true);
			Clients.showNotification("Ingrese la coordenada.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		dao_rack dao = new dao_rack();
		if (dao.consultarRacks(id_dc, 0, txtCoordenada.getText(), "", 0, 4).size() > 0) {
			txtCoordenada.setFocus(true);
			Clients.showNotification("La coordenada ya se encuentra registrada.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (cmbMarca.getSelectedItem() == null) {
			cmbMarca.setFocus(true);
			Clients.showNotification("Seleccione una marca.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (cmbModelo.getSelectedItem() == null) {
			cmbModelo.setFocus(true);
			Clients.showNotification("Seleccione un modelo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (!chkSerie.isChecked()) {
			if (txtSerie.getText().length() <= 0) {
				txtSerie.setFocus(true);
				Clients.showNotification("Ingrese la serie.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 2000, true);
				return;
			}
		}
		if (!chkCodigoActivo.isChecked()) {
			if (txtCodigoActivo.getText().length() <= 0) {
				txtCodigoActivo.setFocus(true);
				Clients.showNotification("Ingrese el codigo de activo.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (intCantidadUR.getValue() == null) {
			intCantidadUR.setFocus(true);
			Clients.showNotification("Ingrese la cantidad de UR.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (intCantidadUR.getValue() < 0) {
			intCantidadUR.setFocus(true);
			Clients.showNotification("Ingrese un valor correcto para la cantidad de UR.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		Messagebox.show("Esta seguro de guardar el rack?", ".:: Nuevo rack ::.", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_rack dao1 = new dao_rack();
							modelo_rack rack = new modelo_rack();
							rack.setCoord_rack(txtCoordenada.getText());
							if (!chkSerie.isChecked()) {
								rack.setSerie(txtSerie.getText());
							} else {
								rack.setSerie("N/A");
							}
							if (!chkCodigoActivo.isChecked()) {
								rack.setCod_activo(txtCodigoActivo.getText());
							} else {
								rack.setCod_activo("N/A");
							}
							rack.setCant_ur(intCantidadUR.getValue());
							rack.setEst_rack("AE");
							rack.setUsu_ingresa(user);
							rack.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							rack.setEmpresa((modelo_empresa) cmbEmpresa.getSelectedItem().getValue());
							rack.setFila((modelo_fila) cmbFila.getSelectedItem().getValue());
							rack.setMarca((modelo_marca) cmbMarca.getSelectedItem().getValue());
							rack.setModelo((modelo_modelo) cmbModelo.getSelectedItem().getValue());
							if (obtenerLocalidad() != null) {
								rack.setLocalidad(obtenerLocalidad());
							} else {
								Clients.showNotification(
										"Se presentó un error al guardar el rack, no se pudo encontrar la localidad.",
										Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 4000, true);
								return;
							}
							List<modelo_relacion_rack_ur_equipo> lista = new ArrayList<modelo_relacion_rack_ur_equipo>();
							dao_relacion_rack_ur_equipo dao2 = new dao_relacion_rack_ur_equipo();
							List<modelo_ur> listaUrs = new ArrayList<modelo_ur>();
							modelo_relacion_rack_ur_equipo relacion_rack_ur_equipo = null;
							listaUrs = consultasABaseDeDatos.consultarUrs(0, 0, "", "", 0, 1);
							for (int i = 0; i < rack.getCant_ur(); i++) {
								relacion_rack_ur_equipo = new modelo_relacion_rack_ur_equipo();
								relacion_rack_ur_equipo.setRack(rack);
								relacion_rack_ur_equipo.setUr(listaUrs.get(i));
								relacion_rack_ur_equipo.setOcupado(false);
								relacion_rack_ur_equipo.setEst_relacion("AE");
								relacion_rack_ur_equipo.setUsu_ingresa(user);
								relacion_rack_ur_equipo.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								lista.add(relacion_rack_ur_equipo);
							}
							try {
								dao1.insertarRack(rack);
								for (int i = 0; i < lista.size(); i++) {
									dao2.insertarRelacionRackUrEquipo(lista.get(i));
								}
								Clients.showNotification("El rack se guardó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al guardar el rack. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}
	
	@Listen("onClick=#btnNuevaEmpresa")
	public void onClick$btnNuevo() {
		window = (Window) Executions.createComponents("/mantenimientos/empresa/nuevo.zul", null, null);
		btnNuevaEmpresa.setDisabled(true);
		if (window instanceof Window) {
			window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					btnNuevaEmpresa.setDisabled(false);
					cargarEmpresas();
				}
			});
		}
		window.setParent(winList);
	}

	public modelo_localidad obtenerLocalidad() {
		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
		modelo_localidad localidad = new modelo_localidad();
		lista = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 2);
		if (lista.size() > 0) {
			localidad = lista.get(0);
		}
		return localidad;
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevoRack));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		cmbEmpresa.setText("");
		cmbFila.setText("");
		txtCoordenada.setText("");
		lCoordenada.setValue("0/10");
		cmbMarca.setText("");
		cmbModelo.setText("");
		txtSerie.setText("");
		lSerie.setValue("0/25");
		txtCodigoActivo.setText("");
		lCodigoActivo.setValue("0/25");
		intCantidadUR.setText("");
		chkSerie.setChecked(true);
		chkCodigoActivo.setChecked(true);
		txtSerie.setDisabled(true);
		txtCodigoActivo.setDisabled(true);
	}

}
