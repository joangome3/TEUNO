package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.conexion.conexion;

public class dao_registra_turno {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL registra_turno_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_registra_turno") + 1;
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

	public List<modelo_registra_turno> obtenerRegistroTurnos(String criterio, int tipo, long id_turno, String fecha,
			long localidad) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_registra_turno> lista_registro_turnos = new ArrayList<modelo_registra_turno>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL registra_turno_obtenerRegistroTurnos(?,?,?,?,?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id_turno);
			consulta.setString(4, fecha);
			consulta.setLong(5, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_registro_turnos.add(
						new modelo_registra_turno(resultado.getLong("id_registra_turno"), resultado.getLong("id_turno"),
								resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
								resultado.getLong("id_localidad"), resultado.getString("est_registra_turno"),
								resultado.getString("usu_inicia"), resultado.getString("usu_termina")));

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
		return lista_registro_turnos;
	}

	public void insertarRegistroTurno(modelo_registra_turno registra_turno) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL registra_turno_insertarRegistroTurno(?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, registra_turno.getId_turno());
			consulta.setTimestamp(3, registra_turno.getFec_inicio());
			consulta.setTimestamp(4, registra_turno.getFec_fin());
			consulta.setLong(5, registra_turno.getId_localidad());
			consulta.setString(6, registra_turno.getEst_registra_turno());
			consulta.setString(7, registra_turno.getUsu_inicia());
			consulta.setString(8, registra_turno.getUsu_termina());
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

	public void modificarRegistroTurno(modelo_registra_turno registra_turno) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL registra_turno_modificarRegistroTurno(?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, registra_turno.getId_turno());
			consulta.setTimestamp(2, registra_turno.getFec_inicio());
			consulta.setTimestamp(3, registra_turno.getFec_fin());
			consulta.setLong(4, registra_turno.getId_localidad());
			consulta.setString(5, registra_turno.getEst_registra_turno());
			consulta.setString(6, registra_turno.getUsu_inicia());
			consulta.setString(7, registra_turno.getUsu_termina());
			consulta.setLong(8, registra_turno.getId_registra_turno());
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
