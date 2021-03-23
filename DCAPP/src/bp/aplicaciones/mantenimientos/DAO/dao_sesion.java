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
import bp.aplicaciones.mantenimientos.modelo.modelo_sesion;

public class dao_sesion {

	// Sesion
	public List<modelo_sesion> obtenerSesiones(String criterio, String id_usuario, String id_localidad, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		List<modelo_sesion> lista_sesiones = new ArrayList<modelo_sesion>();
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL sesion_obtenerSesion(?,?,?,?)}");
			consulta.setString(1, criterio);
			consulta.setString(2, id_usuario);
			consulta.setString(3, id_localidad);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_sesiones.add(new modelo_sesion(resultado.getLong("id_sesion"), resultado.getString("cod_sesion"),
						resultado.getLong("id_usuario_1"), resultado.getString("nom_usuario"),
						resultado.getLong("id_localidad"), resultado.getString("est_sesion"),
						resultado.getTimestamp("fec_sesion_1"), resultado.getLong("id_usuario_2"),
						resultado.getTimestamp("fec_sesion_2")));
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
		return lista_sesiones;

	}

	public modelo_sesion obtenerDatosSesion(String cod_sesion, String id_usuario, String id_localidad, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		conexion conexion = new conexion();
		modelo_sesion sesion = null;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL sesion_obtenerSesion(?,?,?,?)}");
			consulta.setString(1, cod_sesion);
			consulta.setString(2, id_usuario);
			consulta.setString(3, id_localidad);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				sesion = new modelo_sesion(resultado.getLong("id_sesion"), resultado.getString("cod_sesion"),
						resultado.getLong("id_usuario_1"), resultado.getString("nom_usuario"),
						resultado.getLong("id_localidad"), resultado.getString("est_sesion"),
						resultado.getTimestamp("fec_sesion_1"), resultado.getLong("id_usuario_2"),
						resultado.getTimestamp("fec_sesion_2"));
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
		return sesion;

	}

	public void insertarSesion(modelo_sesion sesion) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL sesion_insertarSesion(?,?,?,?,?)}");
			consulta.setString(1, sesion.getCod_sesion());
			consulta.setLong(2, sesion.getId_usuario_1());
			consulta.setLong(3, sesion.getId_localidad());
			consulta.setString(4, sesion.getEst_sesion());
			consulta.setTimestamp(5, sesion.getFec_sesion_1());
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

	public void modificarSesion(modelo_sesion sesion) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL sesion_modificarSesion(?,?,?,?,?)}");
			consulta.setLong(1, sesion.getId_usuario_2());
			consulta.setTimestamp(2, sesion.getFec_sesion_2());
			consulta.setLong(3, sesion.getId_localidad());
			consulta.setString(4, sesion.getEst_sesion());
			consulta.setLong(5, sesion.getId_usuario_1());
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
