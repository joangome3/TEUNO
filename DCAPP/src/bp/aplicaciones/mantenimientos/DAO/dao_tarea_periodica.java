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
import bp.aplicaciones.mantenimientos.modelo.modelo_tarea_periodica;

public class dao_tarea_periodica {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL tarea_periodica_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_tarea_periodica") + 1;
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

	public List<modelo_tarea_periodica> obtenerTareasPeriodicas(String criterio, int tipo, long id, String fecha,
			String turno, int limite) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tarea_periodica> lista_tareas_periodicas = new ArrayList<modelo_tarea_periodica>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL tarea_periodica_obtenerTareasPeriodicas(?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setString(4, fecha);
			consulta.setString(5, turno);
			consulta.setInt(6, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_tareas_periodicas.add(new modelo_tarea_periodica(resultado.getLong("id_tarea_periodica"),
						resultado.getLong("id_cliente"), resultado.getString("ticket_externo"),
						resultado.getLong("id_turno"), resultado.getLong("id_tipo_servicio"),
						resultado.getLong("id_tipo_tarea"), resultado.getString("descripcion"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_estado_bitacora"), resultado.getString("cumplimiento"),
						resultado.getString("est_tarea_periodica"), resultado.getString("usu_ingresa"),
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
		return lista_tareas_periodicas;
	}

	public void insertarTareaPeriodica(modelo_tarea_periodica tarea_periodica) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tarea_periodica_insertarTareaPeriodica(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, tarea_periodica.getId_cliente());
			consulta.setString(3, tarea_periodica.getTicket_externo());
			consulta.setLong(4, tarea_periodica.getId_turno());
			consulta.setLong(5, tarea_periodica.getId_tipo_servicio());
			consulta.setLong(6, tarea_periodica.getId_tipo_tarea());
			consulta.setString(7, tarea_periodica.getDescripcion());
			consulta.setTimestamp(8, tarea_periodica.getFec_inicio());
			consulta.setTimestamp(9, tarea_periodica.getFec_fin());
			consulta.setLong(10, tarea_periodica.getId_estado_bitacora());
			consulta.setString(11, tarea_periodica.getCumplimiento());
			consulta.setString(12, tarea_periodica.getEst_tarea_periodica());
			consulta.setString(13, tarea_periodica.getUsu_ingresa());
			consulta.setTimestamp(14, tarea_periodica.getFec_ingresa());
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

	public void modificarTareaPeriodica(modelo_tarea_periodica tarea_periodica) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL tarea_periodica_modificarTareaPeriodica(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, tarea_periodica.getId_cliente());
			consulta.setString(2, tarea_periodica.getTicket_externo());
			consulta.setLong(3, tarea_periodica.getId_turno());
			consulta.setLong(4, tarea_periodica.getId_tipo_servicio());
			consulta.setLong(5, tarea_periodica.getId_tipo_tarea());
			consulta.setString(6, tarea_periodica.getDescripcion());
			consulta.setTimestamp(7, tarea_periodica.getFec_inicio());
			consulta.setTimestamp(8, tarea_periodica.getFec_fin());
			consulta.setLong(9, tarea_periodica.getId_estado_bitacora());
			consulta.setString(10, tarea_periodica.getCumplimiento());
			consulta.setString(11, tarea_periodica.getEst_tarea_periodica());
			consulta.setString(12, tarea_periodica.getUsu_modifica());
			consulta.setTimestamp(13, tarea_periodica.getFec_modifica());
			consulta.setLong(14, tarea_periodica.getId_tarea_periodica());
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

	public void eliminarTareaPeriodica(Long id_tarea_periodica) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tarea_periodica_eliminarTareaPeriodica (?)}");
			consulta.setLong(1, id_tarea_periodica);
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
