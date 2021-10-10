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
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//Racks
public class dao_rack {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL rack_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_rack") + 1;
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

	public List<modelo_rack> obtenerRacks(String criterio, long id_localidad, long id_cliente, long id_fila, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_rack> lista_racks = new ArrayList<modelo_rack>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL rack_obtenerRacks(?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setLong(2, id_localidad);
			consulta.setLong(3, id_cliente);
			consulta.setLong(4, id_fila);
			consulta.setInt(5, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_racks.add(new modelo_rack(resultado.getLong("id_rack"), resultado.getString("coord_rack"),
						resultado.getLong("id_cliente"), resultado.getString("nom_cliente"),
						resultado.getString("nom_fila"), resultado.getLong("id_localidad"),
						resultado.getString("est_rack"), resultado.getString("usu_ingresa"),
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
		return lista_racks;
	}

	public void insertarRack(modelo_rack rack) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL rack_insertarRack(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, rack.getCoord_rack().toUpperCase());
			consulta.setLong(3, rack.getId_cliente());
			consulta.setLong(4, rack.getId_localidad());
			consulta.setString(5, rack.getEst_rack());
			consulta.setString(6, rack.getUsu_ingresa());
			consulta.setTimestamp(7, rack.getFec_ingresa());
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

	public void modificarRack(modelo_rack rack, modelo_solicitud solicitud, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL rack_modificarRack(?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, rack.getCoord_rack().toUpperCase());
			consulta.setLong(2, rack.getId_cliente());
			consulta.setLong(3, rack.getId_localidad());
			consulta.setString(4, rack.getEst_rack());
			consulta.setString(5, rack.getUsu_modifica());
			consulta.setTimestamp(6, rack.getFec_modifica());
			consulta.setLong(7, rack.getId_rack());
			consulta.setLong(8, tipo);
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

	public void activarDesactivarRack(modelo_rack rack) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL rack_desactivarRack(?, ?, ?, ?)}");
			consulta.setString(1, rack.getEst_rack());
			consulta.setString(2, rack.getUsu_modifica());
			consulta.setTimestamp(3, rack.getFec_modifica());
			consulta.setLong(4, rack.getId_rack());
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

	public void actualizarEstadoRack(modelo_rack rack) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL rack_actualizarEstadoRack(?,?,?,?)}");
			consulta.setString(1, rack.getEst_rack());
			consulta.setString(2, rack.getUsu_modifica());
			consulta.setTimestamp(3, rack.getFec_modifica());
			consulta.setLong(4, rack.getId_rack());
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

	public void eliminarRack(Long id_rack) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL rack_eliminarRack(?)}");
			consulta.setLong(1, id_rack);
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
