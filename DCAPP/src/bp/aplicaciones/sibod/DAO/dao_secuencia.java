package bp.aplicaciones.sibod.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bp.aplicaciones.conexion.conexion;

public class dao_secuencia {

	public Long obtenerSecuencia(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long secuencia = (long) 1;
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL movimiento_obtenerNuevaSecuencia(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				if (resultado.getLong("num_secuencia") != 0) {
					secuencia = resultado.getLong("num_secuencia");
				} else {
					secuencia = secuencia + 1;
				}
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
		return secuencia;
	}

}
