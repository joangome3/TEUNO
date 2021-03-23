package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.bitacora.modelo.modelo_panel1;
import bp.aplicaciones.conexion.conexion;

public class dao_panel1 {

	public List<modelo_panel1> obtenerDatosGraficoDeBarra(String localidad, String tipo_servicio)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel1> lista_datos = new ArrayList<modelo_panel1>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel1Barra1(?, ?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, tipo_servicio);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel1(resultado.getString("fecha"), resultado.getInt("cantidad")));
			}
			resultado.close();
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return lista_datos;
	}

}
