package bp.aplicaciones.sibod.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;

public class dao_movimiento {

	public Long obtenerNuevoTicketMovimiento(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		dao_secuencia secuencia = new dao_secuencia();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL movimiento_obtenerNuevoTicketMovimiento(?)}");
			consulta.setString(1, localidad);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				if (resultado.getLong("tck_movimiento") == 0) {
					id = secuencia.obtenerSecuencia(localidad);
				} else {
					id = resultado.getLong("tck_movimiento") + 1;
				}
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

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL movimiento_obtenerNuevoID()}");
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

	public List<modelo_movimiento> obtenerMovimientos(String criterio, String fecha_inicio, String fecha_fin,
			String localidad, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_movimiento> lista_movimientos = new ArrayList<modelo_movimiento>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL movimiento_obtenerMovimientos(?,?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, localidad);
			consulta.setInt(5, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_movimientos.add(new modelo_movimiento(resultado.getLong("id_movimiento"),
						resultado.getString("tck_movimiento"), resultado.getLong("id_articulo"),
						resultado.getLong("id_estado"), resultado.getString("cod_articulo"),
						resultado.getString("des_articulo"), resultado.getString("ubi_articulo"),
						resultado.getString("tip_movimiento"), resultado.getInt("sto_anterior"),
						resultado.getInt("sto_actual"), resultado.getInt("can_afectada"),
						resultado.getLong("id_localidad"), resultado.getLong("id_solicitante"),
						resultado.getString("nom_solicitante"), resultado.getString("ape_solicitante"),
						resultado.getLong("id_usuario"), resultado.getString("nom_usuario"),
						resultado.getString("ape_usuario"), resultado.getString("emp_solicitante"),
						resultado.getString("emp_ubicacion"), resultado.getString("cat_articulo"),
						resultado.getString("tur_movimiento"), resultado.getTimestamp("fec_movimiento"),
						resultado.getString("obs_movimiento"), resultado.getString("est_movimiento"),
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

	public void insertarMovimiento(List<modelo_movimiento> listaMovimiento, modelo_bitacora bitacora, long secuencia)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = 0;
		String tck_listaMovimiento = "";
		try {
			PreparedStatement consulta = null;
			for (int i = 0; i < listaMovimiento.size(); i++) {
				id = obtenerNuevoId();
				if (listaMovimiento.get(i).getTck_movimiento() == null) {
					tck_listaMovimiento = "TM-000" + id;
				} else {
					tck_listaMovimiento = listaMovimiento.get(i).getTck_movimiento();
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL movimiento_insertarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id);
				consulta.setString(2, tck_listaMovimiento);
				consulta.setLong(3, listaMovimiento.get(i).getId_articulo());
				consulta.setLong(4, listaMovimiento.get(i).getId_ubicacion());
				consulta.setLong(5, listaMovimiento.get(i).getId_estado());
				consulta.setString(6, listaMovimiento.get(i).getTip_movimiento());
				consulta.setInt(7, listaMovimiento.get(i).getSto_anterior());
				consulta.setInt(8, listaMovimiento.get(i).getSto_actual());
				consulta.setInt(9, listaMovimiento.get(i).getCan_afectada());
				consulta.setLong(10, listaMovimiento.get(i).getId_localidad());
				consulta.setLong(11, listaMovimiento.get(i).getId_solicitante());
				consulta.setLong(12, listaMovimiento.get(i).getId_usuario());
				consulta.setString(13, listaMovimiento.get(i).getTur_movimiento());
				consulta.setTimestamp(14, listaMovimiento.get(i).getFec_movimiento());
				consulta.setString(15, listaMovimiento.get(i).getObs_movimiento().toUpperCase());
				consulta.setString(16, listaMovimiento.get(i).getEst_movimiento());
				consulta.setString(17, listaMovimiento.get(i).getUsu_ingresa());
				consulta.setTimestamp(18, listaMovimiento.get(i).getFec_ingresa());
				consulta.executeUpdate();
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
						.prepareStatement("{CALL bitacora_insertarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, id1);
				consulta.setLong(2, setearEmpresaDesdeUbicacion(listaMovimiento.get(i).getId_ubicacion(),
						listaMovimiento.get(i).getId_localidad()));
				consulta.setString(3, tck_listaMovimiento);
				consulta.setLong(4, bitacora.getId_turno());
				consulta.setLong(5, bitacora.getId_tipo_servicio());
				consulta.setLong(6, bitacora.getId_tipo_clasificacion());
				consulta.setLong(7, bitacora.getId_tipo_tarea());
				consulta.setLong(8, bitacora.getId_solicitante());
				String tipoDePedido = "INGRESO";
				String palabra = "EN";
				if (listaMovimiento.get(i).getTip_movimiento().equals("E")) {
					tipoDePedido = "EGRESO";
					palabra = "DE";
				}
				bitacora.setDescripcion("SE REALIZA EL " + tipoDePedido + " DE "
						+ listaMovimiento.get(i).getCan_afectada() + " ARTICULO(S) "
						+ setearNombreEstadoArticulo(listaMovimiento.get(i).getId_estado()) + "(S) "
						+ setearNombreArticulo(listaMovimiento.get(i).getId_articulo(),
								listaMovimiento.get(i).getId_localidad())
						+ " " + palabra + " LA UBICACION " + setearNombreUbicacion(
								listaMovimiento.get(i).getId_ubicacion(), listaMovimiento.get(i).getId_localidad()));
				consulta.setString(9, bitacora.getArea());
				consulta.setString(10, bitacora.getRack());
				consulta.setString(11, bitacora.getDescripcion().toUpperCase());
				consulta.setTimestamp(12, bitacora.getFec_inicio());
				consulta.setTimestamp(13, bitacora.getFec_fin());
				consulta.setLong(14, bitacora.getId_estado_bitacora());
				consulta.setString(15, bitacora.getCumplimiento());
				consulta.setString(16, bitacora.getCumplimientoSLA());
				consulta.setString(17, bitacora.getComentarioCumplimientoSLA());
				consulta.setLong(18, bitacora.getId_localidad());
				consulta.setString(19, bitacora.getEst_bitacora());
				consulta.setString(20, bitacora.getUsu_ingresa());
				consulta.setTimestamp(21, bitacora.getFec_ingresa());
				consulta.setString(22, bitacora.getUsu_modifica());
				consulta.setTimestamp(23, bitacora.getFec_modifica());
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

	public Long setearEmpresaDesdeUbicacion(long id_ubicacion, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_empresa = 0;
		List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
		dao_ubicacion dao = new dao_ubicacion();
		listaUbicacion = dao.obtenerUbicaciones("", String.valueOf(id_dc), 5, 0, 0, id_ubicacion, 0);
		if (listaUbicacion.size() > 0) {
			id_empresa = listaUbicacion.get(0).getId_empresa();
		}
		return id_empresa;
	}

	public long obtenerIdTipoServicioAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_servicio = 0;
		dao_bitacora dao = new dao_bitacora();
		List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
		listaBitacora = dao.obtenerBitacoras(ticket, 7, id_tipo_tarea, "", "", id_dc, "", "", 0, 0, "", 0);
		if (listaBitacora.size() > 0) {
			id_tipo_servicio = listaBitacora.get(0).getId_tipo_servicio();
		}
		return id_tipo_servicio;
	}
	
	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_clasificacion = 0;
		dao_bitacora dao = new dao_bitacora();
		List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
		listaBitacora = dao.obtenerBitacoras(ticket, 7, id_tipo_tarea, "", "", id_dc, "", "", 0, 0, "", 0);
		if (listaBitacora.size() > 0) {
			id_tipo_clasificacion = listaBitacora.get(0).getId_tipo_clasificacion();
		}
		return id_tipo_clasificacion;
	}

	public String setearNombreUbicacion(long id_ubicacion, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_ubicacion = "";
		List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
		dao_ubicacion dao = new dao_ubicacion();
		listaUbicacion = dao.obtenerUbicaciones("", String.valueOf(id_dc), 5, 0, 0, id_ubicacion, 0);
		if (listaUbicacion.size() > 0) {
			nombre_ubicacion = listaUbicacion.get(0).toStringUbicacion();
		}
		return nombre_ubicacion;
	}

	public String setearNombreArticulo(long id_articulo, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_articulo = "";
		List<modelo_articulo> listaArticulos = new ArrayList<modelo_articulo>();
		dao_articulo dao = new dao_articulo();
		listaArticulos = dao.obtenerArticulos(String.valueOf(id_articulo), String.valueOf(id_dc), "", 11, 0);
		if (listaArticulos.size() > 0) {
			nombre_articulo = listaArticulos.get(0).toStringArticulo();
		}
		return nombre_articulo;
	}

	public String setearNombreEstadoArticulo(long id_estado)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_estado = "";
		List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
		dao_estado_articulo dao = new dao_estado_articulo();
		listaEstados = dao.obtenerEstados(String.valueOf(id_estado), 2, "");
		if (listaEstados.size() > 0) {
			nombre_estado = listaEstados.get(0).getNom_estado();
		}
		return nombre_estado;
	}

	public void modificarMovimiento(List<modelo_movimiento> listaMovimiento, List<modelo_bitacora> listaBitacora,
			int tipo1, int tipo2) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			/* SE ACTUALIZA LOS MOVIMIENTOS */
			for (int i = 0; i < listaMovimiento.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL movimiento_modificarMovimiento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setString(1, listaMovimiento.get(i).getTck_movimiento());
				consulta.setLong(2, listaMovimiento.get(i).getId_articulo());
				consulta.setLong(3, listaMovimiento.get(i).getId_ubicacion());
				consulta.setLong(4, listaMovimiento.get(i).getId_estado());
				consulta.setString(5, listaMovimiento.get(i).getTip_movimiento());
				consulta.setInt(6, listaMovimiento.get(i).getSto_anterior());
				consulta.setInt(7, listaMovimiento.get(i).getSto_actual());
				consulta.setInt(8, listaMovimiento.get(i).getCan_afectada());
				consulta.setLong(9, listaMovimiento.get(i).getId_localidad());
				consulta.setLong(10, listaMovimiento.get(i).getId_solicitante());
				consulta.setLong(11, listaMovimiento.get(i).getId_usuario());
				consulta.setString(12, listaMovimiento.get(i).getTur_movimiento());
				consulta.setTimestamp(13, listaMovimiento.get(i).getFec_movimiento());
				consulta.setString(14, listaMovimiento.get(i).getObs_movimiento().toUpperCase());
				consulta.setString(15, listaMovimiento.get(i).getEst_movimiento());
				consulta.setString(16, listaMovimiento.get(i).getUsu_modifica());
				consulta.setTimestamp(17, listaMovimiento.get(i).getFec_modifica());
				consulta.setLong(18, listaMovimiento.get(i).getId_movimiento());
				consulta.setInt(19, tipo1);
				consulta.executeUpdate();
			}
			/* SE ACTUALIZA LOS LOGS DE EVENTOS */
			for (int i = 0; i < listaBitacora.size(); i++) {
				consulta = conexion.abrir().prepareStatement(
						"{CALL bitacora_modificarBitacora(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				consulta.setLong(1, listaBitacora.get(i).getId_cliente());
				consulta.setString(2, listaBitacora.get(i).getTicket_externo().toUpperCase());
				consulta.setLong(3, listaBitacora.get(i).getId_turno());
				consulta.setLong(4, listaBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(5, listaBitacora.get(i).getId_tipo_servicio());
				consulta.setLong(6, listaBitacora.get(i).getId_tipo_tarea());
				consulta.setLong(7, listaBitacora.get(i).getId_solicitante());
				consulta.setString(8, listaBitacora.get(i).getArea());
				consulta.setString(9, listaBitacora.get(i).getRack());
				consulta.setString(10, listaBitacora.get(i).getDescripcion().toUpperCase());
				consulta.setTimestamp(11, listaBitacora.get(i).getFec_inicio());
				consulta.setTimestamp(12, listaBitacora.get(i).getFec_fin());
				consulta.setLong(13, listaBitacora.get(i).getId_estado_bitacora());
				consulta.setString(14, listaBitacora.get(i).getCumplimiento());
				consulta.setString(15, listaBitacora.get(i).getCumplimientoSLA());
				consulta.setString(16, listaBitacora.get(i).getComentarioCumplimientoSLA());
				consulta.setLong(17, listaBitacora.get(i).getId_localidad());
				consulta.setString(18, listaBitacora.get(i).getEst_bitacora());
				consulta.setString(19, listaBitacora.get(i).getUsu_ingresa());
				consulta.setTimestamp(20, listaBitacora.get(i).getFec_ingresa());
				consulta.setString(21, listaBitacora.get(i).getUsu_modifica());
				consulta.setTimestamp(22, listaBitacora.get(i).getFec_modifica());
				consulta.setString(23, listaBitacora.get(i).getCor_revisa());
				consulta.setTimestamp(24, listaBitacora.get(i).getFec_revision());
				consulta.setString(25, listaBitacora.get(i).getObs_coordinador());
				consulta.setLong(26, tipo2);
				consulta.setLong(27, listaBitacora.get(i).getId_bitacora());
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
