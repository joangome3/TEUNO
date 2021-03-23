package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//Categorias
public class dao_categoria_dn {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL categoriaDN_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_categoria") + 1;
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

	public List<modelo_categoria_dn> obtenerCategorias(String criterio, String localidad, int tipo, long id, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_categoria_dn> lista_categorias = new ArrayList<modelo_categoria_dn>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL categoriaDN_obtenerCategorias(?,?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, localidad);
			consulta.setInt(3, tipo);
			consulta.setLong(4, id);
			consulta.setLong(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_categorias.add(new modelo_categoria_dn(resultado.getLong("id_categoria"),
						resultado.getString("nom_categoria"), resultado.getString("des_categoria"),
						resultado.getLong("id_localidad"), resultado.getString("nom_localidad"),
						resultado.getString("mos_capacidad"), resultado.getString("mos_fec_inicio"),
						resultado.getString("mos_fec_fin"), resultado.getString("mos_tip_respaldo"),
						resultado.getString("mos_id_contenedor"), resultado.getString("est_categoria"),
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
		return lista_categorias;
	}

	public void insertarCategoria(modelo_categoria_dn categoria) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL categoriaDN_insertarCategoria(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, categoria.getNom_categoria().toUpperCase());
			consulta.setString(3, categoria.getDes_categoria().toUpperCase());
			consulta.setLong(4, categoria.getId_localidad());
			consulta.setString(5, categoria.getMos_capacidad());
			consulta.setString(6, categoria.getMos_fec_inicio());
			consulta.setString(7, categoria.getMos_fec_fin());
			consulta.setString(8, categoria.getMos_tip_respaldo());
			consulta.setString(9, categoria.getMos_id_contenedor());
			consulta.setString(10, categoria.getEst_categoria());
			consulta.setString(11, categoria.getUsu_ingresa());
			consulta.setTimestamp(12, categoria.getFec_ingresa());
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

	public void modificarCategoria(modelo_categoria_dn categoria, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL categoriaDN_modificarCategoria(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, categoria.getNom_categoria().toUpperCase());
			consulta.setString(2, categoria.getDes_categoria().toUpperCase());
			consulta.setLong(3, categoria.getId_localidad());
			consulta.setString(4, categoria.getMos_capacidad());
			consulta.setString(5, categoria.getMos_fec_inicio());
			consulta.setString(6, categoria.getMos_fec_fin());
			consulta.setString(7, categoria.getMos_tip_respaldo());
			consulta.setString(8, categoria.getMos_id_contenedor());
			consulta.setString(9, categoria.getEst_categoria());
			consulta.setString(10, categoria.getUsu_modifica());
			consulta.setTimestamp(11, categoria.getFec_modifica());
			consulta.setLong(12, categoria.getId_categoria());
			consulta.setLong(13, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 14) {
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

	public void activarDesactivarCategoria(modelo_categoria_dn categoria) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL categoriaDN_desactivarCategoria(?, ?, ?, ?)}");
			consulta.setString(1, categoria.getEst_categoria());
			consulta.setString(2, categoria.getUsu_modifica());
			consulta.setTimestamp(3, categoria.getFec_modifica());
			consulta.setLong(4, categoria.getId_categoria());
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

	public void eliminarCategoria(Long id_categoria) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL categoriaDN_eliminarCategoria(?)}");
			consulta.setLong(1, id_categoria);
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
