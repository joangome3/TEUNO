package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_solicitud;

public class dao_tipo_solicitud {

	public List<modelo_tipo_solicitud> obtenerTipoSolicitudes(String criterio, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tipo_solicitud> lista_tipo_solicitudes = new ArrayList<modelo_tipo_solicitud>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL tipo_solicitud_obtenerTipoSolicitudes(?,?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tipo_solicitudes.add(new modelo_tipo_solicitud(resultado.getLong("id_tipo_solicitud"),
						resultado.getString("nom_tipo_solicitud"), resultado.getString("est_tipo_solicitud"),
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
		return lista_tipo_solicitudes;
	}

}
