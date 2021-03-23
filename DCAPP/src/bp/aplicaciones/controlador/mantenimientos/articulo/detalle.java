package bp.aplicaciones.controlador.mantenimientos.articulo;

import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_estado_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class detalle extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zDetalle;
	@Wire
	Timer cTimer;
	@Wire
	Listbox lbxEstados;

	modelo_articulo articulo = (modelo_articulo) Sessions.getCurrent().getAttribute("articulo");
	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");

	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Sessions.getCurrent().removeAttribute("articulo");
		lbxEstados.setEmptyMessage("¡No existen usuarios que mostrar.!");
		cargarEstados("", 1, id_dc);
		borrarListaEstados();
		dibujarListaEstados(articulo.getId_articulo(), articulo.getId_ubicacion());
	}

	public void cargarEstados(String criterio, int tipo, long localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_articulo dao = new dao_estado_articulo();
		try {
			listaEstados = dao.obtenerEstados(criterio, tipo, String.valueOf(localidad));
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void borrarListaEstados() {
		Listitem lItem;
		for (int i = lbxEstados.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxEstados.getItemAtIndex(i);
			lbxEstados.removeItemAt(lItem.getIndex());
		}
	}

	public void dibujarListaEstados(long id_articulo, long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		List<modelo_relacion_articulo_ubicacion> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion>();
		List<modelo_relacion_estado_articulo_ubicacion> listaRelacionEstadoArticulos = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
		dao_relacion_articulo dao1 = new dao_relacion_articulo();
		dao_relacion_estado_articulo dao2 = new dao_relacion_estado_articulo();
		listaRelacionArticulos1 = dao1.obtenerRelacionesArticulosUbicaciones(0, id_articulo, id_ubicacion, 2);
		if (listaRelacionArticulos1.size() > 0) {
			listaRelacionEstadoArticulos = dao2.obtenerRelacionesEstadosArticulosUbicaciones(0, 0,
					listaRelacionArticulos1.get(0).getId_relacion(), 2);
		}
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaRelacionEstadoArticulos.size(); i++) {
			lItem = new Listitem();
			/* ID */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaRelacionEstadoArticulos.get(i).getId_relacion()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ESTADO */
			lCell = new Listcell();
			for (int j = 0; j < listaEstados.size(); j++) {
				if (listaEstados.get(j).getId_estado() == listaRelacionEstadoArticulos.get(i).getId_estado()) {
					lCell.setLabel(listaEstados.get(j).getNom_estado());
					j = listaEstados.size() + 1;
				}
			}
			lCell.setStyle("text-align: center !important; font-style: italic !important;");
			lItem.appendChild(lCell);
			/* STOCK */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaRelacionEstadoArticulos.get(i).getStock()));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxEstados.appendChild(lItem);
		}
	}

}
