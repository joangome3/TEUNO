package bp.aplicaciones.controlador.mantenimientos.informativo;

import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_informativo_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class leido_usuario extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zLeidos;
	@Wire
	Timer cTimer;
	@Wire
	Listbox lbxUsuarios;

	@SuppressWarnings("unchecked")
	List<modelo_relacion_informativo_usuario> listaRelacion = (List<modelo_relacion_informativo_usuario>) Sessions
			.getCurrent().getAttribute("listaRelacion");
	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");

	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Sessions.getCurrent().removeAttribute("listaRelacion");
		lbxUsuarios.setEmptyMessage("¡No existen usuarios que mostrar.!");
		cargarUsuarios();
		borrarListaConsulta();
		dibujarListaConsulta();
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		listaUsuario = dao.consultarUsuarios(id_dc, 0, "", "", 0, 2);
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaRelacion.size(); i++) {
			lItem = new Listitem();
			Label lUsuario;
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaRelacion.get(i).getId_relacion()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* USUARIO */
			lCell = new Listcell();
			lUsuario = new Label();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			String f = "";
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaUsuario.get(j).getId_usuario() == listaRelacion.get(i).getId_usuario()) {
					f = sdf.format(listaRelacion.get(i).getFec_ingresa());
					lUsuario.setValue("Leído por " + listaUsuario.get(j).verNombreCompleto() + ", el día " + f);
					j = listaUsuario.size() + 1;
				}
			}
			lCell.appendChild(lUsuario);
			lItem.appendChild(lCell);
			/* AÑADIR ITEM A LISTBOX */
			lbxUsuarios.appendChild(lItem);
		}
	}

	public void borrarListaConsulta() {
		lbxUsuarios.clearSelection();
		Listitem lItem;
		for (int i = lbxUsuarios.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxUsuarios.getItemAtIndex(i);
			lbxUsuarios.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxUsuarios);
	}

}
