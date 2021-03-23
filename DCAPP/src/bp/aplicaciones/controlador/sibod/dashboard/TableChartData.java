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
import bp.aplicaciones.sibod.modelo.modelo_top_articulos;

public class TableChartData {

	private static CategoryModel datosTabla;

	public static CategoryModel getModel(String localidad) {

		dao_graficos dao = new dao_graficos();
		List<modelo_top_articulos> listaTop10Articulos = new ArrayList<modelo_top_articulos>();
		try {
			listaTop10Articulos = dao.obtenerDatosTopArticulos(localidad);
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

		datosTabla = new SimpleCategoryModel();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String anio_actual = dateFormat.format(new Date());

		for (int i = 0; i < listaTop10Articulos.size(); i++) {
			datosTabla.setValue(anio_actual, listaTop10Articulos.get(i).getNombre(),
					listaTop10Articulos.get(i).getCantidad());
		}

		return datosTabla;

	}

}
