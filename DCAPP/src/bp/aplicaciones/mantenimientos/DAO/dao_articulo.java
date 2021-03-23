package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;

//Articulos
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_estado_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

public class dao_articulo {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL articulo_obtenerNuevoID()}");
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

	public List<modelo_articulo> obtenerArticulos(String criterio, String localidad, String empresa, int tipo,
			int limite) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_articulo> lista_articulos = new ArrayList<modelo_articulo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL articulo_obtenerArticulos(?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setString(2, localidad);
			consulta.setString(3, empresa);
			consulta.setInt(4, tipo);
			consulta.setInt(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_articulos.add(new modelo_articulo(resultado.getLong("id_articulo"),
						resultado.getString("cod_articulo"), resultado.getString("des_articulo"),
						resultado.getString("nom_empresa"), resultado.getLong("id_categoria"),
						resultado.getString("nom_categoria"), resultado.getLong("id_ubicacion"),
						resultado.getString("nom_ubicacion"), resultado.getInt("sto_articulo"),
						resultado.getBlob("img_articulo"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("marca"),
						resultado.getString("modelo"), resultado.getString("serie"),
						resultado.getString("codigo_activo"), resultado.getString("est_articulo"),
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

	public void insertarArticulo(modelo_articulo articulo, List<modelo_relacion_articulo_ubicacion> relacion,
			List<modelo_relacion_estado_articulo_ubicacion> relacion_estado) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		PreparedStatement consulta = null;
		long id = obtenerNuevoId();
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL articulo_insertarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, articulo.getCod_articulo().toUpperCase());
			consulta.setString(3, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(4, articulo.getId_categoria());
			consulta.setBlob(5, articulo.getImg_articulo());
			consulta.setLong(6, articulo.getId_localidad());
			consulta.setString(7, articulo.getMarca());
			consulta.setString(8, articulo.getModelo());
			consulta.setString(9, articulo.getSerie());
			consulta.setString(10, articulo.getCodig_activo());
			consulta.setString(11, articulo.getEst_articulo());
			consulta.setString(12, articulo.getUsu_ingresa());
			consulta.setTimestamp(13, articulo.getFec_ingresa());
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarEstadoArticuloUbicacion(?)}");
			consulta.setLong(1, id);
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloUbicacion(?)}");
			consulta.setLong(1, id);
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id_relacion = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id_relacion = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloUbicacion (?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id_relacion);
				consulta.setLong(2, id);
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setLong(4, relacion.get(i).getSto_articulo());
				consulta.setString(5, relacion.get(i).getEst_relacion());
				consulta.setString(6, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(7, relacion.get(i).getFec_ingresa());
				consulta.executeUpdate();
				for (int j = 0; j < relacion_estado.size(); j++) {
					Long id_relacion_estado = (long) 0;
					consulta = conexion.abrir()
							.prepareStatement("{CALL relacion_obtenerNuevoIDEstadoArticuloUbicacion()}");
					resultado = consulta.executeQuery();
					while (resultado.next()) {
						id_relacion_estado = resultado.getLong("id_relacion") + 1;
					}
					consulta = conexion.abrir().prepareStatement(
							"{CALL relacion_insertarEstadoArticuloUbicacion (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
					consulta.setLong(1, id_relacion_estado);
					consulta.setLong(2, relacion_estado.get(j).getId_estado());
					consulta.setLong(3, id);
					consulta.setLong(4, relacion.get(i).getId_ubicacion());
					consulta.setLong(5, id_relacion);
					consulta.setLong(6, relacion_estado.get(j).getStock());
					consulta.setString(7, relacion_estado.get(j).getEst_relacion());
					consulta.setString(8, relacion_estado.get(j).getUsu_ingresa());
					consulta.setTimestamp(9, relacion_estado.get(j).getFec_ingresa());
					consulta.executeUpdate();
				}
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

	public void modificarArticulo(modelo_articulo articulo, List<modelo_relacion_articulo_ubicacion> relacion,
			List<modelo_relacion_estado_articulo_ubicacion> relacion_estado1,
			List<modelo_relacion_estado_articulo_ubicacion> relacion_estado2, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			consulta = conexion.abrir()
					.prepareStatement("{CALL articulo_modificarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, articulo.getCod_articulo().toUpperCase());
			consulta.setString(2, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(3, articulo.getId_categoria());
			consulta.setBlob(4, articulo.getImg_articulo());
			consulta.setLong(5, articulo.getId_localidad());
			consulta.setString(6, articulo.getMarca());
			consulta.setString(7, articulo.getModelo());
			consulta.setString(8, articulo.getSerie());
			consulta.setString(9, articulo.getCodig_activo());
			consulta.setString(10, articulo.getEst_articulo());
			consulta.setString(11, articulo.getUsu_modifica());
			consulta.setTimestamp(12, articulo.getFec_modifica());
			consulta.setLong(13, articulo.getId_articulo());
			consulta.setLong(14, tipo);
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarEstadoArticuloUbicacion(?)}");
			consulta.setLong(1, articulo.getId_articulo());
			consulta.executeUpdate();
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloUbicacion(?)}");
			consulta.setLong(1, articulo.getId_articulo());
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id_relacion = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id_relacion = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloUbicacion (?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id_relacion);
				consulta.setLong(2, articulo.getId_articulo());
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setLong(4, relacion.get(i).getSto_articulo());
				consulta.setString(5, relacion.get(i).getEst_relacion());
				consulta.setString(6, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(7, relacion.get(i).getFec_ingresa());
				consulta.executeUpdate();
				/*
				 * SE LLENA LOS ESTADOS ANTERIORES PARA EL ARTICULO CON SUS RESPECTIVOS STOCKS
				 */
				for (int j = 0; j < relacion_estado1.size(); j++) {
					if (relacion_estado1.get(j).getId_ubicacion() == relacion.get(i).getId_ubicacion()) {
						Long id_relacion_estado1 = (long) 0;
						consulta = conexion.abrir()
								.prepareStatement("{CALL relacion_obtenerNuevoIDEstadoArticuloUbicacion()}");
						resultado = consulta.executeQuery();
						while (resultado.next()) {
							id_relacion_estado1 = resultado.getLong("id_relacion") + 1;
						}
						consulta = conexion.abrir().prepareStatement(
								"{CALL relacion_insertarEstadoArticuloUbicacion (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
						consulta.setLong(1, id_relacion_estado1);
						consulta.setLong(2, relacion_estado1.get(j).getId_estado());
						consulta.setLong(3, articulo.getId_articulo());
						consulta.setLong(4, relacion.get(i).getId_ubicacion());
						consulta.setLong(5, id_relacion);
						consulta.setLong(6, relacion_estado1.get(j).getStock());
						consulta.setString(7, relacion_estado1.get(j).getEst_relacion());
						consulta.setString(8, relacion_estado1.get(j).getUsu_ingresa());
						consulta.setTimestamp(9, relacion_estado1.get(j).getFec_ingresa());
						consulta.executeUpdate();
					}
				}
				/* SE LLENA LOS ESTADOS NUEVOS PARA EL ARTICULO CON STOCK 0 */
				for (int j = 0; j < relacion_estado2.size(); j++) {
					Long id_relacion_estado2 = (long) 0;
					consulta = conexion.abrir()
							.prepareStatement("{CALL relacion_obtenerNuevoIDEstadoArticuloUbicacion()}");
					resultado = consulta.executeQuery();
					while (resultado.next()) {
						id_relacion_estado2 = resultado.getLong("id_relacion") + 1;
					}
					consulta = conexion.abrir().prepareStatement(
							"{CALL relacion_insertarEstadoArticuloUbicacion (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
					consulta.setLong(1, id_relacion_estado2);
					consulta.setLong(2, relacion_estado2.get(j).getId_estado());
					consulta.setLong(3, articulo.getId_articulo());
					consulta.setLong(4, relacion.get(i).getId_ubicacion());
					consulta.setLong(5, id_relacion);
					consulta.setLong(6, relacion_estado2.get(j).getStock());
					consulta.setString(7, relacion_estado2.get(j).getEst_relacion());
					consulta.setString(8, relacion_estado2.get(j).getUsu_ingresa());
					consulta.setTimestamp(9, relacion_estado2.get(j).getFec_ingresa());
					consulta.executeUpdate();
				}
			}
			if (solicitud.getId_mantenimiento() == 6) {
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

	public void activarDesactivarArticulo(modelo_articulo articulo, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			conexion.abrir().setAutoCommit(false);
			consulta = conexion.abrir()
					.prepareStatement("{CALL articulo_modificarArticulo (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, articulo.getCod_articulo().toUpperCase());
			consulta.setString(2, articulo.getDes_articulo().toUpperCase());
			consulta.setLong(3, articulo.getId_categoria());
			consulta.setBlob(4, articulo.getImg_articulo());
			consulta.setLong(5, articulo.getId_localidad());
			consulta.setString(6, articulo.getMarca());
			consulta.setString(7, articulo.getModelo());
			consulta.setString(8, articulo.getSerie());
			consulta.setString(9, articulo.getCodig_activo());
			consulta.setString(10, articulo.getEst_articulo());
			consulta.setString(11, articulo.getUsu_modifica());
			consulta.setTimestamp(12, articulo.getFec_modifica());
			consulta.setLong(13, articulo.getId_articulo());
			consulta.setLong(14, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 6) {
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

	public void activarDesactivarArticulo(modelo_articulo articulo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articulo_desactivarArticulo (?, ?, ?, ?)}");
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

	public void actualizarImagenArticulo(modelo_articulo articulo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articulo_actualizarImagenArticulo (?, ?, ?, ?)}");
			consulta.setBlob(1, articulo.getImg_articulo());
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

	public void eliminarArticulo(Long id_articulo) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL articulo_eliminarArticulo (?)}");
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

}
