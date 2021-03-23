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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_4;

public class dao_parametros_generales_4 {

	public List<modelo_parametros_generales_4> obtenerRelacionesDias(String dia, String tarea_periodica, long localidad,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_parametros_generales_4> lista_relacion = new ArrayList<modelo_parametros_generales_4>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerParametros4(?, ?, ?, ?)}");
			consulta.setString(1, dia);
			consulta.setString(2, tarea_periodica);
			consulta.setLong(3, localidad);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(
						new modelo_parametros_generales_4(resultado.getLong("id_relacion"), resultado.getLong("id_dia"),
								resultado.getString("descripcion"), resultado.getLong("id_tarea_periodica"),
								resultado.getLong("id_localidad"), resultado.getString("est_relacion"),
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
		return lista_relacion;
	}

	public void insertarRelacion(List<modelo_parametros_generales_4> relacionOpcion, long id_dia, long id_localidad)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_eliminarParametros4(?,?)}");
			consulta.setLong(1, id_dia);
			consulta.setLong(2, id_localidad);
			consulta.executeUpdate();
			for (int i = 0; i < relacionOpcion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerNuevoIDParametros4()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL parametros_generales_insertarParametros4 (?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionOpcion.get(i).getId_dia());
				consulta.setLong(3, relacionOpcion.get(i).getId_tarea_periodica());
				consulta.setLong(4, relacionOpcion.get(i).getId_localidad());
				consulta.setString(5, relacionOpcion.get(i).getEst_relacion());
				consulta.setString(6, relacionOpcion.get(i).getUsu_ingresa());
				consulta.setTimestamp(7, relacionOpcion.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			consulta.close();
			conexion.abrir().commit();
		} catch (SQLException ex) {
			conexion.abrir().rollback();
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			conexion.abrir().rollback();
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

}
