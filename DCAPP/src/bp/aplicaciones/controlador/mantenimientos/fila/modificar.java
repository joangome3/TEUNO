package bp.aplicaciones.controlador.mantenimientos.fila;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_fila;
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtNombre;
	@Wire
	Label lNombre;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	long id = 0;
	long id_mantenimiento = 22;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_fila fila = (modelo_fila) Sessions.getCurrent().getAttribute("fila");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase().trim());
			}
		});
		cargarDatos();
	}

	public void cargarDatos() {
		txtId.setText(String.valueOf(fila.getId_fila()));
		txtNombre.setText(fila.getNom_fila());
		lNombre.setValue(txtNombre.getText().length() + "/100");
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_fila dao = new dao_fila();
		if (dao.consultarFilas(fila.getId_fila(), id_dc, txtNombre.getText(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		if (txtNombre.getText().length() <= 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("Ingrese el nombre.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right",
					2000, true);
			return;
		}
		dao_fila dao = new dao_fila();
		if (dao.consultarFilas(fila.getId_fila(), id_dc, txtNombre.getText(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		Messagebox.show("Esta seguro de actualizar la fila?", ".:: Modificar fila ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_fila dao = new dao_fila();
							modelo_fila fila = new modelo_fila();
							fila = modificar.this.fila;
							fila.setNom_fila(txtNombre.getText());
							fila.setEst_fila("AE");
							fila.setUsu_modifica(user);
							fila.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							if (obtenerLocalidad() != null) {
								fila.setLocalidad(obtenerLocalidad());
							} else {
								Clients.showNotification(
										"Se presentó un error al actualizar la fila, no se pudo encotrar la localidad.",
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
								return;
							}
							try {
								dao.actualizarFila(fila);
								Clients.showNotification("La fila se actualizó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "top_right", 4000, true);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al actualizar la fila. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	public modelo_localidad obtenerLocalidad() {
		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
		modelo_localidad localidad = new modelo_localidad();
		lista = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 5);
		if (lista.size() > 0) {
			localidad = lista.get(0);
		}
		return localidad;
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		lNombre.setValue("0/100");
		Events.postEvent(new Event("onClose", zModificar));
	}

}
