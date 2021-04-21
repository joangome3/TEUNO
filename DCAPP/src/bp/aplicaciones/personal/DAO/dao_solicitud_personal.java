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
			String fecha_solicitud_1, String fecha_solicitud_2, String fecha_inicio, String fecha_fin, long id_localidad,
			int limite) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_solicitud_personal> lista_solicitudes = new ArrayList<modelo_solicitud_personal>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_obtenerSolicitudesPersonal(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase().trim());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id_cliente);
			consulta.setString(4, fecha_solicitud_1);
			consulta.setString(5, fecha_solicitud_2);
			consulta.setString(6, fecha_inicio);
			consulta.setString(7, fecha_fin);
			consulta.setLong(8, id_localidad);
			consulta.setInt(9, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_solicitudes.add(new modelo_solicitud_personal(resultado.getLong("id_solicitud"),
						resultado.getLong("id_cliente"), resultado.getString("nom_cliente"),
						resultado.getString("ticket"), resultado.getLong("id_tipo_ingreso"),
						resultado.getString("nom_tipo_ingreso"), resultado.getLong("id_tipo_aprobador"),
						resultado.getString("nom_tipo_aprobador"), resultado.getLong("id_solicitante"),
						resultado.getString("nom_solicitante"), resultado.getLong("id_tipo_trabajo"),
						resultado.getString("nom_tipo_trabajo"), resultado.getString("area"),
						resultado.getString("rack"), resultado.getTimestamp("fec_solicitud"),
						resultado.getTimestamp("fec_respuesta"), resultado.getTimestamp("fec_inicio"),
						resultado.getTimestamp("fec_fin"), resultado.getString("descripcion"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getString("est_solicitud"), resultado.getString("usu_ingresa"),
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
		return lista_solicitudes;
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
			consulta = conexion.abrir()
					.prepareStatement("{CALL personal_insertarSolicitudPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, solicitud.getId_cliente());
			consulta.setString(3, solicitud.getTicket().toUpperCase().trim());
			consulta.setLong(4, solicitud.getId_tipo_ingreso());
			consulta.setLong(5, solicitud.getId_tipo_aprobador());
			consulta.setLong(6, solicitud.getId_solicitante());
			consulta.setLong(7, solicitud.getId_tipo_trabajo());
			consulta.setString(8, solicitud.getArea());
			consulta.setString(9, solicitud.getRack());
			consulta.setTimestamp(10, solicitud.getFec_solicitud());
			consulta.setTimestamp(11, solicitud.getFec_respuesta());
			consulta.setTimestamp(12, solicitud.getFec_inicio());
			consulta.setTimestamp(13, solicitud.getFec_fin());
			consulta.setString(14, solicitud.getDescripcion());
			consulta.setLong(15, solicitud.getId_localidad());
			consulta.setString(16, solicitud.getEst_solicitud());
			consulta.setString(17, solicitud.getUsu_ingresa());
			consulta.setTimestamp(18, solicitud.getFec_ingresa());
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
			consulta = conexion.abrir()
					.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id1);
			consulta.setLong(2, bitacora.getId_cliente());
			consulta.setString(3, bitacora.getTicket_externo().toUpperCase());
			consulta.setLong(4, bitacora.getId_turno());
			consulta.setLong(5, bitacora.getId_tipo_servicio());
			consulta.setLong(6, bitacora.getId_tipo_tarea());
			consulta.setLong(7, bitacora.getId_solicitante());
			consulta.setString(8, bitacora.getDescripcion().toUpperCase());
			consulta.setTimestamp(9, bitacora.getFec_inicio());
			consulta.setTimestamp(10, bitacora.getFec_fin());
			consulta.setLong(11, bitacora.getId_estado_bitacora());
			consulta.setString(12, bitacora.getCumplimiento());
			consulta.setLong(13, bitacora.getId_localidad());
			consulta.setString(14, bitacora.getEst_bitacora());
			consulta.setString(15, bitacora.getUsu_ingresa());
			consulta.setTimestamp(16, bitacora.getFec_ingresa());
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
						"{CALL tarea_proveedor_insertarTareaProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, tarea_proveedor.getId_cliente());
				consulta.setString(3, tarea_proveedor.getTicket_externo().toUpperCase());
				consulta.setLong(4, tarea_proveedor.getId_turno());
				consulta.setLong(5, tarea_proveedor.getId_tipo_servicio());
				consulta.setLong(6, tarea_proveedor.getId_tipo_tarea());
				consulta.setLong(7, tarea_proveedor.getId_solicitante());
				consulta.setString(8, tarea_proveedor.getDescripcion().toUpperCase());
				consulta.setTimestamp(9, tarea_proveedor.getFec_inicio());
				consulta.setTimestamp(10, tarea_proveedor.getFec_fin());
				consulta.setLong(11, tarea_proveedor.getId_estado_bitacora());
				consulta.setString(12, tarea_proveedor.getCumplimiento());
				consulta.setLong(13, tarea_proveedor.getId_localidad());
				consulta.setString(14, tarea_proveedor.getEst_tarea_proveedor());
				consulta.setString(15, tarea_proveedor.getUsu_ingresa());
				consulta.setTimestamp(16, tarea_proveedor.getFec_ingresa());
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

}
