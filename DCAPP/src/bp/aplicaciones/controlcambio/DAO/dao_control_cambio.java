package bp.aplicaciones.controlcambio.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;

public class dao_control_cambio {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL control_cambio_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_control_cambio") + 1;
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

	public List<modelo_control_cambio> obtenerControlCambios(String criterio, int tipo, long id, long localidad,
			int limite) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_control_cambio> lista_control_cambios = new ArrayList<modelo_control_cambio>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL control_cambio_obtenerControlCambios(?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setLong(4, localidad);
			consulta.setInt(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_control_cambios.add(new modelo_control_cambio(resultado.getLong("id_control_cambio"),
						resultado.getLong("id_empresa_1"), resultado.getLong("id_empresa_2"),
						resultado.getLong("id_tipo_sistema"), resultado.getLong("id_infraestructura"),
						resultado.getLong("id_tipo_mantenimiento"), resultado.getLong("id_criticidad"),
						resultado.getLong("id_solicitante_1"), resultado.getTimestamp("fec_programada"),
						resultado.getString("descripcion"), resultado.getLong("id_localidad"),
						resultado.getString("est_control_cambio"), resultado.getString("usu_ingresa"),
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
		return lista_control_cambios;
	}

	public long insertarControlCambio(modelo_control_cambio control_cambio, long secuencia) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		long id1 = 0;
		if (id > secuencia) {
			id1 = id;
		} else {
			id1 = secuencia;
		}
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL control_cambio_insertarControlCambio(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, control_cambio.getId_empresa_1());
			consulta.setLong(3, control_cambio.getId_empresa_2());
			consulta.setLong(4, control_cambio.getId_tipo_sistema());
			consulta.setLong(5, control_cambio.getId_infraestructura());
			consulta.setLong(6, control_cambio.getId_tipo_mantenimiento());
			consulta.setLong(7, control_cambio.getId_criticidad());
			consulta.setLong(8, control_cambio.getId_solicitante_1());
			consulta.setTimestamp(9, control_cambio.getFec_programada());
			consulta.setString(10, control_cambio.getDescripcion().toUpperCase());
			consulta.setLong(11, control_cambio.getId_localidad());
			consulta.setString(12, control_cambio.getEst_control_cambio());
			consulta.setString(13, control_cambio.getUsu_ingresa());
			consulta.setTimestamp(14, control_cambio.getFec_ingresa());
			consulta.executeUpdate();
			consulta.close();
		} catch (SQLException ex) {
			id1 = 0;
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			id1 = 0;
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
			id1 = 0;
		}
		return id1;
	}

	public void modificarControlCambio(modelo_control_cambio control_cambio) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL control_cambio_modificarControlCambio(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, control_cambio.getId_empresa_1());
			consulta.setLong(2, control_cambio.getId_empresa_2());
			consulta.setLong(3, control_cambio.getId_tipo_sistema());
			consulta.setLong(4, control_cambio.getId_infraestructura());
			consulta.setLong(5, control_cambio.getId_tipo_mantenimiento());
			consulta.setLong(6, control_cambio.getId_criticidad());
			consulta.setLong(7, control_cambio.getId_solicitante_1());
			consulta.setTimestamp(8, control_cambio.getFec_programada());
			consulta.setString(9, control_cambio.getDescripcion().toUpperCase());
			consulta.setLong(10, control_cambio.getId_localidad());
			consulta.setString(11, control_cambio.getEst_control_cambio());
			consulta.setString(12, control_cambio.getUsu_modifica());
			consulta.setTimestamp(13, control_cambio.getFec_modifica());
			consulta.setLong(14, control_cambio.getId_control_cambio());
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

	public void eliminarControlCambio(Long id_control_cambio) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL control_cambio_eliminarControlCambio (?)}");
			consulta.setLong(1, id_control_cambio);
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
