package bp.aplicaciones.controlador;

import java.text.DecimalFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class MyChartCustomizer implements JRChartCustomizer {

	@Override
	public void customize(JFreeChart jfchart, JRChart jrchart) {
		PiePlot plot = (PiePlot) jfchart.getPlot();
		StandardPieSectionLabelGenerator labelGen = new StandardPieSectionLabelGenerator("{0} ({2})",
				new DecimalFormat("#,##0.00"), new DecimalFormat("0.00%"));
		plot.setLabelGenerator(labelGen);
	}
}
