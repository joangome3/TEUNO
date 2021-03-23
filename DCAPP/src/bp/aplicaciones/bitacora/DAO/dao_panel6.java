package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.bitacora.modelo.modelo_panel6;
import bp.aplicaciones.conexion.conexion;

public class dao_panel6 {

	public List<modelo_panel6> obtenerDatosGraficoDeCantidad(String localidad, String turno)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_panel6> lista_datos = new ArrayList<modelo_panel6>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerGraficoPanel6Cantidad1(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_datos.add(new modelo_panel6(resultado.getString("tiempo_invertido")));
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
	
	public String obtenerTiempoTrabajado(String localidad, String fecha_inicio, String fecha_fin, String turno)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		String tiempo = "";
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL bitacora_obtenerTiempoTrabajado(?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, turno);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				tiempo = resultado.getString("tiempo_trabajado");
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
		return tiempo;
	}

}
