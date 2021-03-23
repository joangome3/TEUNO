package bp.aplicaciones.controlador.sibod.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;

@SuppressWarnings({ "serial", "deprecation" })
public class TableChart extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zTabla;
	@Wire
	Bandbox bdxArticulos;
	@Wire
	Listbox lbxArticulos;
	@Wire
	Textbox txtBuscar, txtStock;

	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarArticulos1("");
	}

	public List<modelo_articulo> obtenerArticulos() {
		return listaArticulo;
	}

	public void cargarArticulos1(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		String categoria = "";
		if (id_dc == 1) {
			categoria = "11";
		}
		if (id_dc == 2) {
			categoria = "2";
		}
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), categoria, 6, 0);
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulos ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarArticulos2(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		String categoria = "";
		if (id_dc == 1) {
			categoria = "11";
		}
		if (id_dc == 2) {
			categoria = "2";
		}
		try {
			if (criterio.equals("")) {
				listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), categoria, 6, 0);
			} else {
				listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), categoria, 8, 0);
			}
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulos ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#lbxArticulos")
	public void onSelect$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getItemCount() == 0) {
			return;
		}
		bdxArticulos.setText(listaArticulo.get(lbxArticulos.getSelectedIndex()).getDes_articulo());
		bdxArticulos.setTooltiptext(listaArticulo.get(lbxArticulos.getSelectedIndex()).getDes_articulo());
		txtStock.setText(String.valueOf(listaArticulo.get(lbxArticulos.getSelectedIndex()).getSto_articulo()));
	}

	@Listen("onOK=#txtBuscar")
	public void onOK$txtBuscar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		listaArticulo = new ArrayList<modelo_articulo>();
		Listitem lItem2;
		for (int i = lbxArticulos.getItemCount() - 1; i >= 0; i--) {
			lItem2 = (Listitem) lbxArticulos.getItemAtIndex(i);
			lbxArticulos.removeItemAt(lItem2.getIndex());
		}
		cargarArticulos2(txtBuscar.getText());
	}

}
