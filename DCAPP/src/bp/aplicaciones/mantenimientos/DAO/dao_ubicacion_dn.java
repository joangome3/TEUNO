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
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;

public class dao_ubicacion_dn {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL ubicacionDN_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_ubicacion") + 1;
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

	public List<modelo_ubicacion_dn> obtenerUbicaciones(String criterio, String localidad, int tipo, long empresa,
			long tipo_ubicacion, long id, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_ubicacion_dn> lista_ubicacions = new ArrayList<modelo_ubicacion_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL ubicacionDN_obtenerUbicaciones(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setString(2, localidad);
			consulta.setInt(3, tipo);
			consulta.setLong(4, empresa);
			consulta.setLong(5, tipo_ubicacion);
			consulta.setLong(6, id);
			consulta.setLong(7, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_ubicacions.add(new modelo_ubicacion_dn(resultado.getLong("id_ubicacion"),
						resultado.getLong("id_empresa"), resultado.getString("nom_empresa"),
						resultado.getLong("id_tip_ubicacion"), resultado.getString("nom_tip_ubicacion"),
						resultado.getString("pos_ubicacion"), resultado.getString("val_capacidad"),
						resultado.getInt("cap_ubicacion"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("est_ubicacion"),
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
		return lista_ubicacions;
	}

	public void insertarUbicacion(modelo_ubicacion_dn ubicacion) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL ubicacionDN_insertarUbicacion (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, ubicacion.getId_empresa());
			consulta.setLong(3, ubicacion.getId_tip_ubicacion());
			consulta.setString(4, ubicacion.getPos_ubicacion().toUpperCase());
			consulta.setString(5, ubicacion.getVal_capacidad().toUpperCase());
			consulta.setInt(6, ubicacion.getCap_ubicacion());
			consulta.setLong(7, ubicacion.getId_localidad());
			consulta.setString(8, ubicacion.getEst_ubicacion());
			consulta.setString(9, ubicacion.getUsu_ingresa());
			consulta.setTimestamp(10, ubicacion.getFec_ingresa());
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

	public void modificarUbicacion(modelo_ubicacion_dn ubicacion, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL ubicacionDN_modificarUbicacion (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, ubicacion.getId_empresa());
			consulta.setLong(2, ubicacion.getId_tip_ubicacion());
			consulta.setString(3, ubicacion.getPos_ubicacion().toUpperCase());
			consulta.setString(4, ubicacion.getVal_capacidad().toUpperCase());
			consulta.setInt(5, ubicacion.getCap_ubicacion());
			consulta.setLong(6, ubicacion.getId_localidad());
			consulta.setString(7, ubicacion.getEst_ubicacion());
			consulta.setString(8, ubicacion.getUsu_modifica());
			consulta.setTimestamp(9, ubicacion.getFec_modifica());
			consulta.setLong(10, ubicacion.getId_ubicacion());
			consulta.setLong(11, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 15) {
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

	public void activarDesactivarUbicacion(modelo_ubicacion_dn ubicacion) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL ubicacionDN_desactivarUbicacion (?, ?, ?, ?)}");
			consulta.setString(1, ubicacion.getEst_ubicacion());
			consulta.setString(2, ubicacion.getUsu_modifica());
			consulta.setTimestamp(3, ubicacion.getFec_modifica());
			consulta.setLong(4, ubicacion.getId_ubicacion());
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

	public void eliminarUbicacion(Long id_ubicacion) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL ubicacionDN_eliminarUbicacion (?)}");
			consulta.setLong(1, id_ubicacion);
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
