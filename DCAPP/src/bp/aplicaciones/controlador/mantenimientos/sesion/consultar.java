package bp.aplicaciones.controlador.mantenimientos.sesion;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_sesion;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_sesion;


@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxSesiones;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_sesion> listaSesion = new ArrayList<modelo_sesion>();
	List<modelo_perfil> listaPerfilUsuario = new ArrayList<modelo_perfil>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfilUsuario();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarSesiones();
			txtBuscar.setDisabled(false);
			lbxSesiones.setEmptyMessage("!No existen datos que mostrar¡.");
		} else {
			txtBuscar.setDisabled(true);
			lbxSesiones.setEmptyMessage("!No tiene permiso para ver los registros¡.");
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
	}

	public void cargarSesiones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_sesion dao = new dao_sesion();
		String criterio = "";
		try {
			listaSesion = dao.obtenerSesiones(criterio, "", String.valueOf(id_dc), 1);
			dibujarListaConsulta();
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las sesiones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar sesion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarPerfilUsuario() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfilUsuario = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisos() {
		if (listaPerfilUsuario.size() == 0) {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. Â¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaPerfilUsuario.size() > 1) {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. Â¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaPerfilUsuario.get(0).getConsultar().equals("S")) {
			consultar = "S";
		} else {
			consultar = "N";
		}
		if (listaPerfilUsuario.get(0).getInsertar().equals("S")) {
			insertar = "S";
		} else {
			insertar = "N";
		}
		if (listaPerfilUsuario.get(0).getModificar().equals("S")) {
			modificar = "S";
		} else {
			modificar = "N";
		}
		if (listaPerfilUsuario.get(0).getRelacionar().equals("S")) {
			relacionar = "S";
		} else {
			relacionar = "N";
		}
		if (listaPerfilUsuario.get(0).getDesactivar().equals("S")) {
			desactivar = "S";
		} else {
			desactivar = "N";
		}
		if (listaPerfilUsuario.get(0).getEliminar().equals("S")) {
			eliminar = "S";
		} else {
			eliminar = "N";
		}
		if (listaPerfilUsuario.get(0).getSolicitar().equals("S")) {
			solicitar = "S";
		} else {
			solicitar = "N";
		}
		if (listaPerfilUsuario.get(0).getRevisar().equals("S")) {
			revisar = "S";
		} else {
			revisar = "N";
		}
		if (listaPerfilUsuario.get(0).getAprobar().equals("S")) {
			aprobar = "S";
		} else {
			aprobar = "N";
		}
		if (listaPerfilUsuario.get(0).getEjecutar().equals("S")) {
			ejecutar = "S";
		} else {
			ejecutar = "N";
		}
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaSesion.size(); i++) {
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaSesion.get(i).getId_sesion()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* NOMBRE */
			lCell = new Listcell();
			lCell.setLabel(listaSesion.get(i).getNom_usuario());
			lItem.appendChild(lCell);
			/* SESION */
			lCell = new Listcell();
			lCell.setLabel(listaSesion.get(i).getCod_sesion());
			lItem.appendChild(lCell);
			/* FECHA */
			lCell = new Listcell();
			lCell.setLabel(listaSesion.get(i).toStringFechaSesion());
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ACCIONES */
			Button btnDesactivar;
			/* DESACTIVAR */
			lCell = new Listcell();
			btnDesactivar = new Button();
			btnDesactivar.setAutodisable("self");
			if (desactivar.equals("S")) {
				btnDesactivar.setDisabled(false);
			} else {
				btnDesactivar.setDisabled(true);
			}
			if (listaSesion.get(i).getEst_sesion().equals("I")) {
				btnDesactivar.setImage("/img/botones/ButtonDeactivate3.png");
				btnDesactivar.setTooltiptext("Activar");
			} else {
				btnDesactivar.setImage("/img/botones/ButtonDeactivate1.png");
				btnDesactivar.setTooltiptext("Desactivar");
			}
			btnDesactivar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnDesactivar.getParent().getParent();
					if (id_user == listaSesion.get(lItem.getIndex()).getId_usuario_1()) {
						Messagebox.show("No puede desactivar su propia sesion.", ".:: Desactivar sesion ::.",
								Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
					onClick$btnDesactivar(lItem.getIndex());
				}
			});
			lCell.appendChild(btnDesactivar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxSesiones.appendChild(lItem);
		}
	}

	public void borrarListaConsulta() {
		lbxSesiones.clearSelection();
		Listitem lItem;
		for (int i = lbxSesiones.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxSesiones.getItemAtIndex(i);
			lbxSesiones.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxSesiones);
	}

	public List<modelo_sesion> obtenerSesiones() {
		return listaSesion;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			String criterio = txtBuscar.getText();
			buscarSesiones(criterio, 3);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			String criterio = txtBuscar.getText();
			buscarSesiones(criterio, 3);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarSesiones(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_sesion dao = new dao_sesion();
		if (txtBuscar.getText().length() <= 0) {
			try {
				listaSesion = dao.obtenerSesiones(criterio, String.valueOf(id_user), String.valueOf(id_dc), tipo);
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			try {
				listaSesion = dao.obtenerSesiones(criterio, String.valueOf(id_user), String.valueOf(id_dc), tipo);
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btnDesactivar(int indice) {
		Messagebox.show("Esta seguro de desactivar la sesion?", ".:: Desactivar sesion ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_sesion dao = new dao_sesion();
							modelo_sesion sesion = new modelo_sesion();
							sesion.setId_usuario_1(listaSesion.get(indice).getId_usuario_1());
							sesion.setId_usuario_2(id_user);
							sesion.setId_localidad(id_dc);
							sesion.setEst_sesion("I");
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							sesion.setFec_sesion_2(timestamp);
							try {
								dao.modificarSesion(sesion);
								Messagebox.show("La sesion se desactivo correctamente.", ".:: Desactivar sesion ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								buscarSesiones(String.valueOf(listaSesion.get(indice).getId_sesion()), 1);
							} catch (Exception e) {
								Messagebox.show(
										"Error al desactivar la sesion. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Desactivar sesion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
		borrarListaConsulta();
	}

}
