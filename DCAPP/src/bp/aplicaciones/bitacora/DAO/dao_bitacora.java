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

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.conexion.conexion;

public class dao_bitacora {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL bitacora_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_bitacora") + 1;
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

	public String obtenerTiempoTrabajado(String localidad, String fecha_inicio, String fecha_fin, String turno,
			String usuario, int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		String tiempo = "";
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL bitacora_obtenerTiempoTrabajado(?,?,?,?,?,?)}");
			consulta.setString(1, localidad);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, turno);
			consulta.setString(5, usuario);
			consulta.setInt(6, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				tiempo = resultado.getString("tiempo_trabajado");
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
		return tiempo;
	}

	public int validarSiExisteTareaRegistrada(String ticket_externo, String id_tipo_tarea)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalTareas = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL bitacora_validarSiTareaExiste(?,?)}");
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

	public int validarSiExisteTareaRegistradaTareaProveedor(String ticket_externo, String id_tipo_tarea)
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

	public int validarSiExistenTareasProgramadasVencidas(String estado)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalTareasVencidas = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL bitacora_validarSiExistenTareasProgramadasVencidas(?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, estado);
			consulta.execute();
			totalTareasVencidas = consulta.getInt(1);
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
		return totalTareasVencidas;
	}

	public int validarSiTareasAutomaticasSeCrearon(String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalTurnosCreados = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL bitacora_validarSiTareasAutomaticasSeCrearon(?, ?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.execute();
			totalTurnosCreados = consulta.getInt(1);
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
		return totalTurnosCreados;
	}

	public List<modelo_bitacora> obtenerBitacoras(String criterio, int tipo, long id, String fecha, String turno,
			long localidad, String fecha_inicio, String fecha_fin, long id_tipo_servicio, String use_usuario,
			int limite) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_bitacora> lista_bitacoras = new ArrayList<modelo_bitacora>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL bitacora_obtenerBitacoras(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
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
			consulta.setInt(11, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_bitacoras.add(new modelo_bitacora(resultado.getLong("id_bitacora"),
						resultado.getLong("id_cliente"), resultado.getString("nom_cliente"),
						resultado.getString("ticket_externo"), resultado.getLong("id_turno"),
						resultado.getString("nom_turno"), resultado.getLong("id_tipo_servicio"),
						resultado.getString("nom_tipo_servicio"), resultado.getLong("id_tipo_tarea"),
						resultado.getString("nom_tipo_tarea"), resultado.getLong("id_solicitante"),
						resultado.getString("nom_solicitante"), resultado.getString("area"),
						resultado.getString("rack"), resultado.getString("descripcion"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_estado_bitacora"), resultado.getString("nom_estado_bitacora"),
						resultado.getString("cumplimiento"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("est_bitacora"),
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
		return lista_bitacoras;
	}

	public void insertarTareas(List<modelo_bitacora> listaTareas, String etiqueta, long secuencia) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			for (int i = 0; i < listaTareas.size(); i++) {
				long id = obtenerNuevoId();
				long id1 = 0;
				if (id > secuencia) {
					id1 = id;
				} else {
					id1 = secuencia;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, listaTareas.get(i).getId_cliente());
				consulta.setString(3, etiqueta.toUpperCase() + id1);
				consulta.setLong(4, listaTareas.get(i).getId_turno());
				consulta.setLong(5, listaTareas.get(i).getId_tipo_servicio());
				consulta.setLong(6, listaTareas.get(i).getId_tipo_tarea());
				consulta.setLong(7, listaTareas.get(i).getId_solicitante());
				consulta.setString(8, listaTareas.get(i).getArea());
				consulta.setString(9, listaTareas.get(i).getRack());
				consulta.setString(10, listaTareas.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(11, listaTareas.get(i).getFec_inicio());
				consulta.setTimestamp(12, listaTareas.get(i).getFec_fin());
				consulta.setLong(13, listaTareas.get(i).getId_estado_bitacora());
				consulta.setString(14, listaTareas.get(i).getCumplimiento());
				consulta.setLong(15, listaTareas.get(i).getId_localidad());
				consulta.setString(16, listaTareas.get(i).getEst_bitacora());
				consulta.setString(17, listaTareas.get(i).getUsu_ingresa());
				consulta.setTimestamp(18, listaTareas.get(i).getFec_ingresa());
				consulta.executeUpdate();
				conexion.abrir().commit();
			}
			consulta.close();
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

	public void insertarBitacora(modelo_bitacora bitacora, long secuencia) throws SQLException,
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
					.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_tarea());
			consulta.setLong(7, bitacora.getId_solicitante());
			consulta.setString(8, bitacora.getArea());
			consulta.setString(9, bitacora.getRack());
			consulta.setString(10, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(11, bitacora.getFec_inicio());
			consulta.setTimestamp(12, bitacora.getFec_fin());
			consulta.setLong(13, bitacora.getId_estado_bitacora());
			consulta.setString(14, bitacora.getCumplimiento());
			consulta.setLong(15, bitacora.getId_localidad());
			consulta.setString(16, bitacora.getEst_bitacora());
			consulta.setString(17, bitacora.getUsu_ingresa());
			consulta.setTimestamp(18, bitacora.getFec_ingresa());
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

	public void insertarBitacora(modelo_bitacora bitacora, long secuencia1, modelo_tarea_proveedor tarea_proveedor,
			long secuencia2) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		dao_tarea_proveedor dao = new dao_tarea_proveedor();
		long id2 = obtenerNuevoId();
		long id1 = 0;
		if (id2 > secuencia1) {
			id1 = id2;
		} else {
			id1 = secuencia1;
		}
		long id3 = dao.obtenerNuevoId();
		long id4 = 0;
		if (id4 > secuencia2) {
			id4 = id3;
		} else {
			id4 = secuencia2;
		}
		try {
			PreparedStatement consulta = null;
			/* SE REGISTRA LA ACTIVIDAD EN LOG DE EVENTOS */
			consulta = conexion.abrir()
					.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_tarea());
			consulta.setLong(7, bitacora.getId_solicitante());
			consulta.setString(8, bitacora.getArea());
			consulta.setString(9, bitacora.getRack());
			consulta.setString(10, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(11, bitacora.getFec_inicio());
			consulta.setTimestamp(12, bitacora.getFec_fin());
			consulta.setLong(13, bitacora.getId_estado_bitacora());
			consulta.setString(14, bitacora.getCumplimiento());
			consulta.setLong(15, bitacora.getId_localidad());
			consulta.setString(16, bitacora.getEst_bitacora());
			consulta.setString(17, bitacora.getUsu_ingresa());
			consulta.setTimestamp(18, bitacora.getFec_ingresa());
			consulta.executeUpdate();
			/* SE REGISTRA LA TAREA DE PROVEEDOR */
			consulta = conexion.abrir().prepareStatement(
					"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, tarea_proveedor.getId_cliente());
			consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
			consulta.setLong(4, tarea_proveedor.getId_turno());
			consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
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
			consulta.executeUpdate();
			/* SI NO OCURRE ALGUN INCONVENIENTE SE REALIZA EL GUARDADO EN LA BD */
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

	public void modificarBitacora(modelo_bitacora bitacora, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL bitacora_modificarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, bitacora.getId_cliente());
			consulta.setString(2, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(3, bitacora.getId_turno());
			consulta.setLong(4, bitacora.getId_tipo_servicio());
			consulta.setLong(5, bitacora.getId_tipo_tarea());
			consulta.setLong(6, bitacora.getId_solicitante());
			consulta.setString(7, bitacora.getArea());
			consulta.setString(8, bitacora.getRack());
			consulta.setString(9, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(10, bitacora.getFec_inicio());
			consulta.setTimestamp(11, bitacora.getFec_fin());
			consulta.setLong(12, bitacora.getId_estado_bitacora());
			consulta.setString(13, bitacora.getCumplimiento());
			consulta.setLong(14, bitacora.getId_localidad());
			consulta.setString(15, bitacora.getEst_bitacora());
			consulta.setString(16, bitacora.getUsu_ingresa());
			consulta.setTimestamp(17, bitacora.getFec_ingresa());
			consulta.setString(18, bitacora.getUsu_modifica());
			consulta.setTimestamp(19, bitacora.getFec_modifica());
			consulta.setString(20, bitacora.getCor_revisa());
			consulta.setTimestamp(21, bitacora.getFec_revision());
			consulta.setString(22, bitacora.getObs_coordinador());
			consulta.setLong(23, tipo);
			consulta.setLong(24, bitacora.getId_bitacora());
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

	public void cambiarEstadoBitacora(modelo_bitacora bitacora) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_cambiarEstadoTareasAbiertas(?,?,?,?)}");
			consulta.setLong(1, bitacora.getId_estado_bitacora());
			consulta.setString(2, bitacora.getCumplimiento());
			consulta.setString(3, bitacora.getObs_coordinador());
			consulta.setLong(4, bitacora.getId_bitacora());
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

	public void cambiarEstadoBitacoraMasivo(List<modelo_bitacora> listaBitacora) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			Iterator<modelo_bitacora> it = listaBitacora.iterator();
			while (it.hasNext()) {
				consulta = conexion.abrir().prepareStatement("{CALL bitacora_cambiarEstadoBitacora(?,?,?,?)}");
				modelo_bitacora modelo = it.next();
				consulta.setLong(1, modelo.getId_estado_bitacora());
				consulta.setString(2, modelo.getUsu_modifica());
				consulta.setTimestamp(3, modelo.getFec_modifica());
				consulta.setLong(4, modelo.getId_bitacora());
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

	public void eliminarBitacora(Long id_bitacora) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_eliminarBitacora (?)}");
			consulta.setLong(1, id_bitacora);
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

	public void eliminarBitacoraMasivo(List<modelo_bitacora> listaBitacora) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			Iterator<modelo_bitacora> it = listaBitacora.iterator();
			while (it.hasNext()) {
				consulta = conexion.abrir().prepareStatement("{CALL bitacora_eliminarBitacora (?)}");
				modelo_bitacora modelo = it.next();
				consulta.setLong(1, modelo.getId_bitacora());
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
