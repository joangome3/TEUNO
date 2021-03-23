package bp.aplicaciones.cintas.DAO;

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
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_detalle_dn;

public class dao_movimiento_dn {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL movimientoDN_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_movimiento") + 1;
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

	public List<modelo_movimiento_dn> obtenerMovimientos(String criterio, String fecha_inicio, String fecha_fin,
			String localidad, int limite, int tipo, String estado)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_movimiento_dn> lista_movimientos = new ArrayList<modelo_movimiento_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL movimientoDN_obtenerMovimientos(?,?,?,?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, localidad);
			consulta.setInt(5, limite);
			consulta.setInt(6, tipo);
			consulta.setString(7, estado);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_movimientos.add(new modelo_movimiento_dn(resultado.getLong("id_movimiento"),
						resultado.getString("tck_movimiento"), resultado.getString("tip_pedido"),
						resultado.getTimestamp("fec_solicitud"), resultado.getLong("hor_solicitud"),
						resultado.getTimestamp("fec_respuesta"), resultado.getLong("hor_respuesta"),
						resultado.getTimestamp("fec_ejecucion"), resultado.getLong("hor_ejecucion"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getLong("id_solicitante"), resultado.getString("nom_solicitante"),
						resultado.getLong("id_usuario"), resultado.getString("nom_usuario"),
						resultado.getString("tur_movimiento"), resultado.getString("nom_turno"),
						resultado.getTimestamp("fec_movimiento"), resultado.getString("obs_movimiento"),
						resultado.getString("usu_revision_1"), resultado.getString("nom_usuario_revisa_1"),
						resultado.getString("usu_revision_2"), resultado.getString("nom_usuario_revisa_2"),
						resultado.getString("usu_revision_3"), resultado.getString("nom_usuario_revisa_3"),
						resultado.getString("est_movimiento"), resultado.getString("est_validacion"),
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
		return lista_movimientos;
	}

	public List<modelo_movimiento_detalle_dn> obtenerDetalleMovimientos(String criterio, String fecha_inicio,
			String fecha_fin, String localidad, int limite, int tipo, String estado)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_movimiento_detalle_dn> lista_movimientos = new ArrayList<modelo_movimiento_detalle_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL movimientoDN_obtenerMovimientos(?,?,?,?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, localidad);
			consulta.setInt(5, limite);
			consulta.setInt(6, tipo);
			consulta.setString(7, estado);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_movimientos.add(new modelo_movimiento_detalle_dn(resultado.getLong("id_movimiento"),
						resultado.getString("tck_movimiento"), resultado.getString("tip_pedido"),
						resultado.getTimestamp("fec_solicitud"), resultado.getLong("hor_solicitud"),
						resultado.getTimestamp("fec_respuesta"), resultado.getLong("hor_respuesta"),
						resultado.getTimestamp("fec_ejecucion"), resultado.getLong("hor_ejecucion"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getLong("id_solicitante"), resultado.getString("nom_solicitante"),
						resultado.getLong("id_usuario"), resultado.getString("nom_usuario"),
						resultado.getString("tur_movimiento"), resultado.getString("nom_turno"),
						resultado.getTimestamp("fec_movimiento"), resultado.getString("obs_movimiento"),
						resultado.getString("usu_revision_1"), resultado.getString("nom_usuario_revisa_1"),
						resultado.getString("usu_revision_2"), resultado.getString("nom_usuario_revisa_2"),
						resultado.getString("usu_revision_3"), resultado.getString("nom_usuario_revisa_3"),
						resultado.getString("est_movimiento"), resultado.getString("est_validacion"),
						resultado.getString("usu_ingresa"), resultado.getString("nom_usuario_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getString("nom_usuario_modifica"), resultado.getTimestamp("fec_modifica"),
						resultado.getLong("id_detalle_movimiento"), resultado.getLong("id_movimiento"),
						resultado.getLong("id_articulo"), resultado.getString("cod_articulo_anterior"),
						resultado.getString("des_articulo_anterior"), resultado.getLong("id_cat_articulo_anterior"),
						resultado.getString("nom_cat_articulo_anterior"),
						resultado.getString("si_ing_fec_inicio_fin_anterior"), resultado.getString("es_fecha_anterior"),
						resultado.getLong("id_fec_respaldo_anterior"),
						resultado.getString("nom_id_fec_respaldo_anterior"),
						resultado.getTimestamp("fec_inicio_anterior"), resultado.getTimestamp("fec_fin_anterior"),
						resultado.getLong("id_cap_articulo_anterior"),
						resultado.getString("nom_id_cap_articulo_anterior"),
						resultado.getString("tip_respaldo_anterior"), resultado.getString("nom_tip_respaldo_anterior"),
						resultado.getString("id_contenedor_anterior"), resultado.getLong("id_ubicacion_anterior"),
						resultado.getString("nom_ubicacion_anterior"),
						resultado.getLong("hora_llegada_custodia_anterior"),
						resultado.getLong("hora_salida_custodia_anterior"),
						resultado.getString("remesa_ingreso_custodia_anterior"),
						resultado.getString("remesa_salida_custodia_anterior"),
						resultado.getString("cod_articulo_actual"), resultado.getString("des_articulo_actual"),
						resultado.getLong("id_cat_articulo_actual"), resultado.getString("nom_cat_articulo_actual"),
						resultado.getString("si_ing_fec_inicio_fin_actual"), resultado.getString("es_fecha_actual"),
						resultado.getLong("id_fec_respaldo_actual"), resultado.getString("nom_id_fec_respaldo_actual"),
						resultado.getTimestamp("fec_inicio_actual"), resultado.getTimestamp("fec_fin_actual"),
						resultado.getLong("id_cap_articulo_actual"), resultado.getString("nom_id_cap_articulo_actual"),
						resultado.getString("tip_respaldo_actual"), resultado.getString("nom_tip_respaldo_actual"),
						resultado.getString("id_contenedor_actual"), resultado.getLong("id_ubicacion_actual"),
						resultado.getString("nom_ubicacion_actual"), resultado.getLong("hora_llegada_custodia_actual"),
						resultado.getLong("hora_salida_custodia_actual"),
						resultado.getString("remesa_ingreso_custodia_actual"),
						resultado.getString("remesa_salida_custodia_actual"), resultado.getString("revision_1"),
						resultado.getString("revision_2"), resultado.getString("revision_3"),
						resultado.getString("actualiza_inventario"), resultado.getString("est_detalle_movimiento"),
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
		return lista_movimientos;
	}

	public void insertarMovimiento(modelo_movimiento_dn movimiento,
			List<modelo_movimiento_detalle_dn> listaMovimientoDetalle, modelo_bitacora bitacora, long secuencia)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		try {
			PreparedStatement consulta = null;
			/* CABECERA */
			consulta = conexion.abrir().prepareStatement(
					"{CALL movimientoDN_insertarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			id = obtenerNuevoId();
			consulta.setLong(1, id);
			consulta.setString(2, movimiento.getTck_movimiento());
			consulta.setString(3, movimiento.getTip_pedido());
			consulta.setTimestamp(4, movimiento.getFec_solicitud());
			consulta.setLong(5, movimiento.getHor_solicitud());
			consulta.setTimestamp(6, movimiento.getFec_respuesta());
			consulta.setLong(7, movimiento.getHor_respuesta());
			consulta.setTimestamp(8, movimiento.getFec_ejecucion());
			consulta.setLong(9, movimiento.getHor_ejecucion());
			consulta.setLong(10, movimiento.getId_localidad());
			consulta.setLong(11, movimiento.getId_solicitante());
			consulta.setLong(12, movimiento.getId_usuario());
			consulta.setString(13, movimiento.getTur_movimiento());
			consulta.setTimestamp(14, movimiento.getFec_movimiento());
			consulta.setString(15, movimiento.getObs_movimiento());
			consulta.setString(16, movimiento.getUsu_revision_1());
			consulta.setString(17, movimiento.getUsu_revision_2());
			consulta.setString(18, movimiento.getUsu_revision_3());
			consulta.setString(19, movimiento.getEst_movimiento());
			consulta.setString(20, movimiento.getEst_validacion());
			consulta.setString(21, movimiento.getUsu_ingresa());
			consulta.setTimestamp(22, movimiento.getFec_ingresa());
			consulta.executeUpdate();
			/* DETALLE */
			for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL movimientoDN_insertarMovimientoDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, listaMovimientoDetalle.get(i).getId_articulo());
				/* ANTERIOR */
				consulta.setString(3, listaMovimientoDetalle.get(i).getCod_articulo_anterior());
				consulta.setString(4, listaMovimientoDetalle.get(i).getDes_articulo_anterior());
				consulta.setLong(5, listaMovimientoDetalle.get(i).getId_cat_articulo_anterior());
				consulta.setString(6, listaMovimientoDetalle.get(i).getNom_cat_articulo_anterior());
				consulta.setString(7, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_anterior());
				consulta.setString(8, listaMovimientoDetalle.get(i).getEs_fecha_anterior());
				consulta.setLong(9, listaMovimientoDetalle.get(i).getId_fec_respaldo_anterior());
				consulta.setString(10, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_anterior());
				consulta.setTimestamp(11, listaMovimientoDetalle.get(i).getFec_inicio_anterior());
				consulta.setTimestamp(12, listaMovimientoDetalle.get(i).getFec_fin_anterior());
				consulta.setLong(13, listaMovimientoDetalle.get(i).getId_cap_articulo_anterior());
				consulta.setString(14, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_anterior());
				consulta.setString(15, listaMovimientoDetalle.get(i).getTip_respaldo_anterior());
				consulta.setString(16, listaMovimientoDetalle.get(i).getNom_tip_respaldo_anterior());
				consulta.setString(17, listaMovimientoDetalle.get(i).getId_contenedor_anterior());
				consulta.setLong(18, listaMovimientoDetalle.get(i).getId_ubicacion_anterior());
				consulta.setString(19, listaMovimientoDetalle.get(i).getNom_ubicacion_anterior());
				consulta.setLong(20, listaMovimientoDetalle.get(i).getHora_llegada_custodia_anterior());
				consulta.setLong(21, listaMovimientoDetalle.get(i).getHora_salida_custodia_anterior());
				consulta.setString(22, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_anterior());
				consulta.setString(23, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_anterior());
				/* ACTUAL */
				consulta.setString(24, listaMovimientoDetalle.get(i).getCod_articulo_actual());
				consulta.setString(25, listaMovimientoDetalle.get(i).getDes_articulo_actual());
				consulta.setLong(26, listaMovimientoDetalle.get(i).getId_cat_articulo_actual());
				consulta.setString(27, listaMovimientoDetalle.get(i).getNom_cat_articulo_actual());
				consulta.setString(28, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual());
				consulta.setString(29, listaMovimientoDetalle.get(i).getEs_fecha_actual());
				consulta.setLong(30, listaMovimientoDetalle.get(i).getId_fec_respaldo_actual());
				consulta.setString(31, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_actual());
				consulta.setTimestamp(32, listaMovimientoDetalle.get(i).getFec_inicio_actual());
				consulta.setTimestamp(33, listaMovimientoDetalle.get(i).getFec_fin_actual());
				consulta.setLong(34, listaMovimientoDetalle.get(i).getId_cap_articulo_actual());
				consulta.setString(35, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_actual());
				consulta.setString(36, listaMovimientoDetalle.get(i).getTip_respaldo_actual());
				consulta.setString(37, listaMovimientoDetalle.get(i).getNom_tip_respaldo_actual());
				consulta.setString(38, listaMovimientoDetalle.get(i).getId_contenedor_actual());
				consulta.setLong(39, listaMovimientoDetalle.get(i).getId_ubicacion_actual());
				consulta.setString(40, listaMovimientoDetalle.get(i).getNom_ubicacion_actual());
				consulta.setLong(41, listaMovimientoDetalle.get(i).getHora_llegada_custodia_actual());
				consulta.setLong(42, listaMovimientoDetalle.get(i).getHora_salida_custodia_actual());
				consulta.setString(43, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_actual());
				consulta.setString(44, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_actual());
				consulta.setString(45, listaMovimientoDetalle.get(i).getRevision_1());
				consulta.setString(46, listaMovimientoDetalle.get(i).getRevision_2());
				consulta.setString(47, listaMovimientoDetalle.get(i).getRevision_3());
				consulta.setString(48, listaMovimientoDetalle.get(i).getActualiza_inventario());
				consulta.setString(49, listaMovimientoDetalle.get(i).getEst_detalle_movimiento());
				consulta.setString(50, listaMovimientoDetalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(51, listaMovimientoDetalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			dao_bitacora dao = new dao_bitacora();
			id = dao.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia) {
				id1 = id;
			} else {
				id1 = secuencia;
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

	public void actualizarMovimiento(modelo_movimiento_dn movimiento,
			List<modelo_movimiento_detalle_dn> listaMovimientoDetalle, modelo_bitacora bitacora, long secuencia)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		try {
			PreparedStatement consulta = null;
			/* CABECERA */
			consulta = conexion.abrir().prepareStatement(
					"{CALL movimientoDN_insertarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			id = obtenerNuevoId();
			consulta.setLong(1, id);
			consulta.setString(2, movimiento.getTck_movimiento());
			consulta.setString(3, movimiento.getTip_pedido());
			consulta.setTimestamp(4, movimiento.getFec_solicitud());
			consulta.setLong(5, movimiento.getHor_solicitud());
			consulta.setTimestamp(6, movimiento.getFec_respuesta());
			consulta.setLong(7, movimiento.getHor_respuesta());
			consulta.setTimestamp(8, movimiento.getFec_ejecucion());
			consulta.setLong(9, movimiento.getHor_ejecucion());
			consulta.setLong(10, movimiento.getId_localidad());
			consulta.setLong(11, movimiento.getId_solicitante());
			consulta.setLong(12, movimiento.getId_usuario());
			consulta.setString(13, movimiento.getTur_movimiento());
			consulta.setTimestamp(14, movimiento.getFec_movimiento());
			consulta.setString(15, movimiento.getObs_movimiento());
			consulta.setString(16, movimiento.getUsu_revision_1());
			consulta.setString(17, movimiento.getUsu_revision_2());
			consulta.setString(18, movimiento.getUsu_revision_3());
			consulta.setString(19, movimiento.getEst_movimiento());
			consulta.setString(20, movimiento.getEst_validacion());
			consulta.setString(21, movimiento.getUsu_ingresa());
			consulta.setTimestamp(22, movimiento.getFec_ingresa());
			consulta.executeUpdate();
			/* DETALLE */
			for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL movimientoDN_insertarMovimientoDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, listaMovimientoDetalle.get(i).getId_articulo());
				/* ANTERIOR */
				consulta.setString(3, listaMovimientoDetalle.get(i).getCod_articulo_anterior());
				consulta.setString(4, listaMovimientoDetalle.get(i).getDes_articulo_anterior());
				consulta.setLong(5, listaMovimientoDetalle.get(i).getId_cat_articulo_anterior());
				consulta.setString(6, listaMovimientoDetalle.get(i).getNom_cat_articulo_anterior());
				consulta.setString(7, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_anterior());
				consulta.setString(8, listaMovimientoDetalle.get(i).getEs_fecha_anterior());
				consulta.setLong(9, listaMovimientoDetalle.get(i).getId_fec_respaldo_anterior());
				consulta.setString(10, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_anterior());
				consulta.setTimestamp(11, listaMovimientoDetalle.get(i).getFec_inicio_anterior());
				consulta.setTimestamp(12, listaMovimientoDetalle.get(i).getFec_fin_anterior());
				consulta.setLong(13, listaMovimientoDetalle.get(i).getId_cap_articulo_anterior());
				consulta.setString(14, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_anterior());
				consulta.setString(15, listaMovimientoDetalle.get(i).getTip_respaldo_anterior());
				consulta.setString(16, listaMovimientoDetalle.get(i).getNom_tip_respaldo_anterior());
				consulta.setString(17, listaMovimientoDetalle.get(i).getId_contenedor_anterior());
				consulta.setLong(18, listaMovimientoDetalle.get(i).getId_ubicacion_anterior());
				consulta.setString(19, listaMovimientoDetalle.get(i).getNom_ubicacion_anterior());
				consulta.setLong(20, listaMovimientoDetalle.get(i).getHora_llegada_custodia_anterior());
				consulta.setLong(21, listaMovimientoDetalle.get(i).getHora_salida_custodia_anterior());
				consulta.setString(22, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_anterior());
				consulta.setString(23, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_anterior());
				/* ACTUAL */
				consulta.setString(24, listaMovimientoDetalle.get(i).getCod_articulo_actual());
				consulta.setString(25, listaMovimientoDetalle.get(i).getDes_articulo_actual());
				consulta.setLong(26, listaMovimientoDetalle.get(i).getId_cat_articulo_actual());
				consulta.setString(27, listaMovimientoDetalle.get(i).getNom_cat_articulo_actual());
				consulta.setString(28, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual());
				consulta.setString(29, listaMovimientoDetalle.get(i).getEs_fecha_actual());
				consulta.setLong(30, listaMovimientoDetalle.get(i).getId_fec_respaldo_actual());
				consulta.setString(31, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_actual());
				consulta.setTimestamp(32, listaMovimientoDetalle.get(i).getFec_inicio_actual());
				consulta.setTimestamp(33, listaMovimientoDetalle.get(i).getFec_fin_actual());
				consulta.setLong(34, listaMovimientoDetalle.get(i).getId_cap_articulo_actual());
				consulta.setString(35, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_actual());
				consulta.setString(36, listaMovimientoDetalle.get(i).getTip_respaldo_actual());
				consulta.setString(37, listaMovimientoDetalle.get(i).getNom_tip_respaldo_actual());
				consulta.setString(38, listaMovimientoDetalle.get(i).getId_contenedor_actual());
				consulta.setLong(39, listaMovimientoDetalle.get(i).getId_ubicacion_actual());
				consulta.setString(40, listaMovimientoDetalle.get(i).getNom_ubicacion_actual());
				consulta.setLong(41, listaMovimientoDetalle.get(i).getHora_llegada_custodia_actual());
				consulta.setLong(42, listaMovimientoDetalle.get(i).getHora_salida_custodia_actual());
				consulta.setString(43, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_actual());
				consulta.setString(44, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_actual());
				consulta.setString(45, listaMovimientoDetalle.get(i).getRevision_1());
				consulta.setString(46, listaMovimientoDetalle.get(i).getRevision_2());
				consulta.setString(47, listaMovimientoDetalle.get(i).getRevision_3());
				consulta.setString(48, listaMovimientoDetalle.get(i).getActualiza_inventario());
				consulta.setString(49, listaMovimientoDetalle.get(i).getEst_detalle_movimiento());
				consulta.setString(50, listaMovimientoDetalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(51, listaMovimientoDetalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			dao_bitacora dao = new dao_bitacora();
			id = dao.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia) {
				id1 = id;
			} else {
				id1 = secuencia;
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

	public void revisarMovimiento(modelo_movimiento_dn movimiento,
			List<modelo_movimiento_detalle_dn> listaMovimientoDetalle, modelo_bitacora bitacora,
			List<modelo_bitacora> listaRegistrosBitacora, long secuencia) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			/* CABECERA */
			consulta = conexion.abrir().prepareStatement(
					"{CALL movimientoDN_actualizarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setString(1, movimiento.getTck_movimiento());
			consulta.setString(2, movimiento.getTip_pedido());
			consulta.setTimestamp(3, movimiento.getFec_solicitud());
			consulta.setLong(4, movimiento.getHor_solicitud());
			consulta.setTimestamp(5, movimiento.getFec_respuesta());
			consulta.setLong(6, movimiento.getHor_respuesta());
			consulta.setTimestamp(7, movimiento.getFec_ejecucion());
			consulta.setLong(8, movimiento.getHor_ejecucion());
			consulta.setLong(9, movimiento.getId_localidad());
			consulta.setLong(10, movimiento.getId_solicitante());
			consulta.setLong(11, movimiento.getId_usuario());
			consulta.setString(12, movimiento.getTur_movimiento());
			consulta.setTimestamp(13, movimiento.getFec_movimiento());
			consulta.setString(14, movimiento.getObs_movimiento());
			consulta.setString(15, movimiento.getUsu_revision_1());
			consulta.setString(16, movimiento.getUsu_revision_2());
			consulta.setString(17, movimiento.getUsu_revision_3());
			consulta.setString(18, movimiento.getEst_movimiento());
			consulta.setString(19, movimiento.getEst_validacion());
			consulta.setString(20, movimiento.getUsu_ingresa());
			consulta.setTimestamp(21, movimiento.getFec_ingresa());
			consulta.setString(22, movimiento.getUsu_modifica());
			consulta.setTimestamp(23, movimiento.getFec_modifica());
			consulta.setLong(24, movimiento.getId_movimiento());
			consulta.executeUpdate();
			/* SE ELIMINA EL DETALLE ASOCIADO A LA CABECERA */
			consulta = conexion.abrir().prepareStatement("{CALL movimientoDN_eliminarMovimientoDetalle(?)}");
			consulta.setLong(1, movimiento.getId_movimiento());
			consulta.executeUpdate();
			/* DETALLE */
			for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL movimientoDN_insertarMovimientoDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, movimiento.getId_movimiento());
				consulta.setLong(2, listaMovimientoDetalle.get(i).getId_articulo());
				/* ANTERIOR */
				consulta.setString(3, listaMovimientoDetalle.get(i).getCod_articulo_anterior());
				consulta.setString(4, listaMovimientoDetalle.get(i).getDes_articulo_anterior());
				consulta.setLong(5, listaMovimientoDetalle.get(i).getId_cat_articulo_anterior());
				consulta.setString(6, listaMovimientoDetalle.get(i).getNom_cat_articulo_anterior());
				consulta.setString(7, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_anterior());
				consulta.setString(8, listaMovimientoDetalle.get(i).getEs_fecha_anterior());
				consulta.setLong(9, listaMovimientoDetalle.get(i).getId_fec_respaldo_anterior());
				consulta.setString(10, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_anterior());
				consulta.setTimestamp(11, listaMovimientoDetalle.get(i).getFec_inicio_anterior());
				consulta.setTimestamp(12, listaMovimientoDetalle.get(i).getFec_fin_anterior());
				consulta.setLong(13, listaMovimientoDetalle.get(i).getId_cap_articulo_anterior());
				consulta.setString(14, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_anterior());
				consulta.setString(15, listaMovimientoDetalle.get(i).getTip_respaldo_anterior());
				consulta.setString(16, listaMovimientoDetalle.get(i).getNom_tip_respaldo_anterior());
				consulta.setString(17, listaMovimientoDetalle.get(i).getId_contenedor_anterior());
				consulta.setLong(18, listaMovimientoDetalle.get(i).getId_ubicacion_anterior());
				consulta.setString(19, listaMovimientoDetalle.get(i).getNom_ubicacion_anterior());
				consulta.setLong(20, listaMovimientoDetalle.get(i).getHora_llegada_custodia_anterior());
				consulta.setLong(21, listaMovimientoDetalle.get(i).getHora_salida_custodia_anterior());
				consulta.setString(22, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_anterior());
				consulta.setString(23, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_anterior());
				/* ACTUAL */
				consulta.setString(24, listaMovimientoDetalle.get(i).getCod_articulo_actual());
				consulta.setString(25, listaMovimientoDetalle.get(i).getDes_articulo_actual());
				consulta.setLong(26, listaMovimientoDetalle.get(i).getId_cat_articulo_actual());
				consulta.setString(27, listaMovimientoDetalle.get(i).getNom_cat_articulo_actual());
				consulta.setString(28, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual());
				consulta.setString(29, listaMovimientoDetalle.get(i).getEs_fecha_actual());
				consulta.setLong(30, listaMovimientoDetalle.get(i).getId_fec_respaldo_actual());
				consulta.setString(31, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_actual());
				consulta.setTimestamp(32, listaMovimientoDetalle.get(i).getFec_inicio_actual());
				consulta.setTimestamp(33, listaMovimientoDetalle.get(i).getFec_fin_actual());
				consulta.setLong(34, listaMovimientoDetalle.get(i).getId_cap_articulo_actual());
				consulta.setString(35, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_actual());
				consulta.setString(36, listaMovimientoDetalle.get(i).getTip_respaldo_actual());
				consulta.setString(37, listaMovimientoDetalle.get(i).getNom_tip_respaldo_actual());
				consulta.setString(38, listaMovimientoDetalle.get(i).getId_contenedor_actual());
				consulta.setLong(39, listaMovimientoDetalle.get(i).getId_ubicacion_actual());
				consulta.setString(40, listaMovimientoDetalle.get(i).getNom_ubicacion_actual());
				consulta.setLong(41, listaMovimientoDetalle.get(i).getHora_llegada_custodia_actual());
				consulta.setLong(42, listaMovimientoDetalle.get(i).getHora_salida_custodia_actual());
				consulta.setString(43, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_actual());
				consulta.setString(44, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_actual());
				consulta.setString(45, listaMovimientoDetalle.get(i).getRevision_1());
				consulta.setString(46, listaMovimientoDetalle.get(i).getRevision_2());
				consulta.setString(47, listaMovimientoDetalle.get(i).getRevision_3());
				consulta.setString(48, listaMovimientoDetalle.get(i).getActualiza_inventario());
				consulta.setString(49, listaMovimientoDetalle.get(i).getEst_detalle_movimiento());
				consulta.setString(50, listaMovimientoDetalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(51, listaMovimientoDetalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/*
			 * DE EXISTIR ALGUN CAMBIO EN LA REVISION SE ACTUALIZAN LOS REGISTROS EN
			 * BITACORA
			 */
			for (int i = 0; i < listaRegistrosBitacora.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL bitacora_modificarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, bitacora.getId_cliente());
				consulta.setString(2, movimiento.getTck_movimiento());
				consulta.setLong(3, listaRegistrosBitacora.get(i).getId_turno());
				consulta.setLong(4, listaRegistrosBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(5, listaRegistrosBitacora.get(i).getId_tipo_tarea());
				consulta.setLong(6, movimiento.getId_solicitante());
				consulta.setString(7, listaRegistrosBitacora.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(8, listaRegistrosBitacora.get(i).getFec_inicio());
				consulta.setTimestamp(9, listaRegistrosBitacora.get(i).getFec_fin());
				consulta.setLong(10, listaRegistrosBitacora.get(i).getId_estado_bitacora());
				consulta.setString(11, listaRegistrosBitacora.get(i).getCumplimiento());
				consulta.setLong(12, listaRegistrosBitacora.get(i).getId_localidad());
				consulta.setString(13, listaRegistrosBitacora.get(i).getEst_bitacora());
				consulta.setString(14, listaRegistrosBitacora.get(i).getUsu_ingresa());
				consulta.setTimestamp(15, listaRegistrosBitacora.get(i).getFec_ingresa());
				consulta.setString(16, listaRegistrosBitacora.get(i).getUsu_modifica());
				consulta.setTimestamp(17, listaRegistrosBitacora.get(i).getFec_modifica());
				consulta.setString(18, listaRegistrosBitacora.get(i).getCor_revisa());
				consulta.setTimestamp(19, listaRegistrosBitacora.get(i).getFec_revision());
				consulta.setString(20, listaRegistrosBitacora.get(i).getObs_coordinador());
				consulta.setLong(21, 0);
				consulta.setLong(22, listaRegistrosBitacora.get(i).getId_bitacora());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			long id = 0;
			dao_bitacora dao = new dao_bitacora();
			id = dao.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia) {
				id1 = id;
			} else {
				id1 = secuencia;
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

	public void revisarMovimiento(modelo_movimiento_dn movimiento,
			List<modelo_movimiento_detalle_dn> listaMovimientoDetalle, modelo_bitacora bitacora,
			List<modelo_bitacora> listaRegistrosBitacora, long secuencia, modelo_solicitud solicitud)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			/* CABECERA */
			consulta = conexion.abrir().prepareStatement(
					"{CALL movimientoDN_actualizarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setString(1, movimiento.getTck_movimiento());
			consulta.setString(2, movimiento.getTip_pedido());
			consulta.setTimestamp(3, movimiento.getFec_solicitud());
			consulta.setLong(4, movimiento.getHor_solicitud());
			consulta.setTimestamp(5, movimiento.getFec_respuesta());
			consulta.setLong(6, movimiento.getHor_respuesta());
			consulta.setTimestamp(7, movimiento.getFec_ejecucion());
			consulta.setLong(8, movimiento.getHor_ejecucion());
			consulta.setLong(9, movimiento.getId_localidad());
			consulta.setLong(10, movimiento.getId_solicitante());
			consulta.setLong(11, movimiento.getId_usuario());
			consulta.setString(12, movimiento.getTur_movimiento());
			consulta.setTimestamp(13, movimiento.getFec_movimiento());
			consulta.setString(14, movimiento.getObs_movimiento());
			consulta.setString(15, movimiento.getUsu_revision_1());
			consulta.setString(16, movimiento.getUsu_revision_2());
			consulta.setString(17, movimiento.getUsu_revision_3());
			consulta.setString(18, movimiento.getEst_movimiento());
			consulta.setString(19, movimiento.getEst_validacion());
			consulta.setString(20, movimiento.getUsu_ingresa());
			consulta.setTimestamp(21, movimiento.getFec_ingresa());
			consulta.setString(22, movimiento.getUsu_modifica());
			consulta.setTimestamp(23, movimiento.getFec_modifica());
			consulta.setLong(24, movimiento.getId_movimiento());
			consulta.executeUpdate();
			/* SE ELIMINA EL DETALLE ASOCIADO A LA CABECERA */
			consulta = conexion.abrir().prepareStatement("{CALL movimientoDN_eliminarMovimientoDetalle(?)}");
			consulta.setLong(1, movimiento.getId_movimiento());
			consulta.executeUpdate();
			/* DETALLE */
			for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL movimientoDN_insertarMovimientoDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, movimiento.getId_movimiento());
				consulta.setLong(2, listaMovimientoDetalle.get(i).getId_articulo());
				/* ANTERIOR */
				consulta.setString(3, listaMovimientoDetalle.get(i).getCod_articulo_anterior());
				consulta.setString(4, listaMovimientoDetalle.get(i).getDes_articulo_anterior());
				consulta.setLong(5, listaMovimientoDetalle.get(i).getId_cat_articulo_anterior());
				consulta.setString(6, listaMovimientoDetalle.get(i).getNom_cat_articulo_anterior());
				consulta.setString(7, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_anterior());
				consulta.setString(8, listaMovimientoDetalle.get(i).getEs_fecha_anterior());
				consulta.setLong(9, listaMovimientoDetalle.get(i).getId_fec_respaldo_anterior());
				consulta.setString(10, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_anterior());
				consulta.setTimestamp(11, listaMovimientoDetalle.get(i).getFec_inicio_anterior());
				consulta.setTimestamp(12, listaMovimientoDetalle.get(i).getFec_fin_anterior());
				consulta.setLong(13, listaMovimientoDetalle.get(i).getId_cap_articulo_anterior());
				consulta.setString(14, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_anterior());
				consulta.setString(15, listaMovimientoDetalle.get(i).getTip_respaldo_anterior());
				consulta.setString(16, listaMovimientoDetalle.get(i).getNom_tip_respaldo_anterior());
				consulta.setString(17, listaMovimientoDetalle.get(i).getId_contenedor_anterior());
				consulta.setLong(18, listaMovimientoDetalle.get(i).getId_ubicacion_anterior());
				consulta.setString(19, listaMovimientoDetalle.get(i).getNom_ubicacion_anterior());
				consulta.setLong(20, listaMovimientoDetalle.get(i).getHora_llegada_custodia_anterior());
				consulta.setLong(21, listaMovimientoDetalle.get(i).getHora_salida_custodia_anterior());
				consulta.setString(22, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_anterior());
				consulta.setString(23, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_anterior());
				/* ACTUAL */
				consulta.setString(24, listaMovimientoDetalle.get(i).getCod_articulo_actual());
				consulta.setString(25, listaMovimientoDetalle.get(i).getDes_articulo_actual());
				consulta.setLong(26, listaMovimientoDetalle.get(i).getId_cat_articulo_actual());
				consulta.setString(27, listaMovimientoDetalle.get(i).getNom_cat_articulo_actual());
				consulta.setString(28, listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual());
				consulta.setString(29, listaMovimientoDetalle.get(i).getEs_fecha_actual());
				consulta.setLong(30, listaMovimientoDetalle.get(i).getId_fec_respaldo_actual());
				consulta.setString(31, listaMovimientoDetalle.get(i).getNom_id_fec_respaldo_actual());
				consulta.setTimestamp(32, listaMovimientoDetalle.get(i).getFec_inicio_actual());
				consulta.setTimestamp(33, listaMovimientoDetalle.get(i).getFec_fin_actual());
				consulta.setLong(34, listaMovimientoDetalle.get(i).getId_cap_articulo_actual());
				consulta.setString(35, listaMovimientoDetalle.get(i).getNom_id_cap_articulo_actual());
				consulta.setString(36, listaMovimientoDetalle.get(i).getTip_respaldo_actual());
				consulta.setString(37, listaMovimientoDetalle.get(i).getNom_tip_respaldo_actual());
				consulta.setString(38, listaMovimientoDetalle.get(i).getId_contenedor_actual());
				consulta.setLong(39, listaMovimientoDetalle.get(i).getId_ubicacion_actual());
				consulta.setString(40, listaMovimientoDetalle.get(i).getNom_ubicacion_actual());
				consulta.setLong(41, listaMovimientoDetalle.get(i).getHora_llegada_custodia_actual());
				consulta.setLong(42, listaMovimientoDetalle.get(i).getHora_salida_custodia_actual());
				consulta.setString(43, listaMovimientoDetalle.get(i).getRemesa_ingreso_custodia_actual());
				consulta.setString(44, listaMovimientoDetalle.get(i).getRemesa_salida_custodia_actual());
				consulta.setString(45, listaMovimientoDetalle.get(i).getRevision_1());
				consulta.setString(46, listaMovimientoDetalle.get(i).getRevision_2());
				consulta.setString(47, listaMovimientoDetalle.get(i).getRevision_3());
				consulta.setString(48, listaMovimientoDetalle.get(i).getActualiza_inventario());
				consulta.setString(49, listaMovimientoDetalle.get(i).getEst_detalle_movimiento());
				consulta.setString(50, listaMovimientoDetalle.get(i).getUsu_ingresa());
				consulta.setTimestamp(51, listaMovimientoDetalle.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			/*
			 * DE EXISTIR ALGUN CAMBIO EN LA REVISION SE ACTUALIZAN LOS REGISTROS EN
			 * BITACORA
			 */
			for (int i = 0; i < listaRegistrosBitacora.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL bitacora_modificarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, bitacora.getId_cliente());
				consulta.setString(2, movimiento.getTck_movimiento());
				consulta.setLong(3, listaRegistrosBitacora.get(i).getId_turno());
				consulta.setLong(4, listaRegistrosBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(5, listaRegistrosBitacora.get(i).getId_tipo_tarea());
				consulta.setLong(6, movimiento.getId_solicitante());
				consulta.setString(7, listaRegistrosBitacora.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(8, listaRegistrosBitacora.get(i).getFec_inicio());
				consulta.setTimestamp(9, listaRegistrosBitacora.get(i).getFec_fin());
				consulta.setLong(10, listaRegistrosBitacora.get(i).getId_estado_bitacora());
				consulta.setString(11, listaRegistrosBitacora.get(i).getCumplimiento());
				consulta.setLong(12, listaRegistrosBitacora.get(i).getId_localidad());
				consulta.setString(13, listaRegistrosBitacora.get(i).getEst_bitacora());
				consulta.setString(14, listaRegistrosBitacora.get(i).getUsu_ingresa());
				consulta.setTimestamp(15, listaRegistrosBitacora.get(i).getFec_ingresa());
				consulta.setString(16, listaRegistrosBitacora.get(i).getUsu_modifica());
				consulta.setTimestamp(17, listaRegistrosBitacora.get(i).getFec_modifica());
				consulta.setString(18, listaRegistrosBitacora.get(i).getCor_revisa());
				consulta.setTimestamp(19, listaRegistrosBitacora.get(i).getFec_revision());
				consulta.setString(20, listaRegistrosBitacora.get(i).getObs_coordinador());
				consulta.setLong(21, 0);
				consulta.setLong(22, listaRegistrosBitacora.get(i).getId_bitacora());
				consulta.executeUpdate();
			}
			/* REGISTRO EN BITACORA */
			long id = 0;
			dao_bitacora dao = new dao_bitacora();
			id = dao.obtenerNuevoId();
			long id1 = 0;
			if (id > secuencia) {
				id1 = id;
			} else {
				id1 = secuencia;
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
			/* SE ACTUALIZA LA SOLICITUD */
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
