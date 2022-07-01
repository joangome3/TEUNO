package bp.aplicaciones.controlador;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.DAO.dao_relacion_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_informativo;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings("deprecation")
public class dashboard extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@Wire
	Window zDashboard;
	@Wire
	Button btnMenu, btnUsuario, btnSalir;
	@Wire
	Image imgCape, imgCintas, imgSibod, imgMantenimientos;
	@Wire
	Menu tMenu1, tMenu2, tMenu3, tMenu4, tCape, tBodega, tCintas, tControlCambio, tMantenimientos, tPersonal;
	@Wire
	Menuitem tcMantenimientoUsuarios, tcMantenimientoPerfiles, tcMantenimientoLocalidades, tcMantenimientoParametros1,
			tcMantenimientoEmpresas, tcMantenimientoUbicaciones1, tcMantenimientoSolicitantesProveedores,
			tcMantenimientoCategorias1, tcMantenimientoSesiones, tcMantenimientoRespaldos, tcMantenimientoCapacidades,
			tcMantenimientoCategorias2, tcMantenimientoUbicaciones2, tcMantenimientoSolicitudes, tcManosRemotas,
			tcMantenimientoFilas, tcMantenimientoRacks, tcMantenimientoMarcas, tcMantenimientoModelos,
			tcMantenimientoTipoEquipos, tcMantenimientoTipoConectores, tcMantenimientoEquipos,
			tcMantenimientoInformativos, tcBodega, tcBitacora, tcControlCambioGenerar, tcCintas, tcAcercaDe, tcDBodega,
			tcDBitacora, tcPersonal, tcMantenimientoManuales, tcMantenimientoEstadosEquipo,
			tcMantenimientoMantenimientos, tcMantenimientoOpciones, tcMantenimientoTipoDocumentos, tcMantenimientoTipoServicios;
	@Wire
	Tabbox tTab;
	@Wire
	Tab tDashboard;
	@Wire
	Tabpanels tPanel;
	@Wire
	Div dSolicitudes;
	@Wire
	Center cPaneles;
	@Wire
	Combobox cmbLocalidad;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String mlocalidades, mparametros1, mperfiles, musuarios, mempresas, msolicitantes, mcategorias1, mubicaciones1,
			marticulos, msesiones, mrespaldos, mcapacidades, mcategorias2, mubicaciones2, msolicitudes, minformativos,
			mmanuales, mracks, mfilas, mmarcas, mmodelos, mtipoequipos, mtipoconectores, mequipos, mestadosequipo,
			mmantenimientos, mopciones, mtipodocumentos, mtipo_servicios;
	String oarticulos, oBodega, oreporte, ocontrolcambio, obitacora, ocintas, oPersonal, oManosRemotas;

	List<modelo_solicitud> listaSolicitud = new ArrayList<modelo_solicitud>();
	List<modelo_movimiento_dn> listaMovimientoDN = new ArrayList<modelo_movimiento_dn>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	modelo_usuario usuario = new modelo_usuario();

	int sAbiertas = 0, sRevision = 0, sPendienteEjecucion = 0, sPendienteActualizacion = 0,
			sMovimientoCintasValidacionOperadorEnTurnoActual = 0, sMovimientoCintasValidacionOperadorEnTurnoT1 = 0,
			sMovimientoCintasValidacionOperadorAuditor = 0;

	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	String usup = "";

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		tMenu1.setAttribute("data-tittle", "APLICACIONES");
		cargarLocalidades();
		mostrarLocalidad();
		cargarDatosUsuario();
		cargarEtiquetaDeUsuario();
		inicializarPermisosMantenimientos();
		validarPermisosMantenimientos();
		inicializarPermisosOpciones();
		validarPermisosOpciones();
		// cargarSolicitudes();
		cargarMovimientosDN();
		inicializarSolicitudesPendientes();
		inicializarValidacionesCruzadasPendientesModuloCintas();
		cargarParametros();
		//onClick$tcDBitacora();
		cargarInformativos();
		Sessions.getCurrent().setAttribute("btn", btnSalir);
		// Clients.showNotification(usup, "info", btnUsuario, "start_before", 3000);
	}

	public void cargarInformativos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_informativo dao = new dao_informativo();
		List<modelo_informativo> listaInformativo = new ArrayList<modelo_informativo>();
		listaInformativo = dao.obtenerInformativos("", 6, 0, 0, "", "");
		if (listaInformativo.size() == 0) {
			return;
		}
		Sessions.getCurrent().setAttribute("listaInformativo", listaInformativo);
		Component comp = Executions.createComponents("/informativo.zul", null, null);
		if (comp instanceof Window) {
			((Window) comp).doModal();
			comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
				}
			});
		}
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
	}

	public void cargarDatosUsuario() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		dao_usuario dao = new dao_usuario();
		usuario = dao.consultarUsuarios(id_user, 0, "", "", 0, 5).get(0);
	}

	public void cargarParametros() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarLocalidades() {
		listaLocalidad = consultasABaseDeDatos.consultarLocalidades(0, 0, "", "", 0, 2);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_localidad bean = (modelo_localidad) o2;
				return bean.getNom_localidad().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaLocalidad), myComparator, 15);
		cmbLocalidad.setModel(mySubModel);
		ComboitemRenderer<modelo_localidad> myRenderer = new ComboitemRenderer<modelo_localidad>() {
			@Override
			public void render(Comboitem item, modelo_localidad bean, int index) throws Exception {
				item.setLabel(bean.getNom_localidad());
				item.setValue(bean);
			}
		};
		cmbLocalidad.setItemRenderer(myRenderer);
		binder.loadComponent(cmbLocalidad);
	}

	public void mostrarLocalidad() throws ClassNotFoundException, FileNotFoundException, IOException {
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == id_dc) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		if (id_perfil != 1 && id_perfil != 3 && id_perfil != 6) {
			cmbLocalidad.setDisabled(true);
			cmbLocalidad.setReadonly(true);
		} else {
			cmbLocalidad.setDisabled(false);
			cmbLocalidad.setReadonly(false);
		}
	}

	public boolean validarSiEsUsuarioPermitido() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean es_usuario_permitido = false;
		cargarParametros();
		if (id_perfil == 1) {
			es_usuario_permitido = true;
		} else if (listaParametros.size() > 0) {
			if (id_perfil == listaParametros.get(0).getId_perfil_revisor_bitacora()) {
				es_usuario_permitido = true;
			} else {
				es_usuario_permitido = false;
			}
		} else {
			es_usuario_permitido = false;
		}
		return es_usuario_permitido;
	}

	public void cargarEtiquetaDeUsuario()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_perfil dao = new dao_perfil();
		List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
		String nom_localidad = "";
		listaPerfil = dao.consultarPerfiles(id_perfil, 0, "", "", 0, 5);
		String[] nombre = usuario.getNom_usuario().split(" ");
		String[] apellido = usuario.getApe_usuario().split(" ");
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (id_dc == listaLocalidad.get(i).getId_localidad()) {
				nom_localidad = listaLocalidad.get(i).getNom_localidad();
				i = listaLocalidad.size();
			}
		}
		if (listaPerfil.size() == 1) {
			if (nom_localidad != "") {
				usup = nombre[0] + " " + apellido[0]
						+ " (<span style='font-weight: bold; font-style: italic !important;'>"
						+ listaPerfil.get(0).getNom_perfil() + "</span>)</br>" + nom_localidad + "</br>"
						+ Executions.getCurrent().getRemoteAddr() + "</br>"
						+ fechas.obtenerFechaFormateada(new Date(), "dd/MM/yyyy HH:mm:ss");
			} else {
				usup = nombre[0] + " " + apellido[0]
						+ " (<span style='font-weight: bold; font-style: italic !important;'>"
						+ listaPerfil.get(0).getNom_perfil() + "</span>)" + "</br>"
						+ Executions.getCurrent().getRemoteAddr() + "</br>"
						+ fechas.obtenerFechaFormateada(new Date(), "dd/MM/yyyy HH:mm:ss");
			}
		} else {
			if (nom_localidad != "") {
				usup = nom_ape_user + "</br>" + nom_localidad + "</br>" + Executions.getCurrent().getRemoteAddr()
						+ "</br>" + fechas.obtenerFechaFormateada(new Date(), "dd/MM/yyyy HH:mm:ss");
			} else {
				usup = nom_ape_user + "</br>" + Executions.getCurrent().getRemoteAddr() + "</br>"
						+ fechas.obtenerFechaFormateada(new Date(), "dd/MM/yyyy HH:mm:ss");
			}
		}
	}

//	public void cargarSolicitudes() {
//		dao_solicitud dao = new dao_solicitud();
//		String criterio = "";
//		try {
//			try {
//				listaSolicitud = dao.obtenerSolicitudes(criterio, "", "", "", 0, 0, 6);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (SQLException e) {
//			Messagebox.show(
//					"Error al cargar las solicitudes. \n\n" + "Codigo de error: " + e.getErrorCode()
//							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
//					".:: Cargar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}

	public void cargarMovimientosDN() throws ClassNotFoundException, FileNotFoundException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		listaMovimientoDN = consultasABaseDeDatos.cargarMovimientosDN("", "", "", String.valueOf(id_dc), 0, 5, "");
	}

	public void inicializarSolicitudesPendientes() {
		sAbiertas = 0;
		sRevision = 0;
		sPendienteEjecucion = 0;
		sPendienteActualizacion = 0;
		for (int i = 0; i < listaSolicitud.size(); i++) {
			if (listaSolicitud.get(i).getEst_solicitud().equals("P")) {
				sAbiertas += 1;
			}
			if (listaSolicitud.get(i).getEst_solicitud().equals("R")) {
				sRevision += 1;
			}
			if (listaSolicitud.get(i).getEst_solicitud().equals("S")) {
				sPendienteEjecucion += 1;
			}
			if (listaSolicitud.get(i).getEst_solicitud().equals("T")) {
				sPendienteActualizacion += 1;
			}
		}
	}

	public void inicializarValidacionesCruzadasPendientesModuloCintas() {
		sMovimientoCintasValidacionOperadorEnTurnoActual = 0;
		sMovimientoCintasValidacionOperadorEnTurnoT1 = 0;
		sMovimientoCintasValidacionOperadorAuditor = 0;
		for (int i = 0; i < listaMovimientoDN.size(); i++) {
			if (listaMovimientoDN.get(i).getEst_validacion().equals("RV1")) {
				sMovimientoCintasValidacionOperadorEnTurnoActual += 1;
			}
			if (listaMovimientoDN.get(i).getEst_validacion().equals("RV2")) {
				sMovimientoCintasValidacionOperadorEnTurnoT1 += 1;
			}
			if (listaMovimientoDN.get(i).getEst_validacion().equals("RV3")) {
				sMovimientoCintasValidacionOperadorAuditor += 1;
			}
		}
	}

	@Listen("onCreate = #zDashboard")
	public void init() {
		String tAbiertas = "", tRevision = "", tPendienteEjecucion = "", tPendienteActualizacion = "",
				tMovimientoCintasValidacionOperadorEnTurnoActual = "",
				tMovimientoCintasValidacionOperadorEnTurnoT1 = "", tMovimientoCintasValidacionOperadorAuditor = "";
		if (sAbiertas == 0) {
			tAbiertas = "* No existen solicitudes abiertas";
		} else {
			tAbiertas = "* " + sAbiertas + " solicitud(es) abierta(s)";
		}
		if (sRevision == 0) {
			tRevision = "* No existen solicitudes en revisión";
		} else {
			tRevision = "* " + sRevision + " solicitud(es) en revisión";
		}
		if (sPendienteEjecucion == 0) {
			tPendienteEjecucion = "* No existen solicitudes pendientes de ejecución";
		} else {
			tPendienteEjecucion = "* " + sPendienteEjecucion + " solicitud(es) pendiente(s) de ejecución";
		}
		if (sPendienteActualizacion == 0) {
			tPendienteActualizacion = "* No existen solicitudes pendientes de actualización";
		} else {
			tPendienteActualizacion = "* " + sPendienteActualizacion + " solicitud(es) pendiente(s) de actualización";
		}
		if (sMovimientoCintasValidacionOperadorEnTurnoActual == 0) {
			tMovimientoCintasValidacionOperadorEnTurnoActual = "* No existen req/mov que validar en el turno actual";
		} else {
			tMovimientoCintasValidacionOperadorEnTurnoActual = "* " + sMovimientoCintasValidacionOperadorEnTurnoActual
					+ " req/mov(s) en validación por turno actual";
		}
		if (sMovimientoCintasValidacionOperadorEnTurnoT1 == 0) {
			tMovimientoCintasValidacionOperadorEnTurnoT1 = "* No existen req/mov que validar en el turno T1";
		} else {
			tMovimientoCintasValidacionOperadorEnTurnoT1 = "* " + sMovimientoCintasValidacionOperadorEnTurnoT1
					+ " req/mov(s) en validación por turno T1";
		}
		if (sMovimientoCintasValidacionOperadorAuditor == 0) {
			tMovimientoCintasValidacionOperadorAuditor = "* No existen req/mov que validar por auditoría";
		} else {
			tMovimientoCintasValidacionOperadorAuditor = "* " + sMovimientoCintasValidacionOperadorAuditor
					+ " req/mov(s) en validación por auditoría";
		}
		Clients.showNotification("<b> ** Control de solicitudes ** </b>" + "<br/>" + tAbiertas + ".<br/>" + tRevision
				+ ".<br/>" + tPendienteEjecucion + ".<br/>" + tPendienteActualizacion + ".<br/>"
				+ "<b> ** Gestión de cintas ** </b>" + "<br/>" + tMovimientoCintasValidacionOperadorEnTurnoActual
				+ ".<br/>" + tMovimientoCintasValidacionOperadorEnTurnoT1 + ".<br/>"
				+ tMovimientoCintasValidacionOperadorAuditor + ".<br/>", "info", dSolicitudes, "after_start", 5000);
	}

	@Listen("onClick=#tDashboard")
	public void onClick$tDashboard() throws ClassNotFoundException, FileNotFoundException, IOException {
		// cargarSolicitudes();
		// inicializarSolicitudesPendientes();
		// init();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnSalir")
	public void onClick$btnSalir() throws ClassNotFoundException, FileNotFoundException, IOException {
		Messagebox.show("Esta seguro de cerrar la sesión?", ".:: Cerrar sesión ::.", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							String url = "/index.zul";
							Sessions.getCurrent().removeAttribute("id_user");
							Sessions.getCurrent().removeAttribute("id_perfil");
							Sessions.getCurrent().removeAttribute("user");
							Sessions.getCurrent().removeAttribute("id_dc");
							Sessions.getCurrent().removeAttribute("nom_ape_user");
							Sessions.getCurrent().setAttribute("div", dSolicitudes);
							Executions.sendRedirect(url);
						}
					}
				});
	}

	@Listen("onClick=#btnUsuario")
	public void onClick$btnUsuario() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		cargarEtiquetaDeUsuario();
		Clients.showNotification(usup, "info", btnUsuario, "start_before", 3000);
	}

	@Listen("onClick=#tcAcercaDe")
	public void onClick$tcAcercaDe() throws ClassNotFoundException, FileNotFoundException, IOException {
		Messagebox.show(
				"Sistema creado para integrar aplicaciones que permitan tener un mejor control de la información que se registra en el Datacenter.",
				".:: DC Aplicaciones v2.5 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onSelect=#cmbLocalidad")
	public void onSelect$cmbLocalidad() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			cmbLocalidad.setFocus(true);
			return;
		}
		if (((modelo_localidad) cmbLocalidad.getSelectedItem().getValue()).getId_localidad() == id_dc) {
			return;
		}
		Messagebox.show(
				"Esta seguro de cambiar de localidad, al realizar este cambio, se cerrará la sesión y deberá iniciar nuevamente?",
				".:: Cambiar localidad ::.", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_usuario dao = new dao_usuario();
							modelo_usuario usuario = new modelo_usuario();
							usuario = dashboard.this.usuario;
							usuario.setId_usuario(id_user);
							usuario.setLocalidad((modelo_localidad) cmbLocalidad.getSelectedItem().getValue());
							usuario.setUsu_modifica(user);
							usuario.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.actualizarUsuario(usuario);
								String url = "/index.zul";
								Sessions.getCurrent().removeAttribute("id_user");
								Sessions.getCurrent().removeAttribute("id_perfil");
								Sessions.getCurrent().removeAttribute("user");
								Sessions.getCurrent().removeAttribute("id_dc");
								Sessions.getCurrent().removeAttribute("nom_ape_user");
								Sessions.getCurrent().setAttribute("div", dSolicitudes);
								Executions.sendRedirect(url);
							} catch (Exception e) {
								Messagebox.show(
										"Error al cambiar de localidad. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Cambiar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	public void inicializarPermisosMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_relacion_perfil dao = new dao_relacion_perfil();
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 1, "", "", 0, 6).size() > 0) {
			msolicitantes = "S";
		} else {
			msolicitantes = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 2, "", "", 0, 6).size() > 0) {
			mubicaciones1 = "S";
		} else {
			mubicaciones1 = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 3, "", "", 0, 6).size() > 0) {
			mlocalidades = "S";
		} else {
			mlocalidades = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 4, "", "", 0, 6).size() > 0) {
			musuarios = "S";
		} else {
			musuarios = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 5, "", "", 0, 6).size() > 0) {
			mcategorias1 = "S";
		} else {
			mcategorias1 = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 6, "", "", 0, 6).size() > 0) {
			marticulos = "S";
		} else {
			marticulos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 7, "", "", 0, 6).size() > 0) {
			mperfiles = "S";
		} else {
			mperfiles = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 8, "", "", 0, 6).size() > 0) {
			mempresas = "S";
		} else {
			mempresas = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 9, "", "", 0, 6).size() > 0) {
			msolicitudes = "S";
		} else {
			msolicitudes = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 11, "", "", 0, 6).size() > 0) {
			msesiones = "S";
		} else {
			msesiones = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 12, "", "", 0, 6).size() > 0) {
			mrespaldos = "S";
		} else {
			mrespaldos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 13, "", "", 0, 6).size() > 0) {
			mcapacidades = "S";
		} else {
			mcapacidades = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 14, "", "", 0, 6).size() > 0) {
			mcategorias2 = "S";
		} else {
			mcategorias2 = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 15, "", "", 0, 6).size() > 0) {
			mubicaciones2 = "S";
		} else {
			mubicaciones2 = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 17, "", "", 0, 6).size() > 0) {
			minformativos = "S";
		} else {
			minformativos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 20, "", "", 0, 6).size() > 0) {
			mmanuales = "S";
		} else {
			mmanuales = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 21, "", "", 0, 6).size() > 0) {
			mracks = "S";
		} else {
			mracks = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 22, "", "", 0, 6).size() > 0) {
			mfilas = "S";
		} else {
			mfilas = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 23, "", "", 0, 6).size() > 0) {
			mmarcas = "S";
		} else {
			mmarcas = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 24, "", "", 0, 6).size() > 0) {
			mmodelos = "S";
		} else {
			mmodelos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 25, "", "", 0, 6).size() > 0) {
			mtipoequipos = "S";
		} else {
			mtipoequipos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 26, "", "", 0, 6).size() > 0) {
			mequipos = "S";
		} else {
			mequipos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 27, "", "", 0, 6).size() > 0) {
			mtipoconectores = "S";
		} else {
			mtipoconectores = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 28, "", "", 0, 6).size() > 0) {
			mestadosequipo = "S";
		} else {
			mestadosequipo = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 70, "", "", 0, 6).size() > 0) {
			mmantenimientos = "S";
		} else {
			mmantenimientos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 71, "", "", 0, 6).size() > 0) {
			mopciones = "S";
		} else {
			mopciones = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 72, "", "", 0, 6).size() > 0) {
			mtipodocumentos = "S";
		} else {
			mtipodocumentos = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 73, "", "", 0, 6).size() > 0) {
			mparametros1 = "S";
		} else {
			mparametros1 = "N";
		}
		if (dao.obtenerRelacionesMantenimientos(id_perfil, 74, "", "", 0, 6).size() > 0) {
			mtipo_servicios = "S";
		} else {
			mtipo_servicios = "N";
		}
	}

	public void inicializarPermisosOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_relacion_perfil dao = new dao_relacion_perfil();
		if (dao.obtenerRelacionesOpciones(id_perfil, 1, "", "", 0, 7).size() > 0) {
			oBodega = "S";
		} else {
			oBodega = "N";
		}
		if (dao.obtenerRelacionesOpciones(id_perfil, 2, "", "", 0, 7).size() > 0) {
			ocontrolcambio = "S";
		} else {
			ocontrolcambio = "N";
		}
		if (dao.obtenerRelacionesOpciones(id_perfil, 3, "", "", 0, 7).size() > 0) {
			obitacora = "S";
		} else {
			obitacora = "N";
		}
		if (dao.obtenerRelacionesOpciones(id_perfil, 4, "", "", 0, 7).size() > 0) {
			ocintas = "S";
		} else {
			ocintas = "N";
		}
		if (dao.obtenerRelacionesOpciones(id_perfil, 5, "", "", 0, 7).size() > 0) {
			oPersonal = "S";
		} else {
			oPersonal = "N";
		}
		if (dao.obtenerRelacionesOpciones(id_perfil, 6, "", "", 0, 7).size() > 0) {
			oManosRemotas = "S";
		} else {
			oManosRemotas = "N";
		}
	}

	public void validarPermisosMantenimientos() {
		if (mlocalidades.equals("S")) {
			tcMantenimientoLocalidades.setDisabled(false);
		} else {
			tcMantenimientoLocalidades.setDisabled(true);
			tcMantenimientoLocalidades.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mperfiles.equals("S")) {
			tcMantenimientoPerfiles.setDisabled(false);
		} else {
			tcMantenimientoPerfiles.setDisabled(true);
			tcMantenimientoPerfiles.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (musuarios.equals("S")) {
			tcMantenimientoUsuarios.setDisabled(false);
		} else {
			tcMantenimientoUsuarios.setDisabled(true);
			tcMantenimientoUsuarios.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mempresas.equals("S")) {
			tcMantenimientoEmpresas.setDisabled(false);
		} else {
			tcMantenimientoEmpresas.setDisabled(true);
			tcMantenimientoEmpresas.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (msolicitantes.equals("S")) {
			tcMantenimientoSolicitantesProveedores.setDisabled(false);
		} else {
			tcMantenimientoSolicitantesProveedores.setDisabled(true);
			tcMantenimientoSolicitantesProveedores.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mcategorias1.equals("S")) {
			tcMantenimientoCategorias1.setDisabled(false);
		} else {
			tcMantenimientoCategorias1.setDisabled(true);
			tcMantenimientoCategorias1.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mubicaciones1.equals("S")) {
			tcMantenimientoUbicaciones1.setDisabled(false);
		} else {
			tcMantenimientoUbicaciones1.setDisabled(true);
			tcMantenimientoUbicaciones1.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (msolicitudes.equals("S")) {
			tcMantenimientoSolicitudes.setDisabled(false);
		} else {
			tcMantenimientoSolicitudes.setDisabled(true);
			tcMantenimientoSolicitudes.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (msesiones.equals("S")) {
			tcMantenimientoSesiones.setDisabled(false);
		} else {
			tcMantenimientoSesiones.setDisabled(true);
			tcMantenimientoSesiones.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mrespaldos.equals("S")) {
			tcMantenimientoRespaldos.setDisabled(false);
		} else {
			tcMantenimientoRespaldos.setDisabled(true);
			tcMantenimientoRespaldos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mcapacidades.equals("S")) {
			tcMantenimientoCapacidades.setDisabled(false);
		} else {
			tcMantenimientoCapacidades.setDisabled(true);
			tcMantenimientoCapacidades.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mcategorias2.equals("S")) {
			tcMantenimientoCategorias2.setDisabled(false);
		} else {
			tcMantenimientoCategorias2.setDisabled(true);
			tcMantenimientoCategorias2.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mubicaciones2.equals("S")) {
			tcMantenimientoUbicaciones2.setDisabled(false);
		} else {
			tcMantenimientoUbicaciones2.setDisabled(true);
			tcMantenimientoUbicaciones2.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (minformativos.equals("S")) {
			tcMantenimientoInformativos.setDisabled(false);
		} else {
			tcMantenimientoInformativos.setDisabled(true);
			tcMantenimientoInformativos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mmanuales.equals("S")) {
			tcMantenimientoManuales.setDisabled(false);
		} else {
			tcMantenimientoManuales.setDisabled(true);
			tcMantenimientoManuales.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mfilas.equals("S")) {
			tcMantenimientoFilas.setDisabled(false);
		} else {
			tcMantenimientoFilas.setDisabled(true);
			tcMantenimientoFilas.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mracks.equals("S")) {
			tcMantenimientoRacks.setDisabled(false);
		} else {
			tcMantenimientoRacks.setDisabled(true);
			tcMantenimientoRacks.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mmarcas.equals("S")) {
			tcMantenimientoMarcas.setDisabled(false);
		} else {
			tcMantenimientoMarcas.setDisabled(true);
			tcMantenimientoMarcas.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mmodelos.equals("S")) {
			tcMantenimientoModelos.setDisabled(false);
		} else {
			tcMantenimientoModelos.setDisabled(true);
			tcMantenimientoModelos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mtipoequipos.equals("S")) {
			tcMantenimientoTipoEquipos.setDisabled(false);
		} else {
			tcMantenimientoTipoEquipos.setDisabled(true);
			tcMantenimientoTipoEquipos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mequipos.equals("S")) {
			tcMantenimientoEquipos.setDisabled(false);
		} else {
			tcMantenimientoEquipos.setDisabled(true);
			tcMantenimientoEquipos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mtipoconectores.equals("S")) {
			tcMantenimientoTipoConectores.setDisabled(false);
		} else {
			tcMantenimientoTipoConectores.setDisabled(true);
			tcMantenimientoTipoConectores.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mestadosequipo.equals("S")) {
			tcMantenimientoEstadosEquipo.setDisabled(false);
		} else {
			tcMantenimientoEstadosEquipo.setDisabled(true);
			tcMantenimientoEstadosEquipo.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mmantenimientos.equals("S")) {
			tcMantenimientoMantenimientos.setDisabled(false);
		} else {
			tcMantenimientoMantenimientos.setDisabled(true);
			tcMantenimientoMantenimientos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mopciones.equals("S")) {
			tcMantenimientoOpciones.setDisabled(false);
		} else {
			tcMantenimientoOpciones.setDisabled(true);
			tcMantenimientoOpciones.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mtipodocumentos.equals("S")) {
			tcMantenimientoTipoDocumentos.setDisabled(false);
		} else {
			tcMantenimientoTipoDocumentos.setDisabled(true);
			tcMantenimientoTipoDocumentos.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mparametros1.equals("S")) {
			tcMantenimientoParametros1.setDisabled(false);
		} else {
			tcMantenimientoParametros1.setDisabled(true);
			tcMantenimientoParametros1.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
		if (mtipo_servicios.equals("S")) {
			tcMantenimientoTipoServicios.setDisabled(false);
		} else {
			tcMantenimientoTipoServicios.setDisabled(true);
			tcMantenimientoTipoServicios.setTooltiptext("No tiene permisos para usar esta configuración.");
		}
	}

	public void validarPermisosOpciones() {
		if (oBodega.equals("S")) {
			tcBodega.setDisabled(false);
		} else {
			tcBodega.setDisabled(true);
			tcBodega.setTooltiptext("No tiene permisos para usar esta opción.");
		}
		if (ocontrolcambio.equals("S")) {
			tcControlCambioGenerar.setDisabled(false);
		} else {
			tcControlCambioGenerar.setDisabled(true);
			tcControlCambioGenerar.setTooltiptext("No tiene permisos para usar esta opción.");
		}
		if (obitacora.equals("S")) {
			tcBitacora.setDisabled(false);
		} else {
			tcBitacora.setDisabled(true);
			tcBitacora.setTooltiptext("No tiene permisos para usar esta opción.");
		}
		if (ocintas.equals("S")) {
			tcCintas.setDisabled(false);
		} else {
			tcCintas.setDisabled(true);
			tcCintas.setTooltiptext("No tiene permisos para usar esta opción.");
		}
		if (oPersonal.equals("S")) {
			tcPersonal.setDisabled(false);
		} else {
			tcPersonal.setDisabled(true);
			tcPersonal.setTooltiptext("No tiene permisos para usar esta opción.");
		}
		if (oManosRemotas.equals("S")) {
			tcManosRemotas.setDisabled(false);
		} else {
			tcManosRemotas.setDisabled(true);
			tcManosRemotas.setTooltiptext("No tiene permisos para usar esta opción.");
		}
	}

	/*
	 * DASHBOARD - BITACORA
	 */

	@Listen("onClick=#tcDBitacora")
	public void onClick$tcDBitacora() {
		try {
			Include incl = new Include("/panel2.zul");
			for (int i = 0; i < cPaneles.getChildren().size(); i++) {
				cPaneles.removeChild(cPaneles.getChildren().get(i));
			}
			cPaneles.appendChild(incl);
			tDashboard.setSelected(true);
			tDashboard.setLabel(" - DASHBOARD | GESTION DE BITACORA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * DASHBOARD - INVENTARIO
	 */

	@Listen("onClick=#tcDBodega")
	public void onClick$tcDBodega() {
		try {
			Include incl = new Include("/panel1.zul");
			for (int i = 0; i < cPaneles.getChildren().size(); i++) {
				cPaneles.removeChild(cPaneles.getChildren().get(i));
			}
			cPaneles.appendChild(incl);
			tDashboard.setSelected(true);
			tDashboard.setLabel(" - DASHBOARD | GESTION DE BODEGA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * BITACORA
	 */

	@Listen("onClick=#tcBitacora")
	public void onClick$tcBitacora() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcBitacora.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcBitacora.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE BITACORA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcBitacora.getId());
			tab.setImage("/img/botones/ButtonBitacore6.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/bitacora/principal.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * CONTROL DE CAMBIOS
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#tcControlCambioGenerar")
	public void onClick$tcControlCambioGenerar() {
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getLocalidad_control_cambio() == 0) {
				Messagebox.show("No se ha definido la localidad para el uso de la aplicación de control de cambios.",
						".:: Gestion de control de cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if (listaParametros.get(0).getLocalidad_control_cambio() != id_dc) {
				if (listaParametros.get(0).getUrl_aplicacion_externa_2() == null) {
					Messagebox.show("No se ha definido la url de la aplicación de control de cambios.",
							".:: Gestion de control de cambio ::.", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				if (listaParametros.get(0).getRedirecciona_usuario_control_cambio().equals("S")) {
					Messagebox.show("Se redireccionará al sistema de control de cambios.",
							".:: Gestion de control de cambio ::.", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
								@Override
								public void onEvent(Event event) throws Exception {
									if (event.getName().equals("onOK")) {
										Executions.getCurrent().sendRedirect(
												listaParametros.get(0).getUrl_aplicacion_externa_2(), "_blank");
									}
								}
							});
				} else {
					try {
						Borderlayout bl = new Borderlayout();
						if (tTab.hasFellow("Tab:" + tcControlCambioGenerar.getId())) {
							Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcControlCambioGenerar.getId());
							tab2.focus();
							tab2.setSelected(true);
							return;
						}
						Tab tab = new Tab();
						tab.setLabel("GESTION DE CONTROL DE CAMBIO | GENERAR CODIGO");
						tab.setClosable(true);
						tab.setSelected(true);
						tab.setId("Tab:" + tcControlCambioGenerar.getId());
						tab.setImage("/img/botones/ButtonGenerate6.png");
						tTab.getTabs().appendChild(tab);
						// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
						Tabpanel tabpanel = new Tabpanel();
						tPanel.appendChild(tabpanel);
						Include include = new Include("/control_cambio/calendario.zul");
						Center c = new Center();
						// c.setAutoscroll(true);
						c.appendChild(include);
						bl.appendChild(c);
						tabpanel.appendChild(bl);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					Borderlayout bl = new Borderlayout();
					if (tTab.hasFellow("Tab:" + tcControlCambioGenerar.getId())) {
						Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcControlCambioGenerar.getId());
						tab2.focus();
						tab2.setSelected(true);
						return;
					}
					Tab tab = new Tab();
					tab.setLabel("GESTION DE CONTROL DE CAMBIO | GENERAR CODIGO");
					tab.setClosable(true);
					tab.setSelected(true);
					tab.setId("Tab:" + tcControlCambioGenerar.getId());
					tab.setImage("/img/botones/ButtonGenerate6.png");
					tTab.getTabs().appendChild(tab);
					// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
					Tabpanel tabpanel = new Tabpanel();
					tPanel.appendChild(tabpanel);
					Include include = new Include("/control_cambio/calendario.zul");
					Center c = new Center();
					// c.setAutoscroll(true);
					c.appendChild(include);
					bl.appendChild(c);
					tabpanel.appendChild(bl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * CINTAS
	 */

	@Listen("onClick=#tcCintas")
	public void onClick$tcCintas() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getUrl_aplicacion_externa_1().length() <= 0) {
				Messagebox.show("No se ha registrado la url de la aplicación de cintas.", ".:: Gestion de cintas ::.",
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			} else {
				if (listaParametros.get(0).getRedirecciona_usuario_diners().equals("S")) {
					Executions.getCurrent().sendRedirect(listaParametros.get(0).getUrl_aplicacion_externa_1(),
							"_blank");
				} else {
					try {
						Borderlayout bl = new Borderlayout();
						if (tTab.hasFellow("Tab:" + tcCintas.getId())) {
							Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcCintas.getId());
							tab2.focus();
							tab2.setSelected(true);
							return;
						}
						Tab tab = new Tab();
						tab.setLabel("GESTION DE CINTAS");
						tab.setClosable(true);
						tab.setSelected(true);
						tab.setId("Tab:" + tcCintas.getId());
						tab.setImage("/img/botones/ButtonDiners5.png");
						tTab.getTabs().appendChild(tab);
						// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
						Tabpanel tabpanel = new Tabpanel();
						tPanel.appendChild(tabpanel);
						Include include = new Include("/cintas/principal.zul");
						Center c = new Center();
						// c.setAutoscroll(true);
						c.appendChild(include);
						bl.appendChild(c);
						tabpanel.appendChild(bl);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			Messagebox.show("No se ha registrado la url de la aplicación de cintas.", ".:: Gestion de cintas ::.",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	/*
	 * INVENTARIO
	 */

	@Listen("onClick=#tcBodega")
	public void onClick$tcBodega() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcBodega.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcBodega.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE BODEGA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcBodega.getId());
			tab.setImage("/img/botones/ButtonInventory4.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/sibod/principal.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * PERSONAL
	 */

	@Listen("onClick=#tcPersonal")
	public void onClick$tcPersonal() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcPersonal.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcPersonal.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcPersonal.getId());
			tab.setImage("/img/botones/ButtonPersonal2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/principal.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * MANOS REMOTAS
	 */

	@Listen("onClick=#tcManosRemotas")
	public void onClick$tcManosRemotas() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcManosRemotas.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcManosRemotas.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION OPERATIVA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcManosRemotas.getId());
			tab.setImage("/img/botones/ButtonRemotas2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/manos_remotas/principal.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * MANTENIMIENTOS
	 */

	@Listen("onClick=#tcMantenimientoParametros1")
	public void onClick$tcMantenimientoParametros1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoParametros1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoParametros1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("PERMITIR REGISTRO MANUAL DE SERVICIO | PARAMETROS GENERALES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoParametros1.getId());
			tab.setImage("/img/botones/ButtonParameters2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros10.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoLocalidades")
	public void onClick$tcMantenimientoLocalidades() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoLocalidades.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoLocalidades.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("ADMINISTRACION - CONFIGURACION | LOCALIDADES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoLocalidades.getId());
			tab.setImage("/img/botones/ButtonOffice2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
//			tabpanel.setStyle("height: calc(100%);");
//			tabpanel.setWidth("100%");
//			tabpanel.setVflex("max");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/localidad/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoPerfiles")
	public void onClick$tcMantenimientoPerfiles() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoPerfiles.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoPerfiles.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("ADMINISTRACION - CONFIGURACION | PERFILES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoPerfiles.getId());
			tab.setImage("/img/botones/ButtonGroup2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/perfiles/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoUsuarios")
	public void onClick$tcMantenimientoUsuarios() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoUsuarios.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoUsuarios.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | USUARIOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoUsuarios.getId());
			tab.setImage("/img/botones/ButtonUser2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/usuario/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoEmpresas")
	public void onClick$tcMantenimientoEmpresas() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoEmpresas.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoEmpresas.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | EMPRESAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoEmpresas.getId());
			tab.setImage("/img/botones/ButtonCompany2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/empresa/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoCategorias1")
	public void onClick$tcMantenimientoCategorias1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoCategorias1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoCategorias1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE BODEGA - CONFIGURACION | CATEGORIAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoCategorias1.getId());
			tab.setImage("/img/botones/ButtonCategory2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/categoria/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoUbicaciones1")
	public void onClick$tcMantenimientoUbicaciones1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoUbicaciones1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoUbicaciones1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE BODEGA - CONFIGURACION | UBICACIONES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoUbicaciones1.getId());
			tab.setImage("/img/botones/ButtonUbication2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/ubicacion/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoSolicitantesProveedores")
	public void onClick$tcMantenimientoSolicitantesProveedores() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoSolicitantesProveedores.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoSolicitantesProveedores.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | SOLICITANTES/PROVEEDORES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoSolicitantesProveedores.getId());
			tab.setImage("/img/botones/ButtonEmployee2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/solicitante/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoSesiones")
	public void onClick$tcMantenimientoSesiones() throws ClassNotFoundException, FileNotFoundException, IOException {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoSesiones.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoSesiones.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - CONFIGURACION | SESIONES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoSesiones.getId());
			tab.setImage("/img/botones/ButtonAlarm2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/sesion/consultar.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoRespaldos")
	public void onClick$tcMantenimientoRespaldos() throws ClassNotFoundException, FileNotFoundException, IOException {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoRespaldos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoRespaldos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - CONFIGURACION | RESPALDOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoRespaldos.getId());
			tab.setImage("/img/botones/ButtonBackup2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/respaldo/consultar.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoCapacidades")
	public void onClick$tcMantenimientoCapacidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoCapacidades.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoCapacidades.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - CONFIGURACION | CAPACIDADES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoCapacidades.getId());
			tab.setImage("/img/botones/ButtonTape2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/capacidad/consultar.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoCategorias2")
	public void onClick$tcMantenimientoCategorias2() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoCategorias2.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoCategorias2.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - CONFIGURACION | CATEGORIAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoCategorias2.getId());
			tab.setImage("/img/botones/ButtonCategory2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/categoria_dn/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoUbicaciones2")
	public void onClick$tcMantenimientoUbicaciones2() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoUbicaciones2.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoUbicaciones2.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE CINTAS - CONFIGURACION | UBICACIONES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoUbicaciones2.getId());
			tab.setImage("/img/botones/ButtonUbication2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/ubicacion_dn/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoSolicitudes")
	public void onClick$tcMantenimientoSolicitudes() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoSolicitudes.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoSolicitudes.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE SOLICITUDES - VALIDACION CRUZADA | SOLICITUDES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoSolicitudes.getId());
			tab.setImage("/img/botones/ButtonRequire2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/solicitud/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoInformativos")
	public void onClick$tcMantenimientoInformativos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoInformativos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoInformativos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | INFORMATIVOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoInformativos.getId());
			tab.setImage("/img/botones/ButtonInfo1.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/informativo/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoManuales")
	public void onClick$tcMantenimientoManuales() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoManuales.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoManuales.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTIÓN DE MANUALES - MANUALES Y ARCHIVOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoManuales.getId());
			tab.setImage("/img/botones/ButtonManual2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/manuales/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoRacks")
	public void onClick$tcMantenimientoRacks() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoRacks.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoRacks.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | RACKS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoRacks.getId());
			tab.setImage("/img/botones/ButtonRack2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/rack/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoFilas")
	public void onClick$tcMantenimientoFilas() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoFilas.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoFilas.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | FILAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoFilas.getId());
			tab.setImage("/img/botones/ButtonFila2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/fila/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoMarcas")
	public void onClick$tcMantenimientoMarcas() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoMarcas.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoMarcas.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | MARCAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoMarcas.getId());
			tab.setImage("/img/botones/ButtonMarca2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/marca/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoModelos")
	public void onClick$tcMantenimientoModelos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoModelos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoModelos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | MODELOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoModelos.getId());
			tab.setImage("/img/botones/ButtonModelo2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/modelo/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoTipoEquipos")
	public void onClick$tcMantenimientoTipoEquipos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoTipoEquipos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoTipoEquipos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | TIPOS DE EQUIPO");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoTipoEquipos.getId());
			tab.setImage("/img/botones/ButtonTipoEquipo2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/tipo_equipo/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoEquipos")
	public void onClick$tcMantenimientoEquipos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoEquipos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoEquipos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | EQUIPOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoEquipos.getId());
			tab.setImage("/img/botones/ButtonEquipo2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/equipo/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoTipoConectores")
	public void onClick$tcMantenimientoTipoConectores() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoTipoConectores.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoTipoConectores.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | TIPOS DE CONECTOR");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoTipoConectores.getId());
			tab.setImage("/img/botones/ButtonTipoConector2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/tipo_conector/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoEstadosEquipo")
	public void onClick$tcMantenimientoEstadosEquipo() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoEstadosEquipo.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoEstadosEquipo.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE EQUIPOS - CONFIGURACION | ESTADOS DEL EQUIPO");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoEstadosEquipo.getId());
			tab.setImage("/img/botones/ButtonEquipo3.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/estado_equipo/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoMantenimientos")
	public void onClick$tcMantenimientoMantenimientos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoMantenimientos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoMantenimientos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("ADMINISTRACIÓN - CONFIGURACION | MANTENIMIENTOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoMantenimientos.getId());
			tab.setImage("/img/botones/ButtonEquipo3.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/mantenimiento/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoOpciones")
	public void onClick$tcMantenimientoOpciones() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoOpciones.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoOpciones.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("ADMINISTRACIÓN - CONFIGURACION | MÓDULOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoOpciones.getId());
			tab.setImage("/img/botones/ButtonEquipo3.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/opcion/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcMantenimientoTipoDocumentos")
	public void onClick$tcMantenimientoTipoDocumentos() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoTipoDocumentos.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoTipoDocumentos.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | TIPO DE DOCUMENTOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoTipoDocumentos.getId());
			tab.setImage("/img/botones/ButtonEquipo3.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/tipo_documento/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onClick=#tcMantenimientoTipoServicios")
	public void onClick$tcMantenimientoTipoServicios() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoTipoServicios.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoTipoServicios.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GENERALES - CONFIGURACION | SERVICIOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoTipoServicios.getId());
			tab.setImage("/img/botones/ButtonEquipo3.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/tipo_servicio/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
