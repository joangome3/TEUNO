package bp.aplicaciones.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.DAO.dao_relacion_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.DAO.dao_informativo;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
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
	Menu tMenu1, tMenu2, tMenu3, tMenu4, tCape, tBodega, tCintas, tControlCambio, tMantenimientos;
	@Wire
	Menuitem tcMantenimientoUsuarios, tcMantenimientoPerfiles, tcMantenimientoLocalidades, tcMantenimientoParametros,
			tcMantenimientoEmpresas, tcMantenimientoUbicaciones1, tcMantenimientoSolicitantesProveedores,
			tcMantenimientoCategorias1, tcMantenimientoSesiones, tcMantenimientoRespaldos, tcMantenimientoCapacidades,
			tcMantenimientoCategorias2, tcMantenimientoUbicaciones2, tcMantenimientoSolicitudes,
			tcMantenimientoInformativos, tcBodega, tcBitacora, tcControlCambioGenerar, tcCintas, tcAcercaDe, tcDBodega,
			tcDBitacora;
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

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String mlocalidades, mparametros, mperfiles, musuarios, mempresas, msolicitantes, mcategorias1, mubicaciones1,
			marticulos, msesiones, mrespaldos, mcapacidades, mcategorias2, mubicaciones2, msolicitudes, minformativos;
	String oarticulos, oBodega, oreporte, ocontrolcambio, obitacora, ocintas;

	List<modelo_solicitud> listaSolicitud = new ArrayList<modelo_solicitud>();
	List<modelo_movimiento_dn> listaMovimientoDN = new ArrayList<modelo_movimiento_dn>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	modelo_usuario usuario = new modelo_usuario();

	int sAbiertas = 0, sRevision = 0, sPendienteEjecucion = 0, sPendienteActualizacion = 0,
			sMovimientoCintasValidacionOperadorEnTurnoActual = 0, sMovimientoCintasValidacionOperadorEnTurnoT1 = 0,
			sMovimientoCintasValidacionOperadorAuditor = 0;

	String usup = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		tMenu1.setAttribute("data-tittle", "APLICACIONES");
		cargarLocalidades();
		cargarDatosUsuario();
		cargarEtiquetaDeUsuario();
		inicializarPermisosMantenimientos();
		validarPermisosMantenimientos();
		inicializarPermisosOpciones();
		validarPermisosOpciones();
		cargarSolicitudes();
		cargarMovimientosDN();
		inicializarSolicitudesPendientes();
		inicializarValidacionesCruzadasPendientesModuloCintas();
		cargarParametros();
		onClick$tcDBitacora();
		cargarInformativos();
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
		usuario = dao.obtenerUsuario(String.valueOf(id_user), "", 2);
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

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 1, 0, 0);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las localidades. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEtiquetaDeUsuario()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_perfil dao = new dao_perfil();
		List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
		String nom_localidad = "";
		listaPerfil = dao.obtenerPerfiles("", 4, id_perfil);
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
				usup = nombre[0] + " " + apellido[0] + " (<span style='color:#008000; font-style: italic !important;'>"
						+ listaPerfil.get(0).getNom_perfil() + "</span>)</br>" + nom_localidad + "</br>"
						+ Executions.getCurrent().getRemoteAddr();
			} else {
				usup = nombre[0] + " " + apellido[0] + " (<span style='color:#008000; font-style: italic !important;'>"
						+ listaPerfil.get(0).getNom_perfil() + "</span>)" + "</br>"
						+ Executions.getCurrent().getRemoteAddr();
			}
		} else {
			if (nom_localidad != "") {
				usup = nom_ape_user + "</br>" + nom_localidad + "</br>" + Executions.getCurrent().getRemoteAddr();
			} else {
				usup = nom_ape_user + "</br>" + Executions.getCurrent().getRemoteAddr();
			}
		}
	}

	public void cargarSolicitudes() {
		dao_solicitud dao = new dao_solicitud();
		String criterio = "";
		try {
			try {
				listaSolicitud = dao.obtenerSolicitudes(criterio, "", "", "", 0, 0, 6);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las solicitudes. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

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
		cargarSolicitudes();
		inicializarSolicitudesPendientes();
		init();
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
							Executions.sendRedirect(url);
						}
					}
				});
	}

	@Listen("onClick=#btnUsuario")
	public void onClick$btnUsuario() throws ClassNotFoundException, FileNotFoundException, IOException {
		Clients.showNotification(usup, "info", btnUsuario, "start_before", 3000);
	}

	@Listen("onClick=#tcAcercaDe")
	public void onClick$tcAcercaDe() throws ClassNotFoundException, FileNotFoundException, IOException {
		Messagebox.show(
				"Sistema creado para integrar aplicaciones que permitan tener un mejor control de la información que se registra en el Datacenter.",
				".:: DC Aplicaciones v1.9 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void inicializarPermisosMantenimientos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_relacion_perfil dao = new dao_relacion_perfil();
		try {
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "1", 1) == true) {
				msolicitantes = "S";
			} else {
				msolicitantes = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "2", 1) == true) {
				mubicaciones1 = "S";
			} else {
				mubicaciones1 = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "3", 1) == true) {
				mlocalidades = "S";
			} else {
				mlocalidades = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "4", 1) == true) {
				musuarios = "S";
			} else {
				musuarios = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "5", 1) == true) {
				mcategorias1 = "S";
			} else {
				mcategorias1 = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "6", 1) == true) {
				marticulos = "S";
			} else {
				marticulos = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "7", 1) == true) {
				mperfiles = "S";
			} else {
				mperfiles = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "8", 1) == true) {
				mempresas = "S";
			} else {
				mempresas = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "9", 1) == true) {
				msolicitudes = "S";
			} else {
				msolicitudes = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "10", 1) == true) {
				mparametros = "S";
			} else {
				mparametros = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "11", 1) == true) {
				msesiones = "S";
			} else {
				msesiones = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "12", 1) == true) {
				mrespaldos = "S";
			} else {
				mrespaldos = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "13", 1) == true) {
				mcapacidades = "S";
			} else {
				mcapacidades = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "14", 1) == true) {
				mcategorias2 = "S";
			} else {
				mcategorias2 = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "15", 1) == true) {
				mubicaciones2 = "S";
			} else {
				mubicaciones2 = "N";
			}
			if (dao.obtenerRelacionesMantenimientos(String.valueOf(id_perfil), "17", 1) == true) {
				minformativos = "S";
			} else {
				minformativos = "N";
			}
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los permisos de las configuraciones. \n\n" + "Mensaje de error: \n\n"
							+ e.getMessage(),
					".:: Permisos de configuraciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisosOpciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_relacion_perfil dao = new dao_relacion_perfil();
		try {
			if (dao.obtenerRelacionesOpciones(String.valueOf(id_perfil), "1", 2) == true) {
				oBodega = "S";
			} else {
				oBodega = "N";
			}
			if (dao.obtenerRelacionesOpciones(String.valueOf(id_perfil), "2", 2) == true) {
				ocontrolcambio = "S";
			} else {
				ocontrolcambio = "N";
			}
			if (dao.obtenerRelacionesOpciones(String.valueOf(id_perfil), "3", 2) == true) {
				obitacora = "S";
			} else {
				obitacora = "N";
			}
			if (dao.obtenerRelacionesOpciones(String.valueOf(id_perfil), "4", 2) == true) {
				ocintas = "S";
			} else {
				ocintas = "N";
			}
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los permisos de las opciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Permisos de opciones ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		if (mparametros.equals("S")) {
			tcMantenimientoParametros.setDisabled(false);
		} else {
			tcMantenimientoParametros.setDisabled(true);
			tcMantenimientoParametros.setTooltiptext("No tiene permisos para usar esta configuración.");
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
						Include include = new Include("/control_cambio/consultar.zul");
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
					Include include = new Include("/control_cambio/consultar.zul");
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
	 * MANTENIMIENTOS
	 */

	@Listen("onClick=#tcMantenimientoParametros")
	public void onClick$tcMantenimientoParametros() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcMantenimientoParametros.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcMantenimientoParametros.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("ADMINISTRACION - CONFIGURACION | PARAMETROS GENERALES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcMantenimientoParametros.getId());
			tab.setImage("/img/botones/ButtonParameters2.png");
			tTab.getTabs().appendChild(tab);
			// tTab.setStyle("font-family:Trebuchet MS; font-size:10px;");
			Tabpanel tabpanel = new Tabpanel();
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/principal.zul");
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

}
