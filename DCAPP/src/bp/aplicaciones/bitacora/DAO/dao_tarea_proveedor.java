package bp.aplicaciones.bitacora.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.conexion.conexion;

public class dao_tarea_proveedor {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL tarea_proveedor_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_tarea_proveedor") + 1;
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

	public int validarSiTareaProgramadaExiste(String ticket_externo, String id_tipo_tarea)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalTareas = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL tarea_proveedor_validarSiTareaProgramadaExiste(?,?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, ticket_externo);
			consulta.setString(3, id_tipo_tarea);
			consulta.execute();
			totalTareas = consulta.getInt(1);
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			if (consulta != null) {
				consulta.close();
			}
			if (conexion != null) {
				conexion.cerrar();
			}
		}
		return totalTareas;
	}

	public List<modelo_tarea_proveedor> obtenerTareasProveedores(String criterio, int tipo, long id, String fecha,
			String turno, long localidad, String fecha_inicio, String fecha_fin, long id_tipo_servicio,
			String use_usuario, long id_estado, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_tarea_proveedor> lista_TareasProveedores = new ArrayList<modelo_tarea_proveedor>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement(
					"{CALL tarea_proveedor_obtenerTareasProveedores(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setString(4, fecha);
			consulta.setString(5, turno);
			consulta.setLong(6, localidad);
			consulta.setString(7, fecha_inicio);
			consulta.setString(8, fecha_fin);
			consulta.setLong(9, id_tipo_servicio);
			consulta.setString(10, use_usuario);
			consulta.setLong(11, id_estado);
			consulta.setInt(12, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_TareasProveedores.add(new modelo_tarea_proveedor(resultado.getLong("id_tarea_proveedor"),
						resultado.getLong("id_cliente"), resultado.getString("nom_cliente"),
						resultado.getString("ticket_externo"), resultado.getLong("id_turno"),
						resultado.getString("nom_turno"), resultado.getLong("id_tipo_servicio"),
						resultado.getString("nom_tipo_servicio"), resultado.getLong("id_tipo_clasificacion"),
						resultado.getString("nom_tipo_clasificacion"), resultado.getLong("id_tipo_tarea"),
						resultado.getString("nom_tipo_tarea"), resultado.getLong("id_solicitante"),
						resultado.getString("nom_solicitante"), resultado.getString("area"),
						resultado.getString("rack"), resultado.getString("descripcion"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_estado_bitacora"), resultado.getString("nom_estado_bitacora"),
						resultado.getString("cumplimiento"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("est_tarea_proveedor"),
						resultado.getString("usu_ingresa"), resultado.getString("nom_usuario_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getString("nom_usuario_modifica"), resultado.getTimestamp("fec_modifica"),
						resultado.getString("cor_revisa"), resultado.getString("nom_usuario_revisa"),
						resultado.getTimestamp("fec_revision"), resultado.getString("obs_coordinador")));
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
		return lista_TareasProveedores;
	}

	public void insertarTareaProveedor(modelo_tarea_proveedor tarea_proveedor, long secuencia) throws SQLException,
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
			consulta = conexion.abrir().prepareStatement(
					"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, tarea_proveedor.getId_cliente());
			consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
			consulta.setLong(4, tarea_proveedor.getId_turno());
			consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
			consulta.setLong(6, tarea_proveedor.getId_tipo_clasificacion());
			consulta.setLong(7, tarea_proveedor.getId_tipo_tarea());
			consulta.setLong(8, tarea_proveedor.getId_solicitante());
			consulta.setString(9, tarea_proveedor.getArea());
			consulta.setString(10, tarea_proveedor.getRack());
			consulta.setString(11, tarea_proveedor.getDescripcion().toUpperCase());
			consulta.setTimestamp(12, tarea_proveedor.getFec_inicio());
			consulta.setTimestamp(13, tarea_proveedor.getFec_fin());
			consulta.setLong(14, tarea_proveedor.getId_estado_bitacora());
			consulta.setString(15, tarea_proveedor.getCumplimiento());
			consulta.setLong(16, tarea_proveedor.getId_localidad());
			consulta.setString(17, tarea_proveedor.getEst_tarea_proveedor());
			consulta.setString(18, tarea_proveedor.getUsu_ingresa());
			consulta.setTimestamp(19, tarea_proveedor.getFec_ingresa());
			consulta.setString(20, tarea_proveedor.getUsu_ingresa());
			consulta.setTimestamp(21, tarea_proveedor.getFec_ingresa());
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

	public void modificarTareaProveedor(modelo_tarea_proveedor tarea_proveedor, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL tarea_proveedor_modificarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, tarea_proveedor.getId_cliente());
			consulta.setString(2, tarea_proveedor.getTicket_externo().toUpperCase());
			consulta.setLong(3, tarea_proveedor.getId_turno());
			consulta.setLong(4, tarea_proveedor.getId_tipo_servicio());
			consulta.setLong(5, tarea_proveedor.getId_tipo_clasificacion());
			consulta.setLong(6, tarea_proveedor.getId_tipo_tarea());
			consulta.setLong(7, tarea_proveedor.getId_solicitante());
			consulta.setString(8, tarea_proveedor.getArea());
			consulta.setString(9, tarea_proveedor.getRack());
			consulta.setString(10, tarea_proveedor.getDescripcion().toUpperCase());
			consulta.setTimestamp(11, tarea_proveedor.getFec_inicio());
			consulta.setTimestamp(12, tarea_proveedor.getFec_fin());
			consulta.setLong(13, tarea_proveedor.getId_estado_bitacora());
			consulta.setString(14, tarea_proveedor.getCumplimiento());
			consulta.setLong(15, tarea_proveedor.getId_localidad());
			consulta.setString(16, tarea_proveedor.getEst_tarea_proveedor());
			consulta.setString(17, tarea_proveedor.getUsu_ingresa());
			consulta.setTimestamp(18, tarea_proveedor.getFec_ingresa());
			consulta.setString(19, tarea_proveedor.getUsu_modifica());
			consulta.setTimestamp(20, tarea_proveedor.getFec_modifica());
			consulta.setString(21, tarea_proveedor.getCor_revisa());
			consulta.setTimestamp(22, tarea_proveedor.getFec_revision());
			consulta.setString(23, tarea_proveedor.getObs_coordinador());
			consulta.setLong(24, tipo);
			consulta.setLong(25, tarea_proveedor.getId_tarea_proveedor());
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

	public void cambiarEstadoTareasProgramadasMasivo(List<modelo_tarea_proveedor> modelo_tarea_proveedor)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			Iterator<modelo_tarea_proveedor> it = modelo_tarea_proveedor.iterator();
			while (it.hasNext()) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL tarea_proveedor_cambiarEstadoTareaProveedor (?,?,?,?)}");
				modelo_tarea_proveedor modelo = it.next();
				consulta.setLong(1, modelo.getId_estado_bitacora());
				consulta.setString(2, modelo.getUsu_modifica());
				consulta.setTimestamp(3, modelo.getFec_modifica());
				consulta.setLong(4, modelo.getId_tarea_proveedor());
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

	public void eliminarTareaProveedor(Long id_tarea_proveedor) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL tarea_proveedor_eliminarTareaProveedor (?)}");
			consulta.setLong(1, id_tarea_proveedor);
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

	public void eliminarTareasProgranadasMasivo(List<modelo_tarea_proveedor> listaTareaProveedor) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			Iterator<modelo_tarea_proveedor> it = listaTareaProveedor.iterator();
			while (it.hasNext()) {
				consulta = conexion.abrir().prepareStatement("{CALL tarea_proveedor_eliminarTareaProveedor (?)}");
				modelo_tarea_proveedor modelo = it.next();
				consulta.setLong(1, modelo.getId_tarea_proveedor());
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
