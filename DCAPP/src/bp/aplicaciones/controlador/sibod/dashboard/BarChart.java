package bp.aplicaciones.controlador.sibod.dashboard;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class BarChart extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zBarra;
	@Wire
	Flashchart cBarra;
	@Wire
	Timer timer;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	
	SimpleCategoryModel barData;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarGraficoBarra();
	}

	public void cargarGraficoBarra() {
		barData = (SimpleCategoryModel) BarChartData.getModel(String.valueOf(id_dc));
		cBarra.setModel(barData);
	}
	
	@Listen("onTimer=#timer")
	public void updateData() {
		cargarGraficoBarra();
	}
	
}
