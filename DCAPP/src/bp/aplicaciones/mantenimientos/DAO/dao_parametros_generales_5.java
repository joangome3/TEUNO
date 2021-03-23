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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;

public class dao_parametros_generales_5 {

	public List<modelo_parametros_generales_5> obtenerRelacionesOpciones(String campo, String opcion, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_parametros_generales_5> lista_relacion = new ArrayList<modelo_parametros_generales_5>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerParametros5(?, ?, ?)}");
			consulta.setString(1, campo);
			consulta.setString(2, opcion);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(new modelo_parametros_generales_5(resultado.getLong("id_relacion"),
						resultado.getLong("id_usuario"), resultado.getString("nom_usuario"),
						resultado.getLong("id_opcion"), resultado.getString("est_relacion"),
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

	public void insertarRelacion(List<modelo_parametros_generales_5> relacionOpcion, long id_opcion)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_eliminarParametros5(?)}");
			consulta.setLong(1, id_opcion);
			consulta.executeUpdate();
			for (int i = 0; i < relacionOpcion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerNuevoIDParametros5()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL parametros_generales_insertarParametros5 (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionOpcion.get(i).getId_usuario());
				consulta.setLong(3, relacionOpcion.get(i).getId_opcion());
				consulta.setString(4, relacionOpcion.get(i).getEst_relacion());
				consulta.setString(5, relacionOpcion.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionOpcion.get(i).getFec_ingresa());
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
