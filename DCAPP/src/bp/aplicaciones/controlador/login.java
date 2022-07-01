package bp.aplicaciones.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class login extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zLogin;
	@Wire
	Textbox txtUsuario, txtContrasena;
	@Wire
	Button btnIngresar;
	@Wire
	Timer cTimer;

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	@Listen("onTimer=#cTimer")
	public void iniciarSesion() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (txtUsuario.getValue() != null) {
			if (txtUsuario.getText().length() <= 0) {
				Messagebox.show("Ingrese el usuario.", ".:: Ingreso al sistema ::.", Messagebox.OK,
						Messagebox.EXCLAMATION);
				cTimer.stop();
				return;
			}
		} else {
			Messagebox.show("Ingrese el usuario.", ".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			cTimer.stop();
			return;
		}
		if (txtContrasena.getValue() != null) {
			if (txtContrasena.getText().length() <= 0) {
				Messagebox.show("Ingrese la contraseña.", ".:: Ingreso al sistema ::.", Messagebox.OK,
						Messagebox.EXCLAMATION);
				cTimer.stop();
				return;
			}
		} else {
			Messagebox.show("Ingrese la contraseña.", ".:: Ingreso al sistema ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			cTimer.stop();
			return;
		}
		String user = "", pass = "", nom_ape_user = "";
		long id_user = 0, id_perfil = 0, id_dc = 0;
		modelo_usuario usuario = new modelo_usuario();
		dao_usuario dao = new dao_usuario();
		user = txtUsuario.getText().toUpperCase();
		pass = txtContrasena.getText();
		usuario = dao.consultarUsuarios(0, 0, user, SH2.getSHA256(pass), 0, 6).get(0);
		if (usuario != null) {
			if (usuario.getEst_usuario().charAt(0) == 'I') {
				Messagebox.show("El usuario se encuentra inactivo. ¡Consulte al administrador del sistema!.",
						".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
				cTimer.stop();
				return;
			}
			if (usuario.getEst_usuario().charAt(0) == 'P') {
				Messagebox.show(
						"Existe una solicitud de aprobación para la creación de este usuario. ¡Consulte al administrador del sistema!.",
						".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
				cTimer.stop();
				return;
			}
			if (usuario.getEst_usuario().charAt(0) == 'A') {
				dao_perfil dao_perfil = new dao_perfil();
				String criterio = "";
				try {
					listaPerfil = dao_perfil.obtenerPerfiles(criterio, 4, usuario.getPerfil().getId_perfil());
				} catch (SQLException e) {
					Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
							".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
					cTimer.stop();
					return;
				}
				if (listaPerfil.size() == 0) {
					Messagebox.show(
							"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
							".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
					cTimer.stop();
					return;
				}
				if (listaPerfil.size() > 1) {
					Messagebox.show(
							"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
							".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
					cTimer.stop();
					return;
				}
				if (listaPerfil.get(0).getEst_perfil().equals("I")) {
					Messagebox.show(
							"El perfil con los permisos asignado al usuario se encuentra inactivo. ¡Consulte al administrador del sistema!.",
							".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
					cTimer.stop();
					return;
				} else {
					if (usuario.getCam_password().equals("S")) {
						Sessions.getCurrent().setAttribute("usuario", usuario);
						Component comp = Executions.createComponents("/mantenimientos/usuario/cambiar_contrasena.zul",
								null, null);
						if (comp instanceof Window) {
							((Window) comp).doModal();
							comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
								@Override
								public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
									limpiar();
									cTimer.stop();
								}
							});
						}
					} else {
						id_user = usuario.getId_usuario();
						id_perfil = usuario.getPerfil().getId_perfil();
						user = usuario.getUse_usuario();
						id_dc = usuario.getLocalidad().getId_localidad();
						nom_ape_user = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
						Sessions.getCurrent().setAttribute("id_user", id_user);
						Sessions.getCurrent().setAttribute("id_perfil", id_perfil);
						Sessions.getCurrent().setAttribute("user", user);
						Sessions.getCurrent().setAttribute("id_dc", id_dc);
						Sessions.getCurrent().setAttribute("nom_ape_user", nom_ape_user);
						limpiar();
						cTimer.stop();
						Executions.sendRedirect("/dashboard.zul");
					}
				}
			}
		} else {
			Messagebox.show("Usuario o contraseña incorrectos.", ".:: Ingreso al sistema ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			cTimer.stop();
			return;
		}

	}

	public void limpiar() {
		txtUsuario.setText("");
		txtContrasena.setText("");
	}

}
