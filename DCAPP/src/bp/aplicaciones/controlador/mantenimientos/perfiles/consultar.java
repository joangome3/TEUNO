package bp.aplicaciones.controlador.mantenimientos.perfiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

@SuppressWarnings({ "serial", "deprecation" })
public class consultar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Button btnNuevo, btnRefrescar;
	@Wire
	Textbox txtBuscar;
	@Wire
	Listbox lbxPerfiles;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_perfil> listaPerfilUsuario = new ArrayList<modelo_perfil>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfilUsuario();
		inicializarPermisos();
		if (consultar.equals("S")) {
			cargarPerfiles();
			txtBuscar.setDisabled(false);
			lbxPerfiles.setEmptyMessage("!No existen datos que mostrar¡.");
		} else {
			txtBuscar.setDisabled(true);
			lbxPerfiles.setEmptyMessage("!No tiene permiso para ver los registros¡.");
		}
		if (insertar.equals("S")) {
			btnNuevo.setDisabled(false);
		} else {
			btnNuevo.setDisabled(true);
		}
		txtBuscar.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtBuscar.setText(validar.soloLetrasyNumeros(txtBuscar.getText()));
			}
		});
	}

	public void cargarPerfiles() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 1, 0);
			dibujarListaConsulta();
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaPerfilUsuario.size() > 1) {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
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
		for (int i = 0; i < listaPerfil.size(); i++) {
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaPerfil.get(i).getId_perfil()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* NOMBRE */
			lCell = new Listcell();
			lCell.setLabel(listaPerfil.get(i).getNom_perfil());
			lItem.appendChild(lCell);
			/* CONSULTAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getConsultar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getConsultar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* INSERTAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getInsertar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getInsertar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* MODIFICAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getModificar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getModificar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* RELACIONAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getRelacionar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getRelacionar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* DESACTIVAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getDesactivar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getDesactivar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* ELIMINAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getEliminar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getEliminar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* SOLICITAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getSolicitar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getSolicitar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* REVISAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getRevisar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getRevisar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* APROBAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getAprobar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getAprobar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* DENEGAR */
			lCell = new Listcell();
			if (listaPerfil.get(i).getEjecutar().equals("S")) {
				lCell.setImage("/img/botones/ButtonOK.png");
			} else {
				lCell.setImage("/img/botones/ButtonError.png");
			}
			lCell.setLabel(listaPerfil.get(i).getEjecutar());
			lCell.setStyle("text-align: center !important; color: transparent;");
			lItem.appendChild(lCell);
			/* ACCIONES */
			Button btnModificar;
			Button btnDesactivar;
			Button btnEliminar;
			Button btnRelacionar;
			/* MODIFICAR */
			lCell = new Listcell();
			btnModificar = new Button();
			btnModificar.setImage("/img/botones/ButtonUpdate.png");
			btnModificar.setTooltiptext("Modificar");
			btnModificar.setAutodisable("self");
			if (modificar.equals("S")) {
				btnModificar.setDisabled(false);
			} else {
				btnModificar.setDisabled(true);
			}
			btnModificar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnModificar.getParent().getParent();
					onClick$btnModificar(lItem.getIndex());
				}
			});
			lCell.appendChild(btnModificar);
			/* DESACTIVAR */
			btnDesactivar = new Button();
			btnDesactivar.setAutodisable("self");
			if (desactivar.equals("S")) {
				btnDesactivar.setDisabled(false);
			} else {
				btnDesactivar.setDisabled(true);
			}
			if (listaPerfil.get(i).getEst_perfil().equals("I")) {
				btnDesactivar.setImage("/img/botones/ButtonDeactivate1.png");
				btnDesactivar.setTooltiptext("Activar");
			} else {
				btnDesactivar.setImage("/img/botones/ButtonDeactivate3.png");
				btnDesactivar.setTooltiptext("Desactivar");
			}
			btnDesactivar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnDesactivar.getParent().getParent();
					onClick$btnDesactivar(lItem.getIndex());
				}
			});
			lCell.appendChild(btnDesactivar);
			/* RELACIONAR */
			btnRelacionar = new Button();
			btnRelacionar.setImage("/img/botones/ButtonApprove.png");
			btnRelacionar.setTooltiptext("Relacionar Perfil");
			btnRelacionar.setAutodisable("self");
			if (relacionar.equals("S")) {
				btnRelacionar.setDisabled(false);
			} else {
				btnRelacionar.setDisabled(true);
			}
			btnRelacionar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnRelacionar.getParent().getParent();
					onClick$btnRelacion(lItem.getIndex());
				}
			});
			lCell.appendChild(btnRelacionar);
			/* ELIMINAR */
			btnEliminar = new Button();
			btnEliminar.setImage("/img/botones/ButtonDelete.png");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			if (eliminar.equals("S")) {
				btnEliminar.setDisabled(false);
			} else {
				btnEliminar.setDisabled(true);
			}
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					onClick$btnEliminar(lItem.getIndex());
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxPerfiles.appendChild(lItem);
		}
	}

	public void borrarListaConsulta() {
		lbxPerfiles.clearSelection();
		Listitem lItem;
		for (int i = lbxPerfiles.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxPerfiles.getItemAtIndex(i);
			lbxPerfiles.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxPerfiles);
	}

	public List<modelo_perfil> obtenerPerfiles() {
		return listaPerfil;
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar() {
		try {
			String criterio = txtBuscar.getText();
			buscarPerfiles(criterio, 1, 0);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Listen("onClick=#btnRefrescar")
	public void onClick$btnRefrescar() {
		try {
			String criterio = txtBuscar.getText();
			buscarPerfiles(criterio, 1, 0);
		} catch (Exception e) {
			Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void buscarPerfiles(String criterio, int tipo, long id) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		if (txtBuscar.getText().length() <= 0) {
			try {
				listaPerfil = dao.obtenerPerfiles(criterio, tipo, id);
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
		if (!txtBuscar.getValue().equals("")) {
			try {
				listaPerfil = dao.obtenerPerfiles(criterio, tipo, id);
				borrarListaConsulta();
				dibujarListaConsulta();
			} catch (SQLException e) {
				Messagebox.show("Error al buscar en los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
						".:: Buscar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	@Listen("onClick=#btnNuevo")
	public void onClick$btnNuevo() {
		Component comp = Executions.createComponents("/mantenimientos/perfiles/nuevo.zul", null, null);
		if (comp instanceof Window) {
			((Window) comp).doModal();
			comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					limpiarCampos();
					if (consultar.equals("S")) {
						cargarPerfiles();
					}
				}
			});
		}
	}

	public void onClick$btnModificar(int indice) {
		if (listaPerfil.get(indice).getEst_perfil().equals("I")) {
			Messagebox.show("El perfil se encuentra inactivo, debe estar activo para poder modificarlo.",
					".:: Modificar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Sessions.getCurrent().setAttribute("perfil", listaPerfil.get(indice));
		Component comp = Executions.createComponents("/mantenimientos/perfiles/modificar.zul", null, null);
		if (comp instanceof Window) {
			((Window) comp).doModal();
			comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					limpiarCampos();
					buscarPerfiles("", 4, listaPerfil.get(indice).getId_perfil());
				}
			});
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btnDesactivar(int indice) {
		if (listaPerfil.get(indice).getEst_perfil().equals("A")) {
			Messagebox.show("Esta seguro de desactivar el perfil?", ".:: Desactivar perfil ::.",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								modelo_perfil perfil = new modelo_perfil();
								perfil.setId_perfil(listaPerfil.get(indice).getId_perfil());
								perfil.setEst_perfil("I");
								perfil.setUsu_modifica(user);
								java.util.Date date = new Date();
								Timestamp timestamp = new Timestamp(date.getTime());
								perfil.setFec_modifica(timestamp);
								dao_perfil dao = new dao_perfil();
								try {
									dao.activarDesactivarPerfil(perfil);
									Messagebox.show("El perfil se desactivo correctamente.",
											".:: Desactivar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
									limpiarCampos();
									buscarPerfiles("", 4, listaPerfil.get(indice).getId_perfil());
								} catch (Exception e) {
									Messagebox.show(
											"Error al desactivar el perfil. \n\n" + "Mensaje de error: \n\n"
													+ e.getMessage(),
											".:: Desactivar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							}
						}
					});
		} else {
			Messagebox.show("Esta seguro de activar el perfil?", ".:: Activar perfil ::.",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								modelo_perfil perfil = new modelo_perfil();
								perfil.setId_perfil(listaPerfil.get(indice).getId_perfil());
								perfil.setEst_perfil("A");
								perfil.setUsu_modifica(user);
								java.util.Date date = new Date();
								Timestamp timestamp = new Timestamp(date.getTime());
								perfil.setFec_modifica(timestamp);
								dao_perfil dao = new dao_perfil();
								try {
									dao.activarDesactivarPerfil(perfil);
									Messagebox.show("El perfil se activo correctamente.", ".:: Activar perfil ::.",
											Messagebox.OK, Messagebox.EXCLAMATION);
									limpiarCampos();
									buscarPerfiles("", 4, listaPerfil.get(indice).getId_perfil());
								} catch (Exception e) {
									Messagebox.show(
											"Error al activar el perfil. \n\n" + "Mensaje de error: \n\n"
													+ e.getMessage(),
											".:: Activar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							}
						}
					});
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btnEliminar(int indice) {
		Messagebox.show("Esta seguro de eliminar el perfil?", ".:: Eliminar perfil ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							long id_perfil = listaPerfil.get(indice).getId_perfil();
							dao_perfil dao = new dao_perfil();
							try {
								dao.eliminarPerfil(id_perfil);
								Messagebox.show("El perfil se elimino correctamente.", ".:: Eliminar perfil ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								buscarPerfiles("", 4, listaPerfil.get(indice).getId_perfil());
							} catch (Exception e) {
								Messagebox.show(
										"Error al eliminar el perfil. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										".:: Eliminar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}
	
	public void onClick$btnRelacion(int indice) {
		if (listaPerfil.get(indice).getEst_perfil().equals("I")) {
			Messagebox.show("El perfil se encuentra inactivo, debe estar activo para poder relacionarlo.",
					".:: Relacionar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Sessions.getCurrent().setAttribute("perfil", listaPerfil.get(indice));
		Component comp = Executions.createComponents("/mantenimientos/perfiles/relacionar.zul", null, null);
		if (comp instanceof Window) {
			((Window) comp).doModal();
			comp.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
					limpiarCampos();
					buscarPerfiles("", 4, listaPerfil.get(indice).getId_perfil());
				}
			});
		}
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtBuscar.setText("");
		borrarListaConsulta();
	}

}
