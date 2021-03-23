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
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import bp.aplicaciones.sibod.DAO.dao_graficos;
import bp.aplicaciones.sibod.modelo.modelo_grafico_lineal;
import tools.dynamia.zk.addons.chartjs.CategoryChartjsData;
import tools.dynamia.zk.addons.chartjs.Chartjs;
import tools.dynamia.zk.addons.chartjs.ChartjsData;
import tools.dynamia.zk.addons.chartjs.ChartjsOptions;
import tools.dynamia.zk.addons.chartjs.Dataset;
import tools.dynamia.zk.addons.chartjs.Labels;
import tools.dynamia.zk.addons.chartjs.Legend;
import tools.dynamia.zk.addons.chartjs.Tooltips;

@SuppressWarnings({ "serial", "deprecation" })
public class LineChart extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zLineal;
	@Wire
	Chartjs cLineal;
	@Wire
	Timer timer;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		init();
	}

	public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		//cLineal = new Chartjs();
		cLineal.setType(Chartjs.TYPE_LINE);
		cLineal.setOptions(getChartOptions());
		cLineal.setData(getChartData());
	}

	public ChartjsData getChartData() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		CategoryChartjsData chartData = new CategoryChartjsData();
		dao_graficos dao = new dao_graficos();
		List<modelo_grafico_lineal> listaDatosIngreso = new ArrayList<modelo_grafico_lineal>();
		List<modelo_grafico_lineal> listaDatosEgreso = new ArrayList<modelo_grafico_lineal>();
		listaDatosIngreso = dao.obtenerIngresosGraficoLineal(String.valueOf(id_dc), "");
		for (int i = 0; i < listaDatosIngreso.size(); i++) {
			chartData.setLabels(listaDatosIngreso.get(i).getMes());
		}
		Dataset<Integer> dataset = new Dataset<Integer>();
		/* INGRESOS */
		dataset.setLabel("INGRESOS");
		List<Integer> lIngresos = new ArrayList<Integer>();
		for (int i = 0; i < listaDatosIngreso.size(); i++) {
			lIngresos.add(listaDatosIngreso.get(i).getCantidad());
		}
		dataset.setData(lIngresos);
		dataset.setFill(false);
		dataset.setBorderColor("rgba(5 , 182, 17, 1)");
		// dataset.setLineTension(1);
		chartData.addDataset(dataset);
		/* EGRESOS */
		listaDatosEgreso = dao.obtenerEgresosGraficoLineal(String.valueOf(id_dc), "");
		dataset = new Dataset<Integer>();
		dataset.setLabel("EGRESOS");
		List<Integer> lEgresos = new ArrayList<Integer>();
		for (int i = 0; i < listaDatosEgreso.size(); i++) {
			lEgresos.add(listaDatosEgreso.get(i).getCantidad());
		}
		dataset.setData(lEgresos);
		dataset.setFill(false);
		dataset.setBorderColor("rgba(75, 192, 192, 1)");
		// dataset.setLineTension(1);
		chartData.addDataset(dataset);
		chartData.getDatasets().remove(0);
		//chartData.getDataset()
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
