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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;

public class dao_parametros_generales_10 {

	public List<modelo_parametros_generales_10> obtenerRelacionesOpciones(String opcion, String tipo_servicio,
			String localidad, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_parametros_generales_10> lista_relacion = new ArrayList<modelo_parametros_generales_10>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerParametros10(?, ?, ?, ?)}");
			consulta.setString(1, opcion);
			consulta.setString(2, tipo_servicio);
			consulta.setString(3, localidad);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(new modelo_parametros_generales_10(resultado.getLong("id_relacion"),
						resultado.getLong("id_opcion"), resultado.getLong("id_tipo_servicio"),
						resultado.getString("se_puede_eliminar"), resultado.getLong("id_localidad"),
						resultado.getString("est_relacion"), resultado.getString("usu_ingresa"),
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
		return lista_relacion;
	}

	public void insertarRelacion(List<modelo_parametros_generales_10> relacionOpcion, long id_opcion, long localidad)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_eliminarParametros10(?, ?)}");
			consulta.setLong(1, id_opcion);
			consulta.setLong(2, localidad);
			consulta.executeUpdate();
			for (int i = 0; i < relacionOpcion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir()
						.prepareStatement("{CALL parametros_generales_obtenerNuevoIDParametros10()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL parametros_generales_insertarParametros10 (?, ?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionOpcion.get(i).getId_opcion());
				consulta.setLong(3, relacionOpcion.get(i).getId_tipo_servicio());
				consulta.setString(4, relacionOpcion.get(i).getSe_puede_eliminar());
				consulta.setLong(5, relacionOpcion.get(i).getId_localidad());
				consulta.setString(6, relacionOpcion.get(i).getEst_relacion());
				consulta.setString(7, relacionOpcion.get(i).getUsu_ingresa());
				consulta.setTimestamp(8, relacionOpcion.get(i).getFec_ingresa());
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
