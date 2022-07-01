package bp.aplicaciones.controlador.mantenimientos.parametros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros10 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Textbox txtOpcion;
	@Wire
	Listbox lbxTipoServicios;
	@Wire
	Combobox cmbOpcion;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
	List<modelo_tipo_servicio> listaTipoServicio = new ArrayList<modelo_tipo_servicio>();
	List<modelo_parametros_generales_10> listaParametrosGenerales10 = new ArrayList<modelo_parametros_generales_10>();

	long id_mantenimiento = 73;

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfil();
		inicializarPermisos();
		if (insertar.equals("S")) {
			btnGrabar.setDisabled(false);
			btnGrabar.setVisible(true);
		} else {
			btnGrabar.setDisabled(true);
			btnGrabar.setVisible(false);
		}
		cargarOpciones();
		cargarTipoServicios();
	}

	public void cargarRelaciones() {
		if (cmbOpcion.getSelectedItem() == null) {
			return;
		}
		modelo_opcion opcion = (modelo_opcion) cmbOpcion.getSelectedItem().getValue();
		listaParametrosGenerales10 = consultasABaseDeDatos.consultarParametrosGenerales10(0, opcion.getId_opcion(), "",
				String.valueOf(id_dc), 0, 4);
		for (int i = 0; i < listaTipoServicio.size(); i++) {
			for (int j = 0; j < listaTipoServicio.get(i).getParametros_10().size(); j++) {
				if (listaTipoServicio.get(i).getParametros_10().get(j).isSe_puede_crear_modificar_eliminar() == true
						&& listaTipoServicio.get(i).getParametros_10().get(j).getOpcion().getId_opcion() == opcion
								.getId_opcion()) {
					lbxTipoServicios.getItemAtIndex(i).setSelected(true);
					break;
				}
			}
		}
	}

	@Listen("onSelect=#cmbOpcion")
	public void onSelect$cmbOpcion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		cargarRelaciones();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaOpcion = consultasABaseDeDatos.consultarOpciones(id_mantenimiento, 0, "", "", 0, 6);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_opcion bean = (modelo_opcion) o2;
				return (bean.getNom_opcion()).contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaOpcion), myComparator, 15);
		cmbOpcion.setModel(mySubModel);
		ComboitemRenderer<modelo_opcion> myRenderer = new ComboitemRenderer<modelo_opcion>() {
			@Override
			public void render(Comboitem item, modelo_opcion bean, int index) throws Exception {
				item.setLabel(bean.getNom_opcion());
				item.setValue(bean);
			}
		};
		cmbOpcion.setItemRenderer(myRenderer);
		binder.loadComponent(cmbOpcion);
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaPerfil = consultasABaseDeDatos.cargarPerfil("", 4, id_perfil);
	}

	public void inicializarPermisos() {
		if (listaPerfil.size() == 1) {
			if (listaPerfil.get(0).getConsultar().equals("S")) {
				consultar = "S";
			} else {
				consultar = "N";
			}
			if (listaPerfil.get(0).getInsertar().equals("S")) {
				insertar = "S";
			} else {
				insertar = "N";
			}
			if (listaPerfil.get(0).getModificar().equals("S")) {
				modificar = "S";
			} else {
				modificar = "N";
			}
			if (listaPerfil.get(0).getRelacionar().equals("S")) {
				relacionar = "S";
			} else {
				relacionar = "N";
			}
			if (listaPerfil.get(0).getDesactivar().equals("S")) {
				desactivar = "S";
			} else {
				desactivar = "N";
			}
			if (listaPerfil.get(0).getEliminar().equals("S")) {
				eliminar = "S";
			} else {
				eliminar = "N";
			}
			if (listaPerfil.get(0).getSolicitar().equals("S")) {
				solicitar = "S";
			} else {
				solicitar = "N";
			}
			if (listaPerfil.get(0).getRevisar().equals("S")) {
				revisar = "S";
			} else {
				revisar = "N";
			}
			if (listaPerfil.get(0).getAprobar().equals("S")) {
				aprobar = "S";
			} else {
				aprobar = "N";
			}
			if (listaPerfil.get(0).getEjecutar().equals("S")) {
				ejecutar = "S";
			} else {
				ejecutar = "N";
			}
		} else {
			Messagebox.show(error.getMensaje_error_3(), informativos.getMensaje_informativo_1(), Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
	}

	public void cargarTipoServicios() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoServicio = consultasABaseDeDatos.consultarTipoServicios(0, 0, "", "", 0, 2);
		binder.loadComponent(lbxTipoServicios);
	}

	public List<modelo_opcion> obtenerOpciones() {
		return listaOpcion;
	}

	public List<modelo_tipo_servicio> obtenerTipoServicios() {
		return listaTipoServicio;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		if (cmbOpcion.getSelectedItem() == null) {
			cmbOpcion.setFocus(true);
			Clients.showNotification("Seleccione un modulo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		Messagebox.show("Esta seguro de guardar los parametros?", "Permitir registro manual de servicio",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_parametros_generales_10 dao = new dao_parametros_generales_10();
							modelo_parametros_generales_10 relacion = null;
							List<modelo_parametros_generales_10> listaRelacion = new ArrayList<modelo_parametros_generales_10>();
							for (int i = 0; i < lbxTipoServicios.getItems().size(); i++) {
								if (lbxTipoServicios.getItemAtIndex(i).isSelected()) {
									relacion = new modelo_parametros_generales_10();
									relacion.setTipo_servicio(
											listaTipoServicio.get(lbxTipoServicios.getItemAtIndex(i).getIndex()));
									relacion.setOpcion((modelo_opcion) cmbOpcion.getSelectedItem().getValue());
									relacion.setLocalidad(obtenerLocalidad());
									relacion.setSe_puede_crear_modificar_eliminar(true);
									relacion.setEst_relacion("AE");
									relacion.setUsu_ingresa(user);
									relacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
									listaRelacion.add(relacion);
								}
							}
							try {
								for (int i = 0; i < listaParametrosGenerales10.size(); i++) {
									dao.eliminarParametroGeneral10(listaParametrosGenerales10.get(i));
								}
								for (int i = 0; i < listaRelacion.size(); i++) {
									dao.insertarParametroGeneral10(listaRelacion.get(i));
								}
								Clients.showNotification("Los permisos se asignaron correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);

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

	public modelo_localidad obtenerLocalidad() {
		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
		modelo_localidad localidad = new modelo_localidad();
		lista = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 2);
		if (lista.size() > 0) {
			localidad = lista.get(0);
		}
		return localidad;
	}

}
