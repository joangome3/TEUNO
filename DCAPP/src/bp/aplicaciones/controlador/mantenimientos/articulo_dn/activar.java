package bp.aplicaciones.controlador.mantenimientos.articulo_dn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mensajes.Error;

@SuppressWarnings({ "serial", "deprecation" })
public class activar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zActivar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtComentario;

	long id = 0;
	long id_mantenimiento = 16;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	modelo_articulo_dn articulo = (modelo_articulo_dn) Sessions.getCurrent().getAttribute("articulo");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		// validarSesion();
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		// validarSesion();
		if (validarSiExisteSolicitudPendienteEjecucion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_15(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_articulo_dn dao = new dao_articulo_dn();
							modelo_articulo_dn articulo = new modelo_articulo_dn();
							articulo = activar.this.articulo;
							articulo.setUsu_modifica(user);
							articulo.setEst_articulo("AE");
							articulo.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							solicitud.setComentario_3(txtComentario.getText());
							solicitud.setId_user_3(id_user);
							solicitud.setEst_solicitud("E");
							solicitud.setFecha_3(fechas.obtenerTimestampDeDate(new Date()));
							solicitud.setUsu_modifica(user);
							solicitud.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							int tipo = 2;
							try {
								dao.activarDesactivarArticulo(articulo, solicitud, tipo);
								if (tipo == 2) {
									Messagebox.show(informativos.getMensaje_informativo_20(),
											informativos.getMensaje_informativo_15(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								} else {
									Messagebox.show(informativos.getMensaje_informativo_26(),
											informativos.getMensaje_informativo_15(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("articulo");
								Events.postEvent(new Event("onClose", zActivar));
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_2(), informativos.getMensaje_informativo_1(),
										Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("articulo");
		// validarSesion();
		Events.postEvent(new Event("onClose", zActivar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtComentario.setText("");
	}

}
