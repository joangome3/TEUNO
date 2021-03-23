package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;

public class dao_mantenimiento {

	public List<modelo_mantenimiento> obtenerMantenimientos(String criterio, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_mantenimiento> lista_mantenimientoes = new ArrayList<modelo_mantenimiento>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL mantenimiento_obtenerMantenimientos(?,?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_mantenimientoes.add(new modelo_mantenimiento(resultado.getLong("id_mantenimiento"),
						resultado.getString("nom_mantenimiento"), resultado.getString("est_mantenimiento"),
						resultado.getString("usu_ingresa"), resultado.getTimestamp("fec_ingresa"),
						resultado.getString("usu_modifica"), resultado.getTimestamp("fec_modifica")));

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
		return lista_mantenimientoes;
	}

}
