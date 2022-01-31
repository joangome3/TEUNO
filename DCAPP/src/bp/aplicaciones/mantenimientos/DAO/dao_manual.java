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
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;

//Manuales
public class dao_manual {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL manual_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_manual") + 1;
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

	public List<modelo_manual> obtenerManuales(String criterio, String localidad, int tipo, long id, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_manual> lista_manuales = new ArrayList<modelo_manual>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL manual_obtenerManuales(?,?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, localidad);
			consulta.setInt(3, tipo);
			consulta.setLong(4, id);
			consulta.setLong(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_manuales.add(new modelo_manual(resultado.getLong("id_manual"), resultado.getString("nom_manual"),
						resultado.getString("des_manual"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("ext_manual"),
						resultado.getBlob("dir_manual"), resultado.getString("est_manual"),
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
		return lista_manuales;
	}

	public void insertarManual(modelo_manual manual) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL manual_insertarManual(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, manual.getNom_manual().toUpperCase());
			consulta.setString(3, manual.getDes_manual().toUpperCase());
			consulta.setLong(4, manual.getId_localidad());
			consulta.setString(5, manual.getExt_manual());
			consulta.setBlob(6, manual.getDir_manual());
			consulta.setString(7, manual.getEst_manual());
			consulta.setString(8, manual.getUsu_ingresa());
			consulta.setTimestamp(9, manual.getFec_ingresa());
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

	public void modificarManual(modelo_manual manual, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL manual_modificarManual(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, manual.getNom_manual().toUpperCase());
			consulta.setString(2, manual.getDes_manual().toUpperCase());
			consulta.setLong(3, manual.getId_localidad());
			consulta.setString(4, manual.getExt_manual());
			consulta.setBlob(5, manual.getDir_manual());
			consulta.setString(6, manual.getEst_manual());
			consulta.setString(7, manual.getUsu_modifica());
			consulta.setTimestamp(8, manual.getFec_modifica());
			consulta.setLong(9, manual.getId_manual());
			consulta.setLong(10, tipo);
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

	public void activarDesactivarManual(modelo_manual manual) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL manual_desactivarManual(?, ?, ?, ?)}");
			consulta.setString(1, manual.getEst_manual());
			consulta.setString(2, manual.getUsu_modifica());
			consulta.setTimestamp(3, manual.getFec_modifica());
			consulta.setLong(4, manual.getId_manual());
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

	public void eliminarManual(Long id_manual) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL manual_eliminarManual(?)}");
			consulta.setLong(1, id_manual);
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
