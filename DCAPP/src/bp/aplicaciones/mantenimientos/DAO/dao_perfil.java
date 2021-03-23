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
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;

public class dao_perfil {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL perfil_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_perfil") + 1;
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

	public List<modelo_perfil> obtenerPerfiles(String criterio, int tipo, long id)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_perfil> lista_perfiles = new ArrayList<modelo_perfil>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL perfil_obtenerPerfiles(?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_perfiles.add(new modelo_perfil(resultado.getLong("id_perfil"), resultado.getString("nom_perfil"),
						resultado.getString("consultar"), resultado.getString("insertar"),
						resultado.getString("modificar"), resultado.getString("relacionar"),
						resultado.getString("desactivar"), resultado.getString("eliminar"),
						resultado.getString("solicitar"), resultado.getString("revisar"),
						resultado.getString("aprobar"), resultado.getString("ejecutar"),
						resultado.getString("est_perfil"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
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
		return lista_perfiles;
	}

	public void insertarPerfil(modelo_perfil perfil) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL perfil_insertarPerfil (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, id);
			consulta.setString(2, perfil.getNom_perfil().toUpperCase());
			consulta.setString(3, perfil.getConsultar());
			consulta.setString(4, perfil.getInsertar());
			consulta.setString(5, perfil.getModificar());
			consulta.setString(6, perfil.getRelacionar());
			consulta.setString(7, perfil.getDesactivar());
			consulta.setString(8, perfil.getEliminar());
			consulta.setString(9, perfil.getSolicitar());
			consulta.setString(10, perfil.getRevisar());
			consulta.setString(11, perfil.getAprobar());
			consulta.setString(12, perfil.getEjecutar());
			consulta.setString(13, perfil.getEst_perfil());
			consulta.setString(14, perfil.getUsu_ingresa());
			consulta.setTimestamp(15, perfil.getFec_ingresa());
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

	public void modificarPerfil(modelo_perfil perfil) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir()
					.prepareStatement("{CALL perfil_modificarPerfil (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, perfil.getNom_perfil().toUpperCase());
			consulta.setString(2, perfil.getConsultar());
			consulta.setString(3, perfil.getInsertar());
			consulta.setString(4, perfil.getModificar());
			consulta.setString(5, perfil.getRelacionar());
			consulta.setString(6, perfil.getDesactivar());
			consulta.setString(7, perfil.getEliminar());
			consulta.setString(8, perfil.getSolicitar());
			consulta.setString(9, perfil.getRevisar());
			consulta.setString(10, perfil.getAprobar());
			consulta.setString(11, perfil.getEjecutar());
			consulta.setString(12, perfil.getEst_perfil());
			consulta.setString(13, perfil.getUsu_ingresa());
			consulta.setTimestamp(14, perfil.getFec_ingresa());
			consulta.setLong(15, perfil.getId_perfil());
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

	public void activarDesactivarPerfil(modelo_perfil perfil) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL perfil_desactivarPerfil (?, ?, ?, ?)}");
			consulta.setString(1, perfil.getEst_perfil());
			consulta.setString(2, perfil.getUsu_modifica());
			consulta.setTimestamp(3, perfil.getFec_modifica());
			consulta.setLong(4, perfil.getId_perfil());
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

	public void eliminarPerfil(Long id_perfil) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL perfil_eliminarPerfil (?)}");
			consulta.setLong(1, id_perfil);
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
