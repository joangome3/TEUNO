package bp.aplicaciones.controlador.mantenimientos.empresa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import bp.aplicaciones.controlador.mantenimientos.empresa.activar;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

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
	long id_mantenimiento = 8;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_empresa empresa = (modelo_empresa) Sessions.getCurrent().getAttribute("empresa");

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
		cargarDatos();
	}

	public void cargarDatos() {
	}
	
	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, empresa.getId_empresa(),
				7);
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
		if (validarSiExisteSolicitudPendienteEjecucion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			txtComentario.setFocus(true);
			return;
		}
		Messagebox.show("Esta seguro de activar el registro?", ".:: Activar empresa ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_empresa dao = new dao_empresa();
							modelo_empresa empresa = new modelo_empresa();
							empresa.setId_empresa(activar.this.empresa.getId_empresa());
							empresa.setNom_empresa(activar.this.empresa.getNom_empresa());
							empresa.setDes_empresa(activar.this.empresa.getDes_empresa());
							empresa.setEst_empresa("AE");
							empresa.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							empresa.setFec_modifica(timestamp);
							solicitud.setComentario_3(txtComentario.getText());
							solicitud.setId_user_3(id_user);
							solicitud.setEst_solicitud("E");
							solicitud.setFecha_3(timestamp);
							solicitud.setUsu_modifica(user);
							solicitud.setFec_modifica(timestamp);
							int tipo = 2;
							try {
								dao.modificarEmpresa(empresa, solicitud, tipo);
								if (tipo == 2) {
									Messagebox.show("La empresa se activó correctamente.",
											".:: Activar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Activar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("empresa");
								Events.postEvent(new Event("onClose", zActivar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el registro. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Activar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zActivar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
	}

}
