package bp.aplicaciones.controlador.sibod.dashboard;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.sibod.DAO.dao_graficos;
import bp.aplicaciones.sibod.modelo.modelo_grafico_pastel;
import tools.dynamia.zk.addons.chartjs.CategoryChartjsData;
import tools.dynamia.zk.addons.chartjs.Chartjs;
import tools.dynamia.zk.addons.chartjs.ChartjsData;
import tools.dynamia.zk.addons.chartjs.ChartjsOptions;
import tools.dynamia.zk.addons.chartjs.Labels;
import tools.dynamia.zk.addons.chartjs.Legend;
import tools.dynamia.zk.addons.chartjs.Tooltips;

@SuppressWarnings({ "serial", "deprecation" })
public class PieChart1 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zPastel;
	@Wire
	Chartjs cPastel;
	@Wire
	Timer timer;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarParametros1();
		String id_categoria = "0";
		String id_ubicacion = "0";
		if (listaParametros1.size() > 0) {
			if ((listaParametros1.get(0).getId_categoria_reporte_1() != 0)
					&& (listaParametros1.get(0).getId_ubicacion_reporte_1() != 0)) {
				id_categoria = String.valueOf(listaParametros1.get(0).getId_categoria_reporte_1());
				id_ubicacion = String.valueOf(listaParametros1.get(0).getId_ubicacion_reporte_1());
			}
		}
		init(String.valueOf(id_dc), id_categoria, id_ubicacion);
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
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

	public void init(String localidad, String categoria, String ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		cPastel.setType(Chartjs.TYPE_PIE);
		cPastel.setOptions(getChartOptions());
		cPastel.setData(getChartData(localidad, categoria, ubicacion));
	}

	public ChartjsData getChartData(String localidad, String categoria, String ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		CategoryChartjsData chartData = new CategoryChartjsData();
		dao_graficos dao = new dao_graficos();
		List<modelo_grafico_pastel> listaDatosPastel = new ArrayList<modelo_grafico_pastel>();
		listaDatosPastel = dao.obtenerDatosGraficoDePastel1(localidad, categoria, ubicacion);
		for (int i = 0; i < listaDatosPastel.size(); i++) {
			chartData.add(listaDatosPastel.get(i).getNombre(), listaDatosPastel.get(i).getCantidad());
		}
		return chartData;
	}

	public ChartjsOptions getChartOptions() {
		ChartjsOptions options = new ChartjsOptions();
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		Labels labels = new Labels();
		labels.setBoxWidth(20);
		labels.setFontSize(11);
		legend.setLabels(labels);
		Tooltips tooltips = new Tooltips();
		tooltips.setEnabled(true);
		tooltips.setMode("nearest");
		options.setLegend(legend);
		options.setTooltips(tooltips);
		options.setResponsive(true);
		return options;
	}

}
