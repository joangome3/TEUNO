package bp.aplicaciones.controlador.sibod.dashboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

import bp.aplicaciones.sibod.DAO.dao_graficos;
import bp.aplicaciones.sibod.modelo.modelo_grafico_barra;

public class BarChartData {

	private static CategoryModel datosBarra;

	public static CategoryModel getModel(String localidad) {

		dao_graficos dao = new dao_graficos();
		List<modelo_grafico_barra> listaCategoriasBarra = new ArrayList<modelo_grafico_barra>();
		try {
			listaCategoriasBarra = dao.obtenerCategoriasGraficoDeBarra(localidad);
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

		datosBarra = new SimpleCategoryModel();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String anio_actual = dateFormat.format(new Date());

		for (int i = 0; i < listaCategoriasBarra.size(); i++) {
			datosBarra.setValue(anio_actual, listaCategoriasBarra.get(i).getNombre(),
					listaCategoriasBarra.get(i).getCantidad());
		}

		return datosBarra;

	}

}
