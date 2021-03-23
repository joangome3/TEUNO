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
import bp.aplicaciones.mantenimientos.modelo.modelo_infraestructura;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

public class dao_infraestructura {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL infraestructura_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_infraestructura") + 1;
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

	public List<modelo_infraestructura> obtenerInfraestructuras(String criterio, int tipo, long id, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_infraestructura> lista_infraestructuras = new ArrayList<modelo_infraestructura>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL infraestructura_obtenerInfraestructuras(?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setInt(4, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_infraestructuras.add(new modelo_infraestructura(resultado.getLong("id_infraestructura"),
						resultado.getLong("id_tipo_sistema"), resultado.getString("nom_infraestructura"),
						resultado.getString("des_infraestructura"), resultado.getString("est_infraestructura"),
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
		return lista_infraestructuras;
	}

	public void insertarInfraestructura(modelo_infraestructura infraestructura) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL infraestructura_insertarInfraestructura(?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, infraestructura.getId_tipo_sistema());
			consulta.setString(3, infraestructura.getNom_infraestructura().toUpperCase());
			consulta.setString(4, infraestructura.getDes_infraestructura().toUpperCase());
			consulta.setString(5, infraestructura.getEst_infraestructura());
			consulta.setString(6, infraestructura.getUsu_ingresa());
			consulta.setTimestamp(7, infraestructura.getFec_ingresa());
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

	public void modificarInfraestructura(modelo_infraestructura infraestructura, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL infraestructura_modificarInfraestructura(?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, infraestructura.getId_tipo_sistema());
			consulta.setString(2, infraestructura.getNom_infraestructura().toUpperCase());
			consulta.setString(3, infraestructura.getDes_infraestructura().toUpperCase());
			consulta.setString(4, infraestructura.getEst_infraestructura());
			consulta.setString(5, infraestructura.getUsu_modifica());
			consulta.setTimestamp(6, infraestructura.getFec_modifica());
			consulta.setLong(7, infraestructura.getId_infraestructura());
			consulta.setLong(8, tipo);
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

	public void actualizarEstadoInfraestructura(modelo_infraestructura infraestructura) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL infraestructura_actualizarEstadoInfraestructura(?,?,?,?)}");
			consulta.setString(1, infraestructura.getEst_infraestructura());
			consulta.setString(2, infraestructura.getUsu_modifica());
			consulta.setTimestamp(3, infraestructura.getFec_modifica());
			consulta.setLong(4, infraestructura.getId_infraestructura());
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

	public void eliminarInfraestructura(long id_infraestructura, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL infraestructura_eliminarInfraestructura(?)}");
			consulta.setLong(1, id_infraestructura);
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
