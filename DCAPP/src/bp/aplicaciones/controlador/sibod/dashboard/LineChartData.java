package bp.aplicaciones.controlador.sibod.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

import bp.aplicaciones.sibod.DAO.dao_graficos;
import bp.aplicaciones.sibod.modelo.modelo_grafico_lineal;

public class LineChartData {

	private static CategoryModel datosLineal;

	public static CategoryModel getModel (String localidad, String articulo) {

		dao_graficos dao = new dao_graficos();
		List<modelo_grafico_lineal> listaIngresosLineal = new ArrayList<modelo_grafico_lineal>();
		List<modelo_grafico_lineal> listaEgresosLineal = new ArrayList<modelo_grafico_lineal>();
		try {
			listaIngresosLineal = dao.obtenerIngresosGraficoLineal(localidad, articulo);
			listaEgresosLineal = dao.obtenerEgresosGraficoLineal(localidad, articulo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		datosLineal = new SimpleCategoryModel();

		for (int i = 0; i < listaIngresosLineal.size(); i++) {
			datosLineal.setValue("INGRESO", listaIngresosLineal.get(i).getMes(),
					listaIngresosLineal.get(i).getCantidad());
		}

		for (int i = 0; i < listaEgresosLineal.size(); i++) {
			datosLineal.setValue("EGRESO", listaEgresosLineal.get(i).getMes(), listaEgresosLineal.get(i).getCantidad());
		}

		return datosLineal;

	}

}
