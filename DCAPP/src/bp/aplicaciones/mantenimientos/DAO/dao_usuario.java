package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.*;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

public class dao_usuario {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL usuario_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_usuario") + 1;
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

	public modelo_usuario obtenerUsuario(String use_usuario, String pas_usuario, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		conexion conexion = new conexion();
		modelo_usuario usuario = null;
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL usuario_obtenerUsuario(?, ?, ?)}");
			consulta.setString(1, use_usuario.toUpperCase());
			consulta.setString(2, pas_usuario);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				usuario = new modelo_usuario(resultado.getLong("id_usuario"), resultado.getString("use_usuario"),
						resultado.getString("pas_usuario"), resultado.getString("nom_usuario"),
						resultado.getString("ape_usuario"), resultado.getLong("id_perfil"),
						resultado.getString("cam_password"), resultado.getLong("id_localidad"),
						resultado.getString("est_usuario"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getTimestamp("fec_modifica"));
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
		return usuario;
	}

	public List<modelo_usuario> obtenerUsuarios(String criterio, int tipo, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_usuario> lista_usuarios = new ArrayList<modelo_usuario>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL usuario_obtenerUsuarios(?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setInt(3, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_usuarios.add(new modelo_usuario(resultado.getLong("id_usuario"),
						resultado.getString("use_usuario"), resultado.getString("pas_usuario"),
						resultado.getString("nom_usuario"), resultado.getString("ape_usuario"),
						resultado.getLong("id_perfil"), resultado.getString("nom_perfil"),
						resultado.getString("cam_password"), resultado.getLong("id_localidad"),
						resultado.getString("nom_localidad"), resultado.getString("est_usuario"),
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
		return lista_usuarios;
	}

	public void insertarUsuario(modelo_usuario usuario) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL usuario_insertarUsuario (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, usuario.getUse_usuario());
			consulta.setString(3, usuario.getPas_usuario());
			consulta.setString(4, usuario.getNom_usuario().toUpperCase());
			consulta.setString(5, usuario.getApe_usuario().toUpperCase());
			consulta.setLong(6, usuario.getId_perfil());
			consulta.setString(7, usuario.getCam_password());
			consulta.setLong(8, usuario.getId_localidad());
			consulta.setString(9, usuario.getEst_usuario());
			consulta.setString(10, usuario.getUsu_ingresa());
			consulta.setTimestamp(11, usuario.getFec_ingresa());
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

	public void modificarUsuario(modelo_usuario usuario, modelo_solicitud solicitud, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL usuario_modificarUsuario (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, usuario.getPas_usuario());
			consulta.setString(2, usuario.getNom_usuario().toUpperCase());
			consulta.setString(3, usuario.getApe_usuario().toUpperCase());
			consulta.setLong(4, usuario.getId_perfil());
			consulta.setString(5, usuario.getCam_password());
			consulta.setLong(6, usuario.getId_localidad());
			consulta.setString(7, usuario.getEst_usuario());
			consulta.setString(8, usuario.getUsu_modifica());
			consulta.setTimestamp(9, usuario.getFec_modifica());
			consulta.setLong(10, usuario.getId_usuario());
			consulta.setLong(11, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 4) {
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

	public void cambiarContrasena(modelo_usuario usuario) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL usuario_cambiarContrasena (?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, usuario.getPas_usuario());
			consulta.setString(2, usuario.getCam_password());
			consulta.setString(3, usuario.getEst_usuario());
			consulta.setString(4, usuario.getUsu_modifica());
			consulta.setTimestamp(5, usuario.getFec_modifica());
			consulta.setLong(6, usuario.getId_usuario());
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

	public void cambiarLocalidad(modelo_usuario usuario) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL usuario_cambiarLocalidad (?, ?, ?, ?)}");
			consulta.setLong(1, usuario.getId_localidad());
			consulta.setString(2, usuario.getUsu_modifica());
			consulta.setTimestamp(3, usuario.getFec_modifica());
			consulta.setLong(4, usuario.getId_usuario());
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

	public void activarDesactivarUsuario(modelo_usuario usuario) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL usuario_desactivarUsuario (?, ?, ?, ?)}");
			consulta.setString(1, usuario.getEst_usuario());
			consulta.setString(2, usuario.getUsu_modifica());
			consulta.setTimestamp(3, usuario.getFec_modifica());
			consulta.setLong(4, usuario.getId_usuario());
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

	public void eliminarUsuario(Long id_usuario) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL usuario_eliminarUsuario (?)}");
			consulta.setLong(1, id_usuario);
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
