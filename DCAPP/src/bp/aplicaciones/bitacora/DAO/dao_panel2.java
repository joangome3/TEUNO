package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.bitacora.modelo.modelo_panel2;
import bp.aplicaciones.conexion.conexion;

public class dao_panel2 {

	public List<modelo_panel2> obtenerDatosGraficoDeBarra1(String localidad, String usuario, String mes, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel2> lista_datos = new ArrayList<modelo_panel2>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel2Barra1(?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, usuario);
			consulta.setString(3, mes);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel2(resultado.getString("tipo_servicio")));
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

	public List<modelo_panel2> obtenerDatosGraficoDeBarra2(String localidad, String usuario, String mes, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel2> lista_datos = new ArrayList<modelo_panel2>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel2Barra2(?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, usuario);
			consulta.setString(3, mes);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel2(resultado.getInt("cantidad"), resultado.getString("tipo_servicio")));
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

	public List<modelo_panel2> obtenerDatosGraficoDeBarra3(String localidad, String usuario, String mes, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel2> lista_datos = new ArrayList<modelo_panel2>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel2Barra3(?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, usuario);
			consulta.setString(3, mes);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel2(resultado.getInt("cantidad"), resultado.getString("tipo_servicio")));
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

	public List<modelo_panel2> obtenerDatosGraficoDePastel1(String localidad, String usuario, String mes, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel2> lista_datos = new ArrayList<modelo_panel2>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel2Pastel1(?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, usuario);
			consulta.setString(3, mes);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel2(resultado.getString("cumplimiento1"), resultado.getInt("cantidad"),
						resultado.getInt("porcentaje")));
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
