package bp.aplicaciones.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_mes;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_mes;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

@SuppressWarnings({ "serial", "deprecation" })
public class panel2 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zPanel;
	@Wire
	Panel cpPanel1, cpPanel2, cpPanel3, cpPanel4, cpPanel5, cpPanel6, cpPanel7, cpPanel8;
	@Wire
	Combobox cmbUsuario, cmbMes;
	@Wire
	Include iPanel1, iPanel2, iPanel3, iPanel4, iPanel5, iPanel6, iPanel7, iPanel8;
	@Wire
	Button btnRefrescar;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_mes> listaMeses = new ArrayList<modelo_mes>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarParametros1();
		cargarUsuarios();
		cargarMeses();
		// cpBarra1.setTitle("TOTAL DE CHEQUEOS PERIODICOS POR DIA");
		cpPanel2.setTitle("PORCENTAJE DE CUMPLIMIENTO DE TAREAS DURANTE EL MES EN CURSO");
		cpPanel3.setTitle("CANTIDAD DE TAREAS CUMPLIDAS E INCUMPLIDAS DURANTE EL MES EN CURSO POR SERVICIO");
		cpPanel4.setTitle("CANTIDAD DE ACTIVIDADES PROGRAMADAS VENCIDAS Y PROXIMAS A VENCER POR SERVICIO");
		cpPanel5.setTitle("CANTIDAD DE ACTIVIDADES PROGRAMADAS A REALIZAR EN EL DIA ACTUAL POR SERVICIO");
		cpPanel6.setTitle("CANTIDAD DE TAREAS ABIERTAS EN LOG DE EVENTOS");
		cpPanel7.setTitle("TIEMPO INVERTIDO EN TAREAS DE LOG DE EVENTOS DURANTE EL TURNO ACTUAL");
		cpPanel8.setTitle("DETALLE DE ACTIVIDADES PROGRAMADAS A REALIZAR EN EL DIA ACTUAL POR SERVICIO");
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getId_perfil_revisor_bitacora() != id_perfil) {
				validarUsuario();
				validarMesActual();
				cmbUsuario.setDisabled(true);
				cmbMes.setDisabled(true);
			} else {
				validarMesActual();
				cmbUsuario.setDisabled(false);
				cmbMes.setDisabled(false);
			}
		} else {
			validarUsuario();
			validarMesActual();
			cmbUsuario.setDisabled(true);
			cmbMes.setDisabled(true);
		}
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_mes> obtenerMeses() {
		return listaMeses;
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

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		listaUsuario = dao.consultarUsuarios(id_dc, 0, "", "", 0, 2);
		binder.loadComponent(cmbUsuario);
	}

	public void cargarMeses() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mes dao = new dao_mes();
		String criterio = "";
		try {
			listaMeses = dao.obtenerMeses(criterio);
			binder.loadComponent(cmbMes);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los meses. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar mes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void validarUsuario() {
		for (int i = 0; i < listaUsuario.size(); i++) {
			if (listaUsuario.get(i).getUse_usuario().equals(user)) {
				cmbUsuario.setText(listaUsuario.get(i).verNombreCompleto());
				i = listaUsuario.size() + 1;
			}
		}
	}

	public void validarMesActual() {
		Fechas extensionFechas = new Fechas();
		int mesActual = extensionFechas.obtenerEnteroDelMesAPartirUnaFecha(new Date());
		Iterator<modelo_mes> it = listaMeses.iterator();
		while (it.hasNext()) {
			modelo_mes mes = it.next();
			if (mes.getId_mes() == mesActual) {
				cmbMes.setText(mes.getDescripcion());
				break;
			}
		}
	}

	@Listen("onSelect=#cmbUsuario")
	public void onSelect$cmbUsuario() {
		actualizarPanelesDelDashboard();
	}

	@Listen("onSelect=#cmbMes")
	public void onSelect$cmbMes() {
		actualizarPanelesDelDashboard();

	}

	public void actualizarPanelesDelDashboard() {
		String use_usuario = null;
		if (cmbUsuario.getSelectedItem() != null) {
			Iterator<modelo_usuario> it = listaUsuario.iterator();
			while (it.hasNext()) {
				modelo_usuario usuario = it.next();
				if (usuario.getId_usuario() == Long.valueOf(cmbUsuario.getSelectedItem().getValue().toString())) {
					use_usuario = usuario.getUse_usuario();
					break;
				}
			}
		}
		String id_mes = null;
		if (cmbMes.getSelectedItem() != null) {
			id_mes = cmbMes.getSelectedItem().getValue().toString();
		}
		Sessions.getCurrent().setAttribute("uPanel", use_usuario);
		Sessions.getCurrent().setAttribute("mPanel", id_mes);
		iPanel2.invalidate();
		iPanel3.invalidate();
		iPanel4.invalidate();
		iPanel5.invalidate();
		iPanel6.invalidate();
		iPanel7.invalidate();
		iPanel8.invalidate();
		binder.loadComponent(iPanel2);
		binder.loadComponent(iPanel3);
		binder.loadComponent(iPanel4);
		binder.loadComponent(iPanel5);
		binder.loadComponent(iPanel6);
		binder.loadComponent(iPanel7);
		binder.loadComponent(iPanel8);
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		actualizarPanelesDelDashboard();
	}

}
