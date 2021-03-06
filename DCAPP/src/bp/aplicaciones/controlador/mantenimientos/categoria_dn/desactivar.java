package bp.aplicaciones.controlador.mantenimientos.categoria_dn;

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

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria_dn;
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
	long id_mantenimiento = 14;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_categoria_dn categoria = (modelo_categoria_dn) Sessions.getCurrent().getAttribute("categoria");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

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

	public void validarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {
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
		validarSesion();
		if (validarSiExisteSolicitudPendienteEjecucion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Messagebox.show("Esta seguro de desactivar el registro?", ".:: Desactivar localidad ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_categoria_dn dao = new dao_categoria_dn();
							modelo_categoria_dn categoria_dn = new modelo_categoria_dn();
							categoria_dn.setId_categoria(desactivar.this.categoria.getId_categoria());
							categoria_dn.setNom_categoria(desactivar.this.categoria.getNom_categoria());
							categoria_dn.setDes_categoria(desactivar.this.categoria.getDes_categoria());
							categoria_dn.setId_localidad(desactivar.this.categoria.getId_localidad());
							categoria_dn.setMos_capacidad(desactivar.this.categoria.getMos_capacidad());
							categoria_dn.setMos_fec_inicio(desactivar.this.categoria.getMos_fec_inicio());
							categoria_dn.setMos_fec_fin(desactivar.this.categoria.getMos_fec_fin());
							categoria_dn.setMos_tip_respaldo(desactivar.this.categoria.getMos_tip_respaldo());
							categoria_dn.setMos_id_contenedor(desactivar.this.categoria.getMos_id_contenedor());
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
									Messagebox.show("La categoria se desactiv? correctamente.",
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
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("categoria");
		validarSesion();
		Events.postEvent(new Event("onClose", zDesactivar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
	}

}
