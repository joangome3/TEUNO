//package bp.aplicaciones.controlador.mantenimientos.solicitud;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Progressmeter;
//import org.zkoss.zul.Row;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Datebox;
//import org.zkoss.zul.Image;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
//import bp.aplicaciones.extensiones.Fechas;
//import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
//import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//import bp.aplicaciones.mensajes.Error;
//import bp.aplicaciones.mensajes.Informativos;
//import bp.aplicaciones.mensajes.Validaciones;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class seguimiento extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zSeguimiento;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtConfiguracion, txtComentario1, txtComentario2, txtComentario3, txtSolicitante, txtAprobador,
//			txtAdministrador;
//	@Wire
//	Combobox cmbTipoSolicitud, cmbCampo;
//	@Wire
//	Progressmeter pgsEstado;
//	@Wire
//	Label lblEstado, lAprobador1, lAprobador2, lAprobador3;
//	@Wire
//	Row rCampo, rAprobador0, rAprobador1, rAprobador2, rAprobador3, rAdministrador0, rAdministrador1, rAdministrador2,
//			rAdministrador3;
//	@Wire
//	Datebox dtxFechaSolicitud, dtxFechaAprobacion, dtxFechaEjecucion;
//	@Wire
//	Label lCampo;
//	@Wire
//	Image iCampo;
//
//	List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
//	List<modelo_tipo_solicitud> listaTipoSolicitud = new ArrayList<modelo_tipo_solicitud>();
//	List<modelo_relacion_campo_mantenimiento> listaCampo = new ArrayList<modelo_relacion_campo_mantenimiento>();
//
//	long id = 0;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	/* Se capturan las variables de sesion pasadas para la solicitud */
//	Object object = (Object) Sessions.getCurrent().getAttribute("objeto");
//	long id_mantenimiento = (long) Sessions.getCurrent().getAttribute("id_mantenimiento");
//	long id_opcion = (long) Sessions.getCurrent().getAttribute("id_opcion");
//	int tipo_solicitud = (Integer) Sessions.getCurrent().getAttribute("tipo_solicitud");
//
//	modelo_solicitud solicitud = new modelo_solicitud();
//
//	validar_datos validar = new validar_datos();
//
//	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
//	Fechas fechas = new Fechas();
//	Validaciones validaciones = new Validaciones();
//
//	Informativos informativos = new Informativos();
//	Error error = new Error();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		/* Se eliminan las variables de sesion pasadas para la solicitud */
//		Sessions.getCurrent().removeAttribute("objeto");
//		Sessions.getCurrent().removeAttribute("id_mantenimiento");
//		Sessions.getCurrent().removeAttribute("id_opcion");
//		Sessions.getCurrent().removeAttribute("tipo_solicitud");
//		inicializarListas();
//		cargarDatos();
//	}
//
//	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
//		listaOpcion = consultasABaseDeDatos.cargarOpciones("");
//		listaTipoSolicitud = consultasABaseDeDatos.cargarTipoDeSolicitudes("", 1);
//		listaCampo = consultasABaseDeDatos.cargarRelacionMantenimientosCampos("", String.valueOf(id_mantenimiento), 2);
//		binder.loadComponent(cmbTipoSolicitud);
//		binder.loadComponent(cmbCampo);
//	}
//
//	public String setearNombreOpcion() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String nombre_opcion = "";
//		Iterator<modelo_opcion> it = listaOpcion.iterator();
//		while (it.hasNext()) {
//			modelo_opcion opcion = it.next();
//			if (opcion.getId_opcion() == id_opcion) {
//				nombre_opcion = opcion.getNom_opcion();
//				break;
//			}
//		}
//		return nombre_opcion;
//	}
//
//	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//		solicitud = (modelo_solicitud) object;
//		txtId.setText(String.valueOf(solicitud.getId_solicitud()));
//		cmbTipoSolicitud.setDisabled(true);
//		if (id_opcion == 4) {
//			txtConfiguracion.setText(setearNombreOpcion());
//		}else {
//			txtConfiguracion.setText(solicitud.getNom_mantenimiento());
//		}
//		txtComentario1.setText(solicitud.getComentario_1());
//		txtComentario1.setDisabled(true);
//		txtComentario2.setText(solicitud.getComentario_2());
//		txtComentario3.setText(solicitud.getComentario_3());
//		btnGrabar.setDisabled(true);
//		btnGrabar.setVisible(false);
//		for (int i = 0; i < listaTipoSolicitud.size(); i++) {
//			if (listaTipoSolicitud.get(i).getId_tipo_solicitud() == solicitud.getId_tip_solicitud()) {
//				cmbTipoSolicitud.setText(listaTipoSolicitud.get(i).getNom_tipo_solicitud());
//				i = listaTipoSolicitud.size() + 1;
//			}
//		}
//		if (solicitud.getId_tip_solicitud() == 2) {
//			for (int i = 0; i < listaCampo.size(); i++) {
//				if (listaCampo.get(i).getId_campo() == solicitud.getId_campo()) {
//					cmbCampo.setText(listaCampo.get(i).getNom_campo());
//					i = listaCampo.size() + 1;
//				}
//			}
//			lCampo.setVisible(true);
//			cmbCampo.setVisible(true);
//			iCampo.setVisible(true);
//		}
//		if (solicitud.getEst_solicitud().equals("P")) { // Abierta
//			pgsEstado.setValue(0);
//			lblEstado.setValue("Abierta");
//		}
//		if (solicitud.getEst_solicitud().equals("R")) { // En revision
//			pgsEstado.setValue(55);
//			lblEstado.setValue("En revisión");
//			lAprobador1.setValue("Comentario Revisor:");
//			lAprobador2.setValue("Revisor:");
//			lAprobador3.setValue("Fecha de revisión:");
//		}
//		if (solicitud.getEst_solicitud().equals("S")) { // Pendiente ejecucion
//			pgsEstado.setValue(75);
//			lblEstado.setValue("Pendiente ejecución");
//		}
//		if (solicitud.getEst_solicitud().equals("T")) {// Pendiente actualizacion
//			pgsEstado.setValue(0);
//			lblEstado.setValue("Pendiente actualización");
//		}
//		if (solicitud.getEst_solicitud().equals("A")) {// Aprobada
//			pgsEstado.setValue(100);
//			lblEstado.setValue("Aprobada");
//		}
//		if (solicitud.getEst_solicitud().equals("N")) {// No aprobada
//			pgsEstado.setValue(0);
//			lblEstado.setValue("No aprobada");
//		}
//		if (solicitud.getEst_solicitud().equals("E")) {// Ejecutada
//			pgsEstado.setValue(100);
//			lblEstado.setValue("Ejecutada");
//		}
//		dao_usuario dao = new dao_usuario();
//		modelo_usuario_bk usuario = new modelo_usuario_bk();
//		usuario = dao.obtenerUsuario(String.valueOf(solicitud.getId_user_1()), "", 2);
//		if (usuario != null) {
//			if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//				txtSolicitante.setText(usuario.getNom_usuario() + " " + usuario.getApe_usuario());
//			}
//		}
//		usuario = dao.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//		if (usuario != null) {
//			if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//				txtAprobador.setText(usuario.getNom_usuario() + " " + usuario.getApe_usuario());
//			}
//		}
//		usuario = dao.obtenerUsuario(String.valueOf(solicitud.getId_user_3()), "", 2);
//		if (usuario != null) {
//			if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//				txtAdministrador.setText(usuario.getNom_usuario() + " " + usuario.getApe_usuario());
//			}
//		}
//		if (solicitud.getId_user_2() == 0) {
//			rAprobador0.setVisible(false);
//			rAprobador1.setVisible(false);
//			rAprobador2.setVisible(false);
//			rAprobador3.setVisible(false);
//		} else {
//			rAprobador0.setVisible(true);
//			if (solicitud.getComentario_2().length() <= 0) {
//				rAprobador1.setVisible(false);
//			} else {
//				rAprobador1.setVisible(true);
//			}
//			rAprobador2.setVisible(true);
//			rAprobador3.setVisible(true);
//		}
//		if (solicitud.getId_user_3() == 0) {
//			rAdministrador0.setVisible(false);
//			rAdministrador1.setVisible(false);
//			rAdministrador2.setVisible(false);
//			rAdministrador3.setVisible(false);
//		} else {
//			rAdministrador0.setVisible(true);
//			rAdministrador1.setVisible(true);
//			rAdministrador2.setVisible(true);
//			rAdministrador3.setVisible(true);
//		}
//		if (solicitud.getFecha_1() != null) {
//			Date date = new Date(solicitud.getFecha_1().getTime());
//			dtxFechaSolicitud.setValue(date);
//		}
//		if (solicitud.getFecha_2() != null) {
//			Date date = new Date(solicitud.getFecha_2().getTime());
//			dtxFechaAprobacion.setValue(date);
//		}
//		if (solicitud.getFecha_3() != null) {
//			Date date = new Date(solicitud.getFecha_3().getTime());
//			dtxFechaEjecucion.setValue(date);
//		}
//	}
//
//	public List<modelo_tipo_solicitud> obtenerTipoSolicitudes() {
//		return listaTipoSolicitud;
//	}
//
//	public List<modelo_relacion_campo_mantenimiento> obtenerCampos() {
//		return listaCampo;
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Sessions.getCurrent().removeAttribute("solicitud");
//		Events.postEvent(new Event("onClose", zSeguimiento));
//	}
//
//}
