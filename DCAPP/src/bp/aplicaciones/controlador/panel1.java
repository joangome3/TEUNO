package bp.aplicaciones.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class panel1 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zPanel;
	@Wire
	Panel cpTopArticulo, cpLineal, cpBarra, cpPastel1, cpPastel2, cpTabla1;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String anio_actual = dateFormat.format(new Date());
		cpLineal.setTitle("MOVIMIENTOS POR MES (" + anio_actual + ")");
		cpPastel1.setTitle("DISTRIBUCIÓN DE BALDOSAS EN BODEGA");
		cpPastel2.setTitle("DISTRIBUCIÓN DE BALDOSAS EN SALA UTIL");
		cpTabla1.setTitle("STOCK DISPONIBLE DE ARTÍCULOS EN LA CATEGORÍA CABLEADO");
	}

}
