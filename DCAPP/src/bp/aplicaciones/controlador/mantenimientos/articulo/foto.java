package bp.aplicaciones.controlador.mantenimientos.articulo;

import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@SuppressWarnings({ "serial", "deprecation" })
public class foto extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zFoto;
	@Wire
	Timer cTimer;
	@Wire
	Image imgFoto;

	Image img = new Image();

	byte[] buffer = null;

	modelo_articulo articulo = (modelo_articulo) Sessions.getCurrent().getAttribute("articulo");
	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");

	List<modelo_usuario_bk> listaUsuario = new ArrayList<modelo_usuario_bk>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Sessions.getCurrent().removeAttribute("articulo");
		mostrarFoto();
	}

	public void mostrarFoto() throws SQLException, IOException, ClassNotFoundException {
		cargarArticulos(String.valueOf(articulo.getId_articulo()), "", "");
		if (listaArticulo.size() > 0) {
			if (listaArticulo.get(0).getImg_articulo() != null) {
				InputStream in = listaArticulo.get(0).getImg_articulo().getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				imgFoto.setContent(image);
			} else {
				imgFoto.setSrc("/img/principal/noimage.png");
			}
		} else {
			imgFoto.setSrc("/img/principal/noimage.png");
		}
		// img.setWidth("400px");
		// img.setHeight("400px");
	}

	public void cargarArticulos(String criterio, String empresa, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		try {
			listaArticulo = dao.obtenerArticulos2(criterio, String.valueOf(id_dc), empresa, 13, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Ver foto ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

}
