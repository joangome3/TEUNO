package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.bitacora.modelo.modelo_panel3;
import bp.aplicaciones.conexion.conexion;

public class dao_panel3 {

	public List<modelo_panel3> obtenerDatosGraficoDeBarra1(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel3> lista_datos = new ArrayList<modelo_panel3>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel3Barra1(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel3(resultado.getString("tipo_servicio")));
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

	public List<modelo_panel3> obtenerDatosGraficoDeBarra2(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel3> lista_datos = new ArrayList<modelo_panel3>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel3Barra2(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel3(resultado.getInt("cantidad"), resultado.getString("tipo_servicio")));
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

	public List<modelo_panel3> obtenerDatosGraficoDeBarra3(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel3> lista_datos = new ArrayList<modelo_panel3>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel3Barra3(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel3(resultado.getInt("cantidad"), resultado.getString("tipo_servicio")));
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
