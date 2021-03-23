package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

//Solicitante
public class dao_solicitante {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL solicitante_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_solicitante") + 1;
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

	public List<modelo_solicitante> obtenerSolicitantes(String criterio, int tipo, String localidad,
			String mantenimiento_opcion, int limite)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_solicitante> lista_solicitantes = new ArrayList<modelo_solicitante>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL solicitante_obtenerSolicitantes(?,?,?,?,?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setString(3, localidad);
			consulta.setString(4, mantenimiento_opcion);
			consulta.setInt(5, limite);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_solicitantes.add(new modelo_solicitante(resultado.getLong("id_solicitante"),
						resultado.getLong("id_tip_documento"), resultado.getString("nom_tip_documento"),
						resultado.getString("num_documento"), resultado.getString("nom_solicitante"),
						resultado.getString("ape_solicitante"), resultado.getLong("id_empresa"),
						resultado.getString("nom_empresa"), resultado.getString("est_solicitante"),
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
		return lista_solicitantes;
	}

	public void insertarSolicitante(modelo_solicitante solicitante) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL solicitante_insertarSolicitante (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, solicitante.getId_tip_documento());
			consulta.setString(3, solicitante.getNum_documento());
			consulta.setString(4, solicitante.getNom_solicitante().toUpperCase());
			consulta.setString(5, solicitante.getApe_solicitante().toUpperCase());
			consulta.setLong(6, solicitante.getId_empresa());
			consulta.setString(7, solicitante.getEst_solicitante());
			consulta.setString(8, solicitante.getUsu_ingresa());
			consulta.setTimestamp(9, solicitante.getFec_ingresa());
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

	public void modificarSolicitante(modelo_solicitante solicitante, modelo_solicitud solicitud, int tipo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL solicitante_modificarSolicitante (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, solicitante.getId_tip_documento());
			consulta.setString(2, solicitante.getNum_documento());
			consulta.setString(3, solicitante.getNom_solicitante().toUpperCase());
			consulta.setString(4, solicitante.getApe_solicitante().toUpperCase());
			consulta.setLong(5, solicitante.getId_empresa());
			consulta.setString(6, solicitante.getEst_solicitante());
			consulta.setString(7, solicitante.getUsu_modifica());
			consulta.setTimestamp(8, solicitante.getFec_modifica());
			consulta.setLong(9, solicitante.getId_solicitante());
			consulta.setLong(10, tipo);
			consulta.executeUpdate();
			if (solicitud.getId_mantenimiento() == 1) {
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

	public void activarDesactivarSolicitante(modelo_solicitante solicitante) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL solicitante_desactivarSolicitante (?, ?, ?, ?)}");
			consulta.setString(1, solicitante.getEst_solicitante());
			consulta.setString(2, solicitante.getUsu_modifica());
			consulta.setTimestamp(3, solicitante.getFec_modifica());
			consulta.setLong(4, solicitante.getId_solicitante());
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

	public void eliminarSolicitante(Long id_solicitante) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL solicitante_eliminarSolicitante (?)}");
			consulta.setLong(1, id_solicitante);
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
