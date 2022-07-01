//package bp.aplicaciones.controlador.mantenimientos.solicitud;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.WrongValueException;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.EventListener;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Progressmeter;
//import org.zkoss.zul.Row;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Comboitem;
//import org.zkoss.zul.Datebox;
//import org.zkoss.zul.Image;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
//import bp.aplicaciones.extensiones.Fechas;
//import bp.aplicaciones.mantenimientos.DAO.dao_solicitud;
//import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//import bp.aplicaciones.mensajes.Error;
//import bp.aplicaciones.mensajes.Informativos;
//import bp.aplicaciones.mensajes.Validaciones;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class aprobar extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zAprobar;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtConfiguracion, txtComentario1, txtComentario2, txtSolicitante, txtAprobador;
//	@Wire
//	Combobox cmbTipoSolicitud, cmbCampo, cmbEstado;
//	@Wire
//	Progressmeter pgsEstado;
//	@Wire
//	Label lblEstado, lAprobador1, lAprobador2, lAprobador3;
//	@Wire
//	Row rCampo, rAprobador0, rAprobador1, rAprobador2, rAprobador3;
//	@Wire
//	Datebox dtxFechaSolicitud, dtxFechaAprobacion;
//	@Wire
//	Label lCampo;
//	@Wire
//	Image iCampo;
//
//	List<modelo_tipo_solicitud> listaTipoSolicitud = new ArrayList<modelo_tipo_solicitud>();
//	List<modelo_relacion_campo_mantenimiento> listaCampo = new ArrayList<modelo_relacion_campo_mantenimiento>();
//
//	long id = 0;
//	long id_mantenimiento = 0;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//	modelo_solicitud solicitud = (modelo_solicitud) Sessions.getCurrent().getAttribute("solicitud");
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
//		inicializarListas();
//		txtConfiguracion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			@SuppressWarnings("static-access")
//			public void onEvent(Event event) throws Exception {
//				txtConfiguracion.setText(validar.soloLetrasyNumeros(txtConfiguracion.getText()));
//			}
//		});
//		txtComentario1.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				txtComentario1.setText(txtComentario1.getText().toUpperCase());
//			}
//		});
//		txtComentario2.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				txtComentario2.setText(txtComentario2.getText().toUpperCase());
//			}
//		});
//		cargarDatos();
//	}
//
//	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
//		txtId.setText(String.valueOf(solicitud.getId_solicitud()));
//		cmbTipoSolicitud.setDisabled(true);
//		txtConfiguracion.setText(solicitud.getNom_mantenimiento());
//		txtComentario1.setText(solicitud.getComentario_1());
//		txtComentario1.setDisabled(true);
//		txtComentario2.setText(solicitud.getComentario_2());
//		txtComentario2.setDisabled(false);
//		txtAprobador.setText(nom_ape_user);
//		btnGrabar.setDisabled(false);
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
//		if (solicitud.getEst_solicitud().equals("P")) {
//			Comboitem item;
//			item = new Comboitem();
//			item.setLabel("REVISIÓN");
//			item.setValue("R");
//			cmbEstado.appendChild(item);
//			rAprobador0.setVisible(false);
//			rAprobador1.setVisible(false);
//			rAprobador2.setVisible(false);
//			rAprobador3.setVisible(false);
//		}
//		if (solicitud.getEst_solicitud().equals("R")) {
//			Comboitem item;
//			item = new Comboitem();
//			item.setLabel("APROBADO");
//			item.setValue("A");
//			cmbEstado.appendChild(item);
//			item = new Comboitem();
//			item.setLabel("NO SE APRUEBA");
//			item.setValue("N");
//			cmbEstado.appendChild(item);
//			if (solicitud.getId_tip_solicitud() == 1) {
//				item = new Comboitem();
//				item.setLabel("SE DEBE ACTUALIZAR INFORMACIÓN");
//				item.setValue("T");
//			}
//			cmbEstado.appendChild(item);
//			rAprobador0.setVisible(true);
//			rAprobador1.setVisible(true);
//			rAprobador2.setVisible(true);
//			rAprobador3.setVisible(true);
//		}
//		if (solicitud.getEst_solicitud().equals("P")) { // Abierta
//			pgsEstado.setValue(0);
//			lblEstado.setValue("Abierta");
//		}
//		if (solicitud.getEst_solicitud().equals("R")) { // En revision
//			pgsEstado.setValue(55);
//			lblEstado.setValue("En revisión");
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
//		if (solicitud.getFecha_1() != null) {
//			Date date = new Date(solicitud.getFecha_1().getTime());
//			dtxFechaSolicitud.setValue(date);
//		}
//		if (solicitud.getFecha_2() != null) {
//			Date date = new Date(solicitud.getFecha_2().getTime());
//			dtxFechaAprobacion.setValue(date);
//		} else {
//			Date fechaActual = new Date();
//			dtxFechaAprobacion.setValue(fechaActual);
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
//	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
//		listaTipoSolicitud = consultasABaseDeDatos.cargarTipoDeSolicitudes("", 1);
//		listaCampo = consultasABaseDeDatos.cargarRelacionMantenimientosCampos("",
//				String.valueOf(solicitud.getId_mantenimiento()), 2);
//		binder.loadComponent(cmbTipoSolicitud);
//		binder.loadComponent(cmbCampo);
//	}
//
//	public boolean validarSiEstaDentroDeGrupoPermitido() {
//		boolean esta_en_grupo_permitido = false;
//		if (id_perfil == 1 || id_perfil == 3 || id_perfil == 6) {
//			esta_en_grupo_permitido = true;
//		} else {
//			if (id_perfil == 5) {
//				if (solicitud.getId_tip_solicitud() != 7) {
//					if (solicitud.getId_mantenimiento() >= 11 && solicitud.getId_mantenimiento() <= 16) {
//						esta_en_grupo_permitido = true;
//					}
//				}
//			}
//		}
//		return esta_en_grupo_permitido;
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (validarSiEstaDentroDeGrupoPermitido() == false) {
//			Messagebox.show("No tiene permisos para poder realizar esta accion", ".:: Validar solicitud ::.",
//					Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
//		if (cmbEstado.getSelectedItem() == null) {
//			cmbEstado.setErrorMessage(validaciones.getMensaje_validacion_18());
//			return;
//		}
//		if (cmbEstado.getSelectedItem().getValue().toString().equals("A")
//				|| cmbEstado.getSelectedItem().getValue().toString().equals("N")
//				|| cmbEstado.getSelectedItem().getValue().toString().equals("T")) {
//			if (txtComentario2.getText().length() <= 0) {
//				txtComentario2.setErrorMessage(validaciones.getMensaje_validacion_11());
//				return;
//			}
//		}
//		if (validarEstadoDeSolicitud() == true) {
//			return;
//		}
//		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_15(),
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_solicitud dao = new dao_solicitud();
//							modelo_solicitud solicitud = new modelo_solicitud();
//							solicitud.setId_solicitud(Long.parseLong(txtId.getText().toString()));
//							solicitud.setId_tip_solicitud(
//									Long.valueOf(cmbTipoSolicitud.getSelectedItem().getValue().toString()));
//							solicitud.setNom_tipo_solicitud(cmbTipoSolicitud.getSelectedItem().getLabel().toString());
//							solicitud.setNom_mantenimiento(aprobar.this.solicitud.getNom_mantenimiento());
//							solicitud.setId_mantenimiento(aprobar.this.solicitud.getId_mantenimiento());
//							solicitud.setId_registro(aprobar.this.solicitud.getId_registro());
//							solicitud.setId_campo(aprobar.this.solicitud.getId_campo());
//							if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("2")) {
//								solicitud.setNom_campo(cmbCampo.getSelectedItem().getLabel().toString());
//							} else {
//								solicitud.setNom_campo("N/A");
//							}
//							solicitud.setComentario_1(aprobar.this.solicitud.getComentario_1());
//							solicitud.setComentario_2(txtComentario2.getText());
//							solicitud.setComentario_3("");
//							solicitud.setComentario_4("");
//							solicitud.setComentario_5("");
//							solicitud.setId_user_1(aprobar.this.solicitud.getId_user_1());
//							solicitud.setId_user_2(id_user);
//							solicitud.setId_user_3(aprobar.this.solicitud.getId_user_3());
//							solicitud.setFecha_1(aprobar.this.solicitud.getFecha_1());
//							if (cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//								solicitud.setEst_solicitud("R");
//							}
//							if (cmbEstado.getSelectedItem().getValue().toString().equals("A")) {// Si se aprueba
//								if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("1")) {
//									solicitud.setEst_solicitud("A"); // Aprobado
//								} else {
//									solicitud.setEst_solicitud("S"); // Pendiente ejecucion
//								}
//							}
//							if (cmbEstado.getSelectedItem().getValue().toString().equals("N")) {// Si no se aprueba
//								solicitud.setEst_solicitud("N"); // No aprobado
//							}
//							if (cmbEstado.getSelectedItem().getValue().toString().equals("T")) {// Si no se aprueba
//								solicitud.setEst_solicitud("T"); // Pendiente actualizacion
//								solicitud.setComentario_4(txtComentario2.getText());
//								solicitud.setId_user_4(id_user);
//								solicitud.setFecha_4(fechas.obtenerTimestampDeDate(new Date()));
//							}
//							solicitud.setUsu_modifica(user);
//							solicitud.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
//							solicitud.setFecha_2(fechas.obtenerTimestampDeDate(new Date()));
//							solicitud.setFecha_3(solicitud.getFecha_3());
//							String est_mantenimiento = aprobar.this.solicitud.getEst_solicitud();
//							long registro = aprobar.this.solicitud.getId_registro();
//							id_mantenimiento = aprobar.this.solicitud.getId_mantenimiento();
//							try {
//								dao.modificarSolicitud(solicitud, id_mantenimiento, registro, est_mantenimiento);
//								Messagebox.show(informativos.getMensaje_informativo_20(),
//										informativos.getMensaje_informativo_15(), Messagebox.OK,
//										Messagebox.EXCLAMATION);
//								Events.postEvent(new Event("onClose", zAprobar));
//							} catch (Exception e) {
//								Messagebox.show(error.getMensaje_error_4() + ", " + e.getLocalizedMessage(),
//										informativos.getMensaje_informativo_15(), Messagebox.OK,
//										Messagebox.EXCLAMATION);
//							}
//						}
//					}
//				});
//	}
//
//	public boolean validarEstadoDeSolicitud()
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		boolean estado_solicitud = false;
//		dao_solicitud dao = new dao_solicitud();
//		modelo_solicitud solicitud = new modelo_solicitud();
//		if (aprobar.this.solicitud.getId_tip_solicitud() == 7) { // Valido para opciones
//			solicitud = dao.obtenerSolicitudesxEstado("", solicitud.getId_mantenimiento(), solicitud.getId_registro(),
//					8);
//		} else { // Valido para mantenimientos
//			solicitud = dao.obtenerSolicitudesxEstado("", solicitud.getId_mantenimiento(), solicitud.getId_registro(),
//					7);
//		}
//		if (solicitud != null) {
//			String estado = solicitud.getEst_solicitud();
//			if (estado != null) {
//				if (estado.equals("R") && cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//					Messagebox.show("La solicitud ya se encuentra en estado de revisión", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("S") && cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de ejecución",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("S") && cmbEstado.getSelectedItem().getValue().toString().equals("A")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de ejecución",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("S") && cmbEstado.getSelectedItem().getValue().toString().equals("N")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de ejecución",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("S") && cmbEstado.getSelectedItem().getValue().toString().equals("T")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de ejecución",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("A") && cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//					Messagebox.show("La solicitud ya se encuentra en estado aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("A") && cmbEstado.getSelectedItem().getValue().toString().equals("A")) {
//					Messagebox.show("La solicitud ya se encuentra en estado aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("A") && cmbEstado.getSelectedItem().getValue().toString().equals("N")) {
//					Messagebox.show("La solicitud ya se encuentra en estado aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("A") && cmbEstado.getSelectedItem().getValue().toString().equals("T")) {
//					Messagebox.show("La solicitud ya se encuentra en estado aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("T") && cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de actualización",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("T") && cmbEstado.getSelectedItem().getValue().toString().equals("A")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de actualización",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("T") && cmbEstado.getSelectedItem().getValue().toString().equals("N")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de actualización",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("T") && cmbEstado.getSelectedItem().getValue().toString().equals("T")) {
//					Messagebox.show("La solicitud ya se encuentra en estado pendiente de actualización",
//							".:: Validar solicitud ::.", Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("N") && cmbEstado.getSelectedItem().getValue().toString().equals("R")) {
//					Messagebox.show("La solicitud ya se encuentra en estado no aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("N") && cmbEstado.getSelectedItem().getValue().toString().equals("A")) {
//					Messagebox.show("La solicitud ya se encuentra en estado no aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("N") && cmbEstado.getSelectedItem().getValue().toString().equals("N")) {
//					Messagebox.show("La solicitud ya se encuentra en estado no aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				} else if (estado.equals("N") && cmbEstado.getSelectedItem().getValue().toString().equals("T")) {
//					Messagebox.show("La solicitud ya se encuentra en estado no aprobada", ".:: Validar solicitud ::.",
//							Messagebox.OK, Messagebox.EXCLAMATION);
//					estado_solicitud = true;
//				}
//			}
//		}
//		return estado_solicitud;
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() {
//		Events.postEvent(new Event("onClose", zAprobar));
//	}
//
//	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
//		txtComentario1.setText("");
//	}
//
//}
