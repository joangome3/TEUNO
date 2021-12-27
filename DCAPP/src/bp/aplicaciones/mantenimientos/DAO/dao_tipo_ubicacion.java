package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;

public class dao_tipo_ubicacion {

	public List<modelo_tipo_ubicacion> obtenerTipoUbicaciones(String criterio, long id_tipo_trabajo, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tipo_ubicacion> lista_tipo_ubicacions = new ArrayList<modelo_tipo_ubicacion>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL tipo_ubicacion_obtenerTipoUbicaciones(?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setLong(2, id_tipo_trabajo);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tipo_ubicacions.add(new modelo_tipo_ubicacion(resultado.getLong("id_tip_ubicacion"),
						resultado.getString("nom_tip_ubicacion"), resultado.getString("est_tip_ubicacion"),
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
		return lista_tipo_ubicacions;
	}

}
