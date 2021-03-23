package bp.aplicaciones.controlador.mantenimientos.categoria;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class desactivar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zDesactivar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtComentario;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id = 0;
	long id_mantenimiento = 5;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_categoria categoria = (modelo_categoria) Sessions.getCurrent().getAttribute("categoria");

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
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, categoria.getId_categoria(),
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
			txtComentario.setFocus(true);
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		Messagebox.show("Esta seguro de desactivar el registro?", ".:: Desactivar localidad ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_categoria dao = new dao_categoria();
							modelo_categoria categoria = new modelo_categoria();
							categoria.setId_categoria(desactivar.this.categoria.getId_categoria());
							categoria.setNom_categoria(desactivar.this.categoria.getNom_categoria());
							categoria.setDes_categoria(desactivar.this.categoria.getDes_categoria());
							categoria.setId_localidad(desactivar.this.categoria.getId_localidad());
							categoria.setMos_marca(desactivar.this.categoria.getMos_marca());
							categoria.setMos_modelo(desactivar.this.categoria.getMos_modelo());
							categoria.setMos_serie(desactivar.this.categoria.getMos_serie());
							categoria.setMos_cod_activo(desactivar.this.categoria.getMos_cod_activo());
							categoria.setEst_categoria("IE");
							categoria.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							categoria.setFec_modifica(timestamp);
							solicitud.setComentario_3(txtComentario.getText());
							solicitud.setId_user_3(id_user);
							solicitud.setEst_solicitud("E");
							solicitud.setFecha_3(timestamp);
							solicitud.setUsu_modifica(user);
							solicitud.setFec_modifica(timestamp);
							int tipo = 2;
							try {
								dao.modificarCategoria(categoria, solicitud, tipo);
								if (tipo == 2) {
									Messagebox.show("La categoria se desactivů correctamente.",
											".:: Desactivar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Desactivar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("categoria");
								Events.postEvent(new Event("onClose", zDesactivar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el registro. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Desactivar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zDesactivar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {

	}

}
