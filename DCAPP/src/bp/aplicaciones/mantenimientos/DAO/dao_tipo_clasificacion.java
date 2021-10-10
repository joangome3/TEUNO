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
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

public class dao_tipo_clasificacion {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_clasificacion_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_tipo_clasificacion") + 1;
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

	public List<modelo_tipo_clasificacion> obtenerTipoClasificaciones(String criterio, int tipo, long id, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tipo_clasificacion> lista_tipo_clasificacions = new ArrayList<modelo_tipo_clasificacion>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_clasificacion_obtenerTipoClasificaciones(?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setInt(4, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tipo_clasificacions.add(new modelo_tipo_clasificacion(resultado.getLong("id_tipo_clasificacion"),
						resultado.getLong("id_tipo_servicio"), resultado.getString("cod_tipo_clasificacion"),
						resultado.getString("nom_tipo_clasificacion"), resultado.getString("des_tipo_clasificacion"),
						resultado.getString("est_tipo_clasificacion"), resultado.getString("usu_ingresa"),
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
		return lista_tipo_clasificacions;
	}

	public void insertarTipoClasificacion(modelo_tipo_clasificacion tipo_clasificacion) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_clasificacion_insertarTipoClasificacion(?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, tipo_clasificacion.getId_tipo_servicio());
			consulta.setString(3, tipo_clasificacion.getCod_tipo_clasificacion());
			consulta.setString(4, tipo_clasificacion.getNom_tipo_clasificacion().toUpperCase());
			consulta.setString(5, tipo_clasificacion.getDes_tipo_clasificacion().toUpperCase());
			consulta.setString(6, tipo_clasificacion.getEst_tipo_clasificacion());
			consulta.setString(7, tipo_clasificacion.getUsu_ingresa());
			consulta.setTimestamp(8, tipo_clasificacion.getFec_ingresa());
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

	public void modificarTipoClasificacion(modelo_tipo_clasificacion tipo_clasificacion, modelo_solicitud solicitud,
			int tipo) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_clasificacion_modificarTipoClasificacion(?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, tipo_clasificacion.getId_tipo_servicio());
			consulta.setString(2, tipo_clasificacion.getCod_tipo_clasificacion());
			consulta.setString(3, tipo_clasificacion.getNom_tipo_clasificacion().toUpperCase());
			consulta.setString(4, tipo_clasificacion.getDes_tipo_clasificacion().toUpperCase());
			consulta.setString(5, tipo_clasificacion.getEst_tipo_clasificacion());
			consulta.setString(6, tipo_clasificacion.getUsu_modifica());
			consulta.setTimestamp(7, tipo_clasificacion.getFec_modifica());
			consulta.setLong(8, tipo_clasificacion.getId_tipo_clasificacion());
			consulta.setLong(9, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 3) {
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

	public void actualizarEstadoTipoClasificacion(modelo_tipo_clasificacion tipo_clasificacion) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_clasificacion_actualizarEstadoTipoClasificacion(?,?,?,?)}");
			consulta.setString(1, tipo_clasificacion.getEst_tipo_clasificacion());
			consulta.setString(2, tipo_clasificacion.getUsu_modifica());
			consulta.setTimestamp(3, tipo_clasificacion.getFec_modifica());
			consulta.setLong(4, tipo_clasificacion.getId_tipo_clasificacion());
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

	public void eliminarTipoClasificacion(long id_tipo_clasificacion, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_clasificacion_eliminarTipoClasificacion(?)}");
			consulta.setLong(1, id_tipo_clasificacion);
			if (tipo == 1) {
				consulta.executeUpdate();
			}
			if (solicitud.getId_mantenimiento() == 3) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL solicitud_modificarSolicitud(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, solicitud.getId_tip_solicitud());
				consulta.setString(2, solicitud.getComentario_1().toUpperCase());
				consulta.setString(3, solicitud.getComentario_2().toUpperCase());
				consulta.setString(4, solicitud.getComentario_3().toUpperCase());
				consulta.setLong(5, solicitud.getId_mantenimiento());
				consulta.setLong(6, solicitud.getId_registro());
				consulta.setLong(7, solicitud.getId_user_1());
				consulta.setLong(8, solicitud.getId_user_2());
				consulta.setLong(9, solicitud.getId_user_3());
				consulta.setString(10, solicitud.getEst_solicitud());
				consulta.setString(11, solicitud.getUsu_modifica());
				consulta.setTimestamp(12, solicitud.getFec_modifica());
				consulta.setLong(13, solicitud.getId_solicitud());
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
