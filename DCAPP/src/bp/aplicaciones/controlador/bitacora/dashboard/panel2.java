package bp.aplicaciones.controlador.bitacora.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.DAO.dao_panel2;
import bp.aplicaciones.bitacora.modelo.modelo_panel2;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
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
public class panel2 extends SelectorComposer<Component> {

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
	String uPanel = (String) Sessions.getCurrent().getAttribute("uPanel");
	String mPanel = (String) Sessions.getCurrent().getAttribute("mPanel");

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	
	int mesActual = 0;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Fechas extensionFechas = new Fechas();
		mesActual = extensionFechas.obtenerEnteroDelMesAPartirUnaFecha(new Date());
		cargarUsuarios();
		cargarPerfil();
		cargarParametros1();
		if (listaParametros1.size() > 0) {
			if (listaParametros1.get(0).getId_perfil_revisor_bitacora() != id_perfil) {
				validarUsuario();
				init1(String.valueOf(id_dc), user, String.valueOf(mesActual), 1);
			} else {
				if (uPanel == null) {
					if (mPanel == null) {
						init1(String.valueOf(id_dc), uPanel, String.valueOf(mesActual), 2);
					} else {
						init1(String.valueOf(id_dc), uPanel, mPanel, 2);
					}
				} else {
					if (mPanel == null) {
						init1(String.valueOf(id_dc), uPanel, String.valueOf(mesActual), 1);
					} else {
						init1(String.valueOf(id_dc), uPanel, mPanel, 1);
					}
				}
			}
		} else {
			validarUsuario();
			if (mPanel == null) {
				init1(String.valueOf(id_dc), user, String.valueOf(mesActual), 1);
			} else {
				init1(String.valueOf(id_dc), user, mPanel, 1);
			}
		}
	}

	public List<modelo_parametros_generales_1> obtenerParametros1() {
		return listaParametros1;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		String criterio = String.valueOf(id_dc);
		try {
			listaUsuario = dao.obtenerUsuarios(criterio, 3, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los usuarios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void validarUsuario() {
		for (int i = 0; i < listaUsuario.size(); i++) {
			if (listaUsuario.get(i).getUse_usuario().equals(user)) {
				i = listaUsuario.size() + 1;
			}
		}
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

	public void init1(String localidad, String usuario, String mes, int tipo)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		cBarra.setType(Chartjs.TYPE_BAR);
		cBarra.setOptions(getChartOptions1());
		cBarra.setData(getChartData1(localidad, usuario, mes, tipo));
	}

	public ChartjsData getChartData1(String localidad, String usuario, String mes, int tipo)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {

		CategoryChartjsData chartData = new CategoryChartjsData();
		dao_panel2 dao = new dao_panel2();
		List<modelo_panel2> listaDatos1 = new ArrayList<modelo_panel2>();
		listaDatos1 = dao.obtenerDatosGraficoDeBarra1(localidad, usuario, mes, tipo);
		for (int i = 0; i < listaDatos1.size(); i++) {
			chartData.setLabels(listaDatos1.get(i).getTipo_servicio());
		}
		/* DATASETS DE CUMPLIMIENTOS */
		List<modelo_panel2> listaDatos2 = new ArrayList<modelo_panel2>();
		listaDatos2 = dao.obtenerDatosGraficoDeBarra2(localidad, usuario, mes, tipo);
		Dataset<Integer> dataset1 = new Dataset<Integer>();
		dataset1.setLabel("CUMPLIDO");
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
		/* DATASETS DE INCUMPLIMIENTOS */
		List<modelo_panel2> listaDatos3 = new ArrayList<modelo_panel2>();
		listaDatos3 = dao.obtenerDatosGraficoDeBarra3(localidad, usuario, mes, tipo);
		Dataset<Integer> dataset2 = new Dataset<Integer>();
		dataset2.setLabel("INCUMPLIDO");
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
