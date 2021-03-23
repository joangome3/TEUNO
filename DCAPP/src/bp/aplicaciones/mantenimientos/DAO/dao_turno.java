package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;

public class dao_turno {

	public List<modelo_turno> obtenerTurnos(String criterio)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_turno> lista_turnos = new ArrayList<modelo_turno>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL turno_obtenerTurnos(?)}");
			consulta.setString(1, criterio.toUpperCase());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_turnos.add(new modelo_turno(resultado.getLong("id_turno"), resultado.getString("nom_turno"),
						resultado.getTime("hora_inicio"), resultado.getTime("hora_fin"),
						resultado.getString("es_extendido"), resultado.getString("est_turno"),
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
		return lista_turnos;
	}

}
