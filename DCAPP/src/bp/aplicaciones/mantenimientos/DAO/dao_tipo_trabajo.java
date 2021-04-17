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
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_trabajo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//TipoDeTrabajos
public class dao_tipo_trabajo {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_tipo_trabajo") + 1;
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

	public List<modelo_tipo_trabajo> obtenerTipoTrabajos(String criterio)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tipo_trabajo> lista_tipo_trabajos = new ArrayList<modelo_tipo_trabajo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_obtenerTipoTrabajos(?)}");
			consulta.setString(1, criterio.toUpperCase());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tipo_trabajos.add(new modelo_tipo_trabajo(resultado.getLong("id_tipo_trabajo"),
						resultado.getString("nom_tipo_trabajo"), resultado.getString("des_tipo_trabajo"),
						resultado.getString("est_tipo_trabajo"), resultado.getString("usu_ingresa"),
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
		return lista_tipo_trabajos;
	}

	public void insertarTipoDeTrabajo(modelo_tipo_trabajo tipo_trabajo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_insertarTipoDeTrabajo(?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, tipo_trabajo.getNom_tipo_trabajo().toUpperCase());
			consulta.setString(3, tipo_trabajo.getDes_tipo_trabajo().toUpperCase());
			consulta.setString(4, tipo_trabajo.getEst_tipo_trabajo());
			consulta.setString(5, tipo_trabajo.getUsu_ingresa());
			consulta.setTimestamp(6, tipo_trabajo.getFec_ingresa());
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

	public void modificarTipoDeTrabajo(modelo_tipo_trabajo tipo_trabajo, modelo_solicitud solicitud, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_modificarTipoDeTrabajo(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, tipo_trabajo.getNom_tipo_trabajo().toUpperCase());
			consulta.setString(2, tipo_trabajo.getDes_tipo_trabajo().toUpperCase());
			consulta.setString(3, tipo_trabajo.getEst_tipo_trabajo());
			consulta.setString(4, tipo_trabajo.getUsu_modifica());
			consulta.setTimestamp(5, tipo_trabajo.getFec_modifica());
			consulta.setLong(6, tipo_trabajo.getId_tipo_trabajo());
			consulta.setLong(7, tipo);
			consulta.executeUpdate();
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

	public void activarDesactivarTipoDeTrabajo(modelo_tipo_trabajo tipo_trabajo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_desactivarTipoDeTrabajo(?, ?, ?, ?)}");
			consulta.setString(1, tipo_trabajo.getEst_tipo_trabajo());
			consulta.setString(2, tipo_trabajo.getUsu_modifica());
			consulta.setTimestamp(3, tipo_trabajo.getFec_modifica());
			consulta.setLong(4, tipo_trabajo.getId_tipo_trabajo());
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

	public void actualizarEstadoTipoDeTrabajo(modelo_tipo_trabajo tipo_trabajo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_actualizarEstadoTipoDeTrabajo(?,?,?,?)}");
			consulta.setString(1, tipo_trabajo.getEst_tipo_trabajo());
			consulta.setString(2, tipo_trabajo.getUsu_modifica());
			consulta.setTimestamp(3, tipo_trabajo.getFec_modifica());
			consulta.setLong(4, tipo_trabajo.getId_tipo_trabajo());
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

	public void eliminarTipoDeTrabajo(Long id_tipo_trabajo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_trabajo_eliminarTipoDeTrabajo(?)}");
			consulta.setLong(1, id_tipo_trabajo);
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
