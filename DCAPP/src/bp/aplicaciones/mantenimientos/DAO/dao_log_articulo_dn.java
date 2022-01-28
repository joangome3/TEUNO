package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_log_articulo_dn;

public class dao_log_articulo_dn {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL articuloDN_obtenerNuevoIDLog()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_log") + 1;
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
		return id;
	}

	public List<modelo_log_articulo_dn> obtenerLogsArticulosDN(String criterio, String fecha_inicio, String fecha_fin,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_log_articulo_dn> lista = new ArrayList<modelo_log_articulo_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_obtenerLogs(?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista.add(new modelo_log_articulo_dn(resultado.getLong("id_log"), resultado.getLong("id_articulo"),
						resultado.getString("des_log"), resultado.getString("mot_log"), resultado.getString("est_log"),
						resultado.getString("usu_ingresa"), resultado.getString("nom_usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getString("nom_usu_modifica"), resultado.getTimestamp("fec_modifica")));

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
		return lista;
	}

	public void insertarLogArticulo(modelo_log_articulo_dn log) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_insertarLog(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, log.getId_articulo());
			consulta.setString(3, log.getDes_log());
			consulta.setString(4, log.getMot_log());
			consulta.setString(5, log.getEst_log());
			consulta.setString(6, log.getUsu_ingresa());
			consulta.setTimestamp(7, log.getFec_ingresa());
			consulta.executeUpdate();
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

}
