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
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//Respaldos
public class dao_respaldo {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL respaldo_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_respaldo") + 1;
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

	public List<modelo_respaldo> obtenerRespaldos(String criterio, int tipo, String localidad,
			String mantenimiento_opcion, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_respaldo> lista_respaldos = new ArrayList<modelo_respaldo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_obtenerRespaldos(?,?,?,?,?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setString(3, localidad);
			consulta.setString(4, mantenimiento_opcion);
			consulta.setInt(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_respaldos
						.add(new modelo_respaldo(resultado.getLong("id_respaldo"), resultado.getLong("id_tip_respaldo"),
								resultado.getString("nom_tip_respaldo"), resultado.getString("dia_respaldo"),
								resultado.getString("es_fec_respaldo"), resultado.getString("est_respaldo"),
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
		return lista_respaldos;
	}

	public void insertarRespaldo(modelo_respaldo respaldo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_insertarRespaldo(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, respaldo.getId_tip_respaldo());
			consulta.setString(3, respaldo.getDia_respaldo().toUpperCase());
			consulta.setString(4, respaldo.getEs_fec_respaldo().toUpperCase());
			consulta.setString(5, respaldo.getEst_respaldo());
			consulta.setString(6, respaldo.getUsu_ingresa());
			consulta.setTimestamp(7, respaldo.getFec_ingresa());
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

	public void modificarRespaldo(modelo_respaldo respaldo, modelo_solicitud solicitud, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_modificarRespaldo(?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, respaldo.getId_tip_respaldo());
			consulta.setString(2, respaldo.getDia_respaldo().toUpperCase());
			consulta.setString(3, respaldo.getEs_fec_respaldo().toUpperCase());
			consulta.setString(4, respaldo.getEst_respaldo());
			consulta.setString(5, respaldo.getUsu_modifica());
			consulta.setTimestamp(6, respaldo.getFec_modifica());
			consulta.setLong(7, respaldo.getId_respaldo());
			consulta.setLong(8, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 12) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL solicitud_modificarSolicitud(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, solicitud.getId_tip_solicitud());
				consulta.setString(2, solicitud.getComentario_1().toUpperCase());
				consulta.setString(3, solicitud.getComentario_2().toUpperCase());
				consulta.setString(4, solicitud.getComentario_3().toUpperCase());
				consulta.setString(5, solicitud.getComentario_4().toUpperCase());
				consulta.setString(6, solicitud.getComentario_5().toUpperCase());
				consulta.setLong(7, solicitud.getId_mantenimiento());
				consulta.setLong(8, solicitud.getId_registro());
				consulta.setLong(9, solicitud.getId_campo());
				consulta.setLong(10, solicitud.getId_user_1());
				consulta.setLong(11, solicitud.getId_user_2());
				consulta.setLong(12, solicitud.getId_user_3());
				consulta.setLong(13, solicitud.getId_user_4());
				consulta.setLong(14, solicitud.getId_user_5());
				consulta.setTimestamp(15, solicitud.getFecha_1());
				consulta.setTimestamp(16, solicitud.getFecha_2());
				consulta.setTimestamp(17, solicitud.getFecha_3());
				consulta.setTimestamp(18, solicitud.getFecha_4());
				consulta.setTimestamp(19, solicitud.getFecha_5());
				consulta.setString(20, solicitud.getEst_solicitud());
				consulta.setString(21, solicitud.getUsu_modifica());
				consulta.setTimestamp(22, solicitud.getFec_modifica());
				consulta.setLong(23, solicitud.getId_solicitud());
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

	public void activarDesactivarRespaldo(modelo_respaldo respaldo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_desactivarRespaldo(?, ?, ?, ?)}");
			consulta.setString(1, respaldo.getEst_respaldo());
			consulta.setString(2, respaldo.getUsu_modifica());
			consulta.setTimestamp(3, respaldo.getFec_modifica());
			consulta.setLong(4, respaldo.getId_respaldo());
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

	public void actualizarEstadoRespaldo(modelo_respaldo respaldo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_actualizarEstadoRespaldo(?,?,?,?)}");
			consulta.setString(1, respaldo.getEst_respaldo());
			consulta.setString(2, respaldo.getUsu_modifica());
			consulta.setTimestamp(3, respaldo.getFec_modifica());
			consulta.setLong(4, respaldo.getId_respaldo());
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

	public void eliminarRespaldo(Long id_respaldo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL respaldo_eliminarRespaldo(?)}");
			consulta.setLong(1, id_respaldo);
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
