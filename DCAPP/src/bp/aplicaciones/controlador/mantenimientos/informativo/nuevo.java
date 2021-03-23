package bp.aplicaciones.controlador.mantenimientos.informativo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_informativo;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId;
	@Wire
	CKeditor cEditor;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;
	@Wire
	Checkbox chkSePublica;

	long id = 0;
	long id_mantenimiento = 17;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		obtenerId();
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_informativo dao = new dao_informativo();
		try {
			id = dao.obtenerNuevoId();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtId.setText(String.valueOf(id));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (dtxFechaInicio.getValue() == null) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage("Indique la fecha inicio.");
			return;
		}
		if (dtxFechaFin.getValue() == null) {
			dtxFechaFin.setFocus(true);
			dtxFechaFin.setErrorMessage("Indique la fecha fin.");
			return;
		}
		LocalDateTime localDateTime1 = null;
		LocalDateTime localDateTime2 = null;
		LocalDate localDate1 = dtxFechaInicio.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = dtxFechaFin.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year1 = localDate1.getYear();
		int year2 = localDate2.getYear();
		localDateTime1 = LocalDateTime.of(year1, dtxFechaInicio.getValue().getMonth() + 1,
				dtxFechaInicio.getValue().getDate(), dtxFechaInicio.getValue().getHours(),
				dtxFechaInicio.getValue().getMinutes());
		localDateTime2 = LocalDateTime.of(year2, dtxFechaFin.getValue().getMonth() + 1,
				dtxFechaFin.getValue().getDate(), dtxFechaFin.getValue().getHours(),
				dtxFechaFin.getValue().getMinutes());
		Date d1 = null;
		Date d2 = null;
		d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
		d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
		if (d2.before(d1)) {
			dtxFechaInicio.setFocus(true);
			dtxFechaInicio.setErrorMessage("La fecha fin debe ser mayor a la fecha inicio.");
			return;
		}
		if (cEditor.getValue().length() <= 0) {
			Messagebox.show("Debe registrar el mensaje a mostrar en el informativo", ".:: Nuevo informativo ::.",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Messagebox.show("Esta seguro de guardar el informativo?", ".:: Nuevo informativo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_informativo dao = new dao_informativo();
							modelo_informativo informativo = new modelo_informativo();
							informativo.setDescripcion(cEditor.getValue());
							if (chkSePublica.isChecked()) {
								informativo.setSe_publica("S");
							} else {
								informativo.setSe_publica("N");
							}
							java.util.Date date = null;
							Timestamp timestamp = null;
							date = dtxFechaInicio.getValue();
							timestamp = new Timestamp(date.getTime());
							informativo.setFec_inicio(timestamp);
							date = dtxFechaFin.getValue();
							timestamp = new Timestamp(date.getTime());
							informativo.setFec_fin(timestamp);
							informativo.setId_localidad(id_dc);
							informativo.setEst_informativo("AE");
							informativo.setUsu_ingresa(user);
							date = new Date();
							timestamp = new Timestamp(date.getTime());
							informativo.setFec_ingresa(timestamp);
							try {
								dao.insertarInformativo(informativo);
								Messagebox.show("El informativo se guardó correctamente.", ".:: Nuevo informativo ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el informativo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nuevo informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {

	}

}
