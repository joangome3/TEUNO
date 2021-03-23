package bp.aplicaciones.controlador.bitacora.dashboard;

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

import bp.aplicaciones.bitacora.DAO.dao_panel3;
import bp.aplicaciones.bitacora.modelo.modelo_panel3;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import tools.dynamia.zk.addons.chartjs.Axe;
import tools.dynamia.zk.addons.chartjs.CategoryChartjsData;
import tools.dynamia.zk.addons.chartjs.Chartjs;
import tools.dynamia.zk.addons.chartjs.ChartjsData;
import tools.dynamia.zk.addons.chartjs.ChartjsOptions;
import tools.dynamia.zk.addons.chartjs.Dataset;
import tools.dynamia.zk.addons.chartjs.Labels;
import tools.dynamia.zk.addons.chartjs.Legend;
import tools.dynamia.zk.addons.chartjs.Scales;
import tools.dynamia.zk.addons.chartjs.Ticks;
import tools.dynamia.zk.addons.chartjs.Tooltips;

@SuppressWarnings({ "serial", "deprecation" })
public class panel3 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zBarra;
	@Wire
	Chartjs cBarra;
	@Wire
	Timer timer;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		init1(String.valueOf(id_dc));
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
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

	public void init1(String localidad)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		cBarra.setType(Chartjs.TYPE_BAR);
		cBarra.setOptions(getChartOptions1());
		cBarra.setData(getChartData1(localidad));
	}

	public ChartjsData getChartData1(String localidad)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		CategoryChartjsData chartData = new CategoryChartjsData();
		dao_panel3 dao = new dao_panel3();
		List<modelo_panel3> listaDatos1 = new ArrayList<modelo_panel3>();
		listaDatos1 = dao.obtenerDatosGraficoDeBarra1(localidad);
		for (int i = 0; i < listaDatos1.size(); i++) {
			chartData.setLabels(listaDatos1.get(i).getTipo_servicio());
		}
		/* DATASETS DE PROXIMOS A VENCER */
		List<modelo_panel3> listaDatos2 = new ArrayList<modelo_panel3>();
		listaDatos2 = dao.obtenerDatosGraficoDeBarra2(localidad);
		Dataset<Integer> dataset1 = new Dataset<Integer>();
		dataset1.setLabel("PROXIMAS A VENCER");
		List<Integer> lDatos1 = new ArrayList<Integer>();
		boolean existe_tipo_servicio = false;
		for (int i = 0; i < listaDatos1.size(); i++) {
			existe_tipo_servicio = false;
			for (int j = 0; j < listaDatos2.size(); j++) {
				if (listaDatos1.get(i).getTipo_servicio().equals(listaDatos2.get(j).getTipo_servicio())) {
					lDatos1.add(listaDatos2.get(j).getCantidad());
					existe_tipo_servicio = true;
					j = listaDatos1.size() + 1;
				}
			}
			if (existe_tipo_servicio == false) {
				lDatos1.add(0);
			}
		}
		dataset1.setBackgroundColor("#C6A664");
		dataset1.setData(lDatos1);
		/* DATASETS DE VENCIDOS */
		List<modelo_panel3> listaDatos3 = new ArrayList<modelo_panel3>();
		listaDatos3 = dao.obtenerDatosGraficoDeBarra3(localidad);
		Dataset<Integer> dataset2 = new Dataset<Integer>();
		dataset2.setLabel("VENCIDAS");
		List<Integer> lDatos2 = new ArrayList<Integer>();
		for (int i = 0; i < listaDatos1.size(); i++) {
			existe_tipo_servicio = false;
			for (int j = 0; j < listaDatos3.size(); j++) {
				if (listaDatos1.get(i).getTipo_servicio().equals(listaDatos3.get(j).getTipo_servicio())) {
					lDatos2.add(listaDatos3.get(j).getCantidad());
					existe_tipo_servicio = true;
					j = listaDatos1.size() + 1;
				}
			}
			if (existe_tipo_servicio == false) {
				lDatos2.add(0);
			}
		}
		dataset2.setBackgroundColor("#C23B22");
		dataset2.setData(lDatos2);
		dataset1.setFill(false);
		dataset2.setFill(false);
		chartData.addDataset(dataset1);
		chartData.addDataset(dataset2);
		chartData.getDatasets().remove(0);
		return chartData;
	}

	public ChartjsOptions getChartOptions1() {
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
		Scales scale = new Scales();
		Axe yaxe = new Axe();
		Ticks tick = new Ticks();
		tick.setBeginAtZero(true);
		yaxe.setTicks(tick);
		scale.addY(yaxe);
		options.setScales(scale);
		return options;
	}
}
