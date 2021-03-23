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
import bp.aplicaciones.mantenimientos.modelo.modelo_informativo;

public class dao_informativo {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL informativo_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_informativo") + 1;
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

	public List<modelo_informativo> obtenerInformativos(String criterio, int tipo, long id, int limite, String fecha_inicio, String fecha_fin)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_informativo> lista_informativos = new ArrayList<modelo_informativo>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL informativo_obtenerInformativos(?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			consulta.setLong(3, id);
			consulta.setInt(4, limite);
			consulta.setString(5, fecha_inicio);
			consulta.setString(6, fecha_fin);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_informativos.add(new modelo_informativo(resultado.getLong("id_informativo"),
						resultado.getString("descripcion"), resultado.getString("se_publica"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_localidad"), resultado.getString("est_informativo"),
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
		return lista_informativos;
	}

	public modelo_informativo obtenerInformativo()
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		modelo_informativo informativo = new modelo_informativo();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL informativo_obtenerInformativo()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				informativo = new modelo_informativo(resultado.getLong("id_informativo"),
						resultado.getString("descripcion"), resultado.getString("se_publica"),
						resultado.getTimestamp("fec_inicio"), resultado.getTimestamp("fec_fin"),
						resultado.getLong("id_localidad"), resultado.getString("est_informativo"),
						resultado.getString("usu_ingresa"), resultado.getTimestamp("fec_ingresa"),
						resultado.getString("usu_modifica"), resultado.getTimestamp("fec_modifica"));

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
		return informativo;
	}

	public void insertarInformativo(modelo_informativo informativo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		long id = obtenerNuevoId();
		try {
			PreparedStatement consulta = null;
//			consulta = conexion.abrir().prepareStatement("{CALL informativo_cambiarEstadoPublicacion(?)}");
//			consulta.setString(1, informativo.getSe_publica());
//			consulta.executeUpdate();
//			consulta.close();
			consulta = conexion.abrir().prepareStatement("{CALL informativo_insertarInformativo(?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setString(2, informativo.getDescripcion());
			consulta.setString(3, informativo.getSe_publica());
			consulta.setTimestamp(4, informativo.getFec_inicio());
			consulta.setTimestamp(5, informativo.getFec_fin());
			consulta.setLong(6, informativo.getId_localidad());
			consulta.setString(7, informativo.getEst_informativo());
			consulta.setString(8, informativo.getUsu_ingresa());
			consulta.setTimestamp(9, informativo.getFec_ingresa());
			consulta.executeUpdate();
			consulta.close();
			conexion.abrir().commit();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

	public void modificarInformativo(modelo_informativo informativo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
//			consulta = conexion.abrir().prepareStatement("{CALL informativo_cambiarEstadoPublicacion(?)}");
//			consulta.setString(1, informativo.getSe_publica());
//			consulta.executeUpdate();
//			consulta.close();
			consulta = conexion.abrir().prepareStatement("{CALL informativo_modificarInformativo(?,?,?,?,?,?,?,?,?)}");
			consulta.setString(1, informativo.getDescripcion());
			consulta.setString(2, informativo.getSe_publica());
			consulta.setTimestamp(3, informativo.getFec_inicio());
			consulta.setTimestamp(4, informativo.getFec_fin());
			consulta.setLong(5, informativo.getId_localidad());
			consulta.setString(6, informativo.getEst_informativo());
			consulta.setString(7, informativo.getUsu_ingresa());
			consulta.setTimestamp(8, informativo.getFec_ingresa());
			consulta.setLong(9, informativo.getId_informativo());
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

	public void eliminarInformativo(long id_informativo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL informativo_eliminarInformativo(?)}");
			consulta.setLong(1, id_informativo);
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

}
