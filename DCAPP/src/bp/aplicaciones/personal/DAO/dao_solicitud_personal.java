package bp.aplicaciones.personal.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.personal.modelo.modelo_detalle_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_registro_permanencia;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;

public class dao_solicitud_personal {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL personal_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_solicitud") + 1;
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

	public Long obtenerNuevoIdRegistroPermanencia()
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerNuevoIDRegistroPermanencia()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_registro_permanencia") + 1;
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

	public int validarSiExisteSolicitudPersonal(String ticket)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalSolicitudes = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL personal_validarSiSolicitudPersonalExiste(?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, ticket);
			consulta.execute();
			totalSolicitudes = consulta.getInt(1);
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
		return totalSolicitudes;
	}

	public List<modelo_solicitud_personal> obtenerSolicitudesPersonal(String criterio, int tipo, long id_cliente,
			String fecha_solicitud_1, String fecha_solicitud_2, String fecha_inicio, String fecha_fin,
			long id_localidad, String estado, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_solicitud_personal> lista_solicitudes = new ArrayList<modelo_solicitud_personal>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerSolicitudesPersonal(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id_cliente);
			consulta.setString(4, fecha_solicitud_1);
			consulta.setString(5, fecha_solicitud_2);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			consulta.setLong(8, id_localidad);
			consulta.setString(9, estado);
			consulta.setInt(10, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_solicitudes.add(new modelo_solicitud_personal(resultado.getLong("id_solicitud"),
						resultado.getLong("id_cliente"), resultado.getString("nom_cliente"),
						resultado.getString("ticket"), resultado.getLong("id_tipo_ingreso"),
						resultado.getString("nom_tipo_ingreso"), resultado.getLong("id_tipo_aprobador"),
						resultado.getString("nom_tipo_aprobador"), resultado.getLong("id_solicitante"),
						resultado.getString("nom_solicitante"), resultado.getLong("id_tipo_trabajo"),
						resultado.getString("nom_tipo_trabajo"), resultado.getString("id_area"),
						resultado.getString("id_rack"), resultado.getString("area"), resultado.getString("rack"),
						resultado.getTimestamp("fec_solicitud"), resultado.getTimestamp("fec_respuesta"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getString("descripcion"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("est_solicitud"),
						resultado.getString("usu_ingresa"), resultado.getString("nom_usuario_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getString("nom_usuario_modifica"), resultado.getTimestamp("fec_modifica")));
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
		return lista_solicitudes;
	}

	public List<modelo_detalle_solicitud_personal> obtenerDetalleSolicitudPersonal(String criterio, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_detalle_solicitud_personal> lista_detalle_solicitud = new ArrayList<modelo_detalle_solicitud_personal>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL personal_obtenerDetalleSolicitudPersonal(?, ?)}");
			consulta.setString(1, criterio);
			consulta.setInt(2, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_detalle_solicitud.add(new modelo_detalle_solicitud_personal(
						resultado.getLong("id_detalle_solicitud"), resultado.getLong("id_solicitud"),
						resultado.getLong("id_proveedor"), resultado.getString("num_documento_proveedor"),
						resultado.getString("nom_proveedor"), resultado.getLong("id_emp_proveedor"),
						resultado.getString("nom_emp_proveedor"), resultado.getLong("id_dispositivo"),
						resultado.getString("nom_dispositivo"), resultado.getString("est_detalle_solicitud"),
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
		return lista_detalle_solicitud;
	}

	public void insertarSolicitudPersonal(modelo_solicitud_personal solicitud,
			List<modelo_detalle_solicitud_personal> detalle, modelo_bitacora bitacora, long secuencia1,
			modelo_tarea_proveedor tarea_proveedor, long secuencia2, int bandera) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL personal_insertarSolicitudPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, solicitud.getId_cliente());
			consulta.setString(3, solicitud.getTicket().toUpperCase().trim());
			consulta.setLong(4, solicitud.getId_tipo_ingreso());
			consulta.setLong(5, solicitud.getId_tipo_aprobador());
			consulta.setLong(6, solicitud.getId_solicitante());
			consulta.setLong(7, solicitud.getId_tipo_trabajo());
			consulta.setString(8, solicitud.getId_area());
			consulta.setString(9, solicitud.getId_rack());
			consulta.setString(10, solicitud.getArea());
			consulta.setString(11, solicitud.getRack());
			consulta.setTimestamp(12, solicitud.getFec_solicitud());
			consulta.setTimestamp(13, solicitud.getFec_respuesta());
			consulta.setTimestamp(14, solicitud.getFec_inicio());
			consulta.setTimestamp(15, solicitud.getFec_fin());
			consulta.setString(16, solicitud.getDescripcion());
			consulta.setLong(17, solicitud.getId_localidad());
			consulta.setString(18, solicitud.getEst_solicitud());
			consulta.setString(19, solicitud.getUsu_ingresa());
			consulta.setTimestamp(20, solicitud.getFec_ingresa());
			consulta.executeUpdate();
			/* INSERTAMOS EL DETALLE DE LA SOLICITUD */
			for (int i = 0; i < detalle.size(); i++) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL personal_insertarDetalleSolicitudPersonal(?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, detalle.get(i).getId_proveedor());
				consulta.setLong(3, detalle.get(i).getId_dispositivo());
				consulta.setString(4, detalle.get(i).getEst_detalle_solicitud());
				consulta.setString(5, detalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, detalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			id = 0;
			dao_bitacora dao1 = new dao_bitacora();
			id = dao1.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia1) {
				id1 = id;
			} else {
				id1 = secuencia1;
			}
			consulta = conexion.abrir().prepareStatement(
					"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_clasificacion());
			consulta.setLong(7, bitacora.getId_tipo_tarea());
			consulta.setLong(8, bitacora.getId_solicitante());
			consulta.setString(9, bitacora.getId_area());
			consulta.setString(10, bitacora.getId_rack());
			consulta.setString(11, bitacora.getArea());
			consulta.setString(12, bitacora.getRack());
			consulta.setString(13, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(14, bitacora.getFec_inicio());
			consulta.setTimestamp(15, bitacora.getFec_fin());
			consulta.setLong(16, bitacora.getId_estado_bitacora());
			consulta.setString(17, bitacora.getCumplimiento());
			consulta.setString(18, bitacora.getCumplimientoSLA());
			consulta.setString(19, bitacora.getComentarioCumplimientoSLA());
			consulta.setLong(20, bitacora.getId_localidad());
			consulta.setString(21, bitacora.getEst_bitacora());
			consulta.setString(22, bitacora.getUsu_ingresa());
			consulta.setTimestamp(23, bitacora.getFec_ingresa());
			consulta.setString(24, bitacora.getUsu_modifica());
			consulta.setTimestamp(25, bitacora.getFec_modifica());
			consulta.executeUpdate();
			/* REGISTRO TAREA DE PROVEEDOR */
			if (bandera == 1) {
				id = 0;
				dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
				id = dao2.obtenerNuevoId();
				id1 = 0;
				if (id > secuencia2) {
					id1 = id;
				} else {
					id1 = secuencia2;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, tarea_proveedor.getId_cliente());
				consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
				consulta.setLong(4, tarea_proveedor.getId_turno());
				consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
				consulta.setLong(6, tarea_proveedor.getId_tipo_clasificacion());
				consulta.setLong(7, tarea_proveedor.getId_tipo_tarea());
				consulta.setLong(8, tarea_proveedor.getId_solicitante());
				consulta.setString(9, tarea_proveedor.getId_area());
				consulta.setString(10, tarea_proveedor.getId_rack());
				consulta.setString(11, tarea_proveedor.getArea());
				consulta.setString(12, tarea_proveedor.getRack());
				consulta.setString(13, tarea_proveedor.getDescripcion().toUpperCase());
				consulta.setTimestamp(14, tarea_proveedor.getFec_inicio());
				consulta.setTimestamp(15, tarea_proveedor.getFec_fin());
				consulta.setLong(16, tarea_proveedor.getId_estado_bitacora());
				consulta.setString(17, tarea_proveedor.getCumplimiento());
				consulta.setLong(18, tarea_proveedor.getId_localidad());
				consulta.setString(19, tarea_proveedor.getEst_tarea_proveedor());
				consulta.setString(20, tarea_proveedor.getUsu_ingresa());
				consulta.setTimestamp(21, tarea_proveedor.getFec_ingresa());
				consulta.setString(22, bitacora.getUsu_modifica());
				consulta.setTimestamp(23, bitacora.getFec_modifica());
				consulta.executeUpdate();
			}
			/*
			 * SI NO SE PRESENTAN ERRORES SE APLICAN LOS CAMBIOS Y SE CIERRA LA INSTANCIA DE
			 * LA BD
			 */
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

	public void insertarSolicitudPersonal1(modelo_solicitud_personal solicitud,
			List<modelo_detalle_solicitud_personal> detalle, modelo_bitacora bitacora, long secuencia1,
			modelo_tarea_proveedor tarea_proveedor, long secuencia2, int bandera,
			modelo_solicitud_personal solicitud_referencia) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL personal_insertarSolicitudPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, solicitud.getId_cliente());
			consulta.setString(3, solicitud.getTicket().toUpperCase().trim());
			consulta.setLong(4, solicitud.getId_tipo_ingreso());
			consulta.setLong(5, solicitud.getId_tipo_aprobador());
			consulta.setLong(6, solicitud.getId_solicitante());
			consulta.setLong(7, solicitud.getId_tipo_trabajo());
			consulta.setString(8, solicitud.getId_area());
			consulta.setString(9, solicitud.getId_rack());
			consulta.setString(10, solicitud.getArea());
			consulta.setString(11, solicitud.getRack());
			consulta.setTimestamp(12, solicitud.getFec_solicitud());
			consulta.setTimestamp(13, solicitud.getFec_respuesta());
			consulta.setTimestamp(14, solicitud.getFec_inicio());
			consulta.setTimestamp(15, solicitud.getFec_fin());
			consulta.setString(16, solicitud.getDescripcion());
			consulta.setLong(17, solicitud.getId_localidad());
			consulta.setString(18, solicitud.getEst_solicitud());
			consulta.setString(19, solicitud.getUsu_ingresa());
			consulta.setTimestamp(20, solicitud.getFec_ingresa());
			consulta.executeUpdate();
			/* INSERTAMOS EL DETALLE DE LA SOLICITUD */
			for (int i = 0; i < detalle.size(); i++) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL personal_insertarDetalleSolicitudPersonal(?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, detalle.get(i).getId_proveedor());
				consulta.setLong(3, detalle.get(i).getId_dispositivo());
				consulta.setString(4, detalle.get(i).getEst_detalle_solicitud());
				consulta.setString(5, detalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, detalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			id = 0;
			dao_bitacora dao1 = new dao_bitacora();
			id = dao1.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia1) {
				id1 = id;
			} else {
				id1 = secuencia1;
			}
			consulta = conexion.abrir().prepareStatement(
					"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_clasificacion());
			consulta.setLong(7, bitacora.getId_tipo_tarea());
			consulta.setLong(8, bitacora.getId_solicitante());
			consulta.setString(9, bitacora.getId_area());
			consulta.setString(10, bitacora.getId_rack());
			consulta.setString(11, bitacora.getArea());
			consulta.setString(12, bitacora.getRack());
			consulta.setString(13, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(14, bitacora.getFec_inicio());
			consulta.setTimestamp(15, bitacora.getFec_fin());
			consulta.setLong(16, bitacora.getId_estado_bitacora());
			consulta.setString(17, bitacora.getCumplimiento());
			consulta.setString(18, bitacora.getCumplimientoSLA());
			consulta.setString(19, bitacora.getComentarioCumplimientoSLA());
			consulta.setLong(20, bitacora.getId_localidad());
			consulta.setString(21, bitacora.getEst_bitacora());
			consulta.setString(22, bitacora.getUsu_ingresa());
			consulta.setTimestamp(23, bitacora.getFec_ingresa());
			consulta.setString(24, bitacora.getUsu_modifica());
			consulta.setTimestamp(25, bitacora.getFec_modifica());
			consulta.executeUpdate();
			/* ELIMINAMOS EL REGISTRO TAREA DE PROVEEDOR ANTERIOR */
			consulta = conexion.abrir().prepareStatement("{CALL tarea_proveedor_eliminarTareaProveedor(?)}");
			consulta.setLong(1, tarea_proveedor.getId_tarea_proveedor());
			consulta.executeUpdate();
			/* REGISTRO TAREA DE PROVEEDOR */
			id = 0;
			dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
			id = dao2.obtenerNuevoId();
			id1 = 0;
			if (id > secuencia2) {
				id1 = id;
			} else {
				id1 = secuencia2;
			}
			consulta = conexion.abrir().prepareStatement(
					"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, tarea_proveedor.getId_cliente());
			consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
			consulta.setLong(4, tarea_proveedor.getId_turno());
			consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
			consulta.setLong(6, tarea_proveedor.getId_tipo_clasificacion());
			consulta.setLong(7, tarea_proveedor.getId_tipo_tarea());
			consulta.setLong(8, tarea_proveedor.getId_solicitante());
			consulta.setString(9, tarea_proveedor.getId_area());
			consulta.setString(10, tarea_proveedor.getId_rack());
			consulta.setString(11, tarea_proveedor.getArea());
			consulta.setString(12, tarea_proveedor.getRack());
			consulta.setString(13, tarea_proveedor.getDescripcion().toUpperCase());
			consulta.setTimestamp(14, tarea_proveedor.getFec_inicio());
			consulta.setTimestamp(15, tarea_proveedor.getFec_fin());
			consulta.setLong(16, tarea_proveedor.getId_estado_bitacora());
			consulta.setString(17, tarea_proveedor.getCumplimiento());
			consulta.setLong(18, tarea_proveedor.getId_localidad());
			consulta.setString(19, tarea_proveedor.getEst_tarea_proveedor());
			consulta.setString(20, tarea_proveedor.getUsu_ingresa());
			consulta.setTimestamp(21, tarea_proveedor.getFec_ingresa());
			consulta.setString(22, bitacora.getUsu_modifica());
			consulta.setTimestamp(23, bitacora.getFec_modifica());
			consulta.executeUpdate();
			/* CAMBIAMOS DE ESTADO SOLICITUD DE PERSONAL TOMADA DE REFERENCIA */
			consulta = conexion.abrir().prepareStatement(
					"{CALL personal_modificarSolicitudPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, solicitud_referencia.getId_cliente());
			consulta.setString(2, solicitud_referencia.getTicket().toUpperCase().trim());
			consulta.setLong(3, solicitud_referencia.getId_tipo_ingreso());
			consulta.setLong(4, solicitud_referencia.getId_tipo_aprobador());
			consulta.setLong(5, solicitud_referencia.getId_solicitante());
			consulta.setLong(6, solicitud_referencia.getId_tipo_trabajo());
			consulta.setString(7, solicitud_referencia.getId_area());
			consulta.setString(8, solicitud_referencia.getId_rack());
			consulta.setString(9, solicitud_referencia.getArea());
			consulta.setString(10, solicitud_referencia.getRack());
			consulta.setTimestamp(11, solicitud_referencia.getFec_solicitud());
			consulta.setTimestamp(12, solicitud_referencia.getFec_respuesta());
			consulta.setTimestamp(13, solicitud_referencia.getFec_inicio());
			consulta.setTimestamp(14, solicitud_referencia.getFec_fin());
			consulta.setString(15, solicitud_referencia.getDescripcion());
			consulta.setLong(16, solicitud_referencia.getId_localidad());
			consulta.setString(17, solicitud_referencia.getEst_solicitud());
			consulta.setString(18, solicitud_referencia.getUsu_modifica());
			consulta.setTimestamp(19, solicitud_referencia.getFec_modifica());
			consulta.setLong(20, solicitud_referencia.getId_solicitud());
			consulta.executeUpdate();
			/*
			 * SI NO SE PRESENTAN ERRORES SE APLICAN LOS CAMBIOS Y SE CIERRA LA INSTANCIA DE
			 * LA BD
			 */
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

	@SuppressWarnings("resource")
	public void modificarSolicitudPersonal(modelo_solicitud_personal solicitud,
			List<modelo_detalle_solicitud_personal> detalle, modelo_bitacora bitacora, long secuencia1,
			modelo_tarea_proveedor tarea_proveedor, long secuencia2, int bandera) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL personal_modificarSolicitudPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, solicitud.getId_cliente());
			consulta.setString(2, solicitud.getTicket().toUpperCase().trim());
			consulta.setLong(3, solicitud.getId_tipo_ingreso());
			consulta.setLong(4, solicitud.getId_tipo_aprobador());
			consulta.setLong(5, solicitud.getId_solicitante());
			consulta.setLong(6, solicitud.getId_tipo_trabajo());
			consulta.setString(7, solicitud.getId_area());
			consulta.setString(8, solicitud.getId_rack());
			consulta.setString(9, solicitud.getArea());
			consulta.setString(10, solicitud.getRack());
			consulta.setTimestamp(11, solicitud.getFec_solicitud());
			consulta.setTimestamp(12, solicitud.getFec_respuesta());
			consulta.setTimestamp(13, solicitud.getFec_inicio());
			consulta.setTimestamp(14, solicitud.getFec_fin());
			consulta.setString(15, solicitud.getDescripcion());
			consulta.setLong(16, solicitud.getId_localidad());
			consulta.setString(17, solicitud.getEst_solicitud());
			consulta.setString(18, solicitud.getUsu_modifica());
			consulta.setTimestamp(19, solicitud.getFec_modifica());
			consulta.setLong(20, solicitud.getId_solicitud());
			consulta.executeUpdate();
			/* ELIMINAMOS DEL DETALLE DE LA SOLICITUD ANTERIOR */
			consulta = conexion.abrir().prepareStatement("{CALL personal_eliminarDetalleSolicitudPersonal(?)}");
			consulta.setLong(1, solicitud.getId_solicitud());
			consulta.executeUpdate();
			/* INSERTAMOS EL DETALLE DE LA SOLICITUD ACTUAL */
			for (int i = 0; i < detalle.size(); i++) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL personal_insertarDetalleSolicitudPersonal(?,?,?,?,?,?)}");
				consulta.setLong(1, solicitud.getId_solicitud());
				consulta.setLong(2, detalle.get(i).getId_proveedor());
				consulta.setLong(3, detalle.get(i).getId_dispositivo());
				consulta.setString(4, detalle.get(i).getEst_detalle_solicitud());
				consulta.setString(5, detalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, detalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/* ELIMINAMOS REGISTRO DE BITACORA */
			consulta = conexion.abrir().prepareStatement("{CALL bitacora_eliminarBitacora (?)}");
			consulta.setLong(1, bitacora.getId_bitacora());
			consulta.executeUpdate();
			/* REGISTRO EN BITACORA */
			long id = 0;
			dao_bitacora dao1 = new dao_bitacora();
			id = dao1.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia1) {
				id1 = id;
			} else {
				id1 = secuencia1;
			}
			consulta = conexion.abrir().prepareStatement(
					"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_clasificacion());
			consulta.setLong(7, bitacora.getId_tipo_tarea());
			consulta.setLong(8, bitacora.getId_solicitante());
			consulta.setString(9, bitacora.getId_area());
			consulta.setString(10, bitacora.getId_rack());
			consulta.setString(11, bitacora.getArea());
			consulta.setString(12, bitacora.getRack());
			consulta.setString(13, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(14, bitacora.getFec_inicio());
			consulta.setTimestamp(15, bitacora.getFec_fin());
			consulta.setLong(16, bitacora.getId_estado_bitacora());
			consulta.setString(17, bitacora.getCumplimiento());
			consulta.setString(18, bitacora.getCumplimientoSLA());
			consulta.setString(19, bitacora.getComentarioCumplimientoSLA());
			consulta.setLong(20, bitacora.getId_localidad());
			consulta.setString(21, bitacora.getEst_bitacora());
			consulta.setString(22, bitacora.getUsu_ingresa());
			consulta.setTimestamp(23, bitacora.getFec_ingresa());
			consulta.setString(24, bitacora.getUsu_modifica());
			consulta.setTimestamp(25, bitacora.getFec_modifica());
			consulta.executeUpdate();
			/* REGISTRO TAREA DE PROVEEDOR ACTUAL */
			if (bandera == 1) { // SI TAREA NO EXISTE
				id = 0;
				dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
				id = dao2.obtenerNuevoId();
				id1 = 0;
				if (id > secuencia2) {
					id1 = id;
				} else {
					id1 = secuencia2;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, tarea_proveedor.getId_cliente());
				consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
				consulta.setLong(4, tarea_proveedor.getId_turno());
				consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
				consulta.setLong(6, tarea_proveedor.getId_tipo_clasificacion());
				consulta.setLong(7, tarea_proveedor.getId_tipo_tarea());
				consulta.setLong(8, tarea_proveedor.getId_solicitante());
				consulta.setString(9, tarea_proveedor.getId_area());
				consulta.setString(10, tarea_proveedor.getId_rack());
				consulta.setString(11, tarea_proveedor.getArea());
				consulta.setString(12, tarea_proveedor.getRack());
				consulta.setString(13, tarea_proveedor.getDescripcion().toUpperCase());
				consulta.setTimestamp(14, tarea_proveedor.getFec_inicio());
				consulta.setTimestamp(15, tarea_proveedor.getFec_fin());
				consulta.setLong(16, tarea_proveedor.getId_estado_bitacora());
				consulta.setString(17, tarea_proveedor.getCumplimiento());
				consulta.setLong(18, tarea_proveedor.getId_localidad());
				consulta.setString(19, tarea_proveedor.getEst_tarea_proveedor());
				consulta.setString(20, tarea_proveedor.getUsu_ingresa());
				consulta.setTimestamp(21, tarea_proveedor.getFec_ingresa());
				consulta.setString(22, bitacora.getUsu_modifica());
				consulta.setTimestamp(23, bitacora.getFec_modifica());
				consulta.executeUpdate();
			} else { // SI TAREA EXISTE
				/* ELIMINAMOS EL REGISTRO TAREA DE PROVEEDOR ANTERIOR */
				consulta = conexion.abrir().prepareStatement("{CALL tarea_proveedor_eliminarTareaProveedor(?)}");
				consulta.setLong(1, tarea_proveedor.getId_tarea_proveedor());
				consulta.executeUpdate();
				id = 0;
				dao_tarea_proveedor dao2 = new dao_tarea_proveedor();
				id = dao2.obtenerNuevoId();
				id1 = 0;
				if (id > secuencia2) {
					id1 = id;
				} else {
					id1 = secuencia2;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, tarea_proveedor.getId_cliente());
				consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
				consulta.setLong(4, tarea_proveedor.getId_turno());
				consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
				consulta.setLong(6, tarea_proveedor.getId_tipo_clasificacion());
				consulta.setLong(7, tarea_proveedor.getId_tipo_tarea());
				consulta.setLong(8, tarea_proveedor.getId_solicitante());
				consulta.setString(9, tarea_proveedor.getId_area());
				consulta.setString(10, tarea_proveedor.getId_rack());
				consulta.setString(11, tarea_proveedor.getArea());
				consulta.setString(12, tarea_proveedor.getRack());
				consulta.setString(13, tarea_proveedor.getDescripcion().toUpperCase());
				consulta.setTimestamp(14, tarea_proveedor.getFec_inicio());
				consulta.setTimestamp(15, tarea_proveedor.getFec_fin());
				consulta.setLong(16, tarea_proveedor.getId_estado_bitacora());
				consulta.setString(17, tarea_proveedor.getCumplimiento());
				consulta.setLong(18, tarea_proveedor.getId_localidad());
				consulta.setString(19, tarea_proveedor.getEst_tarea_proveedor());
				consulta.setString(20, tarea_proveedor.getUsu_ingresa());
				consulta.setTimestamp(21, tarea_proveedor.getFec_ingresa());
				consulta.setString(22, bitacora.getUsu_modifica());
				consulta.setTimestamp(23, bitacora.getFec_modifica());
				consulta.executeUpdate();
			}
			/*
			 * SI NO SE PRESENTAN ERRORES SE APLICAN LOS CAMBIOS Y SE CIERRA LA INSTANCIA DE
			 * LA BD
			 */
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

	public List<modelo_registro_permanencia> obtenerRegistroPermanencia(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_registro_permanencia> lista_permanencia = new ArrayList<modelo_registro_permanencia>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerRegistroPermanencia(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setString(3, estado);
			consulta.setLong(4, id_localidad);
			consulta.setInt(5, limite);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_permanencia.add(new modelo_registro_permanencia(resultado.getLong("id_registro_permanencia"),
						resultado.getLong("id_solicitud"), resultado.getLong("id_cliente"),
						resultado.getString("ticket"), resultado.getTimestamp("fec_ingreso"),
						resultado.getTimestamp("fec_salida"), resultado.getLong("id_proveedor"),
						resultado.getString("nom_empresa"), resultado.getString("nom_proveedor"),
						resultado.getString("num_documento"), resultado.getString("descripcion"),
						resultado.getString("area_rack"), resultado.getString("tipo_trabajo"),
						resultado.getString("num_tarjeta_dn"), resultado.getString("num_tarjeta_bp"),
						resultado.getString("tipo_aprobador"), resultado.getString("tipo_ingreso"),
						resultado.getTimestamp("fec_ingreso_su"), resultado.getTimestamp("fec_salida_su"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getString("est_registro_permanencia"), resultado.getString("usu_ingresa"),
						resultado.getString("nom_usuario_ingresa"), resultado.getTimestamp("fec_ingresa"),
						resultado.getString("usu_modifica"), resultado.getString("nom_usuario_modifica"),
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
		return lista_permanencia;
	}

	public List<modelo_registro_permanencia> obtenerRegistroPermanencia2(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_registro_permanencia> lista_permanencia = new ArrayList<modelo_registro_permanencia>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerRegistroPermanencia(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setString(3, estado);
			consulta.setLong(4, id_localidad);
			consulta.setInt(5, limite);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_permanencia.add(new modelo_registro_permanencia(resultado.getLong("id_solicitud"),
						resultado.getLong("id_cliente"), resultado.getString("ticket"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_proveedor"), resultado.getString("nom_empresa"),
						resultado.getString("nom_proveedor"), resultado.getString("num_documento"),
						resultado.getLong("id_solicitante"), resultado.getLong("id_emp_solicitante"),
						resultado.getString("nom_emp_solicitante"), resultado.getString("descripcion"),
						resultado.getString("area"), resultado.getString("rack"),
						resultado.getString("est_solicitud")));
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
		return lista_permanencia;
	}

	public List<modelo_registro_permanencia> obtenerRegistroPermanencia3(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_registro_permanencia> lista_permanencia = new ArrayList<modelo_registro_permanencia>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerRegistroPermanencia(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setString(3, estado);
			consulta.setLong(4, id_localidad);
			consulta.setInt(5, limite);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_permanencia.add(new modelo_registro_permanencia(resultado.getLong("id_registro_permanencia"),
						resultado.getString("num_tarjeta_bp"), resultado.getString("num_tarjeta_dn"),
						resultado.getTimestamp("fec_ingreso"), resultado.getTimestamp("fec_salida"),
						resultado.getTimestamp("fec_ingreso_su"), resultado.getTimestamp("fec_salida_su"),
						resultado.getLong("id_solicitud"), resultado.getLong("id_cliente"),
						resultado.getString("ticket"), resultado.getTimestamp("fec_inicio"),
						resultado.getTimestamp("fec_fin"), resultado.getLong("id_proveedor"),
						resultado.getString("nom_empresa"), resultado.getString("nom_proveedor"),
						resultado.getString("num_documento"), resultado.getLong("id_solicitante"),
						resultado.getLong("id_emp_solicitante"), resultado.getString("nom_emp_solicitante"),
						resultado.getString("descripcion"), resultado.getString("area"), resultado.getString("rack"), resultado.getString("est_solicitud")));
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
		return lista_permanencia;
	}

	public List<modelo_registro_permanencia> obtenerRegistroPermanencia4(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_registro_permanencia> lista_permanencia = new ArrayList<modelo_registro_permanencia>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerRegistroPermanencia(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setString(3, estado);
			consulta.setLong(4, id_localidad);
			consulta.setInt(5, limite);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_permanencia.add(new modelo_registro_permanencia(resultado.getLong("id_registro_permanencia"),
						resultado.getString("num_tarjeta_bp"), resultado.getString("num_tarjeta_dn"),
						resultado.getTimestamp("fec_ingreso"), resultado.getTimestamp("fec_salida"),
						resultado.getTimestamp("fec_ingreso_su"), resultado.getTimestamp("fec_salida_su"),
						resultado.getLong("id_solicitud"), resultado.getLong("id_cliente"),
						resultado.getString("ticket"), resultado.getTimestamp("fec_inicio"),
						resultado.getTimestamp("fec_fin"), resultado.getLong("id_proveedor"),
						resultado.getString("nom_empresa"), resultado.getString("nom_proveedor"),
						resultado.getString("num_documento"), resultado.getLong("id_solicitante"),
						resultado.getLong("id_emp_solicitante"), resultado.getString("nom_emp_solicitante"),
						resultado.getString("descripcion"), resultado.getString("area"), resultado.getString("rack"), resultado.getString("est_solicitud")));
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
		return lista_permanencia;
	}

	public void insertarRegistroPermanencia(List<modelo_registro_permanencia> listaPermanencia,
			List<modelo_bitacora> listaBitacora, long secuencia1) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		try {
			PreparedStatement consulta = null;
			for (int i = 0; i < listaPermanencia.size(); i++) {
				id = obtenerNuevoIdRegistroPermanencia();
				consulta = conexion.abrir()
						.prepareStatement("{CALL personal_insertarRegistroPermanencia(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, listaPermanencia.get(i).getId_solicitud());
				consulta.setTimestamp(3, listaPermanencia.get(i).getFec_ingreso());
				consulta.setTimestamp(4, listaPermanencia.get(i).getFec_salida());
				consulta.setLong(5, listaPermanencia.get(i).getId_proveedor());
				consulta.setString(6, listaPermanencia.get(i).getNum_tarjeta_dn());
				consulta.setString(7, listaPermanencia.get(i).getNum_tarjeta_bp());
				consulta.setTimestamp(8, listaPermanencia.get(i).getFec_ingreso_su());
				consulta.setTimestamp(9, listaPermanencia.get(i).getFec_salida_su());
				consulta.setLong(10, listaPermanencia.get(i).getId_localidad());
				consulta.setString(11, listaPermanencia.get(i).getEst_registro_permanencia());
				consulta.setString(12, listaPermanencia.get(i).getUsu_ingresa());
				consulta.setTimestamp(13, listaPermanencia.get(i).getFec_ingresa());
				consulta.executeUpdate();
				consulta.close();
				conexion.abrir().commit();
			}
			for (int i = 0; i < listaBitacora.size(); i++) {
				/* REGISTRO EN BITACORA */
				id = 0;
				dao_bitacora dao1 = new dao_bitacora();
				id = dao1.obtenerNuevoId();
				long id1 = 0;
				if (id > secuencia1) {
					id1 = id;
				} else {
					id1 = secuencia1;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, listaBitacora.get(i).getId_cliente());
				consulta.setString(3, listaBitacora.get(i).getTicket_externo().toUpperCase());
				consulta.setLong(4, listaBitacora.get(i).getId_turno());
				consulta.setLong(5, listaBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(6, listaBitacora.get(i).getId_tipo_clasificacion());
				consulta.setLong(7, listaBitacora.get(i).getId_tipo_tarea());
				consulta.setLong(8, listaBitacora.get(i).getId_solicitante());
				consulta.setString(9, listaBitacora.get(i).getId_area());
				consulta.setString(10, listaBitacora.get(i).getId_rack());
				consulta.setString(11, listaBitacora.get(i).getArea());
				consulta.setString(12, listaBitacora.get(i).getRack());
				consulta.setString(13, listaBitacora.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(14, listaBitacora.get(i).getFec_inicio());
				consulta.setTimestamp(15, listaBitacora.get(i).getFec_fin());
				consulta.setLong(16, listaBitacora.get(i).getId_estado_bitacora());
				consulta.setString(17, listaBitacora.get(i).getCumplimiento());
				consulta.setString(18, listaBitacora.get(i).getCumplimientoSLA());
				consulta.setString(19, listaBitacora.get(i).getComentarioCumplimientoSLA());
				consulta.setLong(20, listaBitacora.get(i).getId_localidad());
				consulta.setString(21, listaBitacora.get(i).getEst_bitacora());
				consulta.setString(22, listaBitacora.get(i).getUsu_ingresa());
				consulta.setTimestamp(23, listaBitacora.get(i).getFec_ingresa());
				consulta.setString(24, listaBitacora.get(i).getUsu_modifica());
				consulta.setTimestamp(25, listaBitacora.get(i).getFec_modifica());
				consulta.executeUpdate();
				consulta.close();
				conexion.abrir().commit();
			}
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

	public void modificarRegistroPermanencia(modelo_registro_permanencia registro_permanencia, modelo_bitacora bitacora,
			long secuencia1) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_modificarRegistroPermanencia(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, registro_permanencia.getId_registro_permanencia());
			consulta.setLong(2, registro_permanencia.getId_solicitud());
			consulta.setTimestamp(3, registro_permanencia.getFec_ingreso());
			consulta.setTimestamp(4, registro_permanencia.getFec_salida());
			consulta.setLong(5, registro_permanencia.getId_proveedor());
			consulta.setString(6, registro_permanencia.getNum_tarjeta_dn());
			consulta.setString(7, registro_permanencia.getNum_tarjeta_bp());
			consulta.setTimestamp(8, registro_permanencia.getFec_ingreso_su());
			consulta.setTimestamp(9, registro_permanencia.getFec_salida_su());
			consulta.setLong(10, registro_permanencia.getId_localidad());
			consulta.setString(11, registro_permanencia.getEst_registro_permanencia());
			consulta.setString(12, registro_permanencia.getUsu_modifica());
			consulta.setTimestamp(13, registro_permanencia.getFec_modifica());
			consulta.executeUpdate();
			/* REGISTRO EN BITACORA */
			id = 0;
			dao_bitacora dao1 = new dao_bitacora();
			id = dao1.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia1) {
				id1 = id;
			} else {
				id1 = secuencia1;
			}
			consulta = conexion.abrir().prepareStatement(
					"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_clasificacion());
			consulta.setLong(7, bitacora.getId_tipo_tarea());
			consulta.setLong(8, bitacora.getId_solicitante());
			consulta.setString(9, bitacora.getId_area());
			consulta.setString(10, bitacora.getId_rack());
			consulta.setString(11, bitacora.getArea());
			consulta.setString(12, bitacora.getRack());
			consulta.setString(13, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(14, bitacora.getFec_inicio());
			consulta.setTimestamp(15, bitacora.getFec_fin());
			consulta.setLong(16, bitacora.getId_estado_bitacora());
			consulta.setString(17, bitacora.getCumplimiento());
			consulta.setString(18, bitacora.getCumplimientoSLA());
			consulta.setString(19, bitacora.getComentarioCumplimientoSLA());
			consulta.setLong(20, bitacora.getId_localidad());
			consulta.setString(21, bitacora.getEst_bitacora());
			consulta.setString(22, bitacora.getUsu_ingresa());
			consulta.setTimestamp(23, bitacora.getFec_ingresa());
			consulta.setString(24, bitacora.getUsu_modifica());
			consulta.setTimestamp(25, bitacora.getFec_modifica());
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

	public void modificarRegistroPermanencia(List<modelo_registro_permanencia> listaPermanencia,
			List<modelo_bitacora> listaBitacora, long secuencia1) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		try {
			PreparedStatement consulta = null;
			for (int i = 0; i < listaPermanencia.size(); i++) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL personal_modificarRegistroPermanencia(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, listaPermanencia.get(i).getId_registro_permanencia());
				consulta.setLong(2, listaPermanencia.get(i).getId_solicitud());
				consulta.setTimestamp(3, listaPermanencia.get(i).getFec_ingreso());
				consulta.setTimestamp(4, listaPermanencia.get(i).getFec_salida());
				consulta.setLong(5, listaPermanencia.get(i).getId_proveedor());
				consulta.setString(6, listaPermanencia.get(i).getNum_tarjeta_dn());
				consulta.setString(7, listaPermanencia.get(i).getNum_tarjeta_bp());
				consulta.setTimestamp(8, listaPermanencia.get(i).getFec_ingreso_su());
				consulta.setTimestamp(9, listaPermanencia.get(i).getFec_salida_su());
				consulta.setLong(10, listaPermanencia.get(i).getId_localidad());
				consulta.setString(11, listaPermanencia.get(i).getEst_registro_permanencia());
				consulta.setString(12, listaPermanencia.get(i).getUsu_modifica());
				consulta.setTimestamp(13, listaPermanencia.get(i).getFec_modifica());
				consulta.executeUpdate();
				consulta.close();
				conexion.abrir().commit();
			}
			for (int i = 0; i < listaBitacora.size(); i++) {
				/* REGISTRO EN BITACORA */
				id = 0;
				dao_bitacora dao1 = new dao_bitacora();
				id = dao1.obtenerNuevoId();
				long id1 = 0;
				if (id > secuencia1) {
					id1 = id;
				} else {
					id1 = secuencia1;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, listaBitacora.get(i).getId_cliente());
				consulta.setString(3, listaBitacora.get(i).getTicket_externo().toUpperCase());
				consulta.setLong(4, listaBitacora.get(i).getId_turno());
				consulta.setLong(5, listaBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(6, listaBitacora.get(i).getId_tipo_clasificacion());
				consulta.setLong(7, listaBitacora.get(i).getId_tipo_tarea());
				consulta.setLong(8, listaBitacora.get(i).getId_solicitante());
				consulta.setString(9, listaBitacora.get(i).getId_area());
				consulta.setString(10, listaBitacora.get(i).getId_rack());
				consulta.setString(11, listaBitacora.get(i).getArea());
				consulta.setString(12, listaBitacora.get(i).getRack());
				consulta.setString(13, listaBitacora.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(14, listaBitacora.get(i).getFec_inicio());
				consulta.setTimestamp(15, listaBitacora.get(i).getFec_fin());
				consulta.setLong(16, listaBitacora.get(i).getId_estado_bitacora());
				consulta.setString(17, listaBitacora.get(i).getCumplimiento());
				consulta.setString(18, listaBitacora.get(i).getCumplimientoSLA());
				consulta.setString(19, listaBitacora.get(i).getComentarioCumplimientoSLA());
				consulta.setLong(20, listaBitacora.get(i).getId_localidad());
				consulta.setString(21, listaBitacora.get(i).getEst_bitacora());
				consulta.setString(22, listaBitacora.get(i).getUsu_ingresa());
				consulta.setTimestamp(23, listaBitacora.get(i).getFec_ingresa());
				consulta.setString(24, listaBitacora.get(i).getUsu_modifica());
				consulta.setTimestamp(25, listaBitacora.get(i).getFec_modifica());
				consulta.executeUpdate();
				consulta.close();
				conexion.abrir().commit();
			}
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

	public void eliminarRegistroPermanencia(long id_registro_permanencia, modelo_bitacora bitacora, long secuencia1)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL personal_eliminarRegistroPermanencia(?)}");
			consulta.setLong(1, id_registro_permanencia);
			consulta.executeUpdate();
//			/* REGISTRO EN BITACORA */
//			long id = 0;
//			dao_bitacora dao1 = new dao_bitacora();
//			id = dao1.obtenerNuevoId();
//			long id1 = 0;
//			if (id > secuencia1) {
//				id1 = id;
//			} else {
//				id1 = secuencia1;
//			}
//			consulta = conexion.abrir()
//					.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//			consulta.setLong(1, id1);
//			consulta.setLong(2, bitacora.getId_cliente());
//			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
//			consulta.setLong(4, bitacora.getId_turno());
//			consulta.setLong(5, bitacora.getId_tipo_servicio());
//			consulta.setLong(6, bitacora.getId_tipo_clasificacion());
//			consulta.setLong(7, bitacora.getId_tipo_tarea());
//			consulta.setLong(8, bitacora.getId_solicitante());
//			consulta.setString(9, bitacora.getArea());
//			consulta.setString(10, bitacora.getRack());
//			consulta.setString(11, bitacora.getDescripcion().toUpperCase());
//			consulta.setTimestamp(12, bitacora.getFec_inicio());
//			consulta.setTimestamp(13, bitacora.getFec_fin());
//			consulta.setLong(14, bitacora.getId_estado_bitacora());
//			consulta.setString(15, bitacora.getCumplimiento());
//			consulta.setString(16, bitacora.getCumplimientoSLA());
//			consulta.setString(17, bitacora.getComentarioCumplimientoSLA());
//			consulta.setLong(18, bitacora.getId_localidad());
//			consulta.setString(19, bitacora.getEst_bitacora());
//			consulta.setString(20, bitacora.getUsu_ingresa());
//			consulta.setTimestamp(21, bitacora.getFec_ingresa());
//			consulta.executeUpdate();
			/*
			 * SI NO SE PRESENTAN ERRORES SE APLICAN LOS CAMBIOS Y SE CIERRA LA INSTANCIA DE
			 * LA BD
			 */
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
