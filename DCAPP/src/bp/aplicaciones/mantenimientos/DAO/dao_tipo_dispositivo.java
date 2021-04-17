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
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_dispositivo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//TipoDeDispositivos
public class dao_tipo_dispositivo {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL tipo_dispositivo_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_tipo_dispositivo") + 1;
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

	public List<modelo_tipo_dispositivo> obtenerTipoDispositivos(String criterio)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tipo_dispositivo> lista_tipo_dispositivos = new ArrayList<modelo_tipo_dispositivo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL tipo_dispositivo_obtenerTipoDispositivos(?)}");
			consulta.setString(1, criterio.toUpperCase());
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tipo_dispositivos.add(new modelo_tipo_dispositivo(resultado.getLong("id_tipo_dispositivo"),
						resultado.getString("nom_tipo_dispositivo"), resultado.getString("des_tipo_dispositivo"),
						resultado.getString("est_tipo_dispositivo"), resultado.getString("usu_ingresa"),
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
		return lista_tipo_dispositivos;
	}

	public void insertarTipoDeDispositivo(modelo_tipo_dispositivo tipo_dispositivo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_dispositivo_insertarTipoDeDispositivo(?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, tipo_dispositivo.getNom_tipo_dispositivo().toUpperCase());
			consulta.setString(3, tipo_dispositivo.getDes_tipo_dispositivo().toUpperCase());
			consulta.setString(4, tipo_dispositivo.getEst_tipo_dispositivo());
			consulta.setString(5, tipo_dispositivo.getUsu_ingresa());
			consulta.setTimestamp(6, tipo_dispositivo.getFec_ingresa());
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

	public void modificarTipoDeDispositivo(modelo_tipo_dispositivo tipo_dispositivo, modelo_solicitud solicitud,
			int tipo) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_dispositivo_modificarTipoDeDispositivo(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, tipo_dispositivo.getNom_tipo_dispositivo().toUpperCase());
			consulta.setString(2, tipo_dispositivo.getDes_tipo_dispositivo().toUpperCase());
			consulta.setString(3, tipo_dispositivo.getEst_tipo_dispositivo());
			consulta.setString(4, tipo_dispositivo.getUsu_modifica());
			consulta.setTimestamp(5, tipo_dispositivo.getFec_modifica());
			consulta.setLong(6, tipo_dispositivo.getId_tipo_dispositivo());
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

	public void activarDesactivarTipoDeDispositivo(modelo_tipo_dispositivo tipo_dispositivo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_dispositivo_desactivarTipoDeDispositivo(?, ?, ?, ?)}");
			consulta.setString(1, tipo_dispositivo.getEst_tipo_dispositivo());
			consulta.setString(2, tipo_dispositivo.getUsu_modifica());
			consulta.setTimestamp(3, tipo_dispositivo.getFec_modifica());
			consulta.setLong(4, tipo_dispositivo.getId_tipo_dispositivo());
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

	public void actualizarEstadoTipoDeDispositivo(modelo_tipo_dispositivo tipo_dispositivo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tipo_dispositivo_actualizarEstadoTipoDeDispositivo(?,?,?,?)}");
			consulta.setString(1, tipo_dispositivo.getEst_tipo_dispositivo());
			consulta.setString(2, tipo_dispositivo.getUsu_modifica());
			consulta.setTimestamp(3, tipo_dispositivo.getFec_modifica());
			consulta.setLong(4, tipo_dispositivo.getId_tipo_dispositivo());
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

	public void eliminarTipoDeDispositivo(Long id_tipo_dispositivo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tipo_dispositivo_eliminarTipoDeDispositivo(?)}");
			consulta.setLong(1, id_tipo_dispositivo);
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
