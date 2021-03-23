package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.*;
import bp.aplicaciones.mantenimientos.modelo.modelo_campo;

public class dao_campo {

	public List<modelo_campo> obtenerCampos(String criterio, int tipo, long id)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_campo> lista_campos = new ArrayList<modelo_campo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL campos_obtenerCampos(?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_campos.add(new modelo_campo(resultado.getLong("id_campo"), resultado.getString("nom_campo"),
						resultado.getString("est_campo"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getTimestamp("fec_modifica")));

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
		return lista_campos;
	}

}
