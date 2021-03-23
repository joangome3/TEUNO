//package bp.aplicaciones.controlador.sibod.dashboard;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.zkoss.chart.AxisTitle;
//import org.zkoss.chart.Charts;
//import org.zkoss.chart.Exporting;
//import org.zkoss.chart.ExportingButton;
//import org.zkoss.chart.MenuItem;
//import org.zkoss.chart.PaneBackground;
//import org.zkoss.chart.Point;
//import org.zkoss.chart.RadialGradient;
//import org.zkoss.chart.Series;
//import org.zkoss.chart.YAxis;
//import org.zkoss.chart.plotOptions.GaugeDialPlotOptions;
//import org.zkoss.chart.util.AnyVal;
//import org.zkoss.json.JavaScriptValue;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zk.ui.util.Clients;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Window;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class reloj extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zReloj;
//	@Wire
//	Charts cReloj;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	private static final String ON_MY_CUSTOM_ITEM = "onMyCustomItem";
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		cargarGraficoReloj();
//		createCustomExportItems(cReloj);
//	}
//
//	private void createCustomExportItems(Charts charts) {
//		Exporting exporting = charts.getExporting();
//		ExportingButton buttons = exporting.getButtons();
//		List<MenuItem> menuItems = new ArrayList<>();
//		menuItems.add(customMenuItem("Imprimir", "this.print();"));
//		menuItems.add(separator());
//		menuItems.add(customMenuItem("Descargar", ""));
//		menuItems.add(customMenuItem("1.- PNG", "this.exportChart();"));
//		menuItems.add(customMenuItem("2.- JPEG", "this.exportChart({type: \"image/jpeg\"});"));
//		menuItems.add(customMenuItem("3.- PDF", "this.exportChart({type: \"application/pdf\"});"));
//		menuItems.add(customMenuItem("4.- SVG", "this.exportChart({type: \"image/svg+xml\"});"));
//		menuItems.add(separator());
//		buttons.setMenuItems(menuItems);
//	}
//
//	@Listen(ON_MY_CUSTOM_ITEM + " = #mychart")
//	public void handleMyCustomItem() {
//		Clients.showNotification("custem menu item clicked, handled on server");
//	}
//
//	private MenuItem customMenuItem(String text, String onclickJS) {
//		MenuItem menuItem = new MenuItem();
//		menuItem.setText(text);
//		menuItem.setOnclick(new JavaScriptValue("function(evt) {" + onclickJS + "}"));
//		return menuItem;
//	}
//
//	@SuppressWarnings("unused")
//	private MenuItem defaultMenuItem(String textKey, String onclickJS) {
//		MenuItem menuItem = new MenuItem();
//		menuItem.addExtraAttr("textKey", new AnyVal<String>(textKey));
//		menuItem.setOnclick(new JavaScriptValue("function(evt) {" + onclickJS + "}"));
//		return menuItem;
//	}
//
//	private MenuItem separator() {
//		MenuItem menuItem = new MenuItem();
//		menuItem.addExtraAttr("separator", new AnyVal<Boolean>(true));
//		return menuItem;
//	}
//
//	public void cargarGraficoReloj() {
//		// cReloj.setHeight(200);
//		List<PaneBackground> backgrounds = new LinkedList<PaneBackground>();
//		// Default background
//		backgrounds.add(new PaneBackground());
//		PaneBackground background = new PaneBackground();
//		RadialGradient radialGradient = new RadialGradient(0.5, -0.4, 1.9);
//		radialGradient.addStop(0.5, "rgba(255, 255, 255, 0.2)");
//		radialGradient.addStop(0.5, "rgba(200, 200, 200, 0.2)");
//		background.setBackgroundColor(radialGradient);
//		backgrounds.add(background);
//		cReloj.getPane().setBackground(backgrounds);
//		// The value axis
//		YAxis yAxis = cReloj.getYAxis();
//		yAxis.getLabels().setDistance(-20);
//		yAxis.setMin(0);
//		yAxis.setMax(12);
//		yAxis.setLineWidth(0);
//		yAxis.setShowFirstLabel(false);
//		yAxis.setMinorTickInterval("auto");
//		yAxis.setMinorTickWidth(1);
//		yAxis.setMinorTickLength(5);
//		yAxis.setMinorTickPosition("inside");
//		yAxis.setGridLineWidth(0);
//		yAxis.setMinorTickColor("#666");
//		yAxis.setTickInterval(1);
//		yAxis.setTickWidth(2);
//		yAxis.setTickPosition("inside");
//		yAxis.setTickLength(10);
//		yAxis.setTickColor("#666");
//		AxisTitle title = yAxis.getTitle();
//		// title.setText("Powered by<br/>ZK Charts");
//		title.setStyle("color: '#BBB'; fontWeight: 'normal'; fontSize: '8px'; lineHeight: '10px'");
//		title.setY(10);
//		cReloj.getTooltip().setFormat("{series.chart.tooltipText}");
//		Series series = cReloj.getSeries();
//		Double[] values = currentDateValue();
//		Point hour = new Point("hour", values[0]);
//		GaugeDialPlotOptions dial1 = hour.getDial();
//		dial1.setRadius("60%");
//		dial1.setBaseWidth(4);
//		dial1.setBaseLength("95%");
//		dial1.setRearLength("0");
//		series.addPoint(hour);
//		Point minute = new Point("minute", values[1]);
//		GaugeDialPlotOptions dial2 = minute.getDial();
//		dial2.setBaseLength("95%");
//		dial2.setRearLength("0");
//		series.addPoint(minute);
//		Point second = new Point("second", values[2]);
//		GaugeDialPlotOptions dial3 = second.getDial();
//		dial3.setRadius("100%");
//		dial3.setBaseWidth(1);
//		dial3.setRearLength("20%");
//		series.addPoint(second);
//		series.getPlotOptions().setAnimation(false);
//		series.getDataLabels().setEnabled(false);
//	}
//
//	// Move
//	@Listen("onTimer = #timer")
//	public void updateData() {
//		Double[] value = currentDateValue();
//		for (int i = 0; i < value.length; i++) {
//			cReloj.getSeries().getPoint(i).update(value[i]);
//		}
//	}
//
//	private Double[] currentDateValue() {
//		Date fechaActual = new Date();
//		double hours = fechaActual.getHours(), minutes = fechaActual.getMinutes(), seconds = fechaActual.getSeconds();
//		Double[] result = new Double[3];
//		result[0] = hours + minutes / 60;
//		result[1] = minutes * 12 / 60 + seconds * 12 / 3600;
//		result[2] = seconds * 12 / 60;
//		return result;
//	}
//
//}
