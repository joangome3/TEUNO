package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;

//Articulos
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

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

public class dao_articulo_dn {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL articuloDN_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_articulo") + 1;
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

	public int obtenerPosicionMaximaEnUbicacion(long id_ubicacion)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int posicionMaxima = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_obtenerPosicionMaximaDeUbicacion(?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setLong(2, id_ubicacion);
			consulta.execute();
			posicionMaxima = consulta.getInt(1);
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
		return posicionMaxima;
	}

	public int obtenerCapacidadMaximaEnUbicacion(long id_ubicacion, String valida_capacidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int capacidadMaxima = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_obtenerCapacidadMaximaEnUbicacion(?,?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setLong(2, id_ubicacion);
			consulta.setString(3, valida_capacidad);
			consulta.execute();
			capacidadMaxima = consulta.getInt(1);
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
		return capacidadMaxima;
	}

	public int obtenerTotalArticulosEnUbicacion(long id_ubicacion)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalArticulos = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_obtenerTotalArticulosEnUbicacion(?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setLong(2, id_ubicacion);
			consulta.execute();
			totalArticulos = consulta.getInt(1);
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
		return totalArticulos;
	}

	public String seValidaCapacidadEnUbicacion(long id_ubicacion)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		String seValidaCapacidad = "N";
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_seValidaCapacidadEnUbicacion(?)}");
			consulta.registerOutParameter(1, Types.VARCHAR);
			consulta.setLong(2, id_ubicacion);
			consulta.execute();
			seValidaCapacidad = consulta.getString(1);
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
		return seValidaCapacidad;
	}

	public int validarSiCodigoYFechaDeInicioDeArticuloExiste(String codigo, String fecha_inicio, long categoria)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalArticulos = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_validarSiCodigoYFechaInicioDeArticuloExiste(?,?,?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, codigo);
			consulta.setString(3, fecha_inicio);
			consulta.setLong(4, categoria);
			consulta.execute();
			totalArticulos = consulta.getInt(1);
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
		return totalArticulos;
	}

	public int validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(String codigo, String fecha_inicio,
			String fecha_fin, long categoria) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int totalArticulos = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL articuloDN_validarSiCodigoYFechaInicioYFechaFinDeArticuloExiste(?,?,?,?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setString(2, codigo);
			consulta.setString(3, fecha_inicio);
			consulta.setString(4, fecha_fin);
			consulta.setLong(5, categoria);
			consulta.execute();
			totalArticulos = consulta.getInt(1);
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
		return totalArticulos;
	}

	public List<modelo_articulo_dn> obtenerArticulos(String parametro1, String parametro2, String parametro3,
			int parametro4, int parametro5, String parametro6, String parametro7)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_articulo_dn> lista_articulos = new ArrayList<modelo_articulo_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_obtenerArticulos(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, parametro1.toUpperCase());
			consulta.setString(2, parametro2);
			consulta.setString(3, parametro3);
			consulta.setInt(4, parametro4);
			consulta.setInt(5, parametro5);
			consulta.setString(6, parametro6);
			consulta.setString(7, parametro7);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_articulos.add(new modelo_articulo_dn(resultado.getLong("id_articulo"),
						resultado.getString("cod_articulo"), resultado.getString("des_articulo"),
						resultado.getString("nom_empresa"), resultado.getLong("id_categoria"),
						resultado.getString("nom_categoria"), resultado.getLong("id_ubicacion"),
						resultado.getInt("pos_ubicacion"), resultado.getString("nom_ubicacion"),
						resultado.getInt("sto_articulo"), resultado.getBlob("img_articulo"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getLong("id_capacidad"), resultado.getString("nom_capacidad"),
						resultado.getString("si_ing_fec_inicio_fin"), resultado.getString("es_fecha"),
						resultado.getLong("id_fec_respaldo"), resultado.getString("nom_fec_respaldo"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_tip_respaldo"), resultado.getString("nom_tip_respaldo"),
						resultado.getString("id_contenedor"), resultado.getLong("hora_llegada_custodia"),
						resultado.getLong("hora_salida_custodia"), resultado.getString("remesa_ingreso_custodia"),
						resultado.getString("remesa_salida_custodia"), resultado.getString("est_articulo"),
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
		return lista_articulos;
	}

	public void insertarArticulo(modelo_articulo_dn articulo, List<modelo_relacion_articulo_ubicacion_dn> relacion)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		PreparedStatement consulta = null;
		long id = obtenerNuevoId();
		try {
			consulta = conexion.abrir().prepareStatement(
					"{CALL articuloDN_insertarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, articulo.getCod_articulo().toUpperCase());
			consulta.setString(3, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(4, articulo.getId_categoria());
			consulta.setBlob(5, articulo.getImg_articulo());
			consulta.setLong(6, articulo.getId_localidad());
			consulta.setLong(7, articulo.getId_capacidad());
			consulta.setString(8, articulo.getSi_ing_fec_inicio_fin());
			consulta.setString(9, articulo.getEs_fecha());
			consulta.setLong(10, articulo.getId_fec_respaldo());
			consulta.setTimestamp(11, articulo.getFec_inicio());
			consulta.setTimestamp(12, articulo.getFec_fin());
			consulta.setLong(13, articulo.getId_tip_respaldo());
			consulta.setString(14, articulo.getId_contenedor());
			consulta.setString(15, articulo.getEst_articulo());
			consulta.setString(16, articulo.getUsu_ingresa());
			consulta.setTimestamp(17, articulo.getFec_ingresa());
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloDNUbicacion(?)}");
			consulta.setLong(1, id);
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id_relacion = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloDNUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id_relacion = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloDNUbicacion (?, ?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id_relacion);
				consulta.setLong(2, id);
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setInt(4, relacion.get(i).getPos_ubicacion());
				consulta.setLong(5, relacion.get(i).getSto_articulo());
				consulta.setString(6, relacion.get(i).getEst_relacion());
				consulta.setString(7, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(8, relacion.get(i).getFec_ingresa());
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

	public void modificarArticulo(modelo_articulo_dn articulo, List<modelo_relacion_articulo_ubicacion_dn> relacion,
			modelo_solicitud solicitud, int tipo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			consulta = conexion.abrir().prepareStatement(
					"{CALL articuloDN_modificarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, articulo.getCod_articulo().toUpperCase());
			consulta.setString(2, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(3, articulo.getId_categoria());
			consulta.setBlob(4, articulo.getImg_articulo());
			consulta.setLong(5, articulo.getId_localidad());
			consulta.setLong(6, articulo.getId_capacidad());
			consulta.setString(7, articulo.getSi_ing_fec_inicio_fin());
			consulta.setString(8, articulo.getEs_fecha());
			consulta.setLong(9, articulo.getId_fec_respaldo());
			consulta.setTimestamp(10, articulo.getFec_inicio());
			consulta.setTimestamp(11, articulo.getFec_fin());
			consulta.setLong(12, articulo.getId_tip_respaldo());
			consulta.setString(13, articulo.getId_contenedor());
			consulta.setString(14, articulo.getEst_articulo());
			consulta.setString(15, articulo.getUsu_modifica());
			consulta.setTimestamp(16, articulo.getFec_modifica());
			consulta.setLong(17, articulo.getId_articulo());
			consulta.setLong(18, tipo);
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloDNUbicacion(?)}");
			consulta.setLong(1, articulo.getId_articulo());
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id_relacion = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloDNUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id_relacion = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloDNUbicacion (?, ?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id_relacion);
				consulta.setLong(2, articulo.getId_articulo());
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setInt(4, relacion.get(i).getPos_ubicacion());
				consulta.setLong(5, relacion.get(i).getSto_articulo());
				consulta.setString(6, relacion.get(i).getEst_relacion());
				consulta.setString(7, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(8, relacion.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			if (solicitud.getId_mantenimiento() == 16) {
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

	public void modificarArticulo(modelo_articulo_dn articulo, List<modelo_relacion_articulo_ubicacion_dn> relacion,
			int tipo) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			consulta = conexion.abrir().prepareStatement(
					"{CALL articuloDN_modificarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, articulo.getCod_articulo().toUpperCase());
			consulta.setString(2, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(3, articulo.getId_categoria());
			consulta.setBlob(4, articulo.getImg_articulo());
			consulta.setLong(5, articulo.getId_localidad());
			consulta.setLong(6, articulo.getId_capacidad());
			consulta.setString(7, articulo.getSi_ing_fec_inicio_fin());
			consulta.setString(8, articulo.getEs_fecha());
			consulta.setLong(9, articulo.getId_fec_respaldo());
			consulta.setTimestamp(10, articulo.getFec_inicio());
			consulta.setTimestamp(11, articulo.getFec_fin());
			consulta.setLong(12, articulo.getId_tip_respaldo());
			consulta.setString(13, articulo.getId_contenedor());
			consulta.setString(14, articulo.getEst_articulo());
			consulta.setString(15, articulo.getUsu_modifica());
			consulta.setTimestamp(16, articulo.getFec_modifica());
			consulta.setLong(17, articulo.getId_articulo());
			consulta.setLong(18, tipo);
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloDNUbicacion(?)}");
			consulta.setLong(1, articulo.getId_articulo());
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id_relacion = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloDNUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id_relacion = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloDNUbicacion (?, ?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id_relacion);
				consulta.setLong(2, articulo.getId_articulo());
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setInt(4, relacion.get(i).getPos_ubicacion());
				consulta.setLong(5, relacion.get(i).getSto_articulo());
				consulta.setString(6, relacion.get(i).getEst_relacion());
				consulta.setString(7, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(8, relacion.get(i).getFec_ingresa());
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

	public void activarDesactivarArticulo(modelo_articulo_dn articulo, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			consulta = conexion.abrir().prepareStatement(
					"{CALL articuloDN_modificarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, articulo.getCod_articulo().toUpperCase());
			consulta.setString(2, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(3, articulo.getId_categoria());
			consulta.setBlob(4, articulo.getImg_articulo());
			consulta.setLong(5, articulo.getId_localidad());
			consulta.setLong(6, articulo.getId_capacidad());
			consulta.setString(7, articulo.getSi_ing_fec_inicio_fin());
			consulta.setString(8, articulo.getEs_fecha());
			consulta.setLong(9, articulo.getId_fec_respaldo());
			consulta.setTimestamp(10, articulo.getFec_inicio());
			consulta.setTimestamp(11, articulo.getFec_fin());
			consulta.setLong(12, articulo.getId_tip_respaldo());
			consulta.setString(13, articulo.getId_contenedor());
			consulta.setString(14, articulo.getEst_articulo());
			consulta.setString(15, articulo.getUsu_modifica());
			consulta.setTimestamp(16, articulo.getFec_modifica());
			consulta.setLong(17, articulo.getId_articulo());
			consulta.setLong(18, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 16) {
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

	public void activarDesactivarArticulo(modelo_articulo_dn articulo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_desactivarArticulo (?, ?, ?, ?)}");
			consulta.setString(1, articulo.getEst_articulo());
			consulta.setString(2, articulo.getUsu_modifica());
			consulta.setTimestamp(3, articulo.getFec_modifica());
			consulta.setLong(4, articulo.getId_articulo());
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

	public void activarDesactivarArticulo(List<modelo_articulo_dn> listaArticulos) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			for (int i = 0; i < listaArticulos.size(); i++) {
				consulta = conexion.abrir().prepareStatement("{CALL articuloDN_desactivarArticulo (?, ?, ?, ?)}");
				consulta.setString(1, listaArticulos.get(i).getEst_articulo());
				consulta.setString(2, listaArticulos.get(i).getUsu_modifica());
				consulta.setTimestamp(3, listaArticulos.get(i).getFec_modifica());
				consulta.setLong(4, listaArticulos.get(i).getId_articulo());
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

	public void eliminarArticulo(Long id_articulo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_eliminarArticulo (?)}");
			consulta.setLong(1, id_articulo);
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

	public boolean actualizarUbicacionArticuloIzquierdaDerecha(Long id_articulo, long id_ubicacion, int pos_ubicacion,
			int tipo) throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		boolean ingresoCorrecto = false;
		conexion conexion = new conexion();
		try {
			conexion.abrir().setAutoCommit(false);
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_actualizarUbicacionArticulo (?, ?, ?, ?)}");
			consulta.setLong(1, id_articulo);
			consulta.setLong(2, id_ubicacion);
			consulta.setInt(3, pos_ubicacion);
			consulta.setInt(4, tipo);
			consulta.executeUpdate();
			consulta.close();
			conexion.abrir().commit();
			ingresoCorrecto = true;
		} catch (SQLException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return ingresoCorrecto;
	}

	public boolean actualizarUbicacionArticuloAbajoArriba(Long id_articulo_1, int pos_ubicacion_1, long id_articulo_2,
			int pos_ubicacion_2, long id_ubicacion_1, long id_ubicacion_2, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		boolean ingresoCorrecto = false;
		conexion conexion = new conexion();
		try {
			conexion.abrir().setAutoCommit(false);
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_actualizarUbicacionArticulo (?, ?, ?, ?)}");
			consulta.setLong(1, id_articulo_1);
			consulta.setLong(2, id_ubicacion_1);
			consulta.setInt(3, pos_ubicacion_1);
			consulta.setInt(4, tipo);
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL articuloDN_actualizarUbicacionArticulo (?, ?, ?, ?)}");
			consulta.setLong(1, id_articulo_2);
			consulta.setLong(2, id_ubicacion_2);
			consulta.setInt(3, pos_ubicacion_2);
			consulta.setInt(4, tipo);
			consulta.executeUpdate();
			consulta.close();
			conexion.abrir().commit();
			ingresoCorrecto = true;
		} catch (SQLException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return ingresoCorrecto;
	}

	public boolean actualizarUbicacionArticulos(List<modelo_articulo_dn> listaArticulos, long id_ubicacion,
			int pos_ubicacion, int tipo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		boolean ingresoCorrecto = false;
		try {
			conexion.abrir().setAutoCommit(false);
			PreparedStatement consulta = null;
			Iterator<modelo_articulo_dn> it = listaArticulos.iterator();
			int pos = pos_ubicacion;
			while (it.hasNext()) {
				consulta = conexion.abrir()
						.prepareStatement("{CALL articulodn_actualizarubicacionarticulo (?, ?, ?, ?)}");
				modelo_articulo_dn articulo = it.next();
				consulta.setLong(1, articulo.getId_articulo());
				consulta.setLong(2, id_ubicacion);
				consulta.setInt(3, pos);
				consulta.setInt(4, tipo);
				consulta.executeUpdate();
				pos++;
			}
			consulta.close();
			conexion.abrir().commit();
			ingresoCorrecto = true;
		} catch (SQLException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			ingresoCorrecto = false;
			conexion.abrir().rollback();
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return ingresoCorrecto;
	}

}
