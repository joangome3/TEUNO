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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_12;

public class dao_parametros_generales_12 {

	public List<modelo_parametros_generales_12> obtenerRelacionesOpciones(String campo, String opcion, long localidad,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_parametros_generales_12> lista_relacion = new ArrayList<modelo_parametros_generales_12>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerParametros12(?, ?, ?, ?)}");
			consulta.setString(1, campo);
			consulta.setString(2, opcion);
			consulta.setLong(3, localidad);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(new modelo_parametros_generales_12(resultado.getLong("id_relacion"),
						resultado.getLong("id_empresa"), resultado.getString("nom_empresa"),
						resultado.getLong("id_opcion"), resultado.getString("se_acepta_ticket_repetido"),
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

	public void insertarRelacion(modelo_parametros_generales_12 relacionOpcion, long id_opcion, long id_empresa,
			long id_localidad) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_eliminarParametros12(?, ?, ?)}");
			consulta.setLong(1, id_opcion);
			consulta.setLong(2, id_empresa);
			consulta.setLong(3, id_localidad);
			consulta.executeUpdate();
			Long id = (long) 0;
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerNuevoIDParametros12()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_relacion") + 1;
			}
			consulta = conexion.abrir()
					.prepareStatement("{CALL parametros_generales_insertarParametros12 (?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, relacionOpcion.getId_empresa());
			consulta.setLong(3, relacionOpcion.getId_opcion());
			consulta.setString(4, relacionOpcion.getSe_acepta_ticket_repetido());
			consulta.setLong(5, relacionOpcion.getId_localidad());
			consulta.setString(6, relacionOpcion.getEst_relacion());
			consulta.setString(7, relacionOpcion.getUsu_ingresa());
			consulta.setTimestamp(8, relacionOpcion.getFec_ingresa());
			consulta.executeUpdate();
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
