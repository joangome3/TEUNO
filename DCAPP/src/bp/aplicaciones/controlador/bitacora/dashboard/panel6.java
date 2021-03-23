package bp.aplicaciones.controlador.bitacora.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;

@SuppressWarnings({ "serial", "deprecation" })
public class panel6 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zLabel;
	@Wire
	Label lTiempoTrabajado;
	@Wire
	Timer timer;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String uPanel = (String) Sessions.getCurrent().getAttribute("uPanel");

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_turno> listaTurno2 = new ArrayList<modelo_turno>();

	Date fecha_1 = null;
	Date fecha_2 = null;
	Date fecha_3 = null;
	Date fecha_4 = null;
	Date fecha_5 = null;

	Date fecha_inicio = null;
	Date fecha_fin = null;

	long id_turno = 0;

	boolean es_turno_extendido = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarParametros1();
		cargarTurnos2();
		validarTurno();
		cargarTiempoTrabajado();
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_turno> obtenerTurnos2() {
		return listaTurno2;
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarParametros1() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros1 = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTurnos2() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_turno dao = new dao_turno();
		String criterio = "A";
		try {
			listaTurno2 = dao.obtenerTurnos(criterio);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los turnos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar turnos ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void validarTurno() {
		LocalDateTime localDateTime1 = null;
		LocalDateTime localDateTime2 = null;
		LocalDateTime localDateTime3 = null;
		LocalDateTime localDateTime4 = null;
		LocalDateTime localDateTime5 = null;
		LocalDate localDate1 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate3 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate4 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate5 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year1 = localDate1.getYear();
		int year2 = localDate2.getYear();
		int year3 = localDate3.getYear();
		int year4 = localDate4.getYear();
		int year5 = localDate5.getYear();
		localDateTime1 = LocalDateTime.of(year1, new Date().getMonth() + 1, new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), new Date().getSeconds());
		Date d1 = null; // Fecha actual
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;
		Date d5 = null;
		d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
		fecha_1 = d1;
		for (int i = 0; i < listaTurno2.size(); i++) {
			if (listaTurno2.get(i).getEs_extendido().equals("N")) {
				es_turno_extendido = false;
				localDateTime2 = LocalDateTime.of(year2, new Date().getMonth() + 1, new Date().getDate(),
						listaTurno2.get(i).getHora_inicio().getHours(),
						listaTurno2.get(i).getHora_inicio().getMinutes(),
						listaTurno2.get(i).getHora_inicio().getSeconds());
				d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
				fecha_inicio = d2;
				localDateTime3 = LocalDateTime.of(year3, new Date().getMonth() + 1, new Date().getDate(),
						listaTurno2.get(i).getHora_fin().getHours(), listaTurno2.get(i).getHora_fin().getMinutes(),
						listaTurno2.get(i).getHora_fin().getSeconds());
				d3 = Date.from(localDateTime3.atZone(ZoneId.systemDefault()).toInstant());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
					id_turno = listaTurno2.get(i).getId_turno();
					i = listaTurno2.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				localDateTime2 = LocalDateTime.of(year2, new Date().getMonth() + 1, new Date().getDate(),
						listaTurno2.get(i).getHora_inicio().getHours(),
						listaTurno2.get(i).getHora_inicio().getMinutes(),
						listaTurno2.get(i).getHora_inicio().getSeconds());
				d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
				fecha_2 = d2;
				fecha_inicio = d2;
				localDateTime3 = LocalDateTime.of(year3, new Date().getMonth() + 1, new Date().getDate(), 23, 59, 59);
				d3 = Date.from(localDateTime3.atZone(ZoneId.systemDefault()).toInstant());
				fecha_3 = d3;
				localDateTime4 = LocalDateTime.of(year4, new Date().getMonth() + 1, new Date().getDate(), 0, 0, 0);
				d4 = Date.from(localDateTime4.atZone(ZoneId.systemDefault()).toInstant());
				fecha_4 = d4;
				localDateTime5 = LocalDateTime.of(year5, new Date().getMonth() + 1, new Date().getDate(),
						listaTurno2.get(i).getHora_fin().getHours(), listaTurno2.get(i).getHora_fin().getMinutes(),
						listaTurno2.get(i).getHora_fin().getSeconds());
				d5 = Date.from(localDateTime5.atZone(ZoneId.systemDefault()).toInstant());
				fecha_5 = d5;
				fecha_fin = d5;
				if ((d1.before(d3) && d1.after(d2)) || (d1.before(d5) && d1.after(d4))) {
					id_turno = listaTurno2.get(i).getId_turno();
					i = listaTurno2.size() + 1;
				}
			}
		}
	}

	public void cargarTiempoTrabajado()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String tiempo_trabajado = "";
		dao_bitacora dao = new dao_bitacora();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String f1 = "", f2 = "";
		f1 = sdf.format(fecha_inicio);
		f2 = sdf.format(fecha_fin);
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getId_perfil_revisor_bitacora() != id_perfil) {
				tiempo_trabajado = dao.obtenerTiempoTrabajado(String.valueOf(id_dc), f1, f2, String.valueOf(id_turno),
						user, 1);
			} else {
				if (uPanel == null) {
					tiempo_trabajado = dao.obtenerTiempoTrabajado(String.valueOf(id_dc), f1, f2,
							String.valueOf(id_turno), uPanel, 2);
				} else {
					tiempo_trabajado = dao.obtenerTiempoTrabajado(String.valueOf(id_dc), f1, f2,
							String.valueOf(id_turno), uPanel, 1);
				}
			}
		} else {
			tiempo_trabajado = dao.obtenerTiempoTrabajado(String.valueOf(id_dc), f1, f2, String.valueOf(id_turno), user,
					1);
		}
		if (tiempo_trabajado != null) {
			if (!tiempo_trabajado.equals("")) {
				lTiempoTrabajado.setValue(tiempo_trabajado);
			}
		}

	}

}
